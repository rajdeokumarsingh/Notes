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
        m_loadType = FrameLoadTypeStandard;
        RefPtr<DocumentLoader> loader = m_client->createDocumentLoader(request, substituteData);
        load(loader.get());
    }
        |
        V
void FrameLoader::load(DocumentLoader* newDocumentLoader)
{
    ResourceRequest& r = newDocumentLoader->request();
    // ua, cache control, cookie, ...
    addExtraFieldsToMainResourceRequest(r);
    FrameLoadType type = FrameLoadTypeStandard;

    if (m_documentLoader)
        newDocumentLoader->setOverrideEncoding(m_documentLoader->overrideEncoding());

    loadWithDocumentLoader(newDocumentLoader, type, 0);
}
                |
                V
void FrameLoader::loadWithDocumentLoader(DocumentLoader* loader, FrameLoadType type, PassRefPtr<FormState> prpFormState)
{
    policyChecker()->setLoadType(type);

    const KURL& newURL = loader->request().url();
    const String& httpMethod = loader->request().httpMethod();

    if (shouldScrollToAnchor(isFormSubmission,  httpMethod, policyChecker()->loadType(), newURL)) {
        // TODO:
    } else {
        // inherit encoding from parent
        if (Frame* parent = m_frame->tree()->parent())
            loader->setOverrideEncoding(parent->loader()->documentLoader()->overrideEncoding());

        policyChecker()->stopCheck();
        setPolicyDocumentLoader(loader);

        policyChecker()->checkNavigationPolicy(loader->request(), loader, formState,
                callContinueLoadAfterNavigationPolicy, this);
    }
}       
                |
                V
external/webkit/Source/WebCore/loader/PolicyChecker.cpp
void PolicyChecker::checkNavigationPolicy(const ResourceRequest& request, DocumentLoader* loader,
            PassRefPtr<FormState> formState, NavigationPolicyDecisionFunction function, void* argument)
{
    m_callback.set(request, formState.get(), function, argument);
    m_frame->loader()->client()->dispatchDecidePolicyForNavigationAction(&PolicyChecker::continueAfterNavigationPolicy,
            action, request, formState);
}
    |
    V  // just checking form submission
external/webkit/Source/WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp
void FrameLoaderClientAndroid::dispatchDecidePolicyForNavigationAction(FramePolicyFunction func,
                                        const NavigationAction& action, const ResourceRequest& request,
                                        PassRefPtr<FormState> formState) {

    // Reset multiple form submission protection. If this is a resubmission, we check with the
    // user and reset the protection if they choose to resubmit the form (see WebCoreFrameBridge.cpp)
    if (action.type() == NavigationTypeFormSubmitted)
        m_frame->loader()->resetMultipleFormSubmissionProtection();

    if (action.type() == NavigationTypeFormResubmitted) {
        m_webFrame->decidePolicyForFormResubmission(func);
        return;
    } else
        (m_frame->loader()->policyChecker()->*func)(PolicyUse);
}
        |
        v
external/webkit/Source/WebCore/loader/PolicyChecker.cpp
// invoke shouldOverrideUrlLoading in Browser
void PolicyChecker::continueAfterNavigationPolicy(PolicyAction policy == PolicyUse)
{
    PolicyCallback callback = m_callback;
    bool shouldContinue = policy == PolicyUse;

    switch (policy) {
        case PolicyUse: {
            ResourceRequest request(callback.request());
            // invoke shouldOverrideUrlLoading in Browser
            if (!m_frame->loader()->client()->canHandleRequest(request)) {
                handleUnimplementablePolicy(m_frame->loader()->cannotShowURLError(callback.request()));
                callback.clearRequest();
                shouldContinue = false;
            }
            break;
        }
    }
    callback.call(shouldContinue);
}
            |
            V
external/webkit/Source/WebCore/loader/FrameLoader.cpp
void FrameLoader::callContinueLoadAfterNavigationPolicy(void* argument,
        const ResourceRequest& request, PassRefPtr<FormState> formState, bool shouldContinue)
{
    FrameLoader* loader = static_cast<FrameLoader*>(argument);
    loader->continueLoadAfterNavigationPolicy(request, formState, shouldContinue);
}
        |
        V
