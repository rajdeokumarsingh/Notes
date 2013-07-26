package com.sdk.example.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sdk.example.SdkMainActivity;

import android.os.Bundle;

public class AdapterExamples extends SdkMainActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected List<Map<String, Object>> getData() {
		List<Map<String, Object>> nameList = new ArrayList<Map<String, Object>>();

		nameList.add(addItem(ArrayAdapterSimple.class, "Array Adapter Simple"));
		nameList.add(addItem(ArrayAdapterImageText.class, "Array Adapter Image"));

		return nameList;
	}
}
