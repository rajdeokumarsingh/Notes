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
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

 

import java.lang.reflect.Method;
import java.util.List;

import com.android.browser.UI.ComboViews;


/**
 * controller for Quick Controls pie menu
 */
public class MainMenuControlPhone extends MainMenuControlBase {

    private static final String LOGTAG = "MainMenuControlPhone";

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

    TabMenu.MenuBodyAdapter[] bodyAdapter = new TabMenu.MenuBodyAdapter[3]; 
    TabMenu.MenuTitleAdapter titleAdapter; 
    TabMenu tabMenu; 
    int selTitle = 0; 
 
    public MainMenuControlPhone(Activity activity , BaseUi ui, UiController uiController, TabControl mTabControl) {
        super(activity);
        mActivity = activity;
        this.mUi = ui;
        this.mUiController = uiController;
        this.mTabControl = mTabControl;
        mSettings = BrowserSettings.getInstance();
        if(!BrowserSettings.CMCC_PLATFORM || !BrowserSettings.CMCC_PLAN_ONE){
            titleAdapter = new TabMenu.MenuTitleAdapter(mActivity, new String[] { mActivity.getString(R.string.tabmenu_title_one), 
                    mActivity.getString(R.string.tabmenu_title_two) }, 16, 0xFF222222, Color.LTGRAY, Color.WHITE); 
            
            bodyAdapter[0] = new TabMenu.MenuBodyAdapter(mActivity, new int[] { 
                    R.string.tabmenu_bookmark, 
                    R.string.tabmenu_add_bookmark,
                    R.string.tabmenu_refresh,
                    R.string.tabmenu_history,
                    R.string.tabmenu_switch,
                    R.string.tabmenu_save_page,
                    R.string.tabmenu_download,
                    R.string.tabmenu_exit}, new int[] { R.drawable.menu_fav, R.drawable.menu_fav_add,
                    R.drawable.menu_refresh, R.drawable.menu_history, R.drawable.menu_turn_url, R.drawable.menu_save_webpage, R.drawable.menu_download, R.drawable.menu_exit }, 13, 0xFFFFFFFF); 
     
            bodyAdapter[1] = new TabMenu.MenuBodyAdapter(mActivity, new int[] { 
                    R.string.tabmenu_add_tab,
                    R.string.tabmenu_homepage,
                    R.string.tabmenu_full_screen,
                    R.string.tabmenu_share, 
                    R.string.tabmenu_night, 
                    R.string.tabmenu_change_screen,
                    R.string.tabmenu_change_page,
                    R.string.tabmenu_settings}, new int[] { R.drawable.menu_add_tab, R.drawable.menu_homepage, R.drawable.menu_fullscreen, R.drawable.menu_screenshot_share, R.drawable.tabmenu_find,
                    R.drawable.menu_rotate_screen, R.drawable.menu_scrollbutton, R.drawable.menu_setting}, 13, 
                    0xFFFFFFFF); 

            tabMenu = new TabMenu(mActivity, new TitleClickEvent(), 
                    new BodyClickEvent(), titleAdapter, 0x55123456,
                    R.style.PopupAnimation);
     
            tabMenu.update(); 
            tabMenu.SetTitleSelect(0); 
            tabMenu.SetBodyAdapter(bodyAdapter[0]); 
        }
    }

