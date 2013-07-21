package com.java.examples.xml.xstream.converter;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-21
 * Time: 上午11:44
 * To change this template use File | Settings | File Templates.
 */
public class PersonSimpleConverter2 extends AbstractSingleValueConverter {
    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(Person.class);
    }

    @Override
    public Object fromString(String s) {
        return new Person(s);
    }

    @Override
    public String toString(Object obj) {
        return ((Person)obj).getName();
    }
}
