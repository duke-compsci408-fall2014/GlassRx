package com.compsci408.rxcore.datatypes;

public class Prescription extends Event {
	
	private int general_time;
	private boolean set;
	
	public Prescription() {
		
	}
	
	public int getGeneral_time() {
		return general_time;
	}
	public void setGeneral_time(int general_time) {
		this.general_time = general_time;
	}
	public boolean getSet() {
		return set;
	}
	public void setSet(boolean set) {
		this.set = set;
	}
	

	/**
	 * Display prescription details as a readable string
	 * @param showName Whether or not to display the medication name
	 * @param dateFormat format in which date should be represented,
	 * e.g. 'EEE, MMM dd, yyyy'
	 * @return
	 */
	@Override
	public String toFormattedString(boolean showName, String dateFormat) {
		String time = null, date = getDay_to_take(), result = "";
		
		for (TimeFrame tf : TimeFrame.values()) {
			if (tf.getId() == getGeneral_time()) {
				time = tf.getName();
			}
		}
		
		if (showName) {
			result += getMedication() + ":  ";
		}
		
		
		date = formatDate(getDay_to_take(), dateFormat);
		
		result += date + " in the " + time + 
				"(" + Integer.toString(getDose()) + " " + getDose_description() + ")";
		return result;
	}

	
	

}
