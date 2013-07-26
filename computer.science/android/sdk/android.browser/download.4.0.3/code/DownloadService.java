
public class DownloadService extends Service

    // Download provider更新时, 如有新下载，下载进度变化，...,
    // 会收到通知
    private DownloadManagerContentObserver mObserver;

    // 更新通知栏
    private DownloadNotification mNotifier;

    // 下载项(正在进行，已完成的, 不包括被删除的)在内存中的结构
    // Map的索引是DownloadInfo.mId
    private Map<Long, DownloadInfo> mDownloads = Maps.newHashMap();

    // 将content provider中的更新同步到内部mDownloads list
    UpdateThread mUpdateThread;

    // 内部mDownloads list是否应该被更新
    // 如果需要被更新，则说明有下载正在进行， 如下载进度有更新
    private boolean mPendingUpdate;

    // 通知扫描文件
    private MediaScannerConnection mMediaScannerConnection;

    // 当content provider中数据发生变化时， 更新mDownloads list
    private class DownloadManagerContentObserver extends ContentObserver {
        public void onChange(final boolean selfChange)
            updateFromProvider();


    // 不支持bindService, 仅仅支持 startService
    public IBinder onBind(Intent i)
        throw new UnsupportedOperationException(
                "Cannot bind to Download Manager Service");

    public void onCreate() 
        // 创建content observer
        mObserver = new DownloadManagerContentObserver();
        getContentResolver().registerContentObserver(
                Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI, true, mObserver);

        // 创建状态栏Notifier
        mNotifier = new DownloadNotification(this, mSystemFacade);
        mSystemFacade.cancelAllNotifications();

        mStorageManager = StorageManager.getInstance(getApplicationContext());

        // 更新内部mDownloads list
        updateFromProvider()

    public int onStartCommand(Intent intent, int flags, int startId)
        updateFromProvider();

    public void onDestroy()
        getContentResolver().unregisterContentObserver(mObserver);

    // 1. 设置pending update标志位为true
    // 2. 创建UpdateThread, 开始干活
    private void updateFromProvider()
        mPendingUpdate = true;

        if (mUpdateThread == null)
            mUpdateThread = new UpdateThread();
            mSystemFacade.startThread(mUpdateThread);

    // FIXME: 最核心的函数
    private class UpdateThread extends Thread 
        public void run()
            boolean keepService = false;
            long wakeUp = Long.MAX_VALUE;

            for (;;) {
                // 如果没有更新的请求, 准备停止service
                if (!mPendingUpdate) {
                    mUpdateThread = null;
                    if (!keepService)
                        stopSelf();

                    // 有文件需要过一段时间重试
                    if (wakeUp != Long.MAX_VALUE)
                        scheduleAlarm(wakeUp);
                    return;
                }
                mPendingUpdate = false;

                // 获取content provider中的所有下载项
                Cursor cursor = getContentResolver().query(
                        Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI,
                        null, null, null, null);

                // 遍历获取的所有下载项目
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    long id = cursor.getLong(idColumn);
                    idsNoLongerInDatabase.remove(id);

                    DownloadInfo info = mDownloads.get(id);
                    if (info != null)
                        // 下载项已经在内存mDownloads list中, 更新内存DownloadInfo
                        updateDownload(reader, info, now);
                    else 
                        // 下载项不在内存mDownloads list中, 添加内存DownloadInfo
                        info = insertDownload(reader, now);

                    // 如果需要scan file, 但是scan file失败(如正等待scanner连接)
                    // 则设置标志，暂时不停止service
                    if (info.shouldScanFile() && !scanFile(info, true, false))
                        mustScan = true;
                        keepService = true;

                    // 如果下载项完成后，需要继续显示在状态栏
                    // 则设置标志，暂时不停止service
                    if (info.hasCompletionNotification())
                        keepService = true;                

                    long next = info.nextAction(now);
                    // 该下载项正在下载中
                    // 则设置标志，暂时不停止service
                    if (next == 0) {
                        keepService = true;
                    else if (next > 0 && next < wakeUp)
                        // 需要在一段时间后重启下载项目
                        wakeUp = next;
                } // for(cursor)

                // 删除已经不再数据库中的下载项目
                for (Long id : idsNoLongerInDatabase)
                    deleteDownload(id);

                // 如果下载项被删除, 则必须进行扫描
                if (!mustScan) {
                    for (DownloadInfo info : mDownloads.values()) {
                        if (info.mDeleted && TextUtils.isEmpty(info.mMediaProviderUri)) {
                            mustScan = true;
                            keepService = true;
                            break;

                // 更新statur bar中的进度条
                mNotifier.updateNotification(mDownloads.values());

                // 进行扫描
                if (mustScan)
                    bindMediaScanner();
                else
                    mMediaScannerConnection.disconnectMediaScanner();
               
                // 删除设置了mDeleted位的下载项目:
                // 1. MediaProvider数据库
                // 2. DownloadProvider数据库
                // 3. 文件
                for (DownloadInfo info : mDownloads.values())
                    if (info.mDeleted)
                        // this row is to be deleted from the database. 
                        // but does it have mediaProviderUri?
                        if (TextUtils.isEmpty(info.mMediaProviderUri))
                            if (info.shouldScanFile()) {
                                // initiate rescan of the file to - which will populate
                                // mediaProviderUri column in this row
                                if (!scanFile(info, false, true))
                                    throw new IllegalStateException("scanFile failed!");
                                continue;
                        else
                            // yes it has mediaProviderUri column already filled in.
                            // delete it from MediaProvider database.
                            getContentResolver().delete(Uri.parse(info.mMediaProviderUri), null,
                                    null);
                        // delete the file
                        deleteFileIfExists(info.mFileName);
                        // delete from the downloads db
                        getContentResolver().delete(Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI,
                                Downloads.Impl._ID + " = ? ",
                                new String[]{String.valueOf(info.mId)});

            } // for(;;)


    // 创建一个DownloadInfo, 并将其添加到mDownloads list中
    private DownloadInfo insertDownload(DownloadInfo.Reader reader, long now)
        DownloadInfo info = reader.newDownloadInfo(this, mSystemFacade);

        // mId是数据库中Downloads.Impl._ID字段值
        mDownloads.put(info.mId, info);

        info.startIfReady(now, mStorageManager);
        return info;

    // 根据数据库 更新内存中mDownloads list中的download项目
    private void updateDownload(DownloadInfo.Reader reader, DownloadInfo info, long now) {
        int oldVisibility = info.mVisibility;
        int oldStatus = info.mStatus;

        // 更新DownloadInfo
        reader.updateFromDatabase(info);

        boolean lostVisibility =
            oldVisibility == Downloads.Impl.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            && info.mVisibility != Downloads.Impl.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            && Downloads.Impl.isStatusCompleted(info.mStatus);
        boolean justCompleted =
            !Downloads.Impl.isStatusCompleted(oldStatus)
            && Downloads.Impl.isStatusCompleted(info.mStatus);

        // 取消status bar中的notification
        if (lostVisibility || justCompleted) {
            mSystemFacade.cancelNotification(info.mId);

        info.startIfReady(now, mStorageManager);


    // 1. 删除下载项
    // 2. 扫描文件
    // 3. 设置下载项状态
    // 4. 删除文件
    // 5. 取消status bar中的notification
    private void deleteDownload(long id) {
        DownloadInfo info = mDownloads.get(id);
        if (info.shouldScanFile()) {
            scanFile(info, false, false);

        if (info.mStatus == Downloads.Impl.STATUS_RUNNING) {
            info.mStatus = Downloads.Impl.STATUS_CANCELED;

        if (info.mDestination != Downloads.Impl.DESTINATION_EXTERNAL && info.mFileName != null) {
            new File(info.mFileName).delete();

        mSystemFacade.cancelNotification(info.mId);
        mDownloads.remove(info.mId);

