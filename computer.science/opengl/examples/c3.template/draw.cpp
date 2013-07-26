// GLRect.cpp
// Just draw a single rectangle in the middle of the screen
// OpenGL SuperBible, 3rd Edition
// Richard S. Wright Jr.
// rwright@starstonesoftware.com

// #include "../../shared/gltools.h"	// OpenGL toolkit
#include <GL/glut.h>

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

///////////////////////////////////////////////////////////
// Called to draw scene
void RenderScene(void)
{
    GLdouble dRadius = 0.1; // Initial radius of spiral
    GLdouble dAngle;        // Looping variable

    // Clear blue window
    glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
    // Use 0 for clear stencil, enable stencil test
    glClearStencil(0.0f);

    glEnable(GL_STENCIL_TEST);

    // Clear color and stencil buffer
    glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    // FIXME: 可以想象两个缓冲区，颜色缓冲区（蓝色）, 模板缓冲区（黑色）
    // 模板缓冲区位于颜色缓冲区的上层

    // All drawing commands fail the stencil test, and are not
    // drawn, but increment the value in the stencil buffer. 
    glStencilFunc(GL_NEVER, 0x0, 0x0);
    glStencilOp(GL_INCR, GL_INCR, GL_INCR);
    // FIXME: 下面所有的绘制都不会通过模板测试
    // 但是绘制失败后，会将模板缓冲区的对应部分颜色增加1

    // Spiral pattern will create stencil pattern
    // Draw the spiral pattern with white lines. We 
    // make the lines  white to demonstrate that the 
    // stencil function prevents them from being drawn
    glColor3f(1.0f, 1.0f, 1.0f);
    glBegin(GL_LINE_STRIP);
    for(dAngle = 0; dAngle < 400.0; dAngle += 0.1)
    {
        glVertex2d(dRadius * cos(dAngle), dRadius * sin(dAngle));
        dRadius *= 1.002;
    }
    glEnd();
    // FIXME: 上面所有的绘制都不会通过模板测试
    // 但是绘制失败后，会将模板缓冲区的对应部分颜色增加1
    // glEnd()后，颜色缓冲区没有任何变化。
    // 模板缓冲区上有一个大的螺纹圈, 颜色是0x1


    // Now, allow drawing, except where the stencil pattern is 0x1
    // and do not make any further changes to the stencil buffer
    glStencilFunc(GL_NOTEQUAL, 0x1, 0x1);
    glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
    // FIXME: 如果模板缓冲的颜色不等于0x1，就可以通过stencil测试, 进入颜色缓冲区
    // FIXME: 如果模板缓冲的颜色等于0x1，则不通过stencil测试, 无法进入颜色缓冲区
    // 模板缓冲区不会有任何变化

    // Now draw red bouncing square
    glColor3f(1.0f, 0.0f, 0.0f);
    glRectf(0, 0, 40, -40);
    // FIXME: 如果模板缓冲的颜色不等于0x1，就可以通过stencil测试, 进入颜色缓冲区
    // FIXME: 如果模板缓冲的颜色等于0x1，则不通过stencil测试, 无法进入颜色缓冲区
    // 模板缓冲区不会有任何变化

    // 那么对于矩形中不在螺纹圈的部分，直接通过，写入到颜色缓冲。该部分为红色
    // 那么对于矩形中在螺纹圈的部分，由于模板缓冲中对应位的值为0x1, 无法通过测试
    // 所以螺纹圈部分颜色缓冲值不变

    glDisable(GL_STENCIL_TEST);

    // Flush drawing commands
    glutSwapBuffers();
}

///////////////////////////////////////////////////////////
// Called by GLUT library when the window has chanaged size
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
        glOrtho (-100.0, 100.0, -100.0 / aspectRatio, 100.0 / aspectRatio, -100.0, 100.0);
    else 
        glOrtho (-100.0 * aspectRatio, 100.0 * aspectRatio, -100.0, 100.0, -100.0, 100.0);

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
}

void SpecialKeys(int key, int x, int y)
{
    // Refresh the Window
    glutPostRedisplay();
}

///////////////////////////////////////////////////////////
// Main program entry point
int main(int argc, char* argv[])
{
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_STENCIL);
    glutInitWindowSize(800, 600);
    glutCreateWindow("GLRect");
    glutDisplayFunc(RenderScene);
    glutReshapeFunc(ChangeSize);
    glutSpecialFunc(SpecialKeys);
    glutMainLoop();

    return 0;
}

