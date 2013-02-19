Page 
WebFrame
WebFrameView
Frame 
FrameView
WebViewCore // TODO:
FrameLoaderClientAndroid
Document
FrameLoader
RenderView

./main.class.java.hierarchy.java
    [done]  需要进一步阅读每个类的关系

./main.class.native.hierarchy.cpp

全局拓扑结构 {
    所有Page集合, 保存了所有的页面指针
        static HashSet<Page*>* allPages;

    访问一个Page中的所有Frame {
        一个Page使用一个m_mainFrame指向该页面的main Frame, 
            并通过m_frameCount保存页面中的frame数量

        可通过Frame的tree()方法获取FrameTree, 进而访问页面中的所有的Frame
    }

    一个frame可以通过其成员变量FrameTree遍历其兄弟节点
        FrameTree* tree() const;
        ./code/FrameTree.h
}

Page {
    代表一个完整的网页页面

    主要的工作
        ./code/Page.cpp

        向所有Frame的document, domWindow, loader, editor, view发送各种事件
        通过BackForwardController完成历史项的goBack, goForward的操作
        PageGroup操作
        // TODO: 什么是PageGroup?
            ./code/PageGroup.cpp

            WebKit/android/jni/WebCoreFrameBridge.cpp 
                CreateFrame()中为每个page设置了group都是
                    page->setGroupName("android.webkit");

    static HashSet<Page*>* allPages <---> Page *
        全局结构，所有创建的Page对象都会加入allPages
        Page对象销毁前会从allPages中移除

    // Page包括了一个PageClients
    PageClients {
        ChromeClientAndroid
        EditorClientAndroid
        DeviceMotionClientAndroid(WebViewCore)
        DeviceOrientationClientAndroid(WebViewCore)
        ContextMenuClientAndroid
        DragClientAndroid
        InspectorClientAndroid

        ./code/page.page_clients.cpp
    }

    Frame
    BackForwardList
    PageGroup

    ViewportArguments
    PluginData
    WMLPageState
    ScrollableArea
    SpeechInput
    SpeechInputClient

    // 仅仅创建这些类的实例，以供其他类使用
    {
        Settings
        ProgressTracker
        BackForwardController

        Chrome
        RenderTheme

        FocusController
        SelectionController
        DragController
        ContextMenuController
        InspectorController
        GeolocationController
        DeviceMotionController
        DeviceOrientationController
   }

    // TODO:
   constructor {
       1.  WebKit/android/jni/WebCoreFrameBridge.cpp
            static void CreateFrame(JNIEnv* env, jobject obj, 
                jobject javaview, jobject jAssetManager, jobject historyList)

       2. Page* ChromeClientAndroid::createWindow(Frame* frame, const FrameLoadRequest&, 
               const WindowFeatures& features, const NavigationAction&)

   }
}

WebFrame {
    WebKit/android/jni/WebCoreFrameBridge.cpp

    对应Java层的BrowserFrame 

    ./code/WebFrame.java2cpp.cpp
        java向cpp发送事件的接口
        接口的实现者一般是Frame

        重要方法
            CreateFrame
                创建Page, PageClient
                    WebFrame, WebFrameView
                    Frame, FrameView, FrameLoaderClientAndroid 
                    WebViewCore

    ./code/WebFrame.cpp
        cpp向java发送通知，回调事件的接口
        将WebKit的各种事件通知给java层
            如加载的开始，进度，收到title, 收到favicon, 结束, 错误, 需要下载等等
            添加，更新历史项
            创建新窗口，关闭窗口
            需要鉴权

    // TODO;                
    constructor {
    }
}

WebFrameView : public WebCoreViewBridge {
    ./code/WebCoreViewBridge.h {
        typedef WebCoreViewBridge* PlatformWidget

        WebFrameView的基类
            提供Frame视图的几何参数的管理方法（位置，大小）
                Frame应该画在屏幕的那个区域，画多大

            提供设置边界，可视边界, 设置窗口边界等等接口 {
                WebCore::IntRect    m_bounds;
                WebCore::IntRect    m_visibleBounds;
                WebCore::IntRect    m_windowBounds;

                // FIXME: Just guess
                Window Rect: 
                    这个浏览器窗口的大小, 包括title bar等等
                Bound Rect:
                    WebView的整个区域大小
                Visible Bound Rect:
                    WebView的没有被遮住的区域的大小
            }
    }

    ./code/WebFrameView.cpp
        主要提供了draw方法
        deps: {
          GraphicsContext
          FrameView
          Frame
      }
}

Frame {
    ./code/Frame.cpp
    ./main.class.native.hierarchy.cpp

    // TODO:
    VisiblePosition
    Range
}

FrameView {
    ./code/FrameView.cpp
    ./main.class.native.hierarchy.cpp

    一个可视的对象一般都继承于Widget类
        Widget的类型：
            isFrameView
            isPluginView
            isPluginViewBase
            isScrollbar

    view中的常规组件如button, checkbox, scroll bar的绘制一般都是通过各自的theme来实现。
}

WebViewCore(Frame)
    对应Java层的WebViewCore
    和java层之间传递各种事件

    WebKit/android/jni/WebViewCore.h
    ./code/WebViewCore.cpp

