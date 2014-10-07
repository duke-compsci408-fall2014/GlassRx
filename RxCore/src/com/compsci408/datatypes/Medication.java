package com.compsci408.datatypes;

import java.util.ArrayList;
import java.util.List;

import com.compsci408.alarms.Alarm;

/**
 * Class describing the attributes of a
 * medication object.  Includes names,
 * colors, shapes, and associated alarms.
 * @author Evan
 *
 */
public class Medication {

	private int mId;
	private String mName;
	private int mColor;
	private int mShape;
	
	private String mNickname;	//  Unique name each patient can apply
	private List<Alarm> mAlarms;  //  Unique alarm schedule for each patient
	
	public Medication(int id, String name, int color, int shape) {
		super();
		setId(id);
		setName(name);
		setColor(color);
		setShape(shape);
		mAlarms = new ArrayList<Alarm>();
	}

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		mId = mId;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		mName = mName;
	}

	public int getColor() {
		return mColor;
	}

	public void setColor(int mColor) {
		mColor = mColor;
	}

	public int getShape() {
		return mShape;
	}

	public void setShape(int mShape) {
		mShape = mShape;
	}

	public String getNickname() {
		return mNickname;
	}

	public void setNickname(String mNickname) {
		this.mNickname = mNickname;
	}
	
	public void addAlarm(Alarm a) {
		if (mAlarms != null) {
			mAlarms.add(a);
		}
	}
	
}
