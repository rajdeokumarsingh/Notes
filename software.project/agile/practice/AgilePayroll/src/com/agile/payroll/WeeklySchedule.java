package com.agile.payroll;


import java.util.Date;

public class WeeklySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDay(long date) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
