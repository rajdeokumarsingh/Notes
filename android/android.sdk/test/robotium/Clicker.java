
// Clicks on a given coordinate on the screen
public void clickOnScreen(float x, float y) {
    long downTime = SystemClock.uptimeMillis();
    long eventTime = SystemClock.uptimeMillis();

    MotionEvent event = MotionEvent.obtain(downTime, eventTime,
            MotionEvent.ACTION_DOWN, x, y, 0);
    MotionEvent event2 = MotionEvent.obtain(downTime, eventTime,
            MotionEvent.ACTION_UP, x, y, 0);

    inst.sendPointerSync(event);
    inst.sendPointerSync(event2);
    sleeper.sleep(MINISLEEP);
}


// Long clicks a given coordinate on the screen
public void clickLongOnScreen(float x, float y, int time) {
    long downTime = SystemClock.uptimeMillis();
    long eventTime = SystemClock.uptimeMillis();
    MotionEvent event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, 0);
    inst.sendPointerSync(event);

    eventTime = SystemClock.uptimeMillis();
    event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, 
            x + ViewConfiguration.getTouchSlop() / 2,
            y + ViewConfiguration.getTouchSlop() / 2, 0);
    inst.sendPointerSync(event);

    if(time > 0)
        sleeper.sleep(time);
    else
        sleeper.sleep((int)(ViewConfiguration.getLongPressTimeout() * 2.5f));

    eventTime = SystemClock.uptimeMillis();
    event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, 0);
    inst.sendPointerSync(event);
    sleeper.sleep();
}

// Clicks on a given {@link View}.
// @param view the view that should be clicked
public void clickOnScreen(View view) {
    clickOnScreen(view, false, 0);
}


// Private method used to click on a given view.
public void clickOnScreen(View view, boolean longClick, int time) {
    int[] xy = new int[2];
    view.getLocationOnScreen(xy);

    final int viewWidth = view.getWidth();
    final int viewHeight = view.getHeight();
    final float x = xy[0] + (viewWidth / 2.0f);
    float y = xy[1] + (viewHeight / 2.0f);

    if (longClick)
        clickLongOnScreen(x, y, time);
    else
        clickOnScreen(x, y);
}


/**
 * Long clicks on a specific {@link TextView} and then selects
 * an item from the context menu that appears. Will automatically scroll when needed.
 *
 * @param text the text that should be clicked on. The parameter <strong>will</strong> be interpreted as a regular expression.
 * @param index the index of the menu item that should be pressed
 */
public void clickLongOnTextAndPress(String text, int index)
{
    clickOnText(text, true, 0, true, 0);
    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);

    for(int i = 0; i < index; i++)
    {
        sleeper.sleepMini();
        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
    }
    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
}
