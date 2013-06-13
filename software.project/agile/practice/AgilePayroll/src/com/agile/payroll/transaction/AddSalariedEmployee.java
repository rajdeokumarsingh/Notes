package com.agile.payroll.transaction;

import com.agile.payroll.MonthlySchedule;
import com.agile.payroll.PaymentClassification;
import com.agile.payroll.PaymentSchedule;
import com.agile.payroll.SalariedClassification;

public class AddSalariedEmployee extends AddEmployeeTransaction{

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
