package com.pekall.plist.beans;

/**
 * A password removal policy payload provides a password to allow users to remove
 * a locked configuration profile from the device. If this payload is present
 * and has a password value set, the device asks for the password when the user
 * taps a profile's Remove button. This payload is encrypted with the rest of the profile.
 */
@SuppressWarnings("UnusedDeclaration")
public class PayloadRemovalPassword extends PayloadBase {

    /**
     * Optional. Specifies the removal password for the profile.
     */
    private String RemovalPassword;

    public PayloadRemovalPassword() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_REMOVAL_PASSWORD);
    }

    public String getRemovalPassword() {
        return RemovalPassword;
    }

    public void setRemovalPassword(String removalPassword) {
        RemovalPassword = removalPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadRemovalPassword)) return false;
        if (!super.equals(o)) return false;

        PayloadRemovalPassword that = (PayloadRemovalPassword) o;

        return !(RemovalPassword != null ? !RemovalPassword.equals(that.RemovalPassword) : that.RemovalPassword != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (RemovalPassword != null ? RemovalPassword.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadRemovalPassword{" +
                "RemovalPassword='" + RemovalPassword + '\'' +
                '}';
    }
}