package autotest;

import java.util.Iterator;

import testcase3_1.LoginCase;
import testcase3_2.TestPushServer;
import util.Util;

public class AutoTest {
    private final static String TEST_HOST = "http://192.168.10.234:3342/";
    //private static String PUSH_IP = "http://119.161.242.248:3342/";

    public static void main(String args[]) {
        Util.mSwitch = 0;
        testCase(TEST_HOST);
    }

    public static void testCase(String host) {
        log("TEST push命令");
        String devUuidPrefix = "device_uuid_";
        while (true) {
            for (int id = 10000; id < 20000; id++) {
                new TestPushServer(devUuidPrefix + id, "test push").test();
                Util.sleepSeconds(1);
            }
        }
    }

    public static void log(String Mes) {
        Util.log(Mes);
    }
}
