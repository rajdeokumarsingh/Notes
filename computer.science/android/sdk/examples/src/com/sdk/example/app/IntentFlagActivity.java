package com.sdk.example.app;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sdk.example.R;
import com.sdk.example.SdkMainActivity;

public class IntentFlagActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initUI();
	}

	private void initUI() {
		setContentView(R.layout.intent_activity_flags);

		// Watch for button clicks.
		Button button = (Button) findViewById(R.id.flag_activity_clear_task);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Start multiple activities at once
				startActivities(buildIntentsToViewsLists());
			}
		});
		
		button = (Button) findViewById(R.id.flag_activity_clear_task_pi);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
	            Context context = IntentFlagActivity.this;

				PendingIntent pi = PendingIntent.getActivities(context, 0,
						buildIntentsToViewsLists(),
						PendingIntent.FLAG_UPDATE_CURRENT);
	            try {
	                pi.send();
	            } catch (CanceledException e) {
	                Log.w("IntentActivityFlags", "Failed sending PendingIntent", e);
	            }
			}
		});
	}

	/**
	 * Create a intent list, startActivities() will launch each intent 
	 * one by one, which will build a new stack. 
	 */
	private Intent[] buildIntentsToViewsLists() {
		// We are going to rebuild our task with a new back stack. This will
		// be done by launching an array of Intents, representing the new
		// back stack to be created, with the first entry holding the root
		// and requesting to reset the back stack.
		Intent[] intents = new Intent[3];

		// First: root activity of ApiDemos.
		// This is a convenient way to make the proper Intent to launch and
		// reset an application's task.
//		intents[0] = Intent.makeRestartActivityTask(new ComponentName(this,
//				SdkMainActivity.class));

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setClass(IntentFlagActivity.this, SdkMainActivity.class);
		intents[0] = intent;
		
		// TODO: add intent handler in SdkMainActivity
		intent = new Intent(Intent.ACTION_MAIN);
		intent.setClass(IntentFlagActivity.this, SdkMainActivity.class);
		intent.putExtra("com.sdk.example.content", "ContentExamples");
		intents[1] = intent;

		// TODO: add intent handler in SdkMainActivity
		intent = new Intent(Intent.ACTION_MAIN);
		intent.setClass(IntentFlagActivity.this, SdkMainActivity.class);
		intent.putExtra("com.sdk.example.content",
				"ContentExamples/AssetsExamples");

		intents[2] = intent;
		return intents;
	}

}
