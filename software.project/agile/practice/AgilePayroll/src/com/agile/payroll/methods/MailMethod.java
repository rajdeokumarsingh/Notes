package com.agile.payroll.methods;

import com.agile.payroll.domain.PayCheck;
import com.agile.payroll.domain.PaymentMethod;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午1:21
 * To change this template use File | Settings | File Templates.
 */
public class MailMethod implements PaymentMethod {
    private final String address;

    public MailMethod(String address) {
        this.address = address;
    }

    @Override
    public void pay(PayCheck pay) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getAdress() {
        return address;
    }
}
