package com.agile.payroll.test;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.methods.HoldMethod;
import com.agile.payroll.methods.MailMethod;
import com.agile.payroll.database.PayrollDatabase;
import com.agile.payroll.transactions.AddSalariedEmployee;
import com.agile.payroll.methods.ChangeMailMethod;
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

        e.setMethod(new HoldMethod());
        assertTrue(e.getMethod() instanceof HoldMethod);

        ChangeMailMethod trans = new ChangeMailMethod(empId, "Beijing");
        trans.execute();

        assertTrue(e.getMethod() instanceof MailMethod);
        MailMethod method = (MailMethod) e.getMethod();
        assertEquals(method.getAdress(), "Beijing");
    }
}
