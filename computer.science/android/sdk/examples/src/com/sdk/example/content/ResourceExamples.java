package com.sdk.example.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sdk.example.SdkMainActivity;
import com.sdk.example.app.AlertDlgExamples;

public class ResourceExamples extends SdkMainActivity {
	
	protected List<Map<String, Object>> getData() {
		List<Map<String, Object>> nameList = new ArrayList<Map<String, Object>>();
		nameList.add(addItem(LayoutReference.class, "Layout Reference"));
		nameList.add(addItem(ResourcesExample.class, "Resources"));
		nameList.add(addItem(SmallestWidth.class, "Smallest Width"));
		nameList.add(addItem(StyleTextExample.class, "Style Text"));
		nameList.add(addItem(WidthAndHeight.class, "Width and Heigth"));
		return nameList;
	}
}
