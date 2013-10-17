package com.pekall.plist;


import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadBase;
import com.pekall.plist.beans.PayloadScep;
import com.pekall.plist.beans.ScepContent;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class PayloadNestedTest extends TestCase {

    public void testNullList() throws Exception {
        String xml = PlistBeanConverter.createPlistXmlFromBean(createScepContentWithNullList());
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_NULL_LIST_XML);

        ScepContent content = (ScepContent) PlistBeanConverter.createBeanFromNdict(
                (NSDictionary) PlistXmlParser.fromXml(TEST_NULL_LIST_XML), ScepContent.class);

        assertEquals(content, createScepContentWithNullList());
        xml = PlistBeanConverter.createPlistXmlFromBean(content);
        assertEquals(xml, TEST_NULL_LIST_XML);
    }

    public void testEmptyList() throws Exception {
        String xml = PlistBeanConverter.createPlistXmlFromBean(createScepContentEmptyList());
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_EMPTY_LIST_XML);

        ScepContent content = (ScepContent) PlistBeanConverter.createBeanFromNdict(
                (NSDictionary) PlistXmlParser.fromXml(TEST_EMPTY_LIST_XML), ScepContent.class);

        assertEquals(content, createScepContentEmptyList());
        xml = PlistBeanConverter.createPlistXmlFromBean(content);
        assertEquals(xml, TEST_EMPTY_LIST_XML);
    }

    public void testEmptyList2() throws Exception {
        String xml = PlistBeanConverter.createPlistXmlFromBean(createScepContentEmptyList2());
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_EMPTY_LIST_XML2);

        ScepContent content = (ScepContent) PlistBeanConverter.createBeanFromNdict(
                (NSDictionary) PlistXmlParser.fromXml(TEST_EMPTY_LIST_XML2), ScepContent.class);

        assertEquals(content, createScepContentEmptyList2());
        xml = PlistBeanConverter.createPlistXmlFromBean(content);
        assertEquals(xml, TEST_EMPTY_LIST_XML2);
    }

    public void testEmptyList3() throws Exception {
        String xml = PlistBeanConverter.createPlistXmlFromBean(createScepContentEmptyList3());
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_EMPTY_LIST_XML3);

        ScepContent content = (ScepContent) PlistBeanConverter.createBeanFromNdict(
                (NSDictionary) PlistXmlParser.fromXml(TEST_EMPTY_LIST_XML3), ScepContent.class);

        assertEquals(content, createScepContentEmptyList3());
        xml = PlistBeanConverter.createPlistXmlFromBean(content);
        assertEquals(xml, TEST_EMPTY_LIST_XML3);
    }

    public void testPartialList() throws Exception {
        String xml = PlistBeanConverter.createPlistXmlFromBean(createScepContentPartialEmptyList());
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_PART_LIST);

        ScepContent content = (ScepContent) PlistBeanConverter.createBeanFromNdict(
                (NSDictionary) PlistXmlParser.fromXml(TEST_PART_LIST), ScepContent.class);

        assertEquals(content, createScepContentPartialEmptyList());
        xml = PlistBeanConverter.createPlistXmlFromBean(content);
        assertEquals(xml, TEST_PART_LIST);
    }

    public void testGenXml() throws Exception {
        String xml = PlistBeanConverter.createPlistXmlFromBean(createScepContent());
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testParseXml() throws Exception {
        PlistDebug.setVerboseDebugLog(true);
        ScepContent content = (ScepContent) PlistBeanConverter.createBeanFromNdict(
                (NSDictionary) PlistXmlParser.fromXml(TEST_XML), ScepContent.class);

        assertEquals(content, createScepContent());
        String xml = PlistBeanConverter.createPlistXmlFromBean(content);
        assertEquals(xml, TEST_XML);
    }

    ScepContent createScepContentWithNullList() {
        ScepContent content = new ScepContent();
        content.setChallenge("388AA78F15B91B75D629B45FE1D584DE");
        content.setKeyType("RSA");
        content.setKeyUsage(5);
        content.setKeysize(1024);
        content.setName("PEKALL-CA");
        content.setRetries(2);
        content.setURL("http://192.168.10.23:3337/rest/mdm/v1/ios/scep");

        return content;
    }

    ScepContent createScepContentEmptyList() {
        ScepContent content = new ScepContent();
        content.setChallenge("388AA78F15B91B75D629B45FE1D584DE");
        content.setKeyType("RSA");
        content.setKeyUsage(5);
        content.setKeysize(1024);
        content.setName("PEKALL-CA");
        content.setRetries(2);
        content.setURL("http://192.168.10.23:3337/rest/mdm/v1/ios/scep");

        List<List<List<String>>> subject = new ArrayList<List<List<String>>>();
        content.setSubject(subject);

        return content;
    }

    ScepContent createScepContentEmptyList2() {
        ScepContent content = new ScepContent();
        content.setChallenge("388AA78F15B91B75D629B45FE1D584DE");
        content.setKeyType("RSA");
        content.setKeyUsage(5);
        content.setKeysize(1024);
        content.setName("PEKALL-CA");
        content.setRetries(2);
        content.setURL("http://192.168.10.23:3337/rest/mdm/v1/ios/scep");

        List<List<List<String>>> subject = new ArrayList<List<List<String>>>();
        List<List<String>> list1 = new ArrayList<List<String>>();
        subject.add(list1);
        List<List<String>> list2 = new ArrayList<List<String>>();
        subject.add(list2);
        content.setSubject(subject);

        return content;
    }

    ScepContent createScepContentEmptyList3() {
        ScepContent content = new ScepContent();
        content.setChallenge("388AA78F15B91B75D629B45FE1D584DE");
        content.setKeyType("RSA");
        content.setKeyUsage(5);
        content.setKeysize(1024);
        content.setName("PEKALL-CA");
        content.setRetries(2);
        content.setURL("http://192.168.10.23:3337/rest/mdm/v1/ios/scep");

        List<List<List<String>>> subject = new ArrayList<List<List<String>>>();
        List<List<String>> list1 = new ArrayList<List<String>>();
        List<String> list11 = new ArrayList<String>();
        list1.add(list11);
        subject.add(list1);
        List<List<String>> list2 = new ArrayList<List<String>>();
        List<String> list22 = new ArrayList<String>();
        list2.add(list22);
        subject.add(list2);
        content.setSubject(subject);

        return content;
    }

    ScepContent createScepContentPartialEmptyList() {
        ScepContent content = new ScepContent();
        content.setChallenge("388AA78F15B91B75D629B45FE1D584DE");
        content.setKeyType("RSA");
        content.setKeyUsage(5);
        content.setKeysize(1024);
        content.setName("PEKALL-CA");
        content.setRetries(2);
        content.setURL("http://192.168.10.23:3337/rest/mdm/v1/ios/scep");

        List<List<List<String>>> subject = new ArrayList<List<List<String>>>();
        List<List<String>> list1 = new ArrayList<List<String>>();
        subject.add(list1);
        List<List<String>> list2 = new ArrayList<List<String>>();
        List<String> list22 = new ArrayList<String>();
        list22.add("O");
        list22.add("Pekall");
        list2.add(list22);
        subject.add(list2);
        content.setSubject(subject);

        return content;
    }

    ScepContent createScepContent() {
        ScepContent content = new ScepContent();
        content.setChallenge("388AA78F15B91B75D629B45FE1D584DE");
        content.setKeyType("RSA");
        content.setKeyUsage(5);
        content.setKeysize(1024);
        content.setName("PEKALL-CA");
        content.setRetries(2);

        List<List<List<String>>> subject = new ArrayList<List<List<String>>>();
        List<List<String>> list1 = new ArrayList<List<String>>();
        List<String> list11 = new ArrayList<String>();
        list11.add("O");
        list11.add("Pekall");
        list1.add(list11);
        subject.add(list1);
        List<List<String>> list2 = new ArrayList<List<String>>();
        List<String> list22 = new ArrayList<String>();
        list22.add("O");
        list22.add("Pekall");
        list2.add(list22);
        subject.add(list2);
        content.setSubject(subject);

        content.setURL("http://192.168.10.23:3337/rest/mdm/v1/ios/scep");

        return content;
    }

    private static final String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>Challenge</key>\n" +
            "\t<string>388AA78F15B91B75D629B45FE1D584DE</string>\n" +
            "\t<key>Key Type</key>\n" +
            "\t<string>RSA</string>\n" +
            "\t<key>Key Usage</key>\n" +
            "\t<integer>5</integer>\n" +
            "\t<key>Keysize</key>\n" +
            "\t<integer>1024</integer>\n" +
            "\t<key>Name</key>\n" +
            "\t<string>PEKALL-CA</string>\n" +
            "\t<key>Retries</key>\n" +
            "\t<integer>2</integer>\n" +
            "\t<key>Subject</key>\n" +
            "\t<array>\n" +
            "\t\t<array>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<string>O</string>\n" +
            "\t\t\t\t<string>Pekall</string>\n" +
            "\t\t\t</array>\n" +
            "\t\t</array>\n" +
            "\t\t<array>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<string>O</string>\n" +
            "\t\t\t\t<string>Pekall</string>\n" +
            "\t\t\t</array>\n" +
            "\t\t</array>\n" +
            "\t</array>\n" +
            "\t<key>URL</key>\n" +
            "\t<string>http://192.168.10.23:3337/rest/mdm/v1/ios/scep</string>\n" +
            "</dict>\n" +
            "</plist>";

     private static final java.lang.String TEST_NULL_LIST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>Challenge</key>\n" +
            "\t<string>388AA78F15B91B75D629B45FE1D584DE</string>\n" +
            "\t<key>Key Type</key>\n" +
            "\t<string>RSA</string>\n" +
            "\t<key>Key Usage</key>\n" +
            "\t<integer>5</integer>\n" +
            "\t<key>Keysize</key>\n" +
            "\t<integer>1024</integer>\n" +
            "\t<key>Name</key>\n" +
            "\t<string>PEKALL-CA</string>\n" +
            "\t<key>Retries</key>\n" +
            "\t<integer>2</integer>\n" +
            "\t<key>URL</key>\n" +
            "\t<string>http://192.168.10.23:3337/rest/mdm/v1/ios/scep</string>\n" +
            "</dict>\n" +
            "</plist>";

    private static final String TEST_EMPTY_LIST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>Challenge</key>\n" +
            "\t<string>388AA78F15B91B75D629B45FE1D584DE</string>\n" +
            "\t<key>Key Type</key>\n" +
            "\t<string>RSA</string>\n" +
            "\t<key>Key Usage</key>\n" +
            "\t<integer>5</integer>\n" +
            "\t<key>Keysize</key>\n" +
            "\t<integer>1024</integer>\n" +
            "\t<key>Name</key>\n" +
            "\t<string>PEKALL-CA</string>\n" +
            "\t<key>Retries</key>\n" +
            "\t<integer>2</integer>\n" +
            "\t<key>Subject</key>\n" +
            "\t<array>\n" +
            "\t</array>\n" +
            "\t<key>URL</key>\n" +
            "\t<string>http://192.168.10.23:3337/rest/mdm/v1/ios/scep</string>\n" +
            "</dict>\n" +
            "</plist>";

    private static final String TEST_EMPTY_LIST_XML2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>Challenge</key>\n" +
            "\t<string>388AA78F15B91B75D629B45FE1D584DE</string>\n" +
            "\t<key>Key Type</key>\n" +
            "\t<string>RSA</string>\n" +
            "\t<key>Key Usage</key>\n" +
            "\t<integer>5</integer>\n" +
            "\t<key>Keysize</key>\n" +
            "\t<integer>1024</integer>\n" +
            "\t<key>Name</key>\n" +
            "\t<string>PEKALL-CA</string>\n" +
            "\t<key>Retries</key>\n" +
            "\t<integer>2</integer>\n" +
            "\t<key>Subject</key>\n" +
            "\t<array>\n" +
            "\t\t<array>\n" +
            "\t\t</array>\n" +
            "\t\t<array>\n" +
            "\t\t</array>\n" +
            "\t</array>\n" +
            "\t<key>URL</key>\n" +
            "\t<string>http://192.168.10.23:3337/rest/mdm/v1/ios/scep</string>\n" +
            "</dict>\n" +
            "</plist>";

    private static final String TEST_EMPTY_LIST_XML3 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>Challenge</key>\n" +
            "\t<string>388AA78F15B91B75D629B45FE1D584DE</string>\n" +
            "\t<key>Key Type</key>\n" +
            "\t<string>RSA</string>\n" +
            "\t<key>Key Usage</key>\n" +
            "\t<integer>5</integer>\n" +
            "\t<key>Keysize</key>\n" +
            "\t<integer>1024</integer>\n" +
            "\t<key>Name</key>\n" +
            "\t<string>PEKALL-CA</string>\n" +
            "\t<key>Retries</key>\n" +
            "\t<integer>2</integer>\n" +
            "\t<key>Subject</key>\n" +
            "\t<array>\n" +
            "\t\t<array>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t</array>\n" +
            "\t\t</array>\n" +
            "\t\t<array>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t</array>\n" +
            "\t\t</array>\n" +
            "\t</array>\n" +
            "\t<key>URL</key>\n" +
            "\t<string>http://192.168.10.23:3337/rest/mdm/v1/ios/scep</string>\n" +
            "</dict>\n" +
            "</plist>";

    private static final String TEST_PART_LIST = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>Challenge</key>\n" +
            "\t<string>388AA78F15B91B75D629B45FE1D584DE</string>\n" +
            "\t<key>Key Type</key>\n" +
            "\t<string>RSA</string>\n" +
            "\t<key>Key Usage</key>\n" +
            "\t<integer>5</integer>\n" +
            "\t<key>Keysize</key>\n" +
            "\t<integer>1024</integer>\n" +
            "\t<key>Name</key>\n" +
            "\t<string>PEKALL-CA</string>\n" +
            "\t<key>Retries</key>\n" +
            "\t<integer>2</integer>\n" +
            "\t<key>Subject</key>\n" +
            "\t<array>\n" +
            "\t\t<array>\n" +
            "\t\t</array>\n" +
            "\t\t<array>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<string>O</string>\n" +
            "\t\t\t\t<string>Pekall</string>\n" +
            "\t\t\t</array>\n" +
            "\t\t</array>\n" +
            "\t</array>\n" +
            "\t<key>URL</key>\n" +
            "\t<string>http://192.168.10.23:3337/rest/mdm/v1/ios/scep</string>\n" +
            "</dict>\n" +
            "</plist>";
}

