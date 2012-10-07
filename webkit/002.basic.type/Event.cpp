EventTarget

EventDispatcher
    通过dispatcher去分发事件

Event                                                          
    WebCore/dom/Event.h

    所有事件的基类, 保存了事件的类型，阶段, 创建时间等等信息
        子类有
            UIEvent
            MouseRelatedEvent TouchEvent MouseEvent WheelEvent
            KeyboardEvent DeviceOrientationEvent DeviceMotionEvent 
            ClipboardEvent

            // FIXME: What is
            TextEvent
            MessageEvent
            MutationEvent
            CustomEvent

            dom:
            BeforeTextInsertedEvent
            BeforeLoadEvent
            BeforeUnloadEvent
            BeforeProcessEvent

            WebKitTransitionEvent
            WebKitAnimationEvent
            ErrorEvent
            CompositionEvent
            PageTransitionEvent
            ProgressEvent
            HashChangeEvent
            OverflowEvent
            PopStateEvent

    分类：
    UI交互事件类型
        如鼠标事件，键盘事件，touch事件

    Dom事件类型
        focus event, focus in/out event

    // 事件获取的阶段
    enum PhaseType {
        CAPTURING_PHASE     = 1,
        AT_TARGET           = 2,
        BUBBLING_PHASE      = 3
    };

    // 事件类型
    enum EventType {
        MOUSEDOWN           = 1,
        MOUSEUP             = 2,
        MOUSEOVER           = 4,
        MOUSEOUT            = 8,
        MOUSEMOVE           = 16,
        MOUSEDRAG           = 32,
        CLICK               = 64,
        DBLCLICK            = 128,
        KEYDOWN             = 256,
        KEYUP               = 512,
        KEYPRESS            = 1024,
        DRAGDROP            = 2048,
        FOCUS               = 4096,
        BLUR                = 8192,
        SELECT              = 16384,
        CHANGE              = 32768
    };

