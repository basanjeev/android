package net.redirectme.apps;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends ArrayAdapter<SessionModel> {

	// declaring our ArrayList of items
	private List<SessionModel> objects;


	/* here we must override the constructor for ArrayAdapter
	 * the only variable we care about now is ArrayList<SessionModel> objects,
	 * because it is the list of objects we want to display.
	 */
	public ListViewAdapter(Context context, int textViewResourceId, List<SessionModel> objects) {
		super(context, textViewResourceId, objects);
		this.objects = objects;
	}

	/*
	 * we are overriding the getView method here - this is what defines how each
	 * list item will look.
	 */
	public View getView(int position, View convertView, ViewGroup parent){

		// assign the view we are converting to a local variable
		View v = convertView;

		// first check to see if the view is null. if so, we have to inflate it.
		// to inflate it basically means to render, or show, the view.
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.listitem, null);
		}

		if(objects!=null)
		{
			Log.w("Test", "Size = " + objects.size() + " Index = " + position);
			SessionModel i = objects.get(position);
	
			if (i != null) {
	
				// This is how you obtain a reference to the TextViews.
				// These TextViews are created in the XML files we defined.
				ImageView track = (ImageView) v.findViewById(R.id.list_item_track);
				TextView name = (TextView) v.findViewById(R.id.list_item_name);
				TextView author = (TextView) v.findViewById(R.id.list_item_author);
				TextView location = (TextView) v.findViewById(R.id.list_item_location);
				TextView date = (TextView) v.findViewById(R.id.list_item_date);
				TextView time = (TextView) v.findViewById(R.id.list_item_time);
				//CheckBox remind = (CheckBox)v.findViewById(R.id.list_item_track);
	
				//			CheckBoxClickListener cboxlistener = new CheckBoxClickListener(i, getContext());
				//
				//			remind.setOnCheckedChangeListener(cboxlistener);
				//			
				//			{
				//				@Override
				//				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//					if(isChecked)
				//						i.setRemind("Y");
				//					else
				//						i.setRemind("N");
				//				}
				//			});
	
				//remind.A
				//			Log.w("Testing",  i.getRemind());
				//			if(i.getRemind().equalsIgnoreCase("Y"))
				//				remind.setChecked(true);
				//			else
				//				remind.setChecked(false);
	
				name.setText(i.getName());
				author.setText(i.getAuthor() + ","+ i.getOrganization());
				location.setText(i.getLocation());
				time.setText(i.getStarttime() + "-" + i.getClosetime());
				date.setText(i.getDate());
				
				int trackIcon = i.getTrackIcon();
				track.setBackgroundResource(trackIcon);
			}

		}
		// the view must be returned to our activity
		return v;
	}
//
//	public Filter getFilter() {
//
//		Filter filter = new Filter() {
//
//			@SuppressWarnings("unchecked")
//			@Override
//			protected void publishResults(CharSequence constraint, FilterResults results) {
//
//				objects = (List<SessionModel>) results.values;
//				notifyDataSetChanged();
//			}
//
//			@Override
//			protected FilterResults performFiltering(CharSequence constraint) {
//
//				FilterResults results = new FilterResults();
//				List<SessionModel> FilteredResults = new ArrayList<SessionModel>();
//
//				// perform your search here using the searchConstraint String.
//
//				constraint = constraint.toString();
//				for (int i = 0; i < objects.size(); i++) {
//					SessionModel item = objects.get(i);
//					
//					if(item.getAuthor().contains(constraint) ||
//							item.getName().contains(constraint) ||
//							item.getOrganization().contains(constraint) ||
//							item.getLocation().contains(constraint) ||
//							item.getTrack() .contains(constraint)){
//						FilteredResults.add(item);
//					}
//				}
//
//				results.count = FilteredResults.size();
//				results.values = FilteredResults;
//				//Log.e("VALUES", results.values.toString());
//
//				return results;
//			}
//		};
//
//		return filter;
//	}

}
//
//class CheckBoxClickListener extends CompoundButton implements OnCheckedChangeListener
//{
//	SessionModel model;
//
//	public CheckBoxClickListener(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//	}
//	
//	public CheckBoxClickListener(SessionModel i , Context context) {
//		super(context);
//		model = i;
//		// TODO Auto-generated constructor stub
//	}
//	
//}
