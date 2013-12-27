package com.pekall.push.test;
public class PushConstant {

    public final static int MESSAGE_ID_CONNECT = 0;
    public final static int PUSH_MESSAGE_SENT_TIME = 60 * 1000;

    // Xiao Ke
    // public final static String PUSH_LAN_URL = "ws://192.168.8.79:9090/websocket";
    public static String PUSH_LAN_URL = "ws://192.168.10.233:9090/websocket";
    public static String PUSH_INET_URL = "ws://119.161.242.247:9090/websocket";

    public static long DEVICE_BEGIN_ID = -1;

    public final static String DEVICE_ID_PREFIX = "device_uuid_";
    public final static String DEVICE_TOKEN = "123456";
}
