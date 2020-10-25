package net.redirectme.apps;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ConferenceNotificationService extends Service {
	ConferenceDBHelper db;
	
    public void onCreate() {
    	db = new ConferenceDBHelper(this);
    }
    
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//Build notifications and fire.
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
