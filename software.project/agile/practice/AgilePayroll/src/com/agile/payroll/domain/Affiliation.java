package com.agile.payroll.domain;

public interface Affiliation {
    public double getFee(long date);
    void post(long date);
}
