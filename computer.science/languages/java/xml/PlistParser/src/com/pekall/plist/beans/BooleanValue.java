package com.pekall.plist.beans;

/**
 * Bean wrapping a boolean
 */
@SuppressWarnings("UnusedDeclaration")
class BooleanValue {
    private Boolean value;

    public BooleanValue() {
    }

    public BooleanValue(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BooleanValue)) return false;

        BooleanValue that = (BooleanValue) o;

        return !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BooleanValue{" +
                "value=" + value +
                '}';
    }
}
