package com.agile.payroll.methods;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.transactions.ChangeEmployeeProperty;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午1:38
 * To change this template use File | Settings | File Templates.
 */
public class ChangeMailMethod extends ChangeEmployeeProperty {
    private final String address;

    public ChangeMailMethod(String empId, String address) {
        super(empId);
        this.address = address;
    }

    @Override
    public void change(Employee e) {
        e.setMethod(new MailMethod(this.address));
    }
}
