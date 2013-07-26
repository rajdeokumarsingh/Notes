package com.agile.payroll.transactions;

import com.agile.payroll.database.PayrollDatabase;


public class DeleteEmployee implements Transaction {
    private String mId;
    
    public DeleteEmployee(String id) {
        mId = id;
    }
    
    @Override
    public void execute() {
        PayrollDatabase.getInstance().removeEmployee(mId);
    }
}
