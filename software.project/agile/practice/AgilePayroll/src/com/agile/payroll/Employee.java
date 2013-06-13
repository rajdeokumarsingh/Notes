
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

    public void payDay(long date) {
        if(!paySchedule.isPayDay(date)) {
            return;
        }
        double amount = payClass.calculatePay(date);
        payMethod.pay(amount);

        payClass.post(date);
        affiliation.post(date);

    }

    @Override
    public String toString() {
        return "employee id: " + empId +  ", name: " + empName
                + ", address: " + empAddress + ", member: " + memberId;
    }
}
