package com.pekall.plist;

import com.pekall.plist.beans.IPSecInfo;
import com.pekall.plist.beans.PPPInfo;
import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadVPN;
import junit.framework.TestCase;

public class PayloadVPNTest extends TestCase {

   public void testGenXml() throws Exception {
        PayloadArrayWrapper profile = createProfile();
        String xml = profile.toXml();

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testParseXml() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        PlistDebug.logTest(profile.toString());

        assertEquals(profile, createProfile());
        assertEquals(parser.getVPN(), createVPN());
        assertTrue(ObjectComparator.equals(
                parser.getVPN(), createVPN()));
    }

    public void testTwoWay() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        assertEquals(TEST_XML, profile.toXml());
    }

    private PayloadArrayWrapper createProfile() {
        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(createVPN());
        return wrapper;
    }

    private PayloadVPN createVPN() {
        PayloadVPN vpn = new PayloadVPN();
        vpn.setPayloadDescription("VPN相关配置");
        vpn.setPayloadDisplayName("VPN配置");
        vpn.setPayloadIdentifier("com.pekall.profile.VPN");
        vpn.setPayloadOrganization("Pekall Capital");
        vpn.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        vpn.setPayloadVersion(1);

        vpn.setOverridePrimary(true);
        vpn.setUserDefinedName("test vpn");
        vpn.setVPNType(PayloadVPN.TYPE_L2TP);

        PPPInfo pppInfo = new PPPInfo();
        pppInfo.enableAuthEAPPlugins();
        pppInfo.enableAuthProtocol();
        pppInfo.setAuthName("my vpn");
        pppInfo.setAuthPassword("123456");
        pppInfo.setCCPEnabled(true);
        pppInfo.setCCPMPPE128Enabled(true);
        pppInfo.setCommRemoteAddress("test address");
        vpn.setPPP(pppInfo);

        IPSecInfo ipSecInfo = new IPSecInfo();
        ipSecInfo.setAuthenticationMethod("test method");
        ipSecInfo.setLocalIdentifier("my ipsec id");
        ipSecInfo.setLocalIdentifierType("key id 1003");
        ipSecInfo.setPromptForVPNPIN(true);
        ipSecInfo.setXAuthEnabled(1);
        byte[] data = new byte[10];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        ipSecInfo.setSharedSecret(data);
        vpn.setIPSec(ipSecInfo);

        vpn.setServerHostName("192.168.10.220");
        vpn.setAccount("ray");
        vpn.setPassword("123456");
        vpn.setSharedPassword("qwert");
        vpn.setUserAuth(PayloadVPN.USR_AUTH_PASSWORD);
        vpn.setVpnForAllTraffic(true);
        vpn.setProxyHost("192.168.0.1");
        vpn.setProxyPort(889);
        vpn.setProxyUserName("Ray");
        vpn.setProxyPassword("qwert");

        return vpn;
    }

    private PayloadArrayWrapper createWrapper() {
        PayloadArrayWrapper wrapper = new PayloadArrayWrapper();
        wrapper.setPayloadDescription("描述文件描述。wjl 测试");
        wrapper.setPayloadDisplayName("Pekall MDM Profile");
        wrapper.setPayloadIdentifier("com.pekall.profile");
        wrapper.setPayloadOrganization("Pekall Capital");
        wrapper.setPayloadType("Configuration");
        wrapper.setPayloadUUID("2ED160FF-4B6C-47DD-8105-769231367D2A");
        wrapper.setPayloadVersion(1);
        wrapper.setPayloadRemovalDisallowed(true);
        return wrapper;
    }

    private static final String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>PayloadContent</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>UserDefinedName</key>\n" +
            "\t\t\t<string>test vpn</string>\n" +
            "\t\t\t<key>OverridePrimary</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>VPNType</key>\n" +
            "\t\t\t<string>L2TP</string>\n" +
            "\t\t\t<key>PPP</key>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>AuthName</key>\n" +
            "\t\t\t\t<string>my vpn</string>\n" +
            "\t\t\t\t<key>AuthPassword</key>\n" +
            "\t\t\t\t<string>123456</string>\n" +
            "\t\t\t\t<key>CommRemoteAddress</key>\n" +
            "\t\t\t\t<string>test address</string>\n" +
            "\t\t\t\t<key>AuthEAPPlugins</key>\n" +
            "\t\t\t\t<array>\n" +
            "\t\t\t\t\t<string>EAP-RSA</string>\n" +
            "\t\t\t\t</array>\n" +
            "\t\t\t\t<key>AuthProtocol</key>\n" +
            "\t\t\t\t<array>\n" +
            "\t\t\t\t\t<string>EAP</string>\n" +
            "\t\t\t\t</array>\n" +
            "\t\t\t\t<key>CCPMPPE128Enabled</key>\n" +
            "\t\t\t\t<true/>\n" +
            "\t\t\t\t<key>CCPEnabled</key>\n" +
            "\t\t\t\t<true/>\n" +
            "\t\t\t</dict>\n" +
            "\t\t\t<key>IPSec</key>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>AuthenticationMethod</key>\n" +
            "\t\t\t\t<string>test method</string>\n" +
            "\t\t\t\t<key>XAuthEnabled</key>\n" +
            "\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t<key>LocalIdentifier</key>\n" +
            "\t\t\t\t<string>my ipsec id</string>\n" +
            "\t\t\t\t<key>LocalIdentifierType</key>\n" +
            "\t\t\t\t<string>key id 1003</string>\n" +
            "\t\t\t\t<key>SharedSecret</key>\n" +
            "\t\t\t\t<data>\n" +
            "\t\t\t\t\tAAECAwQFBgcICQ==\n" +
            "\t\t\t\t</data>\n" +
            "\t\t\t\t<key>PromptForVPNPIN</key>\n" +
            "\t\t\t\t<true/>\n" +
            "\t\t\t</dict>\n" +
            "\t\t\t<key>serverHostName</key>\n" +
            "\t\t\t<string>192.168.10.220</string>\n" +
            "\t\t\t<key>account</key>\n" +
            "\t\t\t<string>ray</string>\n" +
            "\t\t\t<key>userAuth</key>\n" +
            "\t\t\t<string>password</string>\n" +
            "\t\t\t<key>password</key>\n" +
            "\t\t\t<string>123456</string>\n" +
            "\t\t\t<key>sharedPassword</key>\n" +
            "\t\t\t<string>qwert</string>\n" +
            "\t\t\t<key>vpnForAllTraffic</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>proxyHost</key>\n" +
            "\t\t\t<string>192.168.0.1</string>\n" +
            "\t\t\t<key>proxyPort</key>\n" +
            "\t\t\t<integer>889</integer>\n" +
            "\t\t\t<key>proxyUserName</key>\n" +
            "\t\t\t<string>Ray</string>\n" +
            "\t\t\t<key>proxyPassword</key>\n" +
            "\t\t\t<string>qwert</string>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.apple.vpn.managed</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.profile.VPN</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>VPN配置</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>VPN相关配置</string>\n" +
            "\t\t\t<key>PayloadOrganization</key>\n" +
            "\t\t\t<string>Pekall Capital</string>\n" +
            "\t\t</dict>\n" +
            "\t</array>\n" +
            "\t<key>PayloadRemovalDisallowed</key>\n" +
            "\t<true/>\n" +
            "\t<key>PayloadType</key>\n" +
            "\t<string>Configuration</string>\n" +
            "\t<key>PayloadVersion</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>PayloadIdentifier</key>\n" +
            "\t<string>com.pekall.profile</string>\n" +
            "\t<key>PayloadUUID</key>\n" +
            "\t<string>2ED160FF-4B6C-47DD-8105-769231367D2A</string>\n" +
            "\t<key>PayloadDisplayName</key>\n" +
            "\t<string>Pekall MDM Profile</string>\n" +
            "\t<key>PayloadDescription</key>\n" +
            "\t<string>描述文件描述。wjl 测试</string>\n" +
            "\t<key>PayloadOrganization</key>\n" +
            "\t<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "</plist>";

}
