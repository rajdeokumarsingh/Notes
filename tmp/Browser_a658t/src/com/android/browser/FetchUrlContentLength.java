package com.android.browser;

import java.io.IOException;
import android.text.format.Formatter;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.conn.params.ConnRouteParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Proxy;
import android.net.http.AndroidHttpClient;
import android.os.Looper;
import android.webkit.CookieManager;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;

public class FetchUrlContentLength extends Thread {

    private Context mContext;
    private String mUri;
    private String mCookies;
    private String mUserAgent;
    private String mfilename;
    private String mMimeType;
    private boolean mprivateBrowseing;
    private Activity mActivity;

    public FetchUrlContentLength(Activity activity, boolean privateBrowsing,
            String uri, String userAgent, String mimeType, String fileName) {
        mContext = activity.getApplicationContext();
        mActivity = activity;
        mUri = uri;
        mMimeType = mimeType;
        mUserAgent = userAgent;
        mfilename = fileName;
        mprivateBrowseing = privateBrowsing;
        mCookies  = CookieManager.getInstance().getCookie(mUri, mprivateBrowseing);
        
    }

    @Override
    public void run() {
        // User agent is likely to be null, though the AndroidHttpClient
        // seems ok with that.
        AndroidHttpClient client = AndroidHttpClient.newInstance(mUserAgent);
        HttpHost httpHost = Proxy.getPreferredHttpHost(mContext, mUri);
        if (httpHost != null) {
            ConnRouteParams.setDefaultProxy(client.getParams(), httpHost);
        }
        HttpHead request = new HttpHead(mUri);

        if (mCookies != null && mCookies.length() > 0) {
            request.addHeader("Cookie", mCookies);
        }

        HttpResponse response;
        String contentLength = null;
        String contentDisposition = null;
        try {
            response = client.execute(request);
            // We could get a redirect here, but if we do lets let
            // the download manager take care of it, and thus trust that
            // the server sends the right mimetype
            if (response.getStatusLine().getStatusCode() == 200) {
                Header header = response.getFirstHeader("Content-Length");
                
                if (header != null) {
                    contentLength = header.getValue();
                }
                if(mMimeType == null){
                    Header mimeTypeHeader = response.getFirstHeader("Content-Type");
                    if (mimeTypeHeader != null) {
                        mMimeType = mimeTypeHeader.getValue();
                        final int semicolonIndex = mMimeType.indexOf(';');
                        if (semicolonIndex != -1) {
                            mMimeType = mMimeType.substring(0, semicolonIndex);
                        }
                    }
                    Header contentDispositionHeader = response.getFirstHeader("Content-Disposition");
                    if (contentDispositionHeader != null) {
                        contentDisposition = contentDispositionHeader.getValue();
                    }
                    mfilename = URLUtil.guessFileName(mUri, contentDisposition,
                            mMimeType);
                }

             }
        } catch (IllegalArgumentException ex) {
            request.abort();
        } catch (IOException ex) {
            request.abort();
        } finally {
            client.close();
        }
       Intent intent = new Intent(mContext.getApplicationContext(), ConfirmActivity.class);
       intent.putExtra("url", mUri);
       intent.putExtra("mimetype", mMimeType);
       intent.putExtra("privateBrowsing", mprivateBrowseing);
       intent.putExtra("filename", mfilename);
       intent.putExtra("contentLength", contentLength);
       mActivity.startActivity(intent);
 

    }

}
