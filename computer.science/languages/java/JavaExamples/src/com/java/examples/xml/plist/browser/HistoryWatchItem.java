package com.java.examples.xml.plist.browser;

import java.util.Date;

public class HistoryWatchItem extends UrlMatchRule {

    /** Visit count */
    private int mCount = 0;

    /** The format of the date is ";long1;long2;long3;...;longn"*/
    private String mDates = "";

    /**
     * Constructor
     *
     * @param url to watch
     * @param type of the watch list, see {@link UrlMatchRule.MATCH_TYPE_CONTAIN, ...}
     */
    public HistoryWatchItem(String url, int type) {
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
        super("");
    }

    /**
     * Add visit count and time
     */
    public void updateVisit() {
        mDates += ";" + new Date().getTime();
        mCount++;
    }


    /**
     * Set the visit count
     *
     * @param count of the item
     * @hide for internal use
     */
    public void setCount(int count) {
        this.mCount = count;
    }

    /**
     * Get visit count
     *
     * @return visit count
     */
    public int getCount() {
        return mCount;
    }

    /**
     * Set visit dates
     *
     * @param date of the watch list
     * @hide for internal use
     */
    public void setDate(String date) {
        this.mDates = date;
    }

    /**
     * Get visit date
     *
     * @return visit date
     */
    public String getDate() {
        return mDates;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "HistoryWatchItem{" +
                super.toString() +
                "mCount=" + mCount +
                ", mDates='" + mDates + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoryWatchItem)) return false;

        HistoryWatchItem item = (HistoryWatchItem) o;

        if (mCount != item.mCount) return false;
        if (!mDates.equals(item.mDates)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mCount;
        result = 31 * result + mDates.hashCode();
        return result;
    }
}
