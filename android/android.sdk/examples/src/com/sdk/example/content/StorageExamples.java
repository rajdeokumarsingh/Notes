package com.sdk.example.content;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sdk.example.R;

public class StorageExamples extends Activity {

	private static final String TEST_JPG = "test.jpg";
	private static final String LOGTAG = "StorageExamples";
	ViewGroup mLayout;

	static class Item {
		View mRoot;
		Button mCreate;
		Button mDelete;
	}

	Item mExternalStoragePublicPicture;
	Item mExternalStoragePrivatePicture;
	Item mExternalStoragePrivateFile;
	private BroadcastReceiver mExternalStorageReceiver;
	private boolean mExternalStorageAvailable;
	private boolean mExternalStorageWriteable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.external_storage);
		mLayout = (ViewGroup) findViewById(R.id.layout);

		mExternalStoragePublicPicture = createStorageControls(
				"test public picture",
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						createExternalStoragePublicPicture();
						updateExternalStorageState();
					}
				}, new OnClickListener() {
					@Override
					public void onClick(View v) {
						deleteExternalStoragePublicPicture();
						updateExternalStorageState();
					}
				});
		mLayout.addView(mExternalStoragePublicPicture.mRoot);
		mExternalStoragePrivatePicture = createStorageControls(
                "Picture getExternalFilesDir",
                getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                new View.OnClickListener() {
                    public void onClick(View v) {
                        createExternalStoragePrivatePicture();
                        updateExternalStorageState();
                    }
                },
                new View.OnClickListener() {
                    public void onClick(View v) {
                        deleteExternalStoragePrivatePicture();
                        updateExternalStorageState();
                    }
                });
        mLayout.addView(mExternalStoragePrivatePicture.mRoot);
        mExternalStoragePrivateFile = createStorageControls(
                "File getExternalFilesDir",
                getExternalFilesDir(null),
                new View.OnClickListener() {
                    public void onClick(View v) {
                        createExternalStoragePrivateFile();
                        updateExternalStorageState();
                    }
                },
                new View.OnClickListener() {
                    public void onClick(View v) {
                        deleteExternalStoragePrivateFile();
                        updateExternalStorageState();
                    }
                });
        mLayout.addView(mExternalStoragePrivateFile.mRoot);
        startWatchingExternalStorage();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopWatchingExternalStorage();
	}

	void startWatchingExternalStorage() {
		mExternalStorageReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				Log.i("test", "Storage: " + intent.getData());
                updateExternalStorageState();
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
		filter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		registerReceiver(mExternalStorageReceiver, filter);
		
		updateExternalStorageState();
	}
	
    void stopWatchingExternalStorage() {
        unregisterReceiver(mExternalStorageReceiver);
    }
    
    void handleExternalStorageState(boolean available, boolean writeable) {
        boolean has = hasExternalStoragePublicPicture();
        mExternalStoragePublicPicture.mCreate.setEnabled(writeable && !has);
        mExternalStoragePublicPicture.mDelete.setEnabled(writeable && has);
        has = hasExternalStoragePrivatePicture();
        mExternalStoragePrivatePicture.mCreate.setEnabled(writeable && !has);
        mExternalStoragePrivatePicture.mDelete.setEnabled(writeable && has);
        has = hasExternalStoragePrivateFile();
        mExternalStoragePrivateFile.mCreate.setEnabled(writeable && !has);
        mExternalStoragePrivateFile.mDelete.setEnabled(writeable && has);
    }
    
	protected void updateExternalStorageState() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        
        handleExternalStorageState(mExternalStorageAvailable,
                mExternalStorageWriteable);
	}

	private Item createStorageControls(CharSequence label, File path,
			View.OnClickListener createClick, View.OnClickListener deleteClick) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		Item item = new Item();
		item.mRoot = inflater.inflate(R.layout.external_storage_item, null);
		TextView tv = (TextView) item.mRoot.findViewById(R.id.label);
		tv.setText(label);
		if (path != null) {
			tv = (TextView) item.mRoot.findViewById(R.id.path);
			tv.setText(path.toString());
		}
		item.mCreate = (Button) item.mRoot.findViewById(R.id.create);
		item.mCreate.setOnClickListener(createClick);
		item.mDelete = (Button) item.mRoot.findViewById(R.id.delete);
		item.mDelete.setOnClickListener(deleteClick);
		return item;
	}

	void createExternalStoragePublicPicture() {
		createPicture(Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
	}

	void deleteExternalStoragePublicPicture() {
		deletePicture(Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
	}

	boolean hasExternalStoragePublicPicture() {
		return hasPicture(Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
	}

	void createExternalStoragePrivatePicture() {
		createPicture(getExternalFilesDir(Environment.DIRECTORY_PICTURES));
	}

	void deleteExternalStoragePrivatePicture() {
		deletePicture(getExternalFilesDir(Environment.DIRECTORY_PICTURES));
	}

	boolean hasExternalStoragePrivatePicture() {
		return hasPicture(getExternalFilesDir(Environment.DIRECTORY_PICTURES));
	}

	void createExternalStoragePrivateFile() {
		createPicture(getExternalFilesDir(null));
	}

	void deleteExternalStoragePrivateFile() {
		deletePicture(getExternalFilesDir(null));
	}

	boolean hasExternalStoragePrivateFile() {
		return hasPicture(getExternalFilesDir(null));
	}

	private void createPicture(File path) {
		if (path == null)
			return;

		File file = new File(path, StorageExamples.TEST_JPG);

		try {
			path.mkdir();
			InputStream is = getResources()
					.openRawResource(R.drawable.balloons);
			OutputStream os = new FileOutputStream(file);
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			os.write(buffer);

			is.close();
			os.close();

			MediaScannerConnection.scanFile(this,
					new String[] { file.getAbsolutePath() }, null,
					new OnScanCompletedListener() {
						@Override
						public void onScanCompleted(String path, Uri uri) {
							Log.i(LOGTAG, "scan complete: " + path + ", " + uri);
						}
					});
		} catch (IOException e) {
			Log.e(LOGTAG, "", e);
		} finally {
		}
	}

	private void deletePicture(File path) {
		if (path == null)
			return;

		File file = new File(path, StorageExamples.TEST_JPG);
		file.delete();
	}

	private boolean hasPicture(File path) {
		if (path == null)
			return false;

		File file = new File(path, StorageExamples.TEST_JPG);
		return file.exists();
	}
}
