package com.sdk.example.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sdk.example.R;

public class ForwardActivity extends Activity {

    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.forwarding);

        Button goButton = (Button)findViewById(R.id.go);
        goButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
	            // Here we start the next activity, and then call finish()
	            // so that our own will stop running and be removed from the
	            // history stack.
				Intent intent = new Intent();
				intent.setClass(ForwardActivity.this, ForwardTargetActivity.class);
				startActivity(intent);
				finish();
			}
		});
    };
}
