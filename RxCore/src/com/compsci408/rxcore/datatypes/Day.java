package com.compsci408.rxcore.datatypes;


/**
 * Enum for defining days of the week
 * @author Evan
 */
public enum Day {
	SUNDAY(1, "Sunday"),
	MONDAY(2, "Monday"),
	TUESDAY(3, "Tuesday"),
	WEDNESDAY(4, "Wednesday"),
    THURSDAY(5, "Thursday"),
    FRIDAY(6, "Friday"),
    SATURDAY(7, "Saturday"); 
    
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
