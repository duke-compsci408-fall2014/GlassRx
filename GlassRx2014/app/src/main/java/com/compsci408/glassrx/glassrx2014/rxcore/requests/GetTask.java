package com.compsci408.glassrx.glassrx2014.rxcore.requests;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import com.compsci408.glassrx.glassrx2014.rxcore.Constants;

import android.os.AsyncTask;
import android.util.Log;

/**
 * {@link android.os.AsyncTask} for performing a GET request.
 * @author Evan
 */
public class GetTask extends AsyncTask<String, Void, JSONObject> {
	
	private static final String TAG = "GetTask";
	
	private InputStream is = null;
	private ResponseCallback mCallback;
	
	public GetTask(ResponseCallback callback) {
		mCallback = callback;
	}
	
	@Override
	protected JSONObject doInBackground(String... address) {
		
		URL url;
		HttpURLConnection urlConnection = null;
		String result = null;
		JSONObject json = null;
		
		try {
	        url = new URL(address[0]);
	        
	        urlConnection = (HttpURLConnection) url.openConnection();
	        
	        urlConnection.setReadTimeout(Constants.READ_TIMEOUT);
	        urlConnection.setConnectTimeout(Constants.CONNECT_TIMEOUT);
	        urlConnection.setRequestMethod(Constants.GET);
	        urlConnection.setDoInput(true);
	        urlConnection.connect();
	        
	        int response = urlConnection.getResponseCode();
	        Log.d(TAG, "Server response code:  " + response);
	        is = urlConnection.getInputStream();
	        result = RequestUtils.readStream(is);
	        Log.d(TAG, "Response:  " + result);
	        is.close();
	        json = new JSONObject(result);
	        
	    } catch (Exception e) {
	    	// TODO:  Improve exception handling
	        e.printStackTrace();
	    } finally {
	    	urlConnection.disconnect();
	    }
		return json;
	}
	
	@Override
	public void onPostExecute(JSONObject result) {
		mCallback.onResponseReceived(result);
		super.onPostExecute(result);
	}

}
