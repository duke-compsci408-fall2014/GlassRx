package com.compsci408.rxcore;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.compsci408.rxcore.alarms.Alarm;
import com.compsci408.rxcore.datatypes.AccountType;
import com.compsci408.rxcore.datatypes.Medication;
import com.compsci408.rxcore.datatypes.Patient;
import com.compsci408.rxcore.datatypes.Schedule;
import com.compsci408.rxcore.listeners.OnLoginAttemptedListener;
import com.compsci408.rxcore.listeners.OnMedicationsLoadedListener;
import com.compsci408.rxcore.listeners.OnSchduleAddedListener;
import com.compsci408.rxcore.listeners.OnImageCapturedListener;
import com.compsci408.rxcore.listeners.OnMedInfoLoadedListener;
import com.compsci408.rxcore.listeners.OnPatientsLoadedListener;
import com.compsci408.rxcore.listeners.OnPictureTakenListener;
import com.compsci408.rxcore.listeners.OnScheduleLoadedListener;
import com.compsci408.rxcore.requests.ResponseCallback;
import com.compsci408.rxcore.requests.ServerRequest;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.SurfaceHolder;

public class Controller {

	public static Controller instance;
	
	private Context mContext;
	private static ServerRequest mServerRequest;
	private static CameraManager mCameraManager;
	
	private int mPatientId;
	private String mPatientName;
	private int mProviderId;
	private String mMedName;
	private int mMedId = 1;	
	
	
	public static Controller getInstance(Context ctxt) {
		if (instance == null) {
			instance = new Controller();
		}
		instance.setContext(ctxt);
		mServerRequest = ServerRequest.getInstance(ctxt);
		mCameraManager = CameraManager.getInstance();
		return instance;
	}
	
	public Context getContext() {
		return mContext;
	}

	public void setContext(Context mContext) {
		this.mContext = mContext;
	}	

	public int getPatientId() {
		return mPatientId;
	}

	public void setPatientId(int mPatientId) {
		this.mPatientId = mPatientId;
	}
	
	public String getPatientName() {
		return mPatientName;
	}

	public void setPatientName(String patientName) {
		this.mPatientName = patientName;
	}
	
	public int getProviderId() {
		return mProviderId;
	}

	public void setProviderId(int mProviderId) {
		this.mProviderId = mProviderId;
	}

	public String getMedName() {
		return mMedName;
	}
	
	public void setMedName(String mMedName) {
		this.mMedName = mMedName;
	}

	public int getMedId() {
		return mMedId;
	}

	public void setMedId(int mMedId) {
		this.mMedId = mMedId;
	}
	
	
	/**
	 * Check to see if device is already logged in
	 * @return Integer value corresponding to AccountType
	 * from last login, or -1 if not logged in
	 */
	public int checkLogin() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
		int login = pref.getInt(Constants.KEY_LOGIN, Constants.DEFAULT_VALUE);
		
		Calendar c = Calendar.getInstance(); 
		long seconds = c.getTimeInMillis();
		
		//  We are not logged in or have been inactive for over an hour
		if (login == Constants.DEFAULT_VALUE ||
				(seconds - pref.getLong(Constants.KEY_LAST_LOGIN, seconds))
				> Constants.LOGIN_TIMEOUT) {
			return Constants.DEFAULT_VALUE;
		}
		
		//  If we were logged in, set appropriate user information
		if (login == Constants.LOGGED_IN) {
			if (pref.getInt(Constants.KEY_ACCOUNT_TYPE, Constants.DEFAULT_VALUE)
					== AccountType.PATIENT.getId()) {
				setPatientId(pref.getInt(Constants.KEY_LAST_USER, -1));
			}
			else if (pref.getInt(Constants.KEY_ACCOUNT_TYPE, Constants.DEFAULT_VALUE)
					== AccountType.PROVIDER.getId()) {
				setProviderId(pref.getInt(Constants.KEY_LAST_USER, -1));
			}
		}
		
