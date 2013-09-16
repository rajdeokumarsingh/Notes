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

public class Factory {
    private static boolean mUseStub;

    /**
     * Set interface type
     *
     * @param stub true for stub interface which is for test purpose, false for
     *            real FMR interface
     */
    public static void useStubInterface(boolean stub) {
        mUseStub = stub;
    }

    /**
     * Create audio interface.
     *
     * @param service FM Radio service
     * @param context TODO
     * @return Audio interface implementation
     */
    public static FMAudioInterface getAudioInterface(FMRadioService service, Context context) {
        if (!mUseStub) {
            return new FMAudioDeviceInterface(service, context);
        } else {
            return new FMAudioStubInterface(service, context);
        }
    }

    /**
     * Create FM radio device interface.
     *
     * @param service FM Radio service
     * @return FM radio interface implementation
     */
    public static FMRadioInterface getFMRadioInterface(FMRadioService service) {
        if (!mUseStub) {
            return new FMRadioNativeInterface(service);
        } else {
            return new FMRadioStubInterface(service);
        }
    }
}
