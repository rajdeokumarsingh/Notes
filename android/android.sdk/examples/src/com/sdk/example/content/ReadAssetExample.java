package com.sdk.example.content;

import java.io.IOException;
import java.io.InputStream;

import com.sdk.example.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ReadAssetExample extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_asset);
		
		try {
			InputStream is = getAssets().open("read_asset.txt");
			int size = is.available();
			byte[] buffer = new byte[size];
			
			is.read(buffer);
			is.close();
			
			TextView tv = (TextView) findViewById(R.id.text);
			tv.setText(new String(buffer));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
