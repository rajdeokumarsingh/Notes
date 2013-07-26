package com.sdk.example.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sdk.example.SdkMainActivity;

public class ActionBarExamples extends SdkMainActivity {
	protected List<Map<String, Object>> getData() {
		List<Map<String, Object>> nameList = new ArrayList<Map<String, Object>>();

		nameList.add(addItem(ActionBarMechanicsActivity.class, "ActionBarMechanicsActivity"));
		nameList.add(addItem(ActionBarUsageActivity.class, "ActionBarUsageActivity"));
		nameList.add(addItem(ActionProviderActivity.class, "ActionProviderActivity"));
		nameList.add(addItem(DisplayOptionsActivity.class, "DisplayOptionsActivity"));
		nameList.add(addItem(ActionBarTabsActivity.class, "ActionBarTabsActivity"));
		
		return nameList;
	}
}
