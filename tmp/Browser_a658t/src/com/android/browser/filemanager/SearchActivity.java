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
import java.util.Collections;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Files.FileColumns;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.browser.filemanager.FileListAdapter.ViewHolder;
import com.android.browser.filemanager.utils.Util;
import com.android.browser.R;

/**
 * @author haoanbang
 * 
 */
public class SearchActivity extends Activity implements OnClickListener, OnItemClickListener {
	public static final int SEARCH_SUCCESS = 0;
	public static final int ERROR_TEXT_IS_EMPTY = 1;
	public static final int ERROR_NO_FIND_FILES = 2;
	public static final String RESULT_FILEINFO = "result_fileinfo";

	private EditText mKeywordsEdit;
	private ImageView mSearchButton;
	private ListView mSearchListView;
	private FileOperationHelper mFileOperationHelper;
	private FileIconHelper mFileIconHelper;
	private SearchListAdapter mAdapter;
	private String mKeywords;
	private InputMethodManager mImm;
	private ArrayList<FileInfo> mData = new ArrayList<FileInfo>();
	private FileSettingsHelper mFileSettingsHelper;
	private FileSortHelper mFileSortHelper;
	private FileSDCardHelper mFileSDCardHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ui_search);
		initUI();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mFileOperationHelper != null) {
			mFileOperationHelper.setSearchContext(null);
		}
	}

	private void initUI() {
		mFileOperationHelper = FileOperationHelper.getInstance(this);
		mFileOperationHelper.setSearchContext(this);
		mFileSettingsHelper = FileSettingsHelper.getInstance(this);
		mFileSortHelper = FileSortHelper.getInstance(mFileSettingsHelper);
		mFileSDCardHelper = FileSDCardHelper.getInstance(this, mFileSettingsHelper, mFileOperationHelper);
		mFileIconHelper = new FileIconHelper(this);
		mSearchListView = (ListView) findViewById(R.id.search_listview);
		mSearchButton = (ImageView) findViewById(R.id.search);
		mKeywordsEdit = (EditText) findViewById(R.id.text_keyword);
		mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mSearchButton.setOnClickListener(this);
		mAdapter = new SearchListAdapter(this);
		mSearchListView.setAdapter(mAdapter);
		mSearchListView.setOnItemClickListener(this);
		mSearchListView.setOnCreateContextMenuListener(this);
	}

	@Override
	public void onClick(View v) {
		int result = searchKeywords();
		if (result == ERROR_TEXT_IS_EMPTY) {
			Toast.makeText(this, R.string.toast_keywords_isempty, Toast.LENGTH_SHORT).show();
		} else if (result == ERROR_NO_FIND_FILES) {
			showEmptyFileView(true);
		} else if (result == SEARCH_SUCCESS) {
			showEmptyFileView(false);
		}

		mImm.hideSoftInputFromWindow(mKeywordsEdit.getWindowToken(), 0);
	}

	private void showEmptyFileView(boolean visibility) {
		mSearchListView.setVisibility(!visibility ? View.VISIBLE : View.GONE);
		findViewById(R.id.file_not_available_page).setVisibility(visibility ? View.VISIBLE : View.GONE);
	}

	private int searchKeywords() {
		mKeywords = mKeywordsEdit.getText().toString();
		if (TextUtils.isEmpty(mKeywords))
			return ERROR_TEXT_IS_EMPTY;
		return onReflush();

	}

	public int onReflush() {
		mData.clear();
		ArrayList<FileInfo> fileInfos = mFileOperationHelper.searchFileInfos(mKeywords);
		mData.addAll(fileInfos);
		Collections.sort(mData, mFileSortHelper.getComparator(mFileSettingsHelper.getSortType()));
		mAdapter.notifyDataSetChanged();
		if (fileInfos.size() <= 0)
			return ERROR_NO_FIND_FILES;
		return SEARCH_SUCCESS;
	}
	
	public void removeDatas(ArrayList<FileInfo> datas){
		if(mAdapter != null && mData != null){
			mData.removeAll(datas);
			mAdapter.notifyDataSetChanged();
		}
	}
	
	public void addDatas(ArrayList<FileInfo> datas){
		if(mAdapter != null && mData != null){
			mData.addAll(datas);
			Collections.sort(mData, mFileSortHelper.getComparator(mFileSettingsHelper.getSortType()));
			mAdapter.notifyDataSetChanged();
		}
	}

	public class SearchListAdapter extends ArrayAdapter<FileInfo> {

		public SearchListAdapter(Context context) {
			super(context, 0, mData);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.search_list_item, null);
				holder.fileName = (TextView) convertView.findViewById(R.id.file_name);
				holder.fileImage = (ImageView) convertView.findViewById(R.id.file_image);
				holder.fileImageFrame = (ImageView) convertView.findViewById(R.id.file_image_frame);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			FileInfo fileInfo = getItem(position);
			if (fileInfo != null) {
				holder.fileName.setText(fileInfo.fileName);
				if (fileInfo.isDir) {
					holder.fileImageFrame.setVisibility(View.GONE);
					holder.fileImage.setImageResource(R.drawable.folder);
				} else {
					mFileIconHelper.setIcon(fileInfo, holder.fileImage, holder.fileImageFrame);
				}
			}
			return convertView;
		}

		class ViewHolder {
			ImageView fileImageFrame;
			ImageView fileImage;
			TextView fileName;
		}

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		MenuHelper.onCreateContextMenu(menu, false, mAdapter.getItem(((AdapterContextMenuInfo) menuInfo).position));
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int position = ((AdapterContextMenuInfo) item.getMenuInfo()).position;
		FileInfo fileInfo = mAdapter.getItem(position);
		if (fileInfo == null) {
			return true;
		}
		switch (item.getItemId()) {
		case MenuHelper.MENU_FAVORITE:
			mFileOperationHelper.onOperationFavorite(fileInfo);
			break;
		case MenuHelper.MENU_COPY:
			copyFile(fileInfo);
			finish();
			break;
		case MenuHelper.MENU_COPY_PATH:
			mFileOperationHelper.onOperationCopyPath(fileInfo.filePath);
			break;
		case MenuHelper.MENU_MOVE:
			moveFile(fileInfo);
			finish();
			break;
		case MenuHelper.MENU_SEND:
			mFileOperationHelper.onOperationSend(fileInfo);
			break;
		case MenuHelper.MENU_RENAME:
			mFileOperationHelper.onOperationRename(fileInfo, this);
			onReflush();
			break;
		case MenuHelper.MENU_DELETE:
			mFileOperationHelper.onOperationDeleteFiles(fileInfo, this);
			onReflush();
			break;
		case MenuHelper.MENU_INFO:
			mFileOperationHelper.onOperationInfo(fileInfo, this);
			break;
		default:
			break;
		}
		return true;
	}

	private void moveFile(FileInfo fileInfo) {
		mFileOperationHelper.setOperationState(FileOperationHelper.FILE_OPERATION_STATE_MOVE);
		mFileOperationHelper.addOperationInfo(fileInfo);
		// reflushData();
	}

	private void copyFile(FileInfo fileInfo) {
		mFileOperationHelper.setOperationState(FileOperationHelper.FILE_OPERATION_STATE_COPY);
		mFileOperationHelper.addOperationInfo(fileInfo);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		FileInfo fileInfo = mAdapter.getItem(position);
		if (fileInfo.isDir) {
			if (mFileSDCardHelper.isDoubleCardPhone) {
				SDCardInfo internalSdCardInfo = mFileSDCardHelper.getRoot(SDCardInfo.INTERNAL_SD);
				if (fileInfo.filePath.startsWith(internalSdCardInfo.path)) {
					mFileOperationHelper.go2Folder(1, fileInfo);
				} else {
					mFileOperationHelper.go2Folder(2, fileInfo);
				}
			} else {
				mFileOperationHelper.go2Folder(1, fileInfo);
			}
			this.finish();
		} else {
			mFileOperationHelper.viewFile(this, fileInfo);
		}
	}
}
