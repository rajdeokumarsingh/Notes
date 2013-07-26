================================================================================
实现功能下面功能：
    <p>下面的下拉框中应该默认显示三个选项，但一共有八个选项</p>
    <form action="" method="post">
        <select name="computer" size=3>
            <option>Apples
            <option>Oranges
            <option>Bananas
            <option>Grapes
            <option>Strawberries
        </select>
    </form>

当select element中如果有属性size=3时，将对话框的大小设置成3个
================================================================================
// 4.0流程

相关问题：

WebCore/platform/PopupMenu.h

创建select popup menu的流程
    WebCore/rendering/RenderMenuList.cpp
        | showPopup(): create and show
        |
        V
    WebKit/android/WebCoreSupport/ChromeClientAndroid.cpp
            |
            | create
            V
    WebCore/platform/android/PopupMenuAndroid.cpp
            | request show()
            V
    WebKit/android/jni/WebViewCore.cpp
        void WebViewCore::listBoxRequest(WebCoreReply* reply, const uint16_t** labels, size_t count, const int enabled[], size_t enabledCount,
                bool multiple, const int selected[], size_t selectedCountOrSelection)
                    |
                    | JNI
                    | request show()
                    V
    base/core/java/android/webkit/WebView.java


    // 修改办法：将alert dialog的高度改小
        mListBoxDialog.show();         
        // For CMCC Web test: Browser1.5->TestCase15
        if(mSelectSize >= 2 && mSelectSize <=7) {
           WindowManager.LayoutParams layoutParams = mListBoxDialog.getWindow().getAttributes();
           layoutParams.width = LayoutParams.WRAP_CONTENT;
           layoutParams.height = mSelectSize*108;
           mListBoxDialog.getWindow().setAttributes(layoutParams);
        }

用户选择一个选项后的流程：
    WebKit/android/jni/WebViewCore.cpp
        SendListBoxChoice
        popupReply()
            |
            |  replyInt()
            V
    WebCore/platform/android/PopupMenuAndroid.cpp
            replyInt
                | popupDidHide
                V
            hint: if (m_viewImpl)
                m_viewImpl->contentInvalidate(m_rect);
                |
                V
    WebCore/rendering/RenderMenuList.cpp
        popupDidHide, 680
            m_popupIsVisible = false;

        valueChanged, 383
            SelectElement* select = toSelectElement(static_cast<Element*>(node()));
            select->setSelectedIndexByUser(select->listToOptionIndex(listIndex), true, fireOnChange);

        setTextFromOption, 227
            void RenderMenuList::setText(const String& s)

        if (m_buttonText)
            m_buttonText->destroy();   

        m_buttonText = new (renderArena()) RenderText(document(), s.impl());                                                                             
        m_buttonText->setStyle(style());

        addChild(m_buttonText); {
            createInnerBlock();
            m_innerBlock->addChild(newChild, beforeChild);
        }

        adjustInnerStyle();
        void RenderMenuList::adjustInnerStyle()

================================================================================
如何画select element的:
RenderMenuList : public RenderFlexibleBox
    class RenderFlexibleBox : public RenderBlock

05-12 15:46:24.639  2403  2425 E         : external/webkit/Source/WebCore/dom/SelectElement.cpp, recalcListItems, 375
05-12 15:46:24.639  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, themeForPage, 102
05-12 15:46:24.639  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, themeForPage, 102
05-12 15:46:24.639  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, adjustListboxStyle, 620
05-12 15:46:24.639  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, adjustMenuListButtonStyle, 660
05-12 15:46:24.639  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, adjustMenuListStyleCommon, 606

05-12 15:46:24.719  2403  2425 E         : external/webkit/Source/WebCore/html/HTMLSelectElement.cpp, recalcStyle, 78
05-12 15:46:24.719  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, themeForPage, 102
05-12 15:46:24.719  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, themeForPage, 102
05-12 15:46:24.719  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, adjustListboxStyle, 620
05-12 15:46:24.719  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, adjustMenuListButtonStyle, 660
05-12 15:46:24.719  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, adjustMenuListStyleCommon, 606
05-12 15:46:24.719  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, themeForPage, 102
05-12 15:46:24.719  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, themeForPage, 102
05-12 15:46:24.719  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, adjustListboxStyle, 620
05-12 15:46:24.719  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, adjustMenuListButtonStyle, 660
05-12 15:46:24.719  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, adjustMenuListStyleCommon, 606

05-12 15:46:24.809  2403  2425 E WebViewCore_native: recordPictureSet, 601
05-12 15:46:24.809  2403  2425 E WebViewCore_native: layoutIfNeededRecursive, 535
05-12 15:46:24.809  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, paintTextArea, 577
05-12 15:46:24.809  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, paintCombo, 639
05-12 15:46:24.809  2403  2425 E         : external/webkit/Source/WebCore/platform/android/RenderThemeAndroid.cpp, getCanvasFromInfo, 72
05-12 15:46:24.809  2403  2425 E         : external/webkit/Source/WebCore/html/HTMLSelectElement.cpp, isKeyboardFocusable, 385


