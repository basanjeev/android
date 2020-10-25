package net.redirectme.inspire;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;


public class MainActivity extends Activity {

	public static final String PREFS_NAME = "net.redirectme.inspire.SMSForwarderPreferences";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean enable = settings.getBoolean("enableService", false);
        String  email  = settings.getString("forwardEmail", "");
        final TextView t=(TextView)findViewById(R.id.editText1);
        final CheckBox c=(CheckBox)findViewById(R.id.checkBox1);
        c.setChecked(enable);
        
        AccountManager accountManager = AccountManager.get(getApplicationContext());
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if(email.length()>0)
        {
        	t.setText(email);
        }else if(accounts.length>0){
        	account = accounts[0];
	        t.setText(account.name);
        }else{
        	t.setText("Set Email Address.");
        }
       
        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            	SharedPreferences.Editor editor = settings.edit();
            	editor.putBoolean("enableService", c.isChecked());
            	editor.putString("forwardEmail", t.getText().toString());
            	editor.commit();
            	finish();
            	
            }
        });
    }
    
    public void onSendClicked(View view) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sm, menu);
        return true;
    }
    
}

