package com.compsci408.glassrx.glassrx2014.rxcore.datatypes;

/**
 * Class describing the attributes of a
 * medication object.  Includes names,
 * colors, shapes, and identification.
 * @author Evan
 *
 */
public class Medication {

	private String name;
	private String purpose;
	private String side_effects;
	private int id;
	
	public Medication() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	public String getSide_effects() {
		return side_effects;
	}

	public void setSide_effects(String side_effects) {
		this.side_effects = side_effects;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
