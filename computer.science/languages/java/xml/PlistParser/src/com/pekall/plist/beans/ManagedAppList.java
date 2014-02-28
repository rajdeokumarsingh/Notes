package com.pekall.plist.beans;

import java.util.HashMap;

/**
 * A dictionary of managed apps.
 * The keys of the ManagedApplicationList dictionary are the
 * app identifiers for the managed apps.
 */
class ManagedAppList {
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

        return this.hashCode() == that.hashCode();

    }

    @Override
    public int hashCode() {
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
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (String s : managedApps.keySet()) {
            if (s == null) continue;
            sb.append("id: ").append(s).append(", info: ");

            if(managedApps.get(s) == null) continue;
            sb.append(managedApps.get(s).toString());
        }
        sb.append("}");

        return "ManagedAppList{" +
                "managedApps=" + sb.toString() +
                '}';
    }
}
