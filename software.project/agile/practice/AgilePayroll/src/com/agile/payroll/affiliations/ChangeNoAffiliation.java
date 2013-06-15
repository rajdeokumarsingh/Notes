package com.agile.payroll.affiliations;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.database.PayrollDatabase;
import com.agile.payroll.transactions.ChangeEmployeeProperty;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午4:50
 * To change this template use File | Settings | File Templates.
 */
public class ChangeNoAffiliation extends ChangeEmployeeProperty {
    public ChangeNoAffiliation(String empId) {
        super(empId);
    }

    @Override
    public void change(Employee e) {
        PayrollDatabase.getInstance().removeUnionMember(e);
        e.setMemberId(null);
        e.setAffiliation(new NoAffiliation());
    }
}
