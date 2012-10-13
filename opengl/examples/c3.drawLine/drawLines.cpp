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

    glPushMatrix();
    glRotatef(xRot, 1.0f, 0.0f, 0.0f);
    glRotatef(yRot, 0.0f, 1.0f, 0.0f);

    // Call only once for all remaining points
    /* glBegin(GL_LINES);
    {
        z = 0.0f;
        for(angle = 0.0f; angle <= GL_PI; angle += (GL_PI / 20.0f))
        {
            // Top half of the circle
            x = 50.0f*sin(angle);
            y = 50.0f*cos(angle);
            glVertex3f(x, y, z);

            // Bottom half of the circle
            x = 50.0f*sin(angle+GL_PI);  // angle + pi
            y = 50.0f*cos(angle+GL_PI);  // angle + pi
            glVertex3f(x, y, z);
        }
    }
    glEnd();
    */

    /* draw line with different width
    GLfloat sizeRange[2];
    GLfloat sizeStep;
    glGetFloatv(GL_LINE_WIDTH_RANGE, sizeRange); // get min and max size
    glGetFloatv(GL_LINE_WIDTH_GRANULARITY, &sizeStep); // get size step
    GLfloat lineWidth = sizeRange[0];

    {
        z = -50.0f;
        GLfloat xPrev = 50.0f * sin(0);
        GLfloat yPrev = 50.0f * cos(0);
        GLfloat zPrev = z;

        GLfloat angleStep = 0.5f;
        GLfloat angleRange = (2.0f*GL_PI)*3.0f;
        GLfloat zStep = 100.0f / (angleRange / angleStep);

        int count = 0;
        for(angle = 0.0f; angle <= angleRange; angle += angleStep) {
            glLineWidth(lineWidth);

            x = 50.0f * sin(angle);
            y = 50.0f * cos(angle);

            glBegin(GL_LINE_STRIP);
                glVertex3f(xPrev, yPrev, zPrev);
                glVertex3f(x, y, z);
            glEnd();

            xPrev = x;
            yPrev = y;
            zPrev = z;

            z += zStep;
            count++;
            lineWidth += 2 * sizeStep;
        }
    }
    */

    {
        glEnable(GL_LINE_STIPPLE);

        GLfloat y;                    // Storeage for varying Y coordinate
        GLint factor = 3;            // Stippling factor
        GLushort pattern = 0x00FF;    // Stipple pattern

        // Step up Y axis 20 units at a time    
        for(y = -90.0f; y < 90.0f; y += 20.0f)
        {
            // Reset the repeat factor and pattern
            glLineStipple(factor,pattern);

            // Draw the line
            glBegin(GL_LINES);
            glVertex2f(-80.0f, y);
            glVertex2f(80.0f, y);    
            glEnd();

            factor++;
        }

        glDisable(GL_LINE_STIPPLE);
    }
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

