package com.pekall.plist.beans;

/**
 * VPN proxies profile
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class VpnProxies  {

    private Integer HTTPEnable;
    private Integer HTTPPort;
    private String  HTTPProxy;
    private String  HTTPProxyPassword;
    private String  HTTPProxyUsername;
    private Integer  HTTPSEnable;
    private Integer  HTTPSPort;
    private String  HTTPSProxy;


    private Integer ProxyAutoConfigEnable;
    private String ProxyAutoConfigURLString;

    public Integer getProxyAutoConfigEnable() {
        return ProxyAutoConfigEnable;
    }

    public void setProxyAutoConfigEnable(Integer proxyAutoConfigEnable) {
        ProxyAutoConfigEnable = proxyAutoConfigEnable;
    }

    public String getProxyAutoConfigURLString() {
        return ProxyAutoConfigURLString;
    }

    public void setProxyAutoConfigURLString(String proxyAutoConfigURLString) {
        ProxyAutoConfigURLString = proxyAutoConfigURLString;
    }

    public Integer getHTTPEnable() {
        return HTTPEnable;
    }

    public void setHTTPEnable(Integer HTTPEnable) {
        this.HTTPEnable = HTTPEnable;
    }

    public Integer getHTTPPort() {
        return HTTPPort;
    }

    public void setHTTPPort(Integer HTTPPort) {
        this.HTTPPort = HTTPPort;
    }

    public String getHTTPProxy() {
        return HTTPProxy;
    }

    public void setHTTPProxy(String HTTPProxy) {
        this.HTTPProxy = HTTPProxy;
    }

    public String getHTTPProxyPassword() {
        return HTTPProxyPassword;
    }

    public void setHTTPProxyPassword(String HTTPProxyPassword) {
        this.HTTPProxyPassword = HTTPProxyPassword;
    }

    public String getHTTPProxyUsername() {
        return HTTPProxyUsername;
    }

    public void setHTTPProxyUsername(String HTTPProxyUsername) {
        this.HTTPProxyUsername = HTTPProxyUsername;
    }

    public Integer getHTTPSEnable() {
        return HTTPSEnable;
    }

    public void setHTTPSEnable(Integer HTTPSEnable) {
        this.HTTPSEnable = HTTPSEnable;
    }

    public Integer getHTTPSPort() {
        return HTTPSPort;
    }

    public void setHTTPSPort(Integer HTTPSPort) {
        this.HTTPSPort = HTTPSPort;
    }

    public String getHTTPSProxy() {
        return HTTPSProxy;
    }

    public void setHTTPSProxy(String HTTPSProxy) {
        this.HTTPSProxy = HTTPSProxy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VpnProxies)) return false;

        VpnProxies that = (VpnProxies) o;

        if (HTTPEnable != null ? !HTTPEnable.equals(that.HTTPEnable) : that.HTTPEnable != null) return false;
        if (HTTPPort != null ? !HTTPPort.equals(that.HTTPPort) : that.HTTPPort != null) return false;
        if (HTTPProxy != null ? !HTTPProxy.equals(that.HTTPProxy) : that.HTTPProxy != null) return false;
        if (HTTPProxyPassword != null ? !HTTPProxyPassword.equals(that.HTTPProxyPassword) : that.HTTPProxyPassword != null)
            return false;
        if (HTTPProxyUsername != null ? !HTTPProxyUsername.equals(that.HTTPProxyUsername) : that.HTTPProxyUsername != null)
            return false;
        if (HTTPSEnable != null ? !HTTPSEnable.equals(that.HTTPSEnable) : that.HTTPSEnable != null) return false;
        if (HTTPSPort != null ? !HTTPSPort.equals(that.HTTPSPort) : that.HTTPSPort != null) return false;
        return !(HTTPSProxy != null ? !HTTPSProxy.equals(that.HTTPSProxy) : that.HTTPSProxy != null);

    }

    @Override
    public int hashCode() {
        int result = HTTPEnable != null ? HTTPEnable.hashCode() : 0;
        result = 31 * result + (HTTPPort != null ? HTTPPort.hashCode() : 0);
        result = 31 * result + (HTTPProxy != null ? HTTPProxy.hashCode() : 0);
        result = 31 * result + (HTTPProxyPassword != null ? HTTPProxyPassword.hashCode() : 0);
        result = 31 * result + (HTTPProxyUsername != null ? HTTPProxyUsername.hashCode() : 0);
        result = 31 * result + (HTTPSEnable != null ? HTTPSEnable.hashCode() : 0);
        result = 31 * result + (HTTPSPort != null ? HTTPSPort.hashCode() : 0);
        result = 31 * result + (HTTPSProxy != null ? HTTPSProxy.hashCode() : 0);
        return result;
    }
}
