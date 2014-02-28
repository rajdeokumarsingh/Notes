package com.pekall.plist.beans;

/**
 * A dictionary contains a managed application's information
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class ManagedAppInfo {
    /**
     * The app is scheduled for installation, but needs a redemption code
     * to complete the transaction.
     */
    public final static String STATUS_NEEDS_REDEMPTION = "NeedsRedemption";
    /**
     * The device is redeeming the redemption code.
     */
    public final static String STATUS_REDEEMING = "Redeeming";
    /**
     * The user is being prompted for app installation.
     */
    public final static String STATUS_PROMPTING = "Prompting";
    /**
     * The app is being installed.
     */
    public final static String STATUS_INSTALLING = "Installing";
    /**
     * The app is installed and managed.
     */
    public final static String STATUS_MANAGED = "Managed";
    /**
     * The app is managed, but has been removed by the user. When the app is installed again
     * (even by the user), it will be managed once again.
     */
    public final static String STATUS_MANAGED_BUT_UNINSTALLED = "ManagedButUninstalled";
    /**
     * The app state is unknown.
     */
    public final static String STATUS_USER_UNKNOWN = "Unknown";
    /**
     * The user has installed the app before managed app installation could take place.
     */
    public final static String STATUS_USER_INSTALLED_APP = "UserInstalledApp";
    /**
     * The user rejected the offer to install the app.
     */
    public final static String STATUS_USER_REJECTED = "UserRejected";
    /**
     * The app installation has failed.
     */
    public final static String STATUS_FAILED = "Failed";

    /** The status of the managed app, See STATUS_NEEDS_REDEMPTION, STATUS_NEEDS_... */
    private String Status;

    /** Management flags. (See InstallApplication command above for a list of flags.) */
    private Integer ManagementFlags;

    /**
     * If the user has already purchased a paid app, the unused redemption code
     * is reported here. This code can be used again to purchase the app for someone else.
     * This code is reported only once.
     */
    private String UnusedRedemptionCode;

    public ManagedAppInfo(String status, Integer managementFlags, String unusedRedemptionCode) {
        Status = status;
        ManagementFlags = managementFlags;
        UnusedRedemptionCode = unusedRedemptionCode;
    }

    public ManagedAppInfo() {
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Integer getManagementFlags() {
        return ManagementFlags;
    }

    public void setManagementFlags(Integer managementFlags) {
        ManagementFlags = managementFlags;
    }

    public String getUnusedRedemptionCode() {
        return UnusedRedemptionCode;
    }

    public void setUnusedRedemptionCode(String unusedRedemptionCode) {
        UnusedRedemptionCode = unusedRedemptionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManagedAppInfo)) return false;

        ManagedAppInfo that = (ManagedAppInfo) o;

        if (ManagementFlags != null ? !ManagementFlags.equals(that.ManagementFlags) : that.ManagementFlags != null)
            return false;
        if (Status != null ? !Status.equals(that.Status) : that.Status != null) return false;
        return !(UnusedRedemptionCode != null ? !UnusedRedemptionCode.equals(that.UnusedRedemptionCode) : that.UnusedRedemptionCode != null);

    }

    @Override
    public int hashCode() {
        int result = Status != null ? Status.hashCode() : 0;
        result = 31 * result + (ManagementFlags != null ? ManagementFlags.hashCode() : 0);
        result = 31 * result + (UnusedRedemptionCode != null ? UnusedRedemptionCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ManagedAppInfo{" +
                "Status='" + Status + '\'' +
                ", ManagementFlags=" + ManagementFlags +
                ", UnusedRedemptionCode='" + UnusedRedemptionCode + '\'' +
                '}';
    }
}
