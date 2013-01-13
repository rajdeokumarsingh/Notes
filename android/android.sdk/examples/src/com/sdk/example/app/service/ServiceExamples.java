package com.sdk.example.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.net.ConnectivityManager;
import android.util.Log;

import com.sdk.example.SdkMainActivity;

public class ServiceExamples extends SdkMainActivity {
	private static final String LOGTAG = "ServiceExamples";

	protected List<Map<String, Object>> getData() {
		List<Map<String, Object>> nameList = new ArrayList<Map<String, Object>>();

		nameList.add(addItem(ServiceForeground.Controller.class,
				"ServiceForeground"));
		nameList.add(addItem(ServiceLocalBindingActivity.Controller.class,
				"ServiceLocalBinding"));
		nameList.add(addItem(ServiceLocalBindingActivity.Binding.class,
				"ServiceLocalBinding2"));
//		nameList.add(addItem(ServiceLocalController.class,
//				"ServiceLocalController"));
		nameList.add(addItem(ServiceMessengerActivity.Binding.class, "ServiceMessenger"));
		
		nameList.add(addItem(ServiceRemoteController.Controller.class,
				"ServiceRemoteController Controller"));
		nameList.add(addItem(ServiceRemoteController.Binding.class,
				"ServiceRemoteController Binding"));
		nameList.add(addItem(ServiceRemoteController.BindingOptions.class,
				"ServiceRemoteController Binding Option"));
		
		nameList.add(addItem(ServiceStartArgumentController.class,
				"ServiceStartArgumentController"));

		ConnectivityManager con;
		try {
			Class c = Class.forName("android.net.ConnectivityManager");
			Log.i(LOGTAG, "get class: " + c);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return nameList;
	}
}
