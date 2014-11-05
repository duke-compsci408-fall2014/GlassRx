package com.compsci408.rxcore.requests;

import org.json.JSONObject;

/**
 * Callback fucntion for describing behavior
 * upon completed web requests.
 * @author Evan
 */
public abstract class ResponseCallback {

	public abstract void onResponseReceived(JSONObject response);
	
}
