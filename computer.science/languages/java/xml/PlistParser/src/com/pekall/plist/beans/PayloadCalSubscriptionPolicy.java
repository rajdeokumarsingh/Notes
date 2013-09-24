package com.pekall.plist.beans;

public class PayloadCalSubscriptionPolicy extends PayloadBase {

    /**
     * Description of the account.
     */
    private String SubCalAccountDescription;

    /**
     * The server address.
     */
    private String SubCalAccountHostName;

    /**
     * The user's login name
     */
    private String SubCalAccountUsername;

    /**
     *  The user's password.
     */
    private String SubCalAccountPassword;

    /**
     * Whether or not to use SSL.
     */
    private Boolean SubCalAccountUseSSL;

    public PayloadCalSubscriptionPolicy() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_CAL_SUB);
    }

    public String getSubCalAccountDescription() {
        return SubCalAccountDescription;
    }

    public void setSubCalAccountDescription(String subCalAccountDescription) {
        SubCalAccountDescription = subCalAccountDescription;
    }

    public String getSubCalAccountHostName() {
        return SubCalAccountHostName;
    }

    public void setSubCalAccountHostName(String subCalAccountHostName) {
        SubCalAccountHostName = subCalAccountHostName;
    }

    public String getSubCalAccountUsername() {
        return SubCalAccountUsername;
    }

    public void setSubCalAccountUsername(String subCalAccountUsername) {
        SubCalAccountUsername = subCalAccountUsername;
    }

    public String getSubCalAccountPassword() {
        return SubCalAccountPassword;
    }

    public void setSubCalAccountPassword(String subCalAccountPassword) {
        SubCalAccountPassword = subCalAccountPassword;
    }

    public Boolean getSubCalAccountUseSSL() {
        return SubCalAccountUseSSL;
    }

    public void setSubCalAccountUseSSL(Boolean subCalAccountUseSSL) {
        SubCalAccountUseSSL = subCalAccountUseSSL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PayloadCalSubscriptionPolicy that = (PayloadCalSubscriptionPolicy) o;

        if (SubCalAccountDescription != null ? !SubCalAccountDescription.equals(that.SubCalAccountDescription) : that.SubCalAccountDescription != null)
            return false;
        if (SubCalAccountHostName != null ? !SubCalAccountHostName.equals(that.SubCalAccountHostName) : that.SubCalAccountHostName != null)
            return false;
        if (SubCalAccountPassword != null ? !SubCalAccountPassword.equals(that.SubCalAccountPassword) : that.SubCalAccountPassword != null)
            return false;
        if (SubCalAccountUseSSL != null ? !SubCalAccountUseSSL.equals(that.SubCalAccountUseSSL) : that.SubCalAccountUseSSL != null)
            return false;
        if (SubCalAccountUsername != null ? !SubCalAccountUsername.equals(that.SubCalAccountUsername) : that.SubCalAccountUsername != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (SubCalAccountDescription != null ? SubCalAccountDescription.hashCode() : 0);
        result = 31 * result + (SubCalAccountHostName != null ? SubCalAccountHostName.hashCode() : 0);
        result = 31 * result + (SubCalAccountUsername != null ? SubCalAccountUsername.hashCode() : 0);
        result = 31 * result + (SubCalAccountPassword != null ? SubCalAccountPassword.hashCode() : 0);
        result = 31 * result + (SubCalAccountUseSSL != null ? SubCalAccountUseSSL.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadCalSubscriptionPolicy{" +
                "SubCalAccountDescription='" + SubCalAccountDescription + '\'' +
                ", SubCalAccountHostName='" + SubCalAccountHostName + '\'' +
                ", SubCalAccountUsername='" + SubCalAccountUsername + '\'' +
                ", SubCalAccountPassword='" + SubCalAccountPassword + '\'' +
                ", SubCalAccountUseSSL=" + SubCalAccountUseSSL +
                '}';
    }
}
