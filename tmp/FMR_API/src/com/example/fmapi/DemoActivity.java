package com.example.fmapi;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.pekall.fmradio.FMAudioInterface;
import com.pekall.fmradio.IFMRadioService;
import com.pekall.fmradio.IRemoteServiceCallback;

public class DemoActivity extends Activity {

    private static final String TAG = "FMRadio";

    private IFMRadioService mFMService = null;

    // Since power-on fmr, seeking station, ... takes some time, we have callbacks
    // for them to update UI. No callback for other APIs
    private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() {
        public void openFMRadioCallBack(boolean result) {
            Log.i(TAG, "open fmr result: " + result);
        }

        public void openFMRadioTxCallBack(boolean result) {
            // not used in xmm6321
        }

        public void setFrequencyCallback(boolean result) {
            Log.i(TAG, "set freq result: " + result);
        }

        public void seekStationCallback(boolean result, int frequency) {
            Log.i(TAG, "seek result: " + result + ", " + frequency);
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                                       android.os.IBinder service) {
            Log.w(TAG, "FMR service connected!");

            mFMService = IFMRadioService.Stub.asInterface(service);
            if (mFMService == null) {
                Log.e(TAG, "Error, mFMService is null");
                return;
            }
            try {
                mFMService.registerCallback(mCallback);
            } catch (RemoteException e) {
                Log.e(TAG, "", e);
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.i(TAG, "FMR service disconnected");
            if (mFMService == null) {
                Log.e(TAG, "Error, mFMService null");
                return;
            }
            try {
                mFMService.unregisterCallback(mCallback);
            } catch (RemoteException e) {
                Log.e(TAG, "", e);
            }
            mFMService = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bindFMRService();
        initUi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindFMRService();
    }

    private void initUi() {
        Button btnOpenFmr = (Button) findViewById(R.id.btn_open_fmr);
        btnOpenFmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFMService == null) {
                    Log.e(TAG, "FMR service not bound!");
                    return;
                }
                try {
                    mFMService.openFMRadio();
                    // The result will be returned in openFMRadioCallBack()
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnCloseFmr = (Button) findViewById(R.id.btn_close_fmr);
        btnCloseFmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFMService == null) {
                    Log.e(TAG, "FMR service not bound!");
                    return;
                }
                try {
                    mFMService.closeFMRadio();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        Button btnSetFreq = (Button) findViewById(R.id.btn_set_freq);
        btnSetFreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFMService == null) {
                    Log.e(TAG, "FMR service not bound!");
                    return;
                }
                try {
                    mFMService.setFMFreq(91500);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        Button btnSeekFwd = (Button) findViewById(R.id.btn_seek_fwd);
        btnSeekFwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFMService == null) {
                    Log.e(TAG, "FMR service not bound!");
                    return;
                }
                try {
                    mFMService.seekFM(1);
                    // The result will be returned in seekStationCallback()
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        Button btnSeekBwd = (Button) findViewById(R.id.btn_seek_bwd);
        btnSeekBwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFMService == null) {
                    Log.e(TAG, "FMR service not bound!");
                    return;
                }
                try {
                    mFMService.seekFM(0);
                    // The result will be returned in seekStationCallback()
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        Button btnToggleMute = (Button) findViewById(R.id.btn_toggle_mute);
        btnToggleMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFMService == null) {
                    Log.e(TAG, "FMR service not bound!");
                    return;
                }
                try {
                    mFMService.setMute(mFMService.isMuteFM() ?
                            FMAudioInterface.FMRADIO_UNMUTE_AUDIO :
                            FMAudioInterface.FMRADIO_MUTE_AUDIO);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        Button btnAudioPath = (Button) findViewById(R.id.btn_headset_speaker);
        btnAudioPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To change body of implemented methods use File | Settings | File Templates.
                if (mFMService == null) {
                    Log.e(TAG, "FMR service not bound!");
                    return;
                }
                try {
                    mFMService.setFMAudioRouting(
                            (mFMService.getFMAudioRouting() == FMAudioInterface.ROUTE_FM_HEADSET) ?
                            FMAudioInterface.ROUTE_FM_SPEAKER : FMAudioInterface.ROUTE_FM_HEADSET);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean bindFMRService() {
        Log.i(TAG, "Start to bind to FMR service");
        return bindService(new Intent("com.pekall.fmradio.FMRADIO_SERVICE"),
                mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindFMRService() {
        Log.i(TAG, "Start to unbind to FMR service");
        if (mFMService != null) {
            unbindService(mServiceConnection);
        }
    }
}
