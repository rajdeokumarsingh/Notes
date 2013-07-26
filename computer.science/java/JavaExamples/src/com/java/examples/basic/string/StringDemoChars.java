package com.java.examples.basic.string;

import static java.lang.System.out;

class Filename {
    private String fullPath;
    private char pathSeparator, 
                 extensionSeparator;

    public Filename(String str, char sep, char ext) {
        fullPath = str;
        pathSeparator = sep;
        extensionSeparator = ext;
    }

    public String extension() {
        int dot = fullPath.lastIndexOf(extensionSeparator);
        return fullPath.substring(dot + 1);
    }

    // gets filename without extension
    public String filename() {
        int dot = fullPath.lastIndexOf(extensionSeparator);
        int sep = fullPath.lastIndexOf(pathSeparator);
        return fullPath.substring(sep + 1, dot);
    }

    public String path() {
        int sep = fullPath.lastIndexOf(pathSeparator);
        return fullPath.substring(0, sep);
    }
}

public class StringDemoChars {

    public static void main(String[] args) {
//        testCharAt();
        final String FPATH = "/home/user/index.html";
        Filename myHomePage = new Filename(FPATH, '/', '.');
        System.out.println("Extension = " + myHomePage.extension());
        System.out.println("Filename = " + myHomePage.filename());
        System.out.println("Path = " + myHomePage.path());
    }

    private static void testCharAt() {
        String anotherPalindrome = "Niagara. O roar again!"; 
        for(int i=0;i<anotherPalindrome.length();i++) {
            out.format("%c%n", anotherPalindrome.charAt(i));
        }
        
        String roar = anotherPalindrome.substring(11, 15); 
        out.format("%s", roar);
    }
}
