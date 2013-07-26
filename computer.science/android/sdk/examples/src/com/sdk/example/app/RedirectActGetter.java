package com.sdk.example.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sdk.example.R;

public class RedirectActGetter extends Activity{
	private String mTextPref;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.redirect_getter);

        // Watch for button clicks.
        Button applyButton = (Button)findViewById(R.id.apply);
        applyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
	            SharedPreferences preferences = getSharedPreferences("RedirectData", MODE_PRIVATE);
	            SharedPreferences.Editor editor = preferences.edit();
	            editor.putString("text", mText.getText().toString());

	            if (editor.commit()) {
	                setResult(RESULT_OK);
	            }

	            finish();			
			}
		});

        // The text being set.
        mText = (TextView)findViewById(R.id.text);

        // Display the stored values, or if not stored initialize with an empty String
        loadPrefs();
    }
    
    private final void loadPrefs()
    {
        // Retrieve the current redirect values.
        // NOTE: because this preference is shared between multiple
        // activities, you must be careful about when you read or write
        // it in order to keep from stepping on yourself.
        SharedPreferences preferences = getSharedPreferences("RedirectData", MODE_PRIVATE);

        mTextPref = preferences.getString("text", null);
        if (mTextPref != null) {
            mText.setText(mTextPref);
        } else {
            mText.setText("");
        }
    }

    
}
