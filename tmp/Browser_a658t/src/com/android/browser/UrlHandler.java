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
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Browser;
import android.util.Log;
import android.webkit.WebView;

import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Matcher;
import java.net.URLDecoder;

/**
 *
 */
public class UrlHandler {

    static final String RLZ_PROVIDER = "com.google.android.partnersetup.rlzappprovider";
    static final Uri RLZ_PROVIDER_URI = Uri.parse("content://" + RLZ_PROVIDER + "/");

    // Use in overrideUrlLoading
    /* package */ final static String SCHEME_WTAI = "wtai://wp/";
    /* package */ final static String SCHEME_WTAI_MC = "wtai://wp/mc;";
    /* package */ final static String SCHEME_WTAI_WC = "wtai://wp/wc;";
    /* package */ final static String SCHEME_WTAI_SD = "wtai://wp/sd;";
    /* package */ final static String SCHEME_WTAI_AP = "wtai://wp/ap;";

    final static String SCHEME_RTSP = "rtsp://";

    Controller mController;
    Activity mActivity;

    private Boolean mIsProviderPresent = null;
    private Uri mRlzUri = null;

    public UrlHandler(Controller controller) {
        mController = controller;
        mActivity = mController.getActivity();
    }

    public static boolean isSpecialUrl(String url) {
        if(url != null) {
            if(url.startsWith(SCHEME_RTSP) ||
                    url.startsWith(SCHEME_WTAI)) {
                return true;
            }
        }
        return false;
    }

