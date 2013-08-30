/*
 * Copyright (C) 2009 The Android Open Source Project
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
import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.DialogInterface.OnCancelListener;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.security.KeyChain;
import android.security.KeyChainAliasCallback;
import android.speech.RecognizerResultsIntent;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.webkit.ClientCertRequestHandler;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebBackForwardListClient;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebResourceResponse;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.Toast;

import android.net.TrafficStats;

import com.android.browser.TabControl.OnThumbnailUpdatedListener;
import com.android.browser.homepages.HomeProvider;
import com.android.browser.provider.BrowserProvider2;
import com.android.browser.provider.SnapshotProvider.Snapshots;
import com.android.common.speech.LoggingEvents;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;
import java.lang.reflect.Method;

/**
 * Class for maintaining Tabs with a main WebView and a subwindow.
 */
class Tab implements PictureListener {

    // Log Tag
    private static final String LOGTAG = "Tab";
    private static final boolean ENABLE_LOG = true;

    // Special case the logtag for messages for the Console to make it easier to
    // filter them and match the logtag used for these messages in older versions
    // of the browser.
    private static final String CONSOLE_LOGTAG = "browser";
    private static final String LOGTAG_SYNC = "Tab_sync";
    
    private static final int MSG_CAPTURE = 42;
    private static final int CAPTURE_DELAY = 100;
    private static final int INITIAL_PROGRESS = 5;
    static final int UPDATE_SCREEN_DATA = 1008;

    static final int UPDATE_SCREEN_FOR_163_AUTO = 2000;

    private static final String[] SCREEN_PROJECT = new String[]{BrowserProvider2.NavScreen._ID, BrowserProvider2.NavScreen.URL, BrowserProvider2.NavScreen.UPDATE}; 

    private static Bitmap sDefaultFavicon;

    private static Paint sAlphaPaint = new Paint();
    public static long startRxBytes = 0;
    public static long startTxBytes = 0;
    public static long endRxBytes = 0;
    public static long endTxBytes = 0;
    private  boolean homeTab = false;
    
    static {
        sAlphaPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        sAlphaPaint.setColor(Color.TRANSPARENT);
    }

    public enum SecurityState {
        // The page's main resource does not use SSL. Note that we use this
        // state irrespective of the SSL authentication state of sub-resources.
        SECURITY_STATE_NOT_SECURE,
        // The page's main resource uses SSL and the certificate is good. The
        // same is true of all sub-resources.
        SECURITY_STATE_SECURE,
        // The page's main resource uses SSL and the certificate is good, but
        // some sub-resources either do not use SSL or have problems with their
        // certificates.
        SECURITY_STATE_MIXED,
        // The page's main resource uses SSL but there is a problem with its
        // certificate.
        SECURITY_STATE_BAD_CERTIFICATE,
    }

