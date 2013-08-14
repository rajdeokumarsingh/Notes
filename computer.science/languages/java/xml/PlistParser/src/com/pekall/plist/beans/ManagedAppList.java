package com.pekall.plist.beans;

import java.util.HashMap;

/**
 * A dictionary of managed apps.
 * The keys of the ManagedApplicationList dictionary are the
 * app identifiers for the managed apps.
 */
public class ManagedAppList {
    // TODO: define app id, should I add all application identifier in this class
    // private ManagedAppInfo appIdentifier1;
    // private ManagedAppInfo appIdentifier2;

    // The key is the app identifier for the managed app.
    private final HashMap<String, ManagedAppInfo> managedApps =
            new HashMap<String, ManagedAppInfo>();

    public ManagedAppList() {
    }

    public HashMap<String, ManagedAppInfo> getManagedApps() {
        return managedApps;
    }

    public void addAppInfo(String identifier, ManagedAppInfo info) {
        managedApps.put(identifier, info);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManagedAppList)) return false;

        ManagedAppList that = (ManagedAppList) o;

        // if (managedApps != null ? !managedApps.equals(that.managedApps) : that.managedApps != null) return false;
        if (this.managedApps == null && that.managedApps == null) return true;
        if (this.managedApps == null || that.managedApps == null) return false;
        if (this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        if (managedApps == null) {
            return 0;
        }
        int result = 0;
        for (String s : managedApps.keySet()) {
            if (s == null) continue;
            result += s.hashCode();
        }
        for (ManagedAppInfo info : managedApps.values()) {
            if (info == null) continue;
            result += info.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for (String s : managedApps.keySet()) {
            if (s == null) continue;
            sb.append("id: " + s + ", info: ");

            if(managedApps.get(s) == null) continue;
            sb.append(managedApps.get(s).toString());
        }
        sb.append("}");

        return "ManagedAppList{" +
                "managedApps=" + sb.toString() +
                '}';
    }
}
