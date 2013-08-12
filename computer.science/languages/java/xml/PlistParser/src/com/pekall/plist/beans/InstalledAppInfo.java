package com.pekall.plist.beans;

/**
 * Entry of the InstalledApplicationList
 */
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
    String Name;

    /** The app's static bundle size, in bytes. */
    long BundleSize;

    /**
     * The size of the app's document, library, and other folders, in bytes.
     * Availability: Available in iOS 5.0 and later.
     */
    long DynamicSize;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstalledAppInfo)) return false;

        InstalledAppInfo that = (InstalledAppInfo) o;

        if (BundleSize != that.BundleSize) return false;
        if (DynamicSize != that.DynamicSize) return false;
        if (Identifier != null ? !Identifier.equals(that.Identifier) : that.Identifier != null) return false;
        if (Name != null ? !Name.equals(that.Name) : that.Name != null) return false;
        if (ShortVersion != null ? !ShortVersion.equals(that.ShortVersion) : that.ShortVersion != null) return false;
        if (Version != null ? !Version.equals(that.Version) : that.Version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Identifier != null ? Identifier.hashCode() : 0;
        result = 31 * result + (Version != null ? Version.hashCode() : 0);
        result = 31 * result + (ShortVersion != null ? ShortVersion.hashCode() : 0);
        result = 31 * result + (Name != null ? Name.hashCode() : 0);
        result = 31 * result + (int)BundleSize;
        result = 31 * result + (int)DynamicSize;
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
                '}';
    }
}
