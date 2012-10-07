
class Page
    // Page has a main frame
    RefPtr<Frame> m_mainFrame;

    // travel frame tree
    for (RefPtr<Frame> frame = mainFrame(); frame; frame = frame->tree()->traverseNext()) 
        frame->loader()->tellClientAboutPastMemoryCacheLoads();

class Frame
    // each frame has a frame loader
    FrameLoader* loader() const;

class FrameLoader
    // each frame loader has loader client
    FrameLoaderClient* m_client;

    // each frame loader has three document loaders
    // Document loaders for the three phases of frame loading. Note that while 
    // a new request is being loaded, the old document loader may still be referenced.
    // E.g. while a new request is in the "policy" state, the old document loader may
    // be consulted in particular as it makes sense to imply certain settings on the new loader.
    RefPtr<DocumentLoader> m_provisionalDocumentLoader; // Not get data from network yet
    RefPtr<DocumentLoader> m_documentLoader;
    RefPtr<DocumentLoader> m_policyDocumentLoader;

    /* if (m_frame->loader()->documentLoader())
        currentURL = m_frame->loader()->documentLoader()->url(); */

class DocumentLoader
    // Each document loader has some URLs
    const KURL& url() const;       
    const KURL& unreachableURL() const;
    const KURL& originalURL() const;
    const KURL& requestURL() const;
    const KURL& responseURL() const;

    // KURL currentURL = currentURL = documentLoader()->url();


// where create Frame
// 1. create page and main frame
base/core/java/android/webkit/BrowserFrame.java
    public BrowserFrame(Context context, WebViewCore w, CallbackProxy proxy,
            WebSettings settings, Map<String, Object> javascriptInterfaces) 
        nativeCreateFrame(w, am, proxy.getBackForwardList());
            | JNI to WebKit
            V
WebKit/android/jni/WebCoreFrameBridge.cpp|963| 
    static void CreateFrame(JNIEnv* env, jobject obj, jobject javaview, 
            jobject jAssetManager, jobject historyList)

        // Create a new page
        WebCore::Page* page = new WebCore::Page(chromeC, contextMenuC, 
                editorC, dragC, inspectorC,
                0, // PluginHalterClient
                0); // GeolocationControllerClient

        FrameLoaderClientAndroid* loaderC = new FrameLoaderClientAndroid(webFrame);
        // Create a WebFrame to access the Java BrowserFrame associated with this page
        WebFrame* webFrame = new WebFrame(env, obj, historyList, page);
        WebCore::Frame* frame = WebCore::Frame::create(page, NULL, loaderC).get();  

// 2. create sub-frame?
WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp|1063| 
    WTF::PassRefPtr<WebCore::Frame> FrameLoaderClientAndroid::createFrame(
            const KURL& url, const String& name,
            HTMLFrameOwnerElement* ownerElement, const String& referrer,
            bool allowsScrolling, int marginWidth, int marginHeight)

        FrameLoaderClientAndroid* loaderC = new FrameLoaderClientAndroid(m_webFrame);
        RefPtr<Frame> pFrame = Frame::create(parent->page(), ownerElement, loaderC); 

