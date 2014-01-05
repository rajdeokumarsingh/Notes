package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Entry of the InstalledApplicationList
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class InstalledAppInfo {
    /** The application's ID. */
    private String Identifier;

    /** The application's version.*/
    private String Version;

    /**
     * The application's short version.
     * Availability: Available in iOS 5.0 and later.
     */
    private String ShortVersion;

    /** The application's name. */
    private String Name;

    /** The app's static bundle size, in bytes. */
    private long BundleSize;

    /**
     * The size of the app's document, library, and other folders, in bytes.
     * Availability: Available in iOS 5.0 and later.
     */
    private long DynamicSize;

   /**
     * Permissions requested, just for Android
     */
   private List<String> AppPermissions;

    public InstalledAppInfo() {
    }

    public InstalledAppInfo(String identifier, String version, String shortVersion,
                            String name, long bundleSize, long dynamicSize) {
        Identifier = identifier;
        Version = version;
        ShortVersion = shortVersion;
        Name = name;
        BundleSize = bundleSize;
        DynamicSize = dynamicSize;
    }

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getShortVersion() {
        return ShortVersion;
    }

    public void setShortVersion(String shortVersion) {
        ShortVersion = shortVersion;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getBundleSize() {
        return BundleSize;
    }

    public void setBundleSize(long bundleSize) {
        BundleSize = bundleSize;
    }

    public long getDynamicSize() {
        return DynamicSize;
    }

    public void setDynamicSize(long dynamicSize) {
        DynamicSize = dynamicSize;
    }

    public List<String> getAppPermissions() {
        return AppPermissions;
    }

    public void setAppPermissions(List<String> appPermissions) {
        AppPermissions = appPermissions;
    }

    public void addAppPermission(String perm) {
        if (AppPermissions == null) {
            AppPermissions = new ArrayList<String>();
        }
        AppPermissions.add(perm);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstalledAppInfo)) return false;

        InstalledAppInfo info = (InstalledAppInfo) o;

        if (BundleSize != info.BundleSize) return false;
        if (DynamicSize != info.DynamicSize) return false;
        if (AppPermissions != null ? !AppPermissions.equals(info.AppPermissions) : info.AppPermissions != null)
            return false;
        if (Identifier != null ? !Identifier.equals(info.Identifier) : info.Identifier != null) return false;
        if (Name != null ? !Name.equals(info.Name) : info.Name != null) return false;
        if (ShortVersion != null ? !ShortVersion.equals(info.ShortVersion) : info.ShortVersion != null) return false;
        return !(Version != null ? !Version.equals(info.Version) : info.Version != null);

    }

    @Override
    public int hashCode() {
        int result = Identifier != null ? Identifier.hashCode() : 0;
        result = 31 * result + (Version != null ? Version.hashCode() : 0);
        result = 31 * result + (ShortVersion != null ? ShortVersion.hashCode() : 0);
        result = 31 * result + (Name != null ? Name.hashCode() : 0);
        result = 31 * result + (int) (BundleSize ^ (BundleSize >>> 32));
        result = 31 * result + (int) (DynamicSize ^ (DynamicSize >>> 32));
        result = 31 * result + (AppPermissions != null ? AppPermissions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InstalledAppInfo{" +
                "Identifier='" + Identifier + '\'' +
                ", Version='" + Version + '\'' +
                ", ShortVersion='" + ShortVersion + '\'' +
                ", Name='" + Name + '\'' +
                ", BundleSize=" + BundleSize +
                ", DynamicSize=" + DynamicSize +
                ", AppPermissions=" + AppPermissions +
                '}';
    }
}
