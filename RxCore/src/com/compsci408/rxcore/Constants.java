package com.compsci408.rxcore;

/**
 * Holds any static field names used across the application
 * @author Evan
 */
public class Constants {
	
	
	//  Alarm fields
	public static final String ID 						= "id";
	public static final String NAME 					= "name";
	public static final String TIME_HOUR 				= "timeHour";
	public static final String TIME_MINUTE 				= "timeMinute";
	public static final String TONE 					= "alarmTone";
	
	//  Table Headers
	public static final String USERNAME					= "username";
	public static final String PASSWORD					= "password";
	public static final String ACCOUNT_TYPE				= "account_type";
	
	//  Account Types
	public static final String ACCOUNT_PATIENT			= "patient";
	public static final String ACCOUNT_PROVIDER			= "provider";
	
	//  Network connection constants
	public static final int READ_TIMEOUT				= 10000;
	public static final int CONNECT_TIMEOUT				= 15000;
	public static final int POLLING_INTERVAL			= 1000 * 60 * 10;  //  Ten minutes
	
	public static final String GET						= "GET";
	public static final String POST						= "POST";
	
	public static final String RESPONSE_SUCCESS			= "success";
	public static final String RESPONSE_TIMEOUT			= "timeout";
	
	//TODO:  add appropriate URLs
	public static final String URL_LOG_IN				= "";
	public static final String URL_LOG_OUT				= "";
	public static final String URL_ADD_ALARM			= "";
	
	

}