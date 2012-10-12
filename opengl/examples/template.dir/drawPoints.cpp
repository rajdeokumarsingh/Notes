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

static const float GL_PI = 3.1415926f;

// Rotation amounts
static GLfloat xRot = 0.0f;
static GLfloat yRot = 0.0f;

///////////////////////////////////////////////////////////
// Called to draw scene
void RenderScene(void)
{
    GLfloat x = 0.0f;
    GLfloat y = 0.0f;
    GLfloat z = 0.0f;
    GLfloat angle = 0.0f;


    // Clear the window with current clearing color
    glClear(GL_COLOR_BUFFER_BIT);

    // Set paint color to green
    glColor3f(0.0f, 1.0f, 0.0f);

    // glPushMatrix();
    // glRotatef(xRot, 1.0f, 0.0f, 0.0f);
    // glRotatef(yRot, 0.0f, 1.0f, 0.0f);

    glBegin(GL_POINTS);
        z = -50.0f;
        for(angle = 0.0f; angle <= (2.0f*GL_PI)*3.0f; angle += 0.1f) {
            x = 50.0f * sin(angle);
            y = 50.0f * cos(angle);

            glVertex3f(x, y, 0.0f);
            z += 0.5f;
        }
    glEnd();

    // Flush drawing commands
    glutSwapBuffers();
}


///////////////////////////////////////////////////////////
// Setup the rendering state
void SetupRC(void)
{
    // Set clear color to black
    glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

    // Set paint color to green
    glColor3f(0.0f, 1.0f, 0.0f);
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
        glOrtho (-100.0, 100.0, -100 / aspectRatio, 100.0 / aspectRatio, 1.0, -1.0);
    else 
        glOrtho (-100.0 * aspectRatio, 100.0 * aspectRatio, -100.0, 100.0, 1.0, -1.0);

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
    //    glutInitWindowSize(800, 600);
    glutCreateWindow("GLRect");
    glutDisplayFunc(RenderScene);
    glutReshapeFunc(ChangeSize);
    glutSpecialFunc(SpecialKeys);
    SetupRC();
    glutMainLoop();

    return 0;
}


