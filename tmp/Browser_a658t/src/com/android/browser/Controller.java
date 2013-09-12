/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.android.browser;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.SearchManager;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ClipboardManager;
import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SqliteWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.net.http.SslError;
import android.net.ConnectivityManager;
import android.net.NetworkUtils;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.NetworkInfo;
import android.net.LinkProperties;
import android.net.ProxyProperties;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemProperties;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.provider.Browser;
import android.provider.BrowserContract;
import android.provider.Settings;
import android.provider.BrowserContract.Images;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Intents.Insert;
import android.provider.Telephony;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.util.Config;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.view.MotionEvent;
import android.view.View;
import android.view.LayoutInflater;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.MimeTypeMap;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebIconDatabase;
import android.webkit.WebSettings;
import android.webkit.WebView;

import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;
import android.telephony.TelephonyManager;
// FIXME: just_for build
// import android.telephony.TelephonyManager2;

import com.android.browser.IntentHandler.UrlData;

import com.android.browser.UI.ComboViews;
import com.android.browser.provider.BrowserProvider;
import com.android.browser.provider.BrowserProvider2.Thumbnails;
import com.android.browser.provider.SnapshotProvider.Snapshots;
import com.android.browser.search.SearchEngine;
import com.android.common.Search;
import com.android.internal.telephony.Phone;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;

/**
 * Controller for browser
 */
