public class DownloadManager

    // 数据库COLUMN_STATUS的归纳，用于显示错误字符串
    // 见ui/src/com/android/providers/downloads/ui/DownloadAdapter.java
    // private int getStatusStringId()
    {
        // 通过DownloadManager返回的cursor中查到的status
        {
            public final static int STATUS_PENDING = 1 << 0;
            public final static int STATUS_RUNNING = 1 << 1;
            public final static int STATUS_PAUSED = 1 << 2;
            public final static int STATUS_SUCCESSFUL = 1 << 3;
            public final static int STATUS_FAILED = 1 << 4;
        }

        // 和COLUMN_STATUS的对应如下:
        private int translateStatus(int status) 
        {
            switch (status) {
                case Downloads.Impl.STATUS_PENDING:
                    return STATUS_PENDING;
                    // 显示R.string.download_running

                case Downloads.Impl.STATUS_RUNNING:
                    return STATUS_RUNNING;
                    // 显示R.string.download_running

                case Downloads.Impl.STATUS_PAUSED_BY_APP:
                case Downloads.Impl.STATUS_WAITING_TO_RETRY:
                case Downloads.Impl.STATUS_WAITING_FOR_NETWORK:
                    // 显示R.string.download_running
                case Downloads.Impl.STATUS_QUEUED_FOR_WIFI:
                    // 显示R.string.download_queued
                    return STATUS_PAUSED;

                case Downloads.Impl.STATUS_SUCCESS:
                    return STATUS_SUCCESSFUL;
                    // 显示R.string.download_success

                default:
                    assert Downloads.Impl.isStatusError(status);
                    return STATUS_FAILED;
                    // 显示R.string.download_error
            }
        }

    }

    // 错误id, 状态(COLUMN_STATUS)可转化为错误id
    // 见private long getErrorCode(int status)
    {
        public final static int ERROR_UNKNOWN = 1000;
        public final static int ERROR_FILE_ERROR = 1001;
        public final static int ERROR_UNHANDLED_HTTP_CODE = 1002;
        public final static int ERROR_HTTP_DATA_ERROR = 1004;
        public final static int ERROR_TOO_MANY_REDIRECTS = 1005;
        public final static int ERROR_INSUFFICIENT_SPACE = 1006;
        public final static int ERROR_DEVICE_NOT_FOUND = 1007;
        public final static int ERROR_CANNOT_RESUME = 1008;
        public final static int ERROR_FILE_ALREADY_EXISTS = 1009;
        public final static int ERROR_BLOCKED = 1010;
    }

    // pause的原因，通过状态(COLUMN_STATUS)转换过来
    // FIXME: 没有paused by app项
    {
        public final static int PAUSED_WAITING_TO_RETRY = 1;
        public final static int PAUSED_WAITING_FOR_NETWORK = 2;
        public final static int PAUSED_QUEUED_FOR_WIFI = 3;
        public final static int PAUSED_UNKNOWN = 4;
    }

    // Intent action
    {
        // Broadcast intent action sent by the download manager when a download completes.
        public final static String ACTION_DOWNLOAD_COMPLETE = 
            "android.intent.action.DOWNLOAD_COMPLETE";                                                         

         // Broadcast intent action sent by the download manager when the user clicks on a running
         // download, either from a system notification or from the downloads UI.
        public final static String ACTION_NOTIFICATION_CLICKED =
            "android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED";

        // Intent action to launch an activity to display all downloads.
        public final static String ACTION_VIEW_DOWNLOADS = 
            "android.intent.action.VIEW_DOWNLOADS"; 
    }

    //  This class contains all the information necessary to request a new download. 
    // The URI is the only required parameter.
    public static class Request {
        // 允许的网络类型, 对应COLUMN_ALLOWED_NETWORK_TYPES
        {
            public static final int NETWORK_MOBILE = 1 << 0;
            public static final int NETWORK_WIFI = 1 << 1;
            public static final int NETWORK_MOBILE_WAP = 1 << 2;
            public static final int NETWORK_MOBILE_INTERNET = 1 << 3;
        }

        // 文件是否能被scan, 对应COLUMN_MEDIA_SCANNED
        {
            // 可被scan
            private static final int SCANNABLE_VALUE_YES = 0;
            // 不可被scan
            private static final int SCANNABLE_VALUE_NO = 2;
            
            // scan后，column对应的值为1
            // value of 1 is stored in the above column by 
            // DownloadProvider after it is scanned by MediaScanner
        }

        // 在notification和download ui中的可见性
        {
            // 仅仅在下载过程中可见
            // This download is visible but only shows in the notifications 
            // while it's in progress.
            public static final int VISIBILITY_VISIBLE = 0;

            // 在下载过程和下载结束后都可见
            // This download is visible and shows in the notifications while
            // in progress and after completion.
            public static final int VISIBILITY_VISIBLE_NOTIFY_COMPLETED = 1;

            // 不可见
            // This download doesn't show in the UI or in the notifications.
            public static final int VISIBILITY_HIDDEN = 2;

            // 仅仅在下载结束后可见
            /**
             * This download shows in the notifications after completion ONLY.
             * It is usuable only with
             * {@link DownloadManager#addCompletedDownload(String, String,
             * boolean, String, String, long, boolean)}.
             */
            public static final int VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION = 3;

        }

        private Uri mUri;
        private Uri mDestinationUri; // 下载路径uri
        private List<Pair<String, String>> mRequestHeaders = 
            new ArrayList<Pair<String, String>>(); // 下载对应的http头
        private CharSequence mTitle;
        private CharSequence mDescription;
        private String mMimeType;
        private boolean mRoamingAllowed = true;
        private int mAllowedNetworkTypes = ~0;      // default to all network types allowed
        private boolean mIsVisibleInDownloadsUi = true;
        private boolean mScannable = false;
        private boolean mUseSystemCache = false;

        // 使用DownloadManager.Request发起的下载默认都是public api
    }

    // This class may be used to filter download manager queries.
    public static class Query {
        // 通过id来过滤
        private long[] mIds = null;

        // 通过状态来过滤
        private Integer mStatusFlags = null;

        // 通过是否可见来过滤
        private boolean mOnlyIncludeVisibleInDownloadsUi = false;

        // 排序方式
        private String mOrderByColumn = 
            Downloads.Impl.COLUMN_LAST_MODIFICATION;
            // Downloads.Impl.COLUMN_TOTAL_BYTES;
    }

    private ContentResolver mResolver;
    private String mPackageName;
    private Uri mBaseUri = Downloads.Impl.CONTENT_URI;

    // 设置访问的范围, my_downloads or all_downloads
    public void setAccessAllDownloads(boolean accessAllDownloads) {
        if (accessAllDownloads) {
            mBaseUri = Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI;
        } else {
            mBaseUri = Downloads.Impl.CONTENT_URI;
        }
    }

    // 添加download项, 将download项插入到download provider
    public long enqueue(Request request) 

    // 设置行的COLUMN_DELETED位
    // 在Download provider/service中会删除下载相关的所有信息
    public int markRowDeleted(long... ids) 
        ContentValues values = new ContentValues();
        values.put(Downloads.Impl.COLUMN_DELETED, 1);
        return mResolver.update(mBaseUri, values, getWhereClauseForIds(ids),
                getWhereArgsForIds(ids)); 

    // same as markRowDeleted
    public int remove(long... ids)

    // 查询download provider
    public Cursor query(Query query)

    public ParcelFileDescriptor openDownloadedFile(long id)

    public Uri getUriForDownloadedFile(long id)

    public String getMimeTypeForDownloadedFile(long id) 

    public void restartDownload(long... ids) 
    {
        ContentValues values = new ContentValues();                           
        values.put(Downloads.Impl.COLUMN_CURRENT_BYTES, 0);                   
        values.put(Downloads.Impl.COLUMN_TOTAL_BYTES, -1);                    
        values.putNull(Downloads.Impl._DATA);                                 
        values.put(Downloads.Impl.COLUMN_STATUS, Downloads.Impl.STATUS_PENDING);       
        mResolver.update(mBaseUri, values, getWhereClauseForIds(ids), getWhereArgsForIds(ids));
    }

    // 增加一个本地文件到下载的数据库中
    // Adds a file to the downloads database system, so it could appear in Downloads App
    // (and thus become eligible for management by the Downloads App).
    public long addCompletedDownload(String title, String description,
        boolean isMediaScannerScannable, String mimeType, String path, 
        long length, boolean showNotification) 

