package com.android.browser;

import java.util.ArrayList;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.os.Handler;
import android.provider.BrowserContract;

public class LastVisitedItems extends LinearLayout implements View.OnClickListener, AdapterView.OnItemClickListener{

	private Context mContext;
	private LayoutInflater inflater;
	private LinearLayout left_item;
    private View screenItemLine;
	private LastItemListView listItem;
	private static final String[] history = {"_id", "title", "url", "visits"}; 
	private static final String[] images = {"favicon", "thumbnail", "touch_icon"};
	private ArrayList list;
	private Cursor lastUrl;
 	private TextView text1;
	private TextView text2;
    private boolean expand = true;
	private LastAdapter adapter;
	private LinearLayout screenItemImage;
	private ImageView screenItemImageList;
	private BaseUi mUi;
	private UiController mUiController;
 
 	public LastVisitedItems(Context context, BaseUi ui, UiController uiController) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context; 
		mUi = ui;
		mUiController = uiController;
        inflater = LayoutInflater.from(mContext);
        left_item = (LinearLayout)inflater.inflate(R.layout.last_visited, null);
        screenItemImage = (LinearLayout)left_item.findViewById(R.id.screen_item_image);
        screenItemImageList = (ImageView)left_item.findViewById(R.id.screen_item_image_list);
        screenItemLine = left_item.findViewById(R.id.screen_item_line);
 
        screenItemLine.setOnClickListener(this);
//        screenItemLine.setBackgroundResource(R.drawable.screen_left_img);
        listItem = (LastItemListView)left_item.findViewById(R.id.list);
        list = new ArrayList<HistoryMost>();
        getLastVistedFormDatabase();
        adapter = new LastAdapter(list);
        listItem.setAdapter(adapter);
        listItem.setOnItemClickListener(this);
		context.getContentResolver().registerContentObserver(BrowserContract.History.CONTENT_URI, false, new MyContentObserver());
        this.addView(left_item);
	}
    private class MyContentObserver extends ContentObserver {
        public MyContentObserver() {
            super(new Handler());
        }
        @Override
        public void onChange(boolean selfChange) {
        	// TODO Auto-generated method stub
        	getLastVistedFormDatabase();
        	adapter = new LastAdapter(list);
        	listItem.setAdapter(adapter);
        	super.onChange(selfChange);
        }
    }
    void getLastVistedFormDatabase(){
        lastUrl = mContext.getContentResolver().query(BrowserContract.History.CONTENT_URI, history, null, null, "visits DESC");
 		int max = lastUrl.getCount()<4?lastUrl.getCount():4;
		HistoryMost history =null;
		list.clear();
		Cursor image = null;
		list.clear();
		for(int i=0; i<max; i++){
			lastUrl.moveToPosition(i);
			history = new HistoryMost();
			history.id = lastUrl.getInt(0);
			history.title = lastUrl.getString(1);
			history.url = lastUrl.getString(2);
			image =  mContext.getContentResolver().query(BrowserContract.Images.CONTENT_URI, images, "url_key = ?", new String[]{history.url}, null);
			if(image.moveToNext()){
				image.moveToFirst();
				history.image = image.getBlob(1);
 			}
			list.add(history);
		}
    }
    class LastAdapter extends BaseAdapter{

    	private ArrayList<HistoryMost> list;
    	private RelativeLayout item;
    	private ImageView image;
    	private TextView title;
    	private TextView url;
    	private HistoryMost histroyMost;
    	public LastAdapter(ArrayList<HistoryMost> list){
    		this.list = list;
    	}
		public int getCount() {
			// TODO Auto-generated method stub
			if(list != null)
				return list.size();
			return 0;
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			if(list != null)
				return list.get(position);
			return null;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
    		item = (RelativeLayout)inflater.inflate(R.layout.last_history_item, null);
    		image = (ImageView)item.findViewById(R.id.image);
    		title = (TextView)item.findViewById(R.id.title);
    		url = (TextView)item.findViewById(R.id.url);
			histroyMost = null;
			try {
				histroyMost = list.get(position);
			} catch (Exception e) {
				// TODO: handle exception
			}

			byte[] imageData = histroyMost.image;
			if(imageData!=null && imageData.length!=0){
				image.setImageBitmap(BitmapFactory.decodeByteArray(imageData, 0, imageData.length));
			} else {
				image.setImageResource(R.drawable.browser_thumbnail); //picture?
			}
			title.setText(histroyMost.title);
			url.setText(histroyMost.url);
			return item;
		}
    	
    }

	
	class HistoryMost {
		 int id;
		 String title;
		 String url;
		 int visits;
		 byte[] image;
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
        if(v.getId()==R.id.screen_item_line){
            if(!expand){
                screenItemImage.setBackgroundColor(0xff1E90FF);
                screenItemImageList.setImageResource(R.drawable.screen_item_image_list_pressed);
                listItem.setVisibility(View.VISIBLE);
                expand = true;
            } else {
                screenItemImage.setBackgroundColor(0xff878787);
                screenItemImageList.setImageResource(R.drawable.screen_item_image_list_normal);
                listItem.setVisibility(View.GONE);
                expand = false;
            }
        }
	}
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		HistoryMost item = (HistoryMost)adapter.getItem(position);
		String url = item.url;
		if(url !=null && url.length()>0){
            Tab tab = mUiController.openTab(url, false, true, false);
            Controller.homepage = false;
            Controller.homepageBack = false;
            mUi.hideHomePage();
		}
	}
}
