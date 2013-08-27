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
 * Xml configuration data for lock policy
 */
public class LockPolicy extends Policy {

    /**
     * Controlled devices
     */
    Devices devices;

    public LockPolicy() {
        this("", -1, "", new Devices());
    }

    public LockPolicy(String name, int status, String description, Devices devices) {
        super(name, status, description);
        /*
        this.name = name;
        this.status = status;
        this.description = description;
        */
        this.devices = devices;
    }

    @Override
    public String toString() {
        return "LockPolicy{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", devices=" + devices.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LockPolicy)) return false;

        LockPolicy that = (LockPolicy) o;

        if (status != that.status) return false;
        if (!description.equals(that.description)) return false;
        if (!devices.equals(that.devices)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    public Devices getDevices() {
        return devices;
    }

    public void setDevices(Devices devices) {
        this.devices = devices;
    }
}
