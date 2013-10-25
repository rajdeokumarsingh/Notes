package com.pekall.push.test;
public class PushCommandMsg {

    String cmd;
    String msg;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "PushCommandMsg{" +
                "cmd='" + cmd + '\'' +
                '}';
    }
}
