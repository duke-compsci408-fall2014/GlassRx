package com.compsci408.rxcore.requests;

import org.json.JSONObject;


public abstract class ResponseCallback {

	/**
	 * Callback function for describing behavior
	 * upon completed web requests.
	 * @param response JSONObject representation of server response
	 */
	public abstract void onResponseReceived(JSONObject response);
	
}
