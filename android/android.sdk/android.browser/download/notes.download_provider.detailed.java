DownloadInfo
    public int mId;             // prime key in DB
    public String mUri;         // url
    public boolean mNoIntegrity;
    public String mHint;        // hint name, set in BrowserActivity.onDownloadStartNoStream()
                                    // URLUtil.guessFileName(url, contentDisposition, mimetype); 
    public String mFileName;    // full path
    public String mMimeType;
    public int mDestination;    // storage place: DESTINATION_EXTERNAL,DESTINATION_CACHE_PARTITION,...
    public int mVisibility;     // This download is visible/not , shows in the notifications/not
    public int mControl;        // pause manually, CONTROL_RUN/CONTROL_PAUSE (not used yet)
    public int mStatus;         // download status.
                                    // Downloads.Impl.STATUS_PENDING: ready to start, initial status
                                    // Downloads.Impl.STATUS_RUNNING: running, set before download thread start
                                    // Downloads.Impl.STATUS_RUNNING_PAUSED: download is waiting for network connectivity 
                                        // to return before it can resume
                                    // Downloads.Impl.STATUS_SUCCESS:
                                    // Downloads.Impl.STATUS_BAD_REQUEST: This request couldn't be parsed. 
                                        // This is also used when processing requests 
                                        // with unknown/unsupported URI schemes.
                                    // Downloads.Impl.STATUS_NOT_ACCEPTABLE: This download can't be performed because 
                                        // the content type cannot be handled.(not used)
                                    // Downloads.Impl.STATUS_LENGTH_REQUIRED
                                        // content lenght of http hearder error
                                    // Downloads.Impl.STATUS_PRECONDITION_FAILED
                                        // the HTTP error "Precondition Failed"
                                    // Downloads.Impl.STATUS_CANCELED: user cancel the download in UI
                                    // Downloads.Impl.STATUS_FILE_ERROR:  storage issue. Typically, that's because 
                                        // the filesystem is missing or full
                                    // Downloads.Impl.STATUS_UNHANDLED_REDIRECT
                                        // This download couldn't be completed because of an HTTP
                                        // redirect response that the download manager couldn't handle.
                                    // Downloads.Impl.STATUS_UNHANDLED_HTTP_CODE
                                        // This download couldn't be completed because of an unspecified unhandled HTTP code.
                                    // Downloads.Impl.STATUS_HTTP_DATA_ERROR
                                        // This download couldn't be completed because of an
                                        // error receiving or processing data at the HTTP level.
    public int mNumFailed;      // failed number
    public int mRetryAfter;     // retry timeout
    public int mRedirectCount;
    public long mLastMod;
    public String mPackage;     // package who initiate the download
    public String mClass;       // values.put(Downloads.Impl.COLUMN_NOTIFICATION_CLASS, OpenDownloadReceiver.class.getCanonicalName());
                                    // the class in the package will be notified by an intent after the download is done
    public String mExtras;      // the extra in notify intent
    public String mCookies;     // cookie in http header
    public String mUserAgent;
    public String mReferer;     // referer in http header
    public int mTotalBytes;     // Content length of the download file
    public int mCurrentBytes;
    public String mETag;
    public int mNetworkType;    // any, wap, net, ...
    public boolean mMediaScanned;

    public int mFuzz;           // for retry

    public volatile boolean mHasActiveThread;

    // notify the browser that download is done
    public void sendIntentIfRequested(Uri contentUri, Context context)

    // when to restart the download after it was paused
    public long restartTime() 

    // the download is ready to start
    public boolean isReadyToStart(long now) 

    // the download is ready to start
    public boolean isReadyToRestart(long now)

    // whether this download has a visible notification after completion.
    public boolean hasCompletionNotification()

    // whether this download is allowed to use the network.
    public boolean canUseNetwork(boolean available, boolean roaming) {
        if (mDestination == Downloads.Impl.DESTINATION_CACHE_PARTITION_NOROAMING)  return !roaming;


Constants

    /** The intent that gets sent when the service must wake up for a retry */
    public static final String ACTION_RETRY = "android.intent.action.DOWNLOAD_WAKEUP";
        // DownloadReceiver got it: 
        // context.startService(new Intent(context, DownloadService.class));

    /** the intent that gets sent when clicking a successful download */
    public static final String ACTION_OPEN = "android.intent.action.DOWNLOAD_OPEN";
        // DownloadReceiver got it, and start an activity to open the file


    /** the intent that gets sent when clicking an incomplete/failed download  */
    public static final String ACTION_LIST = "android.intent.action.DOWNLOAD_LIST";
        // DownloadReceiver got it, and send to browser to list the files

    /** the intent that gets sent when deleting the notification of a completed download */
    public static final String ACTION_HIDE = "android.intent.action.DOWNLOAD_HIDE";
        // update COLUMN_VISIBILITY from Downloads.Impl.VISIBILITY_VISIBLE_NOTIFY_COMPLETED to 
            // Downloads.Impl.VISIBILITY_VISIBLE

// create Helpers.generateSaveFile(), just used by DownloadThread
DownloadFileInfo
    String mFileName;
    FileOutputStream mStream;
    int mStatus;

|-- Helpers.java
    //  Parse the Content-Disposition HTTP Header.
    private static String parseContentDisposition(String contentDisposition) 

    // get download path from browser settings
    private static String getDownloadPath() 

    // Creates a filename (where the file should be saved) from a uri.
    public static DownloadFileInfo generateSaveFile(
            Context context,
            String url,
            String hint,
            String contentDisposition,
            String contentLocation,
            String mimeType,
            int destination,
            int contentLength) throws FileNotFoundException {

        // check the file can be handled 
        // choose a file name 
        String filename = chooseFilename(
            url, hint, contentDisposition, contentLocation, destination);

        // choose extension name
        chooseExtensionFromMimeType(mimeType, true);
        chooseExtensionFromFilename(mimeType, destination, filename, dotIndex);

        // choose saved path
        // drm files should be saved in internal storage
        // others will be saved in SD card

        // check file system, path, storage size

        // generate full file name
        String fullFilename = chooseUniqueFilename(
                destination, filename, extension, recoveryDir);

    private static String chooseFilename(String url, String hint, 
        String contentDisposition, String contentLocation, int destination) 
        // 1. get filename from hint
        // 2. get filename from contentDisposition(from http header)
        filename = parseContentDisposition(contentDisposition);
        // 3. get filename from contentLocation(from http header)
        // 4. get filename from url
        // 5. use a default filename

    private static String chooseExtensionFromMimeType(String mimeType, boolean useDefaults)
        // choose extension from mimetype
        MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);

    private static String chooseExtensionFromFilename(String mimeType, 
        int destination, String filename, int dotIndex) 
        // get extension from the filename

    private static String chooseUniqueFilename(int destination, String filename,
            String extension, boolean recoveryDir) {
        // choose unique name for event same file

    public static final boolean discardPurgeableFiles(Context context, long targetBytes) 
        // Deletes purgeable files from the cache partition.
        // query from db and delete the matched files
        // do this only when storage is not enough


    public static boolean isNetworkAvailable(Context context)
       // true if only one network is connected 


    public static boolean isNetworkRoaming(Context context) 
        // check active network is roaming 

    public static boolean isMobileNetworkActive(Context context) 

    // Checks whether this looks like a legitimate selection parameter
    public static void validateSelection(String selection, Set<String> allowedColumns) {
    private static void parseExpression(Lexer lexer)
    private static void parseStatement(Lexer lexer) 

    public final static int beginConnectivity(Context context,
            String feature) throws IOException
    public final static void endConnectivity(Context context,
            String feature)

    // A simple lexer that recognizes the words of our restricted subset of SQL where clauses
    private static class Lexer

    // Get system preferred apn type
    public static String getPreferredApn(Context context) 

DownloadReceiver.java
    extends BroadcastReceiver 

    public void onReceive(Context context, Intent intent) 
        // intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)
        context.startService(new Intent(context, DownloadService.class));

        // intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)
        // FIXME: Jiang Rui, move the logic to download page
        // context.startService(new Intent(context, DownloadService.class));

        // intent.getAction().equals(Constants.ACTION_RETRY)
        context.startService(new Intent(context, DownloadService.class));

        // if (intent.getAction().equals(Constants.ACTION_OPEN) || 
        // intent.getAction().equals(Constants.ACTION_LIST))
            // open download file or list download file

    // use to query DRM file type
    private String getMediaMimeTypeByDataBase(String path, Context context) 
    
DownloadService.java
    /**
     * The Service's view of the list of downloads. This is kept independently
     * from the content provider, and the Service only initiates downloads
     * based on this data, so that it can deal with situation where the data
     * in the content provider changes or disappears.
     */
    private ArrayList<DownloadInfo> mDownloads;

    // The thread that updates the internal download list from the content provider.              
    private UpdateThread mUpdateThread;

    // Whether the internal download list should be updated from the content provider.
    private boolean mPendingUpdate;

    // Receives notifications when the data in the content provider changes
    private class DownloadManagerContentObserver extends ContentObserver 

        public void onChange(final boolean selfChange) 
            updateFromProvider(); // start UpdateThread

    public void onCreate()
        mObserver = new DownloadManagerContentObserver();
        getContentResolver().registerContentObserver(
            Downloads.Impl.CONTENT_URI, true, mObserver);

        mNotifier = new DownloadNotification(this);
        mNotifier.mNotificationMgr.cancelAll();
        mNotifier.updateNotification();

        // listen to network event to start pending download
        mNetworkStateChangedFilter = new IntentFilter();
        mNetworkStateChangedFilter.addAction(
                ConnectivityManager.CONNECTIVITY_ACTION);

        trimDatabase();
        removeSpuriousFiles();
        updateFromProvider();



    // build/update the DownloadInfo list from DB
    private class UpdateThread extends Thread 
        run()
            for(;;)
                Cursor cursor = getContentResolver().query(Downloads.Impl.CONTENT_URI,
                        null, null, null, Downloads.Impl._ID);
                /*
                 * Walk the cursor and the local array to keep them in sync. The key
                 *     to the algorithm is that the ids are unique and sorted both in
                 *     the cursor and in the array, so that they can be processed in
                 *     order in both sources at the same time: at each step, both
                 *     sources point to the lowest id that hasn't been processed from
                 *     that source, and the algorithm processes the lowest id from
                 *     those two possibilities.
                 * At each step:
                 * -If the array contains an entry that's not in the cursor, remove the
                 *     entry, move to next entry in the array.
                 * -If the array contains an entry that's in the cursor, nothing to do,
                 *     move to next cursor row and next array entry.
                 * -If the cursor contains an entry that's not in the array, insert
                 *     a new entry in the array, move to next cursor row and next
                 *     array entry.
                 */

                mNotifier.updateNotification();
                // notify media scanner to do scan


    // Removes files that may have been left behind in the cache directory
    private void removeSpuriousFiles()

    // Drops old rows from the database to prevent it from growing too large
    // Do not delete files
    private void trimDatabase() 

    // Keeps a local copy of the info about a download, and initiates the download if appropriate.
    // Add a DownloadInfo item in arrayPos
    // beginDownload(info);
    private void insertDownload(
            Cursor cursor, int arrayPos,
            boolean networkAvailable, boolean networkRoaming, long now) 


    // Updates the local copy of the info about a download.
    // if the item.isReadyToRestart(now), update the db and beginDownload(info);
    private void updateDownload(
            Cursor cursor, int arrayPos,
            boolean networkAvailable, boolean networkRoaming, long now) 

    // Removes the local copy of the info about a download.
    // mNotifier.mNotificationMgr.cancel(info.mId);
    private void deleteDownload(int arrayPos) {

    /**
     * Returns the amount of time (as measured from the "now" parameter)
     * at which a download will be active.
     * use to trigger server restart
     * 0 = immediately - service should stick around to handle this download.
     * -1 = never - service can go away without ever waking up.
     * positive value - service must wake up in the future, as specified in ms from "now"
     */
    private long nextAction(int arrayPos, long now)

    // Returns whether there's a visible notification for this download
    private boolean visibleNotification(int arrayPos) {

    // Returns whether a file should be scanned
    private boolean shouldScanFile(int arrayPos) 

    // Attempts to scan the file if necessary.
    private boolean scanFile(Cursor cursor, int arrayPos) 

    // used for omadownload
    private boolean dispatchDownload(DownloadInfo info)

DownloadThread.java
    extends Thread
    try
        // check whether is is new download or resume download
            // resume download should add etag and bytesSoFar to http header
            bytesSoFar = (int) fileLength;
            headerContentLength = Integer.toString(mInfo.mTotalBytes);
            headerETag = mInfo.mETag;
            continuingDownload = true;

            // add http header later
            request.addHeader("If-Match", headerETag);
            request.addHeader("Range", "bytes=" + bytesSoFar + "-");

        client = AndroidHttpClient.newInstance(userAgent(), mContext);

        // close last file stream

        /*
         * This loop is run once for every individual HTTP request that gets sent.
         * The very first HTTP request is a "virgin" request, while every subsequent
         * request is done with the original ETag and a byte-range.
         */
http_request_loop:
        while (true)
            // Prepares the request and fires it.
            HttpGet request = new HttpGet(mInfo.mUri);
            // add http headers, cookie, referer, if-match, range

            HttpResponse response;
            try
                // set http proxy
                response = client.execute(request);

            catch (IllegalArgumentException ex)
                finalStatus = Downloads.Impl.STATUS_BAD_REQUEST;
                // request.abort(); break http_request_loop;

            catch (IOException ex)
                if (mInfo.mNumFailed < Constants.MAX_RETRIES) 
                    finalStatus = 
                    countRetry = true;
                // Downloads.Impl.STATUS_RUNNING_PAUSED ;request.abort(); break http_request_loop;

            // check http status code
            int statusCode = response.getStatusLine().getStatusCode();
            // 503, retry after Retry-After in header
            // Downloads.Impl.STATUS_RUNNING_PAUSED ;request.abort(); break http_request_loop;

            // 301, 302, 303, 307 redirect
            Header header = response.getFirstHeader("Location");
            newUri = new URI(mInfo.mUri).resolve(
                new URI(header.getValue())).toString();
                // the newUri will be updated to DB by notifyDownloadCompleted()->notifyThroughDatabase()
            // Downloads.Impl.STATUS_RUNNING_PAUSED ;request.abort(); break http_request_loop;

            // other http error
            if ((!continuingDownload && statusCode != Downloads.Impl.STATUS_SUCCESS)
                    || (continuingDownload && statusCode != 206)) {
                // set error status
                // request.abort(); break http_request_loop;

            // Handles the response, saves the file
            // set http headers, Accept-Ranges, Content-Disposition, Content-Location, ETag, Transfer-Encoding, Content-Length

            // check content-lenght
            
            // DownloadFileInfo include filename, output stream
            DownloadFileInfo fileInfo = Helpers.generateSaveFile(
                mContext, mInfo.mUri, mInfo.mHint, headerContentDisposition, headerContentLocation, mimeType, 
                mInfo.mDestination, (headerContentLength != null) ? Integer.parseInt(headerContentLength) : 0);
            // update DB with filename, etag, mimetype, content-length

            // read http entity from response
            InputStream entityStream;
            try 
                entityStream = response.getEntity().getContent();
            catch (IOException ex)
                // Downloads.Impl.STATUS_RUNNING_PAUSED ;request.abort(); break http_request_loop;

            for (;;)
                int bytesRead;
                try
                    bytesRead = entityStream.read(data);
                catch (IOException ex)
                    // update DB with bytesSoFar
                    // Downloads.Impl.STATUS_RUNNING_PAUSED ;request.abort(); break http_request_loop;

                // success
                if (bytesRead == -1) 
                    // update DB with bytesSoFar

                // write data from network to file
                for (;;)
                    try 
                        stream = new FileOutputStream(filename, true);
                        stream.write(data, 0, bytesRead);
                        stream.close();
                        stream = null;
                        break
                    catch (IOException ex) {
                        if (!Helpers.discardPurgeableFiles(
                                    mContext, Constants.BUFFER_SIZE)) {
                            finalStatus = Downloads.Impl.STATUS_FILE_ERROR;
                            break http_request_loop;

                bytesSoFar += bytesRead;

                // check whether user select pause/cancel
                    // set download status and break http_request_loop;

    catch (FileNotFoundException ex) // first try
        finalStatus = Downloads.Impl.STATUS_FILE_ERROR;
    catch (RuntimeException ex) //sometimes the socket code throws unchecked exceptions
        finalStatus = Downloads.Impl.STATUS_UNKNOWN_ERROR;
    finally
        // release wake lock
        // close http client
        // close file stream
        // if the download wasn't successful, delete the file

        // make sure the file is readable
        FileUtils.setPermissions(filename, 0644, -1, -1);

        // Sync to storage after completion
        new FileOutputStream(filename, true).getFD().sync();

        notifyDownloadCompleted(finalStatus, countRetry, retryAfter, 
            redirectCount, gotData, filename, newUri, mimeType);

    // Stores information about the completed download, and notifies the initiating application.
    private void notifyDownloadCompleted(int status, boolean countRetry, int retryAfter, 
        int redirectCount, boolean gotData, String filename, String uri, String mimeType) {


DownloadProvider.java
    extends ContentProvider

    /** URI matcher used to recognize URIs sent by applications */
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    /** URI matcher constant for the URI of the entire download list */
    private static final int DOWNLOADS = 1;
    /** URI matcher constant for the URI of an individual download */
    private static final int DOWNLOADS_ID = 2;
    static {
        sURIMatcher.addURI("downloads", "download", DOWNLOADS);
        sURIMatcher.addURI("downloads", "download/#", DOWNLOADS_ID);
    }

    private final class DatabaseHelper extends SQLiteOpenHelper
        public void onCreate(final SQLiteDatabase db)
            createTable(db);

        public void onUpgrade(final SQLiteDatabase db, int oldV, final int newV) 
            dropTable(db);
            createTable(db);

    public boolean onCreate()
        mOpenHelper = new DatabaseHelper(getContext());

    // FIXME: Seem no one use it
    public String getType(final Uri uri) 

    private void createTable(SQLiteDatabase db)
        db.execSQL("CREATE TABLE " + DB_TABLE + "(" +...

    private void dropTable(SQLiteDatabase db)
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

    // Inserts a row in the database
    public Uri insert(final Uri uri, final ContentValues values) 
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if (sURIMatcher.match(uri) != DOWNLOADS) 
            // throw a exception

        // copy values to a new ContentValues
        long rowID = db.insert(DB_TABLE, null, filteredValues);

        // start download 
        context.startService(new Intent(context, DownloadService.class));
        ret = Uri.parse(Downloads.Impl.CONTENT_URI + "/" + rowID);
        context.getContentResolver().notifyChange(uri, null);

    // Starts a database query
    public Cursor query(final Uri uri, String[] projection,
            final String selection, final String[] selectionArgs,
            final String sort) 

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        int match = sURIMatcher.match(uri);
        case DOWNLOADS: 
            qb.setTables(DB_TABLE);        
        case DOWNLOADS_ID:
            // content://xxxx.xxx.xxx/xxx#1
           qb.setTables(DB_TABLE);
           qb.appendWhere(Downloads.Impl._ID + "=");
           qb.appendWhere(uri.getPathSegments().get(1));
           emptyWhere = false;
           break;

        // check whether calling process has permission
        // append where clause
        Cursor ret = qb.query(db, projection, selection, 
                selectionArgs, null, null, sort);

        ret = new ReadOnlyCursorWrapper(ret);
        return ret;

    public int update(final Uri uri, final ContentValues values,
            final String where, final String[] whereArgs)

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        // copy values to a new ContentValues
        // check permission of calling process

        sURIMatcher.match(uri)
        // append where clause

        count = db.update(DB_TABLE, filteredValues, myWhere, whereArgs);

        getContext().getContentResolver().notifyChange(uri, null);
        // start download service

    public int delete(final Uri uri, final String where,
            final String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        // sURIMatcher.match
        // append where clause
        count = db.delete(DB_TABLE, myWhere, whereArgs);

        // notify download service to update its internal data
        getContext().getContentResolver().notifyChange(uri, null);

    // Remotely opens a file
    public ParcelFileDescriptor openFile(Uri uri, String mode)
                throws FileNotFoundException 

        Cursor c = query(uri, new String[]{"_data"}, null, null, null);
        ParcelFileDescriptor ret = ParcelFileDescriptor.open(new File(path),
            ParcelFileDescriptor.MODE_READ_ONLY);

        // update modify time
        ContentValues values = new ContentValues();
        values.put(Downloads.Impl.COLUMN_LAST_MODIFICATION, System.currentTimeMillis());
        update(uri, values, null, null);

DownloadNotification.java

