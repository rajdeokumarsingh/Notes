package com.pekall.plist.su.policy;


/**
 * Wrapper for class Event
 */
@SuppressWarnings("UnusedDeclaration")
public class EventWrapper {

    private String event_id = "";

    public EventWrapper() {
    }

    EventWrapper(String id) {
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

        return event_id.equals(that.event_id);

    }

    @Override
    public int hashCode() {
        return event_id.hashCode();
    }

    @Override
    public String toString() {
        return "EventWrapper{" +
                "eventId='" + event_id + '\'' +
                '}';
    }
}
