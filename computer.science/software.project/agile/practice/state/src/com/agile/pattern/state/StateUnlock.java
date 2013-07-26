package com.agile.pattern.state;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-20
 * Time: 上午10:01
 * To change this template use File | Settings | File Templates.
 */
public class StateUnlock implements State {
    @Override
    public void coin(TurnStyle ts) {
        ts.thank();
    }

    @Override
    public void pass(TurnStyle ts) {
        ts.lock();
        ts.setLockState();
    }
}
