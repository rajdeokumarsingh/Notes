package com.pekall.plist.beans;

/**
 * A dictionary of numeric restrictions.
 */
public class RestrictedValue {
    /**
     * Following fields is in "Passcode Policy Payload",
     * See PayloadPasswordPolicy for details
     */
    private IntegerValue maxFailedAttempts;
    private IntegerValue maxInactivity;
    private IntegerValue maxPINAgeInDays;
    private IntegerValue minComplexChars;
    private IntegerValue minLength;
    private IntegerValue pinHistory;
    private IntegerValue maxGracePeriod;

    public RestrictedValue() {
    }

    public Integer getMaxFailedAttempts() {
        if(maxFailedAttempts == null) return null;

        return maxFailedAttempts.getValue();
    }

    public void setMaxFailedAttempts(Integer maxFailedAttempts) {
        if(this.maxFailedAttempts == null) {
            this.maxFailedAttempts = new IntegerValue();
        }
        this.maxFailedAttempts.setValue(maxFailedAttempts);
    }

    public Integer getMaxInactivity() {
        if(maxInactivity == null) return null;

        return maxInactivity.getValue();
    }

    public void setMaxInactivity(Integer maxInactivity) {
        if(this.maxInactivity == null) {
            this.maxInactivity = new IntegerValue();
        }
        this.maxInactivity.setValue(maxInactivity);
    }

    public Integer getMaxPINAgeInDays() {
        if(maxPINAgeInDays == null) return null;
        return maxPINAgeInDays.getValue();
    }

    public void setMaxPINAgeInDays(Integer maxPINAgeInDays) {
        if(this.maxPINAgeInDays == null) {
            this.maxPINAgeInDays = new IntegerValue();
        }
        this.maxPINAgeInDays.setValue(maxPINAgeInDays);
    }

    public Integer getMinComplexChars() {
        if(minComplexChars == null) return null;

        return minComplexChars.getValue();
    }

    public void setMinComplexChars(Integer minComplexChars) {
        if(this.minComplexChars == null) {
            this.minComplexChars = new IntegerValue();
        }
        this.minComplexChars.setValue(minComplexChars);
    }

    public Integer getMinLength() {
        if(minLength == null) return null;
        return minLength.getValue();
    }

    public void setMinLength(Integer minLength) {
        if(this.minLength == null) {
            this.minLength = new IntegerValue();
        }
        this.minLength.setValue(minLength);
    }

    public Integer getPinHistory() {
        if(pinHistory == null) return null;
        return pinHistory.getValue();
    }

    public void setPinHistory(Integer pinHistory) {
        if(this.pinHistory == null) {
            this.pinHistory = new IntegerValue();
        }
        this.pinHistory.setValue(pinHistory);
    }

    public Integer getMaxGracePeriod() {
        if(maxGracePeriod == null) return null;
        return maxGracePeriod.getValue();
    }

    public void setMaxGracePeriod(Integer maxGracePeriod) {
        if(this.maxGracePeriod == null) {
            this.maxGracePeriod = new IntegerValue();
        }
        this.maxGracePeriod.setValue(maxGracePeriod);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestrictedValue)) return false;

        RestrictedValue value = (RestrictedValue) o;

        if (maxFailedAttempts != null ? !maxFailedAttempts.equals(value.maxFailedAttempts) : value.maxFailedAttempts != null)
            return false;
        if (maxGracePeriod != null ? !maxGracePeriod.equals(value.maxGracePeriod) : value.maxGracePeriod != null)
            return false;
        if (maxInactivity != null ? !maxInactivity.equals(value.maxInactivity) : value.maxInactivity != null)
            return false;
        if (maxPINAgeInDays != null ? !maxPINAgeInDays.equals(value.maxPINAgeInDays) : value.maxPINAgeInDays != null)
            return false;
        if (minComplexChars != null ? !minComplexChars.equals(value.minComplexChars) : value.minComplexChars != null)
            return false;
        if (minLength != null ? !minLength.equals(value.minLength) : value.minLength != null) return false;
        if (pinHistory != null ? !pinHistory.equals(value.pinHistory) : value.pinHistory != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = maxFailedAttempts != null ? maxFailedAttempts.hashCode() : 0;
        result = 31 * result + (maxInactivity != null ? maxInactivity.hashCode() : 0);
        result = 31 * result + (maxPINAgeInDays != null ? maxPINAgeInDays.hashCode() : 0);
        result = 31 * result + (minComplexChars != null ? minComplexChars.hashCode() : 0);
        result = 31 * result + (minLength != null ? minLength.hashCode() : 0);
        result = 31 * result + (pinHistory != null ? pinHistory.hashCode() : 0);
        result = 31 * result + (maxGracePeriod != null ? maxGracePeriod.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RestrictedValue{" +
                "maxFailedAttempts=" + maxFailedAttempts +
                ", maxInactivity=" + maxInactivity +
                ", maxPINAgeInDays=" + maxPINAgeInDays +
                ", minComplexChars=" + minComplexChars +
                ", minLength=" + minLength +
                ", pinHistory=" + pinHistory +
                ", maxGracePeriod=" + maxGracePeriod +
                '}';
    }
}
