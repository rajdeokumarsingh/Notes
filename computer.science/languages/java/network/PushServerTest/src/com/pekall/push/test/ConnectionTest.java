package com.pekall.push.test;

import java.net.URI;

public class ConnectionTest {
    /*
        java -jar PushServerTest.jar 50000 lan 1000000 ws://192.168.10.233:9090/websocket

        第一个参数表示 5w个连接
        第二个参数：lan 表示局域网， inet表示外网。 目前233还没有部署到外网中。
        第三个参数： 1000000 表示device id的开始范围。 建议测试组使用 1000,000开始的， 每台测试机器都不能够一样。
        比如一台使用1000000, 一台使用2000000, 一台使用3000000
        第四个参数： 自定义地址， 注意，第二个参数必须是lan时，本参数才有效。
    */
    public static void main(String args[]) {
        Debug.setVerboseDebugLog(true);

        // begin monitor thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Debug.log(Statistics.getInstance().getStatistics());
                    Util.sleepSeconds(20);
                }
            }
        }).start();

        int clientCnt = 1;
        int sockCnt = Integer.valueOf(args[0]);

        Statistics statistics = Statistics.getInstance();
        MdmPushClient[] mdmPushClients = new MdmPushClient[clientCnt];
        URI uri;
        if(args.length > 3) {
            PushConstant.PUSH_LAN_URL = args[3];
            Debug.log("using push url: " + PushConstant.PUSH_LAN_URL);
        }
        if(args.length > 1 && "inet".equals(args[1])) {
            Debug.log("using internet url: " + PushConstant.PUSH_INET_URL);
            uri = URI.create(PushConstant.PUSH_INET_URL);
        } else {
            Debug.log("using LAN url: " + PushConstant.PUSH_LAN_URL);
            uri = URI.create(PushConstant.PUSH_LAN_URL);
        }

        if(args.length > 2) {
            PushConstant.DEVICE_BEGIN_ID = Integer.valueOf(args[2]);
            Debug.log("device begin id: " + PushConstant.DEVICE_BEGIN_ID);
        }

        Util.sleepSeconds(2);

        for (int i = 0; i < clientCnt; i++) {
            MdmPushClient mdmPushClient = new MdmPushClient(uri, sockCnt);
            mdmPushClients[i] = mdmPushClient;
            mdmPushClients[i].connect();
            statistics.create();
        }

        Debug.log("main thread continue ...");
        Util.sleepSeconds(120);

        while (true) {
            Debug.log("begin ping ...");
            for (MdmPushClient mdmPushClient : mdmPushClients) {
                if (mdmPushClient.isOpen()) {
                    mdmPushClient.ping("ping");
                }
            }
            Debug.log("end ping ...");
            Util.sleepSeconds(60);
        }
    }
}
