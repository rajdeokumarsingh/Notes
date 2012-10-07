SkRegion* region
SkIPoint* point
SkIRect

必现问题：
    在加载网页的时候
    www.cnfol.com
        

BoardConfig.mk
    USE_OPENGL_RENDERER = true/false

JavaScriptCore/wtf/Platform.h
#if !defined WTF_USE_ACCELERATED_COMPOSITING
    #define WTF_USE_ACCELERATED_COMPOSITING 1
    #define ENABLE_3D_RENDERING 1
#endif

ACCELERATED_2D_CANVAS

#define ENABLE_3D_RENDERING 1
    JavaScriptCore/wtf/Platform.h|688| <<<unknown>>> // #define ENABLE_3D_RENDERING 1
    WebCore/css/CSSComputedStyleDeclaration.cpp|490| <<<unknown>>> // Note that this does not flatten to an affine transform if ENABLE(3D_RENDERING) is off, by design.
    WebCore/css/MediaQueryEvaluator.cpp|51| <<<unknown>>> #if ENABLE(3D_RENDERING) && USE(ACCELERATED_COMPOSITING)
    WebCore/css/MediaQueryEvaluator.cpp|479| <<<unknown>>> #if ENABLE(3D_RENDERING)
    WebCore/rendering/RenderLayerCompositor.cpp|67| <<<unknown>>> #if ENABLE(3D_RENDERING)
    WebCore/rendering/RenderObject.cpp|1877| <<<unknown>>> #if ENABLE(3D_RENDERING)
    WebCore/rendering/RenderObject.cpp|1895| <<<unknown>>> #if ENABLE(3D_RENDERING)
    WebCore/rendering/RenderObject.h|1054| <<<unknown>>> #if !ENABLE(3D_RENDERING)

#define WTF_USE_ACCELERATED_COMPOSITING 1

WebKit/android/nav/WebView.cpp
WebKit/android/jni/WebViewCore.cpp
WebKit/android/jni/PictureSet.cpp

base/core/java/android/webkit/WebView.java
base/core/java/android/webkit/WebViewCore.java




调试信息
WebKit/android/jni/PictureSet.h
        #define PICTURE_SET_DUMP 0
        #define PICTURE_SET_DEBUG 0
        #define PICTURE_SET_VALIDATE 0




hint:
    canvas.isHardwareAccelerate
        true

    drawOverScrollBackground


setNewPicture
    setBaseLayer(draw.mBaseLayer, draw.mInvalRegion,
            getSettings().getShowVisualIndicator(),
            isPictureAfterFirstLayout, registerPageSwapCallback);

base/core/java/android/webkit/WebViewCore.java

