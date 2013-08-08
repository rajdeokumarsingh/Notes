package com.pekall.plist.beans;

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

    /**
     * Id in provider
     */
    private String mId = "";

    /**
     * Rule URL
     */
    private String mUrl = "";

    /**
     * Match type
     */
    private int mMatchType = MATCH_TYPE_CONTAIN;

    public static boolean isValidType(int type) {
        if (type < MATCH_TYPE_BEGIN || type > MATCH_TYPE_END) {
            return false;
        }
        return true;
    }

    /**
     * Constructor
     *
     * @param id   in the provider
     * @param url  to match web address
     * @param type of rule
     */
    public UrlMatchRule(String id, String url, int type) {
        if (id == null || url == null || !isValidType(type)) {
            throw new IllegalArgumentException();
        }

        this.mId = id;
        this.mUrl = url;
        this.mMatchType = type;
    }

    /**
     * Constructor
     *
     * @param url  to match web address
     * @param type of rule
     */
    public UrlMatchRule(String url, int type) {
        this("", url, type);
    }

    /**
     * Constructor
     *
     * @param url to match web address
     */
    public UrlMatchRule(String url) {
        this("", url, MATCH_TYPE_CONTAIN);
    }

    public UrlMatchRule() {
        this("", "", MATCH_TYPE_CONTAIN);
    }

    public boolean matchRule(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }

        switch (mMatchType) {
            case MATCH_TYPE_CONTAIN:
                if (url.contains(mUrl)) {
                    return true;
                } else {
                    return false;
                }
            case MATCH_TYPE_PREFIX:
                if (url.startsWith(mUrl)) {
                    return true;
                } else {
                    return false;
                }
            case MATCH_TYPE_SUFFIX:
                if (url.endsWith(mUrl)) {
                    return true;
                } else {
                    return false;
                }
            case MATCH_TYPE_EQUAL:
                if (url.equals(mUrl)) {
                    return true;
                } else {
                    return false;
                }
        }
        return false;
    }

    /**
     * Get the db id
     *
     * @return db id
     */
    public String getId() {
        return mId;
    }

    /**
     * Set the db id
     *
     * @param id
     */
    public void setId(String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        this.mId = id;
    }

    /**
     * Get the url rule
     *
     * @return the url rule
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Set the url rule
     *
     * @param url the url rule
     */
    public void setUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException();
        }
        this.mUrl = url;
    }

    /**
     * Get the match type of this rule
     *
     * @return the match type of this rule
     */
    public int getMatchType() {
        return mMatchType;
    }

    /**
     * Set the match type of this rule
     *
     * @param matchType
     */
    public void setMatchType(int matchType) {
        if (isValidType(matchType)) {
            throw new IllegalArgumentException();
        }
        this.mMatchType = matchType;
    }

    @Override
    public String toString() {
        return "UrlMatchRule{" +
                "mId='" + mId + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", mMatchType=" + mMatchType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlMatchRule)) return false;

        UrlMatchRule rule = (UrlMatchRule) o;

        if (mMatchType != rule.mMatchType) return false;
        if (!mId.equals(rule.mId)) return false;
        if (!mUrl.equals(rule.mUrl)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + mUrl.hashCode();
        result = 31 * result + mMatchType;
        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new UrlMatchRule(mId, mUrl, mMatchType);
    }
}
