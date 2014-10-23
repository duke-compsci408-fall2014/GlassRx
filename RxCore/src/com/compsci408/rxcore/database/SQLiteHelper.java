package com.compsci408.rxcore.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_ALARMS = "alarms";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_HOUR = "hour";
	public static final String COLUMN_MINUTE = "minute";
	public static final String COLUMN_DAY = "day";
	public static final String COLUMN_MED = "medication";
	public static final String COLUMN_ENABLED = "enabled";
	
	private static final String DATABASE_NAME = "alarms.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_ALARMS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME
			+ " text not null, " + COLUMN_HOUR
			+ " integer, " + COLUMN_MINUTE
			+ " integer, " + COLUMN_DAY
			+ " integer, " + COLUMN_MED
			+ " text not null, " + COLUMN_ENABLED
			+ " integer" + ");";
	

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
		Log.w(SQLiteHelper.class.getName(), "Upgrading database from version " + oldV + " to "
	            + newV + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMS);
		onCreate(db);
	}

}
