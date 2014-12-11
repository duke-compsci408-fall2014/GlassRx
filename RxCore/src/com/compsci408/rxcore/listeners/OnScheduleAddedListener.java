package com.compsci408.rxcore.listeners;

public abstract class OnScheduleAddedListener {

	/**
	 * Callback function for describing behavior
	 * upon successful addition of schedule in database.
	 * @param success True if schedule added, false otherwise
	 */
	public abstract void onScheduleAdded(boolean success);
}
