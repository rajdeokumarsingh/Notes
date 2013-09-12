package com.java.examples.xml.plist.browser;

/**
 * Test class for Plist parser
 */
public class InternalData {
    boolean testBoolean = true;
    int testInt = 123;
    long testLong = 234;
    double testDouble = 456.0;

    public boolean isTestBoolean() {
        return testBoolean;
    }

    public void setTestBoolean(boolean testBoolean) {
        this.testBoolean = testBoolean;
    }

    public int getTestInt() {
        return testInt;
    }

    public void setTestInt(int testInt) {
        this.testInt = testInt;
    }

    public long getTestLong() {
        return testLong;
    }

    public void setTestLong(long testLong) {
        this.testLong = testLong;
    }

    public double getTestDouble() {
        return testDouble;
    }

    public void setTestDouble(double testDouble) {
        this.testDouble = testDouble;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InternalData)) return false;

        InternalData that = (InternalData) o;

        if (testBoolean != that.testBoolean) return false;
        if (Double.compare(that.testDouble, testDouble) != 0) return false;
        if (testInt != that.testInt) return false;
        if (testLong != that.testLong) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (testBoolean ? 1 : 0);
        result = 31 * result + testInt;
        result = 31 * result + (int) (testLong ^ (testLong >>> 32));
        temp = Double.doubleToLongBits(testDouble);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "InternalData{" +
                "testBoolean=" + testBoolean +
                ", testInt=" + testInt +
                ", testLong=" + testLong +
                ", testDouble=" + testDouble +
                '}';
    }
}
