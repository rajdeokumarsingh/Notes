package com.agile.payroll.transactions;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.methods.HoldMethod;
import com.agile.payroll.domain.PaymentClassification;
import com.agile.payroll.domain.PaymentSchedule;
import com.agile.payroll.database.PayrollDatabase;

public abstract class AddEmployee implements Transaction {

    private String mEmployeeId;
    private String mName;
    private String mAddress;
    
    public abstract PaymentClassification getClassification();
    
    public abstract PaymentSchedule getSchedule();
    
    public AddEmployee(String id, String name, String address) {
        this.mEmployeeId = id;
        this.mName = name;
        this.mAddress = address;
    }
    
    @Override
    public void execute() {
        Employee emp = new Employee(mEmployeeId, mName, mAddress);
        emp.setClassification(getClassification());
        emp.setSchedule(getSchedule());
        emp.setMethod(new HoldMethod());
        
        PayrollDatabase.getInstance().addEmployee(mEmployeeId, emp);
    }
}
