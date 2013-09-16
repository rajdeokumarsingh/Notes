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

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.RemoteException;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.pekall.fmradio.FMHorizontalScrollView.OnScrollListener;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FMRadio extends Activity implements View.OnClickListener,
        View.OnLongClickListener, SeekBar.OnSeekBarChangeListener,
        View.OnTouchListener, ViewSwitcher.ViewFactory {

    private static final String TAG = "FMRadio";
    int flags = 0;
    private String[] items = {
            "GBK", "UTF8", "UTF16"
    };
    private int mRdsLocaleId = RDSController.RDS_LOCALE_UTF8;

    // TODO: consider display different RDS string in different area
    @SuppressWarnings("unused")
    private int mRdsType = RDSController.RDS_TYPE_UNKNOWN;

    private byte[] mRdsBytes = null;

    private static final int FREQUENCY_ADJUST_STEP = 100;
    public static final int LOW_FREQUENCY = 87500;
    public static final int HIGH_FREQUENCY = 108000;
    private static final int LIGHT_ON_TIME = 40000; // 40s light on when scan
    private static final int LONG_PRESS_TUNE_TIMEOUT = 100;
    private static final int LONG_PRESS_SEEK_TIMEOUT = 2000;

    public static int mCurrentFreq = LOW_FREQUENCY;
    static int FmChannel = 1; // 0 or 1 or 2
    private int mFirstCounter = 0;
    private int mLastPosition = 0; // position in channel list User last clicked
    private int mSeekSaveCount = 0; // number count when seeking all frequency
    private long mListViewSelPos = 0;
    private TextView channel_num = null;
    private TextView channel_str = null;
    private boolean mIsFMRadioEnabled = false; // FM radio power on or not
    private boolean mIsSeekAll = false; // seek all or once
    private boolean mIsScanCanceled = false; // if seeking is canceled when
                                             // seeking all frequency
    private boolean mIsScannedBefore = false; // if FMRadio is started the first
                                              // time
    private boolean mIsLongPressed = false; // if add/reduce frequency button
                                            // long pressed
    private boolean mIsSeekbackwardLongPressed = false; // if seek backward
                                                        // button long pressed
    private boolean mIsSeekforwardLongPressed = false; // if seek forward button
                                                       // long pressed
    private boolean mIsSwitchOff = false;
    private int mRssiThreshold = 7;

    private FMRecorder mRecorder;

    private TextView fm_channel;

    private ImageButton mPlayStop;
    private ImageButton mSoundButton;
    private ImageButton mAddButton;
    private ImageButton mReduceButton;

    private ArrayList<FMInfo> mFMResults;

    // RDS information
    private MarqueeText mInfoText;

    // Dialogs
    private ProgressDialog mScanningDialog;
    private ProgressDialog mLaunchingDialog;
    private static final int DIALOG_SCANNING_PROGRESS = 0;
    private static final int DIALOG_IF_SCAN_FIRST = 1;
    private static final int DIALOG_IF_SCAN_NEXT = 2;
    private static final int DIALOG_CONTEXT_REPLACE = 3;
    private static final int DIALOG_CONTEXT_CLEAR = 4;
    private static final int DIALOG_LAUNCHING_PROGRESS = 5;
    private static final int DIALOG_CONTEXT_ADD = 7;

    // Menu items
    static final int CLEAR_ID = Menu.FIRST;
    static final int EXIT_ID = Menu.FIRST + 1;
    static final int SCAN_SAVE_ID = Menu.FIRST + 2;
    static final int PLAY_MENU_ID = Menu.FIRST + 4;
    static final int EDIT_MENU_ID = Menu.FIRST + 5;
    static final int REPLACE_MENU_ID = Menu.FIRST + 6;
    static final int CLEAR_MENU_ID = Menu.FIRST + 7;
    static final int BY_LOUDSPEAKER_ID = Menu.FIRST + 8;
    static final int BY_HEADSET_ID = Menu.FIRST + 9;
    static final int RECODER_ID = Menu.FIRST + 10;
    static final int RECORDSLIST_ID = Menu.FIRST + 11;
    static final int SETCHANNEL_ID = Menu.FIRST + 15;
    static final int RDS_CHARSET = Menu.FIRST + 16;
    static final int ADD_MENU_ID = Menu.FIRST + 17;

    private SharedPreferences mPerferences;
    private static final int SAVE_CODE = 0;
    private static final int EDIT_CODE = 1;
    private static final int CLEAR_CODE = 2;

    private ViewGroup mChannels;
    private FMHorizontalScrollView mScrollview;
    private static int mScrollChannel = 0;
    private static int mOnLongClickChannel = 0;

    static int CHANNLE_WIDTH;

    private BroadcastReceiver mIntentReceiver = null;
    private WakeLock mWakeLock;

    private FreqIndicator mFreqIndicator;
    private TunerView mTunerView;

    private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() {
        public void openFMRadioCallBack(boolean result) {
            Log.i(TAG, "openFMRadioCallBack: " + result);
            if (result) {
                mHandler.sendEmptyMessage(FM_OPEN_SUCCEED);
            } else {
                mHandler.sendEmptyMessage(FM_OPEN_FAILED);
            }
        }

        public void openFMRadioTxCallBack(boolean result) {
        }

        public void setFrequencyCallback(boolean result) {
            mHandler.sendEmptyMessage(FM_SETFREQ_SUCCEED);
        }

        public void seekStationCallback(boolean result, int frequency) {
            Log.i(TAG, "seekStationCallback: " + result + ", " + frequency);
            if (result) {
                mCurrentFreq = frequency;
                mHandler.sendEmptyMessage(FM_SEEK_SUCCEED);
            } else {
                mHandler.sendEmptyMessage(FM_SEEK_FAILED);
            }
        }
    };

    private TunerView.OnMoveListener mTunerViewMoveListener = new TunerView.OnMoveListener() {
        private int mSteps = 0;
        private static final int TUNE_STEP = 3;

        public void onMove(TunerView tunerView, int step) {
            if (tunerView.isEnabled() && step != 0) {
                Log.i(TAG, "maually tune, step: " + step);
                mSteps += step;
                if (mSteps >= TUNE_STEP) {
                    Log.i(TAG, "maually add freq button clicked");
                    mCurrentFreq += FREQUENCY_ADJUST_STEP;
                    if (mCurrentFreq > HIGH_FREQUENCY) {
                        mCurrentFreq = LOW_FREQUENCY;

                    }
                    enableUI(false, true);
                    setFMRadioFreq();
                    updateDisplayPanel(mCurrentFreq);
                    mSteps = 0;
                } else if (mSteps <= -TUNE_STEP) {
                    Log.i(TAG, "maually reduce freq button clicked");
                    mCurrentFreq -= FREQUENCY_ADJUST_STEP;
                    if (mCurrentFreq < LOW_FREQUENCY) {
                        mCurrentFreq = HIGH_FREQUENCY;
                    }
                    enableUI(false, true);
                    setFMRadioFreq();
                    updateDisplayPanel(mCurrentFreq);
                    mSteps = 0;
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate() IN...");
        super.onCreate(savedInstanceState);

        try {
            mPerferences = PreferenceManager.getDefaultSharedPreferences(this);
            FmChannel = mPerferences.getInt("counter", 1);
            mRdsLocaleId = mPerferences.getInt("rdsLocaleId",
                    RDSController.RDS_LOCALE_UTF8);
        } catch (Exception e1) {
            FmChannel = 1;
            mRdsLocaleId = RDSController.RDS_LOCALE_UTF8;
            e1.printStackTrace();
        }

        CHANNLE_WIDTH = 1;// Integer.parseInt(getResources().getString(R.string.channel_width));
        getLayoutInflater().inflate(R.layout.action_bar_display_options_custom,
                null);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.main);

        final ActionBar bar = getActionBar();
        flags = ActionBar.DISPLAY_SHOW_HOME;
        int change = bar.getDisplayOptions() ^ flags;
        bar.setDisplayOptions(change, flags);

        Intent startFm = new Intent(INTENT_START_FM);
        sendBroadcast(startFm);

        restoreLastStatus();

        // initialize the view reference
        initMainView();

        // get a WakeLock and do not go to sleep mode when seeking frequency
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, this
                .getClass().getName());
        mWakeLock.setReferenceCounted(false);

        // bind to FMRadioService
        bindToService();

        // in order to check headset plug in/out event
        registerBroadcastListener();

        mRecorder = new FMRecorder(this);
    }

    static boolean checkFMFreq(int freq) {
        return (freq != -1 && freq >= LOW_FREQUENCY && freq <= HIGH_FREQUENCY);
    }

    private void initMainView() {
        Log.i(TAG, "initMainView() IN...");

        mTunerView = (TunerView) findViewById(R.id.fm_tuner_view);
        mTunerView.setOnMoveListener(mTunerViewMoveListener);

        mAddButton = (ImageButton) findViewById(R.id.btn_fmincrease);
        mAddButton.setOnClickListener(this);

        mReduceButton = (ImageButton) findViewById(R.id.btn_fmdecrease);
        mReduceButton.setOnClickListener(this);

        mChannels = (LinearLayout) findViewById(R.id.channels);
        mChannels.setOnTouchListener(this);
        mScrollview = (FMHorizontalScrollView) findViewById(R.id.scrollview);
        mScrollview.setOnTouchListener(this);
        mScrollview.setScrollbarFadingEnabled(true);
        mScrollview.setOnScrollListener(new OnScrollListener() {
            public void onAfterFling() {
                currentChannel();
            }
        });

        fm_channel = (TextView) this.findViewById(R.id.fm_channel);
        float tFreqNum = (float) mCurrentFreq / 1000.0f;
        fm_channel.setText(String.valueOf(tFreqNum));
        fm_channel.setTypeface(Typeface.SERIF, Typeface.BOLD);

        initSeekBar();
        initChannelList();
        initCtrlButton();

        enableUI(false, false);

        mInfoText = (MarqueeText) findViewById(R.id.info_text);
        Log.i(TAG, "leave initMainView()");
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart() IN...");
        super.onStart();
        if (getFMServiceStatus() == FMRadioService.FM_SERVICE_ON) {
            mHandler.sendEmptyMessage(START_FMRADIO);
        }
        updateDisplayPanel(mCurrentFreq);
    }

    private void updateTrackInfo() {
        try {
            if (mFMService != null) {
                int freq = mFMService.getFMFreq();
                if (checkFMFreq(freq)) {
                    Log.i(TAG, "updateTrackInfo(): freq = " + freq);
                    mCurrentFreq = freq;
                }

                mIsFMRadioEnabled = (mFMService.getFMServiceStatus() == FMRadioService.FM_SERVICE_ON);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume() IN...");
        super.onResume();

        updateTrackInfo();
        updateDisplayPanel(mCurrentFreq);
        updateSwithcButton();

        // Enable RDS when going foreground
        enableRds();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause() IN...");
        super.onPause();

        mWakeLock.release();
        ContentHelper.saveConfig(this, mLastPosition, mCurrentFreq,
                mIsScannedBefore, mRssiThreshold);

        // Disable RDS when going background
        disableRds();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy() IN...");
        super.onDestroy();

        if (mRecorder.isRecording()) {
            mRecorder.endRecord();
        }
        mRecorder = null;

        mFMService = null;
        unregisterReceiver(mIntentReceiver);
        unbindFMRadioService();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "onConfigurationChanged() called");
        setContentView(R.layout.main);
        initMainView();

        boolean mute = false;
        try {
            mute = mFMService.isMuteFM();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setMuteUi(mute);

        updateDisplayPanel(mCurrentFreq);
        if (mIsLongPressed || mIsSeekbackwardLongPressed
                || mIsSeekforwardLongPressed) {
            setFMRadioFreq();
            mIsLongPressed = false;
            mIsSeekbackwardLongPressed = false;
            mIsSeekforwardLongPressed = false;
        }
        CHANNLE_WIDTH = 0;// Integer.parseInt(getResources().getString(R.string.channel_width));
        enableUI(true, true);
        mRecorder.updateRecordUI();
        updateSwithcButton();
    }

    private void updateSwithcButton() {
        mPlayStop.setBackgroundResource(mIsFMRadioEnabled ? R.drawable.btn_stop
                : R.drawable.btn_play);
    }

    class UiStatus {
        int mLastPosition;
        int mCurrentFreq;
        boolean mIsScannedBefore;

        UiStatus() {
            mLastPosition = 0;
            mCurrentFreq = LOW_FREQUENCY;
            mIsScannedBefore = false;
        }
    }

    private void restoreLastStatus() {
        UiStatus status = new UiStatus();
        ContentHelper.loadConfig(this, status);
        mLastPosition = status.mLastPosition;
        mCurrentFreq = status.mCurrentFreq;
        mIsScannedBefore = status.mIsScannedBefore;
    }

    private void initSeekBar() {
        mFreqIndicator = (FreqIndicator) findViewById(R.id.freq_indicator_view);
        mFreqIndicator.setOnSeekBarChangeListener(this);
    }

    private void initChannelList() {
        // mLeft = (Button) findViewById(R.id.left);
        // mRight = (Button) findViewById(R.id.right);
        updateListView();
    }

    private void initCtrlButton() {
        mPlayStop = (ImageButton) findViewById(R.id.btn_switch);
        if (mPlayStop != null) {
            mPlayStop.setOnClickListener(this);
            mPlayStop.setOnTouchListener(this);
        }

        mSoundButton = (ImageButton) findViewById(R.id.btn_speaker);
        if (mSoundButton != null) {
            mSoundButton.setOnClickListener(this);
            mSoundButton.setOnTouchListener(this);
        }
    }

    private void updateDisplayPanel(int currentFreq) {
        Log.i(TAG, "enter updateDisplayPanel()");

        // set the progress of the seek bar according to the current frequency
        setSeekBarProgress(currentFreq);

        // update to display the current frequency in ImageSwitcher
        updateFrequencyDisplay(currentFreq);
        Log.i(TAG, "leave updateDisplayPanel()");
    }

    private void updateListView() {
        Log.i(TAG, "enter updateListView()");

        // get the new data from DB
        mFMResults = new ArrayList<FMInfo>();
        ContentHelper.getChannelList(this, mFMResults);

        resetChannels();
        Log.i(TAG, "leave updateListView()");
    }

    private ImageView mPrePlayNow;
    private ViewGroup oldmChannelLayout;

    private void resetChannels() {
        mChannels.removeAllViews();
        for (int i = 0; i < mFMResults.size(); i++) {
            final FMInfo info = mFMResults.get(i);

            LayoutInflater inflater = LayoutInflater.from(this);
            final ViewGroup mChannelLayout = (ViewGroup) inflater.inflate(
                    R.layout.channel, null);

            mChannelLayout.setId(info.mId);

            final ImageView mPlay_now = (ImageView) mChannelLayout
                    .findViewById(R.id.play_now);

            TextView mChannel = (TextView) mChannelLayout
                    .findViewById(R.id.channel_num);
            final String mFreq = info.mFreq;
            if (!TextUtils.isEmpty(mFreq)) {
                DecimalFormat format = (DecimalFormat) NumberFormat
                        .getInstance(Locale.ENGLISH);
                format.applyPattern("0.0");
                Number number;
                try {
                    number = format.parse(mFreq);
                } catch (Exception e) {
                    Log.e(TAG, "frequecy parse error");
                    finish();
                    return;
                }
                mChannel.setText(String.valueOf(number.floatValue()));
                int counter = (int) (number.floatValue() * 1000);
                if (counter == mCurrentFreq) {
                    if (mPrePlayNow != null) {
                        mPrePlayNow.setVisibility(ImageView.INVISIBLE);
                        if (oldmChannelLayout != null) {
                            channel_num = mChannel;
                            channel_str = (TextView) oldmChannelLayout
                                    .findViewById(R.id.channel_str);
                            channel_num.setTextColor(Color
                                    .rgb(0x00, 0xbd, 0xfd));
                            channel_str.setTextColor(Color
                                    .rgb(0x00, 0xbd, 0xfd));
                        }
                    }
                    mPlay_now.setVisibility(ImageView.VISIBLE);
                    mPrePlayNow = mPlay_now;
                    oldmChannelLayout = mChannelLayout;
                    if (mChannelLayout != null) {
                        channel_num = mChannel;
                        channel_str = (TextView) mChannelLayout
                                .findViewById(R.id.channel_str);
                        channel_num.setTextColor(Color.rgb(0x2e, 0x2e, 0x2e));
                        channel_str.setTextColor(Color.rgb(0x2e, 0x2e, 0x2e));
                    }
                }
            }
            mChannelLayout.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    if (!TextUtils.isEmpty(mFreq)) {
                        mCurrentFreq = (int) (Float.parseFloat(mFreq) * 1000);
                        setFMRadioFreq();
                        updateDisplayPanel(mCurrentFreq);
                        if (mPrePlayNow != null) {
                            mPrePlayNow.setVisibility(ImageView.INVISIBLE);
                            if (oldmChannelLayout != null) {
                                channel_num = (TextView) oldmChannelLayout
                                        .findViewById(R.id.channel_num);
                                channel_str = (TextView) oldmChannelLayout
                                        .findViewById(R.id.channel_str);
                                channel_num.setTextColor(Color.rgb(0x2e, 0x2e,
                                        0x2e));
                                channel_str.setTextColor(Color.rgb(0x2e, 0x2e,
                                        0x2e));
                            }
                        }
                        mPlay_now.setVisibility(ImageView.VISIBLE);
                        mPrePlayNow = mPlay_now;
                        oldmChannelLayout = mChannelLayout;
                        if (mChannelLayout != null) {
                            channel_num = (TextView) mChannelLayout
                                    .findViewById(R.id.channel_num);
                            channel_str = (TextView) mChannelLayout
                                    .findViewById(R.id.channel_str);
                            channel_num.setTextColor(Color
                                    .rgb(0x00, 0xbd, 0xfd));
                            channel_str.setTextColor(Color
                                    .rgb(0x00, 0xbd, 0xfd));
                        }
                    }
                }
            });
            mChannelLayout.setOnLongClickListener(new OnLongClickListener() {

                public boolean onLongClick(View v) {
                    mOnLongClickChannel = v.getId();
                    return false;
                }
            });
            registerForContextMenu(mChannelLayout);
            mChannels.addView(mChannelLayout, i);
        }
    }

    public View makeView() {
        ImageView i = new ImageView(this);
        i.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        return i;
    }

    private void enableUI(boolean enabled, boolean enableSwitchBtn) {
        mAddButton.setEnabled(enabled);
        mReduceButton.setEnabled(enabled);
    }

    private void setSeekBarProgress(int currentFreq) {
        if (mIsSwitchOff) {
            mFreqIndicator.setVisibility(View.INVISIBLE);
            return;
        }
        mFreqIndicator.setVisibility(View.VISIBLE);
        mFreqIndicator.setProgress(currentFreq - LOW_FREQUENCY);
    }

    public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromTouch) {
        if (fromTouch) {
            if (seekBar == mFreqIndicator) {
                mCurrentFreq = progress + LOW_FREQUENCY;
                setFMRadioFreq();
                updateDisplayPanel(mCurrentFreq);
            }
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void updateFrequencyDisplay(int currentFreq) {
        if (mIsSwitchOff) {
            return;
        }
        if (currentFreq < LOW_FREQUENCY) {
            currentFreq = LOW_FREQUENCY;
            mCurrentFreq = LOW_FREQUENCY;
            setFMRadioFreq();
            setSeekBarProgress(currentFreq);
        }
        if (currentFreq > HIGH_FREQUENCY) {
            currentFreq = HIGH_FREQUENCY;
            mCurrentFreq = HIGH_FREQUENCY;
            setFMRadioFreq();
            setSeekBarProgress(currentFreq);
        }
        DecimalFormat df = (DecimalFormat) NumberFormat
                .getInstance(Locale.ENGLISH);
        df.applyPattern("0.0");
        float mFreqNum = (float) currentFreq / 1000.0f;
        fm_channel.setText(String.valueOf(df.format(mFreqNum)));
        fm_channel.setTypeface(Typeface.SERIF, Typeface.BOLD);

        Log.i(TAG, "enter updateFrequencyDisplay(): currentFreq " + mFreqNum);
        ContentHelper.saveConfig(this, mLastPosition, mCurrentFreq,
                mIsScannedBefore, mRssiThreshold);
    }

    public class FMInfo {
        private int mId;
        private String mFreq;

        public FMInfo() {
        }

        public FMInfo(int id, String freq, String name) {
            mId = id;
            mFreq = freq;
        }
    }

    FMInfo createFmInfo(int id, String freq, String name) {
        return new FMInfo(id, freq, name);
    }

    private boolean hasFMChannel(int channel) {
        if (mFMResults == null)
            return false;

        for (int i = 0; i < mFMResults.size(); i++) {
            FMInfo info = mFMResults.get(i);
            if (info.mId == channel) {
                return !TextUtils.isEmpty(info.mFreq);
            }
        }
        return false;
    }

    // begin_cts_change, add for UTP SMS02655961
    // / check if there's any predefined channel
    private boolean hasFMChannel() {
        boolean hasChannel = false;
        if (mFMResults != null) {
            for (int i = 0; i < mFMResults.size(); i++) {
                FMInfo info = mFMResults.get(i);
                if (info != null && !TextUtils.isEmpty(info.mFreq)) {
                    hasChannel = true;
                    break;
                }
            }
        }
        Log.d(TAG, "hasChannel is " + hasChannel);
        return hasChannel;
    }

    private void showToast(String value, int timeLength) {
        Toast ts = null;
        if (timeLength == Toast.LENGTH_SHORT) {
            ts = Toast.makeText(FMRadio.this, value, Toast.LENGTH_SHORT);
        } else {
            ts = Toast.makeText(FMRadio.this, value, Toast.LENGTH_LONG);
        }
        ts.show();
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                // TODO: reconsider whether to quit or go background
                moveTaskToBack(true);
                return true;

            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    public void onClick(View arg0) {
        Log.i(TAG, "onClick() called");

        switch (arg0.getId()) {
            case R.id.left:
                preChannel();
                return;
            case R.id.right:
                nextChannel();
                return;
            case R.id.btn_switch:
                if (!Util.supportInternalAntenna(FMRadio.this)
                        && !Util.isWiredHeadsetOn()) {
                    showToast(getString(R.string.fmradio_no_headset_in_listen),
                            Toast.LENGTH_SHORT);
                    return;
                }
                if (mRecorder.isRecording()) {
                    showToast(getString(R.string.fmradio_record_btn_switch),
                            Toast.LENGTH_SHORT);
                } else if (getFMServiceStatus() == FMRadioService.FM_SERVICE_TX_INIT
                        || getFMServiceStatus() == FMRadioService.FM_SERVICE_TX_ON) {
                    showToast(getString(R.string.fmradio_close_tx),
                            Toast.LENGTH_LONG);
                } else {
                    mHandler.sendEmptyMessage(FM_TOGGLE_STATE);
                }
                return;
            case R.id.btn_speaker:
                if (getFMServiceStatus() != FMRadioService.FM_SERVICE_ON) {
                    showToast(getString(R.string.fmradio_not_launch),
                            Toast.LENGTH_SHORT);
                    return;
                }

                try {
                    mFMService.setMute(!mFMService.isMuteFM() ? 0 : 1);
                    setMuteUi(mFMService.isMuteFM());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;

            case R.id.btn_fmdecrease:
                Log.i(TAG, "auto seek_down freq button clicked");
                if (LOW_FREQUENCY == mCurrentFreq) {
                    Log.i(TAG, "current frequency is the lowest 87.5 mhz");
                    mCurrentFreq = HIGH_FREQUENCY;
                    setFMRadioFreq();
                }
                mIsSeekAll = false;
                enableUI(false, true);

                seekFMRadioStation(FMRadioInterface.FMRADIO_SEEK_BACKWARD);
                return;
            case R.id.btn_fmincrease:
                Log.i(TAG, "auto seek_up freq button clicked");
                mIsSeekAll = false;
                enableUI(false, true);

                seekFMRadioStation(FMRadioInterface.FMRADIO_SEEK_FORWARD);
                return;
            default:
                return;
        }
    }

    private void changeFreqByStep(boolean flag) {
        if (flag) {
            mCurrentFreq += FREQUENCY_ADJUST_STEP;
            if (mCurrentFreq > HIGH_FREQUENCY) {
                mCurrentFreq = LOW_FREQUENCY;
            }
            updateDisplayPanel(mCurrentFreq);
            mHandler.sendEmptyMessageDelayed(FM_FREQUENCY_ADD,
                    LONG_PRESS_TUNE_TIMEOUT);
        } else {
            mCurrentFreq -= FREQUENCY_ADJUST_STEP;
            if (mCurrentFreq < LOW_FREQUENCY) {
                mCurrentFreq = HIGH_FREQUENCY;
            }
            updateDisplayPanel(mCurrentFreq);
            mHandler.sendEmptyMessageDelayed(FM_FREQUENCY_REDUCE,
                    LONG_PRESS_TUNE_TIMEOUT);
        }
    }

    private void setMuteUi(boolean mute) {
        if (mute) {
            mSoundButton.setImageResource(R.drawable.button_loudspeaker_off);
        } else {
            mSoundButton.setImageResource(R.drawable.button_loudspeaker_on);
        }
    }

    public boolean onLongClick(View v) {
        Log.i(TAG, "onLongClick() called");
        switch (v.getId()) {
            case R.id.btn_fmdecrease:
                Log.i(TAG, "auto seek_down freq button clicked");
                if (LOW_FREQUENCY == mCurrentFreq) {
                    Log.i(TAG, "current frequency is the lowest 87.5 mhz");
                    mCurrentFreq = HIGH_FREQUENCY;
                    setFMRadioFreq();
                }
                mIsSeekAll = false;
                enableUI(false, true);

                seekFMRadioStation(FMRadioInterface.FMRADIO_SEEK_BACKWARD);
                return true;
            case R.id.btn_fmincrease:
                Log.i(TAG, "auto seek_up freq button clicked");
                mIsSeekAll = false;
                enableUI(false, true);

                seekFMRadioStation(FMRadioInterface.FMRADIO_SEEK_FORWARD);

                return true;
        }
        return false;
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.i(TAG, "onTouch() :  event is MotionEvent.ACTION_UP");
            if ((mIsSeekbackwardLongPressed)
                    && (v.getId() == R.id.btn_fmdecrease)) {
                Log.i(TAG, "onTouch(); Release button frequency seekbackward");
                mIsSeekbackwardLongPressed = false;
            } else if ((mIsSeekforwardLongPressed)
                    && (v.getId() == R.id.btn_fmincrease)) {
                Log.i(TAG, "onTouch(); Release button frequency seekforward");
                mIsSeekforwardLongPressed = false;
            }
            currentChannel();
        }

        return false;
    }

    private void currentChannel() {
        int currentx = mScrollview.getScrollX();
        mScrollChannel = (int) round((float) currentx / CHANNLE_WIDTH, 0,
                BigDecimal.ROUND_HALF_DOWN);
        int realx = mScrollChannel * CHANNLE_WIDTH;
        mScrollview.scrollTo(realx, 0);
    }

    private void nextChannel() {
        int currentx = mScrollview.getScrollX();
        mScrollChannel = (int) round((float) currentx / CHANNLE_WIDTH, 0,
                BigDecimal.ROUND_HALF_DOWN);
        int visibleNum = getWindowManager().getDefaultDisplay().getWidth()
                / CHANNLE_WIDTH;
        if (mScrollChannel < (Util.MAX_CHANNEL_NUM - visibleNum)) {
            mScrollChannel++;
        }
        int realx = mScrollChannel * CHANNLE_WIDTH;
        mScrollview.scrollTo(realx, 0);
    }

    private void preChannel() {
        int currentx = mScrollview.getScrollX();
        mScrollChannel = (int) round((float) currentx / CHANNLE_WIDTH, 0,
                BigDecimal.ROUND_HALF_DOWN);
        if (mScrollChannel > 0) {
            mScrollChannel--;
        }
        int realx = mScrollChannel * CHANNLE_WIDTH;
        mScrollview.scrollTo(realx, 0);
    }

    public static double round(double value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        menu.setHeaderTitle(R.string.channel_string);
        if (hasFMChannel(mOnLongClickChannel)) {
            menu.add(0, REPLACE_MENU_ID, 2, R.string.replace_preset);
            menu.add(0, PLAY_MENU_ID, 1, R.string.play_preset);
            menu.add(0, CLEAR_MENU_ID, 3, R.string.clear_preset);
        } else {
            menu.add(0, ADD_MENU_ID, 0, R.string.add_preset);
        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        mListViewSelPos = mOnLongClickChannel;

        Log.i(TAG,
                "onContextItemSelected, info.id = "
                        + Long.toString(mListViewSelPos));

        switch (item.getItemId()) {
            case PLAY_MENU_ID: {
                ViewGroup view = (ViewGroup) findViewById((int) mListViewSelPos);
                view.performClick();
                break;
            }
            case ADD_MENU_ID: {
                showDialog(DIALOG_CONTEXT_ADD);
                break;
            }
            case REPLACE_MENU_ID: {
                showDialog(DIALOG_CONTEXT_REPLACE);
                break;
            }
            case CLEAR_MENU_ID: {
                showDialog(DIALOG_CONTEXT_CLEAR);
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.clear();
        if (!mIsSwitchOff) {
            if ((mIsFMRadioEnabled) && (!mRecorder.isRecording())) {
                menu.add(0, SCAN_SAVE_ID, 0, R.string.scan).setIcon(
                        android.R.drawable.ic_menu_search);
            }

            if (mIsFMRadioEnabled && Util.isWiredHeadsetOn()) {
                if (getFMRadioAudioRouting() == FMAudioInterface.ROUTE_FM_HEADSET) {
                    menu.add(0, BY_LOUDSPEAKER_ID, 1, R.string.by_loudspeaker)
                            .setIcon(R.drawable.ic_menu_loudspeaker);
                } else {
                    menu.add(0, BY_HEADSET_ID, 1, R.string.by_headset).setIcon(
                            R.drawable.ic_menu_headset);
                }
            }

            menu.add(0, RECORDSLIST_ID, 2, R.string.records_list).setIcon(
                    R.drawable.ic_menu_recordslist);
            if (mIsFMRadioEnabled) {
                menu.add(
                        0,
                        RECODER_ID,
                        3,
                        !mRecorder.isRecording() ? R.string.start_record
                                : R.string.stop_record).setIcon(
                        !mRecorder.isRecording() ? R.drawable.ic_menu_record
                                : R.drawable.ic_menu_record_enable);
            }
            menu.add(0, RDS_CHARSET, 4, R.string.rds_charset).setIcon(
                    R.drawable.ic_menu_rds);

            // disable the clear item if channel list is empty
            menu.add(0, CLEAR_ID, 4, R.string.clear_presets)
                    .setIcon(android.R.drawable.ic_menu_delete)
                    .setEnabled(hasFMChannel() ? true : false);

            menu.add(0, EXIT_ID, 6, R.string.exit).setIcon(
                    android.R.drawable.ic_lock_power_off);
        }
        return true;
    }

    private void showRdsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FMRadio.this);
        builder.setTitle(R.string.rds_charset);
        builder.setSingleChoiceItems(items, mRdsLocaleId,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mRdsLocaleId = which;
                    }
                });
        builder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mInfoText.setText(RDSController.getLocalisedString(
                                mRdsBytes, mRdsLocaleId));
                        SharedPreferences.Editor mEditor = mPerferences.edit();
                        mEditor.putInt("rdsLocaleId", mRdsLocaleId);
                        mEditor.commit();
                        Log.i(TAG, "mRdsLocaleId commit: " + mRdsLocaleId);
                    }
                });
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case CLEAR_ID:
                Intent intent_clear = new Intent();
                intent_clear.putExtra("current_num", mLastPosition);
                intent_clear.setClass(FMRadio.this, FMClearChannel.class);
                startActivityForResult(intent_clear, CLEAR_CODE);
                break;
            case RDS_CHARSET:
                Log.i(TAG, "RDS_CHARSET");
                showRdsDialog();
                break;
            case EXIT_ID:
                Log.i(TAG, "User click Exit Menu to exit FM");
                powerOffFMRadio();
                mHandler.sendEmptyMessageDelayed(FM_DELAY_FINISH,
                        FM_DELAY_FINISH_TIMEOUT);
                return true;
            case SETCHANNEL_ID:
                Log.i(TAG, "xxd  User click SETCHANNEL_ID");
                mHandler.sendEmptyMessage(FM_SETCHANNEL_ID);
                break;
            case SCAN_SAVE_ID:
                if (ContentHelper.isDBEmpty(this)) {
                    mIsSeekAll = true;
                    mRssiThreshold = 7;
                    setFMRadioThreshold(mRssiThreshold);
                    mCurrentFreq = LOW_FREQUENCY;
                    setFMRadioFreq();
                    mWakeLock.acquire(LIGHT_ON_TIME);
                    seekFMRadioStation(FMRadioInterface.FMRADIO_SEEK_FORWARD);
                    showDialog(DIALOG_SCANNING_PROGRESS);// scan and save dialog
                } else {
                    showDialog(DIALOG_IF_SCAN_NEXT);
                }
                break;
            case RECORDSLIST_ID:
                if (!Util.isExternalStorageStateMounted()) {
                    showToast(getString(R.string.sdcard_not_mounted),
                            Toast.LENGTH_SHORT);
                    break;
                }
                if (mRecorder.isRecording()) {
                    showToast(getString(R.string.recording), Toast.LENGTH_SHORT);
                    break;
                }
                startActivity(new Intent(this, RecordListActivity.class));
                break;
            case RECODER_ID:
                if (!Util.isExternalStorageStateMounted()) {
                    showToast(getString(R.string.sdcard_not_mounted),
                            Toast.LENGTH_SHORT);
                    break;
                }
                mRecorder.toggleRecord();
                break;

            case BY_LOUDSPEAKER_ID:
                Log.i(TAG, "loudspeaker menu is pressed!");
                Intent iLoudspeaker = new Intent(
                        FMAudioInterface.FM_ROUTE_LOUDSPEAKER);
                sendBroadcast(iLoudspeaker);
                break;

            case BY_HEADSET_ID:
                Log.i(TAG, "headset menu is pressed!");
                Intent iHeadset = new Intent(FMAudioInterface.FM_ROUTE_HEADSET);
                sendBroadcast(iHeadset);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // You can use the requestCode to select between multiple child
        // activities you may have started. Here there is only one thing
        // we launch.
        switch (requestCode) {
            case SAVE_CODE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Log.i(TAG, "Save back to FMRadio UI");
                        updateListView();
                        int id = data.getIntExtra("newSaved_id", 0);
                        mLastPosition = id;
                    }
                }
                break;

            case EDIT_CODE:
                Log.i(TAG, "Edit back to FMRadio UI");
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Log.i(TAG, "resultCode==RESULT_OK");
                        updateListView();
                        int id = data.getIntExtra("current_num", mLastPosition);
                        mLastPosition = id;
                    }
                } else {
                    Log.i(TAG, "resultCode!=RESULT_OK");
                    mCurrentFreq = LOW_FREQUENCY;
                    updateDisplayPanel(mCurrentFreq);
                    setFMRadioFreq();
                }
                break;

            case CLEAR_CODE:
                if (resultCode == RESULT_OK) {
                    updateListView();
                    boolean isClearAll = data.getBooleanExtra("isClearAll", false);
                    if (isClearAll) {
                        Log.i(TAG, "FMRadio clear all is true!");
                        mLastPosition = 0;
                        FMClearChannel.mDeleteAll = true;
                    } else {
                        if (data.getBooleanExtra("isLastPositionCleared", false)) {
                            updateDisplayPanel(mCurrentFreq);
                        }
                    }
                }
                break;
        }
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_LAUNCHING_PROGRESS: {
                mLaunchingDialog = new ProgressDialog(this);
                if (mLaunchingDialog != null) {
                    mLaunchingDialog
                            .setMessage(getString(R.string.fmradio_waiting_for_power_on));
                    mLaunchingDialog.setIndeterminate(true);
                    mLaunchingDialog.setCancelable(false);
                }
                return mLaunchingDialog;
            }
            case DIALOG_SCANNING_PROGRESS: {
                mScanningDialog = new ProgressDialog(this);
                if (mScanningDialog != null) {
                    mScanningDialog
                            .setMessage(getString(R.string.fmradio_scan_begin_msg));
                    mScanningDialog.setIndeterminate(true);
                    mScanningDialog.setCancelable(true);
                    mScanningDialog.setButton(getText(R.string.cancel),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int whichButton) {
                                    mScanningDialog.cancel();
                                }
                            });
                    mScanningDialog
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                public void onCancel(DialogInterface dialog) {
                                    Log.i("FMRadio Progress_Dialog",
                                            "call onCancel");
                                    removeDialog(DIALOG_SCANNING_PROGRESS);
                                }
                            });
                    mScanningDialog
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                public void onDismiss(DialogInterface dialog) {
                                    Log.i("FMRadio Progress_Dialog",
                                            "call onDismiss");
                                    mIsSeekAll = false;
                                    mIsScanCanceled = true;
                                    updateListView();
                                    mLastPosition = 0;
                                    performVirtualClick(0);
                                }
                            });
                }
                return mScanningDialog;
            }
            case DIALOG_IF_SCAN_FIRST: {
                Dialog tmp = new AlertDialog.Builder(this)
                        .setTitle(R.string.scan)
                        .setMessage(R.string.fmradio_scan_confirm_msg)
                        .setPositiveButton(R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                            int whichButton) {
                                        ContentHelper.clearDB(FMRadio.this);
                                        mRssiThreshold = 10;
                                        setFMRadioThreshold(mRssiThreshold);
                                        mCurrentFreq = LOW_FREQUENCY;
                                        setFMRadioFreq();
                                        mIsSeekAll = true;
                                        mWakeLock.acquire(LIGHT_ON_TIME);
                                        seekFMRadioStation(FMRadioInterface.FMRADIO_SEEK_FORWARD);
                                        showDialog(DIALOG_SCANNING_PROGRESS);// scan
                                                                             // and
                                                                             // save
                                                                             // dialog
                                    }
                                })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                            int whichButton) {
                                    }
                                }).create();
                return tmp;
            }
            case DIALOG_IF_SCAN_NEXT: {
                Dialog tmp = new AlertDialog.Builder(this)
                        .setTitle(R.string.scan)
                        .setMessage(R.string.fmradio_clear_confirm_msg)
                        .setPositiveButton(R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                            int whichButton) {
                                        ContentHelper.clearDB(FMRadio.this);
                                        // original code
                                        setFMRadioThreshold(mRssiThreshold);
                                        mCurrentFreq = LOW_FREQUENCY;
                                        setFMRadioFreq();
                                        mIsSeekAll = true;
                                        mWakeLock.acquire(LIGHT_ON_TIME);
                                        seekFMRadioStation(FMRadioInterface.FMRADIO_SEEK_FORWARD);
                                        showDialog(DIALOG_SCANNING_PROGRESS);
                                    }
                                })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                            int whichButton) {
                                    }
                                }).create();
                return tmp;
            }
            case DIALOG_CONTEXT_ADD: {
                Dialog tmp = new AlertDialog.Builder(this)
                        .setTitle(R.string.add_preset)
                        .setMessage(R.string.confirm_add_preset)
                        .setPositiveButton(R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                            int whichButton) {
                                        ContentHelper
                                                .saveChannelInfo(FMRadio.this,
                                                        (int) mListViewSelPos,
                                                        mCurrentFreq);
                                        updateListView();
                                        if (mLastPosition == (int) mListViewSelPos) {
                                            updateDisplayPanel(mCurrentFreq);
                                        }
                                        FMClearChannel.mDeleteAll = false;
                                    }
                                })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                            int whichButton) {
                                    }
                                }).create();
                return tmp;
            }
            case DIALOG_CONTEXT_REPLACE: {
                Dialog tmp = new AlertDialog.Builder(this)
                        .setTitle(R.string.replace_preset)
                        .setMessage(R.string.confirm_replace_preset)
                        .setPositiveButton(R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                            int whichButton) {
                                        ContentHelper
                                                .saveChannelInfo(FMRadio.this,
                                                        (int) mListViewSelPos,
                                                        mCurrentFreq);
                                        updateListView();
                                        if (mLastPosition == (int) mListViewSelPos) {
                                            updateDisplayPanel(mCurrentFreq);
                                        }
                                        FMClearChannel.mDeleteAll = false;
                                    }
                                })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                            int whichButton) {
                                    }
                                }).create();
                return tmp;
            }
            case DIALOG_CONTEXT_CLEAR: {
                Dialog tmp = new AlertDialog.Builder(this)
                        .setTitle(R.string.clear_preset)
                        .setMessage(R.string.confirm_clear_preset)
                        .setPositiveButton(R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                            int whichButton) {
                                        ContentHelper.saveChannelInfo(FMRadio.this,
                                                (int) mListViewSelPos, -1);
                                        updateListView();
                                        if (mLastPosition == (int) mListViewSelPos) {
                                            updateDisplayPanel(mCurrentFreq);
                                        }
                                    }
                                })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                            int whichButton) {
                                    }
                                }).create();
                return tmp;
            }
        }
        return null;
    }

    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DIALOG_SCANNING_PROGRESS: {
                mScanningDialog
                        .setMessage(getString(R.string.fmradio_scan_begin_msg));
                mIsScanCanceled = false;
                mSeekSaveCount = 0;
                mFirstCounter = 0;
                break;
            }
        }
    }

    private void performVirtualClick(long rowid) {
        Log.i(TAG, "performVirtualClick, current freq: " + mCurrentFreq
                + ",last position: " + mLastPosition);
        if(mFMResults.size() <= 0) {
            return;
        }

        FMInfo info = mFMResults.get(mLastPosition);
        if (info != null && !TextUtils.isEmpty(info.mFreq))
            mCurrentFreq = (int) (Float.parseFloat(info.mFreq) * 1000);

        setFMRadioFreq();
        updateDisplayPanel(mCurrentFreq);
    }

    private boolean mIsBound = false; // used for identify the service is binded
    private IFMRadioService mFMService = null;

    private boolean bindToService() {
        Log.i(TAG, "Start to bind to FMRadio service");
        return bindService(new Intent("com.pekall.fmradio.FMRADIO_SERVICE"),
                mServConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindFMRadioService() {
        if (mIsBound) {
            unbindService(mServConnection);
            mIsBound = false;
        }
    }

    private ServiceConnection mServConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                android.os.IBinder service) {
            Log.w(TAG, "onServiceConnected IN...");
            mIsBound = true;

            mFMService = IFMRadioService.Stub.asInterface(service);
            if (mFMService == null) {
                Log.e(TAG, "onServiceConnected error, mFMService null");
                return;
            }

            updateTrackInfo();
            updateDisplayPanel(mCurrentFreq);
            updateSwithcButton();

            try {
                mFMService.registerCallback(mCallback);
            } catch (RemoteException e) {
                Log.e(TAG, "", e);
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.i(TAG, "onServiceDisconnected IN...");
            if (mFMService == null) {
                Log.e(TAG, "onServiceDisconnected error, mFMService null");
                return;
            }

            try {
                mFMService.unregisterCallback(mCallback);
            } catch (RemoteException e) {
                Log.e(TAG, "", e);
            }

            mFMService = null;
            finish();
        }
    };

    private static final int START_FMRADIO = 1;
    private static final int STOP_FMRADIO = 2;
    private static final int QUIT = 3;
    private static final int FM_OPEN_FAILED = 4;
    private static final int FM_OPEN_SUCCEED = 5;
    private static final int FM_SETFREQ_SUCCEED = 6;
    private static final int FM_SEEK_SUCCEED = 7;
    private static final int FM_SEEK_FAILED = 8;
    private static final int SEEK_NEXT = 12;
    private static final int FM_FREQUENCY_ADD = 13;
    private static final int FM_FREQUENCY_REDUCE = 14;
    private static final int FM_SEEK_BACKWARD_LONG_PRESS = 15;
    private static final int FM_SEEK_FORWARD_LONG_PRESS = 16;
    static final int FM_START_RECORD = 20;
    static final int FM_RECORD_BEGIN = 21;
    static final int FM_RECORD_END = 26;
    private static final int FM_DELAY_FINISH = 23;
    private static final int FM_SETCHANNEL_ID = 24;
    private static final int FM_INIT_SETCHANNEL_ID = 25;
    private static final int FM_TOGGLE_STATE = 27;
    private static final int FM_CLOSED = 28;

    private static final int FM_DELAY_FINISH_TIMEOUT = 400; // 400ms

    final Handler getHandler() {
        return mHandler;
    }

    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START_FMRADIO:
                    int ret = getFMServiceStatus();
                    Log.i(TAG, "getFMServiceStatus() = " + ret);
                    if (ret != FMRadioService.FM_SERVICE_ON) {
                        Log.i(TAG, "service state is not READY."
                                + "Will call powerOnFMRadio() now");
                        showDialog(DIALOG_LAUNCHING_PROGRESS);
                        powerOnFMRadio();
                    } else {
                        mIsFMRadioEnabled = true;
                        enableUI(true, true);
                    }
                    break;
                case STOP_FMRADIO:
                    powerOffFMRadio();
                    break;
                case FM_OPEN_FAILED:
                    Log.i(TAG, "FM open failed!!! Will finish()!!!");

                    mIsFMRadioEnabled = false;
                    mLaunchingDialog.dismiss();
                    showToast(getString(R.string.service_start_error_msg),
                            Toast.LENGTH_LONG);
                    finish();
                    break;

                case FM_OPEN_SUCCEED:
                    Log.i(TAG, "FM open succeed callback");

                    mIsFMRadioEnabled = true;
                    if (mLaunchingDialog != null) {
                        mLaunchingDialog.dismiss();
                    }
                    enableUI(true, true);

                    if (!mIsScannedBefore) {
                        Log.i(TAG, "It is the FMRadio first start!");
                        showDialog(DIALOG_IF_SCAN_FIRST);
                        mIsScannedBefore = true;
                    }
                    mHandler.sendEmptyMessage(FM_INIT_SETCHANNEL_ID);
                    break;
                case FM_CLOSED:
                    mIsFMRadioEnabled = false;
                    mAddButton.setEnabled(false);
                    mReduceButton.setEnabled(false);
                    break;

                case FM_SETFREQ_SUCCEED:
                    enableUI(true, true);
                    break;

                case FM_SETCHANNEL_ID:
                    // FIXME: disabled for now
                    // Log.i(TAG, "xxd  set fm radio mode");
                    // setFMRadioChannel();
                    break;
                case FM_INIT_SETCHANNEL_ID:
                    // FIXME: disabled for now
                    // Log.i(TAG, "xxd  set fm radio mode");
                    // setinitFMRadioChannel();
                    break;

                case FM_SEEK_FAILED:
                    if (mIsSeekAll) {
                        mHandler.sendEmptyMessage(SEEK_NEXT);
                    } else {
                        enableUI(true, true);
                    }
                    break;

                case FM_SEEK_SUCCEED:
                    Log.i(TAG, "seek forward/backward callback,"
                            + " the new freqency = " + mCurrentFreq);

                    if (mIsSeekbackwardLongPressed) {
                        Log.d(TAG, "mIsSeekbackwardLongPressed is true,"
                                + " will continue seekbackward after 3 s");
                        updateDisplayPanel(mCurrentFreq);
                        mHandler.sendEmptyMessageDelayed(
                                FM_SEEK_BACKWARD_LONG_PRESS,
                                LONG_PRESS_SEEK_TIMEOUT);
                    } else if (mIsSeekforwardLongPressed) {
                        Log.d(TAG, "mIsSeekforwardLongPressed is ture, "
                                + "will continue seekforward after 3s");
                        updateDisplayPanel(mCurrentFreq);
                        mHandler.sendEmptyMessageDelayed(
                                FM_SEEK_FORWARD_LONG_PRESS, LONG_PRESS_SEEK_TIMEOUT);
                    } else {
                        // seek All
                        if (mIsSeekAll) {
                            Log.i(TAG, "in seek All procedure!");
                            if (mCurrentFreq <= HIGH_FREQUENCY
                                    && mCurrentFreq >= LOW_FREQUENCY
                                    && mCurrentFreq > mFirstCounter) {
                                // Prevent frequency wrapping around
                                mFirstCounter = mCurrentFreq;

                                ContentHelper.saveChannelInfo(FMRadio.this,
                                        mSeekSaveCount, mCurrentFreq);
                                mSeekSaveCount++;
                                mScanningDialog
                                        .setMessage(getString(R.string.found)
                                                + " "
                                                + mSeekSaveCount
                                                + " "
                                                + getString(R.string.channels)
                                                + " "
                                                + Float.toString((float) mCurrentFreq / 1000)
                                                + " " + "MHz");
                            } else {
                                mIsScanCanceled = true;
                                removeDialog(DIALOG_SCANNING_PROGRESS);
                            }

                            // if not canceled, preview this station for 1.5s
                            if (!mIsScanCanceled) {
                                Message seekNextMsg = mHandler
                                        .obtainMessage(SEEK_NEXT);
                                long playoutTime = SystemClock.uptimeMillis() + 1500;
                                mHandler.sendMessageAtTime(seekNextMsg, playoutTime);
                                return;
                            } else {
                                showToast(getString(R.string.saved) + " "
                                        + mSeekSaveCount + " "
                                        + getString(R.string.scanned_channels),
                                        Toast.LENGTH_SHORT);
                                updateListView();
                            }
                        }

                        updateDisplayPanel(mCurrentFreq);
                        resetChannels();
                        enableUI(true, true);
                    }

                    break;

                case SEEK_NEXT:
                    Log.i(TAG, "SEEK_NEXT received");

                    if (mSeekSaveCount < Util.MAX_CHANNEL_NUM && !mIsScanCanceled) {
                        Log.i(TAG, "scan not canceled, seek next station");
                        seekFMRadioStation(FMRadioInterface.FMRADIO_SEEK_FORWARD);
                        return;
                    } else {
                        Log.d(TAG, "MAX_CHANNEL_NUM stations scanned "
                                + "out or Cancel button pressed");
                        removeDialog(DIALOG_SCANNING_PROGRESS);
                        showToast(getString(R.string.saved) + " " + mSeekSaveCount
                                + " " + getString(R.string.scanned_channels),
                                Toast.LENGTH_SHORT);
                        FMClearChannel.mDeleteAll = false;
                        updateListView();
                    }
                    break;

                case FM_FREQUENCY_ADD:
                    Log.i(TAG, "Receive FM_FREQUENCY_ADD msg, "
                            + "mIsLongPressed = " + mIsLongPressed);
                    if (mIsLongPressed) {
                        changeFreqByStep(true);
                    }
                    break;

                case FM_FREQUENCY_REDUCE:
                    Log.i(TAG, "Receive FM_FREQUENCY_REDUCE msg, "
                            + "mIsLongPressed = " + mIsLongPressed);
                    if (mIsLongPressed) {
                        changeFreqByStep(false);
                    }
                    break;

                case FM_SEEK_BACKWARD_LONG_PRESS:
                    Log.i(TAG, "Receive FM_SEEK_BACKWARD_LONG_PRESS msg, "
                            + "mIsSeekbackwardLongPressed ="
                            + mIsSeekbackwardLongPressed);
                    if (mIsSeekbackwardLongPressed)
                        seekFMRadioStation(FMRadioInterface.FMRADIO_SEEK_BACKWARD);
                    break;

                case FM_SEEK_FORWARD_LONG_PRESS:
                    Log.i(TAG, "Receive FM_SEEK_FORWARD_LONG_PRESS msg, "
                            + "mIsSeekforwardLongPressed ="
                            + mIsSeekforwardLongPressed);
                    if (mIsSeekforwardLongPressed)
                        seekFMRadioStation(FMRadioInterface.FMRADIO_SEEK_FORWARD);
                    break;

                case FM_START_RECORD:
                    mRecorder.updateRecordTime();
                    break;

                case QUIT:
                    new AlertDialog.Builder(FMRadio.this)
                            .setMessage(R.string.service_start_error_msg)
                            .setPositiveButton(R.string.service_start_error_button,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                            finish();
                                        }
                                    }).setCancelable(false).show();
                    break;

                case FM_RECORD_BEGIN: {
                    mRecorder.beginRecord();
                    break;
                }
                case FM_RECORD_END: {
                    mRecorder.endRecord();
                    break;
                }
                case FM_DELAY_FINISH: {
                    // It takes some time, about 300ms, to close the fm
                    // hardware.
                    // In FMRadio::onDestroy(), UnbindService is invoked which
                    // cause FMRadioService to quit immediately. If the
                    // FMRadioServce
                    // exits before the fm hardware is closed, there is
                    // potential
                    // issue to cause the handset freezed. So we just delay some
                    // time to quit the activity.
                    finish();
                    break;
                }
                case FM_TOGGLE_STATE: {
                    toggleState();
                }
                default:
                    break;
            }
            mPlayStop
                    .setBackgroundResource(mIsFMRadioEnabled ? R.drawable.btn_stop
                            : R.drawable.btn_play);
        }
    };

    private void registerBroadcastListener() {
        if (mIntentReceiver == null) {
            mIntentReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    Log.i(TAG, "Launcher broadcast Received " + action);
                    if (action.equals(INTENT_STOP_FM)) {
                        powerOffFMRadio();
                        mHandler.sendEmptyMessageDelayed(FM_DELAY_FINISH,
                                FM_DELAY_FINISH_TIMEOUT);
                    } else if (action.equals(Intent.ACTION_HEADSET_PLUG)) {
                        // Hide the option menu since the headset/speaker item
                        // need to be updated.
                        FMRadio.this.closeOptionsMenu();
                    } else if (action.equals(FMRadioService.FM_RDS_STRING)) {
                        mRdsBytes = intent.getByteArrayExtra("rds");
                        mRdsType = intent.getIntExtra("type",
                                RDSController.RDS_TYPE_UNKNOWN);
                        mInfoText.setText(RDSController.getLocalisedString(
                                mRdsBytes, mRdsLocaleId));
                    }
                }
            };
            IntentFilter intent = new IntentFilter();
            intent.addAction(FMRadioService.FM_RDS_STRING);
            intent.addAction(Intent.ACTION_HEADSET_PLUG);
            intent.addAction(INTENT_STOP_FM);
            intent.addAction(FMRadioService.FM_CLOSED);
            registerReceiver(mIntentReceiver, intent);
        }
    }

    private static final String INTENT_STOP_FM = "android.intent.action.STOP_FM";
    private static final String INTENT_START_FM = "android.intent.action.FM_START";

    private int getFMRadioAudioRouting() {
        int ret = FMAudioInterface.ROUTE_FM_HEADSET;
        if (mFMService != null) {
            try {
                ret = mFMService.getFMAudioRouting();
            } catch (RemoteException e) {
                Log.e(TAG, "get fmradio audio routing failed");
            }
        }
        return ret;
    }

    private int getFMServiceStatus() {
        int ret = FMRadioService.FM_SERVICE_NONE;
        if (mFMService != null) {
            try {
                ret = mFMService.getFMServiceStatus();
            } catch (RemoteException e) {
                Log.e(TAG, "get fmradio service status failed");
            }
        }
        return ret;
    }

    private void powerOnFMRadio() {
        Log.i(TAG, "powerOnFMRadio");
        if (mFMService != null) {
            try {
                Log.i(TAG, "mService.open() called!");
                mFMService.openFMRadio();
            } catch (RemoteException ex) {
                Log.i(TAG, "mService.open() RemoteException!");
            }
        }
    }

    private void powerOffFMRadio() {
        Log.i(TAG, "powerOffFMRadio");
        if (mFMService != null) {
            try {
                Log.i(TAG, "mService.close() called!");
                mFMService.closeFMRadio();
                mIsFMRadioEnabled = false;
                mAddButton.setEnabled(false);
                mReduceButton.setEnabled(false);
            } catch (RemoteException ex) {
                Log.i(TAG, "mService.close() RemoteException!");
            }
        }
    }

    private void enableRds() {
        Log.i(TAG, "enableRds");
        if (mFMService != null) {
            try {
                if (mFMService.getFMServiceStatus() == FMRadioService.FM_SERVICE_ON) {
                    mFMService.enableRds();
                    Log.i(TAG, "enableRds done!");
                }
            } catch (RemoteException e) {
                Log.e(TAG, "enableRds()", e);
            }
        }
    }

    private void disableRds() {
        Log.i(TAG, "disableRds");
        if (mFMService != null) {
            try {
                if (mFMService.getFMServiceStatus() == FMRadioService.FM_SERVICE_ON) {
                    mFMService.disableRds();
                    Log.i(TAG, "disableRds done!");
                }
            } catch (RemoteException e) {
                Log.e(TAG, "disableRds()", e);
            }
        }
    }

    private void setFMRadioFreq() {
        Log.i(TAG, "setFMRadioFreq");
        if (mFMService != null) {
            try {
                mFMService.setFMFreq(mCurrentFreq);
            } catch (RemoteException ex) {
                Log.i(TAG, "In setFMRadioFreq(): RemoteException.!");
            }
        }
    }

    private void seekFMRadioStation(int direction) {
        Log.i("FMRadio", "seekFMRadioStation");
        if (mFMService != null) {
            try {
                mFMService.seekFM(direction);
            } catch (RemoteException ex) {
                Log.i(TAG, "In seekFMRadioStation(): RemoteException.!");
            }
        }
    }

    private void setFMRadioThreshold(int value) {
        Log.i(TAG, "setFMRadioThreshold");
        if (mFMService != null) {
            try {
                mFMService.setFMRssiThreshold(value);
            } catch (RemoteException ex) {
                Log.i(TAG, "In setFMRadioThreshold(): RemoteException.!");
            }
        }
    }

    private void toggleState() {
        if (!mIsFMRadioEnabled) {
            if (Util.isAirplaneModeOn(FMRadio.this)) {
                showToast(
                        getString(R.string.fmradio_airplane_mode_enable_at_begin),
                        0);
                return;
            }
            if (Util.isInCallState(FMRadio.this)) {
                showToast(
                        getString(R.string.fmradio_call_state_enable_at_begin),
                        0);
                return;
            }
            int delay = 0;
            int state = getFMServiceStatus();
            if (state == FMRadioService.FM_SERVICE_TX_INIT
                    || state == FMRadioService.FM_SERVICE_TX_ON) {
                showToast(getString(R.string.fmradio_close_tx), 0);
                return;
            }
            mHandler.sendEmptyMessageDelayed(START_FMRADIO, delay);
        } else {
            if (mRecorder.isRecording()) {
                mRecorder.toggleRecord();
            }
            powerOffFMRadio();
            setMuteUi(false);
        }
    }
}
