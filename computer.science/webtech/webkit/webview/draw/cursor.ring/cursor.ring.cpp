// 如果在滑动，就不要显示cursor ring

base/core/java/android/webkit/WebView.java

    // Whether or not to draw the cursor ring.
    private boolean mDrawCursorRing = true;

    mDrawCursorRing


/**
 * Request the anchor or image element URL at the last tapped point.
 * If hrefMsg is null, this method returns immediately and does not
 * dispatch hrefMsg to its target. If the tapped point hits an image,
 * an anchor, or an image in an anchor, the message associates
 * strings in named keys in its data. The value paired with the key
 * may be an empty string.
 * 
 * @param hrefMsg This message will be dispatched with the result of the
 *                request. The message data contains three keys:
 *                - "url" returns the anchor's href attribute.
 *                - "title" returns the anchor's text.
 *                - "src" returns the image's src attribute.
 */
public void requestFocusNodeHref(Message hrefMsg) {
    checkThread();
    if (hrefMsg == null) {
        return;
    }
    int contentX = viewToContentX(mLastTouchX + mScrollX);
    int contentY = viewToContentY(mLastTouchY + mScrollY);
    if (nativeHasCursorNode()) {   
        Rect cursorBounds = nativeGetCursorRingBounds(); 
        if (!cursorBounds.contains(contentX, contentY)) {
            int slop = viewToContentDimension(mNavSlop);
            cursorBounds.inset(-slop, -slop);
            if (cursorBounds.contains(contentX, contentY)) {
                contentX = (int) cursorBounds.centerX();
                contentY = (int) cursorBounds.centerY();
            }
        }
    }
    mWebViewCore.sendMessage(EventHub.REQUEST_CURSOR_HREF,
            contentX, contentY, hrefMsg);
}


private void drawContent(Canvas canvas, boolean drawRings) {
    drawCoreAndCursorRing(canvas, mBackgroundColor,
            mDrawCursorRing && drawRings);
}



void setActive(boolean active) 
    if (active) {
        if (hasFocus()) {
            // If our window regained focus, and we have focus, then begin
            // drawing the cursor ring
            mDrawCursorRing = !inEditingMode();
            setFocusControllerActive(true);
        } else {
            mDrawCursorRing = false;
            if (!inEditingMode()) {
                // If our window gained focus, but we do not have it, do not
                // draw the cursor ring.
                setFocusControllerActive(false);
            }
            // We do not call recordButtons here because we assume
            // that when we lost focus, or window focus, it got called with
            // false for the first parameter
        }
    } else {
        if (!mZoomManager.isZoomPickerVisible()) {
            /*
             * The external zoom controls come in their own window, so our
             * window loses focus. Our policy is to not draw the cursor ring
             * if our window is not focused, but this is an exception since
             * the user can still navigate the web page with the zoom
             * controls showing.
             */
            mDrawCursorRing = false;
        }


protected void onFocusChanged(boolean focused, int direction,
        Rect previouslyFocusedRect) {

    if (focused) {
        // When we regain focus, if we have window focus, resume drawing
        // the cursor ring
        if (hasWindowFocus()) {
            mDrawCursorRing = !inEditingMode();
            setFocusControllerActive(true);
            //} else {
            // The WebView has gained focus while we do not have
            // windowfocus.  When our window lost focus, we should have
            // called recordButtons(false...)
        }
    } else {
        // When we lost focus, unless focus went to the TextView (which is
        // true if we are in editing mode), stop drawing the cursor ring.
        mDrawCursorRing = false;
        if (!inEditingMode()) {
            setFocusControllerActive(false);
        }
        mKeysPressed.clear();
    }

private void drawCoreAndCursorRing(Canvas canvas, int color,
        boolean drawCursorRing) {

    // 如果是前进或后退, 画出history picuture就返回
    if (mDrawHistory) {
        canvas.scale(mZoomManager.getScale(), mZoomManager.getScale());
        canvas.drawPicture(mHistoryPicture);
        return;
    }


    // 画selection
    } else if (mSelectingText && (!USE_JAVA_TEXT_SELECTION || DEBUG_TEXT_HANDLES)) {
        extras = DRAW_EXTRAS_SELECTION;
        nativeSetSelectionPointer(mNativeClass,
                mDrawSelectionPointer,
                mZoomManager.getInvScale(), mSelectX, mSelectY - getTitleHeight());
    // 画cursor ring
    } else if (drawCursorRing) {
        extras = DRAW_EXTRAS_CURSOR_RING;
    }
    
    if (extras == DRAW_EXTRAS_CURSOR_RING) {
        if (mTouchMode == TOUCH_SHORTPRESS_START_MODE) {
            mTouchMode = TOUCH_SHORTPRESS_MODE;
        }
    }


    calcOurContentVisibleRectF(mVisibleContentRect);

    int content = nativeDraw(canvas, mVisibleContentRect, color, extras, false);
                |
                V
WebKit/android/nav/WebView.cpp

PictureSet* draw(SkCanvas* canvas, SkColor bgColor, int extras, bool split)

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
    if (!root) {                                                              
        if (extras == DrawExtrasCursorRing)                                   
            resetCursorRing();                                                

    if (extra) {
        IntRect dummy; // inval area, unused for now
        extra->draw(canvas, &mainPicture, &dummy);
    }




