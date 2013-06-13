package com.agile.payroll.transaction;

import com.agile.payroll.DirectMethod;
import com.agile.payroll.Employee;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午1:28
 * To change this template use File | Settings | File Templates.
 */
public class ChangeDirectTransaction extends ChangeEmployeeTransaction {
    private final String bank;
    private final String account;

    public ChangeDirectTransaction(String empId, String bank, String account) {
        super(empId);
        this.bank = bank;
        this.account = account;
    }

    @Override
    public void change(Employee e) {
        e.setMethd(new DirectMethod(this.bank, this.account));
    }
}
