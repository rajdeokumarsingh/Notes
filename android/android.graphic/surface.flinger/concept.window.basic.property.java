
FEATURE_NO_TITLE = 1;        //无标题栏
FEATURE_PROGRESS = 2;        //在标题栏上显示加载进度，例如webview加载网页时（条状进度条）
FEATURE_LEFT_ICON = 3;        //在标题栏左侧显示一个图标
FEATURE_RIGHT_ICON = 4;        //在标题栏右侧显示一个图标
FEATURE_INDETERMINATE_PROGRESS = 5;        //不确定的进度（圆圈状等待图标）
FEATURE_CONTEXT_MENU = 6;        //上下文菜单，相当于PC上的右键菜单（默认使能）
FEATURE_CUSTOM_TITLE = 7;        //自定义标题栏，该属性不能与其他标题栏属性合用
FEATURE_OPENGL = 8;        //如果开启OpenGL，那么2D将由OpenGL处理（OpenGL中2D是3D的子集）
PROGRESS_VISIBILITY_ON = -1;        //进度条可见
PROGRESS_VISIBILITY_OFF = -2;        //进度条不可见
PROGRESS_INDETERMINATE_ON = -3;        //开启不确定模式
PROGRESS_INDETERMINATE_OFF = -4;        //关闭不确定模式
PROGRESS_START = 0;        //第一进度条的最小值
PROGRESS_END = 10000;        //第一进度条的最大值
PROGRESS_SECONDARY_START = 20000;        //第二进度条的最小值
PROGRESS_SECONDARY_END = 30000;        //第二进度条的最大值

// 1、隐藏标题栏
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    // FIXME: requestWindowFeature()要在setContentView()之前调用；

// 2、在标题栏显示进度条
    requestWindowFeature(Window.FEATURE_PROGRESS);
    getWindow().setFeatureInt(Window.FEATURE_PROGRESS, 40*100);

// 3、使用自定义标题栏
    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    setContentView(R.layout.xxx);
    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.my_title_bar); 

// 4、清除标题栏内容，而区域保留
    ((ViewGroup)getWindow().findViewById(com.android.internal.R.id.title_container)).removeAllViews();

// 5、隐藏标题栏
((ViewGroup)getWindow().
  findViewById(com.android.internal.R.id.title_container)).setVisibility(View.GONE);

// 6、显示标题栏
  setVisibility(View.VISIBLE); 

