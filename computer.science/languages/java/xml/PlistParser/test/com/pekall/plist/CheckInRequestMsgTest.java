package com.pekall.plist;

import com.pekall.plist.beans.BeanBase;
import com.pekall.plist.beans.CheckInRequestMsg;
import junit.framework.TestCase;

public class CheckInRequestMsgTest extends TestCase {

    private static final String TEST_AUTH_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>MessageType</key>\n" +
            "\t<string>Authenticate</string>\n" +
            "\t<key>Topic</key>\n" +
            "\t<string>test topic</string>\n" +
            "\t<key>UDID</key>\n" +
            "\t<string>xxxx-xxxx-xxxx-xxxx</string>\n" +
            "</dict>\n" +
            "</plist>";

    private static final String TEST_TOKEN_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>MessageType</key>\n" +
            "\t<string>TokenUpdate</string>\n" +
            "\t<key>Topic</key>\n" +
            "\t<string>test topic</string>\n" +
            "\t<key>UDID</key>\n" +
            "\t<string>xxxx-xxxx-xxxx-xxxx</string>\n" +
            "\t<key>Token</key>\n" +
            "\t<data>\n" +
            "\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwd\n" +
            "\t</data>\n" +
            "\t<key>PushMagic</key>\n" +
            "\t<string>test magic</string>\n" +
            "\t<key>UnlockToken</key>\n" +
            "\t<data>\n" +
            "\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwd\n" +
            "\t</data>\n" +
            "</dict>\n" +
            "</plist>";

    private static final String TEST_CHECK_OUT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>MessageType</key>\n" +
            "\t<string>CheckOut</string>\n" +
            "\t<key>Topic</key>\n" +
            "\t<string>test topic</string>\n" +
            "\t<key>UDID</key>\n" +
            "\t<string>xxxx-xxxx-xxxx-xxxx</string>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXml() throws Exception {
        CheckInRequestMsg auth = getAuthMsg();
        // PlistDebug.logTest(auth.toXml());
        assertEquals(auth.toXml(), TEST_AUTH_XML);

        CheckInRequestMsg tokenUpdate = getTokenUpdateMsg();
        // PlistDebug.logTest(tokenUpdate.toXml());
        assertEquals(tokenUpdate.toXml(), TEST_TOKEN_XML);

        CheckInRequestMsg checkOut = getCheckOutMsg();
        // PlistDebug.logTest(checkOut.toXml());
        assertEquals(checkOut.toXml(), TEST_CHECK_OUT_XML);
    }

    public void testParseXml() throws Exception {
        CheckInRequestMsg auth = BeanBase.fromXmlT(TEST_AUTH_XML, CheckInRequestMsg.class);
        // PlistDebug.logTest(auth.toString());
        assertEquals(auth, getAuthMsg());

        CheckInRequestMsg tokenUpdate = BeanBase.fromXmlT(TEST_TOKEN_XML, CheckInRequestMsg.class);
        // PlistDebug.logTest(tokenUpdate.toString());
        assertEquals(tokenUpdate, getTokenUpdateMsg());

        CheckInRequestMsg checkOut = BeanBase.fromXmlT(TEST_CHECK_OUT_XML, CheckInRequestMsg.class);
        // PlistDebug.logTest(checkOut.toString());
        assertEquals(checkOut, getCheckOutMsg());
    }

    private CheckInRequestMsg getTokenUpdateMsg() {
        byte[] data = new byte[30];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }

        CheckInRequestMsg msg = new CheckInRequestMsg(CheckInRequestMsg.TYPE_TOKEN_UPDATE,
                "test topic", "xxxx-xxxx-xxxx-xxxx");
        msg.setPushMagic("test magic");
        msg.setToken(data);
        msg.setUnlockToken(data);
        return msg;
    }

    private CheckInRequestMsg getAuthMsg() {
        return new CheckInRequestMsg(CheckInRequestMsg.TYPE_AUTH,
                "test topic", "xxxx-xxxx-xxxx-xxxx");
    }

    public CheckInRequestMsg getCheckOutMsg() {
        return new CheckInRequestMsg(CheckInRequestMsg.TYPE_CHECK_OUT,
                "test topic", "xxxx-xxxx-xxxx-xxxx");
    }
}
