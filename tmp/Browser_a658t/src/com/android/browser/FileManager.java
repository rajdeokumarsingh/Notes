package com.android.browser;

import com.android.browser.filemanager.FileManagerMainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.util.AttributeSet;

public class FileManager extends Preference {

    public FileManager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    public FileManager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onClick() {

//        Intent intent = new Intent("com.pekall.action.CHOOSE_DIRECTORY");
//        ((Activity) getContext()).startActivityForResult(intent, FILE_RESULT);     
        Intent intent = new Intent(this.getContext(), FileManagerMainActivity.class);
        ((Activity) getContext()).startActivity(intent);  
      
    } 
}
