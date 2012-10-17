// Single.cpp
// OpenGL SuperBible
// Richard S. Wright Jr.
// rwright@starstonesoftware.com

#include "../../shared/gltools.h"    // OpenGL toolkit
#include <math.h>
    
///////////////////////////////////////////////////////////
// Called to draw scene
void RenderScene(void)
{
    static GLdouble dRadius = 0.1;
    static GLdouble dAngle = 0.0;

    // Clear blue window
    glClearColor(0.0f, 0.0f, 1.0f, 0.0f);

    if(dAngle == 0.0)
        glClear(GL_COLOR_BUFFER_BIT);

    glPointSize(4);

    GLdouble dRadiusPrev = dRadius;
    GLdouble dAnglePrev = dAngle;

    dRadius *= 1.01;
    dAngle += 0.1;

    /*glBegin(GL_POINTS);
        glVertex2d(dRadiusPrev * cos(dAnglePrev), dRadiusPrev * sin(dAnglePrev));
        glVertex2d(dRadius * cos(dAngle), dRadius * sin(dAngle));
    glEnd();
    */

    glBegin(GL_LINES);
        glVertex2d(dRadiusPrev * cos(dAnglePrev), dRadiusPrev * sin(dAnglePrev));
        glVertex2d(dRadius * cos(dAngle), dRadius * sin(dAngle));
    glEnd();

    /*
    dRadiusPrev = dRadius;
    dAnglePrev = dAngle;

    dRadius *= 1.01;
    dAngle += 0.1;
     */

    if(dAngle > 30.0)
    {
        dRadius = 0.1;
        dAngle = 0.0;
    }

    // glFlush();
    glFinish();
}

///////////////////////////////////////////////////////////
// Trigger a repaint
void Timer(int value)
{
    glutTimerFunc(50,Timer, 0);
    glutPostRedisplay();
}


///////////////////////////////////////////////////////////
// Set viewport and projection
void ChangeSize(int w, int h)
{
    // Prevent a divide by zero
    if(h == 0)
        h = 1;

    // Set Viewport to window dimensions
    glViewport(0, 0, w, h);


    // Set the perspective coordinate system
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();

    // Set 2D Coordinate system
    gluOrtho2D(-4.0, 4.0, -3.0, 3.0);

    // Modelview matrix reset
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
}

void SpecialKeys(int key, int x, int y)
{
    // Refresh the Window
    glutPostRedisplay();
}



///////////////////////////////////////////////////////////
// Program entry point
int main(int argc, char* argv[])
{
    glutInit(&argc, argv);
    // glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
    glutInitDisplayMode(GLUT_RGB);
    glutInitWindowSize(800,600);
    glutCreateWindow("OpenGL Single Buffered");
    glutReshapeFunc(ChangeSize);
    glutDisplayFunc(RenderScene);
    glutSpecialFunc(SpecialKeys);
    glutTimerFunc(50,Timer, 0);
    glutMainLoop();

    return 0;
}
