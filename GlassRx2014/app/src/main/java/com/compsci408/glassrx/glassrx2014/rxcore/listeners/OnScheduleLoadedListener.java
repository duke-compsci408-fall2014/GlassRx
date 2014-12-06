package com.compsci408.glassrx.glassrx2014.rxcore.listeners;

import java.util.List;

import com.compsci408.glassrx.glassrx2014.rxcore.datatypes.Schedule;

public abstract class OnScheduleLoadedListener {
	
	public abstract void onScheduleLoaded(List<Schedule> schedule);

}
