package com.pekall.plist.su.policy;


import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class AppInfoWrapper {

    private List<AppInfo> infos = new ArrayList<AppInfo>();

    private String eventId = "";

    public AppInfoWrapper() {
    }

    public AppInfoWrapper(String id) {
        eventId = id;
    }

    public AppInfoWrapper(List<AppInfo> infos, String eventId) {
        this.infos = infos;
        this.eventId = eventId;
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
        return eventId;
    }

    public void setEventId(String event_id) {
        this.eventId = event_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppInfoWrapper)) return false;

        AppInfoWrapper that = (AppInfoWrapper) o;

        if (!eventId.equals(that.eventId)) return false;
        return infos.equals(that.infos);

    }

    @Override
    public int hashCode() {
        int result = infos.hashCode();
        result = 31 * result + eventId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AppInfoWrapper{" +
                "infos=" + infos +
                ", eventId='" + eventId + '\'' +
                '}';
    }
}
