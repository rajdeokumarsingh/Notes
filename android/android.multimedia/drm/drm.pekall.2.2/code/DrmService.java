./src/com/pekall/android/drm/DrmMetaDataRetriever.java
    // query drm file information from DrmProvider by file path

    private String  mID;
    private String  mFilePath;
    private String  mArtist;
    private String  mTitle;
    private ITEMTYPE mType;

    retrieverAudioMetaData();
    retrieverVideoMetaData();

./src/com/pekall/android/drm/DrmService.java

    // maintain drm items
    private ArrayList<String> fileGotRO = new ArrayList<String> ();
    public static ArrayList<DrmListItem> gslistExpiredFiles = new ArrayList<DrmListItem> ();
    public static ArrayList<DrmListItem> gsitemList;

    // intent to display drm UI
    public static final String ACTION_INTERNAL_LISTVIEW_EXIT =
        "com.pekall.android.intent.action.DRM_LISTVIEW_EXIT";
    public static final String ACTION_INTERNAL_LISTVIEW_REMOVE_ITEM =
        "com.pekall.android.intent.action.DRM_LISTVIEW_REMOVE_ITEM";
    public static final String ACTION_INTERNAL_LISTVIEW_REMOVE_ALL_ITEMS =
        "com.pekall.android.intent.action.DRM_LISTVIEW_REMOVE_ALL_ITEMS";

    // intent to acquire license
    // 1. addToItemList 
    // 2. send notification
    public static final String ACTION_ACQUIRE_LICENSE=
        "com.pekall.android.intent.action.DRM_ACQUIRE_LICENSE";
        /*
        addToItemList(gslistExpiredFiles,fileName);
        updateNotification(gslistExpiredFiles.size(), fileName);
        */

    // intent for download complete
    // 1. Notify media scanner to do the scan
    public static final String ACTION_CONTENT_DOWNLOAD_COMPLETE =
        "com.pekall.android.intent.action.DRM_CONTENT_DOWNLOAD_COMPLETE";
        /* file = new File(fileFullPath);
        new MediaScannerNotifier(DrmService.this, file); */

./src/com/pekall/android/drm/DrmReceiver.java
    // receive com.pekall.android.intent.action.NATIVE_DRM_ACQUIRE_LICENSE
    // and start service to handle the event

./src/com/pekall/android/drm/DrmExpireListActivity.java
./src/com/pekall/android/drm/DrmListItemView.java
./src/com/pekall/android/drm/DrmListItemAdapter.java
./src/com/pekall/android/drm/DrmListItem.java
    /* Activity to display :
        drm items
        expired items
     */
