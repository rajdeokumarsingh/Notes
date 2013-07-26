class WebCoreViewBridge : public WebCoreRefObject {
public:
    WebCoreViewBridge()
        m_bounds.setWidth(320);
        m_bounds.setHeight(240);
        m_visibleBounds = m_bounds;
        m_windowBounds = m_bounds;

    virtual void draw(WebCore::GraphicsContext* ctx, 
            const WebCore::IntRect& rect) = 0;

    void setSize(int w, int h)
        m_bounds.setWidth(w);
        m_bounds.setHeight(h);

    void setVisibleSize(int w, int h)
        m_visibleBounds.setWidth(w);
        m_visibleBounds.setHeight(h);

    void setLocation(int x, int y)
        m_bounds.setX(x);
        m_bounds.setY(y);
        m_visibleBounds.setX(x);
        m_visibleBounds.setY(y);

    void setWindowBounds(int x, int y, int h, int v)
        m_windowBounds = WebCore::IntRect(x, y, h, v);

    int width() const     { return m_bounds.width(); }
    int height() const    { return m_bounds.height(); }
    int locX() const      { return m_bounds.x(); }
    int locY() const      { return m_bounds.y(); }

    int visibleWidth() const    { return m_visibleBounds.width(); }
    int visibleHeight() const   { return m_visibleBounds.height(); }
    int visibleX() const        { return m_visibleBounds.x(); }
    int visibleY() const        { return m_visibleBounds.y(); }

    virtual bool forFrameView() const { return false; }
    virtual bool forPluginView() const { return false; }

    WebCore::IntRect    m_bounds;
    WebCore::IntRect    m_windowBounds;
    WebCore::IntRect    m_visibleBounds;
};


