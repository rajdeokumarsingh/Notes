package com.sdk.example.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sdk.example.R;

public class RecreateActivity extends Activity {
	private static final String BUNDLE_KEY_THEME = "theme";

	int mCurTheme;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			mCurTheme = savedInstanceState.getInt(BUNDLE_KEY_THEME);

			// Switch to a new theme different from last theme.
			switch (mCurTheme) {
			case android.R.style.Theme_Holo_Light:
				mCurTheme = android.R.style.Theme_Holo_Dialog;
				break;
			case android.R.style.Theme_Holo_Dialog:
				mCurTheme = android.R.style.Theme_Holo;
				break;
			default:
				mCurTheme = android.R.style.Theme_Holo_Light;
				break;
			}
			setTheme(mCurTheme);
		}
		setContentView(R.layout.activity_recreate);
		
        // Watch for button clicks.
        Button button = (Button)findViewById(R.id.recreate);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				recreate();
			}
		});
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(BUNDLE_KEY_THEME, mCurTheme);
	}

}
