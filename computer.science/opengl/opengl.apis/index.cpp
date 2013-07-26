{
    // 设置清除屏幕时候的颜色
    glClearColor(0.0, 0.0, 0.0, 0.0);
    // 使用glClearColor设置的颜色来清除屏幕
    glClear(GL_COLOR_BUFFER_BIT);

    // 清除颜色缓冲区和深度缓冲区
    glClearDepth(1.0);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    颜色缓冲区 GL_COLOR_BUFFER_BIT
    深度缓冲区 GL_DEPTH_BUFFER_BIT
    积累缓冲区 GL_ACCUM_BUFFER_BIT
    模板缓冲区 GL_STENCIL_BUFFER_BIT
}

{
    // 设置画笔的颜色
    glColor3f(1.0, 1.0, 1.0);

    // 使用画笔来画多边形
    glBegin(GL_POLYGON);  // 画多边形

    glPointSize(5.0);   // 设置点的大小
    glBegin(GL_POINTS);  // 画点

        glVertex3f(0.25, 0.25, 0.0); // 指定顶点
        glVertex3f(0.75, 0.25, 0.0);
        glVertex3f(0.75, 0.75, 0.0);
        glVertex3f(0.25, 0.75, 0.0);
    glEnd();

    glVertex[234][sifd](...) // 4维还包括一个w


    // 强制完成绘图操作
    glFlush();
}

{
    // 处理键盘事件
    void glutKeyboardFunc(void (*)(unsigned char key, int x, int y))
    // 处理鼠标事件
    void glutMouseFunc(void (*)(int button, int state, int x, int y))
    // 处理鼠标滑动事件
    void glutMotionFunc(void (*)(int x, int y))
}

{
    // 开启/关闭某项功能
    void glEnable(int capability);
    void glDisable(int capability);
        GL_BLEND
        GL_FOG
        GL_LINE_STIPPLE
        GL_LIGHTING

    bool glIsEnabled(int capability)
}
