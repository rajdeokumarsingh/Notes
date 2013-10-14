WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp
cpp和java之间的桥梁
需要添加特殊的log

最好打开WebRequest中的LOG_REQUESTS

2140  2165 WebCore/loader/FrameLoader.cpp, resetMultipleFormSubmissionProtection, 1365
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, suspendPendingRequests, 256
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, requestCount, 812
2140  2165 WebCore/loader/FrameLoader.cpp, tellClientAboutPastMemoryCacheLoads, 4282
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resumePendingRequests, 266
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, hasRequests, 361
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, suspendPendingRequests, 256
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, requestCount, 812
2140  2165 WebCore/loader/FrameLoader.cpp, tellClientAboutPastMemoryCacheLoads, 4282
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resumePendingRequests, 266
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, hasRequests, 361


// WebCore/html/HTMLAnchorElement.cpp handleLinkClick()
2140  2165 WebCore/loader/FrameLoader.cpp, urlSelected, 323
2140  2165 WebCore/loader/FrameLoader.cpp, urlSelected, 335
创建一个FrameLoadRequest, 主要包括下面三个成员 {
    RefPtr<SecurityOrigin> m_requester;
    ResourceRequest m_resourceRequest;
    String m_frameName;
}

并设置http referrer, origin
2140  2165 WebCore/loader/FrameLoader.cpp, outgoingOrigin, 1243
2140  2165 WebCore/loader/FrameLoader.cpp, addHTTPOriginIfNeeded, 3348

检查security origin设置referrer
根据request中的cache type设置load type
根据request中的post/get调用loadPostRequest/loadUrl
2140  2165 WebCore/loader/FrameLoader.cpp, loadFrameRequest, 1582
2140  2165 WebCore/loader/FrameLoader.cpp, isFeedWithNestedProtocolInHTTPFamily, 1565

设置http referrer, origin
2140  2165 WebCore/loader/FrameLoader.cpp, loadURL, 1631

2140  2165 WebCore/loader/FrameLoader.cpp, addExtraFieldsToRequest, 3287
    集中设置了cache的策略
    设置了request中response fallback encoding, 为utf-8
    设置了first party cookie
    设置user agent
        2140  2165 WebCore/loader/FrameLoader.cpp, applyUserAgent, 3805
        2140  2165 WebCore/loader/FrameLoader.cpp, userAgent, 3190

2140  2165 WebCore/loader/FrameLoader.cpp, findFrameForNavigation, 3887
2140  2165 WebCore/loader/FrameLoader.cpp, shouldAllowNavigation, 2023

// 创建NavigationAction
2140  2165 WebCore/loader/NavigationAction.cpp, navigationType, 43
2140  2165 WebCore/loader/NavigationAction.cpp, NavigationAction, 105
2140  2165 WebCore/loader/FrameLoader.cpp, shouldTreatURLAsSameAsCurrent, 3860

// 判读是否是页面内部的跳转
2140  2165 WebCore/loader/FrameLoader.cpp, shouldScrollToAnchor, 3536
2140  2165 WebCore/loader/FrameLoader.cpp, shouldReload, 2571
2140  2165 WebCore/loader/FrameLoader.cpp, frameContainsWMLContent, 1385




2140  2165 WebCore/loader/FrameLoader.cpp, loadWithNavigationAction, 1749
// m_client->createDocumentLoader(request, SubstituteData());

// 创建DocumentLoader和DocumentWriter
2140  2165 WebCore/loader/DocumentWriter.cpp, DocumentWriter, 78
2140  2165 WebCore/loader/DocumentLoader.cpp, DocumentLoader, 115
    FrameLoader是一个总的控制模块
    DocumentLoader
        包括了一个MainResourceLoader和多个SubresourceLoader
        包括了一个DocumentWriter
            DocumentWriter是Loader模块和HTML模块之间的接口


2140  2165 WebCore/loader/FrameLoader.cpp, loadWithDocumentLoader, 1805
    设置policy checker的loadType

2140  2165 WebCore/loader/PolicyChecker.cpp, stopCheck, 143
2140  2165 WebCore/loader/PolicyCallback.cpp, clear, 67
2140  2165 WebCore/loader/PolicyCallback.cpp, clearRequest, 155
2140  2165 WebCore/loader/PolicyCallback.cpp, cancel, 166
2140  2165 WebCore/loader/PolicyCallback.cpp, clearRequest, 155
2140  2165 WebCore/loader/PolicyCallback.cpp, ~PolicyCallback, 59

2140  2165 WebCore/loader/FrameLoader.cpp, setPolicyDocumentLoader, 2240
2140  2165 WebCore/loader/DocumentLoader.cpp, setFrame, 503
2140  2165 WebCore/loader/DocumentLoader.cpp, attachToFrame, 517

2140  2165 WebCore/loader/PolicyChecker.cpp, checkNavigationPolicy, 71

2140  2165 WebCore/loader/PolicyCallback.cpp, set, 80

2140  2165 WebCore/loader/PolicyChecker.cpp, continueAfterNavigationPolicy, 175
2140  2165 WebCore/loader/PolicyCallback.cpp, clear, 67
2140  2165 WebCore/loader/PolicyCallback.cpp, clearRequest, 155
2140  2165 est, 876
2140  2140 Loading()
2140  2140 rlLoading(): http://218.206.177.209:8080/waptest/
2140  2140 orkUp(): true
2140  2140 
2140  2140 .206.177.209:8080/waptest/
2140  2140 
2140  2165 WebCore/loader/PolicyCallback.cpp, call, 130
2140  2165 WebCore/loader/FrameLoader.cpp, callContinueLoadAfterNavigationPolicy, 3561
2140  2165 WebCore/loader/FrameLoader.cpp, continueLoadAfterNavigationPolicy, 3635
2140  2165 WebCore/loader/FrameLoader.cpp, shouldClose, 3571
2140  2165 WebCore/loader/FrameLoader.cpp, fireBeforeUnloadEvent, 3607

// 马上要开始新的加载，停止所有loader
2140  2165 WebCore/loader/FrameLoader.cpp, stopAllLoaders, 2095
2140  2165 WebCore/loader/PolicyChecker.cpp, stopCheck, 143
2140  2165 WebCore/loader/PolicyCallback.cpp, clear, 67
2140  2165 WebCore/loader/PolicyCallback.cpp, clearRequest, 155
2140  2165 WebCore/loader/PolicyCallback.cpp, cancel, 166
2140  2165 WebCore/loader/PolicyCallback.cpp, clearRequest, 155
2140  2165 WebCore/loader/PolicyCallback.cpp, ~PolicyCallback, 59
2140  2165 WebCore/loader/FrameLoader.cpp, stopLoadingSubframes, 2085
2140  2165 WebCore/loader/DocumentLoader.cpp, stopLoading, 286
2140  2165 WebCore/loader/DocumentLoader.cpp, cancelAll, 69
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, stopLoadingInFrame, 339


2140  2165 WebCore/loader/FrameLoader.cpp, setProvisionalDocumentLoader, 2260
2140  2165 WebCore/loader/DocumentLoader.cpp, clearArchiveResources, 640
2140  2165 WebCore/loader/FrameLoader.cpp, setProvisionalDocumentLoader, 2260

// 设置状态为FrameStateProvisional
2140  2165 WebCore/loader/FrameLoader.cpp, setState, 2284
2140  2165 WebCore/loader/FrameLoader.cpp, provisionalLoadStarted, 1337
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, firstLayoutDone, 88
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, advanceTo, 97
2140  2165 WebCore/loader/NavigationScheduler.cpp, cancel, 503
2140  2165 592
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/DocumentLoader.cpp, url, 196
2140  2165 WebCore/loader/DocumentLoader.cpp, request, 178
2140  2165 WebCore/loader/FrameLoader.cpp, loadType, 2878
2140  2165 WebCore/loader/icon/IconDatabaseBase.cpp, iconDatabase, 70
2140  2165 WebCore/loader/icon/IconDatabase.cpp, synchronousIconForPageURL, 250
2140  2165 WebCore/loader/icon/IconDatabase.cpp, isOpen, 1004
2140  2165 WebCore/loader/icon/IconDatabase.cpp, getOrCreatePageURLRecord, 1054
2140  2165 WebKit/android/WebCoreSupport/ChromeClientAndroid.cpp, onMainFrameLoadStarted, 771

// setPolicyDocumentLoader(0);
2140  2165 WebCore/loader/FrameLoader.cpp, setPolicyDocumentLoader, 2240

