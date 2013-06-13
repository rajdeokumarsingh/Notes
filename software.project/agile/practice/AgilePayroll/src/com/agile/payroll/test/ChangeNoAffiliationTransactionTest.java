package com.agile.payroll.test;

import com.agile.payroll.Employee;
import com.agile.payroll.NoAffiliation;
import com.agile.payroll.PayrollDatabase;
import com.agile.payroll.UnionAffiliation;
import com.agile.payroll.transaction.AddSalariedEmployee;
import com.agile.payroll.transaction.ChangeAffiliationTransaction;
import com.agile.payroll.transaction.ChangeNoAffiliationTransaction;
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
            ChangeAffiliationTransaction trans = new ChangeAffiliationTransaction(empId, mbId, 50.0);
            trans.execute();

            assertEquals(e, PayrollDatabase.getInstance().getUnionMember(mbId));
            assertTrue(e.getAffiliation() instanceof UnionAffiliation);
            UnionAffiliation uaf = (UnionAffiliation) e.getAffiliation();
            assertEquals(uaf.getDues(), 50.0);

            ChangeNoAffiliationTransaction trans2 = new ChangeNoAffiliationTransaction(empId);
            trans2.execute();

            assertNull(PayrollDatabase.getInstance().getUnionMember(mbId));
            assertTrue(e.getAffiliation() instanceof NoAffiliation);
    }
}
