package com.java.examples.net.turtorial;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Date;

public class T040MuticastServer {
    public static void main(String[] args) throws java.io.IOException {
        new MulticastServerThread().start();
    }
}

class MulticastServerThread extends QuoteServerThread {

    private long FIVE_SECONDS = 5000;

    public MulticastServerThread() throws IOException {
        super("MulticastServerThread");
    }

    public void run() {
        while (moreQuotes) {
            try {
                // byte[] buf = new byte[256];

                // construct quote
                String dString = null;
                if (fileBufferReader == null)
                    dString = new Date().toString();
                else
                    dString = getNextQuote();
                byte[] buf = dString.getBytes();

                // send it
                InetAddress group = InetAddress.getByName("230.0.113.0");
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
                servSock.send(packet);

                // sleep for a while
                try {
                    sleep((long)(Math.random() * FIVE_SECONDS));
                } catch (InterruptedException e) { }
            } catch (IOException e) {
                e.printStackTrace();
                moreQuotes = false;
            }
        }
        servSock.close();
    }
}
