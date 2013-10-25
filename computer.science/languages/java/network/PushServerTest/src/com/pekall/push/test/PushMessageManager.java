package com.pekall.push.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PushMessageManager {

    private static final String TAG = "PushMessageManager";

    private static final HashMap<PushMessage, Long> sSentMessage = new HashMap<PushMessage, Long>();

    private PushMessageCheckThread mPushMessageCheckThread;

    private static PushMessageManager sInstance;

    private PushMessageManager() {
    }

    public static PushMessageManager getInstance() {
        if (sInstance == null) {
            sInstance = new PushMessageManager();
        }
        return sInstance;
    }

    public static PushMessage genShakeHandMessage() {
        PushMessage pushMessage = new PushMessage();
        pushMessage.setId(PushConstant.MESSAGE_ID_CONNECT);
        pushMessage.setCommand(PushMessage.Command.CONNECT);
        PushMessage.Params params = new PushMessage.Params();
//        params.setDevice(Configuration.getDeviceUuid());
//        params.setToken(Configuration.getPushToken());
        Random random = new Random(100);
        params.setDevice("" + System.currentTimeMillis() + random.nextLong());
        params.setToken("");
        pushMessage.setParams(params);
        return pushMessage;
    }

    public static PushMessage createResponseMessage(long messageId) {
        PushMessage pushMessage = new PushMessage();
        pushMessage.setId(messageId);
        pushMessage.setType(PushMessage.MsgType.TYPE_RECEIVE);
        pushMessage.setResult(PushMessage.MsgResult.RESULT_SUCCESS);
        return pushMessage;
    }

    public void addSentMessage(PushMessage message) {
        synchronized (sSentMessage) {
            System.out.println("add sent message : " + message.toJson());
            sSentMessage.put(message, System.currentTimeMillis());
            if (sSentMessage.size() == 1 && mPushMessageCheckThread == null) {
                mPushMessageCheckThread = new PushMessageCheckThread();
                mPushMessageCheckThread.start();
            }
            sSentMessage.notifyAll();
        }
    }

    public void removeSentMessage(long messageId) {
        synchronized (sSentMessage) {
            System.out.println("remove sent message : " + messageId);
            for (Map.Entry<PushMessage, Long> entry : sSentMessage.entrySet()) {
                System.out.println("sent message :" + entry.getKey().toJson());
                if (entry.getKey().getId() == messageId) {
                    System.out.println("has message : " + messageId);
                    sSentMessage.remove(entry.getKey());
                    break;
                }
            }

            if (sSentMessage.isEmpty()) {
                if (mPushMessageCheckThread != null) {
                    mPushMessageCheckThread.quit();
                } else {
                }
            } else {
                System.out.println("sSentMessage not empty : " + sSentMessage.size());
            }
        }
    }

    public void clearAllSentMessage() {
        synchronized (sSentMessage) {
            sSentMessage.clear();
            if (sSentMessage.isEmpty() && mPushMessageCheckThread != null) {
                System.out.println("set message is empty, quit the thread");
                mPushMessageCheckThread.quit();
            }
        }
    }

    class PushMessageCheckThread extends Thread {

        boolean mKeepRunning;

        @Override
        public synchronized void start() {
            mKeepRunning = true;
            super.start();
        }

        public synchronized void quit() {
            mKeepRunning = false;
        }

        public synchronized boolean isRunning() {
            return mKeepRunning;
        }

        @Override
        public void run() {
            while (mKeepRunning) {
                synchronized (sSentMessage) {
                    Map<PushMessage, Long> copy = (Map<PushMessage, Long>) sSentMessage.clone();
                    for (Map.Entry<PushMessage, Long> entry : copy.entrySet()) {
                        long sentTime = entry.getValue();
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - sentTime > PushConstant.PUSH_MESSAGE_SENT_TIME) {
                            PushMessage pushMessage = entry.getKey();
                            if (pushMessage.getId() == PushConstant.MESSAGE_ID_CONNECT) {
                                System.out.println("connect push server timeout, reconnect again and remove the connect message");
                                sSentMessage.remove(pushMessage);
                            } else {
                                System.out.println("send message timeout, resend the push message");
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(PushConstant.PUSH_MESSAGE_SENT_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
