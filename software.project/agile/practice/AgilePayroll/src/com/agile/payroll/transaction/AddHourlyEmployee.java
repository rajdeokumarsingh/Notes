package com.agile.payroll.transaction;

import com.agile.payroll.HourlyClassification;
import com.agile.payroll.PaymentClassification;
import com.agile.payroll.PaymentSchedule;
import com.agile.payroll.WeeklySchedule;


public class AddHourlyEmployee extends AddEmployeeTransaction {

    private double mRate;
    
    public AddHourlyEmployee(String id, String name, String address, double rate) {
        super(id, name, address);
        this.mRate = rate;
    }

    @Override
    public PaymentClassification getClassification() {
        return new HourlyClassification(mRate);
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new WeeklySchedule();
    }
}
