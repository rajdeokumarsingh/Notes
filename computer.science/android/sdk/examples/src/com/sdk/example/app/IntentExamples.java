package com.sdk.example.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sdk.example.R;

public class IntentExamples extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.intents);

		initUI();
	}

	private void initUI() {
		Button buttonMusic = (Button) findViewById(R.id.get_music);
		buttonMusic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("audio/*");
				startActivity(Intent.createChooser(intent, "Select music"));
			}
		});
		
		Button buttonVideo = (Button) findViewById(R.id.get_video);
		buttonVideo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("video/*");
				startActivity(Intent.createChooser(intent, "Select video"));
			}
		});
		
		Button buttonText = (Button) findViewById(R.id.get_text);
		buttonText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("text/*");
				startActivity(Intent.createChooser(intent, "Select text"));
			}
		});
		
		Button buttonBrowser = (Button) findViewById(R.id.get_browser);
		buttonBrowser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://www.baidu.com"));
				startActivity(Intent.createChooser(intent, "Select Browser"));
			}
		});
	}
}
