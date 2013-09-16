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
import android.media.AudioSystem;
import android.provider.Settings;
import android.util.Log;

/**
 * Device Audio Interface implementation.
 */
public class FMAudioDeviceInterface extends FMAudioDefaultImpl {
    static final String LOGTAG = "FMAudioDeviceInterface";

    // The user experience is bad if both FMR and touch sound are on.
    // We disable the touch sound before FMR begins, and enable
    // it after FMR ends.

    // The setting of dial tone is default on
    private int mDialToneSetting = 1;
    private boolean mToneDisabled = false;

    public FMAudioDeviceInterface(FMRadioService service, Context context) {
        super(service, context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void powerOnAudio() {
        disableTouchTones();

        super.powerOnAudio();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void powerOffAudio() {
        super.powerOffAudio();

        enableTouchTones();
        mDialToneSetting = 1;
        mToneDisabled = false;
    }

    /**
     * Disable Touch Tone in settings
     */
    private void disableTouchTones() {
        if (mToneDisabled) {
            Log.e(LOGTAG, "disableTouchTones invoked when "
                    + "mToneDisabled is true, just return");
            return;
        }
        mToneDisabled = true;

        mAudioMgr.unloadSoundEffects();
        mDialToneSetting = Settings.System.getInt(mFMService.getContentResolver(),
                Settings.System.DTMF_TONE_WHEN_DIALING, 1);

        Log.i(LOGTAG, "disable dial tone, save: " + mDialToneSetting);
        Settings.System.putInt(mFMService.getContentResolver(),
                Settings.System.DTMF_TONE_WHEN_DIALING, 0);
    }

    /**
     * Enable Touch Tone in settings
     */
    private void enableTouchTones() {
        if (!mToneDisabled) {
            Log.e(LOGTAG, "enableTouchTones invoked when "
                    + "mToneDisabled is false, just return");
            return;
        }
        mToneDisabled = false;

        int effect = Settings.System.getInt(mFMService.getContentResolver(),
                Settings.System.SOUND_EFFECTS_ENABLED, 0);
        // Restore sound effect only when its setting is ON
        if (effect == 1) {
            mAudioMgr.loadSoundEffects();
        }

        int currentSetting = Settings.System.getInt(mFMService.getContentResolver(),
                Settings.System.DTMF_TONE_WHEN_DIALING, 1);
        Log.i(LOGTAG, "restore dial tone, saved: " + mDialToneSetting + ", current: "
                + currentSetting);

        // We set DTMF_TONE_WHEN_DIALING to 0 in disableTouchTones()
        // if it is not 0, user must change this setting during FM playback.
        // In this case, do NOT need to restore this setting
        if (currentSetting == 0) {
            Settings.System.putInt(mFMService.getContentResolver(),
                    Settings.System.DTMF_TONE_WHEN_DIALING, mDialToneSetting);
        }

    }

    /**
     * Enable FM audio device by faking a device available event
     */
    @Override
    protected void enableAudio() {
        if (AudioSystem.DEVICE_STATE_UNAVAILABLE == AudioSystem.getDeviceConnectionState(
                AudioSystem.DEVICE_OUT_FM, "")) {
            AudioSystem.setDeviceConnectionState(AudioSystem.DEVICE_OUT_FM,
                    AudioSystem.DEVICE_STATE_AVAILABLE, "");
        }
    }

    /**
     * Disable FM audio device by faking a device unavailable event
     */
    @Override
    protected void disableAudio() {
        if (AudioSystem.DEVICE_STATE_AVAILABLE == AudioSystem.getDeviceConnectionState(
                AudioSystem.DEVICE_OUT_FM, "")) {
            AudioSystem.setDeviceConnectionState(AudioSystem.DEVICE_OUT_FM,
                    AudioSystem.DEVICE_STATE_UNAVAILABLE, "");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAudioOn() {
        return AudioSystem.DEVICE_STATE_AVAILABLE == AudioSystem.getDeviceConnectionState(
                AudioSystem.DEVICE_OUT_FM, "");
    }
}
