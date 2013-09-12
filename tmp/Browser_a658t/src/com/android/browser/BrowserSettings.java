/*
 * Copyright (C) 2011 The Android Open Source Project
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

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.net.NetworkInfo;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Message;
import android.os.SystemProperties;
import android.preference.PreferenceManager;
import android.provider.Browser;
import android.provider.Settings;
import android.provider.Telephony;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.WebIconDatabase;
import android.webkit.WebSettings;
import android.webkit.WebSettings.AutoFillProfile;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewDatabase;

import com.android.browser.homepages.HomeProvider;
import com.android.browser.provider.BrowserProvider;
import com.android.browser.provider.BrowserProvider2;
import com.android.browser.search.SearchEngine;
import com.android.browser.search.SearchEngines;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.WeakHashMap;

/**
 * Class for managing settings
 */
public class BrowserSettings implements OnSharedPreferenceChangeListener,
        PreferenceKeys {

    private static final String LOGTAG = "BrowserSettings";
    private static final String LOGTAG_SYNC = "BrowserSettings_sync";
    
    // TODO: Do something with this UserAgent stuff
    private static final String DESKTOP_USERAGENT = "Mozilla/5.0 (X11; " +
        "Linux x86_64) AppleWebKit/534.24 (KHTML, like Gecko) " +
        "Chrome/11.0.696.34 Safari/534.24";

    private static final String IPHONE_USERAGENT = "Mozilla/5.0 (iPhone; U; " +
        "CPU iPhone OS 4_0 like Mac OS X; en-us) AppleWebKit/532.9 " +
        "(KHTML, like Gecko) Version/4.0.5 Mobile/8A293 Safari/6531.22.7";

    private static final String IPAD_USERAGENT = "Mozilla/5.0 (iPad; U; " +
        "CPU OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 " +
        "(KHTML, like Gecko) Version/4.0.4 Mobile/7B367 Safari/531.21.10";

    private static final String FROYO_USERAGENT = "Mozilla/5.0 (Linux; U; " +
        "Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 " +
        "(KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

    private static final String HONEYCOMB_USERAGENT = "Mozilla/5.0 (Linux; U; " +
        "Android 3.1; en-us; Xoom Build/HMJ25) AppleWebKit/534.13 " +
        "(KHTML, like Gecko) Version/4.0 Safari/534.13";
    
    private static final String ANDROID_DEFAULT_USERAGENT = 
            "Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; Build/Android_V01.01) " + 
            "AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";

    private static final String USER_AGENTS[] = { null,
            DESKTOP_USERAGENT,
            IPHONE_USERAGENT,
            IPAD_USERAGENT,
            FROYO_USERAGENT,
            HONEYCOMB_USERAGENT,
    };

    // The minimum min font size
    // Aka, the lower bounds for the min font size range
    // which is 1:5..24
    private static final int MIN_FONT_SIZE_OFFSET = 5;
    // The initial value in the text zoom range
    // This is what represents 100% in the SeekBarPreference range
    private static final int TEXT_ZOOM_START_VAL = 10;
    // The size of a single step in the text zoom range, in percent
    private static final int TEXT_ZOOM_STEP = 5;
    // The initial value in the double tap zoom range
    // This is what represents 100% in the SeekBarPreference range
    private static final int DOUBLE_TAP_ZOOM_START_VAL = 5;
    // The size of a single step in the double tap zoom range, in percent
    private static final int DOUBLE_TAP_ZOOM_STEP = 5;

    private static BrowserSettings sInstance;

    private Context mContext;
    private SharedPreferences mPrefs;
    private LinkedList<WeakReference<WebSettings>> mManagedSettings;
    private Controller mController;
    private WebStorageSizeManager mWebStorageSizeManager;
    private boolean mForceDefaultUserAgent = false;
    private AutofillHandler mAutofillHandler;
    private WeakHashMap<WebSettings, String> mCustomUserAgents;
    private static boolean sInitialized = false;
    private boolean mNeedsSharedSync = true;
    private float mFontSizeMult = 1.0f;

    // Cached values
    private int mPageCacheCapacity = 1;
    private String mAppCachePath;

    // Cached settings
    private SearchEngine mSearchEngine;

    private static String sFactoryResetUrl;

    public static void initialize(final Context context) {
        sInstance = new BrowserSettings(context);
    }

    public static BrowserSettings getInstance() {
        return sInstance;
    }

    public SharedPreferences getSharedPreferences(){
    	return mPrefs;
    }
    private BrowserSettings(Context context) {
        mContext = context.getApplicationContext();

        // FIXME: Load the defaults from the xml
        // This call is TOO SLOW, need to manually keep the defaults
        // in sync
        PreferenceManager.setDefaultValues(mContext, 
                R.xml.advanced_preferences, false);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mAutofillHandler = new AutofillHandler(mContext);
        mManagedSettings = new LinkedList<WeakReference<WebSettings>>();
        mCustomUserAgents = new WeakHashMap<WebSettings, String>();
        mAutofillHandler.asyncLoadFromDb();
        BackgroundHandler.execute(mSetup);
    }

    public void setController(Controller controller) {
        mController = controller;
        if (sInitialized) {
            syncSharedSettings();
        }
    }

    public void startManagingSettings(WebSettings settings) {
        if (mNeedsSharedSync) {
            syncSharedSettings();
        }
        synchronized (mManagedSettings) {
            if(com.android.browser.Browser.LOG_SYNC_ENABLED) {
                Log.i(LOGTAG_SYNC, "startManagingSettings()," + 
                        " synchronized(mManagedSettings), IN");
            }
            syncStaticSettings(settings);
            syncSetting(settings);
            mManagedSettings.add(new WeakReference<WebSettings>(settings));
            if(com.android.browser.Browser.LOG_SYNC_ENABLED) {
                Log.i(LOGTAG_SYNC, "startManagingSettings()," + 
                        " synchronized(mManagedSettings), OUT");
            }
        }
    }
//pekall
    private String defaultSearchEngine;
    private String dataConnection;
    private String userConnection;

    public void syncSharedPreferences(){
        try {
            dataConnection = (String)getApnType(mPrefs.getString(
                        PREF_DATA_CONNECTION, mContext.getResources().getString(R.string.pref_data_connection_default)));
    		defaultSearchEngine = mPrefs.getString(PREF_SEARCH_ENGINE, "");
    		String baseUrl = (String)getSearchEngineBaseUrl(defaultSearchEngine);
            String download_dir = mPrefs.getString(PREF_DOWNLOAD_DIR_NEW, "");
            String agent = mPrefs.getString(PREF_SETTING_IP, "");
            String port = mPrefs.getString(PREF_SETTING_PORT, "");
            
            Log.v(LOGTAG, "Data Connection: " + dataConnection);
            Log.v(LOGTAG, "search engine: " + defaultSearchEngine);
            Log.v(LOGTAG, "download dir: " + download_dir);
            Log.v(LOGTAG, "agent: " + agent);
            Log.v(LOGTAG, "port: " + port);
    
        	Uri uri = Uri.parse(BrowserProvider2.GLOBAL_INFO_URI); 
            ContentValues cv = new ContentValues();
            cv.put(BrowserProvider2.COLUMN_BASE_URL, baseUrl);
            cv.put(BrowserProvider2.COLUMN_DOWNLOAD_DIR, download_dir);
            mContext.getContentResolver().update(uri, cv, null, null);  
            cv.clear();
            cv.put(BrowserProvider2.Agents.AGENT, agent);
            cv.put(BrowserProvider2.Agents.PORT, port);
            mContext.getContentResolver().update(BrowserProvider2.Agents.CONTENT_URI, cv, "_id=0", null);
        } catch (Exception e) {
            // TODO: handle exception
            Log.v(LOGTAG, "", e);
        }
    }

    private CharSequence getSearchEngineBaseUrl(String enumName) {
        if(mContext == null) return "";
        Resources res = mContext.getResources();
        CharSequence[] enumNames =  null;
        if(BrowserSettings.CU_PLATFORM){
            enumNames = res.getStringArray(R.array.search_engines_cu);
        }else if(BrowserSettings.CMCC_S868T){
        	enumNames = res.getStringArray(R.array.search_engines_s868t);
        }else{
            enumNames = res.getStringArray(R.array.search_engines);
        }
        
        CharSequence[] baseUrls = null;

        if(BrowserSettings.CU_PLATFORM){
            baseUrls = mContext.getResources().getTextArray(R.array.pref_search_engine_base_urls_cu_coolpad);
        }else if(BrowserSettings.CMCC_S868T){
        	baseUrls = mContext.getResources().getTextArray(R.array.pref_search_engine_base_urls_s868);
        }else{
            baseUrls =  mContext.getResources().getTextArray(R.array.pref_search_engine_base_urls);
        }
        if (baseUrls.length != enumNames.length) return "";

        for (int i = 0; i < enumNames.length; i++) {
            if (enumNames[i].equals(enumName)) {
                return baseUrls[i];
            }
        }

        return "";
    }

    private CharSequence getApnName(String apnType) {
        if(mContext == null) return "";
        CharSequence[] apnNames = null;
        CharSequence[] apnTypes = null;

        apnNames = mContext.getResources().getTextArray(
                R.array.pref_data_connection_choices);
        apnTypes = mContext.getResources().getTextArray(
                R.array.pref_data_connection_values);
        
        if (apnTypes.length != apnNames.length) return "";

        for (int i = 0; i < apnTypes.length; i++) {
            if (apnTypes[i].equals(apnType)) {
                return apnNames[i];
            }
        }
        return "";
    }

    private CharSequence getApnType(String apnName) {
        if(mContext == null) return "";

        CharSequence[] apnNames = null;
        CharSequence[] apnTypes = null;
        apnNames = mContext.getResources().getTextArray(
                R.array.pref_data_connection_choices);
        apnTypes = mContext.getResources().getTextArray(
                R.array.pref_data_connection_values);
       

        if (apnTypes.length != apnNames.length) return "";

        for (int i = 0; i < apnNames.length; i++) {
            if (apnNames[i].equals(apnName)) {
                return apnTypes[i];
            }
        }
        return "internet";
    }

    public void setUserConnection(String name) {
        if(name == null) {
            userConnection = null;
            return;
        }

        String type = (String)getApnType(name);
        Log.v(LOGTAG, "getUserConnection: " + type + " " + name);
        userConnection = type;
    }

    public String getUserConnection() {
        return userConnection;
    }

    public String getDataConnection() {
        if(FORCE_SYS_CONN) {
            Log.v(LOGTAG, "force to use system connection");
            return "system";
        }

        Log.v(LOGTAG, "getDataConnection: " + dataConnection);
        return dataConnection;
    }

    public void setDataConnection(String type) {
        String name = (String)getApnName(type);
        Log.v(LOGTAG, "setDataConnection: " + type + " " + name);

        Editor ed = PreferenceManager.
            getDefaultSharedPreferences(mContext).edit();
        ed.putString(PreferenceKeys.PREF_DATA_CONNECTION, name);
        try {
            ed.commit();    
        } catch (Exception e) {
            Log.e(LOGTAG,"", e);
        }

        dataConnection = type;
    }

    private Runnable mSetup = new Runnable() {

        @Override
        public void run() {
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            mFontSizeMult = metrics.scaledDensity / metrics.density;
            // the cost of one cached page is ~3M (measured using nytimes.com). For
            // low end devices, we only cache one page. For high end devices, we try
            // to cache more pages, currently choose 5.
            if (ActivityManager.staticGetMemoryClass() > 16) {
                mPageCacheCapacity = 5;
            }
            mWebStorageSizeManager = new WebStorageSizeManager(mContext,
                    new WebStorageSizeManager.StatFsDiskInfo(getAppCachePath()),
                    new WebStorageSizeManager.WebKitAppCacheInfo(getAppCachePath()));
            // Workaround b/5253777
            CookieManager.getInstance().acceptCookie();
            // Workaround b/5254577
            mPrefs.registerOnSharedPreferenceChangeListener(BrowserSettings.this);
            if (Build.VERSION.CODENAME.equals("REL")) {
                // This is a release build, always startup with debug disabled
                setDebugEnabled(false);
            }
            if (mPrefs.contains(PREF_TEXT_SIZE)) {
                /*
                 * Update from TextSize enum to zoom percent
                 * SMALLEST is 50%
                 * SMALLER is 75%
                 * NORMAL is 100%
                 * LARGER is 150%
                 * LARGEST is 200%
                 */
                switch (getTextSize()) {
                case SMALLEST:
                    setTextZoom(50);
                    break;
                case SMALLER:
                    setTextZoom(75);
                    break;
                case LARGER:
                    setTextZoom(150);
                    break;
                case LARGEST:
                    setTextZoom(200);
                    break;
                }
                mPrefs.edit().remove(PREF_TEXT_SIZE).apply();
            }

            if(CUST_HOME_PAGE != null && CUST_HOME_PAGE.length() != 0) {
                sFactoryResetUrl = CUST_HOME_PAGE;
            } else {
                int homepage_base_id = R.string.homepage_base_w;

                if(BrowserSettings.CU_PLATFORM) {
                    homepage_base_id = R.string.homepage_base_cu;
                } else if(BrowserSettings.DX58X){
                	homepage_base_id = R.string.homepage_base_d58x;
                } else if(BrowserSettings.CMCC_PLATFORM) {
                    homepage_base_id = R.string.homepage_base;
                } 

                sFactoryResetUrl = mContext.getResources().getString(homepage_base_id);
                if (sFactoryResetUrl.indexOf("{CID}") != -1) {
                    sFactoryResetUrl = sFactoryResetUrl.replace("{CID}",
                            BrowserProvider.getClientId(mContext.getContentResolver()));
                }
            }

            synchronized (BrowserSettings.class) {
                if(com.android.browser.Browser.LOG_SYNC_ENABLED) {
                    Log.i(LOGTAG_SYNC, "run()," + 
                            " synchronized(BrowserSettings.class), IN");
                }
                sInitialized = true;
                BrowserSettings.class.notifyAll();
                if(com.android.browser.Browser.LOG_SYNC_ENABLED) {
                    Log.i(LOGTAG_SYNC, "run()," + 
                            " synchronized(BrowserSettings.class), OUT");
                }
            }
        }
    };

    private static void requireInitialization() {
        synchronized (BrowserSettings.class) {
            if(com.android.browser.Browser.LOG_SYNC_ENABLED) {
                Log.i(LOGTAG_SYNC, "requireInitialization()," + 
                        " synchronized(BrowserSettings.class), IN");
            }
            while (!sInitialized) {
                try {
                    BrowserSettings.class.wait();
                } catch (InterruptedException e) {
                }
            }
            if(com.android.browser.Browser.LOG_SYNC_ENABLED) {
                Log.i(LOGTAG_SYNC, "requireInitialization()," + 
                        " synchronized(BrowserSettings.class), OUT");
            }
        }
    }

    /**
     * Syncs all the settings that have a Preference UI
     */
    private void syncSetting(WebSettings settings) {
        settings.setGeolocationEnabled(enableGeolocation());
        settings.setJavaScriptEnabled(enableJavascript());
        settings.setLightTouchEnabled(enableLightTouch());
        settings.setNavDump(enableNavDump());
        settings.setHardwareAccelSkiaEnabled(isSkiaHardwareAccelerated());
        settings.setShowVisualIndicator(enableVisualIndicator());
        settings.setDefaultTextEncodingName(getDefaultTextEncoding());
        /* TODO: Should implement after framework/webkit done
        if(b.defaultTextEncodingName.equals("Auto-Detect")) {
            s.setDefaultTextEncodingName("UTF-8");

            if(BrowserSettings.CMCC_PLATFORM || 
                    BrowserSettings.XMM2231_PLATFORM) 
                s.setUserTextEncoding(false);
        } else {
            s.setDefaultTextEncodingName(b.defaultTextEncodingName);

            if(BrowserSettings.CMCC_PLATFORM || 
                    BrowserSettings.XMM2231_PLATFORM) 
                s.setUserTextEncoding(true);
        } */
        settings.setDefaultZoom(getDefaultZoom());
        settings.setMinimumFontSize(getMinimumFontSize());
        settings.setMinimumLogicalFontSize(getMinimumFontSize());
        settings.setForceUserScalable(forceEnableUserScalable());
        settings.setPluginState(getPluginState());
        settings.setTextZoom(getTextZoom());
        settings.setDoubleTapZoom(getDoubleTapZoom());
        settings.setAutoFillEnabled(isAutofillEnabled());
        settings.setLayoutAlgorithm(getLayoutAlgorithm());
        settings.setJavaScriptCanOpenWindowsAutomatically(!blockPopupWindows());
        settings.setLoadsImagesAutomatically(loadImages());
        settings.setLoadWithOverviewMode(loadPageInOverviewMode());
        settings.setSavePassword(rememberPasswords());
        settings.setSaveFormData(saveFormdata());
        settings.setUseWideViewPort(isWideViewport());
        settings.setAutoFillProfile(getAutoFillProfile());

        if(BrowserSettings.CMCC_PLATFORM) {
            if(mForceDefaultUserAgent) {
                settings.setUserAgentString(ANDROID_DEFAULT_USERAGENT);
            } else {
            	settings.setUserAgentString(settings.getUserAgentString());
            }
        } else {
            String ua = mCustomUserAgents.get(settings);
            if (ua != null) {
                settings.setUserAgentString(ua);
            } else {
                settings.setUserAgentString(USER_AGENTS[getUserAgent()]);
            }
        }

        boolean useInverted = useInvertedRendering();
        settings.setProperty(WebViewProperties.gfxInvertedScreen,
                useInverted ? "true" : "false");
        if (useInverted) {
            settings.setProperty(WebViewProperties.gfxInvertedScreenContrast,
                    Float.toString(getInvertedContrast()));
        }

        if (isDebugEnabled()) {
            settings.setProperty(WebViewProperties.gfxEnableCpuUploadPath,
                    enableCpuUploadPath() ? "true" : "false");
        }
    }

    /**
     * Syncs all the settings that have no UI
     * These cannot change, so we only need to set them once per WebSettings
     */
    private void syncStaticSettings(WebSettings settings) {
        settings.setDefaultFontSize(16);
        settings.setDefaultFixedFontSize(13);
        settings.setPageCacheCapacity(getPageCacheCapacity());

        // WebView inside Browser doesn't want initial focus to be set.
        settings.setNeedInitialFocus(false);
        // Browser supports multiple windows
        settings.setSupportMultipleWindows(true);
        // enable smooth transition for better performance during panning or
        // zooming
        settings.setEnableSmoothTransition(true);
        // WebView should be preserving the memory as much as possible.
        // However, apps like browser wish to turn on the performance mode which
        // would require more memory.
        // TODO: We need to dynamically allocate/deallocate temporary memory for
        // apps which are trying to use minimal memory. Currently, double
        // buffering is always turned on, which is unnecessary.
        settings.setProperty(WebViewProperties.gfxUseMinimalMemory, "false");
        // disable content url access
        settings.setAllowContentAccess(false);

        // HTML5 API flags
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setWorkersEnabled(true);  // This only affects V8.

        // HTML5 configuration parametersettings.
        settings.setAppCacheMaxSize(getWebStorageSizeManager().getAppCacheMaxSize());
        settings.setAppCachePath(getAppCachePath());
        settings.setDatabasePath(mContext.getDir("databases", 0).getPath());
        settings.setGeolocationDatabasePath(mContext.getDir("geolocation", 0).getPath());
    }

    private void syncSharedSettings() {
        mNeedsSharedSync = false;
        CookieManager.getInstance().setAcceptCookie(acceptCookies());
        if (mController != null) {
            mController.setShouldShowErrorConsole(enableJavascriptConsole());
        }
    }

    private void syncManagedSettings() {
        syncSharedSettings();
        synchronized (mManagedSettings) {
            if(com.android.browser.Browser.LOG_SYNC_ENABLED) {
                Log.i(LOGTAG_SYNC, "syncManagedSettings()," + 
                        " synchronized(mManagedSettings), IN");
            }
            Iterator<WeakReference<WebSettings>> iter = mManagedSettings.iterator();
            while (iter.hasNext()) {
                WeakReference<WebSettings> ref = iter.next();
                WebSettings settings = ref.get();
                if (settings == null) {
                    iter.remove();
                    continue;
                }
                syncSetting(settings);
            }
            if(com.android.browser.Browser.LOG_SYNC_ENABLED) {
                Log.i(LOGTAG_SYNC, "syncManagedSettings()," + 
                        " synchronized(mManagedSettings), OUT");
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(
            SharedPreferences sharedPreferences, String key) {
        syncManagedSettings();
        if(mController == null){
            return ;
        }
        if (PREF_SEARCH_ENGINE.equals(key)) {
            updateSearchEngine(false);
        }
        if (PREF_FULLSCREEN.equals(key)) {
            if (mController.getUi() != null) {
                mController.getUi().setFullscreen(useFullscreen());
            }
        } else if (PREF_ENABLE_QUICK_CONTROLS.equals(key)) {
            if (mController.getUi() != null) {
                mController.getUi().setUseQuickControls(sharedPreferences.getBoolean(key, false));
            }
        }
    }

    public static String getFactoryResetHomeUrl(Context context) {
        requireInitialization();
        return sFactoryResetUrl;
    }

    public LayoutAlgorithm getLayoutAlgorithm() {
        LayoutAlgorithm layoutAlgorithm = LayoutAlgorithm.NORMAL;
        if (autofitPages()) {
            layoutAlgorithm = LayoutAlgorithm.NARROW_COLUMNS;
        }
        if (isDebugEnabled()) {
            if (isSmallScreen()) {
                layoutAlgorithm = LayoutAlgorithm.SINGLE_COLUMN;
            } else {
                if (isNormalLayout()) {
                    layoutAlgorithm = LayoutAlgorithm.NORMAL;
                } else {
                    layoutAlgorithm = LayoutAlgorithm.NARROW_COLUMNS;
                }
            }
        }
        return layoutAlgorithm;
    }

    public int getPageCacheCapacity() {
        requireInitialization();
        return mPageCacheCapacity;
    }

    public WebStorageSizeManager getWebStorageSizeManager() {
        requireInitialization();
        return mWebStorageSizeManager;
    }

    private String getAppCachePath() {
        if (mAppCachePath == null) {
            mAppCachePath = mContext.getDir("appcache", 0).getPath();
        }
        return mAppCachePath;
    }

    private void updateSearchEngine(boolean force) {
        String searchEngineName = getSearchEngineName();
        if (force || mSearchEngine == null ||
                !mSearchEngine.getName().equals(searchEngineName)) {
            if (mSearchEngine != null) {
                if (mSearchEngine.supportsVoiceSearch()) {
                     // One or more tabs could have been in voice search mode.
                     // Clear it, since the new SearchEngine may not support
                     // it, or may handle it differently.
                     for (int i = 0; i < mController.getTabControl().getTabCount(); i++) {
                         mController.getTabControl().getTab(i).revertVoiceSearchMode();
                     }
                 }
                mSearchEngine.close();
             }
            mSearchEngine = SearchEngines.get(mContext, searchEngineName);
         }
    }

    public SearchEngine getSearchEngine() {
        if (mSearchEngine == null) {
            updateSearchEngine(false);
        }
        return mSearchEngine;
    }

    public boolean isDebugEnabled() {
        requireInitialization();
        return mPrefs.getBoolean(PREF_DEBUG_MENU, false);
    }

    public void setDebugEnabled(boolean value) {
        Editor edit = mPrefs.edit();
        edit.putBoolean(PREF_DEBUG_MENU, value);
        if (!value) {
            // Reset to "safe" value
            edit.putBoolean(PREF_ENABLE_HARDWARE_ACCEL_SKIA, false);
        }
        edit.apply();
    }

    public void clearCache() {
        WebIconDatabase.getInstance().removeAllIcons();
        if (mController != null) {
            WebView current = mController.getCurrentWebView();
            if (current != null) {
                current.clearCache(true);
            }
        }
    }

    public void clearCookies() {
        CookieManager.getInstance().removeAllCookie();
    }

    public void clearHistory() {
        ContentResolver resolver = mContext.getContentResolver();
        Browser.clearHistory(resolver);
        Browser.clearSearches(resolver);
    }

    public void clearFormData() {
        WebViewDatabase.getInstance(mContext).clearFormData();
        if (mController!= null) {
            WebView currentTopView = mController.getCurrentTopWebView();
            if (currentTopView != null) {
                currentTopView.clearFormData();
            }
        }
    }

    public void clearPasswords() {
        WebViewDatabase db = WebViewDatabase.getInstance(mContext);
        db.clearUsernamePassword();
        db.clearHttpAuthUsernamePassword();

        // Some websites save username in cookies
        CookieManager.getInstance().removeAllCookie();
    }

    public void clearDatabases() {
        WebStorage.getInstance().deleteAllData();
    }

    public void clearLocationAccess() {
        GeolocationPermissions.getInstance().clearAll();
    }

    public void resetDefaultPreferences() {
        // Preserve autologin setting
        long gal = mPrefs.getLong(GoogleAccountLogin.PREF_AUTOLOGIN_TIME, -1);
        mPrefs.edit()
                .clear()
                .putLong(GoogleAccountLogin.PREF_AUTOLOGIN_TIME, gal)
                .apply();
        setHomePage(mContext.getString(R.string.homepage_base));
        syncManagedSettings();
    }

    public AutoFillProfile getAutoFillProfile() {
        mAutofillHandler.waitForLoad();
        return mAutofillHandler.getAutoFillProfile();
    }

    public void setAutoFillProfile(AutoFillProfile profile, Message msg) {
        mAutofillHandler.waitForLoad();
        mAutofillHandler.setAutoFillProfile(profile, msg);
        // Auto-fill will reuse the same profile ID when making edits to the profile,
        // so we need to force a settings sync (otherwise the SharedPreferences
        // manager will optimise out the call to onSharedPreferenceChanged(), as
        // it thinks nothing has changed).
        syncManagedSettings();
    }

    public void toggleDebugSettings() {
        setDebugEnabled(!isDebugEnabled());
    }

    public boolean hasDesktopUseragent(WebView view) {
        return view != null && mCustomUserAgents.get(view.getSettings()) != null;
    }

    public void toggleDesktopUseragent(WebView view) {
        if (view == null) {
            return;
        }
        WebSettings settings = view.getSettings();
        if (mCustomUserAgents.get(settings) != null) {
            mCustomUserAgents.remove(settings);
            settings.setUserAgentString(USER_AGENTS[getUserAgent()]);
        } else {
            mCustomUserAgents.put(settings, DESKTOP_USERAGENT);
            settings.setUserAgentString(DESKTOP_USERAGENT);
        }
    }

    public static int getAdjustedMinimumFontSize(int rawValue) {
        rawValue++; // Preference starts at 0, min font at 1
        if (rawValue > 1) {
            rawValue += (MIN_FONT_SIZE_OFFSET - 2);
        }
        return rawValue;
    }

    public void adjustUserAgent(WebView view, String url) {
        if (!BrowserSettings.CMCC_PLATFORM || view == null || url == null) {
            return;
        }

        WebSettings settings = view.getSettings();
        if(settings == null) return;

        // Hack user agent for yahoo
        if(url.contains("yahoo.com") || url.contains("google.com")) {
            mForceDefaultUserAgent = true;
            settings.setUserAgentString(ANDROID_DEFAULT_USERAGENT);
        } else {
            mForceDefaultUserAgent = false;
            settings.setUserAgentString(settings.getUserAgentString());
        }
    }

    
    public int getAdjustedTextZoom(int rawValue) {
        rawValue = (rawValue - TEXT_ZOOM_START_VAL) * TEXT_ZOOM_STEP;
        return (int) ((rawValue + 100) * mFontSizeMult);
    }

    static int getRawTextZoom(int percent) {
        return (percent - 100) / TEXT_ZOOM_STEP + TEXT_ZOOM_START_VAL;
    }

    public int getAdjustedDoubleTapZoom(int rawValue) {
        rawValue = (rawValue - DOUBLE_TAP_ZOOM_START_VAL) * DOUBLE_TAP_ZOOM_STEP;
        return (int) ((rawValue + 100) * mFontSizeMult);
    }

    static int getRawDoubleTapZoom(int percent) {
        return (percent - 100) / DOUBLE_TAP_ZOOM_STEP + DOUBLE_TAP_ZOOM_START_VAL;
    }

    public SharedPreferences getPreferences() {
        return mPrefs;
    }

    // -----------------------------
    // getter/setters for accessibility_preferences.xml
    // -----------------------------

    @Deprecated
    private TextSize getTextSize() {
        String textSize = mPrefs.getString(PREF_TEXT_SIZE, "NORMAL");
        return TextSize.valueOf(textSize);
    }

    public int getMinimumFontSize() {
        int minFont = mPrefs.getInt(PREF_MIN_FONT_SIZE, 0);
        return getAdjustedMinimumFontSize(minFont);
    }

    public boolean forceEnableUserScalable() {
        return mPrefs.getBoolean(PREF_FORCE_USERSCALABLE, false);
    }

    public int getTextZoom() {
        requireInitialization();
        int textZoom = mPrefs.getInt(PREF_TEXT_ZOOM, 10);
        return getAdjustedTextZoom(textZoom);
    }

    public void setTextZoom(int percent) {
        mPrefs.edit().putInt(PREF_TEXT_ZOOM, getRawTextZoom(percent)).apply();
    }

    public int getDoubleTapZoom() {
        requireInitialization();
        int doubleTapZoom = mPrefs.getInt(PREF_DOUBLE_TAP_ZOOM, 5);
        return getAdjustedDoubleTapZoom(doubleTapZoom);
    }

    public void setDoubleTapZoom(int percent) {
        mPrefs.edit().putInt(PREF_DOUBLE_TAP_ZOOM, getRawDoubleTapZoom(percent)).apply();
    }

    // -----------------------------
    // getter/setters for advanced_preferences.xml
    // -----------------------------
    private String defaultSearchEngineName = "baidu";
    public String getSearchEngineName() {
        return mPrefs.getString(PREF_SEARCH_ENGINE, defaultSearchEngineName);
    }

    public boolean openInBackground() {
        return mPrefs.getBoolean(PREF_OPEN_IN_BACKGROUND, false);
    }

    public boolean enableJavascript() {
        return mPrefs.getBoolean(PREF_ENABLE_JAVASCRIPT, true);
    }

    // TODO: Cache
    public PluginState getPluginState() {
        String state = mPrefs.getString(PREF_PLUGIN_STATE, "ON");
        return PluginState.valueOf(state);
    }

    // TODO: Cache
    public ZoomDensity getDefaultZoom() {
        String zoom = mPrefs.getString(PREF_DEFAULT_ZOOM, "MEDIUM");
        return ZoomDensity.valueOf(zoom);
    }

    public boolean loadPageInOverviewMode() {
        
        if(CU_PLATFORM){
             return mPrefs.getBoolean(PREF_LOAD_PAGE2, false);
        }else{
             return mPrefs.getBoolean(PREF_LOAD_PAGE, true);
        }
    }

    public boolean autofitPages() {
        return mPrefs.getBoolean(PREF_AUTOFIT_PAGES, true);
    }

    public boolean blockPopupWindows() {
        return mPrefs.getBoolean(PREF_BLOCK_POPUP_WINDOWS, true);
    }

    public boolean loadImages() {
        return mPrefs.getBoolean(PREF_LOAD_IMAGES, true);
    }

    public String getDefaultTextEncoding() {
        return mPrefs.getString(PREF_DEFAULT_TEXT_ENCODING, null);
    }

    // -----------------------------
    // getter/setters for general_preferences.xml
    // -----------------------------

    public String getHomePage() {
      //  return mPrefs.getString(PREF_HOMEPAGE, getFactoryResetHomeUrl(mContext));
        String homepage = null;
        Cursor cursor = null;
            try {
                cursor = mContext.getContentResolver().query(BrowserProvider2.Properties.CONTENT_URI, new String[]{BrowserProvider2.Properties._ID, BrowserProvider2.Properties.VALUE}, 
                        BrowserProvider2.Properties._ID + "=?", new String[] {0+""}, null);
                if(cursor!=null && cursor.moveToNext()){
                    homepage = cursor.getString(1);
                }
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                if(cursor != null){
                    cursor.close();
                }
            }

        return homepage==null?getFactoryResetHomeUrl(mContext):homepage;
    }

    public void setHomePage(String value) {
      //  mPrefs.edit().putString(PREF_HOMEPAGE, value).apply();
        ContentValues values = new ContentValues();
        values.put("value", value);
        mContext.getContentResolver().update(BrowserProvider2.Properties.CONTENT_URI, values, "_id = 0", null);
    }

    public boolean isAutofillEnabled() {
        return mPrefs.getBoolean(PREF_AUTOFILL_ENABLED, true);
    }

    public void setAutofillEnabled(boolean value) {
        mPrefs.edit().putBoolean(PREF_AUTOFILL_ENABLED, value).apply();
    }

    // -----------------------------
    // getter/setters for debug_preferences.xml
    // -----------------------------

    public boolean isHardwareAccelerated() {
        if (!isDebugEnabled()) {
            return true;
        }
        return mPrefs.getBoolean(PREF_ENABLE_HARDWARE_ACCEL, true);
    }

    public boolean isSkiaHardwareAccelerated() {
        if (!isDebugEnabled()) {
            return false;
        }
        return mPrefs.getBoolean(PREF_ENABLE_HARDWARE_ACCEL_SKIA, false);
    }

    public int getUserAgent() {
        if (!isDebugEnabled()) {
            return 0;
        }
        return Integer.parseInt(mPrefs.getString(PREF_USER_AGENT, "0"));
    }

    // -----------------------------
    // getter/setters for hidden_debug_preferences.xml
    // -----------------------------

    public boolean enableVisualIndicator() {
        if (!isDebugEnabled()) {
            return false;
        }
        return mPrefs.getBoolean(PREF_ENABLE_VISUAL_INDICATOR, false);
    }

    public boolean enableCpuUploadPath() {
        if (!isDebugEnabled()) {
            return false;
        }
        return mPrefs.getBoolean(PREF_ENABLE_CPU_UPLOAD_PATH, false);
    }

    public boolean enableJavascriptConsole() {
        if (!isDebugEnabled()) {
            return false;
        }
        return mPrefs.getBoolean(PREF_JAVASCRIPT_CONSOLE, true);
    }

    public boolean isSmallScreen() {
        if (!isDebugEnabled()) {
            return false;
        }
        return mPrefs.getBoolean(PREF_SMALL_SCREEN, false);
    }

    public boolean isWideViewport() {
        if (!isDebugEnabled()) {
            return true;
        }
        return mPrefs.getBoolean(PREF_WIDE_VIEWPORT, true);
    }

    public boolean isNormalLayout() {
        if (!isDebugEnabled()) {
            return false;
        }
        return mPrefs.getBoolean(PREF_NORMAL_LAYOUT, false);
    }

    public boolean isTracing() {
        if (!isDebugEnabled()) {
            return false;
        }
        return mPrefs.getBoolean(PREF_ENABLE_TRACING, false);
    }

    public boolean enableLightTouch() {
        if (!isDebugEnabled()) {
            return false;
        }
        return mPrefs.getBoolean(PREF_ENABLE_LIGHT_TOUCH, false);
    }

    public boolean enableNavDump() {
        if (!isDebugEnabled()) {
            return false;
        }
        return mPrefs.getBoolean(PREF_ENABLE_NAV_DUMP, false);
    }

    public String getJsEngineFlags() {
        if (!isDebugEnabled()) {
            return "";
        }
        return mPrefs.getString(PREF_JS_ENGINE_FLAGS, "");
    }

    // -----------------------------
    // getter/setters for lab_preferences.xml
    // -----------------------------

    public boolean useQuickControls() {
        return mPrefs.getBoolean(PREF_ENABLE_QUICK_CONTROLS, false);
    }

    public boolean useMostVisitedHomepage() {
        return HomeProvider.MOST_VISITED.equals(getHomePage());
    }

    public boolean useFullscreen() {
        return mPrefs.getBoolean(PREF_FULLSCREEN, false);
    }

    public boolean useInvertedRendering() {
        return mPrefs.getBoolean(PREF_INVERTED, false);
    }

    public float getInvertedContrast() {
        return 1 + (mPrefs.getInt(PREF_INVERTED_CONTRAST, 0) / 10f);
    }

    // -----------------------------
    // getter/setters for privacy_security_preferences.xml
    // -----------------------------

    public boolean showSecurityWarnings() {
        return mPrefs.getBoolean(PREF_SHOW_SECURITY_WARNINGS, true);
    }

    public boolean acceptCookies() {
        return mPrefs.getBoolean(PREF_ACCEPT_COOKIES, true);
    }

    public boolean saveFormdata() {
        return mPrefs.getBoolean(PREF_SAVE_FORMDATA, true);
    }

    public boolean enableGeolocation() {
        return mPrefs.getBoolean(PREF_ENABLE_GEOLOCATION, true);
    }

    public boolean rememberPasswords() {
        return mPrefs.getBoolean(PREF_REMEMBER_PASSWORDS, true);
    }

    // -----------------------------
    // getter/setters for bandwidth_preferences.xml
    // -----------------------------

    public static String getPreloadOnWifiOnlyPreferenceString(Context context) {
        return context.getResources().getString(R.string.pref_data_preload_value_wifi_only);
    }

    public static String getPreloadAlwaysPreferenceString(Context context) {
        return context.getResources().getString(R.string.pref_data_preload_value_always);
    }

    private static final String DEAULT_PRELOAD_SECURE_SETTING_KEY =
            "browser_default_preload_setting";

    public String getDefaultPreloadSetting() {
        String preload = Settings.Secure.getString(mContext.getContentResolver(),
                DEAULT_PRELOAD_SECURE_SETTING_KEY);
        if (preload == null) {
            preload = mContext.getResources().getString(R.string.pref_data_preload_default_value);
        }
        return preload;
    }

    public String getPreloadEnabled() {
        return mPrefs.getString(PREF_DATA_PRELOAD, getDefaultPreloadSetting());
    }


    public static final boolean CMCC_PLATFORM = isCmccPlatform();
    public static final boolean CMCC_PLAN_ONE = false;
    public static final boolean CMCC_LAB_TEST = isCmccLabTest();
    public static final boolean CU_PLATFORM = isCuPlatform();
    public static final boolean FORCE_SYS_CONN = isForceSystemConnection();
    public static final String CUST_HOME_PAGE = getCustomHomePage();
    public static final boolean LENOVO_TEST = isLenovoTest();
    public static final boolean PAGE_TURN_LOAD_TWICE = true;
    public static final boolean CMCC_S868T = isS868t();
    public static final boolean DX58X = isDX58X();
    public static final boolean CMCC_JICAI = isCMCCJiCai();

    // Enable features of China Mobile
    private static final boolean isCmccPlatform() {
        String product = Build.PRODUCT;
        Log.v(LOGTAG, "enter isCmccPlatform: " + product);
        return true;
    }

    // Enable features of China Mobile lab test
    private static final boolean isCmccLabTest() {
        if(SystemProperties.get("ro.com.ontim.cmcc_lab_test", "").contains("true") ||
                SystemProperties.get("ro.build.cmcc_lab_test", "").contains("true"))
            return true;
        return false;
    }

    private static final boolean isS868t(){
        String product = Build.PRODUCT;
        // Huawei T8808D
        if(product != null && product.contains("S868t"))
            return true;

        return false;
    }
    
    private static final boolean isCMCCJiCai(){
        if(SystemProperties.get("ro.versions.cmcc_jicai_2013", "").contains("true"))
            return true;
        return false;
    }
    private static final boolean isDX58X(){
        if(SystemProperties.get("ontim.preinstall.name", "").contains("qihoo360"))
            return true;
        return false;
    }
    private static final boolean isLenovoTest(){
        if(SystemProperties.get("lenovo.mtbf.test", "").contains("true")){
            return true;
        }
        return false;
    }
    
    // Enable features of China Unicom
    private static final boolean isCuPlatform() {
        String product = Build.PRODUCT;
        if(SystemProperties.get("ro.product.carrier", "unknown").contains("cu"))
            return true;
        return false;
    }

    // Force to use system default network
    private static final boolean isForceSystemConnection() {
        if(isCuPlatform())
            return true;

        if(SystemProperties.get(
                "ro.com.ontim.force_sys_conn",
                "").contains("true"))
            return true;
        return false;
    }

    static final boolean useSystemConnectFlags() {
        return isTigerPlatform();
    }

    static final boolean isTigerPlatform() {
        if (SystemProperties.get("ro.product.name", "").contains("Y500")
                || SystemProperties.get("ro.product.name", "").contains("D98X")
                || SystemProperties.get("ro.product.name", "").contains("C986t")
                || SystemProperties.get("ro.product.name", "").contains("Y320")
                || SystemProperties.get("ro.product.name", "").contains("T965"))
            return true;

        if (SystemProperties.get("ro.board.platform", "").contains("sc8825"))
            return true;

        return false;
    }

    private static final String getCustomHomePage() {
        return SystemProperties.get("ro.browser.homepage", "");
    }

    public void setWifiCloseOperation(boolean s) {
        Log.v(LOGTAG, "setWifiCloseOperation: " + s);

        Uri uri = Uri.parse(BrowserProvider2.GLOBAL_INFO_URI);
        ContentValues cv = new ContentValues();
        cv.put(BrowserProvider2.COLUMN_WIFI_OPT, s?1:0);
        mContext.getContentResolver().update(uri, cv, "_id=0", null);      
    } 

    public boolean getWifiCloseOperation() {
        Cursor cursor = null;
        boolean ret = false;
        try {
            cursor = mContext.getContentResolver().query(
                    Uri.parse(BrowserProvider2.GLOBAL_INFO_URI),
                    new String[]{BrowserProvider2.COLUMN_WIFI_OPT}, null, null, null);

            if (cursor != null && cursor.getCount() != 0) {
                cursor.moveToFirst();
                int tmp = cursor.getInt(0);
                Log.d(LOGTAG, "getWifiCloseOperation: " + tmp);
                ret = (tmp == 1) ? true : false;
            }
        } finally {
            if(cursor != null) cursor.close();
            return ret;
        }
    }

    boolean isCmccApn(String apn) {
        if(apn.contains("CMWAP") || apn.contains("CMNET") 
                || apn.contains("LAB") || apn.contains("lab")
                || apn.contains("cmwap") || apn.contains("cmnet")) {
            return true;
        }
        return false;
    }

    void setApnSystemProperty(String apn) {
        if(apn == null) apn = "";

        // Get system default apn, like:
        // CMNET default,internet,supl
        // CMWAP default,wap,mms,supl
        // 中国移动实验室 default,lab,supl
        String sysApn = getSystemApnName();
        if(isCmccApn(sysApn)) {
            if(apn.equals("default") && !isWifiConnected()) {
                // Using system default 3g connection
                if(sysApn.contains("CMWAP")) {
                    apn = Controller.ApnHandler.APN_TYPE_WAP;
                }
            }
        } else {
            // Default for NON-CMCC APNs
            apn = "default";
        }

        try {
            SystemProperties.set("ontim.sys.browser_interface", apn);
        } catch (Exception e) {
            Log.e(LOGTAG, "", e);
        }
    }

    boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.
            getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] infos = cm.getAllNetworkInfo();
        if(infos == null) return false;

        for(NetworkInfo info : infos) {
            if(info == null) continue;

            if(info.getType() != ConnectivityManager.TYPE_WIFI)
                continue;
            if(info.isConnected()) 
                return true;
            else
                return false;
        }
        return false;
    }

    public String getSystemApnName() {
        if (isTigerPlatform())
            return getSystemApnTiger();
        
        return getSystemApn();
    }
    /**
     * Get system preferred apn type
     *
     * @return system preferred apn type
     */
    private String getSystemApn() {
        String ret = "default";

        // first, checkout if dual sim or single
        ContentResolver cr = mContext.getContentResolver();
        int linkId = android.provider.Settings.System.getInt(cr,
                "ps_default", -1);
        Log.d(LOGTAG, "default linkid=" + linkId);

        // second, fetch the apn pref
        String PREFERRED_APN_URI;
        String CARRIERS_URI_PREFIX = "content://telephony/carriers";
        String PREFERRED_APN_URI_SUFFIX = "/preferapn";

        if (linkId == 0) {
            PREFERRED_APN_URI = CARRIERS_URI_PREFIX +
                PREFERRED_APN_URI_SUFFIX;
        } else if(linkId == 1){
            PREFERRED_APN_URI = CARRIERS_URI_PREFIX + linkId +
                PREFERRED_APN_URI_SUFFIX;
        } else {
            Log.d(LOGTAG, "error: default linkid="+linkId);
            PREFERRED_APN_URI = CARRIERS_URI_PREFIX + 
                PREFERRED_APN_URI_SUFFIX;
            // return ret;
        }

        Cursor cursor = null;
        try {
            cursor = cr.query(
                    Uri.parse(PREFERRED_APN_URI),
                    new String[] {"name", "type"},
                    null, null, Telephony.Carriers.DEFAULT_SORT_ORDER);
            if (cursor == null || !cursor.moveToFirst()) {
                Log.e(LOGTAG, "APN was not found in Database!");
                return ret;
            }
            do {
                String name = cursor.getString(0);
                String apn = cursor.getString(1);
                Log.i(LOGTAG, "apn is: " + name + " " + apn);
                if(name != null && name.length() != 0) {
                    ret = name + ":" + apn;
                    break;
                }
            } while(cursor.moveToNext());
        } catch(Exception e) {
            Log.e(LOGTAG, "", e);
        } finally {
            if (cursor != null) cursor.close();
        }
        return ret;
    }

    /**
     * Get system preferred apn type
     *
     * @return system preferred apn type
     */
    private String getSystemApnTiger() {
        String ret = "default";

        ConnectivityManager cm = (ConnectivityManager) mContext.
            getSystemService(Context.CONNECTIVITY_SERVICE);
        Class cls = ConnectivityManager.class;
        int linkId = 0;
        try {
            Method method = cls.getDeclaredMethod("getPrimaryDataSim", new Class[]{});
            if(method == null){
                return ret;
            } else {
                linkId = (Integer)method.invoke(cm, new Object[]{});
            } 
        } catch (Exception e) {
            // TODO: handle exception
            Log.d(LOGTAG, "", e);
        }
        Log.d(LOGTAG, "default tiger linkid=" + linkId);

        // second, fetch the apn pref
        String PREFERRED_APN_URI;
        String CARRIERS_URI_PREFIX = "content://telephony/carriers";
        String PREFERRED_APN_URI_SUFFIX = "/preferapn";

        if (linkId == 0) {
            PREFERRED_APN_URI = CARRIERS_URI_PREFIX +
                PREFERRED_APN_URI_SUFFIX;
        } else if(linkId == 1){
            PREFERRED_APN_URI = CARRIERS_URI_PREFIX + "2" +
                PREFERRED_APN_URI_SUFFIX;
        } else {
            Log.d(LOGTAG, "error: default linkid="+linkId);
            PREFERRED_APN_URI = CARRIERS_URI_PREFIX + 
                PREFERRED_APN_URI_SUFFIX;
            // return ret;
        }

        ContentResolver cr = mContext.getContentResolver();
        Cursor cursor = null;
        try {
            cursor = cr.query(
                    Uri.parse(PREFERRED_APN_URI),
                    new String[] {"name", "type"},
                    null, null, Telephony.Carriers.DEFAULT_SORT_ORDER);
            if (cursor == null || !cursor.moveToFirst()) {
                Log.e(LOGTAG, "APN was not found in Database!");
                return ret;
            }
            do {
                String name = cursor.getString(0);
                String apn = cursor.getString(1);
                Log.i(LOGTAG, "apn is: " + name + " " + apn);
                if(name != null && name.length() != 0) {
                    ret = name + ":" + apn;
                    break;
                }
            } while(cursor.moveToNext());
        } catch(Exception e) {
            Log.e(LOGTAG, "", e);
        } finally {
            if (cursor != null) cursor.close();
        }
        return ret;
    }
}
