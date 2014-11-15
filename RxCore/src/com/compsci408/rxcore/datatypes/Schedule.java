package com.compsci408.rxcore.datatypes;

public class Schedule {
	
	private String day_to_take;
	private int patientID;
	private String medicine;
	private String other_info;
	private String time_to_take;
	
	public Schedule() {
		
	}
	
	
	public String getDay_to_take() {
		return day_to_take;
	}
	public void setDay_to_take(String day_to_take) {
		this.day_to_take = day_to_take;
	}
	public int getPatientID() {
		return patientID;
	}
	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}
	public String getMedicine() {
		return medicine;
	}
	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	public String getOther_info() {
		return other_info;
	}
	public void setOther_info(String other_info) {
		this.other_info = other_info;
	}
	public String getTime_to_take() {
		return time_to_take;
	}
	public void setTime_to_take(String time_to_take) {
		this.time_to_take = time_to_take;
	}

}
