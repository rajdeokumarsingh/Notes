
namespace WebCore {

#include "ResourceLoader.h"

#include "ApplicationCacheHost.h"
#include "DocumentLoader.h"
#include "FileStreamProxy.h"
#include "Frame.h"
#include "FrameLoader.h"
#include "FrameLoaderClient.h"
#include "InspectorInstrumentation.h"
#include "Page.h"
#include "ProgressTracker.h"
#include "ResourceError.h"
#include "ResourceHandle.h"
#include "ResourceLoadScheduler.h"
#include "Settings.h"
#include "SharedBuffer.h"


ResourceLoader::ResourceLoader(Frame* frame, bool sendResourceLoadCallbacks, bool shouldContentSniff)
    : m_frame(frame)
    , m_documentLoader(frame->loader()->activeDocumentLoader())
    , m_identifier(0)
    , m_reachedTerminalState(false)
    , m_cancelled(false)
    , m_calledDidFinishLoad(false)
    , m_sendResourceLoadCallbacks(sendResourceLoadCallbacks)
    , m_shouldContentSniff(shouldContentSniff)
    , m_shouldBufferData(true)
    , m_defersLoading(frame->page()->defersLoading())

void ResourceLoader::releaseResources()
    m_frame = 0;
    m_documentLoader = 0;
    
