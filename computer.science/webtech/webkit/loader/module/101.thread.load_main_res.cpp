/*WebKit 源码中的FrameLoader类，是专门负责将文档载入到Frame中。
  用户点击一个连接时，FrameLoader创建一个DocumentLoader对象用来载入文档
  DocumentLoader会首先访问 WebKit的Client对象>的shouldOverrideURL方法
  决定是否应该载入指定的URL数据
  如果该URL允许载入，则DocumentLoader会创建MainResourceLoader对象
  MainResourceLoader使用ResourceHandle接口与平台相关的网络库通信
  MainResourceLoader从网络上获取Frame的信息，然后交给相应的解析器解析

    FrameLoader
        |
        V
    DocumentLoader
        | JNI
        V
    Browser(shouldOverrideURL)
        | JNI
        V
    DocumentLoader
        |
        V
    MainResourceLoader
        |
        V
    ResourceHandle
*/

WebKit/android/jni/WebCoreFrameBridge.cpp
    static void LoadUrl(JNIEnv *env, jobject obj, jstring url, jobject headers)
        pFrame->loader()->load(request, false);
            |
            V
void FrameLoader::load(const ResourceRequest& request, 
        const SubstituteData& substituteData, bool lockHistory)

    m_loadType = FrameLoadTypeStandard;
    RefPtr<DocumentLoader> loader = m_client->createDocumentLoader(
            request, substituteData); 
                |
                V
    {
    PassRefPtr<DocumentLoader> FrameLoaderClientAndroid::createDocumentLoader(
            const ResourceRequest& request, const SubstituteData& data) {
        RefPtr<DocumentLoader> loader = DocumentLoader::create(request, data);
        return loader.release();
    }

    load(loader.get());
        |
        V
void FrameLoader::load(DocumentLoader* newDocumentLoader)
    loadWithDocumentLoader(newDocumentLoader, FrameLoadTypeStandard, 0);
        |
        V
void FrameLoader::loadWithDocumentLoader(DocumentLoader* loader, 
    FrameLoadType type,     //  = FrameLoadTypeStandard
    PassRefPtr<FormState> prpFormState // = 0
    )

    policyChecker()->setLoadType(type);

    // 如果是页面内的anchor跳转
    if (shouldScrollToAnchor(isFormSubmission,  httpMethod, 
                policyChecker()->loadType(), newURL)) 

        // 上一级的document loader
        RefPtr<DocumentLoader> oldDocumentLoader = m_documentLoader;
        NavigationAction action(newURL, policyChecker()->loadType(), 
                isFormSubmission);
        oldDocumentLoader->setTriggeringAction(action);
        policyChecker()->stopCheck();
        policyChecker()->checkNavigationPolicy(loader->request(), 
                oldDocumentLoader.get(), formState,
                callContinueFragmentScrollAfterNavigationPolicy, this);

    else // 不是anchor跳转

        // 设置new loader的override encoding
        if (Frame* parent = m_frame->tree()->parent())
            loader->setOverrideEncoding(parent->loader()
                    ->documentLoader()->overrideEncoding());

        policyChecker()->stopCheck();
        setPolicyDocumentLoader(loader); {
            if (loader) loader->setFrame(m_frame);
            m_policyDocumentLoader->detachFromFrame();
            m_policyDocumentLoader = loader;
        }

        if (loader->triggeringAction().isEmpty())
            loader->setTriggeringAction(NavigationAction(newURL, 
                        policyChecker()->loadType(), isFormSubmission));

        if (Element* ownerElement = m_frame->ownerElement()) {
            if (!ownerElement->dispatchBeforeLoadEvent(
                        loader->request().url().string())) {
                continueLoadAfterNavigationPolicy(loader->request(), 
                        formState, false);
                return;

        policyChecker()->checkNavigationPolicy(
                loader->request(), loader, formState,
                callContinueLoadAfterNavigationPolicy, this);
                |
                V
void PolicyChecker::checkNavigationPolicy(
    const ResourceRequest& newRequest, 
    NavigationPolicyDecisionFunction function, // callContinueLoadAfterNavigationPolicy
    void* argument  // FrameLoader
    )
            |
            V
void PolicyChecker::checkNavigationPolicy(const ResourceRequest& request, 
    DocumentLoader* loader,PassRefPtr<FormState> formState, 
    NavigationPolicyDecisionFunction function, void* argument) 

    // 在前面scroll anchor的地方设置了action
    NavigationAction action = loader->triggeringAction();
    if (action.isEmpty()) {
        action = NavigationAction(request.url(), NavigationTypeOther);
        loader->setTriggeringAction(action);

    loader->setLastCheckedRequest(request);

    // 设置回调函数, browser检测完should override loading 后会调用call back
    // function: callContinueLoadAfterNavigationPolicy
    // argument: FrameLoader
    m_callback.set(request, formState.get(), function, argument);

    m_delegateIsDecidingNavigationPolicy = true;
    m_frame->loader()->client()->dispatchDecidePolicyForNavigationAction(
            &PolicyChecker::continueAfterNavigationPolicy,
            action, request, formState);
    m_delegateIsDecidingNavigationPolicy = false;
        |
        V
void FrameLoaderClientAndroid::dispatchDecidePolicyForNavigationAction(
        FramePolicyFunction func,   // &PolicyChecker::continueAfterNavigationPolicy,
        const NavigationAction& action, 
        const ResourceRequest& request,
        PassRefPtr<FormState> formState)

    // Form submit
    if (action.type() == NavigationTypeFormResubmitted) {
        m_webFrame->decidePolicyForFormResubmission(func);
        return;
    } else
        (m_frame->loader()->policyChecker()->*func)(PolicyUse);
            |
            V
void PolicyChecker::continueAfterNavigationPolicy(
        PolicyAction policy     // PolicyUse
        )
    PolicyCallback callback = m_callback; // callContinueLoadAfterNavigationPolicy
    m_callback.clear();

    bool shouldContinue = policy == PolicyUse; // true

    case PolicyUse: {
        ResourceRequest request(callback.request());
        // 检查是否能处理需要被加载的url
        if (!m_frame->loader()->client()->canHandleRequest(request))
        {
        bool FrameLoaderClientAndroid::canHandleRequest(
                const ResourceRequest& request) const
            bool canHandle = WebCore::equalIgnoringFragmentIdentifier(request.url(), m_frame->document()->url()) ||
                (request.url().protocol().startsWith("http", false) && m_frame->tree() && m_frame->tree()->parent()) ||
                m_webFrame->canHandleRequest(request); {
                    // Always handle "POST" in place
                    if (equalIgnoringCase(request.httpMethod(), "POST"))
                        return true;

                            | JNI
                            V
                    base/core/java/android/webkit/BrowserFrame.java
                        public boolean handleUrl(String url) {
                            if (mCallbackProxy.shouldOverrideUrlLoading(url)) {
                                // if the url is hijacked, reset the state of the BrowserFrame
                                didFirstLayout();
                                return true;
                            else
                                return false;
                }

            m_webFrame->loadStarted(m_frame); {
                void WebFrame::loadStarted(WebCore::Frame* frame)
                    env->CallVoidMethod(javaFrame.get(), 
                            mJavaFrame->mLoadStarted, urlStr, favicon, 
                            static_cast<int>(loadType), isMainFrame);
                            |
                            V JNI
                        base/core/java/android/webkit/BrowserFrame.java
                            private void loadStarted(String url, Bitmap favicon,
                                int loadType, boolean isMainFrame) 
                            mCallbackProxy.onPageStarted(url, favicon);

                            if (isMainFrame) {
                                // Call onPageStarted for main frames.
                                mCallbackProxy.onPageStarted(url, favicon);
                                mWebViewCore.removeMessages(
                                        WebViewCore.EventHub.WEBKIT_DRAW);
            }
        }
            callback.call(shouldContinue);
                |
                V
void FrameLoader::callContinueLoadAfterNavigationPolicy(
        void* argument, const ResourceRequest& request, 
        PassRefPtr<FormState> formState, bool shouldContinue)

    FrameLoader* loader = static_cast<FrameLoader*>(argument);
    loader->continueLoadAfterNavigationPolicy(
            request, formState, shouldContinue);
            |
            V
void FrameLoader::continueLoadAfterNavigationPolicy(
        const ResourceRequest&, PassRefPtr<FormState> formState, 
        bool shouldContinue     // true
        )

    FrameLoadType type = policyChecker()->loadType(); // FrameLoadTypeStandard

    // provisional document loader
    setProvisionalDocumentLoader(m_policyDocumentLoader.get());
    m_loadType = type;
    setState(FrameStateProvisional); {
        enum FrameState {
            FrameStateProvisional, // 开始加载，还没有收到任何网络数据
            // This state indicates we are ready to commit to a page,
            // which means the view will transition to use the new data source.
            FrameStateCommittedPage,  // 已经收到了第一个byte
            FrameStateComplete        // 已经完成网络加载
        };
    }

    setPolicyDocumentLoader(0);

    // 如果是历史前进/后退或已经在cache中
    // TODO: fork
    if (isBackForwardLoadType(type) && 
            history()->provisionalItem()->isInPageCache()) {
        loadProvisionalItemFromCachedPage();
        return;

    if (formState)
        m_client->dispatchWillSubmitForm(
                &PolicyChecker::continueLoadAfterWillSubmitForm, formState);
    else
        continueLoadAfterWillSubmitForm();
            |
            V
void FrameLoader::continueLoadAfterWillSubmitForm()
    // DocumentLoader calls back to our prepareForLoadStart
    m_provisionalDocumentLoader->prepareForLoadStart();

    DocumentLoader* activeDocLoader = activeDocumentLoader();

    // 创建ID
    if (Page* page = m_frame->page()) {
        identifier = page->progress()->createUniqueIdentifier();
        notifier()->assignIdentifierToInitialRequest(identifier, 
                m_provisionalDocumentLoader.get(), 
                m_provisionalDocumentLoader->originalRequest());

    if (!m_provisionalDocumentLoader->startLoadingMainResource(identifier))
            m_provisionalDocumentLoader->updateLoading();


bool DocumentLoader::startLoadingMainResource(
        unsigned long identifier    // createUniqueIdentifier()
        )
    m_mainResourceLoader = MainResourceLoader::create(m_frame);
    m_mainResourceLoader->setIdentifier(identifier);

    frameLoader()->addExtraFieldsToMainResourceRequest(m_request);

    m_mainResourceLoader->load(m_request, m_substituteData)
        |
        V
bool MainResourceLoader::load(const ResourceRequest& r, const SubstituteData& substituteData)
    loadNow(request)
        |
        V
bool MainResourceLoader::loadNow(ResourceRequest& r)
    willSendRequest(r, ResourceResponse());

    resourceLoadScheduler()->addMainResourceLoad(this); {
        void ResourceLoadScheduler::addMainResourceLoad(
                ResourceLoader* resourceLoader)

            // 将ResourceLoader加入到对应host的队列中
            hostForURL(resourceLoader->url(), CreateIfNotFound)
                ->addLoadInProgress(resourceLoader); {

                void ResourceLoadScheduler::HostInformation::
                    addLoadInProgress(ResourceLoader* resourceLoader)
                    m_requestsLoading.add(resourceLoader);
            }
    }

    // 加载data， 就像从email发过来的html数据
    if (m_substituteData.isValid())
        handleDataLoadSoon(r); { ////////////////////
            void MainResourceLoader::handleDataLoadNow(MainResourceLoaderTimer*)
                KURL url = m_substituteData.responseURL();
                if (url.isEmpty())
                    url = m_initialRequest.url();

                m_initialRequest = ResourceRequest();
                // html data都保存在substitude data中
                ResourceResponse response(url, m_substituteData.mimeType(),
                        m_substituteData.content()->size(), m_substituteData.textEncoding(), "");
                response.setOverrideEncoding(m_substituteData.isOverrideEncoding());
                didReceiveResponse(response);
        }
    else if (shouldLoadEmpty || frameLoader()->representationExistsForURLScheme(url.protocol()))
        handleEmptyLoad(url, !shouldLoadEmpty);
    else
        m_handle = ResourceHandle::create(m_frame->loader()->networkingContext(), r, this, false, true);

    return false;
        |
        |
        V
void ResourceLoadScheduler::servePendingRequests(
        HostInformation* host, ResourceLoadPriority minimumPriority)

    while (!requestsPending.isEmpty()) {
        RefPtr<ResourceLoader> resourceLoader = requestsPending.first();

        Document* document = resourceLoader->frameLoader() ? 
            resourceLoader->frameLoader()->frame()->document() : 0;
        bool shouldLimitRequests = !host->name().isNull() || 
            (document && (document->parsing() || !document->haveStylesheetsLoaded()));  
        if (shouldLimitRequests && host->limitRequests(ResourceLoadPriority(priority)))
            return;

        requestsPending.removeFirst();
        host->addLoadInProgress(resourceLoader.get());
        resourceLoader->start();
    }
            |
            v
void ResourceLoader::start()
    if (!m_reachedTerminalState)   
        m_handle = ResourceHandle::create(
            m_frame->loader()->networkingContext(), m_request, 
            this, m_defersLoading, m_shouldContentSniff);
            |
            v
PassRefPtr<ResourceHandle> ResourceHandle::create(
        NetworkingContext* context, const ResourceRequest& request, 
        ResourceHandleClient* client, bool defersLoading, bool shouldContentSniff)

    RefPtr<ResourceHandle> newHandle(adoptRef(
                new ResourceHandle(request, client, 
                defersLoading, shouldContentSniff)));

    if (newHandle->start(context))     
        return newHandle.release();
                |
                V
bool ResourceHandle::start(NetworkingContext* context)

    MainResourceLoader* mainLoader = context->mainResourceLoader();
    bool isMainResource = static_cast<void*>(mainLoader) == static_cast<void*>(client());
    RefPtr<ResourceLoaderAndroid> loader = ResourceLoaderAndroid::start(
            this, d->m_firstRequest, context->frameLoaderClient(), isMainResource, false);
                    |
                    v
PassRefPtr<ResourceLoaderAndroid> ResourceLoaderAndroid::start(
        ResourceHandle* handle, const ResourceRequest& request, 
        FrameLoaderClient* client, bool isMainResource, bool isSync)

    // Called on main thread
    FrameLoaderClientAndroid* clientAndroid = 
        static_cast<FrameLoaderClientAndroid*>(client);

#if USE(CHROME_NETWORK_STACK)
    WebViewCore* webViewCore = WebViewCore::getWebViewCore(
            clientAndroid->getFrame()->view());
    bool isMainFrame = !(clientAndroid->getFrame()->tree() 
            && clientAndroid->getFrame()->tree()->parent());
    return WebUrlLoader::start(client, handle, request, isMainResource, 
            isMainFrame, isSync, webViewCore->webRequestContext());
#else
    return clientAndroid->webFrame()->startLoadingResource(handle, request, isMainResource, isSync);
#endif
            |
            V
PassRefPtr<WebUrlLoader> WebUrlLoader::start(FrameLoaderClient* client, WebCore::ResourceHandle* resourceHandle,
        const WebCore::ResourceRequest& resourceRequest, bool isMainResource, bool isMainFrame, bool isSync, WebRequestContext* context)

    FrameLoaderClientAndroid* androidClient = static_cast<FrameLoaderClientAndroid*>(client);
    WebFrame* webFrame = androidClient->webFrame();

    webFrame->maybeSavePassword(androidClient->getFrame(), resourceRequest);

    RefPtr<WebUrlLoader> loader = WebUrlLoader::create(webFrame, resourceHandle, resourceRequest);
    loader->m_loaderClient->start(isMainResource, isMainFrame, isSync, context); {
        m_loaderClient = new WebUrlLoaderClient(webFrame, resourceHandle, resourceRequest);

        bool WebUrlLoaderClient::start(bool isMainResource, bool isMainFrame, 
                bool sync, WebRequestContext* context)
            m_isMainResource = isMainResource;
            m_isMainFrame = isMainFrame;   
            m_sync = sync;
            if (m_sync) {
                m_request->setSync(sync);
                m_request->setRequestContext(context);
                thread->message_loop()->PostTask(FROM_HERE, NewRunnableMethod(m_request.get(), &WebRequest::start));
                 while(!m_finished) {
                    ...
                    syncCondition()->TimedWait(base::TimeDelta::FromSeconds(kCallbackWaitingTime));
                 }
            else
                m_request->setRequestContext(context);
                thread->message_loop()->PostTask(FROM_HERE, NewRunnableMethod(m_request.get(), &WebRequest::start));
    }

    return loader.release();
            |
            V
void WebRequest::start()
    m_loadState = Started;

    // Handle data urls before we send it off to the http stack                                                                       
    if (m_request->url().SchemeIs("data"))
        return handleDataURL(m_request->url());

    if (m_request->url().SchemeIs("browser"))                                                                                         
        return handleBrowserURL(m_request->url());

    // Update load flags with settings from WebSettings                                                                               
    int loadFlags = m_request->load_flags();
    updateLoadFlags(loadFlags);    
    m_request->set_load_flags(loadFlags);

    m_request->Start();
            |
            V
        OwnPtr<net::URLRequest> m_request;



