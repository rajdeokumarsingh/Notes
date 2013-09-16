package com.example.sdk.preference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import com.example.sdk.R;

public class P030SimplePrefFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.simple_preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals("js_engine_flags")) {
            Preference pref = findPreference(s);
            pref.setSummary(sharedPreferences.getString("js_engine_flags", ""));
            return;
        }

        if(s.equals("plugin_state")) {
            Preference pref = findPreference(s);
            pref.setSummary(sharedPreferences.getString("plugin_state", ""));
            return;
        }
    }
}
