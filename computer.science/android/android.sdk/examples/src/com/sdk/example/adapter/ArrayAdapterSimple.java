package com.sdk.example.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ArrayAdapterSimple extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ArrayAdapter<String> array = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1, getData());
		setListAdapter(array);
	}

	private List<String> getData() {
		List<String> slist = new ArrayList<String>();
		for(int i=0;i<10;i++) {
			slist.add("array adapter data: " + i);
		}
		return slist;
	}
}
