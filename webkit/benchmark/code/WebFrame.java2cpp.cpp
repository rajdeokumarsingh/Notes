函数归类
{
    {
        nativeCreateFrame
        nativeDestroyFrame
    }

    {
        nativeLoadUrl
        nativePostUrl
        nativeLoadData

        nativeGoBackOrForward
        nativeCallPolicyFunction

        nativeAddJavascriptInterface
    }


    {
        nativeSslClientCert
        nativeSslCertErrorCancel
        nativeSslCertErrorProceed
        nativeAuthenticationProceed
        nativeAuthenticationCancel
    }

    nativeSaveWebArchive
    nativeOrientationChanged
}

// Java层返回form resubmission结果
static void CallPolicyFunction(JNIEnv* env, jobject obj, jint func, jint decision) 
    (pFrame->loader()->policyChecker()->*(pFunc->func))((WebCore::PolicyAction)decision);

    Browser中会提示用户，是否重新提交form

// java层 BrowserFrame构造函数中会调用CreateFrame
static void CreateFrame(JNIEnv* env, jobject obj, jobject javaview, jobject jAssetManager, jobject historyList)

    ScriptController::initializeThreading();

#if USE(CHROME_NETWORK_STACK)
    initChromium();
#endif 

#ifdef ANDROID_INSTRUMENT
#if USE(V8)
    V8Counters::initCounters();
#endif 
    TimeCounterAuto counter(TimeCounter::NativeCallbackTimeCounter);
#endif 

    // Create a new page
    ChromeClientAndroid* chromeC = new ChromeClientAndroid;
    EditorClientAndroid* editorC = new EditorClientAndroid;
    DeviceMotionClientAndroid* deviceMotionC = new DeviceMotionClientAndroid;
    DeviceOrientationClientAndroid* deviceOrientationC = new DeviceOrientationClientAndroid;

    WebCore::Page::PageClients pageClients;
    pageClients.chromeClient = chromeC;
    pageClients.contextMenuClient = new ContextMenuClientAndroid;
    pageClients.editorClient = editorC;
    pageClients.dragClient = new DragClientAndroid;
    pageClients.inspectorClient = new InspectorClientAndroid;
    pageClients.deviceMotionClient = deviceMotionC;
    pageClients.deviceOrientationClient = deviceOrientationC;
    WebCore::Page* page = new WebCore::Page(pageClients);

    editorC->setPage(page);
    page->setGroupName("android.webkit");

    // Create a WebFrame to access the Java BrowserFrame associated with this page
    WebFrame* webFrame = new WebFrame(env, obj, historyList, page);
    // Attach webFrame to pageClients.chromeClient and release our ownership
    chromeC->setWebFrame(webFrame);

    FrameLoaderClientAndroid* loaderC = new FrameLoaderClientAndroid(webFrame);
    // Create a Frame and the page holds its reference
    WebCore::Frame* frame = WebCore::Frame::create(page, NULL, loaderC).get();
    loaderC->setFrame(frame);
#if ENABLE(WDS)
    WDS::server()->addFrame(frame);
#endif

    // Create a WebViewCore to access the Java WebViewCore associated with this page
    WebViewCore* webViewCore = new WebViewCore(env, javaview, frame);

#if ENABLE(WEB_AUTOFILL)
    editorC->getAutofill()->setWebViewCore(webViewCore);
#endif

    // Create a FrameView
    RefPtr<WebCore::FrameView> frameView = WebCore::FrameView::create(frame);
    // Create a WebFrameView
    WebFrameView* webFrameView = new WebFrameView(frameView.get(), webViewCore);
    // As webFrameView Retains webViewCore, release our ownership
    Release(webViewCore);
    // As frameView Retains webFrameView, release our ownership
    Release(webFrameView);
    // Attach the frameView to the frame and release our ownership
    frame->setView(frameView);
    // Set the frame to active to turn on keyboard focus.
    frame->init();
    frame->selection()->setFocused(true);
    frame->page()->focusController()->setFocused(true);
    deviceMotionC->setWebViewCore(webViewCore);
    deviceOrientationC->setWebViewCore(webViewCore);

    // Allow local access to file:/// and substitute data
    WebCore::SecurityOrigin::setLocalLoadPolicy(
            WebCore::SecurityOrigin::AllowLocalLoadsForLocalAndSubstituteData);

    // Set the mNativeFrame field in Frame
    SET_NATIVE_FRAME(env, obj, (int)frame);

    // 设置render skin的图片资源的目录
    String directory = webFrame->getRawResourceFilename(
            WebCore::PlatformBridge::DrawableDir);
    if (directory.isEmpty())
    else {
        // Initialize our skinning classes
        webFrame->setRenderSkins(new WebCore::RenderSkinAndroid(directory));
    }

    // 设置字符串资源的目录
    for (int i = WebCore::PlatformBridge::FileUploadLabel;
            i <= WebCore::PlatformBridge::FileUploadNoFileChosenLabel; i++)
        initGlobalLocalizedName(
            static_cast<WebCore::PlatformBridge::rawResId>(i), webFrame);

