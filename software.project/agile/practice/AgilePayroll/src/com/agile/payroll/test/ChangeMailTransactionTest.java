package com.agile.payroll.test;

import com.agile.payroll.Employee;
import com.agile.payroll.HoldMethod;
import com.agile.payroll.MailMethod;
import com.agile.payroll.PayrollDatabase;
import com.agile.payroll.transaction.AddHourlyEmployee;
import com.agile.payroll.transaction.AddSalariedEmployee;
import com.agile.payroll.transaction.ChangeHoldMethod;
import com.agile.payroll.transaction.ChangeMailTransaction;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午1:40
 * To change this template use File | Settings | File Templates.
 */
public class ChangeMailTransactionTest extends TestCase {
    public void test001Change() {
        String empId = "empCMTT001";
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 5000);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);

        e.setMethd(new HoldMethod());
        assertTrue(e.getMethod() instanceof HoldMethod);

        ChangeMailTransaction trans = new ChangeMailTransaction(empId, "Beijing");
        trans.execute();

        assertTrue(e.getMethod() instanceof MailMethod);
        MailMethod method = (MailMethod) e.getMethod();
        assertEquals(method.getAdress(), "Beijing");
    }
}
