package com.java.examples.xml.xstream.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-21
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
public class PersonConverter implements Converter {
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        Person person = (Person) o;
        hierarchicalStreamWriter.startNode("full_name");
        hierarchicalStreamWriter.setValue(person.getName());
        hierarchicalStreamWriter.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        Person person = new Person();
        hierarchicalStreamReader.moveDown();
        person.setName(hierarchicalStreamReader.getValue());
        hierarchicalStreamReader.moveUp();
        return person;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(Person.class);
    }
}
