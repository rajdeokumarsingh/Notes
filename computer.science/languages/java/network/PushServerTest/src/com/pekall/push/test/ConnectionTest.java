package com.pekall.push.test;

import java.net.URI;

public class ConnectionTest {

    public static void main(String args[]) {
        // int clientCnt = Integer.valueOf(args[0]);

        // begin monitor thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Statistics.getInstance().getStatistics());
                    try {
                        Thread.sleep(20 * 1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        int clientCnt = 1;
        int sockCnt = Integer.valueOf(args[0]);

        Statistics statistics = Statistics.getInstance();
        MdmPushClient[] mdmPushClients = new MdmPushClient[clientCnt];
        URI uri = URI.create(PushConstant.PUSH_URL);
        for (int i = 0; i < clientCnt; i++) {
            MdmPushClient mdmPushClient = new MdmPushClient(uri, sockCnt);
            mdmPushClients[i] = mdmPushClient;
        }

        for (MdmPushClient mdmPushClient : mdmPushClients) {
            mdmPushClient.connect();
            statistics.create();
        }

        System.out.println("main thread continue ...");

        try {
            Thread.sleep(30 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            System.out.println("begin ping ...");
            for (MdmPushClient mdmPushClient : mdmPushClients) {
                if (mdmPushClient.isOpen()) {
                    mdmPushClient.ping("ping");
                }
            }
            System.out.println("end ping ...");
            try {
                Thread.sleep(60 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
