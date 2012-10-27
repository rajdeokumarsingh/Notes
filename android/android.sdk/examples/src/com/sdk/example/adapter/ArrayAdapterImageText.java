package com.sdk.example.adapter;

import java.util.ArrayList;
import java.util.List;

import com.sdk.example.R;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ArrayAdapterImageText extends ListActivity {

	class ItemData {
		public ItemData(String name, String tel, String email) {
			this.name = name;
			this.tel = tel;
			this.email = email;
		}

		String name;
		String tel;
		String email;
	}

	class ContactArrayAdatpter extends ArrayAdapter<ItemData> {

		public ContactArrayAdatpter(Context context, int resource,
				int textViewResourceId, List<ItemData> objects) {
			super(context, resource, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ItemData item = getItem(position);
			LinearLayout itemView = new LinearLayout(getContext());

			if (convertView == null) {
				// LayoutInflater inflater = (LayoutInflater) getContext()
				// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				// inflater.inflate(
				// R.layout.array_adapter_list_item, itemView, true);
				getLayoutInflater().inflate(R.layout.array_adapter_list_item,
						itemView, true);
			} else {
				itemView = (LinearLayout) convertView;
			}

			TextView name = (TextView) itemView
					.findViewById(R.id.text_view_name);
			TextView tel = (TextView) itemView.findViewById(R.id.text_view_tel);
			TextView email = (TextView) itemView
					.findViewById(R.id.text_view_email);
			name.setText(item.name);
			tel.setText(item.tel);
			email.setText(item.email);

			return itemView;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ContactArrayAdatpter adapter = new ContactArrayAdatpter(this,
				R.layout.array_adapter_list_item, R.id.text_view_name,
				getData());
		getListView().setAdapter(adapter);
	}

	private List<ItemData> getData() {
		String[] names = { "Rui", "Feng", "Mother" };
		String[] tels = { "150 1100 5932", "138 1166 6985", "132 0713 1873" };
		String[] emails = { "rui.jiang@pekall.com", "feng.xiao@yahoo.cn", "NA" };

		List<ItemData> dlist = new ArrayList<ItemData>();
		for (int i = 0; i < names.length; i++) {
			dlist.add(new ItemData(names[i], tels[i], emails[i]));
		}
		return dlist;
	}

}
