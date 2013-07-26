package com.agile.pattern.state;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-20
 * Time: 上午9:19
 * To change this template use File | Settings | File Templates.
 */
public class TurnStyle {
    private final TurnController controller;
    private State currentState;

    static final State lockState = new StateLock();
    static final State unlockState = new StateUnlock();

    public TurnStyle(TurnController tc) {
        this.controller = tc;
        this.currentState = lockState;
    }

    public void coin() {
        currentState.coin(this);
    }

    public void pass() {
        currentState.pass(this);
    }

    void setLockState() {
        currentState = lockState;
    }

    void setUnlockState() {
        currentState = unlockState;
    }

    void lock() {
        controller.lock();
    }

    void unlock() {
        controller.unlock();
    }

    void alarm() {
        controller.alarm();
    }

    void thank() {
        controller.thank();
    }
}
