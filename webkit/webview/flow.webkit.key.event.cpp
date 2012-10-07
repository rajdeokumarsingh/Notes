base/core/java/android/webkit/WebView.java
    public boolean onKeyDown(int keyCode, KeyEvent event) // key events from the framework
        // pass the key to DOM
        mWebViewCore.sendMessage(EventHub.KEY_DOWN, event);

    public boolean onKeyUp(int keyCode, KeyEvent event) 
        // pass the key to DOM
        mWebViewCore.sendMessage(EventHub.KEY_UP, event);
            |
            V
base/core/java/android/webkit/WebViewCore.java
    case KEY_DOWN:
        key((KeyEvent) msg.obj, true); break;

    case KEY_UP:
        key((KeyEvent) msg.obj, false); break;
            |
            v
    private void key(KeyEvent evt, boolean isDown)
        if (!nativeKey(keyCode, unicodeChar, evt.getRepeatCount(), evt.isShiftPressed(),
                    evt.isAltPressed(), evt.isSymPressed(),
                    isDown) && keyCode != KeyEvent.KEYCODE_ENTER) {
            |
            V
    private native boolean nativeKey(int keyCode, int unichar, int repeatCount, 
            boolean isShift, boolean isAlt, boolean isSym, boolean isDown);
                    |
                    | JNI to cpp
                    V
// key board event from java
WebKit/android/jni/WebViewCore.cpp
    static jboolean Key(JNIEnv *env, jobject obj, jint keyCode, jint unichar,
            jint repeatCount, jboolean isShift, jboolean isAlt, jboolean isSym,
            jboolean isDown)
        return GET_NATIVE_VIEW(env, obj)->key(PlatformKeyboardEvent(keyCode,
                    unichar, repeatCount, isDown, isShift, isAlt, isSym));
            |
            v
    bool WebViewCore::key(const PlatformKeyboardEvent& event)
        WebCore::EventHandler* eventHandler;
        WebCore::Node* focusNode = currentFocus();  // send the key event to focus node
        if (focusNode) {  // LIKELY
            WebCore::Frame* frame = focusNode->document()->frame();
            WebFrame* webFrame = WebFrame::getWebFrame(frame);
            eventHandler = frame->eventHandler();
            VisibleSelection old = frame->selection()->selection();
            bool handled = eventHandler->keyEvent(event); 
            if (isContentEditable(focusNode)) {
                // keyEvent will return true even if the contentEditable did not
                // change its selection.  In the case that it does not, we want to
                // return false so that the key will be sent back to our navigation
                // system.
                handled |= frame->selection()->selection() != old;
            }
            return handled;
        } else {
            eventHandler = m_mainFrame->eventHandler();
        }
        return eventHandler->keyEvent(event);


/* scenario: event flow when clicking a WML input and inputting a character
mousemove event:
01-23 17:19:21.045 E/WMLInputElement(  510): defaultEventHandler enter: mouseover
01-23 17:19:21.045 E/WMLInputElement(  510): defaultEventHandler WMLElement::defaultEventHandler
01-23 17:19:21.045 E/WMLInputElement(  510): defaultEventHandler enter: mousemove
... 

focus event:
01-23 17:19:21.365 E/WMLInputElement(  510): dispatchFocusEvent
01-23 17:19:21.365 E/InputElement(  510): dispatchFocusEvent
01-23 17:19:21.365 E/Node    (  510): dispatchFocusEvent, 2803
01-23 17:19:21.365 E/WMLInputElement(  510): defaultEventHandler enter: focus
01-23 17:19:21.365 E/WMLInputElement(  510): defaultEventHandler WMLElement::defaultEventHandler
01-23 17:19:21.365 E/WMLInputElement(  510): defaultEventHandler enter: focusin
01-23 17:19:21.365 E/WMLInputElement(  510): defaultEventHandler WMLElement::defaultEventHandler
01-23 17:19:21.365 E/WMLInputElement(  510): defaultEventHandler enter: DOMFocusIn
01-23 17:19:21.365 E/WMLInputElement(  510): defaultEventHandler WMLElement::defaultEventHandler

01-23 17:19:21.365 E/WMLInputElement(  510): suggestedValue

01-23 17:19:21.395 E/RenderTextControlSingleLine(  510): cacheSelection, 720
01-23 17:19:21.395 E/WMLInputElement(  510): cacheSelection

01-23 17:19:21.405 E/WMLInputElement(  510): defaultEventHandler enter: mouseup
01-23 17:19:21.415 E/WMLInputElement(  510): defaultEventHandler WMLElement::defaultEventHandler
01-23 17:19:21.425 E/WMLInputElement(  510): defaultEventHandler enter: DOMActivate
01-23 17:19:21.425 E/WMLInputElement(  510): defaultEventHandler WMLElement::defaultEventHandler

01-23 17:19:21.425 E/WMLInputElement(  510): defaultEventHandler enter: click
01-23 17:19:21.425 E/WMLInputElement(  510): defaultEventHandler WMLElement::defaultEventHandler
01-23 17:19:21.425 E/WMLInputElement(  510): defaultEventHandler enter: DOMActivate
01-23 17:19:21.425 E/WMLInputElement(  510): defaultEventHandler WMLElement::defaultEventHandler


01-23 17:19:22.445 E/RenderTextControlSingleLine(  510): cacheSelection, 720
01-23 17:19:22.445 E/WMLInputElement(  510): cacheSelection

pressed a key to input a character 't':
01-23 17:19:22.445 E/WMLInputElement(  510): defaultEventHandler enter: keydown
01-23 17:19:22.445 E/WMLInputElement(  510): defaultEventHandler WMLElement::defaultEventHandler
01-23 17:19:22.455 E/WMLInputElement(  510): defaultEventHandler enter: keypress
01-23 17:19:22.455 E/WMLInputElement(  510): defaultEventHandler WMLElement::defaultEventHandler
01-23 17:19:22.455 E/WMLInputElement(  510): defaultEventHandler enter: textInput
01-23 17:19:22.465 E/WMLInputElement(  510): defaultEventHandler, input event
01-23 17:19:22.465 E/WMLInputElement(  510): isConformedToInputMask
01-23 17:19:22.475 E/WMLInputElement(  510): cursorPositionToMaskIndex
01-23 17:19:22.475 E/WMLInputElement(  510): defaultEventHandler WMLElement::defaultEventHandler

01-23 17:19:22.475 E/Node    (  510): defaultEventHandler, 2850
01-23 17:19:22.475 E/Node    (  510): defaultEventHandler, 2853
01-23 17:19:22.475 E/EventHandler(  510): defaultTextInputEventHandler, 2908
01-23 17:19:22.475 E/Editor  (  510): handleTextEvent, 163
01-23 17:19:22.475 E/Editor  (  510): handleTextEvent, 170
01-23 17:19:22.475 E/Editor  (  510): handleTextEvent, 187
01-23 17:19:22.475 E/Editor  (  510): insertTextWithoutSendingTextEvent, 1099
01-23 17:19:22.475 E/Editor  (  510): insertTextWithoutSendingTextEvent, 1103
01-23 17:19:22.485 E/Editor  (  510): insertTextWithoutSendingTextEvent, 1110
01-23 17:19:22.485 E/Editor  (  510): insertTextWithoutSendingTextEvent, 1117
01-23 17:19:22.485 E/Editor  (  510): insertTextWithoutSendingTextEvent, 1125
01-23 17:19:22.485 E/Editor  (  510): insertTextWithoutSendingTextEvent, 1128
01-23 17:19:22.485 E/Editor  (  510): insertTextWithoutSendingTextEvent, 1137
01-23 17:19:22.485 E/TypingCommand(  510): insertText, 173
01-23 17:19:22.495 E/TypingCommand(  510): insertText, 184
01-23 17:19:22.495 E/TypingCommand(  510): insertText, 187
01-23 17:19:22.495 E/WMLInputElement(  510): defaultEventHandler enter: webkitBeforeTextInserted
01-23 17:19:22.495 E/WMLInputElement(  510): defaultEventHandler WMLElement::defaultEventHandler
01-23 17:19:22.495 E/InputElement(  510): handleBeforeTextInsertedEvent
01-23 17:19:22.515 E/InputElement(  510): handleBeforeTextInsertedEvent, textEvent: t
01-23 17:19:22.515 E/InputElement(  510): sanitizeUserInputValue: t
01-23 17:19:22.515 E/InputElement(  510): replaceEOLAndLimitLength: t
01-23 17:19:22.515 E/EditCommand(  510): apply, 66
01-23 17:19:22.515 E/TypingCommand(  510): doApply, 296

01-23 17:19:22.525 E/TypingCommand(  510): doApply, 325
01-23 17:19:22.535 E/EditCommand(  510): apply, 66
01-23 17:19:22.535 E/EditCommand(  510): apply, 103
01-23 17:19:22.535 E/EditCommand(  510): apply, 66
01-23 17:19:22.535 E/EditCommand(  510): apply, 103
01-23 17:19:22.555 E/EditCommand(  510): apply, 66
01-23 17:19:22.555 E/EditCommand(  510): apply, 103
01-23 17:19:22.935 E/TypingCommand(  510): typingAddedToOpenCommand, 381
01-23 17:19:22.935 E/Editor  (  510): appliedEditing, 1005

FIXME: CORE activity!
01-23 17:19:22.935 E/WMLInputElement(  510): defaultEventHandler enter: webkitEditableContentChanged
01-23 17:19:22.935 E/RenderTextControlSingleLine(  510): subtreeHasChanged, 190
01-23 17:19:22.935 E/WMLInputElement(  510): wasChangedSinceLastFormControlChangeEvent: 0
01-23 17:19:22.935 E/WMLInputElement(  510): setChangedSinceLastFormControlChangeEvent
01-23 17:19:22.944 E/RenderTextControlSingleLine(  510): subtreeHasChanged, 205
01-23 17:19:22.944 E/InputElement(  510): sanitizeUserInputValue: t
01-23 17:19:22.944 E/InputElement(  510): replaceEOLAndLimitLength: t
01-23 17:19:22.944 E/WMLInputElement(  510): setValueFromRenderer
01-23 17:19:22.944 E/InputElement(  510): setValueFromRenderer: t
01-23 17:19:22.944 E/InputElement(  510): notifyFormStateChanged

01-23 17:19:22.944 E/Node    (  510): defaultEventHandler, 2887
01-23 17:19:22.944 E/Node    (  510): dispatchInputEvent, 2821
01-23 17:19:22.944 E/WMLInputElement(  510): defaultEventHandler enter: input
01-23 17:19:22.944 E/WMLInputElement(  510): defaultEventHandler WMLElement::defaultEventHandler
01-23 17:19:22.955 E/Editor  (  510): dispatchEditableContentChangedEvents, 993
01-23 17:19:22.955 E/RenderTextControlSingleLine(  510): cacheSelection, 720
01-23 17:19:22.955 E/WMLInputElement(  510): cacheSelection
01-23 17:19:22.955 E/EditCommand(  510): apply, 103
01-23 17:19:22.964 E/EditCommand(  510): apply, 105
01-23 17:19:22.974 E/WMLInputElement(  510): defaultEventHandler enter: keyup
01-23 17:19:22.974 E/WMLInputElement(  510): defaultEventHandler WMLElement::defaultEventHandler
01-23 17:19:22.985 I/CacheBuilder(  510): BuildFrame, 1288
01-23 17:19:22.995 E/WMLInputElement(  510): value
01-23 17:19:22.995 E/WMLInputElement(  510): formControlName
01-23 17:19:22.995 E/InputElementData(  510): name
01-23 17:19:22.995 I/CacheBuilder(  510): BuildFrame, 1302
*/

// FIXME: send the input from UI to input element
void RenderTextControlSingleLine::subtreeHasChanged()
    RenderTextControl::subtreeHasChanged();

    ASSERT(node()->isElementNode());
    Element* element = static_cast<Element*>(node());
    bool wasChanged = element->wasChangedSinceLastFormControlChangeEvent();
    element->setChangedSinceLastFormControlChangeEvent(true);

    InputElement* input = inputElement();
    // We don't need to call sanitizeUserInputValue() function here because
    // InputElement::handleBeforeTextInsertedEvent() has already called
    // sanitizeUserInputValue().
    // sanitizeValue() is needed because IME input doesn't dispatch BeforeTextInsertedEvent.
    String value = text();
    if (input->isAcceptableValue(value))
        input->setValueFromRenderer(input->sanitizeValue(input->convertFromVisibleValue(value)));


ebCore/dom/InputElement.cpp|117| <<global>> void InputElement::setValueFromRenderer(InputElementData& data, InputElement* inputElement, Element* element, const String& value)
WebCore/dom/InputElement.h|69| <<global>> virtual void setValueFromRenderer(const String&) = 0;
WebCore/dom/InputElement.h|93| <<global>> static void setValueFromRenderer(InputElementData&, InputElement*, Element*, const String&);
WebCore/html/HTMLInputElement.cpp|979| <<global>> void HTMLInputElement::setValueFromRenderer(const String& value)
WebCore/html/HTMLInputElement.cpp|985| <<global>> InputElement::setValueFromRenderer(m_data, this, this, value);
WebCore/html/HTMLInputElement.h|142| <<global>> virtual void setValueFromRenderer(const String&);
WebCore/rendering/RenderTextControlSingleLine.cpp|198| <<global>> input->setValueFromRenderer(input->sanitizeValue(input->convertFromVisibleValue(value)));            
WebCore/wml/WMLInputElement.cpp|172| <<global>> void WMLInputElement::setValueFromRenderer(const String& value)
WebCore/html/shadow/SliderThumbElement.cpp|146| <<setPositionFromPoint>> input->setValueFromRenderer(serializeForNumberType(value));


WebCore/editing/EditCommand.cpp|100| <<global>> frame->editor()->appliedEditing(this);
WebCore/editing/Editor.cpp|988| <<global>> void Editor::appliedEditing(PassRefPtr<EditCommand> cmd)                                                                    
WebCore/editing/Editor.h|169| <<global>> void appliedEditing(PassRefPtr<EditCommand>);
WebCore/editing/TypingCommand.cpp|360| <<global>> document()->frame()->editor()->appliedEditing(this);
WebCore/editing/TypingCommand.cpp|367| <<global>> document()->frame()->editor()->appliedEditing(this);


WebCore/dom/Node.cpp|2873| <<global>> } else if (event->type() == eventNames().webkitEditableContentChangedEvent) {                                                    
WebCore/editing/Editor.cpp|983| <<global>> startRoot->dispatchEvent(Event::create(eventNames().webkitEditableContentChangedEvent, false, false), ec);
WebCore/editing/Editor.cpp|985| <<global>> endRoot->dispatchEvent(Event::create(eventNames().webkitEditableContentChangedEvent, false, false), ec);
WebCore/html/HTMLFormControlElement.cpp|563| <<global>> if (event->type() == eventNames().webkitEditableContentChangedEvent && renderer() && renderer()->isTextControl()) {
WebCore/html/shadow/TextControlInnerElements.cpp|114| <<global>> if (event->isBeforeTextInsertedEvent() || event->type() == eventNames().webkitEditableContentChangedEvent) {


WebCore/html/HTMLFormControlElement.cpp|564| <<global>> toRenderTextControl(renderer())->subtreeHasChanged();                                                          
WebCore/rendering/RenderTextControl.cpp|361| <<global>> void RenderTextControl::subtreeHasChanged()
WebCore/rendering/RenderTextControl.h|46| <<global>> virtual void subtreeHasChanged();
WebCore/rendering/RenderTextControlMultiLine.cpp|48| <<global>> void RenderTextControlMultiLine::subtreeHasChanged()
WebCore/rendering/RenderTextControlMultiLine.cpp|50| <<global>> RenderTextControl::subtreeHasChanged();
WebCore/rendering/RenderTextControlMultiLine.h|39| <<global>> virtual void subtreeHasChanged();
WebCore/rendering/RenderTextControlSingleLine.cpp|182| <<global>> void RenderTextControlSingleLine::subtreeHasChanged()
WebCore/rendering/RenderTextControlSingleLine.cpp|184| <<global>> RenderTextControl::subtreeHasChanged();
WebCore/rendering/RenderTextControlSingleLine.h|68| <<global>> virtual void subtreeHasChanged();

