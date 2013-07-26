package com.sdk.example.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sdk.example.R;

public class Reorder3rdActivity extends Activity {
	private static final String LOGTAG = "Reorder3rdActivity";
	
	@Override
	protected void onCreate(Bundle savedState) {
		super.onCreate(savedState);

		setContentView(R.layout.reorder_three);

		Button twoButton = (Button) findViewById(R.id.reorder_launch_four);
		twoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(Reorder3rdActivity.this,
						Reorder4thActivity.class));
			}
		});
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.i(LOGTAG, "onSaveInstanceState");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.i(LOGTAG, "onRestoreInstanceState");
	}

}
