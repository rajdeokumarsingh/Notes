SurfaceView
    android.view.SurfaceView
        派生类
            GLSurfaceView, RSSurfaceView, VideoView
    
    提供专门的画图的surface
    使用者可以指定surface的格式，尺寸

    该surface有z order, 而且是放在SurfaceView的容器Window的后面
    SurfaceView通过一个后门让容器Window将它显示出来

    通过SurfaceHolder接口来访问底层surface
        public SurfaceView.SurfaceHolder getHolder ()


    当容器Window可见时， SurfaceView被创建
    当容器Window不可见时， SurfaceView被销毁
        surfaceCreated(SurfaceHolder) and surfaceDestroyed(SurfaceHolder)

    线程注意事项:
        所有的SurfaceView和SurfaceView.Callback方法都会在主线程中被调用 
            由于SurfaceView的画图线程是一个单独的非主线程，需要进行线程同步

        必须保证drawing thread仅仅在下面其间访问Surface
            [ SurfaceHolder.Callback.surfaceCreated() ~ ~ ~
              SurfaceHolder.Callback.surfaceDestroyed()]

