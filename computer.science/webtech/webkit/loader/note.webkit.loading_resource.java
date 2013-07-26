Android Webkit LoadUrl process


需要研究的代码:
jiangrui@jiangrui-desktop:~/dev_branch/packages/apps/WidgetEngine$ ls WEN/WebKit/android/jni/*.cpp
WEN/WebKit/android/jni/JavaBridge.cpp             WEN/WebKit/android/jni/WebFrameView.cpp
WEN/WebKit/android/jni/JavaSharedClient.cpp       WEN/WebKit/android/jni/WebHistory.cpp
WEN/WebKit/android/jni/PictureSet.cpp             WEN/WebKit/android/jni/WebIconDatabase.cpp
WEN/WebKit/android/jni/WebCoreFrameBridge.cpp     WEN/WebKit/android/jni/WebSettings.cpp
WEN/WebKit/android/jni/WebCoreJni.cpp             WEN/WebKit/android/jni/WebViewCore.cpp
WEN/WebKit/android/jni/WebCoreResourceLoader.cpp
WebCore::ResourceRequest
WebCore::ResourceResponse

BrowserFrame.java
    CallbackProxy
        WebViewClient
        WebChromeClient: 一些对话框的实现
        DownloadListener: 下载时需要弹出的对话框, ui逻辑

BrowserFrame()
    sJavaBridge = new JWebCoreJavaBridge();
    CacheManager.init(context);
    CookieSyncManager.createInstance(context);

    //创建c++ WebFrame等结构
    nativeCreateFrame()
        创建WebFrame::WebFrame时,会将java传过来的WebViewCore对象保存在mObj中.
        然后调用java时,通过mObj调用jni.

框架载入

WebKit 源码中的FrameLoader类，是专门负责将文档载入到Frame中。当用户点击一个连接时，FrameLoader会得到通知， 并且重新创建一个DocumentLoader对象用来载入文档。DocumentLoader此时并不会直接去访问网络下载数据，而是会首先访问 WebKit的Client对象的shouldOverrideURL方法，决定是否应该载入指定的URL数据。如果该URL允许载入，则 DocumentLoader会创建MainResourceLoader对象，该对象使用ResourceHandle接口与平台相关的网络库通信。 MainResourceLoader会从网络上获取Frame的信息，然后交给相应的解析器解析。

ResourceRequest
    ResourceRequestBase
        represent a http request
            http method/headers/body
    created in WorkerThreadableLoader::MainThreadBridge::mainThreadCreateLoader
        OwnPtr<ResourceRequest> request(ResourceRequest::adopt(requestData));

ResourceResponse
    ResourceResponseBase
        represent a http response
        http status code/text/headers/data

ResourceHandle
    handle network loading
    ResourceHandle.cpp
    ResourceHandleAndroid.cpp

ResourceHandleClient
    provide callback interface after resource is loaded.
    class inherit:
        ResourceHandleClient<-ResourceLoader<-MainResourceLoader
    
MainResourceLoader
    FrameLoader和ResourceHandler的controller
    调用其函数

FrameLoaderClient

关键的几条线索
    AddData
    didReceiveResponse
    didReceiveData
    didFinishLoading

Java层加载资源的入口:
UI->
BrowserFrame::loadUrl(String url)
    nativeLoadUrl(url);
        |    jni to c++
        V
     LoadUrl()->FrameLoader::load()->FrameLoader::load()->
     FrameLoader::loadWithDocumentLoader()->FrameLoader::checkNavigationPolicy()->
     FrameLoaderClientAndroid::dispatchDecidePolicyForNavigationAction()->
     FrameLoader::continueAfterNavigationPolicy()
     PolicyCheck::call()->FrameLoader::callContinueLoadAfterNavigationPolicy()->
     FrameLoader::continueLoadAfterNavigationPolicy()->
     FrameLoader::continueLoadAfterWillSubmitForm()->
     DocumentLoader::startLoadingMainResource()->
     MainResourceLoader::load()->MainResourceLoader::loadNow()->
     ResourceHandle::create()->ResourceHandle::start()->（WebCore/platform/network/android/ResourceHandleAndroid.cpp）
     WebFrame::startLoadingResource()
        |    jni to java
        V
    startLoadingResource()
        loadListener = LoadListener.getLoadListener(); 
        loader = new FrameLoader();
        loader.setHeaders(headers);
        loader.setPostData(postData);
        loader.executeLoad();
            FrameLoader::executeLoad()
                 Network.getInstance(mListener.getContext());
                handleHTTPLoad();
                    if (handleCache())
                        则从cache中获取
                    mNetwork.requestURL(mMethod, mHeaders, mPostData, mListener, mIsHighPriority);
                        in Network.java
                        new RequestQueue 如果没有q
                         RequestHandle handle = q.queueRequest()
                         loader.attachRequestHandle(handle); //将requestHandle放到loaderListener中
                        如果需要同步加载, 则等待
                            |
                            |    从网络获取到数据后
                            V
"base/core/java/android/net/http/Request.java"
Request::readResponse()
    mEventHandler.data(buf, count);___
    mEventHandler.endData();         |
                                     V
        LoadListener::data()
            sendMessageInternal(obtainMessage(MSG_CONTENT_DATA));
            LoadListener::commitLoad()
                 nativeAddData(c.mArray, c.mLength);
                    |
                    V jni to c++
        WebCoreResourceLoader::AddData()->ResourceLoader::didReceiveData()
        MainResourceLoader::didReceiveData ()->ResourceLoader::didReceiveData()
        MainResourceLoader::addData()->FrameLoader::receivedData()
        DocumentLoader::receivedData()->DocumentLoader::commitLoad()
        FrameLoader::committedLoad()->FrameLoaderClientAndroid::committedLoad()
        FrameLoader::addData()->FrameLoader::write()->

        对获取的html文件进行解析
        HTMLTokenizer::write()->HTMLTokenizer::parseTag()->HTMLTokenizer::processToken()
        HTMLParser::parseToken()->HTMLParser::insertNode()->ContainerNode::addChild()->
        HTMLLinkElement::insertedIntoDocument()->HTMLLinkElement::process()->

        加载css文件
        DocLoader::requestCSSStyleSheet()->DocLoader::requestResource()->
        Cache::requestResource()->CachedResource::load()->CachedResource::load()
        Loader::load()->Loader::Host::servePendingRequests()->
        SubresourceLoader::create()->ResourceLoader::load()->ResourceHandle::create()
        ResourceHandle::start()->WebFrame::startLoadingResource()
            |
            V jni 2 java

WebKit将预载入的资源分成了两类
    文档, 如html, xml, ...
        使用FrameLoader加载， 将文档载入到Frame中

    文档中所使用的资源（图片、脚本、CSS等）

加载文档：
    当用户点击一个连接时，FrameLoader会得到通知

    创建一个DocumentLoader对象用来载入文档

    DocumentLoader访问WebKit的Client对象的shouldOverrideURL方法，决定是否应该载入指定的URL数据
    如果该URL允许载入，DocumentLoader创建MainResourceLoader对象

    MainResourceLoader使用ResourceHandle接口与平台相关的网络库通信。
        WebCore/platform/network/ResourceHandle.cpp
        WebCore/platform/network/android/ResourceHandleAndroid.cpp）
   
    MainResourceLoader会从网络上获取Frame的信息，然后交给相应的解析器解析。

基本类：
WebCore/platform/network/android/ResourceRequest.h
WebCore/platform/network/ResourceRequestBase.h
    封装一个http加载请求， 保存url, http请求头， http method(get, post,) , cache策略， timeout

WebCore/platform/network/ResourceHandleClient.h
    当request的状态发生变化时， ResourceHanlderClient会收到通知
        如收到响应， 收到数据， 加载完毕等等
        子类有： ResourceLoader， MainResourceLoader，


加载文档失败显示失败页面的流程
    失败显示的页面为：
./frameworks/base/core/res/res/raw-en-rGB/loaderror.html

WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp
FrameLoaderClientAndroid::dispatchDidFailProvisionalLoad(const ResourceError& error)
     String filename = m_webFrame->getRawResourceFilename(LOADERROR);
    Asset* a = am->openNonAsset(filename.utf8().data(), Asset::ACCESS_BUFFER);
    loadDataIntoFrame(m_frame, KURL(data), error.failingURL(), s);


允许自动加载图片的过程：
    ./WebKit/android/jni/WebSettings.cpp
        WebSettings::Sync
                    jboolean flag = env->GetBooleanField(obj, gFieldIds->mLoadsImagesAutomatically);
                    s->setLoadsImagesAutomatically(flag);
                    if (flag)
                        docLoader->setAutoLoadImages(true);


CachedImage* DocLoader::requestImage(const String& url)
    CachedImage* resource = static_cast<CachedImage*>(requestResource(CachedResource::ImageResource, url, String()));
    if (autoLoadImages() && resource && resource->stillNeedsLoad())
        resource->setLoading(true);
        cache()->loader()->load(this, resource, true);
    return resource;

void CachedImage::load(DocLoader* docLoader)
    if (!docLoader || docLoader->autoLoadImages())
        CachedResource::load(docLoader, true, DoSecurityCheck, true);
    else    
        m_loading = false;
}






    








