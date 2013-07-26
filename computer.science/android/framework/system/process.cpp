frameworks/base/core/java/android/os/Process.java
{
    // 对应native层
    frameworks/base/core/jni/android_util_Process.cpp

    定义java层的UID, 和native层完全一致
    ...
    public static final int FIRST_APPLICATION_UID = 10000;
    public static final int LAST_APPLICATION_UID = 99999;
    ...

    定义了线程的优先级
    public static final int THREAD_PRIORITY_DEFAULT = 0;
    public static final int THREAD_PRIORITY_LOWEST = 19;
    public static final int THREAD_PRIORITY_BACKGROUND = 10;
    ...

    // 和zygote通信的socket
    static LocalSocket sZygoteSocket;
    static DataInputStream sZygoteInputStream;
    static BufferedWriter sZygoteWriter;

    /**
     * Tries to open socket to Zygote process if not already open. If
     * already open, does nothing.  May block and retry.
     */
    private static void openZygoteSocketIfNeeded() 
                        
    /**
     * Start a new process.
     *
     * 通过向zygote发送socket消息，让zygote启动新的进程。
     * 并在进程中的main中运行processClass
     *
     */
    public static final ProcessStartResult start(final String processClass,
            final String niceName,
            int uid, int gid, int[] gids,
            int debugFlags, int targetSdkVersion,
            String[] zygoteArgs) {
        return startViaZygote(processClass, niceName, uid, gid, gids,
                debugFlags, targetSdkVersion, zygoteArgs);
    }
          
    /** @hide */
    public static final native int setUid(int uid);
    /** @hide */
    public static final native int setGid(int uid);
    ...
}
