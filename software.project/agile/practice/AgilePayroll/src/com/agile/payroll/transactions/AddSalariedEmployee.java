package com.agile.payroll.transactions;

import com.agile.payroll.schedules.MonthlySchedule;
import com.agile.payroll.domain.PaymentClassification;
import com.agile.payroll.domain.PaymentSchedule;
import com.agile.payroll.classifications.SalariedClassification;

public class AddSalariedEmployee extends AddEmployee {

    private double mSalary;
    
    public AddSalariedEmployee(String empId, String name, String address, double salary) {
        super(empId, name, address);
        this.mSalary = salary;
    }

    @Override
    public PaymentClassification getClassification() {
        return new SalariedClassification(mSalary);
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }

}
