package com.sdk.example.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sdk.example.R;

public class ReorderActivity extends Activity {
	private static final String LOGTAG = "ReorderActivity";

	@Override
	protected void onCreate(Bundle savedState) {
		super.onCreate(savedState);

		setContentView(R.layout.reorder_on_launch);

		Button twoButton = (Button) findViewById(R.id.reorder_launch_two);
		twoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(ReorderActivity.this,
						Reorder2ndActivity.class));
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
