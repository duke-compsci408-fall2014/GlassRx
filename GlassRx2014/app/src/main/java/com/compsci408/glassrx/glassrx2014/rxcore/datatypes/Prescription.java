package com.compsci408.glassrx.glassrx2014.rxcore.datatypes;

public class Prescription {
	
	private transient int id;
	private int patientID;
	private int dose;
	private String dose_description;
	private String day_to_take;
	private int physicianID;
	private int general_time;
	private String medication;
	private boolean set;
	
	public Prescription() {
		
	}
	
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
	public int getGeneral_time() {
		return general_time;
	}
	public void setGeneral_time(int general_time) {
		this.general_time = general_time;
	}
	public String getMedication() {
		return medication;
	}
	public void setMedication(String medication) {
		this.medication = medication;
	}
	public boolean getSet() {
		return set;
	}
	public void setSet(boolean set) {
		this.set = set;
	}

//	@Override
//	public int compareTo(Object another) {
//		return getDay_to_take().toCharArray() - ((Prescription) another).getDay_to_take().toCharArray();
//	}

}
