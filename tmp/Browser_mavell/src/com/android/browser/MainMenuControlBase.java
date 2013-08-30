/*
 * Copyright (C) 2011 The Android Open Source Project
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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

 

import java.util.ArrayList;
import java.util.List;

 

/**
 * base controller for Quick Controls pie menu
 */
public abstract class MainMenuControlBase  {

    protected Activity mActivity;
    protected MainMenu mPie;
 
    protected TextView mTabsCount;
    private int width;
    private int higth;
    public MainMenuControlBase(Activity activity) {
        mActivity = activity;
        Resources res = activity.getResources();
        DisplayMetrics display = res.getDisplayMetrics();

        width = display.widthPixels/5;
        higth = mActivity.getResources().getInteger(R.integer.height_mainmenu);
    }

    public void setMenuWidth(int width){
        this.width = width;
    }
    protected void removeFromContainer(FrameLayout container) {
        container.removeView(mPie);
    }

    protected void forceToTop(FrameLayout container) {
        if (mPie.getParent() != null) {
            container.removeView(mPie);
            container.addView(mPie);
        }
    }

    protected abstract void populateMenu();

    protected void setClickListener(OnClickListener listener, MainMenuItem... items) {
        for (MainMenuItem item : items) {
            item.getView().setOnClickListener(listener);
        }
    }
    protected MainMenuItem makeItem(int image, int l) {
        int orientation = mActivity.getRequestedOrientation();
        ImageView view = new ImageView(mActivity, null, android.R.attr.buttonBarButtonStyle);
        view.setImageResource(image);
        view.setMinimumWidth(width);
        view.setMinimumHeight(higth);
       
        view.setScaleType(ScaleType.CENTER);
        LayoutParams lp = new LayoutParams(width, higth);
        view.setLayoutParams(lp);
        return new MainMenuItem(view, l);
    }
    protected MainMenuItem makeItemB(Bitmap image, int l) {
        int orientation = mActivity.getRequestedOrientation();
        ImageView view = new ImageView(mActivity, null, android.R.attr.buttonBarButtonStyle);
        view.setImageBitmap(image);
        view.setMinimumWidth(width);
        view.setMinimumHeight(higth);
       
        view.setScaleType(ScaleType.CENTER);
        LayoutParams lp = new LayoutParams(width, higth);
        view.setLayoutParams(lp);
        return new MainMenuItem(view, l);
    }
 
}
