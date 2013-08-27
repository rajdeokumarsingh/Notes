package com.pekall.plist.su.policy;


/**
 * Wrapper for class Event
 */
public class EventWrapper {

    String event_id = "";

    public EventWrapper() {
    }

    public EventWrapper(String id) {
        this.event_id = id;
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
        if (!(o instanceof EventWrapper)) return false;

        EventWrapper that = (EventWrapper) o;

        if (!event_id.equals(that.event_id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return event_id.hashCode();
    }

    @Override
    public String toString() {
        return "EventWrapper{" +
                "event_id='" + event_id + '\'' +
                '}';
    }
}
