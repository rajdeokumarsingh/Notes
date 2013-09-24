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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Activity for managing the FM recording files.
 */
public class RecordListActivity extends Activity {

    private static final String RECORDS_PATH_NAME = "FmRecords/";
    private File mRecordsFolder = new File(Environment.getExternalStorageDirectory(),
            RECORDS_PATH_NAME);
    private FileFilter mFileFilter = new FileFilter() {
        public boolean accept(File pathname) {
            if (pathname.getName().endsWith(".amr")) {
                return true;
            }
            return false;
        }
    };

    private ArrayList<File> mRecords;
    private ArrayList<File> mDeleteRecords = new ArrayList<File>();
    private LayoutInflater mInflater;
    private RecordListAdapter mRecordListAdapter;

    private ListView mRecordList;
    private View mControlBar;
    private Button mDelete;
    private Button mCancle;
    private ProgressDialog mProgressDialog;
    private TextView mNoRecords;
    private Handler mHandler;

    private DeleteTask mDeleteTask;

    private enum State {
        PLAY_STATE,
        DELETE_STATE
    }

    private State mState;
    private boolean mIsDeleting = false;
    private boolean mIsSelectAll = false;
    private static final int UPDATE = 0;
    private static final int DELETE_END = 1;
    private static final int DELETE_CANCEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new InternalHandle(this);

