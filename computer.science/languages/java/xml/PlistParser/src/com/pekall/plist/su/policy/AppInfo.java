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
public class AppInfo {
    /**
     * Name of the application
     */
    private String app_name;

    /**
     * Control type
     * 0: must install， 1: white list， 2: black list， 3:grey list
     */
    private int control_type;

    /**
     * Application name match rule, 0 for include the name, 1 for equal the name
     */
    private int match_rule = 1;
    /**
     * Package name of the application
     */
    private String package_name;

    /**
     * Version code of the application
     */
    private String version_code;

    /**
     * Download url for the application
     */
    private String download_url;

    public AppInfo() {
        this("", -1, "", "", "");
    }

    public AppInfo(String app_name, int control_type, String package_name,
                   String version_code, String download_url) {
        this.app_name = app_name;
        this.control_type = control_type;
        this.package_name = package_name;
        this.version_code = version_code;
        this.download_url = download_url;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "app_name='" + app_name + '\'' +
                ", control_type=" + control_type +
                ", match_rule=" + match_rule +
                ", package_name='" + package_name + '\'' +
                ", version_code='" + version_code + '\'' +
                ", download_url='" + download_url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppInfo)) return false;

        AppInfo appInfo = (AppInfo) o;

        if (control_type != appInfo.control_type) return false;
        if (!app_name.equals(appInfo.app_name)) return false;
        if (!download_url.equals(appInfo.download_url)) return false;
        if (!package_name.equals(appInfo.package_name)) return false;
        if (!version_code.equals(appInfo.version_code)) return false;
        if (match_rule != appInfo.match_rule) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = app_name.hashCode();
        result = 31 * result + control_type;
        result = 31 * result + match_rule;
        result = 31 * result + package_name.hashCode();
        result = 31 * result + version_code.hashCode();
        result = 31 * result + download_url.hashCode();
        return result;
    }

    public String getAppName() {
        return app_name;
    }

    public void setAppName(String app_name) {
        this.app_name = app_name;
    }

    public int getControlType() {
        return control_type;
    }

    public void setControlType(int control_type) {
        this.control_type = control_type;
    }

    public String getPackageName() {
        return package_name;
    }

    public void setPackageName(String package_name) {
        this.package_name = package_name;
    }

    public String getVersionCode() {
        return version_code;
    }

    public void setVersionCode(String version_code) {
        this.version_code = version_code;
    }

    public String getDownloadUrl() {
        return download_url;
    }

    public void setDownloadUrl(String download_url) {
        this.download_url = download_url;
    }

    public int getMatchRule() {
        return match_rule;
    }

    public void setMatchRule(int match_rule) {
        this.match_rule = match_rule;
    }
}
