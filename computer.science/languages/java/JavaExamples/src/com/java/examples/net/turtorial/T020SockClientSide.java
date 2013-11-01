package com.java.examples.net.turtorial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 1. Open a socket.
 * 2. Open an input stream and output stream to the socket.
 * 3. Read from and write to the stream according to the server's protocol.
 * 4. Close the streams. Close the socket.
 */
public class T020SockClientSide {
    public static void main(String[] args) throws IOException {
        connectTimeService();
        connectEchoService();
    }

    private static void connectEchoService() throws IOException {
        Socket echoSocket = null;
        PrintWriter sockOut = null;
        BufferedReader sockIn = null;

        try {
            // echoSocket = new Socket("192.168.10.82", 7);
            echoSocket = new Socket("localhost", 7);  // make ECHO service is installed and opened
            sockOut = new PrintWriter(echoSocket.getOutputStream(), true);
            sockIn = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: taranis.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        String userInput;

        System.out.println("Please input a string: ");
        while ((userInput = stdIn.readLine()) != null) {
            sockOut.println(userInput);
            System.out.println("From ECHO service: " + sockIn.readLine());
        }

        sockOut.close();
        sockIn.close();
        stdIn.close();
        echoSocket.close();
    }

    private static void connectTimeService() throws IOException {
        Socket timeSocket = null;
        BufferedReader sockIn = null;

        try {
            timeSocket = new Socket("localhost", 13);  // make TIME service is installed and opened
            sockIn = new BufferedReader(new InputStreamReader(
                    timeSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: taranis.");
            System.exit(1);
        }

        String timeString;
        while ((timeString = sockIn.readLine()) != null) {
            System.out.println("get time from local TIME service: " + timeString);
        }

        sockIn.close();
        timeSocket.close();
    }
}
