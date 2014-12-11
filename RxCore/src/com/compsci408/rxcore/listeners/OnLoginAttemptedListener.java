package com.compsci408.rxcore.listeners;

public abstract class OnLoginAttemptedListener {
	
	/**
	 * Callback function for describing behavior
	 * upon successful log in.
	 * @param result Integer response from server
	 */
	public abstract void onLoginSuccess(int result);
	
	
	/**
	 * Callback function for describing behavior
	 * upon failed log in.
	 * @param error String error message
	 */
	public abstract void onLoginFailed(String error);

}
