================================================================================
                    WebView.cpp
================================================================================

// reture android::WebView::drawGL to java WebView
static jint nativeGetDrawGLFunction(JNIEnv *env, jobject obj, jint nativeView,
        jobject jrect, jobject jviewrect,
        jobject jvisiblerect,
        jfloat scale, jint extras) {

    WebCore::IntRect viewRect = jrect_to_webrect(env, jrect);
    WebView *wvInstance = (WebView*) nativeView;
    SkRect visibleRect = jrectf_to_rect(env, jvisiblerect);
    wvInstance->setVisibleRect(visibleRect);

    GLDrawFunctor* functor = new GLDrawFunctor(wvInstance,
            &android::WebView::drawGL, viewRect, scale, extras);
    wvInstance->setFunctor((Functor*) functor);

    WebCore::IntRect webViewRect = jrect_to_webrect(env, jviewrect);
    functor->updateViewRect(webViewRect);

    return (jint)functor;
}


bool drawGL(WebCore::IntRect& viewRect, WebCore::IntRect* invalRect,
        WebCore::IntRect& webViewRect, int titleBarHeight,
                WebCore::IntRect& clip, float scale, int extras)
{
#if USE(ACCELERATED_COMPOSITING)
    if (!m_glWebViewState) {
        m_glWebViewState = new GLWebViewState();

        m_glWebViewState->setHighEndGfx(m_isHighEndGfx);
        m_glWebViewState->glExtras()->setCursorRingExtra(&m_ring);

        // XXX: connect m_findOnPage with GLExtra
        m_glWebViewState->glExtras()->setFindOnPageExtra(&m_findOnPage);
    }

    // XXX: Set the extra to draw
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
                if (m_ring.m_isPressed || m_ringAnimationEnd == UINT_MAX)
                    extra = &m_ring;
                drawCursorPostamble();
            }
            break;
        default:
            ;
    }

    m_glWebViewState->glExtras()->setDrawExtra(extra);

    bool ret = m_glWebViewState->drawGL(viewRect, m_visibleRect, invalRect,
            webViewRect, titleBarHeight, clip, scale,
            &treesSwapped, &newTreeHasAnim);
            // XXX: GLWebViewState->GLExtra->drawGL()
}
