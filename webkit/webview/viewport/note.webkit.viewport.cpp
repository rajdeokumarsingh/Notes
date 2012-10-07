
http://10086.cn/m
<meta id="viewport" name="viewport" content="initial-scale=0.7; minimum-scale=0.7;maximum-scale=1.0; user-scalable=1;width=device-width; height=device-height;" />

base/core/java/android/webkit/WebViewCore.java

    // notify webkit that our virtual view size changed size (after inv-zoom)
    private void viewSizeChanged(int w, int h, int textwrapWidth, float scale,
            int anchorX, int anchorY, boolean ignoreHeight) 

WebKit/android/jni/WebViewCore.cpp

    SetSize(JNIEnv *env, jobject obj, jint width, jint height,
            jint screenWidth, jfloat scale, jint realScreenWidth, jint screenHeight,
                    jint anchorX, jint anchorY, jboolean ignoreHeight)

        WebViewCore* viewImpl = GET_NATIVE_VIEW(env, obj);
        viewImpl->setSizeScreenWidthAndScale(width, height, screenWidth, scale,
                realScreenWidth, screenHeight, anchorX, anchorY, ignoreHeight);

base/core/java/android/webkit/WebView.java
    DEFAULT_VIEWPORT_WIDTH_NORMAL

    mWebView.getViewWidth

    mViewportWidth
    mCurrentViewWidth

    calculateWindowWidth


////////////////////////////////////////////////////////////////////////////////
base/core/java/android/webkit/WebViewCore.java

////////////////////////////////////////   
    // range is from 200 to 10,000. 0 is a special value means device-width. 
    // -1 means undefined.
    private int mViewportWidth = -1;

    private int calculateWindowWidth(int viewWidth) {
        int width = viewWidth;

        // 浏览器一般都会会设置这个标志位, 否则viewport的最大宽度只有320
        if (mSettings.getUseWideViewPort()) {

            if (mViewportWidth == -1) {
                // 浏览的网页没有设置device-width属性
                // Fixed viewport width.
                width = WebView.DEFAULT_VIEWPORT_WIDTH;
            } else if (mViewportWidth > 0) {
                // 浏览的网页设置了device-width属性
                // Use website specified or desired fixed viewport width.
                width = mViewportWidth;
            } else {
                // For mobile web site.
                width = Math.round(mWebView.getViewWidth() / mWebView.getDefaultZoomScale());

                // getViewWidth: 540
                // getDefaultZoomScale: 1.5 (baidu) 0.75

                // scale越大，网页内容越大
                // mWebView.getDefaultZoomScale() = mWebView.getViewWidth()/width
            }
        }

        return width;
    }

