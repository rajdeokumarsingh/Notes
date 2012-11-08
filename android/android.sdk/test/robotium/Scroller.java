/**
 * Simulate touching a specific location and dragging to a new location.
 *
 * @param fromX X coordinate of the initial touch, in screen coordinates
 * @param toX Xcoordinate of the drag destination, in screen coordinates
 * @param fromY X coordinate of the initial touch, in screen coordinates
 * @param toY Y coordinate of the drag destination, in screen coordinates
 * @param stepCount How many move steps to include in the drag
 *
 */
public void drag(float fromX, float toX, float fromY, float toY, int stepCount) {
    // 模拟motion down事件
    MotionEvent event = MotionEvent.obtain(downTime, eventTime,MotionEvent.ACTION_DOWN, fromX, fromY, 0);
    inst.sendPointerSync(event);

    // 模拟motion move事件
    event = MotionEvent.obtain(downTime, eventTime,MotionEvent.ACTION_MOVE, x, y, 0);
    inst.sendPointerSync(event);
    // ...

    // 模拟motion up事件
    event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP,toX, toY, 0);
    inst.sendPointerSync(event);

// Scrolls a ScrollView.
// @return {@code true} if scrolling occurred, false if it did not
private boolean scrollScrollView(final ScrollView view, int direction)
    final int scrollAmount = view.getHeight()--;
    inst.runOnMainSync(new Runnable(){ 
            public void run(){ view.scrollBy(0, scrollAmount); } });

// Scrolls a ScrollView to top or bottom.
private void scrollScrollViewAllTheWay(final ScrollView view, final int direction)
    while(scrollScrollView(view, direction));

// scrolls up or down.
// @return {@code true} if more scrolling can be done
public boolean scroll(int direction)
    return scroll(direction, false);


// Scrolls up and down.
// @param allTheWay <code>true</code> if the view should be scrolled to the beginning or end,
//                   <code>false</code> to scroll one page up or down.
// @return {@code true} if more scrolling can be done
public boolean scroll(int direction, boolean allTheWay) {

    final ArrayList<View> viewList = RobotiumUtils.
        removeInvisibleViews(viewFetcher.getAllViews(true));

    @SuppressWarnings("unchecked")
    ArrayList<View> views = RobotiumUtils.filterViewsToSet(new Class[] { ListView.class,
            ScrollView.class, GridView.class}, viewList);

    View view = viewFetcher.getFreshestView(views);

    if (view instanceof AbsListView)
        return scrollList((AbsListView)view, direction, allTheWay);

    if (view instanceof ScrollView) {
        if (allTheWay) {
            scrollScrollViewAllTheWay((ScrollView) view, direction);
            return false;
        } else {
            return scrollScrollView((ScrollView)view, direction);
        }
    }
    return false;
}

// Scrolls a list.
// @param allTheWay {@code true} to scroll the view all the way up or down, 
    // {@code false} to scroll one page up or down.
// * @return {@code true} if more scrolling can be done
public <T extends AbsListView> boolean scrollList(T absListView, int direction, boolean allTheWay)

 // Scroll the list to a given line
 // @param view the {@link AbsListView} to scroll
 // @param line the line to scroll to
private <T extends AbsListView> void scrollListToLine(final T view, final int line)
    if(view instanceof GridView)
        lineToMoveTo = line+1;
    else
        lineToMoveTo = line;
    inst.runOnMainSync(new Runnable(){
            public void run(){ view.setSelection(lineToMoveTo); } });

// Scrolls horizontally.
// @param side the side to which to scroll; {@link Side#RIGHT} or {@link Side#LEFT}
public void scrollToSide(Side side) {
    int screenHeight = activityUtils.getCurrentActivity().
        getWindowManager().getDefaultDisplay().getHeight();
    int screenWidth = activityUtils.getCurrentActivity(false).
        getWindowManager().getDefaultDisplay().getWidth();

    float x = screenWidth / 2.0f;
    float y = screenHeight / 2.0f;
    if (side == Side.LEFT)
        drag(0, x, y, y, 40);
    else if (side == Side.RIGHT)
        drag(x, 0, y, y, 40);

// Scrolls view horizontally.
// @param view the view to scroll
// @param side the side to which to scroll; {@link Side#RIGHT} or {@link Side#LEFT}
public void scrollViewToSide(View view, Side side) {
    int[] corners = new int[2];
    view.getLocationOnScreen(corners);
    int viewHeight = view.getHeight();
    int viewWidth = view.getWidth();
    float x = corners[0] + viewWidth / 2.0f;
    float y = corners[1] + viewHeight / 2.0f;
    if (side == Side.LEFT)
        drag(corners[0], x, y, y, 40);
    else if (side == Side.RIGHT)
        drag(x, corners[0], y, y, 40);
}




