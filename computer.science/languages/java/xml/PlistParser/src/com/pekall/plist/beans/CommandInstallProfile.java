package com.pekall.plist.beans;

import com.pekall.plist.PlistDebug;
import com.pekall.plist.Utils;

import java.util.Arrays;

/**
 * Command to install configuration profiles
 */
public class CommandInstallProfile extends CommandObject {

    /**
     * The profile to install. May be signed and/or encrypted for
     * any identity installed on the device.
     */
    private byte[] Payload;

    public CommandInstallProfile() {
        super(CommandObject.REQ_TYPE_INST_PROF);
    }

    public CommandInstallProfile(byte[] payload) {
        super(CommandObject.REQ_TYPE_INST_PROF);
        Payload = payload;
    }

    public byte[] getPayload() {
        return Payload;
    }

    public void setPayload(byte[] payload) {
        Payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandInstallProfile)) return false;
        if (!super.equals(o)) return false;

        CommandInstallProfile that = (CommandInstallProfile) o;

        if (!Arrays.equals(Utils.safeByteArray(Payload),
                Utils.safeByteArray(that.Payload))) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(Utils.safeByteArray(Payload));
        return result;
    }

    @Override
    public String toString() {
        return "CommandInstallProfile{" +
                "super=" + super.toString() +
                "Payload=" + Arrays.toString(Utils.safeByteArray(Payload)) +
                '}';
    }
}
