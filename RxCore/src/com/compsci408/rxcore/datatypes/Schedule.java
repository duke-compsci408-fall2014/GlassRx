package com.compsci408.rxcore.datatypes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Schedule extends Event{
	
	private String time_to_take;
	
	public Schedule() {
		
	}
	public String getTime_to_take() {
		return time_to_take;
	}
	public void setTime_to_take(String time_to_take) {
		this.time_to_take = time_to_take;
	}
	
	/**
	 * Display schedule details as a readable string
	 * @param showName Whether or not to display the medication name
	 * @param dateFormat format in which date should be represented,
	 * e.g. 'EEE, MMM dd, yyyy'
	 * @return
	 */
	@Override
	public String toFormattedString(boolean showName, String dateFormat) {
		String time = getTime_to_take(), date = getDay_to_take(), result = "";
		
		if (showName) {
			result += getMedication() + ":  ";
		}
		
		
		date = formatDate(getDay_to_take(), dateFormat);
		
		result += date + " at " + formatTime(time) + 
				" (" + Integer.toString(getDose()) + " " + getDose_description() + ")";
		return result;
	}
	

	/**
	 * Given a 24-hour formatted time, return the 12-hour format
	 * @param time 24-hour time string
	 * @return 12-hour time string
	 */
	private static String formatTime(String time) {
		try {
		    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.US);
		    final Date dateObj = sdf.parse(time);
		    return new SimpleDateFormat("K:mm a", Locale.US).format(dateObj);
		} catch (final ParseException e) {
		    e.printStackTrace();
		}
		return time;
		
	}
		
}