////////////////////////////////////////
    // These values are used to avoid requesting a layout based on old values
    private int mCurrentViewWidth = 0;

    // notify webkit that our virtual view size changed size (after inv-zoom)
    private void viewSizeChanged(WebView.ViewSizeData data) {
        int w = data.mWidth;
        int h = data.mHeight;
        int textwrapWidth = data.mTextWrapWidth;
        float scale = data.mScale;

        int width = calculateWindowWidth(w);
        int height = h;
        // 调整宽高比
        if (width != w) {
            float heightWidthRatio = data.mHeightWidthRatio;
            float ratio = (heightWidthRatio > 0) ? heightWidthRatio : (float) h / w;
            height = Math.round(ratio * width);
        }
        nativeSetSize(width, height, textwrapWidth, scale, w,
                data.mActualViewHeight > 0 ? data.mActualViewHeight : h,
                data.mAnchorX, data.mAnchorY, data.mIgnoreHeight);

        mCurrentViewWidth = w;
        mCurrentViewHeight = h;
        mCurrentViewScale = scale;

////////////////////////////////////////
// view的实际物理大小 320, 540, ... 减去垂直滚动条的宽度
// 即为view可以显示内容的物理宽度
WebView.getViewWidth()
    getWidth() - getVerticalScrollbarWidth()

////////////////////////////////////////
WebView.getDefaultZoomScale

////////////////////////////////////////////////////////////////////////////////
// ZoomManager
// scale越大，看到的内容越大
private void setDefaultZoomScale(float defaultScale) { 

    final float originalDefault = mDefaultScale;
    mDefaultScale = defaultScale;
    mInvDefaultScale = 1 / defaultScale;

    // protected static final float DEFAULT_MAX_ZOOM_SCALE_FACTOR = 4.00f;
    // protected static final float DEFAULT_MIN_ZOOM_SCALE_FACTOR = 0.25f;
    mDefaultMaxZoomScale = defaultScale * DEFAULT_MAX_ZOOM_SCALE_FACTOR;
    mDefaultMinZoomScale = defaultScale * DEFAULT_MIN_ZOOM_SCALE_FACTOR;
    if (originalDefault > 0.0 && mMaxZoomScale > 0.0) {
        // Keeps max zoom scale when zoom density changes.
        mMaxZoomScale = defaultScale / originalDefault * mMaxZoomScale;
    } else {
        mMaxZoomScale = mDefaultMaxZoomScale;
    }
    if (originalDefault > 0.0 && mMinZoomScale > 0.0) {
        // Keeps min zoom scale when zoom density changes.
        mMinZoomScale = defaultScale / originalDefault * mMinZoomScale;
    } else {
        mMinZoomScale = mDefaultMinZoomScale;
    }
    if (!exceedsMinScaleIncrement(mMinZoomScale, mMaxZoomScale)) {
        mMaxZoomScale = mMinZoomScale;
    }
}


WebView.init()-->
    ZoomManager.init(getContext().getResources().getDisplayMetrics().density)
/**
 * Initialize both the default and actual zoom scale to the given density.
 *
 * @param density The logical density of the display. This is a scaling factor
 * for the Density Independent Pixel unit, where one DIP is one pixel on an
 * approximately 160 dpi screen (see android.util.DisplayMetrics.density).
 */
public void init(float density) {
    assert density > 0;

    mDisplayDensity = density;
    setDefaultZoomScale(density);
    mActualScale = density;
    mInvActualScale = 1 / density;
    mTextWrapScale = getReadingLevelScale();
}

/**
 * Update the default zoom scale using the given density. It will also reset
 * the current min and max zoom scales to the default boundaries as well as
 * ensure that the actual scale falls within those boundaries.
 *
 * @param density The logical density of the display. This is a scaling factor
 * for the Density Independent Pixel unit, where one DIP is one pixel on an
 * approximately 160 dpi screen (see android.util.DisplayMetrics.density).
 */
public void updateDefaultZoomDensity(float density) {
    assert density > 0;

    if (Math.abs(density - mDefaultScale) > MINIMUM_SCALE_INCREMENT) {
        // Remember the current zoom density before it gets changed. 
        final float originalDefault = mDefaultScale;
        // set the new default density
        setDefaultZoomScale(density);
        float scaleChange = (originalDefault > 0.0) ? density / originalDefault: 1.0f;
        // adjust the scale if it falls outside the new zoom bounds
        setZoomScale(mActualScale * scaleChange, true);
    }
}


////////////////////////////////////////////////////////////////////////////////
webview

/**     
 * Set the default zoom density of the page. This should be called from UI
 * thread.
 * @param zoom A ZoomDensity value
 * @see WebSettings.ZoomDensity
 */         
public void setDefaultZoom(ZoomDensity zoom) {
    if (mDefaultZoom != zoom) {
        mDefaultZoom = zoom;
        mWebView.adjustDefaultZoomDensity(zoom.value);
            |
            V
/* package */ void adjustDefaultZoomDensity(int zoomDensity) {
    final float density = mContext.getResources().getDisplayMetrics().density
        * 100 / zoomDensity;
    updateDefaultZoomDensity(density);
}



case UPDATE_ZOOM_DENSITY: {
final float density = (Float) msg.obj;
mZoomManager.updateDefaultZoomDensity(density);
break;
}

////////////////////////////////////////////////////////////////////////////////
base/core/java/android/webkit/WebViewCore.java

    // called by JNI
    private void didFirstLayout(boolean standardLoad) {
        setupViewport(updateViewState);

