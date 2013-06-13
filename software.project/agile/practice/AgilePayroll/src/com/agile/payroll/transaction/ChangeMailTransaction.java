package com.agile.payroll.transaction;

import com.agile.payroll.Employee;
import com.agile.payroll.MailMethod;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午1:38
 * To change this template use File | Settings | File Templates.
 */
public class ChangeMailTransaction extends ChangeEmployeeTransaction {
    private final String address;

    public ChangeMailTransaction(String empId, String address) {
        super(empId);
        this.address = address;
    }

    @Override
    public void change(Employee e) {
        e.setMethd(new MailMethod(this.address));
    }
}
