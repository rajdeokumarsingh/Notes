package com.agile.payroll.affiliations;

import com.agile.payroll.domain.Affiliation;

import java.util.HashMap;

public class UnionAffiliation implements Affiliation {
    private double mDues;
    private String memberId;

    private HashMap<Long, ServiceCharge> serviceCharges =
            new HashMap<Long, ServiceCharge>();
    
    public UnionAffiliation(String memberId, double dues) {
        this.memberId = memberId;
        this.mDues = dues;
    }
    
    public double getDues() {
        return mDues;
    }
    public void setDues(double dues) {
        this.mDues = dues;
    }

    @Override
    public double getFee(long date) {
        // TODO Auto-generated methods pay
        return 0;
    }

    @Override
    public void post(long date) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addServiceCharge(long date, ServiceCharge sc) {
        serviceCharges.put(new Long(date), sc);
    }
    
    public ServiceCharge getServiceCharge(long id) {
        return serviceCharges.get(id);
    }
    
    public String getMemberId() {
        return memberId;
    }
}
