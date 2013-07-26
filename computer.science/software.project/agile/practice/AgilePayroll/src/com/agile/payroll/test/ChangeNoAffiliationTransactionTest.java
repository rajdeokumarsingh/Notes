package com.agile.payroll.test;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.affiliations.NoAffiliation;
import com.agile.payroll.database.PayrollDatabase;
import com.agile.payroll.affiliations.UnionAffiliation;
import com.agile.payroll.transactions.AddSalariedEmployee;
import com.agile.payroll.affiliations.ChangeAffiliation;
import com.agile.payroll.affiliations.ChangeNoAffiliation;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午4:43
 * To change this template use File | Settings | File Templates.
 */
public class ChangeNoAffiliationTransactionTest extends TestCase {
    public void testTestChange001() {
            String empId = "empCNATT001";
            String mbId = "mbCATT001";

            AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.0);
            t.execute();

            Employee e = PayrollDatabase.getInstance().getEmployee(empId);
            ChangeAffiliation trans = new ChangeAffiliation(empId, mbId, 50.0);
            trans.execute();

            assertEquals(e, PayrollDatabase.getInstance().getUnionMember(mbId));
            assertTrue(e.getAffiliation() instanceof UnionAffiliation);
            UnionAffiliation uaf = (UnionAffiliation) e.getAffiliation();
            assertEquals(uaf.getDues(), 50.0);

            ChangeNoAffiliation trans2 = new ChangeNoAffiliation(empId);
            trans2.execute();

            assertNull(PayrollDatabase.getInstance().getUnionMember(mbId));
            assertTrue(e.getAffiliation() instanceof NoAffiliation);
    }
}
