// 全局数据组成
{
    // Page 1 <---> WebViewCore <---> JavaGlue <--JNI--> WebViewCore

    // one instance of WebViewCore per page for calling into Java's WebViewCore
    // 每个page对应一个WebViewCore
    // 每个WebViewCore对应一个WebView

    // 所有WebViewCore都记录在一个全局的链表中
    // 在WebViewCore的构造函数中，将其自身加入到该链表
    // 析构函数中，将其从链表中移除
    static SkTDArray<WebViewCore*> gInstanceList;


    // 和java层的绑定
    {
        struct WebViewCore::JavaGlue {                                                                         
            jweak       m_obj;      // 指向java层的WebViewCore对象
            jmethodID   m_scrollTo;
            ...
        }

        // 创建WebViewCore对象的时候，将m_obj(weak reference)指向java WebViewCore对象
        // FIXME: 为什么是weak reference: 注释中写明了是防止内存泄漏
        WebViewCore::WebViewCore(JNIEnv* env, jobject javaWebViewCore, WebCore::Frame* mainframe)
        ... , m_javaGlue(new JavaGlue) ...  {

            jclass clazz = env->GetObjectClass(javaWebViewCore);
            m_javaGlue->m_obj = env->NewWeakGlobalRef(javaWebViewCore);
            m_javaGlue->m_scrollTo = GetJMethod(env, clazz, "contentScrollTo", "(IIZZ)V");
            ...
        }

        WebViewCore::~WebViewCore()                                                                            
        {                                                                                                      
            WebViewCore::removeInstance(this);                                                                 

            if (m_javaGlue->m_obj) {
                JNIEnv* env = JSC::Bindings::getJNIEnv();
                env->DeleteWeakGlobalRef(m_javaGlue->m_obj);                                                   
                m_javaGlue->m_obj = 0;                                                                         
            }
            delete m_javaGlue;
        }  

    }

    // Field ids for Java WebViewCore
    struct WebViewCoreFields {
        // 将native的WebViewCore的指针保存到java WebViewCore中
        jfieldID    m_nativeClass; 

        // 对应meta "viewport"中的width, height, initial-scale, 
            // maximum-scale, mimimum-scale, user-scalable
            // target-densitydpi: (low-dpi:120, medium-dpi:160, high-dpi:240, device-dpi:0)

            // 参见 ../../webtech/html/note.meta.viewport.html
            // 参见WebCore/page/Settings.cpp
        jfieldID    m_viewportWidth;        
        jfieldID    m_viewportHeight;
        jfieldID    m_viewportInitialScale;
        jfieldID    m_viewportMinimumScale;
        jfieldID    m_viewportMaximumScale;
        jfieldID    m_viewportUserScalable;
        jfieldID    m_viewportDensityDpi;

        jfieldID    m_webView;
        jfieldID    m_drawIsPaused;
        jfieldID    m_lowMemoryUsageMb;
        jfieldID    m_highMemoryUsageMb;
        jfieldID    m_highUsageDeltaMb;
    } gWebViewCoreFields;

}

// 内部数据
{
    CacheBuilder
    CachedRoot* m_temp;
    CachedHistory m_history;

    struct JavaGlue*       m_javaGlue;

    WebCore::Frame*        m_mainFrame;

    WebCoreReply*          m_popupReply;

    WebCore::Node* m_lastFocused;
    WebCore::IntRect m_lastFocusedBounds;
    WebCore::Node* currentFocus()

    PictureSet m_content; // the set of pictures to draw
    SkRegion m_addInval; // the accumulated inval region (not yet drawn)
    SkRegion m_rebuildInval; // the accumulated region for rebuilt pictures

    SkTDArray<PluginWidgetAndroid*> m_plugins;

    Node* m_currentNodeDomNavigationAxis;
    DeviceMotionAndOrientationManager m_deviceMotionAndOrientationManager;

    scoped_refptr<WebRequestContext> m_webRequestContext;

}

