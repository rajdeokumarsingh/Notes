问题：

view width 
    物理屏幕的宽度
        s899t:540, t8808d:480

    是否是不变的?
        猜想应该不变

viewport width
    网页画布的大小
    是否是不变的?
        对于没有设置viewport width参数的网页
            一般都是WebView.DEFAULT_VIEWPORT_WIDTH (800)

viewport中内容的宽度, content width
    content width有时候比viewport的宽度小

    是否是不变的?
        对于没有设置viewport width参数的网页
            一般都是WebView.DEFAULT_VIEWPORT_WIDTH (800)
        设置了viewport width="123"
            宽度就是123

        设置了viewport width="device-width"
            宽度是
            width = Math.round(mWebView.getViewWidth() / 
                    mWebView.getDefaultZoomScale())
            // s899t viewWidth = 540, default zoom = 1.5
            // width = 360

text wrap的宽度
    text wrap有时候比viewport的宽度小

    和viewport width的关系
    是否是不变的?

基本公式:
mViewScale = mTextWrapScale =
    webViewWidth / mViewportWidth

什么是default zoom
    全局的缩放控制变量

概念，公式


http://10086.cn/m
显示的view port是800的， 而UC的却显示的正常。大概400多


下载html页面
<meta id="viewport" name="viewport" content="initial-scale=0.7; minimum-scale=0.7;maximum-scale=1.0; user-scalable=1;width=device-width; height=device-height;" />
发现页面也设置了viewport属性。

对比baidu的viewport属性：
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>

原来10086中使用了分号作为分隔符， baidu用的是逗号。


调用过程如下：

