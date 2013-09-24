/*
 * Copyright (C) 2013 Capital Alliance Software LTD (Pekall)
 * Copyright (C) 2007 The Android Open Source Project
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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

/**
 * Media Button Receiver to handle Hook key event.
 */
public class MediaButtonIntentReceiver extends BroadcastReceiver {
    private static final String LOGTAG = "MediaButtonIntentReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();

        Log.i(LOGTAG, "onReceive: " + intentAction);
        if (Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) {
            KeyEvent event = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (event == null) {
                return;
            }

            int keycode = event.getKeyCode();
            int action = event.getAction();
            Log.i(LOGTAG, "onReceive key event: " + keycode + ", action: " + action);

            if (action == KeyEvent.ACTION_DOWN && keycode == KeyEvent.KEYCODE_HEADSETHOOK) {
                Intent i = new Intent();
                i.setAction(FMRadioService.SERVICE_CMD);
                i.putExtra(FMRadioService.CMD_NAME, FMRadioService.CMD_SEEK);
                context.sendBroadcast(i);
                Log.i(LOGTAG, "onReceive broadcast sent: " + FMRadioService.CMD_SEEK);
            }
        }
    }
}
