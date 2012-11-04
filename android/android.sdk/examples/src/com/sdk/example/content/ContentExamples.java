package com.sdk.example.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sdk.example.SdkMainActivity;
import com.sdk.example.app.ActivityExamples;
import com.sdk.example.app.AlertDlgExamples;

public class ContentExamples extends SdkMainActivity {
	protected List<Map<String, Object>> getData() {
		List<Map<String, Object>> nameList = new ArrayList<Map<String, Object>>();
		nameList.add(addItem(AssetsExamples.class, "Assets"));
		nameList.add(addItem(ClipboardExamples.class, "Clipboard"));
		nameList.add(addItem(PackageExamples.class, "Package"));
		nameList.add(addItem(ProviderExamples.class, "Provider"));
		nameList.add(addItem(ResourceExamples.class, "Resource"));
		nameList.add(addItem(StorageExamples.class, "Storage"));
		return nameList;
	}
}
