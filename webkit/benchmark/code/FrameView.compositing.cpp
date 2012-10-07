
// 判断是否是在compositing模式
bool FrameView::hasCompositedContent() const
{
#if USE(ACCELERATED_COMPOSITING)
    if (RenderView* view = m_frame->contentRenderer())
        return view->compositor()->inCompositingMode();
#endif
    return false;
}

// 判断是否是在compositing模式
bool FrameView::hasCompositedContentIncludingDescendants() const
{
#if USE(ACCELERATED_COMPOSITING)
    for (Frame* frame = m_frame.get(); frame; frame = frame->tree()->traverseNext(m_frame.get())) {
        RenderView* renderView = frame->contentRenderer();
        RenderLayerCompositor* compositor = renderView ? renderView->compositor() : 0;
        if (compositor) {
            if (compositor->inCompositingMode())
                return true;

            if (!RenderLayerCompositor::allowsIndependentlyCompositedFrames(this))
                break;
        }
    }
#endif
    return false;
}

// 判断祖先中是否有人是在compositing模式
bool FrameView::hasCompositingAncestor() const
{
#if USE(ACCELERATED_COMPOSITING)
    for (Frame* frame = m_frame->tree()->parent(); frame; frame = frame->tree()->parent()) {
        if (FrameView* view = frame->view()) {
            if (view->hasCompositedContent())
                return true;
        }
    }
#endif
    return false;
}

// 开启compositing模式
// Sometimes (for plug-ins) we need to eagerly go into compositing mode.
void FrameView::enterCompositingMode()
{
#if USE(ACCELERATED_COMPOSITING)
    if (RenderView* view = m_frame->contentRenderer()) {
        view->compositor()->enableCompositingMode();
        if (!needsLayout())
            view->compositor()->scheduleCompositingLayerUpdate();
    }
#endif
}

#if USE(ACCELERATED_COMPOSITING)
void FrameView::updateCompositingLayers()
{
    RenderView* view = m_frame->contentRenderer();

    // This call will make sure the cached hasAcceleratedCompositing is updated from the pref
    view->compositor()->cacheAcceleratedCompositingFlags();
    view->compositor()->updateCompositingLayers(CompositingUpdateAfterLayoutOrStyleChange);

#if ENABLE(FULLSCREEN_API)
    Document* document = m_frame->document();
    if (isDocumentRunningFullScreenAnimation(document))
        view->compositor()->updateCompositingLayers(
                CompositingUpdateAfterLayoutOrStyleChange, 
                document->fullScreenRenderer()->layer());
#endif
}

bool FrameView::isEnclosedInCompositingLayer() const
{
#if USE(ACCELERATED_COMPOSITING)
    RenderObject* frameOwnerRenderer = m_frame->ownerRenderer();
    if (frameOwnerRenderer && frameOwnerRenderer->containerForRepaint())
        return true;

    if (FrameView* parentView = parentFrameView())
        return parentView->isEnclosedInCompositingLayer();
#endif
    return false;
}

GraphicsLayer* FrameView::layerForHorizontalScrollbar() const {
    RenderView* view = m_frame->contentRenderer();
    return view->compositor()->layerForHorizontalScrollbar();
}

GraphicsLayer* FrameView::layerForVerticalScrollbar() const {
    RenderView* view = m_frame->contentRenderer();
    return view->compositor()->layerForVerticalScrollbar();
}

bool FrameView::syncCompositingStateForThisFrame()
{
    RenderView* view = m_frame->contentRenderer();
    
    // If we sync compositing layers when a layout is pending, we may cause painting of compositing
    // layer content to occur before layout has happened, which will cause paintContents() to bail.
    if (needsLayout())
        return false;

    if (GraphicsLayer* graphicsLayer = view->compositor()->layerForHorizontalScrollbar())
        graphicsLayer->syncCompositingStateForThisLayerOnly();
    if (GraphicsLayer* graphicsLayer = view->compositor()->layerForVerticalScrollbar())
        graphicsLayer->syncCompositingStateForThisLayerOnly();
    if (GraphicsLayer* graphicsLayer = view->compositor()->layerForScrollCorner())
        graphicsLayer->syncCompositingStateForThisLayerOnly();

    view->compositor()->flushPendingLayerChanges();
    // ...
}

// 同步本frame和subframe的compositing状态
bool FrameView::syncCompositingStateIncludingSubframes()
{
#if USE(ACCELERATED_COMPOSITING)
    bool allFramesSynced = syncCompositingStateForThisFrame();

    for (Frame* child = m_frame->tree()->firstChild(); child; child = child->tree()->traverseNext(m_frame.get())) {
        bool synced = child->view()->syncCompositingStateForThisFrame();
        allFramesSynced &= synced;
    }
    return allFramesSynced;
#else // USE(ACCELERATED_COMPOSITING)
    return true;
#endif
}

// 如果document中包括了3D内容，则不是软件渲染的
bool FrameView::isSoftwareRenderable() const
{
#if USE(ACCELERATED_COMPOSITING)
    RenderView* view = m_frame->contentRenderer();
    return !view->compositor()->has3DContent();
#else
    return true;
#endif
}