public class Controller
        implements WebViewController, UiController {

    private static final String LOGTAG = "Controller";
    private static final boolean LOGV_ENABLED = true;
    private final static boolean ENABLE_LOG = true;

    private static final String SEND_APP_ID_EXTRA =
        "android.speech.extras.SEND_APPLICATION_ID_EXTRA";
    private static final String INCOGNITO_URI = "browser:incognito";


    private static final int MAX_RETRY_COUNT = 120;
    private static final int RETRY_TIMEOUT = 500; // 0.5 second
    private int mRetryCount;  

    // public message ids
    public final static int LOAD_URL = 1001;
    public final static int STOP_LOAD = 1002;

    static final int LOAD_URL_DELAY = 2001;
    static final int OPEN_TAB_DELAY = 2003;
    static final int GO_WIFI_SETTING = 3000;
    // static final int RECOVERY_DELAY = 2004;
    static final int LOAD_URL_DELAY_BECKGROUND = 2005;
    static final int UNBLOCK_KEY_EVENTS = 2006;

    // Message Ids
    private static final int FOCUS_NODE_HREF = 102;
    private static final int RELEASE_WAKELOCK = 107;

    static final int UPDATE_BOOKMARK_THUMBNAIL = 108;
    
    static final int UPDATE_HOMEPAGE_THUMBNAIL = 109;

    private static final int OPEN_BOOKMARKS = 201;

    private static final int EMPTY_MENU = -1;

    // activity requestCode
    final static int COMBO_VIEW = 1;
    final static int PREFERENCES_PAGE = 3;
    final static int FILE_SELECTED = 4;
    final static int AUTOFILL_SETUP = 5;
    final static int EDIT_QUICK_NAVIGATION = 6;
    final static int ROTARY = 7;
    
    // private final static int WAKELOCK_TIMEOUT = 5 * 60 * 1000; // 5 minutes
    private final static int WAKELOCK_TIMEOUT = 50 * 1000; // 5 minutes

    // As the ids are dynamically created, we can't guarantee that they will
    // be in sequence, so this static array maps ids to a window number.
    final static private int[] WINDOW_SHORTCUT_ID_ARRAY =
    { R.id.window_one_menu_id, R.id.window_two_menu_id,
      R.id.window_three_menu_id, R.id.window_four_menu_id,
      R.id.window_five_menu_id, R.id.window_six_menu_id,
      R.id.window_seven_menu_id, R.id.window_eight_menu_id };

    // "source" parameter for Google search through search key
    final static String GOOGLE_SEARCH_SOURCE_SEARCHKEY = "browser-key";
    // "source" parameter for Google search through simplily type
    final static String GOOGLE_SEARCH_SOURCE_TYPE = "browser-type";

    // "no-crash-recovery" parameter in intent to suppress crash recovery
    final static String NO_CRASH_RECOVERY = "no-crash-recovery";

    // A bitmap that is re-used in createScreenshot as scratch space
    private static Bitmap sThumbnailBitmap;

    private Activity mActivity;
    private UI mUi;
    private TabControl mTabControl;
    private BrowserSettings mSettings;
    private WebViewFactory mFactory;

    private WakeLock mWakeLock;

    private UrlHandler mUrlHandler;
    private UploadHandler mUploadHandler;
    private IntentHandler mIntentHandler;
    private PageDialogsHandler mPageDialogsHandler;
    private NetworkStateHandler mNetworkHandler;

    private Message mAutoFillSetupMessage;

    private boolean mShouldShowErrorConsole;

    private SystemAllowGeolocationOrigins mSystemAllowGeolocationOrigins;

    // FIXME, temp address onPrepareMenu performance problem.
    // When we move everything out of view, we should rewrite this.
    private int mCurrentMenuState = 0;
    private int mMenuState = R.id.MAIN_MENU;
    private int mOldMenuState = EMPTY_MENU;
    private Menu mCachedMenu;

    private boolean mMenuIsDown;

    // For select and find, we keep track of the ActionMode so that
    // finish() can be called as desired.
    private ActionMode mActionMode;

    /**
     * Only meaningful when mOptionsMenuOpen is true.  This variable keeps track
     * of whether the configuration has changed.  The first onMenuOpened call
     * after a configuration change is simply a reopening of the same menu
     * (i.e. mIconView did not change).
     */
    private boolean mConfigChanged;

    /**
     * Keeps track of whether the options menu is open. This is important in
     * determining whether to show or hide the title bar overlay
     */
    private boolean mOptionsMenuOpen;

    /**
     * Whether or not the options menu is in its bigger, popup menu form. When
     * true, we want the title bar overlay to be gone. When false, we do not.
     * Only meaningful if mOptionsMenuOpen is true.
     */
    private boolean mExtendedMenuOpen;

    private boolean mActivityPaused = true;
    private boolean mLoadStopped;

    private Handler mHandler;
    // Checks to see when the bookmarks database has changed, and updates the
    // Tabs' notion of whether they represent bookmarked sites.
    private ContentObserver mBookmarksObserver;
    private CrashRecoveryHandler mCrashRecoveryHandler;

    private boolean mBlockEvents;
    
    private int mWidth = 0;
    private int mHight = 0;
    private WindowManager mWindowManager = null;
    private LayoutParams params = null;
    private LayoutParams paramsToPageUp = null;
    private LayoutParams paramsToPageDown = null;
    private View mView = null;
    private TextView flowText = null;
    private ImageView pageUp = null;
    private ImageView pageDown = null;
    public static boolean addPageFlip = false;
    public static boolean pageFilpState = false;
    int lastx;
    int lasty = 200;
    private TabMenu tabMenu;
    private MainMenu mPie;


    private String mDefaultTextEncodingName;
    private PageDownloader mPageDownloader; 
    private ApnHandler mApnHandler;
    private WifiDialogManager mWifiDialogManager;

    private static final HashMap<String, String> mUrlRedirectMap = new HashMap<String, String>();
    /* Don't do this because of wlan exlist
    static {
    	mUrlRedirectMap.put("http://www.126.com/", "http://smart.mail.126.com/");
    	mUrlRedirectMap.put("http://www.126.com", "http://smart.mail.126.com/");
    	mUrlRedirectMap.put("http://www.yahoo.com/", "http://cn.yahoo.com/");
    	mUrlRedirectMap.put("http://www.yahoo.com", "http://cn.yahoo.com/");
    }
    */

    private class WifiDialogManager {
        AlertDialog mWifiAvailableDlg;
        AlertDialog mWifiDisconnectedDlg;

        private void dismissAllDlg() {
            if(mWifiAvailableDlg != null && mWifiAvailableDlg.isShowing()) 
                mWifiAvailableDlg.dismiss();   
            if(mWifiDisconnectedDlg != null && mWifiDisconnectedDlg.isShowing()) 
                mWifiDisconnectedDlg.dismiss();
        }

        void showWifiAvailableDlg() {  
            dismissAllDlg();

            boolean noAutoLink = SystemProperties.get(
                    "net.wifi.autoLink", "").contains("false");
            WifiManager mng = (WifiManager) mActivity.
                getSystemService(Context.WIFI_SERVICE);

            if(LOGV_ENABLED) Log.v(LOGTAG, 
                    "showWifiAvailableDlg noAutoLink: " + noAutoLink +
                    ", AvailabilityConfiguredNetworks: " + 
                    mng.getAvailabilityConfiguredNetworks());
            if(noAutoLink || (!noAutoLink && 
                        !mng.getAvailabilityConfiguredNetworks())) {
                int wifiMessage = R.string.wifi_available;
                /* if(!mSettings.getDataConnection().equals(ApnHandler.APN_TYPE_WAP)) {
                   wifiMessage = R.string.wifi_available_net;
                   }*/
    
                if(mWifiAvailableDlg == null) {
                    mWifiAvailableDlg = new AlertDialog.Builder(mActivity)
                        .setTitle(R.string.wifi_available_title)
                        .setMessage(wifiMessage)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = new Intent();
                                intent.setClassName("com.android.settings",
                                    "com.android.settings.wifi.WifiSettings");
                                mActivity.startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mApnHandler.enableApnConnection(
                                    mApnHandler.apnSettingsToApnName(
                                    mSettings.getDataConnection()));
                            }
                        })
                        .create();
                }
                if(!mWifiAvailableDlg.isShowing())
                    mWifiAvailableDlg.show();
            }
        }

        void showWifiDisconnectedDialog() {
            dismissAllDlg();
            if(mSettings.getWifiCloseOperation()) {
                if(LOGV_ENABLED) Log.v(LOGTAG, "showWifiDisconnectedDialog return");
                mApnHandler.enableApnConnection(
                        mApnHandler.apnSettingsToApnName(
                            mSettings.getDataConnection()));
                return;
            }

            if(mWifiDisconnectedDlg == null) {
                final View view = LayoutInflater.from(mActivity).inflate(
                        R.layout.browser_wifi_close_dialog, null);
                mWifiDisconnectedDlg = new AlertDialog.Builder(mActivity)
                    .setView(view)
                    .setTitle(R.string.wifi_disconnected_title)
                    .setPositiveButton(R.string.yes,
                            new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        boolean saveUserOperation = ((CheckBox) view.findViewById(
                                R.id.wifi_close_dialog_check)).isChecked();
                        if(saveUserOperation) {
                            // save it to settings
                            mSettings.setWifiCloseOperation(true);
                        }

                        mApnHandler.enableApnConnection(

                            mApnHandler.apnSettingsToApnName(
                                mSettings.getDataConnection()));
                        }
                    })
                    .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            mActivity.finish();
                        }
                        })
                .create();
            }
            mWifiDisconnectedDlg.setCanceledOnTouchOutside(false);
            if(Settings.System.getInt(mActivity.getContentResolver(), 
                    Settings.System.AIRPLANE_MODE_ON, 0) == 1) {
                if(mWifiDisconnectedDlg.isShowing()){
                    mWifiDisconnectedDlg.dismiss();
                }
                return;
            }
            
            if(!mWifiDisconnectedDlg.isShowing())
                mWifiDisconnectedDlg.show();
        }
    }

    private static final int TYPE_MOBILE = 
        ConnectivityManager.TYPE_MOBILE;
    private static final int TYPE_MOBILE_MMS = 
        ConnectivityManager.TYPE_MOBILE_MMS;
    // FIXME: just hardcode for Suez
    private static int TYPE_MOBILE_INTERNET = 14;
    // ConnectivityManager.TYPE_MOBILE_INTERNET;
    private static int TYPE_MOBILE_WAP = 15;
    // ConnectivityManager.TYPE_MOBILE_WAP;
    private static int TYPE_MOBILE_EMAIL = 15;
    // ConnectivityManager.TYPE_MOBILE_EMAIL;
    private static int TYPE_MOBILE_LAB = 17;
    // ConnectivityManager.TYPE_MOBILE_LAB;
    private static int TYPE_MOBILE2_INTERNET = 14;
    // ConnectivityManager.TYPE_MOBILE2_INTERNET;
    
    private static String ENABLE_APN_NET = "";
    private static String ENABLE_APN_WAP = "";

    class ApnHandler {
        private final static String APN_LOG_TAG = "ApnHandler";

        final static String APN_TYPE_WAP = "wap";
        final static String APN_TYPE_INTERNET = "internet";
        final static String APN_TYPE_SYSTEM = "system";
        final static String APN_TYPE_MMS = "mms";
        final static String APN_TYPE_EMAIL = "email";
        final static String APN_TYPE_LAB = "lab";
        final static String APN_TYPE_INTERNET2 = "internet2";

        //Remember the apn when user launches the Browser.
        private String mOrgApn;

        // APN from launching intent
        private String mIntentApn;

        private String mCurrentApn;

        //copy it from Phone. Hope SDK doesn't change this
        private static final int APN_ALREADY_ACTIVE     = 0;
        private static final int APN_REQUEST_STARTED    = 1;
        private static final int APN_TYPE_NOT_AVAILABLE = 2;
        private static final int APN_REQUEST_FAILED     = 3;

        private int mNetworkType = TYPE_MOBILE;

        private ConnectivityManager mConnMgr;

        private boolean mNeedRestartNetwork;

        private boolean mUserConfigWifi;

        void setUserConfigWifi(boolean b) {
            if(ENABLE_LOG) Log.v(APN_LOG_TAG, "enter setUserConfigWifi()");

            mUserConfigWifi = b;
        }

        boolean userConfigWifi() {
            if(ENABLE_LOG) Log.v(APN_LOG_TAG, 
                    "enter userConfigWifi(): " + mUserConfigWifi);

            return mUserConfigWifi;
        }

        ConnectivityManager getConnectivityManager(){
            return mConnMgr;
        } 
        
        // WIFI power-on, but NO APs nearby
        boolean isWifiPowerOn() {
            WifiManager mng = (WifiManager) mActivity.
                getSystemService(Context.WIFI_SERVICE);
            final List<ScanResult> results = mng.getScanResults();

            if(ENABLE_LOG) Log.v(APN_LOG_TAG, 
                "enter isWifiPowerOn(): wifi enable: " +
                mng.isWifiEnabled() + ", ScanResult: " + results);
            return mng.isWifiEnabled() && 
                (results == null || results.isEmpty());
        }

        // WIFI power-on, and found APs nearby
        boolean isWifiEnabled() {
            WifiManager mng = (WifiManager) mActivity.
                getSystemService(Context.WIFI_SERVICE);
            final List<ScanResult> results = mng.getScanResults();

            if(ENABLE_LOG) Log.v(APN_LOG_TAG, 
                "enter isWifiEnabled(): wifi enable: " +
                mng.isWifiEnabled() + ", ScanResult: " + results);
            return mng.isWifiEnabled() && 
                (results != null && !results.isEmpty());
        }

        // WIFI connected
        boolean isWifiConnected() {
            ConnectivityManager cm = (ConnectivityManager) mActivity.
                getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] infos = cm.getAllNetworkInfo();
            if(infos == null) return false;

            for(NetworkInfo info : infos) {
                if(info == null) continue;

                if(info.getType() != ConnectivityManager.TYPE_WIFI)
                    continue;
                // Network type is WIFI        
                if(info.isConnected()) 
                    return true;
                else
                    return false;
            }
            return false;
        }

        // WIFI power-on but not connected
        boolean isWifiActivated() {
            if(ENABLE_LOG) Log.v(APN_LOG_TAG, "enter isWifiActivated()");

            return (isWifiEnabled() && !isWifiConnected());
        }

        private String mProxyAddress;
        private int mProxyPort = -1;

        private final String[] APN_PROJECTION = {
            Telephony.Carriers.TYPE,         // 0
            Telephony.Carriers.PROXY,        // 1
            Telephony.Carriers.PORT          // 2
        };
        private final int COLUMN_TYPE      = 0;
        private final int COLUMN_PROXY     = 1;
        private final int COLUMN_PORT      = 2;

        boolean isProxySet() {
            return (mProxyAddress != null) && (mProxyAddress.trim().length() != 0);
        }  

        boolean isValidApnType(String types, String requestType) {
            // If APN type is unspecified, assume APN_TYPE_ALL.
            if (TextUtils.isEmpty(types)) {
                return true;
            }

            for (String t : types.split(",")) {
                if (t.equals(requestType) || t.equals(Phone.APN_TYPE_ALL)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Look up a host name and return the result as an int. Works if the argument
         * is an IP address in dot notation. Obviously, this can only be used for IPv4
         * addresses.
         * @param hostname the name of the host (or the IP address)
         * @return the IP address as an {@code int} in network byte order
         */
        int lookupHost(String hostname) {
            if(ENABLE_LOG) Log.v(APN_LOG_TAG, "enter lookupHost(): " + hostname);

            InetAddress inetAddress;       
            try {
                inetAddress = InetAddress.getByName(hostname);
            } catch (UnknownHostException e) {

                return -1;
            }
            byte[] addrBytes;
            int addr;
            addrBytes = inetAddress.getAddress();
            addr = ((addrBytes[3] & 0xff) << 24)
                | ((addrBytes[2] & 0xff) << 16)
                | ((addrBytes[1] & 0xff) << 8) 
                |  (addrBytes[0] & 0xff);      
            return addr;
        }

        /**
         * Get proxy and port for a APN
         *
         * @param apnName name of the APN: wap, internet, ...
         */
        ProxyProperties getProxyForApn(String apnName) {
            if(ENABLE_LOG) Log.v(APN_LOG_TAG, "enter getProxyForApn(): " + apnName);
            LinkProperties props = mConnMgr.getLinkProperties(
                    apnNameToNetworkType(apnName));
            return props.getHttpProxy();

            /* mProxyAddress = null;
            mProxyPort = -1;

            String selection = TextUtils.isEmpty(apnName) ? null :
                Telephony.Carriers.APN + "='" + apnName.trim() + "'";

            Cursor cursor = SqliteWrapper.query(mActivity, mActivity.getContentResolver(),
                    Uri.withAppendedPath(Telephony.Carriers.CONTENT_URI, "current"),
                    APN_PROJECTION, selection, null, null);

            if(LOGV_ENABLED) {
                Log.v(APN_LOG_TAG, "looking for apn: " + selection + " returned: " +
                        (cursor ==null ? "null cursor" : (cursor.getCount() + " hits")));
            }

            if (cursor == null) {
                Log.e(APN_LOG_TAG, "Apn is not found in Database!");
                return mProxyAddress;
            }

            try {
                while (cursor.moveToNext()) {
                    // Read values from APN settings
                    if (isValidApnType(cursor.getString(COLUMN_TYPE), Phone.APN_TYPE_WAP)) {
                        mProxyAddress = NetworkUtils.trimV4AddrZeros(cursor.getString(COLUMN_PROXY));
                        if (isProxySet()) {
                            String portString = cursor.getString(COLUMN_PORT);
                            try {
                                mProxyPort = Integer.parseInt(portString);
                            } catch (NumberFormatException e) {

                                if (TextUtils.isEmpty(portString)) {
                                    Log.w(APN_LOG_TAG, "wap port not set!");
                                } else {
                                    Log.e(APN_LOG_TAG, "Bad port number format: " + portString, e);
                                }
                            }
                        }
                    }
                }
            } finally {
                cursor.close();
            }
            */
        }

        /**
         * Convert browser apn type to system apn type
         *
         * @param setting 
         *          the browser apn type
         * @return the system apn type.
         */
        String apnSettingsToApnName(String setting) {
            return setting;
            /*
            if(!"system".equals(setting)) 
                return setting;

            String systemApn = getPreferredApn();
            // FIXME: How about all APNs were deleted
            if(systemApn == null || systemApn.length() == 0)
                return "wap";
            if(systemApn.startsWith("default,"))
                systemApn = systemApn.substring(8);
            return systemApn;
            */
        }

        String apnNameMapping(String apn) {
            if(ENABLE_LOG) Log.v(APN_LOG_TAG, 
                "enter apnNameMapping(): " + apn);

            if(APN_TYPE_INTERNET.equals(apn))
                return ENABLE_APN_NET;
            if(APN_TYPE_WAP.equals(apn)) 
                return ENABLE_APN_WAP;
            if(APN_TYPE_MMS.equals(apn)) 
                return "enableMMS";
            if(APN_TYPE_EMAIL.equals(apn)) 
                return "enableEMAIL";
            if(APN_TYPE_LAB.equals(apn)) 
                return "enableLAB";
            if(APN_TYPE_INTERNET2.equals(apn)) 
                return "enableINTERNET2";
            // Setting "system" just use default apn
            if(APN_TYPE_SYSTEM.equals(apn)) 
                return "";
 
            return "";
        }

        String networkToApnName(String network) {
            if(ENABLE_LOG) Log.v(APN_LOG_TAG, 
                "enter networkToApnName(): " + network);

            if("mobile".equals(network))
                return APN_TYPE_SYSTEM;
            if("mobile_internet".equals(network))
                return APN_TYPE_INTERNET;
            if("mobile_net".equals(network))
                return APN_TYPE_INTERNET;
            if("mobile_wap".equals(network))
                return APN_TYPE_WAP;
            if("mobile_mms".equals(network))
                return APN_TYPE_MMS;
            if("mobile_email".equals(network))
                return APN_TYPE_EMAIL;
            if("mobile_lab".equals(network))
                return APN_TYPE_LAB;
            if("mobile2_internet".equals(network))
                return APN_TYPE_INTERNET2;
 
            return "";
        }

        int apnNameToNetworkType(String apn) {
            if(ENABLE_LOG) Log.v(APN_LOG_TAG, 
                "enter apnNameToNetworkType(): " + apn);

            if(APN_TYPE_INTERNET.equals(apn)) 
                return TYPE_MOBILE_INTERNET;
            if(APN_TYPE_WAP.equals(apn)) 
                return TYPE_MOBILE_WAP;
            if(APN_TYPE_MMS.equals(apn)) 
                return TYPE_MOBILE_MMS;
            if(APN_TYPE_EMAIL.equals(apn)) 
                return TYPE_MOBILE_EMAIL;
            if(APN_TYPE_LAB.equals(apn)) 
                return TYPE_MOBILE_LAB;
            if(APN_TYPE_INTERNET2.equals(apn)) 
                return TYPE_MOBILE2_INTERNET;
 
            return TYPE_MOBILE;
        }

        void updateStatus() {
            if(LOGV_ENABLED) 
                Log.v(APN_LOG_TAG, "updateStatus, type: " + 
                        mNetworkType + ", apn: " + mCurrentApn );

            if(!mNetworkHandler.isNetworkUp()) {
                Log.e(APN_LOG_TAG, "updateStatus, network down");
                return;
            }

            if(mCurrentApn == null || 
                ApnHandler.APN_TYPE_SYSTEM.equals(mCurrentApn)) {

                mConnMgr.browserUsingInterface("default");
                BrowserSettings.getInstance().setApnSystemProperty("default");
 
                // Clear apn proxy and set system proxy
                // WebView.setHttpProxy(null);
                WebView.enablePlatformNotifications();
                return;
            }

            LinkProperties props = mConnMgr.getLinkProperties(
                    apnNameToNetworkType(mCurrentApn));
            if(props == null) {
                Log.e(APN_LOG_TAG, 
                        "updateStatus, getLinkProperties failed");
                return;
            }

            // Ignore system http proxy event
            WebView.disablePlatformNotifications();

            // Set interface for current APN
            String inf = props.getInterfaceName();
            if(inf == null || inf.length() == 0) {
                inf = "default";
            }

            mConnMgr.browserUsingInterface(inf);
            BrowserSettings.getInstance().setApnSystemProperty(mCurrentApn);

            // Set http proxy for current APN
            ProxyProperties proxy = props.getHttpProxy();
            if(LOGV_ENABLED) {
            if(proxy != null) {
                    Log.v(APN_LOG_TAG, "updateStatus, setHttpProxy: " 
                        + proxy.getHost() + ", " + proxy.getPort());
                } else {
                    Log.v(APN_LOG_TAG, "updateStatus, setHttpProxy: null");
                }
            }

            WebView.setHttpProxy(proxy); // event if proxy is null
        }

        void enableApnConnection(String apn) {
            if(ENABLE_LOG) 
                Log.v(APN_LOG_TAG, "enter enableApnConnection(): " + apn);

            if(apn == null || (apn.equals(mCurrentApn) && 
                        mNetworkHandler.isNetworkUp()))
                return;

            mNetworkHandler.setNetworkUp(false);

            if(!apn.equals(mCurrentApn)) {
                disableApnConnection(mCurrentApn, false);
            }

            mCurrentApn = apn;
            mNetworkType = apnNameToNetworkType(apn);

            String feature = apnNameMapping(apn);

            if("".equals(feature)) { 
                if(LOGV_ENABLED) Log.d(APN_LOG_TAG, 
                    "enableApnConnection(): using system mobile network"); 
                mNetworkHandler.setNetworkUp(true);
                updateStatus();
                return;
            }

            if(getCurrentWebView() != null) {
                getCurrentWebView().FlushSocketPools();
            }

            int result = mConnMgr.startUsingNetworkFeature(
                    TYPE_MOBILE, feature);
            if(LOGV_ENABLED) Log.v(APN_LOG_TAG, 
                "startUsingNetworkFeature: " + feature + ", " + result);
 
            if(result == APN_ALREADY_ACTIVE) {
                mNetworkHandler.setNetworkUp(true);
                updateStatus();
            } else if(result == APN_TYPE_NOT_AVAILABLE || 
                    result == APN_REQUEST_FAILED) {
                if(LOGV_ENABLED) Log.v(APN_LOG_TAG, 
                        "start UsingNetworkFeature failed");
                mApnHandler.setNeedRestartNetwork(true);
            }
        }

        void disableApnConnection(String apn) {
            disableApnConnection(apn, true);
        }

        void disableApnConnection(String apn, boolean flush) {
            if(ENABLE_LOG) Log.v(APN_LOG_TAG, 
                "enter disableApnConnection(): " + apn);

            // stopLoading();
            if(flush && (getCurrentWebView() != null)) {
                getCurrentWebView().FlushSocketPools();
            }

            if(apn == null) {
                mCurrentApn = null;
                mNetworkType = 0;
                mNetworkHandler.setNetworkUp(false);
                mConnMgr.browserUsingInterface("default");
                BrowserSettings.getInstance().setApnSystemProperty("default");
                return;
            }
            try {

                String feature = apnNameMapping(apn);
                if(LOGV_ENABLED) Log.v(APN_LOG_TAG, 
                        "Begin stopUsingNetworkFeature: " + feature);

                /*if(feature == "") {
                    WebView.disablePlatformNotifications();
                    return;
                }*/

                if (mConnMgr != null && !"".equals(feature)) {
                    mConnMgr.stopUsingNetworkFeature(
                            TYPE_MOBILE, feature);
                }
            } finally {

                if(LOGV_ENABLED) Log.v(APN_LOG_TAG, 
                        "stopUsingNetworkFeature done!");
                mCurrentApn = null;
                mNetworkType = 0;
                mNetworkHandler.setNetworkUp(false);

                mConnMgr.browserUsingInterface("default");
                BrowserSettings.getInstance().setApnSystemProperty("default");
                WebView.disablePlatformNotifications();
                // FIXME: Should it be comment for effeciency ?
                // WebView.setHttpProxy(null);
            }
        }

        ApnHandler(Intent intent) {
            if(BrowserSettings.useSystemConnectFlags()) {
                try {
                    Class cls = ConnectivityManager.class;
                    Field fieldnet = cls.getDeclaredField("TYPE_MOBILE_INTERNET");
                    Field fieldwap = cls.getDeclaredField("TYPE_MOBILE_WAP");
                    Field fieldemail = cls.getDeclaredField("TYPE_MOBILE_EMAIL");
                    Field fieldlab = cls.getDeclaredField("TYPE_MOBILE_LAB");
                    
                    TYPE_MOBILE_INTERNET = fieldnet.getInt(null);
                    TYPE_MOBILE_WAP = fieldwap.getInt(null);
                    TYPE_MOBILE_EMAIL = fieldemail.getInt(null);
                    TYPE_MOBILE_LAB = fieldlab.getInt(null);
                    // Same with TYPE_MOBILE_INTERNET
                    TYPE_MOBILE2_INTERNET = fieldnet.getInt(null);
                } catch (Exception e) {
                    Log.e(LOGTAG, "", e);
                }
                
                ENABLE_APN_NET = "enableINTERNET";
                ENABLE_APN_WAP = "enableWAP";
            } else {
                ENABLE_APN_NET = "enableCMNET";
                ENABLE_APN_WAP = "enableCMWAP";
            }

            mConnMgr = (ConnectivityManager) mActivity.
                getSystemService(Context.CONNECTIVITY_SERVICE);

            if(mSettings == null)
                mSettings = BrowserSettings.getInstance();

            // The BrowserSettings object may not be destroyed.
            // We should clear status variable in last launch.
            mSettings.setUserConnection(null);

            //The origianl apn should not be changed after it was set.
            if(mOrgApn == null)
                mOrgApn = mSettings.getDataConnection();

            updateApnSetting(intent);
        }

        void updateApnSetting(Intent intent) {
            if (intent == null) return;
            String apn = intent.getStringExtra("apn");
            if(apn == null) return;

            if(LOGV_ENABLED) Log.v(APN_LOG_TAG, 
                    "handleApnSettingIntent apn: " + apn);
            mIntentApn = apn;

            if(!apn.equals(mSettings.getDataConnection())) {
                if(LOGV_ENABLED) Log.v(APN_LOG_TAG, "handleApnSettingIntent" + 
                        " setDataConnection: " + apn + " " + mOrgApn);
                mSettings.setDataConnection(apn);

                // Since APN is changed, set network flag to down 
                // to prevent action of loading url 
                mNetworkHandler.setNetworkUp(false);
            }
        }

        void restoreApnSetting() {
            String tmp = mSettings.getUserConnection();
            if(LOGV_ENABLED) Log.v(APN_LOG_TAG, 
                    "restoreApnSetting: " + mOrgApn + " " + tmp);

            //Firstly, restore the apn setting which user selected
            if(tmp != null) { 
                mSettings.setDataConnection(tmp);
                return;
            }

            //If user did not change apn setting, restore the value
            //when user launched the Browser
            if(mOrgApn != null)
                mSettings.setDataConnection(mOrgApn);
        }

        void setNeedRestartNetwork(boolean b) {
            if(ENABLE_LOG) Log.v(APN_LOG_TAG, 
                    "enter setNeedRestartNetwork(): " + b);

            mNeedRestartNetwork = b;
        }

        boolean needRestartNetwork() {
            if(ENABLE_LOG) Log.v(APN_LOG_TAG, 
                    "enter needRestartNetwork(): " + mNeedRestartNetwork);

            return mNeedRestartNetwork;
        }

        String getIntentApn() {
            return mIntentApn;
        }

        boolean ignoreWifiLogic() {
            // Disable guangdong feature
            return false;
            /*return (BrowserSettings.CMCC_GUANGDONG && 
                APN_TYPE_WAP.equals(mSettings.getDataConnection()));*/
        }
    }

    /* TODO: implment when begin porting MPDP for download
    int getDownloadApnType() {
if(ENABLE_LOG) Log.v(LOGTAG, "enter getDownloadApnType()");

        if(!BrowserSettings.CMCC_PLATFORM)
            return Downloads.Impl.NETWORK_ANY;

        // If wifi is open, use it. 
        // Otherwise, use browser's APN
        if(mApnHandler.isWifiConnected()) {
            networkType = Downloads.Impl.NETWORK_WIFI_ONLY;
        } else {
            String apn = mApnHandler.apnSettingsToApnName(
                    mSettings.getDataConnection());

            if(LOGV_ENABLED) Log.v(LOGTAG, "Set download APN: " + apn);

            if(ApnHandler.APN_TYPE_WAP.equals(apn)) {
                networkType = Downloads.Impl.NETWORK_MOBILE_WAP;
            } else if(ApnHandler.APN_TYPE_INTERNET.equals(apn)) {
                networkType = Downloads.Impl.NETWORK_MOBILE_NET;
            } else {
                // use system connection for download.
                // FIXME: should I disable current apn connection?
                if(mApnHandler.mCurrentApn != null) {
                    mApnHandler.disableApnConnection(
                            mApnHandler.mCurrentApn);
                }
            }
        }
    }
    */

    void onNetworkToggle(boolean up) {
        if(!BrowserSettings.CMCC_PLATFORM) {
            return;
        }

        if(LOGV_ENABLED) Log.v(LOGTAG, 
                "onNetworkToggle, setNeedRestartNetwork: " + !up);

        if(!up) {
            mApnHandler.setNeedRestartNetwork(true);
            return;
        }

        if(mApnHandler.needRestartNetwork()) {
            if(LOGV_ENABLED) Log.v(LOGTAG, 
                    "onNetworkToggle, restart network");

            mApnHandler.setNeedRestartNetwork(false);
            mApnHandler.enableApnConnection(
                    mApnHandler.apnSettingsToApnName(
                        mSettings.getDataConnection()));
        } else {
            mApnHandler.updateStatus();
        }
    }

    void setNeedRestartNetwork(boolean restart) {
        if(mApnHandler != null) {
            mApnHandler.setNeedRestartNetwork(restart);
        }
    }

    boolean canAccessNetwork() {
        if(!BrowserSettings.CMCC_PLATFORM) {
            if(ENABLE_LOG) 
                Log.v(LOGTAG, "canAccessNetwork(), not cmcc version");

            return true;
        }

        if(mApnHandler == null) {
            Log.e(LOGTAG, "canAccessNetwork(), mApnHandler is null!!!");
            return true;
        }

        boolean wifiOk = mApnHandler.isWifiConnected();
        boolean dataEnable = mApnHandler.
            getConnectivityManager().getMobileDataEnabled();
        if(ENABLE_LOG) 
            Log.v(LOGTAG, "canAccessNetwork, wifi connected: " + wifiOk
                    + ", data enable: " + dataEnable);

        // If browser was launched by intent with special apn,
        // we should only use mobile network.
        if(mApnHandler.getIntentApn() != null
                || mApnHandler.ignoreWifiLogic())
            return dataEnable;

        return (wifiOk || dataEnable);

        // FIXME: just_for build
        /*
        if(Build.IS_DSDS) {
           TelephonyManager2 mTelephonyManager2 = (TelephonyManager2)
               mActivity.getSystemService(Context.TELEPHONY_SERVICE2);
           simOk |= (mTelephonyManager2.getSimState() ==
                   TelephonyManager.SIM_STATE_READY);
        }
        */
    }

    // True for handled
    // False for not handled
    public boolean handleNetworkMessage(NetworkInfo info) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter handleNetworkMessage(): " + info.toString());

        if(!BrowserSettings.CMCC_PLATFORM)
            return false;

        String apn = mApnHandler.apnSettingsToApnName(
                mSettings.getDataConnection()); 
        if(mApnHandler.getIntentApn() == null 
                && !mApnHandler.ignoreWifiLogic()) {
            if(info.getType() == ConnectivityManager.TYPE_WIFI) {
                if(info.isConnected()) {
                    if(LOGV_ENABLED) Log.v(LOGTAG, "onReceive, use wifi");

                    // If wifi is connected, just use it
                    if(mApnHandler.mCurrentApn != null) {
                        Toast.makeText(mActivity, 
                                R.string.wifi_connected, 
                                Toast.LENGTH_SHORT).show();

                        if(!mApnHandler.mCurrentApn.equals(
                                    ApnHandler.APN_TYPE_SYSTEM)) {
                            // Clear apn proxy 
                            WebView.setHttpProxy(null);
                        mApnHandler.disableApnConnection(
                                mApnHandler.mCurrentApn);
                    }
                        mApnHandler.mCurrentApn = null;
                    }

                    WebView.enablePlatformNotifications();
                    mNetworkHandler.setNetworkUp(true);
                    mApnHandler.setNeedRestartNetwork(false);
                } else if(info.isConnectedOrConnecting()){
                    if(LOGV_ENABLED) Log.v(LOGTAG, "onReceive, wifi connecting");
                } else {
                    if(LOGV_ENABLED) Log.v(LOGTAG, "onReceive, wifi disconnect");

                    if(mNetworkHandler.isNetworkUp() && 
                            mApnHandler.mCurrentApn == null) { 
                        // We were using wifi, but wifi is disconnected.
                        mNetworkHandler.setNetworkUp(false);
                        if(mApnHandler.isWifiEnabled()) {
                            // if wifi is open, select other AP
                            mWifiDialogManager.showWifiAvailableDlg();
                        } else {
                            mWifiDialogManager.showWifiDisconnectedDialog();
                        }
                    }
                }
                return true;
            }

            // Ignore other network messages if we are using WIFI
            if(mApnHandler.isWifiConnected()) {
                if(LOGV_ENABLED) Log.v(LOGTAG, "onReceive, wifi is on, ignore " + 
                        "other network message"); 
                return true;
            }
        } else { // special apn case, ignore wifi status
            if(LOGV_ENABLED) Log.v(LOGTAG, "onReceive, ignore wifi");
        }

        String typeName = info.getTypeName();

        // If data network is disconnected for airplane mode or 
        // radio signal problem..., We need to enable it after 
        // radio signal is back on.
        // FIXME: may cmwap/net not down
        /*if("mobile".equals(typeName) && info.isDisconnected()) {
            if(LOGV_ENABLED) Log.v(LOGTAG, "onReceive, mobile down");
            mApnHandler.setNeedRestartNetwork(true);
        } */

        //Ignore broadcast messages of other apn type
        if(apn != null && !apn.equals(mApnHandler.
                    networkToApnName(typeName))) {
            if(LOGV_ENABLED) {
                Log.v(LOGTAG, "onReceive, ignore different message: " 
                        + apn + ", " + typeName);
            }
            return true;
        }
        return false;
    }

    private void handleApnResume() {
        if(!BrowserSettings.CMCC_PLATFORM)
            return;

        if(mApnHandler.getIntentApn() == null 
                && !mApnHandler.ignoreWifiLogic()) {
            if(mApnHandler.isWifiActivated()) {
                if(LOGV_ENABLED) Log.v(LOGTAG, "onResume, wifi is activated");
                if(mApnHandler.userConfigWifi()) {
                    // Clear the flag
                    if(LOGV_ENABLED) Log.v(LOGTAG, "onResume, wifi is " + 
                            "activated user select apn");
                } else {
                    if(LOGV_ENABLED) Log.v(LOGTAG, "onResume, wifi is activated, " + 
                            "send message");
                    mHandler.sendEmptyMessage(GO_WIFI_SETTING);
                }
                mApnHandler.setUserConfigWifi(true);
            }
            if(mApnHandler.isWifiConnected()) {
                if(LOGV_ENABLED) Log.v(LOGTAG, "onResume, use wifi");

                // If wifi is connected, just use it
                if(mApnHandler.mCurrentApn != null) {
                    Toast.makeText(mActivity, 
                            R.string.wifi_connected, 
                            Toast.LENGTH_SHORT).show();

                    if(!mApnHandler.mCurrentApn.equals(
                                ApnHandler.APN_TYPE_SYSTEM)) {
                        // Clear apn proxy
                        WebView.setHttpProxy(null);
                    mApnHandler.disableApnConnection(
                            mApnHandler.mCurrentApn);
                }
                    mApnHandler.mCurrentApn = null;
                }

                WebView.enablePlatformNotifications();
                mNetworkHandler.setNetworkUp(true);
            } else {    // wifi is off or no scan result
                if(LOGV_ENABLED) 
                    Log.v(LOGTAG, "onResume, wifi is off or no scan result");

                // We were using wifi before going to background, but wifi is off
                // or wifi is power on, but no scan result
                if((mNetworkHandler.isNetworkUp() 
                        && mApnHandler.mCurrentApn == null) || 
                        (mApnHandler.isWifiPowerOn())) {
                    mNetworkHandler.setNetworkUp(false);
                    mWifiDialogManager.showWifiDisconnectedDialog();
                } else { 
                    // We were using mobile network before going to background
                    mNetworkHandler.setNetworkUp(false);
                    mApnHandler.enableApnConnection(
                            mApnHandler.apnSettingsToApnName(
                            mSettings.getDataConnection()));
                }
            }
        } else {
            // If Browser was launched by intent with specific APN, 
            // like by DCD application, just use the apn in the intent.
            if(LOGV_ENABLED) Log.v(LOGTAG, "onResume, ignore wifi");

            mNetworkHandler.setNetworkUp(false);
            mApnHandler.enableApnConnection(
                    mApnHandler.apnSettingsToApnName(
                    mSettings.getDataConnection()));
        }
    }

    public Controller(Activity browser, boolean preloadCrashState) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter Controller()");

        WebView fake = new WebView(browser);

        mActivity = browser;
        mSettings = BrowserSettings.getInstance();
        mTabControl = new TabControl(this);
        mSettings.setController(this);
        mCrashRecoveryHandler = CrashRecoveryHandler.initialize(this);
        if (preloadCrashState) {
            mCrashRecoveryHandler.preloadCrashState();
        }
        mFactory = new BrowserWebViewFactory(browser);

        mUrlHandler = new UrlHandler(this);
        mIntentHandler = new IntentHandler(mActivity, this);
        mPageDialogsHandler = new PageDialogsHandler(mActivity, this);

        startHandler();
        mBookmarksObserver = new ContentObserver(mHandler) {

            @Override
            public void onChange(boolean selfChange) {
                int size = mTabControl.getTabCount();
                for (int i = 0; i < size; i++) {
                    mTabControl.getTab(i).updateBookmarkedStatus();
                }
            }

        };
        browser.getContentResolver().registerContentObserver(
                BrowserContract.Bookmarks.CONTENT_URI, true, mBookmarksObserver);

        mNetworkHandler = new NetworkStateHandler(mActivity, this);
        // Start watching the default geolocation permissions
        mSystemAllowGeolocationOrigins =
                new SystemAllowGeolocationOrigins(mActivity.getApplicationContext());
        mSystemAllowGeolocationOrigins.start();

        openIconDatabase();

        mPageDownloader = new PageDownloader();

        if (BrowserSettings.CMCC_PLATFORM) {

            // Handle apn setting in the intent.
            mApnHandler = new ApnHandler(mActivity.getIntent());

            // Prompt user that wifi is connected
            if(mApnHandler.getIntentApn() == null 
                    && !mApnHandler.ignoreWifiLogic()
                    && mApnHandler.isWifiConnected()) {
                Toast.makeText(mActivity, 
                        R.string.wifi_connected, 
                        Toast.LENGTH_SHORT).show();
                WebView.enablePlatformNotifications();
            }

            mWifiDialogManager = new WifiDialogManager();
        }
    }

    void start(final Bundle icicle, final Intent intent) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter start()");

        // FIXME: Go to homepage directly after crash happens
        // boolean noCrashRecovery = intent.getBooleanExtra(NO_CRASH_RECOVERY, false);
        boolean noCrashRecovery = true;
        
        mWidth = mActivity.getWindowManager().getDefaultDisplay().getWidth();
        mHight = mActivity.getWindowManager().getDefaultDisplay().getHeight();
        init();
        tabMenu = mUi.getTabMenu();
        mPie = mUi.getMainMenu();
        if (icicle != null || noCrashRecovery) {
            doStart(icicle, intent, false);
        } else {
            mCrashRecoveryHandler.startRecovery(intent);
            /*if(mActivityPaused == true ) {
                if(LOGV_ENABLED) Log.v(LOGTAG, 
                        "Activity is paused, recover delay 500ms");
                mHandler.sendMessageDelayed(Message.obtain(
                            null, RECOVERY_DELAY, intent), RETRY_TIMEOUT);
                return;
            }
            */
        }
    }

    @Override
    public void setFlow(String flowNum){
//        if(flowText != null){
//            flowText.setText(flowNum); 
//        }
    }
    private void init() {
        // TODO Auto-generated method stub
        mWindowManager = (WindowManager)mActivity.getSystemService(Context.WINDOW_SERVICE);
/*        params = new LayoutParams();
        
        params.width = LayoutParams.WRAP_CONTENT;
        params.height = LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        
        
        params.x = lastx;
        params.y = lasty;
        
        params.format = PixelFormat.TRANSLUCENT;
        params.flags = LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCH_MODAL;
        
        mView = LayoutInflater.from(mActivity).inflate(R.layout.frame, null);
        flowText = (TextView)mView.findViewById(R.id.flow);
        **/
        paramsToPageUp = new LayoutParams();
        paramsToPageUp.width = LayoutParams.WRAP_CONTENT;
        paramsToPageUp.height = LayoutParams.WRAP_CONTENT;
        paramsToPageUp.format = PixelFormat.TRANSLUCENT;
        paramsToPageUp.flags = LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCH_MODAL;
        paramsToPageUp.x = mWidth/2;
        paramsToPageUp.y = -50;
        pageUp = (ImageView)LayoutInflater.from(mActivity).inflate(R.layout.page_up, null);
        pageUp.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Tab current = getCurrentTab();
                if(current != null){
                    current.getWebView().pageUp(false);
                }
            }
        });
     
        paramsToPageDown = new LayoutParams();
        paramsToPageDown.width = LayoutParams.WRAP_CONTENT;
        paramsToPageDown.height = LayoutParams.WRAP_CONTENT;
        paramsToPageDown.format = PixelFormat.TRANSLUCENT;
        paramsToPageDown.flags = LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCH_MODAL;
        paramsToPageDown.x = mWidth/2;
        paramsToPageDown.y = 50;
        pageDown = (ImageView)LayoutInflater.from(mActivity).inflate(R.layout.page_down, null);
        pageDown.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Tab current = getCurrentTab();
                if(current != null){
                    current.getWebView().pageDown(false);
                }
            }
        });
