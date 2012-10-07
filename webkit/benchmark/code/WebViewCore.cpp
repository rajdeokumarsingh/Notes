
// 每个WebView对应一个WebViewCore
// one instance of WebViewCore per page for calling into Java's WebViewCore
class WebViewCore : public WebCoreRefObject 

    // 所有WebViewCore都记录在一个全局的链表中
    // 在WebViewCore的构造函数中，将其自身加入到该链表
    // 析构函数中，将其从链表中移除
    static SkTDArray<WebViewCore*> gInstanceList;



// Check whether a media mimeType is supported in Android media framework.
// jni 2 cpp
bool WebViewCore::isSupportedMediaMimeType(const WTF::String& mimeType) 


// layout自身和其子孙
static bool layoutIfNeededRecursive(WebCore::Frame* f)
    WebCore::FrameView* v = f->view();

    if (v->needsLayout())
        v->layout(f->tree()->parent());

    WebCore::Frame* child = f->tree()->firstChild();
    while (child)
        success &= layoutIfNeededRecursive(child);
        child = child->tree()->nextSibling();


// 将内容保存到一张SkPicture中
void WebViewCore::recordPicture(SkPicture* picture)
    // draw into the picture's recording canvas
    WebCore::FrameView* view = m_mainFrame->view();

    SkAutoPictureRecord arp(picture, view->contentsWidth(),
            view->contentsHeight(), PICT_RECORD_FLAGS);
    SkAutoMemoryUsageProbe mup(__FUNCTION__);

    WebCore::PlatformGraphicsContext pgc(arp.getRecordingCanvas());
    WebCore::GraphicsContext gc(&pgc);
    view->platformWidget()->draw(&gc, WebCore::IntRect(0, 0,
                view->contentsWidth(), view->contentsHeight()));