WebCore/html/HTMLMetaElement.cpp

    void HTMLMetaElement::parseMappedAttribute(Attribute* attr)
        else if (attr->name() == contentAttr) {
            m_content = attr->value();     
            process();
                |
                V
    void HTMLMetaElement::process()
        if (equalIgnoringCase(name(), "viewport"))
            document()->processViewport(m_content);
                |
                V
WebCore/dom/Document.cpp

    void Document::processViewport(const String& features)
        // features这里为initial-scale=0.7; minimum-scale=0.7;maximum-scale=1.0; ...
        m_viewportArguments = ViewportArguments();
        processArguments(features, (void*)&m_viewportArguments, &setViewportFeature);
        frame->page()->updateViewportArguments();

    void Document::processArguments(
        const String& features, void* data, 
        ArgumentsCallback callback)  // 解析完后会调用setViewportFeature, 
                                     // 这个函数仅仅对viewport进行字符串到枚举的转换
                        |
                        V // 唯独没有分号
                static bool isSeparator(UChar c)
                        return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '=' || c == ',' || c == '\0';

        // 将解析出viewport内容保存到WebSettings中
        frame()->settings()->setMetadataSettings(keyString, valueString);
            /* 常见的viewport参数
                width=(默认值 -1)
                    device-width;  // m_viewport_width = 0
                heigth=(默认值 -1)
                    device-height;  // m_viewport_height = 0
                initial-scale=(默认值 0)
                    0.6/1.2/...
                minimum-scale(默认值 0) 
                maximum-scale(默认值 0)
                user-scalable=(默认值 true)
                    no/0/false, ... 用户能否进行缩放
                target-densitydpi= (默认值 -1)
                    device-dpi;   // m_viewport_target_densitydpi = 0;
                    low-dpi;        // m_viewport_target_densitydpi = 120;
                    medium-dpi;     // m_viewport_target_densitydpi = 160;
                    high-dpi;       // m_viewport_target_densitydpi = 240;
                    200;
            */


WebCore/page/Page.cpp

void Page::updateViewportArguments()

    m_viewportArguments = mainFrame()->document()->viewportArguments();
    chrome()->dispatchViewportDataDidChange(m_viewportArguments);
            |
            V
WebCore/page/Chrome.cpp

void Chrome::dispatchViewportDataDidChange(const ViewportArguments& arguments) const
    m_client->dispatchViewportDataDidChange(arguments);
            |
            V
WebKit/android/WebCoreSupport/ChromeClientAndroid.cpp

void ChromeClientAndroid::dispatchViewportDataDidChange(const ViewportArguments& input) const {
    Document* doc = m_webFrame->page()->mainFrame()->document();
    if (!doc->ownerElement()) {
        FrameView* view = doc->view();
        if (view)
            PlatformBridge::updateViewport(view);
                |
                V
WebKit/android/WebCoreSupport/PlatformBridge.cpp
void PlatformBridge::updateViewport(FrameView* frameView)
    android::WebViewCore* webViewCore = android::WebViewCore::getWebViewCore(frameView);
    webViewCore->updateViewport();
        |
        V
WebKit/android/jni/WebViewCore.cpp

void WebViewCore::updateViewport()
    env->CallVoidMethod(javaObject.get(), m_javaGlue->m_updateViewport);
            |
            |  JNI 2 Java
            V
base/core/java/android/webkit/WebViewCore.java
    private void updateViewport() {
        setupViewport(true);

private void setupViewport(boolean updateViewState)  // updateViewState == true

    // set the viewport settings from WebKit
    setViewportSettingsFromNative();
                            |
                            | JNI 2 Cpp, 将cpp层的view port参数设置到java层
                            V
                        // 从Settings中获取view port内容，设置到java层
                        static void SetViewportSettingsFromNative(JNIEnv *env, jobject obj)
                            WebViewCore* viewImpl = GET_NATIVE_VIEW(env, obj);
                            env->SetIntField(obj, gWebViewCoreFields.m_viewportWidth, s->viewportWidth());
                            env->SetIntField(obj, gWebViewCoreFields.m_viewportHeight, s->viewportHeight());
                            env->SetIntField(obj, gWebViewCoreFields.m_viewportInitialScale, s->viewportInitialScale());
                            env->SetIntField(obj, gWebViewCoreFields.m_viewportMinimumScale, s->viewportMinimumScale());
                            env->SetIntField(obj, gWebViewCoreFields.m_viewportMaximumScale, s->viewportMaximumScale());
                            env->SetBooleanField(obj, gWebViewCoreFields.m_viewportUserScalable, s->viewportUserScalable());
                            env->SetIntField(obj, gWebViewCoreFields.m_viewportDensityDpi, s->viewportTargetDensityDpi());

    // clamp initial scale
    if (mViewportInitialScale > 0) {
        if (mViewportMinimumScale > 0) {
            mViewportInitialScale = Math.max(mViewportInitialScale,
                    mViewportMinimumScale);
        }
        if (mViewportMaximumScale > 0) {
            mViewportInitialScale = Math.min(mViewportInitialScale,
                    mViewportMaximumScale);
        }
    }

    // 如果强制允许用户缩放
    if (mSettings.forceUserScalable()) {
        mViewportUserScalable = true;
        if (mViewportInitialScale > 0) {
            if (mViewportMinimumScale > 0) {
                mViewportMinimumScale = Math.min(mViewportMinimumScale,
                        mViewportInitialScale / 2);
            }
            if (mViewportMaximumScale > 0) {
                mViewportMaximumScale = Math.max(mViewportMaximumScale,
                        mViewportInitialScale * 2);
            }
        } else {
            if (mViewportMinimumScale > 0) {
                mViewportMinimumScale = Math.min(mViewportMinimumScale, 50);
            }
            if (mViewportMaximumScale > 0) {
                mViewportMaximumScale = Math.max(mViewportMaximumScale, 200);
            }
        }
    }

    // adjust the default scale to match the densityDpi
    // float adjust = WebView.DEFAULT_DENSITY;
    float adjust = 1.0f;

    // 网页没有指定dpi参数（一般都不会指定）
    if (mViewportDensityDpi == -1) {
        // 用户调整了浏览器中"默认缩放"设置
        if (mWebView != null && (int)(mWebView.getDefaultZoomScale() * 100) != 100) {
            adjust = mWebView.getDefaultZoomScale();
        }
    } else if (mViewportDensityDpi > 0) { // 网页指定了dpi参数
        adjust = (float) mContext.getResources().getDisplayMetrics().densityDpi
            / mViewportDensityDpi;
    }

    // 调整zoom
    if (adjust != mWebView.getDefaultZoomScale()) {
        Message.obtain(mWebView.mPrivateHandler,
                WebView.UPDATE_ZOOM_DENSITY, adjust).sendToTarget();
                        | 
                        V // 更新"默认缩放"设置
                mZoomManager.updateDefaultZoomDensity(density);
    }

    // 根据adjust调整各个参数, 感觉浏览器中"默认缩放"参数是一个全局的控制变量
    // 各项其他参数都会和它相乘，然后得出最终值
    int defaultScale = (int) (adjust * 100);

    if (mViewportInitialScale > 0) {
        mViewportInitialScale *= adjust;
    }
    if (mViewportMinimumScale > 0) {
        mViewportMinimumScale *= adjust;
    }
    if (mViewportMaximumScale > 0) {
        mViewportMaximumScale *= adjust;
    }

    // infer the values if they are not defined.
    if (mViewportWidth == 0) {  // device-width
        if (mViewportInitialScale == 0) {
            mViewportInitialScale = defaultScale;
        }
    }

    // 如果网页规定了禁止用户缩放
    if (mViewportUserScalable == false) {
        mViewportInitialScale = defaultScale;
        mViewportMinimumScale = defaultScale;
        mViewportMaximumScale = defaultScale;
    }

    // if mViewportWidth is 0, it means device-width, always update.
    if (mViewportWidth != 0 && !updateViewState) {
        // For non standard load, since updateViewState will be false.
        mFirstLayoutForNonStandardLoad = true;
        ViewState viewState = new ViewState();
        viewState.mMinScale = mViewportMinimumScale / 100.0f;
        viewState.mMaxScale = mViewportMaximumScale / 100.0f;
        viewState.mDefaultScale = adjust;
        // as mViewportWidth is not 0, it is not mobile site.
        viewState.mMobileSite = false;
        // for non-mobile site, we don't need minPrefWidth, set it as 0
        viewState.mScrollX = 0;
        viewState.mShouldStartScrolledRight = false;
        Message.obtain(mWebView.mPrivateHandler,
                WebView.UPDATE_ZOOM_RANGE, viewState).sendToTarget();
                    |
                    V
        mZoomManager.updateZoomRange(viewState, getViewWidth(), viewState.mScrollX);
        return;
    }

    // webViewWidth refers to the width in the view system
    // 物理显示屏幕的宽度, 320, 480, 540
    int webViewWidth;

    // viewportWidth refers to the width in the document system
    // 网页内容的宽度, 800, 320, ...
    int viewportWidth = mCurrentViewWidth;

    if (viewportWidth == 0) 
        webViewWidth = mWebView.getViewWidth();
        viewportWidth = (int) (webViewWidth / adjust); // 初始的viewport
    else 
        webViewWidth = Math.round(viewportWidth * mCurrentViewScale);

    mInitialViewState = new ViewState();
    mInitialViewState.mMinScale = mViewportMinimumScale / 100.0f;
    mInitialViewState.mMaxScale = mViewportMaximumScale / 100.0f;
    mInitialViewState.mDefaultScale = adjust;
    mInitialViewState.mScrollX = mRestoredX;
    mInitialViewState.mScrollY = mRestoredY;

    mInitialViewState.mMobileSite = (0 == mViewportWidth);

    /* 设置mViewScale, mTextWrapScale
       text wrap length是网页中文字自动换行的长度
     */
    if (mIsRestored) {   // history前进后退？
        mInitialViewState.mIsRestored = true;
        mInitialViewState.mViewScale = mRestoredScale;
        if (mRestoredTextWrapScale > 0) {
            mInitialViewState.mTextWrapScale = mRestoredTextWrapScale;
        } else {
            mInitialViewState.mTextWrapScale = mInitialViewState.mViewScale;
        }
    } else {
        if (mViewportInitialScale > 0) {  // 网页中指明了initial-scale
            mInitialViewState.mViewScale = 
            mInitialViewState.mTextWrapScale =
                mViewportInitialScale / 100.0f;
        } else if (mViewportWidth > 0 && mViewportWidth < webViewWidth &&
                !getSettings().getUseFixedViewport()) {
            // 网页中没有指名initial-scale, 而且不使用fixed viewport
            mInitialViewState.mViewScale = 
            mInitialViewState.mTextWrapScale =
                (float) webViewWidth / mViewportWidth;
        } else {
            mInitialViewState.mTextWrapScale = adjust;
            // 一般都会使用wide viewport 
            if (mSettings.getUseWideViewPort()) { 
                // 0 will trigger WebView to turn on zoom overview mode
                mInitialViewState.mViewScale = 0;
            } else {
                mInitialViewState.mViewScale = adjust;
            }
        }
    }

    if (mWebView.mHeightCanMeasure)  // 获取到了新的height?
        mWebView.mLastHeightSent = 0;
        WebView.ViewSizeData data = new WebView.ViewSizeData();           
        data.mWidth = mWebView.mLastWidthSent;                            
        data.mHeight = 0; 

        data.mTextWrapWidth = data.mWidth;
        data.mScale = -1.0f;
        data.mIgnoreHeight = false;
        data.mAnchorX = data.mAnchorY = 0; 

        // 发送view size changed消息
        mEventHub.removeMessages(EventHub.VIEW_SIZE_CHANGED);
        mEventHub.sendMessageAtFrontOfQueue(Message.obtain(null,
                    EventHub.VIEW_SIZE_CHANGED, data));

    else {
        WebView.ViewSizeData data = new WebView.ViewSizeData();
        // mViewScale as 0 means it is in zoom overview mode. So we don't
        // know the exact scale. If mRestoredScale is non-zero, use it;
        // otherwise just use mTextWrapScale as the initial scale.
        float tentativeScale = mInitialViewState.mViewScale;
        if (tentativeScale == 0) {
            // The following tries to figure out more correct view scale
            // and text wrap scale to be sent to webkit, by using some
            // knowledge from web settings and zoom manager.

            // Calculated window width will be used to guess the scale
            // in zoom overview mode.
            tentativeScale = mInitialViewState.mTextWrapScale;
            int tentativeViewWidth = Math.round(webViewWidth / tentativeScale);
            int windowWidth = calculateWindowWidth(tentativeViewWidth);

            // In viewport setup time, since no content width is known, we assume 
            // the windowWidth will be the content width, to get a more likely
            // zoom overview scale.
            data.mScale = (float) webViewWidth / windowWidth;
            if (!mSettings.getLoadWithOverviewMode()) {
                // If user choose non-overview mode.
                data.mScale = Math.max(data.mScale, tentativeScale);
            }

            // 好像没啥用
            if (mSettings.isNarrowColumnLayout()) {
                // In case of automatic text reflow in fixed view port mode.
                mInitialViewState.mTextWrapScale =
                    mWebView.getReadingLevelScale();
            }
        } else {
            // Scale is given such as when page is restored, use it.
            data.mScale = tentativeScale;
        }

        data.mWidth = Math.round(webViewWidth / data.mScale);
        // We may get a call here when mCurrentViewHeight == 0 if webcore completes the
        // first layout before we sync our webview dimensions to it. In that case, we
        // request the real height of the webview. This is not a perfect solution as we
        // are calling a WebView method from the WebCore thread. But this is preferable
        // to syncing an incorrect height.
        data.mHeight = mCurrentViewHeight == 0 ?
            Math.round(mWebView.getViewHeight() / data.mScale)
            : Math.round((float) mCurrentViewHeight * data.mWidth / viewportWidth);
        data.mTextWrapWidth = Math.round(webViewWidth
                / mInitialViewState.mTextWrapScale);
        data.mIgnoreHeight = false;
        data.mAnchorX = data.mAnchorY = 0;
        // send VIEW_SIZE_CHANGED to the front of the queue so that we
        // can avoid pushing the wrong picture to the WebView side.
        mEventHub.removeMessages(EventHub.VIEW_SIZE_CHANGED);
        // Let webkit know the scale and inner width/height immediately
        // in viewport setup time to avoid wrong information.
        viewSizeChanged(data);
    }


    // Calculate width to be used in webkit window.
    // webkit中内容的宽度
    // 如果忽略"default zoom"设置， 
    private int calculateWindowWidth(int viewWidth) {
        int width = viewWidth;
        if (mSettings.getUseWideViewPort()) {
            if (mViewportWidth == -1) {  // 网页没有指明viewport width参数
                                         // 一般的互联网网页都会走进来
                                         // 对于wml网页,  Document::setDocType
                                         // 会设置"width=device-width"
                width = WebView.DEFAULT_VIEWPORT_WIDTH; // 800
            } else if (mViewportWidth > 0) {
                // Use website specified or desired fixed viewport width.
                // "width=540"
                width = mViewportWidth;
            } else {
                // mViewportWidth = 0 (设置了"width=device-width") 
                // 一般移动网页会设置这个参数
                width = Math.round(mWebView.getViewWidth() / 
                        mWebView.getDefaultZoomScale());
                // s899t viewWidth = 540, default zoom = 1.5
                // width = 360

                // 10086.cn/m need bigger width, othewise, 
                // its footer text will wrap
                if(mWebView.getUrl() != null &&
                        mWebView.getUrl().contains(WebView.CMCC_10086_CN)) {
                    width *= 1.2;
                }
            }
        }
        return width;
    }


