package com.compsci408.rxcore.alarms;

import com.compsci408.rxcore.datatypes.Day;
import com.compsci408.rxcore.datatypes.Medication;

import android.net.Uri;

public class Alarm {
	
	public long mId;
	public int mTimeHour;
	public int mTimeMinute;
	private boolean mRepeatingDays[];
	public boolean mRepeatWeekly;
	public Uri mAlarmTone;
	public String mName;
	public boolean mIsEnabled;
	private Medication mMedication;
	
	public Alarm() {
		mRepeatingDays = new boolean[7];
	}
	
	public void setRepeatingDay(Day dayOfWeek, boolean value) {
		mRepeatingDays[dayOfWeek.getId()] = value;
	}
	
	public boolean getRepeatingDay(int dayOfWeek) {
		return mRepeatingDays[dayOfWeek];
	}
	
	public long getId() {
		return mId;
	}

	public void setId(long id) {
		this.mId = id;
	}

	public int getTimeHour() {
		return mTimeHour;
	}

	public void setTimeHour(int timeHour) {
		this.mTimeHour = timeHour;
	}

	public int getTimeMinute() {
		return mTimeMinute;
	}

	public void setmTimeMinute(int timeMinute) {
		this.mTimeMinute = timeMinute;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public boolean isIsEnabled() {
		return mIsEnabled;
	}

	public void setIsEnabled(boolean isEnabled) {
		this.mIsEnabled = isEnabled;
	}

	public Medication getMedication() {
		return mMedication;
	}

	public void setMedication(Medication medication) {
		this.mMedication = medication;
	}

	
	
}