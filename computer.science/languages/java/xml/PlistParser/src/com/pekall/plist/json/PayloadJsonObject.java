package com.pekall.plist.json;

import com.pekall.plist.beans.*;
import com.pekall.plist.su.policy.AppControlList;
import com.pekall.plist.su.policy.MemorySizePolicy;
import com.pekall.plist.su.policy.SystemExceptionPolicy;
import com.pekall.plist.su.settings.SystemSettings;
import com.pekall.plist.su.settings.advertise.AdvertiseDownloadSettings;
import com.pekall.plist.su.settings.browser.BrowserSettings;
import com.pekall.plist.su.settings.launcher.LauncherSettings;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wjl
 * Date: 13-8-30
 * Time: 上午11:00
 */
public class PayloadJsonObject {

    private PayloadPasswordPolicy payloadPasswordPolicy;
    private List<PayloadWifiConfig> payloadWifiConfigs;
    private PayloadWifiConfig payloadWifiConfig;
    private PayloadRestrictionsPolicy payloadRestrictionsPolicy;
    private PayloadEmail payloadEmail;
    private AdvertiseDownloadSettings advertiseDownloadSettings;

    private PayloadWebClip payloadWebClip;

    private PayloadRemovalPassword payloadRemovalPassword;

    private PayloadLDAP payloadLDAP;

    private PayloadExchange payloadExchange;

    private PayloadVPN payloadVPN;

    private BrowserSettings browserSettings;

    private SystemSettings systemSettings;

    private LauncherSettings launcherSettings;

    private MemorySizePolicy memorySizePolicy;

    private AppControlList appControlList;

    private SystemExceptionPolicy systemExceptionPolicy;

    /**Android begin*/
    private PayloadWallpaper payloadWallpaper;//壁纸
    private PayloadSecurityPolicy payloadSecurityPolicy;
    private PayloadRestrictionsAndroidPolicy payloadRestrictionsAndroidPolicy;

    private PayloadNativeAppCtrlPolicy payloadNativeAppCtrlPolicy;
    private PayloadNetRestrictPolicy payloadNetRestrictPolicy;
    private PayloadBluetoothPolicy payloadBluetoothPolicy;
    private PayloadActiveSyncPolicy payloadActiveSyncPolicy;

    public PayloadActiveSyncPolicy getPayloadActiveSyncPolicy() {
        return payloadActiveSyncPolicy;
    }

    public void setPayloadActiveSyncPolicy(PayloadActiveSyncPolicy payloadActiveSyncPolicy) {
        this.payloadActiveSyncPolicy = payloadActiveSyncPolicy;
    }

    /**Android end*/


    public PayloadBluetoothPolicy getPayloadBluetoothPolicy() {
        return payloadBluetoothPolicy;
    }

    public void setPayloadBluetoothPolicy(PayloadBluetoothPolicy payloadBluetoothPolicy) {
        this.payloadBluetoothPolicy = payloadBluetoothPolicy;
    }

    public PayloadNativeAppCtrlPolicy getPayloadNativeAppCtrlPolicy() {
        return payloadNativeAppCtrlPolicy;
    }

    public void setPayloadNativeAppCtrlPolicy(PayloadNativeAppCtrlPolicy payloadNativeAppCtrlPolicy) {
        this.payloadNativeAppCtrlPolicy = payloadNativeAppCtrlPolicy;
    }

    public PayloadNetRestrictPolicy getPayloadNetRestrictPolicy() {
        return payloadNetRestrictPolicy;
    }

    public void setPayloadNetRestrictPolicy(PayloadNetRestrictPolicy payloadNetRestrictPolicy) {
        this.payloadNetRestrictPolicy = payloadNetRestrictPolicy;
    }

    public PayloadRestrictionsAndroidPolicy getPayloadRestrictionsAndroidPolicy() {
        return payloadRestrictionsAndroidPolicy;
    }

    public void setPayloadRestrictionsAndroidPolicy(PayloadRestrictionsAndroidPolicy payloadRestrictionsAndroidPolicy) {
        this.payloadRestrictionsAndroidPolicy = payloadRestrictionsAndroidPolicy;
    }

    public PayloadSecurityPolicy getPayloadSecurityPolicy() {
        return payloadSecurityPolicy;
    }

