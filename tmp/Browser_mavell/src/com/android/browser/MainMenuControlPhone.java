/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.browser;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.android.browser.UI.ComboViews;



/**
 * controller for Quick Controls pie menu
 */
public class MainMenuControlPhone extends MainMenuControlBase {

    private BaseUi mUi;
    protected UiController mUiController;
    private MainMenuItem homepage;
    private MainMenuItem bookmark;
    private MainMenuItem backward;
    private MainMenuItem forward;
    private MainMenuItem toolbar_menu;
    private PopupMenu mPopup;
    private Activity mActivity;
    protected MainMenu mPie;
    private BrowserSettings mSettings;
    private TabControl mTabControl;
    public static boolean night = false;
	private BlockModeSelectedListener mModeListener;
	private RotaryModeSelectedListener mRotaryListener;
	private UaModeSelectedListener mUaListener;
	private PicModeSelectedListener mPicListerner;
	private CustomDialog dialog;

    TabMenu.MenuBodyAdapter[] bodyAdapter = new TabMenu.MenuBodyAdapter[3]; 
    TabMenu tabMenu; 
    int selTitle = 0; 
    int NavLeft = 0;
    int NavTop = 0;
    int NavRight = 0;
    int NavBottom = 0;
    public MainMenuControlPhone(Activity activity , BaseUi ui, UiController uiController, TabControl mTabControl) {
        super(activity);
        mActivity = activity;
        this.mUi = ui;
        this.mUiController = uiController;
        this.mTabControl = mTabControl;
        NavLeft = mActivity.getResources().getInteger(R.integer.left_toolbar_menu);
        NavTop = mActivity.getResources().getInteger(R.integer.top_toolbar_menu);
        NavRight = mActivity.getResources().getInteger(R.integer.right_toolbar_menu);
        NavBottom = mActivity.getResources().getInteger(R.integer.bottom_toolbar_menu);
        mSettings = BrowserSettings.getInstance();
        mModeListener = new BlockModeSelectedListener();
        mRotaryListener = new RotaryModeSelectedListener();
        mUaListener = new UaModeSelectedListener();
        mPicListerner = new PicModeSelectedListener();
        if(true){

            bodyAdapter[0] = new TabMenu.MenuBodyAdapter(mActivity, new int[] { 
                    R.string.tabmenu_bookmark, 
                    R.string.tabmenu_add_bookmark,
                    R.string.tabmenu_refresh,
                    R.string.tabmenu_history,
                    R.string.tabmenu_switch,
                    R.string.tabmenu_homepage,
                    R.string.tabmenu_download,
                    R.string.tabmenu_exit}, new int[] { R.drawable.menu_fav, R.drawable.menu_fav_add,
                    R.drawable.menu_refresh, R.drawable.menu_history, R.drawable.menu_turn_url, R.drawable.menu_homepage, R.drawable.menu_download, R.drawable.menu_exit }, 13, getColor(R.color.black)); 
     
            bodyAdapter[1] = new TabMenu.MenuBodyAdapter(mActivity, new int[] { 
                    R.string.tabmenu_add_tab,
                    R.string.tabmenu_save_page,
                    R.string.tabmenu_full_screen,
                    R.string.tabmenu_share, 
                    R.string.tabmenu_night, 
                    R.string.tabmenu_change_screen,
                    R.string.tabmenu_change_page,
                    R.string.page_no_picture}, new int[] { R.drawable.menu_add_tab, R.drawable.menu_save_webpage, R.drawable.menu_fullscreen, R.drawable.menu_screenshot_share, R.drawable.menu_nightmode,
                    R.drawable.menu_rotate_screen, R.drawable.menu_scrollbutton, R.drawable.menu_no_image_view}, 13, 
                    getColor(R.color.black)); 
            bodyAdapter[2] = new TabMenu.MenuBodyAdapter(mActivity, new int[] { 
                    R.string.tabmenu_settings,
                    R.string.browser_userAgent,
                    R.string.tabmenu_test,
                    R.string.tabmenu_test,
                    R.string.tabmenu_test,
                }, new int[] { R.drawable.menu_setting, R.drawable.menu_mode, R.drawable.menu_homepage, R.drawable.menu_homepage, R.drawable.menu_homepage}, 13, 
                getColor(R.color.black)); 


        }
    }
     