        createRecordFolder();
        mRecords = getRecordsList(mRecordsFolder, mFileFilter);
        mState = State.PLAY_STATE;
        mInflater = getLayoutInflater();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.record_filelist);

        mRecordList = (ListView) findViewById(R.id.record_list);
        mControlBar = findViewById(R.id.control_bar);
        mDelete = (Button) findViewById(R.id.delete);
        mCancle = (Button) findViewById(R.id.cancel);
        mProgressDialog = new ProgressDialog(RecordListActivity.this);
        mNoRecords = (TextView) findViewById(R.id.no_records);

        mRecordListAdapter = new RecordListAdapter();
        mRecordListAdapter.setFiles(mRecords);
        mRecordList.setAdapter(mRecordListAdapter);
        mRecordList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (mState == State.PLAY_STATE) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    File audioFile = mRecords.get(position);
                    Uri audioUri = Uri.fromFile(audioFile);
                    intent.setDataAndType(audioUri, "audio/*");
                    startActivity(intent);
                }
            }
        });

        mDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDeleteRecords.size() > 0) {
                    Dialog deleteConfirm = new AlertDialog.Builder(RecordListActivity.this)
                            .setTitle(R.string.delete_records)
                            .setMessage(R.string.delete_selected_records)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mProgressDialog.setMessage(getString(R.string.deleting));
                                    mProgressDialog.setOnKeyListener(new OnKeyListener() {
                                        @Override
                                        public boolean onKey(DialogInterface dialog, int keyCode,
                                                KeyEvent event) {
                                            if (mIsDeleting
                                                    && event.getAction() == KeyEvent.ACTION_UP
                                                    && keyCode == KeyEvent.KEYCODE_BACK
                                                    && mDeleteTask != null) {
                                                mDeleteTask.cancel(true);
                                                mIsDeleting = false;
                                                mHandler.sendEmptyMessage(DELETE_CANCEL);
                                            }
                                            return false;
                                        }
                                    });
                                    mProgressDialog.show();
                                    mIsSelectAll = false;
                                    mDeleteTask = new DeleteTask();
                                    mDeleteTask.execute();
                                }
                            })
                            .setNegativeButton(R.string.cancel,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create();
                    deleteConfirm.show();
                }
            }
        });

        mCancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsSelectAll = false;
                mState = State.PLAY_STATE;
                toggleState(mState);
            }
        });
        toggleState(mState);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (mState == State.PLAY_STATE) {
            if (mRecords.size() == 0) {
                return false;
            }
            menu.add(0, 0, 0, getString(R.string.delete));
        } else if (mState == State.DELETE_STATE) {
            if (!mIsSelectAll) {
                menu.add(0, 0, 0, getString(R.string.select_all));
            } else {
                menu.add(0, 0, 0, getString(R.string.unselect_all));
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mState == State.PLAY_STATE) {
            mState = State.DELETE_STATE;
            toggleState(mState);
        } else {
            toggleSelectAll();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mState == State.DELETE_STATE) {
            mState = State.PLAY_STATE;
            toggleState(mState);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private ArrayList<File> getRecordsList(File recordsFolder, FileFilter fileFilter) {
        if (recordsFolder == null) {
            return new ArrayList<File>();
        }
        File[] listFiles = recordsFolder.listFiles(fileFilter);
        if (listFiles == null) {
            return new ArrayList<File>();
        }
        return new ArrayList<File>(Arrays.asList(listFiles));
    }

    @SuppressWarnings("unchecked")
    private void toggleSelectAll() {
        if (!mIsSelectAll) {
            mDeleteRecords = (ArrayList<File>) mRecords.clone();
        } else {
            mDeleteRecords.clear();
        }
        mIsSelectAll = !mIsSelectAll;
        mHandler.sendEmptyMessage(UPDATE);
    }

    private void toggleState(State state) {
        if (state == State.PLAY_STATE) {
            mDeleteRecords.clear();
            mControlBar.setVisibility(View.GONE);
            if (mRecords.size() == 0) {
                mNoRecords.setVisibility(View.VISIBLE);
                mRecordList.setVisibility(View.GONE);
            } else {
                mNoRecords.setVisibility(View.GONE);
                mRecordList.setVisibility(View.VISIBLE);
            }
        } else {
            mControlBar.setVisibility(View.VISIBLE);
        }
    }

    private void deleteSelectRecords() {
        File record = null;
        int index;
        for (int i = 0; i < mDeleteRecords.size();) {
            if (mDeleteTask.isCancelled()) {
                return;
            }
            record = mDeleteRecords.get(i);
            index = mRecords.indexOf(record);
            mRecords.get(index).delete();
            mDeleteRecords.remove(i);
        }
        allScan();
    }

    private void allScan() {
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"
                + Environment.getExternalStorageDirectory())));
    }

    private void createRecordFolder() {
        if (!mRecordsFolder.exists()) {
            mRecordsFolder.mkdir();
        }
    }

    private void updateAdapterDataSet() {
        mRecords = getRecordsList(mRecordsFolder, mFileFilter);
        mRecordListAdapter.setFiles(mRecords);
        mRecordListAdapter.notifyDataSetChanged();
    }

    private void showToast(String value, int timeLength) {
        Toast ts = null;
        if (timeLength == Toast.LENGTH_SHORT) {
            ts = Toast.makeText(RecordListActivity.this, value, Toast.LENGTH_SHORT);
        } else {
            ts = Toast.makeText(RecordListActivity.this, value, Toast.LENGTH_LONG);
        }
        ts.show();
    }

    private static class InternalHandle extends Handler {
        private RecordListActivity mActivity;

        public InternalHandle(RecordListActivity activity) {
            this.mActivity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    mActivity.mRecordListAdapter.notifyDataSetChanged();
                    break;
                case DELETE_END:
                    mActivity.updateAdapterDataSet();
                    mActivity.mState = State.PLAY_STATE;
                    mActivity.toggleState(mActivity.mState);
                    mActivity.mProgressDialog.dismiss();
                    mActivity.showToast(mActivity.getString(R.string.delete_complete),
                            Toast.LENGTH_SHORT);
                    break;
                case DELETE_CANCEL:
                    mActivity.mDeleteRecords.clear();
                    mActivity.updateAdapterDataSet();
                    mActivity.mState = State.PLAY_STATE;
                    mActivity.toggleState(mActivity.mState);
                    mActivity.showToast(mActivity.getString(R.string.cancel_deleting),
                            Toast.LENGTH_SHORT);
                    break;
            }
        }
    }

    class RecordListAdapter extends BaseAdapter {
        private ArrayList<File> files;

        public void setFiles(ArrayList<File> files) {
            this.files = files;
        }

        @Override
        public int getCount() {
            return files.size();
        }

        @Override
        public Object getItem(int position) {
            return files.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.record_list_item, null);
                holder.fileName = (TextView) convertView.findViewById(R.id.record_filename);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.record_checkbox);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (mState == State.PLAY_STATE) {
                holder.checkBox.setVisibility(View.INVISIBLE);
            } else {
                holder.checkBox.setVisibility(View.VISIBLE);
            }
            final File record = files.get(position);
            String fileName = record.getName();
            holder.fileName.setText(fileName.substring(0, fileName.length() - 4));
            holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        if (!mIsSelectAll) {
                            mDeleteRecords.add(record);
                        }
                    } else {
                        mDeleteRecords.remove(record);
                    }

                }
            });
            if (mDeleteRecords.contains(record)) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }
            return convertView;
        }

    }

    static class ViewHolder {
        TextView fileName;
        CheckBox checkBox;
    }

    class DeleteTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            mIsDeleting = true;
            deleteSelectRecords();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mIsDeleting = false;
            mHandler.sendEmptyMessage(DELETE_END);
            super.onPostExecute(result);
        }
    }
}
