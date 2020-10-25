package net.redirectme.apps;
import java.util.Calendar;
import java.util.List;

import android.widget.DatePicker;
import android.widget.TimePicker;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

public class MainActivity extends MenuActivity {

	private ConferenceDataSource datasource;

	public void launchMySchedule(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, SessionDBActivity.class);
		intent.putExtra(android.content.Intent.EXTRA_TEXT, SessionDBActivity.MY_SESSIONS);
		startActivity(intent);
	}

	public void launchCompleteSchedule(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, SessionDBActivity.class);
		intent.putExtra(android.content.Intent.EXTRA_TEXT, SessionDBActivity.ALL_SESSIONS);
		startActivity(intent);
	}

	public void launchCurrent(View view) {
		final Calendar c = Calendar.getInstance();
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {

				final Calendar c = Calendar.getInstance();
				int mHour = c.get(Calendar.HOUR_OF_DAY);
				int mMinute = c.get(Calendar.MINUTE);

				TimePickerDialog tpd = new TimePickerDialog(view.getContext(),
						new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						// Do something in response to button
						Intent intent = new Intent(view.getContext(), SessionDBActivity.class);
						intent.putExtra(android.content.Intent.EXTRA_TEXT, SessionDBActivity.TIME);
						intent.putExtra(android.content.Intent.EXTRA_TITLE, "How to name it");
						startActivity(intent);
					}
				}, mHour, mMinute, false);
				tpd.show();
			}
		}, mYear, mMonth, mDay);
		dpd.show();


		//TODO
	}
	public void onBackPressed() {
		new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
		.setMessage("Are you sure?")
		.setPositiveButton("yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			}
		}).setNegativeButton("no", null).show();
	} 

	// Using test to show it works
	private void createScheduledNotification(List<SessionModel> values, int test)
	{

		// Get new calendar object and set the date to now
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		// Add defined amount of days to the date

		getBaseContext();
		// Retrieve alarm manager from the system
		AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

		for (SessionModel element : values) {

			String[] ymd = element.getStartDateSplit();
			int month = Integer.parseInt(ymd[0]);
			int date = Integer.parseInt(ymd[1]);
			int year = Integer.parseInt(ymd[2]);

			String[] hm = element.getStartTimeSplit();
			int hour = Integer.parseInt(hm[0]);
			int min  = Integer.parseInt(hm[1]);

			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.DATE, date);
			calendar.set(Calendar.HOUR, hour);
			calendar.set(Calendar.MINUTE, min);

			long secs = calendar.getTimeInMillis();
			secs -= 600000;
			calendar.setTimeInMillis(secs);
			Log.w("AlarmManager-TDC2014 ", "Setting Alarm Time : " + calendar.toString());
			int id = (int) System.currentTimeMillis();
			Intent intent = new Intent("net.redirectme.apps.SESSION_NOTIFY");
			intent.putExtra(android.content.Intent.EXTRA_TEXT, element.getId());
			intent.putExtra(android.content.Intent.EXTRA_TITLE, element.getName());;
			intent.putExtra(android.content.Intent.EXTRA_SUBJECT, element.getAuthor());

			int trackIcon = element.getTrackIcon();
			intent.putExtra(android.content.Intent.EXTRA_SHORTCUT_ICON, trackIcon);


			PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_ONE_SHOT);
			alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

		}
		if(test==1)
		{
			// 5secs from now
			calendar.setTimeInMillis(System.currentTimeMillis() + 10000);
			Log.w("AlarmManager-TEST ", "Setting Alarm Time : " + calendar.getTime());
			int id = (int) System.currentTimeMillis();
			Intent intent = new Intent("net.redirectme.apps.SESSION_NOTIFY");
			long value = 1;
			intent.putExtra(android.content.Intent.EXTRA_TEXT, value);
			intent.putExtra(android.content.Intent.EXTRA_TITLE, "Test Session");;
			intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Sanjeev BA");
			intent.putExtra(android.content.Intent.EXTRA_SHORTCUT_ICON, R.drawable.track_43_general);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
			alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		int query = SessionDBActivity.MY_SESSIONS;
		datasource = new ConferenceDataSource(this);
		datasource.open();
		List<SessionModel> values = datasource.getSessionDetails(query, null);

		SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		boolean session_start_alert = myPreferences.getBoolean("pref_key_start_alert", false);
		if(session_start_alert)
			createScheduledNotification(values, 1);
	}
}
