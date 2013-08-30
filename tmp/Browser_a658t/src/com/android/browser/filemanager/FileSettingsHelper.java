/* ---------------------------------------------------------------------------------------------
 *
 *            Capital Alliance Software Confidential Proprietary
 *            (c) Copyright CAS 201{x}, All Rights Reserved
 *                          www.pekall.com
 *
 * ----------------------------------------------------------------------------------------------
 */
package com.android.browser.filemanager;

import java.util.HashMap;

import com.android.browser.filemanager.FileCategoryHelper.FileCategory;
import com.android.browser.filemanager.SoftCursor.SortType;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * @author haoanbang
 * 
 */
public class FileSettingsHelper {
	public static final String KEY_SHOW_HIDEFILE = "showhidefile";
	public static final String KEY_SHOW_VIEW_COUNT = "showViewCount";
	public static final String KEY_ONLY_SHOW_FILENAME = "onlyshowname";
	public static final String KEY_SORT_DESC = "sortdesc";
	public static final String KEY_SORT_TYPE = "sorttype";

	public static HashMap<Integer, SortType> getSortTypeByType = new HashMap<Integer, SortType>();
	public static HashMap<SortType, Integer> getTypeBySortType = new HashMap<SortType, Integer>();

	static {
		getSortTypeByType.put(1, SortType.name);
		getSortTypeByType.put(2, SortType.size);
		getSortTypeByType.put(3, SortType.date);
		getSortTypeByType.put(4, SortType.type);
		getTypeBySortType.put(SortType.name, 1);
		getTypeBySortType.put(SortType.size, 2);
		getTypeBySortType.put(SortType.date, 3);
		getTypeBySortType.put(SortType.type, 4);
	}

	public SortType getSortType() {
		return getSortTypeByType.get(getInt(KEY_SORT_TYPE, 1));
	}

	public void putSortType(SortType sortType) {
		putInt(KEY_SORT_TYPE, getTypeBySortType.get(sortType));
	}

	private static FileSettingsHelper mInstance;
	private SharedPreferences mSettings;
	private Editor mSettingsEditor;
	private boolean mPreShowHideSettings;
	private boolean mPreShowFileNameSettings;
	private boolean mPreSortDescSettings;

	private FileSettingsHelper(Context context) {
		mSettings = PreferenceManager.getDefaultSharedPreferences(context);
		mSettingsEditor = mSettings.edit();
		updatePreSettings();
	}

	public static FileSettingsHelper getInstance(Context context) {
		if (mInstance == null)
			mInstance = new FileSettingsHelper(context);
		return mInstance;
	}

	public boolean getBoolean(String key, boolean defValue) {
		return mSettings.getBoolean(key, defValue);
	}

	public int getInt(String key, int defValue) {
		return mSettings.getInt(key, defValue);
	}

	public void putBoolean(String key, boolean value) {
		mSettingsEditor.putBoolean(key, value).commit();
	}

	public void putInt(String key, int value) {
		mSettingsEditor.putInt(key, value).commit();
	}

	private void updatePreSettings() {
		mPreShowHideSettings = getBoolean(KEY_SHOW_HIDEFILE, false);
		mPreShowFileNameSettings = getBoolean(KEY_ONLY_SHOW_FILENAME, false);
		mPreSortDescSettings = getBoolean(KEY_SORT_DESC, false);
	}

	/**
	 * @return
	 */
	public boolean isSettingsChange() {
		boolean isChange = (mPreShowHideSettings != getBoolean(KEY_SHOW_HIDEFILE, false))
				|| (mPreShowFileNameSettings != getBoolean(KEY_ONLY_SHOW_FILENAME, false))
				|| (mPreSortDescSettings != getBoolean(KEY_SORT_DESC, false));
		if (isChange) {
			updatePreSettings();
		}
		return isChange;
	}
}
