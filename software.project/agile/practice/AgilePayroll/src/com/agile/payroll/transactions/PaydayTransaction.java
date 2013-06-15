package com.agile.payroll.transactions;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.domain.PayCheck;
import com.agile.payroll.database.PayrollDatabase;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午7:35
 * To change this template use File | Settings | File Templates.
 */
public class PaydayTransaction implements Transaction {
    private final long date;
    private HashMap<String, PayCheck> payChecks = new HashMap<String, PayCheck>();

    public PaydayTransaction(long date) {
        this.date = date;
    }

    @Override
    public void execute() {
        for(Employee emp : PayrollDatabase.getInstance().allEmployees()) {
            if(!emp.isPayDay(date)) {
                continue;
            }
            PayCheck pc = new PayCheck();
            emp.payDay(date, pc);
            payChecks.put(emp.getEmployeeId(), pc);
        }
    }

    public PayCheck getPayCheck(String empId) {
        return payChecks.get(empId);
    }
}