// invoked by java BrowserFrame::destroy()
// 关键函数 FrameLoader::detachFromParent
static void DestroyFrame(JNIEnv* env, jobject obj)
{
    WebCore::Frame* pFrame = GET_NATIVE_FRAME(env, obj);

    WebCore::FrameView* view = pFrame->view();
    view->ref();
    // detachFromParent will cause the page to be closed.
    WebCore::FrameLoader* fl = pFrame->loader();
    // retain a pointer because detachFromParent will set the page to null.
    WebCore::Page* page = pFrame->page();
    if (fl)
        fl->detachFromParent();
    delete page;

    // Force remove all deleted pages in the page cache
    WebCore::pageCache()->releaseAutoreleasedPagesNow();

    view->deref();

    SET_NATIVE_FRAME(env, obj, 0);
}


// 将url和headers封装到WebCore::ResourceRequest
// 然后调用FrameLoader接口发送该request
static void LoadUrl(JNIEnv *env, jobject obj, jstring url, jobject headers)  
    pFrame->loader()->load(request, false);

// 将url和post data封装到WebCore::ResourceRequest
// 然后调用FrameLoader接口发送该request
static void PostUrl(JNIEnv *env, jobject obj, jstring url, jbyteArray postData)
    WebCore::FrameLoadRequest frameRequest(pFrame->document()->securityOrigin(), request);
    pFrame->loader()->loadFrameRequest(frameRequest, false, false, 0, 0, WebCore::SendReferrer);

// 被email等等应用程序调用, data是html文本字符串
// 将url和data封装到WebCore::ResourceRequest和WebCore::SubstituteData中
// 然后调用FrameLoader接口发送该request
static void LoadData(JNIEnv *env, jobject obj, jstring baseUrl, jstring data,
        jstring mimeType, jstring encoding, jstring failUrl)
    pFrame->loader()->load(request, substituteData, false);

// 停止加载当前页面
static void StopLoading(JNIEnv *env, jobject obj)
    // Stop loading the page and do not send an unload event
    pFrame->loader()->stopForUserCancel();


// 将当前页面的render tree以text形式表示, 调试使用
static jstring ExternalRepresentation(JNIEnv *env, jobject obj)

// 将document的innerText dump出来
static StringBuilder FrameAsText(WebCore::Frame *pFrame, jboolean dumpChildFrames) 


// reload网页或者通过history刷新
static void Reload(JNIEnv *env, jobject obj, jboolean allowStale)
    WebCore::FrameLoader* loader = pFrame->loader();                                                                                                         
    if (allowStale) {
        WebCore::Page* page = pFrame->page();
        WebCore::HistoryItem* item = page->backForwardList()->currentItem();                                                                                 
        if (item)
            page->goToItem(item, FrameLoadTypeIndexedBackForward);                                                                                           
    } else
        loader->reload(true);


// 历史项前进或后退
static void GoBackOrForward(JNIEnv *env, jobject obj, jint pos)
    WebCore::Frame* pFrame = GET_NATIVE_FRAME(env, obj);
    if (pos == 1)
        pFrame->page()->goForward();
    else if (pos == -1)
        pFrame->page()->goBack();
    else
        pFrame->page()->goBackOrForward(pos);

// 执行以javascript:开头的url
static jobject StringByEvaluatingJavaScriptFromString(JNIEnv *env, jobject obj, jstring script)
    WebCore::Frame* pFrame = GET_NATIVE_FRAME(env, obj);
    WebCore::ScriptValue value =
        pFrame->script()->executeScript(jstringToWtfString(env, script), true);

// 增加新的js interface， 使用java来实现
static void AddJavascriptInterface(JNIEnv *env, jobject obj, jint nativeFramePointer,
        jobject javascriptObj, jstring interfaceName)


