package com.java.examples.xml.xstream.alias;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-20
 * Time: 下午6:34
 * To change this template use File | Settings | File Templates.
 */
public class Entry {
    private String title, description;

    public Entry() {
        this("", "");
    }

    public Entry(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (!description.equals(entry.description)) return false;
        if (!title.equals(entry.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
