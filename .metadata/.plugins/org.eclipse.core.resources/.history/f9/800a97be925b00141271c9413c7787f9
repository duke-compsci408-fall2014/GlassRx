package com.compsci408.rxcore.requests;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;

/**
 * Class for handling requests
 * @author Evan
 */
public class ServerRequest {
	
	private static ServerRequest instance;
	
	private Context mContext;
	
	public static ServerRequest getInstance(Context context) {
		if (instance == null) {
			instance = new ServerRequest();
			instance.mContext = context;
		}
		return instance;
	}
	
	public void doGet(String url, ResponseCallback callback) {
		if (RequestUtils.checkConnection(mContext)) {
			new GetTask(callback).execute(url);
		}
	}
	
	public void doPost(String url, ResponseCallback callback, List<NameValuePair> params) {
		if (RequestUtils.checkConnection(mContext)) {
			new PostTask(callback, params).execute(url);
		}
	}

}
