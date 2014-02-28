
package com.pekall.mobileutil;

/**
 * The result for a transaction
 *
 * @author jiangrui
 */
public class TransResult {

    /**
     * A unique id for a transaction
     */
    private final long mTransId;

    private final int mResult;

    private static long mCurrentTransId = 0;

    public static long generateNextId() {
        return mCurrentTransId++;
    }

    public long getTransId() {
        return mTransId;
    }

    public int getResult() {
        return mResult;
    }

    public TransResult(long id, int result) {
        mTransId = id;
        mResult = result;
    }
}