void FrameLoader::continueLoadAfterNavigationPolicy(const ResourceRequest&, PassRefPtr<FormState> formState, bool shouldContinue)
{
    // Two reasons we can't continue:
    //    1) Navigation policy delegate said we can't so request is nil. A primary case of this 
    //       is the user responding Cancel to the form repost nag sheet.
    //    2) User responded Cancel to an alert popped up by the before unload event handler.
    bool canContinue = shouldContinue && shouldClose();

    if (!canContinue) {
        setPolicyDocumentLoader(0);
        return;
    }

    FrameLoadType type = policyChecker()->loadType();
    // A new navigation is in progress, so don't clear the history's provisional item.
    stopAllLoaders(ShouldNotClearProvisionalItem);

    setProvisionalDocumentLoader(m_policyDocumentLoader.get());
    m_loadType = type;
    setState(FrameStateProvisional);

    setPolicyDocumentLoader(0);

    // load from page cache
    if (isBackForwardLoadType(type) && history()->provisionalItem()->isInPageCache()) {
        loadProvisionalItemFromCachedPage();
        return;
    }

    continueLoadAfterWillSubmitForm();
}

void FrameLoader::continueLoadAfterWillSubmitForm()
{
    // DocumentLoader calls back to our prepareForLoadStart
    m_provisionalDocumentLoader->prepareForLoadStart();
        {
            void FrameLoader::prepareForLoadStart()
                if (Page* page = m_frame->page())
                    page->progress()->progressStarted(m_frame);
                m_client->dispatchDidStartProvisionalLoad();
        }

    // already begin loading???
    DocumentLoader* activeDocLoader = activeDocumentLoader();
    if (activeDocLoader && activeDocLoader->isLoadingMainResource())
        return;

    m_loadingFromCachedPage = false;

    // assgin a unique id
    unsigned long identifier = 0;
    if (Page* page = m_frame->page()) {
        identifier = page->progress()->createUniqueIdentifier();
        notifier()->assignIdentifierToInitialRequest(identifier, m_provisionalDocumentLoader.get(), m_provisionalDocumentLoader->originalRequest());
    }

    // begin provisioning loading
    m_provisionalDocumentLoader->startLoadingMainResource(identifier);
}
        |
        V
external/webkit/Source/WebCore/loader/DocumentLoader.cpp
bool DocumentLoader::startLoadingMainResource(unsigned long identifier)
{
    m_mainResourceLoader = MainResourceLoader::create(m_frame);
    m_mainResourceLoader->setIdentifier(identifier);

    frameLoader()->addExtraFieldsToMainResourceRequest(m_request);

    m_mainResourceLoader->load(m_request, m_substituteData);
}
            |
            V
external/webkit/Source/WebCore/loader/MainResourceLoader.cpp
bool MainResourceLoader::load(const ResourceRequest& r, const SubstituteData& substituteData)
{
    m_substituteData = substituteData;
    ResourceRequest request(r);
    loadNow(request);
}
                |
                V
bool MainResourceLoader::loadNow(ResourceRequest& r)
{
    // Send this synthetic delegate callback since clients expect it, and
    // we no longer send the callback from within NSURLConnection for
    // initial requests.
    willSendRequest(r, ResourceResponse());

    const KURL& url = r.url();

    // add to load scheduler
    resourceLoadScheduler()->addMainResourceLoad(this);

    if (m_substituteData.isValid()) 
        handleDataLoadSoon(r);
    else if (shouldLoadEmpty || frameLoader()->representationExistsForURLScheme(url.protocol()))
        handleEmptyLoad(url, !shouldLoadEmpty);
    else
        // TODO:
        m_handle = ResourceHandle::create(m_frame->loader()->networkingContext(), r, this, false, true);

    return false;
}
    | // add loader to scheduler
    V
external/webkit/Source/WebCore/loader/ResourceLoadScheduler.cpp
void ResourceLoadScheduler::addMainResourceLoad(ResourceLoader* resourceLoader)
        hostForURL(resourceLoader->url(), CreateIfNotFound)->addLoadInProgress(resourceLoader);


ResourceLoadScheduler::HostInformation* ResourceLoadScheduler::hostForURL(const KURL& url, CreateHostPolicy createHostPolicy)
{
    m_hosts.checkConsistency();
    String hostName = url.host();
    HostInformation* host = m_hosts.get(hostName);
    if (!host && createHostPolicy == CreateIfNotFound) {
        host = new HostInformation(hostName, maxRequestsInFlightPerHost);
        m_hosts.add(hostName, host);
    }
    return host;
}
    |
    V
