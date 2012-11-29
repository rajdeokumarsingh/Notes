package com.sdk.example.app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

import com.sdk.example.R;

public class ActionProviderActivity extends Activity {
	
    private static final String SHARED_FILE_NAME = "shared.png";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		copyPrivateRawResuorceToPubliclyAccessibleFile();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.action_bar_action_provider, menu);
		
		MenuItem actionItem = menu.findItem(R.id.menu_item_share_action_provider_action_bar);
		ShareActionProvider actionProvider = (ShareActionProvider) actionItem.getActionProvider();
//		actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
		actionProvider.setShareHistoryFileName(null);
		actionProvider.setShareIntent(createShareIntent());
		
		MenuItem normalItem = menu.findItem(R.id.menu_item_share_action_provider_overflow);
		ShareActionProvider normalProvider = (ShareActionProvider) normalItem.getActionProvider();
		normalProvider.setShareHistoryFileName(null);
		normalProvider.setShareIntent(createShareIntent());
		return true;
	}
	
	private Intent createShareIntent() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		Uri uri = Uri.fromFile(getFileStreamPath(SHARED_FILE_NAME));
		intent.putExtra(Intent.EXTRA_STREAM, uri);
		return intent;
	}
	
    /**
     * Copies a private raw resource content to a publicly readable
     * file such that the latter can be shared with other applications.
     */
    private void copyPrivateRawResuorceToPubliclyAccessibleFile() {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = getResources().openRawResource(R.raw.robot);
            outputStream = openFileOutput(SHARED_FILE_NAME,
                    Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
            byte[] buffer = new byte[1024];
            int length = 0;
            try {
                while ((length = inputStream.read(buffer)) > 0){
                    outputStream.write(buffer, 0, length);
                }
            } catch (IOException ioe) {
                /* ignore */
            }
        } catch (FileNotFoundException fnfe) {
            /* ignore */
        } finally {
            try {
                inputStream.close();
            } catch (IOException ioe) {
               /* ignore */
            }
            try {
                outputStream.close();
            } catch (IOException ioe) {
               /* ignore */
            }
        }
    }

}
