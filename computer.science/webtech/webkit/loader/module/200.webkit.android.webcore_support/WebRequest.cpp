/** 1. set url, ua, referer 
    2.create chromium net request 
    3. copy request content to chromium net request  */
WebRequest::WebRequest(WebUrlLoaderClient* loader, const WebResourceRequest& webResourceRequest)
    : m_urlLoader(loader)
      , m_url(webResourceRequest.url())
      , m_userAgent(webResourceRequest.userAgent())
      , m_referer(webResourceRequest.referrer())
      , m_loadState(Created) {
    GURL gurl(m_url);
    m_request = new net::URLRequest(gurl, this);
}

/** just set request context to chromium */
void WebRequest::setRequestContext(WebRequestContext* context) {
    m_cacheMode = context->getCacheMode();
    if (m_request)
        m_request->set_context(context);
}

/** invoke handleInterceptedURL || handleDataURL || m_request->Start() */
void WebRequest::start() {
    if (m_interceptResponse != NULL)
        return handleInterceptedURL();

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
}

/* 1. chromium cancel 2. finish() */
void WebRequest::cancel() {
    m_request->Cancel();
    finish(true);
}

/** 1. set pause flag 
    2. if resume, start invoking startReading() */
void WebRequest::pauseLoad(bool pause) {
    if (pause) {
        if (!m_isPaused)
            m_wantToPause = true;
    } else {
        m_wantToPause = false;
        if (m_isPaused) {
            m_isPaused = false;
            MessageLoop::current()->PostTask(FROM_HERE, m_runnableFactory.NewRunnableMethod(&WebRequest::startReading));
        }
    }
}

/** 1. callback didReceiveResponse 
    2. read data from m_interceptResponse 
    3. send data by callback didReceiveAndroidFileData
    4. finish() */
void WebRequest::handleInterceptedURL()
{
    OwnPtr<WebResponse> webResponse(new WebResponse(m_url, mimeType, 0, m_interceptResponse->encoding(), m_interceptResponse->status()));
    m_urlLoader->maybeCallOnMainThread(NewRunnableMethod(
                m_urlLoader.get(), &WebUrlLoaderClient::didReceiveResponse, webResponse.release()));

    do {
        // Read returns false on error and size of 0 on eof.
        if (!m_interceptResponse->readStream(data.get()) || data->size() == 0)
            break;

        m_loadState = GotData;
        m_urlLoader->maybeCallOnMainThread(NewRunnableMethod(
                    m_urlLoader.get(), &WebUrlLoaderClient::didReceiveAndroidFileData, data.release()));
    } while (true);

    finish(m_interceptResponse->status() == 200);
}

/** 1. callback didReceiveResponse 
    2. read data by chromium method
    3. send data by callback didReceiveDataUrl
    4. finish() */
void WebRequest::handleDataURL(GURL url) {
    if (net::DataURL::Parse(url, &mimeType, &charset, data.get())) {
        // PopulateURLResponse from chrome implementation
        // weburlloader_impl.cc
        m_loadState = Response;
        OwnPtr<WebResponse> webResponse(new WebResponse(url.spec(), mimeType, data->size(), charset, 200));
        m_urlLoader->maybeCallOnMainThread(NewRunnableMethod(
                    m_urlLoader.get(), &WebUrlLoaderClient::didReceiveResponse, webResponse.release()));

        if (!data->empty()) {
            m_loadState = GotData;
            m_urlLoader->maybeCallOnMainThread(NewRunnableMethod(
                        m_urlLoader.get(), &WebUrlLoaderClient::didReceiveDataUrl, data.release()));
        }
    }
    finish(true);
}

// After calling Start(), the delegate will receive an OnResponseStarted
// callback when the request has completed.  If an error occurred, the
// request->status() will be set.  On success, all redirects have been
// followed and the final response is beginning to arrive.  At this point,
// meta data about the response is available, including for example HTTP
// response headers if this is a request for a HTTP resource.
void WebRequest::OnResponseStarted(net::URLRequest* request)
{
    if (request && request->status().is_success()) {
        OwnPtr<WebResponse> webResponse(new WebResponse(request));
        m_urlLoader->maybeCallOnMainThread(NewRunnableMethod(
                    m_urlLoader.get(), &WebUrlLoaderClient::didReceiveResponse, webResponse.release()));

        // Start reading the response
        startReading();
    } else {
        finish(false);
    }
}

/** 1. read data from chromium 
    2. send it by callback didReceiveData */
void WebRequest::startReading() {
    if (m_wantToPause) {
        m_isPaused = true;
        return;
    }

    if (!read(&bytesRead)) {
        if (m_request && m_request->status().is_io_pending())
            return; // Wait for OnReadCompleted()
        return finish(false);
    }

    // bytesRead == 0 indicates finished
    if (!bytesRead)
        return finish(true);

    m_loadState = GotData;
    // Read ok, forward buffer to webcore
    m_urlLoader->maybeCallOnMainThread(NewRunnableMethod(m_urlLoader.get(), 
                &WebUrlLoaderClient::didReceiveData, m_networkBuffer, bytesRead));
    m_networkBuffer = 0;
    MessageLoop::current()->PostTask(FROM_HERE, m_runnableFactory.NewRunnableMethod(&WebRequest::startReading));
}

// This is called when there is data available

// Called when the a Read of the response body is completed after an
// IO_PENDING status from a Read() call.
// The data read is filled into the buffer which the caller passed
// to Read() previously.
//
// If an error occurred, request->status() will contain the error,
// and bytes read will be -1.
void WebRequest::OnReadCompleted(net::URLRequest* request, int bytesRead)
{
    if (request->status().is_success()) {
        m_loadState = GotData;
        m_urlLoader->maybeCallOnMainThread(NewRunnableMethod(
                    m_urlLoader.get(), &WebUrlLoaderClient::didReceiveData, m_networkBuffer, bytesRead));
        m_networkBuffer = 0;

        // Get the rest of the data
        startReading();
    } else {
        finish(false);
    }
}

/** 1. invoke didFinishLoading or didFail */
void WebRequest::finish(bool success) {
    if (success) {
        m_urlLoader->maybeCallOnMainThread(NewRunnableMethod(
                    m_urlLoader.get(), &WebUrlLoaderClient::didFinishLoading));
    } else {
        if (m_interceptResponse == NULL) {
            OwnPtr<WebResponse> webResponse(new WebResponse(m_request.get()));
            m_urlLoader->maybeCallOnMainThread(NewRunnableMethod(
                        m_urlLoader.get(), &WebUrlLoaderClient::didFail, webResponse.release()));
        } else {
            OwnPtr<WebResponse> webResponse(new WebResponse(m_url, m_interceptResponse->mimeType(), 0,
                        m_interceptResponse->encoding(), m_interceptResponse->status()));
            m_urlLoader->maybeCallOnMainThread(NewRunnableMethod(
                        m_urlLoader.get(), &WebUrlLoaderClient::didFail, webResponse.release()));
        }
    }
}


