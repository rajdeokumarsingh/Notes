Widget类型/结构相关接口
{
// The Widget class serves as a base class for three kinds of objects:
// (1) Scrollable areas (ScrollView)
// (2) Scrollbars (Scrollbar)
// (3) Plugins (PluginView)

    virtual bool isFrameView() const { return false; }
    virtual bool isPluginView() const { return false; }
    virtual bool isPluginViewBase() const { return false; }
    virtual bool isScrollbar() const { return false; }

    ScrollView* parent() const { return m_parent; }
    ScrollView* root() const;
    void removeFromParent();
    virtual void setParent(ScrollView* view);

    // 父子关系， 单链表结构
    ScrollView* m_parent;
        // Only ScrollViews can have children
        // plugins and scrollbars are always leaves 

    // Widget的root节点一把都是frame view, isFrameView()返回true
}

设置/获取几何信息
{
    int x() const { return frameRect().x(); }
    int y() const { return frameRect().y(); }
    int width() const { return frameRect().width(); }
    int height() const { return frameRect().height(); }
    IntSize size() const { return frameRect().size(); }
    IntPoint pos() const { return frameRect().location(); }

    virtual void setFrameRect(const IntRect&);
    virtual void setBoundsSize(const IntSize&);
    virtual IntRect frameRect() const;
    IntRect boundsRect() const { 
        return IntRect(0, 0, width(),  height()); }

    void resize(int w, int h) { 
        setFrameRect(IntRect(x(), y(), w, h)); 
        setBoundsSize(IntSize(w, h)); }

    void resize(const IntSize& s) { 
        setFrameRect(IntRect(pos(), s)); 
        setBoundsSize(s); }

    void move(int x, int y) { 
        setFrameRect(IntRect(x, y, width(), height())); }
    void move(const IntPoint& p) { 
        setFrameRect(IntRect(p, size())); }

    // 和containing window之间的坐标变换
    IntRect convertToContainingWindow(const IntRect&) const;
    IntRect convertFromContainingWindow(const IntRect&) const;
    IntPoint convertToContainingWindow(const IntPoint&) const;
    IntPoint convertFromContainingWindow(const IntPoint&) const;

    // convert points to/from the containing ScrollView
    // 和containing ScrollView之间的坐标变换
    virtual IntRect convertToContainingView(const IntRect&) const;
    virtual IntRect convertFromContainingView(const IntRect&) const;
    virtual IntPoint convertToContainingView(const IntPoint&) const;
    virtual IntPoint convertFromContainingView(const IntPoint&) const;


    一般回调用
        ScrollView::convertFromContainingWindow
        ScrollView::convertToContainingWindow
        ScrollView::convertChildToSelf
        ScrollView::convertSelfToChild

     PlatformWidget m_widget;
}

绘制相关接口:
{
    virtual void paint(GraphicsContext*, const IntRect&);
    void invalidate() { invalidateRect(boundsRect()); }
    virtual void invalidateRect(const IntRect&) = 0;

    virtual void setFocus(bool);
    void setCursor(const Cursor&);

    virtual void show();
    virtual void hide();


    bool isSelfVisible() const { return m_selfVisible; } // Whether or not we have been explicitly marked as visible or not.
    bool isParentVisible() const { return m_parentVisible; } // Whether or not our parent is visible.
    bool isVisible() const { return m_selfVisible && m_parentVisible; } // Whether or not we are actually visible.
    virtual void setParentVisible(bool visible) { m_parentVisible = visible; }
    void setSelfVisible(bool v) { m_selfVisible = v; }

    void setIsSelected(bool);


}

发送事件
{
    virtual void handleEvent(Event*) { }
        PluginView实现了这个接口

    virtual void notifyWidget(WidgetNotification) { }

    enum WidgetNotification { 
        WillPaintFlattened, DidPaintFlattened 
    };

    virtual void frameRectsChanged();

    // Notifies this widget that other widgets 
    // on the page have been repositioned.
    virtual void widgetPositionsUpdated() {}


    // A means to access the AX cache when this object can get a pointer to it.
    virtual AXObjectCache* axObjectCache() const { return 0; }

}



