
WebView.cpp

GLWebViewState

bool drawGL(WebCore::IntRect& viewRect, WebCore::IntRect* invalRect,
        WebCore::IntRect& webViewRect, int titleBarHeight,
                WebCore::IntRect& clip, float scale, int extras)
{
#if USE(ACCELERATED_COMPOSITING)
    if (!m_glWebViewState) {
        m_glWebViewState = new GLWebViewState();
        m_glWebViewState->setHighEndGfx(m_isHighEndGfx);

        // 设置cursor ring, find on page
        m_glWebViewState->glExtras()->setCursorRingExtra(&m_ring);
        m_glWebViewState->glExtras()->setFindOnPageExtra(&m_findOnPage);

        // 设置base layer到GLWebViewState
        if (m_baseLayer->content()) {
            SkRegion region;
            SkIRect rect;
            rect.set(0, 0, m_baseLayer->content()->width(), m_baseLayer->content()->height());
            region.setRect(rect);
            m_glWebViewState->setBaseLayer(m_baseLayer, region, false, true);
        }
    }

    // 设置本次需要绘制的glExtra, 如focus ring, find on page
    unsigned int pic = m_glWebViewState->currentPictureCounter();
    m_glWebViewState->glExtras()->setDrawExtra(extra);


    bool treesSwapped = false;
    bool newTreeHasAnim = false;

    // 调用GLWebViewState::drawGL()来绘制
    bool ret = m_glWebViewState->drawGL(viewRect, m_visibleRect, invalRect,
            webViewRect, titleBarHeight, clip, scale,
            &treesSwapped, &newTreeHasAnim);
    if (treesSwapped && (m_pageSwapCallbackRegistered || newTreeHasAnim)) {
        m_pageSwapCallbackRegistered = false;
        JNIEnv* env = JSC::Bindings::getJNIEnv();
        AutoJObject javaObject = m_javaGlue.object(env);
        if (javaObject.get()) {
            env->CallVoidMethod(javaObject.get(), m_javaGlue.m_pageSwapCallback, newTreeHasAnim);
            checkException(env);
        }
    }
    if (ret || m_glWebViewState->currentPictureCounter() != pic)
        return !m_isDrawingPaused;
#endif
    return false;
}





}


