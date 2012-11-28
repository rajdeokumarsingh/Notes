package com.sdk.example.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sdk.example.SdkMainActivity;
import com.sdk.example.app.theme.ThemeExamples;

public class ActivityExamples extends SdkMainActivity {
	protected List<Map<String, Object>> getData() {
		List<Map<String, Object>> nameList = new ArrayList<Map<String, Object>>();

		nameList.add(addItem(CustomDialog.class, "Custom Dialog"));
		nameList.add(addItem(DialogActivity.class, "Dialog Activity"));
		nameList.add(addItem(CustomTitleActivity.class, "Custom Title"));
		nameList.add(addItem(ThemeExamples.class, "Theme Examples"));
		nameList.add(addItem(PreferenceExamples.class, "Preference Examples"));
		nameList.add(addItem(IntentExamples.class, "Intent Examples"));
		nameList.add(addItem(ReceiveActivityResult.class,
				"Receive Activity Result Examples"));
		
		nameList.add(addItem(IntentFlagActivity.class, "IntentFlagActivity"));
		nameList.add(addItem(ForwardActivity.class, "ForwardActivity"));
		nameList.add(addItem(RecreateActivity.class, "RecreateActivity"));
		nameList.add(addItem(RedirectionActivity.class, "RedirectionActivity"));
		nameList.add(addItem(ReorderActivity.class, "ReorderActivity"));
		nameList.add(addItem(SaveRestoreStateActivity.class, "SaveRestoreStateActivity"));
		nameList.add(addItem(MenuExamples.class, "MenuExamples"));
		
		return nameList;
	}
}
