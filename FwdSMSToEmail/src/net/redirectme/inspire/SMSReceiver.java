package net.redirectme.inspire;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsMessage;
//import android.util.Log;

final class MySendEmailTask extends AsyncTask<String, Integer, Long> {
	protected Long doInBackground(String... args) {
	    try {   
	        GMailSender sender = new GMailSender(args[3], args[4]);
	        //String from = args[0].toString();
	        //String subject = args[1].toString();
	        //String body = args[2].toString();
	        sender.sendMail("SMS from " + args[0],   
	        		args[1],
	        		args[3],   
	        		args[2]);
	        //Log.v("SMS Forwarding", "No Problem");
	    } catch (Exception e) {   
	    	//Log.v("SMS Forwarding", e.getLocalizedMessage());
	    }
	    return null;
	}
}
public class SMSReceiver extends BroadcastReceiver {
	public static final String PREFS_NAME = "net.redirectme.inspire.SMSForwarderPreferences";
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
    	//Log.v("SMS Received : ", "Baaah what");

        if (bundle != null)
        {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
                SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                boolean enable = settings.getBoolean("enableService", false);
                String  email  = settings.getString("forwardEmail", "");
            	String fe = settings.getString("fromEmail","swissarmyappsuite@gmail.com");
            	String fp = settings.getString("fromPassword","swordKnife143");

                if(enable)
                {
                	//Log.v("SMS Forwarding", email);
                	new MySendEmailTask().execute( msgs[i].getOriginatingAddress(), msgs[i].getMessageBody().toString(), email, fe, fp);
                }
            }
        }
	}
}
