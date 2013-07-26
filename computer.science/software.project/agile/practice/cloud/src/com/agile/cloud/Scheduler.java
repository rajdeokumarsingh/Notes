package com.agile.cloud;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-18
 * Time: 下午6:29
 * To change this template use File | Settings | File Templates.
 */
public class Scheduler {
    MonitorScreen monitor;
    TempSensor tempSensor;
    PressureSensor presSensor;

    void tic() {
        monitor.displayTemp(tempSensor.read());
        monitor.displayTemp(presSensor.read());
    }
}
