package com.agile.payroll.classifications;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.domain.PaymentClassification;
import com.agile.payroll.schedules.WeeklySchedule;
import com.agile.payroll.transactions.ChangeEmployeeProperty;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-7
 * Time: 下午6:41
 * To change this template use File | Settings | File Templates.
 */
public class ChangeHourlyClass extends ChangeEmployeeProperty {
    private String empId;
    private double rate;

    public ChangeHourlyClass(String empId, double rate) {
        super(empId);
        this.rate = rate;
    }

    @Override
    public void change(Employee e) {
        PaymentClassification pc = e.getClassification();

        if(pc instanceof HourlyClassification) {
            HourlyClassification hc = (HourlyClassification) pc;
            hc.setRate(rate);
        } else {
            HourlyClassification hc = new HourlyClassification(rate);
            e.setClassification(hc);
            e.setSchedule(new WeeklySchedule());
        }
    }
}
