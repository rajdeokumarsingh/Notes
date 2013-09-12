package com.android.browser;

import java.io.Serializable;

public class SelectItem implements Serializable {
    
    public long id;
    public String title;
    public String url;
    public SelectItem(long id, String title, String url){
        this.id = id;
        this.title = title;
        this.url = url;
    }
}
