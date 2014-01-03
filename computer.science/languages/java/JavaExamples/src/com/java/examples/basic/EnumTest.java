package com.java.examples.basic;

class NetworkInfo {
    private State mState;

    NetworkInfo() {
        this.mState = State.DISCONNECTED;
    }

    State getState() {
        return mState;
    }

    void setState(State mState) {
        this.mState = mState;
    }

    enum State {
        CONNECTING, CONNECTED, SUSPENDED, DISCONNECTING, DISCONNECTED, UNKNOWN
    }
}

public class EnumTest {
    public static void main(String[] args) {
        NetworkInfo info = new NetworkInfo();
        System.out.println("enum equals: " + (info.getState() == NetworkInfo.State.DISCONNECTED));
        System.out.println("enum equals: " + (info.getState() == NetworkInfo.State.CONNECTED));
    }
}

class Circle {

    private double radius;

    public double area() {
        return MathConstants.PI * radius * radius;
    }
}
