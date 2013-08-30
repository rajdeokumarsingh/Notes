/*
 * Copyright (C) 2006 The Android Open Source Project
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

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import com.android.browser.provider.BrowserProvider2;
import com.android.browser.provider.BrowserProvider2.NavScreen;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.net.WebAddress;

public class AddQuickNavigationPage extends Activity implements OnClickListener {

    private long addId = -1;
    private long editId = -1;
    private boolean add = false;
    private SelectItem mItemValue;
    private String mTitle;
    private String mUrl;
    private String mTitleCopy;
    private String mUrlCopy;

    private EditText mTitleEt;
    private EditText mUrlEt;
    private Button mOk, mCancel;
    private boolean urlChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_quick_navigation);
        Intent intent = getIntent();
        addId = intent.getLongExtra("index", -1);
        mItemValue = (SelectItem)intent.getSerializableExtra("selectItem");
        
        if(addId != -1){
            add = true;
        }
        
        if(mItemValue != null){
            editId = mItemValue.id;
            mTitle = mItemValue.title;
            mUrl = mItemValue.url;
            mTitleCopy = mTitle;
            mUrlCopy = mUrl;
            add = false;
        }
        
        mTitleEt = (EditText) findViewById(R.id.title);
        mUrlEt = (EditText) findViewById(R.id.address);
        mOk = (Button) findViewById(R.id.OK);
        mCancel = (Button) findViewById(R.id.cancel);
        mOk.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mTitleEt.setText(mTitle);
        mUrlEt.setText(mUrl);
        urlChange = false;
    }

    private boolean isValid() {
        urlChange = false;
        final String title = mTitleEt.getText().toString().trim();
        String unfilteredUrl;
        unfilteredUrl = UrlUtils.fixUrl(mUrlEt.getText().toString());

        boolean emptyTitle = title.length() == 0;
        boolean emptyUrl = unfilteredUrl.trim().length() == 0;
        Resources r = getResources();
        if (emptyTitle) {
            if (emptyTitle) {
                mTitleEt.setError(r.getText(R.string.bookmark_needs_title));
            }
            if (emptyUrl) {
                mUrlEt.setError(r.getText(R.string.bookmark_needs_url));
            }
            return false;

        }
        String url = unfilteredUrl.trim();
        if (!Patterns.WEB_URL.matcher(url).matches()
                && !UrlUtils.ACCEPTED_URI_SCHEMA.matcher(url).matches()) {
            mUrlEt.setError(r.getText(R.string.bookmark_url_not_valid));
            return false;
        }
        
        if(!add && !mUrl.equalsIgnoreCase(url)){
            urlChange = true;
        }
        
        mTitle = title;
        mUrl = url;
        if(!url.contains("://")){
            mUrl = "http://" + url;
        }
        return true;
    }

    private void save() {
        Bitmap defaultDrawable = BitmapFactory.decodeResource(getResources(), R.drawable.ic_screen_globe_default);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        defaultDrawable.compress(Bitmap.CompressFormat.PNG, 100, os);
        
        if(add){
            Cursor cursor = null;
                try {
                    cursor= getContentResolver().query(BrowserProvider2.NavScreen.CONTENT_URI, BrowserProvider2.SCREEN_PROJECTION, "url = ?", new String[]{mUrl}, null);
                    if(cursor!=null && cursor.moveToFirst()){
                        Toast.makeText(this, R.string.screen_not_double, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                } finally {
                    if(cursor != null){
                        cursor.close();
                    }
                }

            ContentValues values = new ContentValues();
            values.put(BrowserProvider2.NavScreen.TITLE, mTitle);
            values.put(BrowserProvider2.NavScreen.URL, mUrl);
            values.put(BrowserProvider2.NavScreen.UPDATE, 1);
            values.put(BrowserProvider2.NavScreen.DATA, os.toByteArray());
            getContentResolver().update(BrowserProvider2.NavScreen.CONTENT_URI, values, "_id = ?", new String[]{addId+""});
            values.clear();
            values.put(BrowserProvider2.NavScreen.TITLE, this.getResources().getString(R.string.add_new_quick_navigation));
            values.put(BrowserProvider2.NavScreen.UPDATE, 1);
            values.put(BrowserProvider2.NavScreen.DATE, Long.toString(System.currentTimeMillis()));
            getContentResolver().insert(BrowserProvider2.NavScreen.CONTENT_URI, values);
        }else {
            ContentValues values = new ContentValues();
            values.put(BrowserProvider2.NavScreen.TITLE, mTitle);
            if(urlChange){
                values.put(BrowserProvider2.NavScreen.UPDATE, 1);
            }else{
                values.put(BrowserProvider2.NavScreen.UPDATE, 0);
            }
            values.put(BrowserProvider2.NavScreen.URL, mUrl);
            getContentResolver().update(BrowserProvider2.NavScreen.CONTENT_URI, values, "_id = ?", new String[]{editId+""});
        }
    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.OK:
            if (isValid()) {
                if (mTitle.equals(mTitleCopy) && mUrl.equals(mUrlCopy)) {
                    setResult(-1);
                } else {
                    save();
//                    Intent intent = new Intent();
//                    intent.putExtra("index", mIndex);
//                    int result = RESULT_OK;
//                    if (mUrl.equals(mUrlCopy)) {
//                        result = RESULT_CANCELED;
//                    }
//                    setResult(result, intent);
                }
                finish();
            }
            break;
        case R.id.cancel:
            setResult(-1);
            finish();
            break;
        }
    }

}