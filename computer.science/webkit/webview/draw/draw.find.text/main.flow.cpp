================================================================================
                    WebView.java
================================================================================

// user display the find action bar
public boolean showFindDialog(String text, boolean showIme)

// user input a word to trigger findAll
public int findAll(String find)
    int result = find != null ? nativeFindAll(find.toLowerCase(),
    find.toUpperCase(), find.equalsIgnoreCase(mLastFind)) : 0;
    invalidate();
    mLastFind = find;

// user press next/prev button
public void findNext(boolean forward) {
    nativeFindNext(forward);
}

// Toggle whether the find dialog is showing, for both native and Java.
private void setFindIsUp(boolean isUp) {
    mFindIsUp = isUp;
    nativeSetFindIsUp(isUp);
}

================================================================================
                        |
                        |  JNI to cpp
                        V
================================================================================
                    WebView.cpp

static int nativeFindAll(JNIEnv *env, jobject obj, jstring findLower,
    jstring findUpper, jboolean sameAsLastSearch) {
    int width = root->documentWidth();
    int height = root->documentHeight();
    // Create a FindCanvas, which allows us to fake draw into it so we can
    // figure out where our search string is rendered (and how many times).
    FindCanvas canvas(width, height, (const UChar*) findLowerChars,
            (const UChar*) findUpperChars, length << 1);
    SkBitmap bitmap;
    bitmap.setConfig(SkBitmap::kARGB_8888_Config, width, height);
    canvas.setBitmapDevice(bitmap);
    root->draw(canvas);

    WTF::Vector<MatchInfo>* matches = canvas.detachMatches();

    // With setMatches, the WebView takes ownership of matches
    view->setMatches(matches, sameAsLastSearch);  
    return canvas.found();
}
                |
                | WebView::setMatches()
                V
// With this call, WebView takes ownership of matches, and is responsible for
// deleting it.
void setMatches(WTF::Vector<MatchInfo>* matches, jboolean sameAsLastSearch)
{
    m_findOnPage.setMatches(matches); // XXX: ./FindCanvas.cpp

    if (!checkAgainstOldLocation || oldLocation != m_findOnPage.currentMatchBounds())
        scrollToCurrentMatch();

    viewInvalidate();
}
        |
        | WebView::viewInvalidate()
        V
    void viewInvalidate() {
        env->CallVoidMethod(javaObject.get(), m_javaGlue.m_viewInvalidate);
    }
                |
                | JNI to java, WebView:
                V
    private void viewInvalidate() {
        invalidate();
        // 导致WebView.onDraw()
        // XXX: ./framework.display.flow.java
    }

static void nativeFindNext(JNIEnv *env, jobject obj, bool forward)
    WebView* view = GET_NATIVE_VIEW(env, obj);
    view->findNext(forward);
        |
        | WebView::findNext()
        V
    void findNext(bool forward)
    {
        m_findOnPage.findNext(forward); // ./FindCanvas.cpp
        scrollToCurrentMatch(); // TODO:
        viewInvalidate(); // Invalidate Java WebView
    }

static int nativeFindIndex(JNIEnv *env, jobject obj)
    WebView* view = GET_NATIVE_VIEW(env, obj);
    return view->currentMatchIndex();

static void nativeSetFindIsUp(JNIEnv *env, jobject obj, jboolean isUp)
    WebView* view = GET_NATIVE_VIEW(env, obj);
    view->setFindIsUp(isUp); // TODO:


