package android.sdk.example.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by jiangrui on 7/1/13.
 */
public class SdkExampleProvider extends ContentProvider {
    static final String DB_NAME = "sdk_example.db";
    static final int DB_VERSION = 1;

    static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static final int EMPLOYEE = 1;
    static final int EMPLOYEE_ID = 2;

    static {
        final String authority = ProviderConstants.AUTHORITY;
        sUriMatcher.addURI(authority, ProviderConstants.TABLE_EMPLOYEE, EMPLOYEE);
        sUriMatcher.addURI(authority, ProviderConstants.TABLE_EMPLOYEE + "/#", EMPLOYEE_ID);
    }

    private MainDatabaseHelper mOpenHelper;

    final class MainDatabaseHelper extends SQLiteOpenHelper {
        private static final String CREATE_TABLE = "CREATE TABLE " +
                ProviderConstants.TABLE_EMPLOYEE + " " +
                "(" +
                ProviderConstants.EmployeeColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ProviderConstants.EmployeeColumns.NAME + " TEXT NOT NULL," +
                ProviderConstants.EmployeeColumns.GENDER + " TEXT NOT NULL," +
                ProviderConstants.EmployeeColumns.TITLE + " TEXT," +
                ProviderConstants.EmployeeColumns.DATE + " INTEGER" +
                ");";

        public MainDatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        }
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MainDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query( Uri uri, String[] projection, String selection,
                         String[] selectionArgs, String sortOrder)  {
        switch (sUriMatcher.match(uri)) {
            case EMPLOYEE:
                break;
            case EMPLOYEE_ID:
                selection += ProviderConstants.EmployeeColumns._ID + "=" + uri.getLastPathSegment();
                break;
            default:
                break;
        }

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        return db.query(ProviderConstants.TABLE_EMPLOYEE, projection,
                selection, selectionArgs, null, null,sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case EMPLOYEE:
                return "vnd.android.cursor.dir/vnd.android.sdk.example.provider.table.employee";
            case EMPLOYEE_ID:
                return "vnd.android.cursor.item/vnd.android.sdk.example.provider.table.employee";
            default:
                break;
        }
        return "vnd.android.cursor.dir/vnd.android.sdk.example.provider.table.employee";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        // TODO: check the middle parameter
        long id = db.insert(ProviderConstants.TABLE_EMPLOYEE, null, values);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        return db.delete(ProviderConstants.TABLE_EMPLOYEE, where, whereArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] wheraArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        return db.update(ProviderConstants.TABLE_EMPLOYEE, values, where, wheraArgs);
    }
}
