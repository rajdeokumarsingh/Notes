package com.pekall.plist.beans;

/**
 * Status message for command Location
 */
public class CommandLocationStatus extends CommandStatusMsg {
    /**
     * Current longitude of the device
     */
    private Double Longitude;

    /**
     * Current latitude  of the device
     */
    private Double Latitude;

    public CommandLocationStatus() {
    }

    public CommandLocationStatus(String status, String UDID, String commandUUID) {
        super(status, UDID, commandUUID);
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandLocationStatus)) return false;
        if (!super.equals(o)) return false;

        CommandLocationStatus that = (CommandLocationStatus) o;

        if (Latitude != null ? !Latitude.equals(that.Latitude) : that.Latitude != null) return false;
        if (Longitude != null ? !Longitude.equals(that.Longitude) : that.Longitude != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (Longitude != null ? Longitude.hashCode() : 0);
        result = 31 * result + (Latitude != null ? Latitude.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandLocationStatus{" +
                "super=" + super.toString() +
                "Longitude=" + Longitude +
                ", Latitude=" + Latitude +
                '}';
    }
}
