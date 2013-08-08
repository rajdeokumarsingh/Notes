package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandDeviceInfoStatus;
import com.pekall.plist.beans.CommandStatusMsg;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/8/13
 * Time: 8:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandDeviceInfoStatusTest extends TestCase {
    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>QueryResponses</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>UDID</key>\n" +
            "\t\t<string>ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd</string>\n" +
            "\t\t<key>DeviceName</key>\n" +
            "\t\t<string>test ios</string>\n" +
            "\t\t<key>BuildVersion</key>\n" +
            "\t\t<string>ios 12345</string>\n" +
            "\t\t<key>DeviceCapacity</key>\n" +
            "\t\t<real>0.0</real>\n" +
            "\t\t<key>AvailableDeviceCapacity</key>\n" +
            "\t\t<real>12345.5</real>\n" +
            "\t\t<key>BatteryLevel</key>\n" +
            "\t\t<real>0.0</real>\n" +
            "\t\t<key>CellularTechnology</key>\n" +
            "\t\t<integer>0</integer>\n" +
            "\t\t<key>BluetoothMAC</key>\n" +
            "\t\t<string>xxxx-xxxx-xxxx-xxxx</string>\n" +
            "\t\t<key>WiFiMAC</key>\n" +
            "\t\t<string>xxxx-xxxxx-xxxx-xxxx</string>\n" +
            "\t\t<key>CarrierSettingsVersion</key>\n" +
            "\t\t<string>CMCC</string>\n" +
            "\t\t<key>VoiceRoamingEnabled</key>\n" +
            "\t\t<false/>\n" +
            "\t\t<key>DataRoamingEnabled</key>\n" +
            "\t\t<true/>\n" +
            "\t\t<key>IsRoaming</key>\n" +
            "\t\t<false/>\n" +
            "\t\t<key>CurrentMCC</key>\n" +
            "\t\t<string>CMCC</string>\n" +
            "\t</dict>\n" +
            "\t<key>Status</key>\n" +
            "\t<string>Acknowledged</string>\n" +
            "\t<key>UDID</key>\n" +
            "\t<string>ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd</string>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>aa483d15-168d-4022-b69f-dac292096176</string>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXmlMsg() throws Exception {
        CommandDeviceInfoStatus status = new CommandDeviceInfoStatus();
        status.setStatus(CommandStatusMsg.CMD_STAT_ACKNOWLEDGED);
        status.setCommandUUID("aa483d15-168d-4022-b69f-dac292096176");
        status.setUDID("ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd");
        status.getQueryResponses().setUDID("ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd");
        status.getQueryResponses().setAvailableDeviceCapacity(12345.5);
        status.getQueryResponses().setBluetoothMAC("xxxx-xxxx-xxxx-xxxx");
        status.getQueryResponses().setBuildVersion("ios 12345");
        status.getQueryResponses().setCarrierSettingsVersion("CMCC");
        status.getQueryResponses().setCellularTechnology(0);
        status.getQueryResponses().setCurrentMCC("CMCC");
        status.getQueryResponses().setDataRoamingEnabled(true);
        status.getQueryResponses().setIsRoaming(false);
        status.getQueryResponses().setDeviceName("test ios");
        status.getQueryResponses().setWiFiMAC("xxxx-xxxxx-xxxx-xxxx");

        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);
        PlistDebug.logTest(xml);

        assertEquals(xml, TEST_XML);
    }

    public void testFromXml() throws Exception {

        CommandDeviceInfoStatus status = new CommandDeviceInfoStatus();
        status.setStatus(CommandStatusMsg.CMD_STAT_ACKNOWLEDGED);
        status.setCommandUUID("aa483d15-168d-4022-b69f-dac292096176");
        status.setUDID("ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd");
        status.getQueryResponses().setUDID("ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd");
        status.getQueryResponses().setAvailableDeviceCapacity(12345.5);
        status.getQueryResponses().setBluetoothMAC("xxxx-xxxx-xxxx-xxxx");
        status.getQueryResponses().setBuildVersion("ios 12345");
        status.getQueryResponses().setCarrierSettingsVersion("CMCC");
        status.getQueryResponses().setCellularTechnology(0);
        status.getQueryResponses().setCurrentMCC("CMCC");
        status.getQueryResponses().setDataRoamingEnabled(true);
        status.getQueryResponses().setIsRoaming(false);
        status.getQueryResponses().setDeviceName("test ios");
        status.getQueryResponses().setWiFiMAC("xxxx-xxxxx-xxxx-xxxx");

        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandDeviceInfoStatus status1 = (CommandDeviceInfoStatus) parser.getMessage();
        assertEquals(status, status1);

        NSDictionary root = PlistBeanConverter.createNdictFromBean(status1);
        String xml = PlistXmlParser.toXml(root);
        assertEquals(xml, TEST_XML);
    }
}
