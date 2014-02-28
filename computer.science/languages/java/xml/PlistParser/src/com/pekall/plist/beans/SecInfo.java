package com.pekall.plist.beans;

/**
 * Security information in SecurityInfo status message
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class SecInfo {

    /**
     *  Bitfield. Describes the underlying hardware encryption capabilities of the device.
     *  1 for Block-level encryption.
     *  2 for File-level encryption.
     */
    private int HardwareEncryptionCaps;

    /** Set to true if the device is protected by a passcode. */
    private boolean PasscodePresent;

    /**
     * Set to true if the user's passcode is compliant with all requirements on the device,
     * including Exchange and other accounts.
     */
    private boolean PasscodeCompliant;

    /**
     * Set to true if the user's passcode is compliant with requirements from profiles.
     */
    private boolean PasscodeCompliantWithProfiles;

    public SecInfo() {
    }

    public SecInfo(int hardwareEncryptionCaps, boolean passcodePresent,
                   boolean passcodeCompliant, boolean passcodeCompliantWithProfiles) {
        HardwareEncryptionCaps = hardwareEncryptionCaps;
        PasscodePresent = passcodePresent;
        PasscodeCompliant = passcodeCompliant;
        PasscodeCompliantWithProfiles = passcodeCompliantWithProfiles;
    }

    public int getHardwareEncryptionCaps() {
        return HardwareEncryptionCaps;
    }

    public void setHardwareEncryptionCaps(int hardwareEncryptionCaps) {
        HardwareEncryptionCaps = hardwareEncryptionCaps;
    }

    public boolean isPasscodePresent() {
        return PasscodePresent;
    }

    public void setPasscodePresent(boolean passcodePresent) {
        PasscodePresent = passcodePresent;
    }

    public boolean isPasscodeCompliant() {
        return PasscodeCompliant;
    }

    public void setPasscodeCompliant(boolean passcodeCompliant) {
        PasscodeCompliant = passcodeCompliant;
    }

    public boolean isPasscodeCompliantWithProfiles() {
        return PasscodeCompliantWithProfiles;
    }

    public void setPasscodeCompliantWithProfiles(boolean passcodeCompliantWithProfiles) {
        PasscodeCompliantWithProfiles = passcodeCompliantWithProfiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecInfo)) return false;

        SecInfo secInfo = (SecInfo) o;

        if (HardwareEncryptionCaps != secInfo.HardwareEncryptionCaps) return false;
        if (PasscodeCompliant != secInfo.PasscodeCompliant) return false;
        if (PasscodeCompliantWithProfiles != secInfo.PasscodeCompliantWithProfiles) return false;
        return PasscodePresent == secInfo.PasscodePresent;

    }

    @Override
    public int hashCode() {
        int result = HardwareEncryptionCaps;
        result = 31 * result + (PasscodePresent ? 1 : 0);
        result = 31 * result + (PasscodeCompliant ? 1 : 0);
        result = 31 * result + (PasscodeCompliantWithProfiles ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SecInfo{" +
                "HardwareEncryptionCaps=" + HardwareEncryptionCaps +
                ", PasscodePresent=" + PasscodePresent +
                ", PasscodeCompliant=" + PasscodeCompliant +
                ", PasscodeCompliantWithProfiles=" + PasscodeCompliantWithProfiles +
                '}';
    }
}
