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

    glEnable(GL_CULL_FACE);
    glEnable(GL_DEPTH_TEST);
    // glPolygonMode(GL_BACK,GL_FILL);

    glPushMatrix();
    glRotatef(xRot, 1.0f, 0.0f, 0.0f);
    glRotatef(yRot, 0.0f, 1.0f, 0.0f);

    glBegin(GL_TRIANGLE_FAN);
    {
        int iPivot = 1;
        glVertex3f(.0f, .0f, 75.0f);
        // for(angle = 2 * GL_PI; angle >= 0.0f; angle -= (GL_PI / 8.0f))
        for(angle = 0.0f; angle <= 2 * GL_PI; angle += (GL_PI / 8.0f))
        {
            x = 50.0f*sin(angle);
            y = 50.0f*cos(angle);

            if(0 == iPivot % 2)
                glColor3f(0.0f, 1.0f, 1.0f);
            else
                glColor3f(1.0f, 0.0f, 1.0f);

            iPivot++;
            glVertex3f(x, y, z);
        }
    }
    glEnd();

    glBegin(GL_TRIANGLE_FAN);
    {
        int iPivot = 1;
        glVertex3f(.0f, .0f, .0f);
        for(angle = 2 * GL_PI; angle >= 0.0f; angle -= (GL_PI / 8.0f))
        // for(angle = 0.0f; angle <= 2 * GL_PI; angle += (GL_PI / 8.0f))
        {
            x = 50.0f*sin(angle);
            y = 50.0f*cos(angle);

            if(0 == iPivot % 2)
                glColor3f(0.0f, 1.0f, 1.0f);
            else
                glColor3f(1.0f, 0.0f, 1.0f);

            iPivot++;
            glVertex3f(x, y, z);
        }
    }
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

    glShadeModel(GL_FLAT);

    // use triangle fan
    glFrontFace(GL_CW);
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