    resourceLoadScheduler()->remove(this);
    if (m_handle) {
        // Clear out the ResourceHandle's client so that it doesn't try to call
        // us back after we release it, unless it has been replaced by someone else.
        if (m_handle->client() == this)
            m_handle->setClient(0);
        m_handle = 0;

    m_resourceData = 0;
    m_deferredRequest = ResourceRequest();


// 复制并保存ResourceRequest
bool ResourceLoader::init(const ResourceRequest& r)
    ResourceRequest clientRequest(r);
    willSendRequest(clientRequest, ResourceResponse());
    m_request = clientRequest;
    return true;

// 开始加载
// 1. 创建一个ResourceHandle
void ResourceLoader::start()
    if (m_defersLoading) {
        m_deferredRequest = m_request;
        return;

    if (!m_reachedTerminalState)
        m_handle = ResourceHandle::create(
                m_frame->loader()->networkingContext(), 
                m_request, this, m_defersLoading, m_shouldContentSniff);
        {
            // 创建一个新的ResourceHandle, 并开始加载
            RefPtr<ResourceHandle> newHandle(adoptRef(
                        new ResourceHandle(request, client, 
                        defersLoading, shouldContentSniff)));

            if (newHandle->start(context))
                return newHandle.release();
        }

// 设置推迟加载
void ResourceLoader::setDefersLoading(bool defers)
    m_defersLoading = defers;
    if (m_handle)
        m_handle->setDefersLoading(defers);

    if (!defers && !m_deferredRequest.isNull()) {
        m_request = m_deferredRequest;
        m_deferredRequest = ResourceRequest();
        start();


#if PLATFORM(ANDROID)
// TODO: This needs upstreaming to WebKit.
void ResourceLoader::pauseLoad(bool pause)
    if (m_handle)
        m_handle->pauseLoad(pause);
#endif

// Frame-->FrameLoader
FrameLoader* ResourceLoader::frameLoader() const
    return m_frame->loader();

// 是否应该缓存数据
void ResourceLoader::setShouldBufferData(bool shouldBufferData)
    m_shouldBufferData = shouldBufferData; 

    // Reset any already buffered data
    if (!m_shouldBufferData)
        m_resourceData = 0;

// 如果支持缓存，则将网络数据追加到缓存中
void ResourceLoader::addData(const char* data, int length, bool allAtOnce)
    if (!m_shouldBufferData)
        return;

    if (allAtOnce) {
        m_resourceData = SharedBuffer::create(data, length);
        return;
        
    if (ResourceHandle::supportsBufferedData()) {
        // Buffer data only if the connection has handed us the data because is has stopped buffering it.
        if (m_resourceData)
            m_resourceData->append(data, length);
    } else {
        if (!m_resourceData)
            m_resourceData = SharedBuffer::create(data, length);
        else
            m_resourceData->append(data, length);

// 清空缓存
void ResourceLoader::clearResourceData()
    if (m_resourceData)
        m_resourceData->clear();


// ?? 发送request前需要做一些准备工作
// 1. 保存reqeust
// 2. 创建一个m_identifier
void ResourceLoader::willSendRequest(ResourceRequest& request, 
    const ResourceResponse& redirectResponse)

    if (m_sendResourceLoadCallbacks) {
        if (!m_identifier) {
            m_identifier = m_frame->page()->progress()->createUniqueIdentifier();
            frameLoader()->notifier()->assignIdentifierToInitialRequest(
                    m_identifier, documentLoader(), request);

        frameLoader()->notifier()->willSendRequest(
                this, request, redirectResponse);

    if (!redirectResponse.isNull())
        resourceLoadScheduler()->crossOriginRedirectReceived(
                this, request.url());

    m_request = request;


// 1. 保存response
// 2. 调用FrameLoader中的callback
void ResourceLoader::didReceiveResponse(const ResourceResponse& r)
    m_response = r;

    if (FormData* data = m_request.httpBody())
        data->removeGeneratedFilesIfNeeded();
        
    if (m_sendResourceLoadCallbacks)
        frameLoader()->notifier()->didReceiveResponse(this, m_response);


// 1. addData
// 2. 调用FrameLoader中的callback
void ResourceLoader::didReceiveData(const char* data, int length, 
        long long encodedDataLength, bool allAtOnce)

    addData(data, length, allAtOnce);
    if (m_sendResourceLoadCallbacks && m_frame)
        frameLoader()->notifier()->didReceiveData(this, data, length, 
                static_cast<int>(encodedDataLength));
}

// 重新创建数据缓存
void ResourceLoader::willStopBufferingData(const char* data, int length)
    if (!m_shouldBufferData)
        return;
    m_resourceData = SharedBuffer::create(data, length);


// 1. didFinishLoadingOnePart
// 2. 是否资源
void ResourceLoader::didFinishLoading(double finishTime)
    if (m_cancelled)
        return;

    didFinishLoadingOnePart(finishTime);
    releaseResources();

// 1. 设置标志位
// 2. 调用FrameLoader中的callback
void ResourceLoader::didFinishLoadingOnePart(double finishTime)
    if (m_cancelled)
        return;
    if (m_calledDidFinishLoad)
        return;

    m_calledDidFinishLoad = true;
    if (m_sendResourceLoadCallbacks)
        frameLoader()->notifier()->didFinishLoad(this, finishTime);

// 加载失败
// 1. 调用FrameLoader中的callback
// 2. 释放资源
void ResourceLoader::didFail(const ResourceError& error)
    if (m_cancelled)
        return;

    if (FormData* data = m_request.httpBody())
        data->removeGeneratedFilesIfNeeded();

    if (m_sendResourceLoadCallbacks && !m_calledDidFinishLoad)
        frameLoader()->notifier()->didFailToLoad(this, error);

    releaseResources();

// 1. 设置标志
// 2. 调用网络层的clearAuthentication(), cancel
// 3. 释放网络成ResourceHandle
// 4. documentLoader cancelPendingSubstituteLoad
// 5. 调用FrameLoader中的callback
void ResourceLoader::didCancel(const ResourceError& error)
{
    if (FormData* data = m_request.httpBody())
        data->removeGeneratedFilesIfNeeded();

    m_cancelled = true;
    
    if (m_handle)
        m_handle->clearAuthentication();

    m_documentLoader->cancelPendingSubstituteLoad(this);
    if (m_handle) {
        m_handle->cancel();
        m_handle = 0;
    }
    if (m_sendResourceLoadCallbacks && m_identifier && !m_calledDidFinishLoad)
        frameLoader()->notifier()->didFailToLoad(this, error);

    releaseResources();

void ResourceLoader::cancel()
    cancel(ResourceError());

void ResourceLoader::cancel(const ResourceError& error)
        didCancel(error);

const ResourceResponse& ResourceLoader::response() const
    return m_response;

ResourceError ResourceLoader::cancelledError()
    return frameLoader()->cancelledError(m_request);

ResourceError ResourceLoader::blockedError()
    return frameLoader()->blockedError(m_request);

ResourceError ResourceLoader::cannotShowURLError()
    return frameLoader()->cannotShowURLError(m_request);


// 1. 调用FrameLoader中的callback
void ResourceLoader::didReceiveAuthenticationChallenge(
        const AuthenticationChallenge& challenge)
    frameLoader()->notifier()->didReceiveAuthenticationChallenge(
            this, challenge);

// 1. 调用FrameLoader中的callback
void ResourceLoader::didCancelAuthenticationChallenge(
        const AuthenticationChallenge& challenge)
    frameLoader()->notifier()->didCancelAuthenticationChallenge(
            this, challenge);

void ResourceLoader::receivedCancellation(const AuthenticationChallenge&)
{
    cancel();
}

void ResourceLoader::willCacheResponse(ResourceHandle*, CacheStoragePolicy& policy)
    // When in private browsing mode, prevent caching to disk
    if (policy == StorageAllowed && m_frame->settings() 
            && m_frame->settings()->privateBrowsingEnabled())
        policy = StorageAllowedInMemoryOnly;    

}
