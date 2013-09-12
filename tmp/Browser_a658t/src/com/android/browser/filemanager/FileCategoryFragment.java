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
import java.util.Iterator;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.browser.filemanager.FavoriteDatabaseHelper.FavoriteDatabaseListener;
import com.android.browser.filemanager.FileCategoryHelper.CategoryInfo;
import com.android.browser.filemanager.FileCategoryHelper.FileCategory;
import com.android.browser.filemanager.FileManagerMainActivity.OnBackListener;
import com.android.browser.filemanager.SoftCursor.SortType;
import com.android.browser.filemanager.utils.Util;
import com.android.browser.R;

/**
 * @author haoanbang
 * 
 */
public class FileCategoryFragment extends Fragment implements OnBackListener, OnClickListener, IFileOperater,
		FavoriteDatabaseListener, OnItemClickListener {

	private static HashMap<Integer, FileCategory> getCategoryById = new HashMap<Integer, FileCategory>();
	private static HashMap<FileCategory, Integer> getCountIdByCategory = new HashMap<FileCategory, Integer>();

	private static final int VD_FILE_PATH_LIST = R.id.file_path_list;
	private static final int VD_FAVORITE_LIST = R.id.favorite_list;
	private static final int VD_NAVIGATION_BAR = R.id.navigation_bar;
	private static final int VD_CATEGORY_PAGE = R.id.category_page;
	private static final int VD_PATH_TEXT = R.id.current_path_view;
	private static final int VD_NO_SDCARD = R.id.sd_not_available_page;
	private static final int VD_NO_FILES = R.id.file_not_available_page;
	private SortType mSortType;
	private View mRootView;
	private FileCategoryHelper mFileCategoryHelper;
	private TextView mCurrentPath;
	private ImageView mPathImage;
	private FileListCursorAdapter mAdapter;
	private FavoriteListAdapter mFavoriteListAdapter;
	private ArrayList<FileInfo> mFavoriteDatas = new ArrayList<FileInfo>();
	private View mPathPane;
	private ListView mListView;
	private ListView mFavoriteListView;
	private ImageView mReturnUpPath;
	private ScrollView mPathScrollView;
	private FileOperationHelper mFileOperationHelper;
	private FileSettingsHelper mFileSettingsHelper;
	private FileCategory mCategory;
	private FavoriteDatabaseHelper mFavoriteDatabaseHelper;
	private FileSDCardHelper mFileSDCardHelper;
	private boolean mPreShowFileNameSettings;
	private boolean mPreSortDescSettings;
	private FileSortHelper mFileSortHelper;

	static {
		getCategoryById.put(R.id.category_music, FileCategory.Music);
		getCategoryById.put(R.id.category_video, FileCategory.Video);
		getCategoryById.put(R.id.category_picture, FileCategory.Picture);
		getCategoryById.put(R.id.category_theme, FileCategory.Theme);
		getCategoryById.put(R.id.category_document, FileCategory.Doc);
		getCategoryById.put(R.id.category_zip, FileCategory.Zip);
		getCategoryById.put(R.id.category_apk, FileCategory.Apk);
		getCategoryById.put(R.id.category_favorite, FileCategory.Favorite);
		getCountIdByCategory.put(FileCategory.Music, R.id.category_music_count);
		getCountIdByCategory.put(FileCategory.Video, R.id.category_video_count);
		getCountIdByCategory.put(FileCategory.Picture, R.id.category_picture_count);
		getCountIdByCategory.put(FileCategory.Theme, R.id.category_theme_count);
		getCountIdByCategory.put(FileCategory.Doc, R.id.category_document_count);
		getCountIdByCategory.put(FileCategory.Zip, R.id.category_zip_count);
		getCountIdByCategory.put(FileCategory.Apk, R.id.category_apk_count);
		getCountIdByCategory.put(FileCategory.Favorite, R.id.category_favorite_count);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.ui_category_fragment, null);
		mFileCategoryHelper = FileCategoryHelper.getInstance(getActivity());
		initUI(mRootView);
		setHasOptionsMenu(true);
		updateUI();
		setOnClickListener(mRootView);
		setupSdRecever();
		return mRootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!isCategoryView()) {
			if (isFavoriteView()) {
				reflushFavorite();
			} else {
				reflush(mCategory);
			}
		}
	}

	public boolean isSettingsChange() {
		boolean isChange = (mPreShowFileNameSettings != mFileSettingsHelper.getBoolean(
				FileSettingsHelper.KEY_ONLY_SHOW_FILENAME, false))
				|| (mPreSortDescSettings != mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_SORT_DESC, false));
		if (isChange) {
			updatePreSettings();
		}
		return isChange;
	}

	private void updatePreSettings() {
		mPreShowFileNameSettings = mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_ONLY_SHOW_FILENAME, false);
		mPreSortDescSettings = mFileSettingsHelper.getBoolean(FileSettingsHelper.KEY_SORT_DESC, false);
	}

	private void setupSdRecever() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		intentFilter.addDataScheme("file");
		getActivity().registerReceiver(mReceiver, intentFilter);
	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(mReceiver);
		super.onDestroy();
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
						updateUI();
					}
				});
			}
		}
	};

	private Menu mMenu;

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		menu.clear();
		MenuHelper.onCreateOperationMenu(menu, true);
		mMenu = menu;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		System.out.println("===================onPrepareOptionsMenu");
		initMenu(menu);
		MenuItem item = null;
		if (isCategoryView() || isFavoriteView()) {
			showCategoryMenu(false);
		} else {
			showCategoryMenu(true);
			if (mFileSettingsHelper != null) {
				mSortType = mFileSettingsHelper.getSortType();
				switch (mSortType) {
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
				if (item != null)
					item.setChecked(true);
			}
		}

		// item = menu.findItem(MenuHelper.MENU_HIDEMENU);
		// item.setTitle(mCategory == null ?
		// getActivity().getString(R.string.title_category) :
		// buildText(mCategory));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (((FileManagerMainActivity) getActivity()).getCurrentId() != 0) {
			return super.onOptionsItemSelected(item);
		}

		if (!mFileSDCardHelper.isAllSDCardReady() && item.getItemId() != MenuHelper.MENU_SEARCH
				&& item.getItemId() != MenuHelper.MENU_EXIT) {
			Toast.makeText(getActivity(), R.string.enable_sd_card, Toast.LENGTH_SHORT).show();
			return true;
		}

		switch (item.getItemId()) {
		case MenuHelper.MENU_REFRESH:
			if (isCategoryView()) {
				updateUI();
			} else if (isFavoriteView()) {
				reflushFavorite();
			} else {
				reflush(mCategory);
				mListView.setSelection(0);
			}
			break;
		case MenuHelper.MENU_SELECTALL:
			if (mAdapter.getCount() == 0 && item.getItemId() != MenuHelper.MENU_SEARCH
					&& item.getItemId() != MenuHelper.MENU_EXIT) {
				return true;
			}
			mFileOperationHelper.operOperation();
			onDataChange();
			break;
		case MenuHelper.MENU_EXIT:
			((FileManagerMainActivity) getActivity()).exitApp();
			break;
		case MenuHelper.MENU_SORT_NAME:
			mFileSettingsHelper.putSortType(SortType.name);
			reflush(mCategory);
			item.setChecked(true);
			break;
		case MenuHelper.MENU_SORT_DATE:
			mFileSettingsHelper.putSortType(SortType.date);
			reflush(mCategory);
			item.setChecked(true);
			break;
		case MenuHelper.MENU_SORT_TYPE:
			mFileSettingsHelper.putSortType(SortType.type);
			reflush(mCategory);
			item.setChecked(true);
			break;
		case MenuHelper.MENU_SORT_SIZE:
			mFileSettingsHelper.putSortType(SortType.size);
			reflush(mCategory);
			item.setChecked(true);
			break;
		case MenuHelper.MENU_SEARCH:
			showSearchActivity();
			break;
		case MenuHelper.MENU_SETTING:
			Intent intent = new Intent(getActivity(), FileSettingsActivity.class);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showSearchActivity() {
		Intent intent = new Intent(getActivity(), SearchActivity.class);
		getActivity().startActivity(intent);
	}

	private static HashMap<Integer, Integer> mMenuIds = new HashMap<Integer, Integer>();
	static {
		mMenuIds.put(MenuHelper.MENU_SELECTALL, MenuItem.SHOW_AS_ACTION_IF_ROOM);
		mMenuIds.put(MenuHelper.MENU_SORT, MenuItem.SHOW_AS_ACTION_IF_ROOM);
		mMenuIds.put(MenuHelper.MENU_REFRESH, MenuItem.SHOW_AS_ACTION_NEVER);
		mMenuIds.put(MenuHelper.MENU_SETTING, MenuItem.SHOW_AS_ACTION_NEVER);
		mMenuIds.put(MenuHelper.MENU_EXIT, MenuItem.SHOW_AS_ACTION_NEVER);
		mMenuIds.put(MenuHelper.MENU_SEARCH, MenuItem.SHOW_AS_ACTION_ALWAYS);
	}

	private void initMenu(Menu menu) {
		Set<Integer> idsSet = mMenuIds.keySet();
		Iterator<Integer> iter = idsSet.iterator();
		while (iter.hasNext()) {
			int key = iter.next();
			menu.findItem(key).setShowAsAction(mMenuIds.get(key));
		}
	}

	private void updateUI() {
		boolean sdCardReady = mFileSDCardHelper.isAllSDCardReady();
		if (sdCardReady) {
			refreshCategoryInfo();
			showView(VD_NO_SDCARD, false);
			showEmptyFilesView(false);
			if (mCategory == null) {
				showView(VD_NAVIGATION_BAR, false);
				showView(VD_FILE_PATH_LIST, false);
				showView(VD_FAVORITE_LIST, false);
				showView(VD_CATEGORY_PAGE, true);
			} else {
				reflush(mCategory);
			}
		} else {
			showCategoryMenu(false);
			showView(VD_NO_SDCARD, true);
			showEmptyFilesView(false);
			showView(VD_NAVIGATION_BAR, false);
			showView(VD_FILE_PATH_LIST, false);
			showView(VD_FAVORITE_LIST, false);
			showView(VD_CATEGORY_PAGE, false);
		}
	}

	private void refreshCategoryInfo() {
		mFileCategoryHelper.refreshCategoryInfo();
		for (FileCategory fc : FileCategoryHelper.sCategories) {
			if (fc == FileCategory.Other)
				continue;
			CategoryInfo categoryInfo = mFileCategoryHelper.getCategoryInfos().get(fc);
			setCategoryCount(fc, categoryInfo.count);

		}
		setCategoryCount(FileCategory.Favorite, mFavoriteDatabaseHelper.getCount());
	}

	private void setCategoryCount(FileCategory fc, long count) {
		Util.setViewText(mRootView, getCountIdByCategory.get(fc), "(" + count + ")");
	}

	private void initUI(View view) {
		mFavoriteDatabaseHelper = new FavoriteDatabaseHelper(getActivity(), this);
		mCurrentPath = (TextView) view.findViewById(R.id.current_path_view);
		mPathPane = view.findViewById(R.id.current_path_pane);
		mPathPane.setOnClickListener(this);
		mPathScrollView = (ScrollView) view.findViewById(R.id.path_scrollView);
		mPathImage = (ImageView) view.findViewById(R.id.path_image);
		mReturnUpPath = (ImageView) view.findViewById(R.id.path_pane_up_level);
		mReturnUpPath.setOnClickListener(this);
		mListView = (ListView) view.findViewById(R.id.file_path_list);
		mFavoriteListView = (ListView) view.findViewById(R.id.favorite_list);
		mFileOperationHelper = FileOperationHelper.getInstance(getActivity());
		mFileSettingsHelper = FileSettingsHelper.getInstance(getActivity());
		mFileSortHelper = FileSortHelper.getInstance(mFileSettingsHelper);
		updatePreSettings();
		mSortType = mFileSettingsHelper.getSortType();
		mFileSDCardHelper = FileSDCardHelper.getInstance(getActivity(), mFileSettingsHelper, mFileOperationHelper);
		mAdapter = new FileListCursorAdapter(getActivity(), null, mFileOperationHelper, mFileSettingsHelper);
		mFavoriteListAdapter = new FavoriteListAdapter(getActivity(), mFavoriteDatas);
		mListView.setAdapter(mAdapter);
		mFavoriteListView.setAdapter(mFavoriteListAdapter);
		mFavoriteListView.setOnItemClickListener(this);
		mListView.setOnCreateContextMenuListener(this);
		mListView.setOnItemClickListener(this);
		mFavoriteListView.setOnCreateContextMenuListener(this);
	}

	private void setOnClickListener(View view) {
		Set<Integer> idSet = getCategoryById.keySet();
		Iterator<Integer> iterator = idSet.iterator();
		while (iterator.hasNext()) {
			view.findViewById(iterator.next()).setOnClickListener(this);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		switch (v.getId()) {
		case R.id.favorite_list:
			menu.add(0, MenuHelper.MENU_FAVORITE, 0, R.string.operation_unfavorite);
			break;
		case R.id.file_path_list:
			MenuHelper.onCreateContextMenu(menu, false,
					mAdapter.getFileItem(((AdapterContextMenuInfo) menuInfo).position));
			break;
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int position = ((AdapterContextMenuInfo) item.getMenuInfo()).position;
		FileInfo fileInfo = isFavoriteView() ? mFavoriteListAdapter.getItem(position) : mAdapter.getFileItem(position);
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
	public boolean onBack() {
		if (mFileSDCardHelper != null && !mFileSDCardHelper.isAllSDCardReady()) {
			return false;
		}
		mCategory = null;
		if (!isCategoryView()) {
			if (mMenu != null) {
				showCategoryMenu(false);
			}
			showView(VD_NAVIGATION_BAR, false);
			showView(VD_FAVORITE_LIST, false);
			showView(VD_FILE_PATH_LIST, false);
			showView(VD_CATEGORY_PAGE, true);
			// setHasOptionsMenu(false);
			refreshCategoryInfo();
			return true;
		}
		return false;
	}

	private boolean isCategoryView() {

		return mRootView == null ? true : mRootView.findViewById(VD_CATEGORY_PAGE).getVisibility() == View.VISIBLE;
	}

	private void reflushFavorite() {
		mFavoriteDatas.clear();
		ArrayList<FileInfo> datas = mFavoriteDatabaseHelper.query();
		if (datas.size() <= 0) {
			showView(VD_NO_SDCARD, false);
			showEmptyFilesView(true);
		} else {
			showEmptyFilesView(false);
		}
		mFavoriteDatas.addAll(datas);
		Collections.sort(mFavoriteDatas, mFileSortHelper.getComparator(mFileSettingsHelper.getSortType()));
		mFavoriteListAdapter.notifyDataSetChanged();
		setCategoryCount(FileCategory.Favorite, mFavoriteListAdapter.getCount());
		setText(VD_PATH_TEXT, buildPathText(FileCategory.Favorite));
		if (!isFavoriteView()) {
			showView(VD_NAVIGATION_BAR, true);
			showView(VD_FAVORITE_LIST, true);
			showView(VD_FILE_PATH_LIST, false);
			showView(VD_CATEGORY_PAGE, false);
		}
	}

	private void showEmptyFilesView(boolean visibility) {
		if (isShowNoSDcard()) {
			mRootView.findViewById(R.id.file_not_available_page).setVisibility(View.GONE);
		} else {
			showView(VD_NO_FILES, visibility);
		}
	}

	private boolean isShowNoSDcard() {
		return mRootView == null ? true
				: mRootView.findViewById(R.id.sd_not_available_page).getVisibility() == View.VISIBLE;
	}

	private void reflush(FileCategory category) {
		mSortType = mFileSettingsHelper.getSortType();
		Cursor cursor = mFileCategoryHelper.getFileInfosByCategory(category, mSortType);
		if (cursor == null || cursor.getCount() <= 0) {
			showEmptyFilesView(true);
			showView(VD_NAVIGATION_BAR, false);
		} else {
			showEmptyFilesView(false);
			showView(VD_NAVIGATION_BAR, true);
		}
		mAdapter.changeCursor(cursor);
		setText(VD_PATH_TEXT, buildPathText(category));
		showView(VD_NAVIGATION_BAR, true);
		showView(VD_FILE_PATH_LIST, true);
		showView(VD_FAVORITE_LIST, false);
		showView(VD_CATEGORY_PAGE, false);
		if (mMenu != null) {
			showCategoryMenu(true);
		}
	}

	private void showCategoryMenu(boolean isShow) {
		if (mMenu != null) {
			mMenu.findItem(MenuHelper.MENU_SELECTALL).setVisible(isShow);
			mMenu.findItem(MenuHelper.MENU_SORT).setVisible(isShow);
			mMenu.findItem(MenuHelper.MENU_REFRESH).setVisible(isShow);
			mMenu.findItem(MenuHelper.MENU_SETTING).setVisible(isShow);
		}
	}

	private void setText(int viewId, String text) {
		((TextView) mRootView.findViewById(viewId)).setText(text);
	}

	private String buildPathText(FileCategory category) {
		int ext = 0;
		if (category == null)
			return null;
		switch (category) {
		case Music:
			ext = R.string.category_music;
			break;
		case Video:
			ext = R.string.category_video;
			break;
		case Picture:
			ext = R.string.category_picture;
			break;
		case Theme:
			ext = R.string.category_theme;
			break;
		case Doc:
			ext = R.string.category_document;
			break;
		case Zip:
			ext = R.string.category_zip;
			break;
		case Apk:
			ext = R.string.category_apk;
			break;
		case Favorite:
			ext = R.string.category_favorite;
			break;
		}

		return getString(R.string.title_category) + "/" + getString(ext);
	}

	private void showView(int viewId, boolean isVisbility) {
		if (mRootView != null)
			mRootView.findViewById(viewId).setVisibility(isVisbility ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onClick(View v) {
		if (getCategoryById.keySet().contains(v.getId())) {
			mCategory = getCategoryById.get(v.getId());
			if (mCategory == FileCategory.Favorite) {
				reflushFavorite();
			} else {
				reflush(mCategory);
			}
		} else {
			switch (v.getId()) {
			case R.id.current_path_pane:
				Util.buildPathListUi(getActivity(), mPathScrollView, mPathImage, mCurrentPath.getText().toString(),
						this);
				break;
			case R.id.path_list_item:
				Util.showPathScrollView(mPathScrollView, mPathImage, false);
				onBack();
				ActionMode actionMode = ((FileManagerMainActivity) getActivity()).getActionMode();
				if (actionMode != null) {
					actionMode.finish();
				}
				break;
			case R.id.path_pane_up_level:
				Util.showPathScrollView(mPathScrollView, mPathImage, false);
				onBack();
				actionMode = ((FileManagerMainActivity) getActivity()).getActionMode();
				if (actionMode != null) {
					actionMode.finish();
				}
				break;
			}
		}
	}

	@Override
	public ArrayList<FileInfo> getAllFileInfos() {
		return mAdapter == null ? null : mAdapter.getAllFileInfos();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(mMenu != null){
			initMenu(mMenu);
		}
	}

	@Override
	public void onDataChange() {
		if (mAdapter != null)
			mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onDataReflush() {
		if (!isCategoryView() && !isFavoriteView()) {
			reflush(mCategory);
		}
	}

	@Override
	public int getTotalCount() {
		return mAdapter == null ? 0 : mAdapter.getCount();
	}

	@Override
	public FileInfo getDestFileInfo() {
		return null;
	}

	@Override
	public void setOperationBarVisibility(boolean visibility) {
		mMenu.findItem(MenuHelper.MENU_SELECTALL).setVisible(false);
	}

	private boolean isFavoriteView() {
		return mRootView == null ? false : mRootView.findViewById(VD_FAVORITE_LIST).getVisibility() == View.VISIBLE;
	}

	@Override
	public void onFavoriteDatabaseChanged() {
		if (isFavoriteView())
			reflushFavorite();
		setCategoryCount(FileCategory.Favorite, mFavoriteDatabaseHelper.getCount());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (parent.getId()) {
		case R.id.favorite_list:
			FileInfo fileInfo = mFavoriteListAdapter.getItem(position);
			if (fileInfo != null && fileInfo.isDir) {
				if (mFileSDCardHelper.isDoubleCardPhone) {
					SDCardInfo internalSdCardInfo = mFileSDCardHelper.getRoot(SDCardInfo.INTERNAL_SD);
					if (fileInfo.filePath.startsWith(internalSdCardInfo.path)) {
						((IFileOperater) getActivity()).go2Folder(1, fileInfo);
					} else {
						((IFileOperater) getActivity()).go2Folder(2, fileInfo);
					}
				} else {
					((IFileOperater) getActivity()).go2Folder(1, fileInfo);
				}
			} else {
				if (fileInfo != null)
					mFileOperationHelper.viewFile(getActivity(), fileInfo);
			}
			break;
		case R.id.file_path_list:
			fileInfo = mAdapter.getFileItem(position);
			if (fileInfo != null) {
				if (FileOperationHelper.sIsShowOperationBar) {
					fileInfo.selected = !fileInfo.selected;
					mFileOperationHelper.updateFileInfoSelect(fileInfo);
					onDataChange();
				} else {
					mFileOperationHelper.viewFile(getActivity(), fileInfo);
				}
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void go2Folder(int cardId, FileInfo fileInfo) {

	}

	@Override
	public void onSDCardStateChange(boolean isMounted) {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				updateUI();
			}
		});
	}

	@Override
	public void onUpdateMenu(boolean visibility, int position) {
	}

	@Override
	public void removeDatas(ArrayList<FileInfo> datas) {
	}

	@Override
	public void addDatas(ArrayList<FileInfo> datas) {
	}

	@Override
	public void replaceDatas(ArrayList<FileInfo> oDatas, ArrayList<FileInfo> dDatas) {
	}
}
