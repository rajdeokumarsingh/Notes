package com.pekall.plist.su.policy;

/**
 * A period of time after the device receives an alert from
 * MDM server, after which a punishment, like locking device
 * for 30 seconds, will be applied.
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class GraceTime {
    /**
     * Grace time for setting password
     */
    private long time;

    /**
     * Event name for punishment
     */
    private String event_id;

    public GraceTime() {
        this(-1, "");
    }

    private GraceTime(long time, String event_id) {
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
        return event_id.equals(graceTime.event_id);

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
                ", eventId='" + event_id + '\'' +
                '}';
    }
}
