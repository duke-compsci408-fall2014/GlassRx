package com.compsci408.rxcore.listeners;

public abstract class OnLoginAttemptedListener {
	
	public abstract void onLoginSuccess(int result);
	
	public abstract void onLoginFailed(String error);

}
