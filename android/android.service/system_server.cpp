


相关进程
# ps
USER     PID   PPID  VSIZE  RSS     WCHAN    PC         NAME
system    1691  1627  174640 35232 ffffffff afd0dd2c S system_server


System Server是Android系统的核心，他在Dalvik虚拟机启动后立即开始初始化和运行。
其它的系统服务在System Server进程的环境中运行

base/services/java/com/android/server/SystemServer.java
public class SystemServer
    /**                       
     * This method is called from Zygote to initialize the system. This will cause the native
     * services (SurfaceFlinger, AudioFlinger, etc..) to be started. After that it will call back                                            
     * up into init2() to start the Android services.                                                                                        
     */
    native public static void init1(String[] args);  

    public static void main(String[] args) {
        System.loadLibrary("android_servers");
        // start the native services
        init1(args); // -->android_server_SystemServer_init1()

    // start the java services
    public static final void init2() { // <--system_init()
        Thread thr = new ServerThread();
        thr.setName("android.server.ServerThread");
        thr.start();

        { // java services
            LightsService lights;
            PowerManagerService power;
            BatteryService battery;
            ConnectivityServiceExt connectivity;
            IPackageManager pm;
            Context context;
            WindowManagerService wm;
            BluetoothService bluetooth;
            BluetoothA2dpService bluetoothA2dp;
            HeadsetObserver headset;
            DockObserver dock;
            UiModeManagerService uiMode;
            RecognitionManagerService recognition;
            ThrottleService throttle;
            DevicePolicyManagerService devicePolicy;
            StatusBarService statusBar;
            InputMethodManagerService imm;
            AppWidgetService appWidget;
            NotificationManagerService notification;
            WallpaperManagerService wallpaper;
            LocationManagerService location;
        }

base/services/jni/com_android_server_SystemServer.cpp
    static void android_server_SystemServer_init1(JNIEnv* env, jobject clazz)
        system_init();

    static JNINativeMethod gMethods[] = {
        /* name, signature, funcPtr */
        { "init1", "([Ljava/lang/String;)V", (void*) android_server_SystemServer_init1 },

    int register_android_server_SystemServer(JNIEnv* env)
        return jniRegisterNativeMethods(env, "com/android/server/SystemServer",
                gMethods, NELEM(gMethods));

base/cmds/system_server/library/system_init.cpp
    // 在下面函数中启动native系统服务
    extern "C" status_t system_init()
        SurfaceFlinger::instantiate();
        AudioFlinger::instantiate();
        MediaPlayerService::instantiate();
        CameraService::instantiate();
        AudioPolicyService::instantiate();

        // call to java layer to start java service
        AndroidRuntime* runtime = AndroidRuntime::getRuntime();
        runtime->callStatic("com/android/server/SystemServer", "init2");

================================================================================
创建过程
base/core/java/com/android/internal/os/ZygoteInit.java
    /** Prepare the arguments and fork for the system server process. */
    private static boolean startSystemServer()
    /* Hardcoded command line to start the system server */
        String args[] = {
            "--setuid=1000",
            "--setgid=1000",
            "--setgroups=1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,3001,3002,3003",
            "--capabilities=130104352,130104352",
            "--runtime-init",
            "--nice-name=system_server",
            "com.android.server.SystemServer",
        };
        ZygoteConnection.Arguments parsedArgs = null;

        parsedArgs = new ZygoteConnection.Arguments(args);

        /*
         * Enable debugging of the system process if *either* the command line flags
         * indicate it should be debuggable or the ro.debuggable system property
         * is set to "1"
         */
        int debugFlags = parsedArgs.debugFlags;
        if ("1".equals(SystemProperties.get("ro.debuggable")))
            debugFlags |= Zygote.DEBUG_ENABLE_DEBUGGER;

        /* Request to fork the system server process */
        pid = Zygote.forkSystemServer(
                parsedArgs.uid, parsedArgs.gid,
                parsedArgs.gids, debugFlags, null,
                parsedArgs.permittedCapabilities,
                parsedArgs.effectiveCapabilities);
                    |
                    V
libcore/dalvik/src/main/java/dalvik/system/Zygote.java
    native public static int forkSystemServer(int uid, int gid,
            int[] gids, int debugFlags, int[][] rlimits,
            long permittedCapabilities, long effectiveCapabilities);
                |
                V
vm/native/dalvik_system_Zygote.c
static void Dalvik_dalvik_system_Zygote_forkSystemServer(
        const u4* args, JValue* pResult)

