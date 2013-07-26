package com.sdk.example.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sdk.example.R;

public class Reorder2ndActivity extends Activity {
	private static final String LOGTAG = "Reorder2ndActivity";
	
	@Override
	protected void onCreate(Bundle savedState) {
		super.onCreate(savedState);

		setContentView(R.layout.reorder_two);

		Button twoButton = (Button) findViewById(R.id.reorder_launch_three);
		twoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(Reorder2ndActivity.this,
						Reorder3rdActivity.class));
			}
		});
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Log.i(LOGTAG, "onSaveInstanceState");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		Log.i(LOGTAG, "onRestoreInstanceState");
	}
}
