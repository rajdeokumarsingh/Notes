package com.pekall.plist.beans;

import java.util.List;

public class ScepContent {
    private String Challenge;

    /** two underlines will be converted to a space in xml */
    private String Key__Type;

    /** two underlines will be converted to a space in xml */
    private Integer Key__Usage;

    private Integer Keysize;

    private String Name;

    private Integer Retries;

    // todo, check it is ok
    private List<List<List<String>>> Subject;

    private String URL;

    public ScepContent() {
    }

    public String getChallenge() {
        return Challenge;
    }

    public void setChallenge(String challenge) {
        Challenge = challenge;
    }

    public String getKey_Type() {
        return Key__Type;
    }

    public void setKeyType(String key_Type) {
        Key__Type = key_Type;
    }

    public Integer getKey_Usage() {
        return Key__Usage;
    }

    public void setKeyUsage(Integer key_Usage) {
        Key__Usage = key_Usage;
    }

    public Integer getKeysize() {
        return Keysize;
    }

    public void setKeysize(Integer keysize) {
        Keysize = keysize;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getRetries() {
        return Retries;
    }

    public void setRetries(Integer retries) {
        Retries = retries;
    }

    public List<List<List<String>>> getSubject() {
        return Subject;
    }

    public void setSubject(List<List<List<String>>> subject) {
        Subject = subject;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScepContent)) return false;

        ScepContent that = (ScepContent) o;

        if (Challenge != null ? !Challenge.equals(that.Challenge) : that.Challenge != null) return false;
        if (Key__Type != null ? !Key__Type.equals(that.Key__Type) : that.Key__Type != null) return false;
        if (Key__Usage != null ? !Key__Usage.equals(that.Key__Usage) : that.Key__Usage != null) return false;
        if (Keysize != null ? !Keysize.equals(that.Keysize) : that.Keysize != null) return false;
        if (Name != null ? !Name.equals(that.Name) : that.Name != null) return false;
        if (Retries != null ? !Retries.equals(that.Retries) : that.Retries != null) return false;
        if (Subject != null ? !Subject.equals(that.Subject) : that.Subject != null) return false;
        if (URL != null ? !URL.equals(that.URL) : that.URL != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Challenge != null ? Challenge.hashCode() : 0;
        result = 31 * result + (Key__Type != null ? Key__Type.hashCode() : 0);
        result = 31 * result + (Key__Usage != null ? Key__Usage.hashCode() : 0);
        result = 31 * result + (Keysize != null ? Keysize.hashCode() : 0);
        result = 31 * result + (Name != null ? Name.hashCode() : 0);
        result = 31 * result + (Retries != null ? Retries.hashCode() : 0);
        result = 31 * result + (Subject != null ? Subject.hashCode() : 0);
        result = 31 * result + (URL != null ? URL.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ScepContent{" +
                "Challenge='" + Challenge + '\'' +
                ", Key__Type='" + Key__Type + '\'' +
                ", Key__Usage=" + Key__Usage +
                ", Keysize=" + Keysize +
                ", Name='" + Name + '\'' +
                ", Retries=" + Retries +
                ", Subject=" + Subject +
                ", URL='" + URL + '\'' +
                '}';
    }
}
