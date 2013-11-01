package com.pekall.push.test;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClientTest;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class MdmPushClient extends WebSocketClientTest {

    public MdmPushClient(URI serverURI) {
        super(serverURI, 1);
    }

    public MdmPushClient(URI serverURI, int sockCnt) {
        super(serverURI, sockCnt);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        // System.out.println("websocket open");
        Statistics.getInstance().success();
    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
        send(PushMessageManager.genShakeHandMessage().toJson());
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("websocket close: " + code + ", reason: " + reason + ", remote: " + remote);
        Statistics.getInstance().fail();
    }

    @Override
    public void onError(Exception ex) {

        if(ex != null){
            System.out.println("Error: "+ex.getMessage());
            // ex.printStackTrace();
            Statistics.getInstance().error();
        }
    }

    @Override
    public void onWebsocketPong(WebSocket conn, Framedata f) {
        Statistics.getInstance().pong();
    }

    @Override
    public void ping(String text) {
        // do ping statistics in super
        super.ping(text);
    }
}
