samplecode/
    SampleApp.cpp {

        // 旋转
        {
            // cx, cy是旋转的原点
            SkScalar cx = this->width() / 2;   // canvas width/2
            SkScalar cy = this->height() / 2;  // canvas width/2
            canvas->translate(cx, cy);

            // 绕着canvas的几何中心点, 顺时针旋转 
            // 如果没有进行translate, 则旋转的原点是canvas的默认clip的(left, top)
            canvas->rotate(SkIntToScalar(45));

            canvas->translate(-cx, -cy);
        }

        // 缩放
        {
            SkScalar scale = SK_Scalar1 * 7 / 10;

            // (cx, cy)是缩放的原点
            // 如果没有进行translate, 则缩放的原点是canvas的默认clip的(left, top)
            SkScalar cx = this->width() / 2;
            SkScalar cy = this->height() / 2;
            canvas->translate(cx, cy);
            canvas->scale(scale, scale);
            canvas->translate(-cx, -cy);
        }

        // perspect
        {
            SkMatrix m; 
            m.reset();
            m.setPerspY(t); // 沿着y轴旋转
            canvas->concat(m);
        }
    }


xfermodes.cpp
{
    // 在位图上进行绘制
    static void make_bitmaps(int w, int h, SkBitmap* src, SkBitmap* dst) {
        // 绘制圆形
        {
            // 初始化位图
            src->setConfig(SkBitmap::kARGB_8888_Config, w, h);
            src->allocPixels();
            src->eraseColor(0);

            // 将位图作为canvas的绘制设备
            SkCanvas c(*src);         
            SkPaint p;
            SkRect r;
            SkScalar ww = SkIntToScalar(w);
            SkScalar hh = SkIntToScalar(h);

            // 画圆
            p.setAntiAlias(true);
            p.setColor(0xFFFF0000);
            r.set(0, 0, ww*3/4, hh*3/4);   
            c.drawOval(r, p);
        }

        // 绘制矩形
        {
            // 初始化位图
            dst->setConfig(SkBitmap::kARGB_8888_Config, w, h);
            dst->allocPixels();
            dst->eraseColor(0);

            // 将位图作为canvas的绘制设备
            c.setBitmapDevice(*dst);

            // p.setColor(0xFF66AAFF);
            p.setColor(0xFF00FF00);
            r.set(ww/3, hh/3, ww*19/20, hh*19/20);
            c.drawRect(r, p);
        }
    }

    // 在canvas上绘制位图
    void draw_mode(SkCanvas* canvas, SkXfermode* mode, int alpha,
            SkScalar x, SkScalar y) {
        SkPaint p;

        // 对于xfermode，先画的叫dst, 后画的叫src
        canvas->drawBitmap(fDstB, x, y, &p);
        p.setAlpha(alpha);
        p.setXfermode(mode);
        canvas->drawBitmap(fSrcB, x, y, &p);
    }

    // 创建一个白蓝格子着色器
    {
        // 创建一个2*2的位图，其pixel是{白, 蓝, 蓝，白}
        uint16_t localData[] = { 0xFFFF, 0x00FF, 0x00FF, 0xFFFF };  // 注意pixel的格式是(r g b a; 3 2 1 0)
        SkBitmap scratchBitmap;
        scratchBitmap.setConfig(SkBitmap::kARGB_4444_Config, 2, 2, 4);
        scratchBitmap.setPixels(localData);
        scratchBitmap.setIsOpaque(true);
        scratchBitmap.copyTo(&fBG, SkBitmap::kARGB_4444_Config);

        // 创建着色器
        SkShader* s = SkShader::CreateBitmapShader(fBG,
                SkShader::kRepeat_TileMode,
                SkShader::kRepeat_TileMode);
        // 将蓝白格子放大6倍
        SkMatrix m;
        m.setScale(SkIntToScalar(6), SkIntToScalar(6));
        s->setLocalMatrix(m);
    }

    // 为什么要save和restore?
    {
        SkPaint p;
        p.setStyle(SkPaint::kFill_Style);
        p.setShader(s);
        canvas->drawRect(r, p); // 画格子背景

        // 保存背景
        canvas->saveLayer(&r, NULL, SkCanvas::kARGB_ClipLayer_SaveFlag);

        // 画前景，只希望两个图形之间有xfermode
        draw_mode(canvas, mode, twice ? 0x88 : 0xFF, r.fLeft, r.fTop);  
        {
            SkPaint p;

            // 对于xfermode，先画的叫dst, 后画的叫src
            canvas->drawBitmap(fDstB, x, y, &p);
            p.setAlpha(alpha);
            p.setXfermode(mode);
            canvas->drawBitmap(fSrcB, x, y, &p);
        }

        // 恢复背景, 将前景覆盖到背景上
        canvas->restore();
    }

}

