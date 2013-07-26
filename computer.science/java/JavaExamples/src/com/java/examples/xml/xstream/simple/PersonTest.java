package com.java.examples.xml.xstream.simple;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-20
 * Time: 下午6:27
 * To change this template use File | Settings | File Templates.
 */
public class PersonTest extends TestCase {
    public void testGenerateXml() {
        XStream xStream = new XStream(new DomDriver(
                "UTF-8", new NoNameCoder()));
        xStream.alias("person", Person.class);
        xStream.alias("number", PhoneNumber.class);

        Person joe = new Person("tom", "joe",
                new PhoneNumber(123, "234"),
                new PhoneNumber(234, "345"));
        String xml = xStream.toXML(joe);
        System.out.println(xml);

        Person joe1 = (Person) xStream.fromXML(xml);
        assertEquals(joe, joe1);
    }
}
