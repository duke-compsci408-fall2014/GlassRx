package com.compsci408.rxcore.datatypes;

/**
 * Enum of account types.  These types
 * are currently limited to patient and provider.
 * Used to distinguish between types of users and
 * to differentiate what is shown to each. 
 * @author Evan
 */
public enum AccountType {
	
	PATIENT(0, "Patient"),
	PROVIDER(1, "Provider");

	private final int mId;
	private final String mName;
	
	AccountType(int id, String name) {
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