// gm/twopointradial.cpp
{
    translate的累加性质
    {
        for(int i=0;i<4;i++) {
                                                    // i  0              1           2     ...
            drawGrad(canvas, &data[0], &data[3]);   // 画图
            canvas->translate(DX, 0);               // x 平移到DX,  平移到2*DX, 平移到3*DX, ..., 注意参数y是0, y轴不会平移
            drawGrad(canvas, &data[3], &data[0]);   // 画图
            canvas->translate(0, DY);               // y 平移到DY,  平移到2*DY, 平移到3*DY, ..., 注意参数x是0, x轴不会平移
        }
        // 注意rotate也具有累加性质
    }

    通过save/restore/restoreToCount消除累加性
    {
        {
            int n = canvas->save();             // 保存当前位置
            drawGrad(canvas, &data[0], &data[3]);
            canvas->translate(DX, 0);           // x 平移到DX
            drawGrad(canvas, &data[3], &data[0]);
            canvas->restoreToCount(n);          // 恢复x到 0
        }

        canvas->translate(0, DY);               // y 平移到DY, 2*DY, 3*DY
    }
}

// 设置观察的视角
{
    SkMatrix perspective;
    perspective.setIdentity();
    // 将图形沿着x轴向屏幕内旋转
    perspective.setPerspY(SkScalarDiv(SK_Scalar1, SkIntToScalar(1000)));
    // 将图形沿着水品方向拉伸，就是将一个正方形拉伸成一个菱形
    // 将顶点拉伸的距离是length*8/25
    perspective.setSkewX(SkScalarDiv(SkIntToScalar(8), SkIntToScalar(25)));    
    canvas->setMatrix(perspective);        
}

{
    // 创建一个index位图
    {
        SkBitmap bm;

        // 创建颜色表
        {
            SkColorTable* ctable = new SkColorTable(1);
            SkPMColor* c = ctable->lockColors();
            c[0] = SkPackARGB32(0x80, 0x80, 0, 0);
            ctable->unlockColors(true);
        }

        // 创建位图，参数是颜色表
        {
            bm.setConfig(SkBitmap::kIndex8_Config, 1, 1);
            bm.allocPixels(ctable);
            ctable->unref();

            // 为位图赋值, index=0, 也就是颜色表中唯一的值
            bm.lockPixels();
            *bm.getAddr8(0, 0) = 0;
            bm.unlockPixels();
        }

    }

    SkShader* s =
        SkShader::CreateBitmapShader(fBM, SkShader::kRepeat_TileMode,
                SkShader::kMirror_TileMode);
    SkPaint paint;
    paint.setAlpha(0x80);
    paint.setShader(s)->unref();
    canvas->drawPaint(paint);
}


// 使用unit mapper
{
    SkUnitMapper * um = new SkCosineMapper; // 使用cosine mapper后, 颜色的顺序变成blue, red, green
    SkUnitMapper * um = new SkDiscreteMapper(12);      // 颜色变成一条条的, 将区域分成12等分
    SkAutoUnref au(um);                                                       

    SkPoint p1 = {bounds.left(), bounds.top()};                               
    SkPoint p2 = {bounds.right(), bounds.top()};                              
    // SkPoint p2 = {bounds.right(), bounds.bottom()};                        
    SkPoint pts[] = {p1, p2};
    SkColor colors1[] = { SK_ColorGREEN, SK_ColorRED, SK_ColorBLUE };         
    /*paint.setShader(SkGradientShader::CreateLinear(pts, colors1, NULL,      
      3, SkShader::kClamp_TileMode), um)->unref();*/                

    // 使用cosine mapper后, 颜色的顺序变成blue, red, green
    paint.setShader(SkGradientShader::CreateLinear(pts, colors1, NULL,
                3, SkShader::kClamp_TileMode, um))->unref();
}

// 创建colorfilter
{
    static void make_table0(uint8_t table[]) {
        /* for (int i = 0; i < 256; ++i) {
           int n = i >> 5;
           table[i] = (n << 5) | (n << 2) | (n >> 1);
           }*/

        for (int i = 0; i < 256; ++i) {
            table[i] = (i >> 5) << 5;
        }
    }

    static SkColorFilter* make_cf0() {
        uint8_t table[256]; 
        make_table0(table);
        return SkTableColorFilter::Create(table);
    }

    SkPaint paint;
    paint.setColorFilter(make_cf0())->unref();
    canvas->drawBitmap(bm, x, y, &paint);
}

