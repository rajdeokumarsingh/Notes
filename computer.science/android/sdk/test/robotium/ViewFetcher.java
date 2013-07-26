private static Class<?> windowManager;
static{
    try {
        // 这是一个singleton对象, Applicaiton中全局就有一个sWindowManager
        // 并且已经静态初始化过了
        windowManager = Class.forName("android.view.WindowManagerImpl");

    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    } catch (SecurityException e) {
        e.printStackTrace();
    }
}

// 通过反射，获取window manager中的mViews包括的所有view
// FIXME: 并不都是decorView ??
public View[] getWindowDecorViews()
{
    Field viewsField;
    Field instanceField;
    viewsField = windowManager.getDeclaredField("mViews");
    // 这是一个singleton对象, Applicaiton中全局就有一个sWindowManager
    instanceField = windowManager.getDeclaredField("sWindowManager");
    viewsField.setAccessible(true);
    instanceField.setAccessible(true);
    Object instance = instanceField.get(null);
    return (View[]) viewsField.get(instance);
}

// 找出views中所有不是DecorView的view
private final View[] getNonDecorViews(View[] views) {
    final View[] noDecorViews = new View[views.length];

    for (int j = 0; j < views.length; j++) {
        view = views[j];
        if (view != null && !(view.getClass().getName()
                .equals("com.android.internal.policy.impl.PhoneWindow$DecorView"))) {
            noDecorViews[i] = view;
            i++;
        }
    }
    return noDecorViews;
}

// Adds all children of {@code viewGroup} (recursively) into {@code views}.
// * @param views an {@code ArrayList} of {@code View}s
// * @param viewGroup the {@code ViewGroup} to extract children from
// * @param onlySufficientlyVisible if only sufficiently visible views should be returned
private void addChildren(ArrayList<View> views, ViewGroup viewGroup, boolean onlySufficientlyVisible) {
    for (int i = 0; i < viewGroup.getChildCount(); i++) {
        final View child = viewGroup.getChildAt(i);
        views.add(child);
        if (child instanceof ViewGroup) {
            addChildren(views, (ViewGroup) child, onlySufficientlyVisible);
        }
    }
}


// Returns views from the shown DecorViews.
// 列表中包括了所有的非decor view, 以及最近显示的decor view及其子孙
// @param onlySufficientlyVisible if only sufficiently visible views should be returned
// @return all the views contained in the DecorViews
public ArrayList<View> getAllViews(boolean onlySufficientlyVisible) {
    // FIXME：通过window manager获取到所有的view, 而不是decor view
    final View[] views = getWindowDecorViews(); 

    // 获取所有的非decor view
    final View[] nonDecorViews = getNonDecorViews(views);

    // 保存返回的结果
    final ArrayList<View> allViews = new ArrayList<View>();

    for(int i = 0; i < nonDecorViews.length; i++){
        view = nonDecorViews[i];
        // 将view的所有子孙加入到结果列表中
        addChildren(allViews, (ViewGroup)view, onlySufficientlyVisible);
        // 将view的加入到结果列表中
        allViews.add(view);
    }

    // 找到当前最近显示的decor view
    view = getRecentDecorView(views);
    // 将decor view的所有子孙加入到结果列表中
    addChildren(allViews, (ViewGroup)view, onlySufficientlyVisible);
    // 将decor view的加入到结果列表中
    allViews.add(view);

    // 列表中包括了所有的非decor view, 以及最近显示的decor view及其子孙
    return allViews;
}

/** 返回views中最近和用户交互过的view (draw time最近的一个view)
 * Tries to guess which view is the most likely to be interesting. Returns
 * the most recently drawn view, which presumably will be the one that the
 * user was most recently interacting with.
 *
 * @param views A list of potentially interesting views, likely a collection
 *            of views from a set of types, such as [{@link Button},
 *            {@link TextView}] or [{@link ScrollView}, {@link ListView}]
 * @return most recently drawn view, or null if no views were passed 
 */
