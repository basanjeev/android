package net.redirectme.apps;

import java.util.List;

import android.util.Log;
import android.view.View;
import 	android.widget.AdapterView;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SessionDBActivity extends MenuActivity {
	private ConferenceDataSource datasource;

	SessionViewReceiver receiver = null;
	boolean myReceiverIsRegistered = false;
	ListView listView = null;

	public static final int ALL_SESSIONS = 0;
	public static final int MY_SESSIONS = 1;
	public static final int TRACKS = 2;
	public static final int LOCATIONS = 3;
	public static final int TIME = 4;
	public static final int SPEAKERS = 5;
	public static final int TOPICS = 6;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int query = ALL_SESSIONS;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			query = extras.getInt(Intent.EXTRA_TEXT);
		} 
		setContentView(R.layout.session_results);
		listView = (ListView) findViewById(R.id.list);
		datasource = new ConferenceDataSource(this);
		datasource.open();
		List<SessionModel> values = datasource.getSessionDetails(query, null);
		ArrayAdapter<SessionModel> adapter = new ListViewAdapter(this, R.layout.listitem, values);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(SessionDBActivity.this, SessionDetailActivity.class);
				SessionModel obj = (SessionModel)listView.getAdapter().getItem(position);
				intent.putExtra(android.content.Intent.EXTRA_TEXT, obj.getId());
				startActivity(intent);
			}
		});

		receiver = new SessionViewReceiver();
	}

	class SessionViewReceiver extends BroadcastReceiver {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			Log.w("Intent Received", "Now need to check");
			//change the listview display
			if(intent.getAction().equals("net.redirectme.apps.SESSION_VIEW_CHANGE")){
				int query = intent.getIntExtra(android.content.Intent.EXTRA_TEXT, 0);
				List<SessionModel> values = datasource.getSessionDetails(query, null);
				@SuppressWarnings("unchecked")
				ArrayAdapter<SessionModel> adapter = (ArrayAdapter<SessionModel>)listView.getAdapter();
				adapter.clear();
				adapter.addAll(values);
				listView.setAdapter(adapter);
			}
			if(intent.getAction().equals("net.redirectme.apps.SESSION_VIEW_SEARCH")){
				int query = intent.getIntExtra(android.content.Intent.EXTRA_TEXT, 0);
				String searchText = intent.getStringExtra(android.content.Intent.EXTRA_TITLE);
				List<SessionModel> values = datasource.getSessionDetails(query, searchText);
				@SuppressWarnings("unchecked")
				ArrayAdapter<SessionModel> adapter = (ArrayAdapter<SessionModel>)listView.getAdapter();
				adapter.clear();
				adapter.addAll(values);
				listView.setAdapter(adapter);
			}
			if(intent.getAction().equals("net.redirectme.apps.SESSION_VIEW_SEARCH_EMPTY")){
				Toast toast = Toast.makeText(context, "No Matching Results. Showing All Sessions.", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}
	@Override
	protected void onResume() {
		datasource.open();
		if (!myReceiverIsRegistered) {
			registerReceiver(receiver, new IntentFilter("net.redirectme.apps.SESSION_VIEW_CHANGE"));
			registerReceiver(receiver, new IntentFilter("net.redirectme.apps.SESSION_VIEW_SEARCH"));
			registerReceiver(receiver, new IntentFilter("net.redirectme.apps.SESSION_VIEW_SEARCH_EMPTY"));
			myReceiverIsRegistered = true;
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		if (myReceiverIsRegistered) {
			unregisterReceiver(receiver);
			myReceiverIsRegistered = false;
		}
		super.onPause();
	}

} 