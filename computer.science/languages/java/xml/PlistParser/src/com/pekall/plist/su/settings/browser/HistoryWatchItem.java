package com.pekall.plist.su.settings.browser;


import java.util.Date;

/**
 * Store information a history watch list
 */
public class HistoryWatchItem extends UrlMatchRule {

    /** Visit count */
    private Integer count = 0;

    /** The format of the date is ";long1;long2;long3;...;longn"*/
    private String dates = "";

    /**
     * Constructor
     *
     * @param url to watch
     * @param type of the watch list, see {@link UrlMatchRule.MATCH_TYPE_CONTAIN, ...}
     */
    public HistoryWatchItem(String url, Integer type) {
        super(url, type);
    }

    /**
     * Constructor
     *
     * @param url to watch
     */
    public HistoryWatchItem(String url) {
        super(url);
    }

    public HistoryWatchItem() {
        super(null);
    }

    /**
     * Add visit count and time
     */
    public void updateVisit() {
        dates += ";" + new Date().getTime();
        count++;
    }


    /**
     * Set the visit count
     *
     * @param count of the item
     * @hide for internal use
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * Get visit count
     *
     * @return visit count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Set visit dates
     *
     * @param date of the watch list
     * @hide for internal use
     */
    public void setDate(String date) {
        this.dates = date;
    }

    /**
     * Get visit date
     *
     * @return visit date
     */
    public String getDate() {
        return dates;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "HistoryWatchItem{" +
                super.toString() +
                "count=" + count +
                ", dates='" + dates + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoryWatchItem)) return false;

        HistoryWatchItem item = (HistoryWatchItem) o;

        if (count != item.count) return false;
        if (!dates.equals(item.dates)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = count;
        result = 31 * result + dates.hashCode();
        return result;
    }
}
