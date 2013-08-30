/* ---------------------------------------------------------------------------------------------
 *
 *            Capital Alliance Software Confidential Proprietary
 *            (c) Copyright CAS 201{x}, All Rights Reserved
 *                          www.pekall.com
 *
 * ----------------------------------------------------------------------------------------------
 */
package com.android.browser.filemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.provider.MediaStore.Files.FileColumns;

import com.android.browser.filemanager.SoftCursor.SortEntry;

/**
 * @author haoanbang
 * 
 */
public class SoftCursor extends CursorWrapper implements Comparator<SortEntry> {
	private int mPos = 0;
	private Cursor mCursor;
	private ArrayList<SortEntry> sortList = new ArrayList<SortEntry>();
	public static final int TYPE_DIRECTORY = 0;
	public static final int TYPE_OTHERS = 1;

	public static class SortEntry {
		public int type;
		public int order;
	}

	public SoftCursor(Cursor cursor) {
		super(cursor);
		mCursor = cursor;
		if (mCursor != null && mCursor.getCount() > 0) {
			int i = 0;
			int column = cursor.getColumnIndexOrThrow(FileColumns.DATA);
			for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext(), i++) {
				SortEntry sortKey = new SortEntry();
				File file = new File(cursor.getString(column));
//				if(!file.exists()){
//					continue;
//				}
				sortKey.type = file.isDirectory() ? TYPE_DIRECTORY : TYPE_OTHERS;
				sortKey.order = i;
				sortList.add(sortKey);
			}
		}
		Collections.sort(sortList, this);
	}
	
	public boolean moveToPosition(int position) {
		if (position >= 0 && position < sortList.size()) {
			mPos = position;
			int order = sortList.get(position).order;
			return super.moveToPosition(order);
		}
		if (position < 0) {
			mPos = -1;
		}
		if (position >= sortList.size()) {
			mPos = sortList.size();
		}
		return super.moveToPosition(position);
	}

	public boolean moveToFirst() {
		return moveToPosition(0);
	}

	public boolean moveToLast() {
		return moveToPosition(getCount() - 1);
	}

	public boolean moveToNext() {
		return moveToPosition(mPos + 1);
	}

	public boolean moveToPrevious() {
		return moveToPosition(mPos - 1);
	}

	public boolean move(int offset) {
		return moveToPosition(mPos + offset);
	}

	public int getPosition() {
		return mPos;
	}

	public enum SortType {
		name, size, date, type
	}

	public int compare(SortEntry lhs, SortEntry rhs) {
		return lhs.type - rhs.type;
	}

}
