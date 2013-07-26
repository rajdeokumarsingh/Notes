package com.sdk.example.content;

import com.sdk.example.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class StyleTextExample extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.styled_text);

		// Use CharSequence which includes style information instead of String
		CharSequence cs = getText(R.string.styled_text);
		TextView tv = (TextView) findViewById(R.id.text);
		tv.setText(cs);
	}
}
