./log.android.network.txt

// Working class:
    ./WebRequest.cpp
        1. read data from chromium network interface
        2. invoke callbacks
            didReceiveResponse
            didReceiveData
            didFinishLoading
            didFail

    ./WebUrlLoaderClient.cpp
        1. send start, cancel, pause, etc command to WebRequest in an IO thread
        2. Receive call backs, and call into ResourceHandle
            didReceiveResponse(PassOwnPtr<WebResponse>);
            didReceiveData(scoped_refptr<net::IOBuffer>, int size);
            didFinishLoading();
            didFail(PassOwnPtr<WebResponse>);

    ./WebUrlLoader.cpp
        just a wrapper of WebUrlLoaderClient

     command flow:
        WebUrlLoader ---> WebUrlLoaderClient ---> WebRequest ---> libchromium_net

     callback flow:
        libchromium_net ---> WebRequest ---> WebUrlLoaderClient ---> ResourceHandle->client()

// Util class:
    WebResponse.cpp
        just encapsulate 
            url, mimeType, expectedSize, encoding, http status code
       
    WebRequestContext.cpp
        just encapsulate 
            user agent, accept language, cacheMode, isPrivateBrowsing

    WebResourceRequest.cpp
        encapsulate 
            url, loadFlags, method, referrer, userAgent, requestHeaders

    WebViewClientError.cpp
        provider error number translation


