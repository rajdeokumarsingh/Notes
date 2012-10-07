ScrollableArea 1 ---> {

    ScrollAnimator和Scrollbar的包装类
    实现滚动，获取滚动条的大小，位置， 更新滚动条
        提供滚动区域的几何参数等等接口

    ScrollAnimator 1 [create]
        基本上是计算scoll的x,y位置

    Scrollbar 2 [vertical, horizontal, just dependency]
        代表一个滚动条
            滚动条的内部逻辑表示, 按钮，滚动块，等等

        实现了paint方法绘制自己
        通过ScrollbarTheme来实现具体的绘制

    Scrollbar {
        : public Widget

        enum ScrollbarPart {
            BackButtonStartPart = 1,        // 上下/左右的按钮部分
            ForwardButtonStartPart = 1 << 1,
            BackButtonEndPart = 1 << 5,
            ForwardButtonEndPart = 1 << 6,

            BackTrackPart = 1 << 2,   // 按钮和滚动块之间的部分
            ForwardTrackPart = 1 << 4,

            ThumbPart = 1 << 3,  // 滚动块部分

            ScrollbarBGPart = 1 << 7,
            TrackBGPart = 1 << 8,
        }; 
    
        --> {
            ScrollableArea 1
            ScrollbarPart 2 [pressed part, hovered part]

            ScrollbarTheme 1 [通过theme获取scroll bar的样式]
                位置, 长度
        }

        virtual void paint(GraphicsContext*, const IntRect& damageRect) {
            if (!theme()->paint(this, context, damageRect))
                Widget::paint(context, damageRect);
        }
    }

    ScrollAnimator {
        // 保存了当前的scroll位置
        float m_currentPosX;
        float m_currentPosY;
        
        bool ScrollAnimator::scroll(ScrollbarOrientation orientation, ScrollGranularity, float step, float multiplier)
            计算滚动的位置，然后通知scroll area进行滚动
            m_scrollableArea->setScrollOffsetFromAnimation(IntPoint(m_currentPosX, m_currentPosY));

        void ScrollAnimator::handleWheelEvent(PlatformWheelEvent& e)
            通过滚轮事件计算需要滚动的距离，然后进行scroll
    }

    主要流程
    bool ScrollableArea::scroll(ScrollDirection direction, ScrollGranularity granularity, float multiplier) {
        通过ScrollDirection获取垂直或水平滚动条
        通过ScrollGranularity获取滚动的粒度，如一行，一屏

        调用下面函数计算滚动的距离
            m_scrollAnimator->scroll(orientation, granularity, step, multiplier);

        ScrollAnimator回调
        void ScrollableArea::setScrollOffsetFromAnimation(const IntPoint& offset)
            // Tell the derived class to scroll its contents. 
            // 通知子类滚动其内容, ScrollView, FrameView中实现
            setScrollOffset(offset); 

        verticalScrollbar->offsetDidChange(); {
            通过ScrollableArea获取当前滚动的位置
            更新滚动块的位置
                updateThumb() {
                    // 需要更新滚动块，以及滚动块和上下按钮之间的区域
                    theme()->invalidateParts(this, ForwardTrackPart | BackTrackPart | ThumbPart);

                    ScrollbarTheme::invalidateParts() {
                        void ScrollbarThemeComposite::invalidatePart(Scrollbar* scrollbar, ScrollbarPart part) {
                            // ...
                            IntRect beforeThumbRect, thumbRect, afterThumbRect;
                            splitTrack(scrollbar, trackRect(scrollbar), beforeThumbRect, thumbRect, afterThumbRect);
                            if (part == BackTrackPart)
                                result = beforeThumbRect;
                            else if (part == ForwardTrackPart)
                                result = afterThumbRect;
                            else
                                result = thumbRect;

                            // 平移变换
                            result.move(-scrollbar->x(), -scrollbar->y());
                            scrollbar->invalidateRect(result);
                            void Scrollbar::invalidateRect(const IntRect& rect) {
                                m_scrollableArea->invalidateScrollbar(this, rect);
                                void ScrollableArea::invalidateScrollbar(Scrollbar* scrollbar, const IntRect& rect) {
                                    invalidateScrollbarRect(scrollbar, rect);
                                    void FrameView::invalidateScrollbarRect(Scrollbar* scrollbar, const IntRect& rect)
                                    {
                                        // Add in our offset within the FrameView.
                                        IntRect dirtyRect = rect;
                                        dirtyRect.move(scrollbar->x(), scrollbar->y());
                                        invalidateRect(dirtyRect);
                                        void FrameView::invalidateRect(const IntRect& rect) {
                                            RenderPart* renderer = m_frame->ownerRenderer();
                                            IntRect repaintRect = rect;
                                            repaintRect.move(renderer->borderLeft() + renderer->paddingLeft(),
                                                    renderer->borderTop() + renderer->paddingTop());
                                            renderer->repaintRectangle(repaintRect);
                                        }
                                                |
                                                |
                                                V // TODO:
                                            void Scrollbar::paint(GraphicsContext* context, const IntRect& damageRect)
                                                if (!theme()->paint(this, context, damageRect))
                                                    Widget::paint(context, damageRect);
                                                        |
                                                        V
                                        bool ScrollbarThemeComposite::paint(Scrollbar* scrollbar, 
                                                GraphicsContext* graphicsContext, const IntRect& damageRect)
                                    }
                                }
                            }

                        }
                    }
                }
        }

        if (verticalScrollbar->isOverlayScrollbar())                                                   
            verticalScrollbar->invalidate();  
    }
}


