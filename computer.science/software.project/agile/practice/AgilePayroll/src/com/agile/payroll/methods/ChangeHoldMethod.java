package com.agile.payroll.methods;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.transactions.ChangeEmployeeProperty;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 上午11:11
 * To change this template use File | Settings | File Templates.
 */
public class ChangeHoldMethod extends ChangeEmployeeProperty {
    public ChangeHoldMethod(String empId) {
        super(empId);
    }

    @Override
    public void change(Employee e) {
        e.setMethod(new HoldMethod());
    }
}
