package com.pekall.plist.beans;

import com.pekall.plist.Utils;

/**
 * Command message from server to client
 */
public class CommandMsg extends BeanBase {

    // UUID of the command
    private String CommandUUID;

    // Command object
    private CommandObject Command;

    /**
     * Create an empty command
     */
    public CommandMsg() {
    }

    public CommandMsg(String commandUUID, CommandObject command) {
        CommandUUID = commandUUID;
        Command = command;
    }

    public boolean isEmptyCommand() {
        if (Command == null && CommandUUID == null) {
            return true;
        }
        return false;
    }

    public String getCommandUUID() {
        return CommandUUID;
    }

    public void setCommandUUID(String commandUUID) {
        CommandUUID = commandUUID;
    }

    public CommandObject getCommand() {
        return Command;
    }

    public void setCommand(CommandObject command) {
        Command = command;
    }

    public String getRequestType() {
        if (Command != null) {
            return Command.getRequestType();
        }
        return CommandObject.REQ_TYPE_EMPTY_MSG;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandMsg)) return false;

        CommandMsg that = (CommandMsg) o;

        if (!Utils.safeCommandObject(Command)
                .equals(Utils.safeCommandObject(that.Command))) return false;
        if (!Utils.safeString(CommandUUID)
                .equals(Utils.safeString(that.CommandUUID))) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Utils.safeString(CommandUUID).hashCode();
        result = 31 * result + Utils.safeCommandObject(Command).hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CommandMsg{" +
                "CommandUUID='" + CommandUUID + '\'' +
                ", Command=" + Utils.safeCommandObject(Command).toString() +
                '}';
    }
}
