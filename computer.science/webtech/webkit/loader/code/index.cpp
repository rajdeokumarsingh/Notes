What is Navigation?

/*WebKit 源码中的FrameLoader类，是专门负责将文档载入到Frame中。
    用户点击一个连接时，FrameLoader创建一个DocumentLoader对象用来载入文档
    DocumentLoader会首先访问 WebKit的Client对象>的shouldOverrideURL方法
        决定是否应该载入指定的URL数据
        如果该URL允许载入，则DocumentLoader会创建MainResourceLoader对象
            MainResourceLoader使用ResourceHandle接口与平台相关的网络库通信
            MainResourceLoader从网络上获取Frame的信息，然后交给相应的解析器解析

FrameLoader-->DocumentLoader-JNI->Browser(shouldOverrideURL)
        -JNI->DocumentLoader-->MainResourceLoader-->ResourceHandle
*/

/* 
Loader模块
    Network 模块的客户。 
    Loader接触到的基本上是同OS和HTTP实现无关的Network 层接口。 

Network模块
    提供指定资源的下载和上传功能，获取的资源可能来自网络、本地文件或者缓存。
    对不同 HTTP 实现的适配会在 Network 层完成
*/

//////////////////////////////////////////////////////////////////////
// Network模块
//////////////////////////////////////////////////////////////////////
   
    #include "ResourceRequest.h"
    #include "ResourceRequestBase.h"
        /* 封装一个http请求， 
            保存url, http请求头, http method(get, post),cache策略,timeout等等
        created in 
            WorkerThreadableLoader::MainThreadBridge::mainThreadCreateLoader
            OwnPtr<ResourceRequest> request(ResourceRequest::adopt(requestData))
        */

    #include "ResourceResponse.h"
    #include "ResourceResponseBase.h"
        /* 封装一个http响应
            http status code/text/headers/data
        */

    #include "ResourceHandle.h"
        // webkit层http加载的接口层
        // 1. 向平台网络层(mac, win, android)发送http请求
        // 2. 接收到网络响应后，
        //      调用ResourceHandle回调接口, 通知client
        ResourceHandle
            WebCore/platform/network/android/ResourceHandleAndroid.cpp

            -->ResourceHandleClient
                // invoke callback function

            -->ResourceHandleInternal
                -->ResourceLoaderAndroid
                    -->WebKit/android/WebCoreSupport/ResourceLoaderAndroid.cpp
                        // real loading worker

            -->ResourceRequest

    #include "ResourceHandleClient.h"
        接口类
        提供资源加载相关的callback接口

    // 继承关系
    ResourceRequestBase
        <|-ResourceRequest

    ResourceResponseBase
        <|-ResourceResponse

    ResourceHandleClient
        <|-ResourceLoader
        <|-MainResourceLoader

    ResourceHandle
        -->ResourceLoaderAndroid
            -->WebKit/android/WebCoreSupport/ResourceLoaderAndroid.cpp
                // real loading worker

        -->ResourceHandleClient
            // invoke callback function

        -->ResourceHandleInternal
        -->ResourceRequest

