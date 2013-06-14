package com.agile.payroll;

import java.util.Date;

public class MonthlySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDay(long date) {
        return DateUtils.isLastDayOfMonth(new Date(date));
    }
}
