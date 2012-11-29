package com.sdk.example.app;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;

import com.sdk.example.R;


public class DisplayOptionsActivity extends Activity implements
		View.OnClickListener, ActionBar.TabListener {
	private View mCustomView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action_bar_display_options);

		findViewById(R.id.toggle_home_as_up).setOnClickListener(this);
		findViewById(R.id.toggle_show_home).setOnClickListener(this);
		findViewById(R.id.toggle_use_logo).setOnClickListener(this);
		findViewById(R.id.toggle_show_title).setOnClickListener(this);
		findViewById(R.id.toggle_show_custom).setOnClickListener(this);
		findViewById(R.id.toggle_navigation).setOnClickListener(this);
		findViewById(R.id.cycle_custom_gravity).setOnClickListener(this);

//		mCustomView = getLayoutInflater().inflate(
//				R.layout.action_bar_display_options_custom, null);
//		final ActionBar bar = getActionBar();
//		bar.setCustomView(mCustomView, new ActionBar.LayoutParams(
//				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//
//		bar.addTab(bar.newTab().setText("Tab 1").setTabListener(this));
//		bar.addTab(bar.newTab().setText("Tab 2").setTabListener(this));
//		bar.addTab(bar.newTab().setText("Tab 3").setTabListener(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.display_options_actions, menu);
		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onClick(View v) {
		final ActionBar bar = getActionBar();
		int flags = 0;
		switch (v.getId()) {
		case R.id.toggle_home_as_up:
			flags = ActionBar.DISPLAY_HOME_AS_UP;
			break;
		case R.id.toggle_show_home:
			flags = ActionBar.DISPLAY_SHOW_HOME;
			break;
		case R.id.toggle_use_logo:
			flags = ActionBar.DISPLAY_USE_LOGO;
			break;
		case R.id.toggle_show_title:
			flags = ActionBar.DISPLAY_SHOW_TITLE;
			break;
//		case R.id.toggle_show_custom:
//			flags = ActionBar.DISPLAY_SHOW_CUSTOM;
//			break;
		case R.id.toggle_navigation:
			bar.setNavigationMode(bar.getNavigationMode() == ActionBar.NAVIGATION_MODE_STANDARD ? 
					ActionBar.NAVIGATION_MODE_TABS : ActionBar.NAVIGATION_MODE_STANDARD);
			return;
//		case R.id.cycle_custom_gravity:
//			ActionBar.LayoutParams lp = (ActionBar.LayoutParams) mCustomView
//					.getLayoutParams();
//			int newGravity = 0;
//			switch (lp.gravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
//			case Gravity.LEFT:
//				newGravity = Gravity.CENTER_HORIZONTAL;
//				break;
//			case Gravity.CENTER_HORIZONTAL:
//				newGravity = Gravity.RIGHT;
//				break;
//			case Gravity.RIGHT:
//				newGravity = Gravity.LEFT;
//				break;
//			}
//			lp.gravity = lp.gravity & ~Gravity.HORIZONTAL_GRAVITY_MASK
//					| newGravity;
//			bar.setCustomView(mCustomView, lp);
//			return;
		}

		int change = bar.getDisplayOptions() ^ flags;
		bar.setDisplayOptions(change, flags);
	}

}