// 如果在page cache中
2140  2165 WebCore/loader/FrameLoader.cpp, isBackForwardLoadType, 152
if (isBackForwardLoadType(type) && history()->provisionalItem()->isInPageCache()) {
    loadProvisionalItemFromCachedPage();

2140  2165 WebCore/loader/FrameLoader.cpp, continueLoadAfterWillSubmitForm, 3029

2140  2165 WebCore/loader/DocumentLoader.cpp, prepareForLoadStart, 539
2140  2165 WebCore/loader/DocumentLoader.cpp, setPrimaryLoadComplete, 555
2140  2165 WebCore/loader/DocumentLoader.cpp, clearErrors, 254
2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123
2140  2165 WebCore/loader/FrameLoader.cpp, prepareForLoadStart, 1530
2140  2165 WebCore/loader/ProgressTracker.cpp, progressStarted, 126
2140  2165 WebCore/loader/ProgressTracker.cpp, reset, 107
2140  2165 WebCore/loader/ProgressTracker.cpp, estimatedProgress, 98
2140  2165 786
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/DocumentLoader.cpp, isLoadingMainResource, 1037
2140  2165 WebCore/loader/ProgressTracker.cpp, createUniqueIdentifier, 305
2140  2165 WebCore/loader/DocumentLoader.cpp, originalRequest, 160
2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, assignIdentifierToInitialRequest, 141


// 创建MainResourceLoader, MainResourceLoader会有一个特定的id
2140  2165 WebCore/loader/DocumentLoader.cpp, startLoadingMainResource, 1073
2140  2165 WebCore/loader/MainResourceLoader.cpp, create, 90
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
// 基类ResourceLoader构造函数
2140  2165 WebCore/loader/ResourceLoader.cpp, ResourceLoader, 81
2140  2165 WebCore/loader/MainResourceLoader.cpp, MainResourceLoader, 74

2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123
2140  2165 WebCore/loader/FrameLoader.cpp, addExtraFieldsToMainResourceRequest, 3278
2140  2165 WebCore/loader/FrameLoader.cpp, addExtraFieldsToRequest, 3287
2140  2165 WebCore/loader/FrameLoader.cpp, applyUserAgent, 3805
2140  2165 WebCore/loader/FrameLoader.cpp, userAgent, 3190
2140  2165 WebCore/loader/FrameLoader.cpp, isBackForwardLoadType, 152
2140  2165 WebCore/loader/FrameLoader.cpp, addHTTPOriginIfNeeded, 3348
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/DocumentWriter.cpp, deprecatedFrameEncoding, 373
2140  2165 WebCore/loader/DocumentWriter.cpp, encoding, 321
2140  2165 (), from settings defaultTextEncodingName

// MainResourceLoader::load() 开始加载。 如果设置了defer loading, 就过一段时间开始加载
2140  2165 WebCore/loader/MainResourceLoader.cpp, load, 715
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, maybeLoadMainResource, 98
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, isApplicationCacheEnabled, 598
2140  2165 WebCore/loader/appcache/ApplicationCacheGroup.cpp, cacheForMainRequest, 113
2140  2165 WebCore/loader/appcache/ApplicationCache.cpp, requestIsHTTPOrHTTPSGet, 158
2140  2165 WebCore/loader/appcache/ApplicationCacheStorage.cpp, cacheStorage, 1712
2140  2165 WebCore/loader/appcache/ApplicationCacheStorage.cpp, cacheGroupForURL, 214
2140  2165 WebCore/loader/appcache/ApplicationCacheStorage.cpp, loadManifestHostHashes, 186
2140  2165 WebCore/loader/appcache/ApplicationCacheStorage.cpp, openDatabase, 686
2140  2165 WebCore/loader/appcache/ApplicationCacheStorage.cpp, urlHostHash, 97

// MainResourceLoader::loadNow() 
2140  2165 WebCore/loader/MainResourceLoader.cpp, loadNow, 677
2140  2165 WebCore/loader/MainResourceLoader.cpp, shouldLoadAsEmptyDocument, 291

2140  2165 WebCore/loader/MainResourceLoader.cpp, willSendRequest, 220
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/FrameLoader.cpp, isLoadingMainFrame, 2724
2140  2165 WebCore/loader/MainResourceLoader.cpp, isPostOrRedirectAfterPost, 193
2140  2165 WebCore/loader/ResourceLoader.cpp, willSendRequest, 287
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, willSendRequest, 78
2140  2165 WebCore/loader/FrameLoader.cpp, applyUserAgent, 3805
2140  2165 WebCore/loader/FrameLoader.cpp, userAgent, 3190
2140  2165 RL, 804
2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, dispatchWillSendRequest, 150

2140  2165 WebCore/loader/DocumentLoader.cpp, requestURL, 927
2140  2165 WebCore/loader/DocumentLoader.cpp, request, 178
2140  2165 WebCore/loader/DocumentLoader.cpp, setRequest, 215
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/MainResourceLoader.cpp, shouldLoadAsEmptyDocument, 291

// 创建ResourceLoadScheduler, 并添加main resource到scheduler
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, addMainResourceLoad, 129
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, hostForURL, 59
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, HostInformation, 301
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, addLoadInProgress, 329

2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/FrameLoader.cpp, representationExistsForURLScheme, 2743
2140  2165 WebCore/loader/FrameLoader.cpp, networkingContext, 4313

// 创建ResourceHandle
2140  2165 WebCore/platform/network/ResourceHandle.cpp, create, 66
2140  2165 WebCore/platform/network/ResourceHandle.cpp, shouldContentSniffURL, 193
2140  2165 WebCore/platform/network/Credential.cpp, Credential, 44
2140  2165 WebCore/platform/network/ProtectionSpace.cpp, ProtectionSpace, 50
2140  2165 WebCore/platform/network/Credential.cpp, Credential, 44
2140  2165 WebCore/platform/network/AuthenticationChallengeBase.cpp, AuthenticationChallengeBase, 40
2140  2165 WebCore/platform/network/ResourceHandle.cpp, ResourceHandle, 49

// 调用ResourceHandleAndroid::start()
2140  2165 WebCore/platform/network/android/ResourceHandleAndroid.cpp, start, 65
2140  2165 WebKit/android/WebCoreSupport/FrameNetworkingContextAndroid.cpp, mainResourceLoader, 52
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127
2140  2165 WebKit/android/WebCoreSupport/FrameNetworkingContextAndroid.cpp, frameLoaderClient, 61

// 调用ResourceLoaderAndroid::start()
2140  2165 WebKit/android/WebCoreSupport/ResourceLoaderAndroid.cpp, start, 47

// 创建WebUrlLoader, 并开始下载
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoader.cpp, start, 62
2140  2165 word, 1219
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoader.cpp, create, 79
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoader.cpp, WebUrlLoader, 43

// 创建WebUrlLoaderClient
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, WebUrlLoaderClient, 129

// 创建WebResourceRequest
2140  2165 WebKit/android/WebCoreSupport/WebResourceRequest.cpp, WebResourceRequest, 43
2140  2165 ptRequest, 525
2140  2165 quest()

// 创建WebRequest
2140  2165 WebKit/android/WebCoreSupport/WebRequest.cpp, WebRequest, 84

// 唤醒网络加载线程
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, start, 192
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, ioThread, 53
2140  2165 WebKit/android/WebCoreSupport/WebRequest.cpp, setRequestContext, 229
2140  2165 WebKit/android/WebCoreSupport/WebRequestContext.cpp, getCacheMode, 122

// 网络子线程开始活动
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, start, 259
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, updateLoadFlags, 240
2140  2190 WebKit/android/WebCoreSupport/WebRequestContext.cpp, GetUserAgent, 131
2140  2190 WebKit/android/WebCoreSupport/WebRequestContext.cpp, accept_language, 152
2140  2190 WebKit/android/WebCoreSupport/WebRequestContext.cpp, accept_language, 152
2140  2190 WebKit/android/WebCoreSupport/WebCookieJar.cpp, CanGetCookies, 214

// chromium socket 创建
2140  2190 ocket/tcp_client_socket_libevent.cc, BindToDevice, 52
2140  2190 erface [default]

2140  2165 WebCore/loader/PolicyCallback.cpp, ~PolicyCallback, 59
2140  2165 WebCore/loader/FrameLoader.cpp, findFrameForNavigation, 3887
2140  2165 WebCore/loader/FrameLoader.cpp, shouldAllowNavigation, 2023
2140  2165 WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp, get, 109
2140  2140 
2140  2140 ): http://218.206.177.209:8080/waptest/
2140  2140 .206.177.209:8080/waptest/
2140  2140 ark()


2140  2190 WebKit/android/WebCoreSupport/WebCookieJar.cpp, CanSetCookie, 225


// 资源开始加载了
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, OnResponseStarted, 519

// 通知main thread, 收到了response, 在main thread中调用didReceiveResponse()
2140  2190 WebKit/android/WebCoreSupport/WebResponse.cpp, WebResponse, 47
2140  2190 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, maybeCallOnMainThread, 415
2140  2190 WebKit/android/WebCoreSupport/PlatformBridge.cpp, scheduleDispatchFunctionsOnMainThread, 369

// 网络子线程开始获取网络数据
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, startReading, 607
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, read, 643
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, OnReadCompleted, 666

// 通知main thread获取到了数据， 在main thread中调用didReceiveData()
2140  2190 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, maybeCallOnMainThread, 415
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, startReading, 607
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, read, 643

// 通知main thread加载完成， 在main thread中调用didFinishLoading()
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, finish, 160
2140  2190 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, maybeCallOnMainThread, 415

// 主线程被唤醒， callback didReceiveResponse()被调用
2140  2165 WebKit/android/WebCoreSupport/PlatformBridge.cpp, timeoutFired, 360
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, didReceiveResponse, 434
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, isActive, 103

// 获取ResourceHandle的client
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127

