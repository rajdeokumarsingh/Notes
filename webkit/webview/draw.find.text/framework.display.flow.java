WebView.java

protected void onDraw(Canvas canvas) {
// ...
    boolean drawJavaRings = !mTouchHighlightRegion.isEmpty()
        && (mTouchMode == TOUCH_INIT_MODE
                || mTouchMode == TOUCH_SHORTPRESS_START_MODE
                || mTouchMode == TOUCH_SHORTPRESS_MODE
                || mTouchMode == TOUCH_DONE_MODE);

    // native ring和java ring是互斥的
    boolean drawNativeRings = !drawJavaRings;
    if (USE_WEBKIT_RINGS) {
        drawNativeRings = !drawJavaRings && !isInTouchMode();
    }
    drawContent(canvas, drawNativeRings);

    // paint the highlight in the end
    if (drawJavaRings) {
        long delay = System.currentTimeMillis() - mTouchHighlightRequested;
        if (delay < ViewConfiguration.getTapTimeout()) {
            Rect r = mTouchHighlightRegion.getBounds();
            postInvalidateDelayed(delay, r.left, r.top, r.right, r.bottom);
        } else {
            if (mTouchHightlightPaint == null) {
                mTouchHightlightPaint = new Paint();
                mTouchHightlightPaint.setColor(HIGHLIGHT_COLOR);
            }
            RegionIterator iter = new RegionIterator(mTouchHighlightRegion);
            Rect r = new Rect();
            while (iter.next(r)) {
                canvas.drawRect(r, mTouchHightlightPaint);
            }
        }
    }
// ...
}

private void drawContent(Canvas canvas, boolean drawRings) {
    drawCoreAndCursorRing(canvas, mBackgroundColor,
            mDrawCursorRing && drawRings);
}


private void drawCoreAndCursorRing(Canvas canvas, int color,
        boolean drawCursorRing) {
    // ...

    // 确定需要绘制哪一个extra
    int extras = DRAW_EXTRAS_NONE;
    if (mFindIsUp) {
        extras = DRAW_EXTRAS_FIND;
    } else if (mSelectingText && (!USE_JAVA_TEXT_SELECTION || DEBUG_TEXT_HANDLES)) {
        extras = DRAW_EXTRAS_SELECTION;
        nativeSetSelectionPointer(mNativeClass,
                mDrawSelectionPointer,
                mZoomManager.getInvScale(), mSelectX, mSelectY - getTitleHeight());
    } else if (drawCursorRing) {
        extras = DRAW_EXTRAS_CURSOR_RING;
    }


    // 硬件加速绘制流程
    if (canvas.isHardwareAccelerated()) {
        Rect glRectViewport = mGLViewportEmpty ? null : mGLRectViewport;
        Rect viewRectViewport = mGLViewportEmpty ? null : mViewRectViewport;

        // FIXME: 参见 ./hardware.display.way.cpp
        int functor = nativeGetDrawGLFunction(mNativeClass, glRectViewport,
                viewRectViewport, mVisibleContentRect, getScale(), extras);
        ((HardwareCanvas) canvas).callDrawGLFunction(functor);

        if (mHardwareAccelSkia != getSettings().getHardwareAccelSkiaEnabled()) {
            mHardwareAccelSkia = getSettings().getHardwareAccelSkiaEnabled();
            nativeUseHardwareAccelSkia(mHardwareAccelSkia);
        }
    } else {
        // 软件方式绘制
    
        // FIXME: 参见 ./software.display.way.cpp
        // XXX: Revisit splitting content.  Right now it causes a
        // synchronization problem with layers.
        int content = nativeDraw(canvas, mVisibleContentRect, color,
                extras, false);
    }
}


