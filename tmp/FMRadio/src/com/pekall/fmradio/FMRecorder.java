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

import android.media.AudioSystem;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class FMRecorder {
    private final static String LOGTAG = "FMRecorder";

    /**
     * Depends on platform audio system. Please keep it same with
     * AudioSystem.java and MediaRecorder.java.
     */
    // static final int DEVICE_IN_FM_RX = AudioSystem.DEVICE_IN_FM_RX;
    static final int DEVICE_IN_FM_RX = AudioSystem.DEVICE_IN_BUILTIN_MIC;
    // static final int AUDIO_SOURCE_FMRADIO = MediaRecorder.AudioSource.FMRADIO;
    static final int AUDIO_SOURCE_FMRADIO = MediaRecorder.AudioSource.MIC;

    private static final String RECORDS_PATH_NAME = "FmRecords/";
    private static final String RECORD = "fmrecord_";
    private static final long BLOCK_SIZE = 1024 * 512;
    private final Animation rotateAnimation;
    private final FMRadio mActivity;
    private MediaRecorder mMediaRecorder;
    private String mRecordFileName;
    private long mRecordStart;
    private long mRecordEnd;
    private long mMaxRecordFileSize;
    private boolean mIsRecording = false;
    private File mNewRecordFile;
    private final ImageView mImage;

    private static InternalHandler mHandler;

    private Runnable mCheckBlockSize = new Runnable() {
        public void run() {
            if (!mIsRecording) {
                return;
            }

            long blockSize = freeSpaceOnSd() - BLOCK_SIZE;
            if (blockSize <= 0) {
                Toast.makeText(mActivity, R.string.sdcard_no_space_record, Toast.LENGTH_SHORT)
                        .show();
                toggleRecord();
                return;
            }
            mActivity.getHandler().postDelayed(this, 5000);
        }
    };

    private static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        return sdf.format(new Date());
    }

    private static long freeSpaceOnSd() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
    }

    FMRecorder(FMRadio activity) {
        mActivity = activity;
        mImage = (ImageView) mActivity.findViewById(R.id.record_img);
        rotateAnimation = new RotateAnimation(0f, 359f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnimation.setInterpolator(lin);
        rotateAnimation.setRepeatCount(3600 * 10000);
        mImage.setImageResource(R.drawable.white_background);

        mHandler = new InternalHandler(this);
    }

    @SuppressWarnings("deprecation")
    public void beginRecord() {
        Log.i(LOGTAG, "beginRecord");
        try {
            mImage.setVisibility(View.VISIBLE);
            mImage.setImageResource(R.drawable.recordimage);
            rotateAnimation.setDuration(3000);
            mImage.setAnimation(rotateAnimation);
            mImage.startAnimation(rotateAnimation);

            mMediaRecorder.setAudioSource(AUDIO_SOURCE_FMRADIO);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setOutputFile(mRecordFileName);
            mMediaRecorder.setOnErrorListener(new
                    MediaRecorder.OnErrorListener() {
                        public void onError(MediaRecorder mr, int what, int extra) {
                            Toast.makeText(mActivity, R.string.fmrecord_error,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            mActivity.getHandler().postDelayed(mCheckBlockSize, 5000);
            mRecordStart = System.currentTimeMillis();

            mIsRecording = true;
            updateRecordUI();

            Message msg = mActivity.getHandler().obtainMessage(FMRadio.FM_START_RECORD);
            mActivity.getHandler().sendMessage(msg);
        } catch (Exception e) {
            Log.e(LOGTAG, "", e);

            mIsRecording = false;
            updateRecordUI();

            Toast.makeText(mActivity, R.string.error_app_internal, Toast.LENGTH_LONG).show();

            mRecordEnd = System.currentTimeMillis();
            if (mMediaRecorder != null) {
                mMediaRecorder.release();
            }
            mMediaRecorder = null;

            AudioSystem.setDeviceConnectionState(DEVICE_IN_FM_RX,
                    AudioSystem.DEVICE_STATE_UNAVAILABLE, "");
        }
    }

    public void endRecord() {
        Log.i(LOGTAG, "endRecord");
        boolean isError = false;

        mIsRecording = false;
        updateRecordUI();

        mRecordEnd = System.currentTimeMillis();
        try {
            mMediaRecorder.stop();
        } catch (IllegalStateException ie) {
            Log.e(LOGTAG, "", ie);
            isError = true;
        } catch (Exception e) {
            Log.e(LOGTAG, "", e);
            isError = true;
        } finally {
            if (mMediaRecorder != null) {
                mMediaRecorder.release();
            }
            mMediaRecorder = null;
        }

        if (isError) {
            if (mNewRecordFile != null && mNewRecordFile.exists()) {
                mNewRecordFile.delete();
            }
        } else {
            ContentHelper.saveRecordingFile(mActivity, mNewRecordFile, mRecordEnd - mRecordStart);
            Toast.makeText(mActivity, mRecordFileName, Toast.LENGTH_LONG).show();
        }

        AudioSystem.setDeviceConnectionState(DEVICE_IN_FM_RX,
                AudioSystem.DEVICE_STATE_UNAVAILABLE, "");
    }

    public synchronized void toggleRecord() {
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }

        if (!mIsRecording) {
            mMaxRecordFileSize = freeSpaceOnSd() - BLOCK_SIZE;
            if (mMaxRecordFileSize <= 0) {
                Toast.makeText(mActivity, R.string.sdcard_no_space_record, Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            File recordsFolder = new File(Environment.getExternalStorageDirectory(),
                    RECORDS_PATH_NAME);
            if (!recordsFolder.exists()) {
                recordsFolder.mkdir();
            }
            mNewRecordFile = new File(recordsFolder.getPath(), RECORD + getCurrentTime() + ".amr");
            if (!mNewRecordFile.exists()) {
                try {
                    mNewRecordFile.createNewFile();
                } catch (IOException e) {
                    Log.e(LOGTAG, "", e);
                    return;
                }
            }
            mRecordFileName = mNewRecordFile.getPath();
            AudioSystem.setDeviceConnectionState(DEVICE_IN_FM_RX,
                    AudioSystem.DEVICE_STATE_AVAILABLE, "");

            // It takes some time to enable the input device
            mActivity.getHandler().sendEmptyMessageDelayed(FMRadio.FM_RECORD_BEGIN, 500);
        } else {
            mActivity.getHandler().sendEmptyMessageDelayed(FMRadio.FM_RECORD_END, 10);
        }
    }

    public void updateRecordUI() {
        if (mIsRecording) {
            Message msg = mHandler.obtainMessage(start_ui);
            mHandler.sendMessage(msg);
        } else {
            mHandler.removeMessages(start_ui);
            rotateAnimation.cancel();

            mImage.setImageResource(R.drawable.white_background);
            mActivity.getHandler().removeMessages(FMRadio.FM_START_RECORD);
        }
    }

    public boolean isRecording() {
        return mIsRecording;
    }

    private static final int start_ui = 0;

    private static final class InternalHandler extends Handler {
        private final FMRecorder mRecorder;

        InternalHandler(FMRecorder r) {
            mRecorder = r;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case start_ui:
                    this.sendEmptyMessageDelayed(start_ui, 3000 * 3);
                    mRecorder.mImage.setVisibility(View.VISIBLE);
                    mRecorder.mImage.setImageResource(R.drawable.recordimage);
                    mRecorder.rotateAnimation.setDuration(3000);
                    mRecorder.mImage.setAnimation(mRecorder.rotateAnimation);
                    mRecorder.mImage.startAnimation(mRecorder.rotateAnimation);
                    break;
            }
        }
    }

    public void updateRecordTime() {
        Message msg = new Message();
        msg.what = FMRadio.FM_START_RECORD;
        mActivity.getHandler().sendMessageDelayed(msg, 1000);
    }
}