// WebResponse创建ResourceResponseBase
2140  2165 WebKit/android/WebCoreSupport/WebResponse.cpp, createResourceResponse, 93
2140  2165 WebKit/android/WebCoreSupport/WebResponse.cpp, createKurl, 121
2140  2165 WebKit/android/WebCoreSupport/WebResponse.cpp, getMimeType, 151

// 根据http response设置ResourceResponseBase

// TODO:
// ResourceLoader的callback didReceiveResponse()被调用
2140  2165 WebCore/loader/ResourceLoader.cpp, didReceiveResponse, 549
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, maybeLoadFallbackForResponse, 242

// mainResourceLoader的callback didReceiveResponse被调用
2140  2165 WebCore/loader/MainResourceLoader.cpp, didReceiveResponse, 444
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, maybeLoadFallbackForMainResponse, 130
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226

// PolicyChecker检测response, 判断下载还是显示
2140  2165 WebCore/loader/PolicyChecker.cpp, checkContentPolicy, 122
2140  2165 WebCore/loader/PolicyCallback.cpp, set, 114
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/DocumentLoader.cpp, request, 187
2140  2165 pe: text/vnd.wap.wml#
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150

2140  2165 WebCore/loader/PolicyChecker.cpp, continueAfterContentPolicy, 233
2140  2165 WebCore/loader/PolicyCallback.cpp, clear, 67
2140  2165 WebCore/loader/PolicyCallback.cpp, clearRequest, 155
2140  2165 WebCore/loader/PolicyCallback.cpp, call, 143


2140  2165 WebCore/loader/MainResourceLoader.cpp, callContinueAfterContentPolicy, 395
2140  2165 WebCore/loader/MainResourceLoader.cpp, continueAfterContentPolicy, 404
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/FrameLoader.cpp, isStopping, 2687
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/MainResourceLoader.cpp, continueAfterContentPolicy, 304
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226

// 检查response中的mime type, 判断下载或显示
2140  2165 WebCore/loader/FrameLoader.cpp, canShowMIMEType, 2734
2140  2165 pe: text/vnd.wap.wml#

2140  2165 WebCore/loader/ResourceLoader.cpp, didReceiveResponse, 321
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, didReceiveResponse, 89


// 将response添加到DocumentLoader中
2140  2165 WebCore/loader/DocumentLoader.cpp, addResponse, 844

// 增加进度条
2140  2165 WebCore/loader/ProgressTracker.cpp, incrementProgress, 192


2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, dispatchDidReceiveResponse, 172
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/FrameLoader.cpp, isStopping, 2687
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/MainResourceLoader.cpp, shouldLoadAsEmptyDocument, 291
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/FrameLoader.cpp, representationExistsForURLScheme, 2743
2140  2165 WebCore/loader/PolicyCallback.cpp, ~PolicyCallback, 59


2140  2165 WebKit/android/WebCoreSupport/WebResponse.cpp, getHeader, 194

// 获取到了http entity数据
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, didReceiveData, 462
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, isActive, 103
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127

// ResourceLoaderAndroid, MainResourceLoader的callback didReceiveData()被调用
2140  2165 WebCore/loader/ResourceLoader.cpp, didReceiveData, 562
2140  2165 WebCore/loader/MainResourceLoader.cpp, didReceiveData, 519
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, mainResourceDataReceived, 166
2140  2165 WebCore/loader/ResourceLoader.cpp, didReceiveData, 342
2140  2165 WebCore/loader/MainResourceLoader.cpp, addData, 210
2140  2165 WebCore/loader/ResourceLoader.cpp, addData, 251
2140  2165 WebCore/platform/network/android/ResourceHandleAndroid.cpp, supportsBufferedData, 103

// DocumentLoader::receivedData()
2140  2165 WebCore/loader/DocumentLoader.cpp, receivedData, 436
2140  2165 WebCore/loader/DocumentLoader.cpp, doesProgressiveLoad, 427
2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123
2140  2165 WebCore/loader/FrameLoader.cpp, isReplacing, 2820

// DocumentLoader::commitLoad()
2140  2165 WebCore/loader/DocumentLoader.cpp, commitLoad, 378
2140  2165 WebCore/loader/DocumentLoader.cpp, commitIfReady, 352
2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123

// provisional load commit
2140  2165 WebCore/loader/FrameLoader.cpp, commitProvisionalLoad, 2323
2140  2165 WebCore/loader/FrameLoader.cpp, loadType, 2878
2140  2165 WebCore/loader/DocumentLoader.cpp, isLoadingInAPISense, 573
2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, requestCount, 812
2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123
2140  2165 WebCore/loader/FrameLoader.cpp, subframeIsLoading, 2847

// 判读是否能保存在Page Cache
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, canCacheInPageCache, 306
2140  2165 WebKit/android/WebCoreSupport/ChromeClientAndroid.cpp, contentsSizeChanged, 491
2140  2165 WebCore/loader/FrameLoader.cpp, stopLoading, 430
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, cancelRequests, 758
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, clearPendingPreloads, 909
2140  2165 WebCore/loader/NavigationScheduler.cpp, cancel, 503
2140  2165 WebKit/android/WebCoreSupport/CachedFramePlatformDataAndroid.cpp, CachedFramePlatformDataAndroid, 39


2140  2165 WebCore/loader/FrameLoader.cpp, closeOldDataSources, 2591
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, creatingInitialEmptyDocument, 70
2140  2165 WebCore/loader/FrameLoader.cpp, transitionToCommitted, 2420
2140  2165 WebCore/loader/HistoryController.cpp, updateForCommit, 490
2140  2165 WebCore/loader/FrameLoader.cpp, loadType, 2878
2140  2165 WebCore/loader/FrameLoader.cpp, isBackForwardLoadType, 152
2140  2165 WebCore/loader/HistoryController.cpp, isReplaceLoadTypeWithProvisionalItem, 524
2140  2165 WebCore/loader/FrameLoader.cpp, closeURL, 530


2140  2165 WebCore/loader/HistoryController.cpp, saveDocumentState, 170

// 创建初始的空文档
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, creatingInitialEmptyDocument, 70
2140  2165 WebCore/loader/icon/IconDatabaseBase.cpp, iconDatabase, 70
2140  2165 WebCore/loader/icon/IconDatabase.cpp, synchronousIconForPageURL, 250
2140  2165 WebCore/loader/icon/IconDatabase.cpp, isOpen, 1004

// ??
2140  2165 WebCore/loader/FrameLoader.cpp, stopLoading, 430
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, cancelRequests, 758
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, clearPendingPreloads, 909
2140  2165 WebCore/loader/NavigationScheduler.cpp, cancel, 503
2140  2165 WebCore/loader/DocumentLoader.cpp, stopLoadingSubresources, 986
2140  2165 WebCore/loader/DocumentLoader.cpp, cancelAll, 69
2140  2165 WebCore/loader/DocumentLoader.cpp, stopLoadingPlugIns, 977
2140  2165 WebCore/loader/DocumentLoader.cpp, cancelAll, 69
2140  2165 WebCore/loader/FrameLoader.cpp, setDocumentLoader, 2206
2140  2165 WebCore/loader/FrameLoader.cpp, detachChildren, 3107
2140  2165 WebCore/loader/DocumentLoader.cpp, detachFromFrame, 526

// 设置dom cache
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, setDOMApplicationCache, 315


// 设置provisional document loader
2140  2165 WebCore/loader/FrameLoader.cpp, setProvisionalDocumentLoader, 2260
2140  2165 WebCore/loader/FrameLoader.cpp, setState, 2284

2140  2165 WebCore/loader/HistoryController.cpp, updateForStandardLoad, 375

// History
2140  2165 WebCore/loader/DocumentLoader.cpp, urlForHistory, 894
2140  2165 WebCore/loader/HistoryController.cpp, updateBackForwardListClippedAtTarget, 903
2140  2165 WebCore/loader/DocumentLoader.cpp, urlForHistory, 894
2140  2165 WebCore/loader/HistoryController.cpp, containsWMLContent, 887
2140  2165 WebCore/loader/FrameLoader.cpp, checkDidPerformFirstNavigation, 3871

// 创建History tree和item
2140  2165 WebCore/loader/HistoryController.cpp, createItemTree, 739
2140  2165 WebCore/loader/HistoryController.cpp, createItem, 722
2140  2165 WebCore/loader/HistoryController.cpp, initializeItem, 672

2140  2165 WebCore/loader/DocumentLoader.cpp, unreachableURL, 954
2140  2165 WebCore/loader/DocumentLoader.cpp, url, 196
2140  2165 WebCore/loader/DocumentLoader.cpp, request, 178
2140  2165 WebCore/loader/DocumentLoader.cpp, originalURL, 918

2140  2165 WebCore/loader/icon/IconDatabaseBase.cpp, iconDatabase, 70
2140  2165 WebCore/loader/icon/IconDatabase.cpp, releaseIconForPageURL, 500
2140  2165 WebCore/loader/icon/IconDatabase.cpp, isEnabled, 778
2140  2165 WebCore/loader/icon/IconDatabaseBase.cpp, iconDatabase, 70
2140  2165 WebCore/loader/icon/IconDatabase.cpp, retainIconForPageURL, 452
2140  2165 WebCore/loader/icon/IconDatabase.cpp, isEnabled, 778
2140  2165 WebCore/loader/icon/PageURLRecord.cpp, PageURLRecord, 44

