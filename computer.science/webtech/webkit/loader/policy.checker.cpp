// What is navigation?
// look up in the code
enum NavigationType {     
    NavigationTypeLinkClicked,     
    NavigationTypeFormSubmitted,   
    NavigationTypeBackForward,     
    NavigationTypeReload,
    NavigationTypeFormResubmitted, 
    NavigationTypeOther
}; // android中基本没有用这个enum, 常用这个FrameLoadType enum

/*******************************************************************************
Navigation policy
(没有考虑Form的情况)

在FrameLoader加载一个url之前(发出网络请求之前)，会调用navigation policy流程
(感觉对于get方法，最主要的一件事就是调用shouldOverrideUrlLoading, form的另说)

对于相同的/空的请求, 直接通过检测
对于无法访问的url， 直接通过检查
调用平台相关的policy检查函数
    FrameLoaderClientAndroid::dispatchDecidePolicyForNavigationAction
    如果navigation类型是form submit(NavigationTypeFormResubmitted)
        调用平台相关函数，m_webFrame->decidePolicyForFormResubmission(func);
        返回
    否则, 通过检查
如果是PolicyIgnore, 就忽略请求
如果是PolicyDownload, 
    然后发起下载(android好像没有实现这个接口)
如果是PolicyUse
    调用平台相关的canHandleRequest() 
        这个函数会调用java层的shouldOverrideUrlLoading
        看url是否能被上层其他的应用程序处理
        如rtsp流媒体地址, 浏览器就会调用视频播放器处理

如果上层没有将url截住, 就继续加载页面
********************************************************************************/

void FrameLoader::loadWithDocumentLoader(DocumentLoader* loader, FrameLoadType type, PassRefPtr<FormState> prpFormState)
    policyChecker()->stopCheck();
    setPolicyDocumentLoader(loader);
    if (loader->triggeringAction().isEmpty())
        loader->setTriggeringAction(NavigationAction(newURL, policyChecker()->loadType(), isFormSubmission));

    if (Element* ownerElement = m_frame->ownerElement()) {
        if (!ownerElement->dispatchBeforeLoadEvent(loader->request().url().string())) {
            continueLoadAfterNavigationPolicy(loader->request(), formState, false);
            return;
        }
    }

    policyChecker()->checkNavigationPolicy(loader->request(), loader, formState,
            callContinueLoadAfterNavigationPolicy, this);
                |
                V
