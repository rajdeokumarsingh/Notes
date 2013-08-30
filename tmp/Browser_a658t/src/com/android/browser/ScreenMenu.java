package com.android.browser;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class ScreenMenu extends PopupWindow {

    private Context mContext;
    private LinearLayout mLayout; 
    private GridView gvBody, gvTitle; 
    private static ColorDrawable mDrawable;
    Drawable bg = null;

    public ScreenMenu(Context context, OnItemClickListener bodyClick, int colorBgTabMenu, SelectItem item){
        super(context);
        mContext = context;
        mLayout = new LinearLayout(context); 
        mLayout.setOrientation(LinearLayout.VERTICAL); 
        mLayout.setLayoutParams(new LinearLayout.LayoutParams(new LayoutParams( 
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)));
        
        mDrawable = new ColorDrawable();
        mDrawable.setColor(Color.BLACK);
        mDrawable.setAlpha(170);
        
        gvBody = new GridView(context); 
        gvBody.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        gvBody.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gvBody.setNumColumns(2); 
        gvBody.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); 

        gvBody.setPadding(15, 9, 15, 9); 
        gvBody.setGravity(Gravity.CENTER); 
        gvBody.setOnItemClickListener(bodyClick); 
 //       gvBody.setBackgroundDrawable(mDrawable);
        
        mLayout.addView(gvBody); 
        this.setContentView(mLayout); 
        this.setWidth(220); 
        this.setHeight(LayoutParams.WRAP_CONTENT); 
        bg = mContext.getResources().getDrawable(R.drawable.screen_body_item_background);
        bg.setAlpha(170);
        this.setBackgroundDrawable(bg);
        this.setFocusable(true); 
        
    }
    
    public void SetBodyAdapter(MenuBodyAdapter bodyAdapter) { 
        gvBody.setAdapter(bodyAdapter); 
    } 
    
    public void SetBodySelect(int index) { 
        int count = gvBody.getChildCount(); 
        for (int i = 0; i < count; i++) { 
            ((LinearLayout) gvBody.getChildAt(i)) 
                        .setBackgroundColor(Color.TRANSPARENT); 
        } 

    } 

    static public class MenuBodyAdapter extends BaseAdapter { 
        private Context mContext; 
        private int fontColor, fontSize; 
        private String[] texts; 
        private int[] resID;
        private SelectItem mItem = null;
 
        /**
         * 设置TabMenu的分页主体
         * 
         * @param context
         *            调用方的上下文
         * @param texts
         *            按钮集合的字符串数组
         * @param resID
         *            按钮集合的图标资源数组
         * @param fontSize
         *            按钮字体大小
         * @param color
         *            按钮字体颜色
         */ 
        public MenuBodyAdapter(Context context, String[] texts, int[] resID, 
                int fontSize, int fontColor) { 
            this.mContext = context; 
            this.fontColor = fontColor; 
            this.texts = texts; 
            this.fontSize = fontSize; 
            this.resID = resID; 
        } 
 
        public int getCount() { 
            return texts.length; 
        } 
 
        public void setSelectItem(SelectItem selectItem){
            mItem = selectItem;
        }
        
        public Object getItem(int position) { 
 
            return makeMenyBody(position); 
        } 
 
        public long getItemId(int position) { 
            return position; 
        } 
 
        private LinearLayout makeMenyBody(int position) { 
            LinearLayout result = new LinearLayout(this.mContext); 
            result.setOrientation(LinearLayout.VERTICAL); 
            result.setGravity(Gravity.CENTER_HORIZONTAL 
                    | Gravity.CENTER_VERTICAL); 
            result.setPadding(5, 5, 5, 5); 
            result.setTag(R.drawable.screen_body_item_background, mItem);
            
            TextView text = new TextView(this.mContext); 
            text.setText(texts[position]); 
            text.setTextSize(fontSize); 
            text.setTextColor(fontColor); 
            text.setGravity(Gravity.CENTER); 
            text.setPadding(2, 2, 2, 2); 
            ImageView img = new ImageView(this.mContext); 
            img.setBackgroundResource(resID[position]); 
            result.addView(img, new LinearLayout.LayoutParams(new LayoutParams( 
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))); 
            result.addView(text); 
            result.setOnTouchListener(new OnTouchListener() {
                
                public boolean onTouch(View v, MotionEvent event) {
                    // TODO Auto-generated method stub
                    
                    switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                         Drawable deepSkyBlue = new ColorDrawable(0x5500BFFF);
                         v.setBackgroundDrawable(deepSkyBlue);
                         break;
 
                    default:
                         v.setBackgroundColor(Color.TRANSPARENT);
                         break;
                    }                 
                    return false;
                }
            });
            return result; 
        } 
 
        public View getView(int position, View convertView, ViewGroup parent) { 
            return makeMenyBody(position); 
        } 
    } 
}
