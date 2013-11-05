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
        Debug.logVerbose("MdmPushClient, created");
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        Debug.logVerbose("MdmPushClient, onOpen");
        Statistics.getInstance().success();
    }

    @Override
    public void onMessage(String message) {
        Debug.logVerbose("MdmPushClient, got message: " + message);
        Statistics.getInstance().message();
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Debug.log("MdmPushClient, websocket close: " + code + ", reason: " + reason + ", remote: " + remote);
        Statistics.getInstance().fail();
    }

    @Override
    public void onError(Exception ex) {
        Statistics.getInstance().error();
        if(ex != null) {
            Debug.log("Error: " + ex.getMessage());
            ex.printStackTrace();
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
