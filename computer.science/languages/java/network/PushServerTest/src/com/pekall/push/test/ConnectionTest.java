package com.pekall.push.test;

import java.net.URI;

public class ConnectionTest {

    public static void main(String args[]) {
        // Debug.setVerboseDebugLog(true);

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
