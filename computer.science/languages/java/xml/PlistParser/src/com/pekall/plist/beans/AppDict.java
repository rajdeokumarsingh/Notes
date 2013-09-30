package com.pekall.plist.beans;

public class AppDict {

    /**
     * The bundle identifier of the application.
     */
    private String Identifier;

    public AppDict() {
    }

    public AppDict(String identifier) {
        Identifier = identifier;
    }

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppDict)) return false;

        AppDict appDict = (AppDict) o;

        if (Identifier != null ? !Identifier.equals(appDict.Identifier) : appDict.Identifier != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Identifier != null ? Identifier.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AppDict{" +
                "Identifier='" + Identifier + '\'' +
                '}';
    }
}
