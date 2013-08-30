package com.android.browser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CustomDialog extends Dialog implements OnClickListener, OnItemClickListener {

	public interface onModChangeListener{
		public void onModChange(int which, boolean isSave);
	}
	private Context mContext;
	private ListView mList;
	private TextView mTitle;
	private View mBtnOk;   
	private View mBtnCancel;  
	private ChoiceAdapter mAdapter;
	private CharSequence[] mDatas;
	private boolean mIsMessageMode;
	private int mCheckedItem;
	
	private onModChangeListener mListener;
	
	public CustomDialog(Context context) {
		super(context);
		init(context);
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
		init(context);
	}

	private void init(Context context){
		mContext = context;
        setContentView(R.layout.single_choice_dialog);
		
		mList = (ListView) findViewById(R.id.choiceList);
		
		mTitle = (TextView) findViewById(R.id.dialog_title);
		mBtnOk = findViewById(R.id.ok);
		mBtnCancel = findViewById(R.id.cancel);
		mBtnOk.setOnClickListener(this);
		mBtnCancel.setOnClickListener(this);
		
	}
	public void config(int checkedItem, onModChangeListener listener){
		mCheckedItem = checkedItem;
		mList.setOnItemClickListener(this);
		this.mListener = listener;
	}
	
	public void setTitle(int id){
		mTitle.setText(id);
	}
	
	public void setDatas(int arrayId){
		mDatas = mContext.getResources().getStringArray(arrayId);
		mAdapter = new ChoiceAdapter();
		mList.setAdapter(mAdapter);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok:
			mListener.onModChange(mCheckedItem, true);
			break;
		case R.id.cancel:
			mListener.onModChange(mCheckedItem, false);
			break;
		default:
			break;
		}
		
		dismiss();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			mListener.onModChange(mCheckedItem, false);
			dismiss();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mCheckedItem = position;
		mAdapter.notifyDataSetChanged();
	}

	
	private class ChoiceAdapter extends BaseAdapter{

		
		public int getCount() {
			return mDatas.length;
		}


		public Object getItem(int position) {
			return mDatas[position];
		}

		
		public long getItemId(int position) {
			return position;
		}

		
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
//				convertView = getLayoutInflater().inflate(R.layout.single_choice_item, null);
				convertView = getLayoutInflater().inflate(R.layout.select_dialog_singlechoice, null);
			}
			CheckedTextView choice = (CheckedTextView) convertView.findViewById(R.id.choice);
			choice.setText(mDatas[position]);
			if(mCheckedItem == position){
				choice.setCheckMarkDrawable(R.drawable.radio_selected);
			} else {
				choice.setCheckMarkDrawable(R.drawable.radio_unselected);
			}
			return convertView;
		}
		
	}
}
