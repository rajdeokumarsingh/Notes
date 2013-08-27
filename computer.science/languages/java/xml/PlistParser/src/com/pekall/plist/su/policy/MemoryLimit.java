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
 * XML element for "memory_size_policy.memory.memory_limit"
 */
public class MemoryLimit {
    /**
     * Maximum usage ratio of handset memory
     */
    int max_ratio;

    /**
     * Event name for punishment
     */
    String event_id;

    public MemoryLimit() {
        this(-1, "");
    }

    public MemoryLimit(int max_ratio, String event_id) {
        this.max_ratio = max_ratio;
        this.event_id = event_id;
    }

    @Override
    public String toString() {
        return "MemoryLimit{" +
                "max_ratio=" + max_ratio +
                ", event_id='" + event_id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemoryLimit)) return false;

        MemoryLimit that = (MemoryLimit) o;

        if (max_ratio != that.max_ratio) return false;
        if (!event_id.equals(that.event_id)) return false;

        return true;
    }

    public int getMaxRatio() {
        return max_ratio;
    }

    public void setMaxRatio(int max_ratio) {
        this.max_ratio = max_ratio;
    }

    public String getEventId() {
        return event_id;
    }

    public void setEventId(String id) {
        this.event_id = id;
    }
}
