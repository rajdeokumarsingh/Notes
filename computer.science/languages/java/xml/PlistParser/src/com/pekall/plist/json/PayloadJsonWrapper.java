package com.pekall.plist.json;

import com.pekall.plist.beans.*;
import com.pekall.plist.su.policy.AppControlList;
import com.pekall.plist.su.policy.MemorySizePolicy;
import com.pekall.plist.su.policy.SystemExceptionPolicy;
import com.pekall.plist.su.settings.SystemSettings;
import com.pekall.plist.su.settings.advertise.AdvertiseDownloadSettings;
import com.pekall.plist.su.settings.browser.BrowserSettings;
import com.pekall.plist.su.settings.launcher.LauncherSettings;

import java.util.Date;
import java.util.List;

/**
 * PayloadArrayWrapper的一个json封装
 */
public class PayloadJsonWrapper extends PayloadBase {

    public PayloadJsonWrapper(PayloadArrayWrapper arrayWrapper) {
        this.setPayloadType(arrayWrapper.getPayloadType());
        this.setPayloadVersion(arrayWrapper.getPayloadVersion());
        this.setPayloadIdentifier(arrayWrapper.getPayloadIdentifier());
        this.setPayloadUUID(arrayWrapper.getPayloadUUID());
        this.setPayloadDisplayName(arrayWrapper.getPayloadDisplayName());
        this.setPayloadDescription(arrayWrapper.getPayloadDescription());
        this.setPayloadOrganization(arrayWrapper.getPayloadOrganization());

        this.setHasRemovalPasscode(this.getHasRemovalPasscode());
        this.setIsEncrypted(this.getIsEncrypted());
        this.setPayloadRemovalDisallowed(this.getPayloadRemovalDisallowed());
        this.setPayloadScope(this.getPayloadScope());
        this.setRemovalDate(this.getRemovalDate());
        this.setDurationUntilRemoval(this.getDurationUntilRemoval());
        List<PayloadBase> payloadContent = arrayWrapper.getPayloadContent();
        if (payloadContent != null) {
            this.PayloadContent = new PayloadJsonObject();

            for (PayloadBase payload : payloadContent) {
                if (PayloadBase.PAYLOAD_TYPE_EMAIL.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadEmail((PayloadEmail) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_WIFI_MANAGED.equalsIgnoreCase(payload.getPayloadType())) {

//                    this.PayloadContent.addPayloadWifiConfig((PayloadWifiConfig) payload);
                    this.PayloadContent.setPayloadWifiConfig((PayloadWifiConfig) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_PASSWORD_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadPasswordPolicy((PayloadPasswordPolicy) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_RESTRICTIONS.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadRestrictionsPolicy((PayloadRestrictionsPolicy) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_REMOVAL_PASSWORD.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadRemovalPassword((PayloadRemovalPassword) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_WEB_CLIP.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadWebClip((PayloadWebClip) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_LDAP.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadLDAP((PayloadLDAP) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_IOS_EXCHANGE.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadExchange((PayloadExchange) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_VPN.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadVPN((PayloadVPN) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_SE_BROWSER_SETTINGS.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setBrowserSettings((BrowserSettings) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_SYSTEM_SETTINGS.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setSystemSettings((SystemSettings) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_LAUNCHER_SETTINGS.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setLauncherSettings((LauncherSettings) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_ADVT_SETTINGS.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setAdvertiseDownloadSettings((AdvertiseDownloadSettings) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_MEMORY_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setMemorySizePolicy((MemorySizePolicy) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_APP_CONTROL_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setAppControlList((AppControlList) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_SYSTEM_EXCEPTION_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setSystemExceptionPolicy((SystemExceptionPolicy) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_SECURITY_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadSecurityPolicy((PayloadSecurityPolicy)payload);
                }else if (PayloadBase.PAYLOAD_TYPE_RESTRICTIONS_ANDROID_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadRestrictionsAndroidPolicy((PayloadRestrictionsAndroidPolicy)payload);
                }
                else if (PayloadBase.PAYLOAD_TYPE_NATIVE_APP_CONTROL_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadNativeAppCtrlPolicy((PayloadNativeAppCtrlPolicy)payload);
                }
                else if (PayloadBase.PAYLOAD_TYPE_BLUETOOTH_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadBluetoothPolicy((PayloadBluetoothPolicy)payload);
                }
                else if (PayloadBase.PAYLOAD_TYPE_NET_RESTRICT_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadNetRestrictPolicy((PayloadNetRestrictPolicy)payload);
                }
                else if (PayloadBase.PAYLOAD_TYPE_ACTIVE_SYNC_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadActiveSyncPolicy((PayloadActiveSyncPolicy)payload);
                }
                else if (PayloadBase.PAYLOAD_TYPE_WALLPAPER_SETTINGS.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadWallpaper((PayloadWallpaper)payload);
                }
                else if (PayloadBase.PAYLOAD_TYPE_ROAMING_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadRoamingPolicy((PayloadRoamingPolicy) payload);
                }
            }
        }
    }

    /**
     * Optional. Set to true if there is a removal passcode.
     */
    private Boolean HasRemovalPasscode;

    /**
     * Optional. Set to true if the profile is encrypted.
     */
    private Boolean IsEncrypted;

    /**
     * Optional. Array of payload dictionaries. Not present if IsEncrypted is true.
     */
    private PayloadJsonObject PayloadContent;

    /**
     * Optional. If present and set to true, the user cannot delete the profile
     * (unless the profile has a removal password and the user provides it).
     */
    private Boolean PayloadRemovalDisallowed;

    /**
     * Determines if the profile should be installed for the system or the user.
     */
    private Boolean PayloadScope;

    /**
     * Optional. The date on which the profile will be automatically removed.
     */
    private Date RemovalDate;

    /**
     * FIXME: seem it should be a Long or Integer
     * Optional. Number of seconds until the profile is automatically removed.
     */
    private Float DurationUntilRemoval;

    // TODO: can not find in document
    // private Dictionary ConsentText;

    public PayloadJsonWrapper() {
        super();
    }
//
//    public void addPayLoadContent(PayloadBase content) {
//        if (PayloadContent == null) {
//            PayloadContent = new ArrayList<PayloadBase>();
//        }
//        PayloadContent.add(content);
//    }

    public Boolean getHasRemovalPasscode() {
        return HasRemovalPasscode;
    }

    public void setHasRemovalPasscode(Boolean hasRemovalPasscode) {
        HasRemovalPasscode = hasRemovalPasscode;
    }

    public Boolean getIsEncrypted() {
        return IsEncrypted;
    }

    public void setIsEncrypted(Boolean isEncrypted) {
        IsEncrypted = isEncrypted;
    }

    public PayloadJsonObject getPayloadContent() {
        return PayloadContent;
    }

    public void setPayloadContent(PayloadJsonObject payloadContent) {
        PayloadContent = payloadContent;
    }

    //    public List<PayloadBase> getPayloadContent() {
//        return PayloadContent;
//    }
//
//    public void setPayloadContent(List<PayloadBase> payloadContent) {
//        PayloadContent = payloadContent;
//    }

    public Boolean getPayloadRemovalDisallowed() {
        return PayloadRemovalDisallowed;
    }

    public void setPayloadRemovalDisallowed(Boolean payloadRemovalDisallowed) {
        PayloadRemovalDisallowed = payloadRemovalDisallowed;
    }

    public Boolean getPayloadScope() {
        return PayloadScope;
    }

    public void setPayloadScope(Boolean payloadScope) {
        PayloadScope = payloadScope;
    }

    public Date getRemovalDate() {
        return RemovalDate;
    }

    public void setRemovalDate(Date removalDate) {
        RemovalDate = removalDate;
    }

    public Float getDurationUntilRemoval() {
        return DurationUntilRemoval;
    }

    public void setDurationUntilRemoval(Float durationUntilRemoval) {
        DurationUntilRemoval = durationUntilRemoval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadJsonWrapper)) return false;
        if (!super.equals(o)) return false;

        PayloadJsonWrapper that = (PayloadJsonWrapper) o;

        if(this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (HasRemovalPasscode != null ? HasRemovalPasscode.hashCode() : 0);
        result = 31 * result + (IsEncrypted != null ? IsEncrypted.hashCode() : 0);
        result = 31 * result + (PayloadContent != null ? PayloadContent.hashCode() : 0);
        result = 31 * result + (PayloadRemovalDisallowed != null ? PayloadRemovalDisallowed.hashCode() : 0);
        result = 31 * result + (PayloadScope != null ? PayloadScope.hashCode() : 0);
        result = 31 * result + (RemovalDate != null ? RemovalDate.hashCode() : 0);
        result = 31 * result + (DurationUntilRemoval != null ? DurationUntilRemoval.hashCode() : 0);
        return result;
    }

    public PayloadArrayWrapper getPayloadArrayWrapper() {
        PayloadArrayWrapper wrapper = new PayloadArrayWrapper();
        wrapper.setPayloadType(this.getPayloadType());
        wrapper.setPayloadVersion(this.getPayloadVersion());
        wrapper.setPayloadIdentifier(this.getPayloadIdentifier());
        wrapper.setPayloadUUID(this.getPayloadUUID());
        wrapper.setPayloadDisplayName(this.getPayloadDisplayName());
        wrapper.setPayloadDescription(this.getPayloadDescription());
        wrapper.setPayloadOrganization(this.getPayloadOrganization());

        wrapper.setHasRemovalPasscode(this.getHasRemovalPasscode());
        wrapper.setIsEncrypted(this.getIsEncrypted());
        wrapper.setPayloadRemovalDisallowed(this.getPayloadRemovalDisallowed());
        wrapper.setPayloadScope(this.getPayloadScope());
        wrapper.setRemovalDate(this.getRemovalDate());
        wrapper.setDurationUntilRemoval(this.getDurationUntilRemoval());
        PayloadJsonObject payloadContent = this.getPayloadContent();


        PayloadEmail payloadEmail = payloadContent.getPayloadEmail();
        if(payloadEmail != null){
            wrapper.addPayLoadContent(payloadEmail);
        }

//        List<PayloadWifiConfig> payloadWifiConfigs = payloadContent.getPayloadWifiConfigs();
//        for(PayloadWifiConfig payloadWifiConfig : payloadWifiConfigs){
//            if(payloadWifiConfig != null){
//                wrapper.addPayLoadContent(payloadWifiConfig);
//            }
//
//        }
        PayloadWifiConfig payloadWifiConfig = payloadContent.getPayloadWifiConfig();
        if(payloadWifiConfig != null){
            wrapper.addPayLoadContent(payloadWifiConfig);
        }
        PayloadPasswordPolicy payloadPasswordPolicy = payloadContent.getPayloadPasswordPolicy();
        if(payloadPasswordPolicy != null){
            wrapper.addPayLoadContent(payloadPasswordPolicy);
        }

        PayloadRestrictionsPolicy payloadRestrictionsPolicy = payloadContent.getPayloadRestrictionsPolicy();
        if(payloadRestrictionsPolicy != null){
            wrapper.addPayLoadContent(payloadRestrictionsPolicy);
        }
        PayloadRemovalPassword payloadRemovalPassword = payloadContent.getPayloadRemovalPassword();
        if(payloadRemovalPassword != null){
            wrapper.addPayLoadContent(payloadRemovalPassword);
        }
        PayloadWebClip payloadWebClip = payloadContent.getPayloadWebClip();
        if(payloadWebClip != null){
            wrapper.addPayLoadContent(payloadWebClip);
        }
        PayloadLDAP payloadLDAP = payloadContent.getPayloadLDAP();
        if(payloadLDAP != null){
            wrapper.addPayLoadContent(payloadLDAP);
        }

        PayloadExchange payloadExchange = payloadContent.getPayloadExchange();
        if(payloadExchange != null){
            wrapper.addPayLoadContent(payloadExchange);
        }

        PayloadVPN payloadVPN = payloadContent.getPayloadVPN();
        if(payloadVPN != null){
            wrapper.addPayLoadContent(payloadVPN);
        }

        BrowserSettings browserSettings = payloadContent.getBrowserSettings();
        if(browserSettings != null){
            wrapper.addPayLoadContent(browserSettings);
        }
        SystemSettings systemSettings = payloadContent.getSystemSettings();
        if(systemSettings != null){
            wrapper.addPayLoadContent(systemSettings);
        }

        LauncherSettings launcherSettings = payloadContent.getLauncherSettings();
        if(launcherSettings != null){
            wrapper.addPayLoadContent(launcherSettings);
        }

        AdvertiseDownloadSettings advertiseDownloadSettings = payloadContent.getAdvertiseDownloadSettings();
        if(advertiseDownloadSettings != null){
            wrapper.addPayLoadContent(advertiseDownloadSettings);
        }

        MemorySizePolicy memorySizePolicy = payloadContent.getMemorySizePolicy();
        if(memorySizePolicy != null){
            wrapper.addPayLoadContent(memorySizePolicy);
        }

        AppControlList appControlList = payloadContent.getAppControlList();
        if(appControlList != null){
            wrapper.addPayLoadContent(appControlList);
        }

        SystemExceptionPolicy systemExceptionPolicy = payloadContent.getSystemExceptionPolicy();
        if(systemExceptionPolicy != null){
            wrapper.addPayLoadContent(systemExceptionPolicy);
        }

        PayloadSecurityPolicy payloadSecurityPolicy = payloadContent.getPayloadSecurityPolicy();
        if(payloadSecurityPolicy != null){
            wrapper.addPayLoadContent(payloadSecurityPolicy);
        }

        PayloadRestrictionsAndroidPolicy payloadRestrictionsAndroidPolicy = payloadContent.getPayloadRestrictionsAndroidPolicy();
        if(payloadRestrictionsAndroidPolicy != null){
            wrapper.addPayLoadContent(payloadRestrictionsAndroidPolicy);
        }

        PayloadNativeAppCtrlPolicy payloadNativeAppCtrlPolicy = payloadContent.getPayloadNativeAppCtrlPolicy();
        if(payloadNativeAppCtrlPolicy != null){
            wrapper.addPayLoadContent(payloadNativeAppCtrlPolicy);
        }

        PayloadBluetoothPolicy payloadBluetoothPolicy = payloadContent.getPayloadBluetoothPolicy();
        if(payloadBluetoothPolicy != null){
            wrapper.addPayLoadContent(payloadBluetoothPolicy);
        }
        PayloadNetRestrictPolicy payloadNetRestrictPolicy = payloadContent.getPayloadNetRestrictPolicy();
        if(payloadNetRestrictPolicy != null){
            wrapper.addPayLoadContent(payloadNetRestrictPolicy);
        }

        PayloadActiveSyncPolicy payloadActiveSyncPolicy = payloadContent.getPayloadActiveSyncPolicy();
        if(payloadActiveSyncPolicy != null){
            wrapper.addPayLoadContent(payloadActiveSyncPolicy);
        }
        PayloadWallpaper payloadWallpaper = payloadContent.getPayloadWallpaper();
        if(payloadWallpaper != null){
            wrapper.addPayLoadContent(payloadWallpaper);
        }

        PayloadRoamingPolicy payloadRoamingPolicy = payloadContent.getPayloadRoamingPolicy();
        if(payloadRoamingPolicy != null){
            wrapper.addPayLoadContent(payloadRoamingPolicy);
        }


        return wrapper;
    }
}
