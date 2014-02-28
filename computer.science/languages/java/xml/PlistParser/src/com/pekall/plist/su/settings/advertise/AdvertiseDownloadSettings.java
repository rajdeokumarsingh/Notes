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

import com.pekall.plist.beans.PayloadBase;

/**
 * XML configuration for advertise download
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class AdvertiseDownloadSettings extends PayloadBase {
    /**
     * See playType
     */
    public static final String PLAY_TYPE_AUTO = "auto";
    public static final String PLAY_TYPE_MANUAL = "manual";

    /**
     * 下载文件的版本
     */
    private String version;
    /**
     * 下载文件的url地址
     */
    private String downloadUrl;

    /**
     * 播放类型， 手动/自动
     * See PLAY_TYPE_MANUAL, PLAY_TYPE_AUTO
     */
    private String playType;

    public AdvertiseDownloadSettings() {
        setPayloadType(PAYLOAD_TYPE_ADVT_SETTINGS);
    }

    public AdvertiseDownloadSettings(String version, String downloadUrl, String playType) {
        this.version = version;
        this.downloadUrl = downloadUrl;
        this.playType = playType;

        setPayloadType(PAYLOAD_TYPE_ADVT_SETTINGS);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String url) {
        this.downloadUrl = url;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdvertiseDownloadSettings)) return false;
        if (!super.equals(o)) return false;

        AdvertiseDownloadSettings that = (AdvertiseDownloadSettings) o;

        if (downloadUrl != null ? !downloadUrl.equals(that.downloadUrl) : that.downloadUrl != null) return false;
        if (playType != null ? !playType.equals(that.playType) : that.playType != null) return false;
        return !(version != null ? !version.equals(that.version) : that.version != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (downloadUrl != null ? downloadUrl.hashCode() : 0);
        result = 31 * result + (playType != null ? playType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdvertiseDownloadSettings{" +
                "version='" + version + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", playType='" + playType + '\'' +
                '}';
    }
}
