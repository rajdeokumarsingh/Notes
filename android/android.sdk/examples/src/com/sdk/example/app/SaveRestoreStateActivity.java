package com.sdk.example.app;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.sdk.example.R;

public class SaveRestoreStateActivity extends Activity {
    private static final String LOGTAG = "SaveRestoreStateActivity";
	private static final String BUNDLE_KEY_SAVED_TEXT = "saved text";

	/**
     * Initialization of the Activity after it is first created.  Here we use
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
        ((TextView)findViewById(R.id.msg)).setText(R.string.save_restore_msg);
        
        if(savedInstanceState != null) {
        	String text = savedInstanceState.getString(BUNDLE_KEY_SAVED_TEXT);
        	if(text != null)
        		setSavedText(text);
        }
    }

    @Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.i(LOGTAG, "onSaveInstanceState, no super");
		outState.putString(BUNDLE_KEY_SAVED_TEXT, getSavedText().toString());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.i(LOGTAG, "onRestoreInstanceState");
	}

	/**
     * Retrieve the text that is currently in the "saved" editor.
     */
    CharSequence getSavedText() {
        return ((EditText)findViewById(R.id.saved)).getText();
    }

    /**
     * Change the text that is currently in the "saved" editor.
     */
    void setSavedText(CharSequence text) {
        ((EditText)findViewById(R.id.saved)).setText(text);
    }
}
