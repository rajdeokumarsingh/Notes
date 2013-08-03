package com.java.examples.xml.plist.browser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BrowserXmlData {
    /**
     * 是否现实address bar, 0 表示不显示，1表示现实
     */
    private int addressBarDisplay = -1;
    /**
     * 主页的URL地址
     */
    private String homePageUrl = "";

    private InternalData internalData = new InternalData();

    /**
     * 快速启动列表
     */
    private QuickLaunch quickLaunch = new QuickLaunch();
    /**
     * 白名单
     */
    List<UrlMatchRule> whiteList = new ArrayList<UrlMatchRule>();
    /**
     * 历史监控列表
     */
    List<HistoryWatchItem> historyWatch = new ArrayList<HistoryWatchItem>();

    public BrowserXmlData() {
    }

    public BrowserXmlData(int addressBarDisplay, String homePageUrl) {
        this.addressBarDisplay = addressBarDisplay;
        this.homePageUrl = homePageUrl;
    }

    public int getAddressBarDisplay() {
        return addressBarDisplay;
    }

    public void setAddressBarDisplay(int addressBarDisplay) {
        this.addressBarDisplay = addressBarDisplay;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public QuickLaunch getQuickLaunch() {
        return quickLaunch;
    }

    public void setQuickLaunch(QuickLaunch quickLaunch) {
        this.quickLaunch = quickLaunch;
    }

    public List<UrlMatchRule> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<UrlMatchRule> whiteList) {
        this.whiteList = whiteList;
    }

    public List<HistoryWatchItem> getHistoryWatch() {
        return historyWatch;
    }

    public void setHistoryWatch(List<HistoryWatchItem> historyWatch) {
        this.historyWatch = historyWatch;
    }

    public void addQuickLaunch(QuickLaunchItem item) {
        quickLaunch.addQuickLaunch(item);
    }

    public void addWhiteListItem(UrlMatchRule item) {
        whiteList.add(item);
    }

    public void addHistoryItem(HistoryWatchItem item) {
        historyWatch.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BrowserXmlData)) return false;

        BrowserXmlData that = (BrowserXmlData) o;
        if (this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = addressBarDisplay;
        result = 31 * result + homePageUrl.hashCode();
        result = 31 * result + quickLaunch.hashCode();
        for (UrlMatchRule rule : whiteList) {
            result = 31 * result + rule.hashCode();
        }
        for (HistoryWatchItem item : historyWatch) {
            result = 31 * result + item.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (UrlMatchRule rule : whiteList) {
            sb.append(rule.toString());
        }
        sb.append("}");

        StringBuilder sb1 = new StringBuilder();
        sb1.append("{");
        for (HistoryWatchItem item : historyWatch) {
            sb1.append(item.toString());
        }
        sb1.append("}");

        return "BrowserXmlData{" +
                "addressBarDisplay=" + addressBarDisplay +
                ", homePageUrl='" + homePageUrl + '\'' +
                ", internalData='" + internalData.toString() + '\'' +
                ", quickLaunch=" + quickLaunch.toString() +
                ", whiteList=" + sb.toString() +
                ", historyWatch=" + sb1.toString() +
                '}';
    }
}
