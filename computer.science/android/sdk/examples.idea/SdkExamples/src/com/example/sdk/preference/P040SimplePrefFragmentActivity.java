package com.example.sdk.preference;

import android.app.Activity;
import android.os.Bundle;


public class P040SimplePrefFragmentActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new P030SimplePrefFragment())
                .commit();
    }
}