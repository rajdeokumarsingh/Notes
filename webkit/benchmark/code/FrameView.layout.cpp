
// 计算scroll bar的mode
// 设置root和body的child需要layout
// 开始递归向下的layout
// 更新所有render layer的位置
// 更新compositing layer
// 对于android, 设置compositing模式
void FrameView::layout(bool allowSubtree)
{
    if (m_inLayout) return;

    m_layoutTimer.stop();
    m_delayedLayout = false;
    m_setNeedsLayoutWasDeferred = false;


    // Protect the view from being deleted during layout (in recalcStyle)
    RefPtr<FrameView> protector(this);

    // we shouldn't enter layout() while painting
    if (isPainting()) return;

    if (!allowSubtree && m_layoutRoot) {
        m_layoutRoot->markContainingBlocksForLayout(false);
        m_layoutRoot = 0;
    }

    Document* document = m_frame->document();

    m_layoutSchedulingEnabled = false;

    if (!m_nestedLayoutCount && !m_inSynchronousPostLayout && m_hasPendingPostLayoutTasks && !inSubframeLayoutWithFrameFlattening) {
        // This is a new top-level layout. If there are any remaining tasks from the previous
        // layout, finish them now.
        m_inSynchronousPostLayout = true;
        m_postLayoutTasksTimer.stop();
        performPostLayoutTasks();
        m_inSynchronousPostLayout = false;
    }

    // Viewport-dependent media queries may cause us to need completely different style information.
    // Check that here.
    if (document->styleSelector()->affectedByViewportChange())
        document->styleSelectorChanged(RecalcStyleImmediately);

    // Always ensure our style info is up-to-date.  This can happen in situations where
    // the layout beats any sort of style recalc update that needs to occur.
    document->updateStyleIfNeeded();

    bool subtree = m_layoutRoot;

    RenderObject* root = subtree ? m_layoutRoot : document->renderer();
    
    // 设置body的render是否需要layout children

    calculateScrollbarModesForLayout(hMode, vMode);

    m_doFullRepaint = !subtree && (m_firstLayout || toRenderView(root)->printing());

    if (!subtree) {
        // Now set our scrollbar state for the layout.
        
        m_size = IntSize(layoutWidth(), layoutHeight());

        // 设置root和body的child需要layout
        m_doFullRepaint = true;
        if (!m_firstLayout) {
            RenderBox* rootRenderer = document->documentElement() ? document->documentElement()->renderBox() : 0;
            RenderBox* bodyRenderer = rootRenderer && document->body() ? document->body()->renderBox() : 0;
            if (bodyRenderer && bodyRenderer->stretchesToViewport())
                bodyRenderer->setChildNeedsLayout(true);
            else if (rootRenderer && rootRenderer->stretchesToViewport())
                rootRenderer->setChildNeedsLayout(true);
        }

    }

    // 开始递归向下的layout
    m_inLayout = true;
    beginDeferredRepaints();
    root->layout();
    endDeferredRepaints();
    m_inLayout = false;

    m_layoutSchedulingEnabled = true;

    if (!subtree && !toRenderView(root)->printing())
        adjustViewSize();

    // Now update the positions of all layers.
    beginDeferredRepaints();
    IntPoint cachedOffset;
    if (m_doFullRepaint)
        root->view()->repaint(); // FIXME: This isn't really right, since the RenderView doesn't fully encompass the visibleContentRect(). It just happens
    // to work out most of the time, since first layouts and printing don't have you scrolled anywhere.
    RenderLayer* layer = root->enclosingLayer();
    layer->updateLayerPositions((m_doFullRepaint ? 0 : RenderLayer::CheckForRepaint)
            | RenderLayer::IsCompositingUpdateRoot
            | RenderLayer::UpdateCompositingLayers,
            subtree ? 0 : &cachedOffset);
    endDeferredRepaints();

#if USE(ACCELERATED_COMPOSITING)
    updateCompositingLayers();
#endif

    m_layoutCount++;

#ifdef ANDROID_INSTRUMENT
    if (!m_frame->tree()->parent())
        android::TimeCounter::record(android::TimeCounter::LayoutTimeCounter, __FUNCTION__);
#endif

    // 设置android的overflow scoll
    // 如果owner render是iframe,  而且width和height都大于1, 而设置m_hasOverflowScroll为true
    // 如果m_hasOverflowScroll为true, 则enterCompositingMode, 否则scheduleCompositingLayerUpdate
#if ENABLE(ANDROID_OVERFLOW_SCROLL)
    // Reset to false each time we layout in case the overflow status changed.
    bool hasOverflowScroll = false;
    RenderObject* ownerRenderer = m_frame->ownerRenderer();
    if (ownerRenderer && ownerRenderer->isRenderIFrame()) {
        RenderLayer* layer = ownerRenderer->enclosingLayer();
        if (layer) {
            // Some sites use tiny iframes for loading so don't composite those.
            if (canHaveScrollbars() && layoutWidth() > 1 && layoutHeight() > 1)
                hasOverflowScroll = layoutWidth() < contentsWidth() || layoutHeight() < contentsHeight();
        }
    }
    if (RenderView* view = m_frame->contentRenderer()) {
        if (hasOverflowScroll != m_hasOverflowScroll) {
            if (hasOverflowScroll)
                enterCompositingMode();
            else
                // We are leaving overflow mode so we need to update the layer
                // tree in case that is the reason we were composited.
                view->compositor()->scheduleCompositingLayerUpdate();
        }
    }
    m_hasOverflowScroll = hasOverflowScroll;
#endif

}


