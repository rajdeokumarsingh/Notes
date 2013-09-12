/*---------------------------------------------------------------------------------------------
 *                       Copyright (c) 2013 Capital Alliance Software(Pekall) 
 *                                    All Rights Reserved
 *    NOTICE: All information contained herein is, and remains the property of Pekall and
 *      its suppliers,if any. The intellectual and technical concepts contained herein are
 *      proprietary to Pekall and its suppliers and may be covered by P.R.C, U.S. and Foreign
 *      Patents, patents in process, and are protected by trade secret or copyright law.
 *      Dissemination of this information or reproduction of this material is strictly 
 *      forbidden unless prior written permission is obtained from Pekall.
 *                                     www.pekall.com
 *--------------------------------------------------------------------------------------------- 
*/

package com.pekall.plist.su.settings.browser;

import com.pekall.plist.beans.PayloadBase;

import java.util.List;

/**
 * Store data parsed from XML configuration of the security browser
 */
public class BrowserSettings extends PayloadBase {
    /**
     * 是否现实address bar, 0 表示不显示，1表示现实
     */
    private Integer addressBarDisplay;
    /**
     * 主页的URL地址
     */
    private String homePageUrl;
    /**
     * 快速启动列表
     */
    private List<QuickLaunchItem> quickLaunchItems;

    /**
     * 白名单
     */
    private List<UrlMatchRule> whiteList;

    /**
     * 历史监控列表
     */
    private List<HistoryWatchItem> historyWatchItems;

    public BrowserSettings() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_SE_BROWSER_SETTINGS);
    }

    public BrowserSettings(Integer addressBarDisplay, String homePageUrl,
                           List<QuickLaunchItem> quickLaunchItems,
                           List<UrlMatchRule> whiteList,
                           List<HistoryWatchItem> historyWatchItems) {
        this.addressBarDisplay = addressBarDisplay;
        this.homePageUrl = homePageUrl;
        this.quickLaunchItems = quickLaunchItems;
        this.whiteList = whiteList;
        this.historyWatchItems = historyWatchItems;

        setPayloadType(PayloadBase.PAYLOAD_TYPE_SE_BROWSER_SETTINGS);
    }

    public Integer getAddressBarDisplay() {
        return addressBarDisplay;
    }

    public void setAddressBarDisplay(Integer addressBarDisplay) {
        this.addressBarDisplay = addressBarDisplay;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public List<QuickLaunchItem> getQuickLaunchItems() {
        return quickLaunchItems;
    }

    public void setQuickLaunchItems(List<QuickLaunchItem> quickLaunchItems) {
        this.quickLaunchItems = quickLaunchItems;
    }

    public List<UrlMatchRule> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<UrlMatchRule> whiteList) {
        this.whiteList = whiteList;
    }

    public List<HistoryWatchItem> getHistoryWatchItems() {
        return historyWatchItems;
    }

    public void setHistoryWatchItems(List<HistoryWatchItem> historyWatchItems) {
        this.historyWatchItems = historyWatchItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BrowserSettings)) return false;
        if (!super.equals(o)) return false;

        BrowserSettings settings = (BrowserSettings) o;
        if(this.hashCode() != o.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + addressBarDisplay;
        result = 31 * result + (homePageUrl != null ? homePageUrl.hashCode() : 0);

        if (quickLaunchItems != null) {
            for (QuickLaunchItem item : quickLaunchItems) {
                result += item.hashCode();
            }
        }
        if (whiteList != null) {
            for (UrlMatchRule rule : whiteList) {
                result += rule.hashCode();
            }
        }
        if (historyWatchItems != null) {
            for (HistoryWatchItem historyWatchItem : historyWatchItems) {
                result += historyWatchItem.hashCode();
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "BrowserSettings{" +
                "addressBarDisplay=" + addressBarDisplay +
                ", homePageUrl='" + homePageUrl + '\'' +
                ", quickLaunchItems=" + quickLaunchItems +
                ", whiteList=" + whiteList +
                ", historyWatchItems=" + historyWatchItems +
                '}';
    }
}
