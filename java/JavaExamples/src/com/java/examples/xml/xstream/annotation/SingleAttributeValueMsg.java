package com.java.examples.xml.xstream.annotation;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-20
 * Time: 下午9:28
 * To change this template use File | Settings | File Templates.
 */
@XStreamAlias("message")
@XStreamConverter(value = ToAttributedValueConverter.class,
        strings = {"content"}) // content is child entry
                               // all other else are attributes
public class SingleAttributeValueMsg {

    @XStreamAlias("type")
    private int messageType;

    private String content;

    @XStreamConverter(value = BooleanConverter.class,
            booleans = {false}, strings = {"yes", "no"})
    private boolean important;

    // @XStreamConverter(CalendarStringConverter.class)
    // private Calendar created = new GregorianCalendar();

    public SingleAttributeValueMsg() {
        this(-1, "");
    }

    public SingleAttributeValueMsg(int messageType) {
        this(messageType, "");
    }

    public SingleAttributeValueMsg(int messageType, String content) {
        this.messageType = messageType;
        this.content = content;
    }
}
