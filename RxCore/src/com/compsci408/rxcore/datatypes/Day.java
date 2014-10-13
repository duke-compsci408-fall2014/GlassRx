package com.compsci408.rxcore.datatypes;


/**
 * Enum for defining days of the week
 * @author Evan
 */
public enum Day {
	SUNDAY(0),
	MONDAY(1),
	TUESDAY(2),
	WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6); 
    
    private final int mId;
	Day(int id) {
		mId = id;
	}
	
	public int getId() {
		return mId;
	}
}
