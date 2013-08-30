/* ---------------------------------------------------------------------------------------------
 *
 *            Capital Alliance Software Confidential Proprietary
 *            (c) Copyright CAS 201{x}, All Rights Reserved
 *                          www.pekall.com
 *
 * ----------------------------------------------------------------------------------------------
 */
package com.android.browser.filemanager;

import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Pattern;

import android.text.TextUtils;

import com.android.browser.filemanager.SoftCursor.SortType;
import com.android.browser.filemanager.utils.Util;

/**
 * @author haoanbang
 * 
 */
public class FileSortHelper {

	private static FileSortHelper instance;
	private FileSettingsHelper mFileSettingsHelper;
	private boolean mIsDesc;

	private HashMap<SortType, Comparator<FileInfo>> mComparatorList = new HashMap<SortType, Comparator<FileInfo>>();

	private FileSortHelper(FileSettingsHelper fileSettingsHelper) {
		mFileSettingsHelper = fileSettingsHelper;
		mIsDesc = mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_SORT_DESC, false);
		mComparatorList.put(SortType.name, comparatorByName);
		mComparatorList.put(SortType.size, comparatorBySize);
		mComparatorList.put(SortType.date, comparatorByDate);
		mComparatorList.put(SortType.type, comparatorByType);
	}

	public Comparator<FileInfo> getComparator(SortType sortType) {
		mIsDesc = mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_SORT_DESC, false);
		return mComparatorList.get(sortType);
	}

	public static FileSortHelper getInstance(FileSettingsHelper fileSettingsHelper) {
		if (instance == null)
			instance = new FileSortHelper(fileSettingsHelper);
		return instance;
	}

	private abstract class FileComparator implements Comparator<FileInfo> {

		@Override
		public int compare(FileInfo object1, FileInfo object2) {
			if (object1.isDir == object2.isDir) {
				return doCompare(object1, object2);
			}
			return object1.isDir ? -1 : 1;
		}

		protected abstract int doCompare(FileInfo object1, FileInfo object2);
	}

	public static final String chMatches = "[\u4e00-\u9fa5]";

	private Comparator<FileInfo> comparatorByName = new FileComparator() {

		@Override
		protected int doCompare(FileInfo object1, FileInfo object2) {
			return orderByName(object1, object2);
		}
	};

	private int orderByName(FileInfo object1, FileInfo object2) {
		if (String.valueOf(object1.fileName.substring(0, 1)).matches(chMatches)
				&& String.valueOf(object2.fileName.substring(0, 1)).matches(chMatches)) {
			String name1 = HanziToPinyin.getInstance().get(object1.fileName.substring(0, 1)).get(0).target;
			String name2 = HanziToPinyin.getInstance().get(object2.fileName.substring(0, 1)).get(0).target;
			if (mIsDesc) {
				return name2.compareToIgnoreCase(name1);
			} else {
				return name1.compareToIgnoreCase(name2);
			}
		} else {
			if (mIsDesc) {
				return object2.fileName.compareToIgnoreCase(object1.fileName);
			} else {
				return object1.fileName.compareToIgnoreCase(object2.fileName);
			}
		}
	}

	private Comparator<FileInfo> comparatorBySize = new FileComparator() {

		@Override
		protected int doCompare(FileInfo object1, FileInfo object2) {
			if (object1.isDir && object2.isDir) {
				return orderByName(object1, object2);
			}
			return longToCompareInt(object1.fileSize - object2.fileSize);
		}
	};

	private Comparator<FileInfo> comparatorByDate = new FileComparator() {

		@Override
		protected int doCompare(FileInfo object1, FileInfo object2) {
			return longToCompareInt(object1.modifiedDate - object2.modifiedDate);
		}
	};

	private Comparator<FileInfo> comparatorByType = new FileComparator() {

		@Override
		protected int doCompare(FileInfo object1, FileInfo object2) {
			if (object1.isDir && object2.isDir) {
				return orderByName(object1, object2);
			}
			int result = Util.getExtFromFilename(object1.fileName).compareToIgnoreCase(
					Util.getExtFromFilename(object2.fileName));
			if (result != 0)
				return result;

			return Util.getNameFromFilename(object1.fileName).compareToIgnoreCase(
					Util.getNameFromFilename(object2.fileName));
		}
	};

	private int longToCompareInt(long result) {
		if (mIsDesc) {
			return result < 0 ? 1 : (result > 0 ? -1 : 0);
		} else {
			return result > 0 ? 1 : (result < 0 ? -1 : 0);
		}
	}
}
