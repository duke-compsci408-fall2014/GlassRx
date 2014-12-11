package com.compsci408.rxcore.datatypes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.compsci408.rxcore.Constants;

/**
 * Abstract class of all medication alert events.
 * Includes all pertinent information about these
 * events, including the id of the recipient patient
 * and id of the provider who assigned it, the dosage,
 * and the date.  Does not include specific time of event.
 * @author Evan
 */
public abstract class Event {

	private int id;
	private int patientID;
	private int dose;
	private String dose_description;
	private String day_to_take;
	private int physicianID;
	private String medication;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPatientID() {
		return patientID;
	}
	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}
	public int getDose() {
		return dose;
	}
	public void setDose(int dose) {
		this.dose = dose;
	}
	public String getDose_description() {
		return dose_description;
	}
	public void setDose_description(String dose_description) {
		this.dose_description = dose_description;
	}
	public String getDay_to_take() {
		return day_to_take;
	}
	public void setDay_to_take(String day_to_take) {
		this.day_to_take = day_to_take;
	}
	public int getPhysicianID() {
		return physicianID;
	}
	public void setPhysicianID(int physicianID) {
		this.physicianID = physicianID;
	}
	public String getMedication() {
		return medication;
	}
	public void setMedication(String medication) {
		this.medication = medication;
	}
	
	/**
	 * Format the Event's details into a string for display to the user
	 * @param showName True if the medication's name should be shown, False otherwise
	 * @param dateFormat  Format in which date should be displayed, e.g. 'YYYY-MM-dd'
	 * @return
	 */
	public abstract String toFormattedString(boolean showName, String dateFormat);
	
	/**
	 * Given a date string as input and a desired format for that date,
	 * reformat the date string and return it
	 * @param input Original date string
	 * @param outFormat String representation format of desired date string
	 * @return Reformatted date string
	 */
	public static String formatDate(String input, String outFormat) {
	    String inputPattern = Constants.DATE_FORMAT_DATABASE;
	    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.US);
	    SimpleDateFormat outputFormat = new SimpleDateFormat(outFormat, Locale.US);

	    Date date = null;
	    String str = null;

	    try {
	        date = inputFormat.parse(input);
	        str = outputFormat.format(date);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return str;
	}

}
