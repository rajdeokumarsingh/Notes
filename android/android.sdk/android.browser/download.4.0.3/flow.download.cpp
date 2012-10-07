////////////////////////////////////////////////////////////////////////////////
// webkit
////////////////////////////////////////////////////////////////////////////////
WebCore/loader/MainResourceLoader.cpp
void MainResourceLoader::didReceiveResponse(const ResourceResponse& r)
    frameLoader()->policyChecker()->checkContentPolicy(
            m_response, callContinueAfterContentPolicy, this);
            |
            V
WebCore/loader/PolicyChecker.cpp
void PolicyChecker::checkContentPolicy(const ResourceResponse& response, ContentPolicyDecisionFunction function, void* argument)
    m_callback.set(function, argument);
    m_frame->loader()->client()->dispatchDecidePolicyForResponse(&PolicyChecker::continueAfterContentPolicy,
            response, m_frame->loader()->activeDocumentLoader()->request());
            |
            V
WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp
void FrameLoaderClientAndroid::dispatchDecidePolicyForResponse(FramePolicyFunction func,
        const ResourceResponse& response, const ResourceRequest& request) {

    // Check if we should Download instead.
    const String& content_disposition = response.httpHeaderField("Content-Disposition");
    if (!content_disposition.isEmpty() && TreatAsAttachment(content_disposition)) {
        // ...
        action = PolicyDownload;
        (policy->*func)(action);
        return;

    // Ask if it can be handled internally.
    if (!canShowMIMEType(response.mimeType())) {
        // ...
        action = PolicyDownload;
        (policy->*func)(action);
        return;
            |
            V
WebCore/loader/MainResourceLoader.cpp
void MainResourceLoader::continueAfterContentPolicy(
        PolicyAction contentPolicy, const ResourceResponse& r)
    case PolicyDownload:
        InspectorInstrumentation::continueWithPolicyDownload(m_frame.get(), documentLoader(), identifier(), r);
        frameLoader()->client()->download(m_handle.get(), request(), m_handle.get()->firstRequest(), r);
            |
            V
WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp
void FrameLoaderClientAndroid::download(ResourceHandle* handle, 
        const ResourceRequest&, const ResourceRequest&, const ResourceResponse&) {
    handle->getInternal()->m_loader->downloadFile();
            |
            V
WebKit/android/WebCoreSupport/WebUrlLoader.cpp
void WebUrlLoader::downloadFile()
    m_loaderClient->downloadFile();
            |
            V
WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp
void WebUrlLoaderClient::downloadFile()
    m_webFrame->downloadStart(m_response->getUrl(), m_request->getUserAgent(), 
            contentDisposition, m_response->getMimeType(), m_response->getExpectedSize());
            |
            V
WebKit/android/jni/WebCoreFrameBridge.cpp
    void WebFrame::downloadStart(const std::string& url, 
        const std::string& userAgent, const std::string& contentDisposition, 
        const std::string& mimetype, long long contentLength)

    env->CallVoidMethod(javaFrame.get(), mJavaFrame->mDownloadStart, jUrl, 
            jUserAgent, jContentDisposition, jMimetype, contentLength);
                |
                | JNI
                V
////////////////////////////////////////////////////////////////////////////////
// framework
////////////////////////////////////////////////////////////////////////////////
base/core/java/android/webkit/BrowserFrame.java
    /**
     * Called by JNI when the native HTTP stack needs to download a file.
     *
     * We delegate the request to CallbackProxy, which owns the current app's
     * DownloadListener.
     */
    private void downloadStart(String url, String userAgent,
            String contentDisposition, String mimeType, long contentLength)
        // ...
        mCallbackProxy.onDownloadStart(url, userAgent, 
                contentDisposition, mimeType, contentLength);
                    |
                    V
base/core/java/android/webkit/CallbackProxy.java
    public boolean onDownloadStart(String url, String userAgent,
            String contentDisposition, String mimetype, long contentLength) {
        Message msg = obtainMessage(DOWNLOAD_FILE);
        Bundle bundle = msg.getData();
        bundle.putString("url", url);
        bundle.putString("userAgent", userAgent);
        bundle.putString("mimetype", mimetype);
        bundle.putLong("contentLength", contentLength);
        bundle.putString("contentDisposition", contentDisposition);
        sendMessage(msg);
            |
            V
    case DOWNLOAD_FILE:            
        if (mDownloadListener != null) {
            String url = msg.getData().getString("url");
            String userAgent = msg.getData().getString("userAgent");
            String contentDisposition =    
                msg.getData().getString("contentDisposition");
            String mimetype = msg.getData().getString("mimetype");
            Long contentLength = msg.getData().getLong("contentLength");

            mDownloadListener.onDownloadStart(url, userAgent,
                    contentDisposition, mimetype, contentLength);
                        |
                        V
////////////////////////////////////////////////////////////////////////////////
// Browser
////////////////////////////////////////////////////////////////////////////////
src/com/android/browser/Tab.java
    mDownloadListener = new DownloadListener() {
        public void onDownloadStart(String url, String userAgent,
                String contentDisposition, String mimetype,
                long contentLength) {
            mWebViewController.onDownloadStart(Tab.this, url, userAgent, contentDisposition,
                    mimetype, contentLength);
    mMainView.setDownloadListener(mDownloadListener);
            |
            V
src/com/android/browser/Controller.java
    onDownloadStart(Tab tab, String url, String userAgent,
        String contentDisposition, String mimetype, long contentLength) 
            |
            V
src/com/android/browser/DownloadHandler.java
public static void onDownloadStart(Activity activity, String url, String userAgent, 
        String contentDisposition, String mimetype, boolean privateBrowsing) 
            |
            V
static void onDownloadStartNoStream(Activity activity, String url, String userAgent, 
        String contentDisposition, String mimetype, boolean privateBrowsing) {

    final DownloadManager.Request request;
    request = new DownloadManager.Request(uri);
    request.setMimeType(mimetype);
    // set downloaded file destination to /sdcard/Download.
    // or, should it be set to one of several Environment.DIRECTORY* dirs depending on mimetype?
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
    request.allowScanningByMediaScanner();
    request.setDescription(webAddress.getHost());

    String cookies = CookieManager.getInstance().getCookie(url, privateBrowsing);
    request.addRequestHeader("cookie", cookies);
    request.setNotificationVisibility(
            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

    final DownloadManager manager = (DownloadManager) 
        activity.getSystemService(Context.DOWNLOAD_SERVICE);
    manager.enqueue(request);

////////////////////////////////////////////////////////////////////////////////
// framework
////////////////////////////////////////////////////////////////////////////////
base/core/java/android/app/DownloadManager.java
// FIXME: DownloadManager is just a simple wrapper of DownloadProvider
// 1. Provider interface to access DownloadProvider
// 2. Provider helper function for DownloadProvider
/**
 * Enqueue a new download.  The download will start automatically once the download manager is
 * ready to execute it and connectivity is available.
 * 
 * @param request the parameters specifying this download
 * @return an ID for the download, unique across the system.  This ID is used to make future
 * calls related to this download.
 */
public long enqueue(Request request) {
    ContentValues values = request.toContentValues(mPackageName);
    Uri downloadUri = mResolver.insert(Downloads.Impl.CONTENT_URI, values);
    long id = Long.parseLong(downloadUri.getLastPathSegment());
    return id;

////////////////////////////////////////////////////////////////////////////////
// DownloadProvider
////////////////////////////////////////////////////////////////////////////////
src/com/android/providers/downloads/DownloadProvider.java
public Uri insert(final Uri uri, final ContentValues values) 
    // check permisson
    long rowID = db.insert(DB_TABLE, null, filteredValues);

    context.startService(new Intent(context, DownloadService.class));
            |
            V
src/com/android/providers/downloads/DownloadService.java
    public int onStartCommand(Intent intent, int flags, int startId) 
        updateFromProvider();
            |
            V
private void updateFromProvider()
    mUpdateThread = new UpdateThread();
    mSystemFacade.startThread(mUpdateThread);
            |
            V
private class UpdateThread extends Thread
    public void run() {
        // ...
        info = insertDownload(reader, now);
            |
            V
private DownloadInfo insertDownload(DownloadInfo.Reader reader, long now) 
    DownloadInfo info = reader.newDownloadInfo(this, mSystemFacade);
    mDownloads.put(info.mId, info);


    info.startIfReady(now, mStorageManager);
        |
        V
src/com/android/providers/downloads/DownloadInfo.java
    if (mStatus != Impl.STATUS_RUNNING) {
        mStatus = Impl.STATUS_RUNNING;
        ContentValues values = new ContentValues();
        values.put(Impl.COLUMN_STATUS, mStatus);
        mContext.getContentResolver().update(getAllDownloadsUri(), values, null, null);
    
    DownloadHandler.getInstance().enqueueDownload(this);
        |
        V
src/com/android/providers/downloads/DownloadHandler.java
    synchronized void enqueueDownload(DownloadInfo info)
        mDownloadsQueue.put(info.mId, info);
        startDownloadThread();
        |
        V
private synchronized void startDownloadThread() 
    info.startDownloadThread();
        |
        V
src/com/android/providers/downloads/DownloadInfo.java
    void startDownloadThread() {
        DownloadThread downloader = new DownloadThread(mContext, mSystemFacade, this,
                StorageManager.getInstance(mContext));
        mSystemFacade.startThread(downloader); // thread.start();


setDestinationInExternalPublicDir

