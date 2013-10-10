// This class handles communication between the IO thread where loading happens
// and the webkit main thread.
class WebUrlLoaderClient : public base::RefCountedThreadSafe<WebUrlLoaderClient> {
    public:
        WebUrlLoaderClient(WebFrame*, WebCore::ResourceHandle*, const WebCore::ResourceRequest&);

        // Called from WebCore, will be forwarded to the IO thread
        bool start(bool isMainResource, bool isMainFrame, bool sync, WebRequestContext*);
        void cancel();
        void downloadFile();
        void pauseLoad(bool pause);

        // This is called from the IO thread, and dispatches the callback to the main thread.
        // (For asynchronous calls, we just delegate to WebKit's callOnMainThread.)
        void maybeCallOnMainThread(Task* task);

        // Called by WebRequest (using maybeCallOnMainThread), should be forwarded to WebCore.
        void didReceiveResponse(PassOwnPtr<WebResponse>);
        void didReceiveData(scoped_refptr<net::IOBuffer>, int size);
        void didReceiveDataUrl(PassOwnPtr<std::string>);
        void didReceiveAndroidFileData(PassOwnPtr<std::vector<char> >);
        void didFinishLoading();
        void didFail(PassOwnPtr<WebResponse>);
        void willSendRequest(PassOwnPtr<WebResponse>);

        // Handle to the chrome IO thread
        static base::Thread* ioThread();
};

// create a io thread
base::Thread* WebUrlLoaderClient::ioThread()
{
    static base::Thread* networkThread = 0;
    networkThread = new base::Thread("network");

    if (networkThread->IsRunning())
        return networkThread;

    base::Thread::Options options;
    options.message_loop_type = MessageLoop::TYPE_IO;
    if (!networkThread->StartWithOptions(options)) {
        delete networkThread;
        networkThread = 0;
    }
    return networkThread;
}

// create a WebRequest
WebUrlLoaderClient::WebUrlLoaderClient(WebFrame* webFrame, WebCore::ResourceHandle* resourceHandle, const WebCore::ResourceRequest& resourceRequest)
    : m_webFrame(webFrame)
    , m_resourceHandle(resourceHandle)
    , m_isMainResource(false)
    , m_isMainFrame(false)
    , m_isCertMimeType(false)
    , m_cancelling(false)
    , m_sync(false)
      , m_finished(false)
{
    WebResourceRequest webResourceRequest(resourceRequest, block);

    // Go to browser application
    UrlInterceptResponse* intercept = webFrame->shouldInterceptRequest(resourceRequest.url().string());
    if (intercept) {
        m_request = new WebRequest(this, webResourceRequest, intercept);
        return;
    }
    m_request = new WebRequest(this, webResourceRequest);
}

/** 1. invoke WebRequest::start() */
bool WebUrlLoaderClient::start(bool isMainResource, bool isMainFrame, bool sync, WebRequestContext* context)
{
    m_isMainResource = isMainResource;
    m_isMainFrame = isMainFrame;
    m_sync = sync;

    // Asynchronous start.
    // Important to set this before the thread starts so it has a reference and can't be deleted
    // before the task starts running on the IO thread.
    m_request->setRequestContext(context);
    thread->message_loop()->PostTask(FROM_HERE, NewRunnableMethod(m_request.get(), &WebRequest::start));
    return true;
}

/** 1. let the browser application to do download */
void WebUrlLoaderClient::downloadFile() {
    if (m_response) {
        std::string contentDisposition;
        m_response->getHeader("content-disposition", &contentDisposition);
        m_webFrame->downloadStart(m_response->getUrl(), m_request->getUserAgent(), 
                contentDisposition, m_response->getMimeType(), m_request->getReferer(), m_response->getExpectedSize());
    }
}

/** 1. invoke WebRequest::cancel() */
void WebUrlLoaderClient::cancel() {
    thread->message_loop()->PostTask(FROM_HERE, NewRunnableMethod(m_request.get(), &WebRequest::cancel));
}

