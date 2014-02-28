package com.pekall.push.test;

public class ConnectionTest {

<<<<<<< HEAD
    public static void main(String args[]) {
        // Debug.setVerboseDebugLog(true);
=======
    private static final String USAGE =
            "java -jar PushServerTest.jar 50000 1000000 http://192.168.10.43:8080\n" +
            "        第一个参数： 连接数量\n" +
            "        第二个参数： 1000000 表示device id的开始范围。 建议测试组使用 1000,000开始的， 每台测试机器都不能够一样。\n" +
            "        比如一台使用1000000, 一台使用2000000, 一台使用3000000\n" +
            "        第三个参数： 查询服务器地址";
>>>>>>> d52295e16f9d15ba2363b0edc484657729e08558

    private static int SOCK_CNT = 0;

    /*
        java -jar PushServerTest.jar 50000 1000000 http://192.168.10.43:8080
        java -jar PushServerTest.jar 50 1000000 http://192.168.10.43:8080

        第一个参数： 5w个连接
        第二个参数： 1000000 表示device id的开始范围。 建议测试组使用 1000,000开始的， 每台测试机器都不能够一样。
        比如一台使用1000000, 一台使用2000000, 一台使用3000000
        第三个参数： 查询服务器地址
    */
    public static void main(String args[]) {
        // Uncomment following line for very verbose log
        // Debug.setVerboseDebugLog(true);

        processArgs(args);

        final MdmPushClient mdmPushClient =
                new MdmPushClient(PushConstant.PUSH_QUERY_ADDR, SOCK_CNT);
        // connect() will blocks until about 99% handshakes are done
        mdmPushClient.connect();

        Debug.log("main thread continue ...");
        startMonitor(mdmPushClient);

        Util.sleepSeconds(60);

        while (true) {
            Debug.log("begin ping ...");
            if (mdmPushClient.isOpen()) {
                mdmPushClient.ping("ping");
            }
            Debug.log("end ping ...");
            Util.sleepSeconds(60);
        }
    }

    private static void processArgs(String[] args) {
        if (args.length != 3) {
            Debug.log("Usage:");
            Debug.log(USAGE);
            System.exit(-1);
        }

        SOCK_CNT = Integer.valueOf(args[0]);

        PushConstant.DEVICE_BEGIN_ID = Integer.valueOf(args[1]);
        Debug.log("device begin id: " + PushConstant.DEVICE_BEGIN_ID);

        PushConstant.PUSH_QUERY_ADDR = args[2];
        Debug.log("query url: " + PushConstant.PUSH_QUERY_ADDR);
    }

    private static void startMonitor(final MdmPushClient mdmPushClient) {
        Statistics statistics = Statistics.getInstance();
        statistics.create();

        // begin monitor thread
        Thread monitor = new Thread(new Runnable() {
            @Override
            public void run() {
                Statistics.getInstance().setClient(mdmPushClient);
                while (true) {
                    Debug.log(Statistics.getInstance().getStatistics());
                    Util.sleepSeconds(20);
                }
            }
        });
        monitor.setName("Statistics");
        monitor.start();
    }
}
