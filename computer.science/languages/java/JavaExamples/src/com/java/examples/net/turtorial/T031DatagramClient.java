package com.java.examples.net.turtorial;

import java.io.*;
import java.net.*;

/**
 * 1. run T030DatagramServ
 * 2. run self, make sure ip is correct
 */
public class T031DatagramClient {

    public static final String HOST_IP = "192.168.10.82";

    public static void main(String[] args) throws IOException {

        // get a datagram socket
        DatagramSocket socket = new DatagramSocket();

        // send request
        byte[] buf = new byte[256];
        InetAddress address = InetAddress.getByName(HOST_IP);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);

        // get response
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        // display response
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Quote of the Moment: " + received);

        socket.close();
    }
}
