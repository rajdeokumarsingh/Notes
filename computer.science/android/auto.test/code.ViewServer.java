
/**
 * The ViewServer is local socket server that can be used to communicate with the
 * views of the opened windows. Communication with the views is ensured by the
 * {@link com.android.server.wm.WindowManagerService} and is a cross-process operation.
 *
 * {@hide}
 */
class ViewServer implements Runnable {
    
    // server的端口号
    // The default port used to start view servers.
    public static final int VIEW_SERVER_DEFAULT_PORT = 4939;
    private static final int VIEW_SERVER_MAX_CONNECTIONS = 10;

    // Protocol commands, 文本命令, 
    // FIXME: where is "DUMP"
    {
        // Returns the protocol version
        private static final String COMMAND_PROTOCOL_VERSION = "PROTOCOL";
        // Returns the server version
        private static final String COMMAND_SERVER_VERSION = "SERVER";
        // Lists all of the available windows in the system
        private static final String COMMAND_WINDOW_MANAGER_LIST = "LIST";
        // Keeps a connection open and notifies when the list of windows changes
        private static final String COMMAND_WINDOW_MANAGER_AUTOLIST = "AUTOLIST";
        // Returns the focused window
        private static final String COMMAND_WINDOW_MANAGER_GET_FOCUS = "GET_FOCUS";
    }

    // service的socket
    private ServerSocket mServer;
    
    // server的端口
    private final int mPort;

    // 工作线程
    private Thread mThread;

    private final WindowManagerService mWindowManager;
    private ExecutorService mThreadPool;

    /**
     * Creates a new ViewServer associated with the specified window manager on the
     * specified local port. The server is not started by default.
     *
     * @param windowManager The window manager used to communicate with the views.
     * @param port The port for the server to listen to.
     *
     * @see #start()
     */
    ViewServer(WindowManagerService windowManager, int port) {
        mWindowManager = windowManager;
        mPort = port;
    }

    /**
     * Starts the server.
     *
     * @return True if the server was successfully created, or false if it already exists.
     * @throws IOException If the server cannot be created.
     *
     * @see #stop()
     * @see #isRunning()
     * @see WindowManagerService#startViewServer(int)
     */
    boolean start() throws IOException {
        // 创建socket, 工作线程，和线程池
        mServer = new ServerSocket(mPort, VIEW_SERVER_MAX_CONNECTIONS, InetAddress.getLocalHost());
        mThread = new Thread(this, "Remote View Server [port=" + mPort + "]");
        mThreadPool = Executors.newFixedThreadPool(VIEW_SERVER_MAX_CONNECTIONS);
        mThread.start();
    }

    /**
     * Stops the server.
     *
     * @return True if the server was stopped, false if an error occured or if the
     *         server wasn't started.
     *
     * @see #start()
     * @see #isRunning()
     * @see WindowManagerService#stopViewServer()
     */
    boolean stop() {
        mThread.interrupt();
        mThreadPool.shutdownNow();
        mServer.close();
    }

    /**
     * Main server loop.
     */
    public void run() {
        while (Thread.currentThread() == mThread) {
            Socket client = mServer.accept();
            mThreadPool.submit(new ViewServerWorker(client));
        }
    }

    // 将结果写回client
    private static boolean writeValue(Socket client, String value) {
        boolean result;
        BufferedWriter out = null;
        OutputStream clientStream = client.getOutputStream();
        out = new BufferedWriter(new OutputStreamWriter(clientStream), 8 * 1024);
        out.write(value);
        out.write("\n");
        out.flush();
    }

    WindowManagerService.WindowChangeListener
        public interface WindowChangeListener {
            public void windowsChanged();
            public void focusChanged();
        }

    class ViewServerWorker implements Runnable, WindowManagerService.WindowChangeListener {
        private Socket mClient;
        private boolean mNeedWindowListUpdate;
        private boolean mNeedFocusedWindowUpdate;

        public ViewServerWorker(Socket client) {
            mClient = client;
            mNeedWindowListUpdate = false;
            mNeedFocusedWindowUpdate = false;
        }

        public void run() {

            BufferedReader in = null;
                // 读取命令
                in = new BufferedReader(new InputStreamReader(mClient.getInputStream()), 1024);
                final String request = in.readLine();

                String command;
                String parameters;

                int index = request.indexOf(' ');
                if (index == -1) {
                    command = request;
                    parameters = "";
                } else {
                    command = request.substring(0, index);
                    parameters = request.substring(index + 1);
                }

                // 处理命令
                boolean result;
                if (COMMAND_PROTOCOL_VERSION.equalsIgnoreCase(command)) {
                    result = writeValue(mClient, VALUE_PROTOCOL_VERSION);
                } else if (COMMAND_SERVER_VERSION.equalsIgnoreCase(command)) {
                    result = writeValue(mClient, VALUE_SERVER_VERSION);
                } else if (COMMAND_WINDOW_MANAGER_LIST.equalsIgnoreCase(command)) {
                    result = mWindowManager.viewServerListWindows(mClient);
                } else if (COMMAND_WINDOW_MANAGER_GET_FOCUS.equalsIgnoreCase(command)) {
                    result = mWindowManager.viewServerGetFocusedWindow(mClient);
                } else if (COMMAND_WINDOW_MANAGER_AUTOLIST.equalsIgnoreCase(command)) {
                    result = windowManagerAutolistLoop();
                } else {
                    result = mWindowManager.viewServerWindowCommand(mClient,
                            command, parameters);
                }
            }

            public void windowsChanged() {
                mNeedWindowListUpdate = true;
                notifyAll();
            }

            public void focusChanged() {
                mNeedFocusedWindowUpdate = true;
                notifyAll();
            }

            // 返回list/focus update的消息
            private boolean windowManagerAutolistLoop() {
                mWindowManager.addWindowChangeListener(this);
                BufferedWriter out = null;
                    out = new BufferedWriter(new OutputStreamWriter(mClient.getOutputStream()));
                    while (!Thread.interrupted()) {
                        boolean needWindowListUpdate = false;
                        boolean needFocusedWindowUpdate = false;
                        while (!mNeedWindowListUpdate && !mNeedFocusedWindowUpdate) {
                            wait();
                        }
                        if (mNeedWindowListUpdate) {
                            mNeedWindowListUpdate = false;
                            needWindowListUpdate = true;
                        }
                        if (mNeedFocusedWindowUpdate) {
                            mNeedFocusedWindowUpdate = false;
                            needFocusedWindowUpdate = true;
                        }
                        if (needWindowListUpdate) {
                            out.write("LIST UPDATE\n");
                            out.flush();
                        }
                        if (needFocusedWindowUpdate) {
                            out.write("FOCUS UPDATE\n");
                            out.flush();
                        }
                    }
                }
            }
