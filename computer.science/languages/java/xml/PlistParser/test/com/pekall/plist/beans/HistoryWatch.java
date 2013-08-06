package com.pekall.plist.beans;


import java.util.ArrayList;
import java.util.List;

/**
 * XML element for sebrowser_config.historyWatch
 */
public class HistoryWatch {
    /**
     * List contains history watch items
     * Any element named item should go into this list
     */
    List<HistoryWatchItem> history_watch = new ArrayList<HistoryWatchItem>();

    public HistoryWatch() {
        this(new ArrayList<HistoryWatchItem>());
    }

    public HistoryWatch(List<HistoryWatchItem> history_watch) {
        this.history_watch = history_watch;
    }

    @Override
    public String toString() {
        StringBuilder history = new StringBuilder();
        for (HistoryWatchItem hist : history_watch) {
            history.append(hist.toString());
            history.append('\n');
        }
        return "HistoryWatch{" +
                "historyWatch=" + history.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoryWatch)) return false;

        HistoryWatch that = (HistoryWatch) o;

        // if (!historyWatch.equals(that.historyWatch)) return false;
        if (this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int ret = 0;
        for (HistoryWatchItem item : history_watch) {
            ret += item.hashCode();
        }
        return ret;
    }

    public void addHistoryWatch(HistoryWatchItem item) {
        history_watch.add(item);
    }

    public List<HistoryWatchItem> getHistory_watch() {
        return history_watch;
    }

    public void setHistory_watch(List<HistoryWatchItem> history_watch) {
        this.history_watch = history_watch;
    }
}
