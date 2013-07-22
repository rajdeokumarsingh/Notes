package com.java.examples.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-13
 * Time: 上午8:29
 * To change this template use File | Settings | File Templates.
 */
public class WriteXmlFile {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("company");
            doc.appendChild(rootElement);

            Element staff = doc.createElement("staff");
            staff.setAttribute("id", "s0001");
            rootElement.appendChild(staff);

            Element firstname = doc.createElement("first_name");
            firstname.appendChild(doc.createTextNode("San"));
            staff.appendChild(firstname);

            Element secondname = doc.createElement("second_name");
            secondname.appendChild(doc.createTextNode("Zhang"));
            staff.appendChild(secondname);

            Element salary = doc.createElement("salary");
            salary.appendChild(doc.createTextNode("10000"));


            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);

            transformer.transform(source, result);
            System.out.println(sw.toString());

        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
