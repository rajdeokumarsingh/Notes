
// Stores both region information and an SkPicture of the match, so that the
// region can be drawn, followed by drawing the matching text on top of it.
// This class owns its SkPicture
class MatchInfo {
private:
    MatchInfo& operator=(MatchInfo& src);
    SkRegion    m_location;
    SkPicture*  m_picture;
    int         m_layerId;

public:
    const SkRegion& getLocation() const { return m_location; }
    // Return a pointer to our picture, representing the matching text.  Does
    // not transfer ownership of the picture.
    SkPicture* getPicture() const { return m_picture; }
    // This will make a copy of the region, and increase the ref count on the
    // SkPicture.  If this MatchInfo already had one, unref it.
    void set(const SkRegion& region, SkPicture* pic, int layerId);
    bool isInLayer() const { return m_layerId >= 0; }
    int layerId() const { return m_layerId; }
};


class FindBounder : public SkBounder {
    public:
        FindBounder() {}
        ~FindBounder() {}
    protected:
        virtual bool onIRect(const SkIRect&) { return false; }
};

class FindCanvas : public SkCanvas {
public:
    FindCanvas(int width, int height, const UChar* , const UChar*,
            size_t byteLength);

    virtual void drawText(const void* text, size_t byteLength, SkScalar x,
                              SkScalar y, const SkPaint& paint);

     void drawLayers(LayerAndroid*);
      void setLayerId(int layerId);



