package com.agile.payroll.transaction;

import com.agile.payroll.Employee;
import com.agile.payroll.PayCheck;
import com.agile.payroll.PayrollDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午7:35
 * To change this template use File | Settings | File Templates.
 */
public class PaydayTransaction implements Transaction {
    private final long date;
    private PayCheck payCheck;

    public PaydayTransaction(long date) {
        this.date = date;
    }

    @Override
    public void execute() {
        for(Employee emp : PayrollDatabase.getInstance().allEmployees()) {
            emp.payDay(date);
        }
    }

    public PayCheck getPayCheck() {
        return payCheck;
    }
}
