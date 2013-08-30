package com.android.browser;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class RotaryScreen extends Activity implements View.OnClickListener{
    
    private BrowserSettings mSettings;
    private RadioGroup group;
    private Button button;
    private RadioButton system;
    private RadioButton horizontal;
    private RadioButton vertical;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mSettings = BrowserSettings.getInstance();  // style="?android:attr/buttonBarStyle"  style="?android:attr/buttonBarButtonStyle"
        setTitle(R.string.rotary_screen);
        getWindow().setBackgroundDrawable(new ColorDrawable(0x55123456));
        setContentView(R.layout.screen_rotray);
        group = (RadioGroup)findViewById(R.id.rotary_radioGroup);
        system = (RadioButton)findViewById(R.id.radio_system);
        horizontal = (RadioButton)findViewById(R.id.radio_horizontal);
        vertical = (RadioButton)findViewById(R.id.radio_vertical);
        int rotary = mSettings.getSharedPreferences().getInt(PreferenceKeys.PREF_ROTARY, 0);
        switch (rotary) {
        case 0:
            system.setChecked(true);
            break;

        case 1:
            vertical.setChecked(true);
            break;
            
        case 2:
            horizontal.setChecked(true);
            break;            
        default:
            break;
        }

        group.setLayoutParams(new LayoutParams((getWindowManager().getDefaultDisplay().getWidth()/3)*2, LayoutParams.MATCH_PARENT));
        system.setOnClickListener(this);
        horizontal.setOnClickListener(this);
        vertical.setOnClickListener(this);
        button = (Button)findViewById(R.id.cancel);
         
        button.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }
    
    public void onClick(View v) {
        // TODO Auto-generated method stub
        boolean checked =  ((RadioButton)v).isChecked();
        
        int rotary = 0;
        
        switch (v.getId()) {
        case R.id.radio_system:
            if (checked){
                rotary = 0;
                mSettings.getSharedPreferences().edit().putInt(PreferenceKeys.PREF_ROTARY, 0).commit();
            }
            break;
            
        case R.id.radio_horizontal:
            if (checked){
                rotary = 2;
                mSettings.getSharedPreferences().edit().putInt(PreferenceKeys.PREF_ROTARY, 2).commit();
            }
            break;
            
        case R.id.radio_vertical:
            if (checked){
                rotary = 1;
                mSettings.getSharedPreferences().edit().putInt(PreferenceKeys.PREF_ROTARY, 1).commit();
            }
            break;

        default:
            break;
        }
        
        Intent intent = new Intent();
        intent.putExtra("rotary", rotary);
        setResult(Activity.RESULT_OK, intent) ;
        finish();
    }
       
}
