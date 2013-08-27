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

package com.pekall.plist.su.device;

/**
 * XML element for "device_info.net_speed"
 */
public class NetSpeed {
    /**
     * 上行速度, 单位byte
     */
    private double uplink = -1.0;
    /**
     * 下行速度, 单位byte
     */
    private double downlink = -1.0;

    public NetSpeed(double uplink, double downlink) {
        this.uplink = uplink;
        this.downlink = downlink;
    }

    public NetSpeed() {
        this(0.0, 0.0);
    }

    @Override
    public String toString() {
        return "NetSpeed{" +
                "uplink=" + uplink +
                ", downlink=" + downlink +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NetSpeed)) return false;

        NetSpeed netSpeed = (NetSpeed) o;

        if (Double.compare(netSpeed.downlink, downlink) != 0) return false;
        if (Double.compare(netSpeed.uplink, uplink) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(uplink);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(downlink);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public double getUplink() {
        return uplink;
    }

    public void setUplink(double uplink) {
        this.uplink = uplink;
    }

    public double getDownlink() {
        return downlink;
    }

    public void setDownlink(double downlink) {
        this.downlink = downlink;
    }
}
