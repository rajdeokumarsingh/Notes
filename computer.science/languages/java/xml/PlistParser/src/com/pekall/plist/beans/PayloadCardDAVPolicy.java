package com.pekall.plist.beans;

@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class PayloadCardDAVPolicy extends PayloadBase {

    /**
     *  The description of the account.
     */
    private String CardDAVAccountDescription;

    /**
     * The server address.
     */
    private String CardDAVHostName;

    /**
     * The user's login name.
     */
    private String CardDAVUsername;

    /**
     * The user's password
     */
    private String CardDAVPassword;

    /**
     * Whether or not to use SSL.
     */
    private Boolean CardDAVUseSSL;

    /**
     * The port on which to connect to the server.
     */
    private Integer CardDAVPort;

    /**
     * The base URL to the user's calendar.
     */
    private String CalDAVPrincipalURL;

    public PayloadCardDAVPolicy() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_CARD_DAV);
    }

    public String getCardDAVAccountDescription() {
        return CardDAVAccountDescription;
    }

    public void setCardDAVAccountDescription(String cardDAVAccountDescription) {
        CardDAVAccountDescription = cardDAVAccountDescription;
    }

    public String getCardDAVHostName() {
        return CardDAVHostName;
    }

    public void setCardDAVHostName(String cardDAVHostName) {
        CardDAVHostName = cardDAVHostName;
    }

    public String getCardDAVUsername() {
        return CardDAVUsername;
    }

    public void setCardDAVUsername(String cardDAVUsername) {
        CardDAVUsername = cardDAVUsername;
    }

    public String getCardDAVPassword() {
        return CardDAVPassword;
    }

    public void setCardDAVPassword(String cardDAVPassword) {
        CardDAVPassword = cardDAVPassword;
    }

    public Boolean getCardDAVUseSSL() {
        return CardDAVUseSSL;
    }

    public void setCardDAVUseSSL(Boolean cardDAVUseSSL) {
        CardDAVUseSSL = cardDAVUseSSL;
    }

    public Integer getCardDAVPort() {
        return CardDAVPort;
    }

    public void setCardDAVPort(Integer cardDAVPort) {
        CardDAVPort = cardDAVPort;
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

        PayloadCardDAVPolicy that = (PayloadCardDAVPolicy) o;

        if (CalDAVPrincipalURL != null ? !CalDAVPrincipalURL.equals(that.CalDAVPrincipalURL) : that.CalDAVPrincipalURL != null)
            return false;
        if (CardDAVAccountDescription != null ? !CardDAVAccountDescription.equals(that.CardDAVAccountDescription) : that.CardDAVAccountDescription != null)
            return false;
        if (CardDAVHostName != null ? !CardDAVHostName.equals(that.CardDAVHostName) : that.CardDAVHostName != null)
            return false;
        if (CardDAVPassword != null ? !CardDAVPassword.equals(that.CardDAVPassword) : that.CardDAVPassword != null)
            return false;
        if (CardDAVPort != null ? !CardDAVPort.equals(that.CardDAVPort) : that.CardDAVPort != null) return false;
        if (CardDAVUseSSL != null ? !CardDAVUseSSL.equals(that.CardDAVUseSSL) : that.CardDAVUseSSL != null)
            return false;
        return !(CardDAVUsername != null ? !CardDAVUsername.equals(that.CardDAVUsername) : that.CardDAVUsername != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (CardDAVAccountDescription != null ? CardDAVAccountDescription.hashCode() : 0);
        result = 31 * result + (CardDAVHostName != null ? CardDAVHostName.hashCode() : 0);
        result = 31 * result + (CardDAVUsername != null ? CardDAVUsername.hashCode() : 0);
        result = 31 * result + (CardDAVPassword != null ? CardDAVPassword.hashCode() : 0);
        result = 31 * result + (CardDAVUseSSL != null ? CardDAVUseSSL.hashCode() : 0);
        result = 31 * result + (CardDAVPort != null ? CardDAVPort.hashCode() : 0);
        result = 31 * result + (CalDAVPrincipalURL != null ? CalDAVPrincipalURL.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadCardDAVPolicy{" +
                "CardDAVAccountDescription='" + CardDAVAccountDescription + '\'' +
                ", CardDAVHostName='" + CardDAVHostName + '\'' +
                ", CardDAVUsername='" + CardDAVUsername + '\'' +
                ", CardDAVPassword='" + CardDAVPassword + '\'' +
                ", CardDAVUseSSL=" + CardDAVUseSSL +
                ", CardDAVPort=" + CardDAVPort +
                ", CalDAVPrincipalURL='" + CalDAVPrincipalURL + '\'' +
                '}';
    }
}
