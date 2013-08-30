/*
 * Copyright (C) 2008 The Android Open Source Project
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

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import com.android.browser.preferences.AdvancedPreferencesFragment;
import com.android.browser.preferences.BandwidthPreferencesFragment;
import com.android.browser.preferences.DebugPreferencesFragment;

import java.util.List;

public class BrowserPreferencesPage extends PreferenceActivity {

    public static final String CURRENT_PAGE = "currentPage";
    private List<Header> mHeaders;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(
                    ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);
        }
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
                String path = getPathFromFileUri(filename);
                path = DownloadDialog.UrlDecode(path);
                Editor ed = PreferenceManager.
                        getDefaultSharedPreferences(BrowserPreferencesPage.this).edit();
                    ed.putString(PreferenceKeys.PREF_DOWNLOAD_DIR_NEW, 
                                     path);
                ed.commit();
     
                startPreferenceFragment(new AdvancedPreferencesFragment(), false);
            }
        }
    };
    
    /**
     * Populate the activity with the top-level headers.
     */
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_headers, target);

        if (BrowserSettings.getInstance().isDebugEnabled()) {
            Header debug = new Header();
            debug.title = getText(R.string.pref_development_title);
            debug.fragment = DebugPreferencesFragment.class.getName();
            target.add(debug);
        }
        mHeaders = target;
    }

    @Override
    public Header onGetInitialHeader() {
        String action = getIntent().getAction();
        if (Intent.ACTION_MANAGE_NETWORK_USAGE.equals(action)) {
            String fragName = BandwidthPreferencesFragment.class.getName();
            for (Header h : mHeaders) {
                if (fragName.equals(h.fragment)) {
                    return h;
                }
            }
        }
        return super.onGetInitialHeader();
    }

    private static final int FILE_RESULT = 100;
    private static final int SELECT_FILE_PATH = 101;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == FILE_RESULT && resultCode == RESULT_OK) {
                String filename = data.getData().toString();
                String path = getPathFromFileUri(filename);
                path = DownloadDialog.UrlDecode(path);
                Editor ed = PreferenceManager.
                        getDefaultSharedPreferences(this).edit();
                    ed.putString(PreferenceKeys.PREF_DOWNLOAD_DIR_NEW, 
                                     path);
                ed.commit();
     
                startPreferenceFragment(new AdvancedPreferencesFragment(), false);
            }
         
            if(requestCode == SELECT_FILE_PATH && resultCode == RESULT_OK){
                String filename = data.getExtras().getString("file_path");
                String path = getPathFromFileUri(filename);
                    path = DownloadDialog.UrlDecode(path);
                    Editor ed = PreferenceManager.
                        getDefaultSharedPreferences(this).edit();
                    String status = Environment.getExternalStorageState();

                    ed.putString(PreferenceKeys.PREF_DOWNLOAD_DIR_NEW, 
                                   path);
                  
                    ed.commit();
     
                    startPreferenceFragment(new AdvancedPreferencesFragment(), false);
            }
    }
    public String getPathFromFileUri(String uri) {
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
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    };
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                } else {
                    finish();
                }
                return true;
        }

        return false;
    }

    @Override
    public Intent onBuildStartFragmentIntent(String fragmentName, Bundle args,
            int titleRes, int shortTitleRes) {
        Intent intent = super.onBuildStartFragmentIntent(fragmentName, args,
                titleRes, shortTitleRes);
        String url = getIntent().getStringExtra(CURRENT_PAGE);
        intent.putExtra(CURRENT_PAGE, url);
        return intent;
    }

}
