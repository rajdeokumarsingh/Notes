network in framework

    // Consume requests in the RequestQueue
    // Each Host has a Connection and ConnectionThread
    ConnectionThread-->Thread 
        // ActivePool, manage ConnectionThreads
        RequestQueue.ConnectionManager mConnectionManager;
        // RequestQueue
        RequestFeeder mRequestFeeder;

        void requestStop()
            mRunning = false;
            mRequestFeeder.notify();
                // wake up ConnectionThread
                // it will stop because mRunning is false
         
        public void run()
            while (mRunning)
                /* Get a request to process */
                request = mRequestFeeder.getRequest();

                if (request == null) // wait
                // else 
                HttpHost proxy = mConnectionManager.getProxyHost();
                host = (proxy == null || request.mHost.getSchemeName().equals("https")) ?  request.mHost : proxy;

                mConnection = mConnectionManager.getConnection(mContext, host);
                    // RequestQueue.getConnection()
                    // Firstly, get connection from IdleCache.
                    // If not found, create a new Connection
                        if (host.getSchemeName().equals("http")) 
                            return new HttpConnection(context, host, requestFeeder);
                        else
                            return new HttpsConnection(context, host, proxy, requestFeeder);

                mConnection.processRequests(request);
                if (mConnection.getCanPersist())
                    // cache connection in IdleCache
                    mConnectionManager.recycleConnection(host, mConnection);
                else
                    mConnection.closeConnection();

    //Represents an HTTP request for a given host
    Request
        /** The Apache http request */
        BasicHttpRequest mHttpRequest;

        /** Set if I'm using a proxy server */
        HttpHost mProxyHost;

        private InputStream mBodyProvider;

        // Used to send the request
        private Connection mConnection;

        /** The eventhandler to call as the request progresses */
        EventHandler mEventHandler;

        // constructor
        Request(String method, HttpHost host, HttpHost proxyHost, String path,
                InputStream bodyProvider, int bodyLength,
                EventHandler eventHandler,
                Map<String, String> headers)

            // If GET request
                mHttpRequest = new BasicHttpRequest(method, getUri());
            // If POST request
                mHttpRequest = new BasicHttpEntityEnclosingRequest(
                        method, getUri());
                setBodyProvider(bodyProvider, bodyLength);

            addHeader(HOST_HEADER, getHostPort());
            addHeaders(headers);

        void addHeader(String name, String value)
            mHttpRequest.addHeader(name, value);

        // Send the request line and headers
        void sendRequest(AndroidHttpClientConnection httpClientConnection)
            // For content-length...
            requestContentProcessor.process(mHttpRequest,
                            mConnection.getHttpContext());
            // For GET request
            httpClientConnection.sendRequestHeader(mHttpRequest);

            // For POST request
            if (mHttpRequest instanceof HttpEntityEnclosingRequest)
                httpClientConnection.sendRequestEntity(
                        (HttpEntityEnclosingRequest) mHttpRequest);

        // Receive a single http response.
        void readResponse(AndroidHttpClientConnection httpClientConnection)
            httpClientConnection.flush();
            /*
            Response = Status-Line               ; 
                       *(( general-header        ; 
                        | response-header        ; 
                        | entity-header ) CRLF)  ; 
                        CRLF
                       [ message-body ]          ; 
             */

            Headers header = new Headers();
            do {
                statusLine = httpClientConnection.parseResponseHeader(header);
                statusCode = statusLine.getStatusCode();
            } while (statusCode < HttpStatus.SC_OK);
            // Status-Line = HTTP-Version Status-Code Reason-Phrase CRLF

            ProtocolVersion v = statusLine.getProtocolVersion();
            mEventHandler.status(v.getMajor(), v.getMinor(),
                    statusCode, statusLine.getReasonPhrase());
            mEventHandler.headers(header);
            HttpEntity entity = null;

            // blocking ...
            entity = httpClientConnection.receiveResponseEntity(header);

                InputStream is = entity.getContent();

                // process gzip content encoding
                Header contentEncoding = entity.getContentEncoding();
                InputStream nis = null;
                byte[] buf = null;

                    if (contentEncoding != null &&
                        contentEncoding.getValue().equals("gzip")) {
                        nis = new GZIPInputStream(is);
                    } else {
                        nis = is;
                    }

                    buf = mConnection.getBuf();
                    int len = 0;
                    int lowWater = buf.length / 2;
                    while (len != -1) {
                        len = nis.read(buf, count, buf.length - count);
                        if (len != -1) {
                            count += len;
                        }
                        if (len == -1 || count >= lowWater) {
                            mEventHandler.data(buf, count);
                            count = 0;

            mConnection.setCanPersist(entity, statusLine.getProtocolVersion(),
                header.getConnectionType());
            mEventHandler.endData();
            complete();

        void cancel()
            mCancelled = true;
            mConnection.cancel();

    interface RequestFeeder
        Request getRequest();
        Request getRequest(HttpHost host);
        boolean haveRequest(HttpHost host);
        // Put request back on head of queue
        void requeueRequest(Request request);

    RequestQueue-->RequestFeeder
        //Requests, indexed by HttpHost (scheme, host, port)
        private final LinkedHashMap<HttpHost, LinkedList<Request>> mPending;

        // Manager connection threads
        private final ActivePool mActivePool;

        private HttpHost mProxyHost = null;

        private BroadcastReceiver mProxyChangeReceiver;

        interface ConnectionManager
            HttpHost getProxyHost();
            Connection getConnection(Context context, HttpHost host);

            // cache it in IdleCache
            boolean recycleConnection(HttpHost host, Connection connection);

        // This class maintains active connection threads
        class ActivePool implements ConnectionManager {
            /** Threads used to process requests */
            ConnectionThread[] mThreads;

            // Cache idle connections for a while(6 sec)
            IdleCache mIdleCache;

            ActivePool(int connectionCount) {
                mIdleCache = new IdleCache();
                mConnectionCount = connectionCount;
                mThreads = new ConnectionThread[mConnectionCount];

                for (int i = 0; i < mConnectionCount; i++)
                    mThreads[i] = new ConnectionThread(
                            mContext, i, this, RequestQueue.this);

            // start all threads
            void startup()

            // stop all threads
            void shutdown()

            // Turns off persistence on all live connections
            void disablePersistence() {
                for (int i = 0; i < mConnectionCount; i++)
                    Connection connection = mThreads[i].mConnection;
                    if (connection != null) connection.setCanPersist(false);
                mIdleCache.clear();

        public RequestQueue(Context context, int connectionCount) 
            mPending = new LinkedHashMap<HttpHost, LinkedList<Request>>(32);
            mActivePool = new ActivePool(connectionCount);
            mActivePool.startup();

        // if proxy is changed, just clear the connection pool

        public RequestHandle queueRequest(
                String url, String method,
                Map<String, String> headers, EventHandler eventHandler,
                InputStream bodyProvider, int bodyLength)

            HttpHost httpHost = new HttpHost(uri.mHost, uri.mPort, uri.mScheme);
            req = new Request(method, httpHost, mProxyHost, uri.mPath, bodyProvider,
                              bodyLength, eventHandler, headers);
            queueRequest(req, false);
                // Append the request to list in the linked hashmap 

            mActivePool.startConnectionThread();
                RequestQueue.this.notify();
                    // Wake up ConnectionThread to check request

            return new RequestHandle(
                    this, url, uri, method, headers, bodyProvider, bodyLength,
                    req);

    HttpConnection

    // read requests from request queue and process them
    Connection
        /** The low level connection */
        protected AndroidHttpClientConnection mHttpClientConnection = null;

        /**
         * The host this connection is connected to.  If using proxy,
         * this is set to the proxy address
         */
        HttpHost mHost;

        /** true if the connection can be reused for sending more requests */
        private boolean mCanPersist;

        /** context required by ConnectionReuseStrategy. */
        private HttpContext mHttpContext;

        RequestQueue.ConnectionManager mConnectionManager;
        RequestFeeder mRequestFeeder;

        protected Connection(Context context, HttpHost host,
                RequestQueue.ConnectionManager connectionManager,
                RequestFeeder requestFeeder) 
            mHost = host;
            mConnectionManager = connectionManager;
            mRequestFeeder = requestFeeder;
            mCanPersist = false;
            mHttpContext = new BasicHttpContext(null);

            // connection factory: returns an HTTP or HTTPS connection as necessary
            static Connection getConnection( Context context, HttpHost host,
                    RequestQueue.ConnectionManager connectionManager, RequestFeeder requestFeeder) 
                return new HttpConnection(context, host, connectionManager, requestFeeder);
                return new HttpsConnection(context, host, connectionManager, requestFeeder);

            void cancel()
                mActive = STATE_CANCEL_REQUESTED;
                closeConnection();

            void processRequests(req) 
                // if get a request
                    req.setConnection(this);
                    openHttpConnection(req);
                    req.sendRequest(mHttpClientConnection);
                    pipe.addLast(req);
                // if read response
                    req = (Request)pipe.removeFirst();
                    req.readResponse(mHttpClientConnection);

        private boolean openHttpConnection(Request req) 
            mHttpClientConnection = openConnection(req);
            mHttpClientConnection.setSocketTimeout(SOCKET_TIMEOUT);
            mHttpContext.setAttribute(HTTP_CONNECTION, mHttpClientConnection);
     
    class HttpConnection extends Connection
        AndroidHttpClientConnection openConnection(Request req) throws IOException {

            // Update the certificate info (connection not secure - set to null)
            EventHandler eventHandler = req.getEventHandler();
            mCertificate = null;
            eventHandler.certificate(mCertificate);

            AndroidHttpClientConnection conn = new AndroidHttpClientConnection();
            BasicHttpParams params = new BasicHttpParams();
            Socket sock = new Socket(mHost.getHostName(), mHost.getPort());
            params.setIntParameter(HttpConnectionParams.SOCKET_BUFFER_SIZE, 8192);
            conn.bind(sock, params);
            return conn;
 
        void closeConnection() {
            mHttpClientConnection.close();
        
    ConnectionThread-->Thread 
        RequestQueue.ConnectionManager mConnectionManager;
        RequestFeeder mRequestFeeder;

        Connection mConnection;

        void requestStop()
            mRunning = false;
            mRequestFeeder.notify();
         

        public void run()
            while (mRunning)
                /* Get a request to process */
                request = mRequestFeeder.getRequest();

                if (request == null) // wait
                // else 
                HttpHost proxy = mConnectionManager.getProxyHost();
                host = (proxy == null || request.mHost.getSchemeName().equals("https")) ?  request.mHost : proxy;

                mConnection = mConnectionManager.getConnection(mContext, host);
                mConnection.processRequests(request);
                if (mConnection.getCanPersist()) {
                    mConnectionManager.recycleConnection(host, mConnection);
                } else {
                    mConnection.closeConnection();
                }

    RequestHandle



E/DownloadThread( 1479): download failed
E/DownloadThread( 1479): org.apache.http.conn.ConnectTimeoutException: Connect to /10.0.0.172:80 timed out
E/DownloadThread( 1479):    at org.apache.http.conn.scheme.PlainSocketFactory.connectSocket(PlainSocketFactory.java:121)
E/DownloadThread( 1479):    at org.apache.http.impl.conn.DefaultClientConnectionOperator.openConnection(DefaultClientConnectionOperator.java:143)
E/DownloadThread( 1479):    at org.apache.http.impl.conn.AbstractPoolEntry.open(AbstractPoolEntry.java:164)
E/DownloadThread( 1479):    at org.apache.http.impl.conn.AbstractPooledConnAdapter.open(AbstractPooledConnAdapter.java:119)
E/DownloadThread( 1479):    at org.apache.http.impl.client.DefaultRequestDirector.execute(DefaultRequestDirector.java:359)
E/DownloadThread( 1479):    at org.apache.http.impl.client.AbstractHttpClient.execute(AbstractHttpClient.java:555)
E/DownloadThread( 1479):    at org.apache.http.impl.client.AbstractHttpClient.execute(AbstractHttpClient.java:487)
E/DownloadThread( 1479):    at org.apache.http.impl.client.AbstractHttpClient.execute(AbstractHttpClient.java:465)
E/DownloadThread( 1479):    at android.net.http.AndroidHttpClient.execute(AndroidHttpClient.java:243)
E/DownloadThread( 1479):    at com.android.providers.downloads.DownloadThread.run(DownloadThread.java:278)

