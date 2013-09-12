package com.pekall.plist.su.settings.launcher;


import java.util.ArrayList;
import java.util.List;

/**
 * XML configuration for launcher_web_quick_launch
 */
public class WebQuickLaunches {

    private List<WebItem> items;

    public WebQuickLaunches() {
        this(new ArrayList<WebItem>());
    }

    public WebQuickLaunches(List<WebItem> items) {
        this.items = items;
    }

    public List<WebItem> getItems() {
        return items;
    }

    public void setItems(List<WebItem> items) {
        this.items = items;
    }

    public void addItem(WebItem item) {
        items.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebQuickLaunches)) return false;

        WebQuickLaunches that = (WebQuickLaunches) o;

        if (this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (WebItem item : items) {
            result += item.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (WebItem item : items) {
            sb.append(item.toString());
        }
        sb.append("}");
        return "WebQuickLaunches{" +
                "items=" + sb.toString() +
                '}';
    }
}
