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


/**
 * XML configuration for launcher_config
 */
public class LauncherClientConfig {

    /**
     * 手机型号
     */
    private String phone_model;
    /**
     * 所属银行网点
     */
    private String register_bank;
    /**
     * 网点联系电话
     */
    private String phone_number;
    /**
     * 注册日期
     */
    private String register_date;
    /**
     * 所属管理员
     */
    private String admin;
    /**
     * 当前设备状态
     */
    private String device_state;
    /**
     * 是否注册成功, 0: 失败， 1: 成功
     */
    private int is_registered;

    public LauncherClientConfig() {
        this("", "", "", "", "", "", -1);
    }

    public LauncherClientConfig(String phone_model, String register_bank,
                                String phone_number, String register_date,
                                String admin, String device_state, int is_registered) {
        this.phone_model = phone_model;
        this.register_bank = register_bank;
        this.phone_number = phone_number;
        this.register_date = register_date;
        this.admin = admin;
        this.device_state = device_state;
        this.is_registered = is_registered;
    }

    @Override
    public String toString() {
        return "LauncherClientConfig{" +
                "phone_model='" + phone_model + '\'' +
                ", register_bank='" + register_bank + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", register_date='" + register_date + '\'' +
                ", admin='" + admin + '\'' +
                ", device_state='" + device_state + '\'' +
                ", is_registered=" + is_registered +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LauncherClientConfig)) return false;

        LauncherClientConfig that = (LauncherClientConfig) o;

        if (is_registered != that.is_registered) return false;
        if (!admin.equals(that.admin)) return false;
        if (!device_state.equals(that.device_state)) return false;
        if (!phone_model.equals(that.phone_model)) return false;
        if (!phone_number.equals(that.phone_number)) return false;
        if (!register_bank.equals(that.register_bank)) return false;
        if (!register_date.equals(that.register_date)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = phone_model.hashCode();
        result = 31 * result + register_bank.hashCode();
        result = 31 * result + phone_number.hashCode();
        result = 31 * result + register_date.hashCode();
        result = 31 * result + admin.hashCode();
        result = 31 * result + device_state.hashCode();
        result = 31 * result + is_registered;
        return result;
    }

    public String getPhoneModel() {
        return phone_model;
    }

    public void setPhoneModel(String phone_model) {
        this.phone_model = phone_model;
    }

    public String getRegisterBank() {
        return register_bank;
    }

    public void setRegisterBank(String register_bank) {
        this.register_bank = register_bank;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getRegisterDate() {
        return register_date;
    }

    public void setRegisterDate(String register_date) {
        this.register_date = register_date;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getDeviceState() {
        return device_state;
    }

    public void setDeviceState(String device_state) {
        this.device_state = device_state;
    }

    public int getIsRegistered() {
        return is_registered;
    }

    public void setRegistered(int is_registered) {
        this.is_registered = is_registered;
    }
}
