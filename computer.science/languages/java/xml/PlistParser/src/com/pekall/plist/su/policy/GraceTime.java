package com.pekall.plist.su.policy;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 7/29/13
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraceTime {
    /**
     * Grace time for setting password
     */
    long time;

    /**
     * Event name for punishment
     */
    String event_id;

    public GraceTime() {
        this(-1, "");
    }

    public GraceTime(long time, String event_id) {
        this.time = time;
        this.event_id = event_id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getEventId() {
        return event_id;
    }

    public void setEventId(String id) {
        this.event_id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraceTime)) return false;

        GraceTime graceTime = (GraceTime) o;

        if (time != graceTime.time) return false;
        if (!event_id.equals(graceTime.event_id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (time ^ (time >>> 32));
        result = 31 * result + event_id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GraceTime{" +
                "time=" + time +
                ", event_id='" + event_id + '\'' +
                '}';
    }
}
