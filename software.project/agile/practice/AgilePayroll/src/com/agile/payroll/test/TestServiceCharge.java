
package com.agile.payroll.test;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.database.PayrollDatabase;
import com.agile.payroll.affiliations.ServiceCharge;
import com.agile.payroll.affiliations.UnionAffiliation;
import com.agile.payroll.transactions.AddCommissionedEmployee;
import com.agile.payroll.affiliations.ServiceChargeTransaction;

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
