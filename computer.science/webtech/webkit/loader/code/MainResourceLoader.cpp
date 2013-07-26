namespace WebCore {

MainResourceLoader::MainResourceLoader(Frame* frame)
    : ResourceLoader(frame, true, true)
    , m_dataLoadTimer(this, &MainResourceLoader::handleDataLoadNow)
    , m_loadingMultipartContent(false)
    , m_waitingForContentPolicy(false)
    , m_timeOfLastDataReceived(0.0)

MainResourceLoader::~MainResourceLoader()

PassRefPtr<MainResourceLoader> MainResourceLoader::create(Frame* frame)
    return adoptRef(new MainResourceLoader(frame));


void MainResourceLoader::receivedError(const ResourceError& error)
    frameLoader()->receivedMainResourceError(error, true);
    frameLoader()->notifier()->didFailToLoad(this, error);
    releaseResources();

void MainResourceLoader::didCancel(const ResourceError& error)
    m_dataLoadTimer.stop();

    if (m_waitingForContentPolicy) {
        frameLoader()->policyChecker()->cancelCheck();
        m_waitingForContentPolicy = false;
        deref(); // balances ref in didReceiveResponse

    frameLoader()->receivedMainResourceError(error, true);
    ResourceLoader::didCancel(error);

ResourceError MainResourceLoader::interruptionForPolicyChangeError() const
    return frameLoader()->interruptionForPolicyChangeError(request());


void MainResourceLoader::stopLoadingForPolicyChange()
    ResourceError error = interruptionForPolicyChangeError();
    error.setIsCancellation(true);
    cancel(error);

void MainResourceLoader::callContinueAfterNavigationPolicy(void* argument, 
        const ResourceRequest& request, PassRefPtr<FormState>, bool shouldContinue)
    static_cast<MainResourceLoader*>(argument)->
    continueAfterNavigationPolicy(request, shouldContinue);

void MainResourceLoader::continueAfterNavigationPolicy(
        const ResourceRequest& request, bool shouldContinue)

    if (!shouldContinue)
        stopLoadingForPolicyChange();
    else if (m_substituteData.isValid()) {
        // A redirect resulted in loading substitute data.
        handle()->cancel();
        handleDataLoadSoon(request);

    deref(); // balances ref in willSendRequest

void MainResourceLoader::addData(const char* data, int length, bool allAtOnce)
    ResourceLoader::addData(data, length, allAtOnce);

    // 将数据添加到DocumentLoader中
    documentLoader()->receivedData(data, length);


void MainResourceLoader::willSendRequest(ResourceRequest& newRequest, 
        const ResourceResponse& redirectResponse)
    // 如果response是从定向
    if (!redirectResponse.isNull()) {
        DocumentLoadTiming* documentLoadTiming = documentLoader()->timing();

        // Check if the redirected url is allowed to access the redirecting url's timing information.
        RefPtr<SecurityOrigin> securityOrigin = SecurityOrigin::create(newRequest.url());
        if (!securityOrigin->canRequest(redirectResponse.url()))
            documentLoadTiming->hasCrossOriginRedirect = true;

        documentLoadTiming->redirectCount++;
        if (!documentLoadTiming->redirectStart)
            documentLoadTiming->redirectStart = documentLoadTiming->fetchStart;
        documentLoadTiming->redirectEnd = currentTime();
        documentLoadTiming->fetchStart = documentLoadTiming->redirectEnd;
    }

    // Update cookie policy base URL as URL changes, except for subframes, which use the
    // URL of the main frame which doesn't change when we redirect.
    if (frameLoader()->isLoadingMainFrame())
        newRequest.setFirstPartyForCookies(newRequest.url());

    // If we're fielding a redirect in response to a POST, force a load from origin, since
    // this is a common site technique to return to a page viewing some data that the POST
    // just modified.
    // Also, POST requests always load from origin, but this does not affect subresources.
    if (newRequest.cachePolicy() == UseProtocolCachePolicy && isPostOrRedirectAfterPost(newRequest, redirectResponse))
        newRequest.setCachePolicy(ReloadIgnoringCacheData);

    ResourceLoader::willSendRequest(newRequest, redirectResponse);

    // Don't set this on the first request. It is set when the main load was started.
    m_documentLoader->setRequest(newRequest);

    Frame* top = m_frame->tree()->top();
    if (top != m_frame)
        frameLoader()->checkIfDisplayInsecureContent(top->document()->securityOrigin(), newRequest.url());

    // FIXME: Ideally we'd stop the I/O until we hear back from the navigation policy delegate
    // listener. But there's no way to do that in practice. So instead we cancel later if the
    // listener tells us to. In practice that means the navigation policy needs to be decided
    // synchronously for these redirect cases.
    if (!redirectResponse.isNull()) {
        ref(); // balanced by deref in continueAfterNavigationPolicy
        frameLoader()->policyChecker()->checkNavigationPolicy(newRequest, callContinueAfterNavigationPolicy, this);
    }
}


static bool shouldLoadAsEmptyDocument(const KURL& url)
{
#if PLATFORM(TORCHMOBILE)
    return url.isEmpty() || (url.protocolIs("about") && equalIgnoringRef(url, blankURL()));
#else 
    return url.isEmpty() || SchemeRegistry::shouldLoadURLSchemeAsEmptyDocument(url.protocol());
#endif
}

/* 完成了content policy的检测
enum PolicyAction {
    PolicyUse,      // 显示
    PolicyDownload, // 下载
    PolicyIgnore    // 忽略
};
*/
void MainResourceLoader::continueAfterContentPolicy(PolicyAction contentPolicy, const ResourceResponse& r)
    KURL url = request().url();
    const String& mimeType = r.mimeType();
    
    switch (contentPolicy) {
    // 
    case PolicyUse: {
        // Prevent remote web archives from loading because they can claim to be from any domain and thus avoid cross-domain security checks (4120255).
        bool isRemoteWebArchive = equalIgnoringCase("application/x-webarchive", mimeType) && !m_substituteData.isValid() && !url.isLocalFile();
        if (!frameLoader()->canShowMIMEType(mimeType) || isRemoteWebArchive) {
            frameLoader()->policyChecker()->cannotShowMIMEType(r);
            // Check reachedTerminalState since the load may have already been cancelled inside of _handleUnimplementablePolicyWithErrorCode::.
            if (!reachedTerminalState())
                stopLoadingForPolicyChange();
            return;
        }
        break;
    }

    case PolicyDownload:
        // 通知浏览器层，需要下载该文件
        frameLoader()->client()->download(
        m_handle.get(), request(), m_handle.get()->firstRequest(), r);

        // It might have gone missing
        if (frameLoader())
            receivedError(interruptionForPolicyChangeError());
        return;

    case PolicyIgnore:
        stopLoadingForPolicyChange();
        return;
    
    default:
        ASSERT_NOT_REACHED();
    }

    /* PolicyUse:
        didReceiveData()
        didFinishLoading()
    */

    // 处理http错误
    if (r.isHTTP()) {
        int status = r.httpStatusCode();
        if (status < 200 || status >= 300) {
            bool hostedByObject = frameLoader()->isHostedByObjectElement();

            frameLoader()->handleFallbackContent();
            // object elements are no longer rendered after we fallback, so don't
            // keep trying to process data from their load

            if (hostedByObject)
                cancel();

    // we may have cancelled this load as part of switching to fallback content
    if (!reachedTerminalState())
        ResourceLoader::didReceiveResponse(r);

    if (frameLoader() && !frameLoader()->isStopping()) {
        if (m_substituteData.isValid()) {
            if (m_substituteData.content()->size())
                didReceiveData(m_substituteData.content()->data(),
                        m_substituteData.content()->size(), 
                        m_substituteData.content()->size(), true);
            if (frameLoader() && !frameLoader()->isStopping()) 
                didFinishLoading(0);
        } else if (shouldLoadAsEmptyDocument(url) || 
                frameLoader()->representationExistsForURLScheme(url.protocol()))
            didFinishLoading(0);



// 受到response后需要进行policy check
void MainResourceLoader::didReceiveResponse(const ResourceResponse& r)

    HTTPHeaderMap::const_iterator it = r.httpHeaderFields().find(
            AtomicString("x-frame-options"));
    if (it != r.httpHeaderFields().end()) {
        String content = it->second;
        if (m_frame->loader()->shouldInterruptLoadForXFrameOptions(content, r.url())) {
            InspectorInstrumentation::continueAfterXFrameOptionsDenied(m_frame.get(), documentLoader(), identifier(), r);
            DEFINE_STATIC_LOCAL(String, consoleMessage, ("Refused to display document because display forbidden by X-Frame-Options.\n"));
            m_frame->domWindow()->console()->addMessage(JSMessageSource, LogMessageType, ErrorMessageLevel, consoleMessage, 1, String());

            cancel();
            return;

    if (m_loadingMultipartContent) {
        frameLoader()->setupForReplaceByMIMEType(r.mimeType());
        clearResourceData();
    
    if (r.isMultipart())
        m_loadingMultipartContent = true;
        
    m_documentLoader->setResponse(r);

    m_response = r;

    m_waitingForContentPolicy = true;
    ref(); // balanced by deref in continueAfterContentPolicy and didCancel

    // Always show content with valid substitute data.
    if (frameLoader()->activeDocumentLoader()->substituteData().isValid()) {
        callContinueAfterContentPolicy(this, PolicyUse);
        return;
    }

    frameLoader()->policyChecker()->checkContentPolicy(
            m_response, callContinueAfterContentPolicy, this);


void MainResourceLoader::didReceiveData(const char* data, int length, long long encodedDataLength, bool allAtOnce)
    m_timeOfLastDataReceived = currentTime();
    ResourceLoader::didReceiveData(data, length, encodedDataLength, allAtOnce);


void MainResourceLoader::didFinishLoading(double finishTime)
{
    documentLoader()->timing()->responseEnd = finishTime ? finishTime : (m_timeOfLastDataReceived ? m_timeOfLastDataReceived : currentTime());
    frameLoader()->finishedLoading();
    ResourceLoader::didFinishLoading(finishTime);

void MainResourceLoader::didFail(const ResourceError& error)
    receivedError(error);

void MainResourceLoader::handleEmptyLoad(const KURL& url, bool forURLScheme)
    String mimeType;
    if (forURLScheme)
        mimeType = frameLoader()->generatedMIMETypeForURLScheme(url.protocol());
    else
        mimeType = "text/html";
    
    ResourceResponse response(url, mimeType, 0, String(), String());
    didReceiveResponse(response);

// load data, 非http加载。如email
void MainResourceLoader::handleDataLoadNow(MainResourceLoaderTimer*)
    RefPtr<MainResourceLoader> protect(this);

    KURL url = m_substituteData.responseURL();

    // Clear the initial request here so that subsequent entries into the
    // loader will not think there's still a deferred load left to do.
    m_initialRequest = ResourceRequest();
        
    ResourceResponse response(url, m_substituteData.mimeType(), 
            m_substituteData.content()->size(), m_substituteData.textEncoding(), "");
    response.setOverrideEncoding(m_substituteData.isOverrideEncoding());
    didReceiveResponse(response);


void MainResourceLoader::startDataLoadTimer()
    m_dataLoadTimer.startOneShot(0);


void MainResourceLoader::handleDataLoadSoon(const ResourceRequest& r)
    m_initialRequest = r;
    
    if (m_documentLoader->deferMainResourceDataLoad())
        startDataLoadTimer();
    else
        handleDataLoadNow(0);

// 开始加载
bool MainResourceLoader::loadNow(ResourceRequest& r)
    // initial requests.
    willSendRequest(r, ResourceResponse());

    const KURL& url = r.url();
    resourceLoadScheduler()->addMainResourceLoad(this);

    if (m_substituteData.isValid()) 
        handleDataLoadSoon(r);
    else
        m_handle = ResourceHandle::create(m_frame->loader()->networkingContext(), r, this, false, true);
    return false;


bool MainResourceLoader::load(const ResourceRequest& r, const SubstituteData& substituteData)

    m_substituteData = substituteData;

    documentLoader()->timing()->fetchStart = currentTime();
    ResourceRequest request(r);

    bool defer = defersLoading();
    if (defer) {
        bool shouldLoadEmpty = shouldLoadAsEmptyDocument(request.url());
        if (shouldLoadEmpty)
            defer = false;
    }
    if (!defer) {
        if (loadNow(request)) {
            // Started as an empty document, but was redirected to something non-empty.
            ASSERT(defersLoading());
            defer = true;
        }
    }
    if (defer)
        m_initialRequest = request;

    return true;

void MainResourceLoader::setDefersLoading(bool defers)
    ResourceLoader::setDefersLoading(defers);

    if (defers) {
        if (m_dataLoadTimer.isActive())
            m_dataLoadTimer.stop();
    } else {
        if (m_initialRequest.isNull())
            return;

        if (m_substituteData.isValid() && m_documentLoader->deferMainResourceDataLoad())
            startDataLoadTimer();
        else {
            ResourceRequest r(m_initialRequest);
            m_initialRequest = ResourceRequest();
            loadNow(r);

