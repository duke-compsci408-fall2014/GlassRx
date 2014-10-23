package com.compsci408.rxcore.datatypes;


/**
 * Enum for defining days of the week
 * @author Evan
 */
public enum Day {
	SUNDAY(0, "Sunday"),
	MONDAY(1, "Monday"),
	TUESDAY(2, "Tuesday"),
	WEDNESDAY(3, "Wednesday"),
    THURSDAY(4, "Thursday"),
    FRIDAY(5, "Friday"),
    SATURDAY(6, "Saturday"); 
    
    private final int mId;
    private final String mName;
	Day(int id, String name) {
		mId = id;
		mName = name;
	}
	
	public int getId() {
		return mId;
	}
	
	public String getName() {
		return mName;
	}
}
