package com.pekall.plist.beans;

import com.pekall.plist.Utils;

import java.util.Arrays;

/**
 * Command to remove configuration profiles
 */
public class CommandRemoveProfile extends CommandObject {

    public static final String KEY_IDENTIFIER = "Identifier";
    /**
     * The "PayloadIdentifier" value for the profile to remove.
     */
    private String Identifier;

    public CommandRemoveProfile() {
        super(CommandObject.REQ_TYPE_RM_PROF);
    }

    public CommandRemoveProfile(String identifier) {
        super(CommandObject.REQ_TYPE_RM_PROF);
        Identifier = identifier;
    }

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandRemoveProfile)) return false;
        if (!super.equals(o)) return false;

        CommandRemoveProfile that = (CommandRemoveProfile) o;

        if(!Utils.safeString(this.Identifier)
                .equals(Utils.safeString(that.Identifier))) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Identifier.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CommandRemoveProfile{" +
                "super=" + super.toString() +
                "Identifier=" + Identifier +
                '}';
    }
}
