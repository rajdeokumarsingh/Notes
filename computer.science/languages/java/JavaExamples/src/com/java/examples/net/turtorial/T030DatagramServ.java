package com.java.examples.net.turtorial;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

/**
 * Test it with:
 * nc -u 192.168.10.82 4445
 */
public class T030DatagramServ {
    public static void main(String[] args) throws IOException {
        new QuoteServerThread().start();
    }
}

class QuoteServerThread extends Thread {

    protected DatagramSocket servSock = null;
    protected BufferedReader fileBufferReader = null;
    protected boolean moreQuotes = true;

    public QuoteServerThread() throws IOException {
        this("QuoteServerThread");
    }

    public QuoteServerThread(String name) throws IOException {
        super(name);
        servSock = new DatagramSocket(4445);

        try {
            fileBufferReader = new BufferedReader(new FileReader("/tmp/one-liners.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open quote file. Serving time instead.");
        }
    }

    public void run() {

        while (moreQuotes) {
            try {
                byte[] buf = new byte[256];

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                servSock.receive(packet);

                // figure out response
                String dString = null;
                fileBufferReader = null; // todo: just for test, close it
                if (fileBufferReader == null)
                    dString = new Date().toString();
                else
                    dString = getNextQuote();

                buf = dString.getBytes();

                // send the response to the client at "address" and "port"
                packet = new DatagramPacket(buf, buf.length,
                        packet.getAddress(), packet.getPort());
                servSock.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
                moreQuotes = false;
            }
        }
        servSock.close();
    }

    protected String getNextQuote() {
        String returnValue = null;
        try {
            if ((returnValue = fileBufferReader.readLine()) == null) {
                fileBufferReader.close();
                moreQuotes = false;
                returnValue = "No more quotes. Goodbye.";
            }
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        }
        return returnValue;
    }
}