// called from JNI or WebView thread
/* package */ void contentDraw() {
        mEventHub.sendMessage(Message.obtain(null, EventHub.WEBKIT_DRAW));
            |
            V
private void webkitDraw() {
    mDrawIsScheduled = false;
    DrawData draw = new DrawData();
    draw.mBaseLayer = nativeRecordContent(draw.mInvalRegion, draw.mContentSize); {

        recordPictureSet(&m_content); {

            bool success = layoutIfNeededRecursive(m_mainFrame); //-->
            static bool layoutIfNeededRecursive(WebCore::Frame* f) {
                WebCore::FrameView* v = f->view();

                // layout itself
                if (v->needsLayout())
                    v->layout(f->tree()->parent()); {
                        // for example
                        void RenderFlexibleBox::layoutBlock(bool relayoutChildren, int /*pageHeight FIXME: Implement */)
                            if (isHorizontal())
                                layoutHorizontalBox(relayoutChildren);
                            else
                                layoutVerticalBox(relayoutChildren);  

                            layoutPositionedObjects(relayoutChildren || isRoot());
                    }

                // layout all children
                WebCore::Frame* child = f->tree()->firstChild();
                bool success = true;
                while (child) {
                    success &= layoutIfNeededRecursive(child);
                    child = child->tree()->nextSibling();
                }
                return success && !v->needsLayout();
            }

            // if the webkit page dimensions changed, discard the pictureset and redraw.
            WebCore::FrameView* view = m_mainFrame->view();
            int width = view->contentsWidth();
            int height = view->contentsHeight();

            // Use the contents width and height as a starting point.
            SkIRect contentRect;           
            contentRect.set(0, 0, width, height);                              
            SkIRect total(contentRect);

        }

    }


    if (draw.mBaseLayer == 0) {
        if (mWebView != null && !mWebView.isPaused()) {
            mEventHub.sendMessage(Message.obtain(null, EventHub.WEBKIT_DRAW));
        return;

    mLastDrawData = draw;
    webkitDraw(draw);
        |
        V
private void webkitDraw(DrawData draw)
    draw.mFocusSizeChanged = nativeFocusBoundsChanged();
    Message.obtain(mWebView.mPrivateHandler,
            WebView.NEW_PICTURE_MSG_ID, draw).sendToTarget();
                |
                V
base/core/java/android/webkit/WebView.java
    case NEW_PICTURE_MSG_ID:
        final WebViewCore.DrawData draw = (WebViewCore.DrawData) msg.obj;
        setNewPicture(draw, true);
            |
            V
void setNewPicture(final WebViewCore.DrawData draw, boolean updateBaseLayer)
    if (updateBaseLayer) {
        // Request a callback on pageSwap (to reposition the webtextview)
        boolean registerPageSwapCallback =
            !mZoomManager.isFixedLengthAnimationInProgress() && inEditingMode();

        setBaseLayer(draw.mBaseLayer, draw.mInvalRegion,
                getSettings().getShowVisualIndicator(),
                isPictureAfterFirstLayout, registerPageSwapCallback);

        recordNewContentSize(draw.mContentSize.x,
                draw.mContentSize.y, updateLayout);


// called from JNI  
void layersDraw() {     
    mEventHub.sendMessage(Message.obtain(null, EventHub.WEBKIT_DRAW_LAYERS));

    // Only update the layers' content, not the base surface
    // PictureSet.
    private void webkitDrawLayers() {
        mDrawLayersIsScheduled = false;
        if (mDrawIsScheduled || mLastDrawData == null) {
            removeMessages(EventHub.WEBKIT_DRAW);
            webkitDraw();
            return;

        // Directly update the layers we last passed to the UI side
        if (nativeUpdateLayers(mNativeClass, mLastDrawData.mBaseLayer)) {
            // If anything more complex than position has been touched, let's do a full draw
            webkitDraw();
        }

        mWebView.mPrivateHandler.sendMessageAtFrontOfQueue(mWebView.mPrivateHandler
                        .obtainMessage(WebView.INVAL_RECT_MSG_ID));
                            |
                            V
                        invalidate();
                        viewInvalidate(r.left, r.top, r.right, r.bottom);

/*  Called by JNI. The coordinates are in doc coordinates, so they need to
    be scaled before they can be used by the view system, which happens
    in WebView since it (and its thread) know the current scale factor.
 */ 
private void sendViewInvalidate(int left, int top, int right, int bottom) {
    if (mWebView != null) {
        Message.obtain(mWebView.mPrivateHandler,
                WebView.INVAL_RECT_MSG_ID,
                new Rect(left, top, right, bottom)).sendToTarget();
    }       
}    

////////////////////////////////////////////////////////////////////////////////
WebKit/android/nav/WebView.cpp
    hint:
        skia 分层 画图
        来加skia的qq群吧
            群号 : 166139183

    bool drawGL(WebCore::IntRect& viewRect, WebCore::IntRect* invalRect,
            WebCore::IntRect& webViewRect, int titleBarHeight,
            WebCore::IntRect& clip, float scale, int extras)

        BaseLayerAndroid* m_baseLayer;
            class BaseLayerAndroid : public Layer 
                class TEST_EXPORT Layer : public SkRefCnt 

        m_glWebViewState

        LayerAndroid mainPicture(m_navPictureUI);

        LayerAndroid* compositeLayer = compositeRoot();
        if (compositeLayer) {

    bool ret = m_glWebViewState->drawGL(viewRect, m_visibleRect, invalRect,
                                            webViewRect, titleBarHeight, clip, scale,
                                                                                    &treesSwapped, &newTreeHasAnim);





    private Picture mHistoryPicture = null;
    private boolean mDrawHistory = false;
    private int mHistoryWidth = 0;
    private int mHistoryHeight = 0;


    restorePicture(Bundle b, File src)
        |
        V
    restoreHistoryPictureFields

    savePicture



05-11 16:14:03.440  3249  3249 E webkit_native_draw_history: drawGL, 491
这个函数好像没啥用处

