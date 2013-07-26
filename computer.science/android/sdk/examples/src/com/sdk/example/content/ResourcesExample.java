package com.sdk.example.content;

import com.sdk.example.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

public class ResourcesExample extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.resources);
		
		TextView tvStyle = (TextView) findViewById(R.id.styled_text);
		TextView tvPlain = (TextView) findViewById(R.id.plain_text);
		TextView tv = (TextView) findViewById(R.id.res1);
		
		CharSequence cs = getText(R.string.styled_text);
		tvStyle.setText(cs);
		
		String str = getString(R.string.styled_text);
		tvPlain.setText(str);
		
		cs = getResources().getText(R.string.styled_text);
		tv.setText(cs);
	}

}
