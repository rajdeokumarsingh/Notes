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

import static com.pekall.fmradio.FMRadioService.FM_LAUNCHING;
import static com.pekall.fmradio.FMRadioService.FM_OPEN_FAILED;
import static com.pekall.fmradio.FMRadioService.FM_SERVICE_INIT;
import static com.pekall.fmradio.FMRadioService.FM_SERVICE_ON;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDiskIOException;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Simple widget to operate FM Radio by previous, next and switch buttons.
 */
public class FMRadioAppWidgetProvider extends AppWidgetProvider {
    private final float FREQ_UINT = 1000.0f;

    private static int currentFreq = FMRadio.LOW_FREQUENCY;

    private static FMRadioAppWidgetProvider sInstance;

    private static final String[] SAVED_PROJECTION = new String[] {
            FMDataProvider.PROJECTION_ID,
            FMDataProvider.PROJECTION_LAST_FREQ,
    };

    private static final String TAG = "FMRadioAppWidgetProdiver";

    static synchronized FMRadioAppWidgetProvider getInstance() {
        if (sInstance == null) {
            sInstance = new FMRadioAppWidgetProvider();
        }
        return sInstance;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // Initial state.
        initAppWidget(context, appWidgetIds);

        /*
         * Send broadcast intent to any running FMRadioService (if there is) so
         * it can update according to the service state.
         */
        Intent updateIntent = new Intent(FMRadioService.FM_APPWIDGET_UPDATE);
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        updateIntent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY);
        context.sendBroadcast(updateIntent);
    }

    /**
     * Initialize given widgets to default state, where we launch FMRadio on
     * default click and hide actions if there is no service that is running.
     */
    private void initAppWidget(Context context, int[] appWidgetIds) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.fmwidget);

        currentFreq = getInitialFreq(context);
        updateFreqDisplay(views);

        views.setImageViewResource(R.id.switch_button, R.drawable.appwidget_turn_on);

        linkButtons(context, views);
        pushUpdate(context, appWidgetIds, views);
    }

    private int getInitialFreq(Context context) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(FMDataProvider.SAVED_CONTENT_URI,
                    SAVED_PROJECTION, null, null, null);
        } catch (SQLiteDiskIOException e) {
            Log.i(TAG, "onQuery : " + e.toString());
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }

        if (cursor == null) {
            Log.i(TAG, "saved_cur is null get data failed");
            return FMRadio.LOW_FREQUENCY;
        }

        int freq = FMRadio.LOW_FREQUENCY;
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            freq = Integer.parseInt(cursor.getString(1));
        }

        cursor.close();
        cursor = null;

        return freq;
    }

    /**
     * Handle a change notification coming over from {@link FMRadioService}
     */
    void notifyChange(FMRadioService service, String what) {
        if (!hasInstances(service)) {
            return;
        }

        final RemoteViews views = new RemoteViews(service.getPackageName(), R.layout.fmwidget);
        if (what.equals(FM_OPEN_FAILED)) {
            views.setTextViewText(R.id.status_textview,
                    service.getString(R.string.service_start_error_msg));
        } else if (what.equals(FM_LAUNCHING)) {
            views.setTextViewText(R.id.status_textview,
                    service.getString(R.string.fmradio_waiting_for_power_on));
        } else {
            views.setTextViewText(R.id.status_textview, "else");
        }
        views.setTextViewText(R.id.status_textview, "none");

        performUpdate(service, null, what);
    }

    private void pushUpdate(Context context, int[] appWidgetIds, RemoteViews views) {
        // Update specific list of appWidgetIds if given, otherwise default to
        // all

        final AppWidgetManager gm = AppWidgetManager.getInstance(context);
        if (appWidgetIds != null) {
            gm.updateAppWidget(appWidgetIds, views);
        } else {
            gm.updateAppWidget(new ComponentName(context, this.getClass()), views);
        }
    }

    /**
     * Check against {@link AppWidgetManager} if there are any instances of this
     * widget.
     */
    private boolean hasInstances(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, this
                .getClass()));
        return (appWidgetIds.length > 0);
    }

    private void updateFreqDisplay(RemoteViews views) {
        float freqNum = (float) currentFreq / FREQ_UINT;

        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);
        df.applyPattern("0.0");

        views.setTextViewText(R.id.channel_textview, df.format(freqNum));
    }

    /**
     * Update all active widget instances by pushing changes
     */
    void performUpdate(FMRadioService service, int[] appWidgetIds) {
        performUpdate(service, appWidgetIds, null);
    }

    private void performUpdate(FMRadioService service, int[] appWidgetIds, String notification) {
        final RemoteViews views = new RemoteViews(service.getPackageName(), R.layout.fmwidget);

        int freq = service.getFMFreq();
        if (FMRadio.checkFMFreq(freq)) {
            currentFreq = freq;
            updateFreqDisplay(views);
        }

        // Update the appearance of switch button and status text view
        // according to the fm radio status.

        String statusText = "";
        if (notification != null) {
            if (notification.equals(FM_OPEN_FAILED)) {
                statusText = service.getString(R.string.service_start_error_msg);
            } else if (notification.equals(FM_LAUNCHING)) {
                statusText = service.getString(R.string.fmradio_waiting_for_power_on);
            }
        }

        views.setTextViewText(R.id.status_textview, statusText);

        final int serviceState = service.getState();
        if (serviceState == FM_SERVICE_INIT || serviceState == FM_SERVICE_ON) {
            views.setImageViewResource(R.id.switch_button, R.drawable.appwidget_turn_off);
        } else {
            views.setImageViewResource(R.id.switch_button, R.drawable.appwidget_turn_on);
        }

        // Link actions buttons to intents
        linkButtons(service, views);

        pushUpdate(service, appWidgetIds, views);
    }

    /**
     * Link up various button actions using {@link PendingIntents}.
     */
    private void linkButtons(Context context, RemoteViews views) {
        Intent intent;
        PendingIntent pendingIntent;

        final ComponentName serviceName = new ComponentName(context, FMRadioService.class);

        // Previous channel button
        intent = new Intent(FMRadioService.FM_PREV_CHANNEL);
        intent.setComponent(serviceName);
        pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.previous_channel, pendingIntent);

        // Frequency text view.
        intent = new Intent(context, FMRadio.class);
        pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.channel_textview, pendingIntent);

        // Next channel button
        intent = new Intent(FMRadioService.FM_NEXT_CHANNEL);
        intent.setComponent(serviceName);
        pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.next_channel, pendingIntent);

        // Switch button
        intent = new Intent(FMRadioService.FM_TOGGLE_ONOFF);
        intent.setComponent(serviceName);
        intent.putExtra("freq", currentFreq);
        pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.switch_button, pendingIntent);
    }

}
