./ResourceLoader.cpp
    1. lower layer : WebCore.platform.network
        send command : start, cancel, pause to lower layer
            by ResourceHandle

        receive data and status callback from ResourceHandle
        send callback to FrameLoader's notifier

    2. has a SharedBuffer for network data

    3. reference to Frame, DocumentLoader

    4. could be added into ResourceLoadScheduler

    5. has an identifier

    6. handle first party for cookie
    7. handle defer loading
    8. clear auth and pending substitute load when didCancel
    9.handle auth challenge
    10.set content sniff

ResourceLoadNotifier.cpp
    1. send data and status callbacks from ResourceLoader to FrameLoaderClientAndroid
    2. apply user agent to FrameLoader
    3. notify progress change to Page
    4. add response to DocumentLoader
    5. profile loading time by InspectorInstrumentation

MainResourceLoader.cpp
    1. implement ResourceLoader
    2. reference to DocumentLoader
        set request to DocumentLoader
        set response to DocumentLoader
        send data to DocumentLoader

    3. invoke content policy logic
        implemented by FrameLoaderClientAndroid
            decide whether show or download 

    4. handle defer loading
    5. handle substitute data loading
    6. handle redirect response
        update redirect information to DocumentLoadTiming

    7. handle empty load
    8. handle data load

    9. has a data loading timer to handle defer load substitude data
    10. has a flag to indicate loading multi-part content
    11. has a m_timeOfLastDataReceived to remember last time when receiving network data
        which is set to DocumentLoadTiming in DocumentLoader
    12. inform FrameLoader if error happens 
        by receivedMainResourceError()

ResourceLoadScheduler.cpp

SubresourceLoaderClient.h
SubresourceLoader.cpp
SubresourceLoader.h

DocumentLoader.cpp
DocumentLoader.h
DocumentWriter.cpp
DocumentWriter.h

FrameLoaderClient.h
FrameLoader.cpp
FrameLoader.h
FrameLoaderStateMachine.cpp
FrameLoaderStateMachine.h
FrameLoaderTypes.h

ImageLoader.cpp
ImageLoader.h

SubframeLoader.cpp
SubframeLoader.h

PolicyCallback.cpp
PolicyCallback.h
PolicyChecker.cpp
PolicyChecker.h
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

