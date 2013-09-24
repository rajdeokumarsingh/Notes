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

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * FM Content Provider
 */
public class FMDataProvider extends ContentProvider {
    private static final String TAG = "FMDataProvider";

    private static final String DATABASE_NAME = "fmradio.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CHANNELS = "channels";
    private static final String TABLE_LAST_STATUS = "last_status";

    private static final UriMatcher sUriMatcher;
    private static final int CHANNELS = 1;
    private static final int CHANNELS_ID = 2;
    private static final int LAST_STATUS = 3;
    private static final int LAST_STATUS_ID = 4;

    static final String AUTHORITY = "com.pekall.provider.fmradio";
    static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_CHANNELS);

    public static final Uri SAVED_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
            + TABLE_LAST_STATUS);

    static final String PROJECTION_ID = "_id";
    static final String PROJECTION_CH_FREQ = "channel_freq";
    static final String PROJECTION_CH_NAME = "channel_name";
    static final String PROJECTION_LAST_FREQ = "last_freq";
    static final String PROJECTION_LAST_CH_NUM = "last_channel_num";
    static final String PROJECTION_FULL_SCANED = "full_scanned";

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(FMDataProvider.AUTHORITY, TABLE_CHANNELS, CHANNELS);
        sUriMatcher.addURI(FMDataProvider.AUTHORITY, TABLE_CHANNELS + "/#", CHANNELS_ID);
        sUriMatcher.addURI(FMDataProvider.AUTHORITY, TABLE_LAST_STATUS, LAST_STATUS);
        sUriMatcher.addURI(FMDataProvider.AUTHORITY, TABLE_LAST_STATUS + "/#", LAST_STATUS_ID);
    }

    private DatabaseHelper mOpenHelper;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        final Context mContext;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mContext = context;
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL("CREATE TABLE " + TABLE_CHANNELS + " ("
                        + PROJECTION_ID + " INTEGER PRIMARY KEY,"
                        + PROJECTION_CH_FREQ + " FLOAT,"
                        + PROJECTION_CH_NAME + " TEXT" + ");");

                db.execSQL("CREATE TABLE " + TABLE_LAST_STATUS + " ("
                        + PROJECTION_ID + " INTEGER,"
                        + PROJECTION_LAST_CH_NUM + " INTEGER,"
                        + PROJECTION_LAST_FREQ + " INTEGER,"
                        + PROJECTION_FULL_SCANED + " BOOLEAN"
                        + ");");

                db.execSQL("insert into "
                        + TABLE_LAST_STATUS
                        + " (" + PROJECTION_ID
                        + ", " + PROJECTION_LAST_CH_NUM
                        + ", " + PROJECTION_LAST_FREQ
                        + ", " + PROJECTION_FULL_SCANED
                        + ") "
                        + "values('0', '0', '87500', 'false');");
            } catch (SQLException e) {
                Log.e(TAG, e.toString());
            }

            addPresetChannels(db);
        }

        private void addPresetChannels(SQLiteDatabase db) {
            try {
                DecimalFormat myFormat = (DecimalFormat) NumberFormat
                        .getInstance(Locale.ENGLISH);
                myFormat.applyPattern("0.0");
                for (int i = 0; i < Util.MAX_CHANNEL_NUM; i++) {
                    float f = (i * 0.5f) + 87.5f;
                    StringBuilder builder = new StringBuilder();
                    String freq = "";
                    if (Util.displayPresetChannels(mContext)) {
                        freq = myFormat.format(f);
                    }
                    builder.append("insert into ")
                            .append(TABLE_CHANNELS)
                            .append(" (" + PROJECTION_ID + ", "
                                    + PROJECTION_CH_FREQ + ", "
                                    + PROJECTION_CH_NAME + ") values('")
                            .append(Integer.toString(i)).append("', '")
                            .append(freq).append("', '")
                            .append(Integer.toString(i)).append("');");
                    Log.i(TAG, "insert freq: " + myFormat.format(f));
                    Log.i(TAG, "insert freq: " + builder.toString());
                    db.execSQL(builder.toString());
                }
            } catch (SQLException e) {
                Log.e(TAG, e.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c;
        switch (sUriMatcher.match(uri)) {
            case CHANNELS:
                Log.d(TAG, "set channel table: " + TABLE_CHANNELS);
                qb.setTables(TABLE_CHANNELS);
                break;
            case CHANNELS_ID:
                Log.d(TAG, "set channel table: " + TABLE_CHANNELS);
                qb.setTables(TABLE_CHANNELS);

                break;
            case LAST_STATUS:
                Log.d(TAG, "set save table: " + TABLE_LAST_STATUS);
                qb.setTables(TABLE_LAST_STATUS);
                break;
            case LAST_STATUS_ID:
                Log.d(TAG, "set save table: " + TABLE_LAST_STATUS);
                qb.setTables(TABLE_LAST_STATUS);

                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        // Get the database and run the query
        c = qb.query(db, projection, selection, selectionArgs, null, null, null);

        // Tell the cursor what uri to watch, so it knows when its source data
        // changes
        if (c != null) {
            c.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        String field1 = initialValues.get(PROJECTION_ID).toString();
        String field2 = initialValues.get(PROJECTION_CH_FREQ).toString();
        String field3 = initialValues.get(PROJECTION_CH_NAME).toString();

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if (field1 != null && field2 != null && field3 != null) {
            String sql = "insert into " + TABLE_CHANNELS + " (" + PROJECTION_ID
                    + ", " + PROJECTION_CH_FREQ + ", " + PROJECTION_CH_NAME + ") "
                    + "values('" + field1 + "', '" + field2 + "','" + field3 + "');";
            try {
                db.execSQL(sql);
            } catch (SQLException e) {
                Log.e(TAG, e.toString());
            }
        }
        return uri;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        return db.delete(where, whereArgs[0] + "='" + whereArgs[1] + "'", null);
    }

    @Override
    public int update(Uri uri, ContentValues values, String where,
            String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
            case CHANNELS:
                count = db.update(TABLE_CHANNELS, values, where, whereArgs);
                break;
            case CHANNELS_ID:
                count = db.update(TABLE_CHANNELS, values, where, whereArgs);
                break;
            case LAST_STATUS:
                count = db.update(TABLE_LAST_STATUS, values, where, whereArgs);
                break;
            case LAST_STATUS_ID:
                count = db.update(TABLE_LAST_STATUS, values, where, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return count;
    }
}
