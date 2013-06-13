package com.agile.payroll.transaction;

import com.agile.payroll.Employee;
import com.agile.payroll.HoldMethod;
import com.agile.payroll.PaymentClassification;
import com.agile.payroll.PaymentSchedule;
import com.agile.payroll.PayrollDatabase;

public abstract class AddEmployeeTransaction implements Transaction {

    private String mEmployeeId;
    private String mName;
    private String mAddress;
    
    public abstract PaymentClassification getClassification();
    
    public abstract PaymentSchedule getSchedule();
    
    public AddEmployeeTransaction(String id, String name, String address) {
        this.mEmployeeId = id;
        this.mName = name;
        this.mAddress = address;
    }
    
    @Override
    public void execute() {
        Employee emp = new Employee(mEmployeeId, mName, mAddress);
        emp.setClassification(getClassification());
        emp.setSchedule(getSchedule());
        emp.setMethd(new HoldMethod());
        
        PayrollDatabase.getInstance().addEmployee(mEmployeeId, emp);
    }
}
