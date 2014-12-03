package com.compsci408.glassrx.glassrx2014.rxcore.listeners;

public abstract class OnLoginAttemptedListener {
	
	public abstract void onLoginSuccess(int result);
	
	public abstract void onLoginFailed(String error);

}
