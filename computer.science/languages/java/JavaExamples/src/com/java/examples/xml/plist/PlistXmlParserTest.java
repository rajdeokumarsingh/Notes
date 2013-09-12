package com.java.examples.xml.plist;

import com.dd.plist.*;
import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 7/31/13
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlistXmlParserTest extends TestCase {
    private static final String PEOPLE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>People</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>Name</key>\n" +
            "\t\t\t<string>Peter</string>\n" +
            "\t\t\t<key>Age</key>\n" +
            "\t\t\t<integer>35</integer>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>Name</key>\n" +
            "\t\t\t<string>Lisa</string>\n" +
            "\t\t\t<key>Age</key>\n" +
            "\t\t\t<integer>42</integer>\n" +
            "\t\t</dict>\n" +
            "\t</array>\n" +
            "</dict>\n" +
            "</plist>";

    public void testCreatePlist() throws Exception {
        NSDictionary root = new NSDictionary();
        NSArray people = new NSArray(2);

        Calendar cal = Calendar.getInstance();
        cal.set(2011, 1, 13, 9, 28);

        NSDictionary person1 = new NSDictionary();
        person1.put("Name", "Peter");
        // person1.put("RegistrationDate", cal.getTime());
        person1.put("Age", 35);
        // person1.put("Photo", new NSData(new File("peter.jpg")));

        NSDictionary person2 = new NSDictionary();
        person2.put("Name", "Lisa");
        person2.put("Age", 42);
        // person2.put("RegistrationDate", cal.getTime());
        // person2.put("Photo", new NSData(new File("lisa.jpg")));

        people.setValue(0, person1);
        people.setValue(1, person2);

        root.put("People", people);

        // ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // PropertyListParser.saveAsXML(root, bos);
        // System.out.println("xml: " + bos.toString());
        // System.out.println("xml: " + PlistXmlParser.toXml(root));
        assertEquals(PEOPLE_XML, PlistXmlParser.toXml(root));
    }

    public void testParseXml() throws Exception {
        // NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(TEST_XML.getBytes());
        NSDictionary rootDict = (NSDictionary) PlistXmlParser.fromXml(TEST_XML);
        NSArray array = (NSArray) rootDict.objectForKey("People");

        NSDictionary person1 = (NSDictionary) array.objectAtIndex(0);
        NSString name1 = (NSString) person1.objectForKey("Name");
        String sname1 = name1.getContent();
        NSDate date1 = (NSDate) person1.objectForKey("RegistrationDate");
        Date ddate1 = date1.getDate();
        NSNumber number1 = (NSNumber) person1.objectForKey("Age");
        int age1 = number1.intValue();

        System.out.println("parse data: " + sname1 + "," + ddate1 + "," + age1);
        // NSDictionary person2 = (NSDictionary) array.objectAtIndex(1);
    }

    private final static String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "   <key>People</key>\n" +
            "   <array>\n" +
            "       <dict>\n" +
            "           <key>Name</key>\n" +
            "           <string>Peter</string>\n" +
            "           <key>RegistrationDate</key>\n" +
            "           <date>2011-02-13T09:28:27Z</date>\n" +
            "           <key>Age</key>\n" +
            "           <integer>35</integer>\n" +
            "       </dict>\n" +
            "       <dict>\n" +
            "           <key>Name</key>\n" +
            "           <string>Lisa</string>\n" +
            "           <key>Age</key>\n" +
            "           <integer>42</integer>\n" +
            "           <key>RegistrationDate</key>\n" +
            "           <date>2011-02-13T09:28:27Z</date>\n" +
            "       </dict>\n" +
            "   </array>\n" +
            "</dict>\n" +
            "</plist>\n";

}