		return pref.getInt(Constants.ACCOUNT_TYPE, 
				pref.getInt(Constants.KEY_ACCOUNT_TYPE, Constants.DEFAULT_VALUE));
	}
	
	/**
	 * Log user with given username and password into the system.
	 * @param username Username entered by user
	 * @param password Password entered by user
	 * @param accountType Account type selected (patient or provider)
	 * @param callback ResponseCallback from server
	 * @return Response string from server
	 */
	public void logIn(String username, final String password, final String accountType, 
			final OnLoginAttemptedListener listener) {
		
		String url;
		
		if (accountType.equals(AccountType.PATIENT.getName())) {
			url = Constants.URL_LOG_IN_PATIENT + username + "%27" + Constants.URL_SUFFIX;
		}
		else {
			url = Constants.URL_LOG_IN_PROVIDER + username + "%27" + Constants.URL_SUFFIX;
		}
		
		
		mServerRequest.doGet(url, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				JSONObject user = null;
				try {
					JSONArray array = response.getJSONArray("record");
					String userString = array.getString(0);
					user = new JSONObject(userString);
				} catch (JSONException e1) {
					// TODO Improve exception handling
					e1.printStackTrace();
				}
				try {
					if (user.getString(Constants.PASSWORD).equals(password)) {
						SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
						Editor editor = pref.edit();
						
						Calendar c = Calendar.getInstance(); 
						long seconds = c.getTimeInMillis();
						
						editor.putInt(Constants.KEY_LOGIN, Constants.LOGGED_IN);
						editor.putLong(Constants.KEY_LAST_LOGIN, seconds);
						
						if (accountType.contains(AccountType.PATIENT.getName())) {
							setPatientId(user.getInt("patientID"));
							editor.putInt(Constants.KEY_ACCOUNT_TYPE, AccountType.PATIENT.getId());
							editor.putInt(Constants.KEY_LAST_USER, getPatientId());
							listener.onLoginSuccess(AccountType.PATIENT.getId());
						} else {
							setProviderId(user.getInt("physicianID"));
							editor.putInt(Constants.KEY_ACCOUNT_TYPE, AccountType.PROVIDER.getId());
							editor.putInt(Constants.KEY_LAST_USER, getProviderId());
							listener.onLoginSuccess(AccountType.PROVIDER.getId());
						}
						editor.commit();
					} else {
						//TODO:  add more robust error checking
						listener.onLoginFailed("An error occurred.\nPlease try again.");
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
	    	
	    });
				
	}
	
	/**
	 * Log user out of system
	 * @param username Username entered by user
	 * @return Response string from server
	 */
	public void logOut(String username) {
		//TODO:  Implement log out
		
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
		Editor editor = pref.edit();
		
		Calendar c = Calendar.getInstance(); 
		long seconds = c.getTimeInMillis();
		
		editor.putInt(Constants.KEY_LOGIN, Constants.DEFAULT_VALUE);
		editor.putLong(Constants.KEY_LAST_LOGIN, seconds);
		
		editor.commit();
	}
	
	/**
	 * Get all alarms associated with given patient
	 * @param patientId Id of patient
	 * @return List of {@link Alarm}s for given patient
	 */
	public List<Alarm> getAllAlarms(int patientId) {
		//TODO:  Implement function
		return null;
	}
	
	
	/**
	 * Get alarm which will occur soonest from now
	 * @param alarms List of {@link Alarm}s
	 * @return Next alarm to occur
	 */
	public Alarm getNextAlarm(List<Alarm> alarms) {
		//TODO:  Implement function
		return null;
	}
	
	
	/**
	 * Add an alarm to the database
	 * @param alarm Alarm to be added
	 * @return Response string from server
	 */
	public void addSchedule(Schedule schedule, final OnSchduleAddedListener listener) {
		
		String json = new Gson().toJson(schedule, Schedule.class);
		
		mServerRequest.doPost(Constants.URL_ADD_ALARM, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				//TODO distinguish between successes and failures
				listener.onScheduleAdded(true);
			}
			
		}, json);
	}
	
	/**
	 * Remove alarm with the given Id from the database
	 * @param alarmId Id of {@link Alarm} to be removed
	 * @return Response string from server
	 */
	public String removeAlarm(int alarmId) {
		//TODO:  Implement function
		return "";
	}
	
	public void getPatients(final OnPatientsLoadedListener listener) {
		
		final List<Patient> patients = new ArrayList<Patient>();
		final Gson gson = new Gson();
		
		String url = Constants.URL_GET_PATIENTS + Integer.toString(mProviderId) 
				+ Constants.URL_SUFFIX;
		
		mServerRequest.doGet(url, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				try {
					JSONArray array = response.getJSONArray("record");
					for (int i = 0; i < array.length(); i++) {
						Patient p = gson.fromJson(array.getString(i), Patient.class);
						patients.add(p);
					}
					
					listener.onPatientsLoaded(patients);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
		});
	}
	
	public void getPatientSchedule(final OnScheduleLoadedListener listener) {
		
		final List<Schedule> schedule = new ArrayList<Schedule>();
		final Gson gson = new Gson();
		
		String url = Constants.URL_GET_PATIENT_SCHEDULE + Integer.toString(mPatientId) 
				+ "%27&app_name=glass-rx";
		
		mServerRequest.doGet(url, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				try {
					JSONArray array = response.getJSONArray("record");
					for (int i = 0; i < array.length(); i++) {
						Schedule s = gson.fromJson(array.getString(i), Schedule.class);
						schedule.add(s);
					}
					
					listener.onScheduleLoaded(schedule);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
		});
	}

	
	
	public void filterMedications(String input, final OnMedicationsLoadedListener listener) {
		String url = Constants.URL_GET_MEDS_BY_NAME + input + "%27" + Constants.URL_SUFFIX;
		
		mServerRequest.doGet(url, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				JSONArray array = null;
				try {
					array = response.getJSONArray("record");
					ArrayList<Medication> meds = new ArrayList<Medication>();
					for (int i = 0; i < array.length(); i++) {
						meds.add(new Gson().fromJson(array.getString(i), Medication.class));
					}
					listener.onMedicationsLoaded(meds);
				} catch (JSONException e) {
					// TODO Improve exception handling
					e.printStackTrace();
				}
				
			}
			
		});
	}
	
	/**
	 * Get general information for the selected medication
	 * and update the UI as indicated by the provided listener.
	 * @param listener Listener which describes UI updates upon
	 * successful web request.
	 */
	public void getMedication(final OnMedInfoLoadedListener listener) {
		
		 String url = Constants.URL_GET_PATIENT_SCHEDULE + mMedName 
			+ "%27" + Constants.URL_SUFFIX;
		
		mServerRequest.doGet(url, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				Medication med = new Gson().fromJson(response.toString(), Medication.class);
				listener.onMedInfoLoaded(med);
			}
			
		});
	}
	
	
	// -------------------------Camera functionality below-------------------------------

	
	/**
	 * Take a picture using the device's camera
	 * @param upload Boolean indicating whether
	 * the image should be uploaded.  If false,
	 * save image locally.
	 */
	public void takePicture(SurfaceHolder preview, final boolean upload, final OnPictureTakenListener callback) {
		
		
		OnImageCapturedListener listener = new OnImageCapturedListener() {

			@Override
			public void onImageCaptured(byte[] data) {
				boolean success = false;
				if (!upload) {
					success = saveImage(data, mMedName, mContext);
				};
				
				//TODO  Implement image upload
				
				callback.onPictureTaken(success);
			}
			
		};
		
		mCameraManager.captureImage(preview, listener);
	}
	
	/**
	 * Save an image locally using the given file name
	 * and create a thumbnail for it
	 * @param data Byte array of image data
	 * @param name Name of file to be created
	 * @return Boolean indicating whether image was
	 * successfully saved
	 */
	private static boolean saveImage(byte[] data, String name, Context context) {
		File filename;
		Bitmap bitmap;
		boolean success = false;
		try {
			String path = Environment.getExternalStorageDirectory().toString();
			
			if (!new File(path + Constants.RX_DIRECTORY).isDirectory()) {
				new File(path + Constants.RX_DIRECTORY).mkdirs();
			}
			
			filename = new File (path + Constants.RX_DIRECTORY + name.toLowerCase(Locale.US));
			
			FileOutputStream out = new FileOutputStream(filename);
			
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			
			out.flush();
			out.close();
			
			MediaStore.Images.Media.insertImage(context.getContentResolver(), 
					filename.getAbsolutePath(), filename.getName(), filename.getName());
			success = true;
		} catch (Exception e) {
			//TODO Improve exception handling
			e.printStackTrace();
		}
		return success;
		
	}
	
	private static void uploadImage(byte[] data) {
		
		String dataString = Base64.encodeToString(data, Base64.DEFAULT);
		
		mServerRequest.doPost(Constants.URL_UPLOAD_IMAGE, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				
			}
			
		}, dataString);
	}
	
	
}
