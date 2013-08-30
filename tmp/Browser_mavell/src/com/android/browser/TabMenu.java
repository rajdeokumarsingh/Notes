package com.android.browser;

import java.util.ArrayList;
import java.util.zip.Inflater;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.DisplayMetrics;
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
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
 
public class TabMenu extends PopupWindow {
    private GridView gvBody1, gvBody2, gvBody3; 
    private LinearLayout mLayout; 

    private static ColorDrawable mDrawable;
    private Activity mContext;
    private LayoutInflater inflater;
    private ViewPagerMenu vPaper = null;
    private ArrayList<View> pages = null;
    private ScreenPagerAdapter screenPagerAdapter = null;
    private LinearLayout viewPaper = null;
  
    private TextView title1 = null;
    private TextView title2 = null;
    private TextView title3 = null;
    private LinearLayout line1 = null;
    private LinearLayout line2 = null;
    private LinearLayout line3 = null;
    private int padding = 0; 
   
    public TabMenu(Activity activity, OnItemClickListener bodyClick1, 
            OnItemClickListener bodyClick2, OnItemClickListener bodyClick3, MenuBodyAdapter bodyAdapter1, MenuBodyAdapter bodyAdapter2, MenuBodyAdapter bodyAdapter3,
            int aniTabMenu) { 
        super(activity); 
        mContext = activity;
        this.setSoftInputMode(INPUT_METHOD_NOT_NEEDED);
        this.setInputMethodMode(INPUT_METHOD_NOT_NEEDED);
        padding = mContext.getResources().getInteger(R.integer.padding_title);
        inflater = LayoutInflater.from(activity);
        viewPaper = (LinearLayout)inflater.inflate(R.layout.main_menu_page, null);
        vPaper = (ViewPagerMenu)viewPaper.findViewById(R.id.vPager);
        mLayout = (LinearLayout)viewPaper.findViewById(R.id.title);
 
        title1 = (TextView)viewPaper.findViewById(R.id.title1);
        title2 = (TextView)viewPaper.findViewById(R.id.title2);
        title3 = (TextView)viewPaper.findViewById(R.id.title3);
        line1 = (LinearLayout)viewPaper.findViewById(R.id.line1);
        line2 = (LinearLayout)viewPaper.findViewById(R.id.line2);
        line3 = (LinearLayout)viewPaper.findViewById(R.id.line3);
        title1.setPadding(padding, padding, padding, padding);
        title2.setPadding(padding, padding, padding, padding);
        title3.setPadding(padding, padding, padding, padding);
        title1.setTextColor(getColor(R.color.system_blue));
        line1.setBackgroundColor(getColor(R.color.system_blue));
        title2.setTextColor(getColor(R.color.menu_title));
        line2.setBackgroundColor(getColor(R.color.divider_color));
        title3.setTextColor(getColor(R.color.menu_title));
        line3.setBackgroundColor(getColor(R.color.divider_color));
        
        setTitleClick(title1);
        setTitleClick(title2);
        setTitleClick(title3);
        
        gvBody1 = new ScreenGridView(activity); 
        gvBody1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 

        gvBody1.setNumColumns(4); 
        gvBody1.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); 
        gvBody1.setPadding(10, 10, 10, 10); 
        gvBody1.setOnItemClickListener(bodyClick1); 
        gvBody1.setAdapter(bodyAdapter1);
        gvBody1.setFocusable(true);
        gvBody1.requestFocus();
        
