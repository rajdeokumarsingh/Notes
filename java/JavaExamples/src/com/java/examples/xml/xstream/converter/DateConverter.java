package com.java.examples.xml.xstream.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-21
 * Time: 下午12:07
 * To change this template use File | Settings | File Templates.
 */
public class DateConverter implements Converter {
    private Locale locale;

    public DateConverter(Locale locale) {
        super();
        this.locale = locale;
    }

    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        Calendar calendar = (Calendar) o;
        Date date = calendar.getTime();

        DateFormat format = DateFormat.getDateInstance(DateFormat.FULL, this.locale);
        hierarchicalStreamWriter.setValue(format.format(date));
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        Calendar calendar = new GregorianCalendar();
        DateFormat format = DateFormat.getDateInstance(DateFormat.FULL, this.locale);
        try {
            calendar.setTime(format.parse(hierarchicalStreamReader.getValue()));
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return calendar;
    }

    @Override
    public boolean canConvert(Class aClass) {
        // isAssignableFrom alias to isBaseClass
        return Calendar.class.isAssignableFrom(aClass);
    }
}
