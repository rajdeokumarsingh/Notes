package com.agile.payroll.test;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.database.PayrollDatabase;
import com.agile.payroll.transactions.AddSalariedEmployee;
import com.agile.payroll.transactions.PaydayTransaction;
import junit.framework.TestCase;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午7:23
 * To change this template use File | Settings | File Templates.
 */
public class TestPaySalariedEmp extends TestCase {
    public void test001PaySalaryEmp() {
        String empId = "empTPSE001";
        AddSalariedEmployee ase = new AddSalariedEmployee(empId, "Bob", "Home", 15000);
        ase.execute();

        Employee emp = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(emp);

        Date date = new Date(2013,6,30);
        PaydayTransaction pt = new PaydayTransaction(date.getTime());
        pt.execute();

        /*
        PayCheck pc = pt.getPayCheck();
        assertNotNull(pc);
        assertEquals(pc.getPayDate(), date.getTime());
        assertEquals(pc.getGrossPay(), 15000);
        assertEquals(pc.getNetPay(), 15000);
        assertEquals(pc.getDeductions(), 0);
        */
    }
}
