/*---------------------------------------------------------------------------------------------
 *                       Copyright (c) 2013 Capital Alliance Software(Pekall) 
 *                                    All Rights Reserved
 *    NOTICE: All information contained herein is, and remains the property of Pekall and
 *      its suppliers,if any. The intellectual and technical concepts contained herein are
 *      proprietary to Pekall and its suppliers and may be covered by P.R.C, U.S. and Foreign
 *      Patents, patents in process, and are protected by trade secret or copyright law.
 *      Dissemination of this information or reproduction of this material is strictly 
 *      forbidden unless prior written permission is obtained from Pekall.
 *                                     www.pekall.com
 *--------------------------------------------------------------------------------------------- 
*/

package com.pekall.plist.su.policy;

/**
 * XML element for security_policy.password
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class Password {
    /**
     * Whether the password is optional, 0 for mandatory, 1 for optional
     */
    private int optional;

    /**
     * Password type, 0 for numeric, 1 for alphanumeric
     */
    private int type;

    /**
     * Minimum length of password
     */
    private int min_len;

    /**
     * Maximum screen lock time, in unit of minute
     */
    private int max_screen_lock_time;

    /**
     * Whether password should include special characters
     */
    private boolean include_special_chars = false;

    /**
     * Minimum length of complex characters in password
     */
    private int min_complex_len;

    /**
     * Expire age of password, in unit of day
     */
    private int expire_age;

    /**
     * Length of history list
     */
    private int history_len;

    /**
     * Information for failed attempt
     */
    private FailedAttempt failed_attempt;

    /**
     * Information for grace time
     */
    private GraceTime grace_time;

    public Password() {
        this(-1, -1, -1, -1, -1, -1, -1, new FailedAttempt(), new GraceTime());
    }

    private Password(int optional, int type, int min_len, int max_screen_lock_time,
                     int min_complex_len, int expire_age, int history_len,
                     FailedAttempt failed_attempt, GraceTime time) {
        this.optional = optional;
        this.type = type;
        this.min_len = min_len;
        this.max_screen_lock_time = max_screen_lock_time;
        this.min_complex_len = min_complex_len;
        this.expire_age = expire_age;
        this.history_len = history_len;
        this.failed_attempt = failed_attempt;
        this.grace_time = time;
    }

    @Override
    public String toString() {
        return "Password{" +
                "optional=" + optional +
                ", type=" + type +
                ", min_len=" + min_len +
                ", max_screen_lock_time=" + max_screen_lock_time +
                ", include_special_chars=" + include_special_chars +
                ", min_complex_len=" + min_complex_len +
                ", expire_age=" + expire_age +
                ", history_len=" + history_len +
                ", failed_attempt=" + failed_attempt +
                ", failed_attempt=" + grace_time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Password)) return false;

        Password password = (Password) o;

        if (expire_age != password.expire_age) return false;
        if (history_len != password.history_len) return false;
        if (max_screen_lock_time != password.max_screen_lock_time) return false;
        if (min_complex_len != password.min_complex_len) return false;
        if (include_special_chars != password.include_special_chars) return false;
        if (min_len != password.min_len) return false;
        if (optional != password.optional) return false;
        if (type != password.type) return false;
        if (!failed_attempt.equals(password.failed_attempt)) return false;
        return grace_time.equals(password.grace_time);

    }

    public int getOptional() {
        return optional;
    }

    public void setOptional(int optional) {
        this.optional = optional;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMinLen() {
        return min_len;
    }

    public void setMinLen(int min_len) {
        this.min_len = min_len;
    }

    public int getMaxScreenLockTime() {
        return max_screen_lock_time;
    }

    public void setMaxScreenLockTime(int max_screen_lock_time) {
        this.max_screen_lock_time = max_screen_lock_time;
    }

    public int getMinComplexLen() {
        return min_complex_len;
    }

    public void setMinComplexLen(int min_complex_len) {
        this.min_complex_len = min_complex_len;
    }

    public int getExpireAge() {
        return expire_age;
    }

    public void setExpireAge(int expire_age) {
        this.expire_age = expire_age;
    }

    public int getHistoryLen() {
        return history_len;
    }

    public void setHistoryLen(int history_len) {
        this.history_len = history_len;
    }

    public FailedAttempt getFailedAttempt() {
        return failed_attempt;
    }

    public void setFailedAttempt(FailedAttempt failed_attempt) {
        this.failed_attempt = failed_attempt;
    }

    public boolean isIncludeSpecialChars() {
        return include_special_chars;
    }

    public void setIncludeSpecialChars(boolean include_special_chars) {
        this.include_special_chars = include_special_chars;
    }

    public GraceTime getGraceTime() {
        return grace_time;
    }

    public void setGraceTime(GraceTime grace_time) {
        this.grace_time = grace_time;
    }
}
