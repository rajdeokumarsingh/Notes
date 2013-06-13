package com.agile.payroll;

public interface PaymentClassification {
    public double calculatePay(long date);

    void post(long date);
}
