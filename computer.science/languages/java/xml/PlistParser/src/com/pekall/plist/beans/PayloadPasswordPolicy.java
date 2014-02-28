package com.pekall.plist.beans;

/**
 * Bean for payload type "com.apple.mobiledevice.passwordpolicy"
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class PayloadPasswordPolicy extends PayloadBase {
    /**
     * See quality
     */
    public static final String QUALITY_PATTERN = "pattern";
    public static final String QUALITY_NUMERIC = "numeric";
    public static final String QUALITY_ALPHABETIC = "alphabetic";
    public static final String QUALITY_ALPHANUMERIC = "alphanumeric";
    public static final String QUALITY_COMPLEX = "complex";

    /**
     * Optional. Default true. Determines whether a simple passcode is allowed.
     * A simple passcode is defined as containing repeated characters,
     * or increasing/decreasing characters (such as 123 or CBA).
     */
    private Boolean allowSimple;

    /**
     * Optional. Default NO. Determines whether the user is forced to set a PIN.
     * Simply setting this value (and not others) forces the user to enter a passcode,
     * without imposing a length or quality.
     */
    private Boolean forcePIN;


    /**
     * Optional. Default 11. Allowed range [2...11]. Specifies the number of allowed failed
     * attempts to enter the passcode at the device's lock screen. Once this number is exceeded,
     * the device is locked and must be connected to its designated iTunes in order to be unlocked.
     */
    private Integer maxFailedAttempts;

    /**
     * Optional. Default Infinity. Specifies the number of minutes for which the device can
     * be idle (without being unlocked by the user) before it gets locked by the system.
     * Once this limit is reached, the device is locked and the passcode must be entered.
     */
    private Float maxInactivity;

    /**
     * Optional. Default Infinity. Specifies the number of days for which the passcode can
     * remain unchanged. After this number of days, the user is forced to change the passcode
     * before the device is unlocked.
     */
    private Integer maxPINAgeInDays;

    /**
     * Optional. Default 0. Specifies the minimum number of complex characters that a passcode
     * must contain. A "complex" character is a character other than a number or a letter,
     * such as &%$#.
     */
    private Integer minComplexChars;

    /**
     * Optional. Default 0. Specifies the minimum overall length of the passcode.
     * This parameter is independent of the also optional minComplexChars argument.
     */
    private Integer minLength;

    /**
     * Optional. Default NO. Specifies whether the user must enter
     * alphabetic characters ("abcd"), or if numbers are sufficient.
     */
    private Boolean requireAlphanumeric;

    /**
     * Optional. When the user changes the passcode, it has to be unique within the last
     * N entries in the history. Minimum value is 1, maximum value is 50.
     */
    private Integer pinHistory;

    /**
     * Optional. The maximum grace period, in minutes, to unlock the phone without entering
     * a passcode. Default is 0, that is no grace period, which requires a passcode immediately.
     */
    private Integer maxGracePeriod;


    /**
     * Just for android.
     * See QUALITY_...
     */
    private String quality;

    public PayloadPasswordPolicy() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_PASSWORD_POLICY);
    }

    public Boolean getAllowSimple() {
        return allowSimple;
    }

    public void setAllowSimple(Boolean allowSimple) {
        this.allowSimple = allowSimple;
    }

    public Boolean getForcePIN() {
        return forcePIN;
    }

    public void setForcePIN(Boolean forcePIN) {
        this.forcePIN = forcePIN;
    }

    public Integer getMaxFailedAttempts() {
        return maxFailedAttempts;
    }

    public void setMaxFailedAttempts(Integer maxFailedAttempts) {
        this.maxFailedAttempts = maxFailedAttempts;
    }

    public Float getMaxInactivity() {
        return maxInactivity;
    }

    public void setMaxInactivity(Float maxInactivity) {
        this.maxInactivity = maxInactivity;
    }

    public Integer getMaxPINAgeInDays() {
        return maxPINAgeInDays;
    }

    public void setMaxPINAgeInDays(Integer maxPINAgeInDays) {
        this.maxPINAgeInDays = maxPINAgeInDays;
    }

    public Integer getMinComplexChars() {
        return minComplexChars;
    }

    public void setMinComplexChars(Integer minComplexChars) {
        this.minComplexChars = minComplexChars;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Boolean getRequireAlphanumeric() {
        return requireAlphanumeric;
    }

    public void setRequireAlphanumeric(Boolean requireAlphanumeric) {
        this.requireAlphanumeric = requireAlphanumeric;
    }

    public Integer getPinHistory() {
        return pinHistory;
    }

    public void setPinHistory(Integer pinHistory) {
        this.pinHistory = pinHistory;
    }

    public Integer getMaxGracePeriod() {
        return maxGracePeriod;
    }

    public void setMaxGracePeriod(Integer maxGracePeriod) {
        this.maxGracePeriod = maxGracePeriod;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PayloadPasswordPolicy policy = (PayloadPasswordPolicy) o;

        if (allowSimple != null ? !allowSimple.equals(policy.allowSimple) : policy.allowSimple != null) return false;
        if (forcePIN != null ? !forcePIN.equals(policy.forcePIN) : policy.forcePIN != null) return false;
        if (maxFailedAttempts != null ? !maxFailedAttempts.equals(policy.maxFailedAttempts) : policy.maxFailedAttempts != null)
            return false;
        if (maxGracePeriod != null ? !maxGracePeriod.equals(policy.maxGracePeriod) : policy.maxGracePeriod != null)
            return false;
        if (maxInactivity != null ? !maxInactivity.equals(policy.maxInactivity) : policy.maxInactivity != null)
            return false;
        if (maxPINAgeInDays != null ? !maxPINAgeInDays.equals(policy.maxPINAgeInDays) : policy.maxPINAgeInDays != null)
            return false;
        if (minComplexChars != null ? !minComplexChars.equals(policy.minComplexChars) : policy.minComplexChars != null)
            return false;
        if (minLength != null ? !minLength.equals(policy.minLength) : policy.minLength != null) return false;
        if (pinHistory != null ? !pinHistory.equals(policy.pinHistory) : policy.pinHistory != null) return false;
        if (quality != null ? !quality.equals(policy.quality) : policy.quality != null) return false;
        return !(requireAlphanumeric != null ? !requireAlphanumeric.equals(policy.requireAlphanumeric) : policy.requireAlphanumeric != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (allowSimple != null ? allowSimple.hashCode() : 0);
        result = 31 * result + (forcePIN != null ? forcePIN.hashCode() : 0);
        result = 31 * result + (maxFailedAttempts != null ? maxFailedAttempts.hashCode() : 0);
        result = 31 * result + (maxInactivity != null ? maxInactivity.hashCode() : 0);
        result = 31 * result + (maxPINAgeInDays != null ? maxPINAgeInDays.hashCode() : 0);
        result = 31 * result + (minComplexChars != null ? minComplexChars.hashCode() : 0);
        result = 31 * result + (minLength != null ? minLength.hashCode() : 0);
        result = 31 * result + (requireAlphanumeric != null ? requireAlphanumeric.hashCode() : 0);
        result = 31 * result + (pinHistory != null ? pinHistory.hashCode() : 0);
        result = 31 * result + (maxGracePeriod != null ? maxGracePeriod.hashCode() : 0);
        result = 31 * result + (quality != null ? quality.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadPasswordPolicy{" +
                "allowSimple=" + allowSimple +
                ", forcePIN=" + forcePIN +
                ", maxFailedAttempts=" + maxFailedAttempts +
                ", maxInactivity=" + maxInactivity +
                ", maxPINAgeInDays=" + maxPINAgeInDays +
                ", minComplexChars=" + minComplexChars +
                ", minLength=" + minLength +
                ", requireAlphanumeric=" + requireAlphanumeric +
                ", pinHistory=" + pinHistory +
                ", maxGracePeriod=" + maxGracePeriod +
                ", quality='" + quality + '\'' +
                '}';
    }
}
