package com.compsci408.rxcore.listeners;

public abstract class OnPictureTakenListener {
	
	/**
	 * Callback function for describing behavior
	 * upon saving of captured image.
	 * @param success True if image saved successfully, false otherwise
	 */
	public abstract void onPictureTaken(boolean success);

}
