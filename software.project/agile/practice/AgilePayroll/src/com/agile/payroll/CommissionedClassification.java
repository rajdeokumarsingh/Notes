package com.agile.payroll;

import java.util.HashMap;

public class CommissionedClassification implements PaymentClassification {
    private double mSalary;
    private double mCommRate;
    
    private HashMap<Long, SalesReceipt> mSales = new HashMap<Long, SalesReceipt>();
    
    public double getSalary() {
        return mSalary;
    }

    public double getCommRate() {
        return mCommRate;
    }
    
    public CommissionedClassification(double salary, double commRate) {
        this.mSalary = salary;
        this.mCommRate = commRate;
    }
    
    @Override
    public double calculatePay(long date) {
        double commit = 0;

        long begin = DateUtils.monthBeginDate(date);
        for(Long l : mSales.keySet()) {
            long ll = l.longValue();
            if(ll < begin || ll > date) {
                continue;
            }
            commit += ((SalesReceipt)mSales.get(l)).getAmount() * mCommRate;
        }

        return mSalary + commit;
    }

    @Override
    public void post(long date) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public SalesReceipt getSalesReceipt(long date) {
        return mSales.get(date);
    }

    public void addSaleReceipt(SalesReceipt salesReceipt) {
        mSales.put(salesReceipt.getDate(), salesReceipt);
    }
}
