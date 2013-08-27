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


import java.util.ArrayList;
import java.util.List;

/**
 * XML configuration for "app_control_list"
 */
public class AppControlList extends Policy {

    private AppInfoWrapper must_install = new AppInfoWrapper();
    private AppInfoWrapper white_list = new AppInfoWrapper();
    private AppInfoWrapper black_list = new AppInfoWrapper();
    private AppInfoWrapper grey_list = new AppInfoWrapper();

    public AppControlList() {
        super();
    }

    public AppControlList(String name, int status, String description) {
        super(name, status, description);
    }

    public AppControlList(String name, int status, String description,
                          AppInfoWrapper must_install,
                          AppInfoWrapper white_list,
                          AppInfoWrapper black_list,
                          AppInfoWrapper grey_list) {
        super(name, status, description);
        this.must_install = must_install;
        this.white_list = white_list;
        this.black_list = black_list;
        this.grey_list = grey_list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("must_install{");
        for (AppInfo info : must_install.getInfos()) {
            sb.append(info.toString() + ",");
        }
        sb.append("}");
        sb.append(",white_list{");
        for (AppInfo info : white_list.getInfos()) {
            sb.append(info.toString() + ",");
        }
        sb.append("}");
        sb.append(",black_list{");
        for (AppInfo info : black_list.getInfos()) {
            sb.append(info.toString() + ",");
        }
        sb.append("}");
        sb.append(",grey_list{");
        for (AppInfo info : grey_list.getInfos()) {
            sb.append(info.toString() + ",");
        }
        sb.append("}");
        return "AppControlList{" +
                "policy=" + super.toString() +
                "applist=" + sb.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppControlList)) return false;
        if (!super.equals(o)) return false;

        AppControlList that = (AppControlList) o;

        if (this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (AppInfo info : must_install.getInfos()) {
            result = 31 * result + info.hashCode();
        }
        for (AppInfo info : white_list.getInfos()) {
            result = 31 * result + info.hashCode();
        }
        for (AppInfo info : black_list.getInfos()) {
            result = 31 * result + info.hashCode();
        }
        for (AppInfo info : grey_list.getInfos()) {
            result = 31 * result + info.hashCode();
        }
        return result;
    }

    public AppInfoWrapper getMustInstall() {
        return must_install;
    }

    public void setMustInstall(AppInfoWrapper must_install) {
        this.must_install = must_install;
    }

    public AppInfoWrapper getWhiteList() {
        return white_list;
    }

    public void setWhiteList(AppInfoWrapper white_list) {
        this.white_list = white_list;
    }

    public AppInfoWrapper getBlackList() {
        return black_list;
    }

    public void setBlackList(AppInfoWrapper black_list) {
        this.black_list = black_list;
    }

    public AppInfoWrapper getGreyList() {
        return grey_list;
    }

    public void setGreyList(AppInfoWrapper grey_list) {
        this.grey_list = grey_list;
    }
}
