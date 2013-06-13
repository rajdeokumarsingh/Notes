package com.agile.payroll.transaction;

import com.agile.payroll.Employee;
import com.agile.payroll.NoAffiliation;
import com.agile.payroll.PayrollDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午4:50
 * To change this template use File | Settings | File Templates.
 */
public class ChangeNoAffiliationTransaction extends ChangeEmployeeTransaction{
    public ChangeNoAffiliationTransaction(String empId) {
        super(empId);
    }

    @Override
    public void change(Employee e) {
        PayrollDatabase.getInstance().removeUnionMember(e);
        e.setMemberId(null);
        e.setAffiliation(new NoAffiliation());
    }
}
