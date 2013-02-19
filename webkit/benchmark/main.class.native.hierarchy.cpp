Native层基本对应关系 
static HashSet<Page*>* allPages 1 ---> Page *
Page 1 <---> Frame *

主要数据结构创建过程:
WebCoreFrameBridge::createFrame() {
    Page::PageClients [created] {
        ChromeClientAndroid [created] ---> WebFrame
        EditorClientAndroid [created] ---> Page
        DeviceMotionClientAndroid [created] ---> WebViewCore
        DeviceOrientationClientAndroid [created] ---> WebViewCore

        ContextMenuClientAndroid [created, not implemented]
        DragClientAndroid [created, not implemented]
        InspectorClientAndroid [created, not implemented]
    }

    Page [created] -->Page::PageClients

    WebFrame [created] ---> {
        BrowserFrame [JNI binding]
        WebBackForwardList [JNI binding]
        AssetManager [JNI binding]
        
        Page
        RenderSkinAndroid
    }

    FrameLoaderClientAndroid [created] ---> {
        WebFrame
        Frame
    }

    Frame [created, main frame] ---> {
        Page 
        FrameLoaderClientAndroid
        FrameView
    }

    WebViewCore [created] ---> {
        WebViewCore [java, JNI binding]
        Frame
    }

    FrameView [created] ---> {
        Frame 
    }

    WebFrameView [created] {
        FrameView
        WebViewCore
    }

    RenderSkinAndroid [created]
}

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

        FrameLoader 1 [created]
        Document 1
        HTMLFrameOwnerElement 1

        FrameView 1 [create alt]

        RenderView // Root of the render tree for the document contained in this frame.
        RenderPart // Renderer for the element that contains this frame.

        Settings
        Editor 1 [created] // 可实现搜索字符串
        NavigationScheduler 1 [created]
        ScriptController 1 [created]
        DOMWindow
        EventHandler 1 [created]
        SelectionController 1 [created]
        AnimationController 1 [created]
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


Frame 1 ---> {
    Page 1 [belong to]
    FrameTree 1 [hierarchy struct]

    FrameLoader 1 [created]
    Document 1
    HTMLFrameOwnerElement 1

    FrameView 1 [create alt]
    RenderView
    RenderPart

    DOMWindow 1 [created]

    Settings
    Editor 1 [created] // 可实现搜索字符串
    NavigationScheduler 1 [created]
    ScriptController 1 [created]
    EventHandler 1 [created]
    SelectionController 1 [created]
    AnimationController 1 [created]

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

WebViewCore 1 --> {
    WebViewCore.java [JNI binding] // 每个page都有有一个WebViewCore
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

