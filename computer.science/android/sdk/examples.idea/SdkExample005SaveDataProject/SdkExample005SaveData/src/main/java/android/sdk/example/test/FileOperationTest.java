package android.sdk.example.test;

import android.content.Context;
import android.os.Environment;
import android.test.AndroidTestCase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jiangrui on 7/11/13.
 */
public class FileOperationTest extends AndroidTestCase {
    private static final String LOGTAG = "FileOperationTest";

    public void testInternalStorage() {
        // Log.i(LOGTAG, "testInternalStorage");
        // Log.i(LOGTAG, "getFileDir: " + getContext().getFilesDir());
        // Log.i(LOGTAG, "getCacheDir: " + getContext().getCacheDir());

        assertEquals(getContext().getCacheDir().getAbsolutePath(),
                "/data/data/android.sdk.example/cache");
        assertEquals(getContext().getFilesDir().getAbsolutePath(),
                "/data/data/android.sdk.example/files");

        assertEquals(getContext().getFilesDir().getFreeSpace(),
                getContext().getCacheDir().getFreeSpace());
        assertEquals(getContext().getFilesDir().getTotalSpace(),
                getContext().getCacheDir().getTotalSpace());
    }

    public void testWriteInternalFile() {
        final String filename = "myfile";
        String string = "Hello world!";
        FileOutputStream outputStream;
        try {
            // create a file in dir /data/data/android.sdk.example/files
            outputStream = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File myfile = new File(getContext().getFilesDir(), filename);
        assertTrue(myfile.exists());

        // myfile.delete();
        getContext().deleteFile(filename);
        assertTrue(!myfile.exists());
    }

    public void testCreateTempFile() {
        File file;
        try {
            file = File.createTempFile("tmp_file", null, getContext().getCacheDir());
            Log.i(LOGTAG, "createTempFile: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testExtStorageState() {
        Log.i(LOGTAG, "getExternalStorageState: " + Environment.getExternalStorageState());
        Log.i(LOGTAG, "getDownloadCacheDirectory: " + Environment.getDownloadCacheDirectory());
        Log.i(LOGTAG, "getExternalStorageDirectory: " + Environment.getExternalStorageDirectory());
        Log.i(LOGTAG, "getExternalStoragePublicDirectory: " +
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS));

        Log.i(LOGTAG, "private external dir: " + getContext().getExternalFilesDir(null));
    }

}
