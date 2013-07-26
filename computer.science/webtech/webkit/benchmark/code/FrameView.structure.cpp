FrameView::FrameView(Frame* frame) {
    init();

    m_page = m_frame->page();
    m_page->addScrollableArea(this);

    if (m_frame == m_page->mainFrame()) {
        ScrollableArea::setVerticalScrollElasticity(ScrollElasticityAllowed);
        ScrollableArea::setHorizontalScrollElasticity(ScrollElasticityAllowed);
    }
}

void FrameView::init()
    reset(); // 初始化内部变量

    m_margins = IntSize(-1, -1); 
    m_size = IntSize();

    // Propagate the marginwidth/height and scrolling modes to the view.
    // 如果当前的frame是frame或iframe，则设置scroll bar和margin参数
    Element* ownerElement = m_frame ? m_frame->ownerElement() : 0;
    if (ownerElement && (ownerElement->hasTagName(frameTag) || ownerElement->hasTagName(iframeTag))) {
        HTMLFrameElement* frameElt = static_cast<HTMLFrameElement*>(ownerElement);
        if (frameElt->scrollingMode() == ScrollbarAlwaysOff)
            setCanHaveScrollbars(false);

        setMarginWidth(frameElt->marginWidth());
        setMarginHeight(frameElt->marginHeight());
    }
}


// 重载ScrollView的方法
bool FrameView::isFrameView() const
    return true;

void FrameView::clearFrame()
    m_frame = 0;

// 如果是root frame, 调用通过host window来invalicate
// 否则通过frame view onwer的render来重新绘制
void FrameView::invalidateRect(const IntRect& rect)                                                    
{                                                                                                      
    // 如果没有parent, 调用通过host window来invalicate
    if (!parent() && hostWindow())                                                                              
            hostWindow()->invalidateContentsAndWindow(rect, false /*immediate*/);                      
        return;                                                                                        
    }                                                                                                  

    // 否则通过frame view onwer的render来重新绘制
    RenderPart* renderer = m_frame->ownerRenderer();

    IntRect repaintRect = rect;
    repaintRect.move(renderer->borderLeft() + renderer->paddingLeft(),
            renderer->borderTop() + renderer->paddingTop());
    renderer->repaintRectangle(repaintRect);
}

void FrameView::addWidgetToUpdate(RenderEmbeddedObject* object)                                        
    m_widgetUpdateSet->add(object);                                                                    

void FrameView::removeWidgetToUpdate(RenderEmbeddedObject* object)                                     
    m_widgetUpdateSet->remove(object);                                                                 

bool FrameView::isOverlappedIncludingAncestors() const
{
    if (isOverlapped()) return true;

    if (FrameView* parentView = parentFrameView()) {
        if (parentView->isOverlapped()) return true;
    }

    return false;
}

void FrameView::setContentIsOpaque(bool contentIsOpaque)
{
    m_contentIsOpaque = contentIsOpaque;
    updateCanBlitOnScrollRecursively();
}

// HostWindow is alias of Chrome.cpp
HostWindow* FrameView::hostWindow() const
{
    Page* page = frame() ? frame()->page() : 0;
    return page->chrome();
}

static bool isObjectAncestorContainerOf(RenderObject* ancestor, RenderObject* descendant)
{
    for (RenderObject* r = descendant; r; r = r->container()) {
        if (r == ancestor)
            return true;
    }
    return false;
}

bool FrameView::isActive() const
{
    Page* page = frame()->page();
    return page && page->focusController()->isActive();
}

bool FrameView::hasCustomScrollbars() const
{
    const HashSet<RefPtr<Widget> >* viewChildren = children();
    HashSet<RefPtr<Widget> >::const_iterator end = viewChildren->end();
    for (HashSet<RefPtr<Widget> >::const_iterator current = viewChildren->begin(); current != end; ++current) {
        Widget* widget = current->get();
        if (widget->isFrameView()) {
            if (static_cast<FrameView*>(widget)->hasCustomScrollbars())
                return true;
        } else if (widget->isScrollbar()) {
            Scrollbar* scrollbar = static_cast<Scrollbar*>(widget);
            if (scrollbar->isCustomScrollbar())
                return true;
        }
    }

    return false;
}

FrameView* FrameView::parentFrameView() const
{
    if (Widget* parentView = parent()) {
        if (parentView->isFrameView())
            return static_cast<FrameView*>(parentView);
    }
    return 0;
}