void ResourceLoadScheduler::HostInformation::addLoadInProgress(ResourceLoader* resourceLoader)
    LOG(ResourceLoading, "HostInformation '%s' loading '%s'. Current count %d", m_name.latin1().data(), resourceLoader->url().string().latin1().data(), m_requestsLoading.size());
    m_requestsLoading.add(resourceLoader);


m_handle = ResourceHandle::create(m_frame->loader()->networkingContext(), r, this, false, true);
    ||
    V
external/webkit/Source/WebCore/platform/network/ResourceHandle.cpp

PassRefPtr<ResourceHandle> ResourceHandle::create(NetworkingContext* context, const ResourceRequest& request, ResourceHandleClient* client, bool defersLoading, bool shouldContentSniff)
{
    RefPtr<ResourceHandle> newHandle(adoptRef(new ResourceHandle(request, client, defersLoading, shouldContentSniff)));
    newHandle->start(context);
}
    |
    V
ResourceHandle::ResourceHandle(const ResourceRequest& request, ResourceHandleClient* client, bool defersLoading, bool shouldContentSniff)
: d(new ResourceHandleInternal(this, request, client, defersLoading, shouldContentSniff && shouldContentSniffURL(request.url())))
{}

external/webkit/Source/WebCore/platform/network/android/ResourceHandleAndroid.cpp
bool ResourceHandle::start(NetworkingContext* context)
{
    RefPtr<ResourceLoaderAndroid> loader = 
        ResourceLoaderAndroid::start(this, d->m_firstRequest, context->frameLoaderClient(), isMainResource, false);
}
    |
    V
external/webkit/Source/WebKit/android/WebCoreSupport/ResourceLoaderAndroid.cpp
PassRefPtr<ResourceLoaderAndroid> ResourceLoaderAndroid::start(
                ResourceHandle* handle, const ResourceRequest& request, FrameLoaderClient* client, bool isMainResource, bool isSync)
{
    // Called on main thread
    return WebUrlLoader::start(client, handle, request, isMainResource, isMainFrame, isSync, webViewCore->webRequestContext());
}

external/webkit/Source/WebKit/android/WebCoreSupport/WebUrlLoader.cpp

PassRefPtr<WebUrlLoader> WebUrlLoader::start(FrameLoaderClient* client, WebCore::ResourceHandle* resourceHandle,
        const WebCore::ResourceRequest& resourceRequest, bool isMainResource, bool isMainFrame, bool isSync, WebRequestContext* context)
{
    FrameLoaderClientAndroid* androidClient = static_cast<FrameLoaderClientAndroid*>(client);
    WebFrame* webFrame = androidClient->webFrame();

    webFrame->maybeSavePassword(androidClient->getFrame(), resourceRequest);

    RefPtr<WebUrlLoader> loader = WebUrlLoader::create(webFrame, resourceHandle, resourceRequest);
    loader->m_loaderClient->start(isMainResource, isMainFrame, isSync, context);

    return loader.release();
}

        |
        V
// on main thread
WebUrlLoader::WebUrlLoader(WebFrame* webFrame, WebCore::ResourceHandle* resourceHandle, const WebCore::ResourceRequest& resourceRequest)
{
    m_loaderClient = new WebUrlLoaderClient(webFrame, resourceHandle, resourceRequest);
}
        |
        V
bool WebUrlLoaderClient::start(bool isMainResource, bool isMainFrame, bool sync, WebRequestContext* context)
{
    base::Thread* thread = ioThread();

    m_isMainResource = isMainResource;
    m_isMainFrame = isMainFrame;

    // Asynchronous start.
    // Important to set this before the thread starts so it has a reference and can't be deleted
    // before the task starts running on the IO thread.
    m_request->setRequestContext(context);
    thread->message_loop()->PostTask(FROM_HERE, NewRunnableMethod(m_request.get(), &WebRequest::start));
    return true;
}
        |
        V
void WebRequest::start()
{
    m_loadState = Started;
    // Update load flags with settings from WebSettings
    int loadFlags = m_request->load_flags();
    updateLoadFlags(loadFlags);
    m_request->set_load_flags(loadFlags);

    m_request->Start();
}










