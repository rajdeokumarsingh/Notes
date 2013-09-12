package com.android.browser;

import android.util.Log;
import android.webkit.CacheManager;
import android.webkit.CacheManager.CacheResult;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.net.URI;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

public class PageDownloader {
    private ArrayList<String> mPageUrls;

    private static final String LOGTAG = "PageDownloader";

    // Convert page root url to download path
    private String convertUrl2Path(String url) {
        // convert [:/&%#?] in url to '.' or '_'
        Pattern pattern = Pattern.compile("[:/&%#?=]+");
        return pattern.matcher(url).replaceAll("_") + "/";
    }

    private String getDownloadBasePath() { 
        return "/mnt/sdcard/Downloads/.my_pages/";
    }

    private String getDownloadPath(String url) { 
        return getDownloadBasePath() + convertUrl2Path(url);
    }

    // Get the resource file from browser cache
    private boolean getResourceFromCache(String url, File resourceFile) {
        boolean ret = false;
        CacheResult cr = CacheManager.getCacheFile(url, null);
        if(cr == null) return ret;

        InputStream in = null;
        OutputStream out = null;
        try {
            in = cr.getInputStream();
            out = new FileOutputStream(resourceFile);
            byte[] buf = new byte[4096];
            int len;
            while ((len = in.read(buf)) > 0){
                out.write(buf, 0, len);
            }
            ret = true;
        } catch (java.io.FileNotFoundException fe) {
            Log.e(LOGTAG, "", fe);
        } catch (java.io.IOException ioe) {
            Log.e(LOGTAG, "", ioe);
        } catch (Exception ge) {
            Log.e(LOGTAG, "", ge);
        } finally {
            try {
                if(in != null) in.close();
                if(out != null) out.close();
            } catch (java.io.IOException ioe) {
                Log.e(LOGTAG, "", ioe);
            }
        }
        return ret;
    }

    public PageDownloader() {
        // Log.v(LOGTAG, "constructor()");
        mPageUrls = new ArrayList<String>();
    }

    public void reset() {
        // Log.v(LOGTAG, "reset()");
        synchronized(mPageUrls) {
            mPageUrls.clear();
        }
    }

    public void add(String url) {
        // Log.v(LOGTAG, "add(): " + url);
        synchronized(mPageUrls) {
            mPageUrls.add(url);
        }
    }

    public ArrayList<String> getPageUrls() {
        synchronized(mPageUrls) {
            return (ArrayList<String>)mPageUrls.clone();            
        }
    }

    public boolean isPageDownload(String url) {
        String path = getDownloadBasePath() + convertUrl2Path(url);
        if((new File(path)).exists()) {
            return true;
        }
        return false;
    }        

    public void downloadPage() {
        // Log.v(LOGTAG, "downloadPage()");

        File downloadDir = new File(getDownloadPath(mPageUrls.get(0))); 
        if (!downloadDir.isDirectory() && !downloadDir.mkdirs()) {
            Log.e(LOGTAG, "can't create download directory: " 
                    + downloadDir.getPath());
            return;
        }

        for(String url : mPageUrls) {
            Log.v(LOGTAG, "Download resource: " + url);

            try {
                // create folder for resources
                URI uri = new URI(url);
                File file = new File(uri.getPath());
                File parentDir = new File(downloadDir, file.getParent());
                if (!parentDir.isDirectory() && !parentDir.mkdirs()) {
                    Log.e(LOGTAG, "can't create pareent directory: " 
                            + parentDir.getPath());
                    continue;
                }

                File resourceFile = new File(parentDir, file.getName());
                Log.i(LOGTAG, "resource dir: " + parentDir.getPath());
                Log.i(LOGTAG, "resource file: " + resourceFile.getPath());
                if(!resourceFile.exists() && !resourceFile.createNewFile()) {
                    Log.e(LOGTAG, "can't create resourceFile: " 
                            + resourceFile.getPath());
                    continue;
                }

                if(getResourceFromCache(url, resourceFile)) {
                    Log.v(LOGTAG, "Got resource from browser cache!");
                    continue;
                } else {
                    // TODO: 如果从cache中无法获取，考虑能否通过downlaod manager下载
                    // 要求不能有系统的下载notification
                    Log.v(LOGTAG, "Try http download");
                }
            } catch (java.net.URISyntaxException ue) {
                Log.e(LOGTAG, "copy resource:", ue);
                continue;
            } catch (java.io.IOException ioe) {
                Log.e(LOGTAG, "copy resource:", ioe);
                continue;
            }
        }
    }
}

