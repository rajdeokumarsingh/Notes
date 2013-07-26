package com.agile.payroll.test;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.database.PayrollDatabase;
import com.agile.payroll.transactions.AddCommissionedEmployee;
import com.agile.payroll.transactions.DeleteEmployee;

import junit.framework.TestCase;

public class DeleteEmployeeTest extends TestCase {

    public void test001DeleteEmployee() {
        String empId = "emp004";
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 1000.0, 0.5);
        t.execute();

        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertEquals("Bob", e.getName());
        
        DeleteEmployee d = new DeleteEmployee(empId);
        d.execute();
        
        Employee e1 = PayrollDatabase.getInstance().getEmployee(empId);
        assertEquals(null, e1);
    }
    
    public void test002DeleteEmployeeNotExist() {
        String empId = "emp999"; // not exist

        DeleteEmployee d = new DeleteEmployee(empId);
        d.execute();
        
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertEquals(null, e);
    }
}
