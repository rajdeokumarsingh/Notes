package com.android.browser;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.android.browser.preferences.AdvancedPreferencesFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.WebAddress;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.SimpleAdapter.ViewBinder;

public class DownloadDialog extends Activity implements View.OnClickListener{
    private static final int FILE_RESULT = 100;
    private static final int SELECT_FILE_PATH = 101;
    private static final String LOGTAG = "DownloadDialog";
    public static final int AUDIO = 1;
    public static final int IMADE = 2;
    public static final int VIDEO = 3;
    public static final int APK = 5;
    public static final int SUPPORT= 6;
    public static final int ELSE = 4;
    public static final String AUDIO_DIR = "music";
    public static final String IMADE_DIR = "pictures";
    public static final String VIDEO_DIR = "movies";
    CarrierContentRestriction ccr = null;
    private EditText file_path = null;
    private EditText file_name = null;
    private ImageButton choose_path = null;
    private Button cancel = null;
    private Button ok = null;
    private String url = null;
    private String mimetype = null;
    private boolean privateBrowsing = false;
    private String filename = null;
    private String referer = null;
    private String download_path = null;
    private String file_path_text = null;
    private String default_download_path = null;
    private String userAgent = null;
    private boolean another_ex_storage  =  false;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        int layout = 0;
        if(BrowserSettings.CU_PLATFORM){
            layout = R.layout.custom_dialog_cp;
        }else{
            layout = R.layout.custom_dialog;
        }
        setContentView(layout);
        setTitle(R.string.download_new);
        
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        mimetype = intent.getStringExtra("mimetype");
        privateBrowsing = intent.getBooleanExtra("privateBrowsing", false);
        filename = intent.getStringExtra("filename");
        referer = intent.getStringExtra("referer");
        userAgent = intent.getStringExtra("userAgent");
        another_ex_storage = intent.getBooleanExtra("use_in", false);
        Log.e(LOGTAG, "enter DownloadDialog url:" + url+" mimetype: "+mimetype+" privateBrowsing: "+privateBrowsing+" filename: "+filename+" userAgent:"+userAgent);
        
        file_name = (EditText)findViewById(R.id.new_filename);
        if(file_name!=null)
            file_name.setText(filename);
         
        file_path = (EditText)findViewById(R.id.save_file_path);
        ccr = new CarrierContentRestriction();
        int contentType = checkContentType(ccr, mimetype);
        if(contentType==ELSE){
         //   Toast.makeText(DownloadDialog.this, R.string.alert_dialog_not_format, Toast.LENGTH_SHORT).show();
        }
        SharedPreferences spre = PreferenceManager.getDefaultSharedPreferences(this);
        default_download_path = getString(R.string.pref_default_download_path);
        download_path = spre.getString(PreferenceKeys.PREF_DOWNLOAD_DIR_NEW, default_download_path);
        if(another_ex_storage){
            download_path =  spre.getString(PreferenceKeys.PREF_DOWNLOAD_DIR_OLD, "/flash/Download");
        }
        file_path_text = null;
       
        if(contentType==1){
            file_path_text = download_path+"/"+AUDIO_DIR;
        }else if(contentType==2){
            file_path_text = download_path+"/"+IMADE_DIR;
        }else if(contentType==3){
            file_path_text = download_path+"/"+VIDEO_DIR;
        }else {
            file_path_text = download_path;
        }
     

