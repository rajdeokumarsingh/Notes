// 问题 H2是什么element?
    ./html/HTMLTagNames.in
    HTMLHeadingElement
    class HTMLHeadingElement : public HTMLElement

// H2的render是
// H2 什么时候换行?

/* 基本概念
    getViewWidth()   实际的view的宽度
    getViewHeight()  实际的view的高度

    View的逻辑尺寸
        viewWidth * mZoomManager.getInvScale()
        (getViewHeightWithTitle() - getTitleHeight()) * mZoomManager.getInvScale();
        注意如果缩放变小，逻辑尺寸反而变大

    mTextWrapWidth
       getViewWidth / textWrapScale 
       getViewWidth / (mDisplayDensity*mDoubleTapZoomFactor)

*/

 
base/core/java/android/webkit/WebViewCore.java
    data.mWidth = mWebView.mLastWidthSent;
    data.mHeight = 0;
    // if mHeightCanMeasure is true, getUseWideViewPort() can't be
    // true. It is safe to use mWidth for mTextWrapWidth.
    data.mTextWrapWidth = data.mWidth;
        ...
    nativeSetSize(width, height, textwrapWidth, scale, w,
           data.mActualViewHeight > 0 ? data.mActualViewHeight : h,
           data.mAnchorX, data.mAnchorY, data.mIgnoreHeight);


static void SetSize(JNIEnv *env, jobject obj, jint width, jint height,
       jint textWrapWidth, jfloat scale, jint screenWidth, jint screenHeight,
       jint anchorX, jint anchorY, jboolean ignoreHeight)
{
#ifdef LOG_WEBKIT_ANDROID_JNI_WEBVIEWCORE
    android_printLog(ANDROID_LOG_ERROR, LOG_TAG, "%s, %d\n", __FUNCTION__, __LINE__);
#endif

#ifdef ANDROID_INSTRUMENT
    TimeCounterAuto counter(TimeCounter::WebViewCoreTimeCounter);
#endif
    WebViewCore* viewImpl = GET_NATIVE_VIEW(env, obj);
    LOGV("webviewcore::nativeSetSize(%u %u)\n viewImpl: %p", (unsigned)width, (unsigned)height, viewImpl);
    LOG_ASSERT(viewImpl, "viewImpl not set in nativeSetSize");
    viewImpl->setSizeScreenWidthAndScale(width, height, textWrapWidth, scale,
            screenWidth, screenHeight, anchorX, anchorY, ignoreHeight);
}

主要数据结构：

WebView.java
    static class ViewSizeData {
        int mWidth;
        int mHeight;
        float mHeightWidthRatio;
        int mActualViewHeight;
        int mTextWrapWidth;
        int mAnchorX;
        int mAnchorY;
        float mScale;
        boolean mIgnoreHeight;
    }

////////////////////////////////////////////////////////////////////////////////
// Thread 1
////////////////////////////////////////////////////////////////////////////////

    /**
     * Compute unzoomed width and height, and if they differ from the last
     * values we sent, send them to webkit (to be used as new viewport)
     *
     * @param force ensures that the message is sent to webkit even if the width
     * or height has not changed since the last message
     *
     * @return true if new values were sent
     */
    boolean sendViewSizeZoom(boolean force) {
        // getViewWidth()   实际的view的宽度
        // getViewHeight()  实际的view的高度

        // 计算新的view的宽度和高度
        // 注意这里乘以的是inverse scale
        // 假如scale初始值为1, 则viewWidth和newWidth相等
        // 如果scale变成了0.5, 即页面缩小0.5倍，则newWidth等于viewWidth的两倍
        int viewWidth = getViewWidth();
        int newWidth = Math.round(viewWidth * mZoomManager.getInvScale());
        int viewHeight = getViewHeightWithTitle() - getTitleHeight();
        int newHeight = Math.round(viewHeight * mZoomManager.getInvScale());

        float heightWidthRatio = (float) viewHeight / viewWidth;

        // Actual visible content height.
        int actualViewHeight = Math.round(getViewHeight() * mZoomManager.getInvScale());

        // 如果不相同，则将参数传递下去
        if (newWidth != mLastWidthSent || newHeight != mLastHeightSent || force ||
                actualViewHeight != mLastActualHeightSent) {
            ViewSizeData data = new ViewSizeData();
            data.mWidth = newWidth;
            data.mHeight = newHeight;
            data.mHeightWidthRatio = heightWidthRatio;
            data.mActualViewHeight = actualViewHeight;
            data.mTextWrapWidth = Math.round(viewWidth / mZoomManager.getTextWrapScale());
            data.mScale = mZoomManager.getScale();
            data.mIgnoreHeight = mZoomManager.isFixedLengthAnimationInProgress()
                && !mHeightCanMeasure;
            data.mAnchorX = mZoomManager.getDocumentAnchorX();
            data.mAnchorY = mZoomManager.getDocumentAnchorY();
            mWebViewCore.sendMessage(EventHub.VIEW_SIZE_CHANGED, data);
            mLastWidthSent = newWidth;
            mLastHeightSent = newHeight;
            mLastActualHeightSent = actualViewHeight;
            mZoomManager.clearDocumentAnchor();
            return true;
        }
        return false;
    }
            |
            | message VIEW_SIZE_CHANGED
            V
