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
 * XML configuration for device_info.running_proc
 */
public class RunningProc {
    /**
     * 程序类型, application: 应用程序，service: 后台服务
     */
    private String type = "";
    /**
     * 程序名
     */
    private String name = "";
    /**
     * 程序包名
     */
    private String package_name = "";

    public RunningProc(String type, String name, String package_name) {
        this.type = type;
        this.name = name;
        this.package_name = package_name;
    }

    public RunningProc() {
        this("", "", "");
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return package_name;
    }

    public void setPackageName(String package_name) {
        this.package_name = package_name;
    }

        @Override
    public String toString() {
        return "RunningProc{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", package_name='" + package_name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RunningProc)) return false;

        RunningProc that = (RunningProc) o;

        if (!name.equals(that.name)) return false;
        if (!package_name.equals(that.package_name)) return false;
        if (!type.equals(that.type)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + package_name.hashCode();
        return result;
    }
}
