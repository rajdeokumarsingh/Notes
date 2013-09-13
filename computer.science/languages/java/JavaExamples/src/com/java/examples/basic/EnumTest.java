package com.java.examples.basic;

class NetworkInfo {
    enum State {
        CONNECTING, CONNECTED, SUSPENDED, DISCONNECTING, DISCONNECTED, UNKNOWN
    }

    private State mState;

    State getState() {
        return mState;
    }

    void setState(State mState) {
        this.mState = mState;
    }

    NetworkInfo() {
        this.mState = State.DISCONNECTED;
    }
}

public class EnumTest {
    public static void main(String[] args) {
        NetworkInfo info = new NetworkInfo();
        System.out.println("enum equals: " + (info.getState() == NetworkInfo.State.DISCONNECTED));
        System.out.println("enum equals: " + (info.getState() == NetworkInfo.State.CONNECTED));
    }
}
