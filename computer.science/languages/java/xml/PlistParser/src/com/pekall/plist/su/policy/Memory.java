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
 * XML element in memory_size_policy
 */
public class Memory {
    /**
     * Detailed memory information, see {@link MemoryLimit}
     */
    MemoryLimit memory_limit;

    /**
     * Detailed disk information, see {@link DiskLimit}
     */
    DiskLimit disk_limit;

    public Memory() {
        this(new MemoryLimit(), new DiskLimit());
    }

    public Memory(MemoryLimit memory_limit, DiskLimit disk_limit) {
        this.memory_limit = memory_limit;
        this.disk_limit = disk_limit;
    }

    @Override
    public String toString() {
        return "Memory{" +
                "memory_limit=" + memory_limit.toString() +
                "disk_limit=" + disk_limit.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Memory)) return false;

        Memory memory = (Memory) o;

        if (!memory_limit.equals(memory.memory_limit)) return false;
        if (!disk_limit.equals(memory.disk_limit)) return false;

        return true;
    }

    public MemoryLimit getMemoryLimit() {
        return memory_limit;
    }

    public void setMemoryLimit(MemoryLimit memory_limit) {
        this.memory_limit = memory_limit;
    }

    public DiskLimit getDiskLimit() {
        return disk_limit;
    }

    public void setDiskLimit(DiskLimit disk_limit) {
        this.disk_limit = disk_limit;
    }
}
