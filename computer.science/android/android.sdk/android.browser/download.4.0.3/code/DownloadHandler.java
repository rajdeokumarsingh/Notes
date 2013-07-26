
public class DownloadHandler
    
    // 等待中的下载项, 下载线程没有被创建
    private final LinkedHashMap<Long, DownloadInfo> mDownloadsQueue;

    // 正在下载的下载项，下载线程已经创建了
    // 下载完成后，下载线程会调用dequeueDownload()，将下载项从本队列中删除
    private final HashMap<Long, DownloadInfo> mDownloadsInProgress;

    // 并行的最多下载线程数
    private final int mMaxConcurrentDownloadsAllowed =
        <!-- Max number of downloads allowed to proceed concurrently -->
        <integer name="config_MaxConcurrentDownloadsAllowed">5</integer>


    // 1. 将下载项添加到等待队列
    // 2. 启动下载线程
    synchronized void enqueueDownload(DownloadInfo info) {
        if (!mDownloadsQueue.containsKey(info.mId))
            mDownloadsQueue.put(info.mId, info);
            startDownloadThread();


    // 1. 遍历等待队列
    // 2. 如果下载队列的没有超出系统限制
    //  2.1 将等待队列中的下载项添加到下载队列
    //  2.2 启动下载线程
    //  2.3 移除等待队列中的下载项
    private synchronized void startDownloadThread() {
        Iterator<Long> keys = mDownloadsQueue.keySet().iterator();

        ArrayList<Long> ids = new ArrayList<Long>();
        while (mDownloadsInProgress.size() < mMaxConcurrentDownloadsAllowed && keys.hasNext()) {
            Long id = keys.next();
            DownloadInfo info = mDownloadsQueue.get(id);

            //  2.2 启动下载线程
            info.startDownloadThread();
            ids.add(id);

            //  2.1 将等待队列中的下载项添加到下载队列
            mDownloadsInProgress.put(id, mDownloadsQueue.get(id));

        //  2.3 移除等待队列中的下载项
        for (Long id : ids) {
            mDownloadsQueue.remove(id);


    // 下载线程退出前，会调用本函数
    // 1. 将下载项移除下载队列
    // 2. 调用startDownloadThread()查看是否还有等待中的下载
    synchronized void dequeueDownload(long mId) {
        mDownloadsInProgress.remove(mId);
        startDownloadThread();
        if (mDownloadsInProgress.size() == 0 && mDownloadsQueue.size() == 0) {
            notifyAll(); 

