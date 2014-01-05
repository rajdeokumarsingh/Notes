package com.pekall.plist.beans;

/**
 * Status message from client to server for SecurityInfo command
 */
@SuppressWarnings("UnusedDeclaration")
public class CommandSecurityInfoStatus extends CommandStatusMsg {

    /** Security information */
    private SecInfo SecurityInfo;

    public CommandSecurityInfoStatus() {
    }

    public CommandSecurityInfoStatus(String status, String UDID, String commandUUID) {
        super(status, UDID, commandUUID);
    }

    public CommandSecurityInfoStatus(String status, String UDID,
                                     String commandUUID, SecInfo secInfo) {
        this(status, UDID, commandUUID);
        this.SecurityInfo = secInfo;
    }

    public SecInfo getSecurityInfo() {
        return SecurityInfo;
    }

    public void setSecurityInfo(SecInfo securityInfo) {
        SecurityInfo = securityInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandSecurityInfoStatus)) return false;
        if (!super.equals(o)) return false;

        CommandSecurityInfoStatus that = (CommandSecurityInfoStatus) o;

        return !(SecurityInfo != null ? !SecurityInfo.equals(that.SecurityInfo) : that.SecurityInfo != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (SecurityInfo != null ? SecurityInfo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandSecurityInfoStatus{" +
                "super=" + super.toString() +
                "SecurityInfo=" + SecurityInfo.toString() +
                '}';
    }
}
