
// 1. 封装了DRM数据库

// 2. 接受wap push消息, 安装DRM权限

./src/com/android/providers/drm/DrmProvider.java
// DB wrapper
// two table:
    // 1. audio
    db.execSQL("CREATE TABLE audio (" +
            "_id INTEGER PRIMARY KEY," +
            "_data TEXT," +
            "_size INTEGER," +
            "title TEXT," +
            "mime_type TEXT" +
            ");");

    db.execSQL("CREATE TABLE images (" +
            "_id INTEGER PRIMARY KEY," +
            "_data TEXT," +
            "_size INTEGER," +
            "title TEXT," +
            "mime_type TEXT" +
            ");");


./src/com/android/providers/drm/DrmPushReceiver.java
    public void onReceive(Context context, Intent intent) 
        /* receive WAP_PUSH_RECEIVED_ACTION message
           the message contains:
            1. right type
                DrmRightsManager.DRM_MIMETYPE_RIGHTS_XML_STRING
                DrmRightsManager.DRM_MIMETYPE_RIGHTS_WBXML_STRING
            2. right data
                byte[]
         */
        // install the right data
        DrmRightsManager.getInstance().installRights(
            rightDataStream, rightData.length, rightMimeType);


    // drm right install dir
    /data/data/com.android.providers.drm/rights


