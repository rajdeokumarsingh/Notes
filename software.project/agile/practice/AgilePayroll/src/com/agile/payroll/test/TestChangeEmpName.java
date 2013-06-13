package com.agile.payroll.test;

import com.agile.payroll.Employee;
import com.agile.payroll.HoldMethod;
import com.agile.payroll.MonthlySchedule;
import com.agile.payroll.PaymentClassification;
import com.agile.payroll.PaymentMethod;
import com.agile.payroll.PaymentSchedule;
import com.agile.payroll.PayrollDatabase;
import com.agile.payroll.SalariedClassification;
import com.agile.payroll.transaction.AddSalariedEmployee;
import com.agile.payroll.transaction.ChangeNameTransaction;

import junit.framework.TestCase;

public class TestChangeEmpName extends TestCase {

    public void test001ChangeName() {
        String empId = "empCN001";
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.0);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertEquals("Bob", e.getName());

        ChangeNameTransaction cnt = new ChangeNameTransaction(empId, "Tom");
        cnt.execute();

        assertEquals("Tom", e.getName());
    }

    public void test002ChangeName() {
        String empId = "empCN009";

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNull(e);

        try {
            ChangeNameTransaction cnt = new ChangeNameTransaction(empId, "Tom");
            cnt.execute();
        } catch (IllegalArgumentException iae) {
            assertTrue(true);
        }
    }
}
