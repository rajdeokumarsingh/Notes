package com.sdk.example.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sdk.example.R;

public class SendActivityResult extends Activity {
    /**
     * Initialization of the Activity after it is first created.  Must at least
     * call {@link android.app.Activity#setContentView setContentView()} to
     * describe what is to be displayed in the screen.
     */
    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
        // Be sure to call the super class.
        super.onCreate(savedInstanceState);

        // See assets/res/any/layout/hello_world.xml for this
        // view layout definition, which is being set here as
        // the content of our screen.
        setContentView(R.layout.send_result);

        // Watch for button clicks.
        Button button = (Button)findViewById(R.id.corky);
        button.setOnClickListener(mCorkyListener);
        button = (Button)findViewById(R.id.violet);
        button.setOnClickListener(mVioletListener);
    }

    private OnClickListener mCorkyListener = new OnClickListener()
    {
        public void onClick(View v)
        {
            // To send a result, simply call setResult() before your
            // activity is finished.
            setResult(RESULT_OK, (new Intent()).setAction("Corky!"));
            finish();
        }
    };

    private OnClickListener mVioletListener = new OnClickListener()
    {
        public void onClick(View v)
        {
            // To send a result, simply call setResult() before your
            // activity is finished.
            setResult(RESULT_OK, (new Intent()).setAction("Violet!"));
            finish();
        }
    };
}
