package com.pekall.push.test;

/**
 * Created by jiangrui on 2/11/14.
 */
public class ServerAddress {
    String ip = "";
    int port = -1;

    String path = "";

    private String query = "";

    ServerAddress() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return PushConstant.PUSH_QUERY_PATH;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getWsPath() {
        return path;
    }

    @Override
    public String toString() {
        return "ServerAddress{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", path='" + path + '\'' +
                ", query='" + query + '\'' +
                '}';
    }
}
