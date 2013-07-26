namespace WebCore {

// 取消所有ResourceLoader的加载
static void cancelAll(const ResourceLoaderSet& loaders)
    Vector<RefPtr<ResourceLoader> > loadersCopy;
    copyToVector(loaders, loadersCopy);
    size_t size = loadersCopy.size();
    for (size_t i = 0; i < size; ++i)
        loadersCopy[i]->cancel();

// 设置所有ResourceLoader的的defer
static void setAllDefersLoading(const ResourceLoaderSet& loaders, bool defers)
    Vector<RefPtr<ResourceLoader> > loadersCopy;
    copyToVector(loaders, loadersCopy);
    size_t size = loadersCopy.size();
    for (size_t i = 0; i < size; ++i)
        loadersCopy[i]->setDefersLoading(defers);

DocumentLoader::DocumentLoader(const ResourceRequest& req, const SubstituteData& substituteData)
    : m_deferMainResourceDataLoad(true)
    , m_frame(0)
    , m_writer(m_frame)
    , m_originalRequest(req)
    , m_substituteData(substituteData)
    , m_originalRequestCopy(req)
    , m_request(req)
    , m_committed(false)
    , m_isStopping(false)
    , m_loading(false)
    , m_gotFirstByte(false)
    , m_primaryLoadComplete(false)
    , m_isClientRedirect(false)
    , m_wasOnloadHandled(false)
    , m_stopRecordingResponses(false)
    , m_substituteResourceDeliveryTimer(this, &DocumentLoader::substituteResourceDeliveryTimerFired)
    , m_didCreateGlobalHistoryEntry(false)

FrameLoader* DocumentLoader::frameLoader() const
    return m_frame->loader();

DocumentLoader::~DocumentLoader()
{
    if (m_iconLoadDecisionCallback)
        m_iconLoadDecisionCallback->invalidate();
    if (m_iconDataCallback)
        m_iconDataCallback->invalidate();


PassRefPtr<SharedBuffer> DocumentLoader::mainResourceData() const
    if (m_mainResourceData)
        return m_mainResourceData;
    if (m_mainResourceLoader)
        return m_mainResourceLoader->resourceData();

// 在页面内的navigation? 跳到另一个anchor
void DocumentLoader::replaceRequestURLForSameDocumentNavigation(const KURL& url)
    m_originalRequestCopy.setURL(url);
    m_request.setURL(url);


void DocumentLoader::setRequest(const ResourceRequest& req)
    KURL oldURL = m_request.url();
    m_request = req;

    if (!handlingUnreachableURL && !req.url().isNull() && oldURL != req.url())
        frameLoader()->didReceiveServerRedirectForProvisionalLoadForFrame();

// Cancels the data source's pending loads.  Conceptually, a data source only loads
// one document at a time, but one document may have many related resources. 
// stopLoading will stop all loads initiated by the data source, 
// but not loads initiated by child frames' data sources -- that's the WebFrame's job.
void DocumentLoader::stopLoading()
    if (m_committed) {
        // Attempt to stop the frame if the document loader is loading, or if it is done loading but
        // still  parsing. Failure to do so can cause a world leak.
        Document* doc = m_frame->document();
        
        if (loading || doc->parsing())
            m_frame->loader()->stopLoading(UnloadEventPolicyNone);
    }

    // Always cancel multipart loaders
    cancelAll(m_multipartSubresourceLoaders);

    FrameLoader* frameLoader = DocumentLoader::frameLoader();
    if (m_mainResourceLoader)
        // Stop the main resource loader and let it send the cancelled message.
        m_mainResourceLoader->cancel();
    else if (!m_subresourceLoaders.isEmpty())
        // The main resource loader already finished loading. Set the cancelled error on the 
        // document and let the subresourceLoaders send individual cancelled messages below.
        setMainDocumentError(frameLoader->cancelledError(m_request));
    else
        // If there are no resource loaders, we need to manufacture a cancelled message.
        // (A back/forward navigation has no resource loaders because its resources are cached.)
        mainReceivedError(frameLoader->cancelledError(m_request), true);
    
    stopLoadingSubresources();
    stopLoadingPlugIns();
    

// 加载的状态机：
// 1. provisional data source
    // 发起加载到收到第一个byte之前
// 2. commitLoad data source
    // 收到第一个byte之后
void DocumentLoader::commitIfReady()
    // 当收到第一个字节时，provisional load就完成了
    if (m_gotFirstByte && !m_committed) {
        m_committed = true;
        frameLoader()->commitProvisionalLoad();

void DocumentLoader::finishedLoading()
    m_gotFirstByte = true;   
    commitIfReady();
    if (FrameLoader* loader = frameLoader()) {
        loader->finishedLoadingDocument(this);
        m_writer.end();

void DocumentLoader::commitLoad(const char* data, int length)
    commitIfReady();
    frameLoader->client()->committedLoad(this, data, length);


// 收到数据后, 添加到writer中去
void DocumentLoader::commitData(const char* bytes, int length)

    bool userChosen = true;
    String encoding = overrideEncoding();
    if (encoding.isNull()) {

        userChosen = false;
        encoding = response().textEncodingName();
        if(m_response.isOverrideEncoding()) {
            userChosen = true;
            m_overrideEncoding = encoding;
        }
    }
    m_writer.setEncoding(encoding, userChosen);
    m_writer.addData(bytes, length);

void DocumentLoader::receivedData(const char* data, int length)
    m_gotFirstByte = true;
    if (doesProgressiveLoad(m_response.mimeType()))
        commitLoad(data, length);

// FIXME: ??
void DocumentLoader::setupForReplaceByMIMEType(const String& newMIMEType)


void DocumentLoader::updateLoading()
    bool wasLoading = m_loading;
    setLoading(frameLoader()->isLoading());

    if (wasLoading && !m_loading)
        if (DOMWindow* window = m_frame->existingDOMWindow())
            window->finishedLoading();


void DocumentLoader::setFrame(Frame* frame)
    m_frame = frame;
    m_writer.setFrame(frame);
    attachToFrame();


void DocumentLoader::prepareForLoadStart()

void DocumentLoader::setPrimaryLoadComplete(bool flag)

bool DocumentLoader::isLoadingInAPISense() const

ArchiveResource* DocumentLoader::archiveResourceForURL(const KURL& url) const

PassRefPtr<ArchiveResource> DocumentLoader::mainResource() const
    const ResourceResponse& r = response();
    RefPtr<SharedBuffer> mainResourceBuffer = mainResourceData();
    if (!mainResourceBuffer)
        mainResourceBuffer = SharedBuffer::create();
        
    return ArchiveResource::create(mainResourceBuffer, r.url(), r.mimeType(), r.textEncodingName(), frame()->tree()->uniqueName());


PassRefPtr<ArchiveResource> DocumentLoader::subresource(const KURL& url) const
    
    CachedResource* resource = m_frame->document()->cachedResourceLoader()->cachedResource(url);
    if (!resource || !resource->isLoaded())
        return archiveResourceForURL(url);

    // FIXME: This has the side effect of making the resource non-purgeable.
    // It would be better if it didn't have this permanent effect.
    if (!resource->makePurgeable(false))
        return 0;

    RefPtr<SharedBuffer> data = resource->data();
    if (!data)
        return 0;

    return ArchiveResource::create(data.release(), url, resource->response());

// 从cached resource中查询subresources
void DocumentLoader::getSubresources(Vector<PassRefPtr<ArchiveResource> >& subresources) const

    Document* document = m_frame->document();

    const CachedResourceLoader::DocumentResourceMap& allResources = document->cachedResourceLoader()->allCachedResources();
    CachedResourceLoader::DocumentResourceMap::const_iterator end = allResources.end();
    for (CachedResourceLoader::DocumentResourceMap::const_iterator it = allResources.begin(); it != end; ++it) {
        RefPtr<ArchiveResource> subresource = this->subresource(KURL(ParsedURLString, it->second->url()));
        if (subresource)
            subresources.append(subresource.release());

    return;

// 延时提交substituteResource
void DocumentLoader::substituteResourceDeliveryTimerFired(
        Timer<DocumentLoader>*)
    SubstituteResourceMap copy;
    copy.swap(m_pendingSubstituteResources);

    SubstituteResourceMap::const_iterator end = copy.end();
    for (SubstituteResourceMap::const_iterator it = copy.begin(); it != end; ++it) {
        RefPtr<ResourceLoader> loader = it->first;
        SubstituteResource* resource = it->second.get();
        
        if (resource) {
            SharedBuffer* data = resource->data();
        
            loader->didReceiveResponse(resource->response());
            loader->didReceiveData(data->data(), data->size(), data->size(), true);
            loader->didFinishLoading(0);
        } else {
            // A null resource means that we should fail the load.
            // FIXME: Maybe we should use another error here - something like "not in cache".
            loader->didFail(loader->cannotShowURLError());


void DocumentLoader::cancelPendingSubstituteLoad(ResourceLoader* loader)
    m_pendingSubstituteResources.remove(loader);
    if (m_pendingSubstituteResources.isEmpty())
        m_substituteResourceDeliveryTimer.stop();


void DocumentLoader::addResponse(const ResourceResponse& r)
        m_responses.append(r);


void DocumentLoader::setTitle(const StringWithDirection& title)
    frameLoader()->willChangeTitle(this);
    m_pageTitle = title;
    frameLoader()->didChangeTitle(this);

void DocumentLoader::setIconURL(const String& iconURL)
        m_pageIconURL = iconURL;
        frameLoader()->didChangeIcons(this);


KURL DocumentLoader::urlForHistory() const
    // Return the URL to be used for history and B/F list.
    // Returns nil for WebDataProtocol URLs that aren't alternates 
    // for unreachable URLs, because these can't be stored in history.
    return m_originalRequestCopy.url();

const KURL& DocumentLoader::originalURL() const
    return m_originalRequestCopy.url();

const KURL& DocumentLoader::requestURL() const
    return request().url();

const KURL& DocumentLoader::responseURL() const
    return m_response.url();

const String& DocumentLoader::responseMIMEType() const
    return m_response.mimeType();

const KURL& DocumentLoader::unreachableURL() const
    return m_substituteData.failingURL();

void DocumentLoader::setDefersLoading(bool defers)
    if (m_mainResourceLoader)
        m_mainResourceLoader->setDefersLoading(defers);
    setAllDefersLoading(m_subresourceLoaders, defers);
    setAllDefersLoading(m_plugInStreamLoaders, defers);
    if (!defers)
        deliverSubstituteResourcesAfterDelay();

void DocumentLoader::stopLoadingPlugIns()
    cancelAll(m_plugInStreamLoaders);

void DocumentLoader::stopLoadingSubresources()
    cancelAll(m_subresourceLoaders);

void DocumentLoader::addSubresourceLoader(ResourceLoader* loader)
    m_subresourceLoaders.add(loader);
    setLoading(true);

void DocumentLoader::removeSubresourceLoader(ResourceLoader* loader)
    m_subresourceLoaders.remove(loader);
    updateLoading();
    if (Frame* frame = m_frame)
        frame->loader()->checkLoadComplete();

bool DocumentLoader::isLoadingMainResource() const
    return !!m_mainResourceLoader;

bool DocumentLoader::isLoadingSubresources() const
    return !m_subresourceLoaders.isEmpty();

bool DocumentLoader::isLoadingMultipartContent() const
    return m_mainResourceLoader && 
        m_mainResourceLoader->isLoadingMultipartContent();

bool DocumentLoader::startLoadingMainResource(unsigned long identifier)
    // 创建MainResourceLoader
    m_mainResourceLoader = MainResourceLoader::create(m_frame);
    m_mainResourceLoader->setIdentifier(identifier);

    frameLoader()->addExtraFieldsToMainResourceRequest(m_request);

    m_mainResourceLoader->load(m_request, m_substituteData)
    return true;


void DocumentLoader::cancelMainResourceLoad(const ResourceError& error)
    m_mainResourceLoader->cancel(error);

void DocumentLoader::subresourceLoaderFinishedLoadingOnePart(ResourceLoader* loader)
    m_multipartSubresourceLoaders.add(loader);
    m_subresourceLoaders.remove(loader);
    updateLoading();
    if (Frame* frame = m_frame)
        frame->loader()->checkLoadComplete();    


void DocumentLoader::transferLoadingResourcesFromPage(Page* oldPage)
    FrameLoader* loader = frameLoader();

    const ResourceRequest& request = originalRequest();
    if (isLoadingMainResource()) {
        loader->dispatchTransferLoadingResourceFromPage(
            m_mainResourceLoader->identifier(), this, request, oldPage);


    if (isLoadingSubresources()) {
        ResourceLoaderSet::const_iterator it = m_subresourceLoaders.begin();
        ResourceLoaderSet::const_iterator end = m_subresourceLoaders.end();
        for (; it != end; ++it) {
            loader->dispatchTransferLoadingResourceFromPage(
                (*it)->identifier(), this, request, oldPage);

void DocumentLoader::iconLoadDecisionAvailable()
    if (m_frame)
        m_frame->loader()->iconLoadDecisionReceived(
                iconDatabase().synchronousLoadDecisionForIconURL(
                KURL(frameLoader()->iconURL()), this));

static void iconLoadDecisionCallback(IconLoadDecision decision, void* context)
    static_cast<DocumentLoader*>(context)->continueIconLoadWithDecision(decision);

void DocumentLoader::getIconLoadDecisionForIconURL(const String& urlString)
{
    if (m_iconLoadDecisionCallback)
        m_iconLoadDecisionCallback->invalidate();
    m_iconLoadDecisionCallback = IconLoadDecisionCallback::create(this, iconLoadDecisionCallback);
    iconDatabase().loadDecisionForIconURL(urlString, m_iconLoadDecisionCallback);
}

void DocumentLoader::continueIconLoadWithDecision(IconLoadDecision decision)
{
    ASSERT(m_iconLoadDecisionCallback);
    m_iconLoadDecisionCallback = 0;
    if (m_frame)
        m_frame->loader()->continueIconLoadWithDecision(decision);
}

void DocumentLoader::getIconDataForIconURL(const String& urlString)
{   
    if (m_iconDataCallback)
        m_iconDataCallback->invalidate();
    m_iconDataCallback = IconDataCallback::create(this, iconDataCallback);
    iconDatabase().iconDataForIconURL(urlString, m_iconDataCallback);
}

} // namespace WebCore
