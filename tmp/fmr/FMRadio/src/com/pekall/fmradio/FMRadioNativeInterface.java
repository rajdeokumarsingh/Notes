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

import java.lang.ref.WeakReference;

/**
 * Device FM Radio Interface implementation.
 */
public class FMRadioNativeInterface implements FMRadioInterface {
    private final static String TAG = "FMRadioNativeInterface";

    static {
        System.loadLibrary("fm_jni");
    }

    /**
     * Constructor
     */
    public FMRadioNativeInterface(FMRadioService service) {
        Looper looper;
        if ((looper = Looper.myLooper()) != null) {
            Log.d(TAG, "FMRadioNativeInterface Looper = Looper.myLooper()!");
            mEventHandler = new EventHandler(this, looper);
        } else if ((looper = Looper.getMainLooper()) != null) {
            mEventHandler = new EventHandler(this, looper);
        } else {
            mEventHandler = null;
        }

        fmradio_native_setup(new WeakReference<FMRadioNativeInterface>(this));

        mOnCmdDoneListener = service.getCommandDoneListener();
    }

    /**
     * {@inheritDoc}
     */
    public boolean powerOnChip() {
        return nativePowerOnChip();
    }

    /**
     * {@inheritDoc}
     */
    public boolean powerOffChip() {
        return nativePowerOffChip();
    }

    /**
     * {@inheritDoc}
     */
    public boolean openFMRadio() {
        return nativeOpenFMRadio();
    }

    /**
     * {@inheritDoc}
     */
    public boolean closeFMRadio() {
        return nativeCloseFMRadio();
    }

