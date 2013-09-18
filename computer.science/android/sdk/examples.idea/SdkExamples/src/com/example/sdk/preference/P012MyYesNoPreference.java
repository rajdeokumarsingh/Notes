package com.example.sdk.preference;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;


public class P012MyYesNoPreference extends DialogPreference {
    private static final String LOGTAG = "P012MyYesNoPreference";

    public P012MyYesNoPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public P012MyYesNoPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            // setEnabled(false);

            if ("privacy_clear_cache".equals(getKey())) {
                Log.i(LOGTAG, "clear cache");
            }
        }
    }
}