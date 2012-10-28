package com.sdk.example.package1.manager;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdk.example.R;
import com.sdk.example.SdkMainActivity;

public class PackageInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		getActivityInfo();
		
		LinearLayout main = (LinearLayout) getLayoutInflater().inflate(
				R.layout.activity_package_info_main, null);
		TextView text = (TextView) main.findViewById(R.id.textView1);
		text.setText(getActivityInfo());
		setContentView(main);
	}

	private String getActivityInfo() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(SdkMainActivity.CATEGORY_SDK_STUDY);
		PackageManager pm = getPackageManager();
		List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);

		Iterator<ResolveInfo> it = list.iterator();
		StringBuilder builder = new StringBuilder("");
		while(it.hasNext()) {
			ResolveInfo info = it.next();
			builder.append(info.activityInfo.name + ",");
			builder.append(info.activityInfo.packageName + ",");
			builder.append(info.activityInfo.processName + ";");
			builder.append('\n');
		}
		return builder.toString();
	}

}
