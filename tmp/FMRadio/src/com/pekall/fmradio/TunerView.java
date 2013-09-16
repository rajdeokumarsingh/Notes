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
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Customized TunerView
 */
public class TunerView extends ImageView {

    public interface OnMoveListener {
        /**
         * Handle motion event
         *
         * @param tunerView
         * @param step the amount of movement for tuner view, >0 means move to
         *            RIGHT, <0 means move to LEFT. 1 means 1 step to RIGHT, -1
         *            means 1 step to LEFT
         */
        void onMove(TunerView tunerView, int step);
    }

    private static final int MOVE_RIGHT_THRESHOLD = 5;
    private static final int MOVE_LEFT_THRESHOLD = -5;

    private int mTotalMove = 0;

    private int mLastMotionX = 0;

    private OnMoveListener mListener;

    private int mIndex = 0;
    private static final int MININDEX = 0;
    private static final int MAXINDEX = 2;
    private static int[] TunerViewBackgrounds = new int[] {
            R.drawable.tuner_view_0, R.drawable.tuner_view_1,
            R.drawable.tuner_view_2
    };

    public TunerView(Context context) {
        super(context);
        setClickable(true);
    }

    public TunerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
    }

    public TunerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Set listener for motion event
     *
     * @param l listener
     */
    public void setOnMoveListener(OnMoveListener l) {
        mListener = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final int x = (int) event.getX();

        switch (action) {
            case MotionEvent.ACTION_MOVE: {
                if (mLastMotionX == 0) {
                    mLastMotionX = x;
                } else {
                    final int scrollX = x - mLastMotionX;
                    mLastMotionX = x;
                    if (mListener != null && scrollX != 0) {
                        mTotalMove += scrollX;
                        if (mTotalMove > MOVE_RIGHT_THRESHOLD) {
                            mListener.onMove(this, 1);
                            moveRightUI();
                            mTotalMove = 0;
                        } else if (mTotalMove < MOVE_LEFT_THRESHOLD) {
                            mListener.onMove(this, -1);
                            moveLeftUI();
                            mTotalMove = 0;
                        }
                    }
                }
                return true;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // fall through
                mLastMotionX = 0;
            default:
                return super.onTouchEvent(event);
        }
    }

    private void moveRightUI() {
        mIndex++;
        if (mIndex > MAXINDEX) {
            mIndex = MININDEX;
        }
        setImageResource(TunerViewBackgrounds[mIndex]);
    }

    private void moveLeftUI() {
        mIndex--;
        if (mIndex < MININDEX) {
            mIndex = MAXINDEX;
        }
        setImageResource(TunerViewBackgrounds[mIndex]);
    }
}
