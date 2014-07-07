package com.java.examples.system;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MacAddressTest {
    public static void main(String[] args) {
    }

    /* todo:
    With Java 6+, you can use NetworkInterface.getHardwareAddress.
    Bear in mind that a computer can have no network cards, especially if it's embedded
     or virtual. It can also have more than one. You can get a list of all network cards
     with NetworkInterface.getNetworkInterfaces().
     */

//    public String getMacAddress(String host)
//    {
//        String mac = "";
//        StringBuffer sb = new StringBuffer();
//
//        try
//        {
//            NetworkInterface InetAddress;
//            // todo:
//            //NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress.getByName(host));
//
//            byte[] macs = ni.getHardwareAddress();
//
//            for(int i=0; i<macs.length; i++)
//            {
//                mac = Integer.toHexString(macs[i] & 0xFF);
//
//                if (mac.length() == 1)
//                {
//                    mac = '0' + mac;
//                }
//
//                sb.append(mac + "-");
//            }
//
//        } catch (SocketException e) {
//            e.printStackTrace();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//
//        mac = sb.toString();
//        mac = mac.substring(0, mac.length()-1);
//
//        return mac;
//    }
}
