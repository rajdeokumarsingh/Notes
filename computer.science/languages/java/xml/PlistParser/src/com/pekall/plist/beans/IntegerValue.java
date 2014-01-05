package com.pekall.plist.beans;

/**
 * Bean wrapping a integer
 */
@SuppressWarnings("UnusedDeclaration")
class IntegerValue {
    private Integer value;

    public IntegerValue() {
    }

    public IntegerValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerValue)) return false;

        IntegerValue that = (IntegerValue) o;

        return !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "IntegerValue{" +
                "value=" + value +
                '}';
    }
}
