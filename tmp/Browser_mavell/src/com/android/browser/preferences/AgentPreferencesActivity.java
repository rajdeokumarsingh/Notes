package com.android.browser.preferences;


import com.android.browser.PreferenceKeys;
import com.android.browser.R;
import com.android.browser.provider.BrowserProvider2;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class AgentPreferencesActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener{

    private Preference e = null;
    private EditTextPreference ip = null;
    private EditTextPreference port = null;
    private String ipText = null;
    private String portText = null;
    private boolean mFirstTime = false;
    private Cursor mCursor;
    private static final int column_ip = 1;
    private static final int column_port = 2;
    private String sNotSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.save_agent);
        ip = (EditTextPreference)findPreference(PreferenceKeys.PREF_SETTING_IP);
        ip.setOnPreferenceChangeListener(this);
        port = (EditTextPreference)findPreference(PreferenceKeys.PREF_SETTING_PORT);
        port.setOnPreferenceChangeListener(this);
        mFirstTime = savedInstanceState == null;
        sNotSet = getResources().getString(R.string.not_set);
        mCursor = getContentResolver().query(BrowserProvider2.Agents.CONTENT_URI, null, null, null, null);
        mCursor.moveToFirst();
        fillUi();
    }
    private void fillUi() {
        if (mFirstTime) {
            mFirstTime = false;
            ip.setText(mCursor.getString(column_ip));
            port.setText(mCursor.getString(column_port));
        }
        ip.setSummary(checkNull(ip.getText()));
        port.setSummary(checkNull(port.getText()));
    }
    
    private String checkNull(String value) {
        if (value == null || value.length() == 0) {
            return sNotSet;
        } else {
            return value;
        }
    }
    private String ipS = null;
    private String portS = null;
    public boolean insertDB(){
        ipS = ip.getText();
        portS = port.getText();
        if(ipS!=null&&portS!=null){
        ContentValues values = new ContentValues();
        values.put(BrowserProvider2.Agents.AGENT, ipS);
        values.put(BrowserProvider2.Agents.PORT, portS);
        getContentResolver().update(BrowserProvider2.Agents.CONTENT_URI, values, "_id = 0", null);
        return true;
        }
        return false;
}
     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
         super.onCreateOptionsMenu(menu);
         menu.add(0, Menu.FIRST, 0, R.string.autofill_profile_editor_save_profile);
         menu.add(0, Menu.FIRST+1, 0, R.string.autofill_profile_editor_delete_profile);
 
         return true;
        
    }
 
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        // TODO Auto-generated method stub
         
         if(preference.getKey().equals(PreferenceKeys.PREF_SETTING_IP)){
             ip.setText((String)newValue);
             ip.setSummary(ip.getText());
         } 
            
        if(preference.getKey().equals(PreferenceKeys.PREF_SETTING_PORT)){
            port.setText((String)newValue);
            port.setSummary(port.getText());
         }    
        return false;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
         switch (keyCode) {
             case KeyEvent.KEYCODE_BACK: {
                 if (insertDB()) {
                     finish();
                 }
                 return true;
             }
         }
         return super.onKeyDown(keyCode, event);
     }
     
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
        case Menu.FIRST:
            if (insertDB()) {
                finish();
            }
            return true;
        case Menu.FIRST+1: 
            deleteApn();
            return true;
        }
        return super.onOptionsItemSelected(item);
   }

    private void deleteApn() {
        ContentValues values = new ContentValues();
        values.put(BrowserProvider2.Agents.AGENT, "");
        values.put(BrowserProvider2.Agents.PORT, "");
        ip.setText("");
        port.setText("");
        getContentResolver().update(BrowserProvider2.Agents.CONTENT_URI,values,"_id=0",null);
        finish();
    }
}
