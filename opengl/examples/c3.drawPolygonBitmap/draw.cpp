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

#define GL_PI 3.1415f

// Rotation amounts
static GLfloat xRot = 0.0f;
static GLfloat yRot = 0.0f;

GLubyte fire[128] = { 0x00, 0x00, 0x00, 0x00, 
    0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0xc0,
    0x00, 0x00, 0x01, 0xf0,
    0x00, 0x00, 0x07, 0xf0,
    0x0f, 0x00, 0x1f, 0xe0,
    0x1f, 0x80, 0x1f, 0xc0,
    0x0f, 0xc0, 0x3f, 0x80,
    0x07, 0xe0, 0x7e, 0x00,
    0x03, 0xf0, 0xff, 0x80,
    0x03, 0xf5, 0xff, 0xe0,
    0x07, 0xfd, 0xff, 0xf8,
    0x1f, 0xfc, 0xff, 0xe8,
    0xff, 0xe3, 0xbf, 0x70, 
    0xde, 0x80, 0xb7, 0x00,
    0x71, 0x10, 0x4a, 0x80,
    0x03, 0x10, 0x4e, 0x40,
    0x02, 0x88, 0x8c, 0x20,
    0x05, 0x05, 0x04, 0x40,
    0x02, 0x82, 0x14, 0x40,
    0x02, 0x40, 0x10, 0x80, 
    0x02, 0x64, 0x1a, 0x80,
    0x00, 0x92, 0x29, 0x00,
    0x00, 0xb0, 0x48, 0x00,
    0x00, 0xc8, 0x90, 0x00,
    0x00, 0x85, 0x10, 0x00,
    0x00, 0x03, 0x00, 0x00,
    0x00, 0x00, 0x10, 0x00 };


///////////////////////////////////////////////////////////
// Called to draw scene
void RenderScene(void)
{
    // Clear the window with current clearing color
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    glPushMatrix();
    glRotatef(xRot, 1.0f, 0.0f, 0.0f);
    glRotatef(yRot, 0.0f, 1.0f, 0.0f);

    // Begin the stop sign shape,
    // use a standard polygon for simplicity
    glBegin(GL_POLYGON);
        glVertex2f(-20.0f, 50.0f);
        glVertex2f(20.0f, 50.0f);
        glVertex2f(50.0f, 20.0f);
        glVertex2f(50.0f, -20.0f);
        glVertex2f(20.0f, -50.0f);
        glVertex2f(-20.0f, -50.0f);
        glVertex2f(-50.0f, -20.0f);
        glVertex2f(-50.0f, 20.0f);
    glEnd();

    glPopMatrix();

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

    // Enable polygon stippling
    glEnable(GL_POLYGON_STIPPLE);

    // Specify a specific stipple pattern
    glPolygonStipple(fire);
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
    if(key == GLUT_KEY_UP)
        xRot-= 5.0f;

    if(key == GLUT_KEY_DOWN)
        xRot += 5.0f;

    if(key == GLUT_KEY_LEFT)
        yRot -= 5.0f;

    if(key == GLUT_KEY_RIGHT)
        yRot += 5.0f;

    if(key > 356.0f)
        xRot = 0.0f;

    if(key < -1.0f)
        xRot = 355.0f;

    if(key > 356.0f)
        yRot = 0.0f;

    if(key < -1.0f)
        yRot = 355.0f;

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

