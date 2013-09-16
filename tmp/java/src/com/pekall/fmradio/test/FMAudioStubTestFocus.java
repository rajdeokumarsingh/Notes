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

package com.pekall.fmradio.test;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.test.AndroidTestCase;
import android.util.Log;

import com.pekall.fmradio.FMAudioInterface;
import com.pekall.fmradio.FMRadioService;
import com.pekall.fmradio.Factory;

/**
 * Test for FMAudioStubInterface and FMAudioDefaultImpl
 */
public class FMAudioStubTestFocus extends AndroidTestCase {
    // TODO: It is not working, need to investigate how to test audio focus.

    private static final String LOGTAG = "FMAudioInterfaceTest";
    private FMAudioInterface mAudio;

    private int mTestFocus;
    private AudioManager mAudioMgr;
    private Object mLock = new Object();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Log.i(LOGTAG, "setUp");

        Factory.useStubInterface(true);
        mAudio = Factory.getAudioInterface(new FMRadioService(), getContext());
        mAudioMgr = (AudioManager)
                getContext().getSystemService(Context.AUDIO_SERVICE);
        mTestFocus = AudioManager.AUDIOFOCUS_LOSS;
    }

    public void testInit() {
        Log.i(LOGTAG, "testInit");

        final OnAudioFocusChangeListener mAudioFocusListener = new OnAudioFocusChangeListener() {
            public void onAudioFocusChange(int focusChange) {
                Log.i(LOGTAG, "onAudioFocusChange: " + focusChange);
                mTestFocus = focusChange;

                synchronized (mLock) {
                    mLock.notifyAll();
                }
            }
        };

        // This handler runs on the "main" thread of the process
        final Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.i(LOGTAG, "request test focus");
                int result = mAudioMgr.requestAudioFocus(mAudioFocusListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
                assertEquals(result, AudioManager.AUDIOFOCUS_REQUEST_GRANTED);
            }
        };

        // Ask the main thread to start
        mainThreadHandler.sendEmptyMessage(0);

        Log.i(LOGTAG, "power on audio");
        mAudio.powerOnAudio();

        synchronized (mLock) {
            try {
                mLock.wait(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertEquals(mTestFocus, AudioManager.AUDIOFOCUS_LOSS);
    }

    @Override
    protected void tearDown() throws Exception {
        Log.i(LOGTAG, "setUp");
        mAudio.powerOffAudio();
        super.tearDown();
    }

}
