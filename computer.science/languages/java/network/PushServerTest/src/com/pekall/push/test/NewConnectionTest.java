package com.pekall.push.test;

import java.net.URI;

public class NewConnectionTest {

    public static void main(String[] args) throws Exception {
        URI uri;
        if (args.length > 0) {
            uri = new URI(args[0]);
        } else {
            uri = new URI("ws://192.168.10.234:9090/websocket");
        }
        new NWebSocketClient(uri).run();
    }

}
