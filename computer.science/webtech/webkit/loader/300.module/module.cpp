
--------------------------------------------------------------------------------
资源加载层
--------------------------------------------------------------------------------
{
    /** 负责main resource, sub resource的加载，管理 */
    FrameLoader.cpp
    SubframeLoader.cpp
    DocumentLoader.cpp

    ResourceLoader.cpp
    MainResourceLoader.cpp
    SubresourceLoader.cpp
    icon/IconLoader.cpp

    ImageLoader.cpp
    PingLoader.cpp

    FrameLoaderStateMachine.cpp
    cache/CachedResourceLoader.cpp
    WorkerThreadableLoader.cpp
    DocumentThreadableLoader.cpp
    ThreadableLoader.cpp
    NetscapePlugInStreamLoader.cpp
}
--------------------------------------------------------------------------------
            |                          ^
            | 资源加载请求             |  网络响应，数据，进度，状态汇报
            V                          |
--------------------------------------------------------------------------------

--------------------------------------------------------------------------------
接口类
--------------------------------------------------------------------------------
{ 
    /* HTTP 抽象层 
        1. 提供加载http资源的接口
        2. 提供资源加载进度，状态（完成，失败）等等的回调接口
     */

    ./WebCore/platform/network/ResourceHandle.cpp
        // 1. 加载的接口, 被ResourceLoader, MainResourceLoader, SubresourceLoader, 等等调用
        // 2. 
        ./WebCore/platform/network/android/ResourceHandleAndroid.cpp

    ./WebCore/platform/network/ResourceHandleClient.h

    ./WebCore/platform/network/ResourceRequestBase.cpp
    ./WebCore/platform/network/ResourceResponseBase.cpp
    ./WebCore/platform/network/ResourceErrorBase.cpp
    ./WebCore/platform/network/android/ResourceRequestAndroid.cpp

    ./WebCore/platform/network/NetworkStateNotifier.cpp
}

--------------------------------------------------------------------------------
            |                          ^
            | 资源加载请求             |  网络响应，数据，进度，状态汇报
            V                          |
--------------------------------------------------------------------------------
{
    /* 网络抽象层, android network layer
        1. 封装了chrome_net
        2. 提供加载资源的接口
        3. 提供资源加载进度，状态（完成，失败）等等的回调接口
     */
    ./WebKit/android/WebCoreSupport/WebRequest.cpp
    ./WebKit/android/WebCoreSupport/WebResponse.cpp
    ./WebKit/android/WebCoreSupport/WebResourceRequest.cpp
    ./WebKit/android/WebCoreSupport/WebUrlLoader.cpp
    ./WebKit/android/WebCoreSupport/WebRequestContext.cpp
    ./WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp
    ./WebKit/android/WebCoreSupport/WebCache.cpp
    ./WebKit/android/WebCoreSupport/WebViewClientError.cpp
}

WebCore/loader/PolicyCallback.cpp
WebCore/loader/ResourceLoadNotifier.cpp
WebCore/loader/DocumentThreadableLoader.cpp
WebCore/loader/ThreadableLoader.cpp
WebCore/loader/FrameLoader.cpp
WebCore/loader/FrameLoaderStateMachine.cpp
WebCore/loader/PolicyChecker.cpp
WebCore/loader/DocumentLoader.cpp
WebCore/loader/DocumentWriter.cpp
WebCore/loader/TextResourceDecoder.cpp

WebCore/loader/ImageLoader.cpp
WebCore/loader/NetscapePlugInStreamLoader.cpp
WebCore/loader/SubframeLoader.cpp
WebCore/loader/WorkerThreadableLoader.cpp

WebCore/loader/NavigationAction.cpp
WebCore/loader/NavigationScheduler.cpp

WebCore/loader/ResourceLoader.cpp
WebCore/loader/SubresourceLoader.cpp
WebCore/loader/MainResourceLoader.cpp
        
WebCore/loader/ResourceLoadScheduler.cpp


WebCore/loader/FormState.cpp
WebCore/loader/HistoryController.cpp
WebCore/loader/CrossOriginAccessControl.cpp
WebCore/loader/CrossOriginPreflightResultCache.cpp
WebCore/loader/FTPDirectoryParser.cpp
WebCore/loader/SinkDocument.cpp
WebCore/loader/FormSubmission.cpp
WebCore/loader/ProgressTracker.cpp
WebCore/loader/PlaceholderDocument.cpp

WebCore/loader/cache/CachedResourceRequest.cpp
WebCore/loader/cache/CachedResourceLoader.cpp
WebCore/loader/cache/CachedXSLStyleSheet.cpp
WebCore/loader/cache/CachedFont.cpp
WebCore/loader/cache/CachedResource.cpp
WebCore/loader/cache/CachedResourceClientWalker.cpp
WebCore/loader/cache/CachedCSSStyleSheet.cpp
WebCore/loader/cache/CachedScript.cpp
WebCore/loader/cache/MemoryCache.cpp
WebCore/loader/cache/CachedResourceHandle.cpp
WebCore/loader/cache/CachedImage.cpp

WebCore/loader/icon/IconRecord.cpp
WebCore/loader/icon/IconDatabaseBase.cpp
WebCore/loader/icon/IconLoader.cpp
WebCore/loader/icon/PageURLRecord.cpp
WebCore/loader/icon/IconDatabase.cpp

WebCore/loader/appcache/ApplicationCacheStorage.cpp
WebCore/loader/appcache/DOMApplicationCache.cpp
WebCore/loader/appcache/ApplicationCacheHost.cpp
WebCore/loader/appcache/DOMApplicationCache.idl
WebCore/loader/appcache/ApplicationCacheGroup.cpp
WebCore/loader/appcache/ApplicationCacheResource.cpp
WebCore/loader/appcache/ApplicationCache.cpp
WebCore/loader/appcache/ManifestParser.cpp


WebCore/loader/archive/ArchiveResource.cpp
WebCore/loader/archive/ArchiveFactory.cpp
WebCore/loader/archive/android/WebArchiveAndroid.cpp
WebCore/loader/archive/ArchiveResourceCollection.cpp



WebCore/loader/PingLoader.cpp
