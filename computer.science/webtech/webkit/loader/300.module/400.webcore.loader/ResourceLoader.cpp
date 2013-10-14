/** 
   1. lower layer : WebCore.platform.network
        send command : start, cancel, pause to lower layer
            by ResourceHandle

        invoke ResourceHandle interface to request network
        receive data and status callback from ResourceHandle
        send callback to FrameLoader's notifier(ResourceLoadNotifier)

    2. has a SharedBuffer for network data

    3. reference to Frame, DocumentLoader

    4. could be added into ResourceLoadScheduler

    5. has an identifier

    6. handle first party for cookie
    7. handle defer loading
    8. clear auth and pending substitute load when didCancel
    9.handle auth challenge
    10.set content sniff
 
    two loading type:
        1. now
        2. defer
*/

/** store resource data from network */
PassRefPtr<SharedBuffer> ResourceLoader::resourceData() {
    // return m_resourceData or m_handle->bufferedData();
}

void ResourceLoader::releaseResources() {
    m_frame = 0;
    m_documentLoader = 0;

    m_identifier = 0;

    resourceLoadScheduler()->remove(this);

    m_handle->setClient(0);

    m_resourceData = 0;
    m_deferredRequest = ResourceRequest();
}

bool ResourceLoader::init(const ResourceRequest& r) {
    ResourceRequest clientRequest(r);

    willSendRequest(clientRequest, ResourceResponse());
    m_request = clientRequest;
    return true;
}

void ResourceLoader::start()
{
    m_handle = ResourceHandle::create(m_frame->loader()->networkingContext(), m_request, this, m_defersLoading, m_shouldContentSniff);
}

void ResourceLoader::setDefersLoading(bool defers)
{
    m_defersLoading = defers;
    if (m_handle)
        m_handle->setDefersLoading(defers);
    if (!defers && !m_deferredRequest.isNull()) {
        m_request = m_deferredRequest;
        m_deferredRequest = ResourceRequest();
        start();
    }
}

void ResourceLoader::pauseLoad(bool pause) {
    m_handle->pauseLoad(pause);
}

void ResourceLoader::addData(const char* data, int length, bool allAtOnce) {
    if (!m_resourceData)
        m_resourceData = SharedBuffer::create(data, length);
    else
        m_resourceData->append(data, length);
}

void ResourceLoader::clearResourceData() {
    if (m_resourceData)
        m_resourceData->clear();
}

void ResourceLoader::willSendRequest(ResourceRequest& request, const ResourceResponse& redirectResponse)
{
    if (!m_identifier) {
        m_identifier = m_frame->page()->progress()->createUniqueIdentifier();
        frameLoader()->notifier()->assignIdentifierToInitialRequest(m_identifier, documentLoader(), request);
    }
    frameLoader()->notifier()->willSendRequest(this, request, redirectResponse);
    m_request = request;
}

void ResourceLoader::didReceiveResponse(const ResourceResponse& r) {
    m_response = r;
    frameLoader()->notifier()->didReceiveResponse(this, m_response);
}

void ResourceLoader::didReceiveData(const char* data, int length, long long encodedDataLength, bool allAtOnce)
{
    addData(data, length, allAtOnce);
    frameLoader()->notifier()->didReceiveData(this, data, length, static_cast<int>(encodedDataLength));
}

void ResourceLoader::didFinishLoading(double finishTime)
{
    didFinishLoadingOnePart(finishTime);
    releaseResources();
}

void ResourceLoader::didFinishLoadingOnePart(double finishTime) {
        frameLoader()->notifier()->didFinishLoad(this, finishTime);
}

void ResourceLoader::didFail(const ResourceError& error)
{
    frameLoader()->notifier()->didFailToLoad(this, error);
    releaseResources();
}

void ResourceLoader::didCancel(const ResourceError& error) {
    if (m_handle)
        m_handle->clearAuthentication();

    m_documentLoader->cancelPendingSubstituteLoad(this);
    if (m_handle) {
        m_handle->cancel();
        m_handle = 0;
    }
    frameLoader()->notifier()->didFailToLoad(this, error);
    releaseResources();
}

void ResourceLoader::cancel(const ResourceError& error)
{
    if (!error.isNull())
        didCancel(error);
    else
        didCancel(cancelledError());
}

ResourceError ResourceLoader::cancelledError()
{
    return frameLoader()->cancelledError(m_request);
}

bool ResourceLoader::shouldUseCredentialStorage()
{
    RefPtr<ResourceLoader> protector(this);
    return frameLoader()->shouldUseCredentialStorage(this);
}

void ResourceLoader::didReceiveAuthenticationChallenge(const AuthenticationChallenge& challenge)
{
    // Protect this in this delegate method since the additional processing can do
    // anything including possibly derefing this; one example of this is Radar 3266216.
    RefPtr<ResourceLoader> protector(this);
    frameLoader()->notifier()->didReceiveAuthenticationChallenge(this, challenge);
}

void ResourceLoader::didCancelAuthenticationChallenge(const AuthenticationChallenge& challenge)
{
    // Protect this in this delegate method since the additional processing can do
    // anything including possibly derefing this; one example of this is Radar 3266216.
    RefPtr<ResourceLoader> protector(this);
    frameLoader()->notifier()->didCancelAuthenticationChallenge(this, challenge);
}

