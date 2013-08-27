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

package com.pekall.plist.su.settings.advertise;

/**
 * XML element for "advertisement_statistic.item"
 */
public class AdvertiseStaInfo {
    /**
     * 广告id
     */
    private String id;
    /**
     * 广告播放次数
     */
    private Integer count;
    /**
     * 广告累计播放时长, 单位：秒
     */
    private Long totalDuration;

    public AdvertiseStaInfo() {
    }

    public AdvertiseStaInfo(String id, Integer count, Long totalDuration) {
        this.id = id;
        this.count = count;
        this.totalDuration = totalDuration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Long total_duration) {
        this.totalDuration = total_duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdvertiseStaInfo)) return false;

        AdvertiseStaInfo that = (AdvertiseStaInfo) o;

        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (totalDuration != null ? !totalDuration.equals(that.totalDuration) : that.totalDuration != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (totalDuration != null ? totalDuration.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdvertiseStaInfo{" +
                "id='" + id + '\'' +
                ", count=" + count +
                ", totalDuration=" + totalDuration +
                '}';
    }
}
