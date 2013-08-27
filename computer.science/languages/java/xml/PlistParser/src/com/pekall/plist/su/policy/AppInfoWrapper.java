package com.pekall.plist.su.policy;


import java.util.ArrayList;
import java.util.List;

public class AppInfoWrapper {

    private List<AppInfo> infos = new ArrayList<AppInfo>();

    String event_id = "";

    public AppInfoWrapper() {
    }

    public AppInfoWrapper(String id) {
        event_id = id;
    }

    public AppInfoWrapper(List<AppInfo> infos, String event_id) {
        this.infos = infos;
        this.event_id = event_id;
    }

    public List<AppInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<AppInfo> infos) {
        this.infos = infos;
    }

    public void addInfo(AppInfo info) {
        infos.add(info);
    }

    public String getEventId() {
        return event_id;
    }

    public void setEventId(String event_id) {
        this.event_id = event_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppInfoWrapper)) return false;

        AppInfoWrapper that = (AppInfoWrapper) o;

        if (!event_id.equals(that.event_id)) return false;
        if (!infos.equals(that.infos)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = infos.hashCode();
        result = 31 * result + event_id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AppInfoWrapper{" +
                "infos=" + infos +
                ", event_id='" + event_id + '\'' +
                '}';
    }
}
