package com.compsci408.rxcore;

import java.util.List;

import com.compsci408.rxcore.alarms.Alarm;
import com.compsci408.rxcore.requests.ServerRequest;

import android.content.Context;

public class Controller {
	
	public static Controller instance;
	
	public Context mContext;
	private ServerRequest mServerRequest;
	
	public Controller getInstance(Context ctxt) {
		if (instance == null) {
			instance = new Controller();
			instance.mContext = mContext;
			mServerRequest = ServerRequest.getInstance(mContext);
		}
		instance.mContext = mContext;
		return instance;
	}
	
	/**
	 * Log user with given username and password into the system.
	 * @param username Username entered by user
	 * @param password Password entered by user
	 * @return Response string from server
	 */
	public String logIn(String username, String password) {
		//TODO:  Implement log in
		return "";
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
	public String addAlarm(Alarm alarm) {
		//TODO:  Implement function
		return "";
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
	

}
