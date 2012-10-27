package com.sdk.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sdk.example.adapter.AdapterExamples;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SdkMainActivity extends ListActivity {

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Map<String, Object> map = (Map<String, Object>) l
				.getItemAtPosition(position);
		Toast.makeText(this, (String) map.get("title"), Toast.LENGTH_SHORT)
				.show();

		if (map.get("intent") != null) {
			startActivity((Intent) map.get("intent"));
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new SimpleAdapter(this, getData(),
				android.R.layout.simple_list_item_1, new String[] { "title" },
				new int[] { android.R.id.text1 }));
		getListView().setTextFilterEnabled(true);
	}

	protected List<Map<String, Object>> getData() {
		List<Map<String, Object>> nameList = new ArrayList<Map<String, Object>>();

		nameList.add(addItem(AdapterExamples.class, "Adapter Samples"));

//		for (int i = 0; i < 10; i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("title", "title" + i);
//			map.put("content", "content" + i);
//			nameList.add(map);
//		}
		return nameList;
	}

	protected Map<String, Object> addItem(Class cls, String title) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		Intent intent = new Intent();
		// intent.setClassName("com.sdk.example.adapter", "AdapterExamples");
		intent.setClass(this, cls);
		map.put("intent", intent);
		return map;
	}
}
