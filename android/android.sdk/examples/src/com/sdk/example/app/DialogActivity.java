package com.sdk.example.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sdk.example.R;

public class DialogActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_RIGHT_ICON);
		setContentView(R.layout.dialog_activity);
		
		getWindow().setFeatureDrawableResource(Window.FEATURE_RIGHT_ICON,
				android.R.drawable.ic_dialog_alert);
		
		Button addBtn = (Button) findViewById(R.id.add);
		Button rmBtn = (Button) findViewById(R.id.remove);
		addBtn.setOnClickListener(mAddListener);
		rmBtn.setOnClickListener(mRemoveListener);
	}
	
	private OnClickListener mAddListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			LinearLayout inner = (LinearLayout) findViewById(R.id.inner_content);
			ImageView image = new ImageView(DialogActivity.this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.icon48x48_1));
			image.setPadding(4, 4, 4, 4);
			inner.addView(image);
		}
	}; 
	
	private OnClickListener mRemoveListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			LinearLayout inner = (LinearLayout) findViewById(R.id.inner_content);
			if(inner.getChildCount() != 0) {
				inner.removeViewAt(0);
			}
		}
	};

}
