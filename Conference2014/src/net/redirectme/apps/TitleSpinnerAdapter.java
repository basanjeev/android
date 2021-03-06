package net.redirectme.apps;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import net.redirectme.apps.R;

public class TitleSpinnerAdapter extends BaseAdapter {
	private ImageView imgIcon;
	private TextView txtTitle;
	private ArrayList<SpinnerItem> SpinnerItem;
	private Context context;

	public TitleSpinnerAdapter(Context context, ArrayList<SpinnerItem> SpinnerItem) {
		this.SpinnerItem = SpinnerItem;
		this.context = context;
	}

	@Override
	public int getCount() {
		return SpinnerItem.size();
	}

	@Override
	public Object getItem(int index) {
		return SpinnerItem.get(index);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) { 
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.list_item_title_navigation, null);
		}

		imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
		txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
		imgIcon.setImageResource(SpinnerItem.get(position).getIcon());
		imgIcon.setVisibility(View.GONE);
		txtTitle.setText(SpinnerItem.get(position).getTitle());
		return convertView;
	}


	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.list_item_title_navigation, null);
		}
		imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
		txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
		imgIcon.setImageResource(SpinnerItem.get(position).getIcon()); 
		txtTitle.setText(SpinnerItem.get(position).getTitle());
		return convertView;
	}
}