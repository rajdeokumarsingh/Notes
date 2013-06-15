package com.agile.payroll.affiliations;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.database.PayrollDatabase;
import com.agile.payroll.transactions.ChangeEmployeeProperty;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 */
public class ChangeAffiliation extends ChangeEmployeeProperty {
    private final String memberId;
    private final double charge;

    public ChangeAffiliation(String empId, String memberId, double charge) {
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
