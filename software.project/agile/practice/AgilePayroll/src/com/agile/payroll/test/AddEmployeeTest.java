
package com.agile.payroll.test;

import com.agile.payroll.BiWeekSchedule;
import com.agile.payroll.CommissionedClassification;
import com.agile.payroll.Employee;
import com.agile.payroll.HoldMethod;
import com.agile.payroll.HourlyClassification;
import com.agile.payroll.MonthlySchedule;
import com.agile.payroll.PaymentClassification;
import com.agile.payroll.PaymentMethod;
import com.agile.payroll.PaymentSchedule;
import com.agile.payroll.PayrollDatabase;
import com.agile.payroll.SalariedClassification;
import com.agile.payroll.WeeklySchedule;
import com.agile.payroll.transaction.AddCommissionedEmployee;
import com.agile.payroll.transaction.AddHourlyEmployee;
import com.agile.payroll.transaction.AddSalariedEmployee;

import junit.framework.TestCase;

public class AddEmployeeTest extends TestCase {

    public void test001AddSalariedEmployee() {
        String empId = "emp001";
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.0);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertEquals("Bob", e.getName());

        PaymentClassification pc = e.getClassification();
        assertTrue(pc instanceof SalariedClassification);
        assertEquals(1000.0, ((SalariedClassification) pc).getSalary());

        PaymentSchedule ps = e.getSchedule();
        assertTrue(ps instanceof MonthlySchedule);

        PaymentMethod pm = e.getMethod();
        assertTrue(pm instanceof HoldMethod);
    }

    public void test002AddHourlyEmployee() {
        String empId = "emp002";
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 1000.0);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertEquals("Bob", e.getName());

        PaymentClassification pc = e.getClassification();
        assertTrue(pc instanceof HourlyClassification);
        assertEquals(1000.0, ((HourlyClassification) pc).getRate());

        PaymentSchedule ps = e.getSchedule();
        assertTrue(ps instanceof WeeklySchedule);

        PaymentMethod pm = e.getMethod();
        assertTrue(pm instanceof HoldMethod);
    }

    public void test003AddCommEmployee() {
        String empId = "emp003";
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 1000.0, 0.5);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertEquals("Bob", e.getName());

        PaymentClassification pc = e.getClassification();
        assertTrue(pc instanceof CommissionedClassification);
        assertEquals(1000.0, ((CommissionedClassification) pc).getSalary());
        assertEquals(0.5, ((CommissionedClassification) pc).getCommRate());

        PaymentSchedule ps = e.getSchedule();
        assertTrue(ps instanceof BiWeekSchedule);

        PaymentMethod pm = e.getMethod();
        assertTrue(pm instanceof HoldMethod);
    }
}
