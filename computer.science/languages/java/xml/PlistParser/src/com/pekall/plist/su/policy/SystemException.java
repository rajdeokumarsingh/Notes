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
 * XML element for system_exception_alert_policy.system_exception
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class SystemException {
    /**
     * 状态， 0:不生效, 1:生效
     */
    private int status;
    /**
     * 需上报异常的类型
     */
    private String type;
    /**
     * 需上报异常的描述
     */
    private String description;
    /**
     * 异常发生时需要触发的事件
     */
    private String eventId;

    public SystemException() {
        this(-1, "", "", "");
    }
    public SystemException(int status, String type,
                           String description, String eventId) {
        this.status = status;
        this.type = type;
        this.description = description;
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "SystemException{" +
                "status=" + status +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", eventId='" + eventId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SystemException)) return false;

        SystemException that = (SystemException) o;

        if (status != that.status) return false;
        if (!description.equals(that.description)) return false;
        if (!eventId.equals(that.eventId)) return false;
        return type.equals(that.type);

    }

    @Override
    public int hashCode() {
        int result = status;
        result = 31 * result + type.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + eventId.hashCode();
        return result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String id) {
        this.eventId = id;
    }
}
