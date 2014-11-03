package com.compsci408.rxcore;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.compsci408.rxcore.alarms.Alarm;
import com.compsci408.rxcore.requests.ResponseCallback;
import com.compsci408.rxcore.requests.ServerRequest;

import android.content.Context;

public class Controller {
	
	public static Controller instance;
	
	private static Context mContext;
	private static ServerRequest mServerRequest;
	
	private String mUsername;
	private String mId;
	
	public static Controller getInstance(Context ctxt) {
		if (instance == null) {
			instance = new Controller();
		}
		mServerRequest = ServerRequest.getInstance(getContext());
		instance.setContext(ctxt);
		return instance;
	}
	
	/**
	 * Log user with given username and password into the system.
	 * @param username Username entered by user
	 * @param password Password entered by user
	 * @param accountType Account type selected (patient or provider)
	 * @param callback ResponseCallback from server
	 * @return Response string from server
	 */
	public void logIn(String username, String password, String accountType, ResponseCallback callback) {
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(Constants.USERNAME, username));
		params.add(new BasicNameValuePair(Constants.PASSWORD, password));
		params.add(new BasicNameValuePair(Constants.ACCOUNT_TYPE, accountType));
		
		setUsername(username);
		
		mServerRequest.doGet(Constants.URL_LOG_IN, callback, params);
	}
	
	/**
	 * Log user out of system
	 * @param username Username entered by user
	 * @return Response string from server
	 */
	public String logOut(String username) {
		//TODO:  Implement log out
		return "";
	}
	
	/**
	 * Get all alarms associated with given patient
	 * @param patientId Id of patient
	 * @return List of {@link Alarm}s for given patient
	 */
	public List<Alarm> getAllAlarms(int patientId) {
		//TODO:  Implement function
		return null;
	}
	
	
	/**
	 * Get alarm which will occur soonest from now
	 * @param alarms List of {@link Alarm}s
	 * @return Next alarm to occur
	 */
	public Alarm getNextAlarm(List<Alarm> alarms) {
		//TODO:  Implement function
		return null;
	}
	
	
	/**
	 * Add an alarm to the database
	 * @param alarm Alarm to be added
	 * @return Response string from server
	 */
	public void addAlarm(int hour, int min, String name, boolean enabled, String med, ResponseCallback callback) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair(Constants.TIME_HOUR, Integer.toString(hour)));
		params.add(new BasicNameValuePair(Constants.TIME_MINUTE, Integer.toString(min)));
		
		mServerRequest.doPost(Constants.URL_ADD_ALARM, callback, params);
	}
	
	/**
	 * Remove alarm with the given Id from the database
	 * @param alarmId Id of {@link Alarm} to be removed
	 * @return Response string from server
	 */
	public String removeAlarm(int alarmId) {
		//TODO:  Implement function
		return "";
	}

	public static Context getContext() {
		return mContext;
	}

	public void setContext(Context mContext) {
		Controller.mContext = mContext;
	}

	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String mUsername) {
		this.mUsername = mUsername;
	}

	public String getId() {
		return mId;
	}

	public void setId(String mId) {
		this.mId = mId;
	}
	

}