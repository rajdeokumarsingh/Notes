package com.agile.payroll;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午7:41
 * To change this template use File | Settings | File Templates.
 */
public class PayCheck {


    private double payDate;
    private double grossPay;
    private double netPay;
    private double deductions;

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public void setGrossPay(double grossPay) {
        this.grossPay = grossPay;
    }

    public double getPayDate() {
        return payDate;
    }

    public double getGrossPay() {
        return grossPay;
    }

    public double getNetPay() {
        return netPay;
    }

    public double getDeductions() {
        return deductions;
    }

    public void setPayDate(double payDate) {
        this.payDate = payDate;
    }
}

