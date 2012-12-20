package com.sdk.example.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sdk.example.R;

public class ReceiveActivityResult extends Activity {

	// Definition of the one requestCode we use for receiving results.
	static final private int GET_CODE = 0;

	private TextView mResults;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initUI();
	}

	private void initUI() {
		setContentView(R.layout.receive_result);
		Button buttonResult = (Button) findViewById(R.id.get_activity_result);
		buttonResult.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Start the activity whose result we want to retrieve. The
				// result will come back with request code GET_CODE.
				Intent intent = new Intent(ReceiveActivityResult.this,
						SendActivityResult.class);
				startActivityForResult(intent, GET_CODE);
			}
		});
		
        // Retrieve the TextView widget that will display results.
        mResults = (TextView)findViewById(R.id.results);

        // This allows us to later extend the text buffer.
        mResults.setText(mResults.getText(), TextView.BufferType.EDITABLE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     // You can use the requestCode to select between multiple child
        // activities you may have started.  Here there is only one thing
        // we launch.
        if (requestCode == GET_CODE) {

            // We will be adding to our text.
            Editable text = (Editable)mResults.getText();

            // This is a standard resultCode that is sent back if the
            // activity doesn't supply an explicit result.  It will also
            // be returned if the activity failed to launch.
            if (resultCode == RESULT_CANCELED) {
                text.append("(cancelled)");

            // Our protocol with the sending activity is that it will send
            // text in 'data' as its result.
            } else {
                text.append("(okay ");
                text.append(Integer.toString(resultCode));
                text.append(") ");
                if (data != null) {
                    text.append(data.getAction());
                }
            }

            text.append("\n");
        }
	}
	
	

}
