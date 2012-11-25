package com.sdk.example.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.sdk.example.R;

/**
 * <p>Demonstrates required behavior of saving and restoring dynamic activity
 * state, so that an activity will restart with the correct state if it is
 * stopped by the system.</p>
 *
 * <p>In general, any activity that has been paused may be stopped by the system
 * at any time if it needs more resources for the currently running activity.
 * To handle this, before being paused the
 * {@link android.app.Activity#onSaveInstanceState onSaveInstanceState()} method is called before
 * an activity is paused, allowing it to supply its current state.  If that
 * activity then needs to be stopped, upon restarting it will receive its
 * last saved state in
 * {@link android.app.Activity#onCreate}.</p>
 * <p>In this example we are currently saving and restoring the state of the
 * top text editor, but not of the bottom text editor.  You can see the difference
 * by editing the two text fields, then going to a couple different
 * applications while the demo is running and then returning back to it.  The
 * system takes care of saving a view's state as long as an id has been
 * assigned to the view, so we assign an ID to the view being saved but not
 * one to the view that isn't being saved.</p>
 * 
 * FIXME: Jiang Rui: The status of a EditText with ID can be saved automatically.
 * 	But if a EditText has NO ID, it status can not be saved.
 * 		We can test this by change screen orientation.
 * 
 * <h4>Demo</h4>
 * App/Activity/Save &amp; Restore State
 * <h4>Source files</h4>
 * <table class="LinkTable">
        <tr>
            <td class="LinkColumn">src/com.example.android.apis/app/SaveRestoreState.java</td>
            <td class="DescrColumn">The Save/Restore Screen implementation</td>
        </tr>
        <tr>
            <td class="LinkColumn">/res/any/layout/save_restore_state.xml</td>
            <td class="DescrColumn">Defines contents of the screen</td>
        </tr>
</table>
 */
public class SaveRestoreStateActivity extends Activity {
	private static final String LOGTAG = "SaveRestoreStateActivity";
	private static final String BUNDLE_KEY_SAVED_TEXT = "saved text";

	/**
	 * Initialization of the Activity after it is first created. Here we use
	 * {@link android.app.Activity#setContentView setContentView()} to set up
	 * the Activity's content, and retrieve the EditText widget whose state we
	 * will save/restore.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Be sure to call the super class.
		super.onCreate(savedInstanceState);

		// See assets/res/any/layout/save_restore_state.xml for this
		// view layout definition, which is being set here as
		// the content of our screen.
		setContentView(R.layout.save_restore_state);

		// Set message to be appropriate for this screen.
		((TextView) findViewById(R.id.msg)).setText(R.string.save_restore_msg);

		Log.i(LOGTAG, "onCreate");
//		if (savedInstanceState != null) {
//			String text = savedInstanceState.getString(BUNDLE_KEY_SAVED_TEXT);
//			if (text != null) {
//				Log.i(LOGTAG, "onCreate date: " + text);
//				setSavedText(text);
//			}
//		}
	}

//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		Log.i(LOGTAG, "onSaveInstanceState not: " + getSavedText().toString());
//		outState.putString(BUNDLE_KEY_SAVED_TEXT, getSavedText().toString());
//	}
//
//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		super.onRestoreInstanceState(savedInstanceState);
//		Log.i(LOGTAG, "onRestoreInstanceState");
//		if (savedInstanceState != null) {
//			String text = savedInstanceState.getString(BUNDLE_KEY_SAVED_TEXT);
//			if (text != null) {
//				Log.i(LOGTAG, "onRestoreInstanceState not restore: " + text);
//				setSavedText(text);
//			}
//		}
//	}

	/**
	 * Retrieve the text that is currently in the "saved" editor.
	 */
	CharSequence getSavedText() {
		return ((EditText) findViewById(R.id.saved)).getText();
	}

	/**
	 * Change the text that is currently in the "saved" editor.
	 */
	void setSavedText(CharSequence text) {
		((EditText) findViewById(R.id.saved)).setText(text);
	}
}
