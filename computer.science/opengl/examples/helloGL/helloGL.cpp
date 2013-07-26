#include <GL/glut.h>

#include <stdlib.h>
#include <stdio.h>

///////////////////////////////////////////////////////////
// Set up the rendering state
void SetupRC(void)
{
    glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
}

///////////////////////////////////////////////////////////
// Called to draw scene
void RenderScene(void)
{
    // Clear the window with current clearing color
    glClear(GL_COLOR_BUFFER_BIT);

    // opengl有多个缓冲区，如颜色，深度，模板和积累
    // 颜色缓冲区就是存储位图的内存区域
    // 帧缓冲区是上述缓冲区的合称

    glColor3f(1.0, 0.0, 0.0);

    glRectf(-0.25, 0.25, 0.25, -0.25);

    // 执行buffer中的命令
    // Flush drawing commands
    glFlush();
}

// w/h是窗口的尺寸
void ChangeSize(int w, int h)
{
    GLfloat aspectRatio;

    // Prevent a divide by zero
    if(h == 0)
        h = 1;

    // Set Viewport to window dimensions
    glViewport(0, 0, w, h);

    // Reset coordinate system
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();

    // Establish clipping volume (left, right, bottom, top, near, far)
    aspectRatio = (GLfloat)w / (GLfloat)h;
    if (w <= h)
        glOrtho (-100.0, 100.0, -100 / aspectRatio, 100.0 / aspectRatio, 1.0, -1.0);
    else
        glOrtho (-100.0 * aspectRatio, 100.0 * aspectRatio, -100.0, 100.0, 1.0, -1.0);

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
}

///////////////////////////////////////////////////////////
// Main program entry point
int main(int argc, char* argv[])
{
    //初始化glut
    glutInit(&argc, argv);

    //告诉我们要创建的是一个什么窗口（
    //  单缓存/双缓冲
    //  RGBA格式/颜色索引
    glutInitDisplayMode(GLUT_SINGLE | GLUT_RGBA);

    //创建opengl窗口，明成为Simple
    glutCreateWindow("Simple");

    //设置显示的回调函数，即调用什么函数显示
    glutDisplayFunc(RenderScene);
    glutReshapeFunc(ChangeSize);
    //初始化我们的窗口
    SetupRC();

    const char * ext = (const char *)glGetString(GL_EXTENSIONS);
    printf("%s\n", ext);

    //用于保证opengl的消息循环
    glutMainLoop();
    return 0;
}

