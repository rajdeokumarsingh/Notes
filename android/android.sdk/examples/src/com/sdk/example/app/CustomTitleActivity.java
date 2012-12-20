package com.sdk.example.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sdk.example.R;

public class CustomTitleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.custom_title);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_1);
		
		final TextView leftText = (TextView) findViewById(R.id.left_text);
		final TextView rightText = (TextView) findViewById(R.id.right_text);
		final EditText leftEdit = (EditText) findViewById(R.id.left_text_edit);
		final EditText rightEdit = (EditText) findViewById(R.id.right_text_edit);
		Button leftBtn = (Button) findViewById(R.id.left_text_button);
		Button rightBtn = (Button) findViewById(R.id.right_text_button);
		
		leftBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				leftText.setText(leftEdit.getText());
			}
		});
		rightBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rightText.setText(rightEdit.getText());
			}
		});
	}
}
