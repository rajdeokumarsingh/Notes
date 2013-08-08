package com.pekall.plist.beans;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: wjl
 * Date: 13-8-8
 * Time: 下午4:50
 */
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

        if (!Arrays.equals(UnlockToken, that.UnlockToken)) return false;

        return true;
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
