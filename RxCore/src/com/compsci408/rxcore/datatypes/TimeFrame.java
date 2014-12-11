package com.compsci408.rxcore.datatypes;

/**
 * Enum of time frames.  These time frames
 * are used when setting alerts to allow for
 * flexibility in scheduling.  Each describes
 * a time of day (e.g. Morning, Evening) and has
 * an associated range of acceptable times.
 * @author Evan
 */
public enum TimeFrame {
	
	MORNING(0, "Morning", 5, 10),
	AFTERNOON(1, "Afternoon", 10, 16),
	EVENING(2, "Evening", 16, 23),
	ANYTIME(3, "Anytime", 0, 25);
	
	private int mId;
	private String mName;
	private int mStartHour;
	private int mEndHour;
	
	private TimeFrame(int id, String name, int startHour, int endHour) {
		mId = id;
		mName = name;
		mStartHour = startHour;
		mEndHour = endHour;
	}

	public int getId() {
		return mId;
	}

	public void setmId(int Id) {
		this.mId = Id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public int getStartHour() {
		return mStartHour;
	}

	public void setStartHour(int startHour) {
		this.mStartHour = startHour;
	}

	public int getEndHour() {
		return mEndHour;
	}

	public void setEndHour(int endHour) {
		this.mEndHour = endHour;
	}

}
