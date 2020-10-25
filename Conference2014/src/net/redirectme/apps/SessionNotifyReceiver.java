package net.redirectme.apps;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class SessionNotifyReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.w("Intent Received", intent.getAction());
		SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		boolean session_start_alert = myPreferences.getBoolean("pref_key_start_alert", false);
		if(!session_start_alert){
			Log.w("Intent Received", intent.getAction() + " Ignoring");
			return;
		}
		
		if(intent.getAction().equals("net.redirectme.apps.SESSION_NOTIFY")){
			long id = intent.getLongExtra(android.content.Intent.EXTRA_TEXT, 0);
			String name = intent.getStringExtra(android.content.Intent.EXTRA_TITLE);
			String location = intent.getStringExtra(android.content.Intent.EXTRA_SUBJECT);
			int trackIcon = intent.getIntExtra(android.content.Intent.EXTRA_SHORTCUT_ICON, R.drawable.track_43_general);
			
			Log.w("Intent Processing", "For ID = " + id);

			Intent newintent = new Intent(context, SessionDetailActivity.class);
			newintent.putExtra(android.content.Intent.EXTRA_TEXT, id);
			PendingIntent pIntent = PendingIntent.getActivity(context, 0, newintent, 0);

			Notification noti = new Notification.Builder(context)
			.setContentTitle(name)
			.setContentText(location).setSmallIcon(R.drawable.flower)
			.addAction(trackIcon, "View Session Detail", pIntent).build();
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			// hide the notification after its selected
			noti.flags |= Notification.FLAG_AUTO_CANCEL;
			notificationManager.notify(0, noti);
		}			
	}
}