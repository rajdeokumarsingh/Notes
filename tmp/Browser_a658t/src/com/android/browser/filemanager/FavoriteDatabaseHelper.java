/*
 * Copyright (c) 2010-2011, The MiCode Open Source Community (www.micode.net)
 *
 * This file is part of FileExplorer.
 *
 * FileExplorer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FileExplorer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SwiFTP.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.android.browser.filemanager;

import java.io.File;
import java.util.ArrayList;

import com.android.browser.filemanager.utils.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore.Files.FileColumns;

public class FavoriteDatabaseHelper extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "file_manager";

	private final static int DATABASE_VERSION = 1;

	private final static String TABLE_NAME = "favorite";

	public final static String FIELD_ID = FileColumns._ID;

	public final static String FIELD_TITLE = FileColumns.TITLE;

	public final static String FIELD_FILEPATH = FileColumns.DATA;

	public final static int FIELD_ID_INDEX = 0;

	public final static int FIELD_TITLE_INDEX = 1;

	public final static int FIELD_FILEPATH_INDEX = 2;

	private boolean firstCreate;

	private FavoriteDatabaseListener mListener;

	private static FavoriteDatabaseHelper instance;

	public interface FavoriteDatabaseListener {
		void onFavoriteDatabaseChanged();
	}

	public FavoriteDatabaseHelper(Context context, FavoriteDatabaseListener listener) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		instance = this;
		mListener = listener;
	}

	public static FavoriteDatabaseHelper getInstance() {
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "Create table " + TABLE_NAME + "(" + FIELD_ID + " integer primary key autoincrement,"
				+ FIELD_TITLE + " text, " + FIELD_FILEPATH + " text );";
		db.execSQL(sql);
		firstCreate = true;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = " DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

	public boolean isFirstCreate() {
		return firstCreate;
	}

	public boolean isFavorite(String path) {
		String selection = FIELD_FILEPATH + "=?";
		String[] selectionArgs = new String[] { path };
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
		if (cursor == null)
			return false;
		boolean ret = cursor.getCount() > 0;
		cursor.close();
		return ret;
	}

	public ArrayList<FileInfo> query() {
		ArrayList<FileInfo> datas = new ArrayList<FileInfo>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			String filePath = cursor.getString(cursor.getColumnIndex(FIELD_FILEPATH));
			FileInfo fileInfo = Util.getFileInfo(filePath);
			if (fileInfo != null && new File(fileInfo.filePath).exists()) {
				datas.add(fileInfo);
			}
		}
		return datas;
	}

	public long insert(String title, String location) {
		if (isFavorite(location))
			return -1;

		SQLiteDatabase db = this.getWritableDatabase();
		long ret = db.insert(TABLE_NAME, null, createValues(title, location));
		mListener.onFavoriteDatabaseChanged();
		return ret;
	}

	public void reflushData() {
		mListener.onFavoriteDatabaseChanged();
	}

	public long getCount() {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns = new String[] { FIELD_FILEPATH };
		Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
		int count = 0;
		while (cursor.moveToNext()) {
			if (new File(cursor.getString(0)).exists()) {
				count++;
			}
		}
		return count;
	}

	public void delete(long id, boolean notify) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_ID + "=?";
		String[] whereValue = { Long.toString(id) };
		db.delete(TABLE_NAME, where, whereValue);

		if (notify)
			mListener.onFavoriteDatabaseChanged();
	}

	public void delete(String location) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_FILEPATH + "=?";
		String[] whereValue = { location };
		db.delete(TABLE_NAME, where, whereValue);
		mListener.onFavoriteDatabaseChanged();
	}

	public void update(int id, String title, String location) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_ID + "=?";
		String[] whereValue = { Integer.toString(id) };
		db.update(TABLE_NAME, createValues(title, location), where, whereValue);
		mListener.onFavoriteDatabaseChanged();
	}

	private ContentValues createValues(String title, String location) {
		ContentValues cv = new ContentValues();
		cv.put(FIELD_TITLE, title);
		cv.put(FIELD_FILEPATH, location);
		return cv;
	}
}
