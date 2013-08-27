package com.pekall.plist.su.settings.launcher;

/**
 * Element for launcher_web_quick_launch.item
 */
public class WebItem {
    /**
     * Quick launch title
     */
    private String title;
    /**
     * Quick launch url
     */
    private String url;
    /**
     * Quick launch icon url
     */
    private String iconUrl;
    /**
     * Screen number
     */
    private Integer screen;
    /**
     * Row number
     */
    private Integer row;
    /**
     * Row number
     */
    private Integer column;

    public WebItem() {
    }

    public WebItem(String title, String url, String iconUrl,
                   Integer screen, Integer row, Integer column) {
        this.title = title;
        this.url = url;
        this.iconUrl = iconUrl;
        this.screen = screen;
        this.row = row;
        this.column = column;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getScreen() {
        return screen;
    }

    public void setScreen(Integer screen) {
        this.screen = screen;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebItem)) return false;

        WebItem webItem = (WebItem) o;

        if (column != null ? !column.equals(webItem.column) : webItem.column != null) return false;
        if (iconUrl != null ? !iconUrl.equals(webItem.iconUrl) : webItem.iconUrl != null) return false;
        if (row != null ? !row.equals(webItem.row) : webItem.row != null) return false;
        if (screen != null ? !screen.equals(webItem.screen) : webItem.screen != null) return false;
        if (title != null ? !title.equals(webItem.title) : webItem.title != null) return false;
        if (url != null ? !url.equals(webItem.url) : webItem.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (iconUrl != null ? iconUrl.hashCode() : 0);
        result = 31 * result + (screen != null ? screen.hashCode() : 0);
        result = 31 * result + (row != null ? row.hashCode() : 0);
        result = 31 * result + (column != null ? column.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WebItem{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", screen=" + screen +
                ", row=" + row +
                ", column=" + column +
                '}';
    }
}
