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
import android.content.Intent;
import android.util.Log;

import rds.core.GroupLevelDecoder;
import rds.core.Text;
import rds.core.TunedStation;
import rds.input.group.GroupEvent;
import rds.log.RealTime;

/**
 * RDSController controls the RDS decoding. It reads raw RDS data from native
 * layer and send it to RDS decoder. If a RDS string is ready, RDSController
 * will send it to UI layer.
 */
class RDSController {
    private static final String LOGTAG = "RDSController";

    /**
     * Program Service (PS) Name
     */
    static final int RDS_TYPE_PS = 0;
    /**
     * Radio Text (RT) Type A
     */
    static final int RDS_TYPE_RT_A = 1;
    /**
     * Radio Text (RT) Type B
     */
    static final int RDS_TYPE_RT_B = 2;
    /**
     * Unknown type, for error
     */
    static final int RDS_TYPE_UNKNOWN = -1;

    /**
     * GBK encoding for RDS
     */
    static final int RDS_LOCALE_GBK = 0;
    /**
     * UTF-8 encoding for RDS
     */
    static final int RDS_LOCALE_UTF8 = 1;

    private static final int RDS_DISPLAY_INTERVAL = 30;

    private int mLastType = RDS_TYPE_UNKNOWN;
    private int mDisplayCounter = RDS_DISPLAY_INTERVAL;

    private Context mContext;

    static private final GroupLevelDecoder mGroupDecoder = new GroupLevelDecoder(
            new rds.log.Log());

    static private RDSController mInstance = null;

    static synchronized RDSController getInstance() {
        if (mInstance == null) {
            mInstance = new RDSController();
        }

        return mInstance;
    }

    /**
     * The text encoding is UTF-8 in RDS specification. But some stations
     * broadcast string in GBK. Added this function for good compatibility.
     *
     * @param decodedData data to be decoded
     * @param localeId id of locale
     * @return decoded RDS string
     */
    static String getLocalisedString(byte[] decodedData, int localeId) {
        if (decodedData == null || decodedData.length <= 0) {
            return "";
        }
        try {
            if (localeId == RDS_LOCALE_GBK) {
                return new String(decodedData, "GBK");
            } else if (localeId == RDS_LOCALE_UTF8) {
                return new String(decodedData, "UTF8");
            } else {
                return new String(decodedData);
            }
        } catch (java.io.UnsupportedEncodingException e) {
            Log.e(LOGTAG, "not support locale");
        }
        return "";
    }

    private RDSController() {
    }

    void setContext(Context c) {
        mContext = c;
    }

    private void sendRdsData2Ui(byte[] data, int type) {
        if (mContext == null) {
            Log.e(LOGTAG, "sendRdsData2Ui, mContext is null!");
            return;
        }

        Intent intent = new Intent(FMRadioService.FM_RDS_STRING);
        intent.putExtra("rds", data);
        intent.putExtra("type", type);
        mContext.sendBroadcast(intent);
    }

    private void resetRds() {
        mGroupDecoder.reset();
        mLastType = RDS_TYPE_UNKNOWN;
        mDisplayCounter = RDS_DISPLAY_INTERVAL;

        // Clear RDS strings in UI
        sendRdsData2Ui(new byte[0], RDS_TYPE_UNKNOWN);
    }

    public void appendRdsRawData(byte[] raw) {
        if (raw == null || raw.length <= 0) {
            Log.e(LOGTAG, "appendRdsRawData: reset rds");
            resetRds();
            return;
        }

        if (DebugFlags.LOG_RDS) {
            Log.i(LOGTAG, "appendRdsRawData: " + raw.length);
        }

        // read 4 blocks of offsets 0, 1, 2, 3
        int[] res = new int[4];
        for (int i = 0; i < 4; i++) {
            res[i] = (raw[2 * i] & 0xFF) | ((raw[2 * i + 1] & 0xFF) << 8);

            if (DebugFlags.LOG_RDS) {
                Log.i(LOGTAG,
                        "group: " + i + ", 0x" + Integer.toHexString(res[i]));
            }
        }

        try {
            mGroupDecoder.processOneGroup(new GroupEvent(new RealTime(), res,
                    false));
            scheduleRdsDisplay();
        } catch (java.io.IOException ioe) {
            Log.e(LOGTAG, "", ioe);
        }
    }

    private void scheduleRdsDisplay() {
        // avoid update RDS string frequently
        if (mDisplayCounter > 0) {
            mDisplayCounter--;
            return;
        }
        mDisplayCounter = RDS_DISPLAY_INTERVAL;

        if (DebugFlags.LOG_RDS) {
            Log.i(LOGTAG, "scheduleRdsDisplay begin");
        }

        TunedStation station = mGroupDecoder.getTunedStation();

        Text ps = station.getPS();
        if (ps.isComplete()) {
            // display PS if it is not displayed last time
            if (mLastType != RDS_TYPE_PS) {
                mLastType = RDS_TYPE_PS;
                sendRdsData2Ui(ps.getRawBytes(), 0);
                return;
            }
        }

        Text rt = station.getRT();
        if (rt.isComplete()) {
            // display RT if it is not displayed last time
            if (mLastType != RDS_TYPE_RT_A) {
                mLastType = RDS_TYPE_RT_A;
                sendRdsData2Ui(rt.getRawBytes(), 0);
                return;
            }
        }

        Text rtB = station.getRTVersionB();
        if (rtB.isComplete()) {
            // display RT if it is not displayed last time
            if (mLastType != RDS_TYPE_RT_B) {
                mLastType = RDS_TYPE_RT_B;
                sendRdsData2Ui(rtB.getRawBytes(), 0);
                return;
            }
        }
    }
}