void PolicyChecker::checkNavigationPolicy(const ResourceRequest& request, DocumentLoader* loader,
        PassRefPtr<FormState> formState, NavigationPolicyDecisionFunction function, void* argument)
{
    // 如果没有NavigationAction, 创建一个other类型的action
    NavigationAction action = loader->triggeringAction();
    if (action.isEmpty()) {
        action = NavigationAction(request.url(), NavigationTypeOther);
        loader->setTriggeringAction(action);

    // Don't ask more than once for the same request or if we are loading an empty URL.
    // This avoids confusion on the part of the client.
    // 对于相同的/空的请求, 直接通过检测
    if (equalIgnoringHeaderFields(request, loader->lastCheckedRequest()) || (!request.isNull() && request.url().isEmpty())) {
        function(argument, request, 0, true);
        loader->setLastCheckedRequest(request);
        return;
    }

    // We are always willing to show alternate content for unreachable URLs;
    // treat it like a reload so it maintains the right state for b/f list.
    // 对于无法访问的url， 直接通过检查
    if (loader->substituteData().isValid() && !loader->substituteData().failingURL().isEmpty()) {
        if (isBackForwardLoadType(m_loadType))
            m_loadType = FrameLoadTypeReload;
        function(argument, request, 0, true);
        return;
    }


    loader->setLastCheckedRequest(request);
    m_callback.set(request, formState.get(), function, argument);

    m_delegateIsDecidingNavigationPolicy = true;
    m_frame->loader()->client()->dispatchDecidePolicyForNavigationAction(&PolicyChecker::continueAfterNavigationPolicy,
            action, request, formState);                |
    m_delegateIsDecidingNavigationPolicy = false;       |
}                                                       |
                                                        v
void FrameLoaderClientAndroid::dispatchDecidePolicyForNavigationAction(FramePolicyFunction func,
                                const NavigationAction& action, const ResourceRequest& request,
                                PassRefPtr<FormState> formState) {
    if (!func)
        return;
    if (request.isNull()) {
        (m_frame->loader()->policyChecker()->*func)(PolicyIgnore); 
        // PolicyChecker::continueAfterNavigationPolicy(PolicyIgnore)
        // 如果请求是空的，忽略请求
        return;
    }

    // Reset multiple form submission protection. If this is a resubmission, we check with the
    // user and reset the protection if they choose to resubmit the form (see WebCoreFrameBridge.cpp)
    if (action.type() == NavigationTypeFormSubmitted)
        m_frame->loader()->resetMultipleFormSubmissionProtection();

    if (action.type() == NavigationTypeFormResubmitted) {
        m_webFrame->decidePolicyForFormResubmission(func);
        return;
    } else
        (m_frame->loader()->policyChecker()->*func)(PolicyUse);
        // 通过检查
        // PolicyChecker::continueAfterNavigationPolicy(PolicyUse)
}                    |
                     |
                     v
void PolicyChecker::continueAfterNavigationPolicy(PolicyAction policy) 
{   
    PolicyCallback callback = m_callback;
    m_callback.clear();

    bool shouldContinue = policy == PolicyUse;

    switch (policy) {
        case PolicyIgnore:
            callback.clearRequest();
            break;
        case PolicyDownload:
            m_frame->loader()->client()->startDownload(callback.request());
            callback.clearRequest();
            break;
        case PolicyUse: {
            ResourceRequest request(callback.request());
            if (!m_frame->loader()->client()->canHandleRequest(request)) {
                handleUnimplementablePolicy(m_frame->loader()->cannotShowURLError(callback.request()));
                callback.clearRequest();
                shouldContinue = false;
            }
            break;
        }
    }

    callback.call(shouldContinue); // callContinueLoadAfterNavigationPolicy for load a page
}
                                            bool FrameLoaderClientAndroid::canHandleRequest(const ResourceRequest& request) const {
                                                // This is called by WebCore to determine if this load can be handled by the
                                                // WebView. In general, we delegate to the WebFrame, which may ask the
                                                // embedding application whether it wishes to hijack the load. However, we
                                                // don't allow this if the load is ...
                                                // - An intrapage navigation
                                                // - An iframe with a HTTP or HTTPS scheme URL
                                                bool canHandle = WebCore::equalIgnoringFragmentIdentifier(request.url(), m_frame->document()->url()) ||
                                                    (request.url().protocol().startsWith("http", false) && m_frame->tree() && m_frame->tree()->parent()) ||

                                                    // 会调用到浏览器的shouldOverrideUrlLoading
                                                    m_webFrame->canHandleRequest(request);

                                                // If this is a server-side redirect and the WebView will handle loading it,
                                                // notify the WebFrame, which may notify the embedding application that
                                                // we're loading a new URL.
                                                if (m_didReceiveServerRedirect && canHandle)
                                                    m_webFrame->loadStarted(m_frame);
                                                m_didReceiveServerRedirect = false;

                                                return canHandle;
                                            }


void FrameLoader::callContinueLoadAfterNavigationPolicy(void* argument,
        const ResourceRequest& request, PassRefPtr<FormState> formState, bool shouldContinue)
{           
    FrameLoader* loader = static_cast<FrameLoader*>(argument);
    loader->continueLoadAfterNavigationPolicy(request, formState, shouldContinue);
    // 继续加载
} 

/********************************************************************************
 Content policy
1. 只有MainResourceLoader会使用content policy
2. 当MainResourceLoader收到didReceiveResponse回调后，开始content policy的流程
3. 默认会使用policy use, 将会展示该资源
4. 如果设置了http头Content-Disposition, 设置policy download, 将会下载该资源
5. 如果无法显示mime type的内容，选择policy download, 将会下载该资源
6. status code 204 表明内容没有变化. 使用policy ignore, 将会忽略该资源
7. 如果substituteData中已经有内容了， 调用MainResourceLoader::didReceiveData()
********************************************************************************/

// 只有MainResourceLoader会使用content policy
// 当MainResourceLoader收到didReceiveResponse回调后，开始content policy的流程
void MainResourceLoader::didReceiveResponse(const ResourceResponse& r)
    frameLoader()->policyChecker()->checkContentPolicy(m_response, callContinueAfterContentPolicy, this);
    // 注意content check完毕之后会调用callContinueAfterContentPolicy
            |
            V
void PolicyChecker::checkContentPolicy(const ResourceResponse& response, ContentPolicyDecisionFunction function, void* argument)
{   
    m_callback.set(function, argument);
    m_frame->loader()->client()->dispatchDecidePolicyForResponse(&PolicyChecker::continueAfterContentPolicy,
            response, m_frame->loader()->activeDocumentLoader()->request());
    // 注意dispatchDecidePolicyForResponse完毕之后会调用continueAfterContentPolicy
} 

void FrameLoaderClientAndroid::dispatchDecidePolicyForResponse(FramePolicyFunction func,
        const ResourceResponse& response, const ResourceRequest& request) {

    PolicyChecker* policy = m_frame->loader()->policyChecker();

    // 忽略空的请求
    if (request.isNull()) {
        (policy->*func)(PolicyIgnore); 
        return;
    }  

    // Default to Use (display internally).
    PolicyAction action = PolicyUse;

    // Check if we should Download instead.
    const String& content_disposition = response.httpHeaderField("Content-Disposition");
    // 如果设置了http头Content-Disposition, 设置下载的策略
    if (!content_disposition.isEmpty() &&
            TreatAsAttachment(content_disposition)) {
        // Server wants to override our normal policy.
        // Check to see if we are a sub frame (main frame has no owner element)
        if (m_frame->ownerElement() != 0)
            action = PolicyIgnore;
        else
            action = PolicyDownload;

        (policy->*func)(action);
        return;
    }

    // Ask if it can be handled internally.
    // 如果无法显示mime type的内容，选择下载流程
    if (!canShowMIMEType(response.mimeType())) {
        /* 什么是可以显示的mime type
           if (MIMETypeRegistry::isSupportedImageResourceMIMEType(mimeType) ||
                   MIMETypeRegistry::isSupportedNonImageMIMEType(mimeType) ||
                   MIMETypeRegistry::isSupportedJavaScriptMIMEType(mimeType) ||
                   (m_frame && m_frame->settings()
                   && m_frame->settings()->arePluginsEnabled()
                   && PluginDatabase::installedPlugins()->isMIMETypeRegistered(
                   mimeType)) ||
                   (DOMImplementation::isTextMIMEType(mimeType) &&
                   !mimeType.startsWith("text/vnd")) ||
                   DOMImplementation::isXMLMIMEType(mimeType)
                   || mimeType.contains("vnd.wap.wmlscript"))
               return true;
           return false;

         */
        // Check to see if we are a sub frame (main frame has no owner element)
        if (m_frame->ownerElement() != 0)
            action = PolicyIgnore;
        else
            action = PolicyDownload;
        (policy->*func)(action);
        return;
    }

    // A status code of 204 indicates no content change. Ignore the result.
    WebCore::DocumentLoader* docLoader = m_frame->loader()->activeDocumentLoader();
    if (docLoader->response().httpStatusCode() == 204)
        action = PolicyIgnore;

    (policy->*func)(action);
}           |
            |
            V
void PolicyChecker::continueAfterContentPolicy(PolicyAction policy)
{
    PolicyCallback callback = m_callback;
    m_callback.clear();
    callback.call(policy);
}               |
                | MainResourceLoader::didReceiveResponse  call back
                V
void MainResourceLoader::callContinueAfterContentPolicy(void* argument, PolicyAction policy)
    static_cast<MainResourceLoader*>(argument)->continueAfterContentPolicy(policy);
            |
            V
void MainResourceLoader::continueAfterContentPolicy(PolicyAction contentPolicy, const ResourceResponse& r)
{
    KURL url = request().url();
    const String& mimeType = r.mimeType();

    switch (contentPolicy) {
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
            // m_handle can be null, e.g. when loading a substitute resource from application cache.
            if (!m_handle) {
                receivedError(cannotShowURLError());
                return;
            }
            InspectorInstrumentation::continueWithPolicyDownload(m_frame.get(), documentLoader(), identifier(), r);
            frameLoader()->client()->download(m_handle.get(), request(), m_handle.get()->firstRequest(), r);

#if ENABLE(WML)
            LOG_WML();
            //WML may use same loader to load a go tag.
            if(frameLoader()->activeDocumentLoader() &&
                    frameLoader()->activeDocumentLoader()->frame() &&
                    frameLoader()->activeDocumentLoader()->frame()->document() &&
                    frameLoader()->activeDocumentLoader()->frame()->document()->isWMLDocument())
                return;
#endif

            // TODO: Jiang Rui, sometime the page is still in 
            // loading status after the download start, we should stop it
            // stopLoadingForPolicyChange();

            // It might have gone missing
            if (frameLoader())
                receivedError(interruptionForPolicyChangeError());
            return;

        case PolicyIgnore:
            InspectorInstrumentation::continueWithPolicyIgnore(m_frame.get(), documentLoader(), identifier(), r);
            stopLoadingForPolicyChange();
            return;

        default:
            ASSERT_NOT_REACHED();
    }

    RefPtr<MainResourceLoader> protect(this);
    if (r.isHTTP()) {
        int status = r.httpStatusCode();
        if (status < 200 || status >= 300) {
            bool hostedByObject = frameLoader()->isHostedByObjectElement();

            frameLoader()->handleFallbackContent();
            // object elements are no longer rendered after we fallback, so don't
            // keep trying to process data from their load

            if (hostedByObject)
                cancel();
        }
    }

    // we may have cancelled this load as part of switching to fallback content
    if (!reachedTerminalState())
        ResourceLoader::didReceiveResponse(r);

    if (frameLoader() && !frameLoader()->isStopping()) {
        if (m_substituteData.isValid()) {
            if (m_substituteData.content()->size())
                didReceiveData(m_substituteData.content()->data(), m_substituteData.content()->size(), m_substituteData.content()->size(), true);
            if (frameLoader() && !frameLoader()->isStopping())
                didFinishLoading(0);
        } else if (shouldLoadAsEmptyDocument(url) || frameLoader()->representationExistsForURLScheme(url.protocol()))
            didFinishLoading(0);
    }
}



