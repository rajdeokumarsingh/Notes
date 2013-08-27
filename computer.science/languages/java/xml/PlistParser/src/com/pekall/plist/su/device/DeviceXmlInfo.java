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


import java.util.ArrayList;
import java.util.List;

/**
 * Parsed data for XML configuration "device_info"
 */
public class DeviceXmlInfo {
    /**
     * 平台类型
     */
    private String platform = "";
    /**
     * 操作系统的版本
     */
    private String os_version = "";
    /**
     * 手机号
     */
    private String phone_number = "";
    /**
     * 运营商
     */
    private String mobile_vendor = "";
    /**
     * 系统语言
     */
    private String language = "";
    /**
     * 是否解锁
     */
    private String security_flag = "";
    /**
     * 总内存, 单位: byte
     */
    private String ram_total = "";
    /**
     * 已用内存, 单位: byte
     */
    private String ram_used = "";
    /**
     * 总存储空间, 单位: byte
     */
    private String disk_total = "";
    /**
     * 已用存储空间, 单位: byte
     */
    private String disk_used = "";
    /**
     * 位置信息
     */
    private Location location = new Location();
    /**
     * CPU占用率
     */
    private double cpu_ratio = 0.0;
    /**
     * 网速
     */
    private NetSpeed net_speed = new NetSpeed();
    /**
     * 型号
     */
    private String device_mode = "";
    /**
     * 正在运行的应用程序, 后台服务等等
     */
    private List<RunningProc> procList = new ArrayList<RunningProc>();
    /**
     * 应用安装卸载信息
     */
    private List<InstallInfo> installInfos = new ArrayList<InstallInfo>();

    /**
     * 设备id
     */
    private String device_id = "";
    /**
     * 设备imei
     */
    private String imei = "";
    /**
     * 制造商
     */
    private String manufacturer = "";
    /**
     * 品牌
     */
    private String brand = "";
    /**
     * 手机所在国家代码
     */
    private String country_code = "";
    /**
     * 漫游状态
     */
    private String roaming_flag = "";
    /**
     * 显示屏大小(480x320, etc.)
     */
    private String display_size = "";
    /**
     * 手机内部存储空间大小
     */
    private long internal_storage_total = -1;
    /**
     * 手机已用内部存储空间大小
     */
    private long internal_storage_used = -1;
    /**
     * 空闲内存大小
     */
    private long free_ram_size = -1;
    /**
     * 空闲外部存储空间大小
     */
    private long free_disk_size = -1;
    /**
     * 空闲内部存储空间大小
     */
    private long free_internal_storage_size = -1;
    /**
     * mdm是否开启
     */
    private boolean mdm_enabled = true;
    /**
     * usb debug是否开启
     */
    private boolean usb_debugging = false;
    /**
     * WIFI MAC地址
     */
    private String wifi_mac_addr = "";
    /**
     * 电池剩余电量
     */
    private String battery_life = "";

    /**
     * 设备状态， 50001正常, 50006锁屏
     */
    private String device_status = "";

    public DeviceXmlInfo() {
        this("", "", "", "", "", "", "", "", "", "", new Location(), 0.0,
                new NetSpeed(), "", "", new ArrayList<RunningProc>(), new ArrayList<InstallInfo>());
    }

    public DeviceXmlInfo(String platform, String os_version, String phone_number,
                         String mobile_vendor, String language, String security_flag,
                         String ram_total, String ram_used, String disk_total,
                         String disk_used, Location location, double cpu_ratio,
                         NetSpeed net_speed, String device_mode, String device_status,
                         List<RunningProc> procList,
                         List<InstallInfo> installInfos) {
        this.platform = platform;
        this.os_version = os_version;
        this.phone_number = phone_number;
        this.mobile_vendor = mobile_vendor;
        this.language = language;
        this.security_flag = security_flag;
        this.ram_total = ram_total;
        this.ram_used = ram_used;
        this.disk_total = disk_total;
        this.disk_used = disk_used;
        this.location = location;
        this.cpu_ratio = cpu_ratio;
        this.net_speed = net_speed;
        this.device_mode = device_mode;
        this.procList = procList;
        this.installInfos = installInfos;
        this.device_status = device_status;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String mPlatform) {
        this.platform = mPlatform;
    }

    public String getOsVersion() {
        return os_version;
    }

    public void setOsVersion(String mOsVersion) {
        this.os_version = mOsVersion;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.phone_number = mPhoneNumber;
    }

