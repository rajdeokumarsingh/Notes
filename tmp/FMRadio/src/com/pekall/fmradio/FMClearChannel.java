/*
 * Copyright (C) 2013 Capital Alliance Software LTD (Pekall)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pekall.fmradio;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDiskIOException;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Clear Channel Activity.
 */
public class FMClearChannel extends ListActivity implements
        View.OnClickListener {
    private static final String TAG = "FMClearChannel";

    @SuppressWarnings("unused")
    private static final int PRJ_ID_ID = 0;
    private static final int PRJ_ID_CH_FREQ = 1;
    private static final int PRJ_ID_CH_NAME = 2;
    private static final String[] PROJECTION = new String[] {
            FMDataProvider.PROJECTION_ID,
            FMDataProvider.PROJECTION_CH_FREQ,
            FMDataProvider.PROJECTION_CH_NAME,
    };

    private static final int CLEAR_ID = Menu.FIRST;
    private static final int SELECT_ALL_ID = Menu.FIRST + 1;
    private static final int UNSELECT_ALL_ID = Menu.FIRST + 2;

    private static final int DIALOG_CONFIRM_CLEAR = 0;

    private static int CHANNEL_NUM = 0;
    private int mCurNum = 0;
    private ListView mPresetListView;
    private CheckBox mCheckBoxAll = null;
    private Button mClearDone = null;
    private boolean mSelectedAll = false;
    public static boolean mDeleteAll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.clear_ch);
        setTitle(R.string.clear_presets);

        Intent intent = getIntent();
        if (intent != null) {
            mCurNum = intent.getIntExtra("current_num", 1);
        }

        initResourceRefs();
        updateUI();
    }

    private void initResourceRefs() {
        mPresetListView = (ListView) findViewById(android.R.id.list);
        mClearDone = (Button) findViewById(R.id.btn_done);
        mCheckBoxAll = (CheckBox) findViewById(R.id.checkbox);
        mClearDone.setOnClickListener(this);
        mCheckBoxAll.setOnClickListener(this);
    }

    private void updateUI() {
        CHANNEL_NUM = 0;
        Cursor cur = null;
        try {
            cur = getContentResolver().query(FMDataProvider.CONTENT_URI, PROJECTION, null,
                    null, null);
        } catch (SQLiteDiskIOException e) {
            Log.i(TAG, "onOpen : " + e.toString());
            if (cur != null) {
                cur.close();
                cur = null;
            }
        }

        if (cur == null) {
            Log.e(TAG, "cur is null, ERROR!");
            finish();
            return;
        }

        ArrayList<Map<String, Object>> coll = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;

        cur.moveToFirst();
        int i = 1;
        while (!cur.isAfterLast()) {
            item = new HashMap<String, Object>();

            if (cur.getString(PRJ_ID_CH_NAME).equals("")) {
                if (cur.getString(PRJ_ID_CH_FREQ).equals("")) {
                    item.put(
                            "c1",
                            getString(R.string.preset) + " "
                                    + Integer.toString(i) + "("
                                    + getString(R.string.empty) + ")");
                } else {
                    item.put(
                            "c1",
                            getString(R.string.preset) + " "
                                    + Integer.toString(i) + "("
                                    + cur.getString(PRJ_ID_CH_FREQ) + "MHz" + ")");
                    CHANNEL_NUM = CHANNEL_NUM + 1;
                }
            } else {
                item.put("c1",
                        getString(R.string.preset) + " " + Integer.toString(i)
                                + "(" + cur.getString(PRJ_ID_CH_NAME) + ")");
            }
            coll.add(item);
            cur.moveToNext();
            i++;
        }
        cur.close();
        Log.d(TAG, "get data to coll, ready to setListAdapter. ");
        SimpleAdapter.ViewBinder viewBinder = new SimpleAdapter.ViewBinder() {
            public boolean setViewValue(View view, Object obj, String str) {
                CheckedTextView v = (CheckedTextView) view;
                v.setText(obj.toString());
                return true;
            }
        };

        SimpleAdapter sa = new SimpleAdapter(this, coll,
                R.layout.simple_list_item_multiple_choice,
                new String[] {
                        "c1"
                }, new int[] {
                        R.id.text1
                });

        sa.setViewBinder(viewBinder);
        this.setListAdapter(sa);

        Log.d(TAG, "after setListAdapter!");

        mPresetListView.setFocusableInTouchMode(true);
        mPresetListView.requestFocus();
        mPresetListView.setFocusable(true);
        mPresetListView.setItemsCanFocus(true); // true
        mPresetListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mPresetListView.setCacheColorHint(0);
        mPresetListView.setDivider(this.getResources().getDrawable(
                R.drawable.fm_list_line));
        mPresetListView.setDividerHeight(1);
        mPresetListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                SparseBooleanArray checkArray = mPresetListView
                        .getCheckedItemPositions();
                if (checkArray == null)
                    return;
                for (int i = 0; i < Util.MAX_CHANNEL_NUM; i++) {
                    if (!checkArray.get(i)) {
                        if (mCheckBoxAll.isChecked()) {
                            mCheckBoxAll.setChecked(false);
                            mSelectedAll = false;
                        }
                        return;
                    }
                }
                mCheckBoxAll.setChecked(true);
                mSelectedAll = true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, CLEAR_ID, 0, R.string.clear).setIcon(
                android.R.drawable.ic_menu_delete);
        menu.add(0, SELECT_ALL_ID, 1, R.string.select_all).setIcon(
                R.drawable.ic_menu_select_all);
        menu.add(0, UNSELECT_ALL_ID, 2, R.string.unselect_all).setIcon(
                R.drawable.ic_menu_unselect_all);

        return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkbox:
                if (mCheckBoxAll.isChecked()) {
                    for (int j = 0; j < Util.MAX_CHANNEL_NUM; j++) {
                        mPresetListView.setItemChecked(j, true);
                    }
                    mSelectedAll = true;
                } else {
                    for (int j = 0; j < Util.MAX_CHANNEL_NUM; j++) {
                        mPresetListView.setItemChecked(j, false);
                    }
                    mSelectedAll = false;
                }
                break;
            case R.id.btn_done:
                boolean isAnySelected = false;
                SparseBooleanArray checked = mPresetListView
                        .getCheckedItemPositions();
                if (checked == null)
                    return;
                for (int i = 0; i < Util.MAX_CHANNEL_NUM; i++) {
                    if (checked.get(i))
                        isAnySelected = true;
                }
                if (!isAnySelected) {
                    Intent clear_result = new Intent();
                    clear_result.putExtra("isClearAll", false);
                    setResult(RESULT_OK, clear_result);
                    finish();
                } else {
                    showDialogFragment(FMClearChannel.this, DIALOG_CONFIRM_CLEAR);
                }
                break;
            default:
                return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case CLEAR_ID:
                boolean isAnySelected = false;
                SparseBooleanArray checked = mPresetListView
                        .getCheckedItemPositions();
                if (checked == null)
                    break;
                for (int i = 0; i < Util.MAX_CHANNEL_NUM; i++) {
                    if (checked.get(i))
                        isAnySelected = true;
                }
                if (!isAnySelected) {
                    Intent clear_result = new Intent();
                    clear_result.putExtra("isClearAll", false);
                    setResult(RESULT_OK, clear_result);
                    finish();
                } else
                    showDialogFragment(FMClearChannel.this, DIALOG_CONFIRM_CLEAR);

                break;
            case SELECT_ALL_ID:
                mCheckBoxAll.setChecked(true);
                mSelectedAll = true;

                for (int j = 0; j < Util.MAX_CHANNEL_NUM; j++) {
                    mPresetListView.setItemChecked(j, true);
                }

                break;
            case UNSELECT_ALL_ID:
                if (mCheckBoxAll.isChecked()) {
                    mCheckBoxAll.setChecked(false);
                    mSelectedAll = false;
                }

                for (int j = 0; j < Util.MAX_CHANNEL_NUM; j++) {
                    mPresetListView.setItemChecked(j, false);
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialogFragment(FMClearChannel activity, int id) {
        DialogFragment newFragment = MyAlertDialogFragment.newInstance(
                activity, id);
        newFragment.show(getFragmentManager(), "dialog");
    }

    public static class MyAlertDialogFragment extends DialogFragment {
        static FMClearChannel mActivity;

        public static MyAlertDialogFragment newInstance(
                FMClearChannel activity, int id) {
            mActivity = activity;
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("id", id);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int id = getArguments().getInt("id");

            switch (id) {
                case DIALOG_CONFIRM_CLEAR: {
                    return new AlertDialog.Builder(mActivity)
                            .setTitle(R.string.clear_presets)
                            .setMessage(R.string.confirm_clear_presets)
                            .setPositiveButton(R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                            boolean isLastPositionCleared = false;
                                            int checked_num = 0;
                                            int checked_num1 = 0;
                                            SparseBooleanArray checked = mActivity.mPresetListView
                                                    .getCheckedItemPositions();
                                            if (checked == null)
                                                return;
                                            for (int i = 0; i < Util.MAX_CHANNEL_NUM; i++) {
                                                if (checked.get(i)) {
                                                    checked_num++;
                                                    Log.d(TAG, "checked.get(i) "
                                                            + checked.get(i));
                                                    Log.d(TAG, "checked_num is "
                                                            + checked_num);
                                                    ContentValues cv = new ContentValues();
                                                    if (!mDeleteAll) {
                                                        Cursor c = mActivity
                                                                .getContentResolver()
                                                                .query(FMDataProvider.CONTENT_URI,
                                                                        PROJECTION,
                                                                        FMDataProvider.PROJECTION_ID
                                                                                + "="
                                                                                + i,
                                                                        null, null);
                                                        if (c != null) {
                                                            c.moveToFirst();
                                                            if (c.getString(PRJ_ID_CH_FREQ) != null
                                                                    && c.getString(
                                                                            PRJ_ID_CH_FREQ)
                                                                            .length() != 0) {
                                                                checked_num1 = checked_num1 + 1;
                                                            }
                                                            c.close();
                                                        }
                                                    }
                                                    cv.put(FMDataProvider.PROJECTION_ID,
                                                            Integer.toString(i));
                                                    cv.put(FMDataProvider.PROJECTION_CH_FREQ, "");
                                                    cv.put(FMDataProvider.PROJECTION_CH_NAME, "");
                                                    try {
                                                        mActivity
                                                                .getContentResolver()
                                                                .update(FMDataProvider.CONTENT_URI,
                                                                        cv,
                                                                        FMDataProvider.PROJECTION_ID
                                                                                + "="
                                                                                + Integer
                                                                                        .toString(i),
                                                                        null);
                                                    } catch (SQLiteDiskIOException e) {
                                                        Log.i(TAG,
                                                                "update : "
                                                                        + e.toString());
                                                    }
                                                    if (i == mActivity.mCurNum) {
                                                        isLastPositionCleared = true;
                                                    }
                                                }
                                            }
                                            Log.d(TAG, "checked_num1   "
                                                    + checked_num1);
                                            if (mActivity.mSelectedAll) {
                                                FMClearChannel.mDeleteAll = true;
                                            } else if (checked_num1 == CHANNEL_NUM) {
                                                FMClearChannel.mDeleteAll = true;
                                            } else {
                                                FMClearChannel.mDeleteAll = false;
                                            }
                                            Intent clear_result = new Intent();
                                            if (checked_num == Util.MAX_CHANNEL_NUM) {
                                                clear_result.putExtra("isClearAll",
                                                        true);
                                            } else {
                                                clear_result.putExtra("isClearAll",
                                                        false);
                                                clear_result.putExtra(
                                                        "isLastPositionCleared",
                                                        isLastPositionCleared);
                                            }
                                            mActivity.setResult(RESULT_OK,
                                                    clear_result);
                                            mActivity.finish();
                                        }
                                    })
                            .setNegativeButton(R.string.cancel,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                            // do something for cancel
                                            Intent clear_result = new Intent();
                                            clear_result.putExtra("isClearAll",
                                                    false);
                                            mActivity.setResult(RESULT_OK,
                                                    clear_result);
                                            dialog.dismiss();
                                        }
                                    }).create();
                }
            }
            return null;
        }
    }
}
