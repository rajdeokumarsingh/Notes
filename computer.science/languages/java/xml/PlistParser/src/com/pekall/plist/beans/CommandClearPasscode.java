package com.pekall.plist.beans;

import com.pekall.plist.Utils;

import java.util.Arrays;

/**
 * Command to clear the passcode for a device
 */
public class CommandClearPasscode extends CommandObject {
    /**
     * Optional. If the device has given an UnlockToken value in the “TokenUpdate”
     * check-in message, the server must pass the data blob back to the device for
     * this command to work.
     */
    private byte[] UnlockToken;

    public CommandClearPasscode() {
        super(CommandObject.REQ_TYPE_CLEAR_PASSCODE);
    }

    public CommandClearPasscode(byte[] unlockToken) {
        super(CommandObject.REQ_TYPE_CLEAR_PASSCODE);
        UnlockToken = unlockToken;
    }

    public byte[] getUnlockToken() {
        return UnlockToken;
    }

    public void setUnlockToken(byte[] unlockToken) {
        UnlockToken = unlockToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandClearPasscode)) return false;
        if (!super.equals(o)) return false;

        CommandClearPasscode that = (CommandClearPasscode) o;

        if (!Arrays.equals(Utils.safeByteArray(UnlockToken),
                Utils.safeByteArray(that.UnlockToken))) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(Utils.safeByteArray(UnlockToken));
        return result;
    }

    @Override
    public String toString() {
        return "CommandClearPasscode{" +
                "super=" + super.toString() +
                "UnlockToken=" + Arrays.toString(Utils.safeByteArray(UnlockToken)) +
                '}';
    }
}
