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
    // Set clear color to black
    glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT);

    glEnable(GL_SCISSOR_TEST);

    glScissor(100, 100, 600, 400);
    glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
    glClear(GL_COLOR_BUFFER_BIT);

    glScissor(200, 200, 400, 200);
    glClearColor(0.0f, 1.0f, 0.0f, 0.0f);
    glClear(GL_COLOR_BUFFER_BIT);

    // Turn scissor back off for next render
    glDisable(GL_SCISSOR_TEST);

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
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowSize(800, 600);
    glutCreateWindow("GLRect");
    glutDisplayFunc(RenderScene);
    glutReshapeFunc(ChangeSize);
    glutSpecialFunc(SpecialKeys);
    glutMainLoop();

    return 0;
}

