package com.agile.payroll.methods;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.transactions.ChangeEmployeeProperty;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午1:28
 * To change this template use File | Settings | File Templates.
 */
public class ChangeDirectMethod extends ChangeEmployeeProperty {
    private final String bank;
    private final String account;

    public ChangeDirectMethod(String empId, String bank, String account) {
        super(empId);
        this.bank = bank;
        this.account = account;
    }

    @Override
    public void change(Employee e) {
        e.setMethod(new DirectMethod(this.bank, this.account));
    }
}
