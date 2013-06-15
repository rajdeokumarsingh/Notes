package com.agile.payroll.methods;

import com.agile.payroll.domain.PayCheck;
import com.agile.payroll.domain.PaymentMethod;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午1:20
 * To change this template use File | Settings | File Templates.
 */
public class DirectMethod implements PaymentMethod {

    private final String bank;
    private final String account;

    public DirectMethod(String bank, String account) {
        this.bank = bank;
        this.account = account;
    }

    @Override
    public void pay(PayCheck pay) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getBank() {
        return bank;
    }

    public String getAccount() {
        return account;
    }
}
