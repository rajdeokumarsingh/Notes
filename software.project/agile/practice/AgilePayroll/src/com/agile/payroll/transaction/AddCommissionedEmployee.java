package com.agile.payroll.transaction;

import com.agile.payroll.BiWeekSchedule;
import com.agile.payroll.CommissionedClassification;
import com.agile.payroll.PaymentClassification;
import com.agile.payroll.PaymentSchedule;

public class AddCommissionedEmployee extends AddEmployeeTransaction {

    private double mSalary;
    private double mCommRate;
    
    public double getSalary() {
        return mSalary;
    }

    public double getCommRate() {
        return mCommRate;
    }

    public AddCommissionedEmployee(String id, String name, String address, double salay, double rate) {
        super(id, name, address);
        this.mSalary = salay;
        this.mCommRate = rate;
    }

    @Override
    public PaymentClassification getClassification() {
        return new CommissionedClassification(mSalary, mCommRate);
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new BiWeekSchedule();
    }
}
