package com.android.browser;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.android.browser.provider.BrowserProvider2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DeleteQuickNavigationPage extends Activity implements OnClickListener {

    private SelectItem selectItem;
    private long deleteId = -1;
    private String mTitle;

    private LinearLayout mOK, mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_delete);
        Intent intent = getIntent();
        selectItem = (SelectItem)intent.getSerializableExtra("selectItem");
        if(selectItem != null){
            mTitle = selectItem.title;
            deleteId = selectItem.id;
        }else {
            finish();
        }
        
        setTitle(String.format(getString(R.string.delete_quick_navigation_prompt), mTitle));
        mOK = (LinearLayout) findViewById(R.id.ok);
        mCancel = (LinearLayout) findViewById(R.id.cancel);
        mOK.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    private void delete() {
        if(deleteId != -1){
            getContentResolver().delete(BrowserProvider2.NavScreen.CONTENT_URI, "_id = ?", new String[]{deleteId+""});
        }
    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.ok:
            delete();
            finish();
            break;
        case R.id.cancel:
            finish();
            break;
        }
    }
}
