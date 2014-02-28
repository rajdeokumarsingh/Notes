package com.example.testdpi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // densityDpi = 120dpi is ldpi, densityDpi = 160dpi is mdpi,
        // densityDpi = 240dpi is hdpi, densityDpi = 320dpi is xhdpi
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Toast.makeText(this, "dpi is: " + dm.densityDpi, Toast.LENGTH_LONG).show();
    }
}
