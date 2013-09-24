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

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * This class is used for FMR UI test.
 */
public class FMRadioStubInterface implements FMRadioInterface {
    private final static String TAG = "FMRadioStubInterface";
    private static final int FM_SEEK_STEP = 500;

    private int mFreq;

    /**
     * Constructor
     */
    public FMRadioStubInterface(FMRadioService service) {
        Log.d(TAG, "Constructor");

        Looper looper;
        if ((looper = Looper.myLooper()) != null) {
            Log.d(TAG, "FMRadioStubInterface Looper = Looper.myLooper()!");
            mEventHandler = new EventHandler(this, looper);
        } else if ((looper = Looper.getMainLooper()) != null) {
            mEventHandler = new EventHandler(this, looper);
        } else {
            mEventHandler = null;
        }
        mOnCmdDoneListener = service.getCommandDoneListener();
    }

    /**
     * {@inheritDoc}
     */
    public boolean powerOnChip() {
        Log.d(TAG, "powerOnChip");
        Message msg = mEventHandler.obtainMessage(EventHandler.FM_EVENT_CMD_DONE,
                FMRadioInterface.OnCmdDoneListener.FM_CMD_POWERON,
                FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS, 0);
        mEventHandler.sendMessage(msg);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean powerOffChip() {
        Log.d(TAG, "powerOffChip");

        Message msg = mEventHandler.obtainMessage(EventHandler.FM_EVENT_CMD_DONE,
                FMRadioInterface.OnCmdDoneListener.FM_CMD_POWEROFF,
                FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS, 0);
        mEventHandler.sendMessage(msg);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean openFMRadio() {
        Log.d(TAG, "openFMRadio");

        Message msg = mEventHandler.obtainMessage(EventHandler.FM_EVENT_CMD_DONE,
                FMRadioInterface.OnCmdDoneListener.FM_CMD_OPEN,
                FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS, 0);
        mEventHandler.sendMessage(msg);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean closeFMRadio() {
        Log.d(TAG, "closeFMRadio");

        Message msg = mEventHandler.obtainMessage(EventHandler.FM_EVENT_CMD_DONE,
                FMRadioInterface.OnCmdDoneListener.FM_CMD_CLOSE,
                FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS, 0);
        mEventHandler.sendMessage(msg);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean openFMRadioTx() {
        Log.d(TAG, "openFMRadioTx");

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isMuteFM() {
        Log.d(TAG, "isMuteFM");

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean setFMMute(int mode) {
        Log.d(TAG, "setFMMute: " + mode);
        Message msg = mEventHandler.obtainMessage(EventHandler.FM_EVENT_CMD_DONE,
                FMRadioInterface.OnCmdDoneListener.FM_CMD_SET_MUTE,
                FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS, 0);
        mEventHandler.sendMessage(msg);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean getFMFreq() {
        Log.d(TAG, "getFMFreq");

        Message msg = mEventHandler.obtainMessage(EventHandler.FM_EVENT_CMD_DONE,
                FMRadioInterface.OnCmdDoneListener.FM_CMD_GET_FREQ,
                FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS, 0);
        mEventHandler.sendMessage(msg);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean setFMFreq(int freq) {
        Log.d(TAG, "setFMFreq: " + freq);

        mFreq = freq;
        Message msg = mEventHandler.obtainMessage(EventHandler.FM_EVENT_CMD_DONE,
                FMRadioInterface.OnCmdDoneListener.FM_CMD_SET_FREQ,
                FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS, 0);
        mEventHandler.sendMessage(msg);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean setRdsCallback(int start) {
        Log.d(TAG, "setRdsCallback: " + start);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean seekFM(int startFreq, int direction) {
        Log.d(TAG, "seekFM: " + direction);

        if (direction == 0) {
            mFreq -= FM_SEEK_STEP;
        } else if (direction == 1) {
            mFreq += FM_SEEK_STEP;
        }
        if (mFreq < FMRadio.LOW_FREQUENCY) {
            mFreq = FMRadio.LOW_FREQUENCY;
        }
        if (mFreq > FMRadio.HIGH_FREQUENCY) {
            mFreq = FMRadio.HIGH_FREQUENCY;
        }

        Message msg = mEventHandler.obtainMessage(EventHandler.FM_EVENT_CMD_DONE,
                FMRadioInterface.OnCmdDoneListener.FM_CMD_SEEK,
                FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS, mFreq);
        mEventHandler.sendMessage(msg);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean stopSeekFM() {
        Log.d(TAG, "stopSeekFM");

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean getFMVolume() {
        Log.d(TAG, "getFMVolume");

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean setFMVolume(int volume) {
        Log.d(TAG, "setFMVolume: " + volume);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean getFMRssiLevel() {
        Log.d(TAG, "getFMRssiLevel");

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean setFMRssiThreshold(int threshold) {
        Log.d(TAG, "setFMRssiThreshold: " + threshold);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean enableRds() {
        Log.d(TAG, "enableRds");

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean disableRds() {
        Log.d(TAG, "disableRds");

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean setAntenna(int type) {
        Log.d(TAG, "setAntenna: " + type);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public void destroy() {
        Log.d(TAG, "destroy");

    }

    // Event Handle
    private Handler mEventHandler;
    private final OnCmdDoneListener mOnCmdDoneListener;

    // message handler
    private static class EventHandler extends Handler {
        private FMRadioStubInterface mFMRadioSystem;

        private static final int FM_EVENT_CMD_DONE = 0;
        private static final int FM_EVENT_CMD_RDS_STRING = 2;
        private static final int FM_EVENT_CMD_RDS_RAW_DATA = 3;

        public EventHandler(FMRadioStubInterface fm, Looper looper) {
            super(looper);
            mFMRadioSystem = fm;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FM_EVENT_CMD_DONE:
                case FM_EVENT_CMD_RDS_STRING:
                case FM_EVENT_CMD_RDS_RAW_DATA:
                    Log.i(TAG, "handleMessage: " + msg.what);
                    mFMRadioSystem.mOnCmdDoneListener.onCmdDone(
                            msg.arg1, msg.arg2, msg.obj);
                    break;
                default:
                    Log.e(TAG, "Unknown message type " + msg.what);
            }
            return;
        }
    }

    @Override
    public void setResultHandler(Handler handler) {
        mEventHandler = handler;
    }
}
