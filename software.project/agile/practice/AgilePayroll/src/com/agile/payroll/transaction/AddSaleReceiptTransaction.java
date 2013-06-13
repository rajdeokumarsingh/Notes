package com.agile.payroll.transaction;

import com.agile.payroll.CommissionedClassification;
import com.agile.payroll.Employee;
import com.agile.payroll.PaymentClassification;
import com.agile.payroll.PayrollDatabase;
import com.agile.payroll.SalesReceipt;

public class AddSaleReceiptTransaction implements Transaction {

    private long date;
    private double amount;
    private String empId;
    
    public long getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getEmpId() {
        return empId;
    }

    public AddSaleReceiptTransaction(long date, double amount, String empId) {
        this.date = date;
        this.amount = amount;
        this.empId = empId;
    }

    @Override
    public void execute() { 
        Employee emp = PayrollDatabase.getInstance().getEmployee(this.empId);
        if(emp == null) {
            throw new IllegalArgumentException("No such employee");
        }
        
        PaymentClassification pc = emp.getClassification();
        if(!(pc instanceof CommissionedClassification)) {
            throw new IllegalArgumentException("Illegal employee type");
        }
        
        CommissionedClassification cc = (CommissionedClassification) pc;
        cc.addSaleReceipt(new SalesReceipt(this.date, this.amount));
    }

}
