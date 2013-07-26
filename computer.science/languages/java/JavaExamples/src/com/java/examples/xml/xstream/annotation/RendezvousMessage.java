package com.java.examples.xml.xstream.annotation;

import com.thoughtworks.xstream.annotations.*;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-20
 * Time: 下午9:28
 * To change this template use File | Settings | File Templates.
 */
@XStreamAlias("message")
public class RendezvousMessage {

    // omit this field
    @XStreamOmitField
    private String id = "rmsg000";

    @XStreamAlias("type")
    @XStreamAsAttribute
    private int messageType;

    @XStreamImplicit(itemFieldName = "part")
    private List<String> content;

    @XStreamAsAttribute
    @XStreamConverter(value = BooleanConverter.class,
            booleans = {false}, strings = {"yes", "no"})
    private boolean important;

    @XStreamAlias("new")
    @XStreamConverter(CalendarStringConverter.class)
    private Calendar created = new GregorianCalendar();

    public RendezvousMessage() {
        this(-1, new ArrayList<String>());
    }

    public RendezvousMessage(int messageType) {
        this(messageType, new ArrayList<String>());
    }

    public RendezvousMessage(int messageType, List<String> content) {
        this.messageType = messageType;
        this.content = content;
    }

    public void addContent(String c) {
        content.add(c);
    }
}
