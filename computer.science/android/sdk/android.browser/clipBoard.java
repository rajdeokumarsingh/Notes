
// user tap "select text" in browser menu
base/core/java/android/webkit/WebView.java
    public void emulateShiftHeld()
        setUpSelectXY();

    private void setUpSelectXY() 
        mSelectX = mScrollX + (int) mLastTouchX;
        mSelectY = mScrollY + (int) mLastTouchY;
        nativeHideCursor();

// user select text in the webview
public boolean onTouchEvent(MotionEvent ev)
    switch (action) 
        case MotionEvent.ACTION_DOWN:
        else if (!inFullScreenMode() && mShiftIsPressed) 
            mSelectX = mScrollX + (int) x;
            mSelectY = mScrollY + (int) y;
            mTouchMode = TOUCH_SELECT_MODE;
            nativeMoveSelection(contentX, contentY, false);
            mTouchSelection = mExtendSelection = true;
            invalidate(); // draw the i-beam instead of the arrow

        case MotionEvent.ACTION_UP:
            switch (mTouchMode) 
            case TOUCH_SELECT_MODE:
                commitCopy();
                mTouchSelection = false;
                break;

private boolean commitCopy()
    String selection = nativeGetSelection();
    if (selection != "")
        IClipboard clip = IClipboard.Stub.asInterface( 
                ServiceManager.getService("clipboard"));
                clip.setClipboardText(selection);

    mShiftIsPressed = false;
    invalidate(); // remove selection region and pointer
    if (mTouchMode == TOUCH_SELECT_MODE) {
        mTouchMode = TOUCH_INIT_MODE;
    }
    return copiedSomething;


