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

 

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends LinearLayout {

    private static final int MAX_LEVELS = 5;
    private List<MainMenuItem> mItems;
    private int mLevels;
    private int[] mCounts;

    private Drawable mBackground;
    private Paint mNormalPaint;
    private Paint mSelectedPaint;
 

    private boolean mUseBackground;

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public MainMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public MainMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * @param context
     */
    public MainMenu(Context context) {
        super(context);
        init(context);
    }

 
    private void init(Context ctx) {
        mItems = new ArrayList<MainMenuItem>();
        mLevels = 0;
        mCounts = new int[MAX_LEVELS];
        Resources res = ctx.getResources();
        this.setBackgroundDrawable(new ColorDrawable(0XFF515151));
        setWillNotDraw(false);
        setDrawingCacheEnabled(false);
    }
 
    public void setUseBackground(boolean useBackground) {
        mUseBackground = useBackground;
    }

    public void addItem(MainMenuItem item) {
        // add the item to the pie itself
        mItems.add(item);
        int l = item.getLevel();
        mLevels = Math.max(mLevels, l);
        mCounts[l]++;
    }

    public void removeItem(MainMenuItem item) {
        mItems.remove(item);
    }

    public void clearItems() {
        mItems.clear();
    }

 

    /**
     * guaranteed has center set
     * @param show
     */
    public void show(boolean show) {
        layoutPie();
    }
 
    private void layoutPie() {
        this.removeAllViewsInLayout();
        this.removeAllViews();
        for (int i = 0; i < mLevels; i++) {
            int level = i + 1;
              for (MainMenuItem item : mItems) {
                if (item.getLevel() == level) {
                    View view = item.getView();
                    this.addView(view);
                  }
            }
         }
       
    }
 
 
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
 
}
