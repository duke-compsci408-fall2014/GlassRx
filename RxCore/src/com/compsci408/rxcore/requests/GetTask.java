package com.compsci408.rxcore.requests;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.compsci408.rxcore.datatypes.Constants;

import android.os.AsyncTask;
import android.util.Log;

/**
 * {@link AsyncTask} for performing a GET request.
 * @author Evan
 */
public class GetTask extends AsyncTask<String, Void, String> {
	
	private static final String TAG = "PostTask";
	
	private InputStream is = null;
	private ResponseCallback mCallback;
	
	public GetTask(ResponseCallback callback) {
		mCallback = callback;
	}
	
	@Override
	protected String doInBackground(String... address) {
		
		URL url;
		HttpURLConnection urlConnection = null;
		String result = null;
		
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
	        Log.d(TAG, "Respnse:  " + result);
	        is.close();
	    } catch (Exception e) {
	    	// TODO:  Improve exception handling
	        e.printStackTrace();
	    } finally {
	    	urlConnection.disconnect();
	    }
		return result;
	}
	
	@Override
	public void onPostExecute(String result) {
		mCallback.onResponseReceived(result);
		super.onPostExecute(result);
	}

}
