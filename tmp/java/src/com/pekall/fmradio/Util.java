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
import android.telephony.TelephonyManager;

/**
 * Provide utility functions
 */
public class Util {
    /**
     * maximum channel display number TODO: move to config files
     */
    static final int MAX_CHANNEL_NUM = 40;

    /**
     * Get airplane mode state
     *
     * @param content android context
     * @return true for on, false for off
     */
    public static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
    }

    /**
     * Check whether there is an MT/MO call
     *
     * @param context android context
     * @return true for there is, false for there is NOT
     */
    public static boolean isInCallState(Context context) {
        TelephonyManager telemgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        // TODO: enable below code for DSDS case
        /*
         * TelephonyManager2 telemgr2 = (TelephonyManager2) context
         * .getSystemService(Context.TELEPHONY_SERVICE2);
         */
        return ((telemgr.getCallState() != TelephonyManager.CALL_STATE_IDLE));
    }

    /**
     * Check whether this device support internal antenna
     *
     * @return true if supported, otherwise return false
     */
    public static boolean supportInternalAntenna(Context context) {
        return context.getResources().getBoolean(R.bool.support_internal_antenna);
    }

    /**
     * Check whether the PEKALL copyright activity could be displayed
     *
     * @return true if supported, otherwise return false
     */
    public static boolean showCopyright(Context context) {
        return context.getResources().getBoolean(R.bool.show_copyright);
    }

    /**
     * Check whether the preset channels should be displayed
     *
     * @return true if supported, otherwise return false
     */
    public static boolean displayPresetChannels(Context context) {
        return context.getResources().getBoolean(R.bool.display_preset_channels);
    }

    /**
     * Check whether a wired headset is connected to handset
     *
     * @return true for connected, false for NOT connected
     */
    public static boolean isWiredHeadsetOn() {
        if (AudioSystem.getDeviceConnectionState(AudioSystem.DEVICE_OUT_WIRED_HEADSET, "")
                == AudioSystem.DEVICE_STATE_AVAILABLE) {
            return true;
        }
        if (AudioSystem.getDeviceConnectionState(AudioSystem.DEVICE_OUT_WIRED_HEADPHONE, "")
                == AudioSystem.DEVICE_STATE_AVAILABLE) {
            return true;
        }
        return false;
    }

    /**
     * Check whether there is a mounted SD card
     *
     * @return true for there is, otherwise false
     */
    public static boolean isExternalStorageStateMounted() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }
}
