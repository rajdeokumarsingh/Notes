1. PC端通过脚本启动ViewServer (hierachyview或python测试脚本)

    adb shell service call window 1 i32 4939

2. Android端:

    这段脚本对应下面aidl startViewServer调用
        interface IWindowManager
        {
            /**
             * ===== NOTICE =====
             * The first three methods must remain the first three methods. Scripts
             * and tools rely on their transaction number to work properly.
             */
            // This is used for debugging
            boolean startViewServer(int port);   // Transaction #1
            // adb shell service call window 1 i32 4939
            // 1   对应第一个Transaction
            // i32 对应参数类型 int
            // 4939 对应参数port
            ...
        }

Android端system server进程：

    WindowManagerService.startViewServer()被调用创建ViewServer
    ViewServer会监听local socket 4939

3. PC端向本地端口4939发送如下命令:

    // Protocol commands

    ViewServer实现
    {
        // 仅仅返回协议版本号和Server版本号

        // Returns the protocol version
        private static final String COMMAND_PROTOCOL_VERSION = "PROTOCOL";
        // Returns the server version
        private static final String COMMAND_SERVER_VERSION = "SERVER";
    }

    WindowManagerService实现
    {
        // Lists all of the available windows in the system
        private static final String COMMAND_WINDOW_MANAGER_LIST = "LIST";
        // Keeps a connection open and notifies when the list of windows changes
        private static final String COMMAND_WINDOW_MANAGER_AUTOLIST = "AUTOLIST";
        // Returns the focused window
        private static final String COMMAND_WINDOW_MANAGER_GET_FOCUS = "GET_FOCUS";
    }

    ViewDebug实现
    {
        private static final String REMOTE_COMMAND_DUMP = "DUMP";
        private static final String REMOTE_COMMAND_CAPTURE = "CAPTURE";
        private static final String REMOTE_COMMAND_INVALIDATE = "INVALIDATE";
        private static final String REMOTE_COMMAND_REQUEST_LAYOUT = "REQUEST_LAYOUT";
        private static final String REMOTE_PROFILE = "PROFILE";
        private static final String REMOTE_COMMAND_CAPTURE_LAYERS = "CAPTURE_LAYERS";
        private static final String REMOTE_COMMAND_OUTPUT_DISPLAYLIST = "OUTPUT_DISPLAYLIST";
    }

4. Android端system server进程：

ViewServer调用:
WindowManagerService
    boolean viewServerListWindows(Socket client) 
        // 处理"LIST"命令

        // 将WindowManagerService管理的所有window的状态变成字符串, 通过socket返回给pc端
        // 需要访问内部变量mWindows
        // final ArrayList<WindowState> mWindows = new ArrayList<WindowState>();

    boolean viewServerGetFocusedWindow(Socket client) 
        // 处理"GET_FOCUS"命令

        // 获取到当前foucused window的状态，通过socket返回给pc端

        // 需要调用 私有方法getFocusedWindow

    boolean viewServerWindowCommand(Socket client, String command, String parameters) 
        // 处理命令"DUMP", "CAPTURE", "INVALIDATE", "REQUEST_LAYOUT", "PROFILE", "CAPTURE_LAYERS", "OUTPUT_DISPLAYLIST", 

        // 通过传入的参数找到对应的window的数据
        // 通过该window的binder向该window发送命令
        // 将window binder返回的数据封装成字符串，并通过socket返回给PC端

    windowManagerAutolistLoop();
        注册一个listener到WindowManagerService

5. viewServerWindowCommand调用到

ViewRootImpl
    static class W extends IWindow.Stub 
         public void executeCommand(String command, String parameters, ParcelFileDescriptor out) 
             ViewDebug.dispatchCommand(view, command, parameters, clientStream);


            PtfViewDebug.java

             static void dispatchCommand(View view, String command, String parameters,
                     OutputStream clientStream) throws IOException {
                 if (PTF_COMMAND_DUMP.equalsIgnoreCase(command)) {
                     view = view.getRootView();
                     dump(view, clientStream);
                 } else {
                     // If this is not the PTF_DUMP command, sent it back to ViewDebug.
                     ViewDebug.dispatchCommand(view, command, parameters, clientStream);
                 }
             }



