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
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.net.WebAddress;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Toast;

import java.io.File;

/**
 * Handle download requests
 */
public class DownloadHandler {

    // private static final boolean LOGD_ENABLED = 
        // com.android.browser.Browser.LOGD_ENABLED;
    private static final boolean LOGD_ENABLED = true;

    private static final String LOGTAG = "DownloadHandler";

    private static int mDownload;
    private static final int USER_OPERATION_STREAMING = 0;
    private static final int USER_OPERATION_DOWNLOAD = 1;
 
    /**
     * Notify the host application a download should be done, or that
     * the data should be streamed if a streaming viewer is available.
     * @param activity Activity requesting the download.
     * @param url The full url to the content that should be downloaded
     * @param userAgent User agent of the downloading application.
     * @param contentDisposition Content-disposition http header, if present.
     * @param mimetype The mimetype of the content reported by the server
     * @param privateBrowsing If the request is coming from a private browsing tab.
     */
    private static void onDownloadStartOldPolicy(Activity activity, String url,
            String userAgent, String contentDisposition, String mimetype,
            boolean privateBrowsing) {
        Log.v(LOGTAG, "enter onDownloadStart(), url: " + url + ", ua: " +  userAgent + 
                ", contentDisposition: " + contentDisposition + ", mimetype: " + mimetype);
                

        // if we're dealing wih A/V content that's not explicitly marked
        //     for download, check if it's streamable.
        if (contentDisposition == null
                || !contentDisposition.regionMatches(
                        true, 0, "attachment", 0, 10)) {
            // query the package manager to see if there's a registered handler
            //     that matches.
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(url), mimetype);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ResolveInfo info = activity.getPackageManager().resolveActivity(intent,
                    PackageManager.MATCH_DEFAULT_ONLY);
            if (info != null) {
                ComponentName myName = activity.getComponentName();
                // If we resolved to ourselves, we don't want to attempt to
                // load the url only to try and download it again.
                if (!myName.getPackageName().equals(
                        info.activityInfo.packageName)
                        || !myName.getClassName().equals(
                                info.activityInfo.name)) {
                    // someone (other than us) knows how to handle this mime
                    // type with this scheme, don't download.
                    try {
                        activity.startActivity(intent);
                        return;
                    } catch (ActivityNotFoundException ex) {
                        if (LOGD_ENABLED) {
                            Log.d(LOGTAG, "activity not found for " + mimetype
                                    + " over " + Uri.parse(url).getScheme(),
                                    ex);
                        }
                        // Best behavior is to fall back to a download in this
                        // case
                    }
                }
            }
        }
        onDownloadStartNoStream(activity, url, userAgent, contentDisposition,
                mimetype, privateBrowsing);
    }

    /**
     * Notify the host application a download should be done, or that
     * the data should be streamed if a streaming viewer is available.
     * @param activity Activity requesting the download.
     * @param url The full url to the content that should be downloaded
     * @param userAgent User agent of the downloading application.
     * @param contentDisposition Content-disposition http header, if present.
     * @param mimetype The mimetype of the content reported by the server
     * @param privateBrowsing If the request is coming from a private browsing tab.
     */
    public static void onDownloadStart(Activity activity, String url,
            String userAgent, String contentDisposition, String mimetype,
            boolean privateBrowsing) {
        Log.v(LOGTAG, "enter onDownloadStart(), url: " + url + ", ua: " +  userAgent + 
                ", contentDisposition: " + contentDisposition + ", mimetype: " + mimetype);

        onDownloadStartNewPolicy(activity, url, userAgent, 
                contentDisposition, mimetype, privateBrowsing);
    }

    private static void onDownloadStartNewPolicy(final Activity activity,
            final String url, final String userAgent, 
            final String contentDisposition, final String mimetype, 
            final boolean privateBrowsing) {
        Log.v(LOGTAG, "enter onDownloadStartNewPolicy(), url: " + url + ", ua: " +  userAgent + 
                ", contentDisposition: " + contentDisposition + ", mimetype: " + mimetype);
                
        // Explicitly marked for download
        if (contentDisposition != null && contentDisposition.
                regionMatches(true, 0, "attachment", 0, 10)) {
            Log.v(LOGTAG, "Explicitly marked for download" + 
            "onDownloadStartNoStream");
            onDownloadStartNoStream(activity, url, userAgent, 
                    contentDisposition, mimetype, privateBrowsing);
            return;
        }

        // check if it's streamable.
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(url), mimetype);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ResolveInfo info = activity.getPackageManager().resolveActivity(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (info == null) {
            Log.v(LOGTAG, "No one can handle the file, just download it," +
                    "onDownloadStartNoStream");
            // No one can handle the file, just download it
            onDownloadStartNoStream(activity, url, userAgent, 
                    contentDisposition, mimetype, privateBrowsing);
            return;
        }

        ComponentName myName = activity.getComponentName();
        // If we resolved to ourselves, we don't want to attempt to
        // load the url only to try and download it again.
        if (!myName.getPackageName().equals(info.activityInfo.packageName)
                || !myName.getClassName().equals( info.activityInfo.name)) {
            // someone (other than us) knows how to handle this mime
            // type with this scheme, don't download.
            // FIXME: fall back to let user choose downloading or streaming
            Log.v(LOGTAG, "fall back to let user choose downloading or streaming");
            /* try {
                activity.startActivity(intent);
                return;
            } catch (ActivityNotFoundException ex) {
                if (LOGD_ENABLED) {
                    Log.d(LOGTAG, "activity not found for " + mimetype
                            + " over " + Uri.parse(url).getScheme(),
                            ex);
                }
                // Best behavior is to fall back to a download in this
                // case
            }*/
        } else {
            onDownloadStartNoStream(activity, url, userAgent, 
                    contentDisposition, mimetype, privateBrowsing);
            return;
        }

        mDownload = USER_OPERATION_DOWNLOAD; // default to download the file 
        AlertDialog.Builder downloadDialogBuilder = 
            new AlertDialog.Builder(activity);
        downloadDialogBuilder.setTitle(
                activity.getResources().getString(R.string.download_way));
        downloadDialogBuilder.setSingleChoiceItems(
                new String[]{activity.getResources().getString(
                    R.string.online_play),
                activity.getResources().getString(
                    R.string.download_location)}, 1, 
                new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0) {
                    mDownload = USER_OPERATION_STREAMING;
                } else if(which == 1) {
                    mDownload = USER_OPERATION_DOWNLOAD;
                }
            }
        });
        downloadDialogBuilder.setPositiveButton(
                activity.getResources().getString(R.string.ok), 
                    new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(USER_OPERATION_STREAMING == mDownload){
                    try {
                        activity.startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        Log.d(LOGTAG, "activity not found for " + mimetype
                            + " over " + Uri.parse(url).getScheme(), ex);
                        // Best behavior is to fall back to a download in this case
                    }
                } else if(USER_OPERATION_DOWNLOAD == mDownload){
                    onDownloadStartNoStream(activity, url, userAgent, 
                        contentDisposition, mimetype, privateBrowsing);
                } else {
                    Log.d(LOGTAG, "unknown download operation: " + mDownload);
                }
            }
        });
        downloadDialogBuilder.setNegativeButton(activity.getResources().
                getString(R.string.cancel), null);
        AlertDialog dialog = downloadDialogBuilder.create();
        dialog.show();
    }

    // This is to work around the fact that java.net.URI throws Exceptions
    // instead of just encoding URL's properly
    // Helper method for onDownloadStartNoStream
    private static String encodePath(String path) {
        Log.v(LOGTAG, "enter encodePath()");
        char[] chars = path.toCharArray();

        boolean needed = false;
        for (char c : chars) {
            if (c == '[' || c == ']') {
                needed = true;
                break;
            }
        }
        if (needed == false) {
            return path;
        }

        StringBuilder sb = new StringBuilder("");
        for (char c : chars) {
            if (c == '[' || c == ']') {
                sb.append('%');
                sb.append(Integer.toHexString(c));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * Notify the host application a download should be done, even if there
     * is a streaming viewer available for thise type.
     * @param activity Activity requesting the download.
     * @param url The full url to the content that should be downloaded
     * @param userAgent User agent of the downloading application.
     * @param contentDisposition Content-disposition http header, if present.
     * @param mimetype The mimetype of the content reported by the server
     * @param privateBrowsing If the request is coming from a private browsing tab.
     */
    /*package */ static void onDownloadStartNoStream(Activity activity,
            String url, String userAgent, String contentDisposition,
            String mimetype, boolean privateBrowsing) {
        
        if(mimetype == null){
            mimetype =  MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    MimeTypeMap.getFileExtensionFromUrl(url));
        }
        
        Log.v(LOGTAG, "enter onDownloadStartNoStream(), url: " + url + ", ua: " + userAgent + 
                ", contentDisposition: " + contentDisposition + ", mimetype: " + mimetype);

        String filename = URLUtil.guessFileName(url,
                contentDisposition, mimetype);

        // Check to see if we have an SDCard
        String status = Environment.getExternalStorageState();
 
        if(!status.equals(Environment.MEDIA_MOUNTED)) {
                int title;
                String msg;
    
                // Check to see if the SDCard is busy, same as the music app
                if (status.equals(Environment.MEDIA_SHARED)) {
                    msg = activity.getString(R.string.download_sdcard_busy_dlg_msg);
                    title = R.string.download_sdcard_busy_dlg_title;
                } else {
                    msg = activity.getString(R.string.download_no_sdcard_dlg_msg, filename);
                    title = R.string.download_no_sdcard_dlg_title;
                }
    
                new AlertDialog.Builder(activity)
                    .setTitle(title)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage(msg)
                    .setPositiveButton(R.string.ok, null)
                    .show();
                return;
        }
        if (BrowserSettings.CU_PLATFORM){
            new FetchUrlContentLength(activity, privateBrowsing, url, userAgent, mimetype, filename).start(); 
        } else {
            if(mimetype == null){

                // PEKALL begin: download thread will send HTTP GET 
                // and get MIMETYPE from its response.
                /* if (TextUtils.isEmpty(addressString)) {
                    return;
                } 
                */
                // We must have long pressed on a link or image to download it. We
                // are not sure of the mimetype in this case, so do a head request
                Log.v(LOGTAG, "enter DownloadHandler-onDownloadStartNoStream mimetype is null");
                new FetchUrlMimeType(activity, privateBrowsing, url, userAgent).start(); 
                 // PEKALL end
             }else{
                Intent intent = new Intent(activity.getApplicationContext(), DownloadDialog.class);
                intent.putExtra("url", url);
                intent.putExtra("mimetype", mimetype);
                intent.putExtra("privateBrowsing", privateBrowsing);
                intent.putExtra("filename", filename);
                intent.putExtra("userAgent", userAgent);
                activity.startActivity(intent);
            }
        }
      }
 
}
