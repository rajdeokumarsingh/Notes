package com.agile.payroll.classifications;


import com.agile.payroll.utils.DateUtils;
import com.agile.payroll.domain.PaymentClassification;

import java.util.HashMap;

public class HourlyClassification implements PaymentClassification {

    public static final int WORKING_HOURS = 8;
    public static final double OVERTIME_RATE = 1.5;

    private HashMap<Long, TimeCard> timeCards = new HashMap<Long, TimeCard>();
    private double rate;

    public HourlyClassification() {
    }

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
        double amount = 0;

        long begin = DateUtils.weekBeginDate(date);
        for (Long d : timeCards.keySet()) {
            long ll = d.longValue();
            if(ll < begin || ll > date) {
                continue;
            }

            double hours = ((TimeCard)timeCards.get(d)).getHours();
            double pay = 0;
            if(DateUtils.isWorkingDay(ll)) {
                if(hours <= WORKING_HOURS) {
                    pay = hours * rate;
                } else {  // overtime in weekday
                    pay += WORKING_HOURS * rate;
                    pay += OVERTIME_RATE * (hours - WORKING_HOURS) * rate;
                }
            } else {  // overtime in sunday, saturday
                pay = OVERTIME_RATE * hours * rate;
            }
            amount += pay;
        }
        return amount;
    }

    @Override
    public void post(long date) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
