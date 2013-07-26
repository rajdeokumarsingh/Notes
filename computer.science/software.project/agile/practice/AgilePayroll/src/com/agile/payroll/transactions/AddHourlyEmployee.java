package com.agile.payroll.transactions;

import com.agile.payroll.classifications.HourlyClassification;
import com.agile.payroll.domain.PaymentClassification;
import com.agile.payroll.domain.PaymentSchedule;
import com.agile.payroll.schedules.WeeklySchedule;


public class AddHourlyEmployee extends AddEmployee {

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
