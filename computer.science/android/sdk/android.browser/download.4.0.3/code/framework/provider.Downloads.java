base/core/java/android/provider/Downloads.java

public final class Downloads
    public static final class Impl implements BaseColumns
        // content uri
        {
            // The content:// URI to access downloads owned by the caller's UID.
            // 只能查询本UID相关的下载
            public static final Uri CONTENT_URI =
                Uri.parse("content://downloads/my_downloads");

            // The content URI for accessing all downloads across all UIDs (requires the
            // ACCESS_ALL_DOWNLOADS permission).
            // 可查询所有下载项
            public static final Uri ALL_DOWNLOADS_CONTENT_URI =
                Uri.parse("content://downloads/all_downloads");

            /** URI segment to access a publicly accessible downloaded file */
            public static final String PUBLICLY_ACCESSIBLE_DOWNLOADS_URI_SEGMENT = "public_downloads";

            /**
             * The content URI for accessing publicly accessible downloads (i.e., it requires no
             * permissions to access this downloaded file)
             */
            public static final Uri PUBLICLY_ACCESSIBLE_DOWNLOADS_URI =
                Uri.parse("content://downloads/" + PUBLICLY_ACCESSIBLE_DOWNLOADS_URI_SEGMENT);
        }

        // 数据库column
        {
            // 下载uri; TEXT
            public static final String COLUMN_URI = "uri";

            // The name of the column containing application-specific data.
            // <P>Type: TEXT</P>
            public static final String COLUMN_APP_DATA = "entity";

            // 是否需要验证下载的完成性: 断点续传etag是否一致, content-length
            // <P>Type: BOOLEAN</P>
            public static final String COLUMN_NO_INTEGRITY = "no_integrity";

            // 提示文件名
            // 一般为文件的绝对路径地址url
            // file:///mnt/sdcard/Download/music/testfile.mp3
            // <P>Type: TEXT</P>
            public static final String COLUMN_FILE_NAME_HINT = "hint";

            // 文件的绝对路径
            // /mnt/sdcard/Download/music/testfile.mp3
            // <P>Type: TEXT</P>
            public static final String _DATA = "_data";


            // The name of the column containing the MIME type of the downloaded data.
            // <P>Type: TEXT</P>
            public static final String COLUMN_MIME_TYPE = "mimetype";

            // The name of the column containing the flag that controls the destination
            // of the download. See the DESTINATION_* constants for a list of legal values.
            // <P>Type: INTEGER</P>
            // DESTINATION_EXTERNAL = 0;
            // DESTINATION_CACHE_PARTITION = 1;
            // DESTINATION_FILE_URI = 4;
            public static final String COLUMN_DESTINATION = "destination";

            // 下载在状态条上是否可见
            // <P>Type: INTEGER</P>
            // VISIBILITY_VISIBLE, VISIBILITY_VISIBLE_NOTIFY_COMPLETED, VISIBILITY_HIDDEN
            public static final String COLUMN_VISIBILITY = "visibility";

            // 应用程序可以控制下载的暂停/恢复
            // Applications can write to this to control (pause/resume) the download.
            // the CONTROL_* constants for a list of legal values.
            public static final String COLUMN_CONTROL = "control";

            // 下载的状态
            // 比base/core/java/android/net/Downloads.java中的状态要完备
            public static final String COLUMN_STATUS = "status";


            // 文件最后的修改时间。Stored as a System.currentTimeMillis()
            // <P>Type: BIGINT</P>
            public static final String COLUMN_LAST_MODIFICATION = "lastmod";      

            // 发起下载的应用的package名字
            // 和class名字, extra一起使用，用于下载完成后通知发起下载的app
            // <P>Type: TEXT</P>
            public static final String COLUMN_NOTIFICATION_PACKAGE = "notificationpackage";

            // 发起下载的应用的class名字
            // 和package, extra一起使用，用于下载完成后通知发起下载的app
            // * <P>Type: TEXT</P>
            public static final String COLUMN_NOTIFICATION_CLASS = "notificationclass";

            // If extras are specified when requesting a download they will be provided in the intent that
            // is sent to the specified class and package when a download has finished.
            // 和package, class一起使用，用于下载完成后通知发起下载的app
            // <P>Type: TEXT</P>
            public static final String COLUMN_NOTIFICATION_EXTRAS = "notificationextras";

            // HTTP header
            {
                // The name of the column contain the values of the cookie to be used for
                // the download. This is used directly as the value for the Cookie: HTTP
                // header that gets sent with the request.
                // <P>Type: TEXT</P>
                public static final String COLUMN_COOKIE_DATA = "cookiedata";

                // <P>Type: TEXT</P>
                public static final String COLUMN_USER_AGENT = "useragent";

                // The name of the column containing the referer (sic) that the initiating
                // application wants the download manager to use for this download.
                // <P>Type: TEXT</P>
                public static final String COLUMN_REFERER = "referer";
            }

            // The name of the column containing the total size of the file being
            // downloaded.
            // <P>Type: INTEGER</P>
            public static final String COLUMN_TOTAL_BYTES = "total_bytes";


            // The name of the column containing the size of the part of the file that
            // has been downloaded so far.
            // <P>Type: INTEGER</P>
            public static final String COLUMN_CURRENT_BYTES = "current_bytes";

            /** 你发起了一个下载， 你可以提供一个uid，拥有这个uid的应用可以访问你的下载
             * The name of the column where the initiating application can provide the
             * UID of another application that is allowed to access this download. If
             * multiple applications share the same UID, all those applications will be
             * allowed to access this download. This column can be updated after the
             * download is initiated. This requires the permission
             * android.permission.ACCESS_DOWNLOAD_MANAGER_ADVANCED.
             * <P>Type: INTEGER</P>
             * <P>Owner can Init</P>
             */
            public static final String COLUMN_OTHER_UID = "otheruid";

            // 下载项的标题
            // <P>Type: TEXT</P>
            public static final String COLUMN_TITLE = "title";

            // 下载项的描述
            // <P>Type: TEXT</P>
            public static final String COLUMN_DESCRIPTION = "description";

            /** FIXME: 什么是public API
             * The name of the column indicating whether the download was requesting through the public
             * API.  This controls some differences in behavior.
             * <P>Type: BOOLEAN</P> */
            public static final String COLUMN_IS_PUBLIC_API = "is_public_api";

            /** 是否允许使用漫游连接下载
             * The name of the column indicating whether roaming connections can be used.  This is only
             * used for public API downloads.
             * <P>Type: BOOLEAN</P> */
            public static final String COLUMN_ALLOW_ROAMING = "allow_roaming";

            /** 允许的网络连接类型 
             *  对应DownloadManager.java中Request{ NETWORK_MOBILE, NETWORK_WIFI, ...}
             * The name of the column holding a bitmask of allowed network types.  This is only used for
             * public API downloads.
             * <P>Type: INTEGER</P> */
            public static final String COLUMN_ALLOWED_NETWORK_TYPES = "allowed_network_types";

            /**
             * Whether or not this download should be displayed in the system's Downloads UI.  Defaults
             * to true.
             * <P>Type: INTEGER</P> */
            public static final String COLUMN_IS_VISIBLE_IN_DOWNLOADS_UI = "is_visible_in_downloads_ui";

            /**
             * If true, the user has confirmed that this download can proceed over the mobile network
             * even though it exceeds the recommended maximum size.
             * <P>Type: BOOLEAN</P> */
            public static final String COLUMN_BYPASS_RECOMMENDED_SIZE_LIMIT =
                 "bypass_recommended_size_limit";

            /**
             * Set to true if this download is deleted. It is completely removed from the database
             * when MediaProvider database also deletes the metadata asociated with this downloaded file.
             * <P>Type: BOOLEAN</P> */
            public static final String COLUMN_DELETED = "deleted";

            /** 下载项在Meida provider中对应的uri
             * The URI to the corresponding entry in MediaProvider for this downloaded entry. It is
             * used to delete the entries from MediaProvider database when it is deleted from the
             * downloaded list.
             * <P>Type: TEXT</P> */
            public static final String COLUMN_MEDIAPROVIDER_URI = "mediaprovider_uri";

            /** 是否被媒体播放器扫描过
             * The column that is used to remember whether the media scanner was invoked.
             * It can take the values: null or 0(not scanned), 1(scanned), 2 (not scannable).
             * <P>Type: TEXT</P> */
            public static final String COLUMN_MEDIA_SCANNED = "scanned";

            /** 调试错误信息?
             * The column with errorMsg for a failed downloaded.
             * Used only for debugging purposes.
             * <P>Type: TEXT</P> */
            public static final String COLUMN_ERROR_MSG = "errorMsg";

            /** 没有找到使用者 */
            public static final String COLUMN_LAST_UPDATESRC = "lastUpdateSrc";
            public static final int LAST_UPDATESRC_NOT_RELEVANT = 0;
            public static final int LAST_UPDATESRC_DONT_NOTIFY_DOWNLOADSVC = 1;
        }


        // 定义了权限相关的字符串
        {
            // The permission to access the download manager
            public static final String PERMISSION_ACCESS = 
                "android.permission.ACCESS_DOWNLOAD_MANAGER";

            // The permission to access the download manager's advanced functions
            public static final String PERMISSION_ACCESS_ADVANCED =
                "android.permission.ACCESS_DOWNLOAD_MANAGER_ADVANCED";

            // The permission to access the all the downloads in the manager.
            public static final String PERMISSION_ACCESS_ALL =
                "android.permission.ACCESS_ALL_DOWNLOADS";

            // The permission to directly access the download manager's cache directory
            public static final String PERMISSION_CACHE = 
                "android.permission.ACCESS_CACHE_FILESYSTEM";

            // The permission to send broadcasts on download completion
            public static final String PERMISSION_SEND_INTENTS =
                "android.permission.SEND_DOWNLOAD_COMPLETED_INTENTS";

            // The permission to download files to the cache partition that won't be automatically
            // purged when space is needed.
            public static final String PERMISSION_CACHE_NON_PURGEABLE =
                "android.permission.DOWNLOAD_CACHE_NON_PURGEABLE";

            // The permission to download files without any system notification being shown.
            public static final String PERMISSION_NO_NOTIFICATION =
                "android.permission.DOWNLOAD_WITHOUT_NOTIFICATION";
        }


        // Intent Action
        {
            // Download Manger通知发起下载的应用程序, 下载完成
            // 下载项目的url包含在intent的数据中
            public static final String ACTION_DOWNLOAD_COMPLETED =
                "android.intent.action.DOWNLOAD_COMPLETED";

            // Download Manger通知发起下载的应用程序, 下载项被点击
            // 下载项目的url包含在intent的数据中
            public static final String ACTION_NOTIFICATION_CLICKED =
                "android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED";
        }


        // 存储位置
        // 对于数据库项COLUMN_DESTINATION , 和net中DOWNLOAD_DESTINATION_EXTERNAL
        {
            // 对应sd卡
            public static final int DESTINATION_EXTERNAL = 0;

            // 用于下载private文件，并且将会很快被删除
            // 只能通过content provider访问文件。
            // 需要android.permission.ACCESS_DOWNLOAD_MANAGER_ADVANCED权限
            public static final int DESTINATION_CACHE_PARTITION = 1;

            // 用于下载private文件
            // 系统需要空间的时候，这些文件可以被删除
            // 系统空间足够时，不会被删除
            public static final int DESTINATION_CACHE_PARTITION_PURGEABLE = 2;


            // 将会被保存在COLUMN_FILE_NAME_HINT指定的位置
            public static final int DESTINATION_FILE_URI = 4;

            /**
             * This download will be saved to the system cache ("/cache")
             * partition. This option is only used by system apps and so it requires
             * android.permission.ACCESS_CACHE_FILESYSTEM permission.
             */
            public static final int DESTINATION_SYSTEMCACHE_PARTITION = 5;

            /**
             * This download was completed by the caller (i.e., NOT downloadmanager)
             * and caller wants to have this download displayed in Downloads App.
             */
            public static final int DESTINATION_NON_DOWNLOADMANAGER_DOWNLOAD = 6;
        }

        // 下载的暂停/继续控制
        {
            // This download is allowed to run.
            public static final int CONTROL_RUN = 0;

            // This download must pause at the first opportunity.
            public static final int CONTROL_PAUSED = 1;
        }

        /* 下载的状态编号
         * 和数据库COLUMN_STATUS的对应
         * Lists the states that the download manager can set on a download
         * to notify applications of the download progress.
         * The codes follow the HTTP families:<br>
         * 1xx: informational<br>
         * 2xx: success<br>
         * 3xx: redirects (not used by the download manager)<br>
         * 4xx: client errors<br>
         * 5xx: server errors
         */
        {

            // This download hasn't stated yet
            public static final int STATUS_PENDING = 190;

            // This download has started
            public static final int STATUS_RUNNING = 192;

            // This download has been paused by the owning app.
            public static final int STATUS_PAUSED_BY_APP = 193;

            // This download encountered some network error and is waiting before retrying the request.
            public static final int STATUS_WAITING_TO_RETRY = 194;

            // This download is waiting for network connectivity to proceed.
            public static final int STATUS_WAITING_FOR_NETWORK = 195;

            // This download exceeded a size limit for mobile networks and 
            // is waiting for a Wi-Fi connection to proceed.
            public static final int STATUS_QUEUED_FOR_WIFI = 196;

            // sdcard空间不足
            public static final int STATUS_INSUFFICIENT_SPACE_ERROR = 198;

            // sdcard没有安装上
            public static final int STATUS_DEVICE_NOT_FOUND_ERROR = 199;

            // This download has successfully completed.
            public static final int STATUS_SUCCESS = 200;

            /** URL格式有问题
             * This request couldn't be parsed. This is also used when processing
             * requests with unknown/unsupported URI schemes.  */
            public static final int STATUS_BAD_REQUEST = 400;

            /** 手机无法处理/播放content
             * This download can't be performed because the content type cannot be
             * handled.  */
            public static final int STATUS_NOT_ACCEPTABLE = 406;

            /** 没有content length
             * This download cannot be performed because the length cannot be
             * determined accurately. This is the code for the HTTP error "Length
             * Required", which is typically used when making requests that require
             * a content length but don't have one, and it is also used in the
             * client when a response is received whose length cannot be determined
             * accurately (therefore making it impossible to know when a download
             * completes).
             */
            public static final int STATUS_LENGTH_REQUIRED = 411;


            /** 无法进行断点续传
             * This download was interrupted and cannot be resumed.
             * This is the code for the HTTP error "Precondition Failed", and it is
             * also used in situations where the client doesn't have an ETag at all.
             */
            public static final int STATUS_PRECONDITION_FAILED = 412;

            /**
             * The lowest-valued error status that is not an actual HTTP status code.
             */
            public static final int MIN_ARTIFICIAL_ERROR_STATUS = 488;

            /** 好像没有用
             * The requested destination file already exists.
             */
            public static final int STATUS_FILE_ALREADY_EXISTS_ERROR = 488;

            /** 无法进行断点续传
             * Some possibly transient error occurred, but we can't resume the download.
             */
            public static final int STATUS_CANNOT_RESUME = 489;

            // This download was canceled
            public static final int STATUS_CANCELED = 490;

            // This download has completed with an error.
            public static final int STATUS_UNKNOWN_ERROR = 491;

            /**
             * This download couldn't be completed because of a storage issue.
             * Typically, that's because the filesystem is missing or full.
             * Use the more specific {@link #STATUS_INSUFFICIENT_SPACE_ERROR}
             * and {@link #STATUS_DEVICE_NOT_FOUND_ERROR} when appropriate.
             */
            public static final int STATUS_FILE_ERROR = 492;

            /**
             * This download couldn't be completed because of an HTTP
             * redirect response that the download manager couldn't
             * handle.
             */
            public static final int STATUS_UNHANDLED_REDIRECT = 493;

            /**
             * This download couldn't be completed because of an
             * unspecified unhandled HTTP code.
             */
            public static final int STATUS_UNHANDLED_HTTP_CODE = 494;

            /**
             * This download couldn't be completed because of an
             * error receiving or processing data at the HTTP level.
             */
            public static final int STATUS_HTTP_DATA_ERROR = 495;

            /**
             * This download couldn't be completed because of an
             * HttpException while setting up the request.
             */
            public static final int STATUS_HTTP_EXCEPTION = 496;

            /**
             * This download couldn't be completed because there were
             * too many redirects.
             */
            public static final int STATUS_TOO_MANY_REDIRECTS = 497;

            /**
             * This download has failed because requesting application has been
             * blocked by {@link NetworkPolicyManager}.
             *
             * @hide
             */
            public static final int STATUS_BLOCKED = 498;
        }




