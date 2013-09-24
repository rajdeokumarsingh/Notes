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

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

/**
 * FMRadioService provides main interfaces for UI. FM states are maintained in
 * this class, so that UI can be destroyed.
 */
public class FMRadioService extends Service {
    private static final String TAG = "FMRadioService";

    /**
     * Intent action for RDS string
     */
    static final String FM_RDS_STRING = "com.pekall.fmradio.rds.string";

    /**
     * Intent action to close FM application
     */
    static final String FM_CLOSED = "com.pekall.fmradio.closed";

    /**
     * Intent actions for FM widget control
     */
    static final String FM_APPWIDGET_UPDATE = "com.pekall.fmradio.appwidgetupdate";
    static final String FM_TOGGLE_ONOFF = "com.pekall.fmradio.toggleonoff";
    static final String FM_PREV_CHANNEL = "com.pekall.fmradio.prevchannel";
    static final String FM_NEXT_CHANNEL = "com.pekall.fmradio.nextchannel";

    /**
     * Notification message for FM widget
     */
    static final String FM_OPEN_FAILED = "com.pekall.fmradio.openfailed";

    /**
     * Notification message for FM widget
     */
    static final String FM_LAUNCHING = "com.pekall.fmradio.launching";

    /**
     * Commands for media keys
     */
    static final String SERVICE_CMD = "com.pekall.fmradio.servicecommand";
    static final String CMD_NAME = "command";
    static final String CMD_SEEK = "command.seek";

    /**
     * Service states
     */
    public static final int FM_SERVICE_NONE = -1;
    public static final int FM_SERVICE_INIT = 0;
    public static final int FM_SERVICE_OPENED = 1;
    public static final int FM_SERVICE_ON = 2;
    public static final int FM_SERVICE_EXITING = 3;
    public static final int FM_SERVICE_TX_INIT = 4;
    public static final int FM_SERVICE_TX_ON = 5;
    private int mFMServiceState = FM_SERVICE_NONE;

    private int mServiceStartId = -1;
    private int mCurrentFreq = -1;

    private FMRadioInterface mFMRadioInterface = null;
    private FMAudioInterface mAudioInterface = null;

    private FMRadioAppWidgetProvider mAppWidgetProvider =
            FMRadioAppWidgetProvider.getInstance();

    private final static int FM_CB_OPEN_RX = 0;
    private final static int FM_CB_OPEN_TX = 1;
    private final static int FM_CB_SEEK_CHN = 2;
    private final static int FM_CB_SET_FREQ = 3;

    // For FM record. If SD card is unmounted when
    // recording FM, audio system process will be killed
    // TODO: I should create a RecordingController
    private BroadcastReceiver mSdCardListener = null;

