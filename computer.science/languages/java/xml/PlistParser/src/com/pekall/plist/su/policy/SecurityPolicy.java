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
 * XML configuration for the security policy
 */
public class SecurityPolicy extends Policy {

    /**
     * Constraint information for device password
     */
    Password password;

    public SecurityPolicy() {
        this("", -1, "", new Password());
    }

    public SecurityPolicy(String name, int status, String description, Password password) {
        super(name, status, description);
        this.password = password;
    }

    @Override
    public String toString() {
        return "SecurityPolicy{" +
                "policy=" + super.toString() +
                "password=" + password +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecurityPolicy)) return false;
        if (!super.equals(o)) return false;

        SecurityPolicy that = (SecurityPolicy) o;

        if (!password.equals(that.password)) return false;

        return super.equals(o);
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
}
