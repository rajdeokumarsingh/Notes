package com.java.examples.xml.xstream.annotation;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterMatcher;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-21
 * Time: 上午9:10
 * To change this template use File | Settings | File Templates.
 */
public class CalendarStringConverter implements Converter {

    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        Calendar calendar = (Calendar) o;
        hierarchicalStreamWriter.setValue(
                String.valueOf(calendar.getTime().getTime()));
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(
                Long.parseLong(hierarchicalStreamReader.getValue())));
        return calendar;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(GregorianCalendar.class);
    }
}
