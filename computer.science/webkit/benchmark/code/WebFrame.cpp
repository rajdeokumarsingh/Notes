// one instance of WebFrame per Page for calling into Java's BrowserFrame
// 对应一个BrowserFrame 
    // base/core/java/android/webkit/BrowserFrame.java

将WebKit的各种事件通知给java层
    如加载的开始，进度，收到title, 收到favicon, 结束, 错误, 需要下载等等
    添加，更新历史项
    创建新窗口，关闭窗口
    需要鉴权

通知java层WebKit加载网页的状态和进度相关事件 
    如请java层判断是否由其他应用程序去处理特殊的url, 
    加载开始，加载进度，title, favicon, 加载完成，加载错误等等.
{
    // 浏览器会调用shouldOverrideUrlLoading, 判断是否由java层去处理url加载。
    // 如rtsp，http流媒体等url需要提交给媒体播放器处理
    bool WebFrame::canHandleRequest(const WebCore::ResourceRequest& request)

    // 在加载资源之前，允许浏览器应用提供url的定制response
    // 如默认浏览器实现了为"content://"开头的url的提供response
    UrlInterceptResponse* shouldInterceptRequest(const WTF::String& url);

    // 通知java层加载已经开始了, 并传递favicon. Browser会更新状态和UI
    // BrowserFrame会通知webViewCore清除pending的绘制动作
    // 通知ChromeClient, 开始加载main frame了
    void WebFrame::loadStarted(WebCore::Frame* frame)
             
    // 通知java层下载
    void WebFrame::downloadStart(const std::string& url, const std::string& userAgent, 
            const std::string& contentDisposition, const std::string& mimetype, long long contentLength)

    // Indicates the WebKit has committed to the new load
    // 设置加载的状态
    void transitionToCommitted(WebCore::Frame* frame); 

    // 发送title给java层
    void WebFrame::setTitle(const WTF::String& title)

    // 发送进度到java层
    void WebFrame::setProgress(float newProgress)

    // 发送favicon到java层
    void WebFrame::didReceiveIcon(WebCore::Image* icon)
    void WebFrame::didReceiveTouchIconURL(const WTF::String& url, bool precomposed)

    // Indicates the end of a new load.
    // This method will be called once for the main frame.
    // 加载完成, 设置状态机
    void WebFrame::didFinishLoad(WebCore::Frame* frame)

    // java层没干啥事
    void WebFrame::didReceiveData(const char* data, int size)
    void WebFrame::didFinishLoading() 

    // 向java层上报错误
    void reportError(int errorCode, const WTF::String& description,
            const WTF::String& failingUrl);


    // 通过java层加载资源，4.0中没有调用
    virtual PassRefPtr<WebCore::ResourceLoaderAndroid> startLoadingResource(WebCore::ResourceHandle*,
            const WebCore::ResourceRequest& request, bool mainResource,
            bool synchronous);
}

历史相关接口 {
    // 通过WebHistory调用到java层
    void WebFrame::addHistoryItem(WebCore::HistoryItem* item)
        JNIEnv* env = getJNIEnv();
    WebHistory::AddItem(mJavaFrame->history(env), item);

    void WebFrame::removeHistoryItem(int index)
        void WebFrame::updateHistoryIndex(int newIndex)

    // 更新java层全局历史项
    void WebFrame::updateVisitedHistory(const WebCore::KURL& url, bool reload)
}

Javascript状态接口 {
    // 通知java层重新加载js接口
    void WebFrame::windowObjectCleared(WebCore::Frame* frame)
}

UI相关接口 
    创建tab，popup, 关闭窗口 {
    // 通知浏览器，创建popup dialog/tab
    WebCore::Frame* WebFrame::createWindow(bool dialog, bool userGesture)
        返回一个native的Frame
        src/com/android/browser/Tab.java onCreateWindow()中实现

    // 关闭标签
    void WebFrame::closeWindow(WebViewCore* webViewCore)

    // 通知当前webview获取focus
    void WebFrame::requestFocus() const

    // 获取显示密度
    float WebFrame::density() const
}

资源文件相关接口 {
    // 通过资源id获取资源的文件名, 用于获取Framework中的资源文件。
    // 如无法加载网页时，错误的html提示页面
    WTF::String WebFrame::getRawResourceFilename(WebCore::PlatformBridge::rawResId id) const

    jmethodID   mGetFileSize; // 获取资源文件的大小
    jmethodID   mGetFile;     // 获取资源文件，保存到提供的buffer中
}

隐私相关 {
    // when the native HTTP stack gets an authentication request
    // 以及浏览器相关自动保存密码的逻辑
    void WebFrame::didReceiveAuthenticationChallenge(WebUrlLoaderClient* client, const std::string& host, const std::string
    & realm, bool useCachedCredentials, bool suppressDialog)

    // 报告ssl错误给java层
    void WebFrame::reportSslCertError(WebUrlLoaderClient* client, int error, const std::string& cert, const std::string& url)

    // 向java层请求证书
    void WebFrame::requestClientCert(WebUrlLoaderClient* client, const std::string& hostAndPort)

    // Called by JNI when we recieve a certificate for the page's main resource.
    void WebFrame::setCertificate(const std::string& cert)

    // Called by JNI when processing the X-Auto-Login header.
    void WebFrame::autoLogin(const std::string& loginHeader)

    // 提示用户是否保存密码
    void WebFrame::maybeSavePassword(WebCore::Frame* frame, const WebCore::ResourceRequest& request)

    // 从网页中获取用户名和密码
    bool WebFrame::getUsernamePasswordFromDom(WebCore::Frame* frame, WTF::String& username, WTF::String& password)
}

form相关 {
    // 从http request的body中获取form数据
    // java层保存用户名，密码的时候，会检查从dom中获取的用户名和密码
    // 是否也在form数据中
    jbyteArray WebFrame::getPostData(const WebCore::ResourceRequest& request)

    // 保存表单数据到java层
    void saveFormData(WebCore::HTMLFormElement*)
        form数据的格式是：url + HashMap<String name, String value>
        mDatabase.setFormData(url, data);

    // 通知java层提示用户，是否重新提交表单数据
    void WebFrame::decidePolicyForFormResubmission(WebCore::FramePolicyFunction func)
}

