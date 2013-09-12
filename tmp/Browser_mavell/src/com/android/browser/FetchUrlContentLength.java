package com.android.browser;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.URLUtil;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.conn.params.ConnRouteParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Downloads;
import android.content.Intent;
import android.database.Cursor;
import android.net.Proxy;
import android.net.http.AndroidHttpClient;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;


public class FetchUrlContentLength extends Thread {

    private Context mContext;
    private String mUri;
    private String mCookies;
    private String mUserAgent;
    private String mfilename;
    private String mMimeType;
    private String mReferer;
    private boolean mprivateBrowseing;
    private Activity mActivity;
    private boolean mAlreadyLive;
    private SharedPreferences mPrefs;
    private final static String TOGTAG = "FetchUrlContentLength";
    public final static String uiDelay = "uiDelay";
    private final static int APK_DIALOG = 1111;
    private Handler mHandler;

    public FetchUrlContentLength(Activity activity, boolean privateBrowsing,
            String uri, String userAgent, String mimeType, String referer, String fileName) {
        mContext = activity.getApplicationContext();
        mActivity = activity;
        mUri = uri;
        mMimeType = mimeType;
        mUserAgent = userAgent;
        mfilename = fileName;
        mReferer = referer;
        mprivateBrowseing = privateBrowsing;
        mCookies  = CookieManager.getInstance().getCookie(mUri, mprivateBrowseing);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
        mHandler = new Handler(){
        	@Override
        	public void handleMessage(Message msg) {
        		// TODO Auto-generated method stub
        		super.handleMessage(msg);
        		switch (msg.what) {
				case APK_DIALOG:
					final Intent intent = (Intent)msg.obj;
		    	    new AlertDialog.Builder(mActivity, AlertDialog.THEME_HOLO_LIGHT)
		    	    .setTitle(R.string.cu_download)
		    	    .setMessage(R.string.download_already_has)
		    	    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
		    	            public void onClick(DialogInterface dialog, int whichButton) {
		    	                mPrefs.edit().putLong(uiDelay, System.currentTimeMillis()+60*60*1000).commit();
		    	                mActivity.startActivity(intent);
		    	            }
		    	        })
		    	     .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
		    	        public void onClick(DialogInterface dialog, int whichButton) {
		    	        }
		    	       })
		    	     .show();
					break;

				default:
					break;
				}
        	}
        };
    }

    private boolean checkUiDelay(){
    	long remember = mPrefs.getLong(uiDelay, 0);
    	if(System.currentTimeMillis() > remember){
    		return true;
    	}
    	return false;
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
             }
        } catch (IllegalArgumentException ex) {
            request.abort();
        } catch (IOException ex) {
            request.abort();
        } finally {
            client.close();
        }
        
       String unFileName = mfilename.substring(0, mfilename.lastIndexOf("."));
       long oneHour = System.currentTimeMillis()-3600000;
       String SELECTION = Downloads.Impl.COLUMN_TOTAL_BYTES
               + " = ? AND " + Downloads.Impl.COLUMN_MIME_TYPE + " = ? AND "+Downloads.Impl.COLUMN_TITLE+" LIKE '"+unFileName+"%' "+
       		"AND "+ Downloads.Impl.COLUMN_LAST_MODIFICATION + " > "+oneHour;
       Cursor cursor = mContext.getContentResolver().query(Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI,
               null, SELECTION, new String[]{contentLength, "application/vnd.android.package-archive"}, null);
       Log.v(TOGTAG, "query cursor count:"+cursor.getCount());
       int index = 0;
       int state = 0;
       mAlreadyLive = false;
       while(cursor.moveToNext()){
    	   index = cursor.getColumnIndex(Downloads.Impl.COLUMN_STATUS);
    	   state = cursor.getInt(index);
    	   if(!Downloads.Impl.isStatusError(state)){
    		   mAlreadyLive = true;
    		   break;
    	   } else {
    		   mAlreadyLive = false;
    	   }
       }
       cursor.close();
       boolean UiDelay = checkUiDelay();
       final Intent intent = new Intent(mContext.getApplicationContext(), DownloadDialog.class);
       intent.putExtra("url", mUri);
       intent.putExtra("mimetype", mMimeType);
       intent.putExtra("privateBrowsing", mprivateBrowseing);
       intent.putExtra("filename", mfilename);
       intent.putExtra("contentLength", contentLength);
       intent.putExtra("userAgent", mUserAgent);
	   intent.putExtra("referer", mReferer);
       if(mAlreadyLive && UiDelay){
    	   Message msg = new Message();
    	   msg.what = APK_DIALOG;
    	   msg.obj = intent;
    	   mHandler.sendMessage(msg);
       } else {
           mActivity.startActivity(intent);
       }
    }

}
