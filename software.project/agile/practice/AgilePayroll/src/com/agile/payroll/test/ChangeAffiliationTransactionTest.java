package com.agile.payroll.test;

import com.agile.payroll.Employee;
import com.agile.payroll.PayrollDatabase;
import com.agile.payroll.UnionAffiliation;
import com.agile.payroll.transaction.AddSalariedEmployee;
import com.agile.payroll.transaction.ChangeAffiliationTransaction;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午4:29
 * To change this template use File | Settings | File Templates.
 */
public class ChangeAffiliationTransactionTest extends TestCase {
    public void testChange001() {
        String empId = "empCATT001";
        String mbId = "mbCATT001";

        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.0);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertEquals("Bob", e.getName());

        ChangeAffiliationTransaction trans = new ChangeAffiliationTransaction(empId, mbId, 50.0);
        trans.execute();

        assertEquals(e, PayrollDatabase.getInstance().getUnionMember(mbId));
        assertTrue(e.getAffiliation() instanceof UnionAffiliation);
        UnionAffiliation uaf = (UnionAffiliation) e.getAffiliation();
        assertEquals(uaf.getDues(), 50.0);
    }

    public void testChange002() {
        String empId = "empCATT002";
        String mbId = "mbCATT002";

        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.0);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertEquals("Bob", e.getName());

        ChangeAffiliationTransaction trans = new ChangeAffiliationTransaction(empId, mbId, 50.0);
        trans.execute();

        assertEquals(e, PayrollDatabase.getInstance().getUnionMember(mbId));
        assertTrue(e.getAffiliation() instanceof UnionAffiliation);
        UnionAffiliation uaf = (UnionAffiliation) e.getAffiliation();
        assertEquals(uaf.getDues(), 50.0);

        // Test change dues
        ChangeAffiliationTransaction trans2 = new ChangeAffiliationTransaction(empId, mbId, 52.0);
        trans2.execute();

        assertEquals(e, PayrollDatabase.getInstance().getUnionMember(mbId));
        assertTrue(e.getAffiliation() instanceof UnionAffiliation);
        UnionAffiliation uaf2 = (UnionAffiliation) e.getAffiliation();
        assertEquals(uaf2.getDues(), 52.0);

    }

    public void testChange003() {
        String empId = "empCATT003";
        String mbId = "mbCATT003";
        String mbIdNew = "mbCATT0033";

        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.0);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertEquals("Bob", e.getName());

        ChangeAffiliationTransaction trans = new ChangeAffiliationTransaction(empId, mbId, 50.0);
        trans.execute();

        assertEquals(e, PayrollDatabase.getInstance().getUnionMember(mbId));
        assertTrue(e.getAffiliation() instanceof UnionAffiliation);
        UnionAffiliation uaf = (UnionAffiliation) e.getAffiliation();
        assertEquals(uaf.getDues(), 50.0);

        // Test change dues
        ChangeAffiliationTransaction trans2 = new ChangeAffiliationTransaction(empId, mbIdNew, 52.0);
        trans2.execute();

        assertEquals(e, PayrollDatabase.getInstance().getUnionMember(mbIdNew));
        assertEquals(e.getMemberId(), mbIdNew);
        assertTrue(e.getAffiliation() instanceof UnionAffiliation);
        UnionAffiliation uaf2 = (UnionAffiliation) e.getAffiliation();
        assertEquals(uaf2.getDues(), 52.0);
    }
}
