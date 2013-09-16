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

import android.test.AndroidTestCase;
import android.util.Log;

import com.pekall.fmradio.FMAudioInterface;
import com.pekall.fmradio.FMRadioService;
import com.pekall.fmradio.Factory;

/**
 * Test for FMAudioStubInterface and FMAudioDefaultImpl
 *
 */
public class FMAudioStubTest extends AndroidTestCase {
    // TODO: Investigate how to test audio focus

    protected static final String LOGTAG = "FMAudioInterfaceTest";
    private FMAudioInterface mAudio;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Log.i(LOGTAG, "setUp");

        Factory.useStubInterface(true);
        mAudio = Factory.getAudioInterface(new FMRadioService(), getContext());
    }

    public void testInit() {
        assertEquals(mAudio.getAudioPath(), FMAudioInterface.ROUTE_FM_HEADSET);
        assertEquals(mAudio.isAudioOn(), false);
        assertEquals(mAudio.getMute(), false);
    }

    public void testPowerOnAudio() {
        mAudio.powerOnAudio();
        assertEquals(mAudio.getAudioPath(), FMAudioInterface.ROUTE_FM_HEADSET);
        assertEquals(mAudio.isAudioOn(), true);
        assertEquals(mAudio.getMute(), false);
    }

    public void testPowerOffAudio() {
        mAudio.powerOnAudio();

        mAudio.powerOffAudio();
        assertEquals(mAudio.getAudioPath(), FMAudioInterface.ROUTE_FM_HEADSET);
        assertEquals(mAudio.isAudioOn(), false);
        assertEquals(mAudio.getMute(), false);
    }

    public void testMute() {
        mAudio.powerOnAudio();

        mAudio.setMute(true);
        assertEquals(mAudio.getMute(), true);

        mAudio.setMute(false);
        assertEquals(mAudio.getMute(), false);
    }

    public void testSpeaker() {
        mAudio.powerOnAudio();

        mAudio.setAudioOnSpeaker(true);
        assertEquals(mAudio.getAudioPath(), FMAudioInterface.ROUTE_FM_SPEAKER);

        mAudio.setAudioOnSpeaker(false);
        assertEquals(mAudio.getAudioPath(), FMAudioInterface.ROUTE_FM_HEADSET);
    }

    @Override
    protected void tearDown() throws Exception {
        mAudio.powerOffAudio();
        mAudio = null;
        super.tearDown();
    }
}
