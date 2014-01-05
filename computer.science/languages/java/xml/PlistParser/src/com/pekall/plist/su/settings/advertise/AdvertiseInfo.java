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


import com.pekall.plist.beans.CommandStatusMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * XML configuration for "advertisement_statistic"
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class AdvertiseInfo extends CommandStatusMsg {
    /**
     * List contains advertisement information
     * Any element named item should go into this list
     */
    private List<AdvertiseStaInfo> advertiseStaInfos;

    public AdvertiseInfo() {
    }

    public AdvertiseInfo(String status, String UDID, String commandUUID) {
        super(status, UDID, commandUUID);
    }

    public List<AdvertiseStaInfo> getAdvertiseStaInfos() {
        return advertiseStaInfos;
    }

    public void setAdvertiseStaInfos(List<AdvertiseStaInfo> advertiseStaInfos) {
        this.advertiseStaInfos = advertiseStaInfos;
    }

    public void addItem(AdvertiseStaInfo info) {
        if (advertiseStaInfos == null) {
            advertiseStaInfos = new ArrayList<AdvertiseStaInfo>();
        }
        advertiseStaInfos.add(info);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdvertiseInfo)) return false;
        if (!super.equals(o)) return false;

        return this.hashCode() == o.hashCode();

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        if(advertiseStaInfos != null) {
            for (AdvertiseStaInfo advertiseStaInfo : advertiseStaInfos) {
                result += advertiseStaInfo.hashCode();
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "AdvertiseInfo{" +
                "advertiseStaInfos=" + advertiseStaInfos +
                '}';
    }
}