WebViewCore.java
    // notify webkit that our virtual view size changed size 
    // (after inv-zoom)
    private void viewSizeChanged(WebView.ViewSizeData data) {
        int w = data.mWidth;
        int h = data.mHeight;
        int textwrapWidth = data.mTextWrapWidth;
        float scale = data.mScale;

        // 重新计算width和高度
        int width = calculateWindowWidth(w);
        int height = h;
        if (width != w) {
            float heightWidthRatio = data.mHeightWidthRatio;
            float ratio = (heightWidthRatio > 0) ? heightWidthRatio : (float) h / w;
            height = Math.round(ratio * width);
        }

        // 将尺寸设置到native层
        nativeSetSize(width, height, textwrapWidth, scale, w,
            data.mActualViewHeight > 0 ? data.mActualViewHeight : h,
            data.mAnchorX, data.mAnchorY, data.mIgnoreHeight);


////////////////////////////////////////////////////////////////////////////////
// Thread 2
////////////////////////////////////////////////////////////////////////////////
WebViewCore.java

// called by JNI
private void updateViewport() {
    // Update viewport asap to make sure we get correct one.
    setupViewport(true);
}

private void setupViewport(boolean updateViewState) {
    // set the viewport settings from WebKit
    // 从native层获取html viewport中的参数
    setViewportSettingsFromNative();

    // 根据mViewportMinimumScale, mViewportMaximumScale
    // 调整mViewportInitialScale

    // 根据mSettings.forceUserScalable()
    // 设置mViewportUserScalable
    // 重新设置mViewportMinimumScale, mViewportMaximumScale

    // adjust the default scale to match the densityDpi
    // float adjust = WebView.DEFAULT_DENSITY;
    float adjust = 1.0f;

    // 根据html viewport dpi参数调整adjust参数
    // 注意，一般都会进入到下面的分支
    if (mViewportDensityDpi == -1) {
        // convert default zoom scale to a integer (percentage) to avoid any
        // issues with floating point comparisons

        // Default zoom scale是Browser设置中“近，中，远”
        // 这里可简化认为adjust为1.0
        if ((int)(mWebView.getDefaultZoomScale() * 100) != 100) {
            adjust = mWebView.getDefaultZoomScale();
        }
    } else if (mViewportDensityDpi > 0) {
        // 一般不会进入到这个分支
        adjust = (float) mContext.getResources().
        getDisplayMetrics().densityDpi / mViewportDensityDpi;
    }

    // 一般不会进入到这个分支
    if (adjust != mWebView.getDefaultZoomScale()) {
        Message.obtain(mWebView.mPrivateHandler,
            WebView.UPDATE_ZOOM_DENSITY, adjust).sendToTarget();
    }

    // 这里adjust看成是Browser setting中的"默认缩放设置"
    // “远中近”, 默认是“近“, 1.0

    // 根据“默认缩放设置”调整viewport参数
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
    // 根据viewport width参数调整initial scale
    if (mViewportWidth == 0)
        if (mViewportInitialScale == 0)
            mViewportInitialScale = defaultScale;

    // 根据user scale调整view port scale参数
    if (mViewportUserScalable == false) {
        mViewportInitialScale = defaultScale;
        mViewportMinimumScale = defaultScale;
        mViewportMaximumScale = defaultScale;
    }

    // 根据mViewportInitialScale调整minimum/maximum scale
    if (mViewportMinimumScale > mViewportInitialScale
            && mViewportInitialScale != 0) {
        mViewportMinimumScale = mViewportInitialScale;
    }
    if (mViewportMaximumScale > 0
            && mViewportMaximumScale < mViewportInitialScale) {
        mViewportMaximumScale = mViewportInitialScale;
    }

    if (mViewportWidth < 0 && mViewportInitialScale == defaultScale) {
        mViewportWidth = 0;
    }

    // mViewportWidth == 0 则说明是device-width
    // if mViewportWidth is 0, it means device-width, always update.
    if (mViewportWidth != 0 && !updateViewState) {
        // 不是移动网站
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
        return;
    }

    // now notify webview
    // webViewWidth refers to the width in the view system
    int webViewWidth;
    // viewportWidth refers to the width in the document system
    int viewportWidth = mCurrentViewWidth;

    webViewWidth = Math.round(viewportWidth * mCurrentViewScale);
    
    mInitialViewState = new ViewState();
    mInitialViewState.mMinScale = mViewportMinimumScale / 100.0f;
    mInitialViewState.mMaxScale = mViewportMaximumScale / 100.0f;
    mInitialViewState.mDefaultScale = adjust;
    mInitialViewState.mScrollX = mRestoredX;
    mInitialViewState.mScrollY = mRestoredY;
    mInitialViewState.mShouldStartScrolledRight = (mRestoredX == 0)
        && (mRestoredY == 0)
        && (mBrowserFrame != null)
        && mBrowserFrame.getShouldStartScrolledRight();

    mInitialViewState.mMobileSite = (0 == mViewportWidth);

    if (mWebView.mHeightCanMeasure) {
        // Trick to ensure that the Picture has the exact height for the
        // content by forcing to layout with 0 height after the page is
        // ready, which is indicated by didFirstLayout. This is essential to
        // get rid of the white space in the GMail which uses WebView for
        // message view.
        mWebView.mLastHeightSent = 0;
        // Send a negative scale to indicate that WebCore should reuse
        // the current scale
        WebView.ViewSizeData data = new WebView.ViewSizeData();
        data.mWidth = mWebView.mLastWidthSent;
        data.mHeight = 0;
        // if mHeightCanMeasure is true, getUseWideViewPort() can't be
        // true. It is safe to use mWidth for mTextWrapWidth.
        data.mTextWrapWidth = data.mWidth;
        data.mScale = -1.0f;

        // ...
        mEventHub.removeMessages(EventHub.VIEW_SIZE_CHANGED);
        mEventHub.sendMessageAtFrontOfQueue(Message.obtain(null,
                    EventHub.VIEW_SIZE_CHANGED, data));
    } else {
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


}


