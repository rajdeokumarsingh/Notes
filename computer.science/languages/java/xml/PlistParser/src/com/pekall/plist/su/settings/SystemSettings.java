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
@SuppressWarnings("UnusedDeclaration")
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

    private Integer systemUi;

    private Integer eraseDevice;

    public SystemSettings() {
        setPayloadType(PAYLOAD_TYPE_SYSTEM_SETTINGS);
    }

    public SystemSettings(Integer wifi, Integer bluetooth, Integer camera, Integer microphone,
                          Integer nfc, Integer dataRoaming, Integer sdcard, Integer usb,
                          Integer airplane, Integer mobileData, Integer systemUi, Integer eraseDevice) {
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
        this.systemUi = systemUi;
        this.eraseDevice = eraseDevice;

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
                ", systemUi=" + systemUi +
                ", eraseDevice=" + eraseDevice +
                '}';
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

    public Integer getEraseDevice() {
        return eraseDevice;
    }

    public void setEraseDevice(Integer eraseDevice) {
        this.eraseDevice = eraseDevice;
    }

    public Integer getSystemUi() {
        return systemUi;
    }

    public void setSystemUi(Integer systemUi) {
        this.systemUi = systemUi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SystemSettings)) return false;
        if (!super.equals(o)) return false;

        SystemSettings that = (SystemSettings) o;

        if (airplane != null ? !airplane.equals(that.airplane) : that.airplane != null) return false;
        if (bluetooth != null ? !bluetooth.equals(that.bluetooth) : that.bluetooth != null) return false;
        if (camera != null ? !camera.equals(that.camera) : that.camera != null) return false;
        if (dataRoaming != null ? !dataRoaming.equals(that.dataRoaming) : that.dataRoaming != null) return false;
        if (eraseDevice != null ? !eraseDevice.equals(that.eraseDevice) : that.eraseDevice != null) return false;
        if (microphone != null ? !microphone.equals(that.microphone) : that.microphone != null) return false;
        if (mobileData != null ? !mobileData.equals(that.mobileData) : that.mobileData != null) return false;
        if (nfc != null ? !nfc.equals(that.nfc) : that.nfc != null) return false;
        if (sdcard != null ? !sdcard.equals(that.sdcard) : that.sdcard != null) return false;
        if (systemUi != null ? !systemUi.equals(that.systemUi) : that.systemUi != null) return false;
        if (usb != null ? !usb.equals(that.usb) : that.usb != null) return false;
        //noinspection RedundantIfStatement
        if (wifi != null ? !wifi.equals(that.wifi) : that.wifi != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (wifi != null ? wifi.hashCode() : 0);
        result = 31 * result + (bluetooth != null ? bluetooth.hashCode() : 0);
        result = 31 * result + (camera != null ? camera.hashCode() : 0);
        result = 31 * result + (microphone != null ? microphone.hashCode() : 0);
        result = 31 * result + (nfc != null ? nfc.hashCode() : 0);
        result = 31 * result + (dataRoaming != null ? dataRoaming.hashCode() : 0);
        result = 31 * result + (sdcard != null ? sdcard.hashCode() : 0);
        result = 31 * result + (usb != null ? usb.hashCode() : 0);
        result = 31 * result + (airplane != null ? airplane.hashCode() : 0);
        result = 31 * result + (mobileData != null ? mobileData.hashCode() : 0);
        result = 31 * result + (systemUi != null ? systemUi.hashCode() : 0);
        result = 31 * result + (eraseDevice != null ? eraseDevice.hashCode() : 0);
        return result;
    }
}
