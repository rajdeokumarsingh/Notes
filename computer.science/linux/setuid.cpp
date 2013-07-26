reference:
    http://en.wikipedia.org/wiki/Setuid

scenario:
    changing their login password
        jiangrui@jiangrui-laptop:linux$ ls -l /usr/bin/passwd 
        -rwsr-xr-x 1 root root 42792 2011-02-15 06:13 /usr/bin/passwd

    the ping command, which must send and listen for control packets on a network interface.
        jiangrui@jiangrui-laptop:linux$ ls -l /bin/ping
        -rwsr-xr-x 1 root root 35648 2010-07-28 22:51 /bin/ping

concept:
    user id
        of a executable file
        of a running process

setuid / setgid 
    short for "set user ID upon execution" 
    short for "set group ID upon execution"

    allow users to run an executable with the permissions of the executable's owner or group 

    used to allow users on a computer system to run programs with 
        temporarily elevated privileges in order to perform a specific task

    setuid and setgid are needed for tasks that require higher privileges than those which common users have, 
        such as changing their login password

setuid/setgid on executables
    executable file with the setuid attribute
    normal users who have permission to execute this file
        gain the privileges of the user who owns the file (commonly root) within the created process.

    When root privileges have been gained within the process, 
        the application can then perform tasks on the system that regular users normally would be restricted from doing

        For example:
            -rwsr-xr-x 1 root root 159620 2012-05-14 10:19 /home/jiangrui/android/android-sdk/platform-tools/adb

            1. The owner of "adb" is root.
            2. We run "adb" in a shell of normal user, 
            3. "adb" will gain root's privileges.


    The invoking user will be prohibited by the system from altering the new process in any way, 
        such as by using ptrace, LD_LIBRARY_PATH or sending signals to it 
        (signals from the terminal will still be accepted, however).

    executable shell scripts
        Due to potential security issues,[3] many operating systems ignore the setuid attribute 
            when applied to executable shell scripts.

    use "chmod" to setuid/setgid
        setting the high-order octal digit to 4 (for setuid) or 2 (for setgid).
        For example:
        "chmod 6711 file" / "chmod ug+s" 
            set both the setuid and setgid bits (2+4=6)
            make the file read/write/executable for the owner (7), 
            and executable by the group (first 1) and others (second 1). 

            When a user other than the owner executes the file, the process will run 
                with user and group permissions set upon it by its owner
                if the file is owned by user root and group wheel, 
                    it will run as root:wheel no matter who executes the file.

////////////////////////////////////////////////////////////////////////////////
setuid and setgid on directories
    when set on a directory, have an entirely different meaning.

    setuid:
        The setuid permission set on a directory is ignored on UNIX and Linux systems.        

    setgid:
            chmod g+s

        causes new files and subdirectories created within it to inherit its group ID
        rather than the primary group ID of the user who created the file 
            the owner ID is never affected, only the group ID

        Newly created subdirectories inherit the setgid bit.

        Thus, this enables a shared workspace for a group without the inconvenience of 
            requiring group members to explicitly change their current group before creating new files or directories. 

        is not applied to existing entities/files/directories

    Setting the setgid bit on existing subdirectories must be done manually, with a command such as the following:
        [root@foo]# find /path/to/directory -type d -exec chmod g+s {} \;

// For example:
// create a dir "test" by root
jiangrui@jiangrui-laptop:~$ pwd
/home/jiangrui
jiangrui@jiangrui-laptop:~$ sudo bash
root@jiangrui-laptop:~# mkdir test
root@jiangrui-laptop:~# ls -l test
总用量 0
root@jiangrui-laptop:~# ls -ld test
drwxr-xr-x 2 root root 4096 2013-01-06 15:16 test

// create a file in "test" by jiangrui
root@jiangrui-laptop:test# exit
exit
jiangrui@jiangrui-laptop:~$ pwd
/home/jiangrui
jiangrui@jiangrui-laptop:~$ cd test/
jiangrui@jiangrui-laptop:test$ ls
jiangrui@jiangrui-laptop:test$ touch gg
jiangrui@jiangrui-laptop:test$ ls -l gg 
-rw-r--r-- 1 jiangrui jiangrui 0 2013-01-06 15:17 gg

// sudo chmod g+s .
jiangrui@jiangrui-laptop:test$ sudo chmod g+s .
jiangrui@jiangrui-laptop:test$ ls -ld .
drwxrwsrwx 2 root root 4096 2013-01-06 15:17 .
jiangrui@jiangrui-laptop:test$ 

// create a file in "test" after setgid by jiangrui
jiangrui@jiangrui-laptop:test$ touch gg_add_s
jiangrui@jiangrui-laptop:test$ ls -l
总用量 0
-rw-r--r-- 1 jiangrui jiangrui 0 2013-01-06 15:17 gg
-rw-r--r-- 1 jiangrui root     0 2013-01-06 15:19 gg_add_s

////////////////////////////////////////////////////////////////////////////////
// 使用jiangrui权限的passwd无法修改jiangrui的密码
jiangrui@jiangrui-laptop:~$ cp /usr/bin/passwd .
jiangrui@jiangrui-laptop:~$ ls -l passwd 
-rwxr-xr-x 1 jiangrui jiangrui 42792 2013-01-06 14:44 passwd
jiangrui@jiangrui-laptop:~$ ./passwd 
更改 jiangrui 的密码。
（当前）UNIX 密码： 
输入新的 UNIX 密码： 
重新输入新的 UNIX 密码： 
passwd：认证令牌操作错误
passwd: password unchanged

// 将passwd的owner修改成root后仍然无法修改jiangrui的密码
jiangrui@jiangrui-laptop:~$ sudo chown root:root ./passwd 
jiangrui@jiangrui-laptop:~$ ls -l ./passwd 
-rwxr-xr-x 1 root root 42792 2013-01-06 14:44 ./passwd
jiangrui@jiangrui-laptop:~$ ./passwd 
更改 jiangrui 的密码。
（当前）UNIX 密码： 
输入新的 UNIX 密码： 
重新输入新的 UNIX 密码： 
passwd：认证令牌操作错误
passwd: password unchanged

// 将passwd的owner修改成root, 并且setuid后，才可以修改jiangrui的密码
jiangrui@jiangrui-laptop:~$ sudo chmod u+s ./passwd 
jiangrui@jiangrui-laptop:~$ ls -l ./passwd 
-rwsr-xr-x 1 root root 42792 2013-01-06 14:44 ./passwd
jiangrui@jiangrui-laptop:~$ ./passwd 
更改 jiangrui 的密码。
（当前）UNIX 密码： 
输入新的 UNIX 密码： 
重新输入新的 UNIX 密码： 
passwd：已成功更新密码
////////////////////////////////////////////////////////////////////////////////