2140  2165 WebCore/loader/DocumentLoader.cpp, request, 187

// 保存history item
2140  2165 WebCore/loader/HistoryController.cpp, saveScrollPositionAndViewStateToItem, 99
2140  2165 WebCore/loader/icon/IconDatabaseBase.cpp, iconDatabase, 70
2140  2165 WebCore/loader/icon/IconDatabase.cpp, synchronousIconForPageURL, 250
2140  2165 WebCore/loader/icon/IconDatabase.cpp, isOpen, 1004
2140  2165 m, 706
2140  2165 WebCore/loader/icon/IconDatabaseBase.cpp, iconDatabase, 70
2140  2165 WebCore/loader/icon/IconDatabase.cpp, synchronousIconForPageURL, 250
2140  2165 WebCore/loader/icon/IconDatabase.cpp, isOpen, 1004

2140  2165 WebCore/loader/DocumentLoader.cpp, unreachableURL, 954
2140  2165 WebCore/loader/DocumentLoader.cpp, urlForHistory, 894
2140  2165 History, 855
2140  2165 WebCore/loader/DocumentLoader.cpp, urlForHistory, 894
2140  2165 WebCore/loader/DocumentLoader.cpp, url, 196
2140  2165 WebCore/loader/DocumentLoader.cpp, request, 178
2140  2165 WebCore/loader/DocumentLoader.cpp, unreachableURL, 954

2140  2165 WebCore/loader/HistoryController.cpp, addVisitedLink, 68
2140  2165 WebCore/loader/FrameLoader.cpp, resetMultipleFormSubmissionProtection, 1365
2140  2165 WebCore/loader/FrameLoader.cpp, resetMultipleFormSubmissionProtection, 1365
2140  2165 ommitted, 654
2140  2165 WebCore/loader/FrameLoader.cpp, loadType, 2878
2140  2165 WebCore/loader/DocumentLoader.cpp, responseMIMEType, 945


// 改变状态机
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, creatingInitialEmptyDocument, 70
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, committedFirstRealDocumentLoad, 61

2140  2165 WebCore/loader/FrameLoader.cpp, hasHTMLView, 4322
2140  2165 WebCore/loader/DocumentLoader.cpp, request, 187
2140  2165 WebCore/loader/DocumentLoader.cpp, url, 196
2140  2165 WebCore/loader/DocumentLoader.cpp, request, 178
2140  2165 WebCore/loader/FrameLoader.cpp, didOpenURL, 575
2140  2165 WebCore/loader/NavigationScheduler.cpp, redirectScheduledDuringLoad, 266
2140  2165 WebCore/loader/NavigationScheduler.cpp, cancel, 503

2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, creatingInitialEmptyDocument, 70

2140  2165 WebCore/loader/FrameLoader.cpp, started, 1520
2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123
2140  2165 WebCore/loader/archive/ArchiveFactory.cpp, isArchiveMimeType, 89
2140  2165 WebCore/loader/archive/ArchiveFactory.cpp, archiveMIMETypes, 67

// DocumentLoader收到数据
2140  2165 WebCore/loader/DocumentLoader.cpp, commitData, 400
2140  2165 WebCore/loader/DocumentLoader.cpp, commitData, 403
2140  2165 WebCore/loader/DocumentLoader.cpp, commitData, 409

// 根据response中的charset设置DocumentWriter的encoding
2140  2165 WebCore/loader/DocumentWriter.cpp, setEncoding, 348
2140  2165 ing(): utf-8, userChosen: 0
2140  2165 WebCore/loader/FrameLoader.cpp, willSetEncoding, 1374

// 收到了response中的第一段数据
2140  2165 WebCore/loader/FrameLoader.cpp, receivedFirstData, 701
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150

// DocumentWriter开始写数据
2140  2165 WebCore/loader/DocumentWriter.cpp, begin, 147

// DocumentWriter创建文档
2140  2165 WebCore/loader/DocumentWriter.cpp, createDocument, 134

2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, isDisplayingInitialEmptyDocument, 79
2140  2165 WebCore/loader/FrameLoader.cpp, hasHTMLView, 4322

// 创建CachedResourceLoader
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, CachedResourceLoader, 101
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, isDisplayingInitialEmptyDocument, 79


// 清除前一次的Document?
2140  2165 WebCore/loader/FrameLoader.cpp, clear, 648
2140  2165 WebCore/loader/SubframeLoader.cpp, clear, 76
2140  2165 WebCore/loader/NavigationScheduler.cpp, clear, 284
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, isDisplayingInitialEmptyDocument, 79
2140  2165 WebCore/loader/DocumentWriter.cpp, clear, 113
2140  2165 WebCore/loader/FrameLoader.cpp, setOutgoingReferrer, 739

2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, suspendPendingRequests, 256
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, requestCount, 812
2140  2165 WebCore/loader/FrameLoader.cpp, tellClientAboutPastMemoryCacheLoads, 4282
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resumePendingRequests, 266
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150

2140  2165 WebCore/loader/FrameLoader.cpp, dispatchDidClearWindowObjectInWorld, 4200
2140  2165 WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp, dispatchDidClearWindowObjectInWorld, 1404

2140  2165 WebCore/loader/FrameLoader.cpp, didBeginDocument, 748
2140  2165 WebCore/loader/FrameLoader.cpp, updateFirstPartyForCookies, 1399
2140  2165 WebCore/loader/FrameLoader.cpp, setFirstPartyForCookies, 1411
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, setAutoLoadImages, 601
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, setBlockNetworkImage, 650


2140  2165 WebCore/loader/HistoryController.cpp, restoreDocumentState, 219
2140  2165 WebCore/loader/FrameLoader.cpp, loadType, 2878
2140  2165 WebCore/loader/FrameLoader.cpp, hasHTMLView, 4322
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/DocumentWriter.cpp, setDocumentWasLoadedAsPartOfNavigation, 382
2140  2165 WebCore/loader/FrameLoader.cpp, dispatchDidCommitLoad, 4268
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, creatingInitialEmptyDocument, 70
2140  2165 WebKit/android/WebCoreSupport/EditorClientAndroid.cpp, getAutofill, 295

2140  2165 WebCore/loader/FrameLoader.cpp, dispatchDidClearWindowObjectsInAllWorlds, 4185
2140  2165 WebCore/loader/FrameLoader.cpp, dispatchDidClearWindowObjectInWorld, 4200
2140  2165 WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp, dispatchDidClearWindowObjectInWorld, 1404
2140  2165 leared, 768
2140  2165 Interface, 1945
2140  2165 atingJavaScriptFromString, 1837

2140  2165 WebKit/android/WebCoreSupport/PlatformBridge.cpp, memoryUsageMB, 305
2140  2165 WebKit/android/WebCoreSupport/PlatformBridge.cpp, memoryUsageMB, 305
2140  2165 WebCore/platform/network/HTTPParsers.cpp, parseHTTPRefresh, 123
2140  2165 WebCore/platform/network/HTTPParsers.cpp, skipWhiteSpace, 51


// DocumentWriter写数据
2140  2165 WebCore/loader/DocumentWriter.cpp, addData, 278
// 创建decoder
2140  2165 WebCore/loader/DocumentWriter.cpp, createDecoderIfNeeded, 197
2140  2165 coderIfNeeded()
2140  2165 WebCore/loader/TextResourceDecoder.cpp, determineContentType, 303
2140  2165 WebCore/loader/TextResourceDecoder.cpp, defaultEncoding, 318
2140  2165 aultEncoding, content-type: 2, default encoding: 
2140  2165 WebCore/loader/TextResourceDecoder.cpp, TextResourceDecoder, 347
2140  2165 WebCore/loader/DocumentWriter.cpp, canReferToParentFrameEncoding, 62
2140  2165  set decoder from http header or user
2140  2165 
2140  2165 WebCore/loader/TextResourceDecoder.cpp, setEncoding, 363
2140  2165 Encoding(), source: 5
2140  2165 Encoding() name: UTF-8
2140  2165 WebCore/loader/TextResourceDecoder.cpp, decode, 770
2140  2165 WebCore/loader/TextResourceDecoder.cpp, checkForBOM, 447
2140  2165 WebCore/loader/TextResourceDecoder.cpp, setEncoding, 363
2140  2165 Encoding(), source: 1
2140  2165 Encoding() name: UTF-8
2140  2165 ode()
2140  2165 ode() css charset done!
2140  2165 WebCore/loader/TextResourceDecoder.cpp, checkForHeadCharset, 590
2140  2165 ckForHeadCharset(), decode source: 1
2140  2165 ode(),  html/xml charset done! current encoding: UTF-8
2140  2165 WebCore/loader/TextResourceDecoder.cpp, shouldAutoDetect, 742
2140  2165 uldAutoDetect, m_usesEncodingDetector: 1, m_source :1
2140  2165 ode() autodetect done!

2140  2165 WebCore/loader/DocumentWriter.cpp, reportDataReceived, 263
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, suspendPendingRequests, 256
2140  2165 WebCore/loader/FrameLoader.cpp, tellClientAboutPastMemoryCacheLoads, 4282
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resumePendingRequests, 266
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278

