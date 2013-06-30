package com.example.sdktrain002lifecycle;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

    private static final String LOGTAG = "MainActivity";

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOGTAG, "onCreate");

        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOGTAG, "onStart");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOGTAG, "onStop");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOGTAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOGTAG, "onPause");

        // partially visible, should:
        // 1. Stop animations or other ongoing actions that could consume CPU.
        // 2. Commit unsaved changes, but only if users expect such changes
        //      to be permanently saved when they leave (such as a draft email).
        // 3. Release system resources, such as broadcast receivers, handles to sensors (like GPS),
        //      or any resources that may affect battery life while your activity is paused
        //      and the user does not need them
        // 4. stop video
        // 5. stop camera

        // 6.  avoid performing CPU-intensive work, like write database
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOGTAG, "onDestroy");
    }

}
