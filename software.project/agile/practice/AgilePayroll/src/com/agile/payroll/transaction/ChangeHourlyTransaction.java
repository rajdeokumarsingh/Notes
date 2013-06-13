package com.agile.payroll.transaction;

import com.agile.payroll.Employee;
import com.agile.payroll.HourlyClassification;
import com.agile.payroll.PaymentClassification;
import com.agile.payroll.WeeklySchedule;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-7
 * Time: 下午6:41
 * To change this template use File | Settings | File Templates.
 */
public class ChangeHourlyTransaction extends ChangeEmployeeTransaction {
    private String empId;
    private double rate;

    public ChangeHourlyTransaction(String empId, double rate) {
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
