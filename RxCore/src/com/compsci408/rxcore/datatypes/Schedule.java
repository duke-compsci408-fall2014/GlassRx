package com.compsci408.rxcore.datatypes;

public class Schedule extends Event{
	
//	private transient int id;
//	private String day_to_take;
//	private int patientID;
//	private int physicianID;
//	private String medication;
//	private int dose;
//	private String dose_description;
	private String time_to_take;
	
	public Schedule() {
		
	}
	
	
//	public int getId() {
//		return id;
//	}
//
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//
//	public String getDay_to_take() {
//		return day_to_take;
//	}
//	public void setDay_to_take(String day_to_take) {
//		this.day_to_take = day_to_take;
//	}
//	public int getPatientID() {
//		return patientID;
//	}
//	public void setPatientID(int patientID) {
//		this.patientID = patientID;
//	}
//	public int getPhysicianID() {
//		return physicianID;
//	}
//
//
//	public void setPhysicianID(int physicianID) {
//		this.physicianID = physicianID;
//	}
//
//
//	public String getMedication() {
//		return medication;
//	}
//	public void setMedication(String medication) {
//		this.medication = medication;
//	}
//	public int getDose() {
//		return dose;
//	}
//
//
//	public void setDose(int dose) {
//		this.dose = dose;
//	}
//
//
//	public String getDose_description() {
//		return dose_description;
//	}
//
//
//	public void setDose_description(String dose_description) {
//		this.dose_description = dose_description;
//	}

	public String getTime_to_take() {
		return time_to_take;
	}
	public void setTime_to_take(String time_to_take) {
		this.time_to_take = time_to_take;
	}
	
	/**
	 * Display detail prescriptions as a readable string
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
		
		result += date + " at " + time;
		return result;
	}

}
