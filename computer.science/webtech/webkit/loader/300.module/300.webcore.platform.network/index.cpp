// Control class
ResourceHandleAndroid.cpp
    extends ResourceHandle
    just a wrapper of ResourceLoaderAndroid
    send start, cancel, pause to platform network layer

    lower layer: WebKit.android.WebCoreSupport

ResourceHandle.cpp
    lower layer: WebKit.android.WebCoreSupport

    Combine 
        1. a platform handler, like ResourceHandleAndroid
            to send start, cancel, pause to platform loader layer

        2. a callback client, ResourceHandleClient
            receive data and status callbacks from android network layer

        3. has a time to schedule failure
        4. handle defer loading
        5. handle private browsing
            by a privateBrowsingStorageSession()
        
        implement by several platfroms like Android, Qt, ...
                external/webkit/Source/WebCore/platform/network/android/ResourceHandleAndroid.cpp
                external/webkit/Source/WebCore/platform/network/cf/ResourceHandleCFNet.cpp
                external/webkit/Source/WebCore/platform/network/curl/ResourceHandleCurl.cpp
                external/webkit/Source/WebCore/platform/network/qt/ResourceHandleQt.cpp
                external/webkit/Source/WebCore/platform/network/soup/ResourceHandleSoup.cpp
                external/webkit/Source/WebCore/platform/network/win/ResourceHandleWin.cpp

ResourceHandleClient
    interface
        implement 
            virtual void willSendRequest(ResourceHandle*, ResourceRequest&, const ResourceResponse& /*redirectResponse*/) { }
            virtual void didSendData(ResourceHandle*, unsigned long long /*bytesSent*/, unsigned long long /*totalBytesToBeSent*/) { }

            virtual void didReceiveResponse(ResourceHandle*, const ResourceResponse&) { }
            virtual void didReceiveData(ResourceHandle*, const char*, int, int /*encodedDataLength*/) { }
            virtual void didReceiveCachedMetadata(ResourceHandle*, const char*, int) { }
            virtual void didFinishLoading(ResourceHandle*, double /*finishTime*/) { }
            virtual void didFail(ResourceHandle*, const ResourceError&) { }
                                                                 
// Util class
ResourceRequest.h
    extends ResourceRequestBase

./ResourceRequestBase.h
    encapsulate a http request
    wrapper of: 
        url, http method, http header fields, http body,
        load type, cache policy
 
ResourceResponse.h
    extends ResourceResponseBase

./ResourceResponseBase.h
    encapsulate a http response (not include data)

ResourceHandleInternal
    wrapper
        ResourceHandleClient* m_client;
        ResourceRequest m_firstRequest;
        String m_lastHTTPMethod;

        // Suggested credentials for the current redirection step.
        String m_user;
        String m_pass;

        Credential m_initialCredential;

        int status;
        bool m_defersLoading;
        bool m_shouldContentSniff;

        RefPtr<ResourceLoaderAndroid> m_loader;

ResourceErrorBase.cpp
    wrapper of: 
        String m_domain;
        int m_errorCode;
        String m_failingURL;
        String m_localizedDescription;


ResourceLoadInfo.h
    simple wrapper of:
        int httpStatusCode;
        String httpStatusText;
        long long encodedDataLength;
        HTTPHeaderMap requestHeaders;
        HTTPHeaderMap responseHeaders;
        String requestHeadersText;
        String responseHeadersText;


ResourceLoadTiming.h
    Loading time for performance profile
