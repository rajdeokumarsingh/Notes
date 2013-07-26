package com.agile.payroll.transactions;

import com.agile.payroll.schedules.BiWeekSchedule;
import com.agile.payroll.classifications.CommissionedClassification;
import com.agile.payroll.domain.PaymentClassification;
import com.agile.payroll.domain.PaymentSchedule;

public class AddCommissionedEmployee extends AddEmployee {

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