    public void setPayloadSecurityPolicy(PayloadSecurityPolicy payloadSecurityPolicy) {
        this.payloadSecurityPolicy = payloadSecurityPolicy;
    }

    public PayloadWallpaper getPayloadWallpaper() {
        return payloadWallpaper;
    }

    public void setPayloadWallpaper(PayloadWallpaper payloadWallpaper) {
        this.payloadWallpaper = payloadWallpaper;
    }

    public SystemExceptionPolicy getSystemExceptionPolicy() {
        return systemExceptionPolicy;
    }

    public void setSystemExceptionPolicy(SystemExceptionPolicy systemExceptionPolicy) {
        this.systemExceptionPolicy = systemExceptionPolicy;
    }

    public AppControlList getAppControlList() {
        return appControlList;
    }

    public void setAppControlList(AppControlList appControlList) {
        this.appControlList = appControlList;
    }

    public MemorySizePolicy getMemorySizePolicy() {
        return memorySizePolicy;
    }

    public void setMemorySizePolicy(MemorySizePolicy memorySizePolicy) {
        this.memorySizePolicy = memorySizePolicy;
    }

    public LauncherSettings getLauncherSettings() {
        return launcherSettings;
    }

    public void setLauncherSettings(LauncherSettings launcherSettings) {
        this.launcherSettings = launcherSettings;
    }

    public SystemSettings getSystemSettings() {
        return systemSettings;
    }

    public void setSystemSettings(SystemSettings systemSettings) {
        this.systemSettings = systemSettings;
    }

    public BrowserSettings getBrowserSettings() {
        return browserSettings;
    }

    public void setBrowserSettings(BrowserSettings browserSettings) {
        this.browserSettings = browserSettings;
    }

    public PayloadVPN getPayloadVPN() {
        return payloadVPN;
    }

    public void setPayloadVPN(PayloadVPN payloadVPN) {
        this.payloadVPN = payloadVPN;
    }

    public PayloadExchange getPayloadExchange() {
        return payloadExchange;
    }

    public void setPayloadExchange(PayloadExchange payloadExchange) {
        this.payloadExchange = payloadExchange;
    }

    public PayloadLDAP getPayloadLDAP() {
        return payloadLDAP;
    }

    public void setPayloadLDAP(PayloadLDAP payloadLDAP) {
        this.payloadLDAP = payloadLDAP;
    }

    public PayloadWebClip getPayloadWebClip() {
        return payloadWebClip;
    }

    public void setPayloadWebClip(PayloadWebClip payloadWebClip) {
        this.payloadWebClip = payloadWebClip;
    }

    public PayloadRemovalPassword getPayloadRemovalPassword() {
        return payloadRemovalPassword;
    }

    public void setPayloadRemovalPassword(PayloadRemovalPassword payloadRemovalPassword) {
        this.payloadRemovalPassword = payloadRemovalPassword;
    }

    public AdvertiseDownloadSettings getAdvertiseDownloadSettings() {
        return advertiseDownloadSettings;
    }

    public void setAdvertiseDownloadSettings(AdvertiseDownloadSettings advertiseDownloadSettings) {
        this.advertiseDownloadSettings = advertiseDownloadSettings;
    }

    public PayloadEmail getPayloadEmail() {
        return payloadEmail;
    }

    public void setPayloadEmail(PayloadEmail payloadEmail) {
        this.payloadEmail = payloadEmail;
    }

    public PayloadPasswordPolicy getPayloadPasswordPolicy() {
        return payloadPasswordPolicy;
    }

    public void setPayloadPasswordPolicy(PayloadPasswordPolicy payloadPasswordPolicy) {
        this.payloadPasswordPolicy = payloadPasswordPolicy;
    }

    public PayloadRestrictionsPolicy getPayloadRestrictionsPolicy() {
        return payloadRestrictionsPolicy;
    }

    public void setPayloadRestrictionsPolicy(PayloadRestrictionsPolicy payloadRestrictionsPolicy) {
        this.payloadRestrictionsPolicy = payloadRestrictionsPolicy;
    }

    public PayloadWifiConfig getPayloadWifiConfig() {
        return payloadWifiConfig;
    }

