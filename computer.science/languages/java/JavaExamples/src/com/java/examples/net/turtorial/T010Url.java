package com.java.examples.net.turtorial;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class T010Url {
    /**
     * A URL has two main components:
     * Protocol identifier:
     * For the URL http://example.com, the protocol identifier is http.
     * <p/>
     * Resource name:
     * For the URL http://example.com, the resource name is example.com.
     * Host Name
     * The name of the machine on which the resource lives.
     * Filename
     * The pathname to the file on the machine.
     * Port Number
     * The port number to which to connect (typically optional).
     * Reference
     * A reference to a named anchor within a resource
     * that usually identifies a specific location within a file
     * (typically optional).
     */


    public static void main(String[] args) {
        try {
            // Creating a URL
            URL testUrl = new URL("http://example.com/");
            System.out.println(testUrl.toString());

            // Creating relative URL, URL(URL baseURL, String relativeURL)
            URL myURL = new URL("http://example.com/pages/");

            URL page1URL = new URL(myURL, "page1.html");
            System.out.println(page1URL.toString());

            URL page2URL = new URL(myURL, "page2.html");
            System.out.println(page2URL.toString());

            URL page1BottomURL = new URL(page1URL,"#BOTTOM"); // url with anchor
            System.out.println(page1BottomURL.toString());
            System.out.println("getProtocol: " + page1BottomURL.getProtocol());
            System.out.println("getAuthority: " + page1BottomURL.getAuthority());
            System.out.println("getHost: " + page1BottomURL.getHost());
            System.out.println("getPort: " + page1BottomURL.getPort());
            System.out.println("getPath: " + page1BottomURL.getPath());
            System.out.println("getQuery: " + page1BottomURL.getQuery());
            System.out.println("getFile: " + page1BottomURL.getFile());
            System.out.println("getRef: " + page1BottomURL.getRef());

            // other constructor

            // equal to new URL("http://example.com/pages/page1.html");
            new URL("http", "example.com", "/pages/page1.html");

            // url with port
            // http://example.com:80/pages/page1.html
            URL gamelan = new URL("http", "example.com", 80, "pages/page1.html");
            System.out.println(gamelan.toString());

            // URL addresses with Special characters
            URL urlWithSpace = new URL("http://example.com/hello%20world");
            System.out.println(urlWithSpace.toString());

            // auto convert encoding with URI
            URI uri = new URI("http", "example.com", "/hello world/", "");
            URL urlWithSpace1 = uri.toURL();
            System.out.println(urlWithSpace1.toString());

            printUrlComponents();
            // URLs are "write-once" objects. Once you've created a URL object,
            // you cannot change any of its attributes (protocol, host name,
            // filename, or port number).
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static void printUrlComponents() throws MalformedURLException {
        URL myURL = new URL("http://example.com:80/docs/books/tutorial");
        URL page1BottomURL = new URL(myURL,"index.html?name=networking#DOWNLOADING");

        System.out.println(page1BottomURL.toString());
        System.out.println("getProtocol: " + page1BottomURL.getProtocol());
        System.out.println("getAuthority: " + page1BottomURL.getAuthority());
        System.out.println("getHost: " + page1BottomURL.getHost());
        System.out.println("getPort: " + page1BottomURL.getPort());
        System.out.println("getPath: " + page1BottomURL.getPath());
        System.out.println("getQuery: " + page1BottomURL.getQuery());
        System.out.println("getFile: " + page1BottomURL.getFile());
        System.out.println("getRef: " + page1BottomURL.getRef());
    }
}
