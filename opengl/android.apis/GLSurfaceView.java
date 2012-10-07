
/* Features
1 Manages a surface, which is a special piece of memory that can be composited into the Android view system.
2 Manages an EGL display, which enables OpenGL to render into a surface.
3 Accepts a user-provided Renderer object that does the actual rendering.
4 Renders on a dedicated thread to decouple rendering performance from the UI thread.
5 Supports both on-demand and continuous rendering.
6 Optionally wraps, traces, and/or error-checks the renderer's OpenGL calls.
*/
