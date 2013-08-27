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
 * XML configuration for memory size policy
 */
public class MemorySizePolicy extends Policy {
    /**
     * Detailed memory information
     */
    Memory memory;

    public MemorySizePolicy() {
        this("", -1, "", new Memory());
    }

    public MemorySizePolicy(String name, int status, String description, Memory memory) {
        super(name, status, description);
        /*
        this.name = name;
        this.status = status;
        this.description = description;
        */
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "MemorySizePolicy{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", memory=" + memory.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemorySizePolicy)) return false;

        MemorySizePolicy that = (MemorySizePolicy) o;

        if (status != that.status) return false;
        if (!description.equals(that.description)) return false;
        if (!memory.equals(that.memory)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }
}
