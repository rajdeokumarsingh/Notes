package com.android.browser;

import java.util.zip.Inflater;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TabMenu extends PopupWindow {
    private GridView gvBody, gvTitle; 
    private LinearLayout mLayout; 
    private MenuTitleAdapter titleAdapter; 
    private static ColorDrawable mDrawable;
    private Context mContext;
    public TabMenu(Context context, OnItemClickListener titleClick, 
            OnItemClickListener bodyClick, MenuTitleAdapter titleAdapter, 
            int colorBgTabMenu, int aniTabMenu) { 
        super(context); 
        mContext = context;
        this.setSoftInputMode(INPUT_METHOD_NOT_NEEDED);
        this.setInputMethodMode(INPUT_METHOD_NOT_NEEDED);
        mLayout = new LinearLayout(context); 
        mLayout.setOrientation(LinearLayout.VERTICAL); 
        
        gvTitle = new GridView(context); 
        gvTitle.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 
                LayoutParams.WRAP_CONTENT)); 
        gvTitle.setNumColumns(titleAdapter.getCount()); 
        gvTitle.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); 
        gvTitle.setVerticalSpacing(1); 
        gvTitle.setGravity(Gravity.CENTER); 
        gvTitle.setOnItemClickListener(titleClick); 
        gvTitle.setAdapter(titleAdapter); 
        gvTitle.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mDrawable = new ColorDrawable();
        mDrawable.setColor(Color.BLACK);
        mDrawable.setAlpha(170);
        this.titleAdapter = titleAdapter; 
       
        gvBody = new GridView(context); 
        gvBody.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 
                LayoutParams.WRAP_CONTENT)); 
        gvBody.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gvBody.setNumColumns(4); 
    
        gvBody.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); 
        gvBody.setSelector(new ColorDrawable(0x5500BFFF));
        gvBody.setPadding(10, 10, 10, 10); 
        gvBody.setGravity(Gravity.CENTER); 
        gvBody.setOnItemClickListener(bodyClick); 
        gvBody.setBackgroundDrawable(mDrawable);
        gvBody.setFocusable(true);
        gvBody.requestFocus();
        mLayout.addView(gvTitle); 
        mLayout.addView(gvBody); 
        mLayout.setFocusableInTouchMode(true);
        mLayout.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if(keyCode == KeyEvent.KEYCODE_MENU && event.getAction() == KeyEvent.ACTION_UP){
                    TabMenu.this.dismiss();
                }
                return false;
            }
        });
        
        this.setContentView(mLayout); 
        this.setWidth(LayoutParams.FILL_PARENT); 
        this.setHeight(LayoutParams.WRAP_CONTENT); 
        this.setBackgroundDrawable(new ColorDrawable(colorBgTabMenu));
        this.setAnimationStyle(aniTabMenu); 
        this.setFocusable(true); 
        this.setTouchInterceptor(new View.OnTouchListener() {
            
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
                    TabMenu.this.dismiss();
                }
                return false;
            }
        });
    } 
 
    public void SetTitleSelect(int index) { 
        gvTitle.setSelection(index); 
        this.titleAdapter.SetFocus(index); 
    } 
  
    public void SetBodySelect(int index) { 
        int count = gvBody.getChildCount(); 
        for (int i = 0; i < count; i++) { 
            ((LinearLayout) gvBody.getChildAt(i)) 
                        .setBackgroundColor(Color.TRANSPARENT); 
        } 

    } 

 
    public void SetBodyAdapter(MenuBodyAdapter bodyAdapter) { 
        gvBody.setAdapter(bodyAdapter); 
    } 
 
    /**
     * 自定义Adapter，TabMenu的每个分页的主体
     * 
     */ 
    static public class MenuBodyAdapter extends BaseAdapter { 
        private Activity mContext; 
        private int fontColor, fontSize; 
        private int[] texts; 
        private int[] resID; 
        private int[] homepage;
        private int[] snapShot;
        private BrowserSettings mSettings;
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
        public MenuBodyAdapter(Activity context, int[] texts, int[] resID, 
                int fontSize, int fontColor) { 
            this.mContext = context; 
            this.fontColor = fontColor; 
            this.texts = texts; 
            this.fontSize = fontSize; 
            this.resID = resID; 
            mSettings = BrowserSettings.getInstance();
            homepage = new int[]{R.string.tabmenu_add_bookmark, 
                    R.string.tabmenu_refresh,
                    R.string.tabmenu_share, 
                    R.string.tabmenu_change_page,
                    R.string.tabmenu_switch,
                    R.string.tabmenu_save_page,
                    R.string.tabmenu_night};
            
            snapShot = new int[]{R.string.tabmenu_add_bookmark, 
                    R.string.tabmenu_refresh,
                    // R.string.tabmenu_switch,
                    R.string.tabmenu_save_page,
                    R.string.tabmenu_night
                    };
        } 
 
        public int getCount() { 
            return texts.length; 
        } 
 
        public Object getItem(int position) { 
 
            return makeMenyBody(position); 
        } 
 
        public long getItemId(int position) { 
            return position; 
        } 
 
        @Override
        public boolean isEnabled(int position) {
            // TODO Auto-generated method stub
            if(Controller.homepage == true||Controller.homepageBack == true){
                for(int i=0; i<homepage.length; i++){
                    if(texts[position] == homepage[i]){
                        return false;
                    }
                }
            }
            Tab current = ((BrowserActivity)mContext).getController().getCurrentTab();
            boolean isSnap = false;
            if(current!=null){
            	isSnap = current.isSnapshot();
            }
            if(isSnap){
                for(int i=0; i<snapShot.length; i++){
                    if(texts[position] == snapShot[i]){
                        return false;
                    }
                }
            }
            return super.isEnabled(position);
        }
        
        private LinearLayout makeMenyBody(int position) { 
            LinearLayout result = new LinearLayout(this.mContext); 
            result.setOrientation(LinearLayout.VERTICAL); 
            result.setGravity(Gravity.CENTER_HORIZONTAL 
                    | Gravity.CENTER_VERTICAL); 
          // result.setPadding(10, 10, 10, 10); 
            

            TextView text = new TextView(this.mContext); 
            text.setText(getStringFormId(texts[position])); 
            text.setTextSize(fontSize); 
            text.setTextColor(fontColor); 
            text.setGravity(Gravity.CENTER); 
           // text.setPadding(5, 5, 5, 5); 
            ImageView img = new ImageView(this.mContext); 
            img.setBackgroundResource(resID[position]); 

            if(texts[position] == R.string.tabmenu_full_screen){
                boolean fullScreen = mSettings.getSharedPreferences().getBoolean(PreferenceKeys.PREF_FULLSCREEN, Controller.fullMode);
                if(fullScreen){
                    text.setText(getStringFormId(R.string.tabmenu_stop_full_screen)); 
                    img.setBackgroundResource(R.drawable.menu_exitfullscreen);
                }
            }

            if(texts[position] == R.string.tabmenu_change_page){
                boolean flip = mSettings.getSharedPreferences().getBoolean(PreferenceKeys.PREF_PAGE_FLIP, false);
                if(flip){
                    img.setBackgroundResource(R.drawable.menu_scrollbutton_close);
                }
            }
            
            checkHomepageState(position, text, img);
            checkSnapShotState(position, text, img);
          
            result.addView(img, new LinearLayout.LayoutParams(new LayoutParams( 
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))); 
            result.addView(text);
            return result; 
        } 
 
        private void checkSnapShotState(int position, TextView text, ImageView img){
            Tab currentTab = null;
            if(((BrowserActivity)mContext).getController()!=null){
                currentTab = ((BrowserActivity)mContext).getController().getCurrentTab();
            }
        	if(currentTab == null){
        		return;
        	}
            boolean isSnap = currentTab.isSnapshot();
            if(isSnap){
                for(int i=0; i<snapShot.length; i++){
                    if(texts[position] == snapShot[i]){
                        text.setTextColor(0x55BEBEBE);
                    }
                }
                
                switch (texts[position]) {
                case R.string.tabmenu_add_bookmark:
                    img.setBackgroundDrawable(changDrawableA(R.drawable.menu_fav_add_un));
                    break;
                case R.string.tabmenu_refresh:
                    img.setBackgroundDrawable(changDrawableA(R.drawable.menu_refresh_un));
                    break;
                /* case R.string.tabmenu_switch:
                    img.setBackgroundDrawable(changDrawableA(R.drawable.menu_turn_url_dark));
                    break;
                 */
                case R.string.tabmenu_save_page:
                    img.setBackgroundDrawable(changDrawableA(R.drawable.menu_save_webpage_dark));
                    break;
                case R.string.tabmenu_night:
                    img.setBackgroundDrawable(changDrawableA(R.drawable.tabmenu_find_un));
                    break;
                default:
                    break;
            }
           }
        }
        
        private void checkHomepageState(int position, TextView text, ImageView img){
            if(Controller.homepage == true||Controller.homepageBack == true){
                for(int i=0; i<homepage.length; i++){
                    if(texts[position] == homepage[i]){
                        text.setTextColor(0x55BEBEBE);
                    }
                }
               switch (texts[position]) {
                    case R.string.tabmenu_add_bookmark:
                        img.setBackgroundDrawable(changDrawableA(R.drawable.menu_fav_add_un));
                        break;
                    case R.string.tabmenu_refresh:
                        img.setBackgroundDrawable(changDrawableA(R.drawable.menu_refresh_un));
                        break;
                    case R.string.tabmenu_share:
                        img.setBackgroundDrawable(changDrawableA(R.drawable.menu_screenshot_share_un));
                        break;
                    case R.string.tabmenu_change_page:
                        boolean flip = mSettings.getSharedPreferences().getBoolean(PreferenceKeys.PREF_PAGE_FLIP, false);
                        if(flip){
                            img.setBackgroundDrawable(changDrawableA(R.drawable.menu_scrollbutton_close_un));
                        }else{
                             img.setBackgroundDrawable(changDrawableA(R.drawable.menu_scrollbutton_un));
                        }
                        break;
                    case R.string.tabmenu_switch:
                        img.setBackgroundDrawable(changDrawableA(R.drawable.menu_turn_url_dark));
                        break;
                    case R.string.tabmenu_save_page:
                        img.setBackgroundDrawable(changDrawableA(R.drawable.menu_save_webpage_dark));
                        break;
                    case R.string.tabmenu_night:
                        img.setBackgroundDrawable(changDrawableA(R.drawable.tabmenu_find_un));
                        break; 
                    default:
                        break;
            }
           }
        }
        public Drawable changDrawableA(int id){
            Drawable d = mContext.getResources().getDrawable(id);
            d.setAlpha(100);
            return d;
        }
        
        public String getStringFormId(int id){
            return mContext.getString(id);
        }
        public View getView(int position, View convertView, ViewGroup parent) { 
            return makeMenyBody(position); 
        } 
    } 
 
    /**
     * 自定义Adapter,TabMenu的分页标签部分
     * 
     */ 
    static public class MenuTitleAdapter extends BaseAdapter { 
        private Context mContext; 
        private int fontColor, unselcolor, selcolor; 
        private TextView[] title; 
        private LayoutInflater mInflater;
        /**
         * 设置TabMenu的title
         * 
         * @param context
         *            调用方的上下文
         * @param titles
         *            分页标签的字符串数组
         * @param fontSize
         *            字体大小
         * @param fontcolor
         *            字体颜色
         * @param unselcolor
         *            未选中项的背景色
         * @param selcolor
         *            选中项的背景色
         */ 
        public MenuTitleAdapter(Context context, String[] titles, int fontSize, 
                int fontcolor, int unselcolor, int selcolor) { 
            this.mContext = context; 
            this.fontColor = fontcolor; 
            this.unselcolor = unselcolor; 
            this.selcolor = selcolor; 
            this.title = new TextView[titles.length]; 
            for (int i = 0; i < titles.length; i++) { 
                title[i] = new TextView(mContext); 
                title[i].setText(titles[i]); 
                title[i].setTextSize(fontSize); 
                title[i].setTextColor(fontColor); 
                title[i].setGravity(Gravity.CENTER); 
                title[i].setPadding(2, 2, 2, 2); 
            } 
            mInflater = LayoutInflater.from(context);
        } 
 
        public int getCount() { 
            return title.length; 
        } 
 
        public Object getItem(int position) { 
            return title[position]; 
        } 
 
        public long getItemId(int position) { 
            return title[position].getId(); 
        } 
 
        /**
         * 设置选中的效果
         */ 
        private void SetFocus(int index) { 
            for (int i = 0; i < title.length; i++) { 
                if (i != index) { 
                    title[i].setBackgroundDrawable(new ColorDrawable(0XFF515151));
                    title[i].setTextColor(fontColor);
                } 
            } 
            title[index].setBackgroundDrawable(mDrawable); 
            title[index].setTextColor(selcolor);
        } 
 
        public View getView(int position, View convertView, ViewGroup parent) { 
            View v; 
            if (convertView == null) { 
                v = title[position]; 
            } else { 
                v = convertView; 
            } 
            return v; 
        } 
    } 
} 
 