2140  2165 WebKit/android/WebCoreSupport/PlatformBridge.cpp, updateViewport, 251
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, suspendPendingRequests, 256
2140  2165 WebCore/loader/FrameLoader.cpp, tellClientAboutPastMemoryCacheLoads, 4282
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resumePendingRequests, 266
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278

// document element ok了
2140  2165 WebCore/loader/FrameLoader.cpp, dispatchDocumentElementAvailable, 4175
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, creatingInitialEmptyDocument, 70
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, suspendPendingRequests, 256
2140  2165 WebCore/loader/FrameLoader.cpp, tellClientAboutPastMemoryCacheLoads, 4282
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resumePendingRequests, 266
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, suspendPendingRequests, 256
2140  2165 WebCore/loader/FrameLoader.cpp, tellClientAboutPastMemoryCacheLoads, 4282
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resumePendingRequests, 266
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, suspendPendingRequests, 256
2140  2165 WebCore/loader/FrameLoader.cpp, tellClientAboutPastMemoryCacheLoads, 4282
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resumePendingRequests, 266
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, suspendPendingRequests, 256
2140  2165 WebCore/loader/FrameLoader.cpp, tellClientAboutPastMemoryCacheLoads, 4282
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resumePendingRequests, 266
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278
2140  2165 
2140  2165  51
2140  2165 bute, 267
2140  2165 bute, 267
2140  2165 k, 474
2140  2165 bute, 267
2140  2165 bute, 267
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, suspendPendingRequests, 256
2140  2165 304
2140  2165 WebCore/loader/FrameLoader.cpp, tellClientAboutPastMemoryCacheLoads, 4282
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resumePendingRequests, 266
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278
2140  2165 163
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, suspendPendingRequests, 256
2140  2165 WebCore/loader/FrameLoader.cpp, tellClientAboutPastMemoryCacheLoads, 4282
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resumePendingRequests, 266
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, suspendPendingRequests, 256
2140  2165 WebCore/loader/FrameLoader.cpp, tellClientAboutPastMemoryCacheLoads, 4282
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resumePendingRequests, 266
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278
2140  2165 
2140  2165  51
2140  2165 bute, 267
2140  2165 bute, 267
2140  2165 bute, 267
2140  2165 k, 474
2140  2165 bute, 267
2140  2165 bute, 267
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, suspendPendingRequests, 256
2140  2165 304
2140  2165 WebCore/loader/FrameLoader.cpp, tellClientAboutPastMemoryCacheLoads, 4282
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resumePendingRequests, 266
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278
2140  2165 163
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, suspendPendingRequests, 256
2140  2165 WebCore/loader/FrameLoader.cpp, tellClientAboutPastMemoryCacheLoads, 4282
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resumePendingRequests, 266
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, suspendPendingRequests, 256
2140  2165 WebCore/loader/FrameLoader.cpp, tellClientAboutPastMemoryCacheLoads, 4282
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resumePendingRequests, 266
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278


2140  2165 WebCore/loader/ImageLoader.cpp, dispatchPendingBeforeLoadEvents, 357
2140  2165 WebCore/loader/ImageLoader.cpp, beforeLoadEventSender, 92
2140  2165 WebCore/loader/ImageLoader.cpp, dispatchPendingEvents, 427
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, didReceiveData, 103
2140  2165 WebCore/loader/ProgressTracker.cpp, incrementProgress, 216
2140  2165 WebCore/loader/FrameLoader.cpp, numPendingOrLoadingRequests, 3175
2140  2165 WebCore/loader/FrameLoader.cpp, numRequests, 176
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, requestCount, 812
2140  2165 WebCore/loader/FrameLoader.cpp, hasHTMLView, 4322

// 更新状态 first layout done!
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, firstLayoutDone, 88
2140  2165 WebCore/loader/ProgressTracker.cpp, estimatedProgress, 98
2140  2165 786
2140  2140 (): 50


2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, dispatchDidReceiveContentLength, 183
2140  2165 WebKit/android/WebCoreSupport/PlatformBridge.cpp, scheduleDispatchFunctionsOnMainThread, 369
2140  2165 WebKit/android/WebCoreSupport/PlatformBridge.cpp, timeoutFired, 360

// call back didFinishLoading
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, didFinishLoading, 546
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, isActive, 103
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127

2140  2165 WebCore/loader/ResourceLoader.cpp, didFinishLoading, 573
2140  2165 WebCore/loader/MainResourceLoader.cpp, didFinishLoading, 558
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/FrameLoader.cpp, finishedLoading, 2696
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/DocumentLoader.cpp, finishedLoading, 364
2140  2165 WebCore/loader/DocumentLoader.cpp, commitIfReady, 352
2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123
2140  2165 WebCore/loader/FrameLoader.cpp, finishedLoadingDocument, 2770


2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, creatingInitialEmptyDocument, 70
2140  2165 WebCore/loader/DocumentLoader.cpp, mainResourceData, 147
2140  2165 WebCore/loader/ResourceLoader.cpp, resourceData, 56
2140  2165 WebCore/loader/DocumentLoader.cpp, responseMIMEType, 945
2140  2165 WebCore/loader/archive/ArchiveFactory.cpp, create, 98
2140  2165 WebCore/loader/archive/ArchiveFactory.cpp, archiveMIMETypes, 67

2140  2165 WebCore/loader/DocumentLoader.cpp, commitData, 400
2140  2165 WebCore/loader/DocumentLoader.cpp, commitData, 403
2140  2165 WebCore/loader/DocumentLoader.cpp, commitData, 409


// DocumentWriter完成写数据, decoder flush
2140  2165 WebCore/loader/DocumentWriter.cpp, setEncoding, 348
2140  2165 ing(): utf-8, userChosen: 0
2140  2165 WebCore/loader/FrameLoader.cpp, willSetEncoding, 1374
2140  2165 WebCore/loader/DocumentWriter.cpp, addData, 278
2140  2165 WebCore/loader/DocumentWriter.cpp, end, 292
2140  2165 WebCore/loader/FrameLoader.cpp, didEndDocument, 789
2140  2165 WebCore/loader/DocumentWriter.cpp, endIfNotLoadingMainResource, 302
2140  2165 WebCore/loader/DocumentWriter.cpp, addData, 278
2140  2165 WebCore/loader/DocumentWriter.cpp, createDecoderIfNeeded, 197
2140  2165 coderIfNeeded()
2140  2165 WebCore/loader/TextResourceDecoder.cpp, decode, 770
2140  2165 ode()
2140  2165 ode() css charset done!
2140  2165 ode(),  html/xml charset done! current encoding: UTF-8
2140  2165 WebCore/loader/TextResourceDecoder.cpp, shouldAutoDetect, 742
2140  2165 uldAutoDetect, m_usesEncodingDetector: 1, m_source :1
2140  2165 ode() autodetect done!
2140  2165 WebCore/loader/TextResourceDecoder.cpp, flush, 838


2140  2165 sh()
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, suspendPendingRequests, 256
2140  2165 163
2140  2165 163
2140  2165 WebCore/loader/FrameLoader.cpp, tellClientAboutPastMemoryCacheLoads, 4282
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resumePendingRequests, 266
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278
2140  2165 163
2140  2165 163

// 文档解析完成
2140  2165 WebCore/loader/FrameLoader.cpp, finishedParsing, 923
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, creatingInitialEmptyDocument, 70
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, creatingInitialEmptyDocument, 70
2140  2165 WebCore/loader/FrameLoader.cpp, checkCompleted, 987

2140  2165 WebCore/loader/FrameLoader.cpp, numRequests, 176
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, requestCount, 812
2140  2165 WebCore/loader/FrameLoader.cpp, allChildrenAreComplete, 961
2140  2165 WebCore/loader/FrameLoader.cpp, checkCallImplicitClose, 1081
2140  2165 WebCore/loader/FrameLoader.cpp, allChildrenAreComplete, 961

2140  2165 WebCore/loader/NavigationScheduler.cpp, locationChangePending, 275
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, clearPreloads, 885


// 开始加载fav icon
2140  2165 WebCore/loader/FrameLoader.cpp, startIconLoader, 812
2140  2165 WebCore/loader/FrameLoader.cpp, isLoadingMainFrame, 2724
2140  2165 WebCore/loader/icon/IconDatabaseBase.cpp, iconDatabase, 70
2140  2165 WebCore/loader/icon/IconDatabase.cpp, isEnabled, 778
2140  2165 WebCore/loader/FrameLoader.cpp, iconURL, 546
2140  2165 WebCore/loader/FrameLoader.cpp, loadType, 2878
2140  2165 WebCore/loader/icon/IconDatabaseBase.cpp, iconDatabase, 70
2140  2165 WebCore/loader/icon/IconDatabaseBase.cpp, iconDatabase, 70
2140  2165 WebCore/loader/icon/IconDatabase.cpp, synchronousLoadDecisionForIconURL, 714
2140  2165 WebCore/loader/icon/IconDatabase.cpp, isOpen, 1004
2140  2165 WebCore/loader/FrameLoader.cpp, continueIconLoadWithDecision, 867
2140  2165 WebCore/loader/icon/IconDatabaseBase.cpp, iconDatabase, 70
2140  2165 WebCore/loader/icon/IconLoader.cpp, create, 64
2140  2165 WebCore/loader/icon/IconLoader.cpp, IconLoader, 56
2140  2165 WebCore/loader/icon/IconLoader.cpp, startLoading, 81
2140  2165 WebCore/loader/FrameLoader.cpp, iconURL, 546

