package com.compsci408.rxcore.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.NameValuePair;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Some utilities for use when performing web requests.
 * @author Evan
 */
public class RequestUtils {
	
	/**
	 * Read an {@link InputStream} into a String
	 * @param stream  Stream to be read
	 * @return  String result of input stream
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static String readStream(InputStream stream) throws IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(stream));
		StringBuilder result = new StringBuilder();
		String line;
		while ((line = r.readLine()) != null) {
		   result.append(line);
		}
		return result.toString();
	}
	
	/**
	 * Check the current network connectivity
	 * @return  Boolean indicating availability of network connection
	 */
	public static boolean checkConnection(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) 
		        context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return networkInfo != null && networkInfo.isConnected();
	}
	
	/**
	 * Build a query string from the given parameters
	 * @param params  Parameters for the query
	 * @return  String representing query
	 * @throws UnsupportedEncodingException
	 */
	public static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
		if (params == null) {
			return "";
		}
		StringBuilder result = new StringBuilder();
	    boolean first = true;

	    for (NameValuePair pair : params)
	    {
	        if (first) {
	            first = false;
	        } else {
	            result.append("&");
	        }

	        if (!pair.getName().equals("")) {
	        	result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
		        result.append("=");
	        }
	        result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
	    }

	    return result.toString();
	}	

}
