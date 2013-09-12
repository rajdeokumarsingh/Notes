package com.android.browser;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ScreenItemAdapter extends BaseAdapter {

    private String[] webSite = null;
    private ArrayList<String> webSiteName = null;
    private ArrayList<String> webSiteUrl = null;
    private Context mContext = null;
    private TextView text = null;
    private ImageView divider = null;
    private LinearLayout textLine = null;
    private BaseUi mUi;
    private UiController mUiController;

    public ScreenItemAdapter(Context context, String[] objects, BaseUi ui, UiController mUiController){
        mContext = context;
        webSite = objects;
        mUi = ui;
        this.mUiController = mUiController;
        spWebSite(objects);
        
    } 

    public void spWebSite(String[] objects){
        webSiteName = new ArrayList<String>();
        webSiteUrl = new ArrayList<String>();
        for(int i=0; i<objects.length; i=i+2){
            webSiteName.add(objects[i]);
        }
        for(int i=1; i<objects.length; i=i+2){
            webSiteUrl.add(objects[i]);
        }
  
    }
    


    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }



    public int getCount() {
        // TODO Auto-generated method stub
        return webSiteName.size();
    }



    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return webSiteUrl.get(position);
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
       
        textLine = (LinearLayout)LayoutInflater.from(mContext).inflate(R.layout.screen_grid_item, null);
        text =  (TextView)textLine.findViewById(R.id.text);
        text.setText(webSiteName.get(position));
        divider = (ImageView)textLine.findViewById(R.id.divider);
        if((position+1)%5==0){
            divider.setVisibility(View.INVISIBLE);
        }
        textLine.setTag(R.id.text, getItem(position).toString());
        textLine.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String url = (String)v.getTag(R.id.text);
                Tab tab = mUiController.openTab(url, false, true, false);
                Controller.homepage = false;
                Controller.homepageBack = false;
                mUi.hideHomePage();
            }
        });
        return textLine;
    }
    
    
}
