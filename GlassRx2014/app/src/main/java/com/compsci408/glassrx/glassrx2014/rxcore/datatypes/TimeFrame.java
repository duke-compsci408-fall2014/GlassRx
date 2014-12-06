package com.compsci408.glassrx.glassrx2014.rxcore.datatypes;

public enum TimeFrame {
	
	MORNING(0, "Morning", 5, 10),
	AFTERNOON(1, "Afternoon", 10, 16),
	EVENING(2, "Evening", 16, 23);
	
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

	public void setmEndHour(int endHour) {
		this.mEndHour = endHour;
	}

}
