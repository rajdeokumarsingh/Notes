/* ---------------------------------------------------------------------------------------------
 *
 *            Capital Alliance Software Confidential Proprietary
 *            (c) Copyright CAS 201{x}, All Rights Reserved
 *                          www.pekall.com
 *
 * ----------------------------------------------------------------------------------------------
 */
package com.android.browser.filemanager;

import com.android.browser.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Window;

/**
 * @author haoanbang
 * 
 */
public class FileSettingsActivity extends PreferenceActivity {
	public static final String KEY_SHOW_HIDEFILE = "showhidefile";
	public static final String KEY_ONLY_SHOW_FILENAME = "onlyshowname";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);
	}
	
}
