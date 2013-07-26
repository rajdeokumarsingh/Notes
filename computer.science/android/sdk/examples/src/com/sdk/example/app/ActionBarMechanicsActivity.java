package com.sdk.example.app;

import com.sdk.example.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

public class ActionBarMechanicsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // The Action Bar is a window feature. The feature must be requested
        // before setting a content view. Normally this is set automatically
        // by your Activity's theme in your manifest. The provided system
        // theme Theme.WithActionBar enables this for you. Use it as you would
        // use Theme.NoTitleBar. You can add an Action Bar to your own themes
        // by adding the element <item name="android:windowActionBar">true</item>
        // to your style definition.
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Normal item");
		
		MenuItem action = menu.add("Action item");
		action.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		// become a normal menu item
		// action.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		action.setIcon(android.R.drawable.ic_menu_share);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
		return true;
	}
}
