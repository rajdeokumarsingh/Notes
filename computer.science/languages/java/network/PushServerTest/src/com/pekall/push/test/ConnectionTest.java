package com.pekall.push.test;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionTest {


    public static void main(String args[]) {
        int count = Integer.valueOf(args[0]);
//        int count = 30000;
        Statistics statistics = Statistics.getInstance();
        MdmPushClient[] mdmPushClients = new MdmPushClient[count];
        URI uri = URI.create(PushConstant.PUSH_URL);
        for (int i = 0; i < count; i++) {
            MdmPushClient mdmPushClient = new MdmPushClient(uri);
            mdmPushClients[i] = mdmPushClient;
        }
        for (MdmPushClient mdmPushClient : mdmPushClients) {
            mdmPushClient.connect();
            statistics.create();
            System.out.println("" + mdmPushClient.isOpen());
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Statistics.getInstance().getStatistics());
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        while (true) {
            for (MdmPushClient mdmPushClient : mdmPushClients) {
                if (mdmPushClient.isOpen()) {
                    mdmPushClient.ping("ping");
                }
            }
            try {
                Thread.sleep(60 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
