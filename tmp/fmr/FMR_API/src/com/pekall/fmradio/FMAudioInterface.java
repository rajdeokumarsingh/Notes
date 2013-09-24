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

/**
 * Audio APIs for FM audio control
 */
public interface FMAudioInterface {

    /**
     * Intent Action: switch audio path to wired headset
     */
    public static final String FM_ROUTE_HEADSET = "com.pekall.fmradio.route.headset";

    /**
     * Intent Action: switch audio path to speaker
     */
    public static final String FM_ROUTE_LOUDSPEAKER = "com.pekall.fmradio.route.loudspeaker";

    /**
     * Wired headset, for {@link #getAudioPath()}
     */
    public static final int ROUTE_FM_HEADSET = 0;

    /**
     * Speaker, for {@link #getAudioPath()}
     */
    public static final int ROUTE_FM_SPEAKER = 1;

    /**
     * Mute FM radio, for {@link #setFMMuteAudio()}
     */
    public static final int FMRADIO_MUTE_AUDIO = 0;

    /**
     * Unmute FM radio, for {@link #setFMMuteAudio()}
     */
    public static final int FMRADIO_UNMUTE_AUDIO = 1;

    /**
     * Open audio path of FM radio. After invoked, FM radio sound should be
     * heard
     */
    public void powerOnAudio();

    /**
     * Close audio path of FM radio. After invoked, FM radio sound should be off
     */
    public void powerOffAudio();

    /**
     * Check whether FMR audio is on
     *
     * @return true if FMR audio is on, false otherwise
     */
    public boolean isAudioOn();

    /**
     * Set audio path to speaker or wired headset
     *
     * @param speaker true for speaker, false for wired headset
     */
    public void setAudioOnSpeaker(boolean speaker);

    /**
     * Register listener for media keys
     */
    public void registerMediaKey();

    /**
     * Unregister listener for media keys
     */
    public void unregisterMediaKey();

    /**
     * Get current audio path
     *
     * @return {@link #ROUTE_FM_HEADSET} for wired headset,
     *         {@link #ROUTE_FM_SPEAKER} for speaker
     */
    public int getAudioPath();

    /**
     * Mute/unmute FM sound by audio system
     *
     * @param mute true for mute, false for unmute
     */
    public void setMute(boolean mute);

    /**
     * Get mute status from audio system
     *
     * @return true for mute, false for unmute
     */
    public boolean getMute();
}