// 进行剪裁
{
    {
        SkPaint paint;
        paint.setStyle(SkPaint::kStroke_Style);
        paint.setStrokeWidth(SkIntToScalar(9)/2);

        for (int y = 0; y < 2; y++) {
            paint.setAntiAlias(!!y);
            // 自动save/restore
            SkAutoCanvasRestore acr(canvas, true);

            // 将画面的中心部分剪裁下来
            canvas->clipRect(SkRect::MakeLTRB(SW/4, SH/4, SW/4*3,SH/4*3));

            // 随机的画一些圆角矩形, 椭圆
            SkRandom rand;
            for (int i = 0; i < N; i++) {
                SkRect r;
                rnd_rect(&r, &paint, rand);
                canvas->drawOval(r, paint);
                rnd_rect(&r, &paint, rand);
                canvas->drawRoundRect(r, r.width()/4, r.height()/4, paint);
            }
        }
    }
}

{
    static void rotate(SkScalar angle, SkScalar px, SkScalar py, SkCanvas* canvas) {
        // 沿着(px,py)顺时针旋转角度angle
        SkMatrix matrix;                      
        matrix.setRotate(angle, px, py);      
        canvas->concat(matrix);
    }   

    SkRandom rand;
    for (int i = 0; i < N/2; i++) {
        paint.setColor(rand.nextU());
        paint.setAlpha(0xFF);

        rotate(SkIntToScalar(15), SW/2, SH/2, canvas);
        canvas->drawPath(fPath, paint);
    }
}


// 什么是kStrokeAndFill_Style
{
    paint.setStyle(SkPaint::kStrokeAndFill_Style);

    SkPath path;
    path.setFillType(SkPath::kWinding_FillType);

    // 画实心圆
    path.addCircle(x, y + SkIntToScalar(200), SkIntToScalar(50), SkPath::kCW_Direction);
    canvas->drawPath(path, paint);

    // 画实心圆
    path.reset();
    path.addCircle(x, y + SkIntToScalar(200), SkIntToScalar(50), SkPath::kCW_Direction);
    path.addCircle(x, y + SkIntToScalar(200), SkIntToScalar(40), SkPath::kCW_Direction);
    canvas->drawPath(path, paint);

    // 画空心圆
    path.reset();
    path.addCircle(x, y + SkIntToScalar(200), SkIntToScalar(50), SkPath::kCW_Direction);
    path.addCircle(x, y + SkIntToScalar(200), SkIntToScalar(40), SkPath::kCCW_Direction);
    canvas->drawPath(path, paint);

}

{
    // postTranslate和setTranstlate的区别
    // postTranslate 不会影响前面的rotate
    // setTranstlate会取消前面的rotate
    m.setRotate(SkIntToScalar(30), SkIntToScalar(50), SkIntToScalar(50));                                                         
    m.postTranslate(0, SkIntToScalar(120));                    
    fGroup.appendShape(make_shape0(true), m)->unref();         

    // 将图形沿着y轴翻转180度，镜像
    matrix.setScale(-SK_Scalar1, SK_Scalar1);
    matrix.postTranslate(SkIntToScalar(220), SkIntToScalar(240));
    gs->appendShape(&fGroup, matrix);

    // cv->translate(SkIntToScalar(680), SkIntToScalar(480));
    cv->translate(SkIntToScalar(780), SkIntToScalar(480));
    cv->scale(-SK_Scalar1, SK_Scalar1);
    gs->draw(cv);

}

// 绘制阴影
{
    shadowLoopers[3] =
        new SkBlurDrawLooper (SkIntToScalar(5), SkIntToScalar(5),
                SkIntToScalar(10), 0x7FFF0000,
                SkBlurDrawLooper::kIgnoreTransform_BlurFlag |
                SkBlurDrawLooper::kOverrideColor_BlurFlag |
                SkBlurDrawLooper::kHighQuality_BlurFlag  );
    SkAutoUnref aurL3(shadowLoopers[3]);

    SkPaint paint;
    paint.setAntiAlias(true);

    SkAutoCanvasRestore acr(canvas, true);
    paint.setLooper(shadowLoopers[3]);
    paint->setColor(c);
    aint->setStyle(SkPaint::kFill_Style);

    canvas->translate(SkIntToScalar((unsigned int)i*40), SkIntToScalar(0));
    canvas->drawRect(fRect, paint);
}


