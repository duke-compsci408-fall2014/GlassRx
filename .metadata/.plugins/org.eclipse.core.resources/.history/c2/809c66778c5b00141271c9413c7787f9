package com.compsci408.rxcore.alarms;

import java.util.ArrayList;
import java.util.List;

import com.compsci408.rxcore.database.SQLiteHelper;
import com.compsci408.rxcore.datatypes.Medication;
import com.compsci408.rxcore.datatypes.Shape;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

public class AlarmsDataSource {
	
	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;
	private String[] allColumns = { SQLiteHelper.COLUMN_ID,
			SQLiteHelper.COLUMN_NAME, SQLiteHelper.COLUMN_HOUR,
			SQLiteHelper.COLUMN_MINUTE, SQLiteHelper.COLUMN_DAY,
			SQLiteHelper.COLUMN_MED, SQLiteHelper.COLUMN_ENABLED };
	
	
	public AlarmsDataSource(Context context) {
	    dbHelper = new SQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }
	
	  public void close() {
	    dbHelper.close();
	  }
	  
	  public Alarm createAlarm(int hour, int min, int day,
			  String name) {
		  ContentValues values = new ContentValues();
		  values.put(SQLiteHelper.COLUMN_HOUR, hour);
		  values.put(SQLiteHelper.COLUMN_MINUTE, min);
		  values.put(SQLiteHelper.COLUMN_DAY, day);
		  values.put(SQLiteHelper.COLUMN_NAME, name);
		  long insertId = database.insert(SQLiteHelper.TABLE_ALARMS, null, values);
		  Cursor cursor = database.query(SQLiteHelper.TABLE_ALARMS, allColumns,
				  SQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		  cursor.moveToFirst();
		  Alarm newAlarm = cursorToAlarm(cursor);
		  cursor.close();
		  return newAlarm;
	  }
	  
	  public void deleteAlarm(Alarm alarm) {
		  long id = alarm.getId();
		  database.delete(SQLiteHelper.TABLE_ALARMS, SQLiteHelper.COLUMN_ID
				  + " = " + id, null);
	  }
	  
	  public List<Alarm> getAllAlarms() {
		  List<Alarm> alarms = new ArrayList<Alarm>();
		  
		  Cursor cursor = database.query(SQLiteHelper.TABLE_ALARMS,
			        allColumns, null, null, null, null, null);
	
		  cursor.moveToFirst();
		  while (!cursor.isAfterLast()) {
			  Alarm alarm = cursorToAlarm(cursor);
			  alarms.add(alarm);
		  	cursor.moveToNext();
		  }
		  // make sure to close the cursor
		  cursor.close();
		  return alarms;
	  }
	  
	  private Alarm cursorToAlarm(Cursor cursor) {
		  Alarm alarm = new Alarm();
		  alarm.setId(cursor.getLong(0));
		  alarm.setName(cursor.getString(1));
		  alarm.setTimeHour(cursor.getInt(2));
		  alarm.setmTimeMinute(cursor.getInt(3));
		  alarm.setMedication(new Medication(-1, cursor.getString(4), Color.BLACK, Shape.ROUND.getId()));
		  alarm.setIsEnabled(true);
		  
		  return alarm;
	  }

}