2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleSubresourceLoad, 105

// 创建了一个SubresourceLoader
2140  2165 WebCore/loader/SubresourceLoader.cpp, create, 77
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/FrameLoader.cpp, outgoingReferrer, 1234
2140  2165 WebCore/loader/FrameLoader.cpp, outgoingOrigin, 1243
2140  2165 WebCore/loader/FrameLoader.cpp, addHTTPOriginIfNeeded, 3348
2140  2165 WebCore/loader/FrameLoader.cpp, addExtraFieldsToSubresourceRequest, 3269
2140  2165 WebCore/loader/FrameLoader.cpp, addExtraFieldsToRequest, 3287
2140  2165 WebCore/loader/FrameLoader.cpp, applyUserAgent, 3805
2140  2165 WebCore/loader/FrameLoader.cpp, userAgent, 3190
2140  2165 RL, 804

2140  2165 WebCore/loader/DocumentLoader.cpp, isLoadingInAPISense, 573
2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123
2140  2165 WebCore/loader/DocumentLoader.cpp, originalRequest, 160
2140  2165 WebCore/loader/FrameLoader.cpp, addHTTPOriginIfNeeded, 3348
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/DocumentWriter.cpp, deprecatedFrameEncoding, 373
2140  2165 WebCore/loader/DocumentWriter.cpp, encoding, 321
2140  2165 (), from m_decoder: UTF-8
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150

// 创建了一个SubresourceLoader
2140  2165 WebCore/loader/ResourceLoader.cpp, ResourceLoader, 81
2140  2165 WebCore/loader/SubresourceLoader.cpp, SubresourceLoader, 55
2140  2165 WebCore/loader/ResourceLoader.cpp, setShouldBufferData, 237

// 将SubresourceLoader添加到DocumentLoader中
2140  2165 WebCore/loader/DocumentLoader.cpp, addSubresourceLoader, 995
2140  2165 WebCore/loader/ResourceLoader.cpp, init, 135

2140  2165 WebCore/loader/SubresourceLoader.cpp, willSendRequest, 124
2140  2165 WebCore/loader/ResourceLoader.cpp, willSendRequest, 287
2140  2165 WebCore/loader/ProgressTracker.cpp, createUniqueIdentifier, 305
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, assignIdentifierToInitialRequest, 141
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, willSendRequest, 78
2140  2165 WebCore/loader/FrameLoader.cpp, applyUserAgent, 3805
2140  2165 WebCore/loader/FrameLoader.cpp, userAgent, 3190
2140  2165 RL, 804
2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, dispatchWillSendRequest, 150
2140  2165 WebCore/loader/DocumentLoader.cpp, requestURL, 927
2140  2165 WebCore/loader/DocumentLoader.cpp, request, 178
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleLoad, 138
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, hostForURL, 59
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, hasRequests, 361
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, schedule, 320
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226

2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278
2140  2165 WebCore/loader/ImageLoader.cpp, dispatchPendingBeforeLoadEvents, 357
2140  2165 WebCore/loader/ImageLoader.cpp, beforeLoadEventSender, 92
2140  2165 WebCore/loader/ImageLoader.cpp, dispatchPendingEvents, 427
2140  2165 WebCore/loader/ImageLoader.cpp, dispatchPendingLoadEvents, 366
2140  2165 WebCore/loader/ImageLoader.cpp, loadEventSender, 102
2140  2165 WebCore/loader/ImageLoader.cpp, dispatchPendingEvents, 427
2140  2165 WebCore/loader/FrameLoader.cpp, handledOnloadEvents, 3201
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, stopDeferringEvents, 353
2140  2165 WebCore/loader/NavigationScheduler.cpp, locationChangePending, 275
2140  2165 WebCore/loader/FrameLoader.cpp, checkCallImplicitClose, 1081
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, requestCount, 812
2140  2165 WebKit/android/WebCoreSupport/ChromeClientAndroid.cpp, contentsSizeChanged, 491
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, requestCount, 812

2140  2165 WebCore/loader/FrameLoader.cpp, didFirstLayout, 3066
2140  2165 WebCore/loader/FrameLoader.cpp, isBackForwardLoadType, 152

// 状态机改变 firstLayoutDone
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, committedFirstRealDocumentLoad, 61
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, isDisplayingInitialEmptyDocument, 79
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, firstLayoutDone, 88
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, advanceTo, 97

2140  2165 WebCore/loader/FrameLoader.cpp, isComplete, 1493
2140  2165 WebCore/loader/FrameLoader.cpp, loadType, 2878
2140  2165 tScrolledRight, 2258

2140  2165 WebCore/loader/FrameLoader.cpp, didFirstVisuallyNonEmptyLayout, 3080
2140  2165 WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp, dispatchDidFirstVisuallyNonEmptyLayout, 456

2140  2165 WebCore/loader/NavigationScheduler.cpp, startTimer, 484


2140  2165 WebCore/loader/FrameLoader.cpp, completed, 1502
2140  2165 WebCore/loader/FrameLoader.cpp, checkLoadComplete, 3159
2140  2165 WebCore/loader/FrameLoader.cpp, recursiveCheckLoadComplete, 3140
2140  2165 WebCore/loader/FrameLoader.cpp, checkLoadCompleteForThisFrame, 2919

2140  2165 WebCore/loader/DocumentLoader.cpp, isLoadingInAPISense, 573
2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123
2140  2165 
2140  2165  156
2140  2165 
2140  2165 utMask, 528
2140  2165 
2140  2165  156
2140  2165 
2140  2165 utMask, 528

2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123

// 设置主要的加载完成了
2140  2165 WebCore/loader/DocumentLoader.cpp, setPrimaryLoadComplete, 555

2140  2165 WebCore/loader/ResourceLoader.cpp, resourceData, 56
2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/DocumentLoader.cpp, updateLoading, 483
2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123
2140  2165 WebCore/loader/FrameLoader.cpp, isLoading, 2161
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/DocumentLoader.cpp, isLoadingMainResource, 1037
2140  2165 WebCore/loader/DocumentLoader.cpp, isLoadingSubresources, 1046
2140  2165 WebCore/loader/FrameLoader.cpp, checkLoadComplete, 3159
2140  2165 WebCore/loader/FrameLoader.cpp, recursiveCheckLoadComplete, 3140
2140  2165 WebCore/loader/FrameLoader.cpp, checkLoadCompleteForThisFrame, 2919
2140  2165 WebCore/loader/DocumentLoader.cpp, isLoadingInAPISense, 573
2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123

// 加载完成
2140  2165 WebCore/loader/ResourceLoader.cpp, didFinishLoading, 379
2140  2165 WebCore/loader/ResourceLoader.cpp, didFinishLoadingOnePart, 395
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, didFinishLoad, 115
2140  2165 WebCore/loader/ProgressTracker.cpp, completeProgress, 284

2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, dispatchDidFinishLoading, 194
2140  2165 WebCore/loader/ResourceLoader.cpp, releaseResources, 98


2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, remove, 166
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, hostForURL, 59
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, remove, 339
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127
2140  2165 WebCore/platform/network/ResourceHandle.cpp, setClient, 136
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, finishedLoadingMainResource, 194

// main resource加载完成, 清除相关对象
2140  2165 WebCore/loader/MainResourceLoader.cpp, ~MainResourceLoader, 82
2140  2165 WebCore/loader/ResourceLoader.cpp, ~ResourceLoader, 89
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, finish, 388
2140  2165 WebCore/platform/network/android/ResourceHandleAndroid.cpp, ~ResourceHandle, 57
2140  2165 WebCore/platform/network/android/ResourceHandleAndroid.cpp, ~ResourceHandleInternal, 49
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoader.cpp, ~WebUrlLoader, 53
2140  2165 WebKit/android/WebCoreSupport/WebRequest.cpp, ~WebRequest, 122
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, ~WebUrlLoaderClient, 95

2140  2165 WebKit/android/WebCoreSupport/PlatformBridge.cpp, scheduleDispatchFunctionsOnMainThread, 369
2140  2165 WebKit/android/WebCoreSupport/PlatformBridge.cpp, timeoutFired, 360
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, requestTimerFired, 289
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, servePendingRequests, 197
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, servePendingRequests, 227
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, hasRequests, 361
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, servePendingRequests, 227
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, limitRequests, 376
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, addLoadInProgress, 329

// 开始加载icon?
2140  2165 WebCore/loader/ResourceLoader.cpp, start, 168
2140  2165 WebCore/loader/DocumentLoader.cpp, scheduleArchiveLoad, 818
2140  2165 WebCore/loader/DocumentLoader.cpp, archiveResourceForURL, 669
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, maybeLoadResource, 208
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, isApplicationCacheEnabled, 598
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, shouldLoadResourceFromApplicationCache, 450
2140  2165 WebCore/loader/FrameLoader.cpp, networkingContext, 4313

