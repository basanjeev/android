package net.redirectme.apps;

import android.app.Activity;
import android.view.View;
import android.widget.RatingBar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TabHost.TabSpec;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.CheckBox;
import android.os.Bundle;

public class SessionDetailActivity extends Activity{

	private ConferenceDataSource datasource;
	SessionModel model;
	long id = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();

		Log.w("SessionDetail", "Launching Session Detail.");


		if(extras==null)
			return;

		id = extras.getLong(android.content.Intent.EXTRA_TEXT);
		datasource = new ConferenceDataSource(this);
		datasource.open();
		model = datasource.getSessionDetail(id);
		setContentView(R.layout.listitemdetail);

		TextView name = (TextView)findViewById(R.id.name);
		TextView author = (TextView)findViewById(R.id.author);
		TextView speaker = (TextView)findViewById(R.id.speaker);
		TextView sporg = (TextView)findViewById(R.id.sporg);
		TextView organization = (TextView)findViewById(R.id.organization);
		TextView date = (TextView)findViewById(R.id.date);
		TextView time = (TextView)findViewById(R.id.time);
		TextView location = (TextView)findViewById(R.id.location);
		TextView email = (TextView)findViewById(R.id.email);
		TextView phone= (TextView)findViewById(R.id.phone);

		RatingBar rating = (RatingBar)findViewById(R.id.ratingBar1);
		TextView comments = (TextView)findViewById(R.id.comments);

		TabHost host = (TabHost)findViewById(android.R.id.tabhost);
		host.setup();

		TabSpec tab1 = host.newTabSpec("Summary");
		tab1.setIndicator("Summary");

		TabSpec tab2 = host.newTabSpec("Feedback");
		tab2.setIndicator("Feedback");

		TabSpec tab3 = host.newTabSpec("Speaker(s)");
		tab3.setIndicator("Speaker(s)");

		tab1.setContent(R.id.summary_content);
		host.addTab(tab1);
		tab2.setContent(R.id.feedback_content);
		host.addTab(tab2);
		tab3.setContent(R.id.speaker_content);
		host.addTab(tab3);

		TextView summary = (TextView)findViewById(R.id.summary);
		summary.setMovementMethod(new ScrollingMovementMethod());

		CheckBox mysession = (CheckBox)findViewById(R.id.mysession);
		ToggleAttendState checkState = new ToggleAttendState(datasource, id); 
		mysession.setOnCheckedChangeListener(checkState);

		name.setText(model.getName());
		author.setText(model.getAuthor());
		speaker.setText(model.getAuthor());
		sporg.setText(model.getOrganization());
		email.setText(model.getEmail());
		organization.setText(model.getOrganization());
		date.setText(model.getDate());
		time.setText(model.getStarttime() + "-" + model.getClosetime());
		location.setText(model.getLocation());
		summary.setText(model.getSummary());
		String remind = model.getRemind();
		if(remind.equals("Y"))
			mysession.setChecked(true);
		else
			mysession.setChecked(false);
		phone.setText(model.getPhone());

		float f = Float.parseFloat(model.getRating());
		rating.setRating(f);

		comments.setText(model.getComments());
	}

	public void saveFeedback(View view) {
		RatingBar rating = (RatingBar)findViewById(R.id.ratingBar1);
		TextView comments = (TextView)findViewById(R.id.comments);
		model.setComments(comments.getText().toString());
		model.setRating(Float.toString(rating.getRating()));
		datasource.updateSession(id, model);
	}	
}


class ToggleAttendState implements OnCheckedChangeListener
{
	long id;
	ConferenceDataSource datasource;
	ToggleAttendState(ConferenceDataSource datasource, long id)
	{
		this.id = id;
		this.datasource = datasource;
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
		SessionModel sm = new SessionModel();
		if(isChecked){
			sm.setRemind("Y");
		}else{
			sm.setRemind("N");
		}
		datasource.updateSession(id, sm);
	}
}