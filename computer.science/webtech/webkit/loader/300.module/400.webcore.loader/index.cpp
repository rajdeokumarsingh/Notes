main_res.log

./ResourceLoader.cpp

./ResourceLoadNotifier.cpp

./MainResourceLoader.cpp

./ResourceLoadScheduler.cpp

SubresourceLoaderClient.h
    just interface for loading callbacks
        willSendRequest didSendData didReceiveResponse didReceiveData didReceiveCachedMetadata didFinishLoading didFail
        getShouldUseCredentialStorage didReceiveAuthenticationChallenge receivedCancellation

SubresourceLoader.cpp
    delegate callback to SubresourceLoaderClient
    after create, SubResourceLoader will be added to DocumentLoader
    some multipart logic

./CachedResourceLoader.cpp

CachedResourceRequest
    implements SubresourceLoaderClient
    update cache relative field to ResourceRequest
    schedule load of subresource
    handle http 304
    send data to CachedResource

CachedImage.cpp

CachedResource.cpp
    implement RFC2616 13.2.3, 13.2.4, in currentAge(), freshnessLifetime()
    store decode/encode size


CachedCSSStyleSheet.cpp
CachedFont.cpp
CachedResourceClientWalker.cpp
CachedScript.cpp
CachedXSLStyleSheet.cpp
MemoryCache.cpp


./DocumentLoader.cpp
    ArchiveResource : public SubstituteResource 

./DocumentWriter.cpp

./PolicyCallback.cpp

PolicyChecker.cpp

FrameLoaderClient.h
FrameLoader.cpp

FrameLoaderStateMachine.cpp
FrameLoaderStateMachine.h

ImageLoader.cpp
ImageLoader.h

SubframeLoader.cpp
SubframeLoader.h


ProgressTracker.cpp
ProgressTracker.h



SubstituteData.h
SubstituteResource.h
TextResourceDecoder.cpp
TextResourceDecoder.h


================================================================================
CachedMetadata.h
CrossOriginAccessControl.cpp
CrossOriginAccessControl.h
CrossOriginPreflightResultCache.cpp
CrossOriginPreflightResultCache.h
DocumentLoadTiming.h
DocumentThreadableLoaderClient.h
DocumentThreadableLoader.cpp
DocumentThreadableLoader.h
EmptyClients.h
FormState.cpp
FormState.h
FormSubmission.cpp
FormSubmission.h
FrameNetworkingContext.h
FTPDirectoryParser.cpp
FTPDirectoryParser.h
HistoryController.cpp
HistoryController.h
NavigationAction.cpp
NavigationAction.h
NavigationScheduler.cpp
NavigationScheduler.h
NetscapePlugInStreamLoader.cpp
NetscapePlugInStreamLoader.h
PingLoader.cpp
PingLoader.h
PlaceholderDocument.cpp
PlaceholderDocument.h
SinkDocument.cpp
SinkDocument.h
ThreadableLoaderClient.h
ThreadableLoaderClientWrapper.h
ThreadableLoader.cpp
ThreadableLoader.h
WorkerThreadableLoader.cpp
WorkerThreadableLoader.h

================================================================================

drwxrwxr-x 2 jiangrui jiangrui   4096  2月 16  2013 appcache
drwxrwxr-x 4 jiangrui jiangrui   4096  2月 16  2013 archive
drwxrwxr-x 2 jiangrui jiangrui   4096  2月 16  2013 cache
drwxrwxr-x 3 jiangrui jiangrui   4096  2月 16  2013 icon

