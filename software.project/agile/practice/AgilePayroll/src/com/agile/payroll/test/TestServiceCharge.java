
package com.agile.payroll.test;

import com.agile.payroll.Employee;
import com.agile.payroll.PayrollDatabase;
import com.agile.payroll.ServiceCharge;
import com.agile.payroll.UnionAffiliation;
import com.agile.payroll.transaction.AddCommissionedEmployee;
import com.agile.payroll.transaction.ServiceChargeTransaction;

import junit.framework.TestCase;

public class TestServiceCharge extends TestCase {

    public void test001AddServiceCharge() {
        String empId = "empSC001";
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 1000.0, 0.5);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertEquals("Bob", e.getName());

        String memberId = "memSC001";
        UnionAffiliation af = new UnionAffiliation(memberId, 12.5);
        // e.addAffiliation(memberId, af);
        e.setAffiliation(af);

        PayrollDatabase.getInstance().addUnionMember(memberId, e);
        ServiceChargeTransaction st = new ServiceChargeTransaction(memberId, 2001110, 12.95);
        st.execute();

        ServiceCharge sc = af.getServiceCharge(2001110);
        assertNotNull(sc);
        assertEquals(12.95, sc.getAmount());
    }
}