/** 1. invoke WebRequest::pauseLoad() */
void WebUrlLoaderClient::pauseLoad(bool pause) {
    thread->message_loop()->PostTask(FROM_HERE, NewRunnableMethod(m_request.get(), &WebRequest::pauseLoad, pause));
}

// This is called from the IO thread, and dispatches the callback to the main thread.
void WebUrlLoaderClient::maybeCallOnMainThread(Task* task)
{
    if (m_sync) {
    } else {
        // Let WebKit handle it.
        callOnMainThread(RunTask, task);
    }
}

/** 1. callback didReceiveResponse 
    2. set cert to webFrame */
void WebUrlLoaderClient::didReceiveResponse(PassOwnPtr<WebResponse> webResponse)
{
    m_response = webResponse;
    m_resourceHandle->client()->didReceiveResponse(m_resourceHandle.get(), m_response->createResourceResponse());

    // Set the main page's certificate to WebView.
    if (m_isMainResource && m_isMainFrame) {
        const net::SSLInfo& ssl_info = m_response->getSslInfo();
        if (ssl_info.is_valid()) {
            std::vector<std::string> chain_bytes;
            ssl_info.cert->GetChainDEREncodedBytes(&chain_bytes);
            m_webFrame->setCertificate(chain_bytes[0]);
        }
    }
}

/** 1. callback didReceiveData */
void WebUrlLoaderClient::didReceiveData(scoped_refptr<net::IOBuffer> buf, int size)
{
    if (m_isMainResource && m_isCertMimeType) {
        m_webFrame->didReceiveData(buf->data(), size);
    }

    // didReceiveData will take a copy of the data
    if (m_resourceHandle && m_resourceHandle->client())
        m_resourceHandle->client()->didReceiveData(m_resourceHandle.get(), buf->data(), size, size);
}

// For data url's
void WebUrlLoaderClient::didReceiveDataUrl(PassOwnPtr<std::string> str)
{
    // didReceiveData will take a copy of the data
    m_resourceHandle->client()->didReceiveData(m_resourceHandle.get(), str->data(), str->size(), str->size());
}

// For special android files
void WebUrlLoaderClient::didReceiveAndroidFileData(PassOwnPtr<std::vector<char> > vector)
{
    // didReceiveData will take a copy of the data
    m_resourceHandle->client()->didReceiveData(m_resourceHandle.get(), vector->begin(), vector->size(), vector->size());
}

void WebUrlLoaderClient::didFail(PassOwnPtr<WebResponse> webResponse)
{
    if (isActive())
        m_resourceHandle->client()->didFail(m_resourceHandle.get(), webResponse->createResourceError());

    // Always finish a request, if not it will leak
    finish();
}

void WebUrlLoaderClient::willSendRequest(PassOwnPtr<WebResponse> webResponse)
{
    KURL url = webResponse->createKurl();
    OwnPtr<WebCore::ResourceRequest> resourceRequest(new WebCore::ResourceRequest(url));
    m_resourceHandle->client()->willSendRequest(m_resourceHandle.get(), *resourceRequest, webResponse->createResourceResponse());

    // Like Chrome, we only follow the redirect if WebKit left the URL unmodified.
    if (url == resourceRequest->url()) {
        ioThread()->message_loop()->PostTask(FROM_HERE, NewRunnableMethod(m_request.get(), &WebRequest::followDeferredRedirect));
    } else {
        cancel();
    }
}

/** 1. callback didFinishLoading */
void WebUrlLoaderClient::didFinishLoading()
{
    m_resourceHandle->client()->didFinishLoading(m_resourceHandle.get(), 0);
    if (m_isMainResource && m_isCertMimeType) {
        m_webFrame->didFinishLoading();
    }
    // Always finish a request, if not it will leak
    finish();
}

