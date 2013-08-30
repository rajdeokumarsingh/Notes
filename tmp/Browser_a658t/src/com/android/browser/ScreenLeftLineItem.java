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
    private LinearLayout screenItemImage;
    private LinearLayout screen_item_div;
    private ImageView screenItemImageList;
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
        screenItemImage = (LinearLayout)left_item.findViewById(R.id.screen_item_image);
        screenItemImageList = (ImageView)left_item.findViewById(R.id.screen_item_image_list);
        screenItemText = (TextView)left_item.findViewById(R.id.screen_item_text);
        screenItemText.setText(text);
        screenItemLine = left_item.findViewById(R.id.screen_item_line);
        screenItemLine.setOnClickListener(this);
        screenItemLine.setOnTouchListener(new OnTouchListener() {
            
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                float x = 0 ;
                float y = 0 ;
                switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = event.getX();
                    y = event.getY();
                    screenItemLine.setBackgroundColor(0xff1E90FF);
                    break;

                case MotionEvent.ACTION_MOVE:
                    if((event.getX() < x-10)||(event.getX() > x+10)||(event.getY() < y-10)||(event.getY() > y+10))
                        screenItemLine.setBackgroundDrawable(null);
                    break; 
                case MotionEvent.ACTION_UP:
                    screenItemLine.setBackgroundDrawable(null);
                    break;     
                default:
                    break;
                }
                return false;
            }
        });
        
        screen_item_div = (LinearLayout)left_item.findViewById(R.id.screen_item_div);
 
        screenItemGrid = (GridView)left_item.findViewById(R.id.screen_item_grid);
        screenItemGrid.setAdapter(adapter);
        screenItemGrid.setSelector(new ColorDrawable(0x5500BFFF));
        screenItemGrid.setFocusable(false);
      
        
        this.addView(left_item);
        this.setFocusable(true);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v.getId()==R.id.screen_item_line){
            screenItemLine.setBackgroundDrawable(null);
            if(!expand){
                screenItemImage.setBackgroundColor(0xff1E90FF);
                screenItemImageList.setImageResource(R.drawable.screen_item_image_list_pressed);
                screen_item_div.setVisibility(View.VISIBLE);
                screen_item_div.requestFocus();
        
                expand = true;
            } else {
                screenItemImage.setBackgroundColor(0xff878787);
                screenItemImageList.setImageResource(R.drawable.screen_item_image_list_normal);
                screen_item_div.setVisibility(View.GONE);
                expand = false;
            }
            
            
        }
        
    }

  
    
}
