package com.pekall.plist.beans;

/**
 * Status message for command InstallApplication
 */
public class CommandInstallAppStatus extends CommandStatusMsg {

    /**
     * If the app cannot be installed, the device responds with an Error status,
     * with the following fields
     */
    public static final String REJECT_REASON_APP_ALREADY_INSTALLED = "AppAlreadyInstalled";
    public static final String REJECT_REASON_NOT_SUPPORTED = "NotSupported";
    public static final String REJECT_REASON_COULD_NOT_VERIFY_APP_ID = "CouldNotVerifyAppID";
    public static final String REJECT_REASON_APP_STORE_DISABLED = "AppStoreDisabled";

    /**
     * The app's identifier (Bundle ID)
     */
    private String Identifier;

    /**
     * The app's installation state. If the state is NeedsRedemption,
     * the server needs to send a redemption code to complete the app installation.
     */
    private String State;

    /**
     * If the app cannot be installed, the device responds with an Error status
     * with rejection reason. See REJECT_REASON_...
     */
    private String RejectionReason;

    public CommandInstallAppStatus() {
    }

    public CommandInstallAppStatus(String status, String UDID, String commandUUID) {
        super(status, UDID, commandUUID);
    }

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getRejectionReason() {
        return RejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        RejectionReason = rejectionReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandInstallAppStatus)) return false;
        if (!super.equals(o)) return false;

        CommandInstallAppStatus that = (CommandInstallAppStatus) o;

        if (Identifier != null ? !Identifier.equals(that.Identifier) : that.Identifier != null) return false;
        if (RejectionReason != null ? !RejectionReason.equals(that.RejectionReason) : that.RejectionReason != null)
            return false;
        if (State != null ? !State.equals(that.State) : that.State != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (Identifier != null ? Identifier.hashCode() : 0);
        result = 31 * result + (State != null ? State.hashCode() : 0);
        result = 31 * result + (RejectionReason != null ? RejectionReason.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandInstallAppStatus{" +
                "super='" + super.toString() + '\'' +
                "Identifier='" + Identifier + '\'' +
                ", State='" + State + '\'' +
                ", RejectionReason='" + RejectionReason + '\'' +
                '}';
    }
}
