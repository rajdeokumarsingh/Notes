package com.agile.payroll;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午5:26
 * To change this template use File | Settings | File Templates.
 */
public class Debug {
    static boolean debugDB = false;

    static public boolean isDebugDB() {
        return debugDB;
    }

    static public void setDebugDB(boolean debug) {
        debugDB = debug;
    }

    static public void LogDB(String message) {
        if(!debugDB) return;

        System.out.println(message);
    }
}
