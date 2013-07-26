
// 网页中输入框的光标是如何显示的:

WebCore/rendering/RenderTextControlSingleLine.cpp
WebCore/dom/InputElement.cpp
WebCore/html/HTMLInputElement.cpp
WebCore/html/TextFieldInputType.cpp

// 输入法出来的流程

WebKit/android/jni/WebViewCore.cpp

// Common code for both clicking with the trackball and touchUp
// Also used when typing into a non-focused textfield to give the textfield focus,
// in which case, 'fake' is set to true
bool WebViewCore::handleMouseClick(WebCore::Frame* framePtr, WebCore::Node* nodePtr, bool fake)
    // If the user clicked on a textfield, make the focusController active
    // so we show the blinking cursor.
    WebCore::Node* focusNode = currentFocus();
    if (focusNode) {
        readonly = (static_cast<WebCore::HTMLInputElement*>(focusNode))->readOnly();

        WebCore::RenderObject* renderer = focusNode->renderer();
        if (renderer && (renderer->isTextField() || renderer->isTextArea())) {

            if (!fake) {
                RenderTextControl* rtc
                    = static_cast<RenderTextControl*> (renderer);
                // Force an update of the navcache as this will fire off a
                // message to WebView that *must* have an updated focus.
                m_frameCacheOutOfDate = true;
                updateFrameCache();
                requestKeyboardWithSelection(focusNode, rtc->selectionStart(),
                        rtc->selectionEnd());
            }

    }




void WebViewCore::requestKeyboardWithSelection(const WebCore::Node* node,
        int selStart, int selEnd)
    env->CallVoidMethod(javaObject.get(),
            m_javaGlue->m_requestKeyboardWithSelection,
            reinterpret_cast<int>(node), selStart, selEnd, m_textGeneration);
                |
                | JNI to Java
                V
// called by JNI
base/core/java/android/webkit/WebViewCore.java
private void requestKeyboardWithSelection(int pointer, int selStart,
        int selEnd, int textGeneration) {
    if (mWebView != null) {
        Message.obtain(mWebView.mPrivateHandler,
                WebView.REQUEST_KEYBOARD_WITH_SELECTION_MSG_ID, pointer,
                textGeneration, new TextSelectionData(selStart, selEnd))
            .sendToTarget();
    }
}
    |
    |
    V
base/core/java/android/webkit/WebView.java
private void displaySoftKeyboard(boolean isTextView)
    InputMethodManager imm = (InputMethodManager)
        getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

    // bring it back to the default level scale so that user can enter text
    boolean zoom = mZoomManager.getScale() < mZoomManager.getDefaultScale();
    if (zoom) {
        mZoomManager.setZoomCenter(mLastTouchX, mLastTouchY);
        mZoomManager.setZoomScale(mZoomManager.getDefaultScale(), false);
    }

    if (isTextView) {
        rebuildWebTextView();
        if (inEditingMode()) {
            imm.showSoftInput(mWebTextView, 0, mWebTextView.getResultReceiver());
            if (zoom) {
                didUpdateWebTextViewDimensions(INTERSECTS_SCREEN);
            }
            return;
        }
    }



