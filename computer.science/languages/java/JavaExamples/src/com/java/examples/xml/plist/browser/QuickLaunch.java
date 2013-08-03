package com.java.examples.xml.plist.browser;

import java.util.ArrayList;
import java.util.List;

public class QuickLaunch {
    /**
     * List contains quick launch items
     * Any element named quick_launches should go into this list
     */
    List<QuickLaunchItem> quick_launches = new ArrayList<QuickLaunchItem>();

    public QuickLaunch() {
        this(new ArrayList<QuickLaunchItem>());
    }

    public QuickLaunch(List<QuickLaunchItem> quick_launches) {
        this.quick_launches = quick_launches;
    }

    @Override
    public String toString() {
        StringBuilder quick = new StringBuilder();
        for (QuickLaunchItem item : quick_launches) {
            quick.append(item.toString());
            quick.append('\n');
        }
        return "QuickLaunch{" +
                "quick_launches=" + quick.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuickLaunch)) return false;

        QuickLaunch that = (QuickLaunch) o;

        // if (!quick_launches.equals(that.quick_launches)) return false;
        if (this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int ret = 0;
        for (QuickLaunchItem item : quick_launches) {
            ret += item.hashCode();
        }
        return ret;
    }

    public void addQuickLaunch(QuickLaunchItem item) {
        quick_launches.add(item);
    }

    public List<QuickLaunchItem> getQuick_launches() {
        return quick_launches;
    }

    public void setQuick_launches(List<QuickLaunchItem> quick_launches) {
        this.quick_launches = quick_launches;
    }
}