    class TitleClickEvent implements OnItemClickListener { 
        
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, 
                long arg3) { 
            selTitle = arg2; 
            tabMenu.SetTitleSelect(arg2); 
            tabMenu.SetBodyAdapter(bodyAdapter[arg2]); 
        } 
    } 
 
    class BodyClickEvent implements OnItemClickListener { 
         
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, 
                long arg3) { 
            tabMenu.SetBodySelect(arg2); 
            switch (selTitle) {
            case 0:
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
                    WebView webview = mUiController.getCurrentTopWebView();
                    if(webview != null){
                        webview.reload();
                    }
                    break;
                case 3:
                    mUiController.bookmarksOrHistoryPicker(ComboViews.History);
                    break;
                case 4: {
                    Tab tab = mTabControl.getCurrentTab();
                    if(tab != null && tab.isSnapshot()) {
                        tab.loadUrl(tab.getUrl(), null);
                        break;
                    }
                    mUi.Gotourl();
                    break;
                }
                case 5:
                    mUiController.saveSnapShots();
              
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
                break;

            default:
                switch (arg2) {
                case 0:
                    mUiController.openTabToHomePage();
                    break;
                    
                case 1:
                    Tab current = mUiController.getCurrentTab();
                    mUiController.loadUrl(current, mSettings.getHomePage());
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
                    Tab currentTab2 = mTabControl.getCurrentTab();
                    if(currentTab2 != null){
                    	currentTab2.getWebView().showFindDialog(null, true);
                    }
                    break;
                case 5:
                    Intent rotary = new Intent(mActivity, RotaryScreen.class);

                    mActivity.startActivityForResult(rotary, 7);
                    break;
                case 6:
                    boolean flip = mSettings.getSharedPreferences().getBoolean(PreferenceKeys.PREF_PAGE_FLIP, false);
                    if(flip){
                        mUiController.hidePageFlip();
                        Controller.addPageFlip = false;
                        mSettings.getSharedPreferences().edit().putBoolean(PreferenceKeys.PREF_PAGE_FLIP,  Controller.addPageFlip).commit();
                    }else{
                        mUiController.addPageFlip();
                        Controller.addPageFlip = true;
                        mSettings.getSharedPreferences().edit().putBoolean(PreferenceKeys.PREF_PAGE_FLIP,  Controller.addPageFlip).commit();
                    }
                    break;
                case 7:
                    WebView view = mUiController.getCurrentTopWebView();
                    String url = null;
                    if(view!= null){
                        url = view.getUrl();
                    }
                    Intent intent = new Intent(mActivity, BrowserPreferencesPage.class);
                    intent.putExtra(BrowserPreferencesPage.CURRENT_PAGE, url);
                    mActivity.startActivityForResult(intent, 3);
                 
                    break;
                default:
                    break;
                }
                break;
          
            }
            hideMenu();  
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
        mPie.clearItems();
        int count = mUiController.getTabControl().getTabCount();
        if(count==0)
            count = 1;
        Bitmap contactIcon=Bitmap.createBitmap(55, 55, Config.ARGB_8888);
        Bitmap bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.menu_multiwindow);
        Canvas canvas = new Canvas(contactIcon);
        Rect src=new Rect(0, 0, contactIcon.getWidth(), contactIcon.getHeight());  
        Rect dst=new Rect(10, 10, 65, 65);  
        Paint iconPaint=new Paint();  
        iconPaint.setDither(true);//防抖动  
        iconPaint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, src, dst, iconPaint);  
        Paint countPaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DEV_KERN_TEXT_FLAG);  
        countPaint.setColor(Color.WHITE);  
        countPaint.setTypeface(Typeface.DEFAULT_BOLD); 
        if(count < 10){
            countPaint.setTextSize(20f);  
            canvas.drawText(count+"", 25, 35, countPaint);
        } else {
            countPaint.setTextSize(15f);  
            canvas.drawText(count+"", 22, 35, countPaint);
        }

        if(BrowserSettings.CMCC_PLATFORM && BrowserSettings.CMCC_PLAN_ONE){
            if (mPopup == null) {
                mPopup = new PopupMenu(mActivity, mPie);
                mPopup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    
                    public boolean onMenuItemClick(MenuItem item) {
                        // TODO Auto-generated method stub
                        return mUiController.onOptionsItemSelected(item);
                    }
                });
            }
            if (!mActivity.onCreateOptionsMenu(mPopup.getMenu())) {
                mPopup = null;
                return;
            }
        }
        
        homepage = makeItem(R.drawable.toolbar_homepage, 1);
        backward = makeItem(R.drawable.toolbar_backward_disable, 1);
        forward = makeItem(R.drawable.toolbar_forward_disable, 1);
        toolbar_menu = makeItemB(contactIcon, 1);
        bookmark = makeItem(R.drawable.toolbar_menu, 1);
 
        mPie.addItem(backward);
        mPie.addItem(forward);
        mPie.addItem(homepage);
        mPie.addItem(bookmark);
        mPie.addItem(toolbar_menu);
        mPie.show(true);

        setClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (homepage.getView() == v) {
                    if(Controller.homepage == false) {
                        try {
                            Tab current = mUiController.getCurrentTab();
                            for (Method m : WebView.class.getDeclaredMethods()) {
                                if (m.getName().equals("clearActionModes")) {
                                    m.setAccessible(true);
                                    m.invoke(current.getWebView());
                                }
                            }
                        } catch (Exception e) {
                            Log.e(LOGTAG, "", e);
                        }

                        boolean flip = mSettings.getSharedPreferences().getBoolean(PreferenceKeys.PREF_PAGE_FLIP, false);
                        if(flip){
                            mUiController.hidePageFlip();
                        }
                        Controller.homepageBack = true;
                        Controller.homepage = true;
                        mUiController.getHandler().removeMessages(Controller.LOAD_URL_DELAY);
                        mUiController.getHandler().removeMessages(Controller.LOAD_URL_DELAY_BECKGROUND);
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
                   if(current == null) return;
                   setForwardEnabledTrue();
                   Controller c = null;
                   if(mTabControl != null) {
                       c = mTabControl.mController;
                   }
                   if(c != null && c.blockingEvents()) {
                       return;
                   }
                   if(current.canGoBack()) {
                       if(c != null) {
                           if(!c.canAccessNetwork()) {
                               c.setNeedRestartNetwork(true);
                               Toast.makeText(mActivity, R.string.loadSuspended,
                                       Toast.LENGTH_LONG).show();
                               return;
                           }
                       }
                       if(c != null) {
                           c.setBlockEvents(true);
                           c.sendUnblockKeyEventsMsg();
                       }
                       current.goBack();
                       setBackWardEnabledTrue();
                   } else {
                       Tab parent = current.getParent();
                       if (parent != null) {
                           if(c != null) {
                               if(!c.canAccessNetwork()) {
                                   c.setNeedRestartNetwork(true);
                                   Toast.makeText(mActivity, R.string.loadSuspended,
                                           Toast.LENGTH_LONG).show();
                                   return;
                               }
                           }

                           if(c != null) {
                               c.setBlockEvents(true);
                               c.sendUnblockKeyEventsMsg();
                           }

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
                               if(c != null) {
                                   c.setBlockEvents(true);
                                   c.sendUnblockKeyEventsMsg();
                               }

                               try {
                                   for (Method m : WebView.class.getDeclaredMethods()) {
                                      if (m.getName().equals("clearActionModes")) {
                                       m.setAccessible(true);
                                       m.invoke(current.getWebView());
                                       break;
                                     }
                                 }
                                  } catch (Exception ignored) {
                                    }
                               boolean flip = mSettings.getSharedPreferences().getBoolean(PreferenceKeys.PREF_PAGE_FLIP, false);
                               if(flip && Controller.pageFilpState){
                                   mUiController.hidePageFlip();
                               }
                               Controller.homepageBack = true;
                               Controller.homepage = true;
                               mUiController.getHandler().removeMessages(Controller.LOAD_URL_DELAY);
                               mUiController.getHandler().removeMessages(Controller.LOAD_URL_DELAY_BECKGROUND);
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
                   if(mTabControl != null && mTabControl.mController != null) {
                       Controller c = mTabControl.mController;
                       if(!c.canAccessNetwork()) {
                           c.setNeedRestartNetwork(true);
                           Toast.makeText(mActivity, R.string.loadSuspended,
                                   Toast.LENGTH_LONG).show();
                           return;
                       }
                   }

                   Tab current = mUiController.getCurrentTab();
                   if(current == null) return;
                   setBackWardEnabledTrue();
                   if(Controller.homepage == true||Controller.homepageBack == true){
                       boolean flip = mSettings.getSharedPreferences().getBoolean(PreferenceKeys.PREF_PAGE_FLIP, false);
                       if(flip){
                           mUiController.addPageFlip();
                       }
                       Controller.homepage = false;
                       Controller.homepageBack = false;
                       ((BaseUi)mUi).hideHomePage();
                       if(current != null){
	                       if(current.canGoForward()){
	                           setForwardEnabledTrue();
	                       }else{
	                           setForwardEnabledFalse();
	                       }
                       }
                   }else if(current!=null && current.canGoForward()){
                    
                	   current.goForward();
                	   Tab next = mUiController.getCurrentTab();
                	   if(next !=null){
	                       if(next.canGoForward()){
	                           setForwardEnabledTrue();
	                       }else{
	                           setForwardEnabledFalse();
	                       }
                	   }
                   }else {
                
                       setForwardEnabledFalse();
                   }
                   
                   
               } else if (bookmark.getView() == v) {
                   if(BrowserSettings.CMCC_PLATFORM && BrowserSettings.CMCC_PLAN_ONE){
                       mActivity.onPrepareOptionsMenu(mPopup.getMenu());
                       mPopup.show();
                   } else {
                       if (tabMenu != null) { 
                           if (tabMenu.isShowing()) 
                               tabMenu.dismiss(); 
                           else { 
                               tabMenu.showAtLocation(mPie, 
                                       Gravity.BOTTOM, 0, mPie.getHeight()); 
                           } 
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
