package com.android.browser;

import java.sql.Driver;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class ScreenLeftLineItem extends LinearLayout implements View.OnClickListener{

    private Context mContext;
    private LayoutInflater inflater;
    private LinearLayout left_item;
    private ImageView screenItemImage;
    private LinearLayout screen_item_div;
    private TextView screenItemText;
    private GridView screenItemGrid;
    private View screenItemLine;
    private boolean expand = false;
    private ListAdapter mAdapter;
 
    
    public ScreenLeftLineItem(Context context, int text, ListAdapter adapter) {
        super(context);
        mContext = context;
        mAdapter = adapter;
        inflater = LayoutInflater.from(mContext);
        left_item = (LinearLayout)inflater.inflate(R.layout.home_screen_left_item, null);
        screenItemImage = (ImageView)left_item.findViewById(R.id.expandImage);
        screenItemImage.setImageResource(R.drawable.collapsed);
        screenItemText = (TextView)left_item.findViewById(R.id.screen_item_text);
        screenItemText.setText(text);
        screenItemLine = left_item.findViewById(R.id.screen_item_line);
        screenItemLine.setOnClickListener(this);
        screen_item_div = (LinearLayout)left_item.findViewById(R.id.screen_item_div);
        screenItemGrid = (GridView)left_item.findViewById(R.id.screen_item_grid);
        screenItemGrid.setAdapter(adapter);
      
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.addView(left_item, params);
        expandHotTitle(context, text);
    }

    public void expandHotTitle(Context context, int text){
        String title = context.getResources().getString(text);
        String hotTitle = context.getResources().getString(R.string.hot_website);
        if(title!=null && hotTitle!=null && title.equals(hotTitle)){
            expand = true;
            screenItemImage.setImageResource(R.drawable.expanded);
            screen_item_div.setVisibility(View.VISIBLE);
        }
    }
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v.getId()==R.id.screen_item_line){
            if(!expand){
                screenItemImage.setImageResource(R.drawable.expanded);
                screen_item_div.setVisibility(View.VISIBLE);
                expand = true;
            } else {
                screenItemImage.setImageResource(R.drawable.collapsed);
                screen_item_div.setVisibility(View.GONE);
                expand = false;
            }
        }
    }
}
