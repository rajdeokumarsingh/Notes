package com.agile.payroll.schedules;

import com.agile.payroll.utils.DateUtils;
import com.agile.payroll.domain.PaymentSchedule;

import java.util.Date;

public class MonthlySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDay(long date) {
        return DateUtils.isLastDayOfMonth(new Date(date));
    }
}
