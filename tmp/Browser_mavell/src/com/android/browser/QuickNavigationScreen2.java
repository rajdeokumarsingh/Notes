package com.android.browser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.zip.Inflater;

 

import com.android.browser.MainMenuControlPhone.BodyClickEvent;
import com.android.browser.provider.BrowserProvider2;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class QuickNavigationScreen2 extends LinearLayout implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, View.OnLongClickListener{

    private static final int LOADER_SCREEN = 0;
    private GridView gvTitle = null;
    private Activity mActivity = null;
    private QuickNavigationScreenAapter adapter = null;
    private Cursor cursor = null;
    private UiController mController = null;
    private BaseUi mBaseUi = null;
    private LayoutInflater inflater;
    final static int MENU_ORDER_EDIT = 0;
    final static int MENU_ORDER_DELETE = 1;
    private SelectItem item = null;
    private ScreenMenu.MenuBodyAdapter bodyAdapter = null;
    private ScreenMenu screenMenu = null;
    private int horizontalSpacing = 0;
    public QuickNavigationScreen2(Activity activity, UiController controller, BaseUi baseUi) {
        super(activity);
        
        this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 
                LayoutParams.FILL_PARENT));
        this.setBackgroundColor(activity.getResources().getColor(R.color.homepage_color));
        mActivity = activity;
        mController = controller;
        mBaseUi = baseUi;
        horizontalSpacing = mActivity.getResources().getInteger(R.integer.Horizontal_spacing);
        inflater = LayoutInflater.from(activity);
        gvTitle = (GridView)inflater.inflate(R.layout.home_center_gridview, null);
        gvTitle.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); 
        gvTitle.setVerticalSpacing(15); 
        gvTitle.setHorizontalSpacing(horizontalSpacing);
        gvTitle.setPadding(20, 30, 20, 10);
        gvTitle.setGravity(Gravity.CENTER); 

        // Start the loaders
        LoaderManager lm = activity.getLoaderManager();
        lm.restartLoader(LOADER_SCREEN, null, this);

        this.addView(gvTitle);
    }



    public SelectItem getSelectItem(){
        return item;
    }
    
     private class QuickNavigationScreenAapter extends CursorAdapter{

        public QuickNavigationScreenAapter(Context context, Cursor c) {
            super(context, c);
            // TODO Auto-generated constructor stub
        }
        
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return super.getItemId(position);
        }
        
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return super.getItem(position);
        }
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            // TODO Auto-generated method stub
            View screen_item = LayoutInflater.from(context).inflate(R.layout.quick_navigation_item, null);
            return screen_item;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // TODO Auto-generated method stub
            ImageView image = (ImageView)view.findViewById(R.id.quick_navigation_thumb);
            image.setScaleType(ScaleType.FIT_XY);
            image.setTag(R.id.quick_navigation_thumb, cursor.getLong(0));
            image.setTag(R.id.quick_navigation_title, cursor.getString(1));
            image.setTag(R.id.quick_navigation_line, cursor.getString(2));
            image.setOnClickListener(QuickNavigationScreen2.this);
            image.setOnLongClickListener(QuickNavigationScreen2.this);
          
          //  image.setBackgroundResource(R.drawable.quick_navigation_bg);
            TextView text = (TextView)view.findViewById(R.id.quick_navigation_title);
            text.setTextColor(Color.BLACK);
            String name = cursor.getString(1);
            String url = cursor.getString(2);
            if(url!=null && url.length()!=0){
                text.setText(name);
                byte[] imageData = cursor.getBlob(4);
                Bitmap bitmap =  BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                Drawable bitmapAdd = new ColorDrawable(Color.TRANSPARENT);
                Drawable[] array = new Drawable[2];
                array[0] = bitmapAdd;
                array[1] = new BitmapDrawable(bitmap);
                LayerDrawable la = new LayerDrawable(array);
                la.setLayerInset(0, 0, 0, 0, 0);
                la.setLayerInset(1, 2, 2, 2, 2);

                image.setImageDrawable(la);
//                image.setImageBitmap(bitmap);
            }else {
                name = context.getResources().getString(R.string.add_new_quick_navigation);
                text.setText(name);
                Bitmap add = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_add);
                image.setImageBitmap(add);
            }
        }
                
     }


     class ItemViewOnCreateContextMenuListener implements OnCreateContextMenuListener {

         public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
                 menu.add(0, R.layout.quick_navigation_item, MENU_ORDER_EDIT, R.string.edit_quick_navigation);
                 menu.add(0, R.layout.quick_navigation_item, MENU_ORDER_DELETE, R.string.delete_quick_navigation);

         }

     }
     
     static String[] SCREEN_PROJECTION = new String[] {
         BrowserProvider2.NavScreen._ID,
         BrowserProvider2.NavScreen.TITLE,
         BrowserProvider2.NavScreen.URL,
         BrowserProvider2.NavScreen.UPDATE,
         BrowserProvider2.NavScreen.DATA
     };

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // TODO Auto-generated method stub
        CursorLoader loader =
             new CursorLoader(mActivity, BrowserProvider2.NavScreen.CONTENT_URI, SCREEN_PROJECTION, null, null, BrowserProvider2.NavScreen.DATE + " ASC");
      //  loader.setUpdateThrottle(2000);
        return loader;
    }



    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // TODO Auto-generated method stub
        if (adapter == null) {
            adapter = new QuickNavigationScreenAapter(mActivity, data);
            gvTitle.setAdapter(adapter);
        } else {
            adapter.swapCursor(data);
        }
        this.cursor = data;
    }



    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub
        adapter.changeCursor(null);
    }

    public void closeCursor(){
        if(cursor != null){
            cursor.close();
        }
    }

    public void closeScreenCursor(){
        closeCursor();
        LoaderManager lm = mActivity.getLoaderManager();
        lm.destroyLoader(LOADER_SCREEN);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        ImageView image = (ImageView)v.findViewById(R.id.quick_navigation_thumb);
        long id = (Long)image.getTag(R.id.quick_navigation_thumb);
        String url = (String)image.getTag(R.id.quick_navigation_line);
        String name = (String)image.getTag(R.id.quick_navigation_title);
        if(url!=null && url.length()!=0){
          //  Tab tab = ((UiController)mController).openTab(url, false, true, false, ((UiController)mController).getCurrentTab());
            Tab tab = ((UiController)mController).openTab(url, false, true, false);
            Controller.homepage = false;
            Controller.homepageBack = false;
            mBaseUi.hideHomePage();
        }else{
            Intent intent = new Intent(mActivity, AddQuickNavigationPage.class);
            intent.putExtra("index", id);
            mActivity.startActivityForResult(intent, Controller.EDIT_QUICK_NAVIGATION);
        }
    }



    public boolean onLongClick(View v) {
        // TODO Auto-generated method stub
        ImageView image = (ImageView)v.findViewById(R.id.quick_navigation_thumb);
        long id = (Long)image.getTag(R.id.quick_navigation_thumb);
        String url = (String)image.getTag(R.id.quick_navigation_line);
        String name = (String)image.getTag(R.id.quick_navigation_title);
        String edit = mActivity.getString(R.string.edit_quick_navigation);
        String delete = mActivity.getString(R.string.delete_quick_navigation);
        item = new SelectItem(id, name, url);
        if(url!=null && url.length()!=0){       
            bodyAdapter = new ScreenMenu.MenuBodyAdapter(mActivity, new String[] { 
                    edit, delete}, new int[] { R.drawable.quick_menu_editor, 
                    R.drawable.quick_menu_delete }, 13, mActivity.getResources().getColor(R.color.title_color));
            bodyAdapter.setSelectItem(item);
            screenMenu = new ScreenMenu(mActivity, new BodyClickEvent(), 0x55123456, item);
            screenMenu.SetBodyAdapter(bodyAdapter);
            screenMenu.showAsDropDown(v);
        }
        return true;
    }
    
    class BodyClickEvent implements OnItemClickListener { 
        
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, 
                long arg3) { 
            screenMenu.SetBodySelect(arg2);
            item = (SelectItem)arg1.getTag(R.drawable.screen_body_item_background);
            switch (arg2) {
            case 0:
                screenMenu.dismiss();
                Intent edit = new Intent(mActivity, AddQuickNavigationPage.class);
                edit.putExtra("selectItem", item);
                mActivity.startActivityForResult(edit, Controller.EDIT_QUICK_NAVIGATION);
                break;
            case 1:
                screenMenu.dismiss();
                Intent delete = new Intent(mActivity, DeleteQuickNavigationPage.class);
                delete.putExtra("selectItem", item);
                mActivity.startActivityForResult(delete, Controller.EDIT_QUICK_NAVIGATION);
                break;

            default:
                break;
            }
            
        } 
 
    }
}