// 更新frame rect
void FrameView::setFrameRect(const IntRect& newRect)
{
    ScrollView::setFrameRect(newRect);

#if USE(ACCELERATED_COMPOSITING)
    if (RenderView* root = m_frame->contentRenderer()) {
        if (root->usesCompositing())
            root->compositor()->frameViewDidChangeSize();
    }
#endif
}


void FrameView::setContentsSize(const IntSize& size)
{
    ScrollView::setContentsSize(size);
    scrollAnimator()->contentsResized();

    page->chrome()->contentsSizeChanged(frame(), size); //notify only
}

// 设置scoll的origin为(-docLeft(), -docTop()). FIXME: why
// 设置content的size为document的(docWidth(), docHeight())
void FrameView::adjustViewSize()
{
    RenderView* root = m_frame->contentRenderer();
    IntSize size = IntSize(root->docWidth(), root->docHeight());

    ScrollView::setScrollOrigin(IntPoint(-root->docLeft(), -root->docTop()), 
            !m_frame->document()->printing(), size == contentsSize());
    setContentsSize(size);
}


/*
处理css overflow属性
visible默认值。内容不会被修剪，会呈现在元素框之外。
hidden内容会被修剪，并且其余内容是不可见的。
scroll内容会被修剪，但是浏览器会显示滚动条以便查看其余的内容。
auto如果内容被修剪，则浏览器会显示滚动条以便查看其余的内容。
inherit规定应该从父元素继承 overflow 属性的值。
*/
void FrameView::applyOverflowToViewport(RenderObject* o, ScrollbarMode& hMode, ScrollbarMode& vMode)
{
    // Handle the overflow:hidden/scroll case for the body/html elements.      
    switch (o->style()->overflowX()) {
        case OHIDDEN:
            hMode = ScrollbarAlwaysOff;
            break;
        case OSCROLL:
            hMode = ScrollbarAlwaysOn;
            break;
        case OAUTO:
            hMode = ScrollbarAuto;
            break;
        default:
            // Don't set it at all.
            ;
    }
    switch (o->style()->overflowY()) {
    // ...
    }
    m_viewportRenderer = o;
}

// 首先根据owner element的scoll属性设置scroll bar
// 设置body或document的scroll bar
// root render是document element的render
void FrameView::calculateScrollbarModesForLayout(ScrollbarMode& hMode, ScrollbarMode& vMode)
{
    m_viewportRenderer = 0;

    const HTMLFrameOwnerElement* owner = m_frame->ownerElement();
    if (owner && (owner->scrollingMode() == ScrollbarAlwaysOff)) {
        hMode = ScrollbarAlwaysOff;
        vMode = ScrollbarAlwaysOff;
        return;
    }

    if (m_canHaveScrollbars) {
        hMode = ScrollbarAuto;
        vMode = ScrollbarAuto;
    } else {
        hMode = ScrollbarAlwaysOff;
        vMode = ScrollbarAlwaysOff;
    }

    if (!m_layoutRoot) {
        Document* document = m_frame->document();
        Node* documentElement = document->documentElement();
        RenderObject* rootRenderer = documentElement ? documentElement->renderer() : 0;
        Node* body = document->body();
        if (body && body->renderer()) {
            if (body->hasTagName(framesetTag) && m_frame->settings() && !m_frame->settings()->frameFlatteningEnabled()) {
                vMode = ScrollbarAlwaysOff;
                hMode = ScrollbarAlwaysOff;
            } else if (body->hasTagName(bodyTag)) {
                // It's sufficient to just check the X overflow,
                // since it's illegal to have visible in only one direction.
                RenderObject* o = rootRenderer->style()->overflowX() == OVISIBLE && document->documentElement()->hasTagName(htmlTag) ? body->renderer() : rootRenderer;
                applyOverflowToViewport(o, hMode, vMode);
            }
        } else if (rootRenderer) {
            applyOverflowToViewport(rootRenderer, hMode, vMode);
        }
    }
}

// 调用document view的willMoveOffscreen
// 调用scroll animator的contentAreaDidHide
void FrameView::willMoveOffscreen()
{
    RenderView* view = m_frame->contentRenderer();
    view->willMoveOffscreen();
    scrollAnimator()->contentAreaDidHide();
}