/*        
        mWindowManager.addView(mView, params);
        setFlow("0.00B");
    
        mView.setOnTouchListener(new OnTouchListener() {
            float x = 0, y = 0; 
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                
                switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = event.getX();
                    y = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    updateViewPosition(v, event);
                    break;
                case MotionEvent.ACTION_UP:
                    
                    break;

                default:
                    break;
                }
                return false;
            }
            private void updateViewPosition(View v, MotionEvent event){
                lastx = (int)(event.getRawX() - x);
                lasty = (int)(event.getRawY() - y);
                updateFloatFrame(lastx, lasty); 
            }
        });**/
        
    }
    
    @Override
    public void addPageFlip(){
        pageFilpState = true;
        try {
            if(pageUp !=null && pageDown !=null){
                mWindowManager.addView(pageUp, paramsToPageUp);
                mWindowManager.addView(pageDown, paramsToPageDown);  
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    @Override
    public void hidePageFlip(){
        pageFilpState = false;
        try {
            if(pageUp !=null && pageDown !=null){
                mWindowManager.removeView(pageUp);
                mWindowManager.removeView(pageDown);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    @Override 
    public boolean hasFlowViewAdded(){
//        boolean addFlowView = false;
//        try{
//            mWindowManager.addView(mView, params);
//            addFlowView = true;
//        }catch(Exception e){
//        	Log.v(LOGTAG, "Flow has added to WindowManager");
//            addFlowView = false;
//        }
        return false;
    }
    
    @Override 
    public void addInternetFlow() {
        // TODO Auto-generated method stub
//    	try {
//	        if(mView != null)
//	            mWindowManager.addView(mView, params);
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.v(LOGTAG, "addInternetFlow failed");
//		}
    }
    @Override
    public void hideInternetFlow() {
        // TODO Auto-generated method stub
//    	try {
//	        if(mView != null)
//	            mWindowManager.removeView(mView);
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.v(LOGTAG, "hideInternetFlow failed");
//		}
    }
/*    
    void updateFloatFrame(int lastx, int lasty){
        params.x = lastx;
        params.y = lasty;
        mWindowManager.updateViewLayout(mView, params);
    }
    **/
    
    public void checkDataPreferencesLan(){
        String summary = mSettings.getSharedPreferences()
                .getString(PreferenceKeys.PREF_DATA_CONNECTION, null);
        if(summary!=null && !summary.contains("CM")){
          String defaultValue = mActivity.getString(R.string.pref_data_connection_default);
          mSettings.getSharedPreferences().edit().putString(PreferenceKeys.PREF_DATA_CONNECTION, defaultValue).commit();
        }
    }
    void doStart(final Bundle icicle, final Intent intent, final boolean fromCrash) {
        // Unless the last browser usage was within 24 hours, destroy any
        // remaining incognito tabs.

        Calendar lastActiveDate = icicle != null ?
                (Calendar) icicle.getSerializable("lastActiveDate") : null;
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);

        final boolean restoreIncognitoTabs = !(lastActiveDate == null
            || lastActiveDate.before(yesterday)
            || lastActiveDate.after(today));

        // Find out if we will restore any state and remember the tab.
        /* 
       String countryStore = mSettings.getSharedPreferences().getString("country", "");
       String country = mActivity.getResources().getConfiguration().locale.getCountry().toString();
       if(countryStore!=null && countryStore.length()!=0 && !countryStore.equalsIgnoreCase(country)){
          Toast.makeText(mActivity, R.string.language_change, Toast.LENGTH_LONG).show(); 
       }
       mSettings.getSharedPreferences().edit().putString("country", country).commit();
       **/
       checkDataPreferencesLan();
        final long currentTabId = 
        		mTabControl.canRestoreState(icicle, restoreIncognitoTabs);

        if (currentTabId == -1) {
            // Not able to restore so we go ahead and clear session cookies.  We
            // must do this before trying to login the user as we don't want to
            // clear any session cookies set during login.
            CookieManager.getInstance().removeSessionCookie();
        }
        
        int rotary = mSettings.getSharedPreferences().getInt(PreferenceKeys.PREF_ROTARY, 0);

        switch (rotary) {
        case 0:
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            break;
        case 1:
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            break;
        case 2:
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            break;            
        default:
            break;
        }

        GoogleAccountLogin.startLoginIfNeeded(mActivity,
                new Runnable() {

                    @Override public void run() {

                        onPreloginFinished(icicle, intent, currentTabId, restoreIncognitoTabs,
                                fromCrash);
                    }
                });
    }

    @Override
    public float getScreenBrightness() {
        float nowBrightnessValue = 0;
        ContentResolver resolver = mActivity.getContentResolver();
        try{
            nowBrightnessValue = Settings.System.getFloat(resolver, Settings.System.SCREEN_BRIGHTNESS);
        }
        catch(Exception e) {
            Log.v(LOGTAG, "can not get screen brightnessvalue");
        }
        return nowBrightnessValue;
    }
    
    private void onPreloginFinished(Bundle icicle, Intent intent, long currentTabId,
            boolean restoreIncognitoTabs, boolean fromCrash) {
        if(ENABLE_LOG) Log.v(LOGTAG, "onPreloginFinished currentTabId: " + currentTabId);

        if (currentTabId == -1) {
            BackgroundHandler.execute(new PruneThumbnails(mActivity, null));
            final Bundle extra = intent.getExtras();
            // Create an initial tab.
            // If the intent is ACTION_VIEW and data is not null, the Browser is
            // invoked to view the content by another application. In this case,
            // the tab will be close when exit.
            UrlData urlData = IntentHandler.getUrlDataFromIntent(intent);
            Tab t = null;
            if (urlData.isEmpty()) {
                t = openTabToHomePage2();
            } else {
                t = openTab(urlData);
            }
            if (t != null) {
                t.setAppId(intent.getStringExtra(Browser.EXTRA_APPLICATION_ID));
            }
            WebView webView = t.getWebView();
            if (extra != null) {
                int scale = extra.getInt(Browser.INITIAL_ZOOM_LEVEL, 0);
                if (scale > 0 && scale <= 1000) {
                    webView.setInitialScale(scale);
                }
            }
            mUi.updateTabs(mTabControl.getTabs());
        } else {
            mTabControl.restoreState(icicle, currentTabId, restoreIncognitoTabs,
                    mUi.needsRestoreAllTabs());
            List<Tab> tabs = mTabControl.getTabs();
            ArrayList<Long> restoredTabs = new ArrayList<Long>(tabs.size());
            for (Tab t : tabs) {
                restoredTabs.add(t.getId());
            }
            BackgroundHandler.execute(new PruneThumbnails(mActivity, restoredTabs));
            if (tabs.size() == 0) {
                openTabToHomePage();
            }
            mUi.updateTabs(tabs);
            ((BaseUi)mUi).getMainMenuControlPhone().populateMenu();
            // TabControl.restoreState() will create a new tab even if
            // restoring the state fails.
            setActiveTab(mTabControl.getCurrentTab());
            // Handle the intent if needed. If icicle != null, we are restoring
            // and the intent will be stale - ignore it.
            if (icicle == null || fromCrash) {
                mIntentHandler.onNewIntent(intent);
            }
        }
        // Read JavaScript flags if it exists.
        String jsFlags = getSettings().getJsEngineFlags();
        if (jsFlags.trim().length() != 0) {
            getCurrentWebView().setJsFlags(jsFlags);
        }
        if (BrowserActivity.ACTION_SHOW_BOOKMARKS.equals(intent.getAction())) {
            bookmarksOrHistoryPicker(ComboViews.Bookmarks);
        }
    }

    private static class PruneThumbnails implements Runnable {
        private Context mContext;
        private List<Long> mIds;

        PruneThumbnails(Context context, List<Long> preserveIds) {
            mContext = context.getApplicationContext();
            mIds = preserveIds;
        }

        @Override
        public void run() {
            ContentResolver cr = mContext.getContentResolver();
            if (mIds == null || mIds.size() == 0) {
                cr.delete(Thumbnails.CONTENT_URI, null, null);
            } else {
                int length = mIds.size();
                StringBuilder where = new StringBuilder();
                where.append(Thumbnails._ID);
                where.append(" not in (");
                for (int i = 0; i < length; i++) {
                    where.append(mIds.get(i));
                    if (i < (length - 1)) {
                        where.append(",");
                    }
                }
                where.append(")");
                cr.delete(Thumbnails.CONTENT_URI, where.toString(), null);
            }
        }

    }

    @Override
    public WebViewFactory getWebViewFactory() {
        return mFactory;
    }

    @Override
    public void onSetWebView(Tab tab, WebView view) {
        mUi.onSetWebView(tab, view);
    }

    @Override
    public void createSubWindow(Tab tab) {
        endActionMode();
        WebView mainView = tab.getWebView();
        WebView subView = mFactory.createWebView((mainView == null)
                ? false
                : mainView.isPrivateBrowsingEnabled());
        mUi.createSubWindow(tab, subView);
    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    @Override
    public Activity getActivity() {
        return mActivity;
    }

    void setUi(UI ui) {
        mUi = ui;
    }

    BrowserSettings getSettings() {
        return mSettings;
    }

    IntentHandler getIntentHandler() {
        return mIntentHandler;
    }

    @Override
    public UI getUi() {
        return mUi;
    }

    int getMaxTabs() {
        return mActivity.getResources().getInteger(R.integer.max_tabs);
    }

    /* TODO:
    String getTopTabLoadingUrl(){
        if(ENABLE_LOG) Log.v(LOGTAG, "enter getTopTabLoadingUrl()");

        Tab topTab = mTabControl.getCurrentTab();
        return (topTab == null)?null:topTab.getLoadingUrl();
    }

    void setTopTabLoadingUrl(String url){
        if(ENABLE_LOG) Log.v(LOGTAG, "enter setTopTabLoadingUrl()");

        Tab topTab = mTabControl.getCurrentTab();
        if(topTab != null){
            topTab.setLoadingUrl(url);
        }
    } */

    @Override
    public TabControl getTabControl() {
        return mTabControl;
    }

    @Override
    public List<Tab> getTabs() {
        return mTabControl.getTabs();
    }

    // Open the icon database.
    private void openIconDatabase() {
        // We have to call getInstance on the UI thread
        final WebIconDatabase instance = WebIconDatabase.getInstance();
        BackgroundHandler.execute(new Runnable() {


            @Override
            public void run() {
                instance.open(mActivity.getDir("icons", 0).getPath());
            }
        });
    }

    private void startHandler() {
        mHandler = new Handler() {


            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {

                    case WallpaperHandler.DECODE_ERROR:
                        Toast.makeText(mActivity, R.string.base_decode_failed, Toast.LENGTH_SHORT).show();
                        break;
                    case OPEN_BOOKMARKS:
                        bookmarksOrHistoryPicker(ComboViews.Bookmarks);
                        break;
                    case FOCUS_NODE_HREF:
                    {
                        String url = (String) msg.getData().get("url");
                        String title = (String) msg.getData().get("title");
                        String src = (String) msg.getData().get("src");
                        if (url == "") url = src; // use image if no anchor
                        if (TextUtils.isEmpty(url)) {
                            break;
                        }
                        HashMap focusNodeMap = (HashMap) msg.obj;
                        WebView view = (WebView) focusNodeMap.get("webview");
                        // Only apply the action if the top window did not change.
                        if (getCurrentTopWebView() != view) {
                            break;
                        }
                        switch (msg.arg1) {

                            case R.id.open_context_menu_id:
                                loadUrlFromContext(url);
                                break;
                            case R.id.view_image_context_menu_id:
                                loadUrlFromContext(src);
                                break;
                            case R.id.open_newtab_context_menu_id:
                                final Tab parent = mTabControl.getCurrentTab();
                                openTab(url, parent,
                                        !mSettings.openInBackground(), true);
                                break;
                            case R.id.copy_link_context_menu_id:
                                copy(url);
                                break;
                            case R.id.save_link_context_menu_id:
                            case R.id.download_context_menu_id:
                                DownloadHandler.onDownloadStartNoStream(
                                        mActivity, url, null, null, null,
                                        view.isPrivateBrowsingEnabled());
                                break;
                            case R.id.save_link_bookmark_id:
                                Intent linkIntent = addLinkToBookmarks(url, title);
                                if(linkIntent!=null){
                                    mActivity.startActivity(linkIntent);
                                }
                        }
                        break;
                    }

                    case LOAD_URL:
                        loadUrlFromContext((String) msg.obj);
                        break;

                    case STOP_LOAD:
                        stopLoading();
                        break;

                    case RELEASE_WAKELOCK:
                        if (mWakeLock != null && mWakeLock.isHeld()) {
                            if(ENABLE_LOG) 
                                Log.v(LOGTAG, "release loading wake lock!");

                            mWakeLock.release();
                            // if we reach here, Browser should be still in the
                            // background loading after WAKELOCK_TIMEOUT (5-min).
                            // To avoid burning the battery, stop loading.
                            mTabControl.stopAllLoading();
                        }
                        break;

                    case UPDATE_BOOKMARK_THUMBNAIL:
                        Tab tab = (Tab) msg.obj;
                        if (tab != null) {
                            updateScreenshot(tab);
                        }
                        break;

                    case LOAD_URL_DELAY:
                        Object[] args = (Object[])msg.obj;
                        loadUrl((Tab)args[0], (String)args[1], 
                            (args[2] == null) ? null : (Map<String, String>)args[2]);
                        break;
                        
                    case LOAD_URL_DELAY_BECKGROUND:
                        Object[] argsT = (Object[])msg.obj;
                        loadUrl((Tab)argsT[0], (String)argsT[1], 
                            (argsT[2] == null) ? null : (Map<String, String>)argsT[2]);
                        break;
                    /* case RECOVERY_DELAY:
                        mCrashRecoveryHandler.startRecovery((Intent)msg.obj);
                        break;
                     */

                    case GO_WIFI_SETTING:
                        if(LOGV_ENABLED) Log.v(LOGTAG, "handleMessage GO_WIFI_SETTING");

                        mWifiDialogManager.showWifiAvailableDlg();
                        break;

                    case UNBLOCK_KEY_EVENTS:
                        setBlockEvents(false);
                        break;
                }
            }
        };

    }

    public Intent addLinkToBookmarks(String url, String title){
        WebView w = getCurrentTopWebView();
        if (w == null) {
            return null;
        }
        Intent i = new Intent(mActivity,
                AddBookmarkPage.class);
        i.putExtra(BrowserContract.Bookmarks.URL, url);
        i.putExtra(BrowserContract.Bookmarks.TITLE, title);
        String touchIconUrl = w.getTouchIconUrl();
  
        WebSettings settings = w.getSettings();
        if (settings != null) {
           i.putExtra(AddBookmarkPage.USER_AGENT,
                settings.getUserAgentString());
        }
        return i;
    }
    @Override
    public Tab getCurrentTab() {
        return mTabControl.getCurrentTab();
    }

    @Override
    public void shareCurrentPage() {
        shareCurrentPage(mTabControl.getCurrentTab());
    }

    private void shareCurrentPage(Tab tab) {
        if (tab != null) {
            sharePage(mActivity, tab.getTitle(),
                    tab.getUrl(), tab.getFavicon(),
                    createScreenshot(tab.getWebView(),
                            getDesiredThumbnailWidth(mActivity),
                            getDesiredThumbnailHeight(mActivity)));
        }
    }

    /**
     * Share a page, providing the title, url, favicon, and a screenshot.  Uses
     * an {@link Intent} to launch the Activity chooser.
     * @param c Context used to launch a new Activity.
     * @param title Title of the page.  Stored in the Intent with
     *          {@link Intent#EXTRA_SUBJECT}
     * @param url URL of the page.  Stored in the Intent with
     *          {@link Intent#EXTRA_TEXT}
     * @param favicon Bitmap of the favicon for the page.  Stored in the Intent
     *          with {@link Browser#EXTRA_SHARE_FAVICON}
     * @param screenshot Bitmap of a screenshot of the page.  Stored in the
     *          Intent with {@link Browser#EXTRA_SHARE_SCREENSHOT}
     */
    static final void sharePage(Context c, String title, String url,
            Bitmap favicon, Bitmap screenshot) {
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_TEXT, url);
        send.putExtra(Intent.EXTRA_SUBJECT, title);
        send.putExtra(Browser.EXTRA_SHARE_FAVICON, favicon);
        send.putExtra(Browser.EXTRA_SHARE_SCREENSHOT, screenshot);
        try {
            c.startActivity(Intent.createChooser(send, c.getString(
                    R.string.choosertitle_sharevia)));
        } catch(android.content.ActivityNotFoundException ex) {

            // if no app handles it, do nothing
        }
    }

    private void copy(CharSequence text) {
        ClipboardManager cm = (ClipboardManager) mActivity
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(text);
    }

    // lifecycle

    protected void onConfgurationChanged(Configuration config) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter onConfgurationChanged()");

        mConfigChanged = true;
        if (mPageDialogsHandler != null) {
            mPageDialogsHandler.onConfigurationChanged(config);
        }
        mUi.onConfigurationChanged(config);
      

        int orientation = mActivity.getRequestedOrientation();
        Resources res = mActivity.getResources();
        DisplayMetrics display = res.getDisplayMetrics();
        int width = 0;
         
        width = display.widthPixels/5;
        String state = ((BaseUi)mUi).getMainMenuControlPhone().getBackAndForwardState();
        ((BaseUi)mUi).getMainMenuControlPhone().setMenuWidth(width);
        ((BaseUi)mUi).getMainMenuControlPhone().populateMenu();
        ((BaseUi)mUi).getMainMenuControlPhone().updateBackAndForwardState(state);
        // Reload to avoid potential layout error
        /* try {
            if(ENABLE_LOG) Log.v(LOGTAG, 
                    "onConfgurationChanged() reload");
            getCurrentTab().getTopWindow().reload();
        } catch (Exception e) {
            Log.e(LOGTAG, "onConfgurationChanged() error", e);
        }*/
    }

    @Override
    public void handleNewIntent(Intent intent) {
        if(mExitDlg != null && mExitDlg. isShowing()) {
            mExitDlg. dismiss();
            mExitDlg = null;
        }
            
        if(BrowserSettings.CMCC_PLATFORM) {
            mApnHandler.updateApnSetting(intent);
        }

        if (!mUi.isWebShowing()) {
            mUi.showWeb(false);
        }
        mIntentHandler.onNewIntent(intent);
    }

    protected void onPause() {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter onPause()");

//        SharedPreferences share = mActivity.getSharedPreferences("flow", mActivity.MODE_PRIVATE);
//        share.edit().putInt("lastx", lastx).putInt("lasty", lasty).commit();
        
        if (mUi.isCustomViewShowing()) {
            hideCustomView();
        }
        if (mActivityPaused) {
            Log.e(LOGTAG, "BrowserActivity is already paused.");
            return;
        }

        if(BrowserSettings.CMCC_PLATFORM)
            mDefaultTextEncodingName = mSettings.getDefaultTextEncoding();

        mActivityPaused = true;
        Tab tab = mTabControl.getCurrentTab();
        if (tab != null) {
            tab.pause();
            if (!pauseWebViewTimers(tab)) {
                if (mWakeLock == null) {
                    PowerManager pm = (PowerManager) mActivity
                            .getSystemService(Context.POWER_SERVICE);
                    mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Browser");
                }
                if(ENABLE_LOG) 
                    Log.v(LOGTAG, "onPause(), acquire loading wake lock!");
                mWakeLock.acquire();
                mHandler.sendMessageDelayed(mHandler
                        .obtainMessage(RELEASE_WAKELOCK), WAKELOCK_TIMEOUT);
            }
        }
        mUi.onPause();
        mNetworkHandler.onPause();

        /* FIXME: Should it be invoked?
        if(BrowserSettings.CMCC_PLATFORM) {
            WebView.setHttpProxy(null);
        } */
        WebView.disablePlatformNotifications();
        NfcHandler.unregister(mActivity);
        if (sThumbnailBitmap != null) {
            sThumbnailBitmap.recycle();
            sThumbnailBitmap = null;
        }
    }

    void onSaveInstanceState(Bundle outState) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter onSaveInstanceState()");

        // the default implementation requires each view to have an id. As the
        // browser handles the state itself and it doesn't use id for the views,
        // don't call the default implementation. Otherwise it will trigger the
        // warning like this, "couldn't save which view has focus because the
        // focused view XXX has no id".

        // Save all the tabs
        mTabControl.saveState(outState);
        if (!outState.isEmpty()) {
            // Save time so that we know how old incognito tabs (if any) are.
            outState.putSerializable("lastActiveDate", Calendar.getInstance());
        }
    }

    void onResume() {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter onResume()");
        SharedPreferences share = mActivity.getSharedPreferences("flow", mActivity.MODE_PRIVATE);
        hasFlowViewAdded();
        if (!mActivityPaused) {
            Log.e(LOGTAG, "BrowserActivity is already resumed.");
            return;
        }
        mActivityPaused = false;
        Tab current = mTabControl.getCurrentTab();
        if (current != null) {
            current.resume();
            resumeWebViewTimers(current);
        }
        releaseWakeLock();

        mUi.onResume();
        mUi.setUiTitle(getCurrentTab());
        mNetworkHandler.onResume();
        handleApnResume();
        if(!BrowserSettings.CMCC_PLATFORM)
        WebView.enablePlatformNotifications();
        NfcHandler.register(mActivity, this);

        if(BrowserSettings.CMCC_PLATFORM) {

            if(mDefaultTextEncodingName == null) {
                mDefaultTextEncodingName = mSettings.getDefaultTextEncoding();
            } else if(!mDefaultTextEncodingName.equals(
                        mSettings.getDefaultTextEncoding())) {
                if(LOGV_ENABLED) Log.v(LOGTAG, "onResume getDefaultTextEncoding:  "
                        + mDefaultTextEncodingName);

                mDefaultTextEncodingName = mSettings.getDefaultTextEncoding();
                BrowserSettings.getInstance().clearCache();
                getCurrentTab().getTopWindow().reload();
            }

            /* TODO: impelment after integrating stream ...
            if(mRtspReload) {
                if(LOGV_ENABLED) Log.v(LOGTAG, "onResume rtsp reload!");
                mRtspReload = false;
                reload();
            }*/
        }

        if(LOGV_ENABLED) Log.v(LOGTAG, "onResume done!");
    }

    private void releaseWakeLock() {
        if (mWakeLock != null && mWakeLock.isHeld()) {
            if(ENABLE_LOG) Log.v(LOGTAG, "do releaseWakeLock()");

            mHandler.removeMessages(RELEASE_WAKELOCK);
            mWakeLock.release();
        }
    }

    /**
     * resume all WebView timers using the WebView instance of the given tab
     * @param tab guaranteed non-null
     */
    private void resumeWebViewTimers(Tab tab) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter resumeWebViewTimers()");

        boolean inLoad = tab.inPageLoad();
        if ((!mActivityPaused && !inLoad) || (mActivityPaused && inLoad)) {
            CookieSyncManager.getInstance().startSync();
            WebView w = tab.getWebView();
            WebViewTimersControl.getInstance().onBrowserActivityResume(w);
        }
    }

    /**
     * Pause all WebView timers using the WebView of the given tab
     * @param tab
     * @return true if the timers are paused or tab is null
     */
    private boolean pauseWebViewTimers(Tab tab) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter pauseWebViewTimers()");

        if (tab == null) {
            return true;
        } else if (!tab.inPageLoad()) {
            CookieSyncManager.getInstance().stopSync();
            WebViewTimersControl.getInstance().onBrowserActivityPause(getCurrentWebView());
            return true;
        }
        return false;
    }

    void onDestroy() {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter onDestroy()");
 /*       
        if(mView!=null){
        	try {
                mWindowManager.removeView(mView);
			} catch (Exception e) {
				// TODO: handle exception
				Log.v(LOGTAG, "WindowManager remove Flow failed");
			}
         }
         **/
       // Stop all network activities after Browser quit
        if(mTabControl != null)
            mTabControl.stopAllLoading();

        if(getCurrentWebView() != null) {
            getCurrentWebView().FlushSocketPools();
        }

        if (mUploadHandler != null && !mUploadHandler.handled()) {
            mUploadHandler.onResult(Activity.RESULT_CANCELED, null);
            mUploadHandler = null;
        }
        if (mTabControl == null) return;
        mUi.onDestroy();
        // Remove the current tab and sub window
        Tab t = mTabControl.getCurrentTab();
        if (t != null) {
            dismissSubWindow(t);
            removeTab(t);
        }
        mActivity.getContentResolver().unregisterContentObserver(mBookmarksObserver);
        // Destroy all the tabs
        mTabControl.destroy();
        WebIconDatabase.getInstance().close();
        // Stop watching the default geolocation permissions
        mSystemAllowGeolocationOrigins.stop();
        mSystemAllowGeolocationOrigins = null;

        if(BrowserSettings.CMCC_PLATFORM) {

            // Clear pending load url messages
            mRetryCount = 0;
            mHandler.removeMessages(LOAD_URL_DELAY);
            mHandler.removeMessages(LOAD_URL_DELAY_BECKGROUND);
            // mHandler.removeMessages(RECOVERY_DELAY);

            // Disconnect special network
            mApnHandler.disableApnConnection(mApnHandler.mCurrentApn);
            mApnHandler.restoreApnSetting();
        }

        releaseWakeLock();
    }

    protected boolean isActivityPaused() {
        return mActivityPaused;
    }

    protected void onLowMemory() {
      // mTabControl.freeMemory();
        if(ENABLE_LOG) Log.v(LOGTAG, "enter onLowMemory()");
    }

    @Override
    public boolean shouldShowErrorConsole() {
        return mShouldShowErrorConsole;
    }

    protected void setShouldShowErrorConsole(boolean show) {
        if (show == mShouldShowErrorConsole) {
            // Nothing to do.
            return;
        }
        mShouldShowErrorConsole = show;
        Tab t = mTabControl.getCurrentTab();
        if (t == null) {
            // There is no current tab so we cannot toggle the error console
            return;
        }
        mUi.setShouldShowErrorConsole(t, show);
    }

    /* TODO:
       void stopLoadingWithoutToast() {
           if(ENABLE_LOG) Log.v(LOGTAG, "enter stopLoadingWithoutToast()");

        mRetryCount = 0;
        mHandler.removeMessages(LOAD_URL_DELAY);

        mLoadStopped = true;
        resetTitleAndRevertLockIcon();
        WebView w = getTopWindow();
        w.stopLoading();
        // FIXME: before refactor, it is using mWebViewClient. So I keep the
        // same logic here. But for subwindow case, should we call into the main
        // WebView's onPageFinished as we never call its onPageStarted and if
        // the page finishes itself, we don't call onPageFinished.
        mTabControl.getCurrentWebView().getWebViewClient().onPageFinished(w,
                w.getUrl());
    } */

    @Override
    public void stopLoading() {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter stopLoading()");

        if(BrowserSettings.CMCC_PLATFORM) {

            // Clear pending load url messages
            mRetryCount = 0;
            mHandler.removeMessages(LOAD_URL_DELAY);
            mHandler.removeMessages(LOAD_URL_DELAY_BECKGROUND);
            // mHandler.removeMessages(RECOVERY_DELAY);
        }

        mLoadStopped = true;
        Tab tab = mTabControl.getCurrentTab();
        WebView w = getCurrentTopWebView();
        w.stopLoading();
        mUi.onPageStopped(tab);
    }

    boolean didUserStopLoading() {
        return mLoadStopped;
    }

    // WebViewController

    @Override
    public void onPageStarted(Tab tab, WebView view, Bitmap favicon) {
        // We've started to load a new page. If there was a pending message
        // to save a screenshot then we will now take the new page and save
        // an incorrect screenshot. Therefore, remove any pending thumbnail
        // messages from the queue.
        mHandler.removeMessages(Controller.UPDATE_BOOKMARK_THUMBNAIL,
                tab);
        ((BaseUi)mUi).getMainMenuControlPhone().setBackWardEnabledTrue();
        Tab current = getCurrentTab();
        if(current != null){
	        if(current.canGoForward()){
	            ((BaseUi)mUi).getMainMenuControlPhone().setForwardEnabledTrue();
	        }else{
	            ((BaseUi)mUi).getMainMenuControlPhone().setForwardEnabledFalse();
	        }
        }
        // reset sync timer to avoid sync starts during loading a page
        CookieSyncManager.getInstance().resetSync();

        if (!mNetworkHandler.isNetworkUp()) {
            view.setNetworkAvailable(false);
        }

        // when BrowserActivity just starts, onPageStarted may be called before
        // onResume as it is triggered from onCreate. Call resumeWebViewTimers
        // to start the timer. As we won't switch tabs while an activity is in
        // pause state, we can ensure calling resume and pause in pair.
        if (mActivityPaused) {

            resumeWebViewTimers(tab);
        }
        mLoadStopped = false;
        endActionMode();

        mUi.onTabDataChanged(tab);

        String url = tab.getUrl();
        // update the bookmark database for favicon
        maybeUpdateFavicon(tab, null, url, favicon);

        Performance.tracePageStart(url);

        // Performance probe
        if (false) {

            Performance.onPageStarted();
        }

    }

    @Override
    public void onPageFinished(Tab tab) {
        mUi.onTabDataChanged(tab);
        if (!tab.isPrivateBrowsingEnabled()
                && !TextUtils.isEmpty(tab.getUrl())
                && !tab.isSnapshot()) {
            // Only update the bookmark screenshot if the user did not
            // cancel the load early and there is not already
            // a pending update for the tab.
            if (tab.inForeground() && !didUserStopLoading()
                    || !tab.inForeground()) {
                if (!mHandler.hasMessages(UPDATE_BOOKMARK_THUMBNAIL, tab)) {
                    // Only list view in bookmark page, no need thumbnail
                     mHandler.sendMessageDelayed(mHandler.obtainMessage(
                            UPDATE_BOOKMARK_THUMBNAIL, 0, 0, tab), 500);

                }
            }
        }
        // pause the WebView timer and release the wake lock if it is finished
        // while BrowserActivity is in pause state.
        if (mActivityPaused && pauseWebViewTimers(tab)) {
            releaseWakeLock();
        }

        
        // Performance probe
        if (false) {

            Performance.onPageFinished(tab.getUrl());
         }
        
        Performance.tracePageFinished();
    }

    @Override
    public void onLoadPageFinished(Tab tab) {
        // TODO Auto-generated method stub
    	if(homepage != true && homepageBack!= true){
            ((BaseUi)mUi).getMainMenuControlPhone().setBackWardEnabledTrue();
            Tab current = getCurrentTab();
            if(current != null){
	            if(current.canGoForward()){
	                ((BaseUi)mUi).getMainMenuControlPhone().setForwardEnabledTrue();
	            }else{
	                ((BaseUi)mUi).getMainMenuControlPhone().setForwardEnabledFalse();
	            }    
            }
    	}    
    }

    @Override
    public void onProgressChanged(Tab tab) {
        mCrashRecoveryHandler.backupState();
        int newProgress = tab.getLoadProgress();

        if (newProgress == 100) {
            CookieSyncManager.getInstance().sync();
            mUi.setUiTitle(tab);
            // onProgressChanged() may continue to be called after the main
            // frame has finished loading, as any remaining sub frames continue
            // to load. We'll only get called once though with newProgress as
            // 100 when everything is loaded. (onPageFinished is called once
            // when the main frame completes loading regardless of the state of
            // any sub frames so calls to onProgressChanges may continue after
            // onPageFinished has executed)
            if (tab.inPageLoad()) {
                updateInLoadMenuItems(mCachedMenu, tab);
            }
    //        ((BaseUi)mUi).getQuickNavigationScreen().checkTabIsQuick(tab);
        } else {
            if (!tab.inPageLoad()) {
                // onPageFinished may have already been called but a subframe is
                // still loading
                // updating the progress and
                // update the menu items.
                updateInLoadMenuItems(mCachedMenu, tab);
            }
        }
        mUi.onProgressChanged(tab);
    }

    @Override
    public void onUpdatedSecurityState(Tab tab) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter onUpdatedSecurityState()");

        mUi.onTabDataChanged(tab);
    }

    @Override
    public void onReceivedTitle(Tab tab, final String title) {
        mUi.onTabDataChanged(tab);
        final String pageUrl = tab.getOriginalUrl();
        if (TextUtils.isEmpty(pageUrl) || pageUrl.length()
                >= SQLiteDatabase.SQLITE_MAX_LIKE_PATTERN_LENGTH) {
            return;
        }
        // Update the title in the history database if not in private browsing mode
        if (!tab.isPrivateBrowsingEnabled()) {
            DataController.getInstance(mActivity).updateHistoryTitle(pageUrl, title);
        }
    }

    @Override
    public void onFavicon(Tab tab, WebView view, Bitmap icon) {
        mUi.onTabDataChanged(tab);
        maybeUpdateFavicon(tab, view.getOriginalUrl(), view.getUrl(), icon);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        mPageDownloader.add(url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(Tab tab, WebView view, String url) {
        if(ENABLE_LOG) Log.v(LOGTAG, 
                "enter shouldOverrideUrlLoading(): " + url);

        if(url.length() >= UrlInputView.LIMIT_URL_LENGTH){
            Toast.makeText(mActivity, R.string.url_write_limit2, Toast.LENGTH_SHORT).show();
            return true;
        }
        // Do NOT load url when network/APN/proxy are NOT ready
        if(!mNetworkHandler.isNetworkUp() || !canAccessNetwork()) {
            if(ENABLE_LOG) Log.v(LOGTAG, 
                    "shouldOverrideUrlLoading(), network down");

            loadUrl(tab, url);
            return true;
        }

        if(BrowserSettings.CMCC_PLATFORM) {
            // For Android handset, http://www.126.com/ is redirected to 
            // http://smart.mail.126.com/ 
            // But the UA for CMCC products could not be recogonized
            // by 126. Then the page goes to http://m.mail.163.com/auth/login.s
            // ?domain=126.com&fromHost=m.mail.126.com which does not keep 
            // username and password by adding attribute "autocomplete=off"
            // It fails CMCC browser test 15.1.
            // Redirect it manually...
            if(mUrlRedirectMap.containsKey(url)) {
                url = mUrlRedirectMap.get(url);
                if(ENABLE_LOG) Log.v(LOGTAG, 
                    "shouldOverrideUrlLoading(), url redirect to: " + url);

                loadUrl(tab, url);
                return true;
            }
        }

        mPageDownloader.reset();
        mPageDownloader.add(url);

        return mUrlHandler.shouldOverrideUrlLoading(tab, view, url);
    }

    @Override
    public boolean shouldOverrideKeyEvent(KeyEvent event) {
        if (mMenuIsDown) {

            // only check shortcut key when MENU is held
            return mActivity.getWindow().isShortcutKey(event.getKeyCode(),
                    event);
        } else {
            return false;
        }
    }

    @Override
    public void onUnhandledKeyEvent(KeyEvent event) {
        if (!isActivityPaused()) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                mActivity.onKeyDown(event.getKeyCode(), event);
            } else {
                mActivity.onKeyUp(event.getKeyCode(), event);
            }
        }
    }

    @Override
    public void doUpdateVisitedHistory(Tab tab, boolean isReload) {
        // Don't save anything in private browsing mode
        if (tab.isPrivateBrowsingEnabled()) return;
        String url = tab.getOriginalUrl();

        if (TextUtils.isEmpty(url)
                || url.regionMatches(true, 0, "about:", 0, 6)) {
            return;
        }
        DataController.getInstance(mActivity).updateVisitedHistory(url);
        mCrashRecoveryHandler.backupState();
    }

    @Override
    public void getVisitedHistory(final ValueCallback<String[]> callback) {
        AsyncTask<Void, Void, String[]> task =
                new AsyncTask<Void, Void, String[]>() {
            @Override
            public String[] doInBackground(Void... unused) {

                return Browser.getVisitedHistory(mActivity.getContentResolver());
            }
            @Override
            public void onPostExecute(String[] result) {
                callback.onReceiveValue(result);
            }
        };
        task.execute();
    }

    @Override
    public void onReceivedHttpAuthRequest(Tab tab, WebView view,
            final HttpAuthHandler handler, final String host,
            final String realm) {
        String username = null;
        String password = null;

        boolean reuseHttpAuthUsernamePassword
                = handler.useHttpAuthUsernamePassword();

        if (reuseHttpAuthUsernamePassword && view != null) {
            String[] credentials = view.getHttpAuthUsernamePassword(host, realm);
            if (credentials != null && credentials.length == 2) {
                username = credentials[0];
                password = credentials[1];
            }
        }

        if (username != null && password != null) {
            handler.proceed(username, password);
        } else {
            if (tab.inForeground() && !handler.suppressDialog()) {
                mPageDialogsHandler.showHttpAuthentication(tab, handler, host, realm);
            } else {
                handler.cancel();
            }
        }
    }

    @Override
    public void onDownloadStart(Tab tab, String url, String userAgent,
            String contentDisposition, String mimetype, long contentLength) {
        WebView w = tab.getWebView();
        DownloadHandler.onDownloadStart(mActivity, url, userAgent,
                contentDisposition, mimetype, w.isPrivateBrowsingEnabled());
        if (w.copyBackForwardList().getSize() == 0) {
            // This Tab was opened for the sole purpose of downloading a
            // file. Remove it.
            if (tab == mTabControl.getCurrentTab()) {
                // In this case, the Tab is still on top.
                if(ENABLE_LOG) Log.v(LOGTAG, "enter onDownloadStart(), " + 
                        "goBackOnePageOrQuit() url, " + w.getUrl());
                goBackOnePageOrQuit();
            } else {
                // In this case, it is not.
                closeTab(tab);
            }
        }
    }

    @Override
    public Bitmap getDefaultVideoPoster() {
        return mUi.getDefaultVideoPoster();
    }

    @Override
    public View getVideoLoadingProgressView() {
        return mUi.getVideoLoadingProgressView();
    }

    @Override
    public void showSslCertificateOnError(WebView view, SslErrorHandler handler,
            SslError error) {
        mPageDialogsHandler.showSSLCertificateOnError(view, handler, error);
    }

    @Override
    public void showAutoLogin(Tab tab) {
        assert tab.inForeground();
        // Update the title bar to show the auto-login request.
        mUi.showAutoLogin(tab);
    }

    @Override
    public void hideAutoLogin(Tab tab) {
        assert tab.inForeground();
        mUi.hideAutoLogin(tab);
    }

    // helper method

    /*
     * Update the favorites icon if the private browsing isn't enabled and the
     * icon is valid.
     */
    private void maybeUpdateFavicon(Tab tab, final String originalUrl,
            final String url, Bitmap favicon) {
        if (favicon == null) {
            return;
        }
        if (!tab.isPrivateBrowsingEnabled()) {
            Bookmarks.updateFavicon(mActivity
                    .getContentResolver(), originalUrl, url, favicon);
        }
    }

    @Override
    public void bookmarkedStatusHasChanged(Tab tab) {
        // TODO: Switch to using onTabDataChanged after b/3262950 is fixed
        mUi.bookmarkedStatusHasChanged(tab);
    }

    // end WebViewController

    protected void pageUp() {
        getCurrentTopWebView().pageUp(false);
    }

    protected void pageDown() {
        getCurrentTopWebView().pageDown(false);
    }

    // callback from phone title bar
    public void editUrl() {
        if (mOptionsMenuOpen) mActivity.closeOptionsMenu();
        mUi.editUrl(false);
    }

    public void startVoiceSearch() {
        Intent intent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                mActivity.getComponentName().flattenToString());
        intent.putExtra(SEND_APP_ID_EXTRA, false);
        intent.putExtra(RecognizerIntent.EXTRA_WEB_SEARCH_ONLY, true);
        mActivity.startActivity(intent);
    }

    @Override
    public void activateVoiceSearchMode(String title, List<String> results) {
        mUi.showVoiceTitleBar(title, results);
    }

    public void revertVoiceSearchMode(Tab tab) {
        mUi.revertVoiceTitleBar(tab);
    }

    public boolean supportsVoiceSearch() {
        SearchEngine searchEngine = getSettings().getSearchEngine();
        return (searchEngine != null && searchEngine.supportsVoiceSearch());
    }

    public void showCustomView(Tab tab, View view, int requestedOrientation,
            WebChromeClient.CustomViewCallback callback) {
        if (tab.inForeground()) {
            if (mUi.isCustomViewShowing()) {
                callback.onCustomViewHidden();
                return;
            }
            mUi.showCustomView(view, requestedOrientation, callback);
            // Save the menu state and set it to empty while the custom
            // view is showing.
            mOldMenuState = mMenuState;
            mMenuState = EMPTY_MENU;
            mActivity.invalidateOptionsMenu();
        }
    }

    @Override
    public void hideCustomView() {
        if (mUi.isCustomViewShowing()) {
            mUi.onHideCustomView();
            // Reset the old menu state.
            mMenuState = mOldMenuState;
            mOldMenuState = EMPTY_MENU;
            mActivity.invalidateOptionsMenu();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode,
            Intent intent) {

        if (getCurrentTopWebView() == null) return;
        switch (requestCode) {

            case PREFERENCES_PAGE:
                if (resultCode == Activity.RESULT_OK && intent != null) {
                    String action = intent.getStringExtra(Intent.EXTRA_TEXT);
                    if (PreferenceKeys.PREF_PRIVACY_CLEAR_HISTORY.equals(action)) {
                        mTabControl.removeParentChildRelationShips();
                    }
                }
                break;
            case FILE_SELECTED:
                // Chose a file from the file picker.
                if (null == mUploadHandler) break;
                mUploadHandler.onResult(resultCode, intent);
                break;
            case AUTOFILL_SETUP:
                // Determine whether a profile was actually set up or not
                // and if so, send the message back to the WebTextView to
                // fill the form with the new profile.
                if (getSettings().getAutoFillProfile() != null) {
                    mAutoFillSetupMessage.sendToTarget();
                    mAutoFillSetupMessage = null;
                }
                break;
            case COMBO_VIEW:
                if (intent == null || resultCode != Activity.RESULT_OK) {
                    break;
                }
                mUi.showWeb(false);
                if (Intent.ACTION_VIEW.equals(intent.getAction())) {
                    Tab t = getCurrentTab();
                    Uri uri = intent.getData();
                    loadUrl(t, uri.toString());
                } else if (intent.hasExtra(ComboViewActivity.EXTRA_OPEN_ALL)) {
                    String[] urls = intent.getStringArrayExtra(
                            ComboViewActivity.EXTRA_OPEN_ALL);
                    Tab parent = getCurrentTab();
                    for (String url : urls) {
                        parent = openTab(url, null,
                                !mSettings.openInBackground(), true);
                    }
                } else if (intent.hasExtra(ComboViewActivity.EXTRA_OPEN_SNAPSHOT)) {
                    long id = intent.getLongExtra(
                            ComboViewActivity.EXTRA_OPEN_SNAPSHOT, -1);
                    if (id >= 0) {
                        createNewSnapshotTab(id, true);
                    }
                }
                break;
//            case EDIT_QUICK_NAVIGATION: {
//            		if (intent != null) {
//            			boolean urlChanged = true;
//            			if (resultCode == Activity.RESULT_CANCELED) {
//            				urlChanged = false;
//            			}
//            			int index = intent.getIntExtra("index", 0);
//            			((BaseUi)mUi).getQuickNavigationScreen().invalidate(index, urlChanged);
//            		}
//            	}
//            	break;
            	
            case ROTARY:
                if (resultCode == Activity.RESULT_OK && intent != null) {
                   int rotary = intent.getIntExtra("rotary", 0);
                   switch (rotary) {
                   case 0:
                       mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                       break;
                   case 1:
                       mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                       break;
                   case 2:
                       mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                       break;            
                   default:
                       break;
                   }
              
                }
                break;
            default:
                break;
        }
        getCurrentTopWebView().requestFocus();
    }

    /**
     * Open the Go page.
     * @param startWithHistory If true, open starting on the history tab.
     *                         Otherwise, start with the bookmarks tab.
     */
    @Override
    public void bookmarksOrHistoryPicker(ComboViews startView) {

        if (mTabControl.getCurrentWebView() == null) {
            return;
        }
        // clear action mode
        if (isInCustomActionMode()) {
            endActionMode();
        }
        Bundle extras = new Bundle();
        // Disable opening in a new window if we have maxed out the windows
        extras.putBoolean(BrowserBookmarksPage.EXTRA_DISABLE_WINDOW,
                !mTabControl.canCreateNewTab()||mTabControl.getCurrentTab().isSnapshot());
        mUi.showComboView(startView, extras);
    }

    // combo view callbacks

    // key handling
    protected void onBackKey() {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter onBackKey()");

        if (!mUi.onBackKey()) {
            if(ENABLE_LOG) 
                Log.v(LOGTAG, "enter onBackKey(), ui check done");

            if(blockingEvents()) {
                if(ENABLE_LOG) Log.v(LOGTAG, "enter onBackKey(), blockingEvents");
                return;
            }

            setBlockEvents(true);
            sendUnblockKeyEventsMsg();

            WebView subwindow = mTabControl.getCurrentSubWindow();
            if (subwindow != null) {
                if(ENABLE_LOG) 
                    Log.v(LOGTAG, "enter onBackKey(), subwindow NOT null");

                if (subwindow.canGoBack()) {
                    if(ENABLE_LOG) 
                        Log.v(LOGTAG, "enter onBackKey(), subwindow.goBack()");

                    subwindow.goBack();
                } else {
                    if(ENABLE_LOG) 
                        Log.v(LOGTAG, "enter onBackKey(), dismissSubWindow()");

                    dismissSubWindow(mTabControl.getCurrentTab());
                }
            } else {
                if(ENABLE_LOG) 
                    Log.v(LOGTAG, "enter onBackKey(), goBackOnePageOrQuit()");

                goBackOnePageOrQuit();
            }
        }
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void sendUnblockKeyEventsMsg() {
        if(mHandler != null) {
            mHandler.sendEmptyMessageDelayed(UNBLOCK_KEY_EVENTS, 900);
        }
    }

    protected boolean onMenuKey() {

        return mUi.onMenuKey();
    }

    // menu handling and state
    // TODO: maybe put into separate handler

    protected boolean onCreateOptionsMenu(Menu menu) {

        if (mMenuState == EMPTY_MENU) {
            return false;
        }
        menu.clear();
        MenuInflater inflater = mActivity.getMenuInflater();
        inflater.inflate(R.menu.browser, menu);
        return true;
    }

    protected void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {

        if (v instanceof TitleBar) {

            return;
        }
        if (!(v instanceof WebView)) {
            return;
        }
        final WebView webview = (WebView) v;
        WebView.HitTestResult result = webview.getHitTestResult();
        if (result == null) {
            return;
        }

        int type = result.getType();
        if (type == WebView.HitTestResult.UNKNOWN_TYPE) {
            Log.w(LOGTAG,
                    "We should not show context menu when nothing is touched");
            return;
        }
        if (type == WebView.HitTestResult.EDIT_TEXT_TYPE) {
            // let TextView handles context menu
            return;
        }

        // Note, http://b/issue?id=1106666 is requesting that
        // an inflated menu can be used again. This is not available
        // yet, so inflate each time (yuk!)
        MenuInflater inflater = mActivity.getMenuInflater();
        inflater.inflate(R.menu.browsercontext, menu);

        // Show the correct menu group
        final String extra = result.getExtra();
        menu.setGroupVisible(R.id.PHONE_MENU,
                type == WebView.HitTestResult.PHONE_TYPE);
        menu.setGroupVisible(R.id.EMAIL_MENU,
                type == WebView.HitTestResult.EMAIL_TYPE);
        menu.setGroupVisible(R.id.GEO_MENU,
                type == WebView.HitTestResult.GEO_TYPE);
        menu.setGroupVisible(R.id.IMAGE_MENU,
                type == WebView.HitTestResult.IMAGE_TYPE
                || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE);
        menu.setGroupVisible(R.id.ANCHOR_MENU,
                type == WebView.HitTestResult.SRC_ANCHOR_TYPE
                || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE);
        boolean hitText = type == WebView.HitTestResult.SRC_ANCHOR_TYPE
                || type == WebView.HitTestResult.PHONE_TYPE
                || type == WebView.HitTestResult.EMAIL_TYPE
                || type == WebView.HitTestResult.GEO_TYPE;
        menu.setGroupVisible(R.id.SELECT_TEXT_MENU, hitText);
        if (hitText) {

            menu.findItem(R.id.select_text_menu_id)
                    .setOnMenuItemClickListener(new SelectText(webview));
        }
        // Setup custom handling depending on the type
        switch (type) {

            case WebView.HitTestResult.PHONE_TYPE:
                menu.setHeaderTitle(Uri.decode(extra));
                menu.findItem(R.id.dial_context_menu_id).setIntent(
                        new Intent(Intent.ACTION_VIEW, Uri
                                .parse(WebView.SCHEME_TEL + extra)));
                Intent addIntent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
                addIntent.putExtra(Insert.PHONE, Uri.decode(extra));
                addIntent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
                menu.findItem(R.id.add_contact_context_menu_id).setIntent(
                        addIntent);
                menu.findItem(R.id.copy_phone_context_menu_id)
                        .setOnMenuItemClickListener(
                        new Copy(extra));
                break;

            case WebView.HitTestResult.EMAIL_TYPE:
                menu.setHeaderTitle(extra);
                menu.findItem(R.id.email_context_menu_id).setIntent(
                        new Intent(Intent.ACTION_VIEW, Uri
                                .parse(WebView.SCHEME_MAILTO + extra)));
                menu.findItem(R.id.copy_mail_context_menu_id)
                        .setOnMenuItemClickListener(
                        new Copy(extra));
                break;

            case WebView.HitTestResult.GEO_TYPE:
                menu.setHeaderTitle(extra);
                menu.findItem(R.id.map_context_menu_id).setIntent(
                        new Intent(Intent.ACTION_VIEW, Uri
                                .parse(WebView.SCHEME_GEO
                                        + URLEncoder.encode(extra))));
                menu.findItem(R.id.copy_geo_context_menu_id)
                        .setOnMenuItemClickListener(
                        new Copy(extra));
                break;

            case WebView.HitTestResult.SRC_ANCHOR_TYPE:
            case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                menu.setHeaderTitle(extra);
                // decide whether to show the open link in new tab option
                boolean showNewTab = mTabControl.canCreateNewTab();
                MenuItem newTabItem
                        = menu.findItem(R.id.open_newtab_context_menu_id);
                newTabItem.setTitle(getSettings().openInBackground()
                        ? R.string.contextmenu_openlink_newwindow_background
                        : R.string.contextmenu_openlink_newwindow);
                newTabItem.setVisible(showNewTab);
                if (showNewTab) {

                    if (WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE == type) {
                        newTabItem.setOnMenuItemClickListener(
                                new MenuItem.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        final HashMap<String, WebView> hrefMap =
                                                new HashMap<String, WebView>();
                                        hrefMap.put("webview", webview);
                                        final Message msg = mHandler.obtainMessage(
                                                FOCUS_NODE_HREF,
                                                R.id.open_newtab_context_menu_id,
                                                0, hrefMap);
                                        webview.requestFocusNodeHref(msg);
                                        return true;
                                    }
                                });
                    } else {
                        newTabItem.setOnMenuItemClickListener(
                                new MenuItem.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        final Tab parent = mTabControl.getCurrentTab();
                                        openTab(extra, parent,
                                                !mSettings.openInBackground(),
                                                true);
                                        return true;
                                    }
                                });
                    }
                }
                if (type == WebView.HitTestResult.SRC_ANCHOR_TYPE) {
                    break;
                }
                // otherwise fall through to handle image part
            case WebView.HitTestResult.IMAGE_TYPE:
                if (type == WebView.HitTestResult.IMAGE_TYPE) {
                    menu.setHeaderTitle(extra);
                }
                menu.findItem(R.id.view_image_context_menu_id)
                        .setOnMenuItemClickListener(new OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        openTab(extra, mTabControl.getCurrentTab(), true, true);
                        return false;
                    }
                });
                menu.findItem(R.id.download_context_menu_id).
                        setOnMenuItemClickListener(
                                new Download(mActivity, extra, webview.isPrivateBrowsingEnabled()));
                menu.findItem(R.id.set_wallpaper_context_menu_id).
                        setOnMenuItemClickListener(new WallpaperHandler(mActivity,
                                extra, mHandler));
                break;

            default:
                Log.w(LOGTAG, "We should not get here.");
                break;
        }
        //update the ui
        mUi.onContextMenuCreated(menu);
    }

    /**
     * As the menu can be open when loading state changes
     * we must manually update the state of the stop/reload menu
     * item
     */
    private void updateInLoadMenuItems(Menu menu, Tab tab) {

        /* if (menu == null) {
            return;
        }
        MenuItem dest = menu.findItem(R.id.stop_reload_menu_id);
        MenuItem src = menu.findItem(R.id.stop_menu_id);
                 
        if (src != null) {
            dest.setIcon(src.getIcon());
            dest.setTitle(src.getTitle());
        } */
        return;
    }

    boolean onPrepareOptionsMenu(Menu menu) {

        updateInLoadMenuItems(menu, getCurrentTab());
        // hold on to the menu reference here; it is used by the page callbacks
        // to update the menu based on loading state
        mCachedMenu = menu;
        // Note: setVisible will decide whether an item is visible; while
        // setEnabled() will decide whether an item is enabled, which also means
        // whether the matching shortcut key will function.
        switch (mMenuState) {

            case EMPTY_MENU:
                if (mCurrentMenuState != mMenuState) {
                    menu.setGroupVisible(R.id.MAIN_MENU, false);
                    menu.setGroupEnabled(R.id.MAIN_MENU, false);
                    menu.setGroupEnabled(R.id.MAIN_SHORTCUT_MENU, false);
                }
                break;
            default:
                if (mCurrentMenuState != mMenuState) {
                    menu.setGroupVisible(R.id.MAIN_MENU, true);
                    menu.setGroupEnabled(R.id.MAIN_MENU, true);
                    menu.setGroupEnabled(R.id.MAIN_SHORTCUT_MENU, true);
                }
                final MenuItem fullModeItem = menu.findItem(R.id.full_mode_switch);
                MenuItem tab_switcher =  menu.findItem(R.id.tab_switch);
                if(!BrowserActivity.isTablet(mActivity)){
                    tab_switcher.setVisible(true);
                }
                fullMode = mSettings.useFullscreen();
                fullModeItem.setTitle((fullMode?R.string.full_screen_off:R.string.full_screen_on));
                if(BrowserSettings.CU_PLATFORM){
                    final MenuItem url_show = menu.findItem(R.id.page_info_url);
                    url_show.setVisible(true);
                }
                updateMenuState(getCurrentTab(), menu);
                break;
        }
        mCurrentMenuState = mMenuState;
       
        menu.findItem(R.id.reload_menu_id).setEnabled(true);
        menu.findItem(R.id.switch_url).setEnabled(true);
        menu.findItem(R.id.tab_switch).setEnabled(true);
        menu.findItem(R.id.add_bookmark_menu_id).setEnabled(true);
        menu.findItem(R.id.share_page_menu_id).setEnabled(true);
        menu.findItem(R.id.save_snapshot_menu_id).setEnabled(true);
        
        return mUi.onPrepareOptionsMenu(menu);
    }

    @Override
    public void updateMenuState(Tab tab, Menu menu) {

        boolean canGoBack = false;
        boolean canGoForward = false;
        boolean isHome = false;
        boolean isDesktopUa = false;
        boolean isLive = false;
        if (tab != null) {
            canGoBack = tab.canGoBack();
            canGoForward = tab.canGoForward();
            if((Controller.homepage == true||Controller.homepageBack == true) && getCurrentTab().getUrl().toString().length()!=0){
                canGoForward = true;
            }
            isHome = mSettings.getHomePage().equals(tab.getUrl());
            isDesktopUa = mSettings.hasDesktopUseragent(tab.getWebView());
            isLive = !tab.isSnapshot();
        }
        final MenuItem back = menu.findItem(R.id.back2_menu_id);
        back.setEnabled(canGoBack);

        final MenuItem home = menu.findItem(R.id.homepage_menu_id);
        home.setEnabled(!isHome);

        final MenuItem forward = menu.findItem(R.id.forward_menu_id);
        forward.setEnabled(canGoForward);

        /* final MenuItem source = menu.findItem(R.id.stop_menu_id);
        final MenuItem dest = menu.findItem(R.id.stop_reload_menu_id);
        if (source != null && dest != null) {
            dest.setTitle(source.getTitle());
            dest.setIcon(source.getIcon());
        } */

        menu.setGroupVisible(R.id.NAV_MENU, isLive);

        // decide whether to show the share link option
        PackageManager pm = mActivity.getPackageManager();
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        ResolveInfo ri = pm.resolveActivity(send,
                PackageManager.MATCH_DEFAULT_ONLY);
        menu.findItem(R.id.share_page_menu_id).setVisible(ri != null);

        boolean isNavDump = mSettings.enableNavDump();
        final MenuItem nav = menu.findItem(R.id.dump_nav_menu_id);
        nav.setVisible(isNavDump);
        nav.setEnabled(isNavDump);

        boolean showDebugSettings = mSettings.isDebugEnabled();
        final MenuItem counter = menu.findItem(R.id.dump_counters_menu_id);
        counter.setVisible(showDebugSettings);
        counter.setEnabled(showDebugSettings);
//        final MenuItem uaSwitcher = menu.findItem(R.id.ua_desktop_menu_id);
//        uaSwitcher.setChecked(isDesktopUa);
        menu.setGroupVisible(R.id.LIVE_MENU, isLive);
        menu.setGroupVisible(R.id.SNAPSHOT_MENU, !isLive);
        menu.setGroupVisible(R.id.COMBO_MENU, false);

        mUi.updateMenuState(tab, menu);
    }
 
    public static boolean fullMode = false;
    public boolean onOptionsItemSelected(MenuItem item) {

        if (null == getCurrentTopWebView()) {
            return false;
        }
        if (mMenuIsDown) {

            // The shortcut action consumes the MENU. Even if it is still down,
            // it won't trigger the next shortcut action. In the case of the
            // shortcut action triggering a new activity, like Bookmarks, we
            // won't get onKeyUp for MENU. So it is important to reset it here.
            mMenuIsDown = false;
        }
        hasFlowViewAdded();
        if (mUi.onOptionsItemSelected(item)) {
            // ui callback handled it
            return true;
        }
        switch (item.getItemId()) {
            // -- Main menu
            case R.id.new_tab_menu_id:
                openTabToHomePage();
                break;

            case R.id.incognito_menu_id:
                openIncognitoTab();
                break;

            case R.id.goto_menu_id:
                editUrl();
                break;
            
            case R.id.homepage2_menu_id:
                Tab current = mTabControl.getCurrentTab();
                loadUrl(current, mSettings.getHomePage());
                break;

            case R.id.bookmarks_menu_id:
                bookmarksOrHistoryPicker(ComboViews.Bookmarks);
                break;

            case R.id.history_menu_id:
                bookmarksOrHistoryPicker(ComboViews.History);
                break;

            case R.id.snapshots_menu_id:
                bookmarksOrHistoryPicker(ComboViews.Snapshots);
                break;

            case R.id.add_bookmark_menu_id:
                Intent bookmarkIntent = createBookmarkCurrentPageIntent(false);
                if (bookmarkIntent != null) {
                    mActivity.startActivity(bookmarkIntent);
                }
                break;
             
            case R.id.tab_switch:
                ((PhoneUi)mUi).toggleNavScreen();
                break;

            case R.id.switch_url:
                mUi.Gotourl();
                break;

            case R.id.stop_menu_id:
//            case R.id.stop_reload_menu_id:
//                if (isInLoad()) {
//                    stopLoading();
//                } else {
//                    getCurrentTopWebView().reload();
//                }
                mSettings.getSharedPreferences().edit().putString("country", "").commit();
                mActivity.finish();
                break;
            case R.id.reload_menu_id:
                getCurrentTopWebView().reload();
                break;

            case R.id.full_mode_switch:
                
                fullMode = !fullMode;
                //?
                mSettings.getSharedPreferences().edit().putBoolean(PreferenceKeys.PREF_FULLSCREEN, fullMode).commit();
                 if (getUi() != null) {
                     getUi().setFullscreen(fullMode);
                 }
                break;

            case R.id.back2_menu_id:
                getCurrentTab().goBack();
                break;

            case R.id.forward_menu_id:
                if(Controller.homepage == true||Controller.homepageBack == true){
                    homepage = false;
                    homepageBack = false;
                    ((BaseUi)mUi).hideHomePage();
                }else{
                    getCurrentTab().goForward();
                }
                 break;

            case R.id.close_menu_id:
                // Close the subwindow if it exists.
                if (mTabControl.getCurrentSubWindow() != null) {
                    dismissSubWindow(mTabControl.getCurrentTab());
                    break;
                }
                closeCurrentTab();
                break;

            case R.id.homepage_menu_id:
//                Tab current = mTabControl.getCurrentTab();
//                loadUrl(current, mSettings.getHomePage());
                break;

            case R.id.download_page:
                Intent data = new Intent();
                data.setAction("android.intent.action.VIEW_DOWNLOADS");
                mActivity.startActivity(data);
                break;
                
            case R.id.preferences_menu_id:
                Intent intent = new Intent(mActivity, BrowserPreferencesPage.class);
                intent.putExtra(BrowserPreferencesPage.CURRENT_PAGE,
                        getCurrentTopWebView().getUrl());
                mActivity.startActivityForResult(intent, PREFERENCES_PAGE);
                break;

            case R.id.find_menu_id:
                getCurrentTopWebView().showFindDialog(null, true);
                break;

            case R.id.save_snapshot_menu_id:
                // Log.v(LOGTAG, "PageDownloader downloadPage()");
                // FIXME: need to investigate the requirement
                // mPageDownloader.downloadPage();
                saveSnapShots();

                break;

            case R.id.page_info_menu_id:
                showPageInfo();
                break;

            case R.id.page_info_url:
                showPageUrl();
                break;
                
            case R.id.snapshot_go_live:
                goLive();
                return true;

            case R.id.share_page_menu_id:
                Tab currentTab = mTabControl.getCurrentTab();
                if (null == currentTab) {
                    return false;
                }
                shareCurrentPage(currentTab);
                break;

            case R.id.dump_nav_menu_id:
                getCurrentTopWebView().debugDump();
                break;

            case R.id.dump_counters_menu_id:
                getCurrentTopWebView().dumpV8Counters();
                break;

            case R.id.zoom_in_menu_id:
                getCurrentTopWebView().zoomIn();
                break;

            case R.id.zoom_out_menu_id:
                getCurrentTopWebView().zoomOut();
                break;

            case R.id.view_downloads_menu_id:
                viewDownloads();
                break;

//            case R.id.ua_desktop_menu_id:
//                WebView web = getCurrentWebView();
//                mSettings.toggleDesktopUseragent(web);
//                web.loadUrl(web.getOriginalUrl());
//                break;

            case R.id.window_one_menu_id:
            case R.id.window_two_menu_id:
            case R.id.window_three_menu_id:
            case R.id.window_four_menu_id:
            case R.id.window_five_menu_id:
            case R.id.window_six_menu_id:
            case R.id.window_seven_menu_id:
            case R.id.window_eight_menu_id:
                {
                    int menuid = item.getItemId();
                    for (int id = 0; id < WINDOW_SHORTCUT_ID_ARRAY.length; id++) {
                        if (WINDOW_SHORTCUT_ID_ARRAY[id] == menuid) {
                            Tab desiredTab = mTabControl.getTab(id);
                            if (desiredTab != null &&
                                    desiredTab != mTabControl.getCurrentTab()) {
                                switchToTab(desiredTab);
                            }
                            break;
                        }
                    }
                }
                break;

            default:
                return false;
        }
        return true;
    }

    
    @Override
    public void saveSnapShots(){
        final Tab source = getTabControl().getCurrentTab();
        if (source == null) return;
        final ContentResolver cr = mActivity.getContentResolver();
        final ContentValues values = source.createSnapshotValues();
        if(values == null){
        	return;
        }
        String url = (String)values.get(Snapshots.URL);
        boolean repeat = false;
        if(url!=null){
        	Cursor cursor = null;
            	try {
            	    cursor= cr.query(Snapshots.CONTENT_URI, new String[]{"_id"}, "url = ?", new String[]{url}, null);
                    if(cursor!=null && cursor.moveToNext()){
                        int id = cursor.getInt(0);
                        repeat = true;
                        values.put(Snapshots._ID, id);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                } finally {
                    if(cursor != null){
                        cursor.close();
                    }
                }

        }
        
        if (values != null && !repeat) {
            new AsyncTask<Tab, Void, Long>() {

                @Override
                protected Long doInBackground(Tab... params) {

                    Uri result = cr.insert(Snapshots.CONTENT_URI, values);
                    if (result == null) {
                        return null;
                    }
                    long id = ContentUris.parseId(result);
                    return id;
                }

                @Override
                protected void onPostExecute(Long id) {

                    if (id == null) {
                        Toast.makeText(mActivity, R.string.snapshot_failed,
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Bundle b = new Bundle();
                    b.putLong(BrowserSnapshotPage.EXTRA_ANIMATE_ID, id);
                    mUi.showComboView(ComboViews.Snapshots, b);
                };
            }.execute(source);
        } else if(values != null && repeat){
            AlertDialog.Builder  dialogBuilder = 
                    new AlertDialog.Builder(mActivity);
            dialogBuilder.setTitle(R.string.menu_cover);
            dialogBuilder.setMessage(R.string.snap_Coverage);
            dialogBuilder.setPositiveButton(R.string.ok , new OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
		            new AsyncTask<Tab, Void, Long>() {

		                @Override
		                protected Long doInBackground(Tab... params) {
		                	int	reId =  values.getAsInteger(Snapshots._ID);
		                	values.remove(Snapshots._ID);
		                    int id = cr.update(Snapshots.CONTENT_URI, values, "_id = ?", new String[]{reId+""});
		                    return (long)id;
		                }

		                @Override
		                protected void onPostExecute(Long id) {

		                    if (id == null) {
		                        Toast.makeText(mActivity, R.string.snapshot_failed,
		                                Toast.LENGTH_SHORT).show();
		                        return;
		                    }
		                    Bundle b = new Bundle();
		                    b.putLong(BrowserSnapshotPage.EXTRA_ANIMATE_ID, id);
		                    mUi.showComboView(ComboViews.Snapshots, b);
		                };
		            }.execute(source);
				}
			});
            dialogBuilder.setNegativeButton(R.string.cancel, null);
            dialogBuilder.create().show();
        } else {
            Toast.makeText(mActivity, R.string.snapshot_failed,
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void goLive() {

        Tab t = getCurrentTab();
        t.loadUrl(t.getUrl(), null);
    }

    @Override
    public void showPageInfo() {

        mPageDialogsHandler.showPageInfo(mTabControl.getCurrentTab(), false, null);
    }

    public void showPageUrl(){
        mPageDialogsHandler.showPageUrl(mTabControl.getCurrentTab());
    }
    public boolean onContextItemSelected(MenuItem item) {

        // Let the History and Bookmark fragments handle menus they created.
        if (item.getGroupId() == R.id.CONTEXT_MENU) {
            return false;
        }

        int id = item.getItemId();
        boolean result = true;
        switch (id) {

            // -- Browser context menu
            case R.id.open_context_menu_id:
            case R.id.save_link_context_menu_id:
            case R.id.copy_link_context_menu_id:
            case R.id.save_link_bookmark_id:    
                final WebView webView = getCurrentTopWebView();
                if (null == webView) {
                    result = false;
                    break;
                }
                final HashMap<String, WebView> hrefMap =
                        new HashMap<String, WebView>();
                hrefMap.put("webview", webView);
                final Message msg = mHandler.obtainMessage(
                        FOCUS_NODE_HREF, id, 0, hrefMap);
                webView.requestFocusNodeHref(msg);
                break;
            case R.layout.quick_navigation_item:
            
            	SelectItem itemValue = ((PhoneUi)mUi).getQuickNavigationScreen().getSelectItem();
            	Intent intent = new Intent();
            	intent.putExtra("selectItem", itemValue);
            	switch(item.getOrder()) {
            	case QuickNavigationScreen2.MENU_ORDER_EDIT: {
            		intent.setClass(mActivity, AddQuickNavigationPage.class);
            		break;
            	}
            	case QuickNavigationScreen2.MENU_ORDER_DELETE: {
            		intent.setClass(mActivity, DeleteQuickNavigationPage.class);
            	}
            	}
            	mActivity.startActivityForResult(intent, EDIT_QUICK_NAVIGATION);
            	break;

            default:
                // For other context menus
                result = onOptionsItemSelected(item);
        }
        return result;
    }

    /**
     * support programmatically opening the context menu
     */
    public void openContextMenu(View view) {

        mActivity.openContextMenu(view);
    }

    /**
     * programmatically open the options menu
     */
    public void openOptionsMenu() {

        mActivity.openOptionsMenu();
    }

    public boolean onMenuOpened(int featureId, Menu menu) {

        if (mOptionsMenuOpen) {

            if (mConfigChanged) {

                // We do not need to make any changes to the state of the
                // title bar, since the only thing that happened was a
                // change in orientation
                mConfigChanged = false;
            } else {
                if (!mExtendedMenuOpen) {
                    mExtendedMenuOpen = true;
                    mUi.onExtendedMenuOpened();
                } else {
                    // Switching the menu back to icon view, so show the
                    // title bar once again.
                    mExtendedMenuOpen = false;
                    mUi.onExtendedMenuClosed(isInLoad());
                }
            }
        } else {
            // The options menu is closed, so open it, and show the title
            mOptionsMenuOpen = true;
            mConfigChanged = false;
            mExtendedMenuOpen = false;
            mUi.onOptionsMenuOpened();
        }
        return true;
    }

    public void onOptionsMenuClosed(Menu menu) {

        mOptionsMenuOpen = false;
        mUi.onOptionsMenuClosed(isInLoad());
    }

    public void onContextMenuClosed(Menu menu) {

        mUi.onContextMenuClosed(menu, isInLoad());
    }

    // Helper method for getting the top window.
    @Override
    public WebView getCurrentTopWebView() {

        return mTabControl.getCurrentTopWebView();
    }

    @Override
    public WebView getCurrentWebView() {

        return mTabControl.getCurrentWebView();
    }

    /*
     * This method is called as a result of the user selecting the options
     * menu to see the download window. It shows the download window on top of
     * the current window.
     */
    void viewDownloads() {
        Intent intent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
        mActivity.startActivity(intent);
    }

    int getActionModeHeight() {

        TypedArray actionBarSizeTypedArray = mActivity.obtainStyledAttributes(
                    new int[] { android.R.attr.actionBarSize });
        int size = (int) actionBarSizeTypedArray.getDimension(0, 0f);
        actionBarSizeTypedArray.recycle();
        return size;
    }

    // action mode

    void onActionModeStarted(ActionMode mode) {

        mUi.onActionModeStarted(mode);
        mActionMode = mode;
    }

    /*
     * True if a custom ActionMode (i.e. find or select) is in use.
     */
    @Override
    public boolean isInCustomActionMode() {

        return mActionMode != null;
    }

    /*
     * End the current ActionMode.
     */
    @Override
    public void endActionMode() {

        if (mActionMode != null) {
            mActionMode.finish();
        }
    }

    /*
     * Called by find and select when they are finished.  Replace title bars
     * as necessary.
     */
    public void onActionModeFinished(ActionMode mode) {

        if (!isInCustomActionMode()) return;
        mUi.onActionModeFinished(isInLoad());
        mActionMode = null;
    }

    boolean isInLoad() {

        final Tab tab = getCurrentTab();
        return (tab != null) && tab.inPageLoad();
    }

    // bookmark handling

    /**
     * add the current page as a bookmark to the given folder id
     * @param folderId use -1 for the default folder
     * @param editExisting If true, check to see whether the site is already
     *          bookmarked, and if it is, edit that bookmark.  If false, and
     *          the site is already bookmarked, do not attempt to edit the
     *          existing bookmark.
     */
    @Override
    public Intent createBookmarkCurrentPageIntent(boolean editExisting) {

        WebView w = getCurrentTopWebView();
        if (w == null) {
            return null;
        }
        Intent i = new Intent(mActivity,
                AddBookmarkPage.class);
        i.putExtra(BrowserContract.Bookmarks.URL, w.getUrl());
        i.putExtra(BrowserContract.Bookmarks.TITLE, w.getTitle());
        String touchIconUrl = w.getTouchIconUrl();
        if (touchIconUrl != null) {
            i.putExtra(AddBookmarkPage.TOUCH_ICON_URL, touchIconUrl);
            WebSettings settings = w.getSettings();
            if (settings != null) {
                i.putExtra(AddBookmarkPage.USER_AGENT,
                        settings.getUserAgentString());
            }
        }
        i.putExtra(BrowserContract.Bookmarks.THUMBNAIL,
                createScreenshot(w, getDesiredThumbnailWidth(mActivity),
                getDesiredThumbnailHeight(mActivity)));
        i.putExtra(BrowserContract.Bookmarks.FAVICON, w.getFavicon());
        if (editExisting) {

            i.putExtra(AddBookmarkPage.CHECK_FOR_DUPE, true);
        }
        // Put the dialog at the upper right of the screen, covering the
        // star on the title bar.
        i.putExtra("gravity", Gravity.RIGHT | Gravity.TOP);
        return i;
    }

    // file chooser
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {

        mUploadHandler = new UploadHandler(this);
        mUploadHandler.openFileChooser(uploadMsg, acceptType);
    }

    // thumbnails

    /**
     * Return the desired width for thumbnail screenshots, which are stored in
     * the database, and used on the bookmarks screen.
     * @param context Context for finding out the density of the screen.
     * @return desired width for thumbnail screenshot.
     */
    static int getDesiredThumbnailWidth(Context context) {

        return context.getResources().getDimensionPixelOffset(
                R.dimen.bookmarkThumbnailWidth);
    }

    /**
     * Return the desired height for thumbnail screenshots, which are stored in
     * the database, and used on the bookmarks screen.
     * @param context Context for finding out the density of the screen.
     * @return desired height for thumbnail screenshot.
     */
    static int getDesiredThumbnailHeight(Context context) {

        return context.getResources().getDimensionPixelOffset(
                R.dimen.bookmarkThumbnailHeight);
    }

    // Save the url being loaded.
    // private String mLoadingUrl;

    // WebView.reload() will reload the last visited url, 
    // if server do not response for the current loading url.
    // (if no onPageStarted() message comes up). 
    /* void reload() {
        WebView w = getTopWindow();
        if(getTopTabLoadingUrl() != null)
            loadUrl(w, getTopTabLoadingUrl());
        else
            w.reload();
    }*/

    static Bitmap createScreenshot(WebView view, int width, int height) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter createScreenshot()");

        if (view == null || view.getContentHeight() == 0
                || view.getContentWidth() == 0) {
            return null;
        }
        // We render to a bitmap 2x the desired size so that we can then
        // re-scale it with filtering since canvas.scale doesn't filter
        // This helps reduce aliasing at the cost of being slightly blurry
        final int filter_scale = 2;
        int scaledWidth = width * filter_scale;
        int scaledHeight = height * filter_scale;
        if (sThumbnailBitmap == null || sThumbnailBitmap.getWidth() != scaledWidth
                || sThumbnailBitmap.getHeight() != scaledHeight) {
            if (sThumbnailBitmap != null) {
                sThumbnailBitmap.recycle();
                sThumbnailBitmap = null;
            }
            sThumbnailBitmap =
                    Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(sThumbnailBitmap);
        int contentWidth = view.getContentWidth();
        float overviewScale = scaledWidth / (view.getScale() * contentWidth);
        if (view instanceof BrowserWebView) {

            int dy = -((BrowserWebView)view).getTitleHeight();
            canvas.translate(0, dy * overviewScale);
        }

        canvas.scale(overviewScale, overviewScale);

        if (view instanceof BrowserWebView) {

            ((BrowserWebView)view).drawContent(canvas);
        } else {
            view.draw(canvas);
        }
        Bitmap ret = Bitmap.createScaledBitmap(sThumbnailBitmap,
                width, height, true);
        canvas.setBitmap(null);

        if(ENABLE_LOG) Log.v(LOGTAG, "quit createScreenshot()");
        return ret;
    }

    private void updateScreenshot(Tab tab) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter updateScreenshot()");

        // If this is a bookmarked site, add a screenshot to the database.
        // FIXME: Would like to make sure there is actually something to
        // draw, but the API for that (WebViewCore.pictureReady()) is not
        // currently accessible here.

        WebView view = tab.getWebView();
        if (view == null) {
            // Tab was destroyed
            return;
        }
        final String url = tab.getUrl();
        final String originalUrl = view.getOriginalUrl();

        if (TextUtils.isEmpty(url)) {
            return;
        }

        // Only update thumbnails for web urls (http(s)://), not for
        // about:, javascript:, data:, etc...
        // Unless it is a bookmarked site, then always update
        if (!Patterns.WEB_URL.matcher(url).matches() && !tab.isBookmarkedSite()&& !url.equals(mActivity.getString(R.string.homepage_base))) {
            return;
        }

        final Bitmap bm = createScreenshot(view, getDesiredThumbnailWidth(mActivity),
                getDesiredThumbnailHeight(mActivity));
        if (bm == null) {
            return;
        }

        final ContentResolver cr = mActivity.getContentResolver();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... unused) {
                Cursor cursor = null;
                try {
                    // TODO: Clean this up
                    cursor = Bookmarks.queryCombinedForUrl(cr, originalUrl, url);
                    if (cursor != null && cursor.moveToFirst()) {
                        final ByteArrayOutputStream os =
                                new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.PNG, 100, os);

                        ContentValues values = new ContentValues();
                        values.put(Images.THUMBNAIL, os.toByteArray());

                        do {
                            values.put(Images.URL, cursor.getString(0));
                            cr.update(Images.CONTENT_URI, values, null, null);
                        } while (cursor.moveToNext());
                    }
                } catch (IllegalStateException e) {

                    // Ignore
                } finally {
                    if (cursor != null) cursor.close();
                }
                return null;
            }
        }.execute();
    }

    private class Copy implements OnMenuItemClickListener {
        private CharSequence mText;

        public boolean onMenuItemClick(MenuItem item) {
            copy(mText);
            return true;
        }

        public Copy(CharSequence toCopy) {
            mText = toCopy;
        }
    }

    private static class Download implements OnMenuItemClickListener {
        private Activity mActivity;
        private String mText;
        private boolean mPrivateBrowsing;
        private static final String FALLBACK_EXTENSION = "dat";
        private static final String IMAGE_BASE_FORMAT = "yyyy-MM-dd-HH-mm-ss-";

        public boolean onMenuItemClick(MenuItem item) {
            if (DataUri.isDataUri(mText)) {
                saveDataUri();
            } else {
                DownloadHandler.onDownloadStartNoStream(mActivity, mText, null,
                        null, null, mPrivateBrowsing);
            }
            return true;
        }

        public Download(Activity activity, String toDownload, boolean privateBrowsing) {
            mActivity = activity;
            mText = toDownload;
            mPrivateBrowsing = privateBrowsing;
        }

        /**
         * Treats mText as a data URI and writes its contents to a file
         * based on the current time.
         */
        private void saveDataUri() {
            FileOutputStream outputStream = null;
            try {
                DataUri uri = new DataUri(mText);
                File target = getTarget(uri);
                outputStream = new FileOutputStream(target);
                if(uri.getData()==null){
                    Toast.makeText(mActivity, R.string.base_decode_failed, Toast.LENGTH_SHORT).show();
                    return;
                }
                outputStream.write(uri.getData());
                final DownloadManager manager =
                        (DownloadManager) mActivity.getSystemService(Context.DOWNLOAD_SERVICE);
                 manager.addCompletedDownload(target.getName(),
                        mActivity.getTitle().toString(), false,
                        uri.getMimeType(), target.getAbsolutePath(),
                        (long)uri.getData().length, false);
            } catch (IOException e) {

                Log.e(LOGTAG, "Could not save data URL");
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {

                        // ignore close errors
                    }
                }
            }
        }

        /**
         * Creates a File based on the current time stamp and uses
         * the mime type of the DataUri to get the extension.
         */
        private File getTarget(DataUri uri) throws IOException {

            File dir = mActivity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            DateFormat format = new SimpleDateFormat(IMAGE_BASE_FORMAT);
            String nameBase = format.format(new Date());
            String mimeType = uri.getMimeType();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            String extension = mimeTypeMap.getExtensionFromMimeType(mimeType);
            if (extension == null) {
                Log.w(LOGTAG, "Unknown mime type in data URI" + mimeType);
                extension = FALLBACK_EXTENSION;
            }
            extension = "." + extension; // createTempFile needs the '.'
            File targetFile = File.createTempFile(nameBase, extension, dir);
            return targetFile;
        }
    }

    private static class SelectText implements OnMenuItemClickListener {
        private WebView mWebView;

        public boolean onMenuItemClick(MenuItem item) {

            if (mWebView != null) {
                return mWebView.selectText();
            }
            return false;
        }

        public SelectText(WebView webView) {

            mWebView = webView;
        }

    }

    /********************** TODO: UI stuff *****************************/

    // these methods have been copied, they still need to be cleaned up

    /****************** tabs ***************************************************/

    // basic tab interactions:

    // it is assumed that tabcontrol already knows about the tab
    protected void addTab(Tab tab) {
        mUi.addTab(tab);
    }

    protected void removeTab(Tab tab) {
        mUi.removeTab(tab);
        mTabControl.removeTab(tab);
        mCrashRecoveryHandler.backupState();
        ((BaseUi)mUi).getMainMenuControlPhone().populateMenu();
    }

    @Override
    public void setActiveTab(Tab tab) {
        // monkey protection against delayed start
        if (tab != null) {
            mTabControl.setCurrentTab(tab);
            // the tab is guaranteed to have a webview after setCurrentTab
            mUi.setActiveTab(tab);
        }
    }

    protected void closeEmptyTab() {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter closeEmptyTab()");

        Tab current = mTabControl.getCurrentTab();
        if (current != null
                && current.getWebView().copyBackForwardList().getSize() == 0) {
            closeCurrentTab();
        }
    }

    protected void reuseTab(Tab appTab, UrlData urlData) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter reuseTab()");

        // Dismiss the subwindow if applicable.
        dismissSubWindow(appTab);
        // Since we might kill the WebView, remove it from the
        // content view first.
        mUi.detachTab(appTab);
        // Recreate the main WebView after destroying the old one.
        mTabControl.recreateWebView(appTab);
        // TODO: analyze why the remove and add are necessary
        mUi.attachTab(appTab);
        if (mTabControl.getCurrentTab() != appTab) {
            switchToTab(appTab);
            loadUrlDataIn(appTab, urlData);
        } else {
            // If the tab was the current tab, we have to attach
            // it to the view system again.
            setActiveTab(appTab);
            loadUrlDataIn(appTab, urlData);
        }
    }

    // Remove the sub window if it exists. Also called by TabControl when the
    // user clicks the 'X' to dismiss a sub window.
    public void dismissSubWindow(Tab tab) {
        removeSubWindow(tab);
        // dismiss the subwindow. This will destroy the WebView.
        tab.dismissSubWindow();
        WebView wv = getCurrentTopWebView();
        if (wv != null) {
            wv.requestFocus();
        }
    }

    @Override
    public void removeSubWindow(Tab t) {
        if (t.getSubWebView() != null) {
            mUi.removeSubWindow(t.getSubViewContainer());
        }
    }

    @Override
    public void attachSubWindow(Tab tab) {
        if (tab.getSubWebView() != null) {
            mUi.attachSubWindow(tab.getSubViewContainer());
            getCurrentTopWebView().requestFocus();
        }
    }

    private Tab showPreloadedTab(final UrlData urlData) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter showPreloadedTab()");

        if (!urlData.isPreloaded()) {
            return null;
        }
        final PreloadedTabControl tabControl = urlData.getPreloadedTab();
        final String sbQuery = urlData.getSearchBoxQueryToSubmit();
        if (sbQuery != null) {
            if (!tabControl.searchBoxSubmit(sbQuery, urlData.mUrl, urlData.mHeaders)) {
                // Could not submit query. Fallback to regular tab creation
                tabControl.destroy();
                return null;
            }
        }
        // check tab count and make room for new tab
        if (!mTabControl.canCreateNewTab()) {
            Tab leastUsed = mTabControl.getLeastUsedTab(getCurrentTab());
            if (leastUsed != null) {
                closeTab(leastUsed);
            }
        }
        Tab t = tabControl.getTab();
        t.refreshIdAfterPreload();
        mTabControl.addPreloadedTab(t);
        addTab(t);
        setActiveTab(t);
        return t;
    }

    // open a non inconito tab with the given url data
    // and set as active tab
    public Tab openTab(UrlData urlData) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter openTab()");
        ((BaseUi)mUi).hideHomePage();
        Tab tab = showPreloadedTab(urlData);
        if (tab == null) {
            tab = createNewTab(false, true, true);
            if ((tab != null) && !urlData.isEmpty()) {
                loadUrlDataIn(tab, urlData);
            }
        }
        return tab;
    }
    public static boolean homepage  = false;
    @Override
    public Tab openTabToHomePage() {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter openTabToHomePage()");
            ((BaseUi)mUi).hideHomePage();
            return openTab(mSettings.getHomePage(), false, true, false);
    }
    
    public Tab openTabToHomePage2() {
            homepage = true;
            ((BaseUi)mUi).SetSanyaHomepage(); 
            ((BaseUi)mUi).qucikNLRequestFoucs();
            Tab tab = getHomeTab();
            switchToTab(tab);
            ((BaseUi)mUi).showHomePage();
            return tab;
    }
    private Tab mHomeTab;
    
    private Tab getHomeTab() {
        if (mHomeTab == null) {
            mHomeTab = mTabControl.createNewTab(false);
            addTab(mHomeTab);
            mHomeTab.setHomeTab(true);
   //         mHomeTab.loadUrl("", null);
            setActiveTab(mHomeTab);
        }
        return mHomeTab;
    }
    
    public boolean isHomeTab(Tab tab) {
        return mHomeTab != null && mHomeTab.equals(tab);
    }

    @Override
    public Tab openIncognitoTab() {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter openIncognitoTab()");

        return openTab(INCOGNITO_URI, true, true, false);
    }

    @Override
    public Tab openTab(String url, boolean incognito, boolean setActive,
            boolean useCurrent) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter openTab()");

        return openTab(url, incognito, setActive, useCurrent, null);
    }

    @Override
    public Tab openTab(String url, Tab parent, boolean setActive,
            boolean useCurrent) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter openTab()");

        return openTab(url, (parent != null) && parent.isPrivateBrowsingEnabled(),
                setActive, useCurrent, parent);
    }
    
    public Tab openTab(String url, boolean incognito, boolean setActive,
            boolean useCurrent, Tab parent) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter openTab()");

        Tab tab = createNewTab(incognito, setActive, useCurrent);
        if (tab != null) {
            if (parent != null && parent != tab) {
                parent.addChildTab(tab);
            }
            if (url != null) {
                loadUrl(tab, url);
            }
        }
        return tab;
    }

    // this method will attempt to create a new tab
    // incognito: private browsing tab
    // setActive: ste tab as current tab
    // useCurrent: if no new tab can be created, return current tab
    private Tab createNewTab(boolean incognito, boolean setActive,
            boolean useCurrent) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter createNewTab()");

        Tab tab = null;
        if (mTabControl.canCreateNewTab()) {
            tab = mTabControl.createNewTab(incognito);
            addTab(tab);
            if (setActive) {

                setActiveTab(tab);
            }
        } else {
            if (useCurrent) {

                tab = mTabControl.getCurrentTab();
                reuseTab(tab, null);
            } else {
                mUi.showMaxTabsWarning();
            }
        }
        ((BaseUi)mUi).getMainMenuControlPhone().populateMenu();
        return tab;
    }

    @Override
    public SnapshotTab createNewSnapshotTab(long snapshotId, boolean setActive) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter createNewSnapshotTab()");

        SnapshotTab tab = null;
        if (mTabControl.canCreateNewTab()) {
            tab = mTabControl.createSnapshotTab(snapshotId);
            addTab(tab);
            if (setActive) {
                if(Controller.homepage == true||Controller.homepageBack == true){
                    homepage = false;
                    homepageBack = false;
                    ((BaseUi)mUi).hideHomePage();
                }
                setActiveTab(tab);
            }
        } else {
            mUi.showMaxTabsWarning();
        }
        ((BaseUi)mUi).getMainMenuControlPhone().populateMenu();
        return tab;
    }

    /**
     * @param tab the tab to switch to
     * @return boolean True if we successfully switched to a different tab.  If
     *                 the indexth tab is null, or if that tab is the same as
     *                 the current one, return false.
     */
    @Override
    public boolean switchToTab(Tab tab) {
        Tab currentTab = mTabControl.getCurrentTab();
        if (tab == null || tab == currentTab) {
            return false;
        }
        setActiveTab(tab);
        return true;
    }

    @Override
    public void closeCurrentTab() {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter closeCurrentTab()");

        closeCurrentTab(false);
    }

    protected void closeCurrentTab(boolean andQuit) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter closeCurrentTab()");

        if(mTabControl == null)
            return;
        
        if (mTabControl.getTabCount() == 1) {
            if(ENABLE_LOG) Log.v(LOGTAG, "closeCurrentTab() finish");

            mCrashRecoveryHandler.clearState();
            mTabControl.removeTab(getCurrentTab());
            mActivity.finish();
            return;
        }
        final Tab current = mTabControl.getCurrentTab();
        final int pos = mTabControl.getCurrentPosition();
        Tab newTab = null;
        if(current!=null){
            newTab = current.getParent();
        }    
        if (newTab == null) {
            newTab = mTabControl.getTab(pos + 1);
            if (newTab == null) {
                newTab = mTabControl.getTab(pos - 1);
            }
        }
        if (andQuit) {
            if(ENABLE_LOG) Log.v(LOGTAG, "closeCurrentTab() quit");
            mTabControl.setCurrentTab(newTab);
            closeTab(current);
        } else if (switchToTab(newTab)) {
            if(ENABLE_LOG) Log.v(LOGTAG, "closeCurrentTab() no quit");
            // Close window
            closeTab(current);
        }
    }

    /**
     * Close the tab, remove its associated title bar, and adjust mTabControl's
     * current tab to a valid value.
     */
    @Override
    public void closeTab(Tab tab) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter closeTab()");

        if (tab == mTabControl.getCurrentTab()) {
            closeCurrentTab();
        } else {
            removeTab(tab);
        }
    }

    // Called when loading from context menu or LOAD_URL message
    protected void loadUrlFromContext(String url) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter loadUrlFromContext(): " + url);

        Tab tab = getCurrentTab();
        WebView view = tab != null ? tab.getWebView() : null;
        // In case the user enters nothing.
        if (url != null && url.length() != 0 && tab != null && view != null) {
            url = UrlUtils.smartUrlFilter(url);
            if (!view.getWebViewClient().shouldOverrideUrlLoading(view, url)) {
                loadUrl(tab, url);
            }
        }
    }

    /**
     * Load the URL into the given WebView and update the title bar
     * to reflect the new load.  Call this instead of WebView.loadUrl
     * directly.
     * @param view The WebView used to load url.
     * @param url The URL to load.
     */
    @Override
    public void loadUrl(Tab tab, String url) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter loadUrl(): " + url);

        loadUrl(tab, url, null);
    }


    protected void loadUrl(Tab tab, String url, Map<String, String> headers) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter loadUrl() 3: " + url);
        mPageDownloader.reset();
        if(Controller.homepage == true||Controller.homepageBack == true){
            homepage = false;
            homepageBack = false;
            ((BaseUi)mUi).hideHomePage();
        }
        
        boolean flip = mSettings.getSharedPreferences().getBoolean(PreferenceKeys.PREF_PAGE_FLIP, false);
        if(flip && !pageFilpState){
            addPageFlip();
        }
        Tab current = getCurrentTab();
        if(current==null){
        	return;
        }
        ((BaseUi)mUi).getMainMenuControlPhone().setBackWardEnabledTrue();
        if(current.canGoForward()){
            ((BaseUi)mUi).getMainMenuControlPhone().setForwardEnabledTrue();
        }else{
            ((BaseUi)mUi).getMainMenuControlPhone().setForwardEnabledFalse();
        }

        if(BrowserSettings.CMCC_PLATFORM) {

            // Clear pending load url messages
            mHandler.removeMessages(LOAD_URL_DELAY);
            // mHandler.removeMessages(RECOVERY_DELAY);

            // For Android handset, http://www.126.com/ is redirected to 
            // http://smart.mail.126.com/ 
            // But the UA for CMCC products could not be recogonized
            // by 126. Then the page goes to http://m.mail.163.com/auth/login.s
            // ?domain=126.com&fromHost=m.mail.126.com which does not keep 
            // username and password by adding attribute "autocomplete=off"
            // It fails CMCC browser test 15.1.
            // Redirect it manually...
            if(mUrlRedirectMap.containsKey(url)) {
                url = mUrlRedirectMap.get(url);
                if(ENABLE_LOG) Log.v(LOGTAG, 
                        "loadUrl() 3, url redirect to: " + url);
            }
        }

        if (tab == null)
            return;

        // FIXME: Do NOT refresh UI when network is NOT up
        // dismissSubWindow(tab);

        if(mActivityPaused == true && 
                !url.startsWith("javascript:")) { // For cts testTabExhaustion issue
            if(LOGV_ENABLED) Log.v(LOGTAG, 
                    "Activity is paused, loadUrl delay 500ms: " + url);

            Object[] args = new Object[3];
            args[0] = tab;
            args[1] = url;
            args[2] = headers;
            mHandler.sendMessageDelayed(Message.obtain(
                        null, LOAD_URL_DELAY_BECKGROUND, args), RETRY_TIMEOUT);
            return;
        }

        if(!BrowserSettings.CMCC_PLATFORM) {
            dismissSubWindow(tab);
            tab.loadUrl(url, headers);
            mUi.onProgressChanged(tab);
        } else {        // CMCC_PLATFORM

            // In case of no sim card and no wifi
            if(!canAccessNetwork()) {
                if(LOGV_ENABLED) Log.v(LOGTAG, "loadUrl, " + 
                        "can not access network, " + 
                        "setNeedRestartNetwork:true");
                mApnHandler.setNeedRestartNetwork(true);
                Toast.makeText(mActivity, R.string.loadSuspended,
                        Toast.LENGTH_LONG).show();

                // just display the error page
                /*
                dismissSubWindow(tab);
                tab.loadUrl(url, headers);
                mUi.onProgressChanged(tab);
                */
                return;
            }
            if(mApnHandler.needRestartNetwork()) {
                mApnHandler.enableApnConnection(
                        mApnHandler.apnSettingsToApnName(
                            mSettings.getDataConnection()));

                if(LOGV_ENABLED) Log.v(LOGTAG, "loadUrl," + 
                        " setNeedRestartNetwork:false");
                mApnHandler.setNeedRestartNetwork(false);
            }

            if(mNetworkHandler.isNetworkUp()) {
                if(LOGV_ENABLED) Log.v(LOGTAG, "loadUrl: " + url);

                dismissSubWindow(tab);

                mRetryCount = 0;
                tab.loadUrl(url, headers);
                mUi.onProgressChanged(tab);
            } else {
                // Waiting for data network connection
                if(mRetryCount == 2) {
                    Toast.makeText(mActivity, R.string.download_pending_network,
                            Toast.LENGTH_LONG).show();
                }
                if(mRetryCount++ >= MAX_RETRY_COUNT) {
                    if(LOGV_ENABLED) Log.v(LOGTAG, "loadUrl stop retrying");

                    Toast.makeText(mActivity, R.string.loadSuspended,
                            Toast.LENGTH_LONG).show();
                    mRetryCount = 0;
                    mApnHandler.setNeedRestartNetwork(true);
                    return;
                }

                if(LOGV_ENABLED) Log.v(LOGTAG, "loadUrl delay 500ms: " + url);

                // (Tab tab, String url, Map<String, String> headers) 
                Object[] args = new Object[3];
                args[0] = tab;
                args[1] = url;
                args[2] = headers;
                mHandler.sendMessageDelayed(Message.obtain(
                            null, LOAD_URL_DELAY, args), RETRY_TIMEOUT);
            }
        }
    }

    /**
     * Load UrlData into a Tab and update the title bar to reflect the new
     * load.  Call this instead of UrlData.loadIn directly.
     * @param t The Tab used to load.
     * @param data The UrlData being loaded.
     */
    protected void loadUrlDataIn(Tab t, UrlData data) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter loadUrlDataIn()");

        if (data != null) {
            if (data.mVoiceIntent != null) {
                t.activateVoiceSearchMode(data.mVoiceIntent);
            } else if (data.isPreloaded()) {
                // this isn't called for preloaded tabs
            } else {
                // If user input special url, send it to android
                if(UrlHandler.isSpecialUrl(data.mUrl)) {
                    mUrlHandler.handleSpecialUrl(data.mUrl);
                } else {
                loadUrl(t, data.mUrl, data.mHeaders);
                }
            }
        }
    }

    @Override
    public void onUserCanceledSsl(Tab tab) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter onUserCanceledSsl()");

        // TODO: Figure out the "right" behavior
        if (tab.canGoBack()) {
            tab.goBack();
        } else {
            tab.loadUrl(mSettings.getHomePage(), null);
        }
    }

    public static boolean homepageBack = false;
    private AlertDialog mExitDlg;

    void goBackOnePageOrQuit() {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter goBackOnePageOrQuit()");

        final Tab current = mTabControl.getCurrentTab();
        if (current == null) {
            if(ENABLE_LOG) 
                Log.v(LOGTAG, "enter goBackOnePageOrQuit(), current is null");

            mExitDlg = new AlertDialog.Builder(mActivity)
                .setTitle(R.string.exit_browser_title)
                .setMessage(R.string.exit_browser)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            mSettings.getSharedPreferences().edit().putString("country", "").commit();
                            mActivity.finish();
                            mExitDlg = null;
                        }
                    })
            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mExitDlg = null;
                    }
                })
            .show();

            /*
             * Instead of finishing the activity, simply push this to the back
             * of the stack and let ActivityManager to choose the foreground
             * activity. As BrowserActivity is singleTask, it will be always the
             * root of the task. So we can use either true or false for
             * moveTaskToBack().
             */
            // mActivity.moveTaskToBack(true);
            return;
        }
        if (current.canGoBack()) {
            if(ENABLE_LOG) 
                Log.v(LOGTAG, "enter goBackOnePageOrQuit(), current.goBack()");

            if(!canAccessNetwork()) {
                mApnHandler.setNeedRestartNetwork(true);
                Toast.makeText(mActivity, R.string.loadSuspended,
                        Toast.LENGTH_LONG).show();
                return;
            }

            current.goBack();
            ((BaseUi)mUi).getMainMenuControlPhone().setBackWardEnabledTrue();
            if(getCurrentTab().canGoForward()){
                ((BaseUi)mUi).getMainMenuControlPhone().setForwardEnabledTrue();
            }else{
                ((BaseUi)mUi).getMainMenuControlPhone().setForwardEnabledFalse();
            }
        } else {
            // Check to see if we are closing a window that was created by
            // another window. If so, we switch back to that window.
            Tab parent = current.getParent();
            if (parent != null) {
                if(ENABLE_LOG) 
                    Log.v(LOGTAG, "enter goBackOnePageOrQuit(), parent not null");

                if(!canAccessNetwork()) {
                    mApnHandler.setNeedRestartNetwork(true);
                    Toast.makeText(mActivity, R.string.loadSuspended,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                switchToTab(parent);
                // Now we close the other tab
                closeTab(current);
                
                ((BaseUi)mUi).getMainMenuControlPhone().setBackWardEnabledTrue();
                if(parent.canGoForward()){
                    ((BaseUi)mUi).getMainMenuControlPhone().setForwardEnabledTrue();
                }else{
                    ((BaseUi)mUi).getMainMenuControlPhone().setForwardEnabledFalse();
                }
                // In case browser being launched, going background,
                // then other activity triggering url loading.
                // There are tabs in background, hide browser.
                // mActivity.moveTaskToBack(true);
                // return;
            } else {
                if(ENABLE_LOG) 
                    Log.v(LOGTAG, "enter goBackOnePageOrQuit(), parent null");
                if(homepage == false) {
                    /* if(!canAccessNetwork()) {
                        mApnHandler.setNeedRestartNetwork(true);
                        Toast.makeText(mActivity, R.string.loadSuspended,
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    */

                    boolean flip = mSettings.getSharedPreferences().getBoolean(PreferenceKeys.PREF_PAGE_FLIP, false);
                    if(flip){
                        hidePageFlip();
                    }
                    homepageBack = true;
                    homepage = true;
                    mHandler.removeMessages(LOAD_URL_DELAY);
                    mHandler.removeMessages(LOAD_URL_DELAY_BECKGROUND);
                    // mHandler.removeMessages(RECOVERY_DELAY);
                    ((BaseUi)mUi).qucikNLRequestFoucs();
                    ((BaseUi)mUi).SetSanyaHomepage(); 
                    ((BaseUi)mUi).showHomePage();
                    ((BaseUi)mUi).qucikNLRequestFoucs();

                }else{
                    mExitDlg = new AlertDialog.Builder(mActivity)
                        .setTitle(R.string.exit_browser_title)
                        .setMessage(R.string.exit_browser)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    if ((current.getAppId() != null) || current.closeOnBack()) {
                                        if(ENABLE_LOG) 
                                        Log.v(LOGTAG, "enter goBackOnePageOrQuit(), closeCurrentTab");
                                        closeCurrentTab(true);
                                    }
                                    mSettings.getSharedPreferences().edit().putString("country", "").commit();
                                    mActivity.finish();
                                    mExitDlg = null;
                                }
                            })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mExitDlg = null;
                            }
                        })
                    .show();
                }
                /*
                 * Instead of finishing the activity, simply push this to the back
                 * of the stack and let ActivityManager to choose the foreground
                 * activity. As BrowserActivity is singleTask, it will be always the
                 * root of the task. So we can use either true or false for
                 * moveTaskToBack().
                 */
                // mActivity.moveTaskToBack(true);
            }
        }
    }

    /**
     * Feed the previously stored results strings to the BrowserProvider so that
     * the SearchDialog will show them instead of the standard searches.
     * @param result String to show on the editable line of the SearchDialog.
     */
    @Override
    public void showVoiceSearchResults(String result) {

        ContentProviderClient client = mActivity.getContentResolver()
                .acquireContentProviderClient(Browser.BOOKMARKS_URI);
        ContentProvider prov = client.getLocalContentProvider();
        BrowserProvider bp = (BrowserProvider) prov;
        bp.setQueryResults(mTabControl.getCurrentTab().getVoiceSearchResults());
        client.release();

        Bundle bundle = createGoogleSearchSourceBundle(
                GOOGLE_SEARCH_SOURCE_SEARCHKEY);
        bundle.putBoolean(SearchManager.CONTEXT_IS_VOICE, true);
        startSearch(result, false, bundle, false);
    }

    private void startSearch(String initialQuery, boolean selectInitialQuery,
            Bundle appSearchData, boolean globalSearch) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter startSearch()");

        if (appSearchData == null) {
            appSearchData = createGoogleSearchSourceBundle(
                    GOOGLE_SEARCH_SOURCE_TYPE);
        }

        SearchEngine searchEngine = mSettings.getSearchEngine();
        if (searchEngine != null && !searchEngine.supportsVoiceSearch()) {
            appSearchData.putBoolean(SearchManager.DISABLE_VOICE_SEARCH, true);
        }
        mActivity.startSearch(initialQuery, selectInitialQuery, appSearchData,
                globalSearch);
    }

    private Bundle createGoogleSearchSourceBundle(String source) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter createGoogleSearchSourceBundle()");

        Bundle bundle = new Bundle();
        bundle.putString(Search.SOURCE, source);
        return bundle;
    }

    /**
     * helper method for key handler
     * returns the current tab if it can't advance
     */
    private Tab getNextTab() {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter getNextTab()");

        return mTabControl.getTab(Math.min(mTabControl.getTabCount() - 1,
                mTabControl.getCurrentPosition() + 1));
    }

    /**
     * helper method for key handler
     * returns the current tab if it can't advance
     */
    private Tab getPrevTab() {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter getPrevTab()");

        return  mTabControl.getTab(Math.max(0,
                mTabControl.getCurrentPosition() - 1));
    }

    /**
     * handle key events in browser
     *
     * @param keyCode
     * @param event
     * @return true if handled, false to pass to super
     */
    boolean onKeyDown(int keyCode, KeyEvent event) {

    	if(KeyEvent.KEYCODE_DPAD_CENTER == keyCode || KeyEvent.KEYCODE_DPAD_RIGHT == keyCode || KeyEvent.KEYCODE_DPAD_DOWN == keyCode
    	        || KeyEvent.KEYCODE_DPAD_LEFT == keyCode || KeyEvent.KEYCODE_DPAD_UP == keyCode){
    		Log.v(LOGTAG, "ignore some unuseful keys");
    		return true;
    	}
        boolean noModifiers = event.hasNoModifiers();
        // Even if MENU is already held down, we need to call to super to open
        // the IME on long press.
        if (!noModifiers
                && ((KeyEvent.KEYCODE_MENU == keyCode)
                        || (KeyEvent.KEYCODE_CTRL_LEFT == keyCode)
                        || (KeyEvent.KEYCODE_CTRL_RIGHT == keyCode))) {
            mMenuIsDown = true;
            return false;
        }

        WebView webView = getCurrentTopWebView();
        Tab tab = getCurrentTab();
        if (webView == null || tab == null) return false;

        boolean ctrl = event.hasModifiers(KeyEvent.META_CTRL_ON);
        boolean shift = event.hasModifiers(KeyEvent.META_SHIFT_ON);

        switch(keyCode) {

            case KeyEvent.KEYCODE_TAB:
                if (event.isCtrlPressed()) {
                    if (event.isShiftPressed()) {
                        // prev tab
                        switchToTab(getPrevTab());
                    } else {
                        // next tab
                        switchToTab(getNextTab());
                    }
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_SPACE:
                // WebView/WebTextView handle the keys in the KeyDown. As
                // the Activity's shortcut keys are only handled when WebView
                // doesn't, have to do it in onKeyDown instead of onKeyUp.
                if (shift) {

                    pageUp();
                } else if (noModifiers) {

                    pageDown();
                }
                return true;
            case KeyEvent.KEYCODE_BACK:
                if (!noModifiers) break;
                event.startTracking();
                return true;
            case KeyEvent.KEYCODE_FORWARD:
                if (!noModifiers) break;
                tab.goForward();
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (ctrl) {

                    tab.goBack();
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (ctrl) {

                    tab.goForward();
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_A:
                if (ctrl) {

                    webView.selectAll();
                    return true;
                }
                break;
//          case KeyEvent.KEYCODE_B:    // menu
            case KeyEvent.KEYCODE_C:
                if (ctrl) {

                    webView.copySelection();
                    return true;
                }
                break;
//          case KeyEvent.KEYCODE_D:    // menu
//          case KeyEvent.KEYCODE_E:    // in Chrome: puts '?' in URL bar
//          case KeyEvent.KEYCODE_F:    // menu
//          case KeyEvent.KEYCODE_G:    // in Chrome: finds next match
//          case KeyEvent.KEYCODE_H:    // menu
//          case KeyEvent.KEYCODE_I:    // unused
//          case KeyEvent.KEYCODE_J:    // menu
//          case KeyEvent.KEYCODE_K:    // in Chrome: puts '?' in URL bar
//          case KeyEvent.KEYCODE_L:    // menu
//          case KeyEvent.KEYCODE_M:    // unused
//          case KeyEvent.KEYCODE_N:    // in Chrome: new window
//          case KeyEvent.KEYCODE_O:    // in Chrome: open file
//          case KeyEvent.KEYCODE_P:    // in Chrome: print page
//          case KeyEvent.KEYCODE_Q:    // unused
//          case KeyEvent.KEYCODE_R:
//          case KeyEvent.KEYCODE_S:    // in Chrome: saves page
            case KeyEvent.KEYCODE_T:
                // we can't use the ctrl/shift flags, they check for
                // exclusive use of a modifier
                if (event.isCtrlPressed()) {
                    if (event.isShiftPressed()) {
                        openIncognitoTab();
                    } else {
                        openTabToHomePage();
                    }
                    return true;
                }
                break;
//          case KeyEvent.KEYCODE_U:    // in Chrome: opens source of page
//          case KeyEvent.KEYCODE_V:    // text view intercepts to paste
//          case KeyEvent.KEYCODE_W:    // menu
//          case KeyEvent.KEYCODE_X:    // text view intercepts to cut
//          case KeyEvent.KEYCODE_Y:    // unused
//          case KeyEvent.KEYCODE_Z:    // unused
        }
        // it is a regular key and webview is not null
         return mUi.dispatchKey(keyCode, event);
    }

    boolean onKeyLongPress(int keyCode, KeyEvent event) {

        switch(keyCode) {

        case KeyEvent.KEYCODE_BACK:
            if (mUi.isWebShowing()) {
                bookmarksOrHistoryPicker(ComboViews.History);
                return true;
            }
            break;
        }
        return false;
    }

    boolean onKeyUp(int keyCode, KeyEvent event) {

        if (KeyEvent.KEYCODE_MENU == keyCode && !((PhoneUi)mUi).showingNavScreen()) {
            mMenuIsDown = false;
            if(BrowserSettings.CMCC_PLATFORM && BrowserSettings.CMCC_PLAN_ONE){
                PopupMenu mPopup = new PopupMenu(mActivity, mPie);
                mPopup.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
                        
                        public boolean onMenuItemClick(MenuItem item) {
                            // TODO Auto-generated method stub
                            return onOptionsItemSelected(item);
                        }
                    });
                onCreateOptionsMenu(mPopup.getMenu());
                onPrepareOptionsMenu(mPopup.getMenu());
                mPopup.show();
            } else {
                if (tabMenu != null) { 
                    if (tabMenu.isShowing()){ 
                        tabMenu.dismiss(); 
                    }
                    else { 
                        tabMenu.showAtLocation(mPie, 
                                Gravity.BOTTOM, 0, mPie.getHeight()); 
                    } 
                } 
            }
            return true;
//            if (event.isTracking() && !event.isCanceled()) {
//                return onMenuKey();
//            }
        }else if(KeyEvent.KEYCODE_MENU == keyCode){
            return true;
        }
        if (!event.hasNoModifiers()) return false;
        switch(keyCode) {

            case KeyEvent.KEYCODE_BACK:
                if (event.isTracking() && !event.isCanceled()) {
                    onBackKey();
                    return true;
                }
                break;
        }
        return false;
    }

    public boolean isMenuDown() {

        return mMenuIsDown;
    }

    public void setupAutoFill(Message message) {

        // Open the settings activity at the AutoFill profile fragment so that
        // the user can create a new profile. When they return, we will dispatch
        // the message so that we can autofill the form using their new profile.
        Intent intent = new Intent(mActivity, BrowserPreferencesPage.class);
        intent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT,
                AutoFillSettingsFragment.class.getName());
        mAutoFillSetupMessage = message;
        mActivity.startActivityForResult(intent, AUTOFILL_SETUP);
    }

    public boolean onSearchRequested() {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter onSearchRequested()");

        mUi.editUrl(false);
        return true;
    }

    @Override
    public boolean shouldCaptureThumbnails() {
        return mUi.shouldCaptureThumbnails();
    }

    @Override
    public void setBlockEvents(boolean block) {

        mBlockEvents = block;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return mBlockEvents;
    }

    public boolean blockingEvents() {
        return mBlockEvents;
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent event) {

        return mBlockEvents;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {

        return mBlockEvents;
    }

    public boolean dispatchTrackballEvent(MotionEvent ev) {

        // return mBlockEvents;
        // XXX: Monkey test
        return true;
    }

    public boolean dispatchGenericMotionEvent(MotionEvent ev) {

        return mBlockEvents;
    }
    

}
