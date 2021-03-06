package com.compsci408.glassrx.glassrx2014.rxcore.datatypes;

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
