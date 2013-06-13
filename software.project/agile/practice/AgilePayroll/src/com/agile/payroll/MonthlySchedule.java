package com.agile.payroll;

import java.util.Calendar;
import java.util.Date;

public class MonthlySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDay(long date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(new Date(date));

        if (cld.get(Calendar.DAY_OF_MONTH) ==
                cld.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            return true;
        }
        return false;
    }
}
