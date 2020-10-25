package net.redirectme.apps;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class MenuActivity extends Activity implements ActionBar.OnNavigationListener, SearchView.OnQueryTextListener {
	// action bar
	private ActionBar actionBar;
	private int selectedItem=0;
	SharedPreferences myPreferences;

	// Title navigation Spinner data
	private ArrayList<SpinnerItem> navSpinner;

	// Navigation adapter
	private TitleSpinnerAdapter adapter;

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		if(selectedItem!=itemPosition)
		{
			selectedItem = itemPosition;
			// Do something in response to button
			Intent intent = new Intent("net.redirectme.apps.SESSION_VIEW_CHANGE");
			intent.putExtra(android.content.Intent.EXTRA_TEXT, itemPosition);
			sendBroadcast(intent);
			
		}
		return false;
	}
	
	public void setNavigationItem(int position){
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// get Internet status
		// flag for Internet connection status
		actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Spinner title navigation data
		navSpinner = new ArrayList<SpinnerItem>();
		navSpinner.add(new SpinnerItem("All Sessions", R.drawable.calendar));
		navSpinner.add(new SpinnerItem("My Sessions", R.drawable.clock));
		navSpinner.add(new SpinnerItem("Tracks", R.drawable.rainbow));
		navSpinner.add(new SpinnerItem("Locations", R.drawable.door));
		navSpinner.add(new SpinnerItem("Time", R.drawable.selftimer));
		navSpinner.add(new SpinnerItem("Speakers", R.drawable.profile));
		navSpinner.add(new SpinnerItem("Topics", R.drawable.document));

		// title drop down adapter
		adapter = new TitleSpinnerAdapter(getApplicationContext(), navSpinner);

		// assigning the spinner navigation     
		actionBar.setListNavigationCallbacks(adapter, this);
		actionBar.setDisplayShowHomeEnabled(true);

		/*
		// Connection detector class
		CheckNetwork cn = new CheckNetwork(MenuActivity.this);
		isConnected = cn.isConnectingToInternet();

		// check for Internet status
		if (!isConnected) {
			// Internet connection is not present
			// Ask user to connect to Internet
			Toast toast = Toast.makeText(getApplicationContext(), res.getString(R.string.internetna), Toast.LENGTH_SHORT);
			toast.show();
		}*/
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchView.setQueryRefinementEnabled(true);
		searchView.setOnQueryTextListener(this);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		Log.w("OptionSelected", "Item ID = " + id);
		
		if(id==R.id.imgIcon)
		if (id == R.id.action_settings) {
			Intent intent = new Intent();
			intent.setClass(MenuActivity.this, PrefsActivity.class);
			startActivityForResult(intent, 0); 
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		Log.w("Test", "Search Clicked");
		// Do something in response to button
		Intent intent = new Intent("net.redirectme.apps.SESSION_VIEW_SEARCH");
		intent.putExtra(android.content.Intent.EXTRA_TEXT, selectedItem);
		intent.putExtra(android.content.Intent.EXTRA_TITLE, query);
		sendBroadcast(intent);

		return false;
	}
	@Override
	public boolean onQueryTextChange(String query) {
		// TODO Auto-generated method stub
		Log.w("Test", "Search Changed" + query);
		Intent intent = new Intent("net.redirectme.apps.SESSION_VIEW_SEARCH");
		intent.putExtra(android.content.Intent.EXTRA_TEXT, selectedItem);
		intent.putExtra(android.content.Intent.EXTRA_TITLE, query);
		sendBroadcast(intent);
		return false;
	}	
}