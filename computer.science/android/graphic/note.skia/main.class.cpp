SkScalar 
{
    标量， 定点数或浮点数
        either represented as an IEEE float, 
        or as a 16.16 fixed point integer
    一个点可用(SkScalar x, SkScalar y)来表示
}

SkPoint
{
     SkScalar    fX, fY;
     rotateCW 
        绕着原点旋转一个点

    scale(SkScalar scale, SkPoint* dst)
        缩放一个dst点
        既dst和this以及原点在一条直线上， dst和原点的距离是this和原点距离的scale倍

    setLength(SkScalar length)
        使this到原点的距离为length

    normalize()
        return this->setLength(fX, fY, SK_Scalar1);
        使this到原点的距离为1.0
}

SkSize
{
    template <typename T> struct SkTSize 
        T fWidth; T fHeight;   

    struct SkSize : public SkTSize<SkScalar> 
}

SkRect
{
    SkScalar    fLeft
    SkScalar    fTop
    SkScalar    fRight
    SkScalar    fBottom

    // 将rect水平方向压缩dx, 垂直方向压缩dy
    void inset(SkScalar dx, SkScalar dy)
        fLeft   += dx;        
        fRight  -= dx;
        fTop    += dy;   
        fBottom -= dy;

    // 获取两个矩形的重叠部分(交集)
    bool intersect(const SkRect& r);

    // 获取一个最小的矩形，这个矩形包括了两个输入矩形
    void join(const SkRect& r)

    // alias for join()
    void growToInclude(const SkRect& r)

    // 使得矩形的l,r,t,b满足条件
    void SkRect::sort()
        if (fLeft > fRight)
            SkTSwap<SkScalar>(fLeft, fRight);

        if (fTop > fBottom)
            SkTSwap<SkScalar>(fTop, fBottom);
}

