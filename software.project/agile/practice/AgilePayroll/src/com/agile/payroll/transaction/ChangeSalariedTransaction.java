package com.agile.payroll.transaction;

import com.agile.payroll.*;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-7
 * Time: 下午8:01
 * To change this template use File | Settings | File Templates.
 */
public class ChangeSalariedTransaction extends ChangeEmployeeTransaction {
    private double salary;

    public ChangeSalariedTransaction(String empId, double salary) {
        super(empId);
        this.salary = salary;
    }

    @Override
    public void change(Employee e) {
        PaymentClassification pc = e.getClassification();
        if(pc instanceof SalariedClassification) {
           SalariedClassification sc = (SalariedClassification) pc;
           sc.setSalary(salary);
        } else {
            e.setClassification(new SalariedClassification(salary));
            e.setSchedule(new MonthlySchedule());
        }
    }
}
