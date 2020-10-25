package net.redirectme.apps;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ConferenceDataSource {

	// Database fields
	private SQLiteDatabase database;
	private ConferenceDBHelper dbHelper;
	Context context;
	private String[] allColumns = { 	
			ConferenceDBHelper.COLUMN_ID,
			ConferenceDBHelper.COLUMN_NAME,
			ConferenceDBHelper.COLUMN_AUTHOR,
			ConferenceDBHelper.COLUMN_ORG,
			ConferenceDBHelper.COLUMN_TRACK,
			ConferenceDBHelper.COLUMN_LOCATION,
			ConferenceDBHelper.COLUMN_DATE,
			ConferenceDBHelper.COLUMN_STARTTIME,
			ConferenceDBHelper.COLUMN_CLOSETIME,
			ConferenceDBHelper.COLUMN_SUMMARY,
			ConferenceDBHelper.COLUMN_COMMENTS,			  						
			ConferenceDBHelper.COLUMN_MEDIA,
			ConferenceDBHelper.COLUMN_TAG,
			ConferenceDBHelper.COLUMN_RATING,
			ConferenceDBHelper.COLUMN_PHONE,
			ConferenceDBHelper.COLUMN_EMAIL,
			ConferenceDBHelper.COLUMN_REMIND,
	};

	public ConferenceDataSource(Context context) {
		dbHelper = new ConferenceDBHelper(context);
		dbHelper.createDataBase();
		this.context = context;
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	//	public SessionModel createConferenceDetail(
	//			String  name,
	//			String  author,
	//			String  organization,
	//			String  track,
	//			String  location,
	//			String  date,
	//			String  starttime,
	//			String  closetime,
	//			String  summary,
	//			String  comments,			
	//			String  media,
	//			String  tag,
	//			String  rating,
	//			String  phone,
	//			String  email,
	//			String  remind)
	//	{
	//		ContentValues values = new ContentValues();
	//		values.put(ConferenceDBHelper.COLUMN_NAME, name);
	//		values.put(ConferenceDBHelper.COLUMN_AUTHOR, author);
	//		values.put(ConferenceDBHelper.COLUMN_ORG, organization);
	//		values.put(ConferenceDBHelper.COLUMN_TRACK, track);
	//		values.put(ConferenceDBHelper.COLUMN_LOCATION, location);
	//		values.put(ConferenceDBHelper.COLUMN_DATE, date);
	//		values.put(ConferenceDBHelper.COLUMN_STARTTIME, starttime);
	//		values.put(ConferenceDBHelper.COLUMN_CLOSETIME, closetime);
	//		values.put(ConferenceDBHelper.COLUMN_SUMMARY, summary);
	//		values.put(ConferenceDBHelper.COLUMN_COMMENTS, comments);
	//		values.put(ConferenceDBHelper.COLUMN_MEDIA, media);
	//		values.put(ConferenceDBHelper.COLUMN_TAG, tag);
	//		values.put(ConferenceDBHelper.COLUMN_RATING, rating);
	//		values.put(ConferenceDBHelper.COLUMN_PHONE, phone);
	//		values.put(ConferenceDBHelper.COLUMN_EMAIL, email);
	//		values.put(ConferenceDBHelper.COLUMN_REMIND, remind);
	//
	//		long insertId = database.insert(ConferenceDBHelper.TABLE_SESSIONS, null,
	//				values);
	//		Cursor cursor = database.query(ConferenceDBHelper.TABLE_SESSIONS,
	//				allColumns, ConferenceDBHelper.COLUMN_ID + " = " + insertId, null,
	//				null, null, null);
	//		cursor.moveToFirst();
	//		SessionModel newConferenceModel = cursorToConferenceDetail(cursor);
	//		cursor.close();
	//		return newConferenceModel;
	//	}

	public void deleteConferenceDetail(SessionModel detail) {
		long id = detail.getId();
		System.out.println("ConferenceModel deleted with id: " + id);
		database.delete(ConferenceDBHelper.TABLE_SESSIONS, ConferenceDBHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<SessionModel> getSessionDetails(int query, String searchText) {
		List<SessionModel> details = new ArrayList<SessionModel>();

		String selection = null;
		String[] selectionArgs = null;
		String groupBy = null; 
		String having = null;
		String orderBy = null;


		if(searchText!=null && searchText.length()>0)
		{
			Log.w("Test Message", "Adding a Search Criteria");
			selection = " like '%" + searchText + "%' COLLATE NOCASE";

			switch(query)
			{
			case SessionDBActivity.TRACKS:
				selection = ConferenceDBHelper.COLUMN_TRACK + selection;
				break;
			case SessionDBActivity.LOCATIONS:
				selection = ConferenceDBHelper.COLUMN_LOCATION + selection;
				break;				
			case SessionDBActivity.TIME:
				selection = ConferenceDBHelper.COLUMN_STARTTIME + selection
							+ " OR "
							+ ConferenceDBHelper.COLUMN_CLOSETIME + selection
							+ " OR "
							+ ConferenceDBHelper.COLUMN_DATE + selection;
				break;				
			case SessionDBActivity.SPEAKERS:
				selection = ConferenceDBHelper.COLUMN_AUTHOR + selection;
				break;
			case SessionDBActivity.MY_SESSIONS:
				selection = ConferenceDBHelper.COLUMN_NAME + selection;
				break;
			default:
				selection = ConferenceDBHelper.COLUMN_NAME + selection;
				break;
			}

		}
		switch(query)
		{
		case SessionDBActivity.MY_SESSIONS:
		{
			String temp = ConferenceDBHelper.COLUMN_REMIND + "='Y'";
			if(selection!=null && selection.length()>0)
			{
				selection = temp + " AND " + selection;
			}else{
				selection = temp;
			}
		}

		break;
		case SessionDBActivity.TRACKS:
			orderBy = ConferenceDBHelper.COLUMN_TRACK; 
			break;
		case SessionDBActivity.LOCATIONS:
			orderBy = ConferenceDBHelper.COLUMN_LOCATION;
			break;				
		case SessionDBActivity.TIME:
			orderBy = ConferenceDBHelper.COLUMN_DATE + "," + ConferenceDBHelper.COLUMN_STARTTIME;
			break;				
		case SessionDBActivity.SPEAKERS:
			orderBy = ConferenceDBHelper.COLUMN_AUTHOR;
			break;				
		case SessionDBActivity.TOPICS:
			orderBy = ConferenceDBHelper.COLUMN_NAME;
			break;
		default:
			selection = null;
			orderBy = null;
			break;
		}
		Log.w("Test Message", selection + "-" + orderBy);

		Cursor cursor = database.query(ConferenceDBHelper.TABLE_SESSIONS,
				allColumns, selection, selectionArgs, groupBy, having, orderBy);

		if(cursor.getCount()==0)
		{
			Intent intent = new Intent("net.redirectme.apps.SESSION_VIEW_SEARCH_EMPTY");
			context.sendBroadcast(intent);
			cursor = database.query(ConferenceDBHelper.TABLE_SESSIONS,
					allColumns, null, null, null, null, null);
		}

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			SessionModel detail = cursorToConferenceDetail(cursor);
			details.add(detail);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return details;
	}

	public SessionModel getSessionDetail(long id){
		SessionModel model = null;
		Cursor cursor = database.rawQuery("select * from " + ConferenceDBHelper.TABLE_SESSIONS
				+ " where _id = " + id, null );
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			model = cursorToConferenceDetail(cursor);
			break;
		}
		cursor.close();
		return model;
	}

	public void updateSession(long id, SessionModel model){
		ContentValues cv = new ContentValues();
		if(model.getRemind()!=null)
			cv.put("remind", model.getRemind());
		if(model.getRemind()!=null)
			cv.put("comments", model.getComments());
		if(model.getRemind()!=null)
			cv.put("rating", model.getRating());
		database.update(ConferenceDBHelper.TABLE_SESSIONS, cv, "_id = " + id, null);
	}

	public void updateSessionComments(long id, String val){
		ContentValues cv = new ContentValues();
		cv.put("comments", val);
		database.update(ConferenceDBHelper.TABLE_SESSIONS, cv, "_id = " + id, null);
	}

	public void updateSessionRemind(long id, String val){
		ContentValues cv = new ContentValues();
		cv.put("remind", val);
		database.update(ConferenceDBHelper.TABLE_SESSIONS, cv, "_id = " + id, null);
	}

	private SessionModel cursorToConferenceDetail(Cursor cursor) {
		SessionModel detail = new SessionModel();
		detail.setId(cursor.getLong(0));
		detail.setName(cursor.getString(1));
		detail.setAuthor(cursor.getString(2));
		detail.setOrganization(cursor.getString(3));
		detail.setTrack(cursor.getString(4));
		detail.setLocation(cursor.getString(5));
		detail.setDate(cursor.getString(6));
		detail.setStarttime(cursor.getString(7));
		detail.setClosetime(cursor.getString(8));
		detail.setSummary(cursor.getString(9));
		detail.setComments(cursor.getString(10));
		detail.setMedia(cursor.getString(11));
		detail.setTag(cursor.getString(12));
		detail.setRating(cursor.getString(13));
		detail.setPhone(cursor.getString(14));
		detail.setEmail(cursor.getString(15));
		detail.setRemind(cursor.getString(16));
		return detail;
	}
}
