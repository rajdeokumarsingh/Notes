package com.agile.payroll;


import java.util.HashMap;

public class HourlyClassification implements PaymentClassification{

    private HashMap<Long, TimeCard> timeCards = new HashMap<Long, TimeCard>();
    private double rate;

    public double getRate() {
        return rate;
    }

    public void addTimeCard(TimeCard tc) {
        timeCards.put(tc.getDate(), tc);
    }
    
    public TimeCard getTimeCard(long d) {
        return timeCards.get(d);
    }
    
    public HourlyClassification(double rate) {
        this.rate = rate;
    }
    
    @Override
    public double calculatePay(long date) {
        // TODO Auto-generated method pay
        return 0;
    }

    @Override
    public void post(long date) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
