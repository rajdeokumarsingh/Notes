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
import android.util.Log;

/**
 * Stub Audio Interface implementation, just for testing.
 */
public class FMAudioStubInterface extends FMAudioDefaultImpl {
    private static final String LOGTAG = "FMAudioStubInterface";

    private boolean mMute;
    private boolean mAudioOn;

    public FMAudioStubInterface(FMRadioService service, Context context) {
        super(service, context);
        Log.i(LOGTAG, "constructor");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMute(boolean mute) {
        Log.i(LOGTAG, "setMute: " + mute);
        mMute = mute;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getMute() {
        Log.i(LOGTAG, "getMute: " + mMute);
        return mMute;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void enableAudio() {
        Log.i(LOGTAG, "enableAudio");
        mAudioOn = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void disableAudio() {
        Log.i(LOGTAG, "disableAudio");
        mAudioOn = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAudioOn() {
        return mAudioOn;
    }
}
