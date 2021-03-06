package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandDeviceInfoStatus;
import com.pekall.plist.beans.CommandStatusMsg;
import com.pekall.plist.beans.DeviceInfoResp;
import com.pekall.plist.su.device.DeviceInfoRespSU;
import junit.framework.TestCase;

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
            "\t\t<key>AvailableDeviceCapacity</key>\n" +
            "\t\t<real>12345.5</real>\n" +
            "\t\t<key>CellularTechnology</key>\n" +
            "\t\t<integer>0</integer>\n" +
            "\t\t<key>BluetoothMAC</key>\n" +
            "\t\t<string>xxxx-xxxx-xxxx-xxxx</string>\n" +
            "\t\t<key>WiFiMAC</key>\n" +
            "\t\t<string>xxxx-xxxxx-xxxx-xxxx</string>\n" +
            "\t\t<key>CarrierSettingsVersion</key>\n" +
            "\t\t<string>CMCC</string>\n" +
            "\t\t<key>DataRoamingEnabled</key>\n" +
            "\t\t<true/>\n" +
            "\t\t<key>IsRoaming</key>\n" +
            "\t\t<false/>\n" +
            "\t\t<key>CurrentMCC</key>\n" +
            "\t\t<string>CMCC</string>\n" +
            "\t\t<key>HasSamsungSafeAPI</key>\n" +
            "\t\t<true/>\n" +
            "\t\t<key>CanDisableSystemFunction</key>\n" +
            "\t\t<false/>\n" +
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
        status.getQueryResponses().setHasSamsungSafeAPI(true);
        status.getQueryResponses().setCanDisableSystemFunction(false);

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
        status.getQueryResponses().setHasSamsungSafeAPI(true);
        status.getQueryResponses().setCanDisableSystemFunction(false);

        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandDeviceInfoStatus status1 = (CommandDeviceInfoStatus) parser.getMessage();
        assertEquals(status, status1);

        NSDictionary root = PlistBeanConverter.createNdictFromBean(status1);
        String xml = PlistXmlParser.toXml(root);
        assertEquals(xml, TEST_XML);
    }

    public void testErrorParseXml() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(ERROR_XML_MSG);
        CommandDeviceInfoStatus statusMsg = (CommandDeviceInfoStatus) parser.getMessage();

        PlistDebug.logTest(statusMsg.toString());
        assertEquals(statusMsg, createErrorStatueMsg());
    }

    private CommandDeviceInfoStatus createErrorStatueMsg() {
        CommandDeviceInfoStatus msg = new CommandDeviceInfoStatus();
        msg.setCommandUUID("ca642ebf-36fb-4a48-a7da-a1ec71fff28e");
        msg.setStatus("Acknowledged");
        msg.setUDID("34ce984eb2c6d94f152144590492bf6dcce804d1");

        DeviceInfoRespSU resp = msg.getQueryResponses();
        resp.setAvailableDeviceCapacity(5.5006752014160156);
        resp.setBatteryLevel(1.0);
        resp.setBluetoothMAC("c0:63:94:c0:58:6a");
        resp.setBuildVersion("10B329");
        resp.setCarrierSettingsVersion("14.0");
        resp.setCellularTechnology(1);
        resp.setCurrentMCC("000");
        resp.setCurrentMNC("00");
        resp.setDataRoamingEnabled(false);
        resp.setDeviceCapacity(6.3118057250976562);
        resp.setDeviceName("XXO");
        resp.setIMEI("01 328200 621761 5");
        resp.setIsRoaming(true);
        resp.setModel("MD198CH");
        resp.setModelName("iPhone");
        resp.setModemFirmwareVersion("04.12.05");
        resp.setOSVersion("6.1.3");
        resp.setProductName("iPhone3,2");
        resp.setSIMCarrierNetwork("中国联通");
        resp.setSIMMNC("00");
        resp.setSerialNumber("DX6KRFB5DPMW");
        resp.setSubscriberCarrierNetwork("中国联通");
        resp.setSubscriberMNC("00");
        resp.setUDID("34ce984eb2c6d94f152144590492bf6dcce804d1");
        resp.setWiFiMAC("c0:63:94:c0:58:6b");
        return msg;
    }

    private final static String ERROR_XML_MSG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\"\n" +
            "\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "<key>CommandUUID</key>\n" +
            "<string>ca642ebf-36fb-4a48-a7da-a1ec71fff28e</string>\n" +
            "<key>QueryResponses</key>\n" +
            "<dict>\n" +
            "<key>AvailableDeviceCapacity</key>\n" +
            "<real>5.5006752014160156</real>\n" +
            "<key>BatteryLevel</key>\n" +
            "<real>1</real>\n" +
            "<key>BluetoothMAC</key>\n" +
            "<string>c0:63:94:c0:58:6a</string>\n" +
            "<key>BuildVersion</key>\n" +
            "<string>10B329</string>\n" +
            "<key>CarrierSettingsVersion</key>\n" +
            "<string>14.0</string>\n" +
            "<key>CellularTechnology</key>\n" +
            "<integer>1</integer>\n" +
            "<key>CurrentMCC</key>\n" +
            "<string>000</string>\n" +
            "<key>CurrentMNC</key>\n" +
            "<string>00</string>\n" +
            "<key>DataRoamingEnabled</key>\n" +
            "<false/>\n" +
            "<key>DeviceCapacity</key>\n" +
            "<real>6.3118057250976562</real>\n" +
            "<key>DeviceName</key>\n" +
            "<string>XXO</string>\n" +
            "<key>IMEI</key>\n" +
            "<string>01 328200 621761 5</string>\n" +
            "<key>IsRoaming</key>\n" +
            "<true/>\n" +
            "<key>Model</key>\n" +
            "<string>MD198CH</string>\n" +
            "<key>ModelName</key>\n" +
            "<string>iPhone</string>\n" +
            "<key>ModemFirmwareVersion</key>\n" +
            "<string>04.12.05</string>\n" +
            "<key>OSVersion</key>\n" +
            "<string>6.1.3</string>\n" +
            "<key>ProductName</key>\n" +
            "<string>iPhone3,2</string>\n" +
            "<key>SIMCarrierNetwork</key>\n" +
            "<string>中国联通</string>\n" +
            "<key>SIMMNC</key>\n" +
            "<string>00</string>\n" +
            "<key>SerialNumber</key>\n" +
            "<string>DX6KRFB5DPMW</string>\n" +
            "<key>SubscriberCarrierNetwork</key>\n" +
            "<string>中国联通</string>\n" +
            "<key>SubscriberMNC</key>\n" +
            "<string>00</string>\n" +
            "<key>UDID</key>\n" +
            "<string>34ce984eb2c6d94f152144590492bf6dcce804d1</string>\n" +
            "<key>WiFiMAC</key>\n" +
            "<string>c0:63:94:c0:58:6b</string>\n" +
            "</dict>\n" +
            "<key>Status</key>\n" +
            "<string>Acknowledged</string>\n" +
            "<key>UDID</key>\n" +
            "<string>34ce984eb2c6d94f152144590492bf6dcce804d1</string>\n" +
            "</dict>\n" +
            "</plist>";
}
