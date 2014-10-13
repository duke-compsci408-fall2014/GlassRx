package com.compsci408.rxcore.datatypes;

/**
 * Enum describing possible pill shapes
 * @author Evan
 */
public enum Shape {
	ROUND(0),
	CAPSULE(1),
	OVAL(2),
	EGG(3),
	BARREL(4),
	RECTANGLE(5),
	USHAPED(6),
	FIGURE8(7),
	HEART(8),
	KIDNEY(9),
	GEAR(10),
	CHARACTER(11);
	
	private final int mId;
	Shape(int id) {
		mId = id;
	}
	public int getId() {
		return mId;
	}
	
	

}
