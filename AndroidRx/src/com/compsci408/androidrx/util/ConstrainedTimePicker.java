package com.compsci408.androidrx.util;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Calendar;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Custom {@link TimePickerDialog} which bounds the
 * permissable times which can be set.
 * @author Evan
 */
public class ConstrainedTimePicker extends TimePickerDialog {
	private int minHour = -1;
	private int minMinute = -1;

	private int maxHour = 25;
	private int maxMinute = 25;

	private int currentHour = 0;
	private int currentMinute = 0;

	private Calendar calendar = Calendar.getInstance();
	private DateFormat dateFormat;
	
//	private int dayOfWeek;

	public ConstrainedTimePicker(Context context, OnTimeSetListener callBack, int hourOfDay, 
			int minute, boolean is24HourView, int dayOfWeek) {
	    super(context, callBack, hourOfDay, minute, is24HourView);
	    currentHour = hourOfDay;
	    currentMinute = minute;
	    dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
//	    this.dayOfWeek = dayOfWeek;

	    try {
	        Class<?> superclass = getClass().getSuperclass();
	        Field mTimePickerField = superclass.getDeclaredField("mTimePicker");
	        mTimePickerField.setAccessible(true);
	        TimePicker mTimePicker = (TimePicker) mTimePickerField.get(this);
	        mTimePicker.setOnTimeChangedListener(this);
	    } catch (NoSuchFieldException e) {
	    } catch (IllegalArgumentException e) {
	    } catch (IllegalAccessException e) {
	    }
	}

	/**
	 * Set the minimum time which can be set by this dialog
	 * @param hour Hour of earliest time
	 * @param minute  Minute of earliest time
	 */
	public void setMin(int hour, int minute) {
	    minHour = hour;
	    minMinute = minute;
	}

	/**
	 * Set the maximum time which can be set by this dialog
	 * @param hour Hour of latest time
	 * @param minute  Minute of latest time
	 */
	public void setMax(int hour, int minute) {
	    maxHour = hour;
	    maxMinute = minute;
	}
	
//	public void setDayOfWeek(int day) {
//		dayOfWeek = day;
//	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

	    boolean validTime = true;
	    if (hourOfDay < minHour || (hourOfDay == minHour && minute < minMinute)){
	    	Toast.makeText(getContext(), "Time cannot be earlier than this", Toast.LENGTH_SHORT).show();
	        validTime = false;
	    }

	    if (hourOfDay  > maxHour || (hourOfDay == maxHour && minute > maxMinute)){
	    	Toast.makeText(getContext(), "Time cannot be later than this", Toast.LENGTH_SHORT).show();
	        validTime = false;
	    }

	    if (validTime) {
	        currentHour = hourOfDay;
	        currentMinute = minute;
	    }

	    updateTime(currentHour, currentMinute);
	    updateDialogTitle(view, currentHour, currentMinute);
	}

	private void updateDialogTitle(TimePicker timePicker, int hourOfDay, int minute) {
	    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
	    calendar.set(Calendar.MINUTE, minute);
	    String title = dateFormat.format(calendar.getTime());
	    setTitle(title);
	}
}
