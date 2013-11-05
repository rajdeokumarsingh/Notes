package com.java.examples.net.turtorial;

import java.io.*;
import java.net.*;

public class T41MulticastClient {

    public static void main(String[] args) throws IOException {

        MulticastSocket socket = new MulticastSocket(4446);
        InetAddress address = InetAddress.getByName("230.0.113.0");
        socket.joinGroup(address);

        DatagramPacket packet;

        // get a few quotes
        for (int i = 0; i < 5; i++) {

            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Quote of the Moment: " + received);
        }

        socket.leaveGroup(address);
        socket.close();
    }

}
