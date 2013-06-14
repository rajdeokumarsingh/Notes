package com.agile.payroll;

import java.util.Date;

public interface Affiliation {
    public double getFee(long date);
    void post(long date);
}