// 创建ResourceHandle, 并开始网络加载
2140  2165 WebCore/platform/network/ResourceHandle.cpp, create, 66
2140  2165 WebCore/platform/network/ResourceHandle.cpp, shouldContentSniffURL, 193
2140  2165 WebCore/platform/network/Credential.cpp, Credential, 44
2140  2165 WebCore/platform/network/ProtectionSpace.cpp, ProtectionSpace, 50
2140  2165 WebCore/platform/network/Credential.cpp, Credential, 44
2140  2165 WebCore/platform/network/AuthenticationChallengeBase.cpp, AuthenticationChallengeBase, 40
2140  2165 WebCore/platform/network/ResourceHandle.cpp, ResourceHandle, 49
2140  2165 WebCore/platform/network/android/ResourceHandleAndroid.cpp, start, 65
2140  2165 WebKit/android/WebCoreSupport/FrameNetworkingContextAndroid.cpp, mainResourceLoader, 52
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127
2140  2165 WebKit/android/WebCoreSupport/FrameNetworkingContextAndroid.cpp, frameLoaderClient, 61
2140  2165 WebKit/android/WebCoreSupport/ResourceLoaderAndroid.cpp, start, 47
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoader.cpp, start, 62
2140  2165 word, 1219
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoader.cpp, create, 79
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoader.cpp, WebUrlLoader, 43
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, WebUrlLoaderClient, 129
2140  2165 WebKit/android/WebCoreSupport/WebResourceRequest.cpp, WebResourceRequest, 43
2140  2165 ptRequest, 525
2140  2165 quest()
2140  2140 
2140  2140 )
2140  2140 ): http://218.206.177.209:8080/favicon.ico

2140  2140 
2140  2165 WebKit/android/WebCoreSupport/WebRequest.cpp, WebRequest, 84
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, start, 192
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, ioThread, 53
2140  2165 WebKit/android/WebCoreSupport/WebRequest.cpp, setRequestContext, 229
2140  2165 WebKit/android/WebCoreSupport/WebRequestContext.cpp, getCacheMode, 122

// 网络线程开始加载资源
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, start, 259
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, updateLoadFlags, 240
2140  2190 WebKit/android/WebCoreSupport/WebRequestContext.cpp, GetUserAgent, 131
2140  2190 WebKit/android/WebCoreSupport/WebRequestContext.cpp, accept_language, 152
2140  2190 WebKit/android/WebCoreSupport/WebRequestContext.cpp, accept_language, 152
2140  2190 WebKit/android/WebCoreSupport/WebCookieJar.cpp, CanGetCookies, 214

2140  2165 WebCore/loader/ProgressTracker.cpp, estimatedProgress, 98
2140  2165 WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp, get, 109
2140  2165 WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp, get, 109
2140  2165 WebKit/android/WebCoreSupport/ChromeClientAndroid.cpp, layersSync, 73
2140  2165 WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp, get, 109
2140  2165 WebKit/android/WebCoreSupport/ChromeClientAndroid.cpp, keyboardUIMode, 411


// 网络线程完成加载
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, OnResponseStarted, 519
2140  2190 WebKit/android/WebCoreSupport/WebResponse.cpp, WebResponse, 47
2140  2190 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, maybeCallOnMainThread, 415
2140  2190 WebKit/android/WebCoreSupport/PlatformBridge.cpp, scheduleDispatchFunctionsOnMainThread, 369
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, startReading, 607
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, read, 643
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, OnReadCompleted, 666
2140  2190 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, maybeCallOnMainThread, 415
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, startReading, 607
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, read, 643
2140  2190 WebKit/android/WebCoreSupport/WebRequest.cpp, finish, 160
2140  2190 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, maybeCallOnMainThread, 415

// callback didReceiveResponse
2140  2165 WebKit/android/WebCoreSupport/PlatformBridge.cpp, timeoutFired, 360
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, didReceiveResponse, 434
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, isActive, 103
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127
2140  2165 WebKit/android/WebCoreSupport/WebResponse.cpp, createResourceResponse, 93
2140  2165 WebKit/android/WebCoreSupport/WebResponse.cpp, createKurl, 121
2140  2165 WebKit/android/WebCoreSupport/WebResponse.cpp, getMimeType, 151
2140  2165 WebCore/loader/ResourceLoader.cpp, didReceiveResponse, 549
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, maybeLoadFallbackForResponse, 242
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, scheduleLoadFallbackResourceFromApplicationCache, 509
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, isApplicationCacheEnabled, 598
2140  2165 WebCore/loader/appcache/ApplicationCacheHost.cpp, getApplicationCacheFallbackResource, 479

2140  2165 WebCore/loader/SubresourceLoader.cpp, didReceiveResponse, 150
2140  2165 WebCore/loader/icon/IconLoader.cpp, didReceiveResponse, 116
2140  2165 WebCore/platform/network/ResourceHandle.cpp, firstRequest, 145
2140  2165 WebCore/loader/icon/IconLoader.cpp, finishLoading, 195
2140  2165 WebCore/loader/FrameLoader.cpp, commitIconURLToIconDatabase, 912
2140  2165 WebCore/loader/icon/IconDatabaseBase.cpp, iconDatabase, 70
2140  2165 WebCore/loader/icon/IconDatabase.cpp, setIconURLForPageURL, 641
2140  2165 WebCore/loader/icon/IconDatabase.cpp, isOpen, 1004
2140  2165 WebCore/loader/icon/IconDatabase.cpp, getOrCreateIconRecord, 1035
2140  2165 WebCore/loader/icon/IconRecord.cpp, IconRecord, 52
2140  2165 WebCore/loader/icon/PageURLRecord.cpp, setIconRecord, 61
2140  2165 WebCore/loader/icon/PageURLRecord.cpp, snapshot, 76
2140  2165 WebCore/loader/icon/IconDatabase.cpp, scheduleOrDeferSyncTimer, 969
2140  2165 WebCore/loader/icon/IconDatabaseBase.cpp, iconDatabase, 70
2140  2165 WebCore/loader/FrameLoader.cpp, originalRequestURL, 4157
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/DocumentLoader.cpp, originalRequest, 160
2140  2165 WebCore/loader/icon/IconDatabase.cpp, setIconURLForPageURL, 641
2140  2165 WebCore/loader/icon/IconDatabase.cpp, isOpen, 1004
2140  2165 WebCore/loader/icon/IconDatabaseBase.cpp, iconDatabase, 70
2140  2165 WebCore/loader/icon/IconDatabase.cpp, setIconDataForIconURL, 573
2140  2165 WebCore/loader/icon/IconDatabase.cpp, isOpen, 1004
2140  2165 WebCore/loader/icon/IconRecord.cpp, setImageData, 83
2140  2165 WebCore/loader/icon/IconRecord.cpp, snapshot, 128
2140  2165 WebCore/loader/icon/IconDatabase.cpp, scheduleOrDeferSyncTimer, 969
2140  2165 WebCore/loader/icon/IconDatabaseBase.cpp, iconDatabase, 70
2140  2165 WebCore/loader/icon/IconDatabase.cpp, synchronousIconForPageURL, 250
2140  2165 WebCore/loader/icon/IconDatabase.cpp, isOpen, 1004
2140  2165 WebCore/loader/icon/IconRecord.cpp, imageDataStatus, 115
2140  2165 WebCore/loader/icon/IconRecord.cpp, image, 70
2140  2165 n, 812
2140  2165 WebCore/loader/icon/IconLoader.cpp, clearLoadingState, 221

2140  2165 WebCore/loader/ResourceLoader.cpp, didReceiveResponse, 321
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, didReceiveResponse, 89

2140  2165 WebCore/loader/DocumentLoader.cpp, addResponse, 844

2140  2165 WebCore/loader/ProgressTracker.cpp, incrementProgress, 192
2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, dispatchDidReceiveResponse, 172
2140  2165 WebCore/loader/ResourceLoader.cpp, resourceData, 56
2140  2165 WebCore/platform/network/android/ResourceHandleAndroid.cpp, supportsBufferedData, 103


// callback didReceiveData()
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, didReceiveData, 462
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, isActive, 103
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127
2140  2165 WebCore/loader/ResourceLoader.cpp, didReceiveData, 562
2140  2165 WebCore/loader/SubresourceLoader.cpp, didReceiveData, 188
2140  2165 WebCore/loader/ResourceLoader.cpp, didReceiveData, 342

2140  2165 WebCore/loader/ResourceLoader.cpp, addData, 251
2140  2165 WebCore/platform/network/android/ResourceHandleAndroid.cpp, supportsBufferedData, 103
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, didReceiveData, 103
2140  2165 WebCore/loader/ProgressTracker.cpp, incrementProgress, 216
2140  2165 WebCore/loader/FrameLoader.cpp, numPendingOrLoadingRequests, 3175
2140  2165 WebCore/loader/FrameLoader.cpp, numRequests, 176
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, requestCount, 812
2140  2165 WebCore/loader/FrameLoader.cpp, hasHTMLView, 4322
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, firstLayoutDone, 88
2140  2165 WebCore/loader/ProgressTracker.cpp, estimatedProgress, 98
2140  2165 786
2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, dispatchDidReceiveContentLength, 183
2140  2165 WebCore/loader/icon/IconLoader.cpp, didReceiveData, 133


