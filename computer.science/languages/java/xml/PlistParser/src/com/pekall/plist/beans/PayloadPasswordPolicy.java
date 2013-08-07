package com.pekall.plist.beans;

/**
 * Bean for payload type "com.apple.mobiledevice.passwordpolicy"
 */
public class PayloadPasswordPolicy extends PayloadBase {
    private boolean allowSimple;
    private boolean forcePIN;
    private int maxFailedAttempts;
    private int maxGracePeriod;
    private int maxInactivity;
    private int maxPINAgeInDays;
    private int minComplexChars;
    private int minLength;
    private int pinHistory;
    private boolean requireAlphanumeric;

    public PayloadPasswordPolicy() {
        super();
    }

    public PayloadPasswordPolicy(boolean allowSimple, boolean forcePIN, int maxFailedAttempts,
                                 int maxGracePeriod, int maxInactivity, int maxPINAgeInDays,
                                 int minComplexChars, int minLength, int pinHistory, boolean requireAlphanumeric) {
        this.allowSimple = allowSimple;
        this.forcePIN = forcePIN;
        this.maxFailedAttempts = maxFailedAttempts;
        this.maxGracePeriod = maxGracePeriod;
        this.maxInactivity = maxInactivity;
        this.maxPINAgeInDays = maxPINAgeInDays;
        this.minComplexChars = minComplexChars;
        this.minLength = minLength;
        this.pinHistory = pinHistory;
        this.requireAlphanumeric = requireAlphanumeric;
    }

    public boolean isAllowSimple() {
        return allowSimple;
    }

    public void setAllowSimple(boolean allowSimple) {
        this.allowSimple = allowSimple;
    }

    public boolean isForcePIN() {
        return forcePIN;
    }

    public void setForcePIN(boolean forcePIN) {
        this.forcePIN = forcePIN;
    }

    public int getMaxFailedAttempts() {
        return maxFailedAttempts;
    }

    public void setMaxFailedAttempts(int maxFailedAttempts) {
        this.maxFailedAttempts = maxFailedAttempts;
    }

    public int getMaxGracePeriod() {
        return maxGracePeriod;
    }

    public void setMaxGracePeriod(int maxGracePeriod) {
        this.maxGracePeriod = maxGracePeriod;
    }

    public int getMaxInactivity() {
        return maxInactivity;
    }

    public void setMaxInactivity(int maxInactivity) {
        this.maxInactivity = maxInactivity;
    }

    public int getMaxPINAgeInDays() {
        return maxPINAgeInDays;
    }

    public void setMaxPINAgeInDays(int maxPINAgeInDays) {
        this.maxPINAgeInDays = maxPINAgeInDays;
    }

    public int getMinComplexChars() {
        return minComplexChars;
    }

    public void setMinComplexChars(int minComplexChars) {
        this.minComplexChars = minComplexChars;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getPinHistory() {
        return pinHistory;
    }

    public void setPinHistory(int pinHistory) {
        this.pinHistory = pinHistory;
    }

    public boolean isRequireAlphanumeric() {
        return requireAlphanumeric;
    }

    public void setRequireAlphanumeric(boolean requireAlphanumeric) {
        this.requireAlphanumeric = requireAlphanumeric;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PayloadPasswordPolicy that = (PayloadPasswordPolicy) o;

        if (allowSimple != that.allowSimple) return false;
        if (forcePIN != that.forcePIN) return false;
        if (maxFailedAttempts != that.maxFailedAttempts) return false;
        if (maxGracePeriod != that.maxGracePeriod) return false;
        if (maxInactivity != that.maxInactivity) return false;
        if (maxPINAgeInDays != that.maxPINAgeInDays) return false;
        if (minComplexChars != that.minComplexChars) return false;
        if (minLength != that.minLength) return false;
        if (pinHistory != that.pinHistory) return false;
        if (requireAlphanumeric != that.requireAlphanumeric) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (allowSimple ? 1 : 0);
        result = 31 * result + (forcePIN ? 1 : 0);
        result = 31 * result + maxFailedAttempts;
        result = 31 * result + maxGracePeriod;
        result = 31 * result + maxInactivity;
        result = 31 * result + maxPINAgeInDays;
        result = 31 * result + minComplexChars;
        result = 31 * result + minLength;
        result = 31 * result + pinHistory;
        result = 31 * result + (requireAlphanumeric ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadPasswordPolicy{" +
                "allowSimple=" + allowSimple +
                ", forcePIN=" + forcePIN +
                ", maxFailedAttempts=" + maxFailedAttempts +
                ", maxGracePeriod=" + maxGracePeriod +
                ", maxInactivity=" + maxInactivity +
                ", maxPINAgeInDays=" + maxPINAgeInDays +
                ", minComplexChars=" + minComplexChars +
                ", minLength=" + minLength +
                ", pinHistory=" + pinHistory +
                ", requireAlphanumeric=" + requireAlphanumeric +
                '}';
    }
}
