package com.compsci408.rxcore.alarms;

import com.compsci408.rxcore.datatypes.Day;

import android.net.Uri;

public class Alarm {
	
	public long mId = -1;
	public int mTimeHour;
	public int mTimeMinute;
	private boolean mRepeatingDays[];
	public boolean mRepeatWeekly;
	public Uri mAlarmTone;
	public String mName;
	public boolean mIsEnabled;
	
	public Alarm() {
		mRepeatingDays = new boolean[7];
	}
	
	public void setRepeatingDay(Day dayOfWeek, boolean value) {
		mRepeatingDays[dayOfWeek.getId()] = value;
	}
	
	public boolean getRepeatingDay(int dayOfWeek) {
		return mRepeatingDays[dayOfWeek];
	}
	
}