package com.java.examples.xml.xstream.alias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-20
 * Time: 下午6:33
 * To change this template use File | Settings | File Templates.
 */
public class Blog {
    private String mId;
    private Author writer;
    private List<Entry> entries = new ArrayList<Entry>();

    public Blog() {
        this("", new Author());
    }

    public Blog(String id, Author writer) {
        this.mId = id;
        this.writer = writer;
    }

    public void add(Entry entry) {
        entries.add(entry);
    }

    public List getContent() {
        return entries;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Entry e : entries) {
            sb.append(e.toString());
        }
        return "Blog{" +
                "writer=" + writer.toString() +
                ", entries=" + sb.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Blog blog = (Blog) o;

        if (!entries.equals(blog.entries)) return false;
        if (!writer.equals(blog.writer)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = writer.hashCode();
        for (Entry e : entries) {
            result = 31 * result + e.hashCode();
        }
        return result;
    }
}
