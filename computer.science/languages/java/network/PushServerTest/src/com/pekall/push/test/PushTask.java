package com.pekall.push.test;

import com.google.gson.Gson;

import java.net.URI;

public class PushTask {

    private MdmPushClient mMdmPushClient;
    private Gson mGson = new Gson();

    public PushTask(URI uri) {
        this.mMdmPushClient = new MdmPushClient(uri);
    }

    private void keepAliveWithPushServer() {
        if (mMdmPushClient != null && mMdmPushClient.isOpen()) {
            try {
                mMdmPushClient.ping("ping");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String pushUrl = PushConstant.PUSH_URL;
        connectPushServer(pushUrl);
    }

    private void connectPushServer(String pushUrl) {
        if (mMdmPushClient != null && mMdmPushClient.isOpen()) {
            return;
        }

        if (mMdmPushClient == null || !mMdmPushClient.isOpen()) {
            mMdmPushClient = new MdmPushClient(URI.create(pushUrl));
        }

        try {
            mMdmPushClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseMessage(String message) {
        PushMessage pushMessage = PushMessage.fromJson(message);

        if (pushMessage == null) {
            System.out.println("push message is null");
            return;
        }
        System.out.println("push message type :" + pushMessage.getType());
        switch (pushMessage.getType()) {
            case PushMessage.MsgType.TYPE_SEND:
                String cmdMessage = pushMessage.getParams().getMessage();
                try {
                    String deviceId = pushMessage.getParams().getDevice();
//                    if (!TextUtils.equals(deviceId, Configuration.getDeviceUuid())) {
//                        break;
//                    }
                    PushCommandMsg command = mGson.fromJson(cmdMessage, PushCommandMsg.class);
                    System.out.println("command : " + command.toString());
                    String cmd = command.getCmd();
                    int CMD_NOTIFY = 10119;
                    if (cmd.equals(CMD_NOTIFY + "")) {
//                        checkInWithMdmServer();
                    }
//                    if (TextUtils.equals(StateAndErrorCode.CMD_NOTIFY + "", cmd)) {
//                        checkInWithMdmServer();
//                    }
                } catch (Exception e) {
//                    Intent intent = new Intent(ApplicationReceiver.ACTION_REC_PUSH_MESSAGE);
//                    intent.putExtra("msg", cmdMessage);
//                    sendBroadcast(intent);
                }

                PushMessage respMsg = PushMessageManager.createResponseMessage(pushMessage.getId());
                sendStringToPush(respMsg);
                break;
            case PushMessage.MsgType.TYPE_RECEIVE:
                PushMessageManager.getInstance().removeSentMessage(pushMessage.getId());
                break;
            case PushMessage.MsgType.TYPE_ERROR:
                if (pushMessage.getReason() == PushMessage.ResultCode.CODE_PERMISSION_FOBID) {
//                    mHandler.sendEmptyMessage(MSG_PUSHTOKEN_EXPIRED);
                break;
            }
        }
    }

    private void sendStringToPush(final PushMessage message) {
//        synchronized (mExecutorService) {
//            mExecutorService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    if (mMdmPushClient != null && mMdmPushClient.isOpen()) {
//                        if (message.getType() == TYPE_SEND) {
//                            PushMessageManager.getInstance().addSentMessage(message);
//                        }
//                        mMdmPushClient.send(message.toJson());
//                    }
//                }
//            });
//        }
    }

    private void shakeHandWithPushServer() {
        sendStringToPush(PushMessageManager.genShakeHandMessage());
    }
}
