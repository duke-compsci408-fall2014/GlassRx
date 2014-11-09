package com.compsci408.rxcore.datatypes;

public class Patient {
	
	private String name;
	private String pharmacy;
	private String drug_allergies;
	private int patientID;
	private int physicianID;
	
	public Patient() {
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(String pharmacy) {
		this.pharmacy = pharmacy;
	}

	public String getDrug_allergies() {
		return drug_allergies;
	}

	public void setDrug_allergies(String drug_allergies) {
		this.drug_allergies = drug_allergies;
	}

	public int getPatientID() {
		return patientID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	public int getPhysicianID() {
		return physicianID;
	}

	public void setPhysicianID(int physicianID) {
		this.physicianID = physicianID;
	}
	
	
}