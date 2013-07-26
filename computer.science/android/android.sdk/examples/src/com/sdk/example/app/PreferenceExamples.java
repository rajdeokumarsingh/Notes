package com.sdk.example.app;

import com.sdk.example.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class PreferenceExamples extends Activity {

	private static String PREF_EDIT_TEXT = "text";
	private static String PREF_EDIT_SELECTION_START = "selection-start";
	private static String PREF_EDIT_SELECTION_END = "selection-end";

	private EditText mSaved;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.save_restore_state);

		TextView tv = (TextView) findViewById(R.id.msg);
		tv.setText(R.string.persistent_msg);

		mSaved = (EditText) findViewById(R.id.saved);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		String text = prefs.getString(PREF_EDIT_TEXT, null);
		if (text != null) {
			mSaved.setText(text, BufferType.EDITABLE);

			int start = prefs.getInt(PREF_EDIT_SELECTION_START, -1);
			int end = prefs.getInt(PREF_EDIT_SELECTION_END, -1);
			if (start != -1 && end != -1) {
				mSaved.setSelection(start, end);
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
		editor.putString(PREF_EDIT_TEXT, mSaved.getText().toString());
		editor.putInt(PREF_EDIT_SELECTION_START, mSaved.getSelectionStart());
		editor.putInt(PREF_EDIT_SELECTION_END, mSaved.getSelectionEnd());
		editor.commit();
	}
}
