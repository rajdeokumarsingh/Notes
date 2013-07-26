
package com.agile.payroll.affiliations;

import com.agile.payroll.domain.Employee;
import com.agile.payroll.database.PayrollDatabase;
import com.agile.payroll.transactions.Transaction;

public class ServiceChargeTransaction implements Transaction {

    private String memberId;
    private long date;
    private double amount;

    public ServiceChargeTransaction(String memberId, long date, double amount) {
        this.memberId = memberId;
        this.date = date;
        this.amount = amount;
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.getInstance().getUnionMember(memberId);
        if (e == null) {
            throw new IllegalArgumentException("No such employee");
        }

        if(e.getAffiliation() == null ||
                !(e.getAffiliation() instanceof UnionAffiliation)) {
            throw new IllegalArgumentException("No such member");
        }
        
        UnionAffiliation uaf = (UnionAffiliation) e.getAffiliation();
        uaf.addServiceCharge(date, new ServiceCharge(date, amount));
    }
}
