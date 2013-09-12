/*
 * Copyright (C) 2008 The Android Open Source Project
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
 * limitations under the License.
 */

package com.android.browser;

import android.app.AlertDialog;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.EditTextPreference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.util.AttributeSet;
import android.util.Log;
import android.net.Uri;

import java.io.File;

import com.android.browser.filemanager.FileManagerMainActivity;

//public class DownloadDirPreferenceNew extends EditTextPreference {
public class DownloadDirPreferenceNew extends Preference {

    private final static String LOGTAG = "DownloadDirPreferenceNew";
    private static final int FILE_RESULT = 100;
    private static final int SELECT_FILE_PATH = 101;

    public DownloadDirPreferenceNew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DownloadDirPreferenceNew(Context context) {
        super(context);
    }

    public void onClick(View v) {      
    }

    @Override
    protected void onClick() {

//        Intent intent = new Intent("com.pekall.action.CHOOSE_DIRECTORY");
//        ((Activity) getContext()).startActivityForResult(intent, FILE_RESULT);     
        Intent intent = new Intent(this.getContext(), FileManagerMainActivity.class);
        intent.putExtra("choosepath", true);
        ((Activity) getContext()).startActivityForResult(intent, FILE_RESULT);  
      
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
