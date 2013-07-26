


// 开始touch时的坐标
private int mStartTouchX;
private int mStartTouchY;
// 结束touch时的坐标
private int mLastTouchX;
private int mLastTouchY;

// touch角度
private float mAverageAngle;

// 最后一次touch的时间
private long mLastTouchTime;
// Time of the last time sending touch event to WebViewCore
private long mLastSentTouchTime;


// 向WebViewCore发送ACTION_MOVE的最小时间间隔
// 需要为每个设备进行配置
// 依赖于设备处理一个touch事件的所需要的时间(重新绘图)
private static final int TOUCH_SENT_INTERVAL = 0;
private int mCurrentTouchInterval = TOUCH_SENT_INTERVAL;

// 滚动条相关
private int mCurrentScrollingLayerId;
private Rect mScrollingLayerRect = new Rect();


// Touch模式 
private int mTouchMode = TOUCH_DONE_MODE;
private static final int TOUCH_INIT_MODE = 1;
private static final int TOUCH_DRAG_START_MODE = 2;
private static final int TOUCH_DRAG_MODE = 3;
private static final int TOUCH_SHORTPRESS_START_MODE = 4;
private static final int TOUCH_SHORTPRESS_MODE = 5;
private static final int TOUCH_DOUBLE_TAP_MODE = 6;
private static final int TOUCH_DONE_MODE = 7;
private static final int TOUCH_PINCH_DRAG = 8;
private static final int TOUCH_DRAG_LAYER_MODE = 9;

// 是否将touch事件发送到Webcore
// Can only be set by WebKit via JNI.
private boolean mForwardTouchEvents = false;


// ?????
// Whether to prevent default during touch. The initial value depends on
// mForwardTouchEvents. If WebCore wants all the touch events, it says yes
// for touch down. Otherwise UI will wait for the answer of the first
// confirmed move before taking over the control.
private static final int PREVENT_DEFAULT_NO = 0;
private static final int PREVENT_DEFAULT_MAYBE_YES = 1;
private static final int PREVENT_DEFAULT_NO_FROM_TOUCH_DOWN = 2;
private static final int PREVENT_DEFAULT_YES = 3;
private static final int PREVENT_DEFAULT_IGNORE = 4;
private int mPreventDefault = PREVENT_DEFAULT_IGNORE;

// true when the touch movement exceeds the slop
private boolean mConfirmMove;

// 当前的drag对象是textview
boolean mDragFromTextInput;

// 是否画出cursor ring.
private boolean mDrawCursorRing = true;

private HitTestResult mInitialHitTestResult;

// 说明点击对象的类型
public /*static*/ class HitTestResult {

    // Default HitTestResult, where the target is unknown
    public static final int UNKNOWN_TYPE = 0;

    // This type is no longer used.
    public static final int ANCHOR_TYPE = 1;

    // hitting a phone number
    public static final int PHONE_TYPE = 2;

    // hitting a map address
    public static final int GEO_TYPE = 3;

    // hitting an email address
    public static final int EMAIL_TYPE = 4;

    // hitting an HTML::img tag
    public static final int IMAGE_TYPE = 5;

    // This type is no longer used.
    public static final int IMAGE_ANCHOR_TYPE = 6;

    // hitting a HTML::a tag with src=http
    public static final int SRC_ANCHOR_TYPE = 7;

    // hitting a HTML::a tag with src=http + HTML::img
    public static final int SRC_IMAGE_ANCHOR_TYPE = 8;

    // hitting an edit text area
    public static final int EDIT_TEXT_TYPE = 9;

    private int mType;
    private String mExtra;
}

// tap的超时，用于分辨双击，长按等等事件
private static final int TAP_TIMEOUT = 300;

// 长按的超时
private static final int LONG_PRESS_TIMEOUT = 1000;

private static final int MIN_FLING_TIME = 250;

// 拖动后停止的事件
// draw unfiltered after drag is held without movement
private static final int MOTIONLESS_TIME = 100;

// 最后一次给native层发送的宽度和高度
int mLastWidthSent;
int mLastHeightSent;


int mLastActualHeightSent;

// 网页内容的宽度和高度?
private int mContentWidth;   // cache of value from WebViewCore
private int mContentHeight;  // cache of value from WebViewCore


// 是否折行？？
private boolean mWrapContent;

private static final int MOTIONLESS_FALSE           = 0;
private static final int MOTIONLESS_PENDING         = 1;
private static final int MOTIONLESS_TRUE            = 2;
private static final int MOTIONLESS_IGNORE          = 3;
private int mHeldMotionless;

