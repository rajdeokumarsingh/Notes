package com.android.browser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;

public class ApnSetting extends Preference {

    private final static String LOGTAG = "ApnSetting";
    private static final int FILE_RESULT = 100;
    private static final int SELECT_FILE_PATH = 101;

    public ApnSetting(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ApnSetting(Context context) {
        super(context);
    }

    @Override
    protected void onClick() {

        Intent intent = new Intent("android.settings.APN_SETTINGS");
        ((Activity) getContext()).startActivity(intent);
      
    }  

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        if(restoreValue == false) {
            persistString((String)defaultValue);
        }
    }
}
