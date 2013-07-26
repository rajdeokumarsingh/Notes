package com.java.examples.xml.xstream.alias;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-20
 * Time: 下午6:44
 * To change this template use File | Settings | File Templates.
 */
public class BlogTest extends TestCase {

    public void testXml001() throws Exception {

        Blog teamBlog = new Blog("b001", new Author("Guilherme Silveira"));
        teamBlog.add(new Entry("first","My first blog entry."));
        teamBlog.add(new Entry("tutorial",
                "Today we have developed a nice alias tutorial. Tell your friends! NOW!"));

        XStream xstream = new XStream(
                new DomDriver("UTF-8", new NoNameCoder()));
        System.out.println(xstream.toXML(teamBlog));
    }

    public void testXml002() throws Exception {
        Blog teamBlog = new Blog("b001", new Author("Guilherme Silveira"));
        teamBlog.add(new Entry("first","My first blog entry."));
        teamBlog.add(new Entry("tutorial",
                "Today we have developed a nice alias tutorial. Tell your friends! NOW!"));

        XStream xstream = new XStream(
                new DomDriver("UTF-8", new NoNameCoder()));
        xstream.alias("blog", Blog.class);
        xstream.alias("entry", Entry.class);
        System.out.println(xstream.toXML(teamBlog));
    }

    public void testXml003() throws Exception {
        Blog teamBlog = new Blog("b001", new Author("Guilherme Silveira"));
        teamBlog.add(new Entry("first","My first blog entry."));
        teamBlog.add(new Entry("tutorial",
                "Today we have developed a nice alias tutorial. Tell your friends! NOW!"));

        XStream xstream = new XStream(
                new DomDriver("UTF-8", new NoNameCoder()));
        xstream.alias("blog", Blog.class);
        xstream.alias("entry", Entry.class);
        // rename writer to author
        xstream.aliasAttribute(Blog.class, "writer", "author");
        System.out.println(xstream.toXML(teamBlog));
    }

    public void testXml004() throws Exception {
        Blog teamBlog = new Blog("b001", new Author("Guilherme Silveira"));
        teamBlog.add(new Entry("first","My first blog entry."));
        teamBlog.add(new Entry("tutorial",
                "Today we have developed a nice alias tutorial. Tell your friends! NOW!"));

        XStream xstream = new XStream(
                new DomDriver("UTF-8", new NoNameCoder()));
        xstream.alias("blog", Blog.class);
        xstream.alias("entry", Entry.class);
        // rename writer to author
        xstream.aliasAttribute(Blog.class, "writer", "author");

        xstream.addImplicitCollection(Blog.class, "entries");
        System.out.println(xstream.toXML(teamBlog));
    }

    public void testXml005() throws Exception {
        Blog teamBlog = new Blog("b001", new Author("Guilherme Silveira"));
        teamBlog.add(new Entry("first","My first blog entry."));
        teamBlog.add(new Entry("tutorial",
                "Today we have developed a nice alias tutorial. " +
                        "Tell your friends! NOW!"));

        XStream xstream = new XStream(
                new DomDriver("UTF-8", new NoNameCoder()));
        xstream.alias("blog", Blog.class);
        xstream.alias("entry", Entry.class);

        // rename writer to author
        xstream.useAttributeFor(Blog.class, "writer");

        xstream.addImplicitCollection(Blog.class, "entries");
        System.out.println(xstream.toXML(teamBlog));
    }

    public void testXml() throws Exception {
        Blog teamBlog = new Blog("b001", new Author("Guilherme Silveira"));
        teamBlog.add(new Entry("first","My first blog entry."));
        teamBlog.add(new Entry("tutorial",
                "Today we have developed a nice alias tutorial. " +
                        "Tell your friends! NOW!"));

        XStream xStream = new XStream(
                new DomDriver("UTF-8", new NoNameCoder()));
        xStream.alias("blog", Blog.class);
        xStream.alias("entry", Entry.class);
        xStream.addImplicitCollection(Blog.class, "entries");

        xStream.useAttributeFor(Blog.class, "mId");
        // rename mId to id
        xStream.aliasAttribute(Blog.class, "mId", "id");

        xStream.useAttributeFor(Blog.class, "writer");
        // rename writer to author
        xStream.aliasAttribute(Blog.class, "writer", "author");
        xStream.registerConverter(new AuthorConverter());

        String xml = xStream.toXML(teamBlog);
        System.out.println(xml);
    }

    public void testXml006AliasPackage() throws Exception {

        Blog teamBlog = new Blog("b001", new Author("Guilherme Silveira"));
        teamBlog.add(new Entry("first","My first blog entry."));
        teamBlog.add(new Entry("tutorial",
                "Today we have developed a nice alias tutorial. Tell your friends! NOW!"));

        XStream xstream = new XStream(
                new DomDriver("UTF-8", new NoNameCoder()));
        xstream.aliasPackage("org.pekall.mdm", "com.java.examples");
        System.out.println(xstream.toXML(teamBlog));
    }

}
