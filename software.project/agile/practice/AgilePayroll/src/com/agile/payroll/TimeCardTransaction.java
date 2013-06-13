
package com.agile.payroll;

import com.agile.payroll.transaction.Transaction;

public class TimeCardTransaction implements Transaction {

    private long date;
    private double hours;
    private String employeeId;

    public TimeCardTransaction(long date, double hours, String empId) {
        this.date = date;
        this.hours = hours;
        this.employeeId = empId;
    }

    @Override
    public void execute() {
        TimeCard timeCard = new TimeCard(this.date, this.hours, this.employeeId);

        Employee employee = PayrollDatabase.getInstance().getEmployee(employeeId);
        if(employee == null) {
            throw new IllegalArgumentException("No such employee");
        }
        
        PaymentClassification pc = employee.getClassification();
        if (!(pc instanceof HourlyClassification)) {
            throw new IllegalArgumentException();
        }
        
        HourlyClassification hc = (HourlyClassification) pc;
        hc.addTimeCard(timeCard);
    }

}
