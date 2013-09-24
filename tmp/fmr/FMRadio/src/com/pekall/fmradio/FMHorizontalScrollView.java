/*
 * Copyright (C) 2013 Capital Alliance Software LTD (Pekall)
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

package com.pekall.fmradio;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import android.widget.HorizontalScrollView;

/**
 * For FMR channel list
 */
public class FMHorizontalScrollView extends HorizontalScrollView {
    private final static int FLING_MSG = 0;

    private OnScrollListener mOnScrollListener = null;

    /**
     * Scroll listener interface
     */
    public interface OnScrollListener {
        public void onAfterFling();
    }

    /**
     * Constructor
     */
    public FMHorizontalScrollView(Context context) {
        super(context);
    }

    /**
     * Constructor
     */
    public FMHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor
     */
    public FMHorizontalScrollView(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnScrollListener(OnScrollListener l) {
        mOnScrollListener = l;
    }

    @Override
    public void fling(int velocityX) {
        final float deceleration = SensorManager.GRAVITY_EARTH // g
                // (m/s^2)
                * 39.37f // inch/meter
                * (getResources().getDisplayMetrics().density * 160.0f) // pixels
                                                                        // per
                                                                        // inch
                * ViewConfiguration.getScrollFriction();
        final int duration = (int) (1000 * (float) velocityX / deceleration); // duration

        mHandler.sendEmptyMessageDelayed(FLING_MSG, Math.abs(duration));
        super.fling(velocityX);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FLING_MSG:
                    if (mOnScrollListener != null) {
                        mOnScrollListener.onAfterFling();
                    }
                    break;

                default:
                    break;
            }
        }
    };
}