// 为text着色
{
    paint.setShader(shaders[s])->unref();
    canvas->drawText(text, textLen, 0, textBase, paint);

    SkPath path;
    path.arcTo(SkRect::MakeXYWH(SkIntToScalar(-40), SkIntToScalar(15),
            SkIntToScalar(300), SkIntToScalar(90)),
            SkIntToScalar(225), SkIntToScalar(90), false);
    path.close();
    canvas->drawTextOnPath(text, textLen, path, NULL, paint);
}

{
    enum Cap {
        kButt_Cap,      //!< begin/end contours with no extension
        kRound_Cap,     //!< begin/end contours with a semi-circle extension
                        // 线段的端点是半圆的
        kSquare_Cap,    //!< begin/end contours with a half square extension
                        // 线段的端点是方形的

        kCapCount,
        kDefault_Cap = kButt_Cap
    };

}

// 画多个点的模式
{
    SkPoint* pts = new SkPoint[n];
    fill_pts(pts, n, &rand); // 随机的生成点

    canvas->drawPoints(SkCanvas::kPolygon_PointMode, n, pts, p0);  // 将所有的点连接起来
    canvas->drawPoints(SkCanvas::kLines_PointMode, n, pts, p1);    // 仅仅将偶数点和奇数点连接起来 (0,1), (2,3)
                                                                   // 看到的是一条条分散的线段
    canvas->drawPoints(SkCanvas::kPoints_PointMode, n, pts, p2);   // 画点，不连线
}

// 画text path
{
    SkPaint paint;                                             
    paint.setTextSize(SkIntToScalar(100));                     
    SkTypeface* hira = SkTypeface::CreateFromName(
            "Hiragino Maru Gothic Pro", SkTypeface::kNormal);
    SkSafeUnref(paint.setTypeface(hira));                      
    path.reset();
    paint.getTextPath("e", 1, 50, 50, &path);                  
    canvas->translate(0, 100);
    test_rev(canvas, path);
}

// path的不同效果
{
    // 使path转角变成圆弧形
    {
        SkPathEffect* corner = new SkCornerPathEffect(25);
        paint->setPathEffect(compose)->unref();
    }

    // 合并两种path effect
    {
        SkPathEffect* pe = paint->getPathEffect();
        SkPathEffect* corner = new SkCornerPathEffect(25);                        
        SkPathEffect* compose = new SkComposePathEffect(pe, corner);                        
        corner->unref();
        paint->setPathEffect(compose)->unref(); 
    }

    // 虚线效果
    {
        SkScalar inter[] = { 20, 20}; // 黑20, 白20
        // SkScalar inter[] = { 20, 10, 10, 10 };
        paint->setPathEffect(new SkDashPathEffect(
                    inter, SK_ARRAY_COUNT(inter), 0))->unref();      
    }

    // 类似狗啃过的效果
    {
        paint->setPathEffect(new SkDiscretePathEffect(10, 4))->unref();
    }
}

// 绘制位图中的一部分
{
    // 设置模糊效果
    paint.setFilterBitmap(true);

    // 创建一个4X4的位图，并为其填充颜色
    {
        SkBitmap sprite;
        sprite.setConfig(SkBitmap::kARGB_8888_Config, 4, 4, 4*sizeof(SkColor));
        const SkColor spriteData[16] = {
            SK_ColorBLACK,  SK_ColorCYAN,    SK_ColorMAGENTA, SK_ColorYELLOW,
            SK_ColorBLACK,  SK_ColorWHITE,   SK_ColorBLACK,   SK_ColorRED,
            SK_ColorGREEN,  SK_ColorBLACK,   SK_ColorWHITE,   SK_ColorBLUE,
            SK_ColorYELLOW, SK_ColorMAGENTA, SK_ColorCYAN,    SK_ColorBLACK
        };
        sprite.allocPixels();
        sprite.lockPixels();
        SkPMColor* addr = sprite.getAddr32(0, 0);
        for (size_t i = 0; i < SK_ARRAY_COUNT(spriteData); ++i) {
            addr[i] = SkPreMultiplyColor(spriteData[i]);
        }
        sprite.unlockPixels();
    }

    // 将4X4位图放大到100X100, 并绘制
    {
        srcRect.setXYWH(0, 0, 4, 4);
        dstRect.setXYWH(SkIntToScalar(0), SkIntToScalar(0)
                , SkIntToScalar(100), SkIntToScalar(100));
        canvas->drawBitmapRect(sprite, &srcRect, dstRect, &paint);
    }

    // 将4X4位图中的一个2X2[(1,1),(2,2)]子图放大到100X100, 并绘制
    {
        srcRect.setXYWH(1, 1, 2, 2);
        dstRect.setXYWH(SkIntToScalar(25), SkIntToScalar(125)
                , SkIntToScalar(50), SkIntToScalar(50));
        canvas->drawBitmapRect(sprite, &srcRect, dstRect, &paint);
    }
}

