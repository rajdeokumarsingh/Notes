package com.pekall.plist.beans;

public class PayloadCalDAVPolicy extends PayloadBase {

    /**
     * The description of the account.
     */
    private String CalDAVAccountDescription;

    /**
     * The server address. In OS X, this key is required.
     */
    private String CalDAVHostName;

    /**
     *  The user's login name. In OS X, this key is required.
     */
    private String CalDAVUsername;

    /**
     * The user's password.
     */
    private String CalDAVPassword;

    /**
     * Whether or not to use SSL. In OS X, this key is optional.
     */
    private Boolean CalDAVUseSSL;

    /**
     * The port on which to connect to the server.
     */
    private Integer CalDAVPort;

    /**
     * The base URL to the user's calendar.
     */
    private String CalDAVPrincipalURL;

    public PayloadCalDAVPolicy() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_CAL_DAV);
    }

    public String getCalDAVAccountDescription() {
        return CalDAVAccountDescription;
    }

    public void setCalDAVAccountDescription(String calDAVAccountDescription) {
        CalDAVAccountDescription = calDAVAccountDescription;
    }

    public String getCalDAVHostName() {
        return CalDAVHostName;
    }

    public void setCalDAVHostName(String calDAVHostName) {
        CalDAVHostName = calDAVHostName;
    }

    public String getCalDAVUsername() {
        return CalDAVUsername;
    }

    public void setCalDAVUsername(String calDAVUsername) {
        CalDAVUsername = calDAVUsername;
    }

    public String getCalDAVPassword() {
        return CalDAVPassword;
    }

    public void setCalDAVPassword(String calDAVPassword) {
        CalDAVPassword = calDAVPassword;
    }

    public Boolean getCalDAVUseSSL() {
        return CalDAVUseSSL;
    }

    public void setCalDAVUseSSL(Boolean calDAVUseSSL) {
        CalDAVUseSSL = calDAVUseSSL;
    }

    public Integer getCalDAVPort() {
        return CalDAVPort;
    }

    public void setCalDAVPort(Integer calDAVPort) {
        CalDAVPort = calDAVPort;
    }

    public String getCalDAVPrincipalURL() {
        return CalDAVPrincipalURL;
    }

    public void setCalDAVPrincipalURL(String calDAVPrincipalURL) {
        CalDAVPrincipalURL = calDAVPrincipalURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PayloadCalDAVPolicy that = (PayloadCalDAVPolicy) o;

        if (CalDAVAccountDescription != null ? !CalDAVAccountDescription.equals(that.CalDAVAccountDescription) : that.CalDAVAccountDescription != null)
            return false;
        if (CalDAVHostName != null ? !CalDAVHostName.equals(that.CalDAVHostName) : that.CalDAVHostName != null)
            return false;
        if (CalDAVPassword != null ? !CalDAVPassword.equals(that.CalDAVPassword) : that.CalDAVPassword != null)
            return false;
        if (CalDAVPort != null ? !CalDAVPort.equals(that.CalDAVPort) : that.CalDAVPort != null) return false;
        if (CalDAVPrincipalURL != null ? !CalDAVPrincipalURL.equals(that.CalDAVPrincipalURL) : that.CalDAVPrincipalURL != null)
            return false;
        if (CalDAVUseSSL != null ? !CalDAVUseSSL.equals(that.CalDAVUseSSL) : that.CalDAVUseSSL != null) return false;
        if (CalDAVUsername != null ? !CalDAVUsername.equals(that.CalDAVUsername) : that.CalDAVUsername != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (CalDAVAccountDescription != null ? CalDAVAccountDescription.hashCode() : 0);
        result = 31 * result + (CalDAVHostName != null ? CalDAVHostName.hashCode() : 0);
        result = 31 * result + (CalDAVUsername != null ? CalDAVUsername.hashCode() : 0);
        result = 31 * result + (CalDAVPassword != null ? CalDAVPassword.hashCode() : 0);
        result = 31 * result + (CalDAVUseSSL != null ? CalDAVUseSSL.hashCode() : 0);
        result = 31 * result + (CalDAVPort != null ? CalDAVPort.hashCode() : 0);
        result = 31 * result + (CalDAVPrincipalURL != null ? CalDAVPrincipalURL.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadCalDAVPolicy{" +
                "CalDAVAccountDescription='" + CalDAVAccountDescription + '\'' +
                ", CalDAVHostName='" + CalDAVHostName + '\'' +
                ", CalDAVUsername='" + CalDAVUsername + '\'' +
                ", CalDAVPassword='" + CalDAVPassword + '\'' +
                ", CalDAVUseSSL=" + CalDAVUseSSL +
                ", CalDAVPort=" + CalDAVPort +
                ", CalDAVPrincipalURL='" + CalDAVPrincipalURL + '\'' +
                '}';
    }
}
