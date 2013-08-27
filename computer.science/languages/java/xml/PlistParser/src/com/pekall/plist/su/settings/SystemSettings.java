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

package com.pekall.plist.su.settings;

import com.pekall.plist.beans.PayloadBase;

/**
 * XML configuration for "system_settings"
 */
public class SystemSettings extends PayloadBase {
    /**
     * Whether wifi is on, 0 for off, 1 for on
     */
    private Integer wifi;
    /**
     * Whether bluetooth is on, 0 for off, 1 for on
     */
    private Integer bluetooth;
    /**
     * Whether camera is enable, 0 for disable, 1 for enable
     */
    private Integer camera;
    /**
     * Whether microphone is on, 0 for off, 1 for on
     */
    private Integer microphone;
    /**
     * Whether nfc is on, 0 for off, 1 for on
     */
    private Integer nfc;
    /**
     * Whether dataRoaming is on, 0 for off, 1 for on
     */
    private Integer dataRoaming;
    /**
     * Whether sdcard is mounted, 0 for off, 1 for on
     */
    private Integer sdcard;
    /**
     * Whether usb is on, 0 for off, 1 for on
     */
    private Integer usb;
    /**
     * Whether airplane is on, 0 for off, 1 for on
     */
    private Integer airplane;
    /**
     * Whether mobileData is on, 0 for off, 1 for on
     */
    private Integer mobileData;

    public SystemSettings() {
        setPayloadType(PAYLOAD_TYPE_SYSTEM_SETTINGS);
    }

    public SystemSettings(Integer wifi, Integer bluetooth, Integer camera, Integer microphone,
                          Integer nfc, Integer dataRoaming, Integer sdcard, Integer usb,
                          Integer airplane, Integer mobileData) {
        this.wifi = wifi;
        this.bluetooth = bluetooth;
        this.camera = camera;
        this.microphone = microphone;
        this.nfc = nfc;
        this.dataRoaming = dataRoaming;
        this.sdcard = sdcard;
        this.usb = usb;
        this.airplane = airplane;
        this.mobileData = mobileData;

        setPayloadType(PAYLOAD_TYPE_SYSTEM_SETTINGS);
    }

    @Override
    public String toString() {
        return "SystemSettings{" +
                "wifi=" + wifi +
                ", bluetooth=" + bluetooth +
                ", camera=" + camera +
                ", microphone=" + microphone +
                ", nfc=" + nfc +
                ", dataRoaming=" + dataRoaming +
                ", sdcard=" + sdcard +
                ", usb=" + usb +
                ", airplane=" + airplane +
                ", mobileData=" + mobileData +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SystemSettings)) return false;

        SystemSettings that = (SystemSettings) o;

        if (airplane != that.airplane) return false;
        if (bluetooth != that.bluetooth) return false;
        if (camera != that.camera) return false;
        if (dataRoaming != that.dataRoaming) return false;
        if (microphone != that.microphone) return false;
        if (mobileData != that.mobileData) return false;
        if (nfc != that.nfc) return false;
        if (sdcard != that.sdcard) return false;
        if (usb != that.usb) return false;
        if (wifi != that.wifi) return false;

        return true;
    }

    public Integer getWifi() {
        return wifi;
    }

    public void setWifi(Integer wifi) {
        this.wifi = wifi;
    }

    public Integer getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(Integer bluetooth) {
        this.bluetooth = bluetooth;
    }

    public Integer getCamera() {
        return camera;
    }

    public void setCamera(Integer camera) {
        this.camera = camera;
    }

    public Integer getMicrophone() {
        return microphone;
    }

    public void setMicrophone(Integer microphone) {
        this.microphone = microphone;
    }

    public Integer getNfc() {
        return nfc;
    }

    public void setNfc(Integer nfc) {
        this.nfc = nfc;
    }

    public Integer getDataRoaming() {
        return dataRoaming;
    }

    public void setDataRoaming(Integer dataRoaming) {
        this.dataRoaming = dataRoaming;
    }

    public Integer getSdcard() {
        return sdcard;
    }

    public void setSdcard(Integer sdcard) {
        this.sdcard = sdcard;
    }

    public Integer getUsb() {
        return usb;
    }

    public void setUsb(Integer usb) {
        this.usb = usb;
    }

    public Integer getAirplane() {
        return airplane;
    }

    public void setAirplane(Integer airplane) {
        this.airplane = airplane;
    }

    public Integer getMobileData() {
        return mobileData;
    }

    public void setMobileData(Integer mobileData) {
        this.mobileData = mobileData;
    }
}
