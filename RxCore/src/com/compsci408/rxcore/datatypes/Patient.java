package com.compsci408.rxcore.datatypes;

/**
 * Class describing the attributes of a patient.
 * Includes name, pharmacy, login credentials, ID,
 * and ID of associated provider.
 * @author Evan
 *
 */
public class Patient {
	
	private String name;
	private String pharmacy;
	private String drug_allergies;
	private String login;
	private String password;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
