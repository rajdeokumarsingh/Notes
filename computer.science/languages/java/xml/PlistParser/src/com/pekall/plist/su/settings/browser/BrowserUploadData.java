package com.pekall.plist.su.settings.browser;

import com.pekall.plist.beans.CommandStatusMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * XML configuration for sebrowser_history_watch
 */
public class BrowserUploadData extends CommandStatusMsg {
    /**
     * List contains history watch historyWatchItems
     */
    List<HistoryWatchItem> historyWatchItems;

    public BrowserUploadData() {
    }

    public BrowserUploadData(String status, String UDID, String commandUUID) {
        super(status, UDID, commandUUID);
    }

    public List<HistoryWatchItem> getHistoryWatchItems() {
        return historyWatchItems;
    }

    public void setHistoryWatchItems(List<HistoryWatchItem> historyWatchItems) {
        this.historyWatchItems = historyWatchItems;
    }

    public void addItem(HistoryWatchItem item) {
        if (historyWatchItems == null) {
            historyWatchItems = new ArrayList<HistoryWatchItem>();
        }
        this.historyWatchItems.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BrowserUploadData)) return false;

        BrowserUploadData that = (BrowserUploadData) o;

        if(this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        if(historyWatchItems != null) {
            for (HistoryWatchItem item : historyWatchItems) {
                result += item.hashCode();
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if(historyWatchItems != null) {
            for (HistoryWatchItem item : historyWatchItems) {
                sb.append(item.toString() + ", ");
            }
        }
        sb.append("}");
        return "BrowserUploadData{" +
                "historyWatchItems=" + historyWatchItems +
                '}';
    }
}
