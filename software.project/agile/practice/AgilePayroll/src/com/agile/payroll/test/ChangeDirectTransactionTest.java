package com.agile.payroll.test;

import com.agile.payroll.*;
import com.agile.payroll.transaction.AddHourlyEmployee;
import com.agile.payroll.transaction.ChangeDirectTransaction;
import com.agile.payroll.transaction.ChangeHoldMethod;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午1:33
 * To change this template use File | Settings | File Templates.
 */
public class ChangeDirectTransactionTest extends TestCase {
    public void testChange() throws Exception {
        String empId = "empCDTT001";
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 0.5);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        e.setMethd(new HoldMethod());

        ChangeDirectTransaction trans = new ChangeDirectTransaction(empId, "CCB", "111ccbc");
        trans.execute();
        assertTrue(e.getMethod() instanceof DirectMethod);
        DirectMethod method = (DirectMethod) e.getMethod();

        assertEquals(method.getBank(), "CCB");
        assertEquals(method.getAccount(), "111ccbc");
    }
}
