package com.pekall.plist.su.policy;

/**
 * XML element for ios_control_policy.os_version
 */
@SuppressWarnings("UnusedDeclaration")
public class OsVersion extends EventWrapper {

    /**
     * If the os version is less than the value,
     * punishing event will be triggered
     */
    private double less_than;

    public OsVersion() {
        this(0.0, "");
    }

    private OsVersion(double less_than, String id) {
        super(id);
        this.less_than = less_than;
    }

    public double getLessThan() {
        return less_than;
    }

    public void setLessThan(double less_than) {
        this.less_than = less_than;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OsVersion)) return false;
        if (!super.equals(o)) return false;

        OsVersion osVersion = (OsVersion) o;

        return Double.compare(osVersion.less_than, less_than) == 0;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(less_than);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "OsVersion{" +
                "super=" + super.toString() +
                "less_than=" + less_than +
                '}';
    }
}
