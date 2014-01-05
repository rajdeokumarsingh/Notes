package com.pekall.plist.beans;

/**
 * InstallApplication command install a third-party application
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class CommandInstallApp extends CommandObject {

    public static final String KEY_ITUNESSTOREID = "iTunesStoreID";
    public static final String KEY_MANIFESTURL = "ManifestURL";
    public static final String KEY_MANAGEMENTFLAGS = "ManagementFlags";
    public static final String KEY_PACKAGENAME = "PackageName";
    /**
     * The application's iTunes Store ID.
     * For example, the numeric ID for Keynote is 361285480 as found in the App Store link
     * http://itunes.apple.com/us/app/keynote/id361285480?mt=8.
     */
    private Long iTunesStoreID;

    /**
     * The URL where the manifest of an enterprise application can be found
     */
    private String ManifestURL;

    /**
     * The bitwise OR of the following flags:
     * 1 - Remove app when MDM profile is removed.
     * 4 - Prevent backup of the app data.
     */
    private Integer ManagementFlags;


    /**
     * Package name,  just for Android
     */
    private String PackageName;

    /**
     * Version code,  just for Android
     */
    private String versionCode;

    public CommandInstallApp() {
        super(CommandObject.REQ_TYPE_INST_APP);
    }

    public Long getITunesStoreID() {
        return iTunesStoreID;
    }

    public void setITunesStoreID(Long iTunesStoreID) {
        this.iTunesStoreID = iTunesStoreID;
    }

    public String getManifestURL() {
        return ManifestURL;
    }

    public void setManifestURL(String manifestURL) {
        ManifestURL = manifestURL;
    }

    public Integer getManagementFlags() {
        return ManagementFlags;
    }

    public void setManagementFlags(Integer managementFlags) {
        ManagementFlags = managementFlags;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandInstallApp)) return false;
        if (!super.equals(o)) return false;

        CommandInstallApp that = (CommandInstallApp) o;

        if (ManagementFlags != null ? !ManagementFlags.equals(that.ManagementFlags) : that.ManagementFlags != null)
            return false;
        if (ManifestURL != null ? !ManifestURL.equals(that.ManifestURL) : that.ManifestURL != null) return false;
        if (PackageName != null ? !PackageName.equals(that.PackageName) : that.PackageName != null) return false;
        if (iTunesStoreID != null ? !iTunesStoreID.equals(that.iTunesStoreID) : that.iTunesStoreID != null)
            return false;
        return !(versionCode != null ? !versionCode.equals(that.versionCode) : that.versionCode != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (iTunesStoreID != null ? iTunesStoreID.hashCode() : 0);
        result = 31 * result + (ManifestURL != null ? ManifestURL.hashCode() : 0);
        result = 31 * result + (ManagementFlags != null ? ManagementFlags.hashCode() : 0);
        result = 31 * result + (PackageName != null ? PackageName.hashCode() : 0);
        result = 31 * result + (versionCode != null ? versionCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandInstallApp{" +
                "iTunesStoreID=" + iTunesStoreID +
                ", ManifestURL='" + ManifestURL + '\'' +
                ", ManagementFlags=" + ManagementFlags +
                ", PackageName='" + PackageName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                "} " + super.toString();
    }
}
