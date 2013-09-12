/* ---------------------------------------------------------------------------------------------
 *
 *            Capital Alliance Software Confidential Proprietary
 *            (c) Copyright CAS 201{x}, All Rights Reserved
 *                          www.pekall.com
 *
 * ----------------------------------------------------------------------------------------------
 */
package com.android.browser.filemanager;

import com.android.browser.R;

import android.R.drawable;
import android.support.v4.widget.CursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Adapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

/**
 * @author haoanbang
 * 
 */
public class MenuHelper {
	public static final int MENU_SEARCH = 1;

	public static final int MENU_SORT = 3;

	public static final int MENU_SEND = 7;

	public static final int MENU_RENAME = 8;

	public static final int MENU_DELETE = 9;

	public static final int MENU_INFO = 10;

	public static final int MENU_SORT_NAME = 11;

	public static final int MENU_SORT_SIZE = 12;

	public static final int MENU_SORT_DATE = 13;

	public static final int MENU_SORT_TYPE = 14;

	public static final int MENU_REFRESH = 15;

	public static final int MENU_SELECTALL = 16;

	public static final int MENU_SETTING = 17;

	public static final int MENU_EXIT = 18;

	public static final int MENU_COPY = 19;

	public static final int MENU_MOVE = 20;

	public static final int MENU_COPY_PATH = 21;

	public static final int MENU_CREATE_SHORTCUT = 22;

	public static final int MENU_OPERATION_TYPE = 23;

	public static final int MENU_OPERATION_COVER = 24;

	public static final int MENU_OPERATION_NOT_COVER = 25;

	public static final int MENU_NEW_FOLDER = 100;

	public static final int MENU_FAVORITE = 101;

	// public static final int MENU_SHOWHIDE = 117;

	public static final int MENU_HIDEMENU = 200;

	public static final int MENU_AGREE = 201;

	public static final int MENU_CANCEL = 202;

	public static final int GROUP_NORMAL = 0;

	public static final int GROUP_HIDE = 1;

	public static final int GROUP_OPERATION = 2;

	public static void onCreateHideMenu(Menu menu) {
		MenuItem item = menu.add(GROUP_HIDE, MENU_HIDEMENU, 15, R.string.title_category);
		item.setIcon(R.drawable.operation_button_copy);
	}

	public static void onCreateExitMenu(Menu menu) {
		addMenuItem(menu, MENU_EXIT, 9, R.string.menu_exit, R.drawable.ic_menu_exit);
	}

	public static void onCreateOperationMenu(Menu menu, boolean isCategoryView) {
		if (menu.findItem(MENU_HIDEMENU) != null) {
			menu.findItem(MENU_HIDEMENU).setVisible(false);
		}
		if (!isCategoryView) {
			if (menu.findItem(MENU_AGREE) == null)
				menu.add(GROUP_OPERATION, MENU_AGREE, 16, R.string.operation_paste);
			if (menu.findItem(MENU_CANCEL) == null)
				menu.add(GROUP_OPERATION, MENU_CANCEL, 17, R.string.operation_cancel);
			if (menu.findItem(MENU_OPERATION_TYPE) == null) {
				SubMenu sortMenu = menu.addSubMenu(GROUP_OPERATION, MENU_OPERATION_TYPE, 18, R.string.menu_setting);
				addMenuItem(sortMenu, MENU_OPERATION_COVER, 0, R.string.menu_operation_cover);
				addMenuItem(sortMenu, MENU_OPERATION_NOT_COVER, 2, R.string.menu_operation_not_cover);
				sortMenu.setGroupCheckable(0, true, true);
				sortMenu.getItem(0).setChecked(true);
			}
		}
		addMenuItem(menu, MENU_SELECTALL, 0, R.string.operation_select_operation, R.drawable.ic_menu_select_all);
		SubMenu sortMenu = menu.addSubMenu(GROUP_NORMAL, MENU_SORT, 1, R.string.menu_item_sort).setIcon(
				R.drawable.ic_menu_sort);
		addMenuItem(sortMenu, MENU_SORT_NAME, 0, R.string.menu_item_sort_name);
		addMenuItem(sortMenu, MENU_SORT_SIZE, 1, R.string.menu_item_sort_size);
		addMenuItem(sortMenu, MENU_SORT_DATE, 2, R.string.menu_item_sort_date);
		addMenuItem(sortMenu, MENU_SORT_TYPE, 3, R.string.menu_item_sort_type);
		sortMenu.setGroupCheckable(0, true, true);
		sortMenu.getItem(0).setChecked(true);

		// addMenuItem(menu, GlobalConsts.MENU_PASTE, 2,
		// R.string.operation_paste);
		addMenuItem(menu, MENU_SEARCH, 3, R.string.operation_search, R.drawable.ic_menu_search);
		if (!isCategoryView) {
			addMenuItem(menu, MENU_NEW_FOLDER, 4, R.string.operation_create_folder, R.drawable.ic_menu_new_folder);
			addMenuItem(menu, MENU_FAVORITE, 5, R.string.operation_favorite, R.drawable.ic_menu_delete_favorite);
			// addMenuItem(menu, MENU_SHOWHIDE, 6, R.string.operation_show_sys,
			// R.drawable.ic_menu_show_sys);
		}
		addMenuItem(menu, MENU_REFRESH, 7, R.string.operation_refresh, R.drawable.ic_menu_refresh);
		addMenuItem(menu, MENU_SETTING, 8, R.string.menu_setting, drawable.ic_menu_preferences);
		addMenuItem(menu, MENU_EXIT, 9, R.string.menu_exit, R.drawable.ic_menu_exit);
	}

	public static void onCreateContextMenu(ContextMenu menu, boolean noCreate, FileInfo fileInfo) {
		if (noCreate) {
			return;
		}

		FavoriteDatabaseHelper databaseHelper = FavoriteDatabaseHelper.getInstance();
		if (databaseHelper != null && fileInfo != null) {
			int stringId = databaseHelper.isFavorite(fileInfo.filePath) ? R.string.operation_unfavorite
					: R.string.operation_favorite;
			addMenuItem(menu, MENU_FAVORITE, 0, stringId);
		}
		if (fileInfo != null && !fileInfo.isDir)
			addMenuItem(menu, MENU_SEND, 0, R.string.operation_send);
		addMenuItem(menu, MENU_COPY, 0, R.string.operation_copy);
		// addMenuItem(menu, MENU_COPY_PATH, 0, R.string.operation_copy_path);
		addMenuItem(menu, MENU_MOVE, 0, R.string.operation_move);
		addMenuItem(menu, MENU_DELETE, 0, R.string.operation_delete);
		addMenuItem(menu, MENU_RENAME, 0, R.string.operation_rename);
		if (fileInfo != null && fileInfo.isDir)
			addMenuItem(menu, MENU_CREATE_SHORTCUT, 0, R.string.operation_create_shortcut);
		addMenuItem(menu, MENU_INFO, 0, R.string.operation_info);

	}

	private static void addMenuItem(Menu menu, int itemId, int order, int string) {
		addMenuItem(menu, itemId, order, string, -1);
	}

	private static void addMenuItem(Menu menu, int itemId, int order, int string, int iconRes) {
		if (menu.findItem(itemId) == null) {
			MenuItem item = menu.add(GROUP_NORMAL, itemId, order, string);
			if (iconRes > 0) {
				item.setIcon(iconRes);
			}
		}
	}

}
