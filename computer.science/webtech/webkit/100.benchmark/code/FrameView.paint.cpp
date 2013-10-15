enum PaintPhase {
    PaintPhaseBlockBackground,
    PaintPhaseChildBlockBackground,
    PaintPhaseChildBlockBackgrounds,
    PaintPhaseFloat,
    PaintPhaseForeground,
    PaintPhaseOutline,
    PaintPhaseChildOutlines,
    PaintPhaseSelfOutline,
    PaintPhaseSelection, 
    PaintPhaseCollapsedTableBorders,
    PaintPhaseTextClip, 
    PaintPhaseMask
};

enum PaintBehaviorFlags {
    PaintBehaviorNormal = 0,
    PaintBehaviorSelectionOnly = 1 << 0,
    PaintBehaviorForceBlackText = 1 << 1,
    PaintBehaviorFlattenCompositingLayers = 1 << 2
};

// 这时layout应该完成
// 通过root layer来绘制内容
// 通过root layer绘制scrollbars区域
void FrameView::paintContents(GraphicsContext* p, const IntRect& rect) {
    // 这时layout应该完成
    if (needsLayout()) return;

#if USE(ACCELERATED_COMPOSITING)
    if (!p->paintingDisabled())
        syncCompositingStateForThisFrame();
#endif

    // FIXME: What is "paint behavior"?
    PaintBehavior oldPaintBehavior = m_paintBehavior;

    // m_nodeToDraw is used to draw only one element (and its descendants)
    RenderObject* eltRenderer = m_nodeToDraw ? m_nodeToDraw->renderer() : 0;
    RenderLayer* rootLayer = contentRenderer->layer();

    // 通过rootLayer来绘制内容
    rootLayer->paint(p, rect, m_paintBehavior, eltRenderer);

    // 通过root layer绘制scrollbars区域
    if (rootLayer->containsDirtyOverlayScrollbars())
        rootLayer->paintOverlayScrollbars(p, rect, m_paintBehavior, eltRenderer);
}

void FrameView::setNeedsOneShotDrawingSynchronization() {
    page->chrome()->client()->setNeedsOneShotDrawingSynchronization();
}


bool FrameView::useSlowRepaints() const
{
    if (m_useSlowRepaints || m_slowRepaintObjectCount > 0 || (platformWidget() && m_fixedObjectCount > 0) || m_isOverlapped || !m_contentIsOpaque)
        return true;

    if (FrameView* parentView = parentFrameView())
        return parentView->useSlowRepaints();

    return false;
}

// slow repaint和can blit是互反的关系
void FrameView::updateCanBlitOnScrollRecursively()
    for (Frame* frame = m_frame.get(); frame; frame = frame->tree()->traverseNext(m_frame.get())) {
        if (FrameView* view = frame->view())
            view->setCanBlitOnScroll(!view->useSlowRepaints());
    }


void FrameView::setUseSlowRepaints()
    m_useSlowRepaints = true;
    updateCanBlitOnScrollRecursively();

void FrameView::addSlowRepaintObject()
    if (!m_slowRepaintObjectCount)
        updateCanBlitOnScrollRecursively();
    m_slowRepaintObjectCount++;

void FrameView::addFixedObject()
    if (!m_fixedObjectCount && platformWidget())
        updateCanBlitOnScrollRecursively();
    ++m_fixedObjectCount;



