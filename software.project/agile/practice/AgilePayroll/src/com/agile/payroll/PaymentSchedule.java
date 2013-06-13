package com.agile.payroll;

import java.util.Date;

public interface PaymentSchedule {
    boolean isPayDay(long date);
}
