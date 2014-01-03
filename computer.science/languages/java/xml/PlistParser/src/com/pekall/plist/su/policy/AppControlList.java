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


public class AppControlList extends Policy {

    private AppInfoWrapper mustInstall = new AppInfoWrapper();
    private AppInfoWrapper whiteList = new AppInfoWrapper();
    private AppInfoWrapper blackList = new AppInfoWrapper();
    private AppInfoWrapper greyList = new AppInfoWrapper();

    public AppControlList() {
        super();
        setPayloadType(PAYLOAD_TYPE_APP_CONTROL_POLICY);
    }

    public AppControlList(String name, int status, String description) {
        super(name, status, description);
        setPayloadType(PAYLOAD_TYPE_APP_CONTROL_POLICY);
    }

    public AppControlList(String name, int status, String description,
                          AppInfoWrapper mustInstall,
                          AppInfoWrapper whiteList,
                          AppInfoWrapper blackList,
                          AppInfoWrapper greyList) {
        super(name, status, description);
        this.mustInstall = mustInstall;
        this.whiteList = whiteList;
        this.blackList = blackList;
        this.greyList = greyList;

        setPayloadType(PAYLOAD_TYPE_APP_CONTROL_POLICY);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("mustInstall{");
        for (AppInfo info : mustInstall.getInfos()) {
            sb.append(info.toString() + ",");
        }
        sb.append("}");
        sb.append(",whiteList{");
        for (AppInfo info : whiteList.getInfos()) {
            sb.append(info.toString() + ",");
        }
        sb.append("}");
        sb.append(",blackList{");
        for (AppInfo info : blackList.getInfos()) {
            sb.append(info.toString() + ",");
        }
        sb.append("}");
        sb.append(",greyList{");
        for (AppInfo info : greyList.getInfos()) {
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
        for (AppInfo info : mustInstall.getInfos()) {
            result = 31 * result + info.hashCode();
        }
        for (AppInfo info : whiteList.getInfos()) {
            result = 31 * result + info.hashCode();
        }
        for (AppInfo info : blackList.getInfos()) {
            result = 31 * result + info.hashCode();
        }
        for (AppInfo info : greyList.getInfos()) {
            result = 31 * result + info.hashCode();
        }
        return result;
    }

    public AppInfoWrapper getMustInstall() {
        return mustInstall;
    }

    public void setMustInstall(AppInfoWrapper must_install) {
        this.mustInstall = must_install;
    }

    public AppInfoWrapper getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(AppInfoWrapper white_list) {
        this.whiteList = white_list;
    }

    public AppInfoWrapper getBlackList() {
        return blackList;
    }

    public void setBlackList(AppInfoWrapper black_list) {
        this.blackList = black_list;
    }

    public AppInfoWrapper getGreyList() {
        return greyList;
    }

    public void setGreyList(AppInfoWrapper grey_list) {
        this.greyList = grey_list;
    }
}
