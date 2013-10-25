package com.pekall.push.test;

import java.net.URI;

public class ConnectionTask implements Runnable {

    private Statistics statistics;

    public ConnectionTask(Statistics statistics) {
        this.statistics = statistics;
    }

    @Override
    public void run() {
        URI uri = URI.create(PushConstant.PUSH_URL);
        MdmPushClient mdmPushClient = new MdmPushClient(uri);
        synchronized (mdmPushClient) {
            try {
                mdmPushClient.connect();
                statistics.create();
                System.out.println("" + mdmPushClient.isOpen());
                mdmPushClient.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        statistics.success();
        while (mdmPushClient.isOpen()) {
            mdmPushClient.ping("ping");
            try {
                Thread.sleep(60 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        statistics.fail();
    }
}
