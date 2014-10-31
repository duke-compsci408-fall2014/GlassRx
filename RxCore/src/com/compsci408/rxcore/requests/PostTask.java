package com.compsci408.rxcore.requests;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.NameValuePair;
import android.os.AsyncTask;
import android.util.Log;

import com.compsci408.rxcore.Constants;

/**
 * {@link AsyncTask} for performing a POST request.
 * @author Evan
 */
public class PostTask extends AsyncTask<String, Void, String> {

	private static final String TAG = "PostTask";
	
	private InputStream is = null;
	private ResponseCallback mCallback;
	private List<NameValuePair> mParams;
	
	public PostTask(ResponseCallback callback, List<NameValuePair> params) {
		mCallback = callback;
		mParams = params;
	}
	
	@Override
	protected String doInBackground(String... address) {
		URL url;
		HttpURLConnection urlConnection = null;
		String result = null;
		try {
	        url = new URL(address[0]);

	        urlConnection = (HttpURLConnection) url.openConnection();
	        
	        //  Configure connection
	        urlConnection.setReadTimeout(Constants.READ_TIMEOUT);
	        urlConnection.setConnectTimeout(Constants.CONNECT_TIMEOUT);
	        urlConnection.setRequestMethod(Constants.POST);
	        urlConnection.setChunkedStreamingMode(0);
	        urlConnection.setDoInput(true);
	        urlConnection.setDoOutput(true);

	        //  Add POST parameters
	        OutputStream os = urlConnection.getOutputStream();
	        BufferedWriter writer = new BufferedWriter(
	                new OutputStreamWriter(os, "UTF-8"));
	        writer.write(RequestUtils.getQuery(mParams));
	        writer.flush();
	        writer.close();
	        
	        urlConnection.connect();
	        
	        os.close();

	        int response = urlConnection.getResponseCode();
	        Log.d(TAG, "Server response code:  " + response);
	        is = urlConnection.getInputStream();
	        result = RequestUtils.readStream(is);
	        Log.d(TAG, "Response:  " + result);
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
