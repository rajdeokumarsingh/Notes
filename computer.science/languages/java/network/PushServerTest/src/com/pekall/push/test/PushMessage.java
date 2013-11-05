package com.pekall.push.test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class PushMessage implements Serializable {
    private static final long serialVersionUID = -6011241820070393952L;
    @Expose
    private long id;
    @Expose
    private int type = MsgType.TYPE_SEND;
    @Expose
    private int command;
    @Expose
    private int result;
    @Expose
    private String comment;
    @Expose
    private int reason;
    @Expose
    private Params params;

    public static class Command {
        public final static int CONNECT = 0;
        public final static int DISCONNECT = 1;
        public final static int SUBSCRIBE = 2;
        public final static int UNSUBSCRIBE = 3;
        public final static int OPENCHANNEL = 4;
        public final static int CLOSECHANNECL = 5;
        public final static int NOTIFY = 6;
        public final static int LOGIN = 7;
        public final static int PUBLISH = 8;
        public final static int PATTERNPU = 9;
        public final static int EXPRESS = 10;
    }

    public static class MsgType {
        public final static int TYPE_SEND = 0;
        public final static int TYPE_RECEIVE = 1;
        public final static int TYPE_ERROR = 2;
    }

    public static class MsgResult {
        public final static int RESULT_SUCCESS = 0;
        public final static int RESULT_FAILED = 1;
    }

    public static class ResultCode {
        public final static int CODE_SUCCESS = 0;

        public final static int CODE_UNKNOWN = 1;

        public final static int CODE_PERMISSION_FOBID = 2;

        public final static int CODE_OPENCHANNEL_MAX = 3;
    }

    public static class Params implements Serializable {
        private static final long serialVersionUID = -6011241820070393952L;
        @Expose
        private String device;
        @Expose
        private String channel;
        @Expose
        private String message;
        @Expose
        private String expire;
        @Expose
        private String token;

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getExpire() {
            return expire;
        }

        public void setExpire(String expire) {
            this.expire = expire;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static PushMessage fromJson(String msg) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        PushMessage client = gson.fromJson(msg, PushMessage.class);
        return client;
    }

    public String toJson() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        String json = gson.toJson(this);
        return json;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PushMessage) {
            PushMessage p = (PushMessage) obj;
            return this.id == p.id;
        }
        return false;
    }

    @Override
    public String toString() {
        return "PushMessage{" +
                "id=" + id +
                ", type=" + type +
                ", command=" + command +
                ", result=" + result +
                ", comment='" + comment + '\'' +
                ", reason=" + reason +
                ", params=" + params +
                '}';
    }
}

