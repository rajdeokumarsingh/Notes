package com.pekall.plist.su.settings.browser;


import java.util.Arrays;

/**
 * Store information of a quick launch item.
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class QuickLaunchItem {
    /** DB id */
    private String id;

    /** Title of the quick launch */
    private String title;

    /** Web address of the quick launch */
    private String url;

    /**
     * Whether need to update the icon. At first, each quick launch item
     * has a default icon. After user visits the website, the browser will
     * capture a bitmap for its icon and set needUpdate to 0.
     */
    private int needUpdate = 1;

    /** icon of the quick launch */
    private byte[] blob;

    public QuickLaunchItem() {
        this("", "", "", new byte[0]);
    }

    /**
     * Constructor
     *
     * @param id of the item
     * @param title of the item
     * @param url of the item
     * @param icon of the item
     * @param update whether the icon should be updated
     */
    private QuickLaunchItem(String id, String title, String url, byte[] icon, int update) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.blob = icon;
        this.needUpdate = update;
    }

    /**
     * Constructor
     *
     * @param id of the item
     * @param title of the item
     * @param url of the item
     * @param icon of the item
     */
    private QuickLaunchItem(String id, String title, String url, byte[] icon) {
        this(id, title, url, new byte[0], 1);
    }

    /**
     * Constructor
     *
     * @param id of the item
     * @param title of the item
     * @param url of the item
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
        return id;
    }

    /**
     * Set the id of the quick launch item
     *
     * @param id of the item
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the title of the quick launch item
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the quick launch item
     *
     * @param title of the item
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the web address of the quick launch item
     *
     * @return the web url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the web address of the quick launch item
     *
     * @param url of the item
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get the icon of the quick launch item
     *
     * @return the icon
     */
    public byte[] getIcon() {
        return blob;
    }

    /**
     * Set the icon of the quick launch item
     *
     * @param icon icon of the quick launch
     */
    public void setIcon(byte[] icon) {
        this.blob = icon;
    }

    /**
     * Get whether the icon need to update
     * @return whether the icon need to update
     */
    public int getNeedUpdate() {
        return needUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuickLaunchItem)) return false;

        QuickLaunchItem item = (QuickLaunchItem) o;

//        if (needUpdate != item.needUpdate) return false;
//        if (!Arrays.equals(blob, item.blob)) return false;
//        if (!id.equals(item.id)) return false;
        if (!title.equals(item.title)) return false;
        return url.equals(item.url);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + needUpdate;
        result = 31 * result + Arrays.hashCode(blob);
        return result;
    }
}