// 调用document view的didMoveOnscreen
// 调用scroll animator的contentAreaDidShow
void FrameView::didMoveOnscreen()
{
    RenderView* view = m_frame->contentRenderer();
    view->didMoveOnscreen();
    scrollAnimator()->contentAreaDidShow();
}

#if PLATFORM(ANDROID)
// When the screen size change, fixed positioned element should be updated.
// 获取document中所有的positioned objects
// 如果positioned object为fixed postion, 重新计算其逻辑尺寸
void FrameView::updatePositionedObjects()
{
    // 获取document中所有的positioned objects
    RenderBlock::PositionedObjectsListHashSet* positionedObjects = 0;
    if (RenderView* root = m_frame->contentRenderer())
        positionedObjects = root->positionedObjects();

    // 如果positioned object为fixed postion, 重新计算其逻辑尺寸
    RenderBlock::PositionedObjectsListHashSet::const_iterator end = positionedObjects->end();
    for (RenderBlock::PositionedObjectsListHashSet::const_iterator it = positionedObjects->begin(); it != end; ++it) {
        RenderBox* renderBox = *it;
        if (renderBox->style()->position() != FixedPosition)
            continue;

        renderBox->computeLogicalWidth();
        renderBox->computeLogicalHeight();
    }
}
#endif


// 注意没有scale的时候， 函数返回x
int FrameView::scrollXForFixedPosition() const
{
    int visibleContentWidth = visibleContentRect().width();
    int maxX = contentsWidth() - visibleContentWidth;

    int x = scrollX();

    // 注意没有scale的时候, pageScaleFactor = 1， 函数返回x
    float pageScaleFactor = m_frame->pageScaleFactor();

    // When the page is scaled, the scaled "viewport" with respect to which fixed object are positioned
    // doesn't move as fast as the content view, so that when the content is scrolled all the way to the
    // end, the bottom of the scaled "viewport" touches the bottom of the real viewport.
    float dragFactor = (contentsWidth() - visibleContentWidth * pageScaleFactor) / maxX;

    return x * dragFactor / pageScaleFactor;
}

int FrameView::scrollYForFixedPosition() const
{
    // ... , same as scrollXForFixedPosition()
}

// A timer is used to control schedule of layout
bool FrameView::layoutPending() const
    return m_layoutTimer.isActive();

// when we need layout
bool FrameView::needsLayout() const
{
    RenderView* root = m_frame->contentRenderer();
    return layoutPending()
        || (root && root->needsLayout())
        || m_layoutRoot
        || (m_deferSetNeedsLayouts && m_setNeedsLayoutWasDeferred);
}


// layout 完成后的工作
void FrameView::performPostLayoutTasks()
{
    m_hasPendingPostLayoutTasks = false;

    // 更新selection
    m_frame->selection()->setCaretRectNeedsUpdate();
    m_frame->selection()->updateAppearance();

    if (m_nestedLayoutCount <= 1) {
        // first layout
        if (m_firstLayoutCallbackPending) {
            m_firstLayoutCallbackPending = false;
            m_frame->loader()->didFirstLayout();
        }

        if (m_isVisuallyNonEmpty && m_firstVisuallyNonEmptyLayoutCallbackPending) {
            m_firstVisuallyNonEmptyLayoutCallbackPending = false;
            m_frame->loader()->didFirstVisuallyNonEmptyLayout();
        }
    }

    // 更新plugin
    RenderView* root = m_frame->contentRenderer();
    root->updateWidgetPositions();
    for (unsigned i = 0; i < maxUpdateWidgetsIterations; i++) {
        if (updateWidgets())
            break;
    }

    // scroll
    scrollToAnchor();

    // 发送事件
    m_actionScheduler->resume();
    if (!root->printing()) {
        IntSize currentSize = IntSize(width(), height());
        float currentZoomFactor = root->style()->zoom();
        bool resized = !m_firstLayout && (currentSize != m_lastLayoutSize || currentZoomFactor != m_lastZoomFactor);
        m_lastLayoutSize = currentSize;
        m_lastZoomFactor = currentZoomFactor;
        if (resized)
            m_frame->eventHandler()->sendResizeEvent();
    }
}

void FrameView::updateLayoutAndStyleIfNeededRecursive()
{       
    // 更新style, 并layout
    m_frame->document()->updateStyleIfNeeded();
    if (needsLayout()) layout();

    
    // 对于所有子FrameView, 更新style, 并layout
    const HashSet<RefPtr<Widget> >* viewChildren = children();
    HashSet<RefPtr<Widget> >::const_iterator end = viewChildren->end();
    for (HashSet<RefPtr<Widget> >::const_iterator current = viewChildren->begin(); current != end; ++current) {
        Widget* widget = (*current).get();
        if (widget->isFrameView())
            static_cast<FrameView*>(widget)->updateLayoutAndStyleIfNeededRecursive();
    }

    // 完成延迟的重绘
    flushDeferredRepaints();
}