        file_path.setText(file_path_text);
        choose_path = (ImageButton)findViewById(R.id.choose_path);
        choose_path.setImageResource(R.drawable.ic_folder_holo_dark);
        choose_path.setOnClickListener(this);
        
        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        ok = (Button)findViewById(R.id.ok);
        ok.setOnClickListener(this);
        
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.pekall.action.choosepath.result");
        this.registerReceiver(receiver, filter);
    }
      
      BroadcastReceiver receiver = new BroadcastReceiver() {
          
          @Override
          public void onReceive(Context context, Intent intent) {
              // TODO Auto-generated method stub
              if(intent.getAction().equals("android.pekall.action.choosepath.result")){
                  String filename = intent.getStringExtra("path");
                  String new_file_path_text = getPathFromFileUri(filename); 
                  new_file_path_text = UrlDecode(new_file_path_text);
                  Log.v(LOGTAG, "get path from FileManager :"+new_file_path_text);
                  file_path.setText(new_file_path_text);
              //    if(!file_path_text.equals(new_file_path_text))
                     Toast.makeText(DownloadDialog.this, new_file_path_text, Toast.LENGTH_LONG).show();
                     file_path_text = new_file_path_text;
              }
          }
      };
      
      public void onClick(View v) {
        // TODO Auto-generated method stub
          int id = v.getId();
          switch (id) {
            case R.id.choose_path:
                
                Intent intent = new Intent("android.pekall.action.CHOOSEPATH");
                DownloadDialog.this.startActivityForResult(intent, FILE_RESULT);     
                
                break;
            case R.id.cancel:
                this.finish();
                break;
            case R.id.ok:
                filename = file_name.getText().toString();
                onDownloadStartNoStream(url, mimetype, privateBrowsing, userAgent, referer);
                break;        
            default:
                break;
        }
        
    }
    
      @Override
      protected void onDestroy() {
          // TODO Auto-generated method stub
          super.onDestroy();
          unregisterReceiver(receiver);
      }
      
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
       
        if (requestCode == FILE_RESULT && resultCode == RESULT_OK) {
            String filename = data.getData().toString();
            String new_file_path_text = getPathFromFileUri(filename); 
            new_file_path_text = UrlDecode(new_file_path_text);
            Log.v(LOGTAG, "get path from FileManager :"+new_file_path_text);
            file_path.setText(new_file_path_text);
        //    if(!file_path_text.equals(new_file_path_text))
               Toast.makeText(this, new_file_path_text, Toast.LENGTH_LONG).show();
               file_path_text = new_file_path_text;
         }
        
        if(requestCode == SELECT_FILE_PATH && resultCode == RESULT_OK){
            String filename = data.getExtras().getString("file_path");
            String new_file_path_text = getPathFromFileUri(filename); 
            new_file_path_text = UrlDecode(new_file_path_text);
            Log.v(LOGTAG, "get path from FileManager :"+new_file_path_text);
            file_path.setText(new_file_path_text);
       //     if(!file_path_text.equals(new_file_path_text))
               Toast.makeText(this, new_file_path_text, Toast.LENGTH_LONG).show();
               file_path_text = new_file_path_text;
        }
    }
    public static String UrlDecode(String url){
        if(url.startsWith("/")){
            url = url.substring(url.indexOf("/")+1);
        }
        String[] strs = url.split("/");
        StringBuffer buffer = new StringBuffer();
        try {
        for(int i=0; i< strs.length; i++){
             String decode = URLDecoder.decode(strs[i],"UTF-8");
             buffer.append("/");
             buffer.append(decode);
        }   
         } catch (UnsupportedEncodingException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
        return buffer.toString();
    }
    public static int checkContentType(CarrierContentRestriction ccr, String mimetype){
        if(ccr.checkAudioContentType(mimetype)){
            return AUDIO;
        }else if(ccr.checkImageContentType(mimetype)){
            return IMADE;
        }else if(ccr.checkVideoContentType(mimetype)){
            return VIDEO;
        }else if(ccr.checkAndroidAPK(mimetype)){
            return APK;
        }else if(ccr.checkSupportContentType(mimetype)){
            return SUPPORT;
        }else{
            return ELSE;
        }
    }
    public static String getPathFromFileUri(String uri) {
        if (uri != null && uri.indexOf("file://") == 0) {
            String tmp = uri.substring(7);

            //Remove the /mnt prefix
            if(tmp.indexOf("/mnt") == 0) 
                return tmp.substring(4);

            return tmp;
        }
        
        if(uri != null && uri.indexOf("/mnt") == 0){
            return uri.substring(4);
        }
        
        if(uri !=null && uri.indexOf("/storage") == 0){
        	String tmp = uri.substring(8);
        	return tmp;
        }
        return null;
    }
    void onDownloadStartNoStream(String url, String mimetype, boolean privateBrowsing, String userAgent, String referer){
        // java.net.URI is a lot stricter than KURL so we have to encode some
        // extra characters. Fix for b 2538060 and b 1634719
        WebAddress webAddress;
        try {
            webAddress = new WebAddress(url);
            webAddress.setPath(encodePath(webAddress.getPath()));
        } catch (Exception e) {
            // This only happens for very bad urls, we want to chatch the
            // exception here
            Log.e(LOGTAG, "Exception trying to parse url:" + url);
            return;
        }

        String addressString = webAddress.toString();
        Uri uri = Uri.parse(addressString);
        final DownloadManager.Request request;
        
        try {
            request = new DownloadManager.Request(uri);
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, R.string.cannot_download, Toast.LENGTH_SHORT).show();
            return;
        }
        
        request.setMimeType(mimetype);

        // set downloaded file destination to /sdcard/Download.
        // or, should it be set to one of several Environment.DIRECTORY* dirs depending on mimetype?
        // request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
 
        createFiles(download_path);
        File download_file = getFilePath(file_path_text+"/"+filename);
        Uri fileUri = Uri.fromFile(download_file);
        request.setDestinationUri(fileUri);
        Log.v(LOGTAG, "enter DownloadDialog-onDownloadStartNoStream url:"
                +url+" mimetype:"+mimetype+" fileuri:"+fileUri.toString());

        // let this downloaded file be scanned by MediaScanner - so that it can 
        // show up in Gallery app, for example.
        request.allowScanningByMediaScanner();
        request.setDescription(webAddress.getHost());
        // XXX: Have to use the old url since the cookies were stored using the
        // old percent-encoded url.
        String cookies = CookieManager.getInstance().getCookie(url, privateBrowsing);
        request.addRequestHeader("cookie", cookies);
        request.addRequestHeader("User-Agent", userAgent);
        request.addRequestHeader("Referer", referer);
        request.setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
       
        if(BrowserSettings.CMCC_PLATFORM) {
            String apn = BrowserSettings.getInstance().getDataConnection();
            int type = ~0; // allow all network type

            // TODO: Just hardcode here, need to add const string in settings
            if(isWifiConnected()){
                type = Request.NETWORK_WIFI;
            }else if("wap".equals(apn)) {
  //              type = Request.NETWORK_MOBILE_WAP;
            } else if("internet".equals(apn)) {
 //               type= Request.NETWORK_MOBILE_INTERNET;
            } 

            Log.v(LOGTAG, "setAllowedNetworkTypes: " + type);
            request.setAllowedNetworkTypes(type);
        }

        final DownloadManager manager
                = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        new Thread("Browser download") {
             public void run() {
                  manager.enqueue(request);
             }
        }.start();
        
        Toast.makeText(this, R.string.download_pending, Toast.LENGTH_SHORT)
                .show();
        Intent data = new Intent();
        data.setAction("android.intent.action.VIEW_DOWNLOADS");
        startActivity(data);
        finish();
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
    static File getFile(String edit_path){
        String[] path = edit_path.split("/"); 
        File root = null;
        if(path[1].contains("external_sdcard")){
 //           root = Environment.getExternalStorageDirectory_ext();
            root = new File("/mnt/flash");
        }else if(path[1].contains("sdcard")){
            root = Environment.getExternalStorageDirectory();
        }else {
//            root = Environment.getExternalStorageDirectory_ext().getPath();
            root = new File("/storage/emulated/0");
        }
        File downloadDir = null;
        for(int i = 3; i<path.length; i++){
            if(i==3){
                downloadDir = new File(root, path[3]);
                if (!downloadDir.isDirectory() && !downloadDir.mkdirs()) {
                    Log.e(LOGTAG, "download aborted - can't create picturesDir directory " 
                            + downloadDir.getPath());
                   }
            }else{
                downloadDir = new File(downloadDir, path[i]);
                if (!downloadDir.isDirectory() && !downloadDir.mkdirs()) {
                    Log.e(LOGTAG, "download aborted - can't create picturesDir directory " 
                            + downloadDir.getPath());
                   }
            }
        }
        return downloadDir;
    }
    static File getFilePath(String edit_path){
        String[] path = edit_path.split("/");   
        File downloadDir = null;
        File root = null;
        if(path[1].contains("external_sdcard")){
//            root = Environment.getExternalStorageDirectory_ext();
            root = new File("/mnt/flash");
        }else if(path[1].contains("sdcard")){
            root = Environment.getExternalStorageDirectory();
        }else{
//            root = Environment.getExternalStorageDirectory_ext().getPath();
            root = new File("/storage/emulated");
        }
        for(int i = 2; i<path.length; i++){
            if(i==2){
                downloadDir = new File(root, path[2]);
            }else{
                downloadDir = new File(downloadDir, path[i]);
            }
        }
        return downloadDir;
    }
    static void createFiles(String download_path){

         File downloadDir = getFile(download_path);
         File musicDir =  getFile(download_path+"/"+AUDIO_DIR);
         File moviesDir =  getFile(download_path+"/"+IMADE_DIR);
         File picturesDir =  getFile(download_path+"/"+VIDEO_DIR);
 
    }
    boolean isWifiConnected() {
        Log.v(LOGTAG, "enter isWifiConnected()");

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
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
}
