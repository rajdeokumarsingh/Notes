package com.agile.payroll.transactions;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.database.PayrollDatabase;

public abstract class ChangeEmployeeProperty implements Transaction {
    protected String empId;
    
    public ChangeEmployeeProperty(String empId) {
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
