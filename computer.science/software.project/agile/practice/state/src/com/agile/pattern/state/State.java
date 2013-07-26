package com.agile.pattern.state;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-20
 * Time: 上午9:18
 * To change this template use File | Settings | File Templates.
 */
public interface State {
    public void coin(TurnStyle ts);
    public void pass(TurnStyle ts);
}
