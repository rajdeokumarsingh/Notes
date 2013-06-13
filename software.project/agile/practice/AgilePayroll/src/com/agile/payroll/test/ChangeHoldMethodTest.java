package com.agile.payroll.test;

import com.agile.payroll.*;
import com.agile.payroll.transaction.AddHourlyEmployee;
import com.agile.payroll.transaction.ChangeHoldMethod;
import com.agile.payroll.transaction.ChangeHourlyTransaction;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 上午11:13
 * To change this template use File | Settings | File Templates.
 */
public class ChangeHoldMethodTest extends TestCase {
    public void testChange() throws Exception {
        String empId = "empCHM001";
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 0.5);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        e.setMethd(new MailMethod("Beijing"));

        ChangeHoldMethod cm = new ChangeHoldMethod(empId);
        cm.execute();
        assertTrue(e.getMethod() instanceof HoldMethod);
    }
}
