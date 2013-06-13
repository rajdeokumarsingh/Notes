package com.agile.payroll.transaction;

import com.agile.payroll.PayrollDatabase;


public class DeleteEmployee implements Transaction{
    private String mId;
    
    public DeleteEmployee(String id) {
        mId = id;
    }
    
    @Override
    public void execute() {
        PayrollDatabase.getInstance().removeEmployee(mId);
    }
}
