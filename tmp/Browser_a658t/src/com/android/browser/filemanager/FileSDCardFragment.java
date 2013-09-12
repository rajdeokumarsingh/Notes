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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.browser.filemanager.FileManagerMainActivity.OnBackListener;
import com.android.browser.filemanager.SoftCursor.SortType;
import com.android.browser.filemanager.TextInputDialog.OnFinishListener;
import com.android.browser.filemanager.utils.Util;
import com.android.browser.R;

/**
 * @author haoanbang
 * 
 */
public class FileSDCardFragment extends Fragment implements OnItemClickListener, OnBackListener, OnClickListener,
		IFileOperater, OnScrollListener {
	public static final int REQUEST_CODE_SEARCH = 1;
	private FileSDCardHelper mFileSDCardHelper;
	private FileListAdapter mAdapter;
	private ListView mListView;
	private FileInfo mParentInfo;
	private FileInfo mPreInfo;
	private TextView mCurrentPath;
	private ImageView mPathImage;
	private View mPathPane;
	private SDCardInfo mRoot;
	private ScrollView mPathScrollView;
	private ImageView mReturnUpPath;
	private LinearLayout mChoosePathView;
	private Button ok;
	private Button cancel;
	private FileOperationHelper mFileOperationHelper;
	private FileSettingsHelper mFileSettingsHelper;
	private View mRootView;
	private FavoriteDatabaseHelper mDatabaseHelper;
	private HashMap<String, Integer> mViewIndex = new HashMap<String, Integer>();
	private int mSDCardType;
	private boolean mChoosePath;
	private boolean mPreShowHideSettings;
	private boolean mPreShowFileNameSettings;
	private boolean mPreSortDescSettings;
	private ArrayList<FileInfo> mDatas = new ArrayList<FileInfo>();
	private FileSortHelper mFileSortHelper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.ui_sdcard_fragment, container, false);
		setupSdRecever();
		initUI(mRootView);
		updateUI();
		setHasOptionsMenu(true);
		if(mChoosePath){
		    setHasOptionsMenu(false);
		    mChoosePathView.setVisibility(View.VISIBLE);
		    ok.setOnClickListener(this);
		    cancel.setOnClickListener(this);
		}
		return mRootView;
	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(mReceiver);
		super.onDestroy();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isSettingsChange()) {
			reflushData();
		}
		if (mFileOperationHelper != null)
			setOperationBarVisibility(mFileOperationHelper.isMoveState());
	}

	public boolean isSettingsChange() {
		boolean isChange = (mPreShowHideSettings != mFileSettingsHelper.getBoolean(
				FileSettingsHelper.KEY_SHOW_HIDEFILE, false))
				|| (mPreShowFileNameSettings != mFileSettingsHelper.getBoolean(
						FileSettingsHelper.KEY_ONLY_SHOW_FILENAME, false))
				|| (mPreSortDescSettings != mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_SORT_DESC, false));
		if (isChange) {
			updatePreSettings();
		}
		return isChange;
	}

	private void updatePreSettings() {
		mPreShowHideSettings = mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_SHOW_HIDEFILE, false);
		mPreShowFileNameSettings = mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_ONLY_SHOW_FILENAME, false);
		mPreSortDescSettings = mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_SORT_DESC, false);
	}

	private void initUI(View view) {
		mListView = (ListView) view.findViewById(R.id.sdFile_listView);
		mCurrentPath = (TextView) view.findViewById(R.id.current_path_view);
		mPathPane = view.findViewById(R.id.current_path_pane);
		mPathPane.setOnClickListener(this);
		mReturnUpPath = (ImageView) view.findViewById(R.id.path_pane_up_level);
		mReturnUpPath.setOnClickListener(this);
		mPathScrollView = (ScrollView) view.findViewById(R.id.path_scrollView);
		mPathImage = (ImageView) view.findViewById(R.id.path_image);
		
		mChoosePathView = (LinearLayout)view.findViewById(R.id.choosepath);
		ok = (Button)view.findViewById(R.id.button_choose_confirm);
		cancel = (Button)view.findViewById(R.id.button_choose_cancel);
		mReturnUpPath.setVisibility(View.INVISIBLE);
		mPathImage.setVisibility(View.GONE);
		mSDCardType = getArguments().getInt(FileManagerMainActivity.KEY_SDTYPE);
		String mainPath = getArguments().getString(FileManagerMainActivity.KEY_MAINPATH);
		mChoosePath = getArguments().getBoolean(FileManagerMainActivity.KEY_CHOOSEPATH,false);
		mFileOperationHelper = FileOperationHelper.getInstance(getActivity());
		mFileSettingsHelper = FileSettingsHelper.getInstance(getActivity());
		mFileSortHelper = FileSortHelper.getInstance(mFileSettingsHelper);
		updatePreSettings();
		mFileSDCardHelper = FileSDCardHelper.getInstance(getActivity(), mFileSettingsHelper, mFileOperationHelper);
		mDatabaseHelper = FavoriteDatabaseHelper.getInstance();
		mRoot = mFileSDCardHelper.getRoot(mSDCardType);
		if (mRoot == null) {
			mRootView.findViewById(R.id.sd_not_available_page).setVisibility(View.VISIBLE);
			mRootView.findViewById(R.id.navigation_bar).setVisibility(View.GONE);
			mListView.setVisibility(View.GONE);
			return;
		}
		mFileOperationHelper.setRootPath(mRoot.path);
		setOnClick(mRootView, R.id.button_moving_confirm);
		setOnClick(mRootView, R.id.button_moving_cancel);
		mAdapter = new FileListAdapter(getActivity(), mFileOperationHelper, mFileSettingsHelper, mDatas);
		mCurrentPath.setText(replacePathText(mRoot.path, true));
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setOnCreateContextMenuListener(this);
		mListView.setOnScrollListener(this);
		if (mainPath != null) {
			mParentInfo = Util.getFileInfo(mainPath,
					mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_SHOW_HIDEFILE, false));
		}
		reflushData();
	}

	private Menu mMenu;

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		System.out.println("========================onCreateOptionsMenu=");
		super.onCreateOptionsMenu(menu, inflater);
		menu.clear();
		MenuHelper.onCreateOperationMenu(menu, false);
		mMenu = menu;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		System.out.println("========================onPrepareOptionsMenu=");
		super.onPrepareOptionsMenu(menu);
		initMenu(menu);
		if (mFileOperationHelper != null) {
			setOperationBarVisibility(mFileOperationHelper.isMoveState());
			if (mFileOperationHelper.isMoveState()) {
				return;
			}
		}
		menu.findItem(MenuHelper.MENU_SELECTALL).setVisible(true);
		menu.findItem(MenuHelper.MENU_SORT).setVisible(true);
		menu.findItem(MenuHelper.MENU_REFRESH).setVisible(true);
		menu.findItem(MenuHelper.MENU_SETTING).setVisible(true);
		MenuItem item = null;
		if (mFileSDCardHelper != null) {
			SortType sortType = mFileSettingsHelper.getSortType();
			switch (sortType) {
			case name:
				item = menu.findItem(MenuHelper.MENU_SORT_NAME);
				break;
			case size:
				item = menu.findItem(MenuHelper.MENU_SORT_SIZE);
				break;
			case type:
				item = menu.findItem(MenuHelper.MENU_SORT_TYPE);
				break;
			case date:
				item = menu.findItem(MenuHelper.MENU_SORT_DATE);
				break;
			}
		}

		if (item != null)
			item.setChecked(true);

		if (mFileOperationHelper.getIsCover()) {
			menu.findItem(MenuHelper.MENU_OPERATION_COVER).setChecked(true);
			menu.findItem(MenuHelper.MENU_OPERATION_TYPE).setTitle(R.string.menu_cover);
		} else {
			menu.findItem(MenuHelper.MENU_OPERATION_NOT_COVER).setChecked(true);
			menu.findItem(MenuHelper.MENU_OPERATION_TYPE).setTitle(R.string.menu_not_cover);
		}

		if (mDatabaseHelper != null && item != null) {
			item = menu.findItem(MenuHelper.MENU_FAVORITE);
			if (item != null)
				item.setTitle(mDatabaseHelper.isFavorite(mParentInfo == null ? mRoot.path : mParentInfo.filePath) ? R.string.operation_unfavorite
						: R.string.operation_favorite);
		}
	}

	private static HashMap<Integer, Integer> mMenuIds = new HashMap<Integer, Integer>();
	static {
		mMenuIds.put(MenuHelper.MENU_SELECTALL, MenuItem.SHOW_AS_ACTION_ALWAYS);
		mMenuIds.put(MenuHelper.MENU_SORT, MenuItem.SHOW_AS_ACTION_ALWAYS);
		mMenuIds.put(MenuHelper.MENU_NEW_FOLDER, MenuItem.SHOW_AS_ACTION_NEVER);
		mMenuIds.put(MenuHelper.MENU_FAVORITE, MenuItem.SHOW_AS_ACTION_NEVER);
		// mMenuIds.put(MenuHelper.MENU_SHOWHIDE,
		// MenuItem.SHOW_AS_ACTION_NEVER);
		mMenuIds.put(MenuHelper.MENU_REFRESH, MenuItem.SHOW_AS_ACTION_NEVER);
		mMenuIds.put(MenuHelper.MENU_SETTING, MenuItem.SHOW_AS_ACTION_NEVER);
		mMenuIds.put(MenuHelper.MENU_EXIT, MenuItem.SHOW_AS_ACTION_NEVER);
		mMenuIds.put(MenuHelper.MENU_AGREE, MenuItem.SHOW_AS_ACTION_ALWAYS);
		mMenuIds.put(MenuHelper.MENU_CANCEL, MenuItem.SHOW_AS_ACTION_ALWAYS);
		mMenuIds.put(MenuHelper.MENU_OPERATION_TYPE, MenuItem.SHOW_AS_ACTION_ALWAYS);
		mMenuIds.put(MenuHelper.MENU_SEARCH, MenuItem.SHOW_AS_ACTION_ALWAYS);
	}

	private void showSearchActivity() {
		Intent intent = new Intent(getActivity(), SearchActivity.class);
		getActivity().startActivity(intent);
	}

	private void initMenu(Menu menu) {
		Set<Integer> idsSet = mMenuIds.keySet();
		Iterator<Integer> iter = idsSet.iterator();
		while (iter.hasNext()) {
			int key = iter.next();
			menu.findItem(key).setShowAsAction(mMenuIds.get(key));
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (!mFileSDCardHelper.isSDCardReady(mRoot.path) && item.getItemId() != MenuHelper.MENU_SEARCH
				&& item.getItemId() != MenuHelper.MENU_EXIT) {
			Toast.makeText(getActivity(), R.string.enable_sd_card, Toast.LENGTH_SHORT).show();
			return true;
		}
		switch (item.getItemId()) {
		case MenuHelper.MENU_NEW_FOLDER:
			createNewFolder();
			break;
		case MenuHelper.MENU_REFRESH:
			reflushData();
			mListView.setSelection(0);
			// mFileOperationHelper.clearDB();
			break;
		case MenuHelper.MENU_SELECTALL:
			if (mDatas.size() <= 0) {
				Toast.makeText(getActivity(), R.string.toast_no_file_operation, Toast.LENGTH_SHORT).show();
				return true;
			}
			mFileOperationHelper.operOperation();
			onDataChange();
			break;
		case MenuHelper.MENU_EXIT:
			((FileManagerMainActivity) getActivity()).exitApp();
			break;
		// case MenuHelper.MENU_SHOWHIDE:
		// mFileSettingsHelper.putBoolean(FileSettingsHelper.KEY_SHOW_HIDEFILE,
		// !mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_SHOW_HIDEFILE,
		// false));
		// boolean isShowHide =
		// mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_SHOW_HIDEFILE,
		// false);
		// if (isShowHide) {
		// item.setTitle(R.string.operation_hide_sys);
		// } else {
		// item.setTitle(R.string.operation_show_sys);
		// }
		// ((FileManagerMainActivity) getActivity()).onDataReflush();
		// break;
		case MenuHelper.MENU_SORT_NAME:
			mFileSettingsHelper.putSortType(SortType.name);
			reflushData();
			item.setChecked(true);
			break;
		case MenuHelper.MENU_SORT_DATE:
			mFileSettingsHelper.putSortType(SortType.date);
			reflushData();
			item.setChecked(true);
			break;
		case MenuHelper.MENU_SORT_TYPE:
			mFileSettingsHelper.putSortType(SortType.type);
			reflushData();
			item.setChecked(true);
			break;
		case MenuHelper.MENU_SORT_SIZE:
			mFileSettingsHelper.putSortType(SortType.size);
			reflushData();
			item.setChecked(true);
			break;
		case MenuHelper.MENU_FAVORITE:
			operationFavorite();
			break;
		case MenuHelper.MENU_AGREE:
			setOperationBarVisibility(false);
			mFileOperationHelper.confirmOperation();
			break;
		case MenuHelper.MENU_CANCEL:
			setOperationBarVisibility(false);
			mFileOperationHelper.cancelOperation();
			break;
		case MenuHelper.MENU_SETTING:
			Intent intent = new Intent(getActivity(), FileSettingsActivity.class);
			startActivity(intent);
			break;
		case MenuHelper.MENU_SEARCH:
			showSearchActivity();
			break;
		case MenuHelper.MENU_OPERATION_COVER:
			mFileOperationHelper.setIsCover(true);
			item.setChecked(true);
			mMenu.findItem(MenuHelper.MENU_OPERATION_TYPE).setTitle(R.string.menu_cover);
			break;
		case MenuHelper.MENU_OPERATION_NOT_COVER:
			mFileOperationHelper.setIsCover(false);
			item.setChecked(true);
			mMenu.findItem(MenuHelper.MENU_OPERATION_TYPE).setTitle(R.string.menu_not_cover);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void operationFavorite() {
		if (mParentInfo == null) {
			FileInfo fileInfo = new FileInfo();
			fileInfo.fileName = mRoot.title;
			fileInfo.filePath = mRoot.path;
			mFileOperationHelper.onOperationFavorite(fileInfo);
		} else {
			mFileOperationHelper.onOperationFavorite(mParentInfo);
		}
	}

	private void createNewFolder() {
		final String currentPath = mParentInfo == null ? mRoot.path : mParentInfo.filePath;
		String defaultName = getString(R.string.new_folder_name);
		int i = 0;
		while (new File(Util.makePath(currentPath, defaultName)).exists()) {
			defaultName = getString(R.string.new_folder_name) + "[" + i++ + "]";
		}

		TextInputDialog dialog = new TextInputDialog(getActivity(), getString(R.string.operation_create_folder),
				getString(R.string.operation_create_folder_message), defaultName, new OnFinishListener() {
					@Override
					public boolean onFinish(String folderName) {
						int result = FileOperationHelper.CreateFolder(currentPath, folderName);
						if (result == FileOperationHelper.FOLDER_CREATE_SUCCESS) {
							mDatas.add(Util.getFileInfo(Util.makePath(currentPath, folderName)));
							Collections.sort(mDatas, mFileSortHelper.getComparator(mFileSettingsHelper.getSortType()));
							showEmptyFilesView(false);
							mAdapter.notifyDataSetChanged();
						} else {
							if (result == FileOperationHelper.ERROR_FOLDER_EXIST) {
								new AlertDialog.Builder(getActivity())
										.setMessage(getString(R.string.fail_to_create_folder_exist))
										.setPositiveButton(R.string.confirm, null).create().show();
							} else if (result == FileOperationHelper.ERROR_FOLDER_READONLY) {
								new AlertDialog.Builder(getActivity())
										.setMessage(getString(R.string.toast_folder_readonly))
										.setPositiveButton(R.string.confirm, null).create().show();
							} else if (result == FileOperationHelper.ERROR_FOLDER_NOTCREATE) {
								new AlertDialog.Builder(getActivity())
								.setMessage(getString(R.string.toast_no_left_space_on_device))
								.setPositiveButton(R.string.confirm, null).create().show();
							}
						}
						return true;
					}
				});

		dialog.show();
	}

	private String replacePathText(String old, boolean isPositive) {
		if (isPositive)
			return old.replaceFirst(mRoot.path, mRoot.title);
		else
			return old.replaceFirst(mRoot.title, mRoot.path);
	}

	public void reflushData() {
		if (mParentInfo == null || mParentInfo.filePath.equals(mRoot.path)) {
			if (mRootView != null) {
				((ImageView) mRootView.findViewById(R.id.path_pane_up_level)).setVisibility(View.GONE);
				((ImageView) mRootView.findViewById(R.id.path_image)).setVisibility(View.GONE);
			}
		} else {
			if (mRootView != null) {
				((ImageView) mRootView.findViewById(R.id.path_pane_up_level)).setVisibility(View.VISIBLE);
				((ImageView) mRootView.findViewById(R.id.path_image)).setVisibility(View.VISIBLE);
			}
		}
		reflushData(mParentInfo == null ? (mRoot == null ? "" : mRoot.path) : mParentInfo.filePath);
	}

	private void showEmptyFilesView(boolean visibility) {
		if (isShowNoSDcard()) {
			mRootView.findViewById(R.id.file_not_available_page).setVisibility(View.GONE);
		} else {
			mRootView.findViewById(R.id.file_not_available_page).setVisibility(visibility ? View.VISIBLE : View.GONE);
		}
	}

	private Handler mFileHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case FileSDCardHelper.FINDFILES_SHOWPROGRESSDIALOG:
				((FileManagerMainActivity) getActivity()).showProgress(getString(R.string.message_data_to_large));
				break;
			case FileSDCardHelper.FINDFILES_FILES_IS_EMPTY:
				showEmptyFilesView(true);
				mDatas.clear();
				mCurrentPath.setText(replacePathText(mParentInfo == null ? getString(R.string.root_path_text)
						: mParentInfo.filePath, true));
				mAdapter.notifyDataSetChanged();
				break;
			case FileSDCardHelper.FINDFILES_INTERRUPT:
				mParentInfo = mPreInfo;
				((FileManagerMainActivity) getActivity()).closeProgress();
				break;
			case FileSDCardHelper.FINDFILES_SETUP_DATAS_SUCCESS:
				showEmptyFilesView(false);
				mDatas.clear();
				mDatas.addAll(mFileSDCardHelper.getDatas(mRoot.type));
				Collections.sort(mDatas, mFileSortHelper.getComparator(mFileSettingsHelper.getSortType()));
				mCurrentPath.setText(replacePathText(mParentInfo == null ? getString(R.string.root_path_text)
						: mParentInfo.filePath, true));
				mAdapter.notifyDataSetChanged();
				((FileManagerMainActivity) getActivity()).closeProgress();
				break;
			}
		}
	};

	public void reflushData(String filePath) {
		if (mFileSDCardHelper != null) {
			mFileSDCardHelper.getFiles(filePath, mFileHandler, mRoot.type);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		/*MenuHelper.onCreateContextMenu(menu,
				mFileOperationHelper.isMoveState() || mFileOperationHelper.isShowOperation(),
				mAdapter.getItem(((AdapterContextMenuInfo) menuInfo).position));
				**/
		super.onCreateContextMenu(menu, v, menuInfo);
	}

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
			break;
		case MenuHelper.MENU_COPY_PATH:
			mFileOperationHelper.onOperationCopyPath(fileInfo.filePath);
			break;
		case MenuHelper.MENU_MOVE:
			moveFile(fileInfo);
			break;
		case MenuHelper.MENU_SEND:
			mFileOperationHelper.onOperationSend(fileInfo);
			break;
		case MenuHelper.MENU_RENAME:
			mFileOperationHelper.onOperationRename(fileInfo);
			break;
		case MenuHelper.MENU_DELETE:
			mFileOperationHelper.onOperationDeleteFiles(fileInfo);
			break;
		case MenuHelper.MENU_INFO:
			mFileOperationHelper.onOperationInfo(fileInfo);
			break;
		case MenuHelper.MENU_CREATE_SHORTCUT:
			mFileOperationHelper.onCreateShortCut(fileInfo);
			break;
		default:
			break;
		}
		return true;
	}

	private void moveFile(FileInfo fileInfo) {
		mFileOperationHelper.setOperationState(FileOperationHelper.FILE_OPERATION_STATE_MOVE);
		mFileOperationHelper.addOperationInfo(fileInfo);
	}

	private void copyFile(FileInfo fileInfo) {
		mFileOperationHelper.setOperationState(FileOperationHelper.FILE_OPERATION_STATE_COPY);
		mFileOperationHelper.addOperationInfo(fileInfo);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		FileInfo fileInfo = (FileInfo) mAdapter.getItem(position);
		if (fileInfo != null) {
			if (FileOperationHelper.sIsShowOperationBar) {
				fileInfo.selected = !fileInfo.selected;
				mFileOperationHelper.updateFileInfoSelect(fileInfo);
				onDataChange();
			} else {
				if (fileInfo.isDir) {
					if (mParentInfo == null) {
						mViewIndex.put(mRoot.path, mListView.getFirstVisiblePosition());
					} else {
						mViewIndex.put(mParentInfo.filePath, mListView.getFirstVisiblePosition());
					}
					mPreInfo = mParentInfo;
					mParentInfo = fileInfo;
					reflushData();
				} else {
					mFileOperationHelper.viewFile(getActivity(), fileInfo);
				}
			}
		}
	}

	@Override
	public boolean onBack() {
		if (mFileOperationHelper != null && mFileOperationHelper.isOperationState()) {
			mFileOperationHelper.closeDialog();
			setOperationBarVisibility(false);
			mFileOperationHelper.cancelOperation();
			return true;
		}
		if (mFileOperationHelper != null && mFileOperationHelper.isMoveState()) {
			setOperationBarVisibility(false);
			mFileOperationHelper.cancelOperation();
			return true;
		}
		if (mParentInfo == null || mRoot.path.equals(mParentInfo.filePath)) {
			return false;
		} else {
			mParentInfo = Util.getFileInfo(new File(mParentInfo.filePath).getParentFile(), null,
					mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_SHOW_HIDEFILE, false));
			mCurrentPath.setText(replacePathText(mParentInfo == null ? getString(R.string.root_path_text)
					: mParentInfo.filePath, true));
			reflushData();
			if (mListView != null && mRoot != null) {
				mListView.setSelection(mViewIndex == null ? 0 : (mViewIndex.get(mParentInfo == null ? mRoot.path
						: mParentInfo.filePath) == null ? 0 : mViewIndex.get(mParentInfo == null ? mRoot.path
						: mParentInfo.filePath)));
			}

			return true;
		}
	}

	private void navigationOperate(String path) {
		Util.showPathScrollView(mPathScrollView, mPathImage, false);
		mPreInfo = mParentInfo;
		mParentInfo = Util.getFileInfo(new File(path), null,
				mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_SHOW_HIDEFILE, false));
		reflushData();
		mListView.setSelection(mViewIndex.get(mParentInfo == null ? mRoot.path : mParentInfo.filePath) == null ? 0
				: mViewIndex.get(mParentInfo == null ? mRoot.path : mParentInfo.filePath));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.current_path_pane:
			Util.buildPathListUi(getActivity(), mPathScrollView, mPathImage, mCurrentPath.getText().toString(), this);
			break;
		case R.id.path_list_item:
			String path = (String) v.getTag();
			path = replacePathText(path, false);
			navigationOperate(path);
			ActionMode actionMode = ((FileManagerMainActivity) getActivity()).getActionMode();
			if (actionMode != null) {
				actionMode.finish();
			}
			break;
		case R.id.path_pane_up_level:
			path = mCurrentPath.getText().toString();
			if (path.equals(mRoot.title))
				break;
			path = path.substring(0, path.lastIndexOf("/"));
			path = replacePathText(path, false);
			navigationOperate(path);
			actionMode = ((FileManagerMainActivity) getActivity()).getActionMode();
			if (actionMode != null) {
				actionMode.finish();
			}
			break;
		case R.id.button_choose_cancel:
		    getActivity().finish();
		    break;
		case R.id.button_choose_confirm:
		    Intent intent = new Intent();
		    intent.setAction("com.android.browser.choosepath");
		    intent.putExtra(FileManagerMainActivity.KEY_CHOOSEPATH, replacePathText(mCurrentPath.getText().toString(), false));
		    getActivity().sendBroadcast(intent);
		    getActivity().finish();
		    break;
		}
	}

	private void setupSdRecever() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		intentFilter.addDataScheme("file");
		getActivity().registerReceiver(mReceiver, intentFilter);
	}

	private boolean isShowNoSDcard() {
		return mRootView == null ? true
				: mRootView.findViewById(R.id.sd_not_available_page).getVisibility() == View.VISIBLE;
	}

	private void updateUI() {
		if (mRoot != null) {
			showCategoryMenu(true);
			boolean sdCardReady = mFileSDCardHelper.isSDCardReady(mRoot.path);
			View noSdView = mRootView.findViewById(R.id.sd_not_available_page);
			noSdView.setVisibility(sdCardReady ? View.GONE : View.VISIBLE);

			View navigationBar = mRootView.findViewById(R.id.navigation_bar);
			navigationBar.setVisibility(sdCardReady ? View.VISIBLE : View.GONE);
			if (mListView == null)
				mListView = (ListView) mRootView.findViewById(R.id.sdFile_listView);
			mListView.setVisibility(sdCardReady ? View.VISIBLE : View.GONE);

			// setHasOptionsMenu(sdCardReady ? true : false);
			if (sdCardReady) {
				reflushData();
			} else {
				mRootView.findViewById(R.id.file_not_available_page).setVisibility(View.GONE);
			}
		}
	}

	private void showCategoryMenu(boolean isShow) {
	    /*
		if (mMenu != null) {
			mMenu.findItem(MenuHelper.MENU_SELECTALL).setVisible(isShow);
			mMenu.findItem(MenuHelper.MENU_SORT).setVisible(isShow);
			mMenu.findItem(MenuHelper.MENU_REFRESH).setVisible(isShow);
			mMenu.findItem(MenuHelper.MENU_SETTING).setVisible(isShow);
		}   **/
	}

	@Override
	public ArrayList<FileInfo> getAllFileInfos() {
		return mDatas;
	}

	@Override
	public void onDataChange() {
		if (mAdapter != null)
			mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onDataReflush() {
		reflushData();
	}

	@Override
	public int getTotalCount() {
		return mAdapter == null ? 0 : mAdapter.getCount();
	}

	@Override
	public FileInfo getDestFileInfo() {
		if (mParentInfo == null) {
			FileInfo rootInfo = new FileInfo();
			rootInfo.filePath = mRoot.path;
			rootInfo.fileName = mRoot.title;
			rootInfo.dbId = 0;
			return rootInfo;
		}
		return mParentInfo;
	}

	public void setOnClick(View view, int id) {
		View button = view == null ? getActivity().findViewById(id) : view.findViewById(id);
		if (button != null) {
			button.setOnClickListener(mFileOperationHelper);
		}
	}

	@Override
	public void setOperationBarVisibility(boolean visibility) {
		if (mMenu != null) {
			mMenu.setGroupVisible(MenuHelper.GROUP_OPERATION, visibility);
			mMenu.setGroupVisible(MenuHelper.GROUP_NORMAL, !visibility);
		}
	}

	@Override
	public void go2Folder(int cardId, FileInfo fileInfo) {
		if (mAdapter != null) {
			mPreInfo = mParentInfo;
			mParentInfo = Util.getFileInfo(new File(fileInfo.filePath), null,
					mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_SHOW_HIDEFILE, false));
			mCurrentPath.setText(replacePathText(mParentInfo == null ? mRoot.path : mParentInfo.filePath, true));
			reflushData();
		}
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
	        if(intent.getAction() == null){
	            return;
	        }
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_MEDIA_MOUNTED) || action.equals(Intent.ACTION_MEDIA_UNMOUNTED)) {
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (mRoot == null) {
							mRoot = mFileSDCardHelper
									.getRoot(getArguments().getInt(FileManagerMainActivity.KEY_SDTYPE));
							initUI(mRootView);
						}
						if (mFileOperationHelper != null)
							mFileOperationHelper.closeDialog();
						updateUI();
					}
				});

				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						reflushData();
					}
				}, 10 * 1000);
			}
		}
	};

	Handler mHandler = new Handler();

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

	}

	@Override
	public void onSDCardStateChange(boolean isMounted) {

	}

	@Override
	public void onUpdateMenu(boolean visibility, int position) {
	}

	@Override
	public void removeDatas(ArrayList<FileInfo> datas) {
		if (mAdapter != null && mDatas != null) {
			mDatas.removeAll(datas);
			mAdapter.notifyDataSetChanged();
		}
		if (mDatas.size() <= 0) {
			showEmptyFilesView(true);
		}
	}

	@Override
	public void addDatas(ArrayList<FileInfo> datas) {
		if (mAdapter != null && mDatas != null) {

			for (int i = 0; i < datas.size(); i++) {
				int index = mDatas.indexOf(datas.get(i));
				if (index > -1 && index < mDatas.size()) {
					mDatas.remove(index);
					mDatas.add(index, datas.get(i));
				} else if ((mParentInfo == null ? mRoot.path : mParentInfo.filePath).equals(new File(
						datas.get(i).filePath).getParent())) {
					mDatas.add(datas.get(i));
				}
			}
			// mDatas.addAll(datas);
			Collections.sort(mDatas, mFileSortHelper.getComparator(mFileSettingsHelper.getSortType()));
			mAdapter.notifyDataSetChanged();
			if (mDatas.size() > 0) {
				showEmptyFilesView(false);
			}
		}
	}

	@Override
	public void replaceDatas(ArrayList<FileInfo> oDatas, ArrayList<FileInfo> dDatas) {
		if (mAdapter != null && mDatas != null) {
			System.out.println();
			int index = mDatas.indexOf(oDatas.get(0));
			if (index > -1 && index < mDatas.size()) {
				mDatas.remove(index);
				mDatas.add(index, dDatas.get(0));
			}
			mAdapter.notifyDataSetChanged();
		}
	}

}
