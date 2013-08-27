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

package com.pekall.plist.su.settings.launcher;

/**
 * XML configuration for launcher_app_info.item
 */
public class ApkItem {
    /**
     * Name of the application
     */
    private String name;
    /**
     * Package name of the application
     */
    private String packageName;
    /**
     * Class name of the application
     */
    private String className;
    /**
     * Screen in which the application is in
     */
    private Integer screen;
    /**
     * Row number of the application in the screen
     */
    private Integer row;
    /**
     * Column number of the application in the screen
     */
    private Integer column;

    /**
     * Url for the icon of the application
     */
    private String icon_url;

    public ApkItem() {
    }

    public ApkItem(String name, String packageName, String className, String icon_url,
                   Integer screen, Integer row, Integer column) {
        this.name = name;
        this.packageName = packageName;
        this.className = className;
        this.icon_url = icon_url;
        this.screen = screen;
        this.row = row;
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApkItem)) return false;

        ApkItem apkItem = (ApkItem) o;

        if (className != null ? !className.equals(apkItem.className) : apkItem.className != null) return false;
        if (column != null ? !column.equals(apkItem.column) : apkItem.column != null) return false;
        if (icon_url != null ? !icon_url.equals(apkItem.icon_url) : apkItem.icon_url != null) return false;
        if (name != null ? !name.equals(apkItem.name) : apkItem.name != null) return false;
        if (packageName != null ? !packageName.equals(apkItem.packageName) : apkItem.packageName != null) return false;
        if (row != null ? !row.equals(apkItem.row) : apkItem.row != null) return false;
        if (screen != null ? !screen.equals(apkItem.screen) : apkItem.screen != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (packageName != null ? packageName.hashCode() : 0);
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (screen != null ? screen.hashCode() : 0);
        result = 31 * result + (row != null ? row.hashCode() : 0);
        result = 31 * result + (column != null ? column.hashCode() : 0);
        result = 31 * result + (icon_url != null ? icon_url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApkItem{" +
                "name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", screen=" + screen +
                ", row=" + row +
                ", column=" + column +
                ", icon_url='" + icon_url + '\'' +
                '}';
    }
}
