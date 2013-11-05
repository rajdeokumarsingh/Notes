package com.java.examples.net.turtorial;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class T011UrlReader {
    public static void main(String[] args) throws Exception {

        URL baidu = new URL("http://www.baidu.com/");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(baidu.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
}
