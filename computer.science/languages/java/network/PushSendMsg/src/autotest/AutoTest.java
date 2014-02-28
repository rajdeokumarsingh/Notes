package autotest;

import java.util.Iterator;

import testcase3_1.LoginCase;
import testcase3_2.TestPushServer;
import util.Util;

public class AutoTest {
    // private final static String TEST_HOST = "http://192.168.10.233:3342/";
    //private static String PUSH_IP = "http://119.161.242.248:3342/";

    public static void main(String args[]) {
        Util.mSwitch = 0;
        testCase();
    }

    public static void testCase() {
        log("TEST push命令");
        String devUuidPrefix = "device_uuid_";
        while (true) {
            for (int id = 100000; id < 150000; id++) {
            // for (int id = 100000; id <= 100000; id++) {
                System.out.println("send message: " + devUuidPrefix + id);
                new TestPushServer(devUuidPrefix + id, "test push").test();
                Util.sleepSeconds(3);
            }
        }
    }

    public static void log(String Mes) {
        Util.log(Mes);
    }
}
