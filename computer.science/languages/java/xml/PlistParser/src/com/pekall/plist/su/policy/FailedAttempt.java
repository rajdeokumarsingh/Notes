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
 * XML element for security_policy.password.failed_attempt
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class FailedAttempt {
    /**
     * Maximum retry number of password
     */
    private int max_retry_num;

    /**
     * Event name for punishment
     */
    private String event_id;

    public FailedAttempt() {
        this(-1, "");
    }

    private FailedAttempt(int max_retry_num, String event_id) {
        this.max_retry_num = max_retry_num;
        this.event_id = event_id;
    }

    @Override
    public String toString() {
        return "FailedAttempt{" +
                "max_retry_num=" + max_retry_num +
                ", eventId='" + event_id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FailedAttempt)) return false;

        FailedAttempt that = (FailedAttempt) o;

        if (max_retry_num != that.max_retry_num) return false;
        return event_id.equals(that.event_id);

    }

    public int getMaxRetryNum() {
        return max_retry_num;
    }

    public void setMaxRetryNum(int max_retry_num) {
        this.max_retry_num = max_retry_num;
    }

    public String getEventId() {
        return event_id;
    }

    public void setEventId(String id) {
        this.event_id = id;
    }
}
