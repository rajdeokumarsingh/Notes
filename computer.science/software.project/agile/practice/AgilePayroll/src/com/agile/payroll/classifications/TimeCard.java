
package com.agile.payroll.classifications;


/**
 * Time card for hourly employee
 */
public class TimeCard {
    private double hours;
    private long date;
    private String employeeId;

    public TimeCard(long date, double hours, String employeeId) {
        this.date = date;
        this.hours = hours;
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String id) {
        this.employeeId = id;
    }

    public double getHours() {
        return this.hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
