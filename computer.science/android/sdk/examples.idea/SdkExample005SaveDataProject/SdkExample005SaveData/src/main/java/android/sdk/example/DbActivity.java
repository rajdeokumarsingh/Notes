package android.sdk.example;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.sdk.example.provider.ProviderConstants;
import android.widget.Toast;

/**
 * Created by jiangrui on 12/18/13.
 */
public class DbActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createEmployee();
        Toast.makeText(DbActivity.this, getString(R.string.data_saved), Toast.LENGTH_SHORT).show();
        finish();
    }

    private void createEmployee() {
        // Create employees
        ContentResolver resolver = getContentResolver();
        ContentValues values = new ContentValues();

        values.put(ProviderConstants.EmployeeColumns.NAME, "Wang Bo");
        values.put(ProviderConstants.EmployeeColumns.GENDER, "Female");
        values.put(ProviderConstants.EmployeeColumns.TITLE, "CFO");
        resolver.insert(ProviderConstants.CONTENT_URI_EMPLOYEE, values);

        values.put(ProviderConstants.EmployeeColumns.NAME, "Zhang Dan");
        values.put(ProviderConstants.EmployeeColumns.GENDER, "Female");
        values.put(ProviderConstants.EmployeeColumns.TITLE, "Greeting");
        resolver.insert(ProviderConstants.CONTENT_URI_EMPLOYEE, values);
     }
}