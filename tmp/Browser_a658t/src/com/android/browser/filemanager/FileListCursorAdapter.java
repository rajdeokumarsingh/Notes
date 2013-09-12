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
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Files.FileColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.browser.filemanager.utils.Util;
import com.android.browser.R;

/**
 * @author haoanbang
 * 
 */
public class FileListCursorAdapter extends CursorAdapter implements ICursorAdapter {
	private LayoutInflater mInflater;
	private HashMap<Integer, FileInfo> mFileNameList = new HashMap<Integer, FileInfo>();
	private FileOperationHelper mFileOperationHelper;
	private FileSettingsHelper mFileSettingsHelper;
	private FileIconHelper mFileIconHelper;

	public FileListCursorAdapter(Context context, Cursor cursor, FileOperationHelper fileOperationHelper,
			FileSettingsHelper fileSettingsHelper) {
		super(context, cursor, false);
		mFileOperationHelper = fileOperationHelper;
		mFileSettingsHelper = fileSettingsHelper;
		mFileIconHelper = new FileIconHelper(context);
		mInflater = ((Activity) context).getLayoutInflater();
	}

	public ArrayList<FileInfo> getAllFileInfos() {
		ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
		int count = getCount();
		for (int i = 0; i < count; i++) {
			FileInfo fileInfo = getFileItem(i);
			if (fileInfo != null)
				fileInfos.add(getFileItem(i));
		}
		return fileInfos;
	}

	private FileInfo getFileInfo(Cursor cursor) {
		return (cursor == null || cursor.getCount() == 0) ? null : Util.getFileInfo(cursor.getString(cursor
				.getColumnIndex(FileColumns.DATA)));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return mInflater.inflate(R.layout.file_list_item, parent, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		FileInfo fileInfo = getFileItem(cursor.getPosition());
		if (fileInfo == null) {
			fileInfo = new FileInfo();
			fileInfo.dbId = cursor.getLong(cursor.getColumnIndex(FileColumns._ID));
			fileInfo.filePath = cursor.getString(cursor.getColumnIndex(FileColumns.DATA));
			fileInfo.fileName = Util.getNameFromFilepath(fileInfo.filePath);
			fileInfo.fileSize = cursor.getLong(cursor.getColumnIndex(FileColumns.SIZE));
			fileInfo.modifiedDate = cursor.getLong(cursor.getColumnIndex(FileColumns.DATE_MODIFIED));
		}

		if (fileInfo != null) {
			Util.setViewText(view, R.id.file_name, fileInfo.fileName);
			if (mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_ONLY_SHOW_FILENAME, false)) {
				view.findViewById(R.id.file_count).setVisibility(View.GONE);
				view.findViewById(R.id.modified_time).setVisibility(View.GONE);
				view.findViewById(R.id.file_size).setVisibility(View.GONE);
			} else {
				view.findViewById(R.id.file_count).setVisibility(View.VISIBLE);
				view.findViewById(R.id.modified_time).setVisibility(View.VISIBLE);
				view.findViewById(R.id.file_size).setVisibility(View.VISIBLE);
				Util.setViewText(view, R.id.file_count, fileInfo.isDir ? "(" + fileInfo.count + ")" : "");
				Util.setViewText(view, R.id.modified_time, Util.formatDateString(context, fileInfo.modifiedDate));
				Util.setViewText(view, R.id.file_size, (fileInfo.isDir ? "" : Util.convertStorage(fileInfo.fileSize)));
			}

			if (fileInfo.isDir) {
				((ImageView) view.findViewById(R.id.file_image_frame)).setVisibility(View.GONE);
				((ImageView) view.findViewById(R.id.file_image)).setImageResource(R.drawable.folder);
			} else {
				mFileIconHelper.setIcon(fileInfo, (ImageView) view.findViewById(R.id.file_image),
						(ImageView) view.findViewById(R.id.file_image_frame));
			}
			ImageView fileCheckBox = (ImageView) view.findViewById(R.id.file_checkbox);
			fileCheckBox.setVisibility(mFileOperationHelper.isOperation() ? View.VISIBLE : View.GONE);
			fileCheckBox.setImageResource(fileInfo.selected ? R.drawable.btn_check_on_holo_light
					: R.drawable.btn_check_off_holo_light);
			fileCheckBox.setTag(fileInfo);
			view.findViewById(R.id.file_checkbox_area).setOnClickListener(
					new FileOperationHelper.FileOnOperationClickListener(context, mFileOperationHelper));
		}
	}

	@Override
	public void changeCursor(Cursor cursor) {
		mFileNameList.clear();
		super.changeCursor(cursor);
	}

	public FileInfo getFileItem(int pos) {
		Integer position = Integer.valueOf(pos);
		FileInfo fileInfo = null;
		if (mFileNameList.containsKey(position)) {
			fileInfo = mFileNameList.get(position);
		}
		if (fileInfo != null)
			return fileInfo;
		Cursor cursor = (Cursor) getItem(pos);
		fileInfo = getFileInfo(cursor);
		if (fileInfo == null)
			return null;
		fileInfo.dbId = cursor.getLong(cursor.getColumnIndex(FileColumns._ID));
		mFileNameList.put(position, fileInfo);
		return fileInfo;
	}

}