////////////////////////////////////////////////////////////////////////////////
// 加载模块
////////////////////////////////////////////////////////////////////////////////
/*
WebKit将预载入的资源分成了两类
    1. 文档/主资源, 如html, xml, ...
        使用FrameLoader加载， 将文档载入到Frame中
        MainResourceLoader

    2.子资源，文档中所使用的资源（图片、脚本、CSS等）
        SubresourceLoader

    主资源的加载是立刻发起的
    而派生资源则可能会为了优化网络，在队列中等待(这里的立刻发起是loader层面的，不是Network层面的) 

    ResourceScheduler 这个类就是用来管理资源加载的调度。
        主要调度对象就是派生资源，会根据 host 来影响资源加载的先后顺序

    两类资源对于回调的处理有很大的不同:
        比如，同样是下载失败，
            主资源可能需要向用户报错
            派生资源比如页面中的一张图下载失败，
                可能就是图不显示或者显示代替说明文字而已，不向用户报错
            
        它们的公共基类 ResourceLoader 则完成一些两种资源下载都需要完成的操作
            比如通过回调将加载进程告知上层应用。 

        主资源目前是没有缓存的，而派生资源是有缓存机制的。
            这里的缓存指的是 Resouce Cache 
                用于保存原始数据（比如 CSS ， JS 等），以及解码过的图片数据
                通过 Resource Cache 可以节省网络请求和图片解码的时候

            不同于Page Cache ，Page Cache存的是DOM树和Render树的数据结构
                用来在前进后退的时候快速显示页面。

文档加载的基本流程：
    1. 当用户点击一个连接时，FrameLoader得到通知
    2. 创建一个DocumentLoader对象用来载入文档

    3. DocumentLoader访问WebKit的Client对象的shouldOverrideURL方法，
        决定是否应该载 入指定的URL数据
    4. 如果该URL允许载入，DocumentLoader创建MainResourceLoader对象

    5. MainResourceLoader使用ResourceHandle接口与平台相关的网络库通信。
        WebCore/platform/network/ResourceHandle.cpp
        WebCore/platform/network/android/ResourceHandleAndroid.cpp）

    6. MainResourceLoader会从网络上获取Frame的信息，然后交给相应的解析器解析。


ResourceLoader通过ResourceNotifier类将回调传导到FrameLoaderClient类


三、主资源加载过程
    1.DocumentLoader调用MainResourceLoader::load向loader发起请求
    2.调用MainResourceLoader::loadNow
    3.调用MainResourceLoader::willSendRequest
    4.调用ResourceLoader::willSendRequest,将callback通过ResourceNotifier传导给FrameLoaderClient。
        Client可以在回调中操作ResourceRequest，比如设置请求头部。

    5.调用PolicyChecker::checkNavigationPolicy过滤掉重复请求等
        Android中处理了"Reset multiple form submission protection."
        
    6.loader调用ResourceHandle::create向Network发起加载请求

    7.收到第一个HTTP响应数据包,Network回调
        MainResourceLoader::didReceiveResponse，主要处理HTTP头部。

    8.调用PolicyChecker::checkContentPolicy,并最终通过FrameLoaderClient的
        dispatchDecidePolicyForMIMEType判断是否为下载请求（存在"Content-Disposition"http头部）

        Android:
            FrameLoaderClientAndroid::dispatchDecidePolicyForResponse
            canShowMIMEType

    9.调用MainResourceLoader::continueAfterContentPolicy，根据ResourceResponse检测是否发生错误。

    10.调用ResourceLoader::didReceiveResponse，将callback通过ResourceNotifier传导给FrameLoaderClient。

    11.收到HTTP体部数据，调用MainResourceLoader::didReceiveData
    
    12.调用ResourceLoader::didReceiveData，将callback通过ResourceNotifier传导给FrameLoaderClient

    13.调用MainResourceLoader::addData

    14.调用DocumentLoader::receivedData

    15.调用DocumentLoader::commitLoad

    16.调用FrameLoader::commitProvisionalLoad，
        FrameLoader从provisional状态跃迁到Committed状态

    17.调用FrameLoaderClientQt::committedLoad

    18.调用DocumentLoader::commitData，
        启动Writer对象来处理数据（DocumentWriter::setEncoding，DocumentWriter::addData）

    19.调用DocumentWriter::addData

    20.调用DocumentParser::appendByte

    21.调用DecodedDataDocumentParser::appendBytes对文本编码进行解码

    22.调用HTMLDocumentParser::append，进行HTML解析

    23.数据来齐，调用MainResourceLoader::didFinishLoading

    24.调用FrameLoader::finishedLoading
    25.调用DocumentLoader::finishedLoading
    26.调用FrameLoader::finishedLoadingDocument，
        启动writer对象接收剩余数据，重复19-22进行解析
    27.调用DocumentWriter::end结束接收数据（调用Document::finishParsing）
    28.调用HTMLDocumentParser::finish
    */

