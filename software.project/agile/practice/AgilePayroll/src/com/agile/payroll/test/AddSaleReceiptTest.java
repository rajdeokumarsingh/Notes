
package com.agile.payroll.test;

import com.agile.payroll.classifications.CommissionedClassification;
import com.agile.payroll.domain.Employee;
import com.agile.payroll.database.PayrollDatabase;
import com.agile.payroll.classifications.SalesReceipt;
import com.agile.payroll.transactions.AddCommissionedEmployee;
import com.agile.payroll.transactions.AddSalariedEmployee;
import com.agile.payroll.classifications.AddSaleReceipt;

import junit.framework.TestCase;

public class AddSaleReceiptTest extends TestCase {

    public void test001AddSaleReceiptNormal() {
        String empId = "emp001";
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 1000.0, 0.3);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);

        AddSaleReceipt art = new AddSaleReceipt(1234567, 500.0,
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
            AddSaleReceipt art = new AddSaleReceipt(
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
            AddSaleReceipt art = new AddSaleReceipt(
                    1111112, 500.0, empId);
            art.execute();
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }
}
