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

void drawTriangles () {
    glPolygonMode(GL_FRONT_AND_BACK,GL_LINE);
    // glPolygonMode(GL_BACK,GL_FILL);

    glColor3f(1.0f, 0.0f, 0.0f);
    glBegin(GL_TRIANGLES);
    glVertex2f(40.0f, 40.0f);
    glVertex2f(40.0f, -40.0f);
    glVertex2f(-40.0f, -40.0f);
    glEnd();

    glPushMatrix();

    glTranslatef(0.0f, 10.0f, 0.0f);

    glColor3f(0.0f, 1.0f, 0.0f);
    glBegin(GL_TRIANGLES);
    glVertex2f(40.0f, 40.0f);
    glVertex2f(40.0f, -40.0f);
    glVertex2f(-40.0f, -40.0f);
    glEnd();

    glScalef(.5f, .5f, .5f);

    glBegin(GL_TRIANGLES);
    glVertex2f(40.0f, 40.0f);
    glVertex2f(40.0f, -40.0f);
    glVertex2f(-40.0f, -40.0f);
    glEnd();

    glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
    glBegin(GL_TRIANGLES);
    glVertex2f(40.0f, 40.0f);
    glVertex2f(40.0f, -40.0f);
    glVertex2f(-40.0f, -40.0f);
    glEnd();

    glPopMatrix();

    glColor3f(0.0f, 0.0f, 1.0f);
    glPushMatrix();
    glRotatef(30.0f, 0.0f, 0.0f, 1.0f);

    glBegin(GL_TRIANGLES);
    glVertex2f(40.0f, 40.0f);
    glVertex2f(40.0f, -40.0f);
    glVertex2f(-40.0f, -40.0f);
    glEnd();

    glPopMatrix();
}

void drawSpheres() {

    glTranslatef(20.0f, 0.0f, 0.0f);
    glColor3f(1.0f, 0.0f, 0.0f);
    glutSolidSphere(10.0f, 15, 15);

    glTranslatef(00.0f, 20.0f, 0.0f);
    glColor3f(0.0f, 1.0f, 0.0f);
    glutSolidSphere(10.0f, 15, 15);
 
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    glColor3f(1.0f, 1.0f, 0.0f);
    glutSolidSphere(10.0f, 15, 15);
}

///////////////////////////////////////////////////////////
// Called to draw scene
void RenderScene(void)
{
    GLfloat x = 0.0f;
    GLfloat y = 0.0f;
    GLfloat z = 0.0f;
    GLfloat angle = 0.0f;

    // Clear the window with current clearing color
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    drawTriangles();
    // drawSpheres();

    // Flush drawing commands
    glutSwapBuffers();
}


///////////////////////////////////////////////////////////
// Setup the rendering state
void SetupRC(void)
{
    // Set clear color to black
    glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

    // Set paint color to red
    glColor3f(1.0f, 0.0f, 0.0f);
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
    SetupRC();
    glutMainLoop();

    return 0;
}

