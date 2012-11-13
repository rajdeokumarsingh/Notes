WebView.cpp

static jint nativeDraw(JNIEnv *env, jobject obj, jobject canv,
        jobject visible, jint color,
        jint extras, jboolean split) {

    SkCanvas* canvas = GraphicsJNI::getNativeCanvas(env, canv);
    WebView* webView = GET_NATIVE_VIEW(env, obj);
    SkRect visibleRect = jrectf_to_rect(env, visible);
    webView->setVisibleRect(visibleRect);
    PictureSet* pictureSet = webView->draw(canvas, color, extras, split);
    return reinterpret_cast<jint>(pictureSet);
}
        |
        |
        V
PictureSet* draw(SkCanvas* canvas, SkColor bgColor, int extras, bool split)
{
    PictureSet* ret = 0;

    // 如果base layer为空，直接绘制背景颜色
    // 并返回
    if (!m_baseLayer) {
        canvas->drawColor(bgColor);
        return ret;
    }

    // draw the content of the base layer first
    PictureSet* content = m_baseLayer->content();
    int sc = canvas->save(SkCanvas::kClip_SaveFlag);
    canvas->clipRect(SkRect::MakeLTRB(0, 0, content->width(),
                content->height()), SkRegion::kDifference_Op);
    canvas->drawColor(bgColor);
    canvas->restoreToCount(sc);
    if (content->draw(canvas))
        ret = split ? new PictureSet(*content) : 0;

    CachedRoot* root = getFrameCache(AllowNewer);
    LayerAndroid mainPicture(m_navPictureUI);

    // 设置需要绘制的extra
    DrawExtra* extra = 0;
    switch (extras) {
        case DrawExtrasFind:
            extra = &m_findOnPage;
            break;
        case DrawExtrasSelection:
            // This will involve a JNI call, but under normal circumstances we will
            // not hit this anyway. Only if USE_JAVA_TEXT_SELECTION is disabled
            // in WebView.java will we hit this (so really debug only)
            updateSelectionHandles();
            extra = &m_selectText;
            break;
        case DrawExtrasCursorRing:
            if (drawCursorPreamble(root) && m_ring.setup()) {
                extra = &m_ring;
                drawCursorPostamble();
            }
            break;
        default:
            ;
    }
#if USE(ACCELERATED_COMPOSITING)
    LayerAndroid* compositeLayer = compositeRoot();
    if (compositeLayer) {
        // call this to be sure we've adjusted for any scrolling or animations
        // before we actually draw
        compositeLayer->updateFixedLayersPositions(m_visibleRect);
        compositeLayer->updatePositions();
        // We have to set the canvas' matrix on the base layer
        // (to have fixed layers work as intended)
        SkAutoCanvasRestore restore(canvas, true);
        m_baseLayer->setMatrix(canvas->getTotalMatrix());
        canvas->resetMatrix();
        m_baseLayer->draw(canvas);
    }
#endif
    if (extra) {
        IntRect dummy; // inval area, unused for now
        extra->draw(canvas, &mainPicture, &dummy); // XXX: FindOnPage::draw()
                                                   // ./FindCanvas.cpp
    }
    return ret;
}