SkPath
{
    The SkPath class encapsulates compound (multiple contour) geometric paths
    consisting of straight line segments, quadratic curves, and cubic curves.

    几何路径，包括直线，曲线(二次方，三次方)

    FillType // ??

    enum Convexity {       // 凹凸性
        kUnknown_Convexity,
        kConvex_Convexity, // 凸面
        kConcave_Convexity // 凹面
    };

    enum Direction {
        /** clockwise direction for adding closed contours */
        kCW_Direction,
        /** counter-clockwise direction for adding closed contours */
        kCCW_Direction
    };


    static Convexity ComputeConvexity(const SkPath&); // 计算凹凸性
    const SkRect& getBounds() const // 获取边界矩形
    void moveTo(SkScalar x, SkScalar y); // 绝对平移, 移动到(x,y)
    void rMoveTo(SkScalar dx, SkScalar dy); // 相对平移, 水平移动x, 垂直移动y 

    void lineTo(SkScalar x, SkScalar y); // 添加一条线到path中(从最后添加的点)
    void rLineTo(SkScalar dx, SkScalar dy); /* Same as lineTo, but the coordinates 
                    are considered relative to the last point on this contour. */

    // 画二次贝叶斯曲线
    void quadTo(SkScalar x1, SkScalar y1, SkScalar x2, SkScalar y2); /** Add a quadratic bezier from 
                            the last point, approaching control point (x1,y1), and ending at (x2,y2). */
    // 类似quadTo, 不过参数坐标是相对于最后一个点的
    void rQuadTo(SkScalar dx1, SkScalar dy1, SkScalar dx2, SkScalar dy2); 

    // 三次贝叶斯曲线, (x1, y1), (x2, y2)是控制点， (x3, y3)是终点
    void cubicTo(SkScalar x1, SkScalar y1, SkScalar x2, SkScalar y2, SkScalar x3, SkScalar y3);

    // 类似cubicTo, 不过参数坐标是相对于最后一个点的
    void rCubicTo(SkScalar x1, SkScalar y1, SkScalar x2, SkScalar y2, SkScalar x3, SkScalar y3);

    /* 画弧线
       @param oval The bounding oval defining the shape and size of the arc
       @param startAngle Starting angle (in degrees) where the arc begins
       @param sweepAngle Sweep angle (in degrees) measured clockwise. This is treated mod 360.
    */ 
    void arcTo(const SkRect& oval, SkScalar startAngle, 
            SkScalar sweepAngle, bool forceMoveTo);

    /** Close the current contour. If the current point is not equal to the
      first point of the contour, a line segment is automatically added.
     */
    void close();

    /** Add a closed rectangle contour to the path */
    void addRect(const SkRect& rect, Direction dir = kCW_Direction);

    /** Add a closed oval contour to the path */
    void addOval(const SkRect& oval, Direction dir = kCW_Direction);

    /** Add a closed circle contour to the path */
    void addCircle(SkScalar x, SkScalar y, SkScalar radius,
                       Direction dir = kCW_Direction);

    /** Add a copy of src to the path, offset by (dx,dy)*/
    void addPath(const SkPath& src, SkScalar dx, SkScalar dy);

    // 平移
    void offset(SkScalar dx, SkScalar dy, SkPath* dst) const;

    // 旋转?
    /** Transform the points in this path by matrix, and write the answer into
      dst.
      @param matrix   The matrix to apply to the path
      @param dst      The transformed path is written here
     */
    void transform(const SkMatrix& matrix, SkPath* dst) const;

    // 遍历path时， 用于确定返回边的类型
    enum Verb {
        kMove_Verb,     //!< iter.next returns 1 point
        kLine_Verb,     //!< iter.next returns 2 points
        kQuad_Verb,     //!< iter.next returns 3 points
        kCubic_Verb,    //!< iter.next returns 4 points
        kClose_Verb,    //!< iter.next returns 1 point (the last point)
        kDone_Verb      //!< iter.next returns 0 points
    };
    class SK_API Iter {
        /** Return the next verb in this iteration of the path. When all
          segments have been visited, return kDone_Verb.

          @param  pts The points representing the current verb and/or segment
          @return The verb for the current segment
         */
        Verb next(SkPoint pts[4]);

private:
    SkTDArray<SkPoint>  fPts;
    SkTDArray<uint8_t>  fVerbs;
    mutable SkRect      fBounds;
    mutable uint8_t     fBoundsIsDirty;
    uint8_t             fFillType;
    mutable uint8_t     fConvexity;
}

SkRegion
{
    The SkRegion class encapsulates the geometric region used to specify clipping areas for drawing.
    用于保存剪裁的区域
    
    由一个或多个矩形组成
        往简单想，region就是若干个位置随机分布的矩形
        这些矩形的区域可能重叠，可能不重叠

    SkRegion::Iterator
        Returns the sequence of rectangles, sorted in Y and X, that make up this region. 

        输入一个region, 输出构成该region的矩形
        Iterator (const SkRegion &)
            bool    rewind ()
            void    reset (const SkRegion &)
            bool    done () const
            void    next ()
            const SkIRect &     rect () const
            const SkRegion *    rgn () const 

    SkRegion::Cliperator
        Returns the sequence of rectangles, sorted in Y and X, that make up this region intersected with the specified clip rectangle. 

        输入一个SkRegion和SkIRect, 可得到该region和rect的交集rect序列
        Cliperator (const SkRegion &, const SkIRect &clip)
            bool    done ()
            void    next ()
            const SkIRect &     rect () const 


    SkRegion::Spanerator
        Returns the sequence of runs that make up this region for the specified Y scanline, 
        clipped to the specified left and right X values. 

        Spanerator (const SkRegion &, int y, int left, int right)
            bool    next (int *left, int *right)
            Private Attributes
                const SkRegion::RunType *   fRuns

    const SkIRect& getBounds() const { return fBounds; }
        bool getBoundaryPath(SkPath* path) const;

    // 设置区域为一个矩形
    bool setRect(const SkIRect&);

    // Set this region to the union of an array of rects. 
    bool setRects(const SkIRect rects[], int count);

    // set this region to the area described by the path, clipped.
    bool setPath(const SkPath&, const SkRegion& clip);

    bool intersects(const SkRegion&) const;
    bool contains(const SkRegion&) const;
        
    //  The logical operations that can be performed when combining two regions.
    enum Op {
        kDifference_Op, //!< subtract the op region from the first region
        kIntersect_Op,  //!< intersect the two regions (交集)
        kUnion_Op,      //!< union (inclusive-or) the two regions （并集）
        kXOR_Op,        //!< exclusive-or the two regions
        /** subtract the first region from the op region */
        kReverseDifference_Op,
        kReplace_Op     //!< replace the dst region with the op region
    };

    /**
     *  Set this region to the result of applying the Op to this region and the
     *  specified rectangle: this = (this op rect).
     *  Return true if the resulting region is non-empty.
     */
    bool op(const SkIRect& rect, Op op) { return this->op(*this, rect, op); }
}

/** 8-bit type for an alpha value. 0xFF is 100% opaque, 0x00 is 100% transparent.*/
typedef uint8_t SkAlpha;  

/** 32 bit ARGB color value, not premultiplied. The color components are always in
    a known order. This is different from SkPMColor, which has its bytes in a configuration
    dependent order, to match the format of kARGB32 bitmaps. SkColor is the type used to   
    specify colors in SkPaint and in gradients.
*/
typedef uint32_t SkColor; 
    /** Return a SkColor value from 8 bit component values
    */
    static inline SkColor SkColorSetARGBInline(U8CPU a, U8CPU r, U8CPU g, U8CPU b)
    {
        SkASSERT(a <= 255 && r <= 255 && g <= 255 && b <= 255);
        return (a << 24) | (r << 16) | (g << 8) | (b << 0); 
    }

    http://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Triangulo_HSV.png/220px-Triangulo_HSV.png

    static inline void SkColorToHSV(SkColor color, SkScalar hsv[3])
    SK_API void SkRGBToHSV(U8CPU red, U8CPU green, U8CPU blue, SkScalar hsv[3]);

    SK_API SkColor SkHSVToColor(U8CPU alpha, const SkScalar hsv[3]);

    /** 32 bit ARGB color value, premultiplied. The byte order for this value is
        configuration dependent, matching the format of kARGB32 bitmaps. This is different
        from SkColor, which is nonpremultiplied, and is always in the same byte order.
    */
    typedef uint32_t SkPMColor;


SkMatrix
    Holds a 3x3 matrix for transforming coordinates 

SkMask
    SkMask is used to describe alpha bitmaps, either 1bit, 8bit, or the 3-channel 3D format 
    These are passed to SkMaskFilter objects.


SkPicture
    Records the drawing commands made to a canvas, to be played back at a later time 

SkBitmap
    ./webkit.skia.txt

SkCanvas
    ./webkit.skia.txt

SkPaint
    ./webkit.skia.txt

SkShape
    SkCanvas画图的简单封装

SkOSWindow
    SkOSWindow : public SkWindow

SkWindow {
    : public SkView 

    // 处理UI事件
    {
        bool    handleClick(int x, int y, Click::State, void* owner = NULL);
        bool    handleChar(SkUnichar);
        bool    handleKey(SkKey);
        bool    handleKeyUp(SkKey);

        void resize(int width, int height, 
            SkBitmap::Config config = SkBitmap::kNo_Config);
    }

    // 窗口附件, menu, title
    {
        void    addMenu(SkOSMenu*);
        const SkTDArray<SkOSMenu*>* getMenus() { return &fMenus; }

        void    setTitle(const char title[]);
    }

    // 窗口绘制相关
    {
        const SkBitmap& getBitmap() const { return fBitmap; }
        void    setConfig(SkBitmap::Config);

        bool    isDirty() const { return !fDirtyRgn.isEmpty(); }
        bool    update(SkIRect* updateArea, SkCanvas* = NULL);
        // does not call through to onHandleInval(), but does force the fDirtyRgn
        // to be wide open. Call before update() to ensure we redraw everything.
        void    forceInvalAll();

        // TODO: Matrix的作用是?
        void    setMatrix(const SkMatrix&);
    }
}
