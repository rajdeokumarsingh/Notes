重要事项:
OpenGL ES的坐标系轴的方向和笛卡尔坐标系类似
(注意，屏幕坐标系(skia)的y轴方向和笛卡尔的y轴方向是相反的)

    By default, OpenGL ES assumes a coordinate system where 
        [0,0,0] (X,Y,Z) specifies the center of the GLSurfaceView frame, 
        [1,1,0] is the top right corner of the frame and 
        [-1,-1,0] is bottom left corner of the frame.


GLSurfaceView
    ./android.apis/GLSurfaceView.java
    
    GLSurfaceView.Render
        ./android.apis/GLSurfaceView.Render.java


OpenGL standrad APIs
    ./opengl.apis/basic.concepts.cpp
    ./opengl.apis/index.cpp

todo:
    35P
