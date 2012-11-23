package com.sdk.example.util;


import com.sdk.example.R;

import android.app.Activity;
import android.os.Bundle;

public class ColorfulDivider extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// draw color dividers
		setContentView(R.layout.colorfull_divider);
	}

}
