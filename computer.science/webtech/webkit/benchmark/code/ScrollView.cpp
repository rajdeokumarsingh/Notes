public ScrollView {
    // TODO:
    WebCore/platform/android/ScrollViewAndroid.cpp

    ScrollView : public Widget, public ScrollableArea

    ./code/ScrollableArea.cpp

    Widget --> {
        ./code/Widget.cpp
        在哪儿画，画多大, 画什么, 怎么画

        提供功能:
            维护一个父子Widget关系的单链表
            设置/获取几何信息, 如 x,y, width, height
            子widget和root widget之间的坐标变换

            绘制接口
                基本上都是由子类去实现

            类型判断, Widget的子类型有
                FrameView, PluginView, PluginViewBase, Scrollbar
    }

    主要功能
        维护Scroll View的父子view层次结构

        Scroll Bar的控制逻辑
            是否显示，什么时候显示，什么时候隐藏
            设置scroll bar的模式， show/hide/auto
            最大最小的scroll距离
            scroll bar的绘制

        计算scroll view的位置，尺寸，
            可视区域, content区域, fixed layout区域

        viewport, content, window之间的坐标变换

        滚动控制
            获取当前的滚动位移
            滚动到某一个位移 
                通过host window来更新显示内容
            更新滚动条

            scroll view的绘制
                通过FrameView来实现

    HostWindow

    // 拓扑结构
    {
        // ScrollView有一个HostWindow
        // The window thats hosts the ScrollView. The ScrollView will communicate scrolls and repaints to the
        // host window in the window's coordinate space.
        virtual HostWindow* hostWindow() const = 0;

        virtual void setParent(ScrollView*); // Overridden to update the overlapping scrollbar count.

        // ScrollView有一个子孙链表，其子孙类包括PluginView和ScrollBar
        // Functions for child manipulation and inspection.
        const HashSet<RefPtr<Widget> >* children() const { return &m_children; }
        void addChild(PassRefPtr<Widget>);
        void removeChild(Widget*);

        // Overridden by FrameView to create custom CSS scrollbars if applicable.
        virtual PassRefPtr<Scrollbar> createScrollbar(ScrollbarOrientation);

        RefPtr<Scrollbar> m_horizontalScrollbar;
        RefPtr<Scrollbar> m_verticalScrollbar;

        HashSet<RefPtr<Widget> > m_children;

    }

    // 几何相关方法
    {
        // FIXME: what is ?? m_scrollOrigin
            仅仅mac使用，可以忽略

        几个概念：
            0. FrameRect
                Frame的位置, 也就是scroll view在整个document系统中的位置

            1. contentsSize这个scroll area的整个内容，包括了visibleContentRect

            2. visibleContentRect 网页内容在viewport中的剪裁
                坐标系相对于整个document
                Xviewport + visibleContentRect.X = Xcontent
                Yviewport + visibleContentRect.Y = Ycontent

                visibleContentRect的顶点保存了scroll的偏移量
                visibleContentRect的大小是viewport的大小

                // 实现如下：
                IntRect ScrollView::visibleContentRect(bool includeScrollbars) const
                    return IntRect(IntPoint(m_scrollOffset.width(), m_scrollOffset.height()),
                            IntSize(max(0, m_boundsSize.width() - verticalScrollbarWidth),
                                max(0, m_boundsSize.height() - horizontalScrollbarHeight)));


            fixedLayoutSize CSS Fixed layout size // ???

        // Returns a clip rect in host window coordinates. Used to clip the blit on a scroll.
        virtual IntRect windowClipRect(bool clipToContents = true) const = 0;

        // Window坐标系和Content坐标系之间的变换
        // Event中的坐标是基于Window坐标系的
        // Event coordinates are assumed to be in the coordinate space of a window that contains
        // the entire widget hierarchy. It is up to the platform to decide what the precise definition
        // of containing window is. (For example on Mac it is the containing NSWindow.)
        IntPoint windowToContents(const IntPoint&) const;
        IntPoint contentsToWindow(const IntPoint&) const;

        // 屏幕坐标系和Content坐标系之间的变换
        // Functions for converting to and from screen coordinates.
        IntRect contentsToScreen(const IntRect&) const;
        IntPoint screenToContents(const IntPoint&) const;

        // self和child之间的坐标变换
        IntPoint convertChildToSelf(const Widget* child, const IntPoint& point) const
            IntPoint newPoint = point;
            newPoint.move(child->x(), child->y());
            return newPoint;

        IntPoint convertSelfToChild(const Widget* child, const IntPoint& point) const
            IntPoint newPoint = point;
            newPoint.move(-child->x(), -child->y());
            return newPoint;

        virtual IntRect convertFromScrollbarToContainingView(const Scrollbar*, const IntRect&) const;
        virtual IntRect convertFromContainingViewToScrollbar(const Scrollbar*, const IntRect&) const;

        // 如果看不见，就不要更新内容和动画
        // whether or not the scroll view is currently visible
        // Animations and painting updates can be suspended if
        //      either not in a window right now or if that window is not visible
        bool isOffscreen() const

        // visibleContentRect指的是被viewport剪裁的网页的区域
        // 其几何尺寸大小和viewport一致, top,left顶点是相对于网页的偏移
        // The visible content rect has a location that is the scrolled offset of the document. 
        // The width and height are the viewport width and height
        // By default the scrollbars themselves are excluded from this rectangle
        // But an optional boolean argument allows them to be included.
        IntRect visibleContentRect(bool includeScrollbars = false) const;

        // In the situation the client is responsible for the scrolling (ie. with a tiled backing store)
        // it is possible to use the actualVisibleContentRect instead, 
        // though this must be updated manually, e.g after panning ends.
        IntRect actualVisibleContentRect() const { 
            return m_actualVisibleContentRect.isEmpty() ? visibleContentRect() : m_actualVisibleContentRect; }


        // Functions for getting/setting the size webkit should use to layout the contents. 
        // By default this is the same as the visible content size. 
        // Explicitly setting a layout size value will cause webkit to layout the contents using this size instead.
        int layoutWidth() const;
        int layoutHeight() const;

        // CSS fixed layout size?
        IntSize fixedLayoutSize() const;
        void setFixedLayoutSize(const IntSize&);
        bool useFixedLayout() const;
        void setUseFixedLayout(bool enable);

        // Functions for getting/setting the size of the document contained inside the ScrollView (as an IntSize or as individual width and height
        // values).
        IntSize contentsSize() const; // Always at least as big as the visibleWidth()/visibleHeight().
        int contentsWidth() const { return contentsSize().width(); }
        int contentsHeight() const { return contentsSize().height(); }
        virtual void setContentsSize(const IntSize&);

    }

    // 和滚动相关方法
    {
        // 滚动模式
        //  AlwaysOff, AlwaysOn, and Auto
        void setScrollbarModes(ScrollbarMode horizontalMode, ScrollbarMode verticalMode, 
                bool horizontalLock = false, bool verticalLock = false);


        // Functions for scrolling the view.
        void setScrollPosition(const IntPoint&);
        void scrollBy(const IntSize& s) { return setScrollPosition(scrollPosition() + s); }

        // This function scrolls by lines, pages or pixels.
        bool scroll(ScrollDirection, ScrollGranularity);

        // This function scrolls by lines, pages or pixels.
        bool scroll(ScrollDirection, ScrollGranularity);

        // Scroll the actual contents of the view (either blitting or invalidating as needed).
        void scrollContents(const IntSize& scrollDelta);

        virtual int scrollSize(ScrollbarOrientation orientation) const;
        virtual int scrollPosition(Scrollbar*) const;
        virtual void setScrollOffset(const IntPoint&);

        virtual void scrollTo(const IntSize& newOffset);

        // FIXME: What is the lock?
        void setScrollingModesLock(bool lock = true) { 
            m_horizontalScrollbarLock = m_verticalScrollbarLock = lock; }

        // This gives us a means of blocking painting on our scrollbars until the first layout has occurred.
        void setScrollbarsSuppressed(bool suppressed, bool repaintOnUnsuppress = false);
        bool scrollbarsSuppressed() const { return m_scrollbarsSuppressed; }

    }

    // paint相关
    {
        // Widget override. Handles painting of the contents of the view as well as the scrollbars.
        virtual void paint(GraphicsContext*, const IntRect&);
        void paintScrollbars(GraphicsContext*, const IntRect&);

        virtual void repaintContentRectangle(const IntRect&, bool now = false);
        virtual void paintContents(GraphicsContext*, const IntRect& damageRect) = 0;

        // Widget overrides to ensure that our children's visibility status is kept up to date when we get shown and hidden.
        virtual void show();
        virtual void hide();
        virtual void setParentVisible(bool);

        virtual bool isScrollCornerVisible() const;
        void positionScrollbarLayers();

        // By default you only receive paint events for the area that is visible. In the case of using a
        // tiled backing store, this function can be set, so that the view paints the entire contents.
        bool paintsEntireContents() const { return m_paintsEntireContents; }
        void setPaintsEntireContents(bool);


        // By default, paint events are clipped to the visible area.  If set to
        // false, paint events are no longer clipped.  paintsEntireContents() implies !clipsRepaints().
        bool clipsRepaints() const { return m_clipsRepaints; }
        void setClipsRepaints(bool);

    }

    UI交互事件
    {
        void wheelEvent(PlatformWheelEvent&);
        void gestureEvent(const PlatformGestureEvent&);
    }

    virtual void didCompleteRubberBand(const IntSize&) const;
    virtual void notifyPageThatContentAreaWillPaint() const;

    // Called when our frame rect changes (or the rect/scroll position of an ancestor changes).
    virtual void frameRectsChanged();


    // Widget override to update our scrollbars and notify our contents of the resize.
    virtual void setFrameRect(const IntRect&);
    virtual void setBoundsSize(const IntSize&);

    void ScrollView::setHasHorizontalScrollbar(bool hasBar)
        if (hasBar && avoidScrollbarCreation()) return;                            

        if (hasBar && !m_horizontalScrollbar) {
            // 添加scroll bar
            m_horizontalScrollbar = createScrollbar(HorizontalScrollbar);   
            addChild(m_horizontalScrollbar.get());                     
            m_horizontalScrollbar->styleChanged();
        } else if (!hasBar && m_horizontalScrollbar) {
            // 移除scroll bar
            removeChild(m_horizontalScrollbar.get());
            m_horizontalScrollbar = 0;
        }

        if (AXObjectCache::accessibilityEnabled() && axObjectCache())
            axObjectCache()->handleScrollbarUpdate(this);

        // 向上移动的最大的scroll范围
        IntPoint ScrollView::minimumScrollPosition() const
            return IntPoint(-m_scrollOrigin.x(), -m_scrollOrigin.y());

        // 向下移动的最大的scroll范围
        // m_scrollOrigin是visibleContentRect的(left, top)顶点
        IntPoint ScrollView::maximumScrollPosition() const
            IntPoint maximumOffset(contentsWidth() - visibleWidth() - m_scrollOrigin.x(), 
                    contentsHeight() - visibleHeight() - m_scrollOrigin.y());

        // 滚动条被遮住部分的尺寸
        int ScrollView::scrollSize(ScrollbarOrientation orientation) const 
            (scrollbar->totalSize() - scrollbar->visibleSize())

        void ScrollView::setScrollOffset(const IntPoint& offset)
            int horizontalOffset = offset.x();
            int verticalOffset = offset.y();

            IntSize newOffset = m_scrollOffset;
            newOffset.setWidth(horizontalOffset - m_scrollOrigin.x());
            newOffset.setHeight(verticalOffset - m_scrollOrigin.y());

            scrollTo(newOffset);
                |
                V
                void ScrollView::scrollTo(const IntSize& newOffset)
                    IntSize scrollDelta = newOffset - m_scrollOffset;
                    m_scrollOffset = newOffset;

                    repaintFixedElementsAfterScrolling();
                    scrollContents(scrollDelta);


            int ScrollView::scrollPosition(Scrollbar* scrollbar) const
                if (scrollbar->orientation() == HorizontalScrollbar)
                    return scrollPosition().x() + m_scrollOrigin.x();
                if (scrollbar->orientation() == VerticalScrollbar)
                    return scrollPosition().y() + m_scrollOrigin.y();


    void ScrollView::scrollContents(const IntSize& scrollDelta)
        // 获取window的区域
        IntRect clipRect = windowClipRect();

        // 获取visible区域的window系坐标
        IntRect scrollViewRect = convertToContainingWindow(IntRect(0, 0, visibleWidth(), visibleHeight()));

        // 去掉scrollbar区域
        if (hasOverlayScrollbars()) {
            int verticalScrollbarWidth = verticalScrollbar() ? verticalScrollbar()->width() : 0;
            int horizontalScrollbarHeight = horizontalScrollbar() ? horizontalScrollbar()->height() : 0;

            scrollViewRect.setWidth(scrollViewRect.width() - verticalScrollbarWidth);
            scrollViewRect.setHeight(scrollViewRect.height() - horizontalScrollbarHeight);
        }

        IntRect updateRect = clipRect;
        updateRect.intersect(scrollViewRect);

        // Invalidate the window (not the backing store).
        hostWindow()->invalidateWindow(updateRect, false /*immediate*/);

        if (canBlitOnScroll()) { // The main frame can just blit the WebView window
            // FIXME: Find a way to scroll subframes with this faster path
            if (!scrollContentsFastPath(-scrollDelta, scrollViewRect, clipRect))
                scrollContentsSlowPath(updateRect);
        } else {
            // We need to go ahead and repaint the entire backing store.  Do it now before moving the
            // windowed plugins.
            scrollContentsSlowPath(updateRect);
        }

        // Invalidate the overhang areas if they are visible.

        // This call will move children with native widgets (plugins) and invalidate them as well.
        frameRectsChanged();

        // Now blit the backingstore into the window which should be very fast.
        hostWindow()->invalidateWindow(IntRect(), true);

    bool ScrollView::scrollContentsFastPath(const IntSize& scrollDelta, const IntRect& rectToScroll, const IntRect& clipRect)
        hostWindow()->scroll(scrollDelta, rectToScroll, clipRect);


    void ScrollView::paintScrollbars(GraphicsContext* context, const IntRect& rect)
        if (m_horizontalScrollbar
#if USE(ACCELERATED_COMPOSITING)
                && !layerForHorizontalScrollbar()
#endif
           )
            m_horizontalScrollbar->paint(context, rect);
        if (m_verticalScrollbar
#if USE(ACCELERATED_COMPOSITING)
                && !layerForVerticalScrollbar()
#endif
           )
            m_verticalScrollbar->paint(context, rect);

#if USE(ACCELERATED_COMPOSITING)
        if (layerForScrollCorner())
            return;
#endif
        paintScrollCorner(context, scrollCornerRect());


    void ScrollView::paint(GraphicsContext* context, const IntRect& rect)
        IntRect documentDirtyRect = rect;
        documentDirtyRect.intersect(frameRect());

        context->save();

        // 移动坐标系
        context->translate(x(), y());
        // 移动点
        documentDirtyRect.move(-x(), -y());

        if (!paintsEntireContents()) {
            context->translate(-scrollX(), -scrollY());
            documentDirtyRect.move(scrollX(), scrollY());

            context->clip(visibleContentRect());
        }

        paintContents(context, documentDirtyRect);

        context->restore();

        // 画出absolute和fixed layout区域
        IntRect horizontalOverhangRect;
        IntRect verticalOverhangRect;
        calculateOverhangAreasForPainting(horizontalOverhangRect, verticalOverhangRect);
        if (rect.intersects(horizontalOverhangRect) || rect.intersects(verticalOverhangRect))
            paintOverhangAreas(context, horizontalOverhangRect, verticalOverhangRect, rect);

        // Now paint the scrollbars.
        if (!m_scrollbarsSuppressed && (m_horizontalScrollbar || m_verticalScrollbar)) {
            context->save();
            IntRect scrollViewDirtyRect = rect;
            scrollViewDirtyRect.intersect(frameRect());
            context->translate(x(), y());
            scrollViewDirtyRect.move(-x(), -y());

            paintScrollbars(context, scrollViewDirtyRect);
            context->restore();
        }

    // TODO:
    void ScrollView::calculateOverhangAreasForPainting(IntRect& horizontalOverhangRect, IntRect& verticalOverhangRect)



}


