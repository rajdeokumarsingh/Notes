package com.pekall.push.test;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class MdmPushClient extends WebSocketClient {

    private static final String TAG = "MdmPushClient";


    public MdmPushClient(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("WEBSOCKET OPEN");
        Statistics.getInstance().success();
//        if (mHandler != null) {
//            mHandler.sendEmptyMessage(MdmService.MSG_WEBSOCKET_OPEN);
//        }
    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
        send(PushMessageManager.genShakeHandMessage().toJson());
//        if (mHandler != null) {
//            mHandler.sendMessage(Message.obtain(mHandler, MdmService.MSG_WEBSOCKET_MESSAGE, message));
//        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Close"+code);
        Statistics.getInstance().fail();
//        if (mHandler != null) {
//            mHandler.sendEmptyMessage(MdmService.MSG_WEBSOCKET_CLOSE);
//        }
    }

    @Override
    public void onError(Exception ex) {
        if(ex != null){
            System.out.println("Error"+ex.getMessage());
        }

//        if (mHandler != null) {
//            mHandler.sendEmptyMessage(MdmService.MSG_WEBSOCKET_ERROR);
//        }
    }

    @Override
    public void onWebsocketPong(WebSocket conn, Framedata f) {
        System.out.println("ON PONG");
//        if (mHandler != null) {
//            mHandler.sendEmptyMessage(MdmService.MSG_WEBSOCKET_PONG);
//        }
    }


    public boolean isOpen() {
        WebSocket conn = getConnection();
        return ((conn != null) && conn.isOpen());
    }

}
