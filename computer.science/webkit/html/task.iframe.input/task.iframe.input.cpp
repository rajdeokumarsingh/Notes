
/* 问题：

使用4.0的浏览器访问 http://www.10086.cn/index.htm时，点击输入框， 会发现另外一个输入框出现在原来输入框的上方

经过简化，发现只要是iframe中包括了input文字框，都会有这种现象

见: ./86.html

*/

基本类关系：
class HTMLIFrameElement : public HTMLFrameElementBase
    RenderIFrame
class HTMLFrameElement : public HTMLFrameElementBase 
    RenderFrame
class HTMLFrameElementBase : public HTMLFrameOwnerElement
class HTMLFrameOwnerElement : public HTMLElement

frame和iframe都是frame element base

    RenderIFrame : public RenderFrameBase 
    RenderFrame : public RenderFrameBase 
    RenderFrameBase : public RenderPart
    RenderPart : public RenderWidget

线索：
在这个函数中更新textView的位置
base/core/java/android/webkit/WebView.java
    /**
     * Check to see if the focused textfield/textarea is still on screen.  If it
     * is, update the the dimensions and location of WebTextView.  Otherwise,
     * remove the WebTextView.  Should be called when the zoom level changes.
     * @param intersection How to determine whether the textfield/textarea is
     *        still on screen.
     * @return boolean True if the textfield/textarea is still on screen and the
     *         dimensions/location of WebTextView have been updated.
     */
    private boolean didUpdateWebTextViewDimensions(int intersection) {

        // 有问题
        // contentBounds: Rect(10, 10 - 204, 35)
        static jobject nativeFocusCandidateNodeBounds(JNIEnv *env, jobject obj)
        {   
            const CachedFrame* frame;
            const CachedNode* node = getFocusCandidate(env, obj, &frame);

            // 这里获取到的bounds是在iframe中的相对位置而不是绝对位置
            WebCore::IntRect bounds = node ? node->originalAbsoluteBounds()
                : WebCore::IntRect(0, 0, 0, 0);
            return createJavaRect(env, bounds.x(), bounds.y(),
                    bounds.maxX(), bounds.maxY());
        }   


// 如果focus candidata是一个input, 而且candidate在一个iframe中
    通过下面函数可获取当前focus的frame
    static jobject nativeFocusCandidateFrameBounds(JNIEnv *env, jobject obj)                                                                                     
    {
        WebView* view = GET_NATIVE_VIEW(env, obj); 
        CachedRoot* root = view->getFrameCache(WebView::DontAllowNewer);                                                                                         
        if (!root) {
            return createJavaRect(env, 0, 0, 0, 0);                                                                                                              
        }  
        const CachedFrame* frame = 0;  
        const CachedNode* cursor = root->currentCursor(&frame);
        if (!cursor || !cursor->wantsKeyEvents()) 
            (void) root->currentFocus(&frame);                                                                                                                   
        WebCore::IntRect bounds = frame ? frame->getLocalViewBounds()
            : WebCore::IntRect(0, 0, 0, 0);
        return createJavaRect(env, bounds.x(), bounds.y(),
                bounds.maxX(), bounds.maxY());                                                                                                                   
    }

// 为什么有两个input, 另外一个正确的input是谁画的?

