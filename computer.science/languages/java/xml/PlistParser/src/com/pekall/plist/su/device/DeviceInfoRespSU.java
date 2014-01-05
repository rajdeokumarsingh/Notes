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


import com.pekall.plist.beans.DeviceInfoResp;

import java.util.ArrayList;
import java.util.List;

/**
 * Parsed data for XML configuration "device_info"
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class DeviceInfoRespSU extends DeviceInfoResp {
    /**
     * Device is not rooted and jailbreak
     */
    public static final String SECURITY_STAT_NORMAL = "normal";
    /**
     * Device is rooted
     */
    public static final String SECURITY_STAT_ROOT = "root";
    /**
     * Device is jailbreak
     */
    public static final String SECURITY_STAT_JAILBREAK = "jailbreak";

    /**
     * 平台类型
     */
    private String platform;
    /**
     * 操作系统的版本
     */
    // see super
    // private String osVersion;

    /**
     * 手机号
     */
    // see super
    // private String phoneNumber;
    /**
     * 运营商
     */
    private String mobileVendor;
    /**
     * 型号
     */
    private String deviceMode;
    /**
     * 系统语言
     */
    private String language;
    /**
     * 是否解锁
     */
    private String securityFlag;
    /**
     * 总内存, 单位: byte
     */
    private String ramTotal;
    /**
     * 已用内存, 单位: byte
     */
    private String ramUsed;
    /**
     * 总存储空间, 单位: byte
     */
    private String diskTotal;
    /**
     * 已用存储空间, 单位: byte
     */
    private String diskUsed;
    /**
     * CPU占用率
     */
    private Double cpuRatio;

    /**
     * 设备id
     */
    private String deviceId;
    /**
     * 设备imei
     */
    // see super
    // private String imei;
    /**
     * 制造商
     */
    private String manufacturer;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 手机所在国家代码
     */
    private String countryCode;
    /**
     * 漫游状态
     */
    private String roamingFlag;
    /**
     * 显示屏大小(480x320, etc.)
     */
    private String displaySize;
    /**
     * 手机内部存储空间大小
     */
    private Long internalStorageTotal;
    /**
     * 手机已用内部存储空间大小
     */
    private Long internalStorageUsed;
    /**
     * 空闲内存大小
     */
    private Long freeRamSize;
    /**
     * 空闲外部存储空间大小
     */
    private Long freeDiskSize;
    /**
     * 空闲内部存储空间大小
     */
    private Long freeInternalStorageSize;
    /**
     * mdm是否开启
     */
    private Boolean mdmEnabled;
    /**
     * usb debug是否开启
     */
    private Boolean usbDebugging;
    /**
     * WIFI MAC地址
     */
    // see super
    // private String wifiMacAddr;
    /**
     * 电池剩余电量
     */
    // see super
    // private String batteryLife;
    /**
     * 设备状态， 50001正常, 50006锁屏
     */
    private String deviceStatus;
    /**
     * 设备安全状态， root/jailbreak状态, see SECURITY_STAT_...
     */
    private String deviceSecurityStatus;
    /**
     * 客户端版本
     */
    private String clientVersion;
    /**
     * 流量统计
     */
    private Long trafficBytes;
    /**
     * 网速
     */
    private NetSpeed netSpeed;
    /**
     * 位置信息
     */
    private Location location;
    /**
     * 正在运行的应用程序, 后台服务等等
     */
    private List<RunningProc> procList;
    /**
     * 应用安装卸载信息
     */
    private List<InstallInfo> installInfos;

    public DeviceInfoRespSU() {
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getMobileVendor() {
        return mobileVendor;
    }

    public void setMobileVendor(String mobileVendor) {
        this.mobileVendor = mobileVendor;
    }

    public String getDeviceMode() {
        return deviceMode;
    }

    public void setDeviceMode(String deviceMode) {
        this.deviceMode = deviceMode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSecurityFlag() {
        return securityFlag;
    }

    public void setSecurityFlag(String securityFlag) {
        this.securityFlag = securityFlag;
    }

    public String getRamTotal() {
        return ramTotal;
    }

    public void setRamTotal(String ramTotal) {
        this.ramTotal = ramTotal;
    }

    public String getRamUsed() {
        return ramUsed;
    }

    public void setRamUsed(String ramUsed) {
        this.ramUsed = ramUsed;
    }

    public String getDiskTotal() {
        return diskTotal;
    }

    public void setDiskTotal(String diskTotal) {
        this.diskTotal = diskTotal;
    }

    public String getDiskUsed() {
        return diskUsed;
    }

    public void setDiskUsed(String diskUsed) {
        this.diskUsed = diskUsed;
    }

    public Double getCpuRatio() {
        return cpuRatio;
    }

    public void setCpuRatio(Double cpuRatio) {
        this.cpuRatio = cpuRatio;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRoamingFlag() {
        return roamingFlag;
    }

    public void setRoamingFlag(String roamingFlag) {
        this.roamingFlag = roamingFlag;
    }

    public String getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(String displaySize) {
        this.displaySize = displaySize;
    }

    public Long getInternalStorageTotal() {
        return internalStorageTotal;
    }

    public void setInternalStorageTotal(Long internalStorageTotal) {
        this.internalStorageTotal = internalStorageTotal;
    }

    public Long getInternalStorageUsed() {
        return internalStorageUsed;
    }

    public void setInternalStorageUsed(Long internalStorageUsed) {
        this.internalStorageUsed = internalStorageUsed;
    }

    public Long getFreeRamSize() {
        return freeRamSize;
    }

    public void setFreeRamSize(Long freeRamSize) {
        this.freeRamSize = freeRamSize;
    }

    public Long getFreeDiskSize() {
        return freeDiskSize;
    }

    public void setFreeDiskSize(Long freeDiskSize) {
        this.freeDiskSize = freeDiskSize;
    }

    public Long getFreeInternalStorageSize() {
        return freeInternalStorageSize;
    }

    public void setFreeInternalStorageSize(Long freeInternalStorageSize) {
        this.freeInternalStorageSize = freeInternalStorageSize;
    }

    public Boolean getMdmEnabled() {
        return mdmEnabled;
    }

    public void setMdmEnabled(Boolean mdmEnabled) {
        this.mdmEnabled = mdmEnabled;
    }

    public Boolean getUsbDebugging() {
        return usbDebugging;
    }

    public void setUsbDebugging(Boolean usbDebugging) {
        this.usbDebugging = usbDebugging;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceSecurityStatus() {
        return deviceSecurityStatus;
    }

    public void setDeviceSecurityStatus(String deviceSecurityStatus) {
        this.deviceSecurityStatus = deviceSecurityStatus;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public Long getTrafficBytes() {
        return trafficBytes;
    }

    public void setTrafficBytes(Long trafficBytes) {
        this.trafficBytes = trafficBytes;
    }

    public NetSpeed getNetSpeed() {
        return netSpeed;
    }

    public void setNetSpeed(NetSpeed netSpeed) {
        this.netSpeed = netSpeed;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<RunningProc> getProcList() {
        return procList;
    }

    public void setProcList(List<RunningProc> procList) {
        this.procList = procList;
    }

    public void addRunningProc(RunningProc proc) {
        if (procList == null) {
            procList = new ArrayList<RunningProc>();
        }
        procList.add(proc);
    }

    public List<InstallInfo> getInstallInfos() {
        return installInfos;
    }

    public void setInstallInfos(List<InstallInfo> installInfos) {
        this.installInfos = installInfos;
    }

    public void addInstallInfo(InstallInfo info) {
        if (installInfos == null) {
            installInfos = new ArrayList<InstallInfo>();
        }
        installInfos.add(info);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceInfoRespSU)) return false;
        if (!super.equals(o)) return false;

        DeviceInfoRespSU that = (DeviceInfoRespSU) o;

        if (brand != null ? !brand.equals(that.brand) : that.brand != null) return false;
        if (countryCode != null ? !countryCode.equals(that.countryCode) : that.countryCode != null) return false;
        if (cpuRatio != null ? !cpuRatio.equals(that.cpuRatio) : that.cpuRatio != null) return false;
        if (deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null) return false;
        if (deviceMode != null ? !deviceMode.equals(that.deviceMode) : that.deviceMode != null) return false;
        if (deviceStatus != null ? !deviceStatus.equals(that.deviceStatus) : that.deviceStatus != null) return false;
        if (diskTotal != null ? !diskTotal.equals(that.diskTotal) : that.diskTotal != null) return false;
        if (diskUsed != null ? !diskUsed.equals(that.diskUsed) : that.diskUsed != null) return false;
        if (displaySize != null ? !displaySize.equals(that.displaySize) : that.displaySize != null) return false;
        if (freeDiskSize != null ? !freeDiskSize.equals(that.freeDiskSize) : that.freeDiskSize != null) return false;
        if (freeInternalStorageSize != null ? !freeInternalStorageSize.equals(that.freeInternalStorageSize) : that.freeInternalStorageSize != null)
            return false;
        if (freeRamSize != null ? !freeRamSize.equals(that.freeRamSize) : that.freeRamSize != null) return false;
        if (installInfos != null ? !installInfos.equals(that.installInfos) : that.installInfos != null) return false;
        if (internalStorageTotal != null ? !internalStorageTotal.equals(that.internalStorageTotal) : that.internalStorageTotal != null)
            return false;
        if (internalStorageUsed != null ? !internalStorageUsed.equals(that.internalStorageUsed) : that.internalStorageUsed != null)
            return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (manufacturer != null ? !manufacturer.equals(that.manufacturer) : that.manufacturer != null) return false;
        if (mdmEnabled != null ? !mdmEnabled.equals(that.mdmEnabled) : that.mdmEnabled != null) return false;
        if (mobileVendor != null ? !mobileVendor.equals(that.mobileVendor) : that.mobileVendor != null) return false;
        if (netSpeed != null ? !netSpeed.equals(that.netSpeed) : that.netSpeed != null) return false;
        if (platform != null ? !platform.equals(that.platform) : that.platform != null) return false;
        if (procList != null ? !procList.equals(that.procList) : that.procList != null) return false;
        if (ramTotal != null ? !ramTotal.equals(that.ramTotal) : that.ramTotal != null) return false;
        if (ramUsed != null ? !ramUsed.equals(that.ramUsed) : that.ramUsed != null) return false;
        if (roamingFlag != null ? !roamingFlag.equals(that.roamingFlag) : that.roamingFlag != null) return false;
        if (securityFlag != null ? !securityFlag.equals(that.securityFlag) : that.securityFlag != null) return false;
        if (usbDebugging != null ? !usbDebugging.equals(that.usbDebugging) : that.usbDebugging != null) return false;
        if (deviceSecurityStatus != null ? !deviceSecurityStatus.equals(that.deviceSecurityStatus) : that.deviceSecurityStatus != null) return false;
        if (clientVersion != null ? !clientVersion.equals(that.clientVersion) : that.clientVersion != null) return false;
        return !(trafficBytes != null ? !trafficBytes.equals(that.trafficBytes) : that.trafficBytes != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (platform != null ? platform.hashCode() : 0);
        result = 31 * result + (mobileVendor != null ? mobileVendor.hashCode() : 0);
        result = 31 * result + (deviceMode != null ? deviceMode.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (securityFlag != null ? securityFlag.hashCode() : 0);
        result = 31 * result + (ramTotal != null ? ramTotal.hashCode() : 0);
        result = 31 * result + (ramUsed != null ? ramUsed.hashCode() : 0);
        result = 31 * result + (diskTotal != null ? diskTotal.hashCode() : 0);
        result = 31 * result + (diskUsed != null ? diskUsed.hashCode() : 0);
        result = 31 * result + (cpuRatio != null ? cpuRatio.hashCode() : 0);
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (manufacturer != null ? manufacturer.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (roamingFlag != null ? roamingFlag.hashCode() : 0);
        result = 31 * result + (displaySize != null ? displaySize.hashCode() : 0);
        result = 31 * result + (internalStorageTotal != null ? internalStorageTotal.hashCode() : 0);
        result = 31 * result + (internalStorageUsed != null ? internalStorageUsed.hashCode() : 0);
        result = 31 * result + (freeRamSize != null ? freeRamSize.hashCode() : 0);
        result = 31 * result + (freeDiskSize != null ? freeDiskSize.hashCode() : 0);
        result = 31 * result + (freeInternalStorageSize != null ? freeInternalStorageSize.hashCode() : 0);
        result = 31 * result + (mdmEnabled != null ? mdmEnabled.hashCode() : 0);
        result = 31 * result + (usbDebugging != null ? usbDebugging.hashCode() : 0);
        result = 31 * result + (deviceStatus != null ? deviceStatus.hashCode() : 0);
        result = 31 * result + (netSpeed != null ? netSpeed.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (procList != null ? procList.hashCode() : 0);
        result = 31 * result + (installInfos != null ? installInfos.hashCode() : 0);
        result = 31 * result + (deviceSecurityStatus != null ? deviceSecurityStatus.hashCode() : 0);
        result = 31 * result + (clientVersion != null ? clientVersion.hashCode() : 0);
        result = 31 * result + (trafficBytes != null ? trafficBytes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DeviceInfoRespSU{" +
                "super='" + super.toString() + '\'' +
                "platform='" + platform + '\'' +
                ", mobileVendor='" + mobileVendor + '\'' +
                ", deviceMode='" + deviceMode + '\'' +
                ", language='" + language + '\'' +
                ", securityFlag='" + securityFlag + '\'' +
                ", ramTotal='" + ramTotal + '\'' +
                ", ramUsed='" + ramUsed + '\'' +
                ", diskTotal='" + diskTotal + '\'' +
                ", diskUsed='" + diskUsed + '\'' +
                ", cpuRatio=" + cpuRatio +
                ", deviceId='" + deviceId + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", brand='" + brand + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", roamingFlag='" + roamingFlag + '\'' +
                ", displaySize='" + displaySize + '\'' +
                ", internalStorageTotal=" + internalStorageTotal +
                ", internalStorageUsed=" + internalStorageUsed +
                ", freeRamSize=" + freeRamSize +
                ", freeDiskSize=" + freeDiskSize +
                ", freeInternalStorageSize=" + freeInternalStorageSize +
                ", mdmEnabled=" + mdmEnabled +
                ", usbDebugging=" + usbDebugging +
                ", deviceStatus='" + deviceStatus + '\'' +
                ", deviceSecurityStatus='" + deviceSecurityStatus + '\'' +
                ", clientVersion='" + clientVersion + '\'' +
                ", trafficBytes='" + trafficBytes + '\'' +
                ", netSpeed=" + netSpeed +
                ", location=" + location +
                ", procList=" + procList +
                ", installInfos=" + installInfos +
                '}';
    }
}
