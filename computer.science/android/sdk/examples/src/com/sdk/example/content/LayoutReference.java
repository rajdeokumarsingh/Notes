package com.sdk.example.content;

import com.sdk.example.R;

import android.app.Activity;
import android.os.Bundle;

public class LayoutReference extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // This layout uses different configurations to adjust
        // what is shown based on the smallest width that will occur.
		
		setContentView(R.layout.resources_layout_reference);
		
		// setContentView(R.layout.resources_layout_reference_tablet);
	}

}
