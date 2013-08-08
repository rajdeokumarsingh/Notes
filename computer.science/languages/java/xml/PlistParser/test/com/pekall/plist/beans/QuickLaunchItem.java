package com.pekall.plist.beans;

import java.util.Arrays;

public class QuickLaunchItem {
    /**
     * DB id
     */
    private String mId = "";

    /**
     * Title of the quick launch
     */
    private String mTitle = "";

    /**
     * Web address of the quick launch
     */
    private String mUrl = "";

    /**
     * Whether need to update the icon. At first, each quick launch item
     * has a default icon. After user visits the website, the beans will
     * capture a bitmap for its icon and set mNeedupdate to 0.
     */
    private int mNeedUpdate = 1;

    /**
     * icon of the quick launch
     */
    private byte[] mBlob = new byte[0];

    public QuickLaunchItem() {
        this("", "", "", new byte[0]);
    }

    /**
     * Constructor
     *
     * @param id     of the item
     * @param title  of the item
     * @param url    of the item
     * @param icon   of the item
     * @param update whether the icon should be updated
     */
    public QuickLaunchItem(String id, String title, String url, byte[] icon, int update) {
        this.mId = id;
        this.mTitle = title;
        this.mUrl = url;
        this.mBlob = icon;
        this.mNeedUpdate = update;
    }

    /**
     * Constructor
     *
     * @param id    of the item
     * @param title of the item
     * @param url   of the item
     * @param icon  of the item
     */
    public QuickLaunchItem(String id, String title, String url, byte[] icon) {
        this(id, title, url, new byte[0], 1);
    }

    /**
     * Constructor
     *
     * @param id    of the item
     * @param title of the item
     * @param url   of the item
     */
    public QuickLaunchItem(String id, String title, String url) {
        this(id, title, url, new byte[0]);
    }

    /**
     * Get the id of the quick launch item
     *
     * @return the id
     */
    public String getId() {
        return mId;
    }

    /**
     * Set the id of the quick launch item
     *
     * @param id of the item
     * @hide for internal use
     */
    public void setId(String id) {
        this.mId = id;
    }

    /**
     * Get the title of the quick launch item
     *
     * @return the title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Set the title of the quick launch item
     *
     * @param title of the item
     */
    public void setTitle(String title) {
        this.mTitle = title;
    }

    /**
     * Get the web address of the quick launch item
     *
     * @return the web url
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Set the web address of the quick launch item
     *
     * @param url of the item
     */
    public void setUrl(String url) {
        this.mUrl = url;
    }

    /**
     * Get the icon of the quick launch item
     *
     * @return the icon
     */
    public byte[] getIcon() {
        return mBlob;
    }

    /**
     * Set the icon of the quick launch item
     *
     * @param icon
     */
    public void setIcon(byte[] icon) {
        this.mBlob = icon;
    }

    /**
     * Get whether the icon need to update
     *
     * @return whether the icon need to update
     */
    public int getNeedUpdate() {
        return mNeedUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuickLaunchItem)) return false;

        QuickLaunchItem item = (QuickLaunchItem) o;

        if (mNeedUpdate != item.mNeedUpdate) return false;
        if (!Arrays.equals(mBlob, item.mBlob)) return false;
        if (!mId.equals(item.mId)) return false;
        if (!mTitle.equals(item.mTitle)) return false;
        if (!mUrl.equals(item.mUrl)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + mTitle.hashCode();
        result = 31 * result + mUrl.hashCode();
        result = 31 * result + mNeedUpdate;
        result = 31 * result + Arrays.hashCode(mBlob);
        return result;
    }

    @Override
    public String toString() {
        return "QuickLaunchItem{" +
                "mId='" + mId + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", mNeedUpdate=" + mNeedUpdate +
                '}';
    }
}
