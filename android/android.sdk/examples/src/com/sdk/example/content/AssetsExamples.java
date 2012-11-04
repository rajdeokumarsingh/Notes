package com.sdk.example.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sdk.example.SdkMainActivity;
import com.sdk.example.app.AlertDlgExamples;

public class AssetsExamples extends SdkMainActivity {
	protected List<Map<String, Object>> getData() {
		List<Map<String, Object>> nameList = new ArrayList<Map<String, Object>>();
		nameList.add(addItem(ReadAssetExample.class, "Read Assets"));
		return nameList;
	}
}
