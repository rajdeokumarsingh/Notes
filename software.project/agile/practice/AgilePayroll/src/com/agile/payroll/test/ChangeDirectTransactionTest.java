package com.agile.payroll.test;

import com.agile.payroll.database.PayrollDatabase;
import com.agile.payroll.domain.Employee;
import com.agile.payroll.methods.DirectMethod;
import com.agile.payroll.methods.HoldMethod;
import com.agile.payroll.transactions.AddHourlyEmployee;
import com.agile.payroll.methods.ChangeDirectMethod;
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
        e.setMethod(new HoldMethod());

        ChangeDirectMethod trans = new ChangeDirectMethod(empId, "CCB", "111ccbc");
        trans.execute();
        assertTrue(e.getMethod() instanceof DirectMethod);
        DirectMethod method = (DirectMethod) e.getMethod();

        assertEquals(method.getBank(), "CCB");
        assertEquals(method.getAccount(), "111ccbc");
    }
}