// callback didFinishLoading
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, didFinishLoading, 546
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, isActive, 103
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127
2140  2165 WebCore/platform/network/ResourceHandle.cpp, client, 127
2140  2165 WebCore/loader/ResourceLoader.cpp, didFinishLoading, 573
2140  2165 WebCore/loader/SubresourceLoader.cpp, didFinishLoading, 220
2140  2165 WebCore/loader/icon/IconLoader.cpp, didFinishLoading, 176
2140  2165 WebCore/loader/DocumentLoader.cpp, removeSubresourceLoader, 1005
2140  2165 WebCore/loader/DocumentLoader.cpp, updateLoading, 483
2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123
2140  2165 WebCore/loader/FrameLoader.cpp, isLoading, 2161
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/DocumentLoader.cpp, isLoadingMainResource, 1037
2140  2165 WebCore/loader/DocumentLoader.cpp, isLoadingSubresources, 1046
2140  2165 WebCore/loader/DocumentLoader.cpp, isLoadingPlugIns, 1055
2140  2165 WebCore/loader/FrameLoader.cpp, checkLoadComplete, 3159
2140  2165 WebCore/loader/FrameLoader.cpp, recursiveCheckLoadComplete, 3140
2140  2165 WebCore/loader/FrameLoader.cpp, checkLoadCompleteForThisFrame, 2919
2140  2165 WebCore/loader/DocumentLoader.cpp, isLoadingInAPISense, 573
2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123
2140  2165 WebCore/loader/cache/CachedResourceLoader.cpp, requestCount, 812
2140  2165 WebCore/loader/DocumentLoader.cpp, frameLoader, 123
2140  2165 WebCore/loader/FrameLoader.cpp, subframeIsLoading, 2847
2140  2165 WebCore/loader/FrameLoader.cpp, markLoadComplete, 2314
2140  2165 WebCore/loader/FrameLoader.cpp, setState, 2284
2140  2165 WebCore/loader/FrameLoader.cpp, frameLoadCompleted, 3089

2140  2165 WebCore/loader/HistoryController.cpp, updateForFrameLoadCompleted, 616


2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, committedFirstRealDocumentLoad, 61
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, isDisplayingInitialEmptyDocument, 79
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, firstLayoutDone, 88
2140  2165 WebCore/loader/DocumentLoader.cpp, stopRecordingResponses, 854
2140  2165 WebCore/loader/FrameLoader.cpp, isBackForwardLoadType, 152

2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, creatingInitialEmptyDocument, 70
2140  2165 WebCore/loader/FrameLoaderStateMachine.cpp, committedFirstRealDocumentLoad, 61
2140  2165 , 673
2140  2165 WebCore/loader/FrameLoader.cpp, activeDocumentLoader, 2150
2140  2165 WebCore/loader/DocumentLoader.cpp, url, 196
2140  2165 WebCore/loader/DocumentLoader.cpp, request, 178
2140  2165 WebCore/loader/FrameLoader.cpp, loadType, 2878


2140  2165 WebCore/loader/ProgressTracker.cpp, progressCompleted, 148
2140  2165 WebCore/loader/ProgressTracker.cpp, finalProgressComplete, 169
2140  2165 WebCore/loader/ProgressTracker.cpp, estimatedProgress, 98
2140  2165 786
2140  2165 WebCore/loader/ProgressTracker.cpp, reset, 107

2140  2165 WebCore/loader/ResourceLoader.cpp, didFinishLoading, 379
2140  2165 WebCore/loader/ResourceLoader.cpp, didFinishLoadingOnePart, 395
2140  2165 WebCore/loader/ResourceLoader.cpp, frameLoader, 226
2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, didFinishLoad, 115
2140  2165 WebCore/loader/ProgressTracker.cpp, completeProgress, 284
2140  2165 WebCore/loader/ResourceLoadNotifier.cpp, dispatchDidFinishLoading, 194
2140  2165 WebCore/loader/ResourceLoader.cpp, releaseResources, 98
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, resourceLoadScheduler, 78
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, remove, 166
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, hostForURL, 59
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, remove, 339
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, scheduleServePendingRequests, 278


// SubresourceLoader加载完成，清除相关对象
2140  2165 WebCore/loader/SubresourceLoader.cpp, ~SubresourceLoader, 66
2140  2165 WebCore/loader/ResourceLoader.cpp, ~ResourceLoader, 89
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, finish, 388
2140  2165 WebCore/platform/network/android/ResourceHandleAndroid.cpp, ~ResourceHandle, 57
2140  2165 WebCore/platform/network/android/ResourceHandleAndroid.cpp, ~ResourceHandleInternal, 49
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoader.cpp, ~WebUrlLoader, 53
2140  2165 WebKit/android/WebCoreSupport/WebRequest.cpp, ~WebRequest, 122
2140  2165 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, ~WebUrlLoaderClient, 95


2140  2165 WebCore/loader/ProgressTracker.cpp, estimatedProgress, 98
2140  2165 WebKit/android/WebCoreSupport/ChromeClientAndroid.cpp, layersSync, 73
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, requestTimerFired, 289
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, servePendingRequests, 197
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, servePendingRequests, 227
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, hasRequests, 361
2140  2165 WebCore/loader/ResourceLoadScheduler.cpp, ~HostInformation, 309

2140  2154 WebKit/android/WebCoreSupport/WebCookieJar.cpp, get, 130
2140  2154 WebKit/android/WebCoreSupport/WebCookieJar.cpp, instance, 119
2140  2154 WebKit/android/WebCoreSupport/WebCookieJar.cpp, allowCookies, 182
2140  2154 WebKit/android/WebCoreSupport/WebCookieJar.cpp, flush, 290
2140  2154 WebKit/android/WebCoreSupport/WebCookieJar.cpp, get, 130
2140  2154 WebKit/android/WebCoreSupport/WebCookieJar.cpp, instance, 119
2140  2154 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, ioThread, 53
2140  2154 WebKit/android/WebCoreSupport/WebCookieJar.cpp, get, 130
2140  2154 WebKit/android/WebCoreSupport/WebCookieJar.cpp, instance, 119
2140  2154 WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp, ioThread, 53
2140  2165 WebKit/android/WebCoreSupport/PlatformBridge.cpp, actualMemoryUsageMB, 314
2140  2140 tate()
2140  2140 
2140  2140 
1431  1461 ut
2140  2165 WebCore/loader/icon/IconDatabase.cpp, syncTimerFired, 986
2140  2165 WebCore/loader/icon/IconDatabase.cpp, wakeSyncThread, 949
2140  2171 WebCore/loader/icon/IconDatabase.cpp, writeToDatabase, 1788
2140  2171 WebCore/loader/icon/IconDatabase.cpp, writeIconSnapshotToSQLDatabase, 2317
2140  2171 WebCore/loader/icon/IconDatabase.cpp, getIconIDForIconURLFromSQLDatabase, 2188
2140  2171 WebCore/loader/icon/IconDatabase.cpp, readySQLiteStatement, 2109
2140  2171 WebCore/loader/icon/IconDatabase.cpp, readySQLiteStatement, 2109
2140  2171 WebCore/loader/icon/IconDatabase.cpp, readySQLiteStatement, 2109
2140  2171 WebCore/loader/icon/IconDatabase.cpp, setIconURLForPageURLInSQLDatabase, 2127
2140  2171 WebCore/loader/icon/IconDatabase.cpp, getIconIDForIconURLFromSQLDatabase, 2188
2140  2171 WebCore/loader/icon/IconDatabase.cpp, readySQLiteStatement, 2109
2140  2171 WebCore/loader/icon/IconDatabase.cpp, setIconIDForPageURLInSQLDatabase, 2149
2140  2171 WebCore/loader/icon/IconDatabase.cpp, readySQLiteStatement, 2109
2140  2171 WebCore/loader/icon/IconDatabase.cpp, checkForDanglingPageURLs, 1941
2140  2171 WebCore/loader/icon/IconDatabase.cpp, shouldStopThreadActivity, 1121
2140  2171 WebCore/loader/icon/IconDatabase.cpp, readFromDatabase, 1675
2140  2171 WebCore/loader/icon/IconDatabase.cpp, shouldStopThreadActivity, 1121
2140  2171 WebCore/loader/icon/IconDatabase.cpp, pruneUnretainedIcons, 1852
2140  2171 WebCore/loader/icon/IconDatabase.cpp, isOpen, 1004
2140  2171 WebCore/loader/icon/IconDatabase.cpp, checkForDanglingPageURLs, 1941
2140  2171 WebCore/loader/icon/IconDatabase.cpp, shouldStopThreadActivity, 1121
2140  2171 WebCore/loader/icon/IconDatabase.cpp, writeToDatabase, 1788
2140  2171 WebCore/loader/icon/IconDatabase.cpp, shouldStopThreadActivity, 1121
2140  2171 WebCore/loader/icon/IconDatabase.cpp, readFromDatabase, 1675
2140  2171 WebCore/loader/icon/IconDatabase.cpp, shouldStopThreadActivity, 1121
2140  2171 WebCore/loader/icon/IconDatabase.cpp, shouldStopThreadActivity, 1121
2140  2171 WebCore/loader/icon/IconDatabase.cpp, shouldStopThreadActivity, 1121
