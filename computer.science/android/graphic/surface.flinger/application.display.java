

问题
    Activity是怎么在屏幕上显示出来的呢

    基本概念
        Window
            PhoneWindow
                用于手机

            MidWindow
                用于Pad
    
            WindowManager

        View
            DecorView, ViewManager

        Canvas

        Bitmap

        Paint

////////////////////////////////////////////////////////////////////////////////       
/*

LocalWindowManager--wrap-->WindowManagerImpl
    |
    V
PhoneWindow-->DecorView(FrameLayout)-->Application View

WindowManagerImpl
    mRoots[index]->ViewRoot->Surface
                     |
                     V
    mViews[index]->DecorView

*/
////////////////////////////////////////////////////////////////////////////////       

framework/base/core/java/android/app/ActivityThread.java
    ---->ActivityThread:: handleLaunchActivity()

    private final void handleLaunchActivity(ActivityRecord r, Intent customIntent) {
        Activity a = performLaunchActivity(r, customIntent);
        if (a != null) {
            r.createdConfig = new Configuration(mConfiguration);
            Bundle oldState = r.state;
            handleResumeActivity(r.token, false, r.isForward);
                |
                V
final void handleResumeActivity(IBinder token, boolean clearHide, boolean isForward) {
    boolean willBeVisible = !a.mStartedActivity;

    if (r.window == null && !a.mFinished && willBeVisible) {
        r.window = r.activity.getWindow();
        View decor = r.window.getDecorView();
        decor.setVisibility(View.INVISIBLE);
        ViewManager wm = a.getWindowManager();
        WindowManager.LayoutParams l = r.window.getAttributes();
        a.mDecor = decor;
        l.type = WindowManager.LayoutParams.TYPE_BASE_APPLICATION;
        if (a.mVisibleFromClient) {
            a.mWindowAdded = true;
            wm.addView(decor, l); //这个很关键。
        }


// 这些隐藏的View的创建都是由你在Acitivty的onCreate中调用setContentView导致的
framework/policies/base/phone/com/android/internal/policy/impl/PhoneWindow.java
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (mContentParent == null) {  // 刚创建的时候mContentParent为空
            installDecor();

        mContentParent.addView(view, params); // 将用户创建的view加入到mContentParent中
        final Callback cb = getCallback();
        if (cb != null) {
            cb.onContentChanged();

    private void installDecor() { 
        if (mDecor == null) {
            // 创建一个DecorView
            mDecor = generateDecor();  // return new DecorView(getContext(), -1);
            mDecor.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            mDecor.setIsRootNamespace(true);

        if (mContentParent == null) {  
            // 通过mDecor创建mContentParent, mContentParent是ViewGroup类型
            mContentParent = generateLayout(mDecor);

            // title view
            mTitleView = (TextView)findViewById(com.android.internal.R.id.title);
            if (mTitleView != null) {      
                if ((getLocalFeatures() & (1 << FEATURE_NO_TITLE)) != 0) {
                    View titleContainer = findViewById(com.android.internal.R.id.title_container);
                    if (titleContainer != null) {  
                        titleContainer.setVisibility(View.GONE);
                    else {
                        mTitleView.setVisibility(View.GONE);
                    if (mContentParent instanceof FrameLayout) {
                        ((FrameLayout)mContentParent).setForeground(null);
                else {
                    mTitleView.setText(mTitle);    
        

ViewManager wm = a.getWindowManager()又返回什么呢？
PhoneWindow从Window中派生，Acitivity创建的时候会调用它的setWindowManager。而这个函数由Window类实现。

framework/base/core/java/android/view/Window.java中
public void setWindowManager(WindowManager wm,IBinder appToken, String appName) {
    mAppToken = appToken;
    mAppName = appName;
    if (wm == null) {
        wm = WindowManagerImpl.getDefault();
    }
    mWindowManager = new LocalWindowManager(wm);


回到wm.addView(decor, l)。最终会由WindowManagerImpl来完成addView操作
framework/base/core/java/android/view/WindowManagerImpl.java
    private void addView(View view, ViewGroup.LayoutParams params, boolean nest)

        ViewRoot root; //ViewRoot，我们的主人公终于登场！
        synchronized (this) {
            root = new ViewRoot(view.getContext());
            root.mAddNesting = 1;
            view.setLayoutParams(wparams);

            if (mViews == null) {
                index = 1;
                mViews = new View[1];
                mRoots = new ViewRoot[1];
                mParams = new WindowManager.LayoutParams[1];

            index--;
            mViews[index] = view;
            mRoots[index] = root;
            mParams[index] = wparams;
        root.setView(view, wparams, panelParentView);




