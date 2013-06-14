package com.agile.payroll;

public class SalariedClassification implements PaymentClassification {
    private double salary;

    public SalariedClassification(double salary) {
        this.salary = salary;
    }
    
    @Override
    public double calculatePay(long date) {
        return salary;
    }

    @Override
    public void post(long date) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
