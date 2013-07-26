1. Browser
2. framework
    base/core/java/android/webkit/WebSettings.java
3. webkit

WebCore/platform/graphics/BitmapImage.cpp
    
Log:
WebCore/platform/graphics/
    GraphicsContext.cpp
    GraphicsLayer.cpp
    ImageSource.cpp
WebCore/platform/graphics/android

render
cachedimage


层次结构   
    CachedImage

    Image
    BitmapImage
    ImageSource


WebCore/platform/graphics/BitmapImage.cpp
    管理，播放动画图片的多个帧

    提供一个FrameData的数组。
        每个FrameData对应动画的一帧图片。
            包括一个指向位图数据的指针
            以及该帧动画的显示时长

    提供一个Timer， 用于处理每帧动画播放的duration
    包括一个ImageSource, 由于图片的解码 


关键函数：
void BitmapImage::cacheFrame(size_t index)
    调用ImageSource的createFrameAtIndex(index)方法，解码图片中的第index帧
    然后将解码后的数据(位图数据，duration, ...)保存在FrameData数组中

bool BitmapImage::dataChanged(bool allDataReceived)
    调用ImageSource的setData()方法将图片原始数据设置到解码器
    m_source.setData(data(), allDataReceived);  

NativeImagePtr BitmapImage::frameAtIndex(size_t index)
    获取图片的第index帧的位图数据

float BitmapImage::frameDurationAtIndex(size_t index)
    获取图片的第index帧的播放时长

void BitmapImage::startAnimation(bool catchUpIfNecessary)  
    开始播放动画
    计算当前帧的时长，创建一个定时器，调用BitmapImage::advanceAnimation


void BitmapImage::stopAnimation()
    停止动画播放的定时器

void BitmapImage::advanceAnimation(Timer<BitmapImage>*)  
bool BitmapImage::internalAdvanceAnimation(bool skippingFrames)
    通知observer画下一帧动画
    imageObserver()->animationAdvanced(this);

// 创建图片的过程
WebCore/loader/cache/CachedImage.cpp, data, 362  // 收到网络数据
WebCore/loader/cache/CachedImage.cpp, createImage, 322  // 创建图片
WebCore/platform/graphics/Image.cpp, Image, 52
WebCore/platform/graphics/android/ImageSourceAndroid.cpp, ImageSource, 143  // 创建ImageSource
WebCore/platform/graphics/BitmapImage.cpp, BitmapImage, 75  // 创建图片

WebCore/loader/cache/CachedImage.cpp, notifyObservers, 287
WebCore/loader/cache/CachedResourceClientWalker.cpp, CachedResourceClientWalker, 38
WebCore/loader/cache/CachedResourceClientWalker.cpp, next, 51

WebCore/rendering/RenderObject.cpp, imageChanged, 3223
WebCore/rendering/RenderImage.cpp, imageChanged, 182
WebCore/rendering/RenderImage.cpp, imageDimensionsChanged, 220




// 画图的过程
WebCore/rendering/InlineBox.cpp, paint, 251
WebCore/rendering/RenderImage.cpp, paint, 407
WebCore/rendering/RenderReplaced.cpp, paint, 140

void RenderReplaced::paint(PaintInfo& paintInfo, int tx, int ty)
        paintReplaced(paintInfo, tx, ty);
            |
            V
void RenderImage::paintReplaced(PaintInfo& paintInfo, int tx, int ty)
            |
            V
ore/rendering/RenderImage.cpp, paintIntoRect, 480
void RenderImage::paintIntoRect(GraphicsContext* context, const IntRect& rect)
    context->drawImage(m_imageResource->image(rect.width(), rect.height()).get(), 
            style()->colorSpace(), rect, compositeOperator, useLowQualityScaling);
                |
                |  -------------------------------------------------------------
                |    平台图形适配层
                |  -------------------------------------------------------------
                V 
WebCore/platform/graphics/GraphicsContext.cpp
    void GraphicsContext::drawImage(Image* image, ColorSpace styleColorSpace, const FloatRect& dest, 
            const FloatRect& src, CompositeOperator op, bool useLowQualityScale)
        image->draw(this, FloatRect(dest.location(), FloatSize(tw, th)), 
                FloatRect(src.location(), FloatSize(tsw, tsh)), styleColorSpace, op);
                    |
                    V
WebCore/platform/graphics/android/ImageAndroid.cpp
    void BitmapImage::draw(GraphicsContext* ctxt, const FloatRect& dstRect,
            const FloatRect& srcRect, ColorSpace,
            CompositeOperator compositeOp)

        startAnimation();

        SkBitmapRef* image = this->nativeImageForCurrentFrame();
        const SkBitmap& bitmap = image->bitmap();


        // 计算缩放大小
        SkIRect srcR;
        SkRect  dstR(dstRect);
        float invScaleX = (float)bitmap.width() / image->origWidth();
        float invScaleY = (float)bitmap.height() / image->origHeight();
        round_scaled(&srcR, srcRect, invScaleX, invScaleY);

        // 配置画笔
        SkPaint     paint;
        ctxt->setupBitmapPaint(&paint);   // need global alpha among other things
        paint.setFilterBitmap(true);
        paint.setXfermodeMode(WebCoreCompositeToSkiaComposite(compositeOp));
        fixPaintForBitmapsThatMaySeam(&paint);

        // 在canvas上绘制图片
        SkCanvas*   canvas = ctxt->platformContext()->mCanvas;
        canvas->drawBitmapRect(bitmap, &srcR, dstR, &paint);


// 图片改变时
WebCore/loader/cache/CachedImage.cpp, notifyObservers, 287
WebCore/loader/cache/CachedResourceClientWalker.cpp, CachedResourceClientWalker, 38
WebCore/loader/cache/CachedResourceClientWalker.cpp, next, 51
WebCore/rendering/RenderObject.cpp, imageChanged, 3223
WebCore/rendering/RenderImage.cpp, imageChanged, 182
WebCore/rendering/RenderImage.cpp, imageDimensionsChanged, 220

思路在设置改变的时候调用
1. 停止动画
2. CachedImage::data()
    不可行

问题，如何遍历tree?
3. 触发render tree中image的redraw
    或触发dom tree中image的redraw
    或触发cache node tree中的image的redraw

