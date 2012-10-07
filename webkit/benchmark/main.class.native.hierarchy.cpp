Native层基本对应关系 
static HashSet<Page*>* allPages 1 ---> Page *

createFrame() ---[create]---> {
    基本逻辑关系
        Page 1 <---> Frame *

    Page相关数据结构 {
        Page 1 ---> Page::PageClients

        Page::PageClients 1 {
            ChromeClientAndroid 1 ---> WebFrame
            EditorClientAndroid 1 ---> Page
                getAutofill() ---> WebViewCore
            DeviceMotionClientAndroid 1 --->WebViewCore
            DeviceOrientationClientAndroid 1 --->WebViewCore

            ContextMenuClientAndroid 1 [not implemented]
            DragClientAndroid 1 [not implemented]
            InspectorClientAndroid 1 [not implemented]
        }

        WebFrame 1 ---> {
            BrowserFrame 1 [JNI binding]
            WebBackForwardList 1 [JNI binding]
            AssetManager 1 [JNI binding]
            
            Page 1
            WebHistory [dependent]
        }

        WebViewCore 1 {
            WebViewCore 1 [JNI binding]
            Frame 1
        }
    } [create]

    Frame相关数据结构 {
        * <--- Page 1
            一个Page对应一个main frame和多个子frame

        FrameLoaderClientAndroid 1 ---> { 
            Frame 1 [create alt]
            
            FrameView 1 [create alt]
            WebFrame 1 [create alt]
        } 1
        
        Frame 1 ---> {
            FrameTree 1 [hierarchy struct]
                [用于遍历子节点]

            Page 1 [belong to]

            WebViewCore 1 <---> WebView 1

            FrameLoader 1 [create]
            Document 1
            HTMLFrameOwnerElement 1

            FrameView 1 [create alt]

            RenderView // Root of the render tree for the document contained in this frame.
            RenderPart // Renderer for the element that contains this frame.

            Settings
            Editor 1 [create] // 可实现搜索字符串
            NavigationScheduler 1 [create]
            ScriptController 1 [create]
            DOMWindow
            EventHandler 1 [create]
            SelectionController 1 [create]
            AnimationController 1 [create]
            // also created by WebCore/loader/SubframeLoader.cpp 
            //     SubframeLoader::loadSubframe()
        }

        FrameView 1 ---> {
            Frame
        }

        WebFrameView 1 ---> {
            FrameView
            WebViewCore
        }
    }

    Settings 1
    BackForwardController 1
    ProgressTracker 1

    Chrome 1
    RenderTheme 1
    PluginData 1

    FocusController 1
    SelectionController 1
    DragController 1
    ContextMenuController 1
    InspectorController 1
    GeolocationController 1
    DeviceMotionController 1
    DeviceOrientationController 1
} [create]

Frame 1 ---> {
    Page 1 [belong to]
    FrameTree 1 [hierarchy struct]

    FrameLoader 1 [create]
    Document 1
    HTMLFrameOwnerElement 1

    FrameView 1 [create alt]
    RenderView
    RenderPart

    DOMWindow 1 [create]

    Settings
    Editor 1 [create] // 可实现搜索字符串
    NavigationScheduler 1 [create]
    ScriptController 1 [create]
    EventHandler 1 [create]
    SelectionController 1 [create]
    AnimationController 1 [create]

    // also created by WebCore/loader/SubframeLoader.cpp 
    //     SubframeLoader::loadSubframe()
}

FrameView 1 ---> {
    public ScrollView {
        ScrollView : public Widget, public ScrollableArea

        Widget {
        }

        ScrollableArea {
        }
    }
}
