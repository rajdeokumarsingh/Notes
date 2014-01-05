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

package com.pekall.plist.su.settings.launcher;


import com.pekall.plist.beans.PayloadBase;

import java.util.ArrayList;
import java.util.List;

/**
 * XML configuration for "launcher_app_info"
 */
@SuppressWarnings("UnusedDeclaration")
public class LauncherSettings extends PayloadBase {
    /**
     * 手机型号
     */
    private String phoneModel;
    /**
     * 所属银行网点
     */
    private String registerBank;
    /**
     * 网点联系电话
     */
    private String phoneNumber;
    /**
     * 注册日期
     */
    private String registerDate;
    /**
     * 所属管理员
     */
    private String admin;
    /**
     * 当前设备状态
     */
    private String deviceState;
    /**
     * 是否注册成功, 0: 失败， 1: 成功
     */
    private Integer isRegistered;

    /**
     * 管理员密码(由于协议是https的，暂不考虑加密)
     */
    private String adminPassword;
    /**
     * 壁纸文件
     */
    private String wallpaper;
    /**
     * List contains application package/class/screen information
     */
    private List<ApkItem> apkItems;

    /**
     * List contains web quick launches information
     */
    private List<WebItem> webItems;

    public LauncherSettings() {
        setPayloadType(PAYLOAD_TYPE_LAUNCHER_SETTINGS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LauncherSettings)) return false;

        LauncherSettings that = (LauncherSettings) o;

        return this.hashCode() == that.hashCode();

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (phoneModel != null ? phoneModel.hashCode() : 0);
        result = 31 * result + (registerBank != null ? registerBank.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (registerDate != null ? registerDate.hashCode() : 0);
        result = 31 * result + (admin != null ? admin.hashCode() : 0);
        result = 31 * result + (deviceState != null ? deviceState.hashCode() : 0);
        result = 31 * result + (isRegistered != null ? isRegistered.hashCode() : 0);
        result = 31 * result + (adminPassword != null ? adminPassword.hashCode() : 0);
        result = 31 * result + (wallpaper != null ? wallpaper.hashCode() : 0);
        if(webItems != null) {
            for (WebItem item : webItems) {
                result += item.hashCode();
            }
        }
        if (apkItems != null) {
            for (ApkItem item : apkItems) {
                result += item.hashCode();
            }
        }
        return result;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phone_model) {
        this.phoneModel = phone_model;
    }

    public String getRegisterBank() {
        return registerBank;
    }

    public void setRegisterBank(String register_bank) {
        this.registerBank = register_bank;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String register_date) {
        this.registerDate = register_date;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(String device_state) {
        this.deviceState = device_state;
    }

    public Integer getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(Integer is_registered) {
        this.isRegistered = is_registered;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(String wallpaper) {
        this.wallpaper = wallpaper;
    }

    public List<ApkItem> getApkItems() {
        return apkItems;
    }

    public void setApkItems(List<ApkItem> apkItems) {
        this.apkItems = apkItems;
    }

    public List<WebItem> getWebItems() {
        return webItems;
    }

    public void setWebItems(List<WebItem> webItems) {
        this.webItems = webItems;
    }

    public void addApkItem(ApkItem item) {
        if (apkItems == null) {
            apkItems = new ArrayList<ApkItem>();
        }
        apkItems.add(item);
    }

    public void addWebItem(WebItem item) {
        if (webItems == null) {
            webItems = new ArrayList<WebItem>();
        }
        webItems.add(item);
    }
}