/*
四、派生资源加载流程
  在派生资源的加载中， SubresourceLoader更多起到的是一个转发的作用，
    通过它的client（SubresourceLoaderClient类）来完成操作。 

   各个加载阶段的处理在 SubresourceLoaderClient 的派生类 CachedResourceRequest,Loader,IconLoader 中完成。 Client 会创建 SubresourceLoader 。
   请求发起阶段， ResourceLoadScheduler 负责对 SubresourceLoader 进行调度。 

    Document类会创建CachedResourceLoader类的对象 m_cachedResourceLoader, 
        这个类(对象)提供了对Document 的派生资源的访问接口
            requestImage，requestCSSStyleSheet，requestUserCSSStyleSheet，requestScript，requestFont，requestXSLStyleSheet ， requestLinkPrefetch 。
            为了实现这些接口， CachedResourceLoader需要创建CachedResourceRequest对象来发起请求

    一般情况下，一个Document拥有一个CachedResourceLoader类实例。
    MemoryCache 类则对提供缓存条目的管理，可以方便地进行add ，remove，缓存淘汰等。
        具体的缓存条目则是通过 CachedResource 类存储，
        MemoryCache 类维护了一个 HashMap 存储所有缓存条目。
        HashMap <String,CachedResource> m_resources; 
*/


    ResourceLoader.cpp
        #include "ResourceLoader.h"
        <|-ResourceHandleClient // 实现callback client接口

        // http传输
        -->ResourceHandle 
        -->ResourceRequest
        -->ResourceResponse
            -->RefPtr<SharedBuffer> m_resourceData;

        -->DocumentLoader
        -->Frame
            -->FrameLoader

        // 文档和资源加载client的基类
        1. 保存了http request类和http response类
        2. start()方法中创建ResourceLoader, 并开始加载
        3. 使用一个buffer缓存接收到的数据
        4. 调用FrameLoader中的callback
        5. cancel/fail是释放资源

    MainResourceLoader.cpp
        <|-ResourceLoader // 实现callback client接口
        // 实现和main resource(html文档？)加载相关的逻辑

        -->Frame

        -->ResourceRequest m_initialRequest;
        -->SubstituteData m_substituteData;

        -->MainResourceLoaderTimer m_dataLoadTimer;

        由DocumentLoader创建

        // 新的接口
            // 判断一个资源是需要展示，下载或忽略
        static void callContinueAfterNavigationPolicy(
                void*, const ResourceRequest&, PassRefPtr<FormState>, 
                bool shouldContinue);
        void continueAfterNavigationPolicy(
                const ResourceRequest&, bool shouldContinue);
        static void callContinueAfterContentPolicy(void*, PolicyAction);
        void continueAfterContentPolicy(PolicyAction);
        void continueAfterContentPolicy(PolicyAction, const ResourceResponse&);
 
        void MainResourceLoader::addData(const char* data, 
                int length, bool allAtOnce)

            // 将数据添加到DocumentLoader中
            documentLoader()->receivedData(data, length);

    DocumentLoader.cpp
        // created by FrameLoader
        -->Frame
        -->MainResourceLoader (create)
            RefPtr<SharedBuffer> m_mainResourceData;

        -->ResourceLoaderSet m_subresourceLoaders // frame中所有的ResourceLoader
        -->ResourceLoaderSet m_multipartSubresourceLoaders;
        -->ResourceLoaderSet m_plugInStreamLoaders;
             typedef HashSet<RefPtr<ResourceLoader> > ResourceLoaderSet;

        -->ResourceRequest m_originalRequest;   
        -->ResourceRequest m_request;
        -->ResourceResponse m_response;
        -->SubstituteData m_substituteData;


        -->DocumentWriter m_writer;


    SubresourceLoader.cpp
        // created by ResourceLoadScheduler::scheduleSubresourceLoad

    ResourceLoadNotifier.cpp
    ResourceLoadScheduler.cpp
    ImageLoader.cpp

    SubframeLoader.cpp
    DocumentWriter.cpp
    FrameLoader.cpp
    FrameLoaderStateMachine.cpp
    DocumentThreadableLoader.cpp


    NetscapePlugInStreamLoader.cpp
    PingLoader.cpp

    ThreadableLoader.cpp
    WorkerThreadableLoader.cpp

    CrossOriginAccessControl.cpp
    CrossOriginPreflightResultCache.cpp
    FormState.cpp
    FormSubmission.cpp
    FTPDirectoryParser.cpp
    HistoryController.cpp
    NavigationAction.cpp
    NavigationScheduler.cpp
    PlaceholderDocument.cpp
    PolicyCallback.cpp
    PolicyChecker.cpp
    ProgressTracker.cpp
    SinkDocument.cpp
    TextResourceDecoder.cpp

    cache/
        CachedResourceLoader.cpp
        CachedResourceRequest.cpp
        CachedCSSStyleSheet.cpp
        CachedFont.cpp
        CachedImage.cpp
        CachedResource.cpp
        CachedResourceClientWalker.cpp
        CachedResourceHandle.cpp
        CachedScript.cpp
        CachedXSLStyleSheet.cpp
        MemoryCache.cpp

    icon
    appcache/
    archive/
    cf/
    mac
    win
