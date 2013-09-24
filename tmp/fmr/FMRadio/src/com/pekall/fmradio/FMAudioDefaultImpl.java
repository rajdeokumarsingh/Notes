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

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.AudioSystem;
import android.util.Log;

/**
 * Default implementation of FMAudioInterface, including audio focus mechanism.
 */
public abstract class FMAudioDefaultImpl implements FMAudioInterface {

    protected static final String LOGTAG = "FMAudioFocusClient";
    protected int mFMAudioRouting = ROUTE_FM_HEADSET;
    protected boolean mLostFocus;
    protected final AudioManager mAudioMgr;
    protected final FMRadioService mFMService;

    /**
     * Enable FM audio device
     */
    protected abstract void enableAudio();

    /**
     * Disable FM audio device
     */
    protected abstract void disableAudio();

    public FMAudioDefaultImpl(FMRadioService service, Context context) {
        mFMService = service;
        mAudioMgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * {@inheritDoc}
     */
    public void powerOnAudio() {
        mLostFocus = false;
        mAudioMgr.requestAudioFocus(mAudioFocusListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);

        setAudioOnSpeaker(false);
        enableAudio();
    }

    /**
     * {@inheritDoc}
     */
    public void powerOffAudio() {
        setAudioOnSpeaker(false);
        disableAudio();

        setMute(false);
        mAudioMgr.abandonAudioFocus(mAudioFocusListener);
        mLostFocus = false;
    }

    /**
     * {@inheritDoc}
     */
    public void registerMediaKey() {
        /*
         * FIXME: just comment for test
         * mAudioMgr.registerMediaButtonEventReceiver( new
         * ComponentName(mFMService.getPackageName(),
         * MediaButtonIntentReceiver.class.getName()));
         */
    }

    /**
     * {@inheritDoc}
     */
    public void unregisterMediaKey() {
        /* FIXME: just comment for test
         * mAudioMgr.unregisterMediaButtonEventReceiver( new
         * ComponentName(mFMService.getPackageName(),
         * MediaButtonIntentReceiver.class.getName()));
         */
    }

    /**
     * {@inheritDoc}
     */
    public void setMute(boolean mute) {
        mAudioMgr.setStreamMute(AudioManager.STREAM_FM, mute);
    }

    /**
     * {@inheritDoc}
     */
    public boolean getMute() {
        return mAudioMgr.isStreamMute(AudioManager.STREAM_FM);
    }

    /**
     * {@inheritDoc}
     */
    public void setAudioOnSpeaker(boolean speaker) {
        if (speaker) {
            AudioSystem.setForceUse(AudioSystem.FOR_MEDIA, AudioSystem.FORCE_SPEAKER);
        } else {
            AudioSystem.setForceUse(AudioSystem.FOR_MEDIA, AudioSystem.FORCE_NONE);
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getAudioPath() {
        return AudioSystem.getForceUse(AudioSystem.FOR_MEDIA) == AudioSystem.FORCE_SPEAKER ? ROUTE_FM_SPEAKER
                : ROUTE_FM_HEADSET;
    }

    /**
     * Audio focus listener.
     */
    protected OnAudioFocusChangeListener mAudioFocusListener = new OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            Log.v(LOGTAG, "AudioFocus, received: " + focusChange + ", status:" +
                    mFMService.getState());
            if (FMRadioService.FM_SERVICE_ON != mFMService.getState()
                    && FMRadioService.FM_SERVICE_INIT != mFMService.getState()
                    && FMRadioService.FM_SERVICE_OPENED != mFMService.getState()) {
                return;
            }

            switch (focusChange) {
            // Received when music begins
                case AudioManager.AUDIOFOCUS_LOSS: {
                    Log.v(LOGTAG, "AUDIOFOCUS_LOSS");
                    mFMService.clearFMService();

                    // Stop FM application
                    Intent intent = new Intent("android.intent.action.STOP_FM");
                    mFMService.sendBroadcast(intent);
                    break;
                }
                // Received when system plays notifications, ring, alarm, ...
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    Log.v(LOGTAG, "AUDIOFOCUS_LOSS_TRANSIENT");
                    disableAudio();
                    mLostFocus = true;
                    break;

                case AudioManager.AUDIOFOCUS_GAIN:
                    Log.v(LOGTAG, "AUDIOFOCUS_GAIN");
                    if (mLostFocus) {
                        enableAudio();
                        mLostFocus = false;
                    }
                    break;
                default:
                    Log.e(LOGTAG, "Unknown audio focus code");
                    break;
            }
        }
    };
}
