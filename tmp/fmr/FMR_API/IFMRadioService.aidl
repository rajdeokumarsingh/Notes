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

import com.pekall.fmradio.IRemoteServiceCallback;

interface IFMRadioService
{
    /**
     * Get status of FMR service 
     * see {@link FMRadioService#FM_SERVICE_NONE, FMRadioService#FM_SERVICE_INIT, ...}
     */
    int getFMServiceStatus();

    /**
     * Power on FMR
     */
    boolean openFMRadio();

    /**
     * Power off FMR
     */
    boolean closeFMRadio();

    boolean openFMRadioTx();

    /**
     * Get current frequency
     *
     * @return frequency in unit of KHz
     */
    int getFMFreq();

    /**
     * Set frequency
     * @param freq to set in unit of KHz
     */
    boolean setFMFreq(int freq);

    boolean setRdsCallback(int start);

    /**
     * Seek station forward/backward
     * @param direction 0 for backward, 1 for forward
     */
    boolean seekFM(int direction);

    boolean stopSeekFM();

    /**
     * Set mute/unmute
     * @param mode 0 for mute, 1 for unmute
     */
    boolean setMute(int mode);

    /**
     * Get mute status
     * @return true for mute, false for unmute
     */
    boolean isMuteFM();


    /**
     * Obsolete
     */
    int getFMVolume();

    /**
     * Obsolete
     */
    boolean setFMVolume(int volume);

    /**
     * Set audio path of FMR
     * @param routing, 0 for headset, 1 for speaker
     */
    boolean setFMAudioRouting(int routing);

    /**
     * Get audio path of FMR
     * @return audio path
     */
    int getFMAudioRouting();

    int getFMRssiLevel();
    boolean setFMRssiThreshold(int value);

    boolean enableRds();
    boolean disableRds();

    /**
     * Register callback functions
     * @param cb callback interface, see {@link IRemoteServiceCallback}
     */
    void registerCallback(IRemoteServiceCallback cb);

    /**
     * Unregister callback functions
     * @param cb callback interface, see {@link IRemoteServiceCallback}
     */
    void unregisterCallback(IRemoteServiceCallback cb);
}
