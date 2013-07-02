package android.sdk.example.test;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.sdk.example.provider.ProviderConstants;
import android.test.AndroidTestCase;

/**
 * Created by jiangrui on 7/1/13.
 */
public class SdkExampleProviderTest extends AndroidTestCase {
    private ContentResolver mResolver;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Create employees
        ContentResolver resolver = getContext().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(ProviderConstants.EmployeeColumns.NAME, "Philip");
        values.put(ProviderConstants.EmployeeColumns.GENDER, "Male");
        values.put(ProviderConstants.EmployeeColumns.TITLE, "CEO");
        resolver.insert(ProviderConstants.CONTENT_URI_EMPLOYEE, values);

        values.put(ProviderConstants.EmployeeColumns.NAME, "Roger");
        values.put(ProviderConstants.EmployeeColumns.GENDER, "Male");
        values.put(ProviderConstants.EmployeeColumns.TITLE, "CTO");
        resolver.insert(ProviderConstants.CONTENT_URI_EMPLOYEE, values);

        values.put(ProviderConstants.EmployeeColumns.NAME, "Richard");
        values.put(ProviderConstants.EmployeeColumns.GENDER, "Male");
        values.put(ProviderConstants.EmployeeColumns.TITLE, "TVP");
        resolver.insert(ProviderConstants.CONTENT_URI_EMPLOYEE, values);

        values.put(ProviderConstants.EmployeeColumns.NAME, "Zhidong");
        values.put(ProviderConstants.EmployeeColumns.GENDER, "Male");
        values.put(ProviderConstants.EmployeeColumns.TITLE, "TVP");
        resolver.insert(ProviderConstants.CONTENT_URI_EMPLOYEE, values);

        values.put(ProviderConstants.EmployeeColumns.NAME, "Wang Bo");
        values.put(ProviderConstants.EmployeeColumns.GENDER, "Female");
        values.put(ProviderConstants.EmployeeColumns.TITLE, "CFO");
        resolver.insert(ProviderConstants.CONTENT_URI_EMPLOYEE, values);

