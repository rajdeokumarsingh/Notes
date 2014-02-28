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
 * XML element for "app_control_list.app_info"
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class AppInfo {
    /**
     * Name of the application
     */
    private String appName;

    /**
     * Control type
     * 0: must install， 1: white list， 2: black list， 3:grey list
     */
    private int controlType;

    /**
     * Application name match rule, 0 for include the name, 1 for equal the name
     */
    private int matchRule = 1;
    /**
     * Package name of the application
     */
    private String packageName;

    /**
     * Version code of the application
     */
    private String versionCode;

    /**
     * Download url for the application
     */
    private String downloadUrl;

    public AppInfo() {
        this("", -1, "", "", "");
    }

    public AppInfo(String appName, int controlType, String packageName,
                   String versionCode, String downloadUrl) {
        this.appName = appName;
        this.controlType = controlType;
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                ", controlType=" + controlType +
                ", matchRule=" + matchRule +
                ", packageName='" + packageName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppInfo)) return false;

        AppInfo appInfo = (AppInfo) o;

        if (controlType != appInfo.controlType) return false;
        if (!appName.equals(appInfo.appName)) return false;
        if (!downloadUrl.equals(appInfo.downloadUrl)) return false;
        if (!packageName.equals(appInfo.packageName)) return false;
        if (!versionCode.equals(appInfo.versionCode)) return false;
        return matchRule == appInfo.matchRule;

    }

    @Override
    public int hashCode() {
        int result = appName.hashCode();
        result = 31 * result + controlType;
        result = 31 * result + matchRule;
        result = 31 * result + packageName.hashCode();
        result = 31 * result + versionCode.hashCode();
        result = 31 * result + downloadUrl.hashCode();
        return result;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String app_name) {
        this.appName = app_name;
    }

    public int getControlType() {
        return controlType;
    }

    public void setControlType(int control_type) {
        this.controlType = control_type;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String package_name) {
        this.packageName = package_name;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String version_code) {
        this.versionCode = version_code;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String download_url) {
        this.downloadUrl = download_url;
    }

    public int getMatchRule() {
        return matchRule;
    }

    public void setMatchRule(int match_rule) {
        this.matchRule = match_rule;
    }
}
