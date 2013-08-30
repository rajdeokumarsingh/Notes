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
 * limitations under the License
 */

package com.android.browser.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.android.browser.BrowserSettings;
import com.android.browser.PreferenceKeys;
import com.android.browser.R;

public class BandwidthPreferencesFragment extends PreferenceFragment {

    static final String TAG = "BandwidthPreferencesFragment";
    static final int requestCode = 1;
    private Preference e = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the XML preferences file
        int id = 0;
        if(BrowserSettings.CMCC_PLATFORM){
            id = R.xml.bandwidth_preferences;
        }else{
            id = R.xml.bandwidth_preferences_cu;
        }
        addPreferencesFromResource(id);
        if(BrowserSettings.CMCC_PLATFORM){
            e = findPreference(PreferenceKeys.PRE_SAVE_AGENT);
            e.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                
                public boolean onPreferenceClick(Preference preference) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(getActivity(), AgentPreferencesActivity.class);
                    startActivityForResult(intent, requestCode);
                    return false;
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getPreferenceScreen().getSharedPreferences()
                .contains(PreferenceKeys.PREF_DATA_PRELOAD)) {
            // set default value for preload setting
            ListPreference preload = (ListPreference) getPreferenceScreen().findPreference(
                    PreferenceKeys.PREF_DATA_PRELOAD);
            if (preload != null) {
                preload.setValue(BrowserSettings.getInstance().getDefaultPreloadSetting());
            }
        }
    }

}
