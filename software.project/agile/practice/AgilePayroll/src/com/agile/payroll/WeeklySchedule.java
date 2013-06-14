package com.agile.payroll;


import java.util.Date;

public class WeeklySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDay(long date) {
        return DateUtils.isFriday(new Date(date));
    }
}
