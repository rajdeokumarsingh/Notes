package com.pekall.push.test;
public class PushConstant {

    public final static int MESSAGE_ID_CONNECT = 0;
    public final static int PUSH_MESSAGE_SENT_TIME = 60 * 1000;

    public static long DEVICE_BEGIN_ID = -1;

    public final static String DEVICE_ID_PREFIX = "device_uuid_";
    public final static String DEVICE_TOKEN = "123456";

    public final static String WS_SCHEME = "ws://";
    public final static String WS_PATH = "/websocket";

    // default path
    public static String PUSH_QUERY_ADDR = "http://192.168.10.43:8080";
    public static String PUSH_WS_ADDR = null;

    //    public final static String PUSH_QUERY_PATH = "/PushServer/rest/push/assignServer";
    public final static String PUSH_QUERY_PATH = "/assignServer";
    public final static String PUSH_QUERY_PARAM = "?device_id=";
}