    private int getColor(int id){
        return mActivity.getResources().getColor(id);
    }
    
    class BodyClickEvent implements OnItemClickListener { 
         
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, 
                long arg3) { 
 
                switch (arg2) {
                case 0:
                    mUiController.bookmarksOrHistoryPicker(ComboViews.Bookmarks);
                    break;
                case 1:
                    Intent bookmarkIntent = mUiController.createBookmarkCurrentPageIntent(false);
                    if (bookmarkIntent != null) {
                        mActivity.startActivity(bookmarkIntent);
                    }
                   
                    break;
                case 2:
                    mUiController.getCurrentTopWebView().reload();
           
                    break;
                case 3:
                    mUiController.bookmarksOrHistoryPicker(ComboViews.History);
                    break;
                case 4:
                    mUi.Gotourl();
                    break;
                case 5:
                    mUiController.getCurrentTopWebView().reload();
                    Tab current = mUiController.getCurrentTab();
                    mUiController.loadUrl(current, mSettings.getHomePage());
                    break;
                case 6:
                    Intent data = new Intent();
                    data.setAction("android.intent.action.VIEW_DOWNLOADS");
                    mActivity.startActivity(data);
             
                    break;
                case 7:
                    mSettings.getSharedPreferences().edit().putString("country", "").commit();
      
                    mActivity.finish();
                    break;
                default:
                    break;
                }
            hideMenu();  
        } 
     }
   
    class BodyClickEvent2 implements OnItemClickListener { 
        
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, 
                long arg3) { 
            switch (arg2) {
            case 0:
                mUiController.openTabToHomePage();
                break;
                
            case 1:
                 mUiController.saveSnapShots();
                 break;
                
            case 2:
                Controller.fullMode = !Controller.fullMode;
                //?
                mSettings.getSharedPreferences().edit().putBoolean(PreferenceKeys.PREF_FULLSCREEN, Controller.fullMode).commit();
                 if (mUiController.getUi() != null) {
                     mUiController.getUi().setFullscreen(Controller.fullMode);
                 }
                break;
                
            case 3:
                Tab currentTab = mTabControl.getCurrentTab();
                if (null == currentTab) {
               
                    return;
                }
                mUiController.shareCurrentPage();
         
                break;
                
            case 4:
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
             
                if(night){
                    lp.screenBrightness = mUiController.getScreenBrightness();
                    night = false;
                    mSettings.getSharedPreferences().edit().putBoolean(PreferenceKeys.PREF_NIGHTMODE, night).commit();
                }else {
                    lp.screenBrightness = 0.05f;
                    night = true;
                    mSettings.getSharedPreferences().edit().putBoolean(PreferenceKeys.PREF_NIGHTMODE, night).commit();
                }
                mActivity.getWindow().setAttributes(lp);
                break;
            case 5:
            	int checkedItemRotary = mSettings.getSharedPreferences().getInt(PreferenceKeys.PREF_ROTARY, 0);
        		dialog = new CustomDialog(mActivity, R.style.Dialog);
        		dialog.setTitle(R.string.rotary_screen);
        		dialog.setDatas(R.array.pref_Rotary_screen);
        		dialog.config(checkedItemRotary, mRotaryListener);
        		dialog.show();
                break;
            case 6:
            	int checkedItem = mSettings.getSharedPreferences().getInt(PreferenceKeys.PREF_PAGE_FLIP, 0);
        		dialog = new CustomDialog(mActivity, R.style.Dialog);
        		dialog.setTitle(R.string.flip_screen);
        		dialog.setDatas(R.array.pref_flip_screen);
        		dialog.config(checkedItem, mModeListener);
        		dialog.show();
                break;
            case 7:
            	int checkedItemForPic = mSettings.getSharedPreferences().getInt(PreferenceKeys.PREF_PAGE_PICTURE, 1);
        		dialog = new CustomDialog(mActivity, R.style.Dialog);
        		dialog.setTitle(R.string.page_picture);
        		dialog.setDatas(R.array.pref_page_picture);
        		dialog.config(checkedItemForPic, mPicListerner);
        		dialog.show();
                break;
            default:
                break;
            }
            hideMenu();  
        } 
     }

    class BodyClickEvent3 implements OnItemClickListener { 
        
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, 
                long arg3) { 
            switch (arg2) {
            case 0:
                Intent intent = new Intent(mActivity, BrowserPreferencesPage.class);
                intent.putExtra(BrowserPreferencesPage.CURRENT_PAGE,
                        mUiController.getCurrentTopWebView().getUrl());
                mActivity.startActivityForResult(intent, 3);
                break;
                
            case 1:
            	String checkedItem = mSettings.getSharedPreferences().getString(PreferenceKeys.PREF_USER_AGENT, "0");
        		dialog = new CustomDialog(mActivity, R.style.Dialog);
        		dialog.setTitle(R.string.browser_userAgent);
        		dialog.setDatas(R.array.pref_browser_userAgent);
        		dialog.config(Integer.parseInt(checkedItem), mUaListener);
        		dialog.show();
                break;

            default:
                break;
            }
            hideMenu();
        }    
    }
    private class BlockModeSelectedListener implements CustomDialog.onModChangeListener{
    	public void onModChange(int which, boolean isSave) {
    		// TODO Auto-generated method stub
    		dialog.dismiss();
    		dialog = null;
			if(isSave){
                mSettings.getSharedPreferences().edit().putInt(PreferenceKeys.PREF_PAGE_FLIP, which).commit();
                switch (which) {
                case 0:
                     mUiController.hidePageFlip();
                     mSettings.getSharedPreferences().edit().putBoolean(PreferenceKeys.PREF_VOICE_FLIP, false).commit();
                     mUiController.setVoiceFlip(false);
                     break;
                case 1:
                     mSettings.getSharedPreferences().edit().putBoolean(PreferenceKeys.PREF_VOICE_FLIP, false).commit();
                     mUiController.setVoiceFlip(false);
                     mUiController.addPageFlip();
                     break;
                case 2:
                     mSettings.getSharedPreferences().edit().putBoolean(PreferenceKeys.PREF_VOICE_FLIP, false).commit();
                     mUiController.setVoiceFlip(false);
                     mUiController.addPageFlip();
                     break;
                case 3:
                     mSettings.getSharedPreferences().edit().putBoolean(PreferenceKeys.PREF_VOICE_FLIP, true).commit();
                     mUiController.setVoiceFlip(true);
                     if(Controller.pageFilpState){
                    	 mUiController.hidePageFlip();
                     }
                     break;            
                default:
                    break;
                }
			}
    	}
    }
    
    private class RotaryModeSelectedListener implements CustomDialog.onModChangeListener{
    	public void onModChange(int which, boolean isSave) {
    		// TODO Auto-generated method stub
    		dialog.dismiss();
    		dialog = null;
			if(isSave){
                mSettings.getSharedPreferences().edit().putInt(PreferenceKeys.PREF_ROTARY, which).commit();
                switch (which) {
                case 0:
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                    break;
                case 1:
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    break;
                case 2:
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;            
                default:
                    break;
                }
			}
    	}
    }
    
    private class UaModeSelectedListener implements CustomDialog.onModChangeListener{
    	public void onModChange(int which, boolean isSave) {
    		// TODO Auto-generated method stub
    		dialog.dismiss();
    		dialog = null;
			if(isSave){
                mSettings.getSharedPreferences().edit().putString(PreferenceKeys.PREF_USER_AGENT, which+"").commit();
			}
    	}
    }
    
    private class PicModeSelectedListener implements CustomDialog.onModChangeListener{
    	public void onModChange(int which, boolean isSave) {
    		// TODO Auto-generated method stub
    		dialog.dismiss();
    		dialog = null;
			if(isSave){
	    		 mSettings.getSharedPreferences().edit().putInt(PreferenceKeys.PREF_PAGE_PICTURE, which).commit();
			}
    	}
    }    
    
    
    public TabMenu getTabMenu(){
        if(tabMenu != null){
            return tabMenu;
        }
        return null;
    }

    public void setPie(MainMenu mPie){
        this.mPie = mPie;
    }
    protected void populateMenu() {
       // mPie.removeAllViews();
        tabMenu = new TabMenu(mActivity, new BodyClickEvent(), 
                new BodyClickEvent2(),new BodyClickEvent3(),  bodyAdapter[0], bodyAdapter[1], bodyAdapter[2],
                R.style.PopupAnimation);
 
        tabMenu.update(); 
    	
        mPie.clearItems();
        int count = mUiController.getTabControl().getTabCount();
        if(count==0)
            count = 1;
        Bitmap contactIcon=Bitmap.createBitmap(55, 55, Config.ARGB_8888);
        Bitmap bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.menu_multiwindow);
        Canvas canvas = new Canvas(contactIcon);
        Rect src=new Rect(0, 0, contactIcon.getWidth(), contactIcon.getHeight());  
        
        Rect dst=new Rect(NavLeft, NavTop, NavRight, NavBottom);
        Paint iconPaint=new Paint();  
        iconPaint.setDither(true);//防抖动  
        iconPaint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, null, dst, iconPaint);  
        Paint countPaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DEV_KERN_TEXT_FLAG);  
        countPaint.setColor(getColor(R.color.title_color));  
        countPaint.setTextSize(20f);  
        countPaint.setTypeface(Typeface.DEFAULT_BOLD); 
        canvas.drawText(count+"", 19, 37, countPaint);
        
        homepage = makeItem(R.drawable.toolbar_homepage, 1);
        backward = makeItem(R.drawable.toolbar_backward_disable, 1);
        forward = makeItem(R.drawable.toolbar_forward_disable, 1);
        toolbar_menu = makeItemB(contactIcon, 1);
        bookmark = makeItem(R.drawable.toolbar_menu, 1);
 
        mPie.addItem(backward);
        mPie.addItem(forward);
        mPie.addItem(homepage);
        mPie.addItem(toolbar_menu);
        mPie.addItem(bookmark);

        mPie.show(true);

        setClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (homepage.getView() == v) {
                    if(Controller.homepage == false){
                        int flip = mSettings.getSharedPreferences().getInt(PreferenceKeys.PREF_PAGE_FLIP, 0);
                        if((flip==1 || flip==2)&& Controller.pageFilpState){
                            mUiController.hidePageFlip();
                        }
                        Controller.homepageBack = true;
                        Controller.homepage = true;
//                        mUiController.getHandler().removeMessages(Controller.LOAD_URL_DELAY);
                        ((BaseUi)mUi).qucikNLRequestFoucs();
                        ((BaseUi)mUi).SetSanyaHomepage(); 
                        ((BaseUi)mUi).showHomePage();
//                        if(mUiController.getTabControl().getTabCount() > 1){
//                            mUiController.getTabControl().removeTab(mUiController.getTabControl().getTab(mUiController.getTabControl().getCurrentPosition()-1));
//                        }
                        ((BaseUi)mUi).qucikNLRequestFoucs();
                    }
               } else if (backward.getView() == v) {
                   
                   Tab current = mUiController.getCurrentTab();
                   if(current == null){
                	   return;
                   }
                   setForwardEnabledTrue();
                   if(current.canGoBack()){
                   
                       current.goBack();
                       setBackWardEnabledTrue();
                   }else {
                       Tab parent = current.getParent();
                       if (parent != null) {
                           mUiController.switchToTab(parent);
                           // Now we close the other tab
                           mUiController.closeTab(current);
                           
                           ((BaseUi)mUi).getMainMenuControlPhone().setBackWardEnabledTrue();
                           if(parent.canGoForward()){
                               ((BaseUi)mUi).getMainMenuControlPhone().setForwardEnabledTrue();
                           }else{
                               ((BaseUi)mUi).getMainMenuControlPhone().setForwardEnabledFalse();
                           }
                       }else{
                           if(Controller.homepage == false){
                               int flip = mSettings.getSharedPreferences().getInt(PreferenceKeys.PREF_PAGE_FLIP, 0);
                               if(flip==1 || flip==2){
                                   mUiController.hidePageFlip();
                               }
                               Controller.homepageBack = true;
                               Controller.homepage = true;
//                               mUiController.getHandler().removeMessages(Controller.LOAD_URL_DELAY);
                               ((BaseUi)mUi).qucikNLRequestFoucs();
                               ((BaseUi)mUi).SetSanyaHomepage(); 
                               ((BaseUi)mUi).showHomePage();
//                               if(mUiController.getTabControl().getTabCount() > 1){
//                                   mUiController.getTabControl().removeTab(mUiController.getTabControl().getTab(mUiController.getTabControl().getCurrentPosition()-1));
//                               }
                               ((BaseUi)mUi).qucikNLRequestFoucs();
                               
                           }
                       }
                   }    
                       
                       
               } else if (forward.getView() == v) {
                   Tab current = mUiController.getCurrentTab();
                   if(current == null){
                	   return;
                   }
                   setBackWardEnabledTrue();
                   if(Controller.homepage == true||Controller.homepageBack == true){
                       int flip = mSettings.getSharedPreferences().getInt(PreferenceKeys.PREF_PAGE_FLIP, 0);
                       if(flip==1 || flip==2){
                           mUiController.addPageFlip();
                       }
                       Controller.homepage = false;
                       Controller.homepageBack = false;
                       ((BaseUi)mUi).hideHomePage();
                       if(current!=null && current.canGoForward()){
                           setForwardEnabledTrue();
                       }else{
                           setForwardEnabledFalse();
                       }
                   }else if(current!=null && current.canGoForward()){
                    
                	   current.goForward();
                       if(current.canGoForward()){
                           setForwardEnabledTrue();
                       }else{
                           setForwardEnabledFalse();
                       }
                   }else {
                
                       setForwardEnabledFalse();
                   }
                   
                   
               } else if (bookmark.getView() == v) {
                     if (tabMenu != null) { 
                         if (tabMenu.isShowing()){
                              tabMenu.dismiss(); 
                         } else { 
                              tabMenu.showAtLocation(mPie, Gravity.BOTTOM, 0, mPie.getHeight()); 
                         } 
                     } 
               } else if (toolbar_menu.getView() == v) {
                   ((PhoneUi) mUi).toggleNavScreen();
               }
            }
        }, homepage, backward, forward, toolbar_menu, bookmark);

    }
 
    protected void hideMenu() {
        tabMenu.dismiss(); 
    }
    public MainMenuItem getBackWard(){
        return backward;
    }
    
    public MainMenuItem getForward(){
        return forward;
    }
    
    public void setForwardEnabledTrue(){
        forward.getView().setEnabled(true);
        forward.getView().setImageResource(R.drawable.toolbar_forward);
    }
   
    public void setForwardEnabledFalse(){
        forward.getView().setEnabled(false);
        forward.getView().setImageResource(R.drawable.toolbar_forward_disable);
    }
    
    public void setBackWardEnabledTrue(){
        backward.getView().setEnabled(true);
        backward.getView().setImageResource(R.drawable.toolbar_backward);
    }
    
    public void setBackWardEnabledFalse(){
        backward.getView().setEnabled(false);
        backward.getView().setImageResource(R.drawable.toolbar_backward_disable);
    }
    
    public String getBackAndForwardState(){
        String state = "";
        if(backward.getView().isEnabled()){
            state += "BE";
        }else{
            state += "BN";
        }
        if(forward.getView().isEnabled()){
            state +="@FE";
        }else{
            state +="@FN";
        }
        return state;
    }
    public void updateBackAndForwardState(String state){
        String[] states = state.split("@");
        for(int i=0; i<states.length; i++){
            if(states[i].equals("BE")){
                setBackWardEnabledTrue();
            }else if(states[i].equals("BN")){
                setBackWardEnabledFalse();
            }else if(states[i].equals("FE")){
                setForwardEnabledTrue();
            }else if(states[i].equals("FN")){
                setForwardEnabledFalse();
            }
        }
    }
}
