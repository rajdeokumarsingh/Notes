package com.java.examples.xml.xstream.alias;

import com.thoughtworks.xstream.converters.SingleValueConverter;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-20
 * Time: 下午8:48
 * To change this template use File | Settings | File Templates.
 */
public class AuthorConverter implements SingleValueConverter {

    @Override
    public String toString(Object o) {
        return ((Author)o).getName();
    }

    @Override
    public Object fromString(String s) {
        return new Author(s);
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(Author.class);
    }
}
