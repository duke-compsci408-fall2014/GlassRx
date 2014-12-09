package com.compsci408.androidrx.util;

import java.util.List;

import com.compsci408.rxcore.datatypes.Event;

public abstract class OnCalendarClickedCallback {
	
	public abstract void onCalendarClicked(List<Event> selectedDayEvents);

}
