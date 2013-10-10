// on main thread
WebUrlLoader::WebUrlLoader(WebFrame* webFrame, WebCore::ResourceHandle* resourceHandle, const WebCore::ResourceRequest& resourceRequest)
{
    m_loaderClient = new WebUrlLoaderClient(webFrame, resourceHandle, resourceRequest);
}

PassRefPtr<WebUrlLoader> WebUrlLoader::start(FrameLoaderClient* client, WebCore::ResourceHandle* resourceHandle,
        const WebCore::ResourceRequest& resourceRequest, bool isMainResource, bool isMainFrame, bool isSync, WebRequestContext* context)
{
    RefPtr<WebUrlLoader> loader = WebUrlLoader::create(webFrame, resourceHandle, resourceRequest);
    loader->m_loaderClient->start(isMainResource, isMainFrame, isSync, context);
    return loader.release();
}

// on main thread
void WebUrlLoader::cancel() {
    m_loaderClient->cancel();
}

void WebUrlLoader::downloadFile() {
    m_loaderClient->downloadFile();
}

void WebUrlLoader::pauseLoad(bool pause) {
    m_loaderClient->pauseLoad(pause);
}

