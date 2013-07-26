public class DownloadThread extends Thread

    // 一个下载线程和一个下载项关联
    private final DownloadInfo mInfo;

    private DrmConvertSession mDrmConvertSession;

    
    // 下载线程的内部状态, 和mInfo对应??
    // State for the entire run() method.
    static class State {
        public String mFilename;
        public FileOutputStream mStream;
        public String mMimeType;
        public boolean mCountRetry = false;
        public int mRetryAfter = 0;
        public int mRedirectCount = 0;
        public String mNewUri;
        public boolean mGotData = false;
        public String mRequestUri;
        public long mTotalBytes = -1;
        public long mCurrentBytes = 0;
        public String mHeaderETag;
        public boolean mContinuingDownload = false;
        public long mBytesNotified = 0;
        public long mTimeLastNotification = 0;
    }

    // State within executeDownload()
    private static class InnerState {
        public String mHeaderContentLength;
        public String mHeaderContentDisposition;
        public String mHeaderContentLocation;
    }

    // Raised from methods called by executeDownload() to indicate 
    // that the download should be retried immediately.
    private class RetryDownload extends Throwable {}

    public void run()
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        // TODO: ??
        final NetworkPolicyManager netPolicy = NetworkPolicyManager.getSystemService(mContext);

        State state = new State(mInfo);
        try {
            // 获取wake lock
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, Constants.TAG);
            wakeLock.acquire();

            // while performing download, register for rules updates
            netPolicy.registerListener(mPolicyListener);

            client = AndroidHttpClient.newInstance(userAgent(), mContext);

            // 开始流量统计
            // network traffic on this thread should be counted against the
            // requesting uid, and is tagged with well-known value.
            TrafficStats.setThreadStatsTag(TrafficStats.TAG_SYSTEM_DOWNLOAD);
            TrafficStats.setThreadStatsUid(mInfo.mUid);

            while(!finished) {
                // 设置代理
                // Set or unset proxy, which may have changed since last GET request.
                // setDefaultProxy() supports null as proxy parameter.
                ConnRouteParams.setDefaultProxy(client.getParams(),
                        Proxy.getPreferredHttpHost(mContext, state.mRequestUri));

                //  发送http get请求
                HttpGet request = new HttpGet(state.mRequestUri);
                try {
                    executeDownload(state, client, request);
                    // 完成则退出while(!finished)循环
                    finished = true;
                } catch (RetryDownload exc) {
                    // fall through
                    // 立即重试
                } finally {
                    request.abort();
                    request = null;

            } // while(!finished)

            finalizeDestinationFile(state);
            finalStatus = Downloads.Impl.STATUS_SUCCESS;

        } // end try
        catch (StopRequestException error) {
            // 记录错误和最后状态
            errorMsg = error.getMessage();
            finalStatus = error.mFinalStatus;

        } catch (Throwable ex) { //sometimes the socket code throws unchecked exceptions
            // 记录错误和最后状态
            errorMsg = ex.getMessage();
            finalStatus = Downloads.Impl.STATUS_UNKNOWN_ERROR;

        } finally {
            // 结束流量统计
            TrafficStats.clearThreadStatsTag();
            TrafficStats.clearThreadStatsUid();

            // 关闭http client
            client.close();

            // 更新下载状态
            cleanupDestination(state, finalStatus);

            // 更新数据库状态
            notifyDownloadCompleted(finalStatus, state.mCountRetry, state.mRetryAfter,
                    state.mGotData, state.mFilename,
                    state.mNewUri, state.mMimeType, errorMsg);

            // 将下载项移除下载队列
            DownloadHandler.getInstance().dequeueDownload(mInfo.mId);

            netPolicy.unregisterListener(mPolicyListener);
            wakeLock.release();
        }

    // Fully execute a single download request:
        // 1. setup and send the request
        // 2. handle the response
        // 3. transfer the data to the destination file
    private void executeDownload(State state, AndroidHttpClient client, HttpGet request)
        throws StopRequestException, RetryDownload

        InnerState innerState = new InnerState();
        // 下载数据的缓存
        byte data[] = new byte[Constants.BUFFER_SIZE];

        // 准备下载的文件, 创建新文件或seek文件
        setupDestinationFile(state, innerState);
        // 将mInfo中的header加入到http request中
        addRequestHeaders(state, request);

        // 检查网络可用性
        checkConnectivity();

        // 发送http get request, 并获取到response
        HttpResponse response = sendRequest(state, client, request);

        // 处理异常状态
        handleExceptionalStatus(state, innerState, response);

        // 处理响应头
        processResponseHeaders(state, innerState, response);

        // 从响应中读取下载内容(http entity)， 并将其保存到本地文件中
        InputStream entityStream = openResponseEntity(state, response);
        transferData(state, innerState, data, entityStream);


    private void checkConnectivity() throws StopRequestException
        int networkUsable = mInfo.checkCanUseNetwork();

        if (networkUsable == DownloadInfo.NETWORK_UNUSABLE_DUE_TO_SIZE) {
            // mobile流量超过了限制，准备使用wifi下载
            status = Downloads.Impl.STATUS_QUEUED_FOR_WIFI;
            mInfo.notifyPauseDueToSize(true);
        } else if (networkUsable == DownloadInfo.NETWORK_RECOMMENDED_UNUSABLE_DUE_TO_SIZE) {
            // mobile流量超过了限制，准备使用wifi下载
            status = Downloads.Impl.STATUS_QUEUED_FOR_WIFI;
            mInfo.notifyPauseDueToSize(false);
        } else if (networkUsable == DownloadInfo.NETWORK_BLOCKED) {
            // 网络阻塞了
            status = Downloads.Impl.STATUS_BLOCKED;
        }
        // 在下载线程的run函数的try中会catch这个异常
            // 1. 保存下载项信息到数据库
            // 2. 退出下载线程
        throw new StopRequestException(status,
                mInfo.getLogMessageForNetworkError(networkUsable));