    private void registerSdCardListener() {
        if (mSdCardListener != null) {
            return;
        }
        mSdCardListener = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action == null) {
                    return;
                }
                Log.i(TAG, "Received intent: " + action);
                if (action.equals(Intent.ACTION_MEDIA_UNMOUNTED)) {
                    Intent Stopintent = new Intent(
                            "android.intent.action.STOP_FM");
                    sendBroadcast(Stopintent);
                }
            }
        };

        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        iFilter.addDataScheme("file");
        registerReceiver(mSdCardListener, iFilter);
    }

    private BroadcastReceiver mReceiver = null;

    private void registerBroadcastListener() {
        if (mReceiver != null) {
            return;
        }
        mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action == null) {
                    return;
                }
                Log.i(TAG, "Received intent: " + action);

                if (action.equals(Intent.ACTION_HEADSET_PLUG)) {
                    if (mFMServiceState != FM_SERVICE_ON) {
                        return;
                    }
                    final boolean headsetPlugin = (intent.getIntExtra("state",
                            0) == 1);
                    final boolean supportInAntenna = Util
                            .supportInternalAntenna(context);

                    Log.d(TAG, "HEADSET is pluged in? : " + headsetPlugin);
                    if (!supportInAntenna && !headsetPlugin) {
                        // The headset is plugged out, stop FMR.
                        Toast.makeText(FMRadioService.this,
                                R.string.fmradio_no_headset_in_listen,
                                Toast.LENGTH_LONG).show();

                        Intent Stopintent = new Intent(
                                "android.intent.action.STOP_FM");
                        sendBroadcast(Stopintent);
                        mFMServiceState = FM_SERVICE_OPENED;

                        if (mAppWidgetProvider != null) {
                            mAppWidgetProvider.notifyChange(
                                    FMRadioService.this, FM_CLOSED);
                        }

                        clearFMService();
                        stopSelf(mServiceStartId);
                    } else if (supportInAntenna) {
                        mFMRadioInterface.setAntenna(headsetPlugin ? 0 : 1);

                        if (headsetPlugin) {
                            mAudioInterface.setAudioOnSpeaker(false);
                        } else {
                            mAudioInterface.setAudioOnSpeaker(true);
                        }
                    }
                } else if (action.equals(FM_APPWIDGET_UPDATE)) {
                    // Someone asked us to refresh a set of specific
                    // widgets, probably
                    // because they were just added.
                    int[] appWidgetIds = intent
                            .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
                    mAppWidgetProvider.performUpdate(FMRadioService.this,
                            appWidgetIds);
                } else if (action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
                    final boolean isAirplaneModeOn = intent.getBooleanExtra(
                            "state", false);
                    if (isAirplaneModeOn) {
                        Log.i(TAG, "Aireplane mode is enabled.");
                        mHandler.sendEmptyMessage(FM_AIRPLANE_MODE_CHANGED);
                    }
                } else if (action.equals(FMAudioInterface.FM_ROUTE_HEADSET)) {
                    Log.i(TAG, "FM_ROUTE_HEADSET");
                    mAudioInterface.setAudioOnSpeaker(false);
                } else if (action.equals(FMAudioInterface.FM_ROUTE_LOUDSPEAKER)) {
                    Log.i(TAG, "FM_ROUTE_LOUDSPEAKER");
                    mAudioInterface.setAudioOnSpeaker(true);
                } else if (action.equals(SERVICE_CMD)) {
                    Log.i(TAG, "Got media key events");
                    String cmd = intent.getStringExtra(CMD_NAME);
                    if (CMD_SEEK.equals(cmd)) {
                        Message msg = Message
                                .obtain(mHandler,
                                        FM_SEEK_CHANNEL,
                                        Integer.valueOf(FMRadioInterface.FMRADIO_SEEK_FORWARD));
                        mHandler.sendMessage(msg);
                    }
                }
            }
        };

        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        iFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        iFilter.addAction(FMAudioInterface.FM_ROUTE_HEADSET);
        iFilter.addAction(FMAudioInterface.FM_ROUTE_LOUDSPEAKER);
        iFilter.addAction(FM_APPWIDGET_UPDATE);
        iFilter.addAction(FM_TOGGLE_ONOFF);
        iFilter.addAction(FM_PREV_CHANNEL);
        iFilter.addAction(FM_NEXT_CHANNEL);
        iFilter.addAction(SERVICE_CMD);

        registerReceiver(mReceiver, iFilter);
    }

    private void clearTx() {
        mFMServiceState = FM_SERVICE_EXITING;
        mHandler.sendEmptyMessage(FM_CLOSE_FIRMWARE);
    }

    void clearFMService() {
        Log.i(TAG, "clearFMService: " + mFMServiceState);

        // Close FM TX
        if (FM_SERVICE_TX_INIT == mFMServiceState) {
            Log.i(TAG, "clearFMService: wait until FM_SERVICE_TX_ON");
            mHandler.sendEmptyMessageDelayed(FM_CLEAR_SERVICE, 100);
            return;
        }
        if (FM_SERVICE_TX_ON == mFMServiceState) {
            Log.i(TAG, "clearFMService: close tx");
            clearTx();
            return;
        }
        if (mFMServiceState == FM_SERVICE_EXITING) {
            Log.i(TAG, "clearFMService same message, ignore");
            return;
        }

        // Close FM RX
        mFMServiceState = FM_SERVICE_EXITING;
        stopForeground(true);

        mFMRadioInterface.disableRds();
        mAudioInterface.powerOffAudio();
        mHandler.sendEmptyMessage(FM_CLOSE_FIRMWARE);
    }

    private void showNotification() {
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.fm_statusbar_icon)
                .build();

        Intent i = new Intent(this, FMRadio.class);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, i, 0);

        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        notification.setLatestEventInfo(this,
                getText(R.string.fmradio_service_label), "", contentIntent);

        // We use a string id because it is a unique number. We use it later to
        // cancel.
        // mNoteMgr.notify(R.string.fmradio_service_label, notification);
        startForeground(R.string.fmradio_service_label, notification);
    }

    // FMR messages:
    private static final int FM_POWER_ON_CHIP = 1;
    private static final int FM_POWER_OFF_CHIP = 2;
    private static final int FM_OPEN_FIRMWARE = 3;
    private static final int FM_CLOSE_FIRMWARE = 4;
    private static final int FM_SEEK_CHANNEL = 5;
    private static final int FM_SET_RSSI_THRESHOLD = 6;
    private static final int FM_AIRPLANE_MODE_CHANGED = 7;
    private static final int FM_POWER_ON_AUDIO = 12;
    private static final int FM_SET_FREQ = 13;
    private static final int FM_OPEN_TX = 15;
    private static final int FM_CLEAR_SERVICE = 16;
    private static final int FM_DELAY_STOP_SERVICE = 17;
    private static final int FM_ENABLE_RDS = 20;
    private static final int FM_DISABLE_RDS = 21;
    private static final int FM_SET_ANTENNA = 23;
    private static final int FM_SET_OUTPUT_MODE = 24;

    // interval after which we stop the service when idle
    private static final int IDLE_DELAY = 10000;

    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Log.i(TAG, "Received message: " + msg.what);

            switch (msg.what) {
                case FM_POWER_ON_CHIP:
                    Log.i(TAG, "Message FM_POWER_ON_CHIP");
                    mAppWidgetProvider.notifyChange(FMRadioService.this,
                            FM_LAUNCHING);

                    // Wait for FM TX exit
                    if (FM_SERVICE_TX_INIT == mFMServiceState
                            || FM_SERVICE_TX_ON == mFMServiceState
                            || FM_SERVICE_EXITING == mFMServiceState) {
                        Log.i(TAG, "FM_POWER_ON_CHIP: wait tx stop");
                        mHandler.sendEmptyMessageDelayed(FM_POWER_ON_CHIP, 300);
                        break;
                    }

                    mFMServiceState = FM_SERVICE_INIT;
                    showNotification();
                    mFMRadioInterface.powerOnChip();
                    break;

                case FM_POWER_OFF_CHIP:
                    Log.i(TAG, "Message FM_POWER_OFF_CHIP");
                    mFMRadioInterface.powerOffChip();
                    break;

                case FM_OPEN_FIRMWARE:
                    Log.i(TAG, "Message FM_OPEN_FIRMWARE");
                    mFMRadioInterface.openFMRadio();
                    break;

                case FM_OPEN_TX:
                    Log.i(TAG, "Message FM_OPEN_TX: " + mFMServiceState);

                    if (mFMServiceState != FM_SERVICE_NONE) {
                        Log.i(TAG, "FM_OPEN_TX: wait rx stop");
                        mHandler.sendEmptyMessageDelayed(FM_OPEN_TX, 200);
                        break;
                    }

                    mFMServiceState = FM_SERVICE_TX_INIT;
                    mFMRadioInterface.openFMRadioTx();
                    break;

                case FM_CLOSE_FIRMWARE:
                    Log.i(TAG, "Message FM_CLOSE_FIRMWARE");
                    mFMRadioInterface.closeFMRadio();
                    break;

                case FM_CLEAR_SERVICE:
                    Log.i(TAG, "Message FM_CLEAR_SERVICE");
                    clearFMService();
                    break;

                case FM_SET_FREQ:
                    Log.i(TAG, "Message FM_SET_FREQ");
                    mFMRadioInterface.setFMFreq(ContentHelper
                            .loadCurrentFreq(FMRadioService.this));
                    break;

                case FM_SET_OUTPUT_MODE:
                    Log.i(TAG, "Message FM_CMD_SET_OUTPUT_MODE: ");
                    break;

                case FM_POWER_ON_AUDIO:
                    Log.i(TAG, "Message FM_POWER_ON_AUDIO");
                    mAudioInterface.powerOnAudio();
                    break;

                case FM_SEEK_CHANNEL:
                    Log.i(TAG, "Message ACTION_SEEK_CHANNEL");
                    mFMRadioInterface.seekFM(mCurrentFreq, (Integer) msg.obj);
                    break;

                case FM_SET_RSSI_THRESHOLD:
                    Log.i(TAG, "Message ACTION_SET_RSSI_THRESHOLD");
                    mFMRadioInterface.setFMRssiThreshold((Integer) msg.obj);
                    break;

                case FM_AIRPLANE_MODE_CHANGED: {
                    Log.i(TAG, "Message FM_AIRPLANE_MODE_CHANGED");
                    Toast ts = null;
                    ts = Toast.makeText(FMRadioService.this,
                            R.string.fmradio_airplane_mode_enable_in_listen,
                            Toast.LENGTH_LONG);
                    ts.show();

                    clearFMService();
                    Intent intent = new Intent("android.intent.action.STOP_FM");
                    sendBroadcast(intent);
                    break;
                }

                case FM_DELAY_STOP_SERVICE:
                    Log.i(TAG, "Message FM_DELAY_STOP_SERVICE: " + mFMServiceState);
                    if (mFMServiceState != FM_SERVICE_NONE
                            || mHandler.hasMessages(FM_POWER_ON_CHIP)
                            || mHandler.hasMessages(FM_OPEN_TX)) {
                        Log.i(TAG, "FM_DELAY_STOP_SERVICE: service not idle");
                        break;
                    }
                    stopSelf(mServiceStartId);
                    break;

                case FM_ENABLE_RDS: {
                    Log.i(TAG, "Message FM_ENABLE_RDS");
                    mFMRadioInterface.enableRds();
                    break;
                }
                case FM_DISABLE_RDS: {
                    Log.i(TAG, "Message FM_DISABLE_RDS");
                    mFMRadioInterface.disableRds();
                    break;
                }
                case FM_SET_ANTENNA: {
                    // set antenna only for fm rx
                    if (mFMServiceState == FM_SERVICE_ON) {
                        Log.i(TAG, "Message FM_SET_ANTENNA");
                        mFMRadioInterface.setAntenna(Util.isWiredHeadsetOn() ? 0
                                : 1);
                    }
                    break;
                }
                default:
                    Log.i(TAG, "mHandler handleMessage: fmrdio ignore msg");
                    break;
            }
        }
    };

    public FMRadioInterface.OnCmdDoneListener getCommandDoneListener() {
        return mOnCmdDoneListener;
    }

    // Handle low level posted event
    private final FMRadioInterface.OnCmdDoneListener mOnCmdDoneListener = new FMRadioInterface.OnCmdDoneListener() {

        public void onCmdDone(int what, int status, Object value) {
            Log.i(TAG, "OnCmdDoneListener:" + what + ", status:" + status);

            Intent intent = null;
            switch (what) {
                case FM_CMD_POWERON:
                    if (status == FM_STATUS_SUCCESS) {
                        Log.i(TAG, "Power on chip ok");
                        mFMServiceState = FM_SERVICE_OPENED;
                        mFMRadioInterface.openFMRadio();
                    } else {
                        Log.e(TAG, "Power on failed!");
                        mFMServiceState = FM_SERVICE_NONE;

                        callback2Client(FM_CB_OPEN_RX, false, null);
                        mAppWidgetProvider.notifyChange(FMRadioService.this,
                                FM_OPEN_FAILED);
                        mHandler.sendEmptyMessageDelayed(FM_DELAY_STOP_SERVICE,
                                IDLE_DELAY);
                    }
                    break;

                case FM_CMD_TX_ON: {
                    Log.i(TAG, "Open FM Tx done: " + status);
                    if (mFMServiceState != FM_SERVICE_TX_INIT) {
                        Log.e(TAG, "FM_CMD_TX_ON state error: " + mFMServiceState);
                        break;
                    }

                    boolean rst = (status == FM_STATUS_SUCCESS) ? true : false;
                    if (rst) {
                        mFMServiceState = FM_SERVICE_TX_ON;
                    } else {
                        mFMServiceState = FM_SERVICE_NONE;
                    }

                    callback2Client(FM_CB_OPEN_TX, rst, null);

                    break;
                }
                case FM_CMD_POWEROFF:
                    Log.i(TAG, "Power off chip complete!");
                    mFMServiceState = FM_SERVICE_NONE;
                    break;

                case FM_CMD_OPEN: {
                    Log.i(TAG, "open fm complete: " + status);
                    if (status == FM_STATUS_SUCCESS) {
                        mFMServiceState = FM_SERVICE_ON;
                        mAppWidgetProvider.notifyChange(FMRadioService.this, "");

                        mHandler.sendEmptyMessage(FM_SET_ANTENNA);

                        // Reduce the beep during the open of FM Radio
                        mHandler.sendEmptyMessage(FM_SET_FREQ);
                        mHandler.sendEmptyMessageDelayed(FM_POWER_ON_AUDIO, 100);

                        mHandler.sendEmptyMessageDelayed(FM_ENABLE_RDS, 200);
                    } else {
                        Log.i(TAG, "open fm failed");
                        mFMServiceState = FM_SERVICE_OPENED;
                        mHandler.sendEmptyMessage(FM_POWER_OFF_CHIP);
                        mHandler.sendEmptyMessageDelayed(FM_DELAY_STOP_SERVICE,
                                IDLE_DELAY);
                        mAppWidgetProvider.notifyChange(FMRadioService.this,
                                FM_OPEN_FAILED);
                    }

                    callback2Client(FM_CB_OPEN_RX, status == FM_STATUS_SUCCESS,
                            null);
                    break;
                }

                case FM_CMD_CLOSE:
                    Log.i(TAG, "OnCmdDoneListener : FM_CMD_CLOSE");
                    mFMServiceState = FM_SERVICE_OPENED;
                    mHandler.sendEmptyMessage(FM_POWER_OFF_CHIP);
                    intent = new Intent(FM_CLOSED);
                    sendBroadcast(intent);
                    mAppWidgetProvider.notifyChange(FMRadioService.this, FM_CLOSED);
                    ContentHelper
                            .saveCurrentFreq(FMRadioService.this, mCurrentFreq);
                    break;

                case FM_CMD_SET_FREQ: {
                    if (mFMServiceState != FM_SERVICE_TX_ON
                            && mFMServiceState != FM_SERVICE_ON)
                        break;
                    mAppWidgetProvider.notifyChange(FMRadioService.this, "");

                    Log.i(TAG, "set frequency, result: " + status);
                    callback2Client(FM_CB_SET_FREQ, status == FM_STATUS_SUCCESS,
                            null);
                    break;
                }
                case FM_CMD_SET_OUTPUT_MODE: {
                    Log.i(TAG, "OnCmdDoneListener : FM_CMD_SET_OUTPUT_MODE");
                    Message msg = Message.obtain(mHandler, FM_SET_OUTPUT_MODE,
                            Integer.valueOf(status));
                    mHandler.sendMessage(msg);
                    break;
                }
                case FM_CMD_SEEK: {
                    if (status == FM_STATUS_SUCCESS) {
                        mCurrentFreq = Integer.parseInt(value.toString());
                        mAppWidgetProvider.notifyChange(FMRadioService.this, "");
                    } else {
                        mAppWidgetProvider.notifyChange(FMRadioService.this, "");
                    }
                    callback2Client(FM_CB_SEEK_CHN, status == FM_STATUS_SUCCESS,
                            Integer.valueOf(mCurrentFreq));
                    break;
                }

                case FM_CMD_SEND_RDS_STRING:
                    Log.i(TAG, "OnCmdDoneListener, get rds string: ");
                    intent = new Intent(FM_RDS_STRING);
                    intent.putExtra("rds", (byte[]) value);
                    intent.putExtra("type", status);
                    sendBroadcast(intent);
                    break;

                case FM_CMD_SEND_RDS_RAW_DATA:
                    RDSController.getInstance().appendRdsRawData((byte[]) value);
                    break;

                default:
                    Log.i(TAG, "OnCmdDoneListener : fmrdio default cmd");
                    if (status == FM_STATUS_FAILED) {
                        Log.e(TAG, "OnCmdDoneListener, unknown error!");
                    }
                    break;
            }
        }
    };

    // Implementation of IFMRadioService AIDL Interface
    private final IFMRadioService.Stub mBinder = new IFMRadioService.Stub() {
        public int getFMServiceStatus() {
            Log.i(TAG, "AIDL: getFMServiceStatus: " + mFMServiceState);
            return mFMServiceState;
        }

        public boolean openFMRadio() {
            Log.i(TAG, "AIDL: openFMRadio");
            mHandler.sendEmptyMessage(FM_POWER_ON_CHIP);
            return true;
        }

        public boolean closeFMRadio() {
            Log.i(TAG, "AIDL: closeFMRadio");
            mHandler.sendEmptyMessage(FM_CLEAR_SERVICE);
            return true;
        }

        public boolean openFMRadioTx() {
            Log.i(TAG, "AIDL: openFMRadioTx");
            mHandler.sendEmptyMessage(FM_OPEN_TX);
            return true;
        }

        public int getFMFreq() {
            Log.i(TAG, "AIDL: getFMFreq");
            return FMRadioService.this.getFMFreq();
        }

        public boolean setRdsCallback(int start) {
            Log.i(TAG, "AIDL: setRdsCallback: " + start);
            return mFMRadioInterface.setRdsCallback(start);
        }

        public boolean setFMFreq(int freq) {
            Log.i(TAG, "AIDL: setFMFreq: " + freq + ", state:"
                    + mFMServiceState);
            if (mFMServiceState != FM_SERVICE_ON
                    && mFMServiceState != FM_SERVICE_TX_ON) {
                return false;
            }
            mCurrentFreq = freq;
            return mFMRadioInterface.setFMFreq(freq);
        }

        public boolean seekFM(int direction) {
            Log.i(TAG, "AIDL: seekFM");
            if (mFMServiceState != FM_SERVICE_ON)
                return false;
            Message msg = Message.obtain(mHandler, FM_SEEK_CHANNEL,
                    Integer.valueOf(direction));
            mHandler.sendMessage(msg);
            return true;
        }

        @SuppressWarnings("deprecation")
        public boolean stopSeekFM() {
            Log.w(TAG, "AIDL: stopSeekFM");
            if (mFMServiceState != FM_SERVICE_ON) {
                return false;
            }
            return mFMRadioInterface.stopSeekFM();
        }

        @SuppressWarnings("deprecation")
        public int getFMVolume() {
            Log.i(TAG, "AIDL: getFMVolume");
            if (mFMServiceState != FM_SERVICE_ON) {
                return -1;
            }
            mFMRadioInterface.getFMVolume();
            return 0;
        }

        @SuppressWarnings("deprecation")
        public boolean setFMVolume(int volume) {
            Log.i(TAG, "AIDL: setFMVolume");
            if (mFMServiceState != FM_SERVICE_ON) {
                return false;
            }
            return mFMRadioInterface.setFMVolume(volume);
        }

        public boolean isMuteFM() {
            Log.i(TAG, "AIDL: isMuteFM");
            if (mFMServiceState != FM_SERVICE_ON) {
                return false;
            }
            return mAudioInterface.getMute();
        }

        public boolean setMute(int mode) {
            Log.i(TAG, "AIDL: setMute");
            if (mFMServiceState != FM_SERVICE_ON) {
                return false;
            }
            mAudioInterface.setMute(mode == 0);
            return true;
        }

        public int getFMAudioRouting() {
            Log.i(TAG, "AIDL: getFMAudioRouting");
            if (mFMServiceState != FM_SERVICE_ON) {
                return -1;
            }
            return mAudioInterface.getAudioPath();
        }

        public boolean setFMAudioRouting(int mode) {
            Log.i(TAG, "AIDL: setFMAudioRouting");
            if (mFMServiceState != FM_SERVICE_ON) {
                return false;
            }
            mAudioInterface.setAudioOnSpeaker(mode == FMAudioInterface.ROUTE_FM_SPEAKER);
            return true;
        }

        @SuppressWarnings("deprecation")
        public int getFMRssiLevel() {
            Log.i(TAG, "AIDL: getFMRssiLevel");
            if (mFMServiceState != FM_SERVICE_ON) {
                return -1;
            }
            mFMRadioInterface.getFMRssiLevel();
            return 0;
        }

        public boolean setFMRssiThreshold(int value) {
            Log.i(TAG, "AIDL: setFMRssiThreshold! value = " + value);
            if (mFMServiceState != FM_SERVICE_ON) {
                return false;
            }
            Message msg = Message.obtain(mHandler, FM_SET_RSSI_THRESHOLD,
                    Integer.valueOf(value));
            mHandler.sendMessage(msg);
            return true;
        }

        public boolean enableRds() {
            Log.i(TAG, "AIDL: enableRds ");
            mHandler.sendEmptyMessage(FM_ENABLE_RDS);
            return true;
        }

        public boolean disableRds() {
            Log.i(TAG, "AIDL: disableRds ");
            mHandler.sendEmptyMessage(FM_DISABLE_RDS);
            return true;
        }

        public void registerCallback(IRemoteServiceCallback cb) {
            if (cb != null) {
                mCallbacks.register(cb);
            }
        }

        public void unregisterCallback(IRemoteServiceCallback cb) {
            if (cb != null) {
                mCallbacks.unregister(cb);
            }
        }
    };

    final RemoteCallbackList<IRemoteServiceCallback> mCallbacks = new RemoteCallbackList<IRemoteServiceCallback>();

    private void callback2Client(int callbackId, boolean result, Object data) {
        final int number = mCallbacks.beginBroadcast();
        for (int i = 0; i < number; i++) {
            try {
                switch (callbackId) {
                    case FM_CB_OPEN_RX: {
                        mCallbacks.getBroadcastItem(i).openFMRadioCallBack(result);
                        break;
                    }
                    case FM_CB_OPEN_TX: {
                        mCallbacks.getBroadcastItem(i)
                                .openFMRadioTxCallBack(result);
                        break;
                    }
                    case FM_CB_SEEK_CHN: {
                        mCallbacks.getBroadcastItem(i).seekStationCallback(result,
                                ((Integer) data).intValue());
                        break;
                    }
                    case FM_CB_SET_FREQ: {
                        mCallbacks.getBroadcastItem(i).setFrequencyCallback(result);
                        break;
                    }
                    default:
                        break;
                }
            } catch (RemoteException e) {
                Log.e(TAG, "", e);
            }
        }
        mCallbacks.finishBroadcast();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind() called");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind() delay stopSelf()");

        // It takes some time to power off the hardware
        // Delay some time to exit
        mHandler.sendEmptyMessageDelayed(FM_DELAY_STOP_SERVICE, IDLE_DELAY);

        return false;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate()");
        super.onCreate();

        mFMServiceState = FM_SERVICE_NONE;
        mFMRadioInterface = Factory.getFMRadioInterface(this);
        registerBroadcastListener();
        registerSdCardListener();
        RDSController.getInstance().setContext(this);
        mAudioInterface = Factory.getAudioInterface(this, (Context) this);
        mAudioInterface.registerMediaKey();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mServiceStartId = startId;

        if (intent != null) {
            if (mFMServiceState == FM_SERVICE_TX_INIT
                    || mFMServiceState == FM_SERVICE_TX_ON) {
                Toast.makeText(FMRadioService.this, R.string.fmradio_close_tx,
                        Toast.LENGTH_LONG).show();
                return START_STICKY;
            }

            String action = intent.getAction();
            if (FM_TOGGLE_ONOFF.equals(action)) {
                if (!Util.supportInternalAntenna(FMRadioService.this)
                        && !Util.isWiredHeadsetOn()) {
                    Toast.makeText(FMRadioService.this,
                            R.string.fmradio_no_headset_in_listen,
                            Toast.LENGTH_LONG).show();
                } else {
                    if (mFMServiceState == FM_SERVICE_NONE) {
                        try {
                            mBinder.openFMRadio();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            mBinder.closeFMRadio();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (FM_PREV_CHANNEL.equals(action)) {
                try {
                    mBinder.seekFM(FMRadioInterface.FMRADIO_SEEK_BACKWARD);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (FM_NEXT_CHANNEL.equals(action)) {
                try {
                    mBinder.seekFM(FMRadioInterface.FMRADIO_SEEK_FORWARD);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy()");
        mFMServiceState = FM_SERVICE_NONE;

        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        if (mSdCardListener != null) {
            unregisterReceiver(mSdCardListener);
            mSdCardListener = null;
        }

        mHandler.removeCallbacksAndMessages(null);

        mFMRadioInterface.destroy();
        mAudioInterface.unregisterMediaKey();

        super.onDestroy();
    }

    /**
     * Get current FMR frequency, just used for FMR widget
     * @return frequency
     */
    public int getFMFreq() {
        if (mFMServiceState == FM_SERVICE_ON) {
            Log.i(TAG, "getFMFreq: " + mCurrentFreq);
            return mCurrentFreq;
        }
        return -1;
    }

    /**
     * Get FMR service state
     * @return FMR service state
     */
    public int getState() {
        return mFMServiceState;
    }

    /**
     * Set FMR service state
     * @param state of FMR service
     */
    public void setState(int state) {
        if (state < FM_SERVICE_NONE || state > FM_SERVICE_TX_ON) {
            throw new IllegalArgumentException();
        }
        mFMServiceState = state;
    }
}
