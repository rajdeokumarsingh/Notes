./log.android.network.txt

// Working class:
    ./WebRequest.cpp
        1. running in a io thread

        2. upper layer is WebUrlLoaderClient
            receive start, cancel, pause command from WebUrlLoaderClient and send it to libchromium_net
                . by a net::URLRequest
                . set http headers like referer, method etc, and load floag to the request

            receive data from libchromium_net and sent data/status callback to WebUrlLoaderClient
                didReceiveResponse, didReceiveData, didFinishLoading, didFail

        3. can handle intercepted request (by browser), data url and normal url

        4. handle redirect 
            by invoke WebUrlLoaderClient::willSendRequest
    
        5. handle auth require, ssl certificate error, certificate request


        6. maintain 
            internal loading status
                enum LoadState { Created, Started, Response, GotData, Cancelled, Finished, Deleted };

            request url, user agent, referer

        7. set load flag to libchromium_net
            LOAD_CACHE_ELSE_NETWORK, LOAD_NO_CACHE, LOAD_CACHE_ONLY

    ./WebUrlLoaderClient.cpp
        1. running in WebCore main thread

        2. lower layer: WebRequest
            send command: start, cancel, pause, set auth, cancel auth, proceed ssl cert error, cancel ssl cert error

        3. upper layer is WebUrlLoader, ResourceHandle, and ResourceHandleClient
            receive data from WebRequest and sent data/status callback to upper layer
                didReceiveResponse, didReceiveData, didFinishLoading, didFail

        4. create an maintain a base::Thread, which is for running of WebRequest
        
        5. implement synchronized and asynchronized loading
        6. can handle http get/head/post

    ./WebUrlLoader.cpp
        1. just a wrapper of WebUrlLoaderClient
            send command: start, cancel, pause, download file to WebUrlLoaderClient
            no callback to this class

        2. trigger save password logic in WebFrame::maybeSavePassword

     command flow:
        WebUrlLoader ---> WebUrlLoaderClient ---> WebRequest ---> libchromium_net

     data/callback flow:
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


