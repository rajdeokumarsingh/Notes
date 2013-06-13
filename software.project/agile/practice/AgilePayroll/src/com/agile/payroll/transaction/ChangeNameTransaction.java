
package com.agile.payroll.transaction;

import com.agile.payroll.Employee;

public class ChangeNameTransaction extends ChangeEmployeeTransaction {

    private String newName;

    public ChangeNameTransaction(String empId, String newName) {
        super(empId);
        this.newName = newName;
    }

    @Override
    public void change(Employee e) {
        e.setName(newName);
    }

}