// 内部函数，清空memory cache和page cache
static void ClearWebCoreCache()
    if (!WebCore::memoryCache()->disabled()) {
        // Disabling the cache will remove all resources from the cache.  They may
        // still live on if they are referenced by some Web page though.
        WebCore::memoryCache()->setDisabled(true);
        WebCore::memoryCache()->setDisabled(false);
    }

    // clear page cache
    int pageCapacity = WebCore::pageCache()->capacity();
    // Setting size to 0, makes all pages be released.
    WebCore::pageCache()->setCapacity(0);
    WebCore::pageCache()->releaseAutoreleasedPagesNow();
    WebCore::pageCache()->setCapacity(pageCapacity);

// 内部函数，清空webview cache
static void ClearWebViewCache()
#if USE(CHROME_NETWORK_STACK)
    WebCache::get(false /*privateBrowsing*/)->clear();
#else
    // The Android network stack provides a WebView cache in CacheManager.java.
    // Clearing this is handled entirely Java-side.
#endif

static void ClearCache(JNIEnv *env, jobject obj)
    ClearWebCoreCache();
    ClearWebViewCache();

// 文档中是否包括图片
static jboolean DocumentHasImages(JNIEnv *env, jobject obj)
    return pFrame->document()->images()->length() > 0;

// 检测文档中是否有password
// 遍历文档中的所有form
    // 遍历form中所有的input
        // 检查input的类型是否是password
static jboolean HasPasswordField(JNIEnv *env, jobject obj)
    WebCore::Frame* pFrame = GET_NATIVE_FRAME(env, obj);
    WTF::PassRefPtr<WebCore::HTMLCollection> form = pFrame->document()->forms();
    WebCore::Node* node = form->firstItem();
    ...

// 从dom中获取用户名和密码
static jobjectArray GetUsernamePassword(JNIEnv *env, jobject obj)
    WebCore::Frame* pFrame = GET_NATIVE_FRAME(env, obj);
    WebFrame::getWebFrame(pFrame)->getUsernamePasswordFromDom(pFrame, username, password);


// java层用来自动填充用户名和密码
// 遍历文档中的所有form
    // 遍历form中所有的input
        // 检查input的类型是否是password: 则设置password
        // 检查input的类型是否是text field: 则设置username
static void SetUsernamePassword(JNIEnv *env, jobject obj,
    jstring username, jstring password)

// 遍历form中的所有相关element
    // 如果是text类型input element, 且是auto complete, 且不是passwork
    // 将<input->name(), input->value()>发送到java层保存
void WebFrame::saveFormData(HTMLFormElement* form) 

// 设备的方向改变
static void OrientationChanged(JNIEnv *env, jobject obj, int orientation)  
    WebCore::Frame* pFrame = GET_NATIVE_FRAME(env, obj);
    pFrame->sendOrientationChangeEvent(orientation); 


// 页面的滚动条在左边还是右边, 阿拉伯文?
static jboolean GetShouldStartScrolledRight(JNIEnv *env, jobject obj,
        jint browserFrame)

// http 鉴权后，用户输入了密码
static void AuthenticationProceed(JNIEnv *env, jobject obj, int handle, jstring jUsername, jstring jPassword)

    WebUrlLoaderClient* client = reinterpret_cast<WebUrlLoaderClient*>(handle);
    std::string username = jstringToStdString(env, jUsername);
    std::string password = jstringToStdString(env, jPassword);
    client->setAuth(username, password);

// 用户取消了http 鉴权后
static void AuthenticationCancel(JNIEnv *env, jobject obj, int handle)
    WebUrlLoaderClient* client = reinterpret_cast<WebUrlLoaderClient*>(handle);
    client->cancelAuth();


static void SslCertErrorProceed(JNIEnv *env, jobject obj, int handle)
    WebUrlLoaderClient* client = reinterpret_cast
        <WebUrlLoaderClient*>(handle);
    client->proceedSslCertError();

static void SslCertErrorCancel(JNIEnv *env, jobject obj, int handle, int cert_error)
    WebUrlLoaderClient* client = reinterpret_cast
        <WebUrlLoaderClient*>(handle);
    client->cancelSslCertError(cert_error);


// 将客户端(android handset)的private key和certificate传下去
// 对证书进行签名并发送？
static void SslClientCert(JNIEnv *env, jobject obj, int handle, jbyteArray pkey, jobjectArray chain)
    WebUrlLoaderClient* client = reinterpret_cast<WebUrlLoaderClient*>(handle);
    client->sslClientCert(privateKey.release(), certificate);

