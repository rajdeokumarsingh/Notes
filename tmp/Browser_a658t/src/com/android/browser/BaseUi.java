/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.browser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.browser.Tab.SecurityState;
import com.android.browser.UrlInputView.StateListener;
import com.android.internal.view.menu.MenuBuilder;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * UI interface definitions
 */
public abstract class BaseUi implements UI {

    private static final String LOGTAG = "BaseUi";

    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS =
        new FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT);

    protected static final FrameLayout.LayoutParams COVER_SCREEN_GRAVITY_CENTER =
        new FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT,
        Gravity.CENTER);

    private static final int MSG_HIDE_TITLEBAR = 1;
    public static final int HIDE_TITLEBAR_DELAY = 1500; // in ms

    Activity mActivity;
    UiController mUiController;
    TabControl mTabControl;
    protected Tab mActiveTab;
    private InputMethodManager mInputManager;

    private Drawable mLockIconSecure;
    private Drawable mLockIconMixed;
    protected Drawable mGenericFavicon;

    protected FrameLayout mContentView;
    protected FrameLayout mCustomViewContainer;
    protected FrameLayout mFullscreenContainer;

    private View mCustomView;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private int mOriginalOrientation;

    private LinearLayout mErrorConsoleContainer = null;

    private UrlBarAutoShowManager mUrlBarAutoShowManager;

    private Toast mStopToast;

    // the default <video> poster
    private Bitmap mDefaultVideoPoster;
    // the video progress view
    private View mVideoProgressView;

    private boolean mActivityPaused;
    protected boolean mUseQuickControls;
    protected TitleBar mTitleBar;
    private NavigationBarBase mNavigationBar;

    private LinearLayout mHomePage;
    private QuickNavigationScreen2 mQuickNavigationScreen;
    private TitleBar mHomePageTitleBar;
    private LinearLayout mVerticalLayout;
    private FrameLayout frameLayout;
    private FrameLayout title_bar;
    private LinearLayout quick_navigation;
    private MainMenu mainMenu;
    private MainMenuControlPhone phone;
    private ArrayList<View> pages = null;
    private LinearLayout viewPaper = null;
    private View left = null;
    private View right = null;
    private ViewPager vPaper = null;
    private ImageView image = null;
    private LayoutInflater inflater = null;
    private ScreenPagerAdapter screenPagerAdapter = null;
    private ScreenItemAdapter itemAdapter = null;
    private LinearLayout home_left = null;
    public BaseUi(Activity browser, UiController controller) {
        mActivity = browser;
        mUiController = controller;
        mTabControl = controller.getTabControl();
        Resources res = mActivity.getResources();
        mInputManager = (InputMethodManager) browser
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        mLockIconSecure = res.getDrawable(R.drawable.ic_secure_holo_dark);
        mLockIconMixed = res
                .getDrawable(R.drawable.ic_secure_partial_holo_dark);
        frameLayout = (FrameLayout) mActivity.getWindow().getDecorView()
                .findViewById(android.R.id.content);
        LayoutInflater.from(mActivity).inflate(R.layout.custom_screen,
                frameLayout);
        mVerticalLayout = (LinearLayout) frameLayout
                .findViewById(R.id.vertical_layout);
        mContentView = (FrameLayout) frameLayout
                .findViewById(R.id.main_content);
        mCustomViewContainer = (FrameLayout) frameLayout
                .findViewById(R.id.fullscreen_custom_content);
        mErrorConsoleContainer = (LinearLayout) frameLayout
                .findViewById(R.id.error_console);
        setFullscreen(BrowserSettings.getInstance().useFullscreen());
        mGenericFavicon = res.getDrawable(R.drawable.app_web_browser_sm);
        mTitleBar = new TitleBar(mActivity, mUiController, this, mContentView);
        mTitleBar.setProgress(100);
        mNavigationBar = mTitleBar.getNavigationBar();
        mUrlBarAutoShowManager = new UrlBarAutoShowManager(this);
        mHomePage = (LinearLayout) frameLayout.findViewById(R.id.home_page);
//        mHomePageTitleBar = new TitleBar(mActivity, mUiController, this,
//                ((FrameLayout) mHomePage.findViewById(R.id.title_bar_layout)));
        mQuickNavigationScreen = new QuickNavigationScreen2(mActivity,
                mUiController, this);
        title_bar =  ((FrameLayout) mHomePage.findViewById(R.id.title_bar_layout));
        quick_navigation = ((LinearLayout) mHomePage.findViewById(R.id.quick_navigation_layout));
        phone = new MainMenuControlPhone(mActivity, this, mUiController,mTabControl);
        mainMenu = (MainMenu)frameLayout.findViewById(R.id.mainMenu);
        phone.setPie(mainMenu);
        phone.populateMenu();
  
        inflater = LayoutInflater.from(mActivity);
        viewPaper = (LinearLayout)inflater.inflate(R.layout.home_screen_viewpager, null);
        vPaper = (ViewPager)viewPaper.findViewById(R.id.vPager);
        if(!BrowserSettings.CMCC_PLATFORM){
            image = (ImageView)viewPaper.findViewById(R.id.cursor);
            left = inflater.inflate(R.layout.home_screen_left, null);
            home_left = (LinearLayout)left.findViewById(R.id.home_left);
            itemAdapter = new ScreenItemAdapter(mActivity, mActivity.getResources().getStringArray(R.array.hot_websites), BaseUi.this, mUiController );
            home_left.addView(new ScreenLeftLineItem(mActivity, R.string.hot_website, itemAdapter));
            itemAdapter = new ScreenItemAdapter(mActivity, mActivity.getResources().getStringArray(R.array.phone_hot_websites), BaseUi.this, mUiController);
            home_left.addView(new ScreenLeftLineItem(mActivity, R.string.phone_hot_website, itemAdapter));
            itemAdapter = new ScreenItemAdapter(mActivity, mActivity.getResources().getStringArray(R.array.life_help), BaseUi.this, mUiController);
            home_left.addView(new ScreenLeftLineItem(mActivity, R.string.life_help_name, itemAdapter));
            itemAdapter = new ScreenItemAdapter(mActivity, mActivity.getResources().getStringArray(R.array.android_home), BaseUi.this, mUiController);
            home_left.addView(new ScreenLeftLineItem(mActivity, R.string.android_website, itemAdapter));
        }

        
        pages = new ArrayList<View>();
        if(!BrowserSettings.CMCC_PLATFORM){
            pages.add(left);
        }
        pages.add(mQuickNavigationScreen);
        screenPagerAdapter = new ScreenPagerAdapter(mActivity, pages);
        vPaper.setAdapter(screenPagerAdapter);
        vPaper.setCurrentItem(1);
        if(!BrowserSettings.CMCC_PLATFORM){
            InitImageView();
            vPaper.setOnPageChangeListener(new MyOnPageChangeListener());
        }
    }

    @Override
    public TabMenu getTabMenu(){
        if(phone != null){
            return phone.getTabMenu();
        }
        return null;
    }
    
    @Override
    public MainMenu getMainMenu(){
        if(mainMenu != null)
            return mainMenu;
        return null;
    }
    
    @Override
    public MainMenuControlPhone getMainMenuControlPhone(){
        return phone;
    }
    
    public void SetSanyaHomepage() {
        mHomePageTitleBar = new TitleBar(mActivity, mUiController, this,
                ((FrameLayout) mHomePage.findViewById(R.id.title_bar_layout)));
        if(mHomePageTitleBar.getParent()!=null){
            title_bar.removeView(mHomePageTitleBar);
        }
        title_bar.addView(mHomePageTitleBar);
        
        if(viewPaper.getParent()!=null){
            quick_navigation.removeView(viewPaper);
        }
        quick_navigation.addView(viewPaper);
    }

    //
    public void qucikNLRequestFoucs(){
        mHomePage.findViewById(R.id.title_bar_layout).setFocusable(true);
        mHomePage.findViewById(R.id.title_bar_layout).setFocusableInTouchMode(true);
        mHomePage.findViewById(R.id.title_bar_layout).requestFocus();
    }

    public void hideHomepageTitleBar() {
        mHomePageTitleBar.getNavigationBar().mUrlInput.setFocusable(false);
        mHomePageTitleBar.getNavigationBar().mUrlInput.setClickable(false);
    }

    public void showHomepageTitleBar() {
        mHomePageTitleBar.getNavigationBar().mUrlInput.setFocusable(true);
        mHomePageTitleBar.getNavigationBar().mUrlInput.setClickable(true);
    }

    public void hideTitle() {
        mHomePage.removeView(mHomePageTitleBar);
    }

    public void SetNormal() {
        mHomePage.setVisibility(View.GONE);
    }

    private void cancelStopToast() {
        if (mStopToast != null) {
            mStopToast.cancel();
            mStopToast = null;
        }
    }

    // lifecycle

    public void onPause() {
        if (isCustomViewShowing()) {
            onHideCustomView();
        }
        cancelStopToast();
        mActivityPaused = true;
    }

    public void onResume() {
        mActivityPaused = false;
        // check if we exited without setting active tab
        // b: 5188145
        final Tab ct = mTabControl.getCurrentTab();
        if (ct != null) {
            setActiveTab(ct);
        }
    }

    protected boolean isActivityPaused() {
        return mActivityPaused;
    }

    public void onConfigurationChanged(Configuration config) {
        if(!BrowserSettings.CMCC_PLATFORM){
            InitImageView();
        }
    }

    public Activity getActivity() {
        return mActivity;
    }

    // key handling

    @Override
    public boolean onBackKey() {
        if (mCustomView != null) {
            mUiController.hideCustomView();
            return true;
        }
        return false;
    }

    @Override
    public boolean onMenuKey() {
        return false;
    }

    // Tab callbacks
    @Override
    public void onTabDataChanged(Tab tab) {
        setUrlTitle(tab);
        setFavicon(tab);
        updateLockIconToLatest(tab);
        updateNavigationState(tab);
        mTitleBar.onTabDataChanged(tab);
        mNavigationBar.onTabDataChanged(tab);
        onProgressChanged(tab);
    }
    
    @Override
    public void setUiTitle(Tab tab) {
        // TODO Auto-generated method stub
        setUrlTitle(tab);
    }
	
    @Override
    public void bookmarkedStatusHasChanged(Tab tab) {
        if (tab.inForeground()) {
            boolean isBookmark = tab.isBookmarkedSite();
            mNavigationBar.setCurrentUrlIsBookmark(isBookmark);
        }
    }

    @Override
    public void onPageStopped(Tab tab) {
        cancelStopToast();
        if (tab.inForeground()) {
            mStopToast = Toast
                    .makeText(mActivity, R.string.stopping, Toast.LENGTH_SHORT);
            mStopToast.show();
        }
    }

    @Override
    public boolean needsRestoreAllTabs() {
        return true;
    }

    @Override
    public void addTab(Tab tab) {
    }

    @Override
    public void setActiveTab(final Tab tab) {
        mHandler.removeMessages(MSG_HIDE_TITLEBAR);
        if ((tab != mActiveTab) && (mActiveTab != null)) {
            removeTabFromContentView(mActiveTab);
            WebView web = mActiveTab.getWebView();
            if (web != null) {
                web.setOnTouchListener(null);
            }
        }
        mActiveTab = tab;
        WebView web = mActiveTab.getWebView();
        updateUrlBarAutoShowManagerTarget();
        attachTabToContentView(tab);
        setShouldShowErrorConsole(tab, mUiController.shouldShowErrorConsole());
        onTabDataChanged(tab);
        onProgressChanged(tab);
        mNavigationBar.setIncognitoMode(tab.isPrivateBrowsingEnabled());
        updateAutoLogin(tab, false);
        if (web != null && web.getVisibleTitleHeight()
                != mTitleBar.getEmbeddedHeight()
                && !mUseQuickControls) {
            showTitleBarForDuration();
        }
    }

    protected void updateUrlBarAutoShowManagerTarget() {
        WebView web = mActiveTab != null ? mActiveTab.getWebView() : null;
        if (!mUseQuickControls && web instanceof BrowserWebView) {
            mUrlBarAutoShowManager.setTarget((BrowserWebView) web);
        } else {
            mUrlBarAutoShowManager.setTarget(null);
        }
    }

    Tab getActiveTab() {
        return mActiveTab;
    }

    @Override
    public void updateTabs(List<Tab> tabs) {
    }

    @Override
    public void removeTab(Tab tab) {
        if (mActiveTab == tab) {
            removeTabFromContentView(tab);
            mActiveTab = null;
        }
    }

    @Override
    public void detachTab(Tab tab) {
        removeTabFromContentView(tab);
    }

    @Override
    public void attachTab(Tab tab) {
        attachTabToContentView(tab);
    }

    protected void attachTabToContentView(Tab tab) {
        if ((tab == null) || (tab.getWebView() == null)) {
            return;
        }
        View container = tab.getViewContainer();
        WebView mainView  = tab.getWebView();
        // Attach the WebView to the container and then attach the
        // container to the content view.
        FrameLayout wrapper =
                (FrameLayout) container.findViewById(R.id.webview_wrapper);
        ViewGroup parent = (ViewGroup) mainView.getParent();
        if (parent != wrapper) {
            if (parent != null) {
                Log.w(LOGTAG, "mMainView already has a parent in"
                        + " attachTabToContentView!");
                parent.removeView(mainView);
            }
            wrapper.addView(mainView);
        } else {
            Log.w(LOGTAG, "mMainView is already attached to wrapper in"
                    + " attachTabToContentView!");
        }
        parent = (ViewGroup) container.getParent();
        if (parent != mContentView) {
            if (parent != null) {
                Log.w(LOGTAG, "mContainer already has a parent in"
                        + " attachTabToContentView!");
                parent.removeView(container);
            }
            mContentView.addView(container, COVER_SCREEN_PARAMS);
        } else {
            Log.w(LOGTAG, "mContainer is already attached to content in"
                    + " attachTabToContentView!");
        }
        mUiController.attachSubWindow(tab);
    }

    private void removeTabFromContentView(Tab tab) {
        hideTitleBar();
        // Remove the container that contains the main WebView.
        WebView mainView = tab.getWebView();
        View container = tab.getViewContainer();
        if (mainView == null) {
            return;
        }
        // Remove the container from the content and then remove the
        // WebView from the container. This will trigger a focus change
        // needed by WebView.
        mainView.setEmbeddedTitleBar(null);
        FrameLayout wrapper =
                (FrameLayout) container.findViewById(R.id.webview_wrapper);
        wrapper.removeView(mainView);
        mContentView.removeView(container);
        mUiController.endActionMode();
        mUiController.removeSubWindow(tab);
        ErrorConsoleView errorConsole = tab.getErrorConsole(false);
        if (errorConsole != null) {
            mErrorConsoleContainer.removeView(errorConsole);
        }
    }

    @Override
    public void onSetWebView(Tab tab, WebView webView) {
        View container = tab.getViewContainer();
        if (container == null) {
            // The tab consists of a container view, which contains the main
            // WebView, as well as any other UI elements associated with the tab.
            container = mActivity.getLayoutInflater().inflate(R.layout.tab,
                    mContentView, false);
            tab.setViewContainer(container);
        }
        if (tab.getWebView() != webView) {
            // Just remove the old one.
            FrameLayout wrapper =
                    (FrameLayout) container.findViewById(R.id.webview_wrapper);
            wrapper.removeView(tab.getWebView());
        }
    }

    /**
     * create a sub window container and webview for the tab
     * Note: this methods operates through side-effects for now
     * it sets both the subView and subViewContainer for the given tab
     * @param tab tab to create the sub window for
     * @param subView webview to be set as a subwindow for the tab
     */
    @Override
    public void createSubWindow(Tab tab, WebView subView) {
        View subViewContainer = mActivity.getLayoutInflater().inflate(
                R.layout.browser_subwindow, null);
        ViewGroup inner = (ViewGroup) subViewContainer
                .findViewById(R.id.inner_container);
        inner.addView(subView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        final ImageButton cancel = (ImageButton) subViewContainer
                .findViewById(R.id.subwindow_close);
        final WebView cancelSubView = subView;
        cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                cancelSubView.getWebChromeClient().onCloseWindow(cancelSubView);
            }
        });
        tab.setSubWebView(subView);
        tab.setSubViewContainer(subViewContainer);
    }

    /**
     * Remove the sub window from the content view.
     */
    @Override
    public void removeSubWindow(View subviewContainer) {
        mContentView.removeView(subviewContainer);
        mUiController.endActionMode();
    }

    /**
     * Attach the sub window to the content view.
     */
    @Override
    public void attachSubWindow(View container) {
        if (container.getParent() != null) {
            // already attached, remove first
            ((ViewGroup) container.getParent()).removeView(container);
        }
        mContentView.addView(container, COVER_SCREEN_PARAMS);
    }

    protected void refreshWebView() {
        WebView web = getWebView();
        if (web != null) {
            web.invalidate();
        }
    }

    public void editUrl(boolean clearInput) {
        if (mUiController.isInCustomActionMode()) {
            mUiController.endActionMode();
        }
        showTitleBar();
        if ((getActiveTab() != null) && !getActiveTab().isSnapshot()) {
            mNavigationBar.startEditingUrl(clearInput);
        }
    }

    boolean canShowTitleBar() {
        return !isTitleBarShowing()
                && !isActivityPaused()
                && (getActiveTab() != null)
                && (getWebView() != null)
                && !mUiController.isInCustomActionMode();
    }

    protected void showTitleBar() {
        mHandler.removeMessages(MSG_HIDE_TITLEBAR);
        if (canShowTitleBar()) {
            mTitleBar.show();
        }
    }

    protected void hideTitleBar() {
        if (mTitleBar.isShowing()) {
            mTitleBar.hide();
        }
    }

    protected boolean isTitleBarShowing() {
        return mTitleBar.isShowing();
    }

    public boolean isEditingUrl() {
        return mTitleBar.isEditingUrl();
    }

    public TitleBar getTitleBar() {
        return mTitleBar;
    }

    protected void setTitleGravity(int gravity) {
        WebView web = getWebView();
        if (web != null) {
            web.setTitleBarGravity(gravity);
        }
    }

    @Override
    public void showVoiceTitleBar(String title, List<String> results) {
        mNavigationBar.setInVoiceMode(true, results);
        mNavigationBar.setDisplayTitle(title);
    }

    @Override
    public void revertVoiceTitleBar(Tab tab) {
        mNavigationBar.setInVoiceMode(false, null);
        String url = tab.getUrl();
        mNavigationBar.setDisplayTitle(url);
    }

    @Override
    public void showComboView(ComboViews startingView, Bundle extras) {
        Intent intent = new Intent(mActivity, ComboViewActivity.class);
        intent.putExtra(ComboViewActivity.EXTRA_INITIAL_VIEW, startingView.name());
        intent.putExtra(ComboViewActivity.EXTRA_COMBO_ARGS, extras);
        Tab t = getActiveTab();
        if (t != null) {
            intent.putExtra(ComboViewActivity.EXTRA_CURRENT_URL, t.getUrl());
        }
        mActivity.startActivityForResult(intent, Controller.COMBO_VIEW);
    }

    @Override
    public void showCustomView(View view, int requestedOrientation,
            WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (mCustomView != null) {
            callback.onCustomViewHidden();
            return;
        }

        mOriginalOrientation = mActivity.getRequestedOrientation();
        FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();
        mFullscreenContainer = new FullscreenHolder(mActivity);
        mFullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(mFullscreenContainer, COVER_SCREEN_PARAMS);
        mCustomView = view;
        setFullscreen(true);
        mCustomViewCallback = callback;
        mActivity.setRequestedOrientation(requestedOrientation);
    }

    @Override
    public void onHideCustomView() {
        if (mCustomView == null)
            return;
        setFullscreen(false);
        FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();
        decor.removeView(mFullscreenContainer);
        mFullscreenContainer = null;
        mCustomView = null;
        mCustomViewCallback.onCustomViewHidden();
        // Show the content view.
        mActivity.setRequestedOrientation(mOriginalOrientation);
    }

    @Override
    public boolean isCustomViewShowing() {
        return mCustomView != null;
    }

    protected void dismissIME() {
        if (mInputManager.isActive()) {
            mInputManager.hideSoftInputFromWindow(mContentView.getWindowToken(),
                    0);
        }
    }

    @Override
    public boolean isWebShowing() {
        return mCustomView == null;
    }

    @Override
    public void showAutoLogin(Tab tab) {
        updateAutoLogin(tab, true);
    }

    @Override
    public void hideAutoLogin(Tab tab) {
        updateAutoLogin(tab, true);
    }

    // -------------------------------------------------------------------------

    protected void updateNavigationState(Tab tab) {
    }

    protected void updateAutoLogin(Tab tab, boolean animate) {
        mTitleBar.updateAutoLogin(tab, animate);
    }

    /**
     * Update the lock icon to correspond to our latest state.
     */
    protected void updateLockIconToLatest(Tab t) {
        if (t != null && t.inForeground()) {
            updateLockIconImage(t.getSecurityState());
        }
    }

    /**
     * Updates the lock-icon image in the title-bar.
     */
    private void updateLockIconImage(SecurityState securityState) {
        Drawable d = null;
        if (securityState == SecurityState.SECURITY_STATE_SECURE) {
            d = mLockIconSecure;
        } else if (securityState == SecurityState.SECURITY_STATE_MIXED
                || securityState == SecurityState.SECURITY_STATE_BAD_CERTIFICATE) {
            // TODO: It would be good to have different icons for insecure vs mixed content.
            // See http://b/5403800
            d = mLockIconMixed;
        }
        mNavigationBar.setLock(d);
    }

    protected void setUrlTitle(Tab tab) {
        if(tab == null) return;

        String url = tab.getUrl();
        String title = tab.getTitle();
//        if (TextUtils.isEmpty(title)) {
//            title = url;
//        }
        if(!TextUtils.isEmpty(title)){
            url = title + " "+url;
        }
        if (tab.isInVoiceSearchMode()) return;
        if (tab.inForeground()) {
            mNavigationBar.setDisplayTitle(url);
        }
    }

    // Set the favicon in the title bar.
    protected void setFavicon(Tab tab) {
        if (tab.inForeground()) {
            Bitmap icon = tab.getFavicon();
            mNavigationBar.setFavicon(icon);
        }
    }

    @Override
    public void onActionModeFinished(boolean inLoad) {
    }

    // active tabs page

    public void showActiveTabsPage() {
    }

    /**
     * Remove the active tabs page.
     */
    public void removeActiveTabsPage() {
    }

    // menu handling callbacks

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void updateMenuState(Tab tab, Menu menu) {
    }

    @Override
    public void onOptionsMenuOpened() {
    }

    @Override
    public void onExtendedMenuOpened() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onOptionsMenuClosed(boolean inLoad) {
    }

    @Override
    public void onExtendedMenuClosed(boolean inLoad) {
    }

    @Override
    public void onContextMenuCreated(Menu menu) {
    }

    @Override
    public void onContextMenuClosed(Menu menu, boolean inLoad) {
    }

    // error console

    @Override
    public void setShouldShowErrorConsole(Tab tab, boolean flag) {
        if (tab == null) return;
        ErrorConsoleView errorConsole = tab.getErrorConsole(true);
        if (flag) {
            // Setting the show state of the console will cause it's the layout
            // to be inflated.
            if (errorConsole.numberOfErrors() > 0) {
                errorConsole.showConsole(ErrorConsoleView.SHOW_MINIMIZED);
            } else {
                errorConsole.showConsole(ErrorConsoleView.SHOW_NONE);
            }
            if (errorConsole.getParent() != null) {
                mErrorConsoleContainer.removeView(errorConsole);
            }
            // Now we can add it to the main view.
            mErrorConsoleContainer.addView(errorConsole,
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            mErrorConsoleContainer.removeView(errorConsole);
        }
    }

    // -------------------------------------------------------------------------
    // Helper function for WebChromeClient
    // -------------------------------------------------------------------------

    @Override
    public Bitmap getDefaultVideoPoster() {
        if (mDefaultVideoPoster == null) {
            mDefaultVideoPoster = BitmapFactory.decodeResource(
                    mActivity.getResources(), R.drawable.default_video_poster);
        }
        return mDefaultVideoPoster;
    }

    @Override
    public View getVideoLoadingProgressView() {
        if (mVideoProgressView == null) {
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            mVideoProgressView = inflater.inflate(
                    R.layout.video_loading_progress, null);
        }
        return mVideoProgressView;
    }

    @Override
    public void showMaxTabsWarning() {
        Toast warning = Toast.makeText(mActivity,
                mActivity.getString(R.string.max_tabs_warning),
                Toast.LENGTH_SHORT);
        warning.show();
    }

    protected WebView getWebView() {
        if (mActiveTab != null) {
            return mActiveTab.getWebView();
        } else {
            return null;
        }
    }

    protected Menu getMenu() {
        MenuBuilder menu = new MenuBuilder(mActivity);
        mActivity.getMenuInflater().inflate(R.menu.browser, menu);
        return menu;
    }

    public void setFullscreen(boolean enabled) {
        Window win = mActivity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (enabled) {
            winParams.flags |=  bits;
        } else {
            winParams.flags &= ~bits;
            if (mCustomView != null) {
                mCustomView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            } else {
                mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
        win.setAttributes(winParams);
    }

    public Drawable getFaviconDrawable(Bitmap icon) {
        Drawable[] array = new Drawable[3];
        array[0] = new PaintDrawable(Color.BLACK);
        PaintDrawable p = new PaintDrawable(Color.WHITE);
        array[1] = p;
        if (icon == null) {
            array[2] = mGenericFavicon;
        } else {
            array[2] = new BitmapDrawable(icon);
        }
        LayerDrawable d = new LayerDrawable(array);
        d.setLayerInset(1, 1, 1, 1, 1);
        d.setLayerInset(2, 2, 2, 2, 2);
        return d;
    }

    public boolean isLoading() {
        return mActiveTab != null ? mActiveTab.inPageLoad() : false;
    }

    /**
     * Suggest to the UI that the title bar can be hidden. The UI will then
     * decide whether or not to hide based off a number of factors, such
     * as if the user is editing the URL bar or if the page is loading
     */
    public void suggestHideTitleBar() {
        if (!isLoading() && !isEditingUrl() && !mTitleBar.wantsToBeVisible()
                && !mNavigationBar.isMenuShowing()) {
            hideTitleBar();
        }
    }

    protected final void showTitleBarForDuration() {
        showTitleBarForDuration(HIDE_TITLEBAR_DELAY);
    }

    protected final void showTitleBarForDuration(long duration) {
        showTitleBar();
        Message msg = Message.obtain(mHandler, MSG_HIDE_TITLEBAR);
        mHandler.sendMessageDelayed(msg, duration);
    }

    protected Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_HIDE_TITLEBAR) {
                suggestHideTitleBar();
            }
            BaseUi.this.handleMessage(msg);
        }
    };

    protected void handleMessage(Message msg) {}

    @Override
    public void showWeb(boolean animate) {
        mUiController.hideCustomView();
    }

    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }

    }
    @Override
    public void Gotourl() {
        // TODO Auto-generated method stub
        
    }

    public void hideHomePage() {
        mHomePage.setVisibility(View.INVISIBLE);
        mVerticalLayout.setVisibility(View.VISIBLE);
    }

    public void showHomePage() {
        mHomePage.setVisibility(View.VISIBLE);
        mVerticalLayout.setVisibility(View.INVISIBLE);
    //    mVerticalLayout.requestLayout();
        mHomePageTitleBar.getNavigationBar().mUrlInput.setText("");
        NavigationBarPhone mNavigationBar = (NavigationBarPhone) mHomePageTitleBar.getNavigationBar();
        mNavigationBar.onStateChanged(StateListener.STATE_NORMAL);
        phone.setBackWardEnabledFalse();
        Tab current = mUiController.getCurrentTab();
        if(current!=null && current.getCount()>0){
            phone.setForwardEnabledTrue();
        }else{
            phone.setForwardEnabledFalse();
        }
    }

    public QuickNavigationScreen2 getQuickNavigationScreen() {
        return mQuickNavigationScreen;
    }
    
    class ScreenPagerAdapter extends android.support.v4.view.PagerAdapter{
        
        private ArrayList<View> views;
        public ScreenPagerAdapter(Context context, ArrayList<View> pages){
            views = pages;
        }
        
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
           
        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(views.get(position));
        }
    
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(views.get(position));
            return views.get(position);
        }
    }
    private int bmpw = 0;
 
    private int currIndex = 1;
    private void InitImageView() {
        
       DisplayMetrics dm = new DisplayMetrics();
       mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
       int screenW = dm.widthPixels;
       bmpw = screenW / 2;
       Bitmap imageDefault = Bitmap.createBitmap(bmpw, 5, Config.RGB_565);
       imageDefault.eraseColor(0xff1E90FF);

       Matrix matrix = new Matrix();
       matrix.postTranslate(bmpw, 0);
       image.setImageBitmap(imageDefault);
       image.setScaleType(ScaleType.MATRIX);
       image.setImageMatrix(matrix);
     }
    
    public class MyOnPageChangeListener implements OnPageChangeListener {
        
        int one = bmpw; 
  
        
        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                  if (currIndex == 1) {
                      animation = new TranslateAnimation(0, -one, 0, 0);
                  } 
                  break;
                case 1:
                  if (currIndex == 0) {
                      animation = new TranslateAnimation(-one, 0, 0, 0);
                  } 
                break;
//                case 2:
//                  if (currIndex == 0) {
//                      animation = new TranslateAnimation(-one, one, 0, 0);
//                  } else if (currIndex == 1) {
//                      animation = new TranslateAnimation(0, one, 0, 0);
//                  }
//                break;
           }
                    currIndex = arg0;
                    animation.setFillAfter(true);// True:图片停在动画结束位置
                    animation.setDuration(300);
                    image.startAnimation(animation);
       }
       
       @Override
       public void onPageScrolled(int arg0, float arg1, int arg2) {
           
       }
       
        @Override
       public void onPageScrollStateChanged(int arg0) {
            
       }
    }
    
}
