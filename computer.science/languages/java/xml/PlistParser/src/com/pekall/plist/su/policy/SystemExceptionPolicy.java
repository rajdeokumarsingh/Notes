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


import java.util.ArrayList;
import java.util.List;

/**
 * XML configuration for system_exception_alert_policy
 */
@SuppressWarnings("UnusedDeclaration")
public class SystemExceptionPolicy extends Policy {

    private List<SystemException> exceptions;

    public SystemExceptionPolicy() {
        super();
        exceptions = new ArrayList<SystemException>();
        setPayloadType(PAYLOAD_TYPE_SYSTEM_EXCEPTION_POLICY);
    }
    public SystemExceptionPolicy(String name, int status, String description,
                                 List<SystemException> exceptions) {
        super(name, status, description);
        this.exceptions = exceptions;

        setPayloadType(PAYLOAD_TYPE_SYSTEM_EXCEPTION_POLICY);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (SystemException e : exceptions) {
            sb.append(e.toString());
        }
        sb.append("}");
        return "SystemExceptionPolicy{" +
                "policy=" + super.toString() +
                "exceptions=" + sb.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SystemExceptionPolicy)) return false;
        if (!super.equals(o)) return false;

        SystemExceptionPolicy that = (SystemExceptionPolicy) o;

        // if (!exceptions.equals(that.exceptions)) return false;
        return this.hashCode() == that.hashCode();

    }

    @Override
    public int hashCode() {
        int ret = 0;
        for (SystemException se : exceptions) {
            ret += se.hashCode();
        }
        return ret;
    }

    public List<SystemException> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<SystemException> exceptions) {
        this.exceptions = exceptions;
    }

    public void addException(SystemException se) {
        exceptions.add(se);
    }
}
