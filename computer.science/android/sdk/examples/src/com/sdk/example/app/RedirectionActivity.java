package com.sdk.example.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sdk.example.R;

public class RedirectionActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.redirect_enter);

		// Watch for button clicks.
		Button goButton = (Button) findViewById(R.id.go);
		goButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Here we start up the main entry point of our redirection
				// example.
				Intent intent = new Intent(RedirectionActivity.this,
						RedirectActChooser.class);
				startActivity(intent);

			}
		});
	}
}
