
class WebFrameView: public WebCoreViewBridge

WebFrameView::WebFrameView(WebCore::FrameView* frameView, WebViewCore* webViewCore)
: WebCoreViewBridge()
, mFrameView(frameView)
, mWebViewCore(webViewCore) {
    // attach itself to mFrameView
    mFrameView->setPlatformWidget(this);
    Retain(mWebViewCore);

void WebFrameView::draw(WebCore::GraphicsContext* ctx, const WebCore::IntRect& rect) {
    WebCore::Frame* frame = mFrameView->frame();

    // 画白色背景
    if (NULL == frame->contentRenderer()) {                                   
        // We only do this if there is nothing else to draw.                  
        // If there is a renderer, it will fill the bg itself, so we don't want to 
        // double-draw (slow)                                                 
        SkCanvas* canvas = ctx->platformContext()->mCanvas;                   
        canvas->drawColor(SK_ColorWHITE); 

    } else if (frame->tree()->parent()) {  // 如果还有父frame
        // For subframe, create a new translated rect from the given rectangle.    
        WebCore::IntRect transRect(rect);

        SkCanvas* canvas = ctx->platformContext() ? ctx->platformContext()->mCanvas : NULL;
        if (canvas) {
            const WebCore::IntRect& bounds = getBounds();

            // Grab the intersection of transRect and the frame's bounds.
            // 父frame和子frame的重叠区域，一般都是子frame
            transRect.intersect(bounds);

            // Translate the canvas, add a clip.
            canvas->save();
            // 将坐标原点指向父frame的左上角
            canvas->translate(SkIntToScalar(bounds.x()), SkIntToScalar(bounds.y()));
            // Move the transRect into the frame's local coordinates.
            // 将子frame的坐标变换到父frame中来
            transRect.move(-bounds.x(), -bounds.y());
            canvas->clipRect(transRect);
        }

        // 重新绘制该区域
        mFrameView->paintContents(ctx, transRect);
        if (canvas)
            canvas->restore();
    } else {  // 没有父frame, 直接绘制
        mFrameView->paintContents(ctx, rect);
    }

