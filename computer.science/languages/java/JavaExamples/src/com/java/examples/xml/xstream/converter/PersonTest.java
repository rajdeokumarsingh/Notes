package com.java.examples.xml.xstream.converter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import junit.framework.TestCase;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-21
 * Time: 上午11:04
 * To change this template use File | Settings | File Templates.
 */
public class PersonTest extends TestCase {
    public void testPerson() throws Exception {
        Person person = new Person();
        person.setName("Ray Jiang");

        XStream xstream = new XStream(
                new DomDriver("UTF-8", new NoNameCoder()));
        xstream.alias("person", Person.class);
        String xml = xstream.toXML(person);
        System.out.println(xstream.toXML(person));

        Person person1 = (Person) xstream.fromXML(xml);
        assertEquals(person, person1);
    }

    public void testPersonConverter() throws Exception {
        Person person = new Person();
        person.setName("Ray Jiang");

        XStream xstream = new XStream(
                new DomDriver("UTF-8", new NoNameCoder()));
        xstream.alias("person", Person.class);
        xstream.registerConverter(new PersonConverter());

        String xml = xstream.toXML(person);
        System.out.println(xstream.toXML(person));

        Person person1 = (Person) xstream.fromXML(xml);
        assertEquals(person, person1);
    }

    public void testPersonSimpleConverter() throws Exception {
        Person person = new Person();
        person.setName("Ray Jiang");

        XStream xstream = new XStream(
                new DomDriver("UTF-8", new NoNameCoder()));
        xstream.alias("person", Person.class);
        xstream.registerConverter(new PersonSimpleConverter());

        String xml = xstream.toXML(person);
        System.out.println(xstream.toXML(person));

        Person person1 = (Person) xstream.fromXML(xml);
        assertEquals(person, person1);
    }

    public void testPersonSimpleConverter2() throws Exception {
        Person person = new Person();
        person.setName("Ray Jiang");

        XStream xstream = new XStream(
                new DomDriver("UTF-8", new NoNameCoder()));
        xstream.alias("person", Person.class);
        xstream.registerConverter(new PersonSimpleConverter2());

        String xml = xstream.toXML(person);
        System.out.println(xstream.toXML(person));

        Person person1 = (Person) xstream.fromXML(xml);
        assertEquals(person, person1);
    }

    public void testDateConverter() {
        // grabs the current date from the virtual machine
        GregorianCalendar calendar = new GregorianCalendar();

        // creates the xstream
        XStream xStream = new XStream(new DomDriver());

        // brazilian portuguese locale
        xStream.registerConverter(new DateConverter(new Locale("zh", "cn")));

        // prints the result
        System.out.println(xStream.toXML(calendar));

        System.out.println("Calendar isAssignableFrom GregorianCalendar: " +
                Calendar.class.isAssignableFrom(GregorianCalendar.class));
        System.out.println("GregorianCalendar isAssignableFrom Calendar: " +
                GregorianCalendar.class.isAssignableFrom(Calendar.class));
    }
}
