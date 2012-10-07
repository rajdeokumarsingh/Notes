base/core/java/android/net/Downloads.java

public final class Downloads

    // 定义了Download status codes
    // 保存在DownloadInfo的mStatus中，对应数据库Downloads.Impl.COLUMN_STATUS列
    {
        // This download hasn't started yet
        public static final int STATUS_PENDING = 190;

        // This download has started
        public static final int STATUS_RUNNING = 192;

        // This download has successfully completed.
        public static final int STATUS_SUCCESS = 200; 

        // This download can't be performed because the content type cannot be handled.
        public static final int STATUS_NOT_ACCEPTABLE = 406;

        // This download has completed with an error.
        public static final int STATUS_UNKNOWN_ERROR = 491;

        // This download couldn't be completed because of an HTTP
        //  redirect response that the download manager couldn't handle.
        public static final int STATUS_UNHANDLED_REDIRECT = 493;

        // this is because the SD card is full.
        public static final int STATUS_INSUFFICIENT_SPACE_ERROR = 498;

        // Typically, this is because the SD card is not mounted
        public static final int STATUS_DEVICE_NOT_FOUND_ERROR = 499;
    }

    // 下载位置
    // 保存在DownloadInfo.mDestination, 对应数据库Downloads.Impl.COLUMN_DESTINATION列
    {
        // default in sdcard
        public static final int DOWNLOAD_DESTINATION_EXTERNAL = 1;


        // 用于下载private文件，并且将会很快被删除
        // 只能通过content provider访问文件。
        // 需要android.permission.ACCESS_DOWNLOAD_MANAGER_ADVANCED权限
        public static final int DOWNLOAD_DESTINATION_CACHE = 2;


        // 用于下载private文件
        // 系统需要空间的时候，这些文件可以被删除
        // 系统空间足够时，不会被删除
        public static final int DOWNLOAD_DESTINATION_CACHE_PURGEABLE = 3;

        public static final int DOWNLOAD_DESTINATION_SYS_CACHE = 4;
    }

    // 下载完成后，download manager会发送ACTION_DOWNLOAD_COMPLETED给发起下载的app。
    // app的class或package保存在DownloadInfo的mPackage或mClass中
    // intent的extra为download的id或url
    public static final String ACTION_DOWNLOAD_COMPLETED =
    "android.intent.action.DOWNLOAD_COMPLETED";

    
    // 应用开始下载的时候，可以设置notification extras域.
    // 下载完成后，download manager将这个notification extras域返回给应用
    // 保存在DownloadInfo的mExtras中，对应数据库Downloads.Impl.COLUMN_NOTIFICATION_EXTRAS列
    public static final String COLUMN_NOTIFICATION_EXTRAS = "notificationextras";






