package com.agile.payroll.classifications;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.domain.PaymentClassification;
import com.agile.payroll.database.PayrollDatabase;
import com.agile.payroll.transactions.Transaction;

public class AddSaleReceipt implements Transaction {

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

    public AddSaleReceipt(long date, double amount, String empId) {
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
