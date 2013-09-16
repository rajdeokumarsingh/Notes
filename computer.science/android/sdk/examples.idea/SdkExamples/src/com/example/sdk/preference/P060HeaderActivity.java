package com.example.sdk.preference;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.example.sdk.R;

import java.util.List;

public class P060HeaderActivity extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_headers, target);
    }
}