
package com.pekall.mobileutil;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Main service for handling transactions
 */
public class Transaction {
    @SuppressWarnings("UnusedDeclaration")
    private static final boolean ENABLE_LOG = true;
    @SuppressWarnings("UnusedDeclaration")
    private static final String LOGTAG = "TransactionService";

    public static final Queue<TransInfo> mTransQueue = new LinkedList<TransInfo>();

    /* TODO: check whether there are same trans info in the queue
    private static boolean hasSameTransInfo(TransInfo trans) {
        return false;
    }
    */

    public static void enqueueTransInfo(TransInfo trans) {
        if (trans == null) {
            throw new IllegalArgumentException();
        }

        synchronized (mTransQueue) {
            // TODO: check whether there are same trans info in the queue
            // if (hasSameTransInfo(trans)) return;

            mTransQueue.offer(trans);
            mTransQueue.notifyAll();
        }
    }
}
