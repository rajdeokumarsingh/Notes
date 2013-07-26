接口类,用来实现真正的GL绘图

用户可以实现该类，然后通过下面方法注册render到GLSurfaceView
    GLSurfaceView.setRenderer(GLSurfaceView.Renderer)

线程
    绘制是在一个单独的线程中进行，与主UI线程独立

接口方法：
    abstract void onSurfaceCreated(GL10 gl, EGLConfig config)
        Called when the surface is created or recreated.
        called at the beginning of rendering
        a convenient place to put code to create resources 
        注意, 这里创建的资源会被自动回收

    abstract void onDrawFrame(GL10 gl)
        Called to draw the current frame.
        {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            //... other gl calls to render the scene ...
        }

    abstract void onSurfaceChanged(GL10 gl, int width, int height)
        Called when the surface changed size.
        Typically you will set your viewport here. 
        If your camera is fixed then you could also set your projection matrix here

        void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0, 0, width, height);
            // for a fixed camera, set the projection too
            float ratio = (float) width / height;
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        }