        values.put(ProviderConstants.EmployeeColumns.NAME, "Zhang Dan");
        values.put(ProviderConstants.EmployeeColumns.GENDER, "Female");
        values.put(ProviderConstants.EmployeeColumns.TITLE, "Greeting");
        resolver.insert(ProviderConstants.CONTENT_URI_EMPLOYEE, values);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        // Delete all employees
        ContentResolver resolver = getContext().getContentResolver();
        resolver.delete(ProviderConstants.CONTENT_URI_EMPLOYEE,
                ProviderConstants.EmployeeColumns._ID + " LIKE ?",
                new String[] {"%"});
    }

    public void test001Query() {
        // ensure we have 6 employees at first
        ContentResolver resolver = getContext().getContentResolver();
        Cursor cursor = resolver.query(ProviderConstants.CONTENT_URI_EMPLOYEE,
                ProviderConstants.EmployeeProjections, null, null, null);
        assertEquals(6, cursor.getCount());
        cursor.close();

        cursor = resolver.query(ProviderConstants.CONTENT_URI_EMPLOYEE,
                ProviderConstants.EmployeeProjections,
                ProviderConstants.EmployeeColumns.GENDER + "=?",
                new String[]{"Female"}, null);
        assertEquals(2, cursor.getCount());
        cursor.close();

        cursor = resolver.query(ProviderConstants.CONTENT_URI_EMPLOYEE,
                ProviderConstants.EmployeeProjections,
                ProviderConstants.EmployeeColumns.GENDER + "=?",
                new String[]{"Male"}, null);
        assertEquals(4, cursor.getCount());
        cursor.close();

        cursor = resolver.query(ProviderConstants.CONTENT_URI_EMPLOYEE,
                ProviderConstants.EmployeeProjections,
                ProviderConstants.EmployeeColumns.TITLE + "=?",
                new String[]{"CTO"}, null);
        assertEquals(1, cursor.getCount());
        int nameIndex = cursor.getColumnIndex(ProviderConstants.EmployeeColumns.NAME);
        int genderIndex = cursor.getColumnIndex(ProviderConstants.EmployeeColumns.GENDER);
        if(cursor.moveToFirst()) {
            assertEquals("Roger", cursor.getString(nameIndex));
            assertEquals("Male", cursor.getString(genderIndex));
        }
        cursor.close();
    }

    public void test002GetType() throws Exception {
    }

    public void test003Insert() throws Exception {
        // ensure we have 6 employees at first
        ContentResolver resolver = getContext().getContentResolver();
        Cursor cursor = resolver.query(ProviderConstants.CONTENT_URI_EMPLOYEE,
                ProviderConstants.EmployeeProjections, null, null, null);
        assertEquals(6, cursor.getCount());
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(ProviderConstants.EmployeeColumns.NAME, "Jiang Rui");
        values.put(ProviderConstants.EmployeeColumns.GENDER, "Male");
        values.put(ProviderConstants.EmployeeColumns.TITLE, "Staff Engineer");

        resolver.insert(ProviderConstants.CONTENT_URI_EMPLOYEE, values);

        cursor = resolver.query(ProviderConstants.CONTENT_URI_EMPLOYEE,
                ProviderConstants.EmployeeProjections, null, null, null);
        assertEquals(7, cursor.getCount());
        cursor.close();

        cursor = resolver.query(ProviderConstants.CONTENT_URI_EMPLOYEE,
            ProviderConstants.EmployeeProjections,
            ProviderConstants.EmployeeColumns.NAME + "=?",
            new String[]{"Jiang Rui"}, null);
        assertEquals(1, cursor.getCount());
        int titleIndex = cursor.getColumnIndex(ProviderConstants.EmployeeColumns.TITLE);
        int genderIndex = cursor.getColumnIndex(ProviderConstants.EmployeeColumns.GENDER);
        if(cursor.moveToFirst()) {
            assertEquals("Staff Engineer", cursor.getString(titleIndex));
            assertEquals("Male", cursor.getString(genderIndex));
        }
        cursor.close();
    }

    public void test004Delete() throws Exception {
        // ensure we have 6 employees at first
        ContentResolver resolver = getContext().getContentResolver();
        Cursor cursor = resolver.query(ProviderConstants.CONTENT_URI_EMPLOYEE,
                ProviderConstants.EmployeeProjections, null, null, null);
        assertEquals(6, cursor.getCount());
        cursor.close();

        resolver.delete(ProviderConstants.CONTENT_URI_EMPLOYEE,
                ProviderConstants.EmployeeColumns.GENDER + "=?",
                new String[] {"Male"});

        cursor = resolver.query(ProviderConstants.CONTENT_URI_EMPLOYEE,
                ProviderConstants.EmployeeProjections, null, null, null);
        assertEquals(2, cursor.getCount());
        cursor.close();
    }

    public void test005Update() throws Exception {
        // ensure we have 6 employees at first
        ContentResolver resolver = getContext().getContentResolver();
        Cursor cursor = resolver.query(ProviderConstants.CONTENT_URI_EMPLOYEE,
                ProviderConstants.EmployeeProjections, null, null, null);
        assertEquals(6, cursor.getCount());
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(ProviderConstants.EmployeeColumns.NAME, "Jiang Rui");
        values.put(ProviderConstants.EmployeeColumns.GENDER, "Male");
        values.put(ProviderConstants.EmployeeColumns.TITLE, "Staff Engineer");
        resolver.insert(ProviderConstants.CONTENT_URI_EMPLOYEE, values);

        values = new ContentValues();
        values.put(ProviderConstants.EmployeeColumns.TITLE, "R&D Manager");
        resolver.update(ProviderConstants.CONTENT_URI_EMPLOYEE,
                values,
                ProviderConstants.EmployeeColumns.NAME + "=?",
                new String[] {"Jiang Rui"});

        cursor = resolver.query(ProviderConstants.CONTENT_URI_EMPLOYEE,
            ProviderConstants.EmployeeProjections,
            ProviderConstants.EmployeeColumns.NAME + "=?",
            new String[]{"Jiang Rui"}, null);
        assertEquals(1, cursor.getCount());
        int titleIndex = cursor.getColumnIndex(ProviderConstants.EmployeeColumns.TITLE);
        if(cursor.moveToFirst()) {
            assertEquals("R&D Manager", cursor.getString(titleIndex));
        }
        cursor.close();
    }
}
