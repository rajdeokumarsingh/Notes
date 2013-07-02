package android.sdk.example.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jiangrui on 7/1/13.
 */
public class ProviderConstants {
    // Be unique globally
    public static final String AUTHORITY = "android.sdk.example.provider";

    public static final String TABLE_EMPLOYEE = "employee";
    public static final String TABLE_HOLIDAY = "holiday";
    public static final String TABLE_SALARY = "salary";

    // Path could be zero or more segments
    public static final Uri CONTENT_URI_EMPLOYEE =
            Uri.parse("content://" + AUTHORITY + "/" + TABLE_EMPLOYEE);
    public static final Uri CONTENT_URI_HOLIDAY =
                Uri.parse("content://" + AUTHORITY + "/" + TABLE_HOLIDAY);
    public static final Uri CONTENT_URI_SALARY =
            Uri.parse("content://" + AUTHORITY + "/" + TABLE_SALARY);

    // append id
    // Uri singleUri = ContentUris.withAppendedId(CONTENT_URI_EMPLOYEE, 5);

    /**
     * Column definitions for the employee table, available at
     * {@link CONTENT_URI_EMPLOYEE}
     */
    public static class EmployeeColumns implements BaseColumns {
        public static final String NAME = "name";
        public static final String TITLE = "title";
        public static final String GENDER = "gender";
        public static final String DATE = "date";
    }

    public static final String[] EmployeeProjections = {
         EmployeeColumns._ID,
         EmployeeColumns.NAME,
         EmployeeColumns.TITLE,
         EmployeeColumns.GENDER,
         EmployeeColumns.DATE
     };

    /**
     * Column definitions for the holiday table, available at
     * {@link CONTENT_URI_HOLIDAY}
     */
    public static class HolidayColumns implements BaseColumns {
        public static final String TOTAL = "total";
        public static final String USED = "used";
    }

    public static final String[] HolidayProjections = {
        HolidayColumns._ID,
        HolidayColumns.TOTAL,
        HolidayColumns.USED
    };

    /**
     * Column definitions for the holiday table, available at
     * {@link CONTENT_URI_SALARY}
     */
    public static class SalaryColumns implements BaseColumns{
        public static final String SALARY = "salary";
        public static final String BONUS = "bonus";
    }

    public static final String[] SalaryProjections = {
        SalaryColumns._ID,
        SalaryColumns.SALARY,
        SalaryColumns.BONUS
    };
}