public final <T extends View> T getFreshestView(ArrayList<T> views){
    T viewToReturn = null;
    for(T view : views){
        view.getLocationOnScreen(locationOnScreen);
        if (locationOnScreen[0] < 0 ) 
            continue;

        if(view.getDrawingTime() > drawingTime && view.getHeight() > 0){
            drawingTime = view.getDrawingTime();
            viewToReturn = view;
        }
    }
    return viewToReturn;
}

// Returns the absolute top parent {@code View} in for a given {@code View}.
// @param view the {@code View} whose top parent is requested
// @return the top parent {@code View}
public View getTopParent(View view)
    通过view.getParent() && 
        view.getParent()  instanceof android.view.View
        不断向上找到最顶层的view

// 返回一个子view的parent list view或scroll view
// Returns the scroll or list parent view
// @param view the view who's parent should be returned
// @return the parent scroll view, list view or null
public View getScrollOrListParent(View view) {
    if ((view instanceof android.widget.AbsListView) || 
            (view instanceof android.widget.ScrollView)) 
        return view;
    else 
        return getScrollOrListParent((View) view.getParent());
}

// 找出views中最晚绘制的一个view
// Returns the most recent view container
// @param views the views to check
// @return the most recent view container
private final View getRecentContainer(View[] views) {
    View container = null;
    long drawingTime = 0;

    for(int i = 0; i < views.length; i++){
        View view = views[i];
        if (view != null && view.isShown() && view.hasWindowFocus() && 
        view.getDrawingTime() > drawingTime) {
            container = view;
            drawingTime = view.getDrawingTime();
        }
    }
    return container;
}

// 大概意思是view的超过一半(高度)都显示到了屏幕/parent上
// Returns true if the view is sufficiently shown
// @param view the view to check
// @return true if the view is sufficiently shown
public final boolean isViewSufficientlyShown(View view){

// 返回parent以及parent的所有子view
// 如果parent为空，返回getAllViews()
public ArrayList<View> getViews(View parent, boolean onlySufficientlyVisible)
    final ArrayList<View> views = new ArrayList<View>();
    final View parentToUse;

    if (parent == null){
        return getAllViews(onlySufficientlyVisible);
    }else{
        parentToUse = parent;

        views.add(parentToUse);
        if (parentToUse instanceof ViewGroup) {
            addChildren(views, (ViewGroup) parentToUse, onlySufficientlyVisible);
        }
    }
    return views;

/**
 * Returns an {@code ArrayList} of {@code View}s of the specified {@code Class} 
 *  located under the specified {@code parent}.
 *
 * @param classToFilterBy return all instances of this class, e.g. {@code Button.class} or {@code GridView.class}
 * @param parent the parent {@code View} for where to start the traversal
 * @return an {@code ArrayList} of {@code View}s of the specified {@code Class} located under the specified {@code parent}
 */
public <T extends View> ArrayList<T> getCurrentViews(Class<T> classToFilterBy, View parent) {
    ArrayList<T> filteredViews = new ArrayList<T>();
    List<View> allViews = getViews(parent, true);
    for(View view : allViews){
        if (view != null && classToFilterBy.isAssignableFrom(view.getClass())) {
            filteredViews.add(classToFilterBy.cast(view));
        }
    }
    allViews = null;
    return filteredViews;
}


/**
 * Returns an {@code ArrayList} of {@code View}s of the specified {@code Class} located in the current
 * {@code Activity}.
 *
 * @param classToFilterBy return all instances of this class, e.g. {@code Button.class} or {@code GridView.class}
 * @return an {@code ArrayList} of {@code View}s of the specified {@code Class} located in the current {@code Activity}
 */
public <T extends View> ArrayList<T> getCurrentViews(Class<T> classToFilterBy)
    return getCurrentViews(classToFilterBy, null);


// Returns the height of the scroll or list view parent
// @param view the view who's parents height should be returned
// @return the height of the scroll or list view parent
public float getScrollListWindowHeight(View view)
