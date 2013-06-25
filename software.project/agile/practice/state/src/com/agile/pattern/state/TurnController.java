package com.agile.pattern.state;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-20
 * Time: 上午9:51
 * To change this template use File | Settings | File Templates.
 */
public interface TurnController {
    public void lock();

    public void unlock();

    public void alarm();

    public void thank();

}
