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


import com.pekall.plist.beans.PayloadBase;

/**
 * Base class for policy XML configuration
 */
public class Policy extends PayloadBase {
    /**
     * Policy name
     */
    String name;

    /**
     * Policy status, 0 for inactivate, 1 for active
     */
    int status;

    /**
     * Whether is is a default policy
     */
    boolean defaultPolicy = false;

    /**
     * Policy description
     */
    String description;

    public Policy() {
        this("", -1, "");
    }

    public Policy(String name, int status, String description) {
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDefault() {
        return defaultPolicy;
    }

    public void setDefault(boolean mDefault) {
        this.defaultPolicy = mDefault;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Policy)) return false;
        if (!super.equals(o)) return false;

        Policy policy = (Policy) o;

        if (defaultPolicy != policy.defaultPolicy) return false;
        if (status != policy.status) return false;
        if (description != null ? !description.equals(policy.description) : policy.description != null) return false;
        if (name != null ? !name.equals(policy.name) : policy.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + (defaultPolicy ? 1 : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "super=" + super.toString() + "," +
                "name='" + name + '\'' +
                ", status=" + status +
                ", defaultPolicy=" + defaultPolicy +
                ", description='" + description + '\'' +
                '}';
    }
}
