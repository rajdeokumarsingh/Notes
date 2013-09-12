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

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


/**
 * Base class for a title bar used by the browser.
 */
public class QuickNavigationTitleBar extends RelativeLayout {

    private static final int PROGRESS_MAX = 100;
    private static final float ANIM_TITLEBAR_DECELERATE = 2.5f;

    private UiController mUiController;
    private BaseUi mBaseUi;
    private PageProgressView mProgress;

    private AutologinBar mAutoLogin;
    private NavigationBarBase mNavBar;
    private boolean mUseQuickControls;
    private SnapshotBar mSnapshotBar;
    private ImageButton mSwitcher;


    public QuickNavigationTitleBar(Context context, UiController controller, BaseUi ui,
            FrameLayout parent) {
        super(context, null);
        mUiController = controller;
        mBaseUi = ui;
        initLayout(context);
    }

    public BaseUi getUi() {
        return mBaseUi;
    }

    public UiController getUiController() {
        return mUiController;
    }

    public void setUseQuickControls(boolean use) {
        mUseQuickControls = use;
        setLayoutParams(makeLayoutParams());
    }

    private void initLayout(Context context) {
        LayoutInflater factory = LayoutInflater.from(context);
        factory.inflate(R.layout.quick_navigation_title_bar, this);
    }

    void setupTitleBarAnimator(Animator animator) {
        Resources res = mContext.getResources();
        int duration = res.getInteger(R.integer.titlebar_animation_duration);
        animator.setInterpolator(new DecelerateInterpolator(
                ANIM_TITLEBAR_DECELERATE));
        animator.setDuration(duration);
    }

    /**
     * Update the progress, from 0 to 100.
     */
    public void Gotourl(){
        mNavBar.Gotourl();
    }

    public int getEmbeddedHeight() {
        int height = mNavBar.getHeight();
        if (mAutoLogin != null && mAutoLogin.getVisibility() == View.VISIBLE) {
            height += mAutoLogin.getHeight();
        }
        return height;
    }

    public boolean wantsToBeVisible() {
        return inAutoLogin()
            || (mSnapshotBar != null && mSnapshotBar.getVisibility() == View.VISIBLE
                    && mSnapshotBar.isAnimating());
    }

    private boolean inAutoLogin() {
        return mAutoLogin != null && mAutoLogin.getVisibility() == View.VISIBLE;
    }

    public boolean isEditingUrl() {
        return mNavBar.isEditingUrl();
    }

    public WebView getCurrentWebView() {
        Tab t = mBaseUi.getActiveTab();
        if (t != null) {
            return t.getWebView();
        } else {
            return null;
        }
    }

    public PageProgressView getProgressView() {
        return mProgress;
    }

    public NavigationBarBase getNavigationBar() {
        return mNavBar;
    }

    public boolean useQuickControls() {
        return mUseQuickControls;
    }

    private ViewGroup.LayoutParams makeLayoutParams() {
        if (mUseQuickControls) {
            return new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
        } else {
            return new AbsoluteLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
                    0, 0);
        }
    }

    @Override
    public View focusSearch(View focused, int dir) {
        if (FOCUS_DOWN == dir && hasFocus()) {
            return getCurrentWebView();
        }
        return super.focusSearch(focused, dir);
    }

    public void onTabDataChanged(Tab tab) {
        if (mSnapshotBar != null) {
            mSnapshotBar.onTabDataChanged(tab);
        }

        if (tab.isSnapshot()) {
            mSnapshotBar.setVisibility(VISIBLE);
            mNavBar.setVisibility(GONE);
        } else {
            if (mSnapshotBar != null) {
                mSnapshotBar.setVisibility(GONE);
            }
            mNavBar.setVisibility(VISIBLE);
        }
    }

}
