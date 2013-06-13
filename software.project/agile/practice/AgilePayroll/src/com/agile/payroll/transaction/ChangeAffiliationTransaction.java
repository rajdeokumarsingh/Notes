package com.agile.payroll.transaction;

import com.agile.payroll.Employee;
import com.agile.payroll.PayrollDatabase;
import com.agile.payroll.UnionAffiliation;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 */
public class ChangeAffiliationTransaction extends ChangeEmployeeTransaction {
    private final String memberId;
    private final double charge;

    public ChangeAffiliationTransaction(String empId, String memberId, double charge) {
        super(empId);
        this.memberId = memberId;
        this.charge = charge;
    }

    @Override
    public void change(Employee e) {
        PayrollDatabase.getInstance().removeUnionMember(e);
        PayrollDatabase.getInstance().addUnionMember(memberId, e);

        e.setMemberId(memberId);
        e.setAffiliation(new UnionAffiliation(memberId, charge));
    }
}
