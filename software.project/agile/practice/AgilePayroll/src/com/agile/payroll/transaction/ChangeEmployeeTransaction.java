package com.agile.payroll.transaction;

import com.agile.payroll.Employee;
import com.agile.payroll.PayrollDatabase;

public abstract class ChangeEmployeeTransaction implements Transaction {
    protected String empId;
    
    public ChangeEmployeeTransaction(String empId) {
        this.empId = empId;
    }

    public abstract void change(Employee e);
    
    @Override
    public void execute() {
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        if(e == null) {
            throw new IllegalArgumentException();
        }
        
        change(e);
    }
}
