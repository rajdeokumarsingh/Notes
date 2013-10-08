//================================================================================
// Browser
//================================================================================
Controller.java
protected void loadUrl(Tab tab, String url, Map<String, String> headers) {
    tab.loadUrl(url, headers);
        |
        V
    mMainView.loadUrl(url, headers);
            |
            V
//================================================================================
// Framework
//================================================================================

frameworks/base/core/java/android/webkit/WebView.java
frameworks/base/core/java/android/webkit/WebViewProvider.java
frameworks/base/core/java/android/webkit/WebViewClassic.java
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders)
        |
        V
        private void loadUrlImpl(String url, Map<String, String> extraHeaders) {
            WebViewCore.GetUrlData arg = new WebViewCore.GetUrlData();
            arg.mUrl = url;
            arg.mExtraHeaders = extraHeaders;
            mWebViewCore.sendMessage(EventHub.LOAD_URL, arg);
        }

frameworks/base/core/java/android/webkit/WebViewCore.java
    private void transferMessages() {
        case LOAD_URL: {
           CookieManagerClassic.getInstance().waitForCookieOperationsToComplete();
           GetUrlData param = (GetUrlData) msg.obj;
           loadUrl(param.mUrl, param.mExtraHeaders);
        }
    }
                |
                V
    private void loadUrl(String url, Map<String, String> extraHeaders) {
        if (DebugFlags.WEB_VIEW_CORE) Log.v(LOGTAG, " CORE loadUrl " + url);
        mBrowserFrame.loadUrl(url, extraHeaders);
    }
                |
                V
frameworks/base/core/java/android/webkit/WebViewCore.java
    public void loadUrl(String url, Map<String, String> extraHeaders) {
        nativeLoadUrl(url, extraHeaders);
    }
                |
                |  JNI
                V
//================================================================================
// libwebcore
//================================================================================

external/webkit/Source/WebKit/android/jni/WebCoreFrameBridge.cpp
static void LoadUrl(JNIEnv *env, jobject obj, jstring url, jobject headers)
{

    WTF::String webcoreUrl = jstringToWtfString(env, url);
    WebCore::KURL kurl(WebCore::KURL(), webcoreUrl);
    WebCore::ResourceRequest request(kurl);
    if (headers) {
        // build headers
    }

    WebCore::Frame* pFrame = GET_NATIVE_FRAME(env, obj);
    pFrame->loader()->load(request, false);

    // PS:
    {
        FrameLoaderClientAndroid* loaderC = new FrameLoaderClientAndroid(webFrame);
        // Create a Frame and the page holds its reference
        WebCore::Frame* frame = WebCore::Frame::create(page, NULL, loaderC).get();
        loaderC->setFrame(frame);
    }
}
        |
        V
external/webkit/Source/WebCore/page/Frame.cpp
    loader()
        |
        V
external/webkit/Source/WebCore/loader/FrameLoader.cpp
    void FrameLoader::load(const ResourceRequest& request, bool lockHistory) {
        load(request, SubstituteData(), lockHistory);
    }
            |
            V
    void FrameLoader::load(const ResourceRequest& request, const SubstituteData& substituteData, bool lockHistory)
    {
        if (m_inStopAllLoaders)
            return;

        // FIXME: is this the right place to reset loadType? Perhaps this should be done after loading is finished or aborted.
        m_loadType = FrameLoadTypeStandard;
        RefPtr<DocumentLoader> loader = m_client->createDocumentLoader(request, substituteData);
        if (lockHistory && m_documentLoader)
            loader->setClientRedirectSourceForHistory(m_documentLoader->didCreateGlobalHistoryEntry() ? m_documentLoader->urlForHistory().string() : m_documentLoader->clientRedirectSourceForHistory());
        load(loader.get());
    }

