package com.java.examples.xml.xstream.converter;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-21
 * Time: 下午8:56
 * To change this template use File | Settings | File Templates.
 */
public class BirthdayConverter implements Converter {

    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
        Birthday birthday = (Birthday)o;
        if (birthday.getGender() != '\0') {
            writer.addAttribute("gender", Character.toString(birthday.getGender()));
        }
        if (birthday.getPerson() != null) {
            writer.startNode("person");
            context.convertAnother(birthday.getPerson());
            writer.endNode();
        }
        if (birthday.getDate() != null) {
            writer.startNode("birth");
            context.convertAnother(birthday.getDate());
            writer.endNode();
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader,
                            UnmarshallingContext context) {
        Birthday birthday = new Birthday();
        String gender = reader.getAttribute("gender");
        if (gender != null) {
            if (gender.length() > 0) {

                if (gender.startsWith("f")) {
                    birthday.setGenderFemale();
                } else if (gender.startsWith("m")) {
                    birthday.setGenderMale();
                } else {
                    throw new ConversionException("Invalid gender value: " + gender);
                }
            } else {
                throw new ConversionException("Empty string is invalid gender value");
            }
        }
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            if ("person".equals(reader.getNodeName())) {
                Person person = (Person)context.convertAnother(birthday, Person.class);
                birthday.setPerson(person);
            } else if ("birth".equals(reader.getNodeName())) {
                Calendar date = (Calendar)context.convertAnother(birthday, Calendar.class);
                birthday.setDate(date);
            }
            reader.moveUp();
        }
        return birthday;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(Birthday.class);
    }
}
