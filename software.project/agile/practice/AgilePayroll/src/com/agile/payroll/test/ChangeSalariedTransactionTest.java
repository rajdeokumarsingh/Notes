package com.agile.payroll.test;

import com.agile.payroll.classifications.ChangeSalariedClass;
import com.agile.payroll.classifications.SalariedClassification;
import com.agile.payroll.database.PayrollDatabase;
import com.agile.payroll.domain.Employee;
import com.agile.payroll.domain.PaymentClassification;
import com.agile.payroll.transactions.AddSalariedEmployee;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-7
 * Time: 下午8:06
 * To change this template use File | Settings | File Templates.
 */
public class ChangeSalariedTransactionTest extends TestCase {
    public void test001ChangeSalariedTrans() {
        String empId = "empCST001";
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.0);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        PaymentClassification pc = e.getClassification();
        assertTrue(pc instanceof SalariedClassification);
        assertEquals(1000.0, ((SalariedClassification) pc).getSalary());

        ChangeSalariedClass trans = new ChangeSalariedClass(empId, 1200.0);
        trans.execute();

        PaymentClassification pct = e.getClassification();
        assertTrue(pc instanceof SalariedClassification);

        SalariedClassification sc = (SalariedClassification) pc;
        assertEquals(1200.0, sc.getSalary());
    }
}
