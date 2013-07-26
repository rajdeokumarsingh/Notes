package com.sdk.example.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sdk.example.R;

public class Reorder4thActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        
        setContentView(R.layout.reorder_four);
        
        Button twoButton = (Button) findViewById(R.id.reorder_second_to_front);
        twoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Reorder4thActivity.this, Reorder2ndActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}
		});
    }
}
