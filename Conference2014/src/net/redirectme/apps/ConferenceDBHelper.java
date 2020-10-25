package net.redirectme.apps;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class ConferenceDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "tdc.db";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_SESSIONS = "sessions";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_AUTHOR = "author";
	public static final String COLUMN_ORG = "organization";
	public static final String COLUMN_TRACK = "track";
	public static final String COLUMN_LOCATION = "location";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_STARTTIME = "starttime";
	public static final String COLUMN_CLOSETIME = "closetime";
	public static final String COLUMN_SUMMARY = "summary";
	public static final String COLUMN_COMMENTS = "comments";
	public static final String COLUMN_MEDIA = "media";
	public static final String COLUMN_TAG = "tag";
	public static final String COLUMN_RATING = "rating";
	public static final String COLUMN_PHONE = "phone";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_REMIND = "remind";

	private final Context myContext;
	private SQLiteDatabase myDataBase; 
	String pkgname;
	String DBPATH;
	String myPath;


	public ConferenceDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;
		pkgname = myContext.getPackageName();
		DBPATH = context.getFilesDir().getParentFile().getPath() + "/databases/";
		myPath = DBPATH + DATABASE_NAME;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own database.
	 * */
	public void createDataBase(){

		boolean dbExist = checkDataBase();

		if(!dbExist){
			//By calling this method and empty database will be created into the default system path
			//of your application so we are gonna be able to overwrite that database with our database.
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				// If DB Fails - App Fails
				// TODO show error and exit.
			}
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each time you open the application.
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase(){

		SQLiteDatabase checkDB = null;
		try{
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		}catch(SQLiteException e){
			return false;
		}
		if(checkDB != null){
			checkDB.close();
			return true;
		}
		return false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created empty database in the
	 * system folder, from where it can be accessed and handled.
	 * This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException{

		//Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = DBPATH + DATABASE_NAME;

		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer))>0){
			myOutput.write(buffer, 0, length);
		}

		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException{
		//Open the database
		// Path to the just created empty db
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}

	@Override
	public synchronized void close() {
		if(myDataBase != null)
			myDataBase.close();
		super.close();
	}
}
