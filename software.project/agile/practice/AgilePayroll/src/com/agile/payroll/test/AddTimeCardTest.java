
package com.agile.payroll.test;

import com.agile.payroll.Employee;
import com.agile.payroll.HourlyClassification;
import com.agile.payroll.PaymentClassification;
import com.agile.payroll.PayrollDatabase;
import com.agile.payroll.TimeCard;
import com.agile.payroll.TimeCardTransaction;
import com.agile.payroll.transaction.AddHourlyEmployee;
import com.agile.payroll.transaction.AddSalariedEmployee;

import junit.framework.TestCase;

public class AddTimeCardTest extends TestCase {

    public void test001TimeCardTrans() {
        String empId = "emp001";
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 1000.0);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);

        PaymentClassification pc = e.getClassification();
        assertTrue(pc instanceof HourlyClassification);

        TimeCardTransaction tc = new TimeCardTransaction(1234567, 6.0, empId);
        tc.execute();

        HourlyClassification hc = (HourlyClassification) pc;
        TimeCard timeCard = hc.getTimeCard(1234567);
        assertEquals(empId, timeCard.getEmployeeId());
        assertEquals(6.0, timeCard.getHours());
    }

    public void test002TimeCardTransIllegalArg() {
        String empId = "emp002";
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.0);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);

        // Since e is not a Hourly employee, TimeCardTransaction should throw
        // exception.
        try {
            TimeCardTransaction tc = new TimeCardTransaction(123456, 6.0, empId);
            tc.execute();
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
            return;
        }

        assertTrue(false);
    }

    public void test003TimeCardTransIllegalArg2() {
        String empId = "emp900";

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNull(e);

        // Since the employee is not exist, TimeCardTransaction should throw
        // exception.
        try {
            TimeCardTransaction tc = new TimeCardTransaction(123456, 6.0, empId);
            tc.execute();
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
            return;
        }

        assertTrue(false);
    }
}
