Native层的定义:
    system/core/include/private/android_filesystem_config.h
    {
        #define AID_ROOT             0  /* traditional unix root user */
        #define AID_SYSTEM        1000  /* system server */
        ...
        #define AID_APP          10000 /* first app user */

        // id和名字的对应
        static const struct android_id_info android_ids[] = {
            { "root",      AID_ROOT, },
            { "system",    AID_SYSTEM, },
            ...
        }

        // 文件夹对应的uid和权限
        static struct fs_path_config android_dirs[] = {
            ...
            { 00750, AID_ROOT,   AID_SHELL,  "sbin" },
            { 00755, AID_ROOT,   AID_SHELL,  "system/bin" },
            { 00755, AID_ROOT,   AID_SHELL,  "system/xbin" },
            ...

        }

        // 系统文件对应的uid和权限
        static struct fs_path_config android_files[] = {
            { 00440, AID_ROOT,      AID_SHELL,     "system/etc/init.goldfish.rc" },
            { 00550, AID_ROOT,      AID_SHELL,     "system/etc/init.goldfish.sh" },
            { 00440, AID_ROOT,      AID_SHELL,     "system/etc/init.trout.rc" },
            ...
            /* the following five files are INTENTIONALLY set-uid, but they
             * are NOT included on user builds. */
            { 06755, AID_ROOT,      AID_ROOT,      "system/xbin/su" },
            ...
            { 06755, AID_ROOT,      AID_ROOT,      "system/xbin/tcpdump" },
        }

    }

Java层的定义：
    frameworks/base/core/java/android/os/Process.java
    {
        定义java层的UID, 和native层完全一致
        ...
        public static final int FIRST_APPLICATION_UID = 10000;
        public static final int LAST_APPLICATION_UID = 99999;
        ...

        /** @hide */
        public static final native int setUid(int uid);
        /** @hide */
        public static final native int setGid(int uid);

        ...
        定义了线程的优先级

    }

    // 应用程序UID的分配
    frameworks/base/services/java/com/android/server/pm/Settings.java
    {
        private final ArrayList<Object> mUserIds = new ArrayList<Object>();

        // Returns -1 if we could not find an available UserId to assign
        private int newUserIdLPw(Object obj) {
            // Let's be stupidly inefficient for now...
            final int N = mUserIds.size();
            for (int i = 0; i < N; i++) {
                if (mUserIds.get(i) == null) {
                    mUserIds.set(i, obj);
                    return PackageManagerService.FIRST_APPLICATION_UID + i;
                }
            }

            // None left?
            if (N >= PackageManagerService.MAX_APPLICATION_UIDS) {
                return -1;
            }

            mUserIds.add(obj);
            return PackageManagerService.FIRST_APPLICATION_UID + N;
        }

        private boolean addUserIdLPw(int uid, Object obj, Object name)
    }

// 创建共享用户id
SharedUserSetting getSharedUserLPw(
        String name, int pkgFlags, boolean create) {
    SharedUserSetting s = mSharedUsers.get(name);
    if (s == null) {
        s = new SharedUserSetting(name, pkgFlags);
        s.userId = newUserIdLPw(s);
        mSharedUsers.put(name, s);
    }
    return s;
}


private PackageSetting getPackageLPw(String name, PackageSetting origPackage,
        String realName, SharedUserSetting sharedUser, File codePath, File resourcePath,
        String nativeLibraryPathString, int vc, int pkgFlags, boolean create, boolean add) {

    // Create a new PackageSettings entry. this can end up here because
    // of code path mismatch or user id mismatch of an updated system partition

    // 创建一个PackageSettings时会创建userId
    // Assign new user id
    p.userId = newUserIdLPw(p);
}




