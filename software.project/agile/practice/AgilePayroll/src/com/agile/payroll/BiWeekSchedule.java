package com.agile.payroll;

import java.util.Date;

public class BiWeekSchedule implements PaymentSchedule {

    @Override
    public boolean isPayDay(long date) {
        return DateUtils.isBiWeekFriday(new Date(date));
    }
}
