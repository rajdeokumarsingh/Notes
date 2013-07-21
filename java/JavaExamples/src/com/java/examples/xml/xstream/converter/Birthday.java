package com.java.examples.xml.xstream.converter;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-21
 * Time: 下午8:55
 * To change this template use File | Settings | File Templates.
 */
public class Birthday {
    private Person person;
    private Calendar date;
    private char gender;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public char getGender() {
        return gender;
    }

    public void setGenderMale() {
        this.gender = 'm';
    }

    public void setGenderFemale() {
        this.gender = 'f';
    }
}
