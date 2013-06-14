
package com.agile.payroll;

public class Employee {

    private String empId;
    private String empName;
    private String empAddress;

    private PaymentClassification payClass;
    private PaymentMethod payMethod;
    private PaymentSchedule paySchedule;

    private Affiliation affiliation = new NoAffiliation();
    private String memberId;

    public Employee(String id, String name, String address) {
        this.empId = id;
        this.empName = name;
        this.empAddress = address;
    }

    public void setClassification(PaymentClassification ps) {
        this.payClass = ps;
    }

    public void setMethd(PaymentMethod pm) {
        this.payMethod = pm;
    }

    public void setSchedule(PaymentSchedule ps) {
        this.paySchedule = ps;
    }

    public PaymentClassification getClassification() {
        return payClass;
    }

    public String getEmployeeId() {
        return empId;
    }

    public String getName() {
        return empName;
    }

    public void setName(String name) {
        empName = name;
    }

    public PaymentMethod getMethod() {
        return payMethod;
    }

    public PaymentSchedule getSchedule() {
        return paySchedule;
    }

    public String getAddress() {
        return empAddress;
    }

    public void setAffiliation(Affiliation af) {
        affiliation = af;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    double calculatePay(long date) {
        return payClass.calculatePay(date);
    }

    double getFee(long date) {
        // TODO:
        return 0;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public boolean isPayDay(long date) {
        return paySchedule.isPayDay(date);
    }

    public void payDay(long date, PayCheck payCheck) {
        double amount = payClass.calculatePay(date);
        double deduction = affiliation.getFee(date);
        double netPay = amount - deduction;

        payCheck.setGrossPay(amount);
        payCheck.setDeductions(deduction);
        payCheck.setNetPay(netPay);

        payMethod.pay(payCheck);
    }

    @Override
    public String toString() {
        return "employee id: " + empId +  ", name: " + empName
                + ", address: " + empAddress + ", member: " + memberId;
    }
}
