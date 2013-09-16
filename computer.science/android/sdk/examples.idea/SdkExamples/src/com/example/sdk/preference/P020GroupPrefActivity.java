package com.example.sdk.preference;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.example.sdk.R;

public class P020GroupPrefActivity extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.group_preferences);
    }
}