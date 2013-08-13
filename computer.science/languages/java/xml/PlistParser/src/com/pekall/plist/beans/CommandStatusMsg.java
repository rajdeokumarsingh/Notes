package com.pekall.plist.beans;

import com.pekall.plist.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Status message from client to server to report client status
 * or last command result
 */
public class CommandStatusMsg {

    /** Everything went well. */
    public static final String CMD_STAT_ACKNOWLEDGED = "Acknowledged";

    /** An error has occurred. See the ErrorChain for details. */
    public static final String CMD_STAT_ERROR = "Error";

    /** A protocol error has occurred. The command may be malformed. */
    public static final String CMD_STAT_FMT_ERROR = "CommandFormatError";

    /** The device is idle (there is no status) */
    public static final String CMD_STAT_IDLE = "Idle";

    /** The device received the command, but cannot perform it at
     this time. The device will poll the server again in the future. */
    public static final String CMD_STAT_NOT_NOW = "NotNow";

    // Status code for the device, see CMD_STAT_ACKNOWLEDGED, CMD_STAT_...
    private String Status;

    // UDID of the device
    private String UDID;

    // UUID of the command that this response is for (if any)
    private String CommandUUID;


    // Optional. Array of dictionaries representing the chain of
    // errors that occurred.
    private List<CommandError> ErrorChain;

    public CommandStatusMsg() {
    }

    public CommandStatusMsg(String status, String UDID, String commandUUID) {
        Status = status;
        this.UDID = UDID;
        CommandUUID = commandUUID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUDID() {
        return UDID;
    }

    public void setUDID(String UDID) {
        this.UDID = UDID;
    }

    public String getCommandUUID() {
        return CommandUUID;
    }

    public void setCommandUUID(String commandUUID) {
        CommandUUID = commandUUID;
    }

    public List<CommandError> getErrorChain() {
        return ErrorChain;
    }

    public void setErrorChain(List<CommandError> errorChain) {
        ErrorChain = errorChain;
    }

    public void addError(CommandError error) {
        if (ErrorChain == null) {
            ErrorChain = new ArrayList<CommandError>();
        }
        ErrorChain.add(error);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandStatusMsg)) return false;

        CommandStatusMsg that = (CommandStatusMsg) o;
        if(this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + Utils.safeString(Status).hashCode();
        result = 31 * result + Utils.safeString(UDID).hashCode();
        result = 31 * result + Utils.safeString(CommandUUID).hashCode();
        if (ErrorChain != null) {
            for (CommandError error : ErrorChain) {
                result += error.hashCode();
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "CommandStatusMsg{" +
                "Status='" + Utils.safeString(Status) + '\'' +
                ", UDID='" + Utils.safeString(UDID) + '\'' +
                ", CommandUUID='" + Utils.safeString(CommandUUID) + '\'' +
                '}';
    }
}
