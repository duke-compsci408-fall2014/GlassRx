package com.compsci408.rxcore.listeners;

import java.util.List;

import com.compsci408.rxcore.datatypes.Schedule;

public abstract class OnScheduleLoadedListener {
	
	/**
	 * Callback function for describing behavior
	 * upon successful loading of schedules.
	 * @param schedule List of loaded schedules
	 */
	public abstract void onScheduleLoaded(List<Schedule> schedule);

}
