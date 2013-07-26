    public boolean dispatchKeyEvent(KeyEventevent);
        // 处理Key事件

    public boolean dispatchTouchEvent(MotionEvent event);
        // 处理touch事件

    public boolean dispatchTrackballEvent(MotionEvent event);
        // 处理trackball事件

    public booleandispatchPopulateAccessibilityEvent(AccessibilityEventevent);
        // 处理Accessibility事件（辅助事件，如焦点变化，按钮被点击等），处理完成后返回true。

    public View onCreatePanelView(intfeatureId);
        /* Instantiate the view to display in the panel for 'featureId'.
        You can return null, in which case the default content 
        (typically a menu) will be created for you. */

        // 估计可以创建自定义的menu，类似UCWeb风格的menu

    public boolean onCreatePanelMenu(intfeatureId, Menu menu);
        /* Initialize the contents of the menu for panel 'featureId'.  This is
        called if onCreatePanelView() returns null, giving you a standard
        menu in which you can place your items.  It is only called once for
        the panel, the first time it is shown. */

        // 创建标准的menu, 仅仅调用一次

    public boolean onPreparePanel(intfeatureId, View view, Menu menu);
        /* Prepare a panel to be displayed.  This is called right before the
        panel window is shown, every time it is shown. */

        // 每次显示menu的时候都会被调用

    public boolean onMenuOpened(intfeatureId, Menu menu);
        /* Called when a panel's menu is opened by the user. This may also be
        called when the menu is changing from one type to another (for
                example, from the icon menu to the expanded menu). */

        // 在menu用icon view变成expanded view的时候也会被调用到

    public boolean onMenuItemSelected(intfeatureId, MenuItem item);
        // Called when a panel's menu item has been selected by the user

    public void onWindowAttributesChanged(WindowManager.LayoutParamsattrs);
        // called whenever the current window attributes change.

    public void onContentChanged();
        // called whenever the content view of the screen changes

    public void onWindowFocusChanged(booleanhasFocus);

    public void onAttachedToWindow();
        // Called when the window has been attached to the window manager.

    public void onDetachedFromWindow();
        // Called when the window has been dettached to the window manager.

    public void onPanelClosed(int featureId,Menu menu);
        
    public booleanonSearchRequested(); 
        // Called when the user signals the desire to start a search.

