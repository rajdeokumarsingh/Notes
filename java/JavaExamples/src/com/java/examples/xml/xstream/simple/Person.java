package com.java.examples.xml.xstream.simple;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-20
 * Time: 下午5:57
 * To change this template use File | Settings | File Templates.
 */
public class Person {
    private String firstname;
    private String lastname;
    private PhoneNumber phone;
    private PhoneNumber fax;

    public Person() {
        this("", "", new PhoneNumber(), new PhoneNumber());
    }

    public Person(String firstname, String lastname,
                  PhoneNumber phone, PhoneNumber fax) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.fax = fax;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phone=" + phone.toString() +
                ", fax=" + fax.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!fax.equals(person.fax)) return false;
        if (!firstname.equals(person.firstname)) return false;
        if (!lastname.equals(person.lastname)) return false;
        if (!phone.equals(person.phone)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstname.hashCode();
        result = 31 * result + lastname.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + fax.hashCode();
        return result;
    }
}
