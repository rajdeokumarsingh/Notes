
package com.agile.payroll.test;

import com.agile.payroll.CommissionedClassification;
import com.agile.payroll.Employee;
import com.agile.payroll.PayrollDatabase;
import com.agile.payroll.SalesReceipt;
import com.agile.payroll.transaction.AddCommissionedEmployee;
import com.agile.payroll.transaction.AddSalariedEmployee;
import com.agile.payroll.transaction.AddSaleReceiptTransaction;

import junit.framework.TestCase;

public class AddSaleReceiptTest extends TestCase {

    public void test001AddSaleReceiptNormal() {
        String empId = "emp001";
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 1000.0, 0.3);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);

        AddSaleReceiptTransaction art = new AddSaleReceiptTransaction(1234567, 500.0,
                empId);
        art.execute();

        CommissionedClassification cc = (CommissionedClassification) e.getClassification();
        SalesReceipt sr2 = cc.getSalesReceipt(1234567);
        assertEquals(500.0, sr2.getAmount());
    }

    public void test002TimeCardTransIllegalArg() {
        String empId = "emp910";

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNull(e);

        try {
            AddSaleReceiptTransaction art = new AddSaleReceiptTransaction(
                    1111111, 500.0, empId);
            art.execute();
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    public void test003TimeCardTransIllegalArg2() {
        String empId = "emp005";

        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.0);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);

        try {
            AddSaleReceiptTransaction art = new AddSaleReceiptTransaction(
                    1111112, 500.0, empId);
            art.execute();
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }
}
