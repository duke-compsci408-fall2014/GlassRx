package com.compsci408.rxcore;

/**
 * Holds any static field names and values used across the application
 * @author Evan
 */
public class Constants {
	
	
	
	//  Login Info
	public static final String KEY_LOGIN				= "Login";
	public static final String KEY_ACCOUNT_TYPE			= "AccountType";
	public static final String KEY_LAST_ACTIVITY		= "LastActivity";
	public static final String KEY_LAST_USER			= "LastUser";
	
	public static final int DEFAULT_VALUE				= -1;
	public static final int LOGGED_IN					= 1;
	
	public static final int LOGIN_TIMEOUT				= 3600000;  //  Logout after 1 hour
	
	//  Alarm fields
	public static final String ALARM_ID 				= "id";
	public static final String ALARM_NAME 				= "name";
	public static final String ALARM_TIME_HOUR 			= "timeHour";
	public static final String ALARM_TIME_MINUTE 		= "timeMinute";
	public static final String ALARM_TONE 				= "alarmTone";
	
	// Medication Fields
	public static final String MED_ID					= "id";
	public static final String MED_NAME					= "name";
	public static final String MED_NICKNAME				= "nickname";
	public static final String MED_DOSE_QUANT			= "dose";
	public static final String MED_DOSE_DESCR			= "dose_description";
	
	//  Table Headers
	public static final String USERNAME					= "username";
	public static final String PASSWORD					= "password";
	public static final String ACCOUNT_TYPE				= "account_type";
	
	//  Account Types
	public static final String ACCOUNT_PATIENT			= "patient";
	public static final String ACCOUNT_PROVIDER			= "provider";
	
	//  Local directory
	public static final String RX_DIRECTORY				= "";
	
	//  Network connection constants
	public static final int READ_TIMEOUT				= 10000;
	public static final int CONNECT_TIMEOUT				= 15000;
	public static final int POLLING_INTERVAL			= 1000 * 60 * 10;  //  Ten minutes
	
	public static final String GET						= "GET";
	public static final String POST						= "POST";
	
	public static final String RESPONSE_SUCCESS			= "success";
	public static final String RESPONSE_TIMEOUT			= "timeout";
	
	public static final String DATE_FORMAT_DATABASE		= "yyyy-MM-dd";
	public static final String DATE_FORMAT_READABLE		= "EEE, MMM dd, yyyy";
	
	
	
	//TODO:  add appropriate URLs
	// --------------------------------------URLs---------------------------------------------------
	//  Suffix of all URLs
	public static final String URL_SUFFIX 				= "&app_name=glass-rx";
	
	//  Log in
	public static final String URL_LOG_IN_PATIENT		= "https://dsp-glass-rx-duke.cloud.dreamfactory.com/rest/db/Patient?filter=login%3D%27";
	public static final String URL_LOG_IN_PROVIDER		= "https://dsp-glass-rx-duke.cloud.dreamfactory.com/rest/db/Physician?filter=login%3D%27";

	//  Patients
	public static final String URL_GET_PATIENTS			= "https://dsp-glass-rx-duke.cloud.dreamfactory.com/rest/db/Patient?filter=physicianID%3D";
	
	//  Medications
	public static final String URL_GET_MED_INFO			= "https://dsp-glass-rx-duke.cloud.dreamfactory.com/rest/db/Medication?filter=name%3D";
	public static final String URL_FILTER_MEDS_BY_NAME	= "https://dsp-glass-rx-duke.cloud.dreamfactory.com/rest/db/Medication?filter=name%20like%20%27";
	public static final String URL_GET_MED_BY_NAME		= "https://dsp-glass-rx-duke.cloud.dreamfactory.com/rest/db/Medication?filter=name%20%3D%20%27";
	
	// Prescription/Schedule
	public static final String URL_ADD_PRESCRIPTION		= "https://dsp-glass-rx-duke.cloud.dreamfactory.com:443/rest/db/Prescription";
	public static final String URL_ADD_SCHEDULE			= "https://dsp-glass-rx-duke.cloud.dreamfactory.com:443/rest/db/Schedule";
	public static final String URL_GET_PATIENT_SCHEDULE	= "https://dsp-glass-rx-duke.cloud.dreamfactory.com/rest/db/Schedule?filter=patientID%3D%27";
	public static final String URL_GET_PATIENT_PRESCRIP	= "https://dsp-glass-rx-duke.cloud.dreamfactory.com/rest/db/Prescription?filter=patientID%3D%27";
	
	// Images
	public static final String URL_UPLOAD_IMAGE			= "";
	
	
	

}