    /**
     * {@inheritDoc}
     */
    public boolean openFMRadioTx() {
        return nativeOpenFMRadioTx();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isMuteFM() {
        return nativeIsMuteFM();
    }

    /**
     * {@inheritDoc}
     */
    public boolean setFMMute(int mode) {
        return nativeSetFMMute(mode);
    }

    /**
     * {@inheritDoc}
     */
    public boolean getFMFreq() {
        return nativeGetFMFreq();
    }

    /**
     * {@inheritDoc}
     */
    public boolean setFMFreq(int freq) {
        return nativeSetFMFreq(freq);
    }

    /**
     * {@inheritDoc}
     */
    public boolean setRdsCallback(int start) {
        return nativeSetRdsCallback(start);
    }

    /**
     * {@inheritDoc}
     */
    public boolean seekFM(int startFreq, int direction) {
        return nativeSeekFM(startFreq, direction);
    }

    /**
     * {@inheritDoc}
     */
    public boolean stopSeekFM() {
        return nativeStopSeekFM();
    }

    /**
     * {@inheritDoc}
     */
    public boolean getFMVolume() {
        return nativeGetFMVolume();
    }

    /**
     * {@inheritDoc}
     */
    public boolean setFMVolume(int volume) {
        return nativeSetFMVolume(volume);
    }

    /**
     * {@inheritDoc}
     */
    public boolean getFMRssiLevel() {
        return nativeGetFMRssiLevel();
    }

    /**
     * {@inheritDoc}
     */
    public boolean setFMRssiThreshold(int threshold) {
        return nativeSetFMRssiThreshold(threshold);
    }

    /**
     * {@inheritDoc}
     */
    public boolean enableRds() {
        return nativeEnableRds();
    }

    /**
     * {@inheritDoc}
     */
    public boolean disableRds() {
        return nativeDisableRds();
    }

    /**
     * {@inheritDoc}
     */
    public boolean setAntenna(int type) {
        return nativeSetAntenna(type);
    }

    /**
     * {@inheritDoc}
     */
    public void destroy() {
        fmradio_native_finalize();
    }

    public native boolean nativePowerOnChip();

    public native boolean nativePowerOffChip();

    public native boolean nativeOpenFMRadio();

    public native boolean nativeCloseFMRadio();

    public native boolean nativeOpenFMRadioTx();

    public native boolean nativeGetFMBand();

    public native boolean nativeSetFMBand(int band);

    public native boolean nativeSetEmphasisFilter(int deEmphasis);

    public native boolean nativeGetFMAudioMode();

    public native boolean nativeSetFMAudioMode(int mode);

    public native boolean nativeIsMuteFM();

    public native boolean nativeSetFMMute(int mode);

    public native boolean nativeGetFMFreq();

    public native boolean nativeSetFMFreq(int freq);

    public native boolean nativeSetFMChannel(int freq);

    public native boolean nativeSetRdsCallback(int start);

    public native boolean nativeSeekFM(int startFreq, int direction);

    public native boolean nativeStopSeekFM();

    public native boolean nativeGetFMVolume();

    public native boolean nativeSetFMVolume(int volume);

    public native boolean nativeGetFMRssiLevel();

    public native boolean nativeSetFMRssiThreshold(int threshold);

    public native boolean nativeEnableRds();

    public native boolean nativeDisableRds();

    public native boolean nativeSetAntenna(int type);

    private native void fmradio_native_setup(Object fmradio_this);

    private native void fmradio_native_finalize();

    // Called from native code when an interesting event happens.
    private static void postEventFromNative(Object fmradio_ref, int cmdid,
            int cmdresult, int fmstatus, int fmvalue) {
        FMRadioNativeInterface fmsystem = (FMRadioNativeInterface) ((WeakReference<?>) fmradio_ref)
                .get();
        if (fmsystem == null) {
            return;
        }

        if (fmsystem.mEventHandler != null) {
            Message msg = fmsystem.mEventHandler.obtainMessage(cmdresult,
                    cmdid, fmstatus, fmvalue);
            fmsystem.mEventHandler.sendMessage(msg);
        }
    }

    // Called from native code when there is incoming RDS string
    private static void postRdsStringFromNative(Object fmradio_ref, byte[] rds,
            int type) {
        FMRadioNativeInterface fmsystem = (FMRadioNativeInterface) ((WeakReference<?>) fmradio_ref)
                .get();
        if (fmsystem == null || fmsystem.mEventHandler == null || rds == null) {
            return;
        }

        Message msg = fmsystem.mEventHandler.obtainMessage(
                EventHandler.FM_EVENT_CMD_RDS_STRING,
                OnCmdDoneListener.FM_CMD_SEND_RDS_STRING, type, rds);
        fmsystem.mEventHandler.sendMessage(msg);
    }

    // Called from native code when there is incoming RDS raw data
    private static void postRdsRawDataFromNative(Object fmradio_ref, byte[] raw,
            int len) {
        FMRadioNativeInterface fmsystem = (FMRadioNativeInterface) ((WeakReference<?>) fmradio_ref)
                .get();
        if (raw == null || fmsystem == null || fmsystem.mEventHandler == null) {
            return;
        }

        Message msg = fmsystem.mEventHandler.obtainMessage(
                EventHandler.FM_EVENT_CMD_RDS_RAW_DATA,
                OnCmdDoneListener.FM_CMD_SEND_RDS_RAW_DATA, len, raw);
        fmsystem.mEventHandler.sendMessage(msg);
    }

    private Handler mEventHandler;
    private int mNativeContext;

    private final OnCmdDoneListener mOnCmdDoneListener;

    private static class EventHandler extends Handler {
        private FMRadioNativeInterface mFMRadioSystem;

        private static final int FM_EVENT_CMD_DONE = 0;
        private static final int FM_EVENT_CMD_RDS_STRING = 2;
        private static final int FM_EVENT_CMD_RDS_RAW_DATA = 3;

        public EventHandler(FMRadioNativeInterface fm, Looper looper) {
            super(looper);
            mFMRadioSystem = fm;
        }

        @Override
        public void handleMessage(Message msg) {
            if (mFMRadioSystem.mNativeContext == 0) {
                Log.w(TAG, "FMRadioCore went away with unhandler events !");
                return;
            }
            if (mFMRadioSystem.mOnCmdDoneListener == null) {
                Log.w(TAG, "Listener null, return");
                return;
            }
            switch (msg.what) {
                case FM_EVENT_CMD_DONE:
                case FM_EVENT_CMD_RDS_STRING:
                case FM_EVENT_CMD_RDS_RAW_DATA:
                    Log.i(TAG, "handleMessage: " + msg.what);
                    mFMRadioSystem.mOnCmdDoneListener.onCmdDone(msg.arg1, msg.arg2,
                            msg.obj);
                    break;
                default:
                    Log.e(TAG, "Unknown message type " + msg.what);
            }
            return;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setResultHandler(Handler handler) {
        mEventHandler = handler;
    }
}