// Native to Java
{
    // UI事件相关
    {
        void formDidBlur(const WebCore::Node*);
        void focusNodeChanged(const WebCore::Node*);

        void listBoxRequest(WebCoreReply* reply, const uint16_t** labels,
                size_t count, const int enabled[], size_t enabledCount,
                bool multiple, const int selected[], size_t selectedCountOrSelection, int selectSize);
    }

    // 绘制相关
    {
        /**
         * Record the invalid rectangle
         */
        void contentInvalidate(const WebCore::IntRect &rect);
        void contentInvalidateAll();

        /**
         * Satisfy any outstanding invalidates, so that the current state
         * of the DOM is drawn.
         */
        void contentDraw();

        /**
         * copy the layers to the UI side
         */
        void layersDraw();

#if USE(ACCELERATED_COMPOSITING)
        GraphicsLayerAndroid* graphicsRootLayer() const;
#endif

        /** Invalidate the view/screen, NOT the content/DOM, but expressed in
         *  content/DOM coordinates (i.e. they need to eventually be scaled,
         *  by webview into view.java coordinates
         */
        void viewInvalidate(const WebCore::IntRect& rect);

        /**
         * Invalidate part of the content that may be offscreen at the moment
         */
        void offInvalidate(const WebCore::IntRect &rect);
    }

    // layout相关
    {
        /**
         * Notify the view that WebCore did its first layout.
         */
        void didFirstLayout();

        /**
         * Notify the view to restore the screen width, which in turn restores
         * the scale. Also restore the scale for the text wrap.
         */
        void restoreScale(float scale, float textWrapScale);
    }

    // edit相关
    {
        /**
         * Tell the java side to update the focused textfield
         * @param pointer   Pointer to the node for the input field.
         * @param   changeToPassword  If true, we are changing the textfield to
         *          a password field, and ignore the String
         * @param text  If changeToPassword is false, this is the new text that
         *              should go into the textfield.
         */
        void updateTextfield(WebCore::Node* pointer,
                bool changeToPassword, const WTF::String& text);

        /**
         * Tell the java side to update the current selection in the focused
         * textfield to the WebTextView.  This function finds the currently
         * focused textinput, and passes its selection to java.
         * If there is no focus, or it is not a text input, this does nothing.
         */
        void updateTextSelection();

        void clearTextEntry();
    }

    // 滚动相关
    {
        /**
         * Scroll to an absolute position.
         * @param x The x coordinate.
         * @param y The y coordinate.
         * @param animate If it is true, animate to the new scroll position
         *
         * This method calls Java to trigger a gradual scroll event.
         */
        void scrollTo(int x, int y, bool animate = false);

        /**
         * Tell the Java side of the scrollbar mode
         */
        void setScrollbarModes(ScrollbarMode horizontalMode, ScrollbarMode verticalMode);
    }


    // JavaScript support
    {
        void jsAlert(const WTF::String& url, const WTF::String& text);
        bool jsConfirm(const WTF::String& url, const WTF::String& text);
        bool jsPrompt(const WTF::String& url, const WTF::String& message,
                const WTF::String& defaultValue, WTF::String& result);
        bool jsUnload(const WTF::String& url, const WTF::String& message);
        bool jsInterrupt();
    }

    // 加载相关
    {
        /**
         * Called by webcore when the progress indicator is done
         * used to rebuild and display any changes in focus
         */
        void notifyProgressFinished();
    }

    // DB相关
    {
        /**
         * Posts a message to the UI thread to inform the Java side that the
         * origin has exceeded its database quota.
         * @param url The URL of the page that caused the quota overflow
         * @param databaseIdentifier the id of the database that caused the
         *     quota overflow.
         * @param currentQuota The current quota for the origin
         * @param estimatedSize The estimated size of the database
         * @return Whether the message was successfully sent.
         */
        bool exceededDatabaseQuota(const WTF::String& url,
                const WTF::String& databaseIdentifier,
                const unsigned long long currentQuota,
                const unsigned long long estimatedSize);

        /**
         * Posts a message to the UI thread to inform the Java side that the
         * appcache has exceeded its max size.
         * @param spaceNeeded is the amount of disk space that would be needed
         * in order for the last appcache operation to succeed.
         * @return Whether the message was successfully sent.
         */
        bool reachedMaxAppCacheSize(const unsigned long long spaceNeeded);
    }

    // PageGroup
    {
        /**
         * Set up the PageGroup's idea of which links have been visited,
         * with the browser history.
         * @param group the object to deliver the links to.
         */
        void populateVisitedLinks(WebCore::PageGroup*);

    }

    // 地理位置相关
    {
        /**
         * Instruct the browser to show a Geolocation permission prompt for the
         * specified origin.
         * @param origin The origin of the frame requesting Geolocation
         *     permissions.
         */
        void geolocationPermissionsShowPrompt(const WTF::String& origin);
        /**
         * Instruct the browser to hide the Geolocation permission prompt.
         */
        void geolocationPermissionsHidePrompt();

        jobject getDeviceMotionService();
        jobject getDeviceOrientationService();

    }

    // Check whether a media mimeType is supported in Android media framework.
    static bool isSupportedMediaMimeType(const WTF::String& mimeType);
}

