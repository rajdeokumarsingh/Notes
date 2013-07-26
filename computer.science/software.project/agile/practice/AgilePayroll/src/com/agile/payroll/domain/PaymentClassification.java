package com.agile.payroll.domain;

public interface PaymentClassification {
    public double calculatePay(long date);

    void post(long date);
}
