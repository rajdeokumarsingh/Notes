package com.agile.payroll;

import java.util.Date;

public interface Affiliation {
    public double getFee(Date d);
    void post(long date);
}
