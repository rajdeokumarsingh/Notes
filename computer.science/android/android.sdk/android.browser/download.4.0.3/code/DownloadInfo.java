public class DownloadInfo

    // 从数据库中读取下载项的信息
    public static class Reader
    {
        // 1. 创建一个新的下载项
        // 2. 从数据库中读取下载项的信息
        public DownloadInfo newDownloadInfo(Context context, SystemFacade systemFacade) {
            DownloadInfo info = new DownloadInfo(context, systemFacade);
            updateFromDatabase(info);      
            readRequestHeaders(info);      
            return info;

        // 从数据库中读取下载项的信息
        public void updateFromDatabase(DownloadInfo info) 
            info.mId = getLong(Downloads.Impl._ID);
            info.mUri = getString(Downloads.Impl.COLUMN_URI);
            info.mNoIntegrity = getInt(Downloads.Impl.COLUMN_NO_INTEGRITY) == 1;
            // ...

        // 从content provider的request_headers表中查询下载项的header
        private void readRequestHeaders(DownloadInfo info)
            info.mRequestHeaders.clear();
            // 查询数据库并更新下载项

            // 添加header
            if (info.mCookies != null)
                addHeader(info, "Cookie", info.mCookies);

            if (info.mReferer != null)
                addHeader(info, "Referer", info.mReferer);
    }

    // 下载项信息
    {
        public long mId;            // _id
        public String mUri;
        public boolean mNoIntegrity;    // 是否需要对文件的完整性进行检查
        public String mHint;        // 下载UI中的文字
        public String mFileName;
        public String mMimeType;
        public int mDestination;
        public int mVisibility;     // 是否在status bar中可见
        public int mControl;        // 开始/暂停
        public int mStatus;         // 下载状态
        public long mLastMod;
        public String mPackage;     // 下载完成后，需要发intent通知的package, class, extras
        public String mClass;
        public String mExtras;
        public String mCookies;     // cookies, 在browser中添加
        public String mUserAgent;
        public String mReferer;
        public long mTotalBytes;    // content-lenght
        public long mCurrentBytes;  // 当前下载的byte
        public int mUid;
        public int mMediaScanned;
        public boolean mDeleted;
        public String mMediaProviderUri;
        public boolean mIsPublicApi;
        public int mAllowedNetworkTypes;
        public boolean mAllowRoaming;
        public String mTitle;
        public String mDescription;
        public int mBypassRecommendedSizeLimit;
    }

    public String mETag;        // http etag
    public int mNumFailed;      // 下载中出现错误的次数
    public int mRetryAfter;     // 多少时间后重试
    public int mFuzz;   // 随机数，用于产生下载的restartTime

    // 下载重试的时间点
    public long restartTime(long now) {
        if (mNumFailed==0||mNumFailed==1||mNumFailed==2) {
            return now;

        // 如果数据库中已经记录重试的时间, 就直接返回重试时间
        // （一般都是下载中收到了HTTP response code 503, 并且响应中包括了retry-After头）
        if (mRetryAfter > 0)
            return mLastMod + mRetryAfter;

        // 否则根据失败次数和随机数计算一个重试时间
        return mLastMod +
            Constants.RETRY_FIRST_DELAY *
            (1000 + mFuzz) * (1 << (mNumFailed - 1));

    private boolean isReadyToStart(long now)
        // already running
        if (DownloadHandler.getInstance().hasDownloadInQueue(mId))
            return false;

        // 用户将下载暂停了
        if (mControl == Downloads.Impl.CONTROL_PAUSED)
            return false;

        switch (mStatus) {
            case 0: // status hasn't been initialized yet, this is a new download
            case Downloads.Impl.STATUS_PENDING: // download is explicit marked as ready to start
            case Downloads.Impl.STATUS_RUNNING: // download interrupted (process killed etc) while
                // running, without a chance to update the database
            case Downloads.Impl.STATUS_PAUSED_BY_APP:
                return true;

            case Downloads.Impl.STATUS_WAITING_FOR_NETWORK:
            case Downloads.Impl.STATUS_QUEUED_FOR_WIFI:
                return checkCanUseNetwork() == NETWORK_OK;

            case Downloads.Impl.STATUS_WAITING_TO_RETRY:
                // download was waiting for a delayed restart
                return restartTime(now) <= now;
            case Downloads.Impl.STATUS_DEVICE_NOT_FOUND_ERROR:
                // is the media mounted?
                return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);


    // 如果下载完成了，status bar上是否需要提示？
    // 要是下载没有完成，返回false
    public boolean hasCompletionNotification() {
        if (!Downloads.Impl.isStatusCompleted(mStatus)) {
            return false;

        if (mVisibility == Downloads.Impl.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) {
            return true;

        return false;

    // 检查网络是否可用
    public int checkCanUseNetwork() {
        final NetworkInfo info = mSystemFacade.getActiveNetworkInfo(mUid);

        // TODO: NOT a good way, need investigate later
        if(mAllowedNetworkTypes == Request.NETWORK_MOBILE_INTERNET ||
                mAllowedNetworkTypes == Request.NETWORK_MOBILE_WAP) {
            return NETWORK_OK;

        if (info == null) {
            return NETWORK_NO_CONNECTION;

        if (DetailedState.BLOCKED.equals(info.getDetailedState())) {
            return NETWORK_BLOCKED;

        if (!isRoamingAllowed() && mSystemFacade.isNetworkRoaming()) {
            return NETWORK_CANNOT_USE_ROAMING;
        return checkIsNetworkTypeAllowed(info.getType());


    // 1. 判断目前活跃的网络类型是否和下载指定的网络类型匹配
    // 2. 判断下载文件的大小是否在网络类型的最大流量范围内
    private int checkIsNetworkTypeAllowed(int networkType) {
        if (mIsPublicApi)
            int flag = translateNetworkTypeToApiFlag(networkType);
            if ((flag & mAllowedNetworkTypes) == 0) {
                return NETWORK_TYPE_DISALLOWED_BY_REQUESTOR;

        return checkSizeAllowedForNetwork(networkType);


    // 判断下载文件的大小是否在网络类型的最大流量范围内
    private int checkSizeAllowedForNetwork(int networkType) {
        if (mTotalBytes <= 0) {
            return NETWORK_OK; // we don't know the size yet

        if (networkType == ConnectivityManager.TYPE_WIFI) {
            return NETWORK_OK; // anything goes over wifi

        Long maxBytesOverMobile = mSystemFacade.getMaxBytesOverMobile();
        if (maxBytesOverMobile != null && mTotalBytes > maxBytesOverMobile) {
            return NETWORK_UNUSABLE_DUE_TO_SIZE;

        if (mBypassRecommendedSizeLimit == 0) {
            Long recommendedMaxBytesOverMobile = mSystemFacade.getRecommendedMaxBytesOverMobile();
            if (recommendedMaxBytesOverMobile != null
                    && mTotalBytes > recommendedMaxBytesOverMobile) {
                return NETWORK_RECOMMENDED_UNUSABLE_DUE_TO_SIZE;

        return NETWORK_OK;


    // 1. 判断是否应该开始下载
    // 2. 设置下载项的状态为running, 并更新状态到数据库
    // 3. 将下载项添加到下载队列
    void startIfReady(long now, StorageManager storageManager) {
        if (!isReadyToStart(now))
            return;

        if (mStatus != Impl.STATUS_RUNNING) {
            mStatus = Impl.STATUS_RUNNING;
            ContentValues values = new ContentValues();
            values.put(Impl.COLUMN_STATUS, mStatus);
            mContext.getContentResolver().update(getAllDownloadsUri(), values, null, null);

        DownloadHandler.getInstance().enqueueDownload(this);


    /**
     * Returns the amount of time (as measured from the "now" parameter)
     * at which a download will be active.
     * 0 = immediately - service should stick around to handle this download.
     * -1 = never - service can go away without ever waking up.
     * positive value - service must wake up in the future, as specified in ms from "now"
     */
    long nextAction(long now) {
        // 下载已经完成
        if (Downloads.Impl.isStatusCompleted(mStatus)) {
            return -1;

        // 下载正在进行
        if (mStatus != Downloads.Impl.STATUS_WAITING_TO_RETRY) {
            return 0;

        // 等待重试
        long when = restartTime(now);
        if (when <= now) {
            return 0;
        return when - now;


    // 下载完成了 && 没有被scan && 下载路径是外部文件系统...
    boolean shouldScanFile() {
        return (mMediaScanned == 0)
            && (mDestination == Downloads.Impl.DESTINATION_EXTERNAL ||
                    mDestination == Downloads.Impl.DESTINATION_FILE_URI ||
                    mDestination == Downloads.Impl.DESTINATION_NON_DOWNLOADMANAGER_DOWNLOAD)
            && Downloads.Impl.isStatusSuccess(mStatus);

    // 启动一个dialog提示用户下载文件的大小超过了mobile网络的
    // 最大流量限制
    void notifyPauseDueToSize(boolean isWifiRequired)

    // 启动下载线程
    void startDownloadThread()
        DownloadThread downloader = new DownloadThread(mContext, 
                mSystemFacade, this, StorageManager.getInstance(mContext));
        mSystemFacade.startThread(downloader);

