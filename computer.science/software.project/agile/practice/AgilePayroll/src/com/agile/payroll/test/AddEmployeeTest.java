
package com.agile.payroll.test;

import com.agile.payroll.schedules.BiWeekSchedule;
import com.agile.payroll.classifications.CommissionedClassification;
import com.agile.payroll.domain.Employee;
import com.agile.payroll.methods.HoldMethod;
import com.agile.payroll.classifications.HourlyClassification;
import com.agile.payroll.schedules.MonthlySchedule;
import com.agile.payroll.domain.PaymentClassification;
import com.agile.payroll.domain.PaymentMethod;
import com.agile.payroll.domain.PaymentSchedule;
import com.agile.payroll.database.PayrollDatabase;
import com.agile.payroll.classifications.SalariedClassification;
import com.agile.payroll.schedules.WeeklySchedule;
import com.agile.payroll.transactions.AddCommissionedEmployee;
import com.agile.payroll.transactions.AddHourlyEmployee;
import com.agile.payroll.transactions.AddSalariedEmployee;

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
