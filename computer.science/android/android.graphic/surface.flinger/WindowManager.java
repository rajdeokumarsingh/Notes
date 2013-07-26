什么是WindowManager

1. 用于管理一个窗口中的所有view
    包括contentView, decorView，menu等等
    维持view的拓扑层次结构
    提供添加，删除, 更新view的接口
        public interface WindowManager extends ViewManager
            public interface ViewManager
                public void addView(View view, ViewGroup.LayoutParams params);
                public void updateViewLayout(View view, ViewGroup.LayoutParams params);
                public void removeView(View view);

    WindowManagerService
        管理系统中的所有窗口

2. 提供LayoutParams
    used by views to tell their parents how they want to be laid out.
    basically, describes how big the view wants to be for both width and height

        FILL_PARENT, MATCH_PARENT
            the view wants to be as big as its parent (minus padding)
        WRAP_CONTENT
            the view wants to be just big enough to enclose its content (plus padding)

    /**
     * X position for this window.  With the default gravity it is ignored.
     * When using {@link Gravity#LEFT} or {@link Gravity#RIGHT} it provides
     * an offset from the given edge.
     */
    public int x;

    /**
     * Y position for this window.  With the default gravity it is ignored.
     * When using {@link Gravity#TOP} or {@link Gravity#BOTTOM} it provides
     * an offset from the given edge.
     */
    public int y;

    /**
     * Indicates how much of the extra space will be allocated horizontally
     * to the view associated with these LayoutParams. Specify 0 if the view
     * should not be stretched. Otherwise the extra pixels will be pro-rated
     * among all views whose weight is greater than 0.
     */
    public float horizontalWeight;
    public float verticalWeight;

    layout_weight 用于给一个线性布局中的诸多视图的重要度赋值。数值越小，重要度越高
    所有的视图都有一个layout_weight值，
        默认为零
        需要显示多大的视图就占据多大的屏幕空间。

    若赋一个高于零的值
        则将父视图中的可用空间分割
        分割大小具体取决于
            每一个视图的layout_weight值
            以及该值在当前屏幕布局的整体layout_weight值
            和在其它视图屏幕布局的layout_weight值中所占的比率而定。 

    举个例子：比如说我们在 水平方向上有一个文本标签和两个文本编辑元素。 
    该文本标签并无指定layout_weight值，所以它将占据需要提供的最少空间。 
    如果两个文本编辑元素每一个的layout_weight值都设置为1，则两者平分 
    在父视图布局剩余的宽度(因为我们声明这两者的重要度相等)。如果两个  
    文本编辑元素其中第一个的layout_weight值设置为1，而第二个的设置为2， 
    则剩余空间的三分之二分给第一个，三分之一分给第二个(数值越小，重要度越高)。  

    在layout_width设置为fill_parent的时候，layout_weight代表的是你的控件要优先尽可能的大,但尽可能大是有限度的，即fill_parent.
    在layout_width设置为wrap_content的时候，layout_weight代表的是你的控件要优先尽可能的小,但这个小是有限度的，即wrap_content.
     

    public int type;
        three main classes of window types:
            Application windows
                normal top-level application windows
                the {@link #token} must be set to the token of the activity

                * @see #TYPE_BASE_APPLICATION
                * @see #TYPE_APPLICATION
                    normal application
                * @see #TYPE_APPLICATION_STARTING

            Sub-windows
                associated with another top-level window
                the {@link #token} must be the token of the window it is attached to

                * @see #TYPE_APPLICATION_PANEL
                    menu
                * @see #TYPE_APPLICATION_MEDIA
                    video
                * @see #TYPE_APPLICATION_SUB_PANEL
                    sub menu
                * @see #TYPE_APPLICATION_ATTACHED_DIALOG
                    dialog

            System windows
                use by the system for specific purposes

                * @see #TYPE_STATUS_BAR
                * @see #TYPE_SEARCH_BAR
                * @see #TYPE_PHONE
                    incoming call
                * @see #TYPE_SYSTEM_ALERT
                    low power alert
                * @see #TYPE_KEYGUARD
                * @see #TYPE_TOAST
                * @see #TYPE_SYSTEM_OVERLAY
                * @see #TYPE_PRIORITY_PHONE
                * @see #TYPE_STATUS_BAR_PANEL
                * @see #TYPE_SYSTEM_DIALOG
                * @see #TYPE_KEYGUARD_DIALOG
                * @see #TYPE_SYSTEM_ERROR
                * @see #TYPE_INPUT_METHOD
                * @see #TYPE_INPUT_METHOD_DIALOG

        /**
         * Specifies what type of memory buffers should be used by this window.
         * Default is normal.
         * 
         * @see #MEMORY_TYPE_NORMAL
             The window's surface is allocated in main memory
             etc, the windown contains a bitmap

         * @see #MEMORY_TYPE_PUSH_BUFFERS
             The window's surface doesn't own its buffers and
             therefore cannot be locked. Instead the buffers are pushed to
             it through native binder calls.

             etc, the window contains a camera preview
                 tells surface flinger that another component is going to provide a buffer heap and indeed
                 use that buffer heap as the means of transferring surface data to it.
         */
        public int memoryType;

        /**
         * Various behavioral options/flags.  Default is none.
         * 
         * @see #FLAG_BLUR_BEHIND
            blur everything behind this window
         * @see #FLAG_DIM_BEHIND
            everything behind this window will be dimmed
         * @see #FLAG_NOT_FOCUSABLE
         * @see #FLAG_NOT_TOUCHABLE
         * @see #FLAG_NOT_TOUCH_MODAL
         * @see #FLAG_LAYOUT_IN_SCREEN
         * @see #FLAG_DITHER
         * @see #FLAG_KEEP_SCREEN_ON
         * @see #FLAG_FULLSCREEN
         * @see #FLAG_FORCE_NOT_FULLSCREEN
         * @see #FLAG_IGNORE_CHEEK_PRESSES
         */
        public int flags;

        /**
         * Desired operating mode for any soft input area.  May any combination
         * of:
         * 
         * <ul>
         * <li> One of the visibility states
             * {@link #SOFT_INPUT_STATE_UNSPECIFIED}, 
             * {@link #SOFT_INPUT_STATE_UNCHANGED},
                 don't change the state of the soft input area.
                 (when the user is navigating forward to your window).

             * {@link #SOFT_INPUT_STATE_HIDDEN}, 
                 please hide any soft input area when normally appropriate 
                 (when the user is navigating forward to your window).

             * {@link #SOFT_INPUT_STATE_ALWAYS_HIDDEN}, 
                please always hide any soft input area when this window receives focus.

             * {@link #SOFT_INPUT_STATE_VISIBLE}.
                please show the soft input area when normally appropriate 
                (when the user is navigating forward to your window).

             * {@link #SOFT_INPUT_STATE_ALWAYS_VISIBLE}

         * <li> One of the adjustment options
             * {@link #SOFT_INPUT_ADJUST_RESIZE}, or
             * {@link #SOFT_INPUT_ADJUST_PAN}.

             SOFT_INPUT_ADJUST_NOTHING:         不调整(输入法完全直接覆盖住,未开放此参数)
             SOFT_INPUT_ADJUST_PAN:         把整个Layout顶上去露出获得焦点的EditText,不压缩多余空间
                                            就是仅仅将正在编辑的EditText放到input method的顶部，
                                            EditText下面的其他部分不会显示

             SOFT_INPUT_ADJUST_RESIZE:      整个Layout重新编排,重新分配多余空间
                                            EditText下面的其他部分会显示出一部分，和输入法窗口填满屏幕

             SOFT_INPUT_ADJUST_UNSPECIFIED: 系统自己根据内容自行选择上两种方式的一种执行(默认配置)
         */
        public int softInputMode; 

        /**
         * This can be used to override the user's preferred brightness of
         * the screen.  A value of less than 0, the default, means to use the
         * preferred screen brightness.  0 to 1 adjusts the brightness from
         * dark to full bright.
         */
        public float screenBrightness = BRIGHTNESS_OVERRIDE_NONE;

        /**
         * Identifier for this window.  This will usually be filled in for
         * you.
         */
        public IBinder token = null;

        public int screenOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;

3. 其他member field:

private final Context mContext;                                                                                                   
private TypedArray mWindowStyle;
private Callback mCallback;    
private WindowManager mWindowManager;
    // mWindowManager = new LocalWindowManager(wm);

// The current window attributes.
private final WindowManager.LayoutParams mWindowAttributes =
    new WindowManager.LayoutParams();    

private IBinder mAppToken;

private Window mContainer;
private Window mActiveChild;   

WindowManager

private class LocalWindowManager implements WindowManager 
    封装了一个WindowManagerImpl
    包含了一个Display
        ./concept.display.txt

WindowManagerImpl
public class WindowManagerImpl implements WindowManager 
    Low-level communication with the global system window manager.

    // 单例模式
    private static WindowManagerImpl mWindowManager = new WindowManagerImpl();
    public static WindowManagerImpl getDefault() 
        return mWindowManager;

    // 成员
    private View[] mViews;
    private ViewRoot[] mRoots;
    private WindowManager.LayoutParams[] mParams;

    // 功能

    private void addView(View view, ViewGroup.LayoutParams params, boolean nest)
        int index = findViewLocked(view, false);

        // 如果添加的View是菜单，将其和父view关联
        if (wparams.type >= WindowManager.LayoutParams.FIRST_SUB_WINDOW &&
                wparams.type <= WindowManager.LayoutParams.LAST_SUB_WINDOW) {
            for (int i=0; i<count; i++) 
                if (mRoots[i].mWindow.asBinder() == wparams.token) 
                    panelParentView = mViews[i];

        // 为添加的View创建ViewRoot, 并设置参数
        root = new ViewRoot(view.getContext());
        root.mAddNesting = 1;
        view.setLayoutParams(wparams);

        // 创建或更新拓扑结构数组
        if (mViews == null) {
            index = 1;
            mViews = new View[1];
            mRoots = new ViewRoot[1];
            mParams = new WindowManager.LayoutParams[1];
        } else {
            index = mViews.length + 1;
            Object[] old = mViews;
            mViews = new View[index];
            System.arraycopy(old, 0, mViews, 0, index-1);
            old = mRoots;
            mRoots = new ViewRoot[index];
            System.arraycopy(old, 0, mRoots, 0, index-1);
            old = mParams;
            mParams = new WindowManager.LayoutParams[index];
            System.arraycopy(old, 0, mParams, 0, index-1);
        }
        index--;

        // 将新的view, viewRoot, LayoutParams 加入到拓扑结构数组中
        mViews[index] = view;
        mRoots[index] = root;
        mParams[index] = wparams;

        // do this last because it fires off messages to start doing things
        // 将新view，viewRoot, wparams以及panelView的父view关联起来
        root.setView(view, wparams, panelParentView);

public void updateViewLayout(View view, ViewGroup.LayoutParams params)
    // 更新view和其对应viewRoot的params
    view.setLayoutParams(wparams);

    int index = findViewLocked(view, true);
    ViewRoot root = mRoots[index];
    mParams[index] = wparams;
    root.setLayoutParams(wparams, false);

public void removeViewImmediate(View view) {
    // 找到拓扑结构中的viewRoot, 并调用die()
    int index = findViewLocked(view, true);
    ViewRoot root = mRoots[index];
    View curView = root.getView();
    root.mAddNesting = 0;
    root.die(true);

    // 更新拓扑结构，将view, viewRoot, params都删除
    finishRemoveViewLocked(curView, index);

View removeViewLocked(int index) {
    ViewRoot root = mRoots[index];
    View view = root.getView();

    InputMethodManager imm = InputMethodManager.getInstance(view.getContext());
    if (imm != null) {
        imm.windowDismissed(mViews[index].getWindowToken());
    }
    root.die(false);
    finishRemoveViewLocked(view, index);
    return view;

public void closeAll(IBinder token, String who, String what) {
    // remove all view, viewRoot, ... from the window