// 重影/压缩效果
{
    struct {
        int fWidth, fHeight;
        int fRadiusX, fRadiusY;
    } samples[] = {
        { 140, 140,   0,   0 },
        { 140, 140,   0,   2 },
        { 140, 140,   2,   0 },
        { 140, 140,   2,   2 },
        {  24,  24,  25,  25 },
    };

    // 拉伸效果
    paint.setImageFilter(new SkDilateImageFilter(    // dilate 扩大, 膨胀
                samples[i].fRadiusX,    // 沿着x轴拉伸的重叠
                samples[i].fRadiusY))->unref();  // 沿着y轴拉伸的位移

    // 压缩效果
    paint.setImageFilter(new SkErodeImageFilter(   // erode 腐蚀
                samples[i].fRadiusX,   // 沿着x轴压缩的距离，
                samples[i].fRadiusY))->unref();  // 沿着y轴压缩的距离

    SkRect bounds = SkRect::MakeXYWH(
            x,
            y,
            SkIntToScalar(samples[i].fWidth),
            SkIntToScalar(samples[i].fHeight));
    canvas->saveLayer(&bounds, &paint);
    canvas->drawBitmap(fBitmap, x, y);
    canvas->restore();
}

// 画path
{
    static const FillAndName gFills[] = {
        {SkPath::kWinding_FillType, "Winding"},                 // 画path里面的区域
        {SkPath::kEvenOdd_FillType, "Even / Odd"},              // 画path里面的区域
        {SkPath::kInverseWinding_FillType, "Inverse Winding"},   // 将path外面的区域画出来
        {SkPath::kInverseEvenOdd_FillType, "Inverse Even / Odd"}, // 将path外面的区域画出来           
    }; 
    static const StyleAndName gStyles[] = {                    
        {SkPaint::kFill_Style, "Fill"},             // 填充path内部
        {SkPaint::kStroke_Style, "Stroke"},         // 画轮廓线           
        {SkPaint::kStrokeAndFill_Style, "Stroke And Fill"},    
    };                                                    
    static const CapAndName gCaps[] = {
        {SkPaint::kButt_Cap, SkPaint::kBevel_Join, "Butt"},     // 方角
        {SkPaint::kRound_Cap, SkPaint::kRound_Join, "Round"},   // 圆角
        {SkPaint::kSquare_Cap, SkPaint::kBevel_Join, "Square"}  // 方角
    };

    void drawPath(SkPath& path,SkCanvas* canvas,SkColor color,
            const SkRect& clip,SkPaint::Cap cap, SkPaint::Join join,
            SkPaint::Style style, SkPath::FillType fill,
            SkScalar strokeWidth) {
        path.setFillType(fill); // 
        SkPaint paint;
        paint.setStrokeCap(cap); // 圆角，方形
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeJoin(join);  // 圆角，方形
        paint.setColor(color);
        paint.setStyle(style);    // fill, stroke, fill and stroke
        canvas->save();
        canvas->clipRect(clip);
        canvas->drawPath(path, paint);
        canvas->restore();
    }

}

// Image filter
{
    class FailImageFilter : public SkImageFilter {
        protected:
            virtual bool onFilterImage(Proxy*, const SkBitmap& src, const SkMatrix&,
                    SkBitmap* result, SkIPoint* offset) {
                return false;  // 直接返回false， image filter过滤出来什么都没有
            }
    };

    class IdentityImageFilter : public SkImageFilter {
        protected:
            virtual bool onFilterImage(Proxy*, const SkBitmap& src, const SkMatrix&,
                    SkBitmap* result, SkIPoint* offset) {
                *result = src;    // 过滤后的图片和原图完全一样
                return true;
            }
    };

    SkColorFilter* cf = SkColorFilter::CreateModeFilter(SK_ColorRED,
            SkXfermode::kSrcIn_Mode);  // 使用red替换原来图片的数据

    SkImageFilter* filters[] = {
#if 1
        NULL,
        new IdentityImageFilter,
        new FailImageFilter,
        new SkColorFilterImageFilter(cf),
#endif
        new SkBlurImageFilter(12.0f, 0.0f),    // 实现blur效果
    };

    SkPaint paint;
    paint.setColor(SK_ColorYELLOW);
    paint.setImageFilter(imf);   // 设置image filter
    SkRect rr(r);
    rr.inset(r.width()/10, r.height()/10);
    canvas->drawRect(rr, paint);
}

