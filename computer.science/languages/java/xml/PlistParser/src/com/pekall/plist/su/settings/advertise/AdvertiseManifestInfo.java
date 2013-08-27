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


import java.util.ArrayList;
import java.util.List;
public class AdvertiseManifestInfo {

     List<AdvertiseItem> items;

    public AdvertiseManifestInfo(){
        this(new ArrayList<AdvertiseItem>());
    }

    public AdvertiseManifestInfo(List<AdvertiseItem>items){
        this.setItems(items);
    }


    public List<AdvertiseItem> getItems() {
        return items;
    }

    public void setItems(List<AdvertiseItem> items) {
        this.items = items;
    }

    public void addItems(AdvertiseItem item){
        items.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdvertiseManifestInfo)) return false;

        AdvertiseManifestInfo that = (AdvertiseManifestInfo) o;

        if (!items.equals(that.items)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