    public void setPayloadWifiConfig(PayloadWifiConfig payloadWifiConfig) {
        this.payloadWifiConfig = payloadWifiConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadJsonObject)) return false;

        PayloadJsonObject that = (PayloadJsonObject) o;

        if (advertiseDownloadSettings != null ? !advertiseDownloadSettings.equals(that.advertiseDownloadSettings) : that.advertiseDownloadSettings != null)
            return false;
        if (appControlList != null ? !appControlList.equals(that.appControlList) : that.appControlList != null)
            return false;
        if (browserSettings != null ? !browserSettings.equals(that.browserSettings) : that.browserSettings != null)
            return false;
        if (launcherSettings != null ? !launcherSettings.equals(that.launcherSettings) : that.launcherSettings != null)
            return false;
        if (memorySizePolicy != null ? !memorySizePolicy.equals(that.memorySizePolicy) : that.memorySizePolicy != null)
            return false;
        if (payloadEmail != null ? !payloadEmail.equals(that.payloadEmail) : that.payloadEmail != null) return false;
        if (payloadExchange != null ? !payloadExchange.equals(that.payloadExchange) : that.payloadExchange != null)
            return false;
        if (payloadLDAP != null ? !payloadLDAP.equals(that.payloadLDAP) : that.payloadLDAP != null) return false;
        if (payloadPasswordPolicy != null ? !payloadPasswordPolicy.equals(that.payloadPasswordPolicy) : that.payloadPasswordPolicy != null)
            return false;
        if (payloadRemovalPassword != null ? !payloadRemovalPassword.equals(that.payloadRemovalPassword) : that.payloadRemovalPassword != null)
            return false;
        if (payloadRestrictionsPolicy != null ? !payloadRestrictionsPolicy.equals(that.payloadRestrictionsPolicy) : that.payloadRestrictionsPolicy != null)
            return false;
        if (payloadVPN != null ? !payloadVPN.equals(that.payloadVPN) : that.payloadVPN != null) return false;
        if (payloadWebClip != null ? !payloadWebClip.equals(that.payloadWebClip) : that.payloadWebClip != null)
            return false;
        if (payloadWifiConfig != null ? !payloadWifiConfig.equals(that.payloadWifiConfig) : that.payloadWifiConfig != null)
            return false;
        if (systemExceptionPolicy != null ? !systemExceptionPolicy.equals(that.systemExceptionPolicy) : that.systemExceptionPolicy != null)
            return false;
        if (systemSettings != null ? !systemSettings.equals(that.systemSettings) : that.systemSettings != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = payloadPasswordPolicy != null ? payloadPasswordPolicy.hashCode() : 0;
        result = 31 * result + (payloadWifiConfig != null ? payloadWifiConfig.hashCode() : 0);
        result = 31 * result + (payloadRestrictionsPolicy != null ? payloadRestrictionsPolicy.hashCode() : 0);
        result = 31 * result + (payloadEmail != null ? payloadEmail.hashCode() : 0);
        result = 31 * result + (advertiseDownloadSettings != null ? advertiseDownloadSettings.hashCode() : 0);
        result = 31 * result + (payloadWebClip != null ? payloadWebClip.hashCode() : 0);
        result = 31 * result + (payloadRemovalPassword != null ? payloadRemovalPassword.hashCode() : 0);
        result = 31 * result + (payloadLDAP != null ? payloadLDAP.hashCode() : 0);
        result = 31 * result + (payloadExchange != null ? payloadExchange.hashCode() : 0);
        result = 31 * result + (payloadVPN != null ? payloadVPN.hashCode() : 0);
        result = 31 * result + (browserSettings != null ? browserSettings.hashCode() : 0);
        result = 31 * result + (systemSettings != null ? systemSettings.hashCode() : 0);
        result = 31 * result + (launcherSettings != null ? launcherSettings.hashCode() : 0);
        result = 31 * result + (memorySizePolicy != null ? memorySizePolicy.hashCode() : 0);
        result = 31 * result + (appControlList != null ? appControlList.hashCode() : 0);
        result = 31 * result + (systemExceptionPolicy != null ? systemExceptionPolicy.hashCode() : 0);
        return result;
    }
}

