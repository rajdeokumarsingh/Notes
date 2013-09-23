package com.pekall.plist.json;

import com.pekall.plist.beans.*;
import com.pekall.plist.su.policy.AppControlList;
import com.pekall.plist.su.policy.MemorySizePolicy;
import com.pekall.plist.su.policy.SystemExceptionPolicy;
import com.pekall.plist.su.settings.SystemSettings;
import com.pekall.plist.su.settings.advertise.AdvertiseDownloadSettings;
import com.pekall.plist.su.settings.browser.BrowserSettings;
import com.pekall.plist.su.settings.launcher.LauncherSettings;

import java.util.ArrayList;
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

    private PayloadRestrictionsPolicy payloadRestrictionsPolicy;
    private List<PayloadEmail> payloadEmails;

    private List<AdvertiseDownloadSettings> advertiseDownloadSettingses;
    private List<PayloadWebClip> payloadWebClips;

    private PayloadRemovalPassword payloadRemovalPassword;

    private List<PayloadLDAP> payloadLDAPs;
    private List<PayloadExchange> payloadExchanges;

    private List<PayloadVPN> payloadVPNs;

    private BrowserSettings browserSettings;

    private SystemSettings systemSettings;

    private LauncherSettings launcherSettings;

    private MemorySizePolicy memorySizePolicy;

    private AppControlList appControlList;

    private SystemExceptionPolicy systemExceptionPolicy;

    /**
     * Android begin
     */
    private PayloadWallpaper payloadWallpaper;//壁纸
    private PayloadSecurityPolicy payloadSecurityPolicy;
    private PayloadRestrictionsAndroidPolicy payloadRestrictionsAndroidPolicy;

    private PayloadNativeAppCtrlPolicy payloadNativeAppCtrlPolicy;
    private PayloadNetRestrictPolicy payloadNetRestrictPolicy;
    private PayloadBluetoothPolicy payloadBluetoothPolicy;
    private PayloadActiveSyncPolicy payloadActiveSyncPolicy;

    private PayloadRoamingPolicy payloadRoamingPolicy;

    public List<AdvertiseDownloadSettings> getAdvertiseDownloadSettingses() {
        return advertiseDownloadSettingses;
    }

    public void setAdvertiseDownloadSettingses(List<AdvertiseDownloadSettings> advertiseDownloadSettingses) {
        this.advertiseDownloadSettingses = advertiseDownloadSettingses;
    }

    public void addAdvertiseDownloadSettings(AdvertiseDownloadSettings payloadVPN) {
        if (this.advertiseDownloadSettingses == null) {
            this.advertiseDownloadSettingses = new ArrayList<AdvertiseDownloadSettings>();
        }
        this.advertiseDownloadSettingses.add(payloadVPN);
    }

    public List<PayloadVPN> getPayloadVPNs() {
        return payloadVPNs;
    }

    public void setPayloadVPNs(List<PayloadVPN> payloadVPNs) {
        this.payloadVPNs = payloadVPNs;
    }

    public void addPayloadVPN(PayloadVPN payloadVPN) {
        if (this.payloadVPNs == null) {
            this.payloadVPNs = new ArrayList<PayloadVPN>();
        }
        this.payloadVPNs.add(payloadVPN);
    }

    public List<PayloadExchange> getPayloadExchanges() {
        return payloadExchanges;
    }

    public void setPayloadExchanges(List<PayloadExchange> payloadExchanges) {
        this.payloadExchanges = payloadExchanges;
    }

    public void addPayloadExchange(PayloadExchange payloadExchange) {
        if (this.payloadExchanges == null) {
            this.payloadExchanges = new ArrayList<PayloadExchange>();
        }
        this.payloadExchanges.add(payloadExchange);
    }

    public List<PayloadLDAP> getPayloadLDAPs() {
        return payloadLDAPs;
    }

    public void setPayloadLDAPs(List<PayloadLDAP> payloadLDAPs) {
        this.payloadLDAPs = payloadLDAPs;
    }

    public void addPayloadLDAP(PayloadLDAP payloadLDAP) {
        if (this.payloadLDAPs == null) {
            this.payloadLDAPs = new ArrayList<PayloadLDAP>();
        }
        this.payloadLDAPs.add(payloadLDAP);
    }

    public List<PayloadWebClip> getPayloadWebClips() {
        return payloadWebClips;
    }

    public void setPayloadWebClips(List<PayloadWebClip> payloadWebClips) {
        this.payloadWebClips = payloadWebClips;
    }

    public void addPayloadWebClip(PayloadWebClip payloadWebClip) {
        if (this.payloadWebClips == null) {
            this.payloadWebClips = new ArrayList<PayloadWebClip>();
        }
        this.payloadWebClips.add(payloadWebClip);
    }

    public List<PayloadEmail> getPayloadEmails() {
        return payloadEmails;
    }

    public void setPayloadEmails(List<PayloadEmail> payloadEmails) {
        this.payloadEmails = payloadEmails;
    }

    public void addPayloadEmail(PayloadEmail payloadEmail) {
        if (this.payloadEmails == null) {
            this.payloadEmails = new ArrayList<PayloadEmail>();
        }
        this.payloadEmails.add(payloadEmail);
    }

    public List<PayloadWifiConfig> getPayloadWifiConfigs() {
        return payloadWifiConfigs;
    }

    public void setPayloadWifiConfigs(List<PayloadWifiConfig> payloadWifiConfigs) {
        this.payloadWifiConfigs = payloadWifiConfigs;
    }

    public void addPayloadWifiConfig(PayloadWifiConfig payloadWifiConfig) {
        if (this.payloadWifiConfigs == null) {
            this.payloadWifiConfigs = new ArrayList<PayloadWifiConfig>();
        }
        this.payloadWifiConfigs.add(payloadWifiConfig);
    }

    public PayloadRoamingPolicy getPayloadRoamingPolicy() {
        return payloadRoamingPolicy;
    }

    public void setPayloadRoamingPolicy(PayloadRoamingPolicy payloadRoamingPolicy) {
        this.payloadRoamingPolicy = payloadRoamingPolicy;
    }

    public PayloadActiveSyncPolicy getPayloadActiveSyncPolicy() {
        return payloadActiveSyncPolicy;
    }

    public void setPayloadActiveSyncPolicy(PayloadActiveSyncPolicy payloadActiveSyncPolicy) {
        this.payloadActiveSyncPolicy = payloadActiveSyncPolicy;
    }

    /**
     * Android end
     */


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


    public PayloadRemovalPassword getPayloadRemovalPassword() {
        return payloadRemovalPassword;
    }

    public void setPayloadRemovalPassword(PayloadRemovalPassword payloadRemovalPassword) {
        this.payloadRemovalPassword = payloadRemovalPassword;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PayloadJsonObject that = (PayloadJsonObject) o;

        if (advertiseDownloadSettingses != null ? !advertiseDownloadSettingses.equals(that.advertiseDownloadSettingses) : that.advertiseDownloadSettingses != null)
            return false;
        if (appControlList != null ? !appControlList.equals(that.appControlList) : that.appControlList != null)
            return false;
        if (browserSettings != null ? !browserSettings.equals(that.browserSettings) : that.browserSettings != null)
            return false;
        if (launcherSettings != null ? !launcherSettings.equals(that.launcherSettings) : that.launcherSettings != null)
            return false;
        if (memorySizePolicy != null ? !memorySizePolicy.equals(that.memorySizePolicy) : that.memorySizePolicy != null)
            return false;
        if (payloadActiveSyncPolicy != null ? !payloadActiveSyncPolicy.equals(that.payloadActiveSyncPolicy) : that.payloadActiveSyncPolicy != null)
            return false;
        if (payloadBluetoothPolicy != null ? !payloadBluetoothPolicy.equals(that.payloadBluetoothPolicy) : that.payloadBluetoothPolicy != null)
            return false;
        if (payloadEmails != null ? !payloadEmails.equals(that.payloadEmails) : that.payloadEmails != null)
            return false;
        if (payloadExchanges != null ? !payloadExchanges.equals(that.payloadExchanges) : that.payloadExchanges != null)
            return false;
        if (payloadLDAPs != null ? !payloadLDAPs.equals(that.payloadLDAPs) : that.payloadLDAPs != null) return false;
        if (payloadNativeAppCtrlPolicy != null ? !payloadNativeAppCtrlPolicy.equals(that.payloadNativeAppCtrlPolicy) : that.payloadNativeAppCtrlPolicy != null)
            return false;
        if (payloadNetRestrictPolicy != null ? !payloadNetRestrictPolicy.equals(that.payloadNetRestrictPolicy) : that.payloadNetRestrictPolicy != null)
            return false;
        if (payloadPasswordPolicy != null ? !payloadPasswordPolicy.equals(that.payloadPasswordPolicy) : that.payloadPasswordPolicy != null)
            return false;
        if (payloadRemovalPassword != null ? !payloadRemovalPassword.equals(that.payloadRemovalPassword) : that.payloadRemovalPassword != null)
            return false;
        if (payloadRestrictionsAndroidPolicy != null ? !payloadRestrictionsAndroidPolicy.equals(that.payloadRestrictionsAndroidPolicy) : that.payloadRestrictionsAndroidPolicy != null)
            return false;
        if (payloadRestrictionsPolicy != null ? !payloadRestrictionsPolicy.equals(that.payloadRestrictionsPolicy) : that.payloadRestrictionsPolicy != null)
            return false;
        if (payloadRoamingPolicy != null ? !payloadRoamingPolicy.equals(that.payloadRoamingPolicy) : that.payloadRoamingPolicy != null)
            return false;
        if (payloadSecurityPolicy != null ? !payloadSecurityPolicy.equals(that.payloadSecurityPolicy) : that.payloadSecurityPolicy != null)
            return false;
        if (payloadVPNs != null ? !payloadVPNs.equals(that.payloadVPNs) : that.payloadVPNs != null) return false;
        if (payloadWallpaper != null ? !payloadWallpaper.equals(that.payloadWallpaper) : that.payloadWallpaper != null)
            return false;
        if (payloadWebClips != null ? !payloadWebClips.equals(that.payloadWebClips) : that.payloadWebClips != null)
            return false;
        if (payloadWifiConfigs != null ? !payloadWifiConfigs.equals(that.payloadWifiConfigs) : that.payloadWifiConfigs != null)
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
        result = 31 * result + (payloadWifiConfigs != null ? payloadWifiConfigs.hashCode() : 0);
        result = 31 * result + (payloadRestrictionsPolicy != null ? payloadRestrictionsPolicy.hashCode() : 0);
        result = 31 * result + (payloadEmails != null ? payloadEmails.hashCode() : 0);
        result = 31 * result + (advertiseDownloadSettingses != null ? advertiseDownloadSettingses.hashCode() : 0);
        result = 31 * result + (payloadWebClips != null ? payloadWebClips.hashCode() : 0);
        result = 31 * result + (payloadRemovalPassword != null ? payloadRemovalPassword.hashCode() : 0);
        result = 31 * result + (payloadLDAPs != null ? payloadLDAPs.hashCode() : 0);
        result = 31 * result + (payloadExchanges != null ? payloadExchanges.hashCode() : 0);
        result = 31 * result + (payloadVPNs != null ? payloadVPNs.hashCode() : 0);
        result = 31 * result + (browserSettings != null ? browserSettings.hashCode() : 0);
        result = 31 * result + (systemSettings != null ? systemSettings.hashCode() : 0);
        result = 31 * result + (launcherSettings != null ? launcherSettings.hashCode() : 0);
        result = 31 * result + (memorySizePolicy != null ? memorySizePolicy.hashCode() : 0);
        result = 31 * result + (appControlList != null ? appControlList.hashCode() : 0);
        result = 31 * result + (systemExceptionPolicy != null ? systemExceptionPolicy.hashCode() : 0);
        result = 31 * result + (payloadWallpaper != null ? payloadWallpaper.hashCode() : 0);
        result = 31 * result + (payloadSecurityPolicy != null ? payloadSecurityPolicy.hashCode() : 0);
        result = 31 * result + (payloadRestrictionsAndroidPolicy != null ? payloadRestrictionsAndroidPolicy.hashCode() : 0);
        result = 31 * result + (payloadNativeAppCtrlPolicy != null ? payloadNativeAppCtrlPolicy.hashCode() : 0);
        result = 31 * result + (payloadNetRestrictPolicy != null ? payloadNetRestrictPolicy.hashCode() : 0);
        result = 31 * result + (payloadBluetoothPolicy != null ? payloadBluetoothPolicy.hashCode() : 0);
        result = 31 * result + (payloadActiveSyncPolicy != null ? payloadActiveSyncPolicy.hashCode() : 0);
        result = 31 * result + (payloadRoamingPolicy != null ? payloadRoamingPolicy.hashCode() : 0);
        return result;
    }
}

