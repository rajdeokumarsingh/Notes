package com.agile.payroll.affiliations;

import com.agile.payroll.domain.Affiliation;

/**
 * Null Object for Affiliation
 */
public class NoAffiliation implements Affiliation {

    @Override
    public double getFee(long date) {
        return 0;
    }

    @Override
    public void post(long date) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