// 如果没有positionedObjects, 调用hostWindow的scroll()方法，并返回
// 对于所有fixed postion的对象， 重新绘制该对象所在layer, 并进行剪裁
// 调用hostWindow的scroll()方法，scroll非position区域
// 更新fixed position对象的区域
bool FrameView::scrollContentsFastPath(const IntSize& scrollDelta, const IntRect& rectToScroll, const IntRect& clipRect)
{
    const size_t fixedObjectThreshold = 5;
    
    // 如果没有positionedObjects, 调用hostWindow的scroll()方法
    RenderBlock::PositionedObjectsListHashSet* positionedObjects = 0;
    positionedObjects = m_frame->contentRenderer()->positionedObjects();
    if (!positionedObjects || positionedObjects->isEmpty()) {
        hostWindow()->scroll(scrollDelta, rectToScroll, clipRect);
        return true;
    }

    // Get the rects of the fixed objects visible in the rectToScroll
    // 对于fixed postion的对象， 重新绘制该对象所在layer, 并进行剪裁
    Vector<IntRect, fixedObjectThreshold> subRectToUpdate;
    bool updateInvalidatedSubRect = true;
    RenderBlock::PositionedObjectsListHashSet::const_iterator end = positionedObjects->end();
    for (RenderBlock::PositionedObjectsListHashSet::const_iterator it = positionedObjects->begin(); it != end; ++it) {
        RenderBox* renderBox = *it;
        if (renderBox->style()->position() != FixedPosition)
            continue;
        IntRect updateRect = renderBox->layer()->repaintRectIncludingDescendants();
        updateRect = contentsToWindow(updateRect);
        if (clipsRepaints())
            updateRect.intersect(rectToScroll);
        if (!updateRect.isEmpty()) {
            if (subRectToUpdate.size() >= fixedObjectThreshold) {
                updateInvalidatedSubRect = false;
                break;
            }
            subRectToUpdate.append(updateRect);
        }
    }

    // Scroll the view
    if (updateInvalidatedSubRect) {
        // 1) scroll
        hostWindow()->scroll(scrollDelta, rectToScroll, clipRect);

        // 2) update the area of fixed objects that has been invalidated
        size_t fixObjectsCount = subRectToUpdate.size();
        for (size_t i = 0; i < fixObjectsCount; ++i) {
            IntRect updateRect = subRectToUpdate[i];
            IntRect scrolledRect = updateRect;
            scrolledRect.move(scrollDelta);
            updateRect.unite(scrolledRect);
            if (clipsRepaints())
                updateRect.intersect(rectToScroll);
            hostWindow()->invalidateContentsAndWindow(updateRect, false);
        }
        return true;
    }

    return false;
}

// 如果使用了加速compositing, 则调用owner render的repaintRectangle()方法
// 否则, 调用
void FrameView::scrollContentsSlowPath(const IntRect& updateRect)
{
#if USE(ACCELERATED_COMPOSITING)
    if (RenderPart* frameRenderer = m_frame->ownerRenderer()) {
        if (frameRenderer->containerForRepaint()) {
            IntRect rect(frameRenderer->borderLeft() + frameRenderer->paddingLeft(),
                    frameRenderer->borderTop() + frameRenderer->paddingTop(),
                    visibleWidth(), visibleHeight());
            frameRenderer->repaintRectangle(rect);
            return;
        }
    }
#endif
    ScrollView::scrollContentsSlowPath(updateRect);
        // hostWindow()->invalidateContentsForSlowScroll(updateRect, false);
}

// Note that this gets called at painting time.
void FrameView::setIsOverlapped(bool isOverlapped)
{
    m_isOverlapped = isOverlapped;
    updateCanBlitOnScrollRecursively();

#if USE(ACCELERATED_COMPOSITING)
    if (hasCompositedContentIncludingDescendants()) {
        // Overlap can affect compositing tests, so if it changes, we need to trigger
        // a layer update in the parent document.
        // 获取parent frame的compositor
        // 设置需要重新构造parent layer, 并schedule这次重绘
        if (Frame* parentFrame = m_frame->tree()->parent()) {
            if (RenderView* parentView = parentFrame->contentRenderer()) {
                RenderLayerCompositor* compositor = parentView->compositor();
                compositor->setCompositingLayersNeedRebuild();
                compositor->scheduleCompositingLayerUpdate();
            }
        }

        // 允许独立绘制frame, 则schedule所有子frame的重新绘制
        if (RenderLayerCompositor::allowsIndependentlyCompositedFrames(this)) {
            // We also need to trigger reevaluation for this and all descendant frames,
            // since a frame uses compositing if any ancestor is compositing.
            for (Frame* frame = m_frame.get(); frame; frame = frame->tree()->traverseNext(m_frame.get())) {
                if (RenderView* view = frame->contentRenderer()) {
                    RenderLayerCompositor* compositor = view->compositor();
                    compositor->setCompositingLayersNeedRebuild();
                    compositor->scheduleCompositingLayerUpdate();
                }
            }
        }
    }
#endif
}


void FrameView::repaintFixedElementsAfterScrolling()
{
    // For fixed position elements, update widget positions and compositing layers after scrolling,
    // but only if we're not inside of layout.
    if (!m_nestedLayoutCount && hasFixedObjects()) {
        if (RenderView* root = m_frame->contentRenderer()) {
            root->updateWidgetPositions();
            root->layer()->updateRepaintRectsAfterScroll();
#if USE(ACCELERATED_COMPOSITING)
            root->compositor()->updateCompositingLayers(CompositingUpdateOnScroll);
#endif
        }
    }
}




