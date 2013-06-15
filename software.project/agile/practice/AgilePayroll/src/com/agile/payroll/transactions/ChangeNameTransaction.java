
package com.agile.payroll.transactions;

import com.agile.payroll.domain.Employee;

public class ChangeNameTransaction extends ChangeEmployeeProperty {

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
