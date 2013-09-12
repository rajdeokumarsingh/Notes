package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandDeviceInfo;
import com.pekall.plist.beans.CommandMsg;
import com.pekall.plist.beans.CommandObject;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/8/13
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandDeviceInfoTest extends TestCase {
    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>aa483d15-168d-4022-b69f-dac292096176</string>\n" +
            "\t<key>Command</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>Queries</key>\n" +
            "\t\t<array>\n" +
            "\t\t\t<string>UDID</string>\n" +
            "\t\t\t<string>DeviceName</string>\n" +
            "\t\t\t<string>OSVersion</string>\n" +
            "\t\t\t<string>BuildVersion</string>\n" +
            "\t\t\t<string>ModelName</string>\n" +
            "\t\t\t<string>Model</string>\n" +
            "\t\t\t<string>ProductName</string>\n" +
            "\t\t\t<string>SerialNumber</string>\n" +
            "\t\t\t<string>DeviceCapacity</string>\n" +
            "\t\t\t<string>AvailableDeviceCapacity</string>\n" +
            "\t\t\t<string>BatteryLevel</string>\n" +
            "\t\t\t<string>CellularTechnology</string>\n" +
            "\t\t\t<string>IMEI</string>\n" +
            "\t\t\t<string>MEID</string>\n" +
            "\t\t\t<string>ModemFirmwareVersion</string>\n" +
            "\t\t\t<string>ICCID</string>\n" +
            "\t\t\t<string>BluetoothMAC</string>\n" +
            "\t\t\t<string>WiFiMAC</string>\n" +
            "\t\t\t<string>EthernetMAC</string>\n" +
            "\t\t\t<string>CurrentCarrierNetwork</string>\n" +
            "\t\t\t<string>SIMCarrierNetwork</string>\n" +
            "\t\t\t<string>SubscriberCarrierNetwork</string>\n" +
            "\t\t\t<string>CarrierSettingsVersion</string>\n" +
            "\t\t\t<string>PhoneNumber</string>\n" +
            "\t\t\t<string>VoiceRoamingEnabled</string>\n" +
            "\t\t\t<string>DataRoamingEnabled</string>\n" +
            "\t\t\t<string>IsRoaming</string>\n" +
            "\t\t\t<string>SubscriberMCC</string>\n" +
            "\t\t\t<string>SubscriberMNC</string>\n" +
            "\t\t\t<string>SIMMCC</string>\n" +
            "\t\t\t<string>SIMMNC</string>\n" +
            "\t\t\t<string>CurrentMCC</string>\n" +
            "\t\t\t<string>CurrentMNC</string>\n" +
            "\t\t</array>\n" +
            "\t\t<key>RequestType</key>\n" +
            "\t\t<string>DeviceInformation</string>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXmlMsg() throws Exception {
        CommandMsg msg = new CommandMsg(
                "aa483d15-168d-4022-b69f-dac292096176", new CommandDeviceInfo());
        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testParseXmlMsg() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();

        assertEquals(msg.getRequestType(), CommandObject.REQ_TYPE_DEVICE_INFO);
        assertTrue(msg.getCommand() instanceof CommandDeviceInfo);

        CommandMsg msg1 = new CommandMsg(
                "aa483d15-168d-4022-b69f-dac292096176", new CommandDeviceInfo());
        assertEquals(msg, msg1);

        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);
        assertEquals(xml, TEST_XML);
    }
}
