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

/**
 * FM function APIs
 */
public interface FMRadioInterface {

    /**
     * Seek a station backward, for {@link #seekFM()}
     */
    public static final int FMRADIO_SEEK_BACKWARD = 0;

    /**
     * Seek a station forward, for {@link #seekFM()}
     */
    public static final int FMRADIO_SEEK_FORWARD = 1;

    /**
     * Power on FM chipset
     *
     * @return true if successful, false if failed
     */
    public boolean powerOnChip();

    /**
     * Power off FM chipset
     *
     * @return true if successful, false if failed
     */
    public boolean powerOffChip();

    /**
     * Open FM radio
     *
     * @return true if successful, false if failed
     */
    public boolean openFMRadio();

    /**
     * Close FM radio, for both RX and TX
     *
     * @return true if successful, false if failed
     */
    public boolean closeFMRadio();

    /**
     * Open FM TX function
     *
     * @return true if successful, false if failed
     */
    public boolean openFMRadioTx();

    /**
     * Get FM mute status
     *
     * @return true for mute, false for not mute
     */
    public boolean isMuteFM();

    /**
     * Set FM mute
     *
     * @param mode 0 for mute, 1 for unmute
     * @return unused
     */
    public boolean setFMMute(int mode);

    /**
     * Get current frequency
     *
     * @return true if successful, false if failed
     */
    public boolean getFMFreq();

    /**
     * Set FM frequency
     *
     * @param freq frequency to set
     * @return true if successful, false if failed
     */
    public boolean setFMFreq(int freq);

    /**
     * Set RDS calllback
     *
     * @return true if successful, false if failed
     */
    public boolean setRdsCallback(int start);

    /**
     * Seek a station backward or forward from a given frequency
     *
     * @param startFreq start frequency for seeking, 0 means from current
     *            frequency
     * @param direction , {@link #FMRADIO_SEEK_BACKWARD()} or
     *            {@link #FMRADIO_SEEK_BACKWARD()}
     * @return true if successful, false if failed
     */
    public boolean seekFM(int startFreq, int direction);

    /**
     * Stop seeking
     *
     * @return true if successful, false if failed
     * @deprecated unused
     */
    public boolean stopSeekFM();

    /**
     * Get volume from FM chipset API. We prefer to do it by audio HAL
     *
     * @return true if successful, false if failed
     * @deprecated unused
     */
    @Deprecated
    public boolean getFMVolume();

    /**
     * Set volume from FM chipset API. We prefer to do it by audio HAL
     *
     * @param volume volume to be set
     * @return true if successful, false if failed
     * @deprecated unused
     */
    @Deprecated
    public boolean setFMVolume(int volume);

    /**
     * Get RSSI level
     *
     * @return true if successful, false if failed
     * @deprecated unused
     */
    @Deprecated
    public boolean getFMRssiLevel();

    /**
     * Set RSSI threshold param threshold threshold to be set
     *
     * @return true if successful, false if failed
     * @deprecated unused
     */
    @Deprecated
    public boolean setFMRssiThreshold(int threshold);

    /**
     * Enable RDS
     *
     * @return true if successful, false if failed
     */
    public boolean enableRds();

    /**
     * Disable RDS
     *
     * @return true if successful, false if failed
     */
    public boolean disableRds();

    /**
     * Set antenna type, wired headset or embedded
     *
     * @param type 0 for wired headset, 1 for embedded
     * @return true if successful, false if failed
     */
    public boolean setAntenna(int type);

    /**
     * Clear all FM functions
     */
    public void destroy();

    /**
     * Set a handler to deal with results of FM native APIs
     * @param handler which deals with results of FM native APIs
     */
    public void setResultHandler(Handler handler);

    /**
     * Command callback interface, sending back result
     */
    public interface OnCmdDoneListener {
        /**
         * The command was executed successfully
         */
        public final static int FM_STATUS_SUCCESS = 0;

        /**
         * The command was failed to executed
         */
        public final static int FM_STATUS_FAILED = 1;

        /**
         * Callback commands, see {@link #onCmdDone()}
         */
        public final static int FM_CMD_NONE = -1;
        public final static int FM_CMD_POWERON = 0;
        public final static int FM_CMD_POWEROFF = 1;
        public final static int FM_CMD_OPEN = 2;
        public final static int FM_CMD_CLOSE = 3;
        public final static int FM_CMD_GET_BAND = 4;
        public final static int FM_CMD_SET_BAND = 5;
        public final static int FM_CMD_GET_AUDIO_MODE = 6;
        public final static int FM_CMD_SET_AUDIO_MODE = 7;
        public final static int FM_CMD_IS_MUTE = 8;
        public final static int FM_CMD_SET_MUTE = 9;
        public final static int FM_CMD_GET_FREQ = 10;
        public final static int FM_CMD_SET_FREQ = 11;
        public final static int FM_CMD_SEEK = 12;
        public final static int FM_CMD_STOP_SEEK = 13;
        public final static int FM_CMD_GET_VOLUME = 14;
        public final static int FM_CMD_SET_VOLUME = 15;
        public final static int FM_CURRENT_RSSI = 16;
        public final static int FM_SET_RSSI_THRESHOLD = 17;
        public final static int FM_SET_DEEMPHASIS_FILTER = 18;
        public final static int FM_CMD_TX_ON = 19;
        public final static int FM_CMD_SEND_RDS_STRING = 20;
        public final static int FM_CMD_SET_OUTPUT_MODE = 21;
        public final static int FM_CMD_SEND_RDS_RAW_DATA = 22;

        /**
         * Invoked when a command is completed.
         *
         * @param what see {@link FMRadioInterfaceCMD}
         * @param status see {@link #FM_STATUS_SUCCESS},
         *            {@link #FM_STATUS_FAILED}
         * @param value extra result data
         */
        void onCmdDone(int what, int status, Object value);
    }
}
