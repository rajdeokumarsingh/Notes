package com.pekall.plist.beans;

import java.util.Arrays;

@SuppressWarnings("UnusedDeclaration")
public class ClearPasscodeReq extends CommandObject {
    public static final String KEY_UNLOCKTOKEN = "UnlockToken";
    private byte[] UnlockToken;

    public byte[] getUnlockToken() {
        return UnlockToken;
    }

    public void setUnlockToken(byte[] unlockToken) {
        UnlockToken = unlockToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClearPasscodeReq)) return false;
        if (!super.equals(o)) return false;

        ClearPasscodeReq that = (ClearPasscodeReq) o;

        return Arrays.equals(UnlockToken, that.UnlockToken);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (UnlockToken != null ? Arrays.hashCode(UnlockToken) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClearPasscodeReq{" +
                "UnlockToken=" + Arrays.toString(UnlockToken) +
                "} " + super.toString();
    }
}