        gvBody2 = new ScreenGridView(activity); 
        gvBody2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        gvBody2.setNumColumns(4); 
        gvBody2.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); 
        gvBody2.setPadding(10, 10, 10, 10); 
        gvBody2.setOnItemClickListener(bodyClick2); 
        gvBody2.setAdapter(bodyAdapter2);
        
        gvBody3 = new ScreenGridView(activity); 
        gvBody3.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        gvBody3.setNumColumns(4); 
        gvBody3.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); 
        gvBody3.setPadding(10, 10, 10, 10); 
        gvBody3.setOnItemClickListener(bodyClick3); 
        gvBody3.setAdapter(bodyAdapter3);

        viewPaper.setFocusableInTouchMode(true);
        viewPaper.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if(keyCode == KeyEvent.KEYCODE_MENU && event.getAction() == KeyEvent.ACTION_UP){
                    TabMenu.this.dismiss();
                }
                return false;
            }
        });
        
        pages = new ArrayList<View>();
        pages.add(0, gvBody1);
        pages.add(1, gvBody2);
        pages.add(2, gvBody3);
        
        screenPagerAdapter = new ScreenPagerAdapter(mContext, pages);
        vPaper.setAdapter(screenPagerAdapter);
        vPaper.setCurrentItem(0);
        vPaper.setOnPageChangeListener(new MyOnPageChangeListener());
        
        this.setContentView(viewPaper); 
        this.setWidth(LayoutParams.FILL_PARENT); 
        this.setHeight(LayoutParams.WRAP_CONTENT); 
   //     this.setBackgroundDrawable(new ColorDrawable(getColor(R.color.homepage_color)));
        this.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.menu_dropdown_panel_holo_light));
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
 
    private int getColor(int id){
        return mContext.getResources().getColor(id);
    }
    private void setTitleClick(View view){
    	view.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.title1:
					vPaper.setCurrentItem(0);
					break;
				case R.id.title2:
					vPaper.setCurrentItem(1);
					break;
				case R.id.title3:
					vPaper.setCurrentItem(2);
					break;	

				default:
					break;
				}
			}
		});
    }
    class ScreenPagerAdapter extends com.android.browser.PagerAdapterMenu{
        
        private ArrayList<View> views;
        public ScreenPagerAdapter(Context context, ArrayList<View> pages){
            views = pages;
        }
        
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
           
        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPagerMenu)container).removeView(views.get(position));
        }
    
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPagerMenu)container).addView(views.get(position));
            return views.get(position);
        }
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
                    R.string.tabmenu_save_page};
            
            snapShot = new int[]{R.string.tabmenu_add_bookmark, 
                    R.string.tabmenu_refresh,
                    R.string.tabmenu_switch,
                    R.string.tabmenu_save_page,
                    };
        } 
 
        public int getCount() { 
        	if(texts != null){
        		return texts.length;
        	}
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
        	Tab currentTab = ((BrowserActivity)mContext).getController().getCurrentTab();
        	if(currentTab == null){
        		return false;
        	}
            boolean isSnap = currentTab.isSnapshot();
            if(isSnap){
                for(int i=0; i<snapShot.length; i++){
                    if(texts[position] == snapShot[i]){
                        return false;
                    }
                }
            }
            if(texts[position] == R.string.tabmenu_test){
            	return false;
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
            if(texts[position] == R.string.tabmenu_night){
                boolean night = mSettings.getSharedPreferences().getBoolean(PreferenceKeys.PREF_NIGHTMODE, false);
                if(night){
                    text.setText(getStringFormId(R.string.tabmenu_datey)); 
                    img.setBackgroundResource(R.drawable.menu_daymode);
                }
            }
            if(texts[position] == R.string.tabmenu_change_page){
                int flip = mSettings.getSharedPreferences().getInt(PreferenceKeys.PREF_PAGE_FLIP, 0);
                if(flip!=0){
                    img.setBackgroundResource(R.drawable.menu_scrollbutton_close);
                }
            }
            if(texts[position] == R.string.tabmenu_test){
            	text.setText("");
            	img.setBackgroundColor(Color.WHITE);
            	img.setMinimumHeight(60);
            }
            checkHomepageState(position, text, img);
            checkSnapShotState(position, text, img);
          
            result.addView(img, new LinearLayout.LayoutParams(new LayoutParams( 
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))); 
            result.addView(text);
            return result; 
        } 
 
        private void checkSnapShotState(int position, TextView text, ImageView img){
        	Tab currentTab = ((BrowserActivity)mContext).getController().getCurrentTab();
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
                case R.string.tabmenu_switch:
                    img.setBackgroundDrawable(changDrawableA(R.drawable.menu_turn_url_dark));
                    break;
                case R.string.tabmenu_save_page:
                    img.setBackgroundDrawable(changDrawableA(R.drawable.menu_save_webpage_dark));
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
                        int flip = mSettings.getSharedPreferences().getInt(PreferenceKeys.PREF_PAGE_FLIP, 0);
                        if(flip!=0){
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
                    default:
                        break;
            }
           }
        }
        public Drawable changDrawableA(int id){
            Drawable d = mContext.getResources().getDrawable(id);
           // d.setAlpha(100);
            return d;
        }
        
        public String getStringFormId(int id){
            return mContext.getString(id);
        }
        public View getView(int position, View convertView, ViewGroup parent) { 
            return makeMenyBody(position); 
        } 
    } 
     
    @Override
    public void dismiss() {
    	// TODO Auto-generated method stub
    	super.dismiss();
    	setTitleColor(0);
    	vPaper.setCurrentItem(0);
    }

    private void setTitleColor(int i){
    	switch (i) {
		case 0:
			title1.setTextColor(getColor(R.color.system_blue));
			title2.setTextColor(getColor(R.color.menu_title));
			title3.setTextColor(getColor(R.color.menu_title));
			line1.setBackgroundColor(getColor(R.color.system_blue));
			line2.setBackgroundColor(getColor(R.color.divider_color));
			line3.setBackgroundColor(getColor(R.color.divider_color));
			break;
		case 1:
			title2.setTextColor(getColor(R.color.system_blue));
			title1.setTextColor(getColor(R.color.menu_title));
			title3.setTextColor(getColor(R.color.menu_title));
			line2.setBackgroundColor(getColor(R.color.system_blue));
			line1.setBackgroundColor(getColor(R.color.divider_color));
			line3.setBackgroundColor(getColor(R.color.divider_color));			
			break;

		default:
			title3.setTextColor(getColor(R.color.system_blue));
			title2.setTextColor(getColor(R.color.menu_title));
			title1.setTextColor(getColor(R.color.menu_title));
			line3.setBackgroundColor(getColor(R.color.system_blue));
			line1.setBackgroundColor(getColor(R.color.divider_color));
			line2.setBackgroundColor(getColor(R.color.divider_color));			
			break;
		}
    }
    
    public class MyOnPageChangeListener implements com.android.browser.ViewPagerMenu.OnPageChangeListener {
                
       @Override
       public void onPageSelected(int arg0) {
            setTitleColor(arg0);
       }
       
       @Override
       public void onPageScrolled(int arg0, float arg1, int arg2) {
           
       }
       
        @Override
       public void onPageScrollStateChanged(int arg0) {
            
       }
    }
} 
 
