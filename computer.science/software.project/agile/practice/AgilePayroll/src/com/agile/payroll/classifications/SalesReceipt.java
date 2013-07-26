package com.agile.payroll.classifications;


public class SalesReceipt {
    private long date;
    private double amount;
    
    public SalesReceipt(long date, double amount) {
        this.date = date;
        this.amount = amount;
    }
    
    public long getDate() {
        return this.date;
    }
    public void setDate(long date) {
        this.date = date;
    }
    public double getAmount() {
        return this.amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
}
