
FrameView {
    // TODO: composited content
    // TODO: void FrameView::repaintContentRectangle(const IntRect& r, bool immediate)
    // void FrameView::paintContents(GraphicsContext* p, const IntRect& rect)

    ./code/FrameView.structure.cpp 
    ./code/FrameView.layout.cpp 
    ./code/FrameView.paint.cpp 
    ./code/FrameView.scollbar.cpp 
    ./code/FrameView.compositing.cpp 
    ./code/FrameView.event.cpp 

    public ScrollView {
        ScrollView : public Widget, public ScrollableArea
        ./code/ScrollView.cpp
    }

    ---> {
        Scrollbar 2 [vertical, horizontal]
    }

    structural functions {
        static PassRefPtr<FrameView> create(Frame*);
        void clear();

        virtual HostWindow* hostWindow() const;
            // Chrome.cpp

        // Frame
        RefPtr<Frame> m_frame;
        void clearFrame();
        
        IntSize m_size;
        IntSize m_margins;


    }

    Scroll bar 
    {
        virtual PassRefPtr<Scrollbar> createScrollbar(ScrollbarOrientation);
        void updateCanHaveScrollbars();
        virtual void setCanHaveScrollbars(bool);
        void resetScrollbars();
        void resetScrollbarsAndClearContentsSize();
        void detachCustomScrollbars();

        void setScrollPosition(const IntPoint&);
        void scrollPositionChangedViaPlatformWidget();
        virtual void repaintFixedElementsAfterScrolling();

        // Functions for querying the current scrolled position, negating the effects of overhang
        // and adjusting for page scale.
        int scrollXForFixedPosition() const;
        int scrollYForFixedPosition() const;
        IntSize scrollOffsetForFixedPosition() const;

        void restoreScrollbar();

        bool wasScrolledByUser() const;
        void setWasScrolledByUser(bool);

        bool scrollToFragment(const KURL&);
        bool scrollToAnchor(const String&);
        void maintainScrollPositionAtAnchor(Node*);

        void calculateScrollbarModesForLayout(ScrollbarMode& hMode, ScrollbarMode& vMode);

        virtual bool shouldSuspendScrollAnimations() const;

        virtual bool scrollContentsFastPath(const IntSize& scrollDelta, const IntRect& rectToScroll, const IntRect& clipRect);
        virtual void scrollContentsSlowPath(const IntRect& updateRect);
    }

    layout functions {
        bool needsLayout() const;
        void setNeedsLayout();
        bool needsFullRepaint() const { return m_doFullRepaint; }

        void scheduleRelayout();
        void unscheduleRelayout();
        void scheduleRelayoutOfSubtree(RenderObject*);
 
        void layout(bool allowSubtree = true);
        bool didFirstLayout() const;
        void layoutTimerFired(Timer<FrameView>*);
        void postLayoutTimerFired(Timer<FrameView>*);
        bool layoutPending() const;
        bool isInLayout() const { return m_inLayout; }

        virtual void setContentsSize(const IntSize&);

        #if PLATFORM(ANDROID)
        void updatePositionedObjects();
        #endif
        void adjustViewSize();

        virtual IntRect windowClipRect(bool clipToContents = true) const;
        IntRect windowClipRectForLayer(const RenderLayer*, bool clipToLayerContents) const;

        virtual IntRect windowResizerRect() const;

        void addFixedObject();
        void removeFixedObject();

        void updateLayoutAndStyleIfNeededRecursive();
        void forceLayout(bool allowSubtree = false);
        void forceLayoutForPagination(const FloatSize& pageSize, 
                float maximumShrinkFactor, Frame::AdjustViewSizeOrNot);

        // Methods to convert points and rects between the coordinate space of the renderer, and this view.
        virtual IntRect convertFromRenderer(const RenderObject*, const IntRect&) const;
        virtual IntRect convertToRenderer(const RenderObject*, const IntRect&) const;
        virtual IntPoint convertFromRenderer(const RenderObject*, const IntPoint&) const;
        virtual IntPoint convertToRenderer(const RenderObject*, const IntPoint&) const;
    }

    paint functions {
        virtual void invalidateRect(const IntRect&);
        virtual void setFrameRect(const IntRect&);

        bool isTransparent() const;
        void setTransparent(bool isTransparent);

        Color baseBackgroundColor() const;
        void setBaseBackgroundColor(const Color&);
        void updateBackgroundRecursively(const Color&, bool);

        void setUseSlowRepaints();
        void setIsOverlapped(bool);
        bool isOverlapped() const { return m_isOverlapped; }
        bool isOverlappedIncludingAncestors() const;
        void setContentIsOpaque(bool);

        void addSlowRepaintObject();
        void removeSlowRepaintObject();

        void beginDeferredRepaints();
        void endDeferredRepaints();
        void checkStopDelayingDeferredRepaints();
        void resetDeferredRepaintDelay();

        virtual void paintContents(GraphicsContext*, const IntRect& damageRect);

        void setPaintBehavior(PaintBehavior);
        PaintBehavior paintBehavior() const;

        bool isPainting() const;
        bool hasEverPainted() const { return m_lastPaintTime; }

        void setNodeToDraw(Node*);

        virtual void paintOverhangAreas(GraphicsContext*, const IntRect& horizontalOverhangArea, 
                const IntRect& verticalOverhangArea, const IntRect& dirtyRect);

        static double currentPaintTimeStamp() { return sCurrentPaintTimeStamp; } // returns 0 if not painting

        void flushDeferredRepaints();

        // Normal delay
        static void setRepaintThrottlingDeferredRepaintDelay(double p);
        // Negative value would mean that first few repaints happen without a delay
        static void setRepaintThrottlingnInitialDeferredRepaintDelayDuringLoading(double p);
        // The delay grows on each repaint to this maximum value
        static void setRepaintThrottlingMaxDeferredRepaintDelayDuringLoading(double p);
        // On each repaint the delay increses by this amount
        static void setRepaintThrottlingDeferredRepaintDelayIncrementDuringLoading(double p);
    }

    Event
    {
        void scheduleEvent(PassRefPtr<Event>, PassRefPtr<Node>);
        void pauseScheduledEvents();
        void resumeScheduledEvents();

    }

    ACCELERATED_COMPOSITING
    {
        void updateCompositingLayers();
        bool syncCompositingStateForThisFrame();

        // Called when changes to the GraphicsLayer hierarchy have to be synchronized with
        // content rendered via the normal painting path.
        void setNeedsOneShotDrawingSynchronization();
    }

    media {
        String mediaType() const;
        void setMediaType(const String&);
        void adjustMediaTypeForPrinting(bool printing);
    }

    bool hasCompositedContent() const;
    bool hasCompositedContentIncludingDescendants() const;
    bool hasCompositingAncestor() const;
    void enterCompositingMode();
    bool isEnclosedInCompositingLayer() const;

    // Only used with accelerated compositing, but outside the #ifdef to make linkage easier.
    // Returns true if the sync was completed.
    bool syncCompositingStateIncludingSubframes();

    void addWidgetToUpdate(RenderEmbeddedObject*);
    void removeWidgetToUpdate(RenderEmbeddedObject*);

    virtual IntPoint currentMousePosition() const;
}
