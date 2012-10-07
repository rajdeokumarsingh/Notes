WebKit/android/jni/WebCoreJniOnLoad.cpp

EXPORT void benchmark(const char* url, int reloadCount, int width, int height)

    ScriptController::initializeThreading(); {
        /*和javascript世界的接口
        完成获取当前的JSDOMWindow, 执行js脚本等等工作
        initializeThreading() 函数创建了线程mutex， 做了一些资源分配的工作*/
    }

    // 返回一个假的虚拟机, 作为benchmark的调用JNI时使用
    InitializeJavaVM();

    // 什么是Timer Client 
        // 定时的回调机制, 设置一个callback和延时，超时后会回调这个timer
    // 什么是Cookie Client
        // 设置和获取cookie; 一个cookie是一个url和string组成的值对
    MyJavaSharedClient client; // 什么都没有干
    JavaSharedClient::SetTimerClient(&client);
    JavaSharedClient::SetCookieClient(&client);

    // FIXME: 为什么叫"client"
        // 从WebCore的角度来看，需要操作系统来实现一些功能,
        // 而这些功能是平台相关的。所以称这些实现者为"client"。
    ChromeClientAndroid* chrome = new ChromeClientAndroid;
    EditorClientAndroid* editor = new EditorClientAndroid;
    DeviceMotionClientAndroid* deviceMotion = new DeviceMotionClientAndroid;
    DeviceOrientationClientAndroid* deviceOrientation = new DeviceOrientationClientAndroid;

    WebCore::Page::PageClients pageClients;

    pageClients.chromeClient = chrome;
    pageClients.editorClient = editor;
    pageClients.deviceMotionClient = deviceMotion;
    pageClients.deviceOrientationClient = deviceOrientation;
    pageClients.contextMenuClient = new ContextMenuClientAndroid;
    pageClients.dragClient = new DragClientAndroid; 
    pageClients.inspectorClient = new InspectorClientAndroid;
    WebCore::Page* page = new WebCore::Page(pageClients);

    editor->setPage(page)

    MyWebFrame* webFrame = new MyWebFrame(page);
    chrome->setWebFrame(webFrame);

    RefPtr<Frame> frame = Frame::create(page, NULL, loader);
    RefPtr<FrameView> frameView = FrameView::create(frame.get());
    frame->setView(frameView);
    FrameLoaderClientAndroid* loader = new FrameLoaderClientAndroid(webFrame);
    loader->setFrame(frame.get());

    WebViewCore* webViewCore = new WebViewCore(JSC::Bindings::getJNIEnv(),
            MY_JOBJECT, frame.get());

    WebFrameView* webFrameView = new WebFrameView(frameView.get(), webViewCore);

    frame->init();
    frame->selection()->setFocused(true);
    frame->page()->focusController()->setFocused(true);

    deviceMotion->setWebViewCore(webViewCore);
    deviceOrientation->setWebViewCore(webViewCore);

    Settings* s = frame->settings();
    s->setLayoutAlgorithm(Settings::kLayoutNormal); 
    ...

    // Finally, load the actual data
    ResourceRequest req(url);
    frame->loader()->load(req, false);

    do {
        // Layout the page and service the timer
        frame->view()->layout();
        while (client.m_hasTimer) {
            client.m_func();
            JavaSharedClient::ServiceFunctionPtrQueue();
        }
        JavaSharedClient::ServiceFunctionPtrQueue();

        // Layout more if needed.
        while (frame->view()->needsLayout())
            frame->view()->layout();
        JavaSharedClient::ServiceFunctionPtrQueue();

        if (reloadCount)
            frame->loader()->reload(true);

    } while (reloadCount--);


    // Draw into an offscreen bitmap
    SkBitmap bmp;
    bmp.setConfig(SkBitmap::kARGB_8888_Config, width, height);
    bmp.allocPixels();
    SkCanvas canvas(bmp);
    PlatformGraphicsContext ctx(&canvas);
    GraphicsContext gc(&ctx);
    frame->view()->paintContents(&gc, IntRect(0, 0, width, height));

    // Write the bitmap to the sdcard
    SkImageEncoder* enc = SkImageEncoder::Create(SkImageEncoder::kPNG_Type);
    enc->encodeFile("/sdcard/webcore_test.png", bmp, 100);
    delete enc;

    // Tear down the world.
    frame->loader()->detachFromParent();
    delete page;



