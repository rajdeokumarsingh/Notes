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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Simple wrapper of FM content provider
 */
public class ContentHelper {
    private static final String LOGTAG = "ContentHelper";

    private static final int PRJ_ID_ID = 0;
    private static final int PRJ_ID_CH_FREQ = 1;
    private static final int PRJ_ID_CH_NAME = 2;
    static final String[] PROJECTION = new String[] {
            FMDataProvider.PROJECTION_ID,
            FMDataProvider.PROJECTION_CH_FREQ,
            FMDataProvider.PROJECTION_CH_NAME,
    };

    private static final String[] SAVED_PROJECTION = new String[] {
            FMDataProvider.PROJECTION_ID,
            FMDataProvider.PROJECTION_LAST_CH_NUM,
            FMDataProvider.PROJECTION_LAST_FREQ,
            FMDataProvider.PROJECTION_FULL_SCANED,
    };

    /**
     * Save the last used frequency.
     *
     * @param content Android context
     * @param frequency frequency to be saved
     */
    public static void saveCurrentFreq(Context context, int frequency) {
        if (DebugFlags.LOG_CONTENT) {
            Log.i(LOGTAG, "Save frequency: " + frequency);
        }

        ContentValues cv = new ContentValues();
        cv.put(FMDataProvider.PROJECTION_LAST_FREQ, frequency);
        try {
            context.getContentResolver().update(FMDataProvider.SAVED_CONTENT_URI, cv,
                    FMDataProvider.PROJECTION_ID + "=0", null);
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "", e);
        }
    }

    /**
     * Get saved frequency, see {@link #saveCurrentFreq()}
     *
     * @param content android context
     * @return saved frequency
     */
    public static int loadCurrentFreq(Context context) {
        Cursor cursor = null;
        int frequency = FMRadio.LOW_FREQUENCY;
        try {
            cursor = context.getContentResolver().query(
                    FMDataProvider.SAVED_CONTENT_URI, SAVED_PROJECTION, null, null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                frequency = Integer.parseInt(cursor.getString(2));
            }
        } catch (SQLiteDiskIOException e) {
            Log.e(LOGTAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return frequency;
    }

    /**
     * Save UI status to content provider
     *
     * @param context android context
     * @param channel current channel number in UI list
     * @param frequency current frequency
     * @param scanned whether user has done a full scan
     * @param rssi current rssi threshold
     */
    public static void saveConfig(Context context, int channel, int frequency,
            boolean scanned, int rssi) {

        ContentValues cv = new ContentValues();
        cv.put(FMDataProvider.PROJECTION_LAST_CH_NUM, channel);
        cv.put(FMDataProvider.PROJECTION_LAST_FREQ, frequency);

        if (scanned) {
            cv.put(FMDataProvider.PROJECTION_FULL_SCANED,
                    Boolean.toString(true));
        }
        try {
            context.getContentResolver().update(FMDataProvider.SAVED_CONTENT_URI, cv,
                    FMDataProvider.PROJECTION_ID + "=0", null);
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "", e);
        }
    }

    /**
     * Restore saved UI status
     *
     * @param status ui status, see {@link FMRadio.UiStatus}
     */
    public static void loadConfig(Context context, FMRadio.UiStatus status) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(
                    FMDataProvider.SAVED_CONTENT_URI, SAVED_PROJECTION, null, null,
                    null);

            if (cursor != null && cursor.moveToFirst()) {
                status.mLastPosition = Integer.parseInt(cursor.getString(1));
                status.mCurrentFreq = Integer.parseInt(cursor.getString(2));
                status.mIsScannedBefore = Boolean.parseBoolean(cursor.getString(3));

                if (DebugFlags.LOG_CONTENT) {
                    Log.i(LOGTAG, "last channel: " + status.mLastPosition);
                    Log.i(LOGTAG, "last freq: " + status.mCurrentFreq);
                    Log.i(LOGTAG, "is first scaned: " + status.mIsScannedBefore);
                }
            }
        } catch (SQLiteDiskIOException e) {
            Log.e(LOGTAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
    }

    /**
     * Get saved channels information
     *
     * @param context android context
     * @param resultInfo store channel list
     * @return
     */
    public static boolean getChannelList(Context context,
            ArrayList<FMRadio.FMInfo> resultInfo) {

        if (DebugFlags.LOG_CONTENT) {
            Log.i(LOGTAG, "getChannelList");
        }

        Cursor cursor = null;
        boolean ret = false;
        try {
            cursor = context.getContentResolver().query(FMDataProvider.CONTENT_URI,
                    PROJECTION, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    int id = cursor.getInt(PRJ_ID_ID);
                    String freq = cursor.getString(PRJ_ID_CH_FREQ);
                    String name = cursor.getString(PRJ_ID_CH_NAME);
                    FMRadio.FMInfo info = ((FMRadio) context).createFmInfo(id, freq, name);
                    resultInfo.add(info);
                    cursor.moveToNext();
                }
                ret = true;
            }
        } catch (SQLiteDiskIOException e) {
            Log.e(LOGTAG, "", e);
            ret = false;
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return ret;
    }

    /**
     * Check whether FM database is empty
     *
     * @param context android context
     * @return true if empty, false if not
     */
    public static boolean isDBEmpty(Context context) {
        Cursor cursor = null;
        boolean ret = false;
        try {
            cursor = context.getContentResolver().query(FMDataProvider.CONTENT_URI,
                    PROJECTION, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int i = 0;
                while (!cursor.isAfterLast()) {
                    if (cursor.getString(PRJ_ID_CH_FREQ).equals("")) {
                        i++;
                        if (i == Util.MAX_CHANNEL_NUM) {
                            Log.i(LOGTAG, "DB is Empty!");
                            ret = true;
                            break;
                        }
                    }
                    cursor.moveToNext();
                }
            }
        } catch (SQLiteDiskIOException e) {
            Log.e(LOGTAG, "", e);
            ret = false;
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return ret;
    }

    /**
     * Clear FM database
     *
     * @param context android context
     */
    public static void clearDB(Context context) {
        for (int i = 0; i < Util.MAX_CHANNEL_NUM; i++) {
            ContentValues cv = new ContentValues();
            cv.put(FMDataProvider.PROJECTION_ID, i);
            cv.put(FMDataProvider.PROJECTION_CH_FREQ, "");
            cv.put(FMDataProvider.PROJECTION_CH_NAME, "");
            try {
                context.getContentResolver().update(FMDataProvider.CONTENT_URI, cv,
                        FMDataProvider.PROJECTION_ID + "=" + i, null);
            } catch (SQLiteDiskIOException e) {
                Log.e(LOGTAG, "", e);
            }
        }
    }

    /**
     * Save a channel information to FM database
     *
     * @param context android context
     * @param id database id of the channel
     * @param freq frequency of the channel, -1 for clear channel
     */
    public static void saveChannelInfo(Context context, int id, int freq) {
        if (DebugFlags.LOG_CONTENT) {
            Log.i(LOGTAG, "saveChannelInfo, id: " + id + ", freq: " + freq);
        }

        ContentValues cv = new ContentValues();
        cv.put(FMDataProvider.PROJECTION_ID, id);
        cv.put(FMDataProvider.PROJECTION_CH_NAME, "");
        if (freq > 0) {
            DecimalFormat format = (DecimalFormat) NumberFormat
                    .getInstance(Locale.ENGLISH);
            format.applyPattern("0.0");
            cv.put(FMDataProvider.PROJECTION_CH_FREQ, String.valueOf(format.format(freq / 1000.0f)));
        } else {
            cv.put(FMDataProvider.PROJECTION_CH_FREQ, "");
        }
        try {
            context.getContentResolver().update(FMDataProvider.CONTENT_URI, cv,
                    FMDataProvider.PROJECTION_ID + "=" + id, null);
        } catch (SQLiteDiskIOException e) {
            Log.e(LOGTAG, "", e);
        }
    }

    /**
     * Save information of FM record file to media database
     *
     * @param context android context
     * @param recordFile record file
     * @param duration duration of the record file
     */
    public static void saveRecordingFile(Context context, File recordFile,
            long duration) {
        duration = duration < 0 ? 0 : duration;
        String fileName = recordFile.getName();

        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Audio.Media.IS_MUSIC, "1");
        cv.put(MediaStore.Audio.Media.TITLE,
                fileName.substring(0, fileName.lastIndexOf(".")));
        cv.put(MediaStore.Audio.Media.DATA, recordFile.getAbsolutePath());
        cv.put(MediaStore.Audio.Media.DATE_ADDED,
                (int) (System.currentTimeMillis() / 1000));
        cv.put(MediaStore.Audio.Media.DATE_MODIFIED,
                (int) (System.currentTimeMillis() / 1000));
        cv.put(MediaStore.Audio.Media.DURATION, duration);
        cv.put(MediaStore.Audio.Media.SIZE, (int) (recordFile.length()));
        cv.put(MediaStore.Audio.Media.MIME_TYPE, "audio/amr");
        cv.put(MediaStore.Audio.Media.ARTIST, "<unknown>");
        cv.put(MediaStore.Audio.Media.ALBUM, "FmRecords");

        if (DebugFlags.LOG_CONTENT) {
            Log.d(LOGTAG, "Inserting fm record: " + cv.toString());
        }
        Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        context.getContentResolver().insert(base, cv);
    }
}
