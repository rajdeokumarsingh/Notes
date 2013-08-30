/* ---------------------------------------------------------------------------------------------
 *
 *            Capital Alliance Software Confidential Proprietary
 *            (c) Copyright CAS 201{x}, All Rights Reserved
 *                          www.pekall.com
 *
 * ----------------------------------------------------------------------------------------------
 */
package com.android.browser.filemanager;

import java.util.List;

import com.android.browser.filemanager.utils.Util;
import com.android.browser.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author haoanbang
 * 
 */
public class FileListAdapter extends ArrayAdapter<FileInfo> {
	private FileOperationHelper mFileOperationHelper;
	private FileSettingsHelper mFileSettingsHelper;
	private FileIconHelper mFileIconHelper;
	private LayoutInflater mInflater;
	private Context mContext;

	public FileListAdapter(Context context, FileOperationHelper fileOperationHelper,
			FileSettingsHelper fileSettingsHelper, List<FileInfo> objects) {
		super(context, 0, objects);
		mContext = context;
		mFileOperationHelper = fileOperationHelper;
		mFileSettingsHelper = fileSettingsHelper;
		mFileIconHelper = new FileIconHelper(context);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FileInfo fileInfo = getItem(position);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.file_list_item, null);
			holder.fileImageFrame = (ImageView) convertView.findViewById(R.id.file_image_frame);
			holder.fileImage = (ImageView) convertView.findViewById(R.id.file_image);
			holder.fileName = (TextView) convertView.findViewById(R.id.file_name);
			holder.fileCount = (TextView) convertView.findViewById(R.id.file_count);
			holder.fileModifiedDate = (TextView) convertView.findViewById(R.id.modified_time);
			holder.fileSize = (TextView) convertView.findViewById(R.id.file_size);
			holder.fileCheckBoxArea = convertView.findViewById(R.id.file_checkbox_area);
			holder.fileCheckBox = (ImageView) convertView.findViewById(R.id.file_checkbox);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (fileInfo != null) {
			holder.fileName.setText(fileInfo.fileName);
			if (mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_ONLY_SHOW_FILENAME, false)) {
				holder.fileCount.setVisibility(View.GONE);
				holder.fileModifiedDate.setVisibility(View.GONE);
				holder.fileSize.setVisibility(View.GONE);
			} else {
				holder.fileCount.setVisibility(View.VISIBLE);
				holder.fileModifiedDate.setVisibility(View.VISIBLE);
				holder.fileSize.setVisibility(View.VISIBLE);
				holder.fileCount.setText(fileInfo.isDir ? "(" + fileInfo.count + ")" : "");
				holder.fileModifiedDate.setText(Util.formatDateString(mContext, fileInfo.modifiedDate));
				holder.fileSize.setText(fileInfo.isDir ? "" : Util.convertStorage(fileInfo.fileSize));
			}

			if (fileInfo.isDir) {
				holder.fileImageFrame.setVisibility(View.GONE);
				holder.fileImage.setImageResource(R.drawable.folder);
			} else {
				mFileIconHelper.setIcon(fileInfo, holder.fileImage, holder.fileImageFrame);
			}
			holder.fileCheckBox.setVisibility(mFileOperationHelper.isOperation() ? View.VISIBLE : View.GONE);
			holder.fileCheckBox.setImageResource(fileInfo.selected ? R.drawable.btn_check_on_holo_light
					: R.drawable.btn_check_off_holo_light);
			holder.fileCheckBox.setTag(fileInfo);
			holder.fileCheckBoxArea.setOnClickListener(new FileOperationHelper.FileOnOperationClickListener(mContext,
					mFileOperationHelper));
		}
		return convertView;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#notifyDataSetChanged()
	 */
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		System.out.println("===================notify");
	}

	class ViewHolder {
		ImageView fileImageFrame;
		ImageView fileImage;
		TextView fileName;
		TextView fileCount;
		TextView fileModifiedDate;
		TextView fileSize;
		View fileCheckBoxArea;
		ImageView fileCheckBox;
	}

}