    void handleSpecialUrl(String url) {
        if(url == null) return;

        if(url.startsWith(SCHEME_RTSP)) {
            Intent intent;
            try {
                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            } catch (URISyntaxException ex) {
                Log.w("Browser", "Bad URI " + url + ": " + ex.getMessage());
                return;
            }

            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setComponent(null);
            try {
                if (mActivity.startActivityIfNeeded(intent, -1)) {
                    return;
                }
            } catch (ActivityNotFoundException ex) {
            }
            return;
        }

        if(url.startsWith(SCHEME_WTAI)) {
            // wtai://wp/mc;number
            // number=string(phone-number)
            if (url.startsWith(SCHEME_WTAI_MC)) {
                /*Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(WebView.SCHEME_TEL +
                            url.substring(SCHEME_WTAI_MC.length())));*/

                Intent intent = new Intent(Intent.ACTION_CALL_PRIVILEGED, 
                        Uri.fromParts("tel", url.substring(
                        SCHEME_WTAI_MC.length()), null));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mActivity.startActivity(intent);
                return;
            }
            // wtai://wp/wc;number
            // "wc" is only for cmcc test webpage!
            if (url.startsWith(SCHEME_WTAI_WC)) {
                /*Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(WebView.SCHEME_TEL +
                            url.substring(SCHEME_WTAI_WC.length())));*/

                Intent intent = new Intent(Intent.ACTION_CALL_PRIVILEGED, 
                        Uri.fromParts("tel", url.substring(
                        SCHEME_WTAI_WC.length()), null));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mActivity.startActivity(intent);
                return;
            }
            // wtai://wp/sd;dtmf
            // dtmf=string(dialstring)
            if (url.startsWith(SCHEME_WTAI_SD)) {
                // TODO: only send when there is active voice connection
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(WebView.SCHEME_TEL +
                            url.substring(SCHEME_WTAI_SD.length())));
                mActivity.startActivity(intent);
                return;
            }
            // wtai://wp/ap;number;name
            // number=string(phone-number)
            // name=string
            if (url.startsWith(SCHEME_WTAI_AP)) {
                // FIXME: There is a "?" at the end of the url, web page issue?
                String newUrl = url.trim().replaceAll("\\?", "");

                Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
                intent.setType("vnd.android.cursor.item/contact");

                String[] info = newUrl.split(";");
                Log.i("Browser", "wtai_ap: " + newUrl + ", " + info.length);
                if(info.length >= 2 && info[1] != null && info[1].length() > 0) {
                    intent.putExtra(android.provider.Contacts.Intents.
                            Insert.PHONE, info[1]);
                }
                if(info.length >= 3 && info[2] != null && info[2].length() > 0) {
                    try {
                    intent.putExtra(android.provider.Contacts.Intents.
                            Insert.NAME, URLDecoder.decode(info[2], "UTF-8"));
                    } catch (java.io.UnsupportedEncodingException e) {
                        Log.e("Browser", "", e);
                    }
                }
                mActivity.startActivity(intent); 
                return;
            }
        }
    }

    boolean shouldOverrideUrlLoading(Tab tab, WebView view, String url) {
    	
        BrowserSettings.getInstance().adjustUserAgent(view, url);
    	
        if (view.isPrivateBrowsingEnabled()) {
            // Don't allow urls to leave the browser app when in
            // private browsing mode
            return false;
        }

        if (url.startsWith(SCHEME_WTAI)) {
            // wtai://wp/mc;number
            // number=string(phone-number)
            if (url.startsWith(SCHEME_WTAI_MC)) {
                /*Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(WebView.SCHEME_TEL +
                        url.substring(SCHEME_WTAI_MC.length())));*/
                Intent intent = new Intent(Intent.ACTION_CALL_PRIVILEGED, 
                        Uri.fromParts("tel", url.substring(
                        SCHEME_WTAI_MC.length()), null));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 
                mActivity.startActivity(intent);
                // before leaving BrowserActivity, close the empty child tab.
                // If a new tab is created through JavaScript open to load this
                // url, we would like to close it as we will load this url in a
                // different Activity.
                mController.closeEmptyTab();
                return true;
            }
            // wtai://wp/sd;dtmf
            // dtmf=string(dialstring)
            if (url.startsWith(SCHEME_WTAI_SD)) {
                // TODO: only send when there is active voice connection
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(WebView.SCHEME_TEL +
                            url.substring(SCHEME_WTAI_SD.length())));
                mActivity.startActivity(intent);
                return true;
            }
            // wtai://wp/ap;number;name
            // number=string(phone-number)
            // name=string
            if (url.startsWith(SCHEME_WTAI_AP)) {
                // FIXME: There is a "?" at the end of the url, web page issue?
                String newUrl = url.trim().replaceAll("\\?", "");

                Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
                intent.setType("vnd.android.cursor.item/contact");

                String[] info = newUrl.split(";");
                Log.i("Browser", "wtai_ap: " + newUrl + ", " + info.length);
                if(info.length >= 2 && info[1] != null && info[1].length() > 0) {
                    intent.putExtra(android.provider.Contacts.Intents.
                            Insert.PHONE, info[1]);
                }
                if(info.length >= 3 && info[2] != null && info[2].length() > 0) {
                    try {
                    intent.putExtra(android.provider.Contacts.Intents.
                            Insert.NAME, URLDecoder.decode(info[2], "UTF-8"));
                    } catch (java.io.UnsupportedEncodingException e) {
                        Log.e("Browser", "", e);
                    }
                }
                mActivity.startActivity(intent); 
 
                return true;
            }
        }

        // The "about:" schemes are internal to the browser; don't want these to
        // be dispatched to other apps.
        if (url.startsWith("about:")) {
            return false;
        }

        /* FIXME:  CMCC Web test, download this file anyway.
            http://218.206.177.209:8080/waptest, Browser1.5-->
            Content download and store-->TXT download */
        if(url.equals("http://218.206.177.209:8080/waptest/browser15/file/Test.txt")) {
            DownloadHandler.onDownloadStartNoStream(
                    mActivity, url, null, null, null,false);
            return true;
        }

        // If this is a Google search, attempt to add an RLZ string
        // (if one isn't already present).
        if (rlzProviderPresent()) {
            Uri siteUri = Uri.parse(url);
            if (needsRlzString(siteUri)) {
                // Need to look up the RLZ info from a database, so do it in an
                // AsyncTask. Although we are not overriding the URL load synchronously,
                // we guarantee that we will handle this URL load after the task executes,
                // so it's safe to just return true to WebCore now to stop its own loading.
                new RLZTask(tab, siteUri, view).execute();
                return true;
            }
        }

        if (startActivityForUrl(tab, url)) {
            return true;
        }

        if (handleMenuClick(tab, url)) {
            return true;
        }

        return false;
    }

    boolean startActivityForUrl(Tab tab, String url) {
      Intent intent;
      // perform generic parsing of the URI to turn it into an Intent.
      try {
          intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
      } catch (URISyntaxException ex) {
          Log.w("Browser", "Bad URI " + url + ": " + ex.getMessage());
          return false;
      }

      // check whether the intent can be resolved. If not, we will see
      // whether we can download it from the Market.
      if (mActivity.getPackageManager().resolveActivity(intent, 0) == null) {
          String packagename = intent.getPackage();
          if (packagename != null) {
              intent = new Intent(Intent.ACTION_VIEW, Uri
                      .parse("market://search?q=pname:" + packagename));
              intent.addCategory(Intent.CATEGORY_BROWSABLE);
              mActivity.startActivity(intent);
              // before leaving BrowserActivity, close the empty child tab.
              // If a new tab is created through JavaScript open to load this
              // url, we would like to close it as we will load this url in a
              // different Activity.
              mController.closeEmptyTab();
              return true;
          } else {
              return false;
          }
      }

      // sanitize the Intent, ensuring web pages can not bypass browser
      // security (only access to BROWSABLE activities).
      intent.addCategory(Intent.CATEGORY_BROWSABLE);
      intent.setComponent(null);
      // Re-use the existing tab if the intent comes back to us
      if (tab != null) {
          if (tab.getAppId() == null) {
              tab.setAppId("com.android.browser-" + tab.getId());
          }
          intent.putExtra(Browser.EXTRA_APPLICATION_ID, tab.getAppId());
      }
      // Make sure webkit can handle it internally before checking for specialized
      // handlers. If webkit can't handle it internally, we need to call
      // startActivityIfNeeded
      Matcher m = UrlUtils.ACCEPTED_URI_SCHEMA.matcher(url);
      if (m.matches() && !isSpecializedHandlerAvailable(intent)) {
          return false;
      }
      try {
          if (mActivity.startActivityIfNeeded(intent, -1)) {
              // before leaving BrowserActivity, close the empty child tab.
              // If a new tab is created through JavaScript open to load this
              // url, we would like to close it as we will load this url in a
              // different Activity.
              mController.closeEmptyTab();
              return true;
          }
      } catch (ActivityNotFoundException ex) {
          // ignore the error. If no application can handle the URL,
          // eg about:blank, assume the browser can handle it.
      }

      return false;
    }

    /**
     * Search for intent handlers that are specific to this URL
     * aka, specialized apps like google maps or youtube
     */
    private boolean isSpecializedHandlerAvailable(Intent intent) {
        PackageManager pm = mActivity.getPackageManager();
          List<ResolveInfo> handlers = pm.queryIntentActivities(intent,
                  PackageManager.GET_RESOLVED_FILTER);
          if (handlers == null || handlers.size() == 0) {
              return false;
          }
          for (ResolveInfo resolveInfo : handlers) {
              IntentFilter filter = resolveInfo.filter;
              if (filter == null) {
                  // No intent filter matches this intent?
                  // Error on the side of staying in the browser, ignore
                  continue;
              }
              if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) {
                  // Generic handler, skip
                  continue;
              }
              return true;
          }
          return false;
    }

    // In case a physical keyboard is attached, handle clicks with the menu key
    // depressed by opening in a new tab
    boolean handleMenuClick(Tab tab, String url) {
        if (mController.isMenuDown()) {
            mController.openTab(url,
                    (tab != null) && tab.isPrivateBrowsingEnabled(),
                    !BrowserSettings.getInstance().openInBackground(), true);
            mActivity.closeOptionsMenu();
            return true;
        }

        return false;
    }

    // TODO: Move this class into Tab, where it can be properly stopped upon
    // closure of the tab
    private class RLZTask extends AsyncTask<Void, Void, String> {
        private Tab mTab;
        private Uri mSiteUri;
        private WebView mWebView;

        public RLZTask(Tab tab, Uri uri, WebView webView) {
            mTab = tab;
            mSiteUri = uri;
            mWebView = webView;
        }

        protected String doInBackground(Void... unused) {
            String result = mSiteUri.toString();
            Cursor cur = null;
            try {
                cur = mActivity.getContentResolver()
                        .query(getRlzUri(), null, null, null, null);
                if (cur != null && cur.moveToFirst() && !cur.isNull(0)) {
                    result = mSiteUri.buildUpon()
                           .appendQueryParameter("rlz", cur.getString(0))
                           .build().toString();
                }
            } finally {
                if (cur != null) {
                    cur.close();
                }
            }
            return result;
        }

        protected void onPostExecute(String result) {
            // abort if we left browser already
            if (mController.isActivityPaused()) return;
            // Make sure the Tab was not closed while handling the task
            if (mController.getTabControl().getTabPosition(mTab) != -1) {
                // If the Activity Manager is not invoked, load the URL directly
                if (!startActivityForUrl(mTab, result)) {
                    if (!handleMenuClick(mTab, result)) {
                        mController.loadUrl(mTab, result);
                    }
                }
            }
        }
    }

    // Determine whether the RLZ provider is present on the system.
    private boolean rlzProviderPresent() {
        if (mIsProviderPresent == null) {
            PackageManager pm = mActivity.getPackageManager();
            mIsProviderPresent = pm.resolveContentProvider(RLZ_PROVIDER, 0) != null;
        }
        return mIsProviderPresent;
    }

    // Retrieve the RLZ access point string and cache the URI used to
    // retrieve RLZ values.
    private Uri getRlzUri() {
        if (mRlzUri == null) {
            String ap = mActivity.getResources()
                    .getString(R.string.rlz_access_point);
            mRlzUri = Uri.withAppendedPath(RLZ_PROVIDER_URI, ap);
        }
        return mRlzUri;
    }

    // Determine if this URI appears to be for a Google search
    // and does not have an RLZ parameter.
    // Taken largely from Chrome source, src/chrome/browser/google_url_tracker.cc
    private static boolean needsRlzString(Uri uri) {
        String scheme = uri.getScheme();
        if (("http".equals(scheme) || "https".equals(scheme)) &&
            (uri.getQueryParameter("q") != null) &&
                    (uri.getQueryParameter("rlz") == null)) {
            String host = uri.getHost();
            if (host == null) {
                return false;
            }
            String[] hostComponents = host.split("\\.");

            if (hostComponents.length < 2) {
                return false;
            }
            int googleComponent = hostComponents.length - 2;
            String component = hostComponents[googleComponent];
            if (!"google".equals(component)) {
                if (hostComponents.length < 3 ||
                        (!"co".equals(component) && !"com".equals(component))) {
                    return false;
                }
                googleComponent = hostComponents.length - 3;
                if (!"google".equals(hostComponents[googleComponent])) {
                    return false;
                }
            }

            // Google corp network handling.
            if (googleComponent > 0 && "corp".equals(
                    hostComponents[googleComponent - 1])) {
                return false;
            }

            return true;
        }
        return false;
    }

}
