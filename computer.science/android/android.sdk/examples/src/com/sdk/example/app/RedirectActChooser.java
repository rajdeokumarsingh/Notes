package com.sdk.example.app;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sdk.example.R;

public class RedirectActChooser extends Activity{
    static final int INIT_TEXT_REQUEST = 0;
    static final int NEW_TEXT_REQUEST = 1;

    private String mTextPref;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();

        // Retrieve the current text preference.  If there is no text
        // preference set, we need to get it from the user by invoking the
        // activity that retrieves it.  To do this cleanly, we will
        // temporarily hide our own activity so it is not displayed until the
        // result is returned.
        if (!loadPrefs()) {
            Intent intent = new Intent(this, RedirectActGetter.class);
            startActivityForResult(intent, INIT_TEXT_REQUEST);
        }
    }

	private void initUI() {
		setContentView(R.layout.redirect_main);

        // Watch for button clicks.
        Button clearButton = (Button)findViewById(R.id.clear);
        clearButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				  // Erase the preferences and exit!
	            SharedPreferences preferences = getSharedPreferences("RedirectData", MODE_PRIVATE);
	            preferences.edit().remove("text").commit();
	            finish();
			}
		});
        Button newButton = (Button)findViewById(R.id.newView);
        newButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

	            // Retrieve new text preferences.
	            Intent intent = new Intent(RedirectActChooser.this, RedirectActGetter.class);
	            startActivityForResult(intent, NEW_TEXT_REQUEST);				
			}
		});
	}

    @Override
	protected void onActivityResult(int requestCode, int resultCode,
		Intent data) {
        if (requestCode == INIT_TEXT_REQUEST) {

            // If the request was cancelled, then we are cancelled as well.
            if (resultCode == RESULT_CANCELED) {
                finish();

            // Otherwise, there now should be text...  reload the prefs,
            // and show our UI.  (Optionally we could verify that the text
            // is now set and exit if it isn't.)
            } else {
                loadPrefs();
            }

        } else if (requestCode == NEW_TEXT_REQUEST) {

            // In this case we are just changing the text, so if it was
            // cancelled then we can leave things as-is.
            if (resultCode != RESULT_CANCELED) {
                loadPrefs();
            }

        }
    }

    private final boolean loadPrefs() {
        // Retrieve the current redirect values.
        // NOTE: because this preference is shared between multiple
        // activities, you must be careful about when you read or write
        // it in order to keep from stepping on yourself.
        SharedPreferences preferences = getSharedPreferences("RedirectData", MODE_PRIVATE);

        mTextPref = preferences.getString("text", null);
        if (mTextPref != null) {
            TextView text = (TextView)findViewById(R.id.text);
            text.setText(mTextPref);
            return true;
        }

        return false;
    }

}
