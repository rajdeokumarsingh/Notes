package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

public class PayloadAPN extends PayloadBase {

    /**
     * This string specifies the Access Point Name. Just use for web json
     */
    @PlistControl(toPlistXml = false)
    private String apn;

    /**
     * This string specifies the user name for this APN. If it is
     * missing, the device prompts for it during profile installation.
     *
     * Just use for web json
     */
    @PlistControl(toPlistXml = false)
    private String username;

    /**
     * Optional. This data represents the password for the user for this APN.
     * For obfuscation purposes, the password is encoded. If it is missing from the payload,
     * the device prompts for the password during profile installation.
     *
     * Just use for web json
     */
    @PlistControl(toPlistXml = false)
    private String password;

    /**
     * Optional. The IP address or URL of the APN proxy.
     *
     * Just use for web json
     */
    @PlistControl(toPlistXml = false)
    private String proxy;

    /**
     * Optional. The port number of the APN proxy.
     *
     * Just use for web json
     */
    @PlistControl(toPlistXml = false)
    private Integer proxyPort;

    public String getApn() {
        return apn;
    }

    public void setApn(String apn) {
        this.apn = apn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private List<APNDataArray> PayloadContent;

    public List<APNDataArray> getPayloadContent() {
        return PayloadContent;
    }

    public void setPayloadContent(List<APNDataArray> payloadContent) {
        PayloadContent = payloadContent;
    }


    public void addPayloadContent(APNDataArray payloadContent) {
      if(PayloadContent == null)
          PayloadContent = new ArrayList<APNDataArray>();
        PayloadContent.add(payloadContent);
    }

    public PayloadAPN() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_APN);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadAPN)) return false;
        if (!super.equals(o)) return false;

        PayloadAPN that = (PayloadAPN) o;

        if (PayloadContent != null ? !PayloadContent.equals(that.PayloadContent) : that.PayloadContent != null)
            return false;
        if (apn != null ? !apn.equals(that.apn) : that.apn != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (proxy != null ? !proxy.equals(that.proxy) : that.proxy != null) return false;
        if (proxyPort != null ? !proxyPort.equals(that.proxyPort) : that.proxyPort != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (apn != null ? apn.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (proxy != null ? proxy.hashCode() : 0);
        result = 31 * result + (proxyPort != null ? proxyPort.hashCode() : 0);
        result = 31 * result + (PayloadContent != null ? PayloadContent.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadAPN{" +
                "apn='" + apn + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", proxy='" + proxy + '\'' +
                ", proxyPort=" + proxyPort +
                ", PayloadContent=" + PayloadContent +
                "} " + super.toString();
    }
}
