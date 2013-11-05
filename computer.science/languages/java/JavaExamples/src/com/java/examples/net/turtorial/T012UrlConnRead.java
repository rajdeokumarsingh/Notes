package com.java.examples.net.turtorial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class T012UrlConnRead {
    public static void main(String[] args) {

        URL baidu = null;
        try {
            // pekall = new URL("http://www.pekall.com");
            baidu = new URL("http://www.baidu.com");
            URLConnection connection = baidu.openConnection();
            connection.connect();

            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String inputLine;
            while ((inputLine = br.readLine()) != null)
                System.out.println(inputLine);
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
