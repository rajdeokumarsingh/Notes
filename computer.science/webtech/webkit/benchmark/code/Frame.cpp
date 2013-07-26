结构性方法 {
// 创建一个Frame, 并根据是否存在owner element设置其为main frame
PassRefPtr<Frame> Frame::create(Page* page, HTMLFrameOwnerElement* ownerElement, FrameLoaderClient* client)    
    RefPtr<Frame> frame = adoptRef(new Frame(page, ownerElement, client));                             
    if (!ownerElement) page->setMainFrame(frame);                                                                     

// 隐藏FrameView, 使其脱离Frame并置空
// 取消当前的加载，并清理FrameLoader
// 设置owner element为空
// 将DOMWindow脱离Frame
// 清理script controller
Frame::~Frame() 
    setView(0);
    loader()->cancelAndClear();  

    disconnectOwnerElement();

    m_domWindow->disconnectFrame();
    script()->clearWindowShell();

    m_view->hide();
    m_view->clearFrame();

// 清理当前的FrameView, Document, EventHandler
// 设置新的FrameView
// FIXME: 什么时候需要setView? 跳转到一个新的url
void Frame::setView(PassRefPtr<FrameView> view)
    m_view->detachCustomScrollbars();
    m_doc->detach();
    m_view->unscheduleRelayout();
    eventHandler()->clear();

    m_view = view;

// Detach当前的Document
// 设置并attach新的Document
// 更新selection, script controller
// 更新Page的viewport参数
void Frame::setDocument(PassRefPtr<Document> newDoc)
    m_doc->detach();

    m_doc = newDoc;
    m_doc->attach();

    selection()->updateSecureKeyboardEntryIfActive();
    m_script.updateDocument();
    m_page->updateViewportArguments();

// 通过document发送orientation变化事件
void Frame::sendOrientationChangeEvent(int orientation)
    m_orientation = orientation;
    document()->dispatchWindowEvent(Event::create(
        eventNames().orientationchangeEvent, false, false));

// FIXME: 设置打印页面的参数 ??
void Frame::setPrinting(bool printing, 
        const FloatSize& pageSize, 
        float maximumShrinkRatio, 
        AdjustViewSizeOrNot shouldAdjustViewSize)

    m_doc->setPrinting(printing);
    view()->adjustMediaTypeForPrinting(printing);

    m_doc->styleSelectorChanged(RecalcStyleImmediately);
    view()->forceLayoutForPagination(pageSize, maximumShrinkRatio, shouldAdjustViewSize);

    for (Frame* child = tree()->firstChild(); child; child = child->tree()->nextSibling())
        child->setPrinting(printing, IntSize(), 0, shouldAdjustViewSize);

// FIXME: what is user scripts
// relative to PageGroup
// execute a specific JS by script controller
void Frame::injectUserScripts(UserScriptInjectionTime injectionTime)
    const UserScriptMap* userScripts = m_page->group().userScripts();
    UserScriptMap::const_iterator end = userScripts->end();
    for (UserScriptMap::const_iterator it = userScripts->begin(); it != end; ++it)
        injectUserScriptsForWorld(it->first.get(), *it->second, injectionTime);

void Frame::injectUserScriptsForWorld(DOMWrapperWorld* world, 
        const UserScriptVector& userScripts, 
        UserScriptInjectionTime injectionTime)

    for (unsigned i = 0; i < count; ++i) {
        UserScript* script = userScripts[i].get();
        if (script->injectedFrames() == InjectInTopFrameOnly && ownerElement())
            continue;

        if (script->injectionTime() == injectionTime && 
                UserContentURLPattern::matchesPatterns(
                doc->url(), script->whitelist(), script->blacklist()))
            m_script.evaluateInWorld(ScriptSourceCode(script->source(), script->url()), world);


// 激活life support timer一次
void Frame::keepAlive()
    if (m_lifeSupportTimer.isActive())
        return;
    ref();
    m_lifeSupportTimer.startOneShot(0);

void Frame::lifeSupportTimerFired(Timer<Frame>*)
    deref();

// 清理DOMWindow
void Frame::clearDOMWindow()
    if (m_domWindow) {
        m_liveFormerWindows.add(m_domWindow.get());
        m_domWindow->clear();
    }
    m_domWindow = 0;

// 返回widget对应的frame
Frame* Frame::frameForWidget(const Widget* widget)
    if (RenderWidget* renderer = RenderWidget::find(widget))
        if (Node* node = renderer->node())
            return node->document()->frame();

    return static_cast<const FrameView*>(widget)->frame();

// 停止frame的动画
// 停止frame event handler中的auto scroll timer
void Frame::clearTimers(FrameView *view, Document *document)
    view->unscheduleRelayout();
    view->frame()->animation()->suspendAnimationsForDocument(document);
    view->frame()->eventHandler()->stopAutoscrollTimer();

// 清除原来的dom window
// 设置新的dom window
void Frame::setDOMWindow(DOMWindow* domWindow)
    if (m_domWindow)
        m_liveFormerWindows.add(m_domWindow.get());
        m_domWindow->clear();

    m_domWindow = domWindow;

// 创建并获取dom window
DOMWindow* Frame::domWindow() const
    if (!m_domWindow)
        m_domWindow = DOMWindow::create(const_cast<Frame*>(this));

    return m_domWindow.get();

void Frame::clearFormerDOMWindow(DOMWindow* window)
    m_liveFormerWindows.remove(window);

// Page销毁时被调用
// 清空dom window
// 清空focus frame
// 清空script controller
// 从Page中移除Frame
void Frame::pageDestroyed()
    if (Frame* parent = tree()->parent())
        parent->loader()->checkLoadComplete();

    m_domWindow->resetGeolocation();
    m_domWindow->pageDestroyed();

    page()->focusController()->setFocusedFrame(0);

    script()->clearWindowShell();
    script()->clearScriptObjects();
    script()->updatePlatformScriptObjects();

    detachFromPage();

// 将Frame移动到新的Page中
// 清空原来Page的focused frame
// 减少原来Page的reference count
// 重置地理位置信息
// 设置新的Page
// 增加新Page的reference count
// 更新frame tree
// 更新FrameLoader和FrameLoaderClient
// 对所有的child frame递归调用transferChildFrameToNewDocument
void Frame::transferChildFrameToNewDocument()

    Frame* newParent = m_ownerElement->document()->frame();

    Page* newPage = newParent->page();
    Page* oldPage = m_page;

    if (m_page != newPage) {
        m_page->focusController()->setFocusedFrame(0);
        m_page->decrementFrameCount();

        m_domWindow->resetGeolocation();

        m_page = newPage;
        newPage->incrementFrameCount();
    }

    // Update the frame tree.
    newParent->tree()->transferChild(this);

    loader()->client()->didTransferChildFrameToNewDocument(oldPage);
    loader()->transferLoadingResourcesFromPage(oldPage);

    // Do the same for all the children.
    for (Frame* child = tree()->firstChild(); child; child = child->tree()->nextSibling())
        child->transferChildFrameToNewDocument();


// 返回点所在的Document
Document* Frame::documentAtPoint(const IntPoint& point)
    IntPoint pt = view()->windowToContents(point);
    HitTestResult result = HitTestResult(pt);

    if (contentRenderer())
        result = eventHandler()->hitTestResultAtPoint(pt, false);
    return result.innerNode() ? result.innerNode()->document() : 0;
}


UI 相关方法 {
    创建FrameView
    缩放页面

    // 返回当前Frame的RenderObject的根节点
    RenderView* Frame::contentRenderer() const
        return static_cast<RenderView*>(document()->renderer());

    // 返回当前Frame parent的RenderObject的根节点
    RenderPart* Frame::ownerRenderer() const
        return static_cast<RenderPart*>(m_ownerElement->renderer());

    // 创建FrameView
    // 设置FrameView的滚动条模式
    // 设置FrameView的背景
    // 将FrameView添加到owner render中
    void Frame::createView(const IntSize& viewportSize,
            const Color& backgroundColor, bool transparent,
            const IntSize& fixedLayoutSize, bool useFixedLayout,
            ScrollbarMode horizontalScrollbarMode, bool horizontalLock,
            ScrollbarMode verticalScrollbarMode, bool verticalLock) {

        setView(0);
        RefPtr<FrameView> frameView;
        if (isMainFrame) {                  
            frameView = FrameView::create(this, viewportSize);
            frameView->setFixedLayoutSize(fixedLayoutSize);
            frameView->setUseFixedLayout(useFixedLayout);
        } else
            frameView = FrameView::create(this);

        frameView->setScrollbarModes(horizontalScrollbarMode, 
                verticalScrollbarMode, horizontalLock, verticalLock);
        setView(frameView);

        frameView->updateBackgroundRecursively(backgroundColor, transparent);

        if (isMainFrame)
            frameView->setParentVisible(true);

        if (ownerRenderer())
            ownerRenderer()->setWidget(frameView);

        if (HTMLFrameOwnerElement* owner = ownerElement())
            view()->setCanHaveScrollbars(owner->scrollingMode() != ScrollbarAlwaysOff);
    }

    // TODO: TiledBackingStore

    // 将layerTree转换成文本信息
    String Frame::layerTreeAsText(bool showDebugInfo) const
        document()->updateLayout();
        return contentRenderer()->compositor()->layerTreeAsText(showDebugInfo);

    // 设置页面和文本的缩放系数
    // 调整滚动条的位置
    // 通知document, 强制重新更新style
    // 对于每一个子frame, 递归调用setPageAndTextZoomFactors
    // 通知FrameView, 发起layout过程
    void Frame::setPageAndTextZoomFactors(float pageZoomFactor, float textZoomFactor)
        m_editor.dismissCorrectionPanelAsIgnored();

        // Update the scroll position when doing a full page zoom, 
        // so the content stays in relatively the same position.
        // 假设页面被放大了2倍，那么滚动条的位置也相应应该乘以2
        IntPoint scrollPosition = view->scrollPosition();
        float percentDifference = (pageZoomFactor / m_pageZoomFactor);
        view->setScrollPosition(IntPoint(scrollPosition.x() * percentDifference, 
                    scrollPosition.y() * percentDifference));

        m_pageZoomFactor = pageZoomFactor;
        m_textZoomFactor = textZoomFactor;

        document->recalcStyle(Node::Force);

        for (Frame* child = tree()->firstChild(); child; child = child->tree()->nextSibling())
            child->setPageAndTextZoomFactors(m_pageZoomFactor, m_textZoomFactor);

        if (FrameView* view = this->view()) 
            if (document->renderer() && document->renderer()->needsLayout() && view->didFirstLayout())
                view->layout();

    // 更新内容的缩放
    #if USE(ACCELERATED_COMPOSITING)
    void Frame::updateContentsScale(float scale)
        for (Frame* child = tree()->firstChild(); child; child = child->tree()->nextSibling())
            child->updateContentsScale(scale);

        RenderView* root = contentRenderer();
        if (root && root->compositor())
            root->compositor()->updateContentsScale(scale);
    #endif


    // 缩放页面
    // 设置document的render, 需要layout
    // 通知document, 强制重新更新style
    // 更新内容的缩放
    // 通知FrameView, 开始layout流程
    // 设置scroll的位置
    void Frame::scalePage(float scale, const IntPoint& origin)
        m_pageScaleFactor = scale;

        document->renderer()->setNeedsLayout(true);
        document->recalcStyle(Node::Force);

    #if USE(ACCELERATED_COMPOSITING)
        updateContentsScale(scale);
    #endif

        if (document->renderer() && document->renderer()->needsLayout() && view->didFirstLayout())
            view->layout();
        view->setScrollPosition(origin);
}

查找文字相关方法 {
    // 创建一个正则表达式, 输入是一个String的vector
    // 输出是正则表达式(v[0]|v[1]|v[2]|...|v[len-1])
    static RegularExpression* createRegExpForLabels(const Vector<String>& labels)

    // 查找HTMLTableCellElement中匹配正则表达式regExp的字符串
    // 返回满足的字符串和字符串的位置HTMLTableCellElement
    String Frame::searchForLabelsAboveCell(RegularExpression* regExp, 
        HTMLTableCellElement* cell, 
        size_t* resultDistanceFromStartOfCell)

    // 查找element之前的节点(table, text)，是否包括字符数组labels
    String Frame::searchForLabelsBeforeElement(
        const Vector<String>& labels, 
        Element* element, size_t* resultDistance, 
        bool* resultIsInCellAbove)


    // 从stringToMatch搜寻"(labels[0]|labels[1]|...|labels[len-1])"
    // 并返回最长的匹配
    // 也就是返回最长的匹配stringToMatch的labels[i]
    static String matchLabelsAgainstString(
            const Vector<String>& labels, const String& stringToMatch)

    // Match against the name element
    // then against the id element 
        // if no match is found for the name element.
    String Frame::matchLabelsAgainstElement(
        const Vector<String>& labels, Element* element)

        String resultFromNameAttribute = 
            matchLabelsAgainstString(labels, 
            element->getAttribute(nameAttr));
        if (!resultFromNameAttribute.isEmpty())
                return resultFromNameAttribute;

        return matchLabelsAgainstString(labels, 
                element->getAttribute(idAttr));
}


