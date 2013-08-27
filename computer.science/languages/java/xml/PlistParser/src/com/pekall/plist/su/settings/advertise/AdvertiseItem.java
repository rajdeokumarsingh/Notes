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

public class AdvertiseItem {
    private String id;
    private String name;
    private String path;
    private int isIndex;

    public AdvertiseItem(){
    }

    public AdvertiseItem(String id, String name, String path, int isIndex){
        this.name = name;
        this.id = id;
        this.path = path;
        this.isIndex = isIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public int getIndex() {
        return isIndex;
    }

    public void setIndex(int index) {
        isIndex = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdvertiseItem)) return false;

        AdvertiseItem item = (AdvertiseItem) o;

        if (isIndex != item.isIndex) return false;
        if (!id.equals(item.id)) return false;
        if (!name.equals(item.name)) return false;
        if (!path.equals(item.path)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + isIndex;
        result = 31 * result + name.hashCode();
        result = 31 * result + path.hashCode();
        return result;
    }
}
