/*
** Licensed under the Apache License, Version 2.0 (the "License"); 
** you may not use this file except in compliance with the License. 
** You may obtain a copy of the License at 
**
**     http://www.apache.org/licenses/LICENSE-2.0 
**
** Unless required by applicable law or agreed to in writing, software 
** distributed under the License is distributed on an "AS IS" BASIS, 
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
** See the License for the specific language governing permissions and 
** limitations under the License.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <dirent.h>
#include <errno.h>
#include <sys/stat.h>

#include <unistd.h>
#include <time.h>

#include <pwd.h>

#include <sqlite3.h>

#define DBPATH "/data/data/com.koushikdutta.superuser/databases/superuser.sqlite"

static int g_puid;

static void printRow(int argc, char** argv, char** azColName)
{
	int i;
	for (i = 0; i < argc; i++)
	{
		printf("%s: %s\n", azColName[i], argv[i]);
	}
}

typedef struct whitelistCallInfo whitelistCallInfo;
struct whitelistCallInfo
{
	sqlite3* db;
	int count;
};

// 更新白名单数据库
static int whitelistCallback(void *data, int argc, char **argv, char **azColName)
{	
	whitelistCallInfo* callInfo = (whitelistCallInfo*)data;
	// note the count
	int count = atoi(argv[2]);
	callInfo->count = count;
	// remove whitelist entries that are expired
	if (count - 1 <= 0)
	{
		char remove[1024];
		sprintf(remove, "delete from whitelist where _id='%s';", argv[0]);
		sqlite3_exec(callInfo->db, remove, NULL, NULL, NULL);
		return 0;
	}

	char update[1024];
	sprintf(update, "update whitelist set count=%d where _id='%s';", count, argv[0]);
	sqlite3_exec(callInfo->db, update, NULL, NULL, NULL);
	return 0;
}

// 查询白名单数据库，是否包括g_puid
static int checkWhitelist()
{
	sqlite3 *db;
	int rc = sqlite3_open_v2(DBPATH, &db, SQLITE_OPEN_READWRITE, NULL);
	if (!rc)
	{
		char *errorMessage;
		char query[1024];
		sprintf(query, "select * from whitelist where _id=%d limit 1;", g_puid);
		struct whitelistCallInfo callInfo;
		callInfo.count = 0;
		callInfo.db = db;
		rc = sqlite3_exec(db, query, whitelistCallback, &callInfo, &errorMessage);
		if (rc != SQLITE_OK)
		{
			sqlite3_close(db);
			return 0;
		}
		sqlite3_close(db);
		return callInfo.count;
	}
	sqlite3_close(db);
	return 0;
}

static int executionFailure(char *context)
{
	fprintf(stderr, "su: %s. Error:%s\n", context, strerror(errno));
	return -errno;
}

static int permissionDenied()
{
	// the superuser activity couldn't be started
	printf("su: permission denied\n");
	return 1;
}

int main(int argc, char **argv)
{
	struct stat stats;
	struct passwd *pw;
	int uid = 0;
	int gid = 0;

    // 获取父进程的pid，也就是调用su的java/c进程的pid
	int ppid = getppid();
	char szppid[256];
	sprintf(szppid, "/proc/%d", ppid);
	stat(szppid, &stats);
	g_puid = stats.st_uid;  // 获取调用进程的uid

	// lets make sure the caller is allowed to execute this
	if (!checkWhitelist())      // 调用进程uid不再白名单中, 需要弹出对话框提示用户
	{
        // 弹出对话框提示用户
		char sysCmd[1024];
        sprintf(sysCmd, "am start -a android.intent.action.MAIN -n "
                "com.koushikdutta.superuser/com.koushikdutta.superuser.SuperuserRequestActivity"
                " --ei uid %d --ei pid %d > /dev/null", g_puid, ppid);

		// XXX: 启动am时，有时app_process会crash。从Log中看是初始化java虚拟机时，
		// 系统环境LD_LIBRARY_PATH获取为空, 导致虚拟机启动失败。
		// Workaround是将setgid, setuid放到"system()"调用之前，并且手动设置LD_LIBRARY_PATH
		if (system(sysCmd))
			return executionFailure("am.");

		int found = 0;
		int i;
		for (i = 0; i < 10; i++)
		{
			sleep(1);
			// 0 means waiting for user input
			// > 0 means yes/always
			// < 0 means no
			int checkResult = checkWhitelist();
			if (checkResult > 0)
			{
				found = 1;
				break;
			}
			else if (checkResult < 0)
			{
				// user hit no
				return permissionDenied();
			}
		}

		if (!found)
			return permissionDenied();
	}

    // 调用进程uid在白名单中, 或已经获得权限
	if(setgid(gid) || setuid(uid)) 
		return permissionDenied();

    // XXX: 这一段需要修改成原生su中的最后一段的执行代码
	char *exec_args[argc + 1];
	exec_args[argc] = NULL;
	exec_args[0] = "sh";
	int i;
	for (i = 1; i < argc; i++)
	{
		exec_args[i] = argv[i];
	}
	execv("/system/bin/sh", exec_args);
	return executionFailure("sh");
}

