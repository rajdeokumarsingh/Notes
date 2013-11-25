package com.java.examples.reflection.method;

import java.lang.reflect.Method;

public class GetFuncRuntimeInfo {


    void test(String str1, int str2) {
        StackTraceElement traceElement = Thread.currentThread().getStackTrace()[2];
        StringBuffer sb = new StringBuffer("[").append(traceElement.getFileName()).append(" | ")
                .append(traceElement.getMethodName() + "()").append("]");
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        new GetFuncRuntimeInfo().test("string 1", 2);
    }
}