    public int getAppUid(){
        PackageManager pm = null;
        ApplicationInfo ai =null;
        try {
            pm = mContext.getPackageManager();
            ai = pm.getApplicationInfo("com.android.browser", PackageManager.GET_ACTIVITIES);
            return ai.uid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public boolean isHomeTab(){
        return homeTab;
    }
    
    public void setHomeTab(boolean isHomeTab){
        homeTab = isHomeTab;
    }
    
    Context mContext;
    protected WebViewController mWebViewController;

    // The tab ID
    private long mId = -1;

    // The Geolocation permissions prompt
    private GeolocationPermissionsPrompt mGeolocationPermissionsPrompt;
    // Main WebView wrapper
    private View mContainer;
    // Main WebView
    private WebView mMainView;
    // Subwindow container
    private View mSubViewContainer;
    // Subwindow WebView
    private WebView mSubView;
    // Saved bundle for when we are running low on memory. It contains the
    // information needed to restore the WebView if the user goes back to the
    // tab.
    private Bundle mSavedState;
    // Parent Tab. This is the Tab that created this Tab, or null if the Tab was
    // created by the UI
    private Tab mParent;
    // Tab that constructed by this Tab. This is used when this Tab is
    // destroyed, it clears all mParentTab values in the children.
    private Vector<Tab> mChildren;
    // If true, the tab is in the foreground of the current activity.
    private boolean mInForeground;
    // If true, the tab is in page loading state (after onPageStarted,
    // before onPageFinsihed)
    private boolean mInPageLoad;
    // The last reported progress of the current page
    private int mPageLoadProgress;
    // The time the load started, used to find load page time
    private long mLoadStartTime;
    // Application identifier used to find tabs that another application wants
    // to reuse.
    private String mAppId;
    // flag to indicate if tab should be closed on back
    private boolean mCloseOnBack;
    // Keep the original url around to avoid killing the old WebView if the url
    // has not changed.
    // Error console for the tab
    private ErrorConsoleView mErrorConsole;
    // The listener that gets invoked when a download is started from the
    // mMainView
    private final DownloadListener mDownloadListener;
    // Listener used to know when we move forward or back in the history list.
    private final WebBackForwardListClient mWebBackForwardListClient;
    private DataController mDataController;
    // State of the auto-login request.
    private DeviceAccountLogin mDeviceAccountLogin;

    // AsyncTask for downloading touch icons
    DownloadTouchIcon mTouchIconLoader;

    private BrowserSettings mSettings;
    private int mCaptureWidth;
    private int mCaptureHeight;
    private Bitmap mCapture;
    private Handler mHandler;
    private int mCount;

    /**
     * See {@link #clearBackStackWhenItemAdded(String)}.
     */
    private Pattern mClearHistoryUrlPattern;

    private static synchronized Bitmap getDefaultFavicon(Context context) {
        if(Browser.LOG_SYNC_ENABLED) {
            Log.i(LOGTAG_SYNC, "getDefaultFavicon()," + 
                    " synchronized getDefaultFavicon, IN");
        }

        if (sDefaultFavicon == null) {
            sDefaultFavicon = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.app_web_browser_sm);
        }
        if(Browser.LOG_SYNC_ENABLED) {
            Log.i(LOGTAG_SYNC, "getDefaultFavicon()," + 
                    " synchronized getDefaultFavicon, OUT");
        }
        return sDefaultFavicon;
    }

    // All the state needed for a page
    protected static class PageState {
        String mUrl;
        String mOriginalUrl;
        String mTitle;
        SecurityState mSecurityState;
        // This is non-null only when mSecurityState is SECURITY_STATE_BAD_CERTIFICATE.
        SslError mSslCertificateError;
        Bitmap mFavicon;
        boolean mIsBookmarkedSite;
        boolean mIncognito;

        PageState(Context c, boolean incognito) {

            mIncognito = incognito;
            if (mIncognito) {

                mOriginalUrl = mUrl = "browser:incognito";
                mTitle = c.getString(R.string.new_incognito_tab);
            } else {
                mOriginalUrl = mUrl = "";
                mTitle = c.getString(R.string.new_tab);
            }
            mSecurityState = SecurityState.SECURITY_STATE_NOT_SECURE;
        }

        PageState(Context c, boolean incognito, String url, Bitmap favicon) {

            mIncognito = incognito;
            mOriginalUrl = mUrl = url;
            if (URLUtil.isHttpsUrl(url)) {
                mSecurityState = SecurityState.SECURITY_STATE_SECURE;
            } else {
                mSecurityState = SecurityState.SECURITY_STATE_NOT_SECURE;
            }
            mFavicon = favicon;
        }

    }

    // The current/loading page's state
    protected PageState mCurrentState;

    // Used for saving and restoring each Tab
    static final String ID = "ID";
    static final String CURRURL = "currentUrl";
    static final String CURRTITLE = "currentTitle";
    static final String PARENTTAB = "parentTab";
    static final String APPID = "appid";
    static final String INCOGNITO = "privateBrowsingEnabled";
    static final String USERAGENT = "useragent";
    static final String CLOSEFLAG = "closeOnBack";

    // -------------------------------------------------------------------------

    /**
     * Private information regarding the latest voice search.  If the Tab is not
     * in voice search mode, this will be null.
     */
    private VoiceSearchData mVoiceSearchData;
    /**
     * Remove voice search mode from this tab.
     */
    public void revertVoiceSearchMode() {

        if (mVoiceSearchData != null) {
            mVoiceSearchData = null;
            if (mInForeground) {

                mWebViewController.revertVoiceSearchMode(this);
            }
        }
    }

    /**
     * Return whether the tab is in voice search mode.
     */
    public boolean isInVoiceSearchMode() {

        return mVoiceSearchData != null;
    }
    /**
     * Return true if the Tab is in voice search mode and the voice search
     * Intent came with a String identifying that Google provided the Intent.
     */
    public boolean voiceSearchSourceIsGoogle() {

        return mVoiceSearchData != null && mVoiceSearchData.mSourceIsGoogle;
    }
    /**
     * Get the title to display for the current voice search page.  If the Tab
     * is not in voice search mode, return null.
     */
    public String getVoiceDisplayTitle() {

        if (mVoiceSearchData == null) return null;
        return mVoiceSearchData.mLastVoiceSearchTitle;
    }
    /**
     * Get the latest array of voice search results, to be passed to the
     * BrowserProvider.  If the Tab is not in voice search mode, return null.
     */
    public ArrayList<String> getVoiceSearchResults() {

        if (mVoiceSearchData == null) return null;
        return mVoiceSearchData.mVoiceSearchResults;
    }
    /**
     * Activate voice search mode.
     * @param intent Intent which has the results to use, or an index into the
     *      results when reusing the old results.
     */
    /* package */ void activateVoiceSearchMode(Intent intent) {

        int index = 0;
        ArrayList<String> results = intent.getStringArrayListExtra(
                    RecognizerResultsIntent.EXTRA_VOICE_SEARCH_RESULT_STRINGS);
        if (results != null) {
            ArrayList<String> urls = intent.getStringArrayListExtra(
                        RecognizerResultsIntent.EXTRA_VOICE_SEARCH_RESULT_URLS);
            ArrayList<String> htmls = intent.getStringArrayListExtra(
                        RecognizerResultsIntent.EXTRA_VOICE_SEARCH_RESULT_HTML);
            ArrayList<String> baseUrls = intent.getStringArrayListExtra(
                        RecognizerResultsIntent
                        .EXTRA_VOICE_SEARCH_RESULT_HTML_BASE_URLS);
            // This tab is now entering voice search mode for the first time, or
            // a new voice search was done.
            int size = results.size();
            if (urls == null || size != urls.size()) {
                throw new AssertionError("improper extras passed in Intent");
            }
            if (htmls == null || htmls.size() != size || baseUrls == null ||
                    (baseUrls.size() != size && baseUrls.size() != 1)) {
                // If either of these arrays are empty/incorrectly sized, ignore
                // them.
                htmls = null;
                baseUrls = null;
            }
            mVoiceSearchData = new VoiceSearchData(results, urls, htmls,
                    baseUrls);
            mVoiceSearchData.mHeaders = intent.getParcelableArrayListExtra(
                    RecognizerResultsIntent
                    .EXTRA_VOICE_SEARCH_RESULT_HTTP_HEADERS);
            mVoiceSearchData.mSourceIsGoogle = intent.getBooleanExtra(
                    VoiceSearchData.SOURCE_IS_GOOGLE, false);
            mVoiceSearchData.mVoiceSearchIntent = new Intent(intent);
        }
        String extraData = intent.getStringExtra(
                SearchManager.EXTRA_DATA_KEY);
        if (extraData != null) {
            index = Integer.parseInt(extraData);
            if (index >= mVoiceSearchData.mVoiceSearchResults.size()) {
                throw new AssertionError("index must be less than "
                        + "size of mVoiceSearchResults");
            }
            if (mVoiceSearchData.mSourceIsGoogle) {

                Intent logIntent = new Intent(
                        LoggingEvents.ACTION_LOG_EVENT);
                logIntent.putExtra(LoggingEvents.EXTRA_EVENT,
                        LoggingEvents.VoiceSearch.N_BEST_CHOOSE);
                logIntent.putExtra(
                        LoggingEvents.VoiceSearch.EXTRA_N_BEST_CHOOSE_INDEX,
                        index);
                mContext.sendBroadcast(logIntent);
            }
            if (mVoiceSearchData.mVoiceSearchIntent != null) {
                // Copy the Intent, so that each history item will have its own
                // Intent, with different (or none) extra data.
                Intent latest = new Intent(mVoiceSearchData.mVoiceSearchIntent);
                latest.putExtra(SearchManager.EXTRA_DATA_KEY, extraData);
                mVoiceSearchData.mVoiceSearchIntent = latest;
            }
        }
        mVoiceSearchData.mLastVoiceSearchTitle
                = mVoiceSearchData.mVoiceSearchResults.get(index);
        if (mInForeground) {

            mWebViewController.activateVoiceSearchMode(
                    mVoiceSearchData.mLastVoiceSearchTitle,
                    mVoiceSearchData.mVoiceSearchResults);
        }
        if (mVoiceSearchData.mVoiceSearchHtmls != null) {
            // When index was found it was already ensured that it was valid
            String uriString = mVoiceSearchData.mVoiceSearchHtmls.get(index);
            if (uriString != null) {
                Uri dataUri = Uri.parse(uriString);
                if (RecognizerResultsIntent.URI_SCHEME_INLINE.equals(
                        dataUri.getScheme())) {
                    // If there is only one base URL, use it.  If there are
                    // more, there will be one for each index, so use the base
                    // URL corresponding to the index.
                    String baseUrl = mVoiceSearchData.mVoiceSearchBaseUrls.get(
                            mVoiceSearchData.mVoiceSearchBaseUrls.size() > 1 ?
                            index : 0);
                    mVoiceSearchData.mLastVoiceSearchUrl = baseUrl;
                    mMainView.loadDataWithBaseURL(baseUrl,
                            uriString.substring(RecognizerResultsIntent
                            .URI_SCHEME_INLINE.length() + 1), "text/html",
                            "utf-8", baseUrl);
                    return;
                }
            }
        }
        mVoiceSearchData.mLastVoiceSearchUrl
                = mVoiceSearchData.mVoiceSearchUrls.get(index);
        if (null == mVoiceSearchData.mLastVoiceSearchUrl) {
            mVoiceSearchData.mLastVoiceSearchUrl = UrlUtils.smartUrlFilter(
                    mVoiceSearchData.mLastVoiceSearchTitle);
        }
        Map<String, String> headers = null;
        if (mVoiceSearchData.mHeaders != null) {
            int bundleIndex = mVoiceSearchData.mHeaders.size() == 1 ? 0
                    : index;
            Bundle bundle = mVoiceSearchData.mHeaders.get(bundleIndex);
            if (bundle != null && !bundle.isEmpty()) {
                Iterator<String> iter = bundle.keySet().iterator();
                headers = new HashMap<String, String>();
                while (iter.hasNext()) {
                    String key = iter.next();
                    headers.put(key, bundle.getString(key));
                }
            }
        }
        mMainView.loadUrl(mVoiceSearchData.mLastVoiceSearchUrl, headers);
    }
    /* package */ static class VoiceSearchData {
        public VoiceSearchData(ArrayList<String> results,
                ArrayList<String> urls, ArrayList<String> htmls,
                ArrayList<String> baseUrls) {
            mVoiceSearchResults = results;
            mVoiceSearchUrls = urls;
            mVoiceSearchHtmls = htmls;
            mVoiceSearchBaseUrls = baseUrls;
        }
        /*
         * ArrayList of suggestions to be displayed when opening the
         * SearchManager
         */
        public ArrayList<String> mVoiceSearchResults;
        /*
         * ArrayList of urls, associated with the suggestions in
         * mVoiceSearchResults.
         */
        public ArrayList<String> mVoiceSearchUrls;
        /*
         * ArrayList holding content to load for each item in
         * mVoiceSearchResults.
         */
        public ArrayList<String> mVoiceSearchHtmls;
        /*
         * ArrayList holding base urls for the items in mVoiceSearchResults.
         * If non null, this will either have the same size as
         * mVoiceSearchResults or have a size of 1, in which case all will use
         * the same base url
         */
        public ArrayList<String> mVoiceSearchBaseUrls;
        /*
         * The last url provided by voice search.  Used for comparison to see if
         * we are going to a page by some method besides voice search.
         */
        public String mLastVoiceSearchUrl;
        /**
         * The last title used for voice search.  Needed to update the title bar
         * when switching tabs.
         */
        public String mLastVoiceSearchTitle;
        /**
         * Whether the Intent which turned on voice search mode contained the
         * String signifying that Google was the source.
         */
        public boolean mSourceIsGoogle;
        /**
         * List of headers to be passed into the WebView containing location
         * information
         */
        public ArrayList<Bundle> mHeaders;
        /**
         * The Intent used to invoke voice search.  Placed on the
         * WebHistoryItem so that when coming back to a previous voice search
         * page we can again activate voice search.
         */
        public Intent mVoiceSearchIntent;
        /**
         * String used to identify Google as the source of voice search.
         */
        public static String SOURCE_IS_GOOGLE
                = "android.speech.extras.SOURCE_IS_GOOGLE";
    }

    // Container class for the next error dialog that needs to be displayed
    private class ErrorDialog {
        public final int mTitle;
        public final String mDescription;
        public final int mError;
        ErrorDialog(int title, String desc, int error) {

            mTitle = title;
            mDescription = desc;
            mError = error;
        }
    }

    private void processNextError() {

        if (mQueuedErrors == null) {
            return;
        }
        // The first one is currently displayed so just remove it.
        mQueuedErrors.removeFirst();
        if (mQueuedErrors.size() == 0) {
            mQueuedErrors = null;
            return;
        }
        showError(mQueuedErrors.getFirst());
    }

    private DialogInterface.OnDismissListener mDialogListener =
            new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface d) {

                    processNextError();
                }
            };
    private LinkedList<ErrorDialog> mQueuedErrors;

    private void queueError(int err, String desc) {

        if (mQueuedErrors == null) {
            mQueuedErrors = new LinkedList<ErrorDialog>();
        }
        for (ErrorDialog d : mQueuedErrors) {
            if (d.mError == err) {
                // Already saw a similar error, ignore the new one.
                return;
            }
        }
        ErrorDialog errDialog = new ErrorDialog(
                err == WebViewClient.ERROR_FILE_NOT_FOUND ?
                R.string.browserFrameFileErrorLabel :
                R.string.browserFrameNetworkErrorLabel,
                desc, err);
        mQueuedErrors.addLast(errDialog);

        // Show the dialog now if the queue was empty and it is in foreground
        if (mQueuedErrors.size() == 1 && mInForeground) {
            showError(errDialog);
        }
    }

    private void showError(ErrorDialog errDialog) {
       if(errDialog.mDescription.equals(mContext.getResources().getString(com.android.internal.R.string.httpErrorProxyAuth))){
           if (mInForeground) {

               AlertDialog d = new AlertDialog.Builder(mContext)
                       .setTitle(errDialog.mTitle)
                       .setMessage(errDialog.mDescription)
                       .setPositiveButton(R.string.ok, null)
                       .create();
               d.setOnDismissListener(mDialogListener);
               d.show();
           }
       }else {
           return;
       }

        // Just disable the error dialog.
        /* if (mInForeground) {

            AlertDialog d = new AlertDialog.Builder(mContext)
                    .setTitle(errDialog.mTitle)
                    .setMessage(errDialog.mDescription)
                    .setPositiveButton(R.string.ok, null)
                    .create();
            d.setOnDismissListener(mDialogListener);
            d.show();
        }
        */
    }

    // -------------------------------------------------------------------------
    // WebViewClient implementation for the main WebView
    // -------------------------------------------------------------------------

    private final WebViewClient mWebViewClient = new WebViewClient() {

        private Message mDontResend;
        private Message mResend;

        private boolean providersDiffer(String url, String otherUrl) {

            Uri uri1 = Uri.parse(url);
            Uri uri2 = Uri.parse(otherUrl);
            return !uri1.getEncodedAuthority().equals(uri2.getEncodedAuthority());
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if(BrowserSettings.PAGE_TURN_LOAD_TWICE) Log.v(
                    PhoneUi.LOGTAG_TWICE, "onPageStarted url:"+url);
		    mInPageLoad = true;
            mPageLoadProgress = INITIAL_PROGRESS;
            mCurrentState = new PageState(mContext,
                    view.isPrivateBrowsingEnabled(), url, favicon);
            mLoadStartTime = SystemClock.uptimeMillis();
            if (mVoiceSearchData != null
                    && providersDiffer(url, mVoiceSearchData.mLastVoiceSearchUrl)) {
                if (mVoiceSearchData.mSourceIsGoogle) {

                    Intent i = new Intent(LoggingEvents.ACTION_LOG_EVENT);
                    i.putExtra(LoggingEvents.EXTRA_FLUSH, true);
                    mContext.sendBroadcast(i);
                }
                revertVoiceSearchMode();
            }


            // If we start a touch icon load and then load a new page, we don't
            // want to cancel the current touch icon loader. But, we do want to
            // create a new one when the touch icon url is known.
            if (mTouchIconLoader != null) {
                mTouchIconLoader.mTab = null;
                mTouchIconLoader = null;
            }

            // reset the error console
            if (mErrorConsole != null) {
                mErrorConsole.clearErrorMessages();
                if (mWebViewController.shouldShowErrorConsole()) {
                    mErrorConsole.showConsole(ErrorConsoleView.SHOW_NONE);
                }
            }

            // Cancel the auto-login process.
            if (mDeviceAccountLogin != null) {
                mDeviceAccountLogin.cancel();
                mDeviceAccountLogin = null;
                mWebViewController.hideAutoLogin(Tab.this);
            }

            // finally update the UI in the activity if it is in the foreground
            mWebViewController.onPageStarted(Tab.this, view, favicon);

            updateBookmarkedStatus();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if(BrowserSettings.PAGE_TURN_LOAD_TWICE) 
                Log.v(PhoneUi.LOGTAG_TWICE, "onPageFinished url:"+url);

            // The 163 automobile page can not be displayed fully while 
            // user goes back to it. We must "update" its display by sending 
            // fake scrolling up/down events to it.
            if (url != null && url.contains("http://3g.163.com/touch/auto/") &&
                    !mHandler.hasMessages(UPDATE_SCREEN_FOR_163_AUTO)) {
                if(ENABLE_LOG) Log.v(LOGTAG, "enter onPageFinished(), refresh page");

                Message message = mHandler.obtainMessage(UPDATE_SCREEN_FOR_163_AUTO);
                message.obj = view;
                mHandler.sendMessageDelayed(message, 2000);
            }		

            if (!mHandler.hasMessages(UPDATE_SCREEN_DATA)) {
                endRxBytes = TrafficStats.getUidRxBytes(getAppUid());
                endTxBytes = TrafficStats.getUidTxBytes(getAppUid());

                Message message = mHandler.obtainMessage(UPDATE_SCREEN_DATA);
                message.obj = new Object[]{view, url};
                mHandler.sendMessageDelayed(message, 500);
            }		
            mWebViewController.onLoadPageFinished(Tab.this);
            if (!mInPageLoad) {
                // In page navigation links (www.something.com#footer) will
                // trigger an onPageFinished which we don't care about.
                return;
            }
            if (!isPrivateBrowsingEnabled()) {
                LogTag.logPageFinishedLoading(
                        url, SystemClock.uptimeMillis() - mLoadStartTime);
            }
            syncCurrentState(view, url);
            mWebViewController.onPageFinished(Tab.this);
        }

        public void onUrlPageFinished(WebView view, String url) {
            mWebViewController.onLoadPageFinished(Tab.this);
        };
        // return true if want to hijack the url to let another app to handle it
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (voiceSearchSourceIsGoogle()) {
                // This method is called when the user clicks on a link.
                // VoiceSearchMode is turned off when the user leaves the
                // Google results page, so at this point the user must be on
                // that page.  If the user clicked a link on that page, assume
                // that the voice search was effective, and broadcast an Intent
                // so a receiver can take note of that fact.
                Intent logIntent = new Intent(LoggingEvents.ACTION_LOG_EVENT);
                logIntent.putExtra(LoggingEvents.EXTRA_EVENT,
                        LoggingEvents.VoiceSearch.RESULT_CLICKED);
                mContext.sendBroadcast(logIntent);
            }
            if (mInForeground) {

                return mWebViewController.shouldOverrideUrlLoading(Tab.this,
                        view, url);
            } else {
                return false;
            }
        }

        /**
         * Updates the security state. This method is called when we discover
         * another resource to be loaded for this page (for example,
         * javascript). While we update the security state, we do not update
         * the lock icon until we are done loading, as it is slightly more
         * secure this way.
         */
        @Override
        public void onLoadResource(WebView view, String url) {
            if (url != null && url.length() > 0) {
                mWebViewController.onLoadResource(view, url);

                // It is only if the page claims to be secure that we may have
                // to update the security state:
                if (mCurrentState.mSecurityState == SecurityState.SECURITY_STATE_SECURE) {
                    // If NOT a 'safe' url, change the state to mixed content!
                    if (!(URLUtil.isHttpsUrl(url) || URLUtil.isDataUrl(url)
                            || URLUtil.isAboutUrl(url))) {
                        mCurrentState.mSecurityState = SecurityState.SECURITY_STATE_MIXED;
                    }
                }
            }
        }

        /**
         * Show a dialog informing the user of the network error reported by
         * WebCore if it is in the foreground.
         */
        @Override
        public void onReceivedError(WebView view, int errorCode,
                String description, String failingUrl) {
            Log.e(LOGTAG, "onReceivedError: " + errorCode + ", " 
                    + failingUrl + ", " + description);

            if (errorCode != WebViewClient.ERROR_HOST_LOOKUP &&
                    errorCode != WebViewClient.ERROR_CONNECT &&
                    errorCode != WebViewClient.ERROR_BAD_URL &&
                    errorCode != WebViewClient.ERROR_UNSUPPORTED_SCHEME &&
                    errorCode != WebViewClient.ERROR_FILE &&
                    errorCode != WebViewClient.ERROR_UNKNOWN // Ignore unknown error
                    ) {
                queueError(errorCode, description);

                // Don't log URLs when in private browsing mode
                if (!isPrivateBrowsingEnabled()) {
                    Log.e(LOGTAG, "onReceivedError " + errorCode + " " + failingUrl
                        + " " + description);
                }
            }
        }

        /**
         * Check with the user if it is ok to resend POST data as the page they
         * are trying to navigate to is the result of a POST.
         */
        @Override
        public void onFormResubmission(WebView view, final Message dontResend,
                                       final Message resend) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onFormResubmission()");

            if (!mInForeground) {
                dontResend.sendToTarget();
                return;
            }
            if (mDontResend != null) {
                Log.w(LOGTAG, "onFormResubmission should not be called again "
                        + "while dialog is still up");
                dontResend.sendToTarget();
                return;
            }
            mDontResend = dontResend;
            mResend = resend;
            new AlertDialog.Builder(mContext).setTitle(
                    R.string.browserFrameFormResubmitLabel).setMessage(
                    R.string.browserFrameFormResubmitMessage)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                    if (mResend != null) {
                                        mResend.sendToTarget();
                                        mResend = null;
                                        mDontResend = null;
                                    }
                                }
                            }).setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                    if (mDontResend != null) {
                                        mDontResend.sendToTarget();
                                        mResend = null;
                                        mDontResend = null;
                                    }
                                }
                            }).setOnCancelListener(new OnCancelListener() {

                        public void onCancel(DialogInterface dialog) {
                            if (mDontResend != null) {
                                mDontResend.sendToTarget();
                                mResend = null;
                                mDontResend = null;
                            }
                        }
                    }).show();
        }

        /**
         * Insert the url into the visited history database.
         * @param url The url to be inserted.
         * @param isReload True if this url is being reloaded.
         * FIXME: Not sure what to do when reloading the page.
         */
        @Override
        public void doUpdateVisitedHistory(WebView view, String url,
                boolean isReload) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter doUpdateVisitedHistory()");

            mWebViewController.doUpdateVisitedHistory(Tab.this, isReload);
        }

        /**
         * Displays SSL error(s) dialog to the user.
         */
        @Override
        public void onReceivedSslError(final WebView view,
                final SslErrorHandler handler, final SslError error) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onReceivedSslError()");
            if(error.getUrl().equals("https://b2c.icbc.com.cn/")){
                handler.proceed();
                return;
            }
            if (!mInForeground) {
                handler.cancel();
                setSecurityState(SecurityState.SECURITY_STATE_NOT_SECURE);
                return;
            }
            if (mSettings.showSecurityWarnings()) {
                new AlertDialog.Builder(mContext)
                    .setTitle(R.string.security_warning)
                    .setMessage(R.string.ssl_warnings_header)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(R.string.ssl_continue,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int whichButton) {
                                handler.proceed();
                                handleProceededAfterSslError(error);
                            }
                        })
                    .setNeutralButton(R.string.view_certificate,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int whichButton) {
                                mWebViewController.showSslCertificateOnError(
                                        view, handler, error);
                            }
                        })
                    .setNegativeButton(R.string.ssl_go_back,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int whichButton) {
                                dialog.cancel();
                            }
                        })
                    .setOnCancelListener(
                        new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                handler.cancel();
                                setSecurityState(SecurityState.SECURITY_STATE_NOT_SECURE);
                                mWebViewController.onUserCanceledSsl(Tab.this);
                            }
                        })
                    .show();
            } else {
                handler.proceed();
            }
        }

        /**
         * Called when an SSL error occurred while loading a resource, but the
         * WebView but chose to proceed anyway based on a decision retained
         * from a previous response to onReceivedSslError(). We update our
         * security state to reflect this.
         */
        @Override
        public void onProceededAfterSslError(WebView view, SslError error) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onProceededAfterSslError()");
            if(error.getUrl().contains("b2c.icbc.com.cn")){
                return;
            }
            handleProceededAfterSslError(error);
        }

        /**
         * Displays client certificate request to the user.
         */
        @Override
        public void onReceivedClientCertRequest(final WebView view,
                final ClientCertRequestHandler handler, final String host_and_port) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onReceivedClientCertRequest()");

            if (!mInForeground) {
                handler.ignore();
                return;
            }
            int colon = host_and_port.lastIndexOf(':');
            String host;
            int port;
            if (colon == -1) {
                host = host_and_port;
                port = -1;
            } else {
                String portString = host_and_port.substring(colon + 1);
                try {
                    port = Integer.parseInt(portString);
                    host = host_and_port.substring(0, colon);
                } catch  (NumberFormatException e) {

                    host = host_and_port;
                    port = -1;
                }
            }
            KeyChain.choosePrivateKeyAlias(
                    mWebViewController.getActivity(), new KeyChainAliasCallback() {

                @Override public void alias(String alias) {
                    if (alias == null) {
                        handler.cancel();
                        return;
                    }
                    new KeyChainLookup(mContext, handler, alias).execute();
                }
            }, null, null, host, port, null);
        }

        /**
         * Handles an HTTP authentication request.
         *
         * @param handler The authentication handler
         * @param host The host
         * @param realm The realm
         */
        @Override
        public void onReceivedHttpAuthRequest(WebView view,
                final HttpAuthHandler handler, final String host,
                final String realm) {
            mWebViewController.onReceivedHttpAuthRequest(Tab.this, view, handler, host, realm);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view,
                String url) {
            if(ENABLE_LOG) Log.v(LOGTAG, "enter shouldInterceptRequest()");

            if(mContext != null && mWebViewController != null && 
                    (mWebViewController instanceof Controller)) {
                //this for cts BrowserTest testBrowserPrivateDataAccess
            	if(url.contains("file:///data/data/com.android.cts.stub/files/target.txt")){
            		return new WebResourceResponse(null, null, null);
            	}
            	/*
                if(!((Controller)mWebViewController).canAccessNetwork()) {
                    try {
                        InputStream ins = mContext.getAssets().open("html/empty.html");
                        if(ins != null) {
                            return new WebResourceResponse("text/html", "utf-8", ins);
                        }
                    } catch (java.io.IOException e) {
                        Log.e(LOGTAG, "", e);
                    }
                }
                */
            }

            WebResourceResponse res = HomeProvider.shouldInterceptRequest(
                    mContext, url);
            return res;
        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter shouldOverrideKeyEvent()");

            if (!mInForeground) {
                return false;
            }
            return mWebViewController.shouldOverrideKeyEvent(event);
        }

        @Override
        public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onUnhandledKeyEvent()");

            if (!mInForeground) {
                return;
            }
            mWebViewController.onUnhandledKeyEvent(event);
        }

        @Override
        public void onReceivedLoginRequest(WebView view, String realm,
                String account, String args) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onReceivedLoginRequest()");

            new DeviceAccountLogin(mWebViewController.getActivity(), view, Tab.this, mWebViewController)
                    .handleLogin(realm, account, args);
        }

    };

    private void syncCurrentState(WebView view, String url) {
        // Sync state (in case of stop/timeout)
        mCurrentState.mUrl = view.getUrl();
        if (mCurrentState.mUrl == null) {
            mCurrentState.mUrl = "";
        }
        mCurrentState.mOriginalUrl = view.getOriginalUrl();
        mCurrentState.mTitle = view.getTitle();
        /* Fix CTS error: testAccessAllowFileAccess
           junit.framework.ComparisonFailure: expected:<Webpage not available> 
           but was:<null> at android.webkit.cts.WebSettingsTest.testAccessAllowFileAccess
           (WebSettingsTest.java:235) 
         */
        if("Webpage not available".equals(mCurrentState.mTitle)) {
            mCurrentState.mTitle = "";
        }
        mCurrentState.mFavicon = view.getFavicon();
        if (!URLUtil.isHttpsUrl(mCurrentState.mUrl)) {
            // In case we stop when loading an HTTPS page from an HTTP page
            // but before a provisional load occurred
            mCurrentState.mSecurityState = SecurityState.SECURITY_STATE_NOT_SECURE;
            mCurrentState.mSslCertificateError = null;
        }
        mCurrentState.mIncognito = view.isPrivateBrowsingEnabled();
    }

    // Called by DeviceAccountLogin when the Tab needs to have the auto-login UI
    // displayed.
    void setDeviceAccountLogin(DeviceAccountLogin login) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter setDeviceAccountLogin()");

        mDeviceAccountLogin = login;
    }

    // Returns non-null if the title bar should display the auto-login UI.
    DeviceAccountLogin getDeviceAccountLogin() {
        return mDeviceAccountLogin;
    }

    // -------------------------------------------------------------------------
    // WebChromeClient implementation for the main WebView
    // -------------------------------------------------------------------------

    private final WebChromeClient mWebChromeClient = new WebChromeClient() {

        // Helper method to create a new tab or sub window.
        private void createWindow(final boolean dialog, final Message msg) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter createWindow()");

            WebView.WebViewTransport transport =
                    (WebView.WebViewTransport) msg.obj;
            if (dialog) {

                createSubWindow();
                mWebViewController.attachSubWindow(Tab.this);
                transport.setWebView(mSubView);
            } else {
                final Tab newTab = mWebViewController.openTab(null,
                        Tab.this, true, true);
                transport.setWebView(newTab.getWebView());
            }
            msg.sendToTarget();
        }

        @Override
        public boolean onCreateWindow(WebView view, final boolean dialog,
                final boolean userGesture, final Message resultMsg) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onCreateWindow()");

            // only allow new window or sub window for the foreground case
            if (!mInForeground) {
                return false;
            }
            // Short-circuit if we can't create any more tabs or sub windows.
            if (dialog && mSubView != null) {
                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.too_many_subwindows_dialog_title)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage(R.string.too_many_subwindows_dialog_message)
                        .setPositiveButton(R.string.ok, null)
                        .show();
                return false;
            } else if (!mWebViewController.getTabControl().canCreateNewTab()) {
                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.too_many_windows_dialog_title)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage(R.string.too_many_windows_dialog_message)
                        .setPositiveButton(R.string.ok, null)
                        .show();
                return false;
            }

            // Short-circuit if this was a user gesture.
            if (userGesture) {

                createWindow(dialog, resultMsg);
                return true;
            }

            // Allow the popup and create the appropriate window.
            final AlertDialog.OnClickListener allowListener =
                    new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface d,
                                int which) {
                            createWindow(dialog, resultMsg);
                        }
                    };

            // Block the popup by returning a null WebView.
            final AlertDialog.OnClickListener blockListener =
                    new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface d, int which) {
                            resultMsg.sendToTarget();
                        }
                    };

            // Build a confirmation dialog to display to the user.
            final AlertDialog d =
                    new AlertDialog.Builder(mContext)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage(R.string.popup_window_attempt)
                    .setPositiveButton(R.string.allow, allowListener)
                    .setNegativeButton(R.string.block, blockListener)
                    .setCancelable(false)
                    .create();

            // Show the confirmation dialog.
            d.show();
            return true;
        }

        @Override
        public void onRequestFocus(WebView view) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onRequestFocus()");

            if (!mInForeground) {
                mWebViewController.switchToTab(Tab.this);
            }
        }

        @Override
        public void onCloseWindow(WebView window) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onCloseWindow()");

            if (mParent != null) {
                // JavaScript can only close popup window.
                if (mInForeground) {

                    mWebViewController.switchToTab(mParent);
                }
                mWebViewController.closeTab(Tab.this);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
		if(ENABLE_LOG) Log.v(LOGTAG, 
                "enter onProgressChanged(): " + newProgress);

            mPageLoadProgress = newProgress;
            if (newProgress == 100) {
                mInPageLoad = false;
            }
            mWebViewController.onProgressChanged(Tab.this);
        }

        @Override
        public void onReceivedTitle(WebView view, final String title) {
            mCurrentState.mTitle = title;
            mWebViewController.onReceivedTitle(Tab.this, title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onReceivedIcon()");

            mCurrentState.mFavicon = icon;
            mWebViewController.onFavicon(Tab.this, view, icon);
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url,
                boolean precomposed) {
            final ContentResolver cr = mContext.getContentResolver();
            // Let precomposed icons take precedence over non-composed
            // icons.
            if (precomposed && mTouchIconLoader != null) {
                mTouchIconLoader.cancel(false);
                mTouchIconLoader = null;
            }
            // Have only one async task at a time.
            if (mTouchIconLoader == null) {
                mTouchIconLoader = new DownloadTouchIcon(Tab.this,
                        mContext, cr, view);
                mTouchIconLoader.execute(url);
            }
        }

        @Override
        public void onShowCustomView(View view,
                WebChromeClient.CustomViewCallback callback) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onShowCustomView()");

            Activity activity = mWebViewController.getActivity();
            if (activity != null) {
                onShowCustomView(view, activity.getRequestedOrientation(), callback);
            }
        }

        @Override
        public void onShowCustomView(View view, int requestedOrientation,
                WebChromeClient.CustomViewCallback callback) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onShowCustomView()");

            if (mInForeground) mWebViewController.showCustomView(Tab.this, view,
                    requestedOrientation, callback);
        }

        @Override
        public void onHideCustomView() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onHideCustomView()");

            if (mInForeground) mWebViewController.hideCustomView();
        }

        /**
         * The origin has exceeded its database quota.
         * @param url the URL that exceeded the quota
         * @param databaseIdentifier the identifier of the database on which the
         *            transaction that caused the quota overflow was run
         * @param currentQuota the current quota for the origin.
         * @param estimatedSize the estimated size of the database.
         * @param totalUsedQuota is the sum of all origins' quota.
         * @param quotaUpdater The callback to run when a decision to allow or
         *            deny quota has been made. Don't forget to call this!
         */
        @Override
        public void onExceededDatabaseQuota(String url,
            String databaseIdentifier, long currentQuota, long estimatedSize,
            long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {

            if(ENABLE_LOG) Log.v(LOGTAG, "enter onExceededDatabaseQuota()");
            mSettings.getWebStorageSizeManager()
                    .onExceededDatabaseQuota(url, databaseIdentifier,
                            currentQuota, estimatedSize, totalUsedQuota,
                            quotaUpdater);
        }

        /**
         * The Application Cache has exceeded its max size.
         * @param spaceNeeded is the amount of disk space that would be needed
         *            in order for the last appcache operation to succeed.
         * @param totalUsedQuota is the sum of all origins' quota.
         * @param quotaUpdater A callback to inform the WebCore thread that a
         *            new app cache size is available. This callback must always
         *            be executed at some point to ensure that the sleeping
         *            WebCore thread is woken up.
         */
        @Override
        public void onReachedMaxAppCacheSize(long spaceNeeded,
                long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onReachedMaxAppCacheSize()");

            mSettings.getWebStorageSizeManager()
                    .onReachedMaxAppCacheSize(spaceNeeded, totalUsedQuota,
                            quotaUpdater);
        }

        /**
         * Instructs the browser to show a prompt to ask the user to set the
         * Geolocation permission state for the specified origin.
         * @param origin The origin for which Geolocation permissions are
         *     requested.
         * @param callback The callback to call once the user has set the
         *     Geolocation permission state.
         */
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                GeolocationPermissions.Callback callback) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onGeolocationPermissionsShowPrompt()");

            if (mInForeground) {

                getGeolocationPermissionsPrompt().show(origin, callback);
            }
        }

        /**
         * Instructs the browser to hide the Geolocation permissions prompt.
         */
        @Override
        public void onGeolocationPermissionsHidePrompt() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onGeolocationPermissionsHidePrompt()");

            if (mInForeground && mGeolocationPermissionsPrompt != null) {
                mGeolocationPermissionsPrompt.hide();
            }
        }

        /* Adds a JavaScript error message to the system log and if the JS
         * console is enabled in the about:debug options, to that console
         * also.
         * @param consoleMessage the message object.
         */
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onConsoleMessage()");

            if (mInForeground) {

                // call getErrorConsole(true) so it will create one if needed
                ErrorConsoleView errorConsole = getErrorConsole(true);
                errorConsole.addErrorMessage(consoleMessage);
                if (mWebViewController.shouldShowErrorConsole()
                        && errorConsole.getShowState() !=
                            ErrorConsoleView.SHOW_MAXIMIZED) {
                    errorConsole.showConsole(ErrorConsoleView.SHOW_MINIMIZED);
                }
            }

            // Don't log console messages in private browsing mode
            if (isPrivateBrowsingEnabled()) return true;

            String message = "Console: " + consoleMessage.message() + " "
                    + consoleMessage.sourceId() +  ":"
                    + consoleMessage.lineNumber();

            switch (consoleMessage.messageLevel()) {
                case TIP:
                    Log.v(CONSOLE_LOGTAG, message);
                    break;
                case LOG:
                    Log.i(CONSOLE_LOGTAG, message);
                    break;
                case WARNING:
                    Log.w(CONSOLE_LOGTAG, message);
                    break;
                case ERROR:
                    Log.e(CONSOLE_LOGTAG, message);
                    break;
                case DEBUG:
                    Log.d(CONSOLE_LOGTAG, message);
                    break;
            }

            return true;
        }

        /**
         * Ask the browser for an icon to represent a <video> element.
         * This icon will be used if the Web page did not specify a poster attribute.
         * @return Bitmap The icon or null if no such icon is available.
         */
        @Override
        public Bitmap getDefaultVideoPoster() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter getDefaultVideoPoster()");

            if (mInForeground) {

                return mWebViewController.getDefaultVideoPoster();
            }
            return null;
        }

        /**
         * Ask the host application for a custom progress view to show while
         * a <video> is loading.
         * @return View The progress view.
         */
        @Override
        public View getVideoLoadingProgressView() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter getVideoLoadingProgressView()");

            if (mInForeground) {

                return mWebViewController.getVideoLoadingProgressView();
            }
            return null;
        }

        @Override
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter openFileChooser()");

            if (mInForeground) {

                mWebViewController.openFileChooser(uploadMsg, acceptType);
            } else {
                uploadMsg.onReceiveValue(null);
            }
        }

        /**
         * Deliver a list of already-visited URLs
         */
        @Override
        public void getVisitedHistory(final ValueCallback<String[]> callback) {
            mWebViewController.getVisitedHistory(callback);
        }

        @Override
        public void setupAutoFill(Message message) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter setupAutoFill()");

            // Prompt the user to set up their profile.
            final Message msg = message;
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.setup_autofill_dialog, null);

            builder.setView(layout)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        CheckBox disableAutoFill = (CheckBox) layout.findViewById(
                                R.id.setup_autofill_dialog_disable_autofill);

                        if (disableAutoFill.isChecked()) {
                            // Disable autofill and show a toast with how to turn it on again.
                            mSettings.setAutofillEnabled(false);
                            Toast.makeText(mContext,
                                    R.string.autofill_setup_dialog_negative_toast,
                                    Toast.LENGTH_LONG).show();
                        } else {
                            // Take user to the AutoFill profile editor. When they return,
                            // we will send the message that we pass here which will trigger
                            // the form to get filled out with their new profile.
                            mWebViewController.setupAutoFill(msg);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
        }
    };

    // -------------------------------------------------------------------------
    // WebViewClient implementation for the sub window
    // -------------------------------------------------------------------------

    // Subclass of WebViewClient used in subwindows to notify the main
    // WebViewClient of certain WebView activities.
    private static class SubWindowClient extends WebViewClient {
        // The main WebViewClient.
        private final WebViewClient mClient;
        private final WebViewController mController;

        SubWindowClient(WebViewClient client, WebViewController controller) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter SubWindowClient()");

            mClient = client;
            mController = controller;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // Unlike the others, do not call mClient's version, which would
            // change the progress bar.  However, we do want to remove the
            // find or select dialog.
            mController.endActionMode();
        }
        @Override
        public void doUpdateVisitedHistory(WebView view, String url,
                boolean isReload) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter doUpdateVisitedHistory()");

            mClient.doUpdateVisitedHistory(view, url, isReload);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return mClient.shouldOverrideUrlLoading(view, url);
        }
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                SslError error) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onReceivedSslError()");

            mClient.onReceivedSslError(view, handler, error);
        }
        @Override
        public void onReceivedClientCertRequest(WebView view,
                ClientCertRequestHandler handler, String host_and_port) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onReceivedClientCertRequest()");

            mClient.onReceivedClientCertRequest(view, handler, host_and_port);
        }
        @Override
        public void onReceivedHttpAuthRequest(WebView view,
                HttpAuthHandler handler, String host, String realm) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onReceivedHttpAuthRequest()");

            mClient.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
        @Override
        public void onFormResubmission(WebView view, Message dontResend,
                Message resend) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onFormResubmission()");

            mClient.onFormResubmission(view, dontResend, resend);
        }
        @Override
        public void onReceivedError(WebView view, int errorCode,
                String description, String failingUrl) {
            mClient.onReceivedError(view, errorCode, description, failingUrl);
        }
        @Override
        public boolean shouldOverrideKeyEvent(WebView view,
                android.view.KeyEvent event) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter shouldOverrideKeyEvent()");

            return mClient.shouldOverrideKeyEvent(view, event);
        }
        @Override
        public void onUnhandledKeyEvent(WebView view,
                android.view.KeyEvent event) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onUnhandledKeyEvent()");

            mClient.onUnhandledKeyEvent(view, event);
        }
    }

    // -------------------------------------------------------------------------
    // WebChromeClient implementation for the sub window
    // -------------------------------------------------------------------------

    private class SubWindowChromeClient extends WebChromeClient {
        // The main WebChromeClient.
        private final WebChromeClient mClient;

        SubWindowChromeClient(WebChromeClient client) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter SubWindowChromeClient()");

            mClient = client;
        }
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onProgressChanged()");

            mClient.onProgressChanged(view, newProgress);
        }
        @Override
        public boolean onCreateWindow(WebView view, boolean dialog,
                boolean userGesture, android.os.Message resultMsg) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onCreateWindow()");

            return mClient.onCreateWindow(view, dialog, userGesture, resultMsg);
        }
        @Override
        public void onCloseWindow(WebView window) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onCloseWindow()");

            if (window != mSubView) {
                Log.e(LOGTAG, "Can't close the window");
            }
            mWebViewController.dismissSubWindow(Tab.this);
        }
    }

    // -------------------------------------------------------------------------

    // Construct a new tab
    Tab(WebViewController wvcontroller, WebView w) {
        this(wvcontroller, w, null);
    }

    Tab(WebViewController wvcontroller, Bundle state) {
        this(wvcontroller, null, state);
    }

    Tab(WebViewController wvcontroller, WebView w, Bundle state) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter Tab()");

        mWebViewController = wvcontroller;
        mContext = mWebViewController.getContext();
        mSettings = BrowserSettings.getInstance();
        mDataController = DataController.getInstance(mContext);
        mCurrentState = new PageState(mContext, w != null
                ? w.isPrivateBrowsingEnabled() : false);
        mInPageLoad = false;
        mInForeground = false;

        mDownloadListener = new DownloadListener() {

            public void onDownloadStart(String url, String userAgent,
                    String contentDisposition, String mimetype,
                    long contentLength) {
                mWebViewController.onDownloadStart(Tab.this, url, userAgent, contentDisposition,
                        mimetype, contentLength);
            }
        };
        mWebBackForwardListClient = new WebBackForwardListClient() {

            @Override
            public void onNewHistoryItem(WebHistoryItem item) {
                if (isInVoiceSearchMode()) {
                    item.setCustomData(mVoiceSearchData.mVoiceSearchIntent);
                }
                if (mClearHistoryUrlPattern != null) {
                    boolean match =
                        mClearHistoryUrlPattern.matcher(item.getOriginalUrl()).matches();
                    if (ENABLE_LOG) {

                        Log.d(LOGTAG, "onNewHistoryItem: match=" + match + "\n\t"
                                + item.getUrl() + "\n\t"
                                + mClearHistoryUrlPattern);
                    }
                    if (match) {

                        if (mMainView != null) {
                            mMainView.clearHistory();
                        }
                    }
                    mClearHistoryUrlPattern = null;
                }
            }
            @Override
            public void onIndexChanged(WebHistoryItem item, int index) {
                Object data = item.getCustomData();
                if (data != null && data instanceof Intent) {
                    activateVoiceSearchMode((Intent) data);
                }
            }
        };

        mCaptureWidth = mContext.getResources().getDimensionPixelSize(
                R.dimen.tab_thumbnail_width);
        mCaptureHeight = mContext.getResources().getDimensionPixelSize(
                R.dimen.tab_thumbnail_height);
        updateShouldCaptureThumbnails();
        restoreState(state);
        if (getId() == -1) {
            mId = TabControl.getNextId();
        }
        setWebView(w);
        mHandler = new Handler() {

            @Override
            public void handleMessage(Message m) {
                switch (m.what) {

                case MSG_CAPTURE:
                    capture();
                    break;
                    
                case UPDATE_SCREEN_FOR_163_AUTO: {
                    if(ENABLE_LOG) Log.v(LOGTAG, 
                            "onPageFinished(), refresh begin");
                    WebView wv = (WebView) m.obj;
                    if(wv != null) {
                        try {
                            Class<?> c = WebView.class;
                            Method methodPinScrollBy = c.getDeclaredMethod(
                                    "pinScrollBy", int.class, int.class, 
                                    boolean.class, int.class);

                            methodPinScrollBy.setAccessible(true);
                            methodPinScrollBy.invoke(wv, 0, 1, false, 0);
                            methodPinScrollBy.invoke(wv, 0, -1, false, 0);
                        } catch(java.lang.NoSuchMethodException e) {
                            Log.e(LOGTAG, "", e);
                        } catch(java.lang.IllegalAccessException e) {
                            Log.e(LOGTAG, "", e);
                        } catch(java.lang.reflect.InvocationTargetException e) {
                            Log.e(LOGTAG, "", e);
                        }
                    }
                    break;
                }
 
                case UPDATE_SCREEN_DATA:
                    Object[] obj = (Object[]) m.obj;
                    WebView view = (WebView)obj[0];
                    String url = (String)obj[1];
                    String originalUrl = view.getOriginalUrl();
                    if (view != null && url != null && originalUrl!=null) {
                        String fixUrl = null;
                        if(url.endsWith("/")){
                            fixUrl = url.substring(0, url.length()-1);
                        }else{
                            fixUrl = url + "/";
                        }
                        String originalUrl2 = null;
                        if(originalUrl.endsWith("/")){
                            originalUrl2 = originalUrl.substring(0, originalUrl.length()-1);
                        }else{
                            originalUrl2 = originalUrl + "/";
                        }
                        updateScreenshot(view, url, fixUrl, originalUrl2);
                    }
                /*    
                    long flowNum = 1;
                    startRxBytes = mSettings.getSharedPreferences().getLong(PreferenceKeys.PREF_FLOW_START_RX, 0);
                    startTxBytes = mSettings.getSharedPreferences().getLong(PreferenceKeys.PREF_FLOW_START_TX, 0);
                    if(startRxBytes!=0 && startTxBytes!=0 && endRxBytes!=0 && endTxBytes!=0){
                        flowNum = endRxBytes + endTxBytes - startRxBytes - startTxBytes;
                    }
                    mWebViewController.setFlow(Formatter.formatFileSize(mContext, flowNum));**/
                    break;
                }
            }
        };
    }


    private boolean isScreenShotSite(String url, String originalUrl, String fixUrl, String originalUrl2){
        Cursor cursor = null;
            try {
                cursor = mContext.getContentResolver().query(BrowserProvider2.NavScreen.CONTENT_URI, SCREEN_PROJECT, "(url = ? or url = ? or url = ? or url = ?) and needUpdate = 1", new String[]{url, originalUrl, fixUrl, originalUrl2}, null);
                if(cursor!=null && cursor.moveToFirst()){
                    return true;
                }
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                if(cursor != null){
                    cursor.close();
                }
            }
        return false;
    }
    
    private void updateScreenshot(WebView view, final String url, final String fixUrl, final String originalUrl2) {
        if(ENABLE_LOG) Log.v(LOGTAG, "enter updateScreenshot()");

        if (view == null) {
            // Tab was destroyed
            return;
        }
        final String originalUrl = view.getOriginalUrl();

        if (TextUtils.isEmpty(url)) {
            return;
        }

        if ((!Patterns.WEB_URL.matcher(url).matches()||UrlUtils.ACCEPTED_SCREEN_URI_SCHEMA.matcher(url).matches())&& !isScreenShotSite(url, originalUrl, fixUrl, originalUrl2)) {
             return;
        }

        final Bitmap bm = Controller.createScreenshot(view, Controller.getDesiredThumbnailWidth(mContext),
                Controller.getDesiredThumbnailHeight(mContext));
        if (bm == null) {
             return;
        }

        final ContentResolver cr = mContext.getContentResolver();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... unused) {
                Cursor cursor = null;
                try {
                    // TODO: Clean this up
                     cursor = cr.query(BrowserProvider2.NavScreen.CONTENT_URI, SCREEN_PROJECT, "(url = ? or url = ? or url = ? or url = ?) and needUpdate = 1", new String[]{url, originalUrl, fixUrl, originalUrl2}, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        final ByteArrayOutputStream os =
                                new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.PNG, 100, os);
                        ContentValues values = new ContentValues();
                        values.put(BrowserProvider2.NavScreen.DATA, os.toByteArray());
                        values.put(BrowserProvider2.NavScreen.UPDATE, 0);
                        cr.update(BrowserProvider2.NavScreen.CONTENT_URI, values, "_id = ?", new String[]{cursor.getLong(0)+""});
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
    
    
    
    
    /**
     * This is used to get a new ID when the tab has been preloaded, before it is displayed and
     * added to TabControl. Preloaded tabs can be created before restoreInstanceState, leading
     * to overlapping IDs between the preloaded and restored tabs.
     */
    public void refreshIdAfterPreload() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter refreshIdAfterPreload()");

        mId = TabControl.getNextId();
    }

    public void updateShouldCaptureThumbnails() {
        if (mWebViewController.shouldCaptureThumbnails()) {
            synchronized (Tab.this) {
                if(Browser.LOG_SYNC_ENABLED) {
                    Log.i(LOGTAG_SYNC, "updateShouldCaptureThumbnails()," + 
                            " synchronized(Tab.this), IN");
                }

                if (mCapture == null) {
                    mCapture = Bitmap.createBitmap(mCaptureWidth, mCaptureHeight,
                            Bitmap.Config.RGB_565);
                    mCapture.eraseColor(Color.WHITE);
                    if (mInForeground) {

                        postCapture();
                    }
                }
                if(Browser.LOG_SYNC_ENABLED) {
                    Log.i(LOGTAG_SYNC, "updateShouldCaptureThumbnails()," + 
                            " synchronized(Tab.this), OUT");
                }
            }
        } else {
            synchronized (Tab.this) {
                if(Browser.LOG_SYNC_ENABLED) {
                    Log.i(LOGTAG_SYNC, "updateShouldCaptureThumbnails()," + 
                            " synchronized(Tab.this), IN 2");
                }

                mCapture = null;
                deleteThumbnail();
                if(Browser.LOG_SYNC_ENABLED) {
                    Log.i(LOGTAG_SYNC, "updateShouldCaptureThumbnails()," + 
                            " synchronized(Tab.this), OUT 2");
                }
            }
        }
    }

    public void setController(WebViewController ctl) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter setController()");

        mWebViewController = ctl;
        updateShouldCaptureThumbnails();
    }

    public long getId() {
        return mId;
    }

    /**
     * Sets the WebView for this tab, correctly removing the old WebView from
     * the container view.
     */
    void setWebView(WebView w) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter setWebView()");

        if (mMainView == w) {
            return;
        }

        // If the WebView is changing, the page will be reloaded, so any ongoing
        // Geolocation permission requests are void.
        if (mGeolocationPermissionsPrompt != null) {
            mGeolocationPermissionsPrompt.hide();
        }

        mWebViewController.onSetWebView(this, w);

        if (mMainView != null) {
            mMainView.setPictureListener(null);
            if (w != null) {
                syncCurrentState(w, null);
            } else {
                mCurrentState = new PageState(mContext, false);
            }
        }
        // set the new one
        mMainView = w;
        // attach the WebViewClient, WebChromeClient and DownloadListener
        if (mMainView != null) {
            mMainView.setWebViewClient(mWebViewClient);
            mMainView.setWebChromeClient(mWebChromeClient);
            // Attach DownloadManager so that downloads can start in an active
            // or a non-active window. This can happen when going to a site that
            // does a redirect after a period of time. The user could have
            // switched to another tab while waiting for the download to start.
            mMainView.setDownloadListener(mDownloadListener);
            mMainView.setWebBackForwardListClient(mWebBackForwardListClient);
            TabControl tc = mWebViewController.getTabControl();
            if (tc != null && tc.getOnThumbnailUpdatedListener() != null) {
                mMainView.setPictureListener(this);
            }
            if (mSavedState != null) {
                WebBackForwardList restoredState
                        = mMainView.restoreState(mSavedState);
                if (restoredState == null || restoredState.getSize() == 0) {
                    Log.w(LOGTAG, "Failed to restore WebView state!");
                    loadUrl(mCurrentState.mOriginalUrl, null);
                }
                mSavedState = null;
            }
        }
    }

    /**
     * Destroy the tab's main WebView and subWindow if any
     */
    void destroy() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter destroy()");

        if (mMainView != null) {
            dismissSubWindow();
            // Make sure the embedded title bar isn't still attached
            mMainView.setEmbeddedTitleBar(null);
            // save the WebView to call destroy() after detach it from the tab
            WebView webView = mMainView;
            setWebView(null);
            webView.destroy();
        }
    }

    /**
     * Remove the tab from the parent
     */
    void removeFromTree() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter removeFromTree()");

        // detach the children
        if (mChildren != null) {
            for(Tab t : mChildren) {
                t.setParent(null);
            }
        }
        // remove itself from the parent list
        if (mParent != null) {
            mParent.mChildren.remove(this);
        }
        deleteThumbnail();
    }

    /**
     * Create a new subwindow unless a subwindow already exists.
     * @return True if a new subwindow was created. False if one already exists.
     */
    boolean createSubWindow() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter createSubWindow()");

        if (mSubView == null) {
            mWebViewController.createSubWindow(this);
            mSubView.setWebViewClient(new SubWindowClient(mWebViewClient,
                    mWebViewController));
            mSubView.setWebChromeClient(new SubWindowChromeClient(
                    mWebChromeClient));
            // Set a different DownloadListener for the mSubView, since it will
            // just need to dismiss the mSubView, rather than close the Tab
            mSubView.setDownloadListener(new DownloadListener() {

                public void onDownloadStart(String url, String userAgent,
                        String contentDisposition, String mimetype,
                        long contentLength) {
                    mWebViewController.onDownloadStart(Tab.this, url, userAgent,
                            contentDisposition, mimetype, contentLength);
                    if (mSubView.copyBackForwardList().getSize() == 0) {
                        // This subwindow was opened for the sole purpose of
                        // downloading a file. Remove it.
                        mWebViewController.dismissSubWindow(Tab.this);
                    }
                }
            });
            mSubView.setOnCreateContextMenuListener(mWebViewController.getActivity());
            return true;
        }
        return false;
    }

    /**
     * Dismiss the subWindow for the tab.
     */
    void dismissSubWindow() {
        if (mSubView != null) {
            mWebViewController.endActionMode();
            mSubView.destroy();
            mSubView = null;
            mSubViewContainer = null;
        }
    }


    /**
     * Set the parent tab of this tab.
     */
    void setParent(Tab parent) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter setParent()");

        if (parent == this) {
            throw new IllegalStateException("Cannot set parent to self!");
        }
        mParent = parent;
        // This tab may have been freed due to low memory. If that is the case,
        // the parent tab id is already saved. If we are changing that id
        // (most likely due to removing the parent tab) we must update the
        // parent tab id in the saved Bundle.
        if (mSavedState != null) {
            if (parent == null) {
                mSavedState.remove(PARENTTAB);
            } else {
                mSavedState.putLong(PARENTTAB, parent.getId());
            }
        }

        // Sync the WebView useragent with the parent
        if (parent != null && mSettings.hasDesktopUseragent(parent.getWebView())
                != mSettings.hasDesktopUseragent(getWebView())) {
            mSettings.toggleDesktopUseragent(getWebView());
        }

        if (parent != null && parent.getId() == getId()) {
            throw new IllegalStateException("Parent has same ID as child!");
        }
    }

    /**
     * If this Tab was created through another Tab, then this method returns
     * that Tab.
     * @return the Tab parent or null
     */
    public Tab getParent() {
        return mParent;
    }

    /**
     * When a Tab is created through the content of another Tab, then we
     * associate the Tabs.
     * @param child the Tab that was created from this Tab
     */
    void addChildTab(Tab child) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter addChildTab()");

        if (mChildren == null) {
            mChildren = new Vector<Tab>();
        }
        mChildren.add(child);
        child.setParent(this);
    }

    Vector<Tab> getChildren() {
        return mChildren;
    }

    void resume() {
        if (mMainView != null) {
            setupHwAcceleration(mMainView);
            mMainView.onResume();
            if (mSubView != null) {
                mSubView.onResume();
            }
        }
    }

    private void setupHwAcceleration(View web) {
        if (web == null) return;
        BrowserSettings settings = BrowserSettings.getInstance();
        if (settings.isHardwareAccelerated()) {
            web.setLayerType(View.LAYER_TYPE_NONE, null);
        } else {
            web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    void pause() {
        if (mMainView != null) {
            mMainView.onPause();
            if (mSubView != null) {
                mSubView.onPause();
            }
        }
    }

    void putInForeground() {
        if (mInForeground) {
            return;
        }
        mInForeground = true;
        resume();
        Activity activity = mWebViewController.getActivity();
        mMainView.setOnCreateContextMenuListener(activity);
        if (mSubView != null) {
            mSubView.setOnCreateContextMenuListener(activity);
        }
        // Show the pending error dialog if the queue is not empty
        if (mQueuedErrors != null && mQueuedErrors.size() >  0) {
            showError(mQueuedErrors.getFirst());
        }
        mWebViewController.bookmarkedStatusHasChanged(this);
    }

    void putInBackground() {
        if (!mInForeground) {
            return;
        }
        capture();
        mInForeground = false;
        pause();
        mMainView.setOnCreateContextMenuListener(null);
        if (mSubView != null) {
            mSubView.setOnCreateContextMenuListener(null);
        }
    }

    boolean inForeground() {
        return mInForeground;
    }

    /**
     * Return the top window of this tab; either the subwindow if it is not
     * null or the main window.
     * @return The top window of this tab.
     */
    WebView getTopWindow() {
        if (mSubView != null) {
            return mSubView;
        }
        return mMainView;
    }

    /**
     * Return the main window of this tab. Note: if a tab is freed in the
     * background, this can return null. It is only guaranteed to be
     * non-null for the current tab.
     * @return The main WebView of this tab.
     */
    WebView getWebView() {
        return mMainView;
    }

    void setViewContainer(View container) {
        mContainer = container;
    }

    View getViewContainer() {
        return mContainer;
    }

    /**
     * Return whether private browsing is enabled for the main window of
     * this tab.
     * @return True if private browsing is enabled.
     */
    boolean isPrivateBrowsingEnabled() {
        return mCurrentState.mIncognito;
    }

    /**
     * Return the subwindow of this tab or null if there is no subwindow.
     * @return The subwindow of this tab or null.
     */
    WebView getSubWebView() {
        return mSubView;
    }

    void setSubWebView(WebView subView) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter setSubWebView()");

        mSubView = subView;
    }

    View getSubViewContainer() {
        return mSubViewContainer;
    }

    void setSubViewContainer(View subViewContainer) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter setSubViewContainer()");

        mSubViewContainer = subViewContainer;
    }

    /**
     * @return The geolocation permissions prompt for this tab.
     */
    GeolocationPermissionsPrompt getGeolocationPermissionsPrompt() {
        if (mGeolocationPermissionsPrompt == null) {
            ViewStub stub = (ViewStub) mContainer
                    .findViewById(R.id.geolocation_permissions_prompt);
            mGeolocationPermissionsPrompt = (GeolocationPermissionsPrompt) stub
                    .inflate();
        }
        return mGeolocationPermissionsPrompt;
    }

    /**
     * @return The application id string
     */
    String getAppId() {
        return mAppId;
    }

    /**
     * Set the application id string
     * @param id
     */
    void setAppId(String id) {
        mAppId = id;
    }

    boolean closeOnBack() {
        return mCloseOnBack;
    }

    void setCloseOnBack(boolean close) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter setCloseOnBack()");

        mCloseOnBack = close;
    }

    String getUrl() {
        return UrlUtils.filteredUrl(mCurrentState.mUrl);
    }

    String getOriginalUrl() {
        if (mCurrentState.mOriginalUrl == null) {
            return getUrl();
        }
        return UrlUtils.filteredUrl(mCurrentState.mOriginalUrl);
    }

    /**
     * Get the title of this tab.
     */
    String getTitle() {
        if (mCurrentState.mTitle == null && mInPageLoad) {
            return mContext.getString(R.string.title_bar_loading);
        }
        return mCurrentState.mTitle;
    }

    /**
     * Get the favicon of this tab.
     */
    Bitmap getFavicon() {
        if (mCurrentState.mFavicon != null) {
            return mCurrentState.mFavicon;
        }
        return getDefaultFavicon(mContext);
    }

    public boolean isBookmarkedSite() {
        return mCurrentState.mIsBookmarkedSite;
    }

    /**
     * Return the tab's error console. Creates the console if createIfNEcessary
     * is true and we haven't already created the console.
     * @param createIfNecessary Flag to indicate if the console should be
     *            created if it has not been already.
     * @return The tab's error console, or null if one has not been created and
     *         createIfNecessary is false.
     */
    ErrorConsoleView getErrorConsole(boolean createIfNecessary) {
        if (createIfNecessary && mErrorConsole == null) {
            mErrorConsole = new ErrorConsoleView(mContext);
            mErrorConsole.setWebView(mMainView);
        }
        return mErrorConsole;
    }

    /**
     * Sets the security state, clears the SSL certificate error and informs
     * the controller.
     */
    private void setSecurityState(SecurityState securityState) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter setSecurityState()");

        mCurrentState.mSecurityState = securityState;
        mCurrentState.mSslCertificateError = null;
        mWebViewController.onUpdatedSecurityState(this);
    }

    /**
     * @return The tab's security state.
     */
    SecurityState getSecurityState() {
        return mCurrentState.mSecurityState;
    }

    /**
     * Gets the SSL certificate error, if any, for the page's main resource.
     * This is only non-null when the security state is
     * SECURITY_STATE_BAD_CERTIFICATE.
     */
    SslError getSslCertificateError() {
        return mCurrentState.mSslCertificateError;
    }

    int getLoadProgress() {
        if (mInPageLoad) {

            return mPageLoadProgress;
        }
        return 100;
    }

    /**
     * @return TRUE if onPageStarted is called while onPageFinished is not
     *         called yet.
     */
    boolean inPageLoad() {
        return mInPageLoad;
    }

    // force mInLoad to be false. This should only be called before closing the
    // tab to ensure BrowserActivity's pauseWebViewTimers() is called correctly.
    void clearInPageLoad() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter clearInPageLoad()");

        mInPageLoad = false;
    }

    /**
     * @return The Bundle with the tab's state if it can be saved, otherwise null
     */
    public Bundle saveState() {
        // If the WebView is null it means we ran low on memory and we already
        // stored the saved state in mSavedState.
        if (mMainView == null) {
            return mSavedState;
        }

        if (TextUtils.isEmpty(mCurrentState.mUrl)) {
            return null;
        }

        mSavedState = new Bundle();
        WebBackForwardList savedList = mMainView.saveState(mSavedState);
        if (savedList == null || savedList.getSize() == 0) {
            Log.w(LOGTAG, "Failed to save back/forward list for "
                    + mCurrentState.mUrl);
        }

        mSavedState.putLong(ID, mId);
        mSavedState.putString(CURRURL, mCurrentState.mUrl);
        mSavedState.putString(CURRTITLE, mCurrentState.mTitle);
        mSavedState.putBoolean(INCOGNITO, mMainView.isPrivateBrowsingEnabled());
        if (mAppId != null) {
            mSavedState.putString(APPID, mAppId);
        }
        mSavedState.putBoolean(CLOSEFLAG, mCloseOnBack);
        // Remember the parent tab so the relationship can be restored.
        if (mParent != null) {
            mSavedState.putLong(PARENTTAB, mParent.mId);
        }
        mSavedState.putBoolean(USERAGENT,
                mSettings.hasDesktopUseragent(getWebView()));
        return mSavedState;
    }

    /*
     * Restore the state of the tab.
     */
    private void restoreState(Bundle b) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter restoreState()");

        mSavedState = b;
        if (mSavedState == null) {
            return;
        }
        // Restore the internal state even if the WebView fails to restore.
        // This will maintain the app id, original url and close-on-exit values.
        mId = b.getLong(ID);
        mAppId = b.getString(APPID);
        mCloseOnBack = b.getBoolean(CLOSEFLAG);
        if (b.getBoolean(USERAGENT)
                != mSettings.hasDesktopUseragent(getWebView())) {
            mSettings.toggleDesktopUseragent(getWebView());
        }
        String url = b.getString(CURRURL);
        String title = b.getString(CURRTITLE);
        boolean incognito = b.getBoolean(INCOGNITO);
        mCurrentState = new PageState(mContext, incognito, url, null);
        mCurrentState.mTitle = title;
        synchronized (Tab.this) {
            if(Browser.LOG_SYNC_ENABLED) {
                Log.i(LOGTAG_SYNC, "restoreState()," + 
                        " synchronized(Tab.this), IN ");
            }

            if (mCapture != null) {
                DataController.getInstance(mContext).loadThumbnail(this);
            }
            if(Browser.LOG_SYNC_ENABLED) {
                Log.i(LOGTAG_SYNC, "restoreState()," + 
                        " synchronized(Tab.this), OUT ");
            }
        }
    }

    public void updateBookmarkedStatus() {
        mDataController.queryBookmarkStatus(getUrl(), mIsBookmarkCallback);
    }

    private DataController.OnQueryUrlIsBookmark mIsBookmarkCallback
            = new DataController.OnQueryUrlIsBookmark() {
        @Override
        public void onQueryUrlIsBookmark(String url, boolean isBookmark) {
            if (mCurrentState.mUrl.equals(url)) {
                mCurrentState.mIsBookmarkedSite = isBookmark;
                mWebViewController.bookmarkedStatusHasChanged(Tab.this);
            }
        }
    };

    public Bitmap getScreenshot() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter getScreenshot()");

        synchronized (Tab.this) {
            if(Browser.LOG_SYNC_ENABLED) {
                Log.i(LOGTAG_SYNC, "getScreenshot()," + 
                        " synchronized(Tab.this), IN ");
            }
            if(Browser.LOG_SYNC_ENABLED) {
                Log.i(LOGTAG_SYNC, "getScreenshot()," + 
                        " synchronized(Tab.this), OUT ");
            }
            return mCapture;
        }
    }

    public boolean isSnapshot() {
        return false;
    }

    public ContentValues createSnapshotValues() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter createSnapshotValues()");

        if (mMainView == null) return null;
        SnapshotByteArrayOutputStream bos = new SnapshotByteArrayOutputStream();
        try {
            GZIPOutputStream stream = new GZIPOutputStream(bos);
            if (!mMainView.saveViewState(stream)) {
                return null;
            }
            stream.flush();
            stream.close();
        } catch (Exception e) {

            Log.w(LOGTAG, "Failed to save view state", e);
            return null;
        }
        byte[] data = bos.toByteArray();
        ContentValues values = new ContentValues();
        values.put(Snapshots.TITLE, mCurrentState.mTitle);
        values.put(Snapshots.URL, mCurrentState.mUrl);
        values.put(Snapshots.VIEWSTATE, data);
        values.put(Snapshots.BACKGROUND, mMainView.getPageBackgroundColor());
        values.put(Snapshots.DATE_CREATED, System.currentTimeMillis());
        values.put(Snapshots.FAVICON, compressBitmap(getFavicon()));
        Bitmap screenshot = Controller.createScreenshot(mMainView,
                Controller.getDesiredThumbnailWidth(mContext),
                Controller.getDesiredThumbnailHeight(mContext));
        values.put(Snapshots.THUMBNAIL, compressBitmap(screenshot));
        return values;
    }

    public byte[] compressBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public int getCount(){
        return mCount;
    }
    
    public void loadUrl(String url, Map<String, String> headers) {
        if (mMainView != null) {
            mCount++;
            BrowserSettings.getInstance().adjustUserAgent(mMainView, url); 
            Controller.homepage = false;
            Controller.homepageBack = false;
            mPageLoadProgress = INITIAL_PROGRESS;
            mInPageLoad = true;
            mCurrentState = new PageState(mContext, false, url, null);
            mWebViewController.onPageStarted(this, mMainView, null);
            mMainView.loadUrl(url, headers);
        }
    }

    protected void capture() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter capture()");

        if (mMainView == null || mCapture == null) return;
        if (mMainView.getContentWidth() <= 0 || mMainView.getContentHeight() <= 0) {
            return;
        }
        Canvas c = new Canvas(mCapture);
        final int left = mMainView.getScrollX();
        final int top = mMainView.getScrollY() + mMainView.getVisibleTitleHeight();
        int state = c.save();
        c.translate(-left, -top);
        float scale = mCaptureWidth / (float) mMainView.getWidth();
        c.scale(scale, scale, left, top);
        if (mMainView instanceof BrowserWebView) {

            ((BrowserWebView)mMainView).drawContent(c);
        } else {
            mMainView.draw(c);
        }
        c.restoreToCount(state);
        // manually anti-alias the edges for the tilt
        c.drawRect(0, 0, 1, mCapture.getHeight(), sAlphaPaint);
        c.drawRect(mCapture.getWidth() - 1, 0, mCapture.getWidth(),
                mCapture.getHeight(), sAlphaPaint);
        c.drawRect(0, 0, mCapture.getWidth(), 1, sAlphaPaint);
        c.drawRect(0, mCapture.getHeight() - 1, mCapture.getWidth(),
                mCapture.getHeight(), sAlphaPaint);
        c.setBitmap(null);
        mHandler.removeMessages(MSG_CAPTURE);
        persistThumbnail();
        TabControl tc = mWebViewController.getTabControl();
        if (tc != null) {
            OnThumbnailUpdatedListener updateListener
                    = tc.getOnThumbnailUpdatedListener();
            if (updateListener != null) {
                updateListener.onThumbnailUpdated(this);
            }
        }
    }

    public Bitmap getHomeTabCapture(){
        Activity activity = mWebViewController.getActivity();
        Locale l = Locale.getDefault();  
        String language = l.getLanguage();  
        String country = l.getCountry().toLowerCase();  
        Bitmap cache = null;
        if ("zh".equals(language)) {  
            cache = BitmapFactory.decodeResource(activity.getResources(), R.drawable.home_tab);
        }else{
            cache = BitmapFactory.decodeResource(activity.getResources(), R.drawable.home_tab_en);
         }
        return cache;
    }
    
    @Override
    public void onNewPicture(WebView view, Picture picture) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter onNewPicture()");

        //update screenshot
        postCapture();
    }

    private void postCapture() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter postCapture()");

        if (!mHandler.hasMessages(MSG_CAPTURE)) {
            mHandler.sendEmptyMessageDelayed(MSG_CAPTURE, CAPTURE_DELAY);
        }
    }

    public boolean canGoBack() {
        return mMainView != null ? mMainView.canGoBack() : false;
    }

    public boolean canGoForward() {
        return mMainView != null ? mMainView.canGoForward() : false;
    }

    public void goBack() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter goBack()");

        if (mMainView != null) {
            mMainView.goBack();
            BrowserSettings.getInstance().adjustUserAgent(
                    mMainView, mMainView.getUrl()); 
        }
    }

    public void goForward() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter goForward()");

        if (mMainView != null) {
            mMainView.goForward();
            BrowserSettings.getInstance().adjustUserAgent(
                    mMainView, mMainView.getUrl()); 
        }
    }

    /**
     * Causes the tab back/forward stack to be cleared once, if the given URL is the next URL
     * to be added to the stack.
     *
     * This is used to ensure that preloaded URLs that are not subsequently seen by the user do
     * not appear in the back stack.
     */
    public void clearBackStackWhenItemAdded(Pattern urlPattern) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter clearBackStackWhenItemAdded()");

        mClearHistoryUrlPattern = urlPattern;
    }

    protected void persistThumbnail() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter persistThumbnail()");

        DataController.getInstance(mContext).saveThumbnail(this);
    }

    protected void deleteThumbnail() {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter deleteThumbnail()");

        DataController.getInstance(mContext).deleteThumbnail(this);
    }

    void updateCaptureFromBlob(byte[] blob) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter updateCaptureFromBlob()");

        synchronized (Tab.this) {
            if(Browser.LOG_SYNC_ENABLED) {
                Log.i(LOGTAG_SYNC, "updateCaptureFromBlob()," + 
                        " synchronized(Tab.this), IN ");
            }
            if (mCapture == null) {
                if(Browser.LOG_SYNC_ENABLED) {
                    Log.i(LOGTAG_SYNC, "updateCaptureFromBlob()," + 
                            " synchronized(Tab.this), OUT 1 ");
                }
                return;
            }
            ByteBuffer buffer = ByteBuffer.wrap(blob);
            try {
                mCapture.copyPixelsFromBuffer(buffer);
            } catch (RuntimeException rex) {

                Log.e(LOGTAG, "Load capture has mismatched sizes; buffer: "
                        + buffer.capacity() + " blob: " + blob.length
                        + "capture: " + mCapture.getByteCount());
                throw rex;
            }
            if(Browser.LOG_SYNC_ENABLED) {
                Log.i(LOGTAG_SYNC, "updateCaptureFromBlob()," + 
                        " synchronized(Tab.this), OUT 2 ");
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(100);
        builder.append(mId);
        builder.append(") has parent: ");
        if (getParent() != null) {
            builder.append("true[");
            builder.append(getParent().getId());
            builder.append("]");
        } else {
            builder.append("false");
        }
        builder.append(", incog: ");
        builder.append(isPrivateBrowsingEnabled());
        if (!isPrivateBrowsingEnabled()) {
            builder.append(", title: ");
            builder.append(getTitle());
            builder.append(", url: ");
            builder.append(getUrl());
        }
        return builder.toString();
    }

    private void handleProceededAfterSslError(SslError error) {
		if(ENABLE_LOG) Log.v(LOGTAG, "enter handleProceededAfterSslError()");

        if (error.getUrl().equals(mCurrentState.mUrl)) {
            // The security state should currently be SECURITY_STATE_SECURE.
            setSecurityState(SecurityState.SECURITY_STATE_BAD_CERTIFICATE);
            mCurrentState.mSslCertificateError = error;
        } else if (getSecurityState() == SecurityState.SECURITY_STATE_SECURE) {
            // The page's main resource is secure and this error is for a
            // sub-resource.
            setSecurityState(SecurityState.SECURITY_STATE_MIXED);
        }
    }
}
