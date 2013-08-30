/* ---------------------------------------------------------------------------------------------
 *
 *            Capital Alliance Software Confidential Proprietary
 *            (c) Copyright CAS 201{x}, All Rights Reserved
 *                          www.pekall.com
 *
 * ----------------------------------------------------------------------------------------------
 */
package com.android.browser.filemanager;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.browser.filemanager.utils.Util;
import com.android.browser.R;

/**
 * @author haoanbang
 * 
 */
public class FavoriteListAdapter extends ArrayAdapter<FileInfo> {
	private LayoutInflater mInflater;
	private FileIconHelper mFileIconHelper;
	private FileSettingsHelper mFileSettingsHelper;

	public FavoriteListAdapter(Context context, ArrayList<FileInfo> datas) {
		super(context, 0, datas);
		mInflater = ((Activity) context).getLayoutInflater();
		mFileIconHelper = new FileIconHelper(context);
		mFileSettingsHelper = FileSettingsHelper.getInstance(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.favorite_item, null);
			holder.fileName = (TextView) convertView.findViewById(R.id.file_name);
			holder.modifiedTime = (TextView) convertView.findViewById(R.id.modified_time);
			holder.fileSize = (TextView) convertView.findViewById(R.id.file_size);
			holder.fileImage = (ImageView) convertView.findViewById(R.id.file_image);
			holder.fileImageFrame = (ImageView) convertView.findViewById(R.id.file_image_frame);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		FileInfo fileInfo = getItem(position);
		if (fileInfo != null) {
			Util.setViewText(convertView, R.id.file_name, fileInfo.fileName);
			if (mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_ONLY_SHOW_FILENAME, false)) {
				holder.modifiedTime.setVisibility(View.GONE);
				holder.fileSize.setVisibility(View.GONE);
			} else {
				holder.modifiedTime.setVisibility(View.VISIBLE);
				holder.fileSize.setVisibility(View.VISIBLE);
				if (fileInfo.modifiedDate > 0) {
					Util.setViewText(convertView, R.id.modified_time,
							Util.formatDateString(getContext(), fileInfo.modifiedDate));
					holder.modifiedTime.setVisibility(View.VISIBLE);
				} else {
					holder.modifiedTime.setVisibility(View.GONE);
				}
				if (fileInfo.isDir) {
					holder.fileSize.setVisibility(View.GONE);
				} else {
					holder.fileSize.setVisibility(View.VISIBLE);
					Util.setViewText(convertView, R.id.file_size, Util.convertStorage(fileInfo.fileSize));
				}
			}

			holder.fileImage.setTag(position);

			if (fileInfo.isDir) {
				holder.fileImageFrame.setVisibility(View.GONE);
				holder.fileImage.setImageResource(R.drawable.folder_fav);
			} else {
				mFileIconHelper.setIcon(fileInfo, holder.fileImage, holder.fileImageFrame);
			}
		}

		return convertView;
	}

	class ViewHolder {
		TextView fileName;
		TextView modifiedTime;
		TextView fileSize;
		ImageView fileImage;
		ImageView fileImageFrame;
	}

}