    public String getMobileVendor() {
        return mobile_vendor;
    }

    public void setMobileVendor(String mMobileVendor) {
        this.mobile_vendor = mMobileVendor;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String mLanguage) {
        this.language = mLanguage;
    }

    public String getSecurityFlag() {
        return security_flag;
    }

    public void setSecurityFlag(String mSecurityFlag) {
        this.security_flag = mSecurityFlag;
    }

    public String getRamTotal() {
        return ram_total;
    }

    public void setRamTotal(String mRamTotal) {
        this.ram_total = mRamTotal;
    }

    public String getRamUsed() {
        return ram_used;
    }

    public void setRamUsed(String mRamUsed) {
        this.ram_used = mRamUsed;
    }

    public String getDiskTotal() {
        return disk_total;
    }

    public void setDiskTotal(String mDiskTotal) {
        this.disk_total = mDiskTotal;
    }

    public String getDiskUsed() {
        return disk_used;
    }

    public void setDiskUsed(String mDiskUsed) {
        this.disk_used = mDiskUsed;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setDeviceMode(String deviceMode) {
        this.device_mode = deviceMode;
    }

    public String getDeviceMode() {
        return this.device_mode;
    }


    public double getCpuRatio() {
        return cpu_ratio;
    }

    public void setCpuRatio(double cpu_ratio) {
        this.cpu_ratio = cpu_ratio;
    }

    public NetSpeed getNetSpeed() {
        return net_speed;
    }

    public void setNetSpeed(NetSpeed net_speed) {
        this.net_speed = net_speed;
    }

    public List<RunningProc> getProcList() {
        return procList;
    }

    public void setProcList(List<RunningProc> procList) {
        this.procList = procList;
    }

    public List<InstallInfo> getInstallInfos() {
        return installInfos;
    }

    public void setInstallInfos(List<InstallInfo> installInfos) {
        this.installInfos = installInfos;
    }

    public String getDeviceId() {
        return device_id;
    }

    public void setDeviceId(String device_id) {
        this.device_id = device_id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCountryCode() {
        return country_code;
    }

    public void setCountryCode(String country_code) {
        this.country_code = country_code;
    }

    public String getRoamingFlag() {
        return roaming_flag;
    }

    public void setRoamingFlag(String roaming_flag) {
        this.roaming_flag = roaming_flag;
    }

    public String getDisplaySize() {
        return display_size;
    }

    public void setDisplaySize(String display_size) {
        this.display_size = display_size;
    }

    public long getInternalStorageTotal() {
        return internal_storage_total;
    }

    public void setInternalStorageTotal(long internal_storage_total) {
        this.internal_storage_total = internal_storage_total;
    }

    public long getInternalStorageUsed() {
        return internal_storage_used;
    }

    public void setInternalStorageUsed(long internal_storage_used) {
        this.internal_storage_used = internal_storage_used;
    }

    public long getFreeRamSize() {
        return free_ram_size;
    }

    public void setFreeRamSize(long free_ram_size) {
        this.free_ram_size = free_ram_size;
    }

    public long getFreeDiskSize() {
        return free_disk_size;
    }

    public void setFreeDiskSize(long free_disk_size) {
        this.free_disk_size = free_disk_size;
    }

    public long getFreeInternalStorageSize() {
        return free_internal_storage_size;
    }

    public void setFreeInternalStorageSize(long free_internal_storage_size) {
        this.free_internal_storage_size = free_internal_storage_size;
    }

    public boolean isMdmEnabled() {
        return mdm_enabled;
    }

    public void setMdmEnabled(boolean mdm_enabled) {
        this.mdm_enabled = mdm_enabled;
    }

    public boolean isUsbDebugging() {
        return usb_debugging;
    }

    public void setUsbDebugging(boolean usb_debugging) {
        this.usb_debugging = usb_debugging;
    }

    public String getWifiMacAddr() {
        return wifi_mac_addr;
    }

    public void setWifiMacAddr(String wifi_mac_addr) {
        this.wifi_mac_addr = wifi_mac_addr;
    }

    public String getBatteryLife() {
        return battery_life;
    }

    public void setBatteryLife(String battery_life) {
        this.battery_life = battery_life;
    }

    public String getDeviceStatus() {
        return device_status;
    }

    public void setDeviceStatus(String device_status) {
        this.device_status = device_status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (RunningProc rp: procList) {
            sb.append(rp);
        }
        sb.append("}");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("{");
        for (InstallInfo ii : installInfos) {
            sb2.append(ii);
        }
        sb2.append("}");
        return "DeviceXmlInfo{" +
                "platform='" + platform + '\'' +
                ", os_version='" + os_version + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", mobile_vendor='" + mobile_vendor + '\'' +
                ", language='" + language + '\'' +
                ", security_flag='" + security_flag + '\'' +
                ", ram_total='" + ram_total + '\'' +
                ", ram_used='" + ram_used + '\'' +
                ", disk_total='" + disk_total + '\'' +
                ", disk_used='" + disk_used + '\'' +
                ", location='" + location.toString() + '\'' +
                ", cpu_ratio='" + cpu_ratio + '\'' +
                ", net_speed='" + net_speed + '\'' +
                ", device_mode='" + device_mode + '\'' +
                ", device_id='" + device_id + '\'' +
                ", imei='" + imei + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", brand='" + brand + '\'' +
                ", country_code='" + country_code + '\'' +
                ", roaming_flag='" + roaming_flag + '\'' +
                ", display_size='" + display_size + '\'' +
                ", internal_storage_total=" + internal_storage_total + '\'' +
                ", internal_storage_used=" + internal_storage_used + '\'' +
                ", free_ram_size=" + free_ram_size + '\'' +
                ", free_disk_size=" + free_disk_size + '\'' +
                ", free_internal_storage_size=" + free_internal_storage_size + '\'' +
                ", mdm_enabled=" + mdm_enabled + '\'' +
                ", usb_debugging=" + usb_debugging + '\'' +
                ", wifi_mac_addr=" + wifi_mac_addr + '\'' +
                ", battery_life=" + battery_life + '\'' +
                ", device_status=" + device_status + '\'' +
                ", running_proc='" + sb.toString() + '\'' +
                ", install_info='" + sb2.toString() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceXmlInfo)) return false;

        DeviceXmlInfo that = (DeviceXmlInfo) o;

        if (!disk_total.equals(that.disk_total)) return false;
        if (!disk_used.equals(that.disk_used)) return false;
        if (!language.equals(that.language)) return false;
        if (!location.equals(that.location)) return false;
        if (!mobile_vendor.equals(that.mobile_vendor)) return false;
        if (!os_version.equals(that.os_version)) return false;
        if (!phone_number.equals(that.phone_number)) return false;
        if (!platform.equals(that.platform)) return false;
        if (!ram_total.equals(that.ram_total)) return false;
        if (!ram_used.equals(that.ram_used)) return false;
        if (!security_flag.equals(that.security_flag)) return false;
        if (!device_mode.equals(that.device_mode)) return false;
        if (!device_status.equals(that.device_status)) return false;

        if (cpu_ratio != that.cpu_ratio) return false;
        if (!net_speed.equals(that.net_speed)) return false;

        if(this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = platform.hashCode();
        result = 31 * result + os_version.hashCode();
        result = 31 * result + phone_number.hashCode();
        result = 31 * result + mobile_vendor.hashCode();
        result = 31 * result + language.hashCode();
        result = 31 * result + security_flag.hashCode();
        result = 31 * result + ram_total.hashCode();
        result = 31 * result + ram_used.hashCode();
        result = 31 * result + disk_total.hashCode();
        result = 31 * result + disk_used.hashCode();
        result = 31 * result + location.hashCode();
        temp = Double.doubleToLongBits(cpu_ratio);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + net_speed.hashCode();
        result = 31 * result + device_id.hashCode();
        result = 31 * result + imei.hashCode();
        result = 31 * result + manufacturer.hashCode();
        result = 31 * result + brand.hashCode();
        result = 31 * result + country_code.hashCode();
        result = 31 * result + roaming_flag.hashCode();
        result = 31 * result + display_size.hashCode();
        result = (int) (31 * result + internal_storage_total);
        result = (int) (31 * result + internal_storage_used);
        result = (int) (31 * result + free_ram_size);
        result = (int) (31 * result + free_disk_size);
        result = (int) (31 * result + free_internal_storage_size);
        result = 31 * result + (mdm_enabled ? 0 : 1);
        result = 31 * result + (usb_debugging ? 0 : 1);
        result = 31 * result + wifi_mac_addr.hashCode();
        result = 31 * result + battery_life.hashCode();
        result = 31 * result + device_status.hashCode();
        for (RunningProc rp : procList) {
            result = 31 * result + rp.hashCode();
        }
        for (InstallInfo ii : installInfos) {
            result = 31 * result + ii.hashCode();
        }
        return result;
    }
}
