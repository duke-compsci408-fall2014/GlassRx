package com.compsci408.glassrx.glassrx2014.rxcore.requests;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.compsci408.glassrx.glassrx2014.rxcore.Constants;

/**
 * {@link android.os.AsyncTask} for performing a POST request.
 * @author Evan
 */
public class PostTask extends AsyncTask<String, Void, JSONObject> {

	private static final String TAG = "PostTask";
	
	private InputStream is = null;
	private ResponseCallback mCallback;
	private String mBody;
	
	public PostTask(ResponseCallback callback, String body) {
		mCallback = callback;
		mBody = body;
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
	        
	        //  Configure connection
	        urlConnection.setReadTimeout(Constants.READ_TIMEOUT);
	        urlConnection.setConnectTimeout(Constants.CONNECT_TIMEOUT);
	        urlConnection.setRequestMethod(Constants.POST);
	        urlConnection.setRequestProperty("Content-Type","application/json");  
	        urlConnection.setChunkedStreamingMode(0);
	        urlConnection.setDoInput(true);
	        urlConnection.setDoOutput(true);

	        //  Add POST parameters
	        OutputStream os = urlConnection.getOutputStream();
	        BufferedWriter writer = new BufferedWriter(
	                new OutputStreamWriter(os, "UTF-8"));
	        writer.write(mBody);
	        writer.flush();
	        writer.close();
	        
	        urlConnection.connect();
	        
	        os.close();

	        int response = urlConnection.getResponseCode();
	        Log.d(TAG, "Server response code:  " + response);
	        is = urlConnection.getInputStream();
	        result = RequestUtils.readStream(is);
	        Log.d(TAG, "Body:  " + mBody);
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
