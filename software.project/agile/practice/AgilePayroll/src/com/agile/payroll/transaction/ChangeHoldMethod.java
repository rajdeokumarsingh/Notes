package com.agile.payroll.transaction;

import com.agile.payroll.Employee;
import com.agile.payroll.HoldMethod;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 上午11:11
 * To change this template use File | Settings | File Templates.
 */
public class ChangeHoldMethod extends ChangeEmployeeTransaction {
    public ChangeHoldMethod(String empId) {
        super(empId);
    }

    @Override
    public void change(Employee e) {
        e.setMethd(new HoldMethod());
    }
}