// 光照效果
{
    // 扩散效果
    {
        SkPoint3 pointLocation1(0, 0, SkIntToScalar(30)); // 光源的位置
        SkScalar surfaceScale1 = SkIntToScalar(0.5);        // 浮雕效果，0~1之间变化
        SkScalar kd1 = SkIntToScalar(1);                  // 光源的强度
        paint.setImageFilter(SkLightingImageFilter::                          
                CreatePointLitDiffuse(pointLocation1,                         
                green, surfaceScale1, kd1))->unref();
        canvas->drawSprite(fBitmap, 0, 0, &paint);
    }

    // 聚光灯效果 
    {
        SkPoint3 spotLocation(SkIntToScalar(-10),
                SkIntToScalar(-10), SkIntToScalar(20));   // 聚光灯的光源点
        SkPoint3 spotTarget(SkIntToScalar(40), SkIntToScalar(40), 0); // 聚光灯的投射目标点
        SkScalar spotExponent = SK_Scalar1;
        SkScalar cutoffAngle = SkIntToScalar(15); // 聚光灯的扇形扩散角度

        paint.setImageFilter(SkLightingImageFilter::
                CreateSpotLitDiffuse(spotLocation, spotTarget, spotExponent,
                    cutoffAngle, white, surfaceScale, kd))->unref();
        canvas->drawSprite(fBitmap, 110*i, 0, &paint);
        {
            paint.setImageFilter(SkLightingImageFilter::
                    CreateSpotLitSpecular(spotLocation, spotTarget, spotExponent,
                        cutoffAngle, white, surfaceScale, ks, shininess))->unref();
            canvas->drawSprite(fBitmap, 110*i, 0, &paint);
        }
    }
    {
        SkScalar ks = SkIntToScalar(1);    // 聚光灯的大小
        SkScalar shininess = SkIntToScalar(8);  // 和光线覆盖的范围成反比

        paint.setImageFilter(SkLightingImageFilter::
                CreatePointLitSpecular(pointLocation, white,
                    surfaceScale, ks, shininess))->unref();
        canvas->drawSprite(fBitmap, 110*i, 0, &paint);
    }
    {
        paint.setImageFilter(SkLightingImageFilter::
                CreateDistantLitSpecular(distantDirection,
                    white, surfaceScale, ks, shininess))->unref();
        canvas->drawSprite(fBitmap, 110*i, 110, &paint);
    }
}

// 模糊效果
{
    SkPaint paint;
    canvas->saveLayer(NULL, &paint);
    paint.setAntiAlias(true);
    const char* str = "The quick brown fox jumped over the lazy dog.";
    for (int i = 0; i < 12; ++i) {
        int x = 0;
        int y = i * HEIGHT / 10;
        paint.setColor(0xFF00FF00);
        paint.setImageFilter(
                new SkBlurImageFilter(i*1.01f, 0.0f))->unref();
                // SkBlurImageFilter(SkScalar sigmaX, SkScalar sigmaY);
                // sigmaX越大，文字的垂直部分模糊的越厉害
                // sigmaY越大，文字的水平部分模糊的越厉害
        paint.setTextSize(SkIntToScalar(50));
        canvas->drawText(str, strlen(str), SkIntToScalar(x),
                SkIntToScalar(y), paint);
    }
    canvas->restore();
}

// path 的fill 类型
{
    enum FillType {
        /** Specifies that "inside" is computed by a non-zero sum of signed
          edge crossings
         */
        kWinding_FillType,    
        /** Specifies that "inside" is computed by an odd number of edge
          crossings
         */
        kEvenOdd_FillType,
        /** Same as Winding, but draws outside of the path, rather than inside
         */
        kInverseWinding_FillType,      
        /** Same as EvenOdd, but draws outside of the path, rather than inside
         */
        kInverseEvenOdd_FillType       
    }; 

    目前得到的经验是kEvenOdd_FillType填充内部的区域有时候比kWinding_FillType要少
}


