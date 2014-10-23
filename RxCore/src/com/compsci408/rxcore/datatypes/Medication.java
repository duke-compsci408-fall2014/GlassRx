package com.compsci408.rxcore.datatypes;

/**
 * Class describing the attributes of a
 * medication object.  Includes names,
 * colors, shapes, and identification.
 * @author Evan
 *
 */
public class Medication {

	private int mId;
	private String mName;
	private int mColor;
	private int mShape;
	
	private String mNickname;	//  Unique name each patient can apply
	
	public Medication(int id, String name, int color, int shape) {
		super();
		setId(id);
		setName(name);
		setColor(color);
		setShape(shape);
	}

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		this.mId = mId;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public int getColor() {
		return mColor;
	}

	public void setColor(int mColor) {
		this.mColor = mColor;
	}

	public int getShape() {
		return mShape;
	}

	public void setShape(int mShape) {
		this.mShape = mShape;
	}

	public String getNickname() {
		return mNickname;
	}

	public void setNickname(String mNickname) {
		this.mNickname = mNickname;
	}
	
}