// Java 2 native
{
    // Document相关
    {
        WTF::String retrieveHref(int x, int y);
        WTF::String retrieveAnchorText(int x, int y);
        WTF::String retrieveImageSource(int x, int y);
        WTF::String requestLabel(WebCore::Frame* , WebCore::Node* );

        void saveDocumentState(WebCore::Frame* frame);

        void updateFrameCache();
        void updateCacheOnNodeChange();
        void dumpDomTree(bool);
        void dumpRenderTree(bool);
        void dumpNavTree();
    }

    // 滚动相关
    {
        // If the focus is a textfield (<input>), textarea, or contentEditable,
        // scroll the selection on screen (if necessary).
        void revealSelection();

        // set the scroll amount that webview.java is currently showing
        void setScrollOffset(int moveGeneration, bool sendScrollEvent, int dx, int dy);

        /**
         * Scroll the focused textfield to (x, y) in document space
         */
        void scrollFocusedTextInput(float x, int y);
    }

    // 绘制相关
    {
        // Create a single picture to represent the drawn DOM (used by navcache)
        void recordPicture(SkPicture* picture);

        void setBackgroundColor(SkColor c);

        // Make the rect (left, top, width, height) visible. If it can be fully
        // fit, center it on the screen. Otherwise make sure the point specified
        // by (left + xPercentInDoc * width, top + yPercentInDoc * height)
        // pinned at the screen position (xPercentInView, yPercentInView).
        void showRect(int left, int top, int width, int height, int contentWidth,
                int contentHeight, float xPercentInDoc, float xPercentInView,
                float yPercentInDoc, float yPercentInView);

        // reset the picture set to empty
        void clearContent();

        // record the inval area, and the picture size
        BaseLayerAndroid* recordContent(SkRegion* , SkIPoint* );

        // This creates a new BaseLayerAndroid by copying the current m_content
        // and doing a copy of the layers. The layers' content may be updated
        // as we are calling layersSync().
        BaseLayerAndroid* createBaseLayer(SkRegion*);
        bool updateLayers(LayerAndroid*);

    }

    // UI事件
    {
        void moveFocus(WebCore::Frame* frame, WebCore::Node* node);
        void moveMouse(WebCore::Frame* frame, int x, int y);
        void moveMouseIfLatest(int moveGeneration,
                WebCore::Frame* frame, int x, int y);

        /**
         * Handle key events from Java.
         * @return Whether keyCode was handled by this class.
         */
        bool key(const WebCore::PlatformKeyboardEvent& event);

        /**
         * Handle (trackball) click event / dpad center press from Java.
         * Also used when typing into an unfocused textfield, in which case 'fake'
         * will be true.
         */
        void click(WebCore::Frame* frame, WebCore::Node* node, bool fake);

        /**
         * Handle touch event
         */
        bool handleTouchEvent(int action, Vector<int>& ids, Vector<IntPoint>& points, int actionIndex, int metaState);

        /**
         * Handle motionUp event from the UI thread (called touchUp in the
         * WebCore thread).
         * @param touchGeneration Generation number for touches so we can ignore
         *      touches when a newer one has been generated.
         * @param frame Pointer to Frame containing the node that was touched.
         * @param node Pointer to Node that was touched.
         * @param x x-position of the touch.
         * @param y y-position of the touch.
         */
        void touchUp(int touchGeneration, WebCore::Frame* frame,
                WebCore::Node* node, int x, int y);


        /**
         * Sets the index of the label from a popup
         */
        void popupReply(int index);
        void popupReply(const int* array, int count);

        /**
         * Set the FocusController's active and focused states, so that
         * the caret will draw (true) or not.
         */
        void setFocusControllerActive(bool active);

        bool focusBoundsChanged();

        void updateCursorBounds(const CachedRoot* root,
                const CachedFrame* cachedFrame, const CachedNode* cachedNode);

    }

    // select相关
    {
        /**
         *  Delete text from start to end in the focused textfield.
         *  If start == end, set the selection, but perform no deletion.
         *  If there is no focus, silently fail.
         *  If start and end are out of order, swap them.
         */
        void deleteSelection(int start, int end, int textGeneration);

        /**
         *  Set the selection of the currently focused textfield to (start, end).
         *  If start and end are out of order, swap them.
         */
        void setSelection(int start, int end);

        /**
         * Modifies the current selection.
         *
         * Note: Accessibility support.
         *
         * direction - The direction in which to alter the selection.
         * granularity - The granularity of the selection modification.
         *
         * returns - The selected HTML as a string. This is not a well formed
         *           HTML, rather the selection annotated with the tags of all
         *           intermediary elements it crosses.
         */
        String modifySelection(const int direction, const int granularity);

        /**
         *  In the currently focused textfield, replace the characters from oldStart to oldEnd
         *  (if oldStart == oldEnd, this will be an insert at that position) with replace,
         *  and set the selection to (start, end).
         */
        void replaceTextfieldText(int oldStart,
                int oldEnd, const WTF::String& replace, int start, int end,
                int textGeneration);

    }

    // layout相关
    {
        void setGlobalBounds(int x, int y, int h, int v);

        void setSizeScreenWidthAndScale(int width, int height, int screenWidth,
                float scale, int realScreenWidth, int screenHeight, int anchorX,
                int anchorY, bool ignoreHeight);

        bool validNodeAndBounds(Frame* , Node* , const IntRect& );

        // Scale the rect (x, y, width, height) to make it just fit and centered
        // in the current view.
        void centerFitRect(int x, int y, int width, int height);

        // return a list of rects matching the touch point (x, y) with the slop
        Vector<IntRect> getTouchHighlightRects(int x, int y, int slop);

        int textWrapWidth() const { return m_textWrapWidth; }
        float scale() const { return m_scale; }
        float textWrapScale() const { return m_screenWidth * m_scale / m_textWrapWidth; }
    }

    // JS相关
    {
        // TODO: I don't like this hack but I need to access the java object in
        // order to send it as a parameter to java
        AutoJObject getJavaObject();

        // Return the parent WebView Java object associated with this
        // WebViewCore.
        jobject getWebViewJavaObject();
    }

    // plugin相关
    {
    }
}

// loader相关
{
#if USE(CHROME_NETWORK_STACK)
    void setWebRequestContextUserAgent();
    void setWebRequestContextCacheMode(int mode);
    WebRequestContext* webRequestContext();
#endif
}

{
    // Attempts to scroll the layer to the x,y coordinates of rect. The
    // layer is the id of the LayerAndroid.
    void scrollRenderLayer(int layer, const SkRect& rect);

}

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

