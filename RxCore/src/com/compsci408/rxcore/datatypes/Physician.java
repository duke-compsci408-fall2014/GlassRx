package com.compsci408.rxcore.datatypes;

public class Physician {
	
	private String name;
	private int physicianID;
	private String hospital;
	
	public Physician() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhysicianID() {
		return physicianID;
	}

	public void setPhysicianID(int physicianID) {
		this.physicianID = physicianID;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

}
