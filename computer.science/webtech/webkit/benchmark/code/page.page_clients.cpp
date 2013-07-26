DeviceMotionClient {
    WebCore/dom/DeviceMotionClient.h

    提供设备的移动数据, 如加速度，旋转加速度
    DeviceMotionData {
        Acceleration
        RotationRate
    }

    DeviceMotionClientAndroid {
        WebCore通过调用下面接口连接到java层的Motion Client, 
        并将一个用于回调的DeviceMotionController设置到上层client。
            m_client = m_webViewCore->deviceMotionAndOrientationManager()->motionClient(); 
            m_client->setController(m_controller); 

        当需要motion数据时
             client()->startUpdating();

        当不需要motion数据时
             client()->stopUpdating();

        获取motion数据
            client()->currentDeviceMotion(); 

        DeviceMotionController {
            用于注册需要motion事件的DOMWindow
        }
    }
}

DeviceOrientationClient{
    WebCore/dom/DeviceOrientationClient.h

    提供方向数据
    DeviceOrientation {
        alpha, beta, gamma 
    }

    DeviceOrientationClientAndroid {
        实现和DeviceMotionClientAndroid几乎完全一样(99%)
    }
}



ChromeClient {
    ./code/ChromeClient.cpp

    和显示相关的功能

    为WebCore提供以下UI相关接口：
        弹出js alert, comfirm, prompt
        页面创建, 关闭，显示，大小，缩放，
        更新窗口，页面内容
        页面和物理屏幕之间的几何坐标转换
        全屏幕模式
        焦点，滚动条，工具条，状态条

    ChromeClientAndroid implements ChromeClient{
        ./code/ChromeClientAndroid.cpp

        基本是下面两个类的功能封装: {
            // TODO:
            WebFrame
            GraphicsLayer
            Frame
            FrameView
            Page
            WebCoreViewBridge (继承于PlatformWidget)
            WebViewCore
        }
    }
}

TextCheckerClient{
    WebCore/platform/text/TextCheckerClient.h
    单词的语法检测相关接口
}
 
EditorClient {
    WebCore/page/EditorClient.h
    编辑相关的接口

    EditorClientAndroid {
        处理物理键盘的输入事件
        处理selection变化

        WebKit/android/WebCoreSupport/EditorClientAndroid.h
        // FIXME deps
        {
            Page
            WebAutofill
        }
    }
}

ContextMenuClient {
    WebCore/page/ContextMenuClient.h

    提供和context menu相关接口
    ContextMenuClientAndroid {
         notImplemented();
    }
}

DragClientAndroid {
    和拖动相关的操作
    notImplemented();
}

InspectorClientAndroid {
    notImplemented();
}

