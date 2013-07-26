package com.java.examples.xml.xstream.annotation;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-20
 * Time: 下午9:29
 * To change this template use File | Settings | File Templates.
 */
public class RendezvousMessageTest extends TestCase {
    public void testXml() {
        RendezvousMessage message = new RendezvousMessage(15);

        XStream xstream = new XStream(
                new DomDriver("UTF-8", new NoNameCoder()));
        String xml = xstream.toXML(message);
        System.out.println(xml);
    }

    public void testAnnotation() {
        RendezvousMessage message = new RendezvousMessage(15);
        message.addContent("math");
        message.addContent("english");
        message.addContent("physics");

        XStream xstream = new XStream(
                new DomDriver("UTF-8", new NoNameCoder()));
        xstream.processAnnotations(RendezvousMessage.class);
        String xml = xstream.toXML(message);
        System.out.println(xml);
    }

    public void testAutoDetectAnnotation() {
        RendezvousMessage message = new RendezvousMessage(15);
        message.addContent("math");
        message.addContent("english");
        message.addContent("physics");

        XStream xstream = new XStream(
                new DomDriver("UTF-8", new NoNameCoder()));
        // xstream.processAnnotations(RendezvousMessage.class);

        // cool, but not thread-safe, low performance
        xstream.autodetectAnnotations(true);
        String xml = xstream.toXML(message);
        System.out.println(xml);

        // FIXME: has issue with auto-detect mode, since xstream1 has no
        // annotation information
        /*
        XStream xstream1 = new XStream(
                new DomDriver("UTF-8", new NoNameCoder()));
        RendezvousMessage msg = (RendezvousMessage)xstream1.fromXML(xml);
        */
        // FIXME: no issue since xstream do toXml before
        RendezvousMessage msg = (RendezvousMessage)xstream.fromXML(xml);
    }

    public void testSingleAttributeValue() {
        SingleAttributeValueMsg message = new SingleAttributeValueMsg(15, "message content");

        XStream xstream = new XStream(
                new DomDriver("UTF-8", new NoNameCoder()));
        xstream.processAnnotations(SingleAttributeValueMsg.class);
        String xml = xstream.toXML(message);
        System.out.println(xml);
    }
}
