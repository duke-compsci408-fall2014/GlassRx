package com.compsci408.rxcore.listeners;

public abstract class OnDataUpdatedListener {

	/**
	 * Callback function for describing behavior
	 * upon completed database update.
	 * @param success  True if update was successful, false otherwise
	 */
	public abstract void onDataUpdated(boolean success);
}
