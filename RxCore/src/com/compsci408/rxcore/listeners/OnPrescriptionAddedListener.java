package com.compsci408.rxcore.listeners;

public abstract class OnPrescriptionAddedListener {
	
	/**
	 * Callback function for describing behavior
	 * upon successful addition of prescription in database.
	 * @param success True if prescription added, false otherwise
	 */
	public abstract void onPrescriptionAdded(boolean success);

}
