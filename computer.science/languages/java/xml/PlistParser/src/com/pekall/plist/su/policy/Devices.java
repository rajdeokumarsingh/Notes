/*---------------------------------------------------------------------------------------------
 *                       Copyright (c) 2013 Capital Alliance Software(Pekall) 
 *                                    All Rights Reserved
 *    NOTICE: All information contained herein is, and remains the property of Pekall and
 *      its suppliers,if any. The intellectual and technical concepts contained herein are
 *      proprietary to Pekall and its suppliers and may be covered by P.R.C, U.S. and Foreign
 *      Patents, patents in process, and are protected by trade secret or copyright law.
 *      Dissemination of this information or reproduction of this material is strictly 
 *      forbidden unless prior written permission is obtained from Pekall.
 *                                     www.pekall.com
 *--------------------------------------------------------------------------------------------- 
*/

package com.pekall.plist.su.policy;


/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 7/17/13
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Devices {
    /**
     * Whether wifi is on, 0 for off, 1 for on
     */
    private int wifi;
    /**
     * Whether bluetooth is on, 0 for off, 1 for on
     */
    private int bluetooth;
    /**
     * Whether camera is enable, 0 for disable, 1 for enable
     */
    private int camera;
    /**
     * Whether microphone is on, 0 for off, 1 for on
     */
    private int microphone;
    /**
     * Whether nfc is on, 0 for off, 1 for on
     */
    private int nfc;
    /**
     * Whether data_roaming is on, 0 for off, 1 for on
     */
    private int data_roaming;
    /**
     * Whether sdcard is mounted, 0 for off, 1 for on
     */
    private int sdcard;
    /**
     * Whether usb is on, 0 for off, 1 for on
     */
    private int usb;
    /**
     * Whether airplane is on, 0 for off, 1 for on
     */
    private int airplane;
    /**
     * Whether mobile_data is on, 0 for off, 1 for on
     */
    private int mobile_data;

    public Devices() {
        this(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1);
    }

    public Devices(int wifi, int bluetooth, int camera, int microphone,
                          int nfc, int data_roaming, int sdcard, int usb,
                          int airplane, int mobile_data) {
        this.wifi = wifi;
        this.bluetooth = bluetooth;
        this.camera = camera;
        this.microphone = microphone;
        this.nfc = nfc;
        this.data_roaming = data_roaming;
        this.sdcard = sdcard;
        this.usb = usb;
        this.airplane = airplane;
        this.mobile_data = mobile_data;
    }

    @Override
    public String toString() {
        return "SystemSettings{" +
                "wifi=" + wifi +
                ", bluetooth=" + bluetooth +
                ", camera=" + camera +
                ", microphone=" + microphone +
                ", nfc=" + nfc +
                ", data_roaming=" + data_roaming +
                ", sdcard=" + sdcard +
                ", usb=" + usb +
                ", airplane=" + airplane +
                ", mobile_data=" + mobile_data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Devices)) return false;

        Devices that = (Devices) o;

        if (airplane != that.airplane) return false;
        if (bluetooth != that.bluetooth) return false;
        if (camera != that.camera) return false;
        if (data_roaming != that.data_roaming) return false;
        if (microphone != that.microphone) return false;
        if (mobile_data != that.mobile_data) return false;
        if (nfc != that.nfc) return false;
        if (sdcard != that.sdcard) return false;
        if (usb != that.usb) return false;
        if (wifi != that.wifi) return false;

        return true;
    }

    public int getWifi() {
        return wifi;
    }

    public void setWifi(int wifi) {
        this.wifi = wifi;
    }

    public int getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(int bluetooth) {
        this.bluetooth = bluetooth;
    }

    public int getCamera() {
        return camera;
    }

    public void setCamera(int camera) {
        this.camera = camera;
    }

    public int getMicrophone() {
        return microphone;
    }

    public void setMicrophone(int microphone) {
        this.microphone = microphone;
    }

    public int getNfc() {
        return nfc;
    }

    public void setNfc(int nfc) {
        this.nfc = nfc;
    }

    public int getData_roaming() {
        return data_roaming;
    }

    public void setData_roaming(int data_roaming) {
        this.data_roaming = data_roaming;
    }

    public int getSdcard() {
        return sdcard;
    }

    public void setSdcard(int sdcard) {
        this.sdcard = sdcard;
    }

    public int getUsb() {
        return usb;
    }

    public void setUsb(int usb) {
        this.usb = usb;
    }

    public int getAirplane() {
        return airplane;
    }

    public void setAirplane(int airplane) {
        this.airplane = airplane;
    }

    public int getMobile_data() {
        return mobile_data;
    }

    public void setMobile_data(int mobile_data) {
        this.mobile_data = mobile_data;
    }
}
