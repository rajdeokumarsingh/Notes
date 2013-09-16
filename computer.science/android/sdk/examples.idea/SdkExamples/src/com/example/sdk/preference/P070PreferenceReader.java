package com.example.sdk.preference;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;


public class P070PreferenceReader extends Activity {
    private static final String LOGTAG = "P070PreferenceReader";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean quickControl = sharedPref.getBoolean("enable_quick_controls", true);
        Log.i(LOGTAG, "quickControl: " + quickControl);

        boolean quickControl1 = sharedPref.getBoolean("enable_quick_controls1", true);
        Log.i(LOGTAG, "quickControl 1: " + quickControl1);

        boolean quickControl2 = sharedPref.getBoolean("enable_quick_controls2", true);
        Log.i(LOGTAG, "quickControl 2: " + quickControl2);

        String jsFlags = sharedPref.getString("js_engine_flags", "");
        Log.i(LOGTAG, "jsFlags: " + jsFlags);
    }
}