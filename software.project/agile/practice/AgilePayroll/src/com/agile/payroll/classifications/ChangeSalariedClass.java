package com.agile.payroll.classifications;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.domain.PaymentClassification;
import com.agile.payroll.schedules.MonthlySchedule;
import com.agile.payroll.transactions.ChangeEmployeeProperty;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-7
 * Time: 下午8:01
 * To change this template use File | Settings | File Templates.
 */
public class ChangeSalariedClass extends ChangeEmployeeProperty {
    private double salary;

    public ChangeSalariedClass(String empId, double salary) {
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
