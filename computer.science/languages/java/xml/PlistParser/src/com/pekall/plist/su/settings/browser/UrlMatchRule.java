package com.pekall.plist.su.settings.browser;

/**
 * The rule to match given web URLs
 */
public class UrlMatchRule {

    private static final int MATCH_TYPE_BEGIN = 0;

    /**
     * If given web URL contains the rule URL,
     * it will match the white list rule.
     */
    public static final int MATCH_TYPE_CONTAIN = MATCH_TYPE_BEGIN;

    /**
     * If the prefix of web URL equals the rule URL,
     * it will match the white list rule.
     */
    public static final int MATCH_TYPE_PREFIX = 1;

     /**
     * If the suffix of web URL equals the rule URL,
     * it will match the white list rule.
     */
    public static final int MATCH_TYPE_SUFFIX = 2;

    /**
     * If the web URL equals the rule URL,
     * it will match the white list rule.
     */
    public static final int MATCH_TYPE_EQUAL = 3;

    private static final int MATCH_TYPE_END = MATCH_TYPE_EQUAL;

    /** Id in provider */
    private String id;

    /** Rule URL*/
    private String url;

    /** Match type */
    private int matchType;

    public static boolean isValidType(int type) {
        if (type < MATCH_TYPE_BEGIN || type > MATCH_TYPE_END) {
            return false;
        }
        return true;
    }

    /**
     * Constructor
     * @param id in the provider
     * @param url to match web address
     * @param type of rule
     */
    public UrlMatchRule(String id, String url, int type) {
        if (!isValidType(type)) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.url = url;
        this.matchType = type;
    }

    /**
     * Constructor
     * @param url to match web address
     * @param type of rule
     */
    public UrlMatchRule(String url, int type) {
        this(null, url, type);
    }

    /**
     * Constructor
     * @param url to match web address
     */
    public UrlMatchRule(String url) {
        this(null, url, MATCH_TYPE_CONTAIN);
    }

    public UrlMatchRule() {
        this(null, null, MATCH_TYPE_CONTAIN);
    }

     public boolean matchRule(String url) {
         if (url == null || "".equals(url)) {
             return false;
         }

         switch (matchType) {
            case MATCH_TYPE_CONTAIN:
                if (url.contains(this.url)) {
                    return true;
                } else {
                    return false;
                }
            case MATCH_TYPE_PREFIX:
                if (url.startsWith(this.url)) {
                    return true;
                } else {
                    return false;
                }
            case MATCH_TYPE_SUFFIX:
                if (url.endsWith(this.url)) {
                    return true;
                } else {
                    return false;
                }
            case MATCH_TYPE_EQUAL:
                if (url.equals(this.url)) {
                    return true;
                } else {
                    return false;
                }
        }
        return false;
    }

    /**
     * Get the db id
     * @return db id
     */
    public String getId() {
        return id;
    }

    /**
     * Set the db id
     * @param id
     */
    public void setId(String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    /**
     * Get the url rule
     * @return the url rule
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the url rule
     * @param url the url rule
     */
    public void setUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException();
        }
        this.url = url;
    }

    /**
     * Get the match type of this rule
     * @return the match type of this rule
     */
    public int getMatchType() {
        return matchType;
    }

    /**
     * Set the match type of this rule
     * @param matchType
     */
    public void setMatchType(int matchType) {
        if (!isValidType(matchType)) {
            throw new IllegalArgumentException();
        }
        this.matchType = matchType;
    }

    @Override
    public String toString() {
        return "UrlMatchRule{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", matchType=" + matchType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlMatchRule)) return false;

        UrlMatchRule that = (UrlMatchRule) o;

        if (matchType != that.matchType) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + matchType;
        return result;
    }
}
