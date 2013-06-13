package com.agile.payroll.test;

import com.agile.payroll.*;
import com.agile.payroll.transaction.AddHourlyEmployee;
import com.agile.payroll.transaction.AddSalariedEmployee;
import com.agile.payroll.transaction.ChangeHourlyTransaction;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-7
 * Time: 下午6:26
 * To change this template use File | Settings | File Templates.
 */
public class TestChangeEmpClass extends TestCase {

    public void test001ChangeEmpClass() {
        String empId = "empSC001";
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 0.5);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertTrue(e.getClassification() instanceof HourlyClassification);

        HourlyClassification hc = (HourlyClassification) e.getClassification();
        assertEquals(hc.getRate(), 0.5);

        ChangeHourlyTransaction trans = new ChangeHourlyTransaction(empId, 0.6);
        trans.execute();

        assertEquals(hc.getRate(), 0.6);
    }

    public void test002ChangeEmpClass() {
        String empId = "empSC002";
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 15000);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertTrue(e.getClassification() instanceof SalariedClassification);

        ChangeHourlyTransaction trans = new ChangeHourlyTransaction(empId, 0.7);
        trans.execute();

        assertTrue(e.getSchedule() instanceof WeeklySchedule);
        assertTrue(e.getClassification() instanceof HourlyClassification);
        HourlyClassification hc = (HourlyClassification) e.getClassification();
        assertEquals(hc.getRate(), 0.7);
    }
}
