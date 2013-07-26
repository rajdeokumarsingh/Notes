package com.agile.payroll.test;

import com.agile.payroll.classifications.HourlyClassification;
import com.agile.payroll.classifications.SalariedClassification;
import com.agile.payroll.database.PayrollDatabase;
import com.agile.payroll.domain.Employee;
import com.agile.payroll.schedules.WeeklySchedule;
import com.agile.payroll.transactions.AddHourlyEmployee;
import com.agile.payroll.transactions.AddSalariedEmployee;
import com.agile.payroll.classifications.ChangeHourlyClass;
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

        ChangeHourlyClass trans = new ChangeHourlyClass(empId, 0.6);
        trans.execute();

        assertEquals(hc.getRate(), 0.6);
    }

    public void test002ChangeEmpClass() {
        String empId = "empSC002";
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 15000);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertTrue(e.getClassification() instanceof SalariedClassification);

        ChangeHourlyClass trans = new ChangeHourlyClass(empId, 0.7);
        trans.execute();

        assertTrue(e.getSchedule() instanceof WeeklySchedule);
        assertTrue(e.getClassification() instanceof HourlyClassification);
        HourlyClassification hc = (HourlyClassification) e.getClassification();
        assertEquals(hc.getRate(), 0.7);
    }
}
