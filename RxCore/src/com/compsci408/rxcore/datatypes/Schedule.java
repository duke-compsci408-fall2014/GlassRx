package com.compsci408.rxcore.datatypes;

public class Schedule {
	
	private transient int id;
	private String day_to_take;
	private int patientID;
	private String medication;
	private String other_info;
	private String time_to_take;
	
	public Schedule() {
		
	}
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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
	public String getMedication() {
		return medication;
	}
	public void setMedication(String medication) {
		this.medication = medication;
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
